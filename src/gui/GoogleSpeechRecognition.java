package gui;

import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.recognizer.GSpeechDuplex;
import com.darkprograms.speech.recognizer.GSpeechResponseListener;
import com.darkprograms.speech.recognizer.GoogleResponse;
import java.awt.Color;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import net.sourceforge.javaflacencoder.FLACFileWriter;

public class GoogleSpeechRecognition implements GSpeechResponseListener {
	 boolean activate=false;
          
	 void Jarvis() throws IOException {
           
		final Microphone mic = new Microphone(FLACFileWriter.FLAC);
		
                
		
                GSpeechDuplex duplex = new GSpeechDuplex("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
               
                
                  
                

		duplex.setLanguage("en");
		
		JFrame frame = new JFrame("XENO");
                frame.setSize(500, 500);
                frame.getContentPane().setBackground(Color.black);
                Icon icon = new ImageIcon("voice.gif");
                JLabel label = new JLabel(icon);
                frame.getContentPane().add(label);
        
		frame.setDefaultCloseOperation(3);
		JTextArea response = new JTextArea(3,10);
                
                
		response.setEditable(false);
		response.setWrapStyleWord(true);
		response.setLineWrap(true);
		
		final JButton record = new JButton("Record");
		final JButton stop = new JButton("Stop");
		stop.setEnabled(false);
		
		record.addActionListener((ActionEvent evt) -> {
                    new Thread(() -> {
                        try {
                            duplex.recognize(mic.getTargetDataLine(), mic.getAudioFormat());
                        } catch (InterruptedException | LineUnavailableException ex) {
                           
                            
                        }
                        
                    }).start();
                    record.setEnabled(false);
                    stop.setEnabled(true);
                });
		stop.addActionListener((ActionEvent arg0) -> {
                    mic.close();
                    duplex.stopSpeechRecognition();
                    record.setEnabled(true);
                    stop.setEnabled(false);
                });
                /*
		JLabel infoText = new JLabel(
				"<html><div style=\"text-align: center;\">Just hit record and watch your voice be translated into text.\n<br>Only English is supported by this demo, but the full API supports dozens of languages.<center></html>",
				
				0);*/
		//frame.getContentPane().add(infoText);
		//infoText.setAlignmentX(0.5F);
		JScrollPane scroll = new JScrollPane(response);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), 1));
		frame.getContentPane().add(scroll);
		JPanel recordBar = new JPanel();
		frame.getContentPane().add(recordBar);
		recordBar.setLayout(new BoxLayout(recordBar, 0));
		recordBar.add(record);
		recordBar.add(stop);
		frame.setVisible(true);
		frame.pack();
		
                
		frame.setLocationRelativeTo(null);
		
		duplex.addResponseListener(new GSpeechResponseListener() {
			String old_text = "";
			
                        
                        @Override
			public void onResponse(GoogleResponse gr) {
				String output = "";
				output = gr.getResponse();
				if (gr.getResponse() == null) {
					this.old_text = response.getText();
					if (this.old_text.contains("(")) {
						this.old_text = this.old_text.substring(0, this.old_text.indexOf('('));
					}
					System.out.println("Paragraph Line Added");
					this.old_text = ( response.getText() + "\n" );
					this.old_text = this.old_text.replace(")", "").replace("( ", "");
					response.setText(this.old_text);
					return;
				}
				if (output.contains("(")) {
					output = output.substring(0, output.indexOf('('));
				}
				if (!gr.getOtherPossibleResponses().isEmpty()) {
					output = output + " (" + (String) gr.getOtherPossibleResponses().get(0) + ")";
				}
				System.out.println("   "+output);
                                
                            
				response.setText("");
				response.append(this.old_text);
				response.append(output);
                                
                                FaceRec fr=new FaceRec();
                               
                                if((output.contains("hi") ||output.contains("hey")|| output.contains("zeno") || output.contains("jeno"))&&!activate)
                                {
                                        activate=true;
                                        
                                       fr.speaker("Yes .How can i help you sir");
                                }
                                if(output.contains("snap") || output.contains("map"))
                                {
                                    try {
                                        Thread.sleep(100);
                                         DeepLearningFaceDetection.ck=1;
                                    } catch (Exception ex) {
                                       
                                    }    
                                }
                                else if( output.contains("terminate"))
                                {
                                   DeepLearningFaceDetection.termination=true;
                                  //terminate the speech API frame
                                   frame.dispose();
                                   frame.setVisible(false);
                                }
                                else if(output.contains("recognize") || output.contains("recognise"))
                                {
                                     MyThread.recog=true;
                                     System.out.println("recog=true");
                                }
                               
			}
		});
                
	}
        
	
                       
	@Override
	public void onResponse(GoogleResponse paramGoogleResponse) {
		// TODO Auto-generated method stub
		
	}
}
