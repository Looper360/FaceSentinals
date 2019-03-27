/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import static com.sun.javafx.geom.Point2D.distance;
import static com.sun.javafx.geom.Vec2f.distance;
import static gui.GUI_FaceRecog.a;
import static gui.Test.netIsAvailable;
import static java.awt.geom.Point2D.distance;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import org.bytedeco.javacpp.indexer.FloatIndexer;
import static org.bytedeco.javacpp.opencv_core.CV_32F;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_dnn.Net;
import static org.bytedeco.javacpp.opencv_dnn.blobFromImage;
import static org.bytedeco.javacpp.opencv_dnn.readNetFromCaffe;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_imgproc.rectangle;
import static org.bytedeco.javacpp.opencv_imgproc.resize;
import static org.bytedeco.javacpp.opencv_videoio.CV_CAP_PROP_FRAME_HEIGHT;
import static org.bytedeco.javacpp.opencv_videoio.CV_CAP_PROP_FRAME_WIDTH;
import org.bytedeco.javacpp.opencv_videoio.VideoCapture;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter;
//import org.opencv.imgproc.Imgproc;

/*
 *
 * This example is based on this code
 * https://github.com/opencv/opencv/blob/master/modules/dnn/misc/face_detector_accuracy.py
 *
 * To run this example you need two files: deploy.prototxt can be downloaded
 * from
 * https://github.com/opencv/opencv/blob/master/samples/dnn/face_detector/deploy.prototxt
 *
 * and res10_300x300_ssd_iter_140000.caffemodel
 * https://github.com/opencv/opencv_3rdparty/blob/dnn_samples_face_detector_20170830/res10_300x300_ssd_iter_140000.caffemodel
 *
 */

 class MyThread extends Thread{
    Scanner sc=new Scanner(System.in);
    private static Rect R;
    private static Mat image;
    static CanvasFrame mainframe;
   
    int check_lock=0;
    public static boolean recog=false;
    MyThread(int check_lock)
    {
        this.check_lock=check_lock;
    }
     static void getFace(Rect R,Mat image)
     {         
         MyThread.R=R;
         MyThread.image=image;
            
     }
      
                      
                   
    static int LastLabelFinder()
      {
        File f=new File("LastLabel.txt");
        Scanner sc=null;
       try {
           sc = new Scanner(f);
       } catch (FileNotFoundException ex) {
          
       }
        
        int Last_label=sc.nextInt();
        
        return Last_label;
     }
    @Override
        public  void run() 
        {
        try {    
            Thread.sleep(2000);     //mythread needs a break while main thread detects the face for recognition
        } catch (InterruptedException ex) {
            
        }
        if(check_lock==1){  //when locking it will take 20 images and username  
          
         
        System.out.println("check_lock: "+check_lock);
                     try {
                         GoogleSpeechRecognition A=new GoogleSpeechRecognition();
                         Thread.sleep(50);
                         
                        if(netIsAvailable())
                         {
                            System.out.println("Internet Connection On");
                            if(mainframe.isVisible()) A.Jarvis();
                         }
                        else
                        {
                             JOptionPane.showMessageDialog(null,"No internet connection");
                        }
                        while(true){ 
                            
                           Thread.sleep(50);
                           // System.out.println(Thread.currentThread().getName() );
                         if(DeepLearningFaceDetection.ck==1)  //for snaping and storing in dataset
                            {
                                int i;
                                //System.out.println(Thread.currentThread().getName() +"huh");

                                 String name = JOptionPane.showInputDialog( "What's your name?");
                                 FolderLock.SetName(name);      //setting the name
                               //  FolderLock.cmd("cmd.exe /c start "+"cacls "+"D:\\netbeansProject\\FaceRecognition\\DataSet"+" /p everyone:f");  //temporary unlock for storing images
                                 
                                  FaceRec FR=new FaceRec();
                                  FR.speaker("You are about to take 20 images in 10 seconds Sir");
                                  JOptionPane.showMessageDialog(null,"You are about to take 20 images in 10 seconds");
                                   DeepLearningFaceDetection.SNAP=true;
                                  // get the user's input. note that if they press Cancel, 'name' will be null
                                    System.out.println("The user's name is:"+ name); 
                                 String s="";
                                 int new_label=LastLabelFinder()+1;      //here will be a function  that will return the last label of database  .now assuming 11
                                // putText(image, "SNAPSHOT taken : "+ , new Point(10, 20), 0, 0.5, new Scalar(255, 0, 0, 0));
                                 for(i=1;i<=20;i++){
                                     
                                            Mat croppedface= image.apply(R);
                                            String inputImagePath = "./DataSet/";          //path of database
                                            if(i<10 && new_label<10)s="0"+new_label+"-"+name+"_0";             //here a getText() will get the username
                                            else if(i<10 && new_label>=10)s=new_label+"-"+name+"_0";
                                            else if(i>=10 && new_label<10)s="0"+new_label+"-"+name+"_";
                                            else if(i>=10 && new_label>=10) s=new_label+"-"+name+"_";
                                            s+=i+".png";
                                            inputImagePath+=s;
                                            System.out.println(s);
                                             System.out.println("Snap taken");
                                              imwrite(inputImagePath,croppedface);

                                              ImageResizer.image_resizer (inputImagePath);
                                              
                                              putText(image, "SNAPSHOT taken : "+i , new Point(10, 20), 0, 0.5, new Scalar(255, 0, 0, 0));
                                              Thread.sleep(500);
                                             
                                 }
                                 
                                  //FolderLock.cmd("cmd.exe /c start "+"cacls "+"D:\\netbeansProject\\FaceRecognition\\DataSet"+" /p everyone:n");   //lock the dataset after storing images
                                     try{
            FileWriter wr=new FileWriter("LastLabel.txt");
             BufferedWriter bw=new  BufferedWriter(wr);
            
            //System.out.println(stack.lastElement());
           int Last_label=new_label;
          
           bw.write( String.valueOf(Last_label));
           bw.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }

                                FR.speaker("You have taken 20 images of the User Sir");
                                JOptionPane.showMessageDialog(null,"You have taken 20 images of the User");


                               DeepLearningFaceDetection.ck=0;
                               FolderLock.folderChooser(1); 
                            }
                        }
                     }
                      catch ( InterruptedException e) {
                            System.out.println("OTHER ERROR");
                      } catch (IOException ex) {
                Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
            }
                    // while(true)DeepLearningFaceDetection.ck=sc.nextInt();
           
                // while(true)DeepLearningFaceDetection.ck=sc.nextInt();
                
           
        }
        else if(check_lock==2){//when unlocking take one image and match with dataset
                System.out.println("check_lock: "+check_lock);
            GoogleSpeechRecognition A=new GoogleSpeechRecognition();
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                if(netIsAvailable())
                {
                     System.out.println("Internet Connection On");
                     if(mainframe.isVisible())A.Jarvis();
                }
                else
                {
                     JOptionPane.showMessageDialog(null,"No internet connection");
                }
               
            } catch (IOException ex) {
                
            }
           while(true){  
               try {
                          Thread.sleep(100);
                      } catch (InterruptedException ex) {
                          Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
                      }
             if(recog)
             {
                  System.out.println("Recognising");
                Mat croppedface= image.apply(R);
                String inputImagePath = "test.png";
                
                imwrite(inputImagePath,croppedface);

                ImageResizer.image_resizer (inputImagePath);
               

               FaceRec object1=new FaceRec();
            try {
              //  FolderLock.cmd("cmd.exe /c start "+"cacls "+"D:\\netbeansProject\\FaceRecognition\\DataSet"+" /p everyone:f");      //unlock for recognizing
                object1.FaceRecognize();      //call for face recognition program
                
               // FolderLock.cmd("cmd.exe /c start "+"cacls "+"D:\\netbeansProject\\FaceRecognition\\DataSet"+" /p everyone:n");   //lock the dataset after recognition
                      try {
                          Thread.sleep(50);
                      } catch (InterruptedException ex) {
                          Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
                      }
            } catch (FileNotFoundException ex) {
               
            }      
            //before getting out of the condition recog must be false
           recog=false;
            }
           }
        }
        }
        }
    
