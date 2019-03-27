/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.*; 
import java.io.File;
import java.io.FilenameFilter;
import java.nio.IntBuffer;
import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import org.bytedeco.javacpp.opencv_face.LBPHFaceRecognizer;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import com.sun.speech.freetts.*;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.bytedeco.javacpp.opencv_face.EigenFaceRecognizer;
import org.bytedeco.javacpp.opencv_face.FisherFaceRecognizer;



public class FaceRec {
    int Threshold=9;
    static int a;
    
    VoiceManager vm;
    Voice v;
    
    public void setThreshold(int val)
    {
        Threshold=val;
         System.out.println("Threshold value is :" + Threshold);
    }
    void speaker(String speakVoice)
    {
         //these codes for text to speech 
         System.setProperty("mbrola.base", "mbrola");
         vm=VoiceManager.getInstance();
         v=vm.getVoice("kevin16");
         v.allocate();
         
         try{
                v.speak(speakVoice);
         }catch(Exception e)
         {
             System.out.println(e);
         }
    }
     void FaceRecognize() throws FileNotFoundException 
    {
        String str="test.png";
        String trainingDir = "./DataSet/";
        Mat testImage = imread(str, CV_LOAD_IMAGE_GRAYSCALE);

        File root = new File(trainingDir);

        FilenameFilter imgFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                
                name = name.toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
            }
        };
        
            
        File[] imageFiles = root.listFiles(imgFilter);
        /*
            for (String fileName : root.list()) {
                    System.out.println(fileName);
                }
          */
        MatVector images = new MatVector(imageFiles.length);
         
         
        Mat labels = new Mat(imageFiles.length, 1, CV_32SC1);
        
        
        IntBuffer labelsBuf = labels.createBuffer();
       
        
        int counter = 0;
        
        String s;
        Map<String,Integer> map=new HashMap<String,Integer>(); 
        Stack<Integer>stack=new Stack<Integer>();
        
        for (File image : imageFiles) {
            Mat img = imread(image.getAbsolutePath(), CV_LOAD_IMAGE_GRAYSCALE);
            
            s=image.getName();
           //System.out.println(s.substring(3,s.length()-6));
           //System.out.println(s.substring(0,2));
           //stack.push(Integer.parseInt(s.substring(0,2)));     //getting the label of every image and pushing it in a Set for removing duplicate values
           
           s=s.substring(3,s.length()-7);   //spliting the name from whole image name
           
           
           int label = Integer.parseInt(image.getName().split("\\-")[0]);
           
          
           //inserting username and its corresponding label to the map
           map.put(s,label);  
           
            images.put(counter, img);

            labelsBuf.put(counter, label);
            
            counter++;
            
            
        }
        
      
       
       // FaceRecognizer faceRecognizer = FisherFaceRecognizer.create();
        //FaceRecognizer faceRecognizer = EigenFaceRecognizer.create();
         FaceRecognizer faceRecognizer = LBPHFaceRecognizer.create();
        
        faceRecognizer.train(images, labels);

        IntPointer label = new IntPointer(1);
        DoublePointer confidence = new DoublePointer(1);
      
        
            String name="";
            faceRecognizer.predict(testImage, label, confidence);
            
            double conf=confidence.get();
            System.out.println(conf);
          
               
             int predictedLabel = label.get(0);
           // System.out.println(confidence.get());
             //using map for finding the username of matched label
             int check=0;
             for(Map.Entry m:map.entrySet()){  
            //System.out.println(m.getKey()+" "+m.getValue()); 
            
            a=(int)m.getValue();
                        
            check=0;
            if(predictedLabel==a && conf<Threshold)
            {
                  
                    name=(String)m.getKey();
                    System.out.println("Name is: "+name);
                    System.out.println("Predicted label: " + predictedLabel);
                     String speakVoice="Welcome"+name;
                    speaker(speakVoice);
                     JOptionPane.showMessageDialog(null,"Welcome "+name);
                    try {  
                        FolderLock.folderChooser(2);
                    } catch (Exception ex) {
                        Logger.getLogger(FaceRec.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   check=1;
                   break;    
            }
              
        }
         if(check==0 && conf>=Threshold)
         {
             speaker("No match found Sir");
             JOptionPane.showMessageDialog(null,"No match found Sir");
         }
             
    }
   
}