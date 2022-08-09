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


public class join extends JFrame implements ActionListener, KeyListener {
	
	public String image_path = "C:\\Users\\user\\Desktop\\자바사진\\에스더버니2.png";
	private JPanel contentPane;
	private JTextField id_tf;
	private JPasswordField pw_tf;
	private JTextField name_tf;
	private JTextField birth_year_tf;
	private JTextField email_tf;
	private JTextField birth_month_tf;
	private JTextField birth_day_tf;
	Image originalImage;
	private ImageIcon originalIcon; // 기본 프로필 사진
	private JFrame frame = new JFrame();
	private JLabel img_lb = new JLabel("");
	private JLabel ES_img_lb1 = new JLabel("");
	private JLabel es_mainimg_lb = new JLabel("");
	   
	static JTextField postnum_tf;
	static JTextField address_tf2;
	static JTextField address_tf1;
	
	private JButton id_btn = new JButton("중복확인");
	private JButton join_btn = new JButton("가  입");
	private JButton address_btn = new JButton("주소 찾기");
	private JButton img_btn = new JButton("사진 변경");
	private JButton	cancle_btn = new JButton("취   소");

	private JComboBox sex_comboBox;
	
	
	String str = null; //SQL실행문 넣어줄 변수
	DB_Join db_m = new DB_Join();
	private JPasswordField pwCK_tf;
	private JTextField phone_tf1;
	private JLabel pw_er_lb;
	private JLabel pwCK_er_lb;

	
	
	public join() {
		Join_init(); //회원가입 UI넣을 곳
		start();
	}
	
	public void start() { //이벤트 리스너 넣어줄 곳
		cancle_btn.addActionListener(this);
		join_btn.addActionListener(this);
		address_btn.addActionListener(this);
		id_btn.addActionListener(this);
		pw_tf.addKeyListener(this);
		pwCK_tf.addKeyListener(this);
		address_tf2.addKeyListener(this);
		img_btn.addActionListener(this);
		
	}
	
	public static void main(String[] args) {
		new join();
	}
	
	public void Join_init() {
		setBackground(Color.PINK);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 537, 591);
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
		
		pw_tf = new JPasswordField();
		pw_tf.setColumns(10);
		pw_tf.setBounds(91, 106, 153, 28);
		contentPane.add(pw_tf);
		
		name_tf = new JTextField();
		name_tf.setColumns(10);
		name_tf.setBounds(91, 192, 153, 28);
		contentPane.add(name_tf);
		
		birth_year_tf = new JTextField();
		birth_year_tf.setColumns(10);
		birth_year_tf.setBounds(92, 271, 61, 28);
		contentPane.add(birth_year_tf);
		
		email_tf = new JTextField();
		email_tf.setColumns(10);
		email_tf.setBounds(91, 311, 153, 28);
		contentPane.add(email_tf);
		
		address_tf2 = new JTextField();
		address_tf2.setColumns(10);
		address_tf2.setBounds(91, 455, 224, 28);
		contentPane.add(address_tf2);
		
		
		join_btn.setBackground(SystemColor.menu);
		join_btn.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		join_btn.setBounds(30, 495, 228, 35);
		contentPane.add(join_btn);
		
		JLabel talk_lb = new JLabel("Talk");
		talk_lb.setForeground(Color.BLACK);
		talk_lb.setHorizontalAlignment(SwingConstants.CENTER);
		talk_lb.setFont(new Font("Adobe Fan Heiti Std B", Font.BOLD | Font.ITALIC, 27));
		talk_lb.setBounds(250, 13, 89, 35);
		contentPane.add(talk_lb);
		
		JLabel birth_year_lb = new JLabel("년");
		birth_year_lb.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		birth_year_lb.setBounds(152, 273, 22, 28);
		contentPane.add(birth_year_lb);

		birth_month_tf = new JTextField();
		birth_month_tf.setColumns(10);
		birth_month_tf.setBounds(170, 271, 47, 28);
		contentPane.add(birth_month_tf);
		
		JLabel birth_month_lb = new JLabel("월");
		birth_month_lb.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		birth_month_lb.setBounds(218, 277, 22, 22);
		contentPane.add(birth_month_lb);

		birth_day_tf = new JTextField();
		birth_day_tf.setColumns(10);
		birth_day_tf.setBounds(241, 271, 56, 28);
		contentPane.add(birth_day_tf);
		
		JLabel birth_day_lb = new JLabel("일");
		birth_day_lb.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		birth_day_lb.setBounds(298, 274, 31, 28);
		contentPane.add(birth_day_lb);
		
		
		id_btn.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		id_btn.setBackground(SystemColor.menu);
		id_btn.setBounds(253, 66, 87, 28);
		contentPane.add(id_btn);
		
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
		
		phone_tf1 = new JTextField();
		phone_tf1.setColumns(10);
		phone_tf1.setBounds(91, 351, 153, 28);
		contentPane.add(phone_tf1);
		
		
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
		contentPane.add(img_lb);
		
		
		img_btn.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		img_btn.setBackground(SystemColor.menu);
		img_btn.setBounds(377, 320, 91, 28);
		contentPane.add(img_btn);
		
		originalIcon = new ImageIcon("C:\\Users\\user\\Desktop\\자바사진\\에스더버니2.png");  //이미지 경로
	    Image originalImage=originalIcon.getImage(); // 해당 위치의 파일을 이미지로 얻어온다
	    Image resizeImage= originalImage.getScaledInstance(170,170,Image.SCALE_SMOOTH); //파일의 크기를 조정한다.
	    ImageIcon resizeIcon=new ImageIcon(resizeImage); // 객체 생성후      
	    img_lb.setIcon(resizeIcon);
	    