public class DeepLearningFaceDetection {
    static boolean SNAP=false;
    static int ck=0;
    private static final String PROTO_FILE = "deploy.prototxt";
    private static final String CAFFE_MODEL_FILE = "res10_300x300_ssd_iter_140000.caffemodel";
    private static final OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
    private static Net net = null;
    
    static boolean termination=false;
    
    static {
        net = readNetFromCaffe(PROTO_FILE, CAFFE_MODEL_FILE);
    }
   
    private static void detectAndDraw(Mat image) throws InterruptedException {//detect faces and draw a blue rectangle arroung each face
        

        resize(image, image, new Size(300, 300));//resize the image to match the input size of the model

        //create a 4-dimensional blob from image with NCHW (Number of images in the batch -for training only-, Channel, Height, Width) dimensions order,
        //for more detailes read the official docs at https://docs.opencv.org/trunk/d6/d0f/group__dnn.html#gabd0e76da3c6ad15c08b01ef21ad55dd8
        Mat blob = blobFromImage(image, 1.0, new Size(300, 300), new Scalar(104.0, 177.0, 123.0, 0), false, false);

        net.setInput(blob);//set the input to network model
        Mat output = net.forward();//feed forward the input to the netwrok to get the output matrix

        Mat ne = new Mat(new Size(output.size(3), output.size(2)), CV_32F, output.ptr(0, 0));//extract a 2d matrix for 4d output matrix with form of (number of detections x 7)

        FloatIndexer srcIndexer = ne.createIndexer(); // create indexer to access elements of the matric

        for (int i = 0; i < output.size(3); i++) {//iterate to extract elements
            float confidence = srcIndexer.get(i, 2);
            float f1 = srcIndexer.get(i, 3);
            float f2 = srcIndexer.get(i, 4);
            float f3 = srcIndexer.get(i, 5);
            float f4 = srcIndexer.get(i, 6);
            if (confidence > .6) {
                float tx = f1 * 300;//top left point's x
                float ty = f2 * 300;//top left point's y
                float bx = f3 * 300;//bottom right point's x
                float by = f4 * 300;//bottom right point's y
            
                rectangle(image, new Rect(new Point((int) tx, (int) ty), new Point((int) bx, (int) by)), new Scalar(0, 0,255, 0));//print blue rectangle 
                Rect R=new Rect(new Point((int) tx, (int) ty), new Point((int) bx, (int) by));
                MyThread.getFace(R,image);
               
                /*
                if(SNAP)
                {
                    for(i=1;i<=20;i++)
                    {
                        putText(image, "SNAPSHOT taken : "+i , new Point(10, 20), 0, 0.5, new Scalar(255, 0, 0, 0));
                        Thread.sleep(500);
                    }
                    SNAP=false;
                }
                */
                //distance measurement starts here
               //putText(image, "(x,y) "+(int)tx+" "+(int)ty, new Point((int)tx, (int)(ty-10)), 0, 0.5, new Scalar(0, 255, 0, 0));
              // putText(image, "(x,y) "+(int)bx+" "+(int)by, new Point((int)bx, (int)(by+10)), 0, 0.5, new Scalar(0, 255, 0, 0));
              int width=(int)(bx-tx);
              int height=(int)(by-ty);
              int area=width*height;
              //putText(image,"Area:"+area, new Point(10, 20), 0, 0.5, new Scalar(0, 255, 0, 0));
              
              if(area>8000 && area<15000){
                  putText(image,"Perfect", new Point(200, 20), 0, 0.5, new Scalar(0, 255, 0, 0));
              }
              else if(area>15000){
                  putText(image,"too close", new Point(200, 20), 0, 0.5, new Scalar(0, 0, 255, 0));
              }
              else if(area<8000){
                  putText(image,"too far", new Point(200, 20), 0, 0.5, new Scalar(0, 0, 255, 0));
              }
             //distance measurement ends here              
            }
        }
    }

