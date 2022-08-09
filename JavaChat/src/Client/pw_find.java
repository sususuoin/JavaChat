package Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class pw_find extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JFrame frame = new JFrame();
	private JTextField id_tf;
	private JTextField name_tf;
	private JTextField phone_tf; // 010 - 0000 - 0000 처음
	private JTextField phone_tf2; // 010 - 0000 - 0000 중간
	private JTextField phone_tf3; // 010 - 0000 - 0000  끝
	private JLabel find_lb1 = new JLabel("비밀번호는");
	private JLabel find_lb3 = new JLabel("입니다.");
	private JLabel find_lb2 = new JLabel("PW"); // 찾는 비밀번호가 뜰 라벨
	private JButton ok_btn = new JButton("확인");
	private JButton cancel_btn = new JButton("취소");

	String PW ="";

	public pw_find() { // 생성자
		IDfind_init();
		start();
	}

	public void start(){ // 이벤트리스너 넣어줄 곳
		ok_btn.addActionListener(this);
		cancel_btn.addActionListener(this);
	}

	public void IDfind_init(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("PW 찾기");
		setBounds(100, 100, 371, 345);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(251, 220, 235));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("비밀번호 찾기");
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblNewLabel.setBounds(115, 12, 137, 35);
		contentPane.add(lblNewLabel);

		JLabel id_lb = new JLabel("ID");
		id_lb.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		id_lb.setBounds(68, 73, 42, 35);
		contentPane.add(id_lb);

		JLabel lblNewLabel_3 = new JLabel("이름");
		lblNewLabel_3.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		lblNewLabel_3.setBounds(60, 113, 55, 35);
		contentPane.add(lblNewLabel_3);

		JLabel label = new JLabel("휴대폰");
		label.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		label.setBounds(54, 153, 54, 35);
		contentPane.add(label);

		ok_btn.setBackground(SystemColor.menu);
		ok_btn.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		ok_btn.setBounds(67, 245, 100, 35);
		contentPane.add(ok_btn);

		cancel_btn.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		cancel_btn.setBackground(SystemColor.menu);
		cancel_btn.setBounds(190, 245, 100, 35);
		contentPane.add(cancel_btn);

		id_tf = new JTextField();
		id_tf.setColumns(10);
		id_tf.setBounds(105, 75, 188, 28);
		contentPane.add(id_tf);

		name_tf = new JTextField();
		name_tf.setColumns(10);
		name_tf.setBounds(105, 115, 188, 28);
		contentPane.add(name_tf);

		phone_tf = new JTextField();
		phone_tf.setColumns(10);
		phone_tf.setBounds(105, 155, 50, 28);
		contentPane.add(phone_tf);

		phone_tf2 = new JTextField();
		phone_tf2.setColumns(10);
		phone_tf2.setBounds(173, 155, 50, 28);
		contentPane.add(phone_tf2);

		phone_tf3 = new JTextField();
		phone_tf3.setColumns(10);
		phone_tf3.setBounds(243, 155, 50, 28);
		contentPane.add(phone_tf3);

		JLabel lblNewLabel_2 = new JLabel("-");
		lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 11));
		lblNewLabel_2.setBounds(160, 153, 30, 35);
		contentPane.add(lblNewLabel_2);

		JLabel label_1 = new JLabel("-");
		label_1.setFont(new Font("굴림", Font.PLAIN, 11));
		label_1.setBounds(230, 153, 30, 35);
		contentPane.add(label_1);


		find_lb1.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		find_lb1.setBounds(45, 197, 65, 35);
		find_lb1.setVisible(false);
		contentPane.add(find_lb1);

		find_lb2.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		find_lb2.setBounds(118, 197, 116, 35);
		find_lb2.setVisible(false);
		find_lb2.setHorizontalAlignment(JLabel.CENTER);
		contentPane.add(find_lb2);

		find_lb3.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		find_lb3.setBounds(243, 197, 55, 35);
		find_lb3.setVisible(false);
		contentPane.add(find_lb3);

		setVisible(true);

	} // init 끝

	public static void main(String[] args) {
		new pw_find();
	}

	public void actionPerformed(ActionEvent e) { // 액션리스너

		if(e.getSource() == ok_btn) { // 확인 버튼을 누르면
			String ID = id_tf.getText();
			String NAME = name_tf.getText();
			String PHONE = phone_tf.getText()+"-"+phone_tf2.getText()+"-"+phone_tf3.getText();

			String id = IDfind(ID, NAME, PHONE);


		}
		else if(e.getSource() == cancel_btn){
			 System.out.println("PW찾기 취소 버튼 클릭");
			 setVisible(false);
		 }
	}

	public String IDfind(String id, String name, String phone) {
		String url = "URL";
		Connection con = null;  //view 도 역시 list와 같이 데이터를 불러와야하기 때문에 ResultSet을 준비 한다.
		Properties info = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt;

		String ID = id_tf.getText();
		String NAME = name_tf.getText();
		String PHONE = phone_tf.getText()+"-"+phone_tf2.getText()+"-"+phone_tf3.getText();



		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			info = new Properties();
			info.setProperty("user", "아이디");
			info.setProperty("password", "비밀번호");
			con = DriverManager.getConnection(url, info); 

			String sql = "select pw"
					+ " from CH_USER"
					+ " where ID='" +  ID + "' and name = '" + NAME + "' and phone = '" + PHONE + "'"  ;   // 조건은 로그인 한 id와 같은 id를 가진 데이터
			System.out.println(ID);
			System.out.println(NAME);
			System.out.println(PHONE);

			pstmt = con.prepareStatement(sql);  
			rs = pstmt.executeQuery();


			if(rs.next()) {
				PW = rs.getString("pw");
				System.out.println(PW);
				find_lb2.setText(PW);
				find_lb1.setVisible(true);
				find_lb2.setVisible(true);
				find_lb3.setVisible(true);
				return PW;
				
			}
			else
				System.out.println("실패");
			JOptionPane.showMessageDialog(null, "   입력하신 정보와 일치하는\n비밀번호가 존재하지 않습니다.");
//			find_lb1.setVisible(true);
//			find_lb2.setVisible(true);
//			find_lb3.setVisible(true);



		}
		catch(ClassNotFoundException cnfe){
			cnfe.printStackTrace();
		}
		catch(SQLException se){
			se.printStackTrace();
		}


		return PW;
	}
}