		cancle_btn.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		cancle_btn.setBackground(SystemColor.menu);
		cancle_btn.setBounds(265, 495, 228, 35);
		contentPane.add(cancle_btn);
		
		ImageIcon image1 = new ImageIcon("C:\\Users\\user\\Desktop\\자바사진\\estherBUNNY.png");  //이미지 경로
		ES_img_lb1 = new JLabel(image1,SwingConstants.CENTER);
		ES_img_lb1.setBounds(175, 3, 95, 50);
		contentPane.add(ES_img_lb1);
		
		ImageIcon image2 = new ImageIcon("C:\\Users\\user\\Desktop\\자바사진\\bunny1.png");  //이미지 경로
		es_mainimg_lb = new JLabel(image2,JLabel.CENTER);
		es_mainimg_lb.setBounds(14, 408, 50, 70);
		contentPane.add(es_mainimg_lb);
		
		
		setVisible(true);
		
	}
	
	
	
	
	public void actionPerformed(ActionEvent e) {
		FileInputStream fis=null;
        Connection conn = null; // 데이터 연결하기 위함
        PreparedStatement pstmt  = null; // 데이터를 읽어오기 위함
        	
		if(e.getSource() == join_btn) { //회원가입 버튼 클릭 시
			
			 if(id_tf.getText().length() == 0){
				 JOptionPane.showMessageDialog(null, "ID를 입력해 주세요.");
		            id_tf.requestFocus();
		            return;
			 }
			 else if(pw_tf.getText().length() == 0){
				 JOptionPane.showMessageDialog(null, "비밀번호를 입력해 주세요.");
		            pw_tf.requestFocus();
		            return;
			 }
			 else if(name_tf.getText().length()==0 || email_tf.getText().length() == 0 
					 || phone_tf1.getText().length() == 0 
					 ||birth_year_tf.getText().length() == 0 ||postnum_tf.getText().length() == 0){
				 JOptionPane.showMessageDialog(null, "회원 정보를 입력해주세요.");
		            return;
			 }
			else {
				
			String ID = id_tf.getText();
			String PW = pw_tf.getText();
			String NAME = name_tf.getText();
			String SEX = (String) sex_comboBox.getSelectedItem();
			String EMAIL = email_tf.getText();
			String PHONE = phone_tf1.getText();
			String BIRTHDAY = birth_year_tf.getText()+"/"+birth_month_tf.getText()+"/"+birth_day_tf.getText()+"/";
			String ADDRESS_1 = postnum_tf.getText();
			String ADDRESS_2 = address_tf1.getText();
			String ADDRESS_3 = address_tf2.getText();
			//String image_path;
			
			String sql ="Insert into CH_USER (ID, PW, NAME, SEX, EMAIL, PHONE, BIRTHDAY,"
					+ " ADDRESS_1, ADDRESS_2, ADDRESS_3,image_path,image) values(?,?,?,?,?,?,?,?,?,?,?,?)";
            
            
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
             
             
             pstmt.executeUpdate();
            
            System.out.println("이미지 저장 성공");
            
            pstmt.close();
            conn.close();
            System.out.println(ID+"/"+PW+"/"+NAME+"/"+SEX+"/"+BIRTHDAY+"/"+PHONE+"/"+EMAIL+"저장 성공!");
    
         
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
		 else if(e.getSource() == id_btn)
	      {
	        System.out.println("중복 확인");
	        String id =id_tf.getText().trim();
	        System.out.println(id);
	        java.sql.Connection con= null;
	          java.sql.Statement stmt =null;
	          try{
	             Class.forName("oracle.jdbc.driver.OracleDriver");
	             con= DriverManager.getConnection("URL","아이디","비밀번호");
	             stmt =con.createStatement();
	             ResultSet rs=stmt.executeQuery("select *from CH_USER");
	             while(rs.next())
	             {
	                if(id.equals(rs.getString("id")))
	                {
	                JOptionPane.showMessageDialog(null,"이미 사용중인 아이디입니다.","중복 확인",JOptionPane.ERROR_MESSAGE);
	                id_tf.requestFocus();
	                return;
	             }
	             }
	             JOptionPane.showMessageDialog(null,"사용 가능한 아이디입니다.","중복 확인", JOptionPane.INFORMATION_MESSAGE);
	             id_tf.requestFocus();
	             //ID_Check=true;
	             return;
	          }
	             catch(ClassNotFoundException cnfe){
	                 System.out.println(" "+cnfe.getMessage());
	              }
	              catch(SQLException se)
	              {
	              }
	           
	      }
		
		 else if(e.getSource() == img_btn){
			 System.out.print("프로필 변경");
			 
			 FileDialog file=new FileDialog(frame,"사진찾기",FileDialog.LOAD); // 파일을 선택할 수있는 창을 생성한다
	         file.setSize(300,200); // 파일 선택창의 크기이다
	         file.show(); // 창을 연다
	            
	         originalIcon=new ImageIcon(file.getDirectory()+file.getFile()); // 파일의 위치를 얻어온다
	               
	         originalImage=originalIcon.getImage(); // 해당 위치의 파일을 이미지로 얻어온다
	         Image resizeImage= originalImage.getScaledInstance(170,170,Image.SCALE_SMOOTH); //파일의 크기를 조정한다.
	         ImageIcon resizeIcon=new ImageIcon(resizeImage); // 객체 생성후      
	         img_lb.setIcon(resizeIcon);//레이블에 icon으로 나타낸다.
	         image_path=file.getDirectory()+file.getFile();
	         System.out.print("이미지 경로:" + image_path); 
			 
		 }
		 else if(e.getSource() == cancle_btn){
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
		    	   join_btn.doClick();
		       }
		}    
		//TODO Auto-generated method stub	
	} //주소 필드에서 엔터키 누르면 가입 버튼 더블클릭

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
}
	

