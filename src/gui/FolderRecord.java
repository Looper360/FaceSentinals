/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;    
import javax.swing.event.*;  
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class FolderRecord {
    
    public static void RecordOfFolders() throws IOException{  
           JFrame f = new JFrame("Locked Folder Records") { 
            Image backgroundImage = ImageIO.read(new File("bg.png"));
            @Override
             public void paint( Graphics g ) { 
             super.paint(g);
             g.drawImage(backgroundImage, 0, 0, null);
  }
};
            Scanner sc = null;
        try {
            sc = new Scanner(new File("LockedFolderRecord.txt"));
        } catch (FileNotFoundException ex) {
                 
        }
            Record rec[]=new Record[30];
            int i=0;
            while(sc.hasNextLine()){
                   try{
                    String STR[]=sc.nextLine().split(";");
                    String lockedPath=STR[0];
                    String NAME=STR[2];
                    rec[i]=new Record(STR[1],STR[0],STR[2]);
                   }catch(Exception e)
                   {
                                FaceRec FR=new FaceRec();
                                FR.speaker("The Folder Record is empty sir");
                                JOptionPane.showMessageDialog(null,"The Folder Record is empty sir");
                                return;
                   }
                    
                    i++;
                    
            }
            int n=i;
             String data[][]=new String[n+1][n+1];
             
         
            for(int k=0;k<n;k++)
            {
                for(int j=0;j<2;j++)
                {
                    if(j==0)data[k][j]=rec[k].Path;
                    else data[k][j]=rec[k].Name;
                }
            }
         
           /*
            String data[][] ={ {"101","Amit"},    
                          {"102","Jai"},    
                          {"101","Sachin"}};   
            */
           
             JTable jt;
            try{  
            String column[]={"Locked Folders","Name"};         
             jt=new JTable(data,column);  
            
            jt.setCellSelectionEnabled(true);  
            ListSelectionModel select= jt.getSelectionModel();  
            select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
           
            select.addListSelectionListener(new ListSelectionListener() {  
              public void valueChanged(ListSelectionEvent e) {  
                String Data = null;  
                int[] row = jt.getSelectedRows();  
                int[] columns = jt.getSelectedColumns();  
               
                for (int i = 0; i < row.length; i++) {  
                  for (int j = 0; j < columns.length; j++) {  
                        Data = (String) jt.getValueAt(row[i], columns[j]);  
                  } 
                }  
                System.out.println("Table element selected is: " + Data);    
              }
              
            }); 
             }catch(Exception e)
             {
                                FaceRec FR=new FaceRec();
                                FR.speaker("The Folder Record is empty sir");
                                JOptionPane.showMessageDialog(null,"The Folder Record is empty sir");
                                return;
                 }
            
            
            
            JScrollPane sp=new JScrollPane(jt);    
            
            JButton b=new JButton("Back");  
             b.setBounds(650,300,95,30); 
              
              
              b.addActionListener((ActionEvent e) -> {
                  
                  f.dispose();
                  // System.out.println("LOL");
            });  
             
             
               f.add(b);
            f.add(sp);  
            f.setBounds(200,200,800, 400);  
            f.setResizable(false);
            f.setVisible(true);  
             f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
           
          }  
}
