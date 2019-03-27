package gui;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

class MyThread1 extends Thread{
    DeepLearningFaceDetection ob;
    DeepLearningFaceDetection ob1;
    public void run()
    {
        if(GUI_FaceRecog.lock==1)
        {
             ob=new DeepLearningFaceDetection();
        try {
            Thread.sleep(100);
             
           ob.detector(1);
        } catch (InterruptedException ex) {
            //Logger.getLogger(MyThread1.class.getName()).log(Level.SEVERE, null, ex);
        }}
        else if(GUI_FaceRecog.lock==2)
        {
             ob1=new DeepLearningFaceDetection();
            try {
                Thread.sleep(100);
             
               ob1.detector(2);
            } catch (InterruptedException ex) {
               // Logger.getLogger(MyThread1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
public class GUI_FaceRecog extends javax.swing.JFrame {
   static  GUI_FaceRecog a; 
      int counter=0;
    MyThread1 tt;
    MyThread1 tt2;
    static int lock=0;
    public GUI_FaceRecog() {
        initComponents();
    }
   

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 565, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 343, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Face Recognition");
        setBackground(new java.awt.Color(0, 153, 0));
        setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jButton1.setBackground(new java.awt.Color(0, 102, 102));
        jButton1.setFont(new java.awt.Font("Segoe UI Black", 3, 14)); // NOI18N
        jButton1.setText("LOCK FOLDER");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 102, 102));
        jButton2.setFont(new java.awt.Font("Segoe UI Black", 3, 14)); // NOI18N
        jButton2.setText("UNLOCK FOLDER");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 102, 102));
        jButton3.setFont(new java.awt.Font("Segoe UI Black", 3, 16)); // NOI18N
        jButton3.setText("Folder Records");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 250, 330);

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/face1.gif"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(220, 0, 700, 330);

        jLabel2.setBackground(java.awt.Color.white);
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel2.setMaximumSize(new java.awt.Dimension(550, 382));
        jLabel2.setPreferredSize(new java.awt.Dimension(550, 382));
        getContentPane().add(jLabel2);
        jLabel2.setBounds(240, 0, 460, 330);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      //UNLOCK
       lock=2;
       tt2=new MyThread1();     
              
       a.setVisible(false);
       tt2.start();
           
       
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //LoCK
        lock=1;
          tt=new MyThread1();
      
      a.setVisible(false);
      tt.start();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
      
       try {
           FolderRecord.RecordOfFolders();
       } catch (IOException ex) {
           Logger.getLogger(GUI_FaceRecog.class.getName()).log(Level.SEVERE, null, ex);
       }
       
        
    }//GEN-LAST:event_jButton3ActionPerformed

  
    public static void main(String args[]) {
         try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception useDefault) {
                }
         
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
           // java.util.logging.Logger.getLogger(GUI_FaceRecog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
      
        /* Create and display the form */
                
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
           public void run() {
                 a=new GUI_FaceRecog();
           
                Image icon = Toolkit.getDefaultToolkit().getImage("images.jpg");
                a.setIconImage(icon);
                a.setSize(704,370);
                a.setResizable(false); 
                a.setLocationRelativeTo(null);
                a.setVisible(true);   
                a.setDefaultCloseOperation(a.EXIT_ON_CLOSE);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
