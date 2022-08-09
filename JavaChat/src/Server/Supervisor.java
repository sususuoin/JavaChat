package Server;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



public class Supervisor extends JFrame implements ActionListener{
   
//   ImageIcon icon;
   private JPanel Super_Pane;
   private JButton member_btn = new JButton("회원 관리");
   private JButton chat_btn = new JButton("채팅 관리");
   private JButton login_btn = new JButton("로그인 관리");
   

   public Supervisor(){
      setTitle("관리자 모드");
   init();
   start();
   }
   
   public void start() {
      member_btn.addActionListener(this);
      chat_btn.addActionListener(this);
      login_btn.addActionListener(this);
   
   }
   public void init() {
        Super_Pane = new JPanel();       
      
      setBounds(100, 100, 324, 297);
      Super_Pane.setBackground(new Color(251, 220, 235));
      Super_Pane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(Super_Pane);
      Super_Pane.setLayout(null);
      
      
      member_btn.setBackground(SystemColor.menu);
      member_btn.setFont(new Font("맑은 고딕", Font.BOLD, 18));
      member_btn.setBounds(31, 40, 251, 52);
      Super_Pane.add(member_btn);
      
      
      chat_btn.setBackground(SystemColor.menu);
      chat_btn.setFont(new Font("맑은 고딕", Font.BOLD, 18));
      chat_btn.setBounds(31, 103, 251, 52);
      Super_Pane.add(chat_btn);
      
      
      login_btn.setBackground(SystemColor.menu);
      login_btn.setFont(new Font("맑은 고딕", Font.BOLD, 18));
      login_btn.setBounds(31, 165, 251, 52);
      Super_Pane.add(login_btn);
      
      this.setLocationRelativeTo(null);
      this.setVisible(true);
   }

   public static void main(String[] args) {
      // TODO Auto-generated method stub
      new Supervisor();
   }
   
   @Override
   public void actionPerformed(ActionEvent e) {
      if(e.getSource() == member_btn) {
         new member_DB();
      }
      if(e.getSource() == chat_btn) {
         new chatting_DB();

      }
      if(e.getSource()==login_btn){
    	  new login_DB();
      }
   }

   
   
}