    public  void detector(int check) throws InterruptedException {
        
        VideoCapture capture = new VideoCapture();
        
        capture.set(CV_CAP_PROP_FRAME_WIDTH, 1280);
        capture.set(CV_CAP_PROP_FRAME_HEIGHT, 720);
        
        
        //multithread begins
        MyThread t1=new MyThread(check);   //sends 1 or 2.....1 for lock.2 for unlock.
        t1.setName("My fav Thread-1");
        t1.start();
        
        if (!capture.open(0)) {
            System.out.println("Can not open the cam !!!");
        }
        
        Mat colorimg = new Mat();

        CanvasFrame mainframe = new CanvasFrame("Face Detection", CanvasFrame.getDefaultGamma() / 2.2);
        MyThread.mainframe=mainframe;
        
       mainframe.setLocationRelativeTo(null);
        
        
        mainframe.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        mainframe.setCanvasSize(600, 600);
        mainframe.setLocationRelativeTo(null);
        
        mainframe.setVisible(true);

        while (true) {
            while (capture.read(colorimg) && mainframe.isVisible()) {
                
                
                if(termination) {
                   mainframe.dispose();
                   
                   System.out.println("terminated");
                   //capture=null;
                  // mainframe=null;
                    termination=false;
                   
                    a.setVisible(true);
                    
                    
                }
                detectAndDraw(colorimg);
                mainframe.showImage(converter.convert(colorimg));
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }                
            }
        }    
    }
}
