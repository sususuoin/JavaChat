package Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class emoticon extends JFrame {
   private JPanel a = new JPanel();
      emoticon(){
      Emoticon_init();
      
      
   }
   void Emoticon_init() {
      JFrame b = new JFrame("옴팡이");
      b.setBounds(100, 100, 370, 464);
      a.setBackground(Color.WHITE);
      a.setBorder(new EmptyBorder(5, 5, 5, 5));
      a.setLayout(null);
      b.getContentPane().add(a);
      
      JButton Image1 = new JButton("");
      Image1.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\자바사진\\[크기변환]옴팡이10.gif"));
      Image1.setHorizontalAlignment(SwingConstants.CENTER);
      Image1.setBounds(14, 88, 105, 105);
      a.add(Image1);
            
      JButton Image2 = new JButton("");
      Image2.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\자바사진\\1[크기변환]옴팡이.gif"));
      Image2.setBackground(Color.WHITE);
      Image2.setHorizontalAlignment(SwingConstants.CENTER);
      Image2.setBounds(124, 88, 105, 105);
      a.add(Image2);
      
      JButton Image3 = new JButton("");
      Image3.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\자바사진\\[크기변환]옴팡이3.gif"));
      Image3.setHorizontalAlignment(SwingConstants.CENTER);
      Image3.setBounds(234, 88, 105, 105);
      a.add(Image3);
      
      JButton Image4 = new JButton("");
      Image4.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\자바사진\\[크기변환]옴팡이2.gif"));
      Image4.setHorizontalAlignment(SwingConstants.CENTER);
      Image4.setBounds(14, 198, 105, 105);
      a.add(Image4);
      
      JButton Image5 = new JButton("");
      Image5.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\자바사진\\[크기변환]옴팡이5.gif"));
      Image5.setHorizontalAlignment(SwingConstants.CENTER);
      Image5.setBounds(124, 198, 105, 105);
      a.add(Image5);
      
      JButton Image6 = new JButton("");
      Image6.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\자바사진\\[크기변환]옴팡이6.gif"));
      Image6.setHorizontalAlignment(SwingConstants.CENTER);
      Image6.setBounds(234, 198, 105, 105);
      a.add(Image6);
      
      JButton Image7 = new JButton("");
      Image7.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\자바사진\\[크기변환]옴팡이7.gif"));
      Image7.setHorizontalAlignment(SwingConstants.CENTER);
      Image7.setBounds(14, 308, 105, 105);
      a.add(Image7);
      
      JButton Image8 = new JButton("");
      Image8.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\자바사진\\[크기변환]옴팡이8.gif"));
      Image8.setHorizontalAlignment(SwingConstants.CENTER);
      Image8.setBounds(124, 308, 105, 105);
      a.add(Image8);
      
      JButton Image9 = new JButton("");
      Image9.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\자바사진\\[크기변환]옴팡이9.gif"));
      Image9.setHorizontalAlignment(SwingConstants.CENTER);
      Image9.setBounds(234, 308, 105, 105);
      a.add(Image9);
      
      JLabel lblNewLabel = new JLabel("옴팡이");
      lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 35));
      lblNewLabel.setBounds(100, 25, 117, 35);
      a.add(lblNewLabel);
      
      JLabel lblNewLabel_1 = new JLabel("");
      lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\자바사진\\하트211.jpg"));
      lblNewLabel_1.setBounds(210, 3, 100, 70);
      a.add(lblNewLabel_1);
      
      Image1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	client.send_message("Chatting/"+client.My_Room+"/"+"옴팡1");
            }  
         }); 
      Image2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	client.send_message("Chatting/"+client.My_Room+"/"+"옴팡2");
            }  
         });
      Image3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	client.send_message("Chatting/"+client.My_Room+"/"+"옴팡3");
               
            }
         });
      Image4.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	client.send_message("Chatting/"+client.My_Room+"/"+"옴팡4");
            } 
         });
      Image5.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	client.send_message("Chatting/"+client.My_Room+"/"+"옴팡5");
            }
         });
      Image6.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	client.send_message("Chatting/"+client.My_Room+"/"+"옴팡6");
            }
         });
      Image7.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	client.send_message("Chatting/"+client.My_Room+"/"+"옴팡7");
            }
         });
      Image8.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	client.send_message("Chatting/"+client.My_Room+"/"+"옴팡8");
            }
         });
      Image9.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e) {
        	  client.send_message("Chatting/"+client.My_Room+"/"+"옴팡9");
          }
       });
      
      b.setVisible(true);
      b.setLocation
		(client.Chat_GUI.getLocationOnScreen().x - 355, client.Chat_GUI.getLocationOnScreen().y + 115);
      
   }
   
public static void main(String[] args) {
      
      new emoticon();

   }
}