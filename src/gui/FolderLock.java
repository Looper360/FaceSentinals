package gui;

import java.io.*;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

class Record{
    String label;
    String Path;
    String Name;
    
    Record(String label,String path,String Name)
    {
        this.label=label;
        this.Path=path;
        this.Name=Name;
    }
}
public class FolderLock {
    static String name;
    
     static BufferedWriter bw1;
     static boolean unlocked=false;
     
     static void SetName(String Name)
     {
         name=Name;
     }
     static void cmd(String command) throws IOException
     {
         
          String ss = null;
        Runtime obj = null;
        
         Process p = Runtime.getRuntime().exec(command);
       
        BufferedWriter writeer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
        writeer.write("dir");
        writeer.flush();
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        System.out.println("Here is the standard output of the command:\n");
        while ((ss = stdInput.readLine()) != null) {
            System.out.println(ss);
        }
        System.out.println("Here is the standard error of the command (if any):\n");
        while ((ss = stdError.readLine()) != null) {
             System.out.println(ss);
        }

    
     }
    static void folderLock(String folderPath,int ck) throws Exception
    {
       
        
        String command="";
        String[] arg1 = new String[]{"-u root", "-h localhost"};
        
    try {
       
        if(ck==1){      //lock
                
             FileWriter wr1=new FileWriter("LockedFolderRecord.txt",true);  //append mode
           
             try (BufferedWriter bw = new  BufferedWriter(wr1)) {
                int sum=MyThread.LastLabelFinder();
               
                bw.write(folderPath+";"+sum+";"+name);
                bw.newLine();
                bw.close();
            }
            command = "cmd.exe /c start "+"cacls "+folderPath+" /p everyone:n";
          //  command="move "+folderPath+" Locker";       //moving the selected folder to the Locker folder 
            
        }  
        else if(ck==2){
            Scanner sc=new Scanner(new File("LockedFolderRecord.txt"));
            Record rec[]=new Record[30];
            int i=0;
            while(sc.hasNextLine()){
                String STR[]=sc.nextLine().split(";");
                String lockedPath=STR[0];
                int fl=Integer.parseInt(STR[1]);
                
                //System.out.println(lockedPath+" "+fl);
                
                if(FaceRec.a==fl && lockedPath.equals(folderPath))
                {
                    FaceRec object=new FaceRec();
                     String speakVoice="Folder Unlocked Sir";
                     object.speaker(speakVoice);
                      JOptionPane.showMessageDialog(null,"Folder Unlocked Sir");
                     unlocked=true;
                      
                }
                else {
                    rec[i]=new Record(STR[1],STR[0],STR[2]);
                    System.out.println(rec[i].label+"->"+rec[i].Path);
                   i++;
                        }
                    
                }
             int n=i;
                     FileWriter wr1=new FileWriter("LockedFolderRecord.txt",false);  //write mode
                      bw1 = new  BufferedWriter(wr1);
                      i=0;
                    while(i<n){
                    try{   
                        bw1.write(rec[i].Path+";"+rec[i].label+";"+rec[i].Name);
                            bw1.newLine();
                            i++;
                    }catch(IOException e)
                            {
                                System.out.println("HI");
                            }
                    }
             bw1.close();
             if(unlocked==false)
             {
                    FaceRec object=new FaceRec();
                     String speakVoice="Sorry Sir";
                    object.speaker(speakVoice);
                    JOptionPane.showMessageDialog(null,"Sorry Sir,You Are Not Authorized Unlock the Folder");
                    return ;
            
            }
            command = "cmd.exe /c start "+"cacls "+folderPath+" /p everyone:f";
           
        }   //unlock
        
        cmd(command);
       
    } catch (IOException e) {
        System.out.println("FROM CATCH" + e.toString());
    }
        
         
         
         
    }
    // public static void main(String st[]) throws Exception
      static void folderChooser(int ck) throws Exception
     {
           // System.out.println(Thread.currentThread().getName() );
         String str="";
         
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Folder Lock");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
          
            File file=chooser.getSelectedFile();
            str = file.toString();
            System.out.println("getSelectedFile() : " + str);
          //check if selected path is equal to the already locked path
           String checkPath="";
           
            Scanner sc1=null;
          try{
              
             sc1=new Scanner(new File("LockedFolderRecord.txt"));
            
          
          }
          catch(FileNotFoundException e)
          {
              
          }
          while(sc1.hasNextLine())
          {
              String STR1[]=sc1.nextLine().split(";");
              checkPath=STR1[0];
             if(str.equals(checkPath) && ck==1)
             {
                FaceRec ob1=new FaceRec();
                ob1.speaker("The folder is already Locked Sir");
                JOptionPane.showMessageDialog(null,"The folder is already Locked Sir");
                folderChooser(1);
                return;
             }
             
          }          
            
          folderLock(str,ck);        //send 1 for lock & 2 for unlock
        } else {
          System.out.println("No Selection ");
        }
     }
  
}