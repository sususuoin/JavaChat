package Server;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Client.DB;


public class Super_login extends JFrame implements ActionListener, KeyListener{
   private JFrame Login_GUI = new JFrame();
   
   private JTextField superid_tf; //id 받는 텍스트필드
   private JPasswordField superpw_pf; //password 받는 텍스트 필드
   private JButton superlogin_btn = new JButton("로그인"); //접속버튼
//   ImageIcon icon;
   private JPanel Login_Pane;
   
   String strSQL = null;
   DB mDB = new DB();
   boolean idcheck = false; //id 체크에 쓰임
   boolean pwcheck = false; //password 체크에 쓰임
   boolean check = false; //아이디,패스워드 체크에 쓰임
   private String id = "";
   private JLabel lblNewLabel;
   
   public Super_login()
   {
      setTitle("관리자 모드");
      Login_init();
      start();
   }
   
   public void start() {
      superlogin_btn.setForeground(Color.BLACK);
      superlogin_btn.setFont(new Font("휴먼모음T", Font.PLAIN, 18));
      superlogin_btn.addActionListener(this);
      superpw_pf.addKeyListener(this);
   
   }
   
   public void Login_init() {
      Login_Pane = new JPanel() {
      
      public void paintComponent(Graphics g) {
         setOpaque(false);
         super.paintComponent(g);
      }
      
   };
   
   Login_GUI.setBounds(100, 100, 421, 369);
   Login_Pane.setBackground(new Color(251, 220, 235));
   Login_Pane.setBorder(new EmptyBorder(5, 5, 5, 5));
   Login_GUI.setContentPane(Login_Pane);
   Login_Pane.setLayout(null);
   
   
   superid_tf = new JTextField();
   superid_tf.setBounds(80, 115, 243, 46);
   Login_Pane.add(superid_tf);
   superid_tf.setColumns(10);
   
   superpw_pf = new JPasswordField();
   superpw_pf.setBounds(80, 162, 243, 46);
   Login_Pane.add(superpw_pf);
   superpw_pf.setColumns(10);
   
   
   superlogin_btn.setBackground(Color.pink);
   superlogin_btn.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
   superlogin_btn.setBounds(80, 220, 243, 54);
   Login_Pane.add(superlogin_btn);
   
   lblNewLabel = new JLabel("\uAD00\uB9AC\uC790 \uBAA8\uB4DC");
   lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
   lblNewLabel.setBounds(155, 49, 165, 46);
   Login_Pane.add(lblNewLabel);
   
   Login_GUI.setLocationRelativeTo(null);
   Login_GUI.setVisible(true); //true = 화면에 보인다 false = 화면에 보이지 않는다
   }
   
   
   
   public static void main(String[] args) {
      
      new Super_login(); //익명으로 객체 발생

   }

   @Override
   public void keyPressed(KeyEvent e) {
      if(e.getSource() == superpw_pf) { //패스워드 필드에서 엔터키 누르면 로그인 버튼 더블클릭
         if(e.getKeyCode() == 10) {
            superlogin_btn.doClick();
         }
      }
      
   }

   @Override
   public void keyReleased(KeyEvent arg0) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void keyTyped(KeyEvent arg0) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      String password = "";
      char[] secret_pw = superpw_pf.getPassword();
      
      for(char cha : secret_pw) {
         Character.toString(cha);
         
         password += (password.equals("")) ? ""+cha+"" : ""+cha+"";

      }
      // TODO Auto-generated method stub
      if(e.getSource() == superlogin_btn) {
         System.out.println("로그인 버튼 클릭");
         
         if(superid_tf.getText().length() == 0) //ID 텍스트 필트가 비어있으면
         {
            JOptionPane.showMessageDialog(null, "ID를 입력해 주세요.");
            superid_tf.requestFocus();
            return;
         }
         
         if(password.equals(""))    { //비밀번호 필드가 공백이라면
              JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요", "비밀번호 입력",JOptionPane.WARNING_MESSAGE);
              superpw_pf.grabFocus();
              return; 
         }
         if(!idcheck){
               
               //DB에 있는 아이디인지 체크
               id = superid_tf.getText();
               
               try{
                  mDB.dbOpen();
                   strSQL = "select id from super where id = '";
                   strSQL += id + "'";
                   mDB.DB_rs = mDB.DB_stmt.executeQuery(strSQL);
                   
               if(mDB.DB_rs.next()){               
                   check = false;   
               }else{check = true;}
               mDB.dbClose();
               //JOptionPane.showMessageDialog(null, "DB닫음");
               }catch(Exception e1){
               JOptionPane.showMessageDialog(null, "SQLException : " + e1.getMessage());
               System.out.println("SQLException : " + e1.getMessage());} //catch문 끝
               
               if(check){//db에 아이디가 없으면
                  idcheck = true;
                  JOptionPane.showMessageDialog(null, "ID를 확인해주세요");
                  superid_tf.requestFocus();
                  idcheck = false; 
                  }
               else{//db에 아이디가 있으면
                     idcheck = false;
                     if(!pwcheck){
                          
                          //DB에 있는 패스워드인지 체크
                          
                          try{
                             mDB.dbOpen();
                              //JOptionPane.showMessageDialog(null, "DB연결");
                              strSQL = "select pw from super where pw = '";
                              strSQL += password + "'";
                              mDB.DB_rs = mDB.DB_stmt.executeQuery(strSQL);
                              
                          if(mDB.DB_rs.next()){               
                              check = false;   
                          }else{check = true;}
                          mDB.dbClose();
                          //JOptionPane.showMessageDialog(null, "DB닫음");
                          }catch(Exception e1){
                          JOptionPane.showMessageDialog(null, "SQLException : " + e1.getMessage());
                          System.out.println("SQLException : " + e1.getMessage());} //catch문 끝
                          
                          if(check){//db에 패스워드가 없으면
                             pwcheck = true;
                             JOptionPane.showMessageDialog(null, "비밀번호를 확인해주세요");
                             superpw_pf.requestFocus();
                             pwcheck = false; 
                             }
                          else{//db에 패스워드가 있으면
                                pwcheck = false;
                                new Supervisor();
                               this.Login_GUI.setVisible(false);
                             }
                     } //password DB와 비교코딩 끝
                     
               } //id가 있을 때 if문 끝
         
         
           } //id,password DB와 비교코딩 끝
         
         
         
      
      }
      
   }

}