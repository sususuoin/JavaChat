package Client; //채팅서버진짜

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class view_other extends JFrame {

	private JPanel contentPane;

	Image originalImage;
	private ImageIcon originalIcon; // 기본 프로필 사진
	private JFrame frame = new JFrame();
	private JLabel proIMG_lb = new JLabel("    img"); 	//사용자 프로필 변수 시작
	private JLabel proID_lb = new JLabel("ID");
	private JLabel proName_lb = new JLabel("이름");
	private JLabel proSex_lb = new JLabel("성별");
	private JLabel proBirth_lb = new JLabel("생년월일");
	private String id_other = client.id_other;
	

	private JComboBox sex_comboBox;


	String str = null; //SQL실행문 넣어줄 변수
	DB_Join db_m = new DB_Join();
	private JPasswordField pwCK_tf;
	private JTextField phone_tf;
	private JLabel pw_er_lb;
	private JLabel pwCK_er_lb;



	public view_other() {
		viewOther_init(); //회원가입 UI넣을 곳
		start();

	}

	public void start() { //이벤트 리스너 넣어줄 곳
		view(id_other);


	}

	public static void main(String[] args) {
		new view_other();
	}

	public void viewOther_init() {
		setTitle("★☆Frend☆★");
		setBackground(Color.PINK);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 58, 321, 370);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(251, 220, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel view_panel = new JPanel();
		view_panel.setBackground(Color.WHITE);
		view_panel.setBounds(12, 68, 281, 250);
		contentPane.add(view_panel);
		view_panel.setLayout(null);
		proIMG_lb.setBackground(Color.RED);


		proIMG_lb.setBounds(78, 12, 130, 130);
		view_panel.add(proIMG_lb);
		proIMG_lb.setIcon(null);

		proName_lb.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		proName_lb.setBounds(126, 181, 82, 15);
		view_panel.add(proName_lb);


		proSex_lb.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		proSex_lb.setBounds(126, 202, 82, 15);
		view_panel.add(proSex_lb);

		proBirth_lb.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		proBirth_lb.setBounds(123, 223, 103, 15);
		view_panel.add(proBirth_lb);


		JLabel proID = new JLabel("ID  ");
		proID.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		proID.setBounds(97, 160, 19, 15);
		view_panel.add(proID);

		JLabel proName = new JLabel("이름");
		proName.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		proName.setBounds(90, 182, 22, 15);
		view_panel.add(proName);
		proID_lb.setBackground(Color.PINK);
		
				proID_lb.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
				proID_lb.setBounds(128, 159, 80, 15);
				view_panel.add(proID_lb);

		JLabel proSex = new JLabel("성별");
		proSex.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		proSex.setBounds(90, 202, 22, 15);
		view_panel.add(proSex);

		JLabel proBirth = new JLabel("생년월일");
		proBirth.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		proBirth.setBounds(70, 223, 44, 15);
		view_panel.add(proBirth);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(172, 0, 120, 75);
		originalIcon = new ImageIcon("C:\\Users\\user\\Desktop\\자바사진\\에스더버니_4.png");  //이미지 경로
		Image originalImage2=originalIcon.getImage(); // 해당 위치의 파일을 이미지로 얻어온다
		Image resizeImage2= originalImage2.getScaledInstance(116,70,Image.SCALE_SMOOTH); //파일의 크기를 조정한다.
		ImageIcon resizeIcon2=new ImageIcon(resizeImage2); // 객체 생성후      
		lblNewLabel.setIcon(resizeIcon2);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(id_other);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 27));
		lblNewLabel_1.setBounds(48, 20, 120, 35);
		contentPane.add(lblNewLabel_1);


		setVisible(true);
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
            con = DriverManager.getConnection("url", "아이디", "비밀번호");
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

				proID_lb.setText(id_);
				proName_lb.setText(name);
				proSex_lb.setText(sex);
				proBirth_lb.setText(birth);



				if(rs2.next()) {   
					String pro = rs2.getString("image_path");
					proIMG_lb.setIcon(ResizeImage(pro,proIMG_lb));
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
}


