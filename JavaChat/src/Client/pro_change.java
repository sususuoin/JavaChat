package Client;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class pro_change extends JFrame implements ActionListener, KeyListener {
	
	public String image_path = "C:\\Users\\user\\Desktop\\자바사진\\에스더버니2.png";
	private JPanel contentPane;
	private JTextField id_tf;
	private JPasswordField pw_tf;
	private JTextField name_tf;
	private JTextField birth_tf;
	private JTextField email_tf;
	private String id=client.id;
	Image originalImage;
	private ImageIcon originalIcon; // 기본 프로필 사진
	private JFrame frame = new JFrame();
	private JLabel img_lb = new JLabel("");
	private JLabel ES_img_lb1 = new JLabel("");
	private JLabel es_mainimg_lb = new JLabel("");
	   
	static JTextField postnum_tf;
	static JTextField address_tf2;
	static JTextField address_tf1;
	private JButton change_btn = new JButton("저  장");
	private JButton address_btn = new JButton("주소 찾기");
	private JButton img_btn = new JButton("사진 변경");
	private JButton	cancel_btn = new JButton("취   소");

	private JComboBox sex_comboBox;
	
	
	String str = null; //SQL실행문 넣어줄 변수
	DB_Join db_m = new DB_Join();
	private JPasswordField pwCK_tf;
	private JTextField phone_tf;
	private JLabel pw_er_lb;
	private JLabel pwCK_er_lb;

	
	
	public pro_change() {
		pro_init(); //회원가입 UI넣을 곳
		start();
		
	}
	
	public void start() { //이벤트 리스너 넣어줄 곳
		cancel_btn.addActionListener(this);
		change_btn.addActionListener(this);
		address_btn.addActionListener(this);
		pw_tf.addKeyListener(this);
		pwCK_tf.addKeyListener(this);
		address_tf2.addKeyListener(this);
		img_btn.addActionListener(this);
		
	}
	
	public static void main(String[] args) {
		new pro_change();
	}
	
	public void pro_init() {
		setTitle("프로필 변경");
		setBackground(Color.PINK);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 58, 537, 591);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(251, 220, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel id_lb = new JLabel("ID");
		id_lb.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		id_lb.setBounds(70, 66, 137, 28);
		contentPane.add(id_lb);
		
		JLabel pw_lb = new JLabel("비밀번호");
		pw_lb.setForeground(Color.BLACK);
		pw_lb.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		pw_lb.setBounds(40, 106, 137, 35);
		contentPane.add(pw_lb);
		
		JLabel name_lb = new JLabel("이름");
		name_lb.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		name_lb.setBounds(62, 184, 137, 44);
		contentPane.add(name_lb);
		
		JLabel sex_lb = new JLabel("성별");
		sex_lb.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		sex_lb.setBounds(62, 237, 137, 22);
		contentPane.add(sex_lb);
		
		JLabel birthday_lb = new JLabel("생년월일");
		birthday_lb.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		birthday_lb.setBounds(41, 271, 74, 28);
		contentPane.add(birthday_lb);
		
		JLabel phone_lb = new JLabel("휴대폰");
		phone_lb.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		phone_lb.setBounds(50, 348, 68, 35);
		contentPane.add(phone_lb);
		
		JLabel address_lb = new JLabel("주소");
		address_lb.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		address_lb.setBounds(62, 385, 68, 45);
		contentPane.add(address_lb);
		
		id_tf = new JTextField();
		id_tf.setBounds(91, 66, 153, 28);
		contentPane.add(id_tf);
		id_tf.setColumns(10);
		id_tf.setEditable(false); //화면을 수정할 수 없도록 만들어줌

		
		pw_tf = new JPasswordField();
		pw_tf.setColumns(10);
		pw_tf.setBounds(91, 106, 153, 28);
		contentPane.add(pw_tf);
		
		name_tf = new JTextField();
		name_tf.setColumns(10);
		name_tf.setBounds(91, 192, 153, 28);
		contentPane.add(name_tf);
		
		birth_tf = new JTextField();
		birth_tf.setColumns(10);
		birth_tf.setBounds(92, 271, 152, 28);
		contentPane.add(birth_tf);
		
		email_tf = new JTextField();
		email_tf.setColumns(10);
		email_tf.setBounds(91, 311, 153, 28);
		contentPane.add(email_tf);
		
		address_tf2 = new JTextField();
		address_tf2.setColumns(10);
		address_tf2.setBounds(91, 455, 224, 28);
		contentPane.add(address_tf2);
		
		
		change_btn.setBackground(SystemColor.menu);
		change_btn.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		change_btn.setBounds(30, 495, 228, 35);
		contentPane.add(change_btn);
		
		JLabel talk_lb = new JLabel("Talk");
		talk_lb.setForeground(Color.BLACK);
		talk_lb.setHorizontalAlignment(SwingConstants.CENTER);
		talk_lb.setFont(new Font("Adobe Fan Heiti Std B", Font.BOLD | Font.ITALIC, 27));
		talk_lb.setBounds(250, 13, 89, 35);
		contentPane.add(talk_lb);
	
		ArrayList<String> sex = new ArrayList<String>();
		sex.add("성별");
		sex.add("여성");
		sex.add("남성");
		
		sex_comboBox = new JComboBox(sex.toArray());
		sex_comboBox.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		sex_comboBox.setBounds(91, 235, 153, 22);
		contentPane.add(sex_comboBox);
		
		address_tf1 = new JTextField();
		address_tf1.setColumns(10);
		address_tf1.setBounds(91, 423, 224, 28);
		contentPane.add(address_tf1);
		
		postnum_tf = new JTextField();
		postnum_tf.setColumns(10);
		postnum_tf.setBounds(91, 391, 107, 28);
		contentPane.add(postnum_tf);
	
		address_btn.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		address_btn.setBackground(SystemColor.menu);
		address_btn.setBounds(206, 392, 91, 28);
		contentPane.add(address_btn);
		
		JLabel pwCK_lb = new JLabel("비밀번호 확인");
		pwCK_lb.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		pwCK_lb.setBounds(14, 148, 97, 35);
		contentPane.add(pwCK_lb);
		
		pwCK_tf = new JPasswordField();
		pwCK_tf.setColumns(10);
		pwCK_tf.setBounds(91, 149, 153, 28);
		contentPane.add(pwCK_tf);
		
		JLabel email_lb = new JLabel("이메일");
		email_lb.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		email_lb.setBounds(50, 311, 64, 28);
		contentPane.add(email_lb);
		
		phone_tf = new JTextField();
		phone_tf.setColumns(10);
		phone_tf.setBounds(91, 351, 153, 28);
		contentPane.add(phone_tf);
		
		
		pw_er_lb = new JLabel("");
		pw_er_lb.setFont(new Font("맑은 고딕", Font.BOLD, 10));
		pw_er_lb.setBounds(91, 132, 224, 16);
		contentPane.add(pw_er_lb);
		
		
		pwCK_er_lb = new JLabel("");
		pwCK_er_lb.setForeground(Color.BLACK);
		pwCK_er_lb.setFont(new Font("맑은 고딕", Font.BOLD, 10));
		pwCK_er_lb.setBounds(91, 175, 189, 16);
		contentPane.add(pwCK_er_lb);
		
		
		img_lb.setForeground(Color.BLACK);
		img_lb.setBackground(Color.LIGHT_GRAY);
		img_lb.setBounds(333, 130, 170, 170);
		img_lb.setIcon(originalIcon);
		contentPane.add(img_lb);
		
		
		img_btn.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		img_btn.setBackground(SystemColor.menu);
		img_btn.setBounds(377, 320, 91, 28);
		contentPane.add(img_btn);
		
		cancel_btn.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		cancel_btn.setBackground(SystemColor.menu);
		cancel_btn.setBounds(265, 495, 228, 35);
		contentPane.add(cancel_btn);


		ImageIcon image1 = new ImageIcon("C:\\Users\\user\\Desktop\\자바사진\\estherBUNNY.png");  //이미지 경로
		ES_img_lb1 = new JLabel(image1,SwingConstants.CENTER);
		ES_img_lb1.setBounds(175, 3, 95, 50);
		contentPane.add(ES_img_lb1);
		
		ImageIcon image2 = new ImageIcon("C:\\Users\\user\\Desktop\\자바사진\\bunny1.png");  //이미지 경로
		es_mainimg_lb = new JLabel(image2,JLabel.CENTER);
		es_mainimg_lb.setBounds(14, 408, 50, 70);
		contentPane.add(es_mainimg_lb);
		
		
		setVisible(true);
		view(id);
		
	}
	
	
	private void view(String id) { //프로필 뜨기
        // TODO Auto-generated method stub
           Connection con = null;  
           Statement stmt = null;
           Statement stmt2 = null;
           ResultSet rs = null;
           ResultSet rs2 = null;
           
           try {
              Class.forName("oracle.jdbc.driver.OracleDriver");
               con=DriverManager.getConnection("URL", "아이디", "비밀번호");
               stmt = con.createStatement();       //sql 전달
               stmt2 = con.createStatement();
               
	            rs = stmt.executeQuery(         // 해당 칼럼 선택.
	  	              "select ID,PW,NAME, SEX, BIRTHDAY, EMAIL, PHONE, ADDRESS_1, ADDRESS_2, ADDRESS_3"
	  	              + " from CH_USER"
	  	              + " where id='" +  id  + "'");   // 조건은 로그인 한 id와 같은 id를 가진 데이터
	  	            
	  	            rs2 = stmt2.executeQuery("select * from CH_USER where id = '" + id + "'"); 
	  	            
	  	            if(rs.next()) {
	  	            	String id_ = rs.getString("ID");
	  	            	String pw = rs.getString("PW");
	  	    			String pw_ck = rs.getString("PW");
	  	                String name = rs.getString("NAME");
	  	                 String sex=rs.getString("SEX");
	  	                 String birth=rs.getString("BIRTHDAY");
	  	                 String email=rs.getString("EMAIL");
	  	                 String phone=rs.getString("PHONE");
	  	             	String ADDRESS_1 = rs.getString("ADDRESS_1");
	  	    			String ADDRESS_2 = rs.getString("ADDRESS_2");
	  	    			String ADDRESS_3 = rs.getString("ADDRESS_3");
	  	       
	  	                 id_tf.setText(id_);
	  		    		 pw_tf.setText(pw);
	  		    		 pwCK_tf.setText(pw);
	  	                 name_tf.setText(name);
	  	                 sex_comboBox.setSelectedItem(sex);
	  	                 birth_tf.setText(birth);
	  	                 email_tf.setText(email);
	  	                 phone_tf.setText(phone);
	  	                 postnum_tf.setText(ADDRESS_1);
	  	     			 address_tf1.setText(ADDRESS_2);
	  	     			 address_tf2.setText(ADDRESS_3);
                 
               if(rs2.next()) {   
                  String pro = rs2.getString("image_path");
                  img_lb.setIcon(ResizeImage(pro,img_lb));
                  originalIcon=new ImageIcon(pro);
                     }
               }
            else {
               throw new Exception("해당 ID가 없습니다.");  //
            }     
           } catch (Exception e) {
            e.printStackTrace();
            return;
           } finally {
              try {rs.close();} catch (Exception e) {}
              try {stmt.close();} catch (Exception e) {}
              try {con.close();} catch (Exception e) {}
             }  
     }
	public static ImageIcon ResizeImage(String ImagePath, JLabel a)//지정된 라벨에 크기에 맞게 사진을 넣을때
	{
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(a.getWidth(), a.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}
	
	public void actionPerformed(ActionEvent e) { // 액션리스너
		FileInputStream fis=null;
        Connection conn = null; // 데이터 연결하기 위함
        PreparedStatement pstmt  = null; // 데이터를 읽어오기 위함
        	
		if(e.getSource() == change_btn) { //저장 버튼 클릭 시
			
			if(pw_tf.getText().length() == 0){
				 JOptionPane.showMessageDialog(null, "비밀번호를 입력해 주세요.");
		            pw_tf.requestFocus();
		            return;
			 }
			 else if(name_tf.getText().length()==0 || email_tf.getText().length() == 0 
					 || phone_tf.getText().length() == 0 
					 ||birth_tf.getText().length() == 0 ||postnum_tf.getText().length() == 0){
				 JOptionPane.showMessageDialog(null, "회원 정보를 입력해주세요.");
		            return;
			 }
			else {
				
			String ID = id_tf.getText();
			String PW = pw_tf.getText();
			String NAME = name_tf.getText();
			String SEX = (String) sex_comboBox.getSelectedItem();
			String EMAIL = email_tf.getText();
			String PHONE = phone_tf.getText();
			String BIRTHDAY = birth_tf.getText();
			String ADDRESS_1 = postnum_tf.getText();
			String ADDRESS_2 = address_tf1.getText();
			String ADDRESS_3 = address_tf2.getText();
			//String image_path;
			
			String sql ="update CH_USER SET ID=?,PW=?, NAME=?, SEX=?, EMAIL=?, PHONE=?, BIRTHDAY=?, ADDRESS_1=?, "
					+ "ADDRESS_2=?, ADDRESS_3=?,image_path=?,image=?  where id=?";            
            
            try{

             Class.forName("oracle.jdbc.driver.OracleDriver"); //jdbc 드라이버를 로드한다.
             conn = (Connection) DriverManager.getConnection( //정적 메소드인 getConnection 메소드 호출
             "URL", "아이디", "비밀번호"); // 데이터베이스에 필요한  URL,ID,PW
             File imgfile = new File(originalIcon.toString());
             fis = new FileInputStream(imgfile);
             pstmt = conn.prepareStatement(sql);
             
             pstmt.setString(1, ID);
             pstmt.setString(2, PW);
             pstmt.setString(3, NAME);
             pstmt.setString(4, SEX);
             pstmt.setString(5, EMAIL);
             pstmt.setString(6, PHONE);
             pstmt.setString(7, BIRTHDAY);
             pstmt.setString(8, ADDRESS_1);
             pstmt.setString(9, ADDRESS_2);
             pstmt.setString(10, ADDRESS_3);
             pstmt.setString(11, image_path);
             pstmt.setBinaryStream(12,fis,(int)imgfile.length());
             pstmt.setString(13, ID);
             
             
             pstmt.executeUpdate();
            
            System.out.println("이미지 저장 성공");
            
            pstmt.close();
            conn.close();
    
         
         }
          catch(ClassNotFoundException cnfe){
             cnfe.printStackTrace();
                              }
         catch(SQLException se){
            se.printStackTrace();
         }
         catch(FileNotFoundException fnfe){
            fnfe.printStackTrace();
         }
         finally{
            try{
               fis.close();
            }
            catch(Exception e2){
               e2.printStackTrace();
               
            }
         }
			}
            setVisible(false);
			}
             // 회원가입 버튼if문 끝
			
		else if(e.getSource() == address_btn) {
			System.out.println("주소 찾기");
			ZipSearch zipsearch = new ZipSearch();
			zipsearch.setVisible(true);
			
		}
		 	           
		
		 else if(e.getSource() == img_btn){
			 System.out.println("프로필 변경");
			 
			 FileDialog file=new FileDialog(frame,"사진찾기",FileDialog.LOAD); // 파일을 선택할 수있는 창을 생성한다
	         file.setSize(300,200); // 파일 선택창의 크기이다
	         file.show(); // 창을 연다
	            
	         originalIcon=new ImageIcon(file.getDirectory()+file.getFile()); // 파일의 위치를 얻어온다
	               
	         originalImage=originalIcon.getImage(); // 해당 위치의 파일을 이미지로 얻어온다
	         Image resizeImage= originalImage.getScaledInstance(170,170,Image.SCALE_SMOOTH); //파일의 크기를 조정한다.
	         ImageIcon resizeIcon=new ImageIcon(resizeImage); // 객체 생성후      
	         img_lb.setIcon(resizeIcon);//레이블에 icon으로 나타낸다.
	         image_path=file.getDirectory()+file.getFile();
	         System.out.println("이미지 경로:" + image_path); 
			 
		 }
		 else if(e.getSource() == cancel_btn){
			 System.out.println("가입 취소 버튼 클릭");
			 setVisible(false);
		 }

			
	      }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getSource() == address_tf2) {
		       if(e.getKeyCode() == 10) {
		    	   System.out.println("확인");
		    	   change_btn.doClick();
		       }
		}    
		//TODO Auto-generated method stub	
	} //주소 필드에서 엔터키 누르면 가입 버튼 더블클릭

	@Override
	public void keyReleased(KeyEvent e) {
		String pwPattern = "([a-z0-9_-].*[!,@,#,^,&,*,(,)])|([!,@,#,^,&,*,(,)].*[a-z0-9_-])";
	      String pw = pw_tf.getText().trim();
	      Boolean tt = Pattern.matches(pwPattern, pw);
	      
	      if(e.getSource() == pw_tf) {
	         if (pw.toString().length() < 8 || pw.length() > 16 || tt!= true) {
	        	 pw_er_lb.setText("8~16자 영어, 숫자, 특수문자를 사용해주세요");
	         }
	         else {
	        	 pw_er_lb.setText("");
	         }
	      } // 비밀번호 조건 if 끝
	      
	      else if(e.getSource() == pwCK_tf) {
	         if(!pw.equals(pwCK_tf.getText().trim())){
	        	 pwCK_er_lb.setText("비밀번호가 일치하지 않습니다.");
	         }
	         else {
	        	 pwCK_er_lb.setText("");
	         }
	      } //비밀번호 확인 else if 끝
		
	}	
}
	

