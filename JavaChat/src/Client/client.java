package Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class client extends JFrame implements ActionListener, KeyListener, WindowListener {
	// 자동 import ctrl + shift + o


	GregorianCalendar calendar = new GregorianCalendar();
	int year = calendar.get(Calendar.YEAR);
	int month = calendar.get(Calendar.MONTH);
	int date = calendar.get(Calendar.DATE);
	int amPm = calendar.get(Calendar.AM_PM);
	int hour = calendar.get(Calendar.HOUR);
	int min = calendar.get(Calendar.MINUTE);
	int sec = calendar.get(Calendar.SECOND);
	String sAmPm = amPm == Calendar.AM ? "오전" : "오후";


	//Login GUI 변수
	private JFrame Login_GUI = new JFrame();
	private JPanel Login_Pane;
	private JTextField ip_tf; //sever ip 받는 텍스트필드
	private JPasswordField Password_tf; //sever port 받는 텍스트필드
	private JTextField id_tf; //id 받는 텍스트필드
	private JButton login_btn = new JButton("로그인"); //접속버튼
	private JButton join_btn = new JButton("회원가입"); // 회원가입 버튼
	private JButton IDFind_btn = new JButton("ID 찾기");
	private JButton PWFind_btn = new JButton("PW 찾기");

	private String STATE = ""; // login, logout 로그인기록 저장 변수


	//Main GUI 변수

	private JPanel contentPane;

	private JButton UserPanel_btn = new JButton(""); //전제 접속자 버튼
	private JPanel User_panel = new JPanel(); // 전체 접속자 패널
	private JList User_list = new JList(); // 전체 접속자 List
	private JButton notesend_btn = new JButton("");
	static String id_other = ""; // 다른 사용자 id
	private final JLabel lblNewLabel_2 = new JLabel("님");
	private final JLabel lblNewLabel_3 = new JLabel("--------------------------------");
	private final JLabel label_1 = new JLabel("--------------------------------");
	private  JLabel UserID_lb = new JLabel("");
	private JButton proview_btn = new JButton("");



	private JButton RoomPanel_btn = new JButton(""); // 채팅방 버튼
	private JPanel Room_panel = new JPanel(); // 채팅방 패널
	private JList Room_list = new JList(); // 전체 방목록 List
	private JButton joinroom_btn = new JButton("");
	private JButton createroom_btn = new JButton("");



	private JButton ProPanel_btn = new JButton(""); // 프로필 버튼
	private JPanel pro_panel = new JPanel(); // 프로필 패널
	private JLabel proIMG_lb = new JLabel("    img"); 	//사용자 프로필 변수 시작
	private JLabel proID_lb = new JLabel("ID");
	private JLabel proName_lb = new JLabel("이름");
	private JLabel proSex_lb = new JLabel("성별");
	private JLabel proBirth_lb = new JLabel("생년월일");
	private JLabel proEmail_lb = new JLabel("이메일");
	private JLabel proPhone_lb = new JLabel("핸드폰번호"); //사용자 프로필 변수 시작
	private JButton pro_btn = new JButton(""); // 사용자 프로필 변경 버튼
	private JButton proUP_btn = new JButton("");

	//Chat GUI 변수
	public static JFrame Chat_GUI = new JFrame(); // 채팅창 
	private JPanel Chat_Pane;
	private JButton clear_btn= new JButton(""); // 대화 모두 지우기 버튼
	private JButton exit_btn = new JButton("");
	private JButton send_btn = new JButton("전송");
	private JButton emoticon_btn = 
			new JButton(new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\emoticon.png"));
	private JTextField message_tf; // 메시지 입력하는 tf
	private JTextPane Chat_area = new JTextPane();
	private JLabel es_lb1 = new JLabel("");
	Image originalImage;
	private ImageIcon originalIcon;
	private final JLabel label = new JLabel("");
	private JLabel ES_img_lb1 = new JLabel("");
	private JLabel es_mainimg_lb = new JLabel("");	
	private final JButton colorMe_btn = new JButton("My");
	private final JButton colorOther_btn = new JButton("Other");
	Color my_color = Color.black; 
	Color you_color = Color.blue; 

	//네트워크를 위한 자원 변수
	private Socket socket;
	private String ip="IP"; //127.0.0.1 네트워크에서 자기 자신을 가리키는 변수
	private int port=9000;
	static String id = "";
	private String pw = "";
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private static DataOutputStream dos;

	//DB연결변수
	DB mDB = new DB();
	String strSQL = null;
	boolean idcheck = false;
	boolean pwcheck = false;
	boolean check = false;

	//채팅저장 변수들
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "url";
	private static final String USER = "ID"; // DB ID
	private static final String PASS = "비밀번호";
	//static final String My_room = null;


	//그 외 변수들
	Vector user_list = new Vector();
	Vector room_list = new Vector();
	StringTokenizer st;

	static String My_Room; // 내가 있는 방 이름
	private final JScrollPane scrollPane = new JScrollPane();
	private final JButton logout_btn = new JButton("");




	client() //생성자 메소드
	{
		super("client");
		Login_init(); //Login창 화면 구성 메소드
		Main_init(); //Main창 화면 구성 메소드
		Chat_init(); // Chat창 화면 구성 메소드
		start();
	}


	private void start()
	{
		Password_tf.addKeyListener(this);
		join_btn.addActionListener(this); //회원가입 버튼 리스너
		login_btn.addActionListener(this); //로그인 버튼 리스너
		notesend_btn.addActionListener(this); // 쪽지보내기 버튼 리스너
		joinroom_btn.addActionListener(this); // 채팅방 참여 버튼 리스너
		createroom_btn.setBackground(SystemColor.menu);
		createroom_btn.addActionListener(this); // 채팅방 만들기 버튼 리스너
		send_btn.addActionListener(this); // 채팅 전송 버튼 리스너
		message_tf.addKeyListener(this);
		emoticon_btn.addActionListener(this);
		exit_btn.addActionListener(this);
		logout_btn.addActionListener(this);
		pro_btn.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\profile.png"));
		pro_btn.addActionListener(this);
		IDFind_btn.addActionListener(this);
		PWFind_btn.addActionListener(this);
		RoomPanel_btn.addActionListener(this);
		ProPanel_btn.addActionListener(this);
		UserPanel_btn.addActionListener(this);
		proUP_btn.addActionListener(this);
		colorMe_btn.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\color.png"));
		colorMe_btn.addActionListener(this);
		colorOther_btn.addActionListener(this);
		proview_btn.addActionListener(this);
		clear_btn.addActionListener(this);


	}



	private void Main_init()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(490, 100, 354, 550);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(251, 220, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel talk_lb2 = new JLabel("Talk");
		talk_lb2.setForeground(Color.BLACK);
		talk_lb2.setHorizontalAlignment(SwingConstants.CENTER);
		talk_lb2.setFont(new Font("Adobe Fan Heiti Std B", Font.BOLD | Font.ITALIC, 23));
		talk_lb2.setBounds(165, 27, 89, 35);
		contentPane.add(talk_lb2);
		
		ImageIcon image1 = new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\estherBUNNY.png");  //이미지 경로
		ES_img_lb1 = new JLabel(image1,SwingConstants.CENTER);
		ES_img_lb1.setBounds(95, 10, 95, 50);
		contentPane.add(ES_img_lb1);

		//추가 시작

		//전체 사용자 패널 시작
		UserPanel_btn.setBackground(SystemColor.menu);
		UserPanel_btn.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\user2.png"));
		UserPanel_btn.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		UserPanel_btn.setBounds(0, 451, 114, 60);
		UserPanel_btn.setFocusPainted(false); // 포커스 표시 설정
		UserPanel_btn.setBorderPainted(false); // 버튼 테두리 설정

		contentPane.add(UserPanel_btn);

		User_panel.setBackground(Color.WHITE);
		User_panel.setBounds(20, 71, 298, 361);
		contentPane.add(User_panel);
		User_panel.setLayout(null);
		User_list.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		User_list.setBounds(83, 63, 120, 273); // 유저리스트
		DefaultListCellRenderer User_ = (DefaultListCellRenderer) User_list.getCellRenderer();
		User_.setHorizontalAlignment(SwingConstants.CENTER);
		User_panel.add(User_list);
		User_list.setListData(user_list);

		notesend_btn.setFont(new Font("맑은 고딕", Font.BOLD, 12)); // 쪽지보내기
		notesend_btn.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\send11.png"));
		notesend_btn.setFocusPainted(false); // 포커스 표시 설정
		notesend_btn.setBorderPainted(false); // 버튼 테두리 설정
		notesend_btn.setContentAreaFilled(false); // 버튼 영역 배경 표시 설정
		notesend_btn.setBounds(205, 285, 89, 70);
		User_panel.add(notesend_btn);

		proview_btn.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\보기.png"));
		proview_btn.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		proview_btn.setFocusPainted(false);
		proview_btn.setContentAreaFilled(false);
		proview_btn.setBorderPainted(false);
		proview_btn.setBounds(205, 213, 89, 70);
		User_panel.add(proview_btn);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("맑은 고딕", Font.BOLD, 22));
		lblNewLabel_2.setBounds(190, 10, 43, 39);
		
		User_panel.add(lblNewLabel_2);
		lblNewLabel_3.setFont(new Font("맑은 고딕", Font.PLAIN, 23));
		lblNewLabel_3.setBounds(2, -12, 298, 35);
		
		User_panel.add(lblNewLabel_3);
		label_1.setFont(new Font("맑은 고딕", Font.PLAIN, 23));
		label_1.setBounds(2, 29, 298, 35);
		
		User_panel.add(label_1);
		UserID_lb.setHorizontalAlignment(SwingConstants.CENTER);
		UserID_lb.setFont(new Font("맑은 고딕", Font.BOLD, 22));
		UserID_lb.setBounds(66, 10, 127, 39);
		
		User_panel.add(UserID_lb);

		User_panel.setVisible(true);
		// 전체 사용자 패널 끝

		// 채팅방 패널 시작
		RoomPanel_btn.setBackground(SystemColor.menu);
		RoomPanel_btn.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\message.png"));
		RoomPanel_btn.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		RoomPanel_btn.setFocusPainted(false); // 포커스 표시 설정
		RoomPanel_btn.setBorderPainted(false); // 버튼 테두리 설정
		RoomPanel_btn.setBounds(114, 451, 113, 60);
		contentPane.add(RoomPanel_btn);
		Room_panel.setBackground(Color.WHITE);
		Room_panel.setBounds(20, 71, 298, 361);

		contentPane.add(Room_panel);
		Room_panel.setLayout(null);


		createroom_btn.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\room+1.png"));
		createroom_btn.setFocusPainted(false); // 포커스 표시 설정
		createroom_btn.setBorderPainted(false); // 버튼 테두리 설정
		createroom_btn.setContentAreaFilled(false); // 버튼 영역 배경 표시 설정
		createroom_btn.setBounds(205, 285, 89, 70);
		Room_panel.add(createroom_btn);

		Room_list.setBounds(86, 10, 120, 326);
		Room_panel.add(Room_list);
		Room_list.setListData(room_list);
		DefaultListCellRenderer Room_ = (DefaultListCellRenderer) Room_list.getCellRenderer();
		Room_.setHorizontalAlignment(SwingConstants.CENTER);
		Room_list.setFont(new Font("맑은 고딕", Font.BOLD, 13));



		joinroom_btn.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\join3.png"));
		joinroom_btn.setFocusPainted(false);
		joinroom_btn.setBorderPainted(false);
		joinroom_btn.setContentAreaFilled(false); // 버튼 영역 배경 표시 설정
		joinroom_btn.setBackground(Color.WHITE);
		joinroom_btn.setBounds(205, 220, 89, 70);
		Room_panel.add(joinroom_btn);

		Room_panel.setVisible(false);
		//채팅방 패널 끝

		// 프로필 패널 시작
		ProPanel_btn.setBackground(SystemColor.menu);
		ProPanel_btn.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\set.png"));
		ProPanel_btn.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		ProPanel_btn.setFocusPainted(false); // 포커스 표시 설정
		ProPanel_btn.setBorderPainted(false); // 버튼 테두리 설정
		ProPanel_btn.setBounds(226, 451, 114, 60);
		contentPane.add(ProPanel_btn);

		pro_panel.setBounds(504, 59, 197, 406);
		contentPane.add(pro_panel);
		pro_panel.setLayout(null);

		pro_panel.setBackground(Color.WHITE);
		pro_panel.setBounds(20, 71, 298, 361);
		contentPane.add(pro_panel);
		pro_panel.setLayout(null);
		pro_panel.setVisible(false);
		proIMG_lb.setBackground(Color.RED);


		proIMG_lb.setBounds(80, 15, 140, 140);
		pro_panel.add(proIMG_lb);
		proIMG_lb.setIcon(null);
		proID_lb.setBackground(Color.PINK);

		proID_lb.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		proID_lb.setBounds(125, 172, 105, 24);
		pro_panel.add(proID_lb);

		proName_lb.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		proName_lb.setBounds(125, 198, 105, 24);
		pro_panel.add(proName_lb);


		proSex_lb.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		proSex_lb.setBounds(125, 223, 100, 24);
		pro_panel.add(proSex_lb);

		proBirth_lb.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		proBirth_lb.setBounds(125, 247, 105, 24);
		pro_panel.add(proBirth_lb);

		proEmail_lb.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		proEmail_lb.setBounds(125, 273, 115, 24);
		pro_panel.add(proEmail_lb);

		proPhone_lb.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		proPhone_lb.setBounds(125, 298, 105, 24);
		pro_panel.add(proPhone_lb);

		pro_btn.setBackground(SystemColor.menu);
		pro_btn.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		pro_btn.setBounds(246, 308, 40, 40);
		pro_btn.setFocusPainted(false); // 포커스 표시 설정
		pro_btn.setBorderPainted(false); // 버튼 테두리 설정
		pro_btn.setContentAreaFilled(false); // 버튼 영역 배경 표시 설정
		pro_panel.add(pro_btn);

		JLabel proID = new JLabel("ID  ");
		proID.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		proID.setBounds(90, 173, 24, 24);
		pro_panel.add(proID);

		JLabel proName = new JLabel("이름");
		proName.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		proName.setBounds(83, 198, 33, 24);
		pro_panel.add(proName);

		JLabel proSex = new JLabel("성별");
		proSex.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		proSex.setBounds(83, 223, 33, 24);
		pro_panel.add(proSex);

		JLabel proBirth = new JLabel("생년월일");
		proBirth.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		proBirth.setBounds(63, 248, 60, 24);
		pro_panel.add(proBirth);

		JLabel proEmail = new JLabel("E-mail");
		proEmail.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		proEmail.setBounds(73, 273, 40, 24);
		pro_panel.add(proEmail);

		JLabel proPhone = new JLabel("Phone");
		proPhone.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		proPhone.setBounds(73, 298, 40, 24);
		pro_panel.add(proPhone);

		proUP_btn.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\update.png"));
		proUP_btn.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		proUP_btn.setFocusPainted(false);
		proUP_btn.setContentAreaFilled(false);
		proUP_btn.setBorderPainted(false);
		proUP_btn.setBackground(SystemColor.menu);
		proUP_btn.setBounds(246, 258, 40, 40);
		pro_panel.add(proUP_btn);

		pro_panel.setVisible(false);
		//프로필 패널 끝

		logout_btn.setFont(new Font("맑은 고딕", Font.PLAIN, 11)); // 로그아웃 버튼
		logout_btn.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\exit_.png"));
		logout_btn.setFocusPainted(false); // 포커스 표시 설정
		logout_btn.setBorderPainted(false); // 버튼 테두리 설정
		logout_btn.setContentAreaFilled(false); // 버튼 영역 배경 표시 설정
		logout_btn.setBounds(258, 11, 64, 60);
		contentPane.add(logout_btn);

		this.setVisible(false); //맨 처음에 보이지 않게 함 true = 화면에 보인다 false = 화면에 보이지 않는다
	} 


	private void Login_init() //Login창 화면 구성 메소드 생성
	{

		Login_GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Login_GUI.setBounds(400, 100, 520, 442);
		Login_Pane = new JPanel();
		Login_Pane.setBackground(new Color(251, 220, 235));
		Login_Pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		Login_GUI.setContentPane(Login_Pane);
		Login_Pane.setLayout(null);

		JLabel lblPassword = new JLabel("비밀번호");
		lblPassword.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		lblPassword.setBounds(307, 200, 95, 18);
		Login_Pane.add(lblPassword);


		JLabel lblId = new JLabel("ID");
		lblId.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		lblId.setBounds(320, 150, 62, 18);
		Login_Pane.add(lblId);

		Password_tf = new JPasswordField();
		Password_tf.setBounds(360, 200, 120, 24);
		Login_Pane.add(Password_tf);
		Password_tf.setColumns(10);

		id_tf = new JTextField();
		id_tf.setBounds(360, 150, 120, 24);
		Login_Pane.add(id_tf);
		id_tf.setColumns(10);


		login_btn.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		login_btn.setBackground(SystemColor.menu);
		login_btn.setBounds(330, 270, 140, 27);
		Login_Pane.add(login_btn);


		join_btn.setBackground(SystemColor.menu);
		join_btn.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		join_btn.setBounds(330, 310, 140, 27);
		Login_Pane.add(join_btn);

		ImageIcon image1 = new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\estherBUNNY.png");  //이미지 경로
		JLabel ES_imglb = new JLabel(image1,SwingConstants.CENTER);
		ES_imglb.setBounds(170, 15, 95, 50);
		Login_Pane.add(ES_imglb);

		JLabel talk_lb1 = new JLabel("Talk");
		talk_lb1.setHorizontalAlignment(SwingConstants.CENTER);
		talk_lb1.setForeground(Color.BLACK);
		talk_lb1.setFont(new Font("Adobe Fan Heiti Std B", Font.BOLD | Font.ITALIC, 27));
		talk_lb1.setBounds(250, 30, 89, 35);
		Login_Pane.add(talk_lb1);

		ImageIcon image2 = new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\초롱초롱.gif");  //이미지 경로
		JLabel lblNewLabel = new JLabel(image2,SwingConstants.CENTER);
		lblNewLabel.setText("");
		lblNewLabel.setBounds(31, 79, 258, 300);
		Login_Pane.add(lblNewLabel);


		IDFind_btn.setContentAreaFilled(false); // 버튼 영역 배경 표시 설정
		IDFind_btn.setBorderPainted(false); // 버튼 테두리 설정			
		IDFind_btn.setFont(new Font("맑은 고딕", Font.PLAIN, 10));
		IDFind_btn.setBackground(SystemColor.menu);
		IDFind_btn.setBounds(307, 345, 80, 27);
		Login_Pane.add(IDFind_btn);

		JLabel lblNewLabel_1 = new JLabel("|");
		lblNewLabel_1.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		lblNewLabel_1.setBounds(387, 339, 10, 35);
		Login_Pane.add(lblNewLabel_1);

		PWFind_btn.setFont(new Font("맑은 고딕", Font.PLAIN, 10));
		PWFind_btn.setContentAreaFilled(false);
		PWFind_btn.setBorderPainted(false);
		PWFind_btn.setBackground(SystemColor.menu);
		PWFind_btn.setBounds(395, 345, 85, 27);
		Login_Pane.add(PWFind_btn);

		Login_GUI.setVisible(true); //true = 화면에 보인다 false = 화면에 보이지 않는다

	} 


	private void Chat_init(){
		Chat_GUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Chat_GUI.setBounds(390, 70, 385, 580);
		Chat_Pane = new JPanel();
		Chat_Pane.setBackground(new Color(251, 220, 235));
		Chat_Pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		Chat_GUI.setContentPane(Chat_Pane);
		Chat_Pane.setLayout(null);


		Chat_GUI.getContentPane().add(scrollPane);
		Chat_area.setEditable(false);
		scrollPane.setViewportView(Chat_area);

		message_tf = new JTextField();
		message_tf.setBounds(20, 465, 266, 24);
		Chat_GUI.getContentPane().add(message_tf);
		message_tf.setColumns(10);
		message_tf.setEnabled(false); //main UI가 뜨면 채팅 내역이 나오는 텍스트 필드를 비활성화 시킴

		send_btn.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		send_btn.setBounds(294, 465, 56, 24);
		Chat_Pane.add(send_btn);
		send_btn.setEnabled(false); //main UI가 뜨면  채팅 전송 버튼을 비활성화 시킴

		emoticon_btn.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		emoticon_btn.setFocusPainted(false); // 포커스 표시 설정
		emoticon_btn.setContentAreaFilled(false); // 버튼 영역 배경 표시 설정
		emoticon_btn.setBorderPainted(false); // 버튼 테두리 설정
		emoticon_btn.setBounds(20, 495, 38, 38);
		emoticon_btn.setEnabled(false);
		Chat_Pane.add(emoticon_btn);
		scrollPane.setBounds(20, 75, 330, 373);

		exit_btn.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		exit_btn.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\-1.png"));
		exit_btn.setBackground(SystemColor.menu);
		exit_btn.setBounds(260, 13, 90, 65);
		exit_btn.setFocusPainted(false); // 포커스 표시 설정
		exit_btn.setContentAreaFilled(false); // 버튼 영역 배경 표시 설정
		exit_btn.setBorderPainted(false); // 버튼 테두리 설정
		Chat_Pane.add(exit_btn);

		originalIcon = new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\에스더비니_리본버니.png");  //이미지 경로
		Image originalImage2=originalIcon.getImage(); // 해당 위치의 파일을 이미지로 얻어온다
		Image resizeImage2= originalImage2.getScaledInstance(173,77,Image.SCALE_SMOOTH); //파일의 크기를 조정한다.
		ImageIcon resizeIcon2=new ImageIcon(resizeImage2); // 객체 생성후      
		es_lb1.setIcon(resizeIcon2);
		es_lb1.setBounds(40, -9, 126, 89);
		Chat_Pane.add(es_lb1);

		originalIcon = new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\그림1.png");  //이미지 경로
		Image originalImage1=originalIcon.getImage(); // 해당 위치의 파일을 이미지로 얻어온다
		Image resizeImage1= originalImage1.getScaledInstance(45,45,Image.SCALE_SMOOTH); //파일의 크기를 조정한다.
		ImageIcon resizeIcon1=new ImageIcon(resizeImage1); // 객체 생성후      
		label.setIcon(resizeIcon1);
		label.setBounds(50, 9, 98, 89);
		Chat_Pane.add(label);

		colorMe_btn.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		colorMe_btn.setBounds(69, 495, 100, 38);
		colorMe_btn.setFocusPainted(false); // 포커스 표시 설정
		colorMe_btn.setContentAreaFilled(false); // 버튼 영역 배경 표시 설정
		colorMe_btn.setBorderPainted(false); // 버튼 테두리 설정
		Chat_Pane.add(colorMe_btn);

		colorOther_btn.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\color.png"));
		colorOther_btn.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		colorOther_btn.setFocusPainted(false);
		colorOther_btn.setContentAreaFilled(false);
		colorOther_btn.setBorderPainted(false);
		colorOther_btn.setBounds(176, 495, 100, 38);

		Chat_Pane.add(colorOther_btn);
		
		clear_btn.setBounds(40, 2, 124, 73); 
		clear_btn.setFocusPainted(false); // 포커스 표시 설정
		clear_btn.setContentAreaFilled(false); // 버튼 영역 배경 표시 설정
		clear_btn.setBorderPainted(false); // 버튼 테두리 설정
		Chat_Pane.add(clear_btn);

		ImageIcon image1 = new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\estherBUNNY.png");

		ImageIcon image2 = new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\bunny1.png");


		Chat_GUI.setVisible(false);

	}


	private void Network()
	{
		try {
			socket = new Socket(ip,port);

			if(socket != null) //정상적으로 소켓이 연결되었을경우
			{
				Connection();
				view(id);
			}

		} catch (UnknownHostException e) { //소켓이 정상적을 연결되지 않았을 때

			JOptionPane.showMessageDialog(null, "소켓 연결 실패", "알림", JOptionPane.ERROR_MESSAGE);

		} catch (IOException e) {

			JOptionPane.showMessageDialog(null, "소켓 연결 실패", "알림", JOptionPane.ERROR_MESSAGE);
		}

	}


	private void Connection() //실제적인 메소드 연결부분
	{
		try {
			is = socket.getInputStream();
			dis = new DataInputStream(is);

			os = socket.getOutputStream();
			dos = new DataOutputStream(os);
		}
		catch(IOException e) //에러처리부분
		{
			JOptionPane.showMessageDialog(null, "연결 실패", "알림", JOptionPane.ERROR_MESSAGE);
		} //Stream 설정 끝
		STATE = "Login";
		LoginDB(id);
		this.setVisible(true); //main UI 표시
		this.Login_GUI.setVisible(false); //Login UI 닫기

		//처음 접속시에  ID 전송
		send_message(id);

		// User_list에 사용자 추가
		this.setVisible(true);
		user_list.add(id);


		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				while(true){

					try {
						String msg = dis.readUTF(); //메세지 수신

						System.out.println("서버로부터 수신된 메시지 : " + msg);

						inmessage(msg);

					} catch (IOException e) {

						try {
							os.close();
							is.close();
							dos.close();
							dis.close();
							socket.close();
						}
						catch(IOException e1) {}
						break;

					}


				} //while문 끝

			}
		});

		th.start();

	}

	private void inmessage(String str) //서버로부터 들어오는 모든 메세지
	{
		st = new StringTokenizer(str,"/");

		String protocol = st.nextToken();
		String Message = st.nextToken();

		System.out.println("프로토콜 : " + protocol);
		System.out.println("내용 : " + Message);

		if(protocol.equals("NewUser")) //새로운 접속자
		{
			user_list.add(Message);

		}
		else if(protocol.equals("OldUser"))
		{
			user_list.add(Message);
		}
		else if(protocol.equals("Note"))
		{

			String note = st.nextToken();

			System.out.println(Message + " 사용자로부터 온 쪽지 " + note);

			JOptionPane.showMessageDialog(null,note,Message + "님으로부터 쪽지", JOptionPane.CLOSED_OPTION);

		}
		else if(protocol.equals("user_list_update"))
		{
			User_list.setListData(user_list);
		}

		else if(protocol.equals("CreateRoom")) //새로운 방을 만들었을 때
		{
			My_Room = Message;
			message_tf.requestFocus();
			message_tf.setEnabled(true); //방을 만들었을 때 메세지 나오는 텍스트 필드를 활성화시킨다
			send_btn.setEnabled(true); //방을 만들었을 때 전송 버튼을 활성화시킨다
			emoticon_btn.setEnabled(true);
			exit_btn.setEnabled(true);
			joinroom_btn.setEnabled(false); //방을 만들었을 때 방만들기 버튼을 비활성화
			createroom_btn.setEnabled(false); //방을 만들었을 때 방참여 버튼을 비활성화
			Chat_GUI.setVisible(true);


		}

		else if(protocol.equals("CreateRoomFail")) //방 만들기 실패했을 경우
		{
			JOptionPane.showMessageDialog(null, "방 만들기 실패", "알림", JOptionPane.ERROR_MESSAGE);
		}

		else if(protocol.equals("New_Room")) //새로운 방을 만들었을 때
		{
			room_list.add(Message);
			Room_list.setListData(room_list);
		}

		else if(protocol.equals("Chatting"))
		{
			StyledDocument doc = Chat_area.getStyledDocument(); //StyledDocument를 통해 Chat_area의 텍스트의 속성을 바꿈
			SimpleAttributeSet smi=new SimpleAttributeSet();
			String msg =  st.nextToken();


			Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

			Style s=doc.addStyle("color_my", def);
			StyleConstants.setForeground(s, my_color); //내 채팅색
			


			s=doc.addStyle("color_you", def);
			StyleConstants.setForeground(s, you_color); //상대 채팅색

			s =doc.addStyle("black", def);
			StyleConstants.setForeground(s, Color.black); //자신, 알림

			s = doc.addStyle("right", def);
			StyleConstants.setAlignment(s, StyleConstants.ALIGN_RIGHT);
			s = doc.addStyle("left", def);
			StyleConstants.setAlignment(s, StyleConstants.ALIGN_LEFT);
			s = doc.addStyle("center", def);
			StyleConstants.setAlignment(s, StyleConstants.ALIGN_CENTER);


			System.out.println(Message);
			System.out.println(id);

			try {
				String Str=null;



				if(Message.equals(id)) {
					doc.setParagraphAttributes(0, doc.getLength(), smi, false);
					doc.setLogicalStyle(doc.getLength(), doc.getStyle("right"));
					Style profile = doc.addStyle("프로필IMG1", null);
					StyleConstants.setIcon(profile, ResizeImage(checkIDImage(id),40,40));
					doc.insertString(doc.getLength(),Message+" \n ", doc.getStyle("color_my")); //사용자아이디
					doc.insertString(doc.getLength(),"ignored text", profile); //사용자프로필사진
					StyleConstants.setForeground(smi, Color.black);
					doc.insertString(doc.getLength(),"\n", null);


					if(msg.equals("옴팡1")||msg.equals("1")){          
						System.out.println("호롤로로롤");

						StyleConstants.setIcon(smi,new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\[크기변환]옴팡이10.gif"));      // smi변수에 이모티콘 저장
						doc.insertString(doc.getLength(),Message,smi);               //이미지 출력

					} 


					else if(msg.equals("옴팡2")||msg.equals("2")){     

						StyleConstants.setIcon(smi,new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\1[크기변환]옴팡이.gif"));
						doc.insertString(doc.getLength(),Message,smi);


					}
					else if(msg.equals("옴팡3")||msg.equals("3")){     


						StyleConstants.setIcon(smi,new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\[크기변환]옴팡이3.gif"));
						doc.insertString(doc.getLength(),Message,smi);


					}
					else if(msg.equals("옴팡4")||msg.equals("4")){     


						StyleConstants.setIcon(smi,new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\[크기변환]옴팡이2.gif"));
						doc.insertString(doc.getLength(),Message,smi);

					} 
					else if(msg.equals("옴팡5")||msg.equals("5")){      


						StyleConstants.setIcon(smi,new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\[크기변환]옴팡이5.gif"));
						doc.insertString(doc.getLength(),Message,smi);

					} 
					else if(msg.equals("옴팡6")||msg.equals("6")){    

						StyleConstants.setIcon(smi,new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\[크기변환]옴팡이6.gif"));
						doc.insertString(doc.getLength(),Message,smi);

					} 
					else if(msg.equals("옴팡7")||msg.equals("7")){      

						StyleConstants.setIcon(smi,new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\[크기변환]옴팡이7.gif"));
						doc.insertString(doc.getLength(),Message,smi);

					} 
					else if(msg.equals("옴팡8")||msg.equals("8")){      


						StyleConstants.setIcon(smi,new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\[크기변환]옴팡이8.gif"));
						doc.insertString(doc.getLength(),Message,smi);

					} 
					else if(msg.equals("옴팡9")||msg.equals("9")){    

						StyleConstants.setIcon(smi,new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\[크기변환]옴팡이9.gif"));
						doc.insertString(doc.getLength(),Message,smi);

					} 

					else{
						doc.insertString(doc.getLength(),msg, doc.getStyle("color_my"));
					}
					doc.insertString(doc.getLength()," ("+sAmPm+""+hour+":"+min+") ", doc.getStyle("color_my"));
					doc.insertString(doc.getLength(),"\n", null);
					Chat_area.setCaretPosition(doc.getLength());


				}


				else if(Message.equals("알림")) {
					doc.setLogicalStyle(doc.getLength(), doc.getStyle("center"));
					doc.insertString(doc.getLength(), Message+" : "+msg+"\n", doc.getStyle("black"));
					Chat_area.setCaretPosition(doc.getLength());

				}


				else { //상대방일 경우

					doc.setParagraphAttributes(0, doc.getLength(), smi, false);
					doc.setLogicalStyle(doc.getLength(), doc.getStyle("left"));

					Style profile = doc.addStyle("프로필IMG2", null);
					StyleConstants.setIcon(profile, ResizeImage(checkIDImage(Message),40,40));
					doc.insertString(doc.getLength(),Message+" \n ", doc.getStyle("color_you")); // 사용자 아이디
					doc.insertString(doc.getLength(),"ignored text", profile); // 사용자 프로팔
					StyleConstants.setForeground(smi, Color.blue);
					doc.insertString(doc.getLength(),"\n", null);

					if(msg.equals("옴팡1")||msg.equals("1")){          
						System.out.println("호롤로로롤");

						StyleConstants.setIcon(smi,new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\[크기변환]옴팡이10.gif"));      // smi 변수에 이모티콘 저장
						doc.insertString(doc.getLength(),Message,smi);               // 이미지 출력

					} 


					else if(msg.equals("옴팡2")||msg.equals("2")){     

						StyleConstants.setIcon(smi,new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\1[크기변환]옴팡이.gif"));
						doc.insertString(doc.getLength(),Message,smi);


					}
					else if(msg.equals("옴팡3")||msg.equals("3")){     


						StyleConstants.setIcon(smi,new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\[크기변환]옴팡이3.gif"));
						doc.insertString(doc.getLength(),Message,smi);


					}
					else if(msg.equals("옴팡4")||msg.equals("4")){     


						StyleConstants.setIcon(smi,new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\[크기변환]옴팡이2.gif"));
						doc.insertString(doc.getLength(),Message,smi);

					} 
					else if(msg.equals("옴팡5")||msg.equals("5")){      


						StyleConstants.setIcon(smi,new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\[크기변환]옴팡이5.gif"));
						doc.insertString(doc.getLength(),Message,smi);

					} 
					else if(msg.equals("옴팡6")||msg.equals("6")){    

						StyleConstants.setIcon(smi,new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\[크기변환]옴팡이6.gif"));
						doc.insertString(doc.getLength(),Message,smi);

					} 
					else if(msg.equals("옴팡7")||msg.equals("7")){      

						StyleConstants.setIcon(smi,new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\[크기변환]옴팡이7.gif"));
						doc.insertString(doc.getLength(),Message,smi);

					} 
					else if(msg.equals("옴팡8")||msg.equals("8")){      


						StyleConstants.setIcon(smi,new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\[크기변환]옴팡이8.gif"));
						doc.insertString(doc.getLength(),Message,smi);

					} 
					else if(msg.equals("옴팡9")||msg.equals("9")){    

						StyleConstants.setIcon(smi,new ImageIcon("C:\\Users\\user\\Desktop\\자바프로젝트\\자바사진\\[크기변환]옴팡이9.gif"));
						doc.insertString(doc.getLength(),Message,smi);

					}

					else{ //채팅일 경우

						doc.insertString(doc.getLength(),msg, doc.getStyle("color_you"));
					}

					doc.insertString(doc.getLength()," ("+sAmPm+""+hour+":"+min+") ", doc.getStyle("color_you"));
					doc.insertString(doc.getLength(),"\n", null);
					Chat_area.setCaretPosition(doc.getLength());
				}


			}


			catch (Exception e) {
				System.out.println("오류");
			}

		}

		else if(protocol.equals("OldRoom"))
		{
			room_list.add(Message);
		}

		else if(protocol.equals("Room_list_update"))
		{
			Room_list.setListData(room_list);

		}
		else if(protocol.equals("JoinRoom"))
		{
			My_Room = Message;
			message_tf.requestFocus();
			Chat_GUI.setVisible(true);
			message_tf.setEnabled(true); //채팅방에 입장했을 때 메세지 나오는 텍스트 필드를 활성화시킨다
			send_btn.setEnabled(true); //채팅방에 입장했을 때 전송 버튼을 활성화시킨다
			emoticon_btn.setEnabled(true);
			exit_btn.setEnabled(true);
			joinroom_btn.setEnabled(false); //채팅방에 입장했을 때 방만들기 버튼을 비활성화
			createroom_btn.setEnabled(false); //채팅방에 입장했을 때 방참여 버튼을 비활성화
			JOptionPane.showMessageDialog(null, "채팅방에 입장했습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(protocol.equals("exitRoom"))
		{
			My_Room = "";
			Chat_GUI.setVisible(false);
			Chat_area.setText("");
			message_tf.setEnabled(false); //채팅방에 퇴장했을 때 메세지 나오는 텍스트 필드를 비활성화시킨다
			send_btn.setEnabled(false); //채팅방에 퇴장했을 때 전송 버튼을 비활성화시킨다
			emoticon_btn.setEnabled(false);
			exit_btn.setEnabled(false); //
			joinroom_btn.setEnabled(true); //채팅방에 퇴장했을 때 방만들기 버튼을 활성화
			createroom_btn.setEnabled(true); //채팅방에 퇴장했을 때 방참여 버튼을 활성화
			JOptionPane.showMessageDialog(null, "채팅방에서 퇴장했습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);

		}
		else if(protocol.equals("User_out"))
		{
			user_list.remove(Message);


		}
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
			con=DriverManager.getConnection("url", "ID", "비밀번호");
			stmt = con.createStatement();       //sql 전달
			stmt2 = con.createStatement();

			rs = stmt.executeQuery(         // 해당 칼럼 선택.
					"select ID,NAME, SEX, BIRTHDAY, EMAIL, PHONE"
					+ " from CH_USER"
					+ " where id='" +  id  + "'");   // 조건은 로그인 한 id와 같은 id를 가진 데이터

			rs2 = stmt2.executeQuery("select * from CH_USER where id = '" + id + "'"); 

			if(rs.next()) {
				String id_ = rs.getString("ID");
				String name = rs.getString("NAME");
				String sex=rs.getString("SEX");
				String birth=rs.getString("BIRTHDAY");
				String email=rs.getString("EMAIL");
				String phone=rs.getString("PHONE");

				proID_lb.setText(id_);
				proName_lb.setText(name);
				proSex_lb.setText(sex);
				proBirth_lb.setText(birth);
				proEmail_lb.setText(email);
				proPhone_lb.setText(phone);

				if(rs2.next()) {   
					String pro = rs2.getString("image_path");
					proIMG_lb.setIcon(ResizeImage(pro,proIMG_lb));
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
	//pro 불러오기
	public static String checkIDImage(String id) { // id에 해당하는 프로필사진 찾기
		id = id;
		String str=null;

		Connection con = null;  //view 도 역시 list와 같이 데이터를 불러와야하기 때문에 ResultSet을 준비 한다.
		Statement stmt = null;
		ResultSet rs = null;


		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
			con=DriverManager.getConnection( "url", "ID", "비밀번호"); 
			stmt = con.createStatement(); //db접속

			String sql = "select * from ch_user where id='" + id + "'";
			rs = stmt.executeQuery(sql);// 입력id와 테이블에 저장된 id와 비교
			//sql = "select * from (select * from joindb where id='" + id + "')";
			while (rs.next() == true) {     
				str =rs.getString(10);       // 다음값의  같으면 로그인 성공
			}      
		} catch (Exception ee) {
			System.out.println("오류");
			ee.printStackTrace();
		}
		return str; 
	}


	static void send_message(String str) //서버에게 메세지를 보내는 부분
	{

		try {
			dos.writeUTF(str);
		} catch (IOException e) { //에러처리부분

		}

	}
	public static void main(String[] args) {

		new client(); //익명으로 Client객체 발생

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//login_btn = 로그인 버튼(접속버튼)

		if(e.getSource() == login_btn)
		{
			System.out.println("로그인 버튼 클릭");

			if(id_tf.getText().length() == 0) //ID 텍스트 필트가 비어있으면
			{
				JOptionPane.showMessageDialog(null,"ID를 입력해주세요","알림",JOptionPane.ERROR_MESSAGE);
				id_tf.requestFocus();
			}
			if(Password_tf.getText().length() == 0) //비밀번호 텍스트 필트가 비어있으면
			{
				JOptionPane.showMessageDialog(null,"비밀번호를 입력해주세요","알림",JOptionPane.ERROR_MESSAGE);
				Password_tf.requestFocus();
			}

			else if(!idcheck){

				//DB에 있는 아이디인지 체크
				id = id_tf.getText();

				try{
					mDB.dbOpen();
					//JOptionPane.showMessageDialog(null, "DB연결");
					strSQL = "select id from CH_USER where id = '";
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
					id_tf.requestFocus();
					idcheck = false; 
				}
				else{//db에 아이디가 있으면
					idcheck = false;
					if(!pwcheck){

						//DB에 있는 패스워드인지 체크

						try{
							mDB.dbOpen();
							//JOptionPane.showMessageDialog(null, "DB연결");
							String pw = Password_tf.getText().trim();
							strSQL = "select pw from CH_USER where pw = '";
							strSQL += pw + "'";
							System.out.println(pw);
							System.out.println(strSQL);
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
							Password_tf.requestFocus();
							pwcheck = false; 
						}
						else{//db에 패스워드가 있으면
							pwcheck = false;
							Network(); //네트워크 실행


						}
					} //password DM와 비교코딩 끝

				} //id가 있을 때 if문 끝


			} //id,password DB와 비교코딩 끝

			UserID_lb.setText(id);

		} // login_btn 버튼 if 끝

		else if(e.getSource()== join_btn)
		{
			System.out.println("회원가입 버튼 클릭");
			new join();

		}
		else if(e.getSource()==IDFind_btn)
		{
			new id_find();
		}
		else if(e.getSource()==PWFind_btn)
		{
			new pw_find();
		}
		else if(e.getSource()== notesend_btn)

		{
			System.out.println("쪽지 보내기 버튼 클릭");
			String user = (String)User_list.getSelectedValue();

			String note = JOptionPane.showInputDialog("보낼메세지");

			if(note != null)
			{
				send_message("Note/" + user + "/" + note);
				//ex) Note/User2/나는 User1이야
			}
			System.out.println("받는 사람 : " + user + "|보낼 내용 : " + note);

		}
		else if(e.getSource()== joinroom_btn)
		{
			String JoinRoom = (String)Room_list.getSelectedValue();

			send_message("JoinRoom/" + JoinRoom);

			System.out.println("방 참여 버튼 클릭");

		}

		else if(e.getSource()== createroom_btn)
		{
			String roomname = JOptionPane.showInputDialog("방 이름");
			if(roomname != null)
			{
				send_message("CreateRoom/" + roomname);
			}
			System.out.println("방 만들기 버튼 클릭");

		}

		else if(e.getSource()== send_btn)
		{
			send_message("Chatting/" + My_Room + "/" + message_tf.getText().trim());
			// Chatting + 방이름 + 내용
			System.out.println(id);
			InsertDB(id);
			message_tf.setText("");
			message_tf.requestFocus();


			System.out.println("채팅 전송 버튼 클릭");
		}
		else if(e.getSource()== emoticon_btn){
			new emoticon();
			System.out.println("이모티콘 버튼 클릭");
		}
		else if(e.getSource()== exit_btn){
			int result;
			result = JOptionPane.showConfirmDialog(null, "퇴장하시겠습니까?","알림",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if(result == 0) { //로그아웃 한다면
				System.out.println("나가기 버튼 클릭" + My_Room);
				send_message("exitRoom/" + My_Room);
			}
			else if(result == 1) {
				System.out.println("취소 버튼 클릭");
			}

		}
		else if(e.getSource()==logout_btn){
			int result;
			result = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?","Logout",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if(result == 0) { //로그아웃 한다면

				try {
					id_tf.setText(""); //아이디 텍스트 필드 비워주기
					Password_tf.setText(""); //비밀번호 텍스트 필드 비워주기
					id_tf.grabFocus(); //아이디 필드로 포커스 해주기
					user_list.remove(id);
					room_list.removeAllElements();
					user_list.removeAllElements();
					//Logout(id);
					os.close();
					is.close();
					dos.close();
					dis.close();
					socket.close();
					this.setVisible(false); //메인 프레임 안보이게
					JOptionPane.showMessageDialog(null, "로그아웃 하였습니다", "알림", JOptionPane.INFORMATION_MESSAGE);
					Login_GUI.setVisible(true); // 로그인 프레임 보이게

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if(result == 1) {
				System.out.println("취소 버튼 클릭");
			}

			STATE="Logout";
			LoginDB(id);
		} // 로그아웃 버튼 if 끝
		else if (e.getSource()==pro_btn){
			new pro_change();
		}
		else if (e.getSource()==UserPanel_btn){ // 전체사용자 패널 버튼 클릭시
			User_panel.setVisible(true);
			Room_panel.setVisible(false);
			pro_panel.setVisible(false);

		}
		else if (e.getSource()==RoomPanel_btn){ // 채팅방 패널 버튼 클릭시
			User_panel.setVisible(false);
			Room_panel.setVisible(true);
			pro_panel.setVisible(false);

		}
		else if (e.getSource()==ProPanel_btn ){ //프로필 패널 버튼 클릭시
			User_panel.setVisible(false);
			Room_panel.setVisible(false);
			pro_panel.setVisible(true);
		}
		else if (e.getSource()==proUP_btn){
			view(id);

		}
		else if (e.getSource()==colorMe_btn){ //내 채팅색 바꾸기 버튼

			JColorChooser chooser = new JColorChooser();

			my_color = chooser.showDialog(null,"Color",Color.YELLOW);

			//			StyleConstants.setForeground(smi, color_.color);

		}
		else if (e.getSource()==colorOther_btn){ //내 채팅색 바꾸기 버튼

			JColorChooser chooser = new JColorChooser();

			you_color = chooser.showDialog(null,"Color",Color.YELLOW);

			//			StyleConstants.setForeground(smi, color_.color);

		}
		else if (e.getSource()==proview_btn){
			id_other = (String)User_list.getSelectedValue();
			if(id_other==null){

			}
			else{
				new view_other();
			}

		}
		else if (e.getSource()==clear_btn){
			Chat_area.setText("");
		}

	}

	public void LoginDB(String id){ // login기록 저장
		System.out.println("로그인기록 저장");
		Connection conn= null;
		Statement stmt = null;
		long curtime = System.currentTimeMillis();
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
		String time = timeFormat.format(new Date(curtime));
		try {

			Class.forName(DRIVER);
			conn = (Connection) DriverManager.getConnection(URL, USER, PASS);
			stmt = (Statement) conn.createStatement();
			stmt.executeUpdate("insert into CHATLOGIN" + "(ID,STATE,TIME) values('"+id+"','"+STATE+"','"+time+"')");

			System.out.print("insert into CHATLOGIN" + "(ID,STATE,TIME) values('"+id+"','"+STATE+"','"+time+"')");

		} catch (ClassNotFoundException cnfe) {
			System.out.println("해당 클래스를 찾을수 없습니다." + cnfe.getMessage());
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		} finally {
			try {
				stmt.close();
			} catch (Exception ignored) {
			}
			try {
				conn.close();
			} catch (Exception ignored) {

			}
		}
	}


	public void InsertDB(String id){ // 채팅db저장

		Connection conn= null;
		Statement stmt = null;
		String msg = message_tf.getText().trim();
		System.out.println(msg);
		long curtime = System.currentTimeMillis();
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
		String time = timeFormat.format(new Date(curtime));
		try {

			Class.forName(DRIVER);
			conn = (Connection) DriverManager.getConnection(URL, USER, PASS);
			stmt = (Statement) conn.createStatement();
			stmt.executeUpdate("insert into CHATSAVE" + "(ROOMNAME,Id,Text,Time) values('"+My_Room+"'" + ",'"+id+"','"+msg+"','"+time+"')");

			System.out.println("insert into CHATSAVE" + "(ROOMNAME,Id,Text,Time) values('"+My_Room+"'" + ",'"+id+"','"+msg+"','"+time+"')");
		} catch (ClassNotFoundException cnfe) {
			System.out.println("해당 클래스를 찾을수 없습니다." + cnfe.getMessage());
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		} finally {
			try {
				stmt.close();
			} catch (Exception ignored) {
			}
			try {
				conn.close();
			} catch (Exception ignored) {

			}
		}

	} 

	private void TO_DATE(long curtime, String time) {
		// TODO Auto-generated method stub

	}


	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getSource() == Password_tf) {
			if(e.getKeyCode() == 10) {
				System.out.println("확인");
				login_btn.doClick();
			}
		}    
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {

		if(e.getKeyCode() == 10) //엔터키 쳤을 때 이벤트
		{
			send_message("Chatting/" + My_Room + "/" + message_tf.getText().trim());
			// Chatting + 방이름 + 내용
			System.out.println(id);
			InsertDB(id);
			message_tf.setText("");
			message_tf.requestFocus();
		}
		// TODO Auto-generated method stub

	}


	public static ImageIcon ResizeImage(String ImagePath, JLabel a)//지정된 라벨에 크기에 맞게 사진을 넣을때
	{
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(a.getWidth(), a.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}
	public static ImageIcon ResizeImage(String ImagePath,int a,int b)//직접 사진 크기 입력할 떄
	{
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(a, b, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		dispose();
		System.exit(0);

	}


	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}
}