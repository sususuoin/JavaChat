package Server;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Server extends JFrame implements ActionListener{
	//자동 import 단축키 ctrl + shift + o

	private JPanel contentPane;
	private JTextField port_tf; // 텍스트 필드는 포트 번호를 입력받기 때문에 port_tf로 설정 sever GUI 소스코드에서 필요한 변수들을 붙여놓음
	private JTextArea textArea = new JTextArea();
	private JButton start_btn = new JButton("서버 실행");
	private JButton stop_btn = new JButton("서버 중지");

	//Network 자원

	private JLabel es_mainimg_lb = new JLabel("");
	private JLabel ES_img_lb1 = new JLabel("");
	private JButton super_btn = new JButton("관리자 모드");      
	//GUI 자원

	private ServerSocket server_socket;
	private Socket socket;
	private int port;
	private Vector user_vc = new Vector();
	private Vector room_vc = new Vector();

	private StringTokenizer st;



	Server() //생성자 생성
	{
		super("Server");
		init(); //화면 생성 메소드, 화면을 구성할 때 init()이라는 메소드 안에 구성
		start(); //리스너 생성 메소드
	}

	private void start() //start_btn과 stop_btn에 대한 ActionListener 설정
	{
		start_btn.setBackground(SystemColor.menu);
		start_btn.addActionListener(this);
		stop_btn.setBackground(SystemColor.menu);
		stop_btn.addActionListener(this);
		super_btn.addActionListener(this);
	}

	private void init() //화면 구성 init()메소드 생성,sever GUI 소스코드에서 화면을 구성하는 모든 소스코드 복붙
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 405, 482);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(251, 220, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 62, 359, 252);
		contentPane.add(scrollPane);


		scrollPane.setViewportView(textArea);
		textArea.setEditable(false); //화면을 수정할 수 없도록 만들어줌

		JLabel port_lb = new JLabel("포트 번호");
		port_lb.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		port_lb.setBounds(30, 330, 74, 18);
		contentPane.add(port_lb);

		port_tf = new JTextField("9000");
		port_tf.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		port_tf.setBounds(102, 327, 271, 24);
		contentPane.add(port_tf);
		port_tf .setColumns(10);
		port_tf .setEditable(false); //화면을 수정할 수 없도록 만들어줌


		start_btn.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		start_btn.setBounds(14, 400, 176, 27);
		contentPane.add(start_btn);


		stop_btn.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		stop_btn.setBounds(197, 400, 176, 27);
		contentPane.add(stop_btn);
		stop_btn.setEnabled(false); // 처음에 서버중지버튼 비활성화 상태


		super_btn.setBackground(SystemColor.menu);
		super_btn.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		super_btn.setBounds(14, 365, 360, 25);
		contentPane.add(super_btn);

		JLabel talk_lb = new JLabel("Talk");
		talk_lb.setForeground(Color.BLACK);
		talk_lb.setHorizontalAlignment(SwingConstants.CENTER);
		talk_lb.setFont(new Font("Adobe Fan Heiti Std B", Font.BOLD | Font.ITALIC, 27));
		talk_lb.setBounds(206, 20, 89, 35);
		contentPane.add(talk_lb);

		ImageIcon image1 = new ImageIcon("C:\\Users\\user\\Desktop\\자바사진\\estherBUNNY.png");  //이미지 경로
		ES_img_lb1 = new JLabel(image1,SwingConstants.CENTER);
		ES_img_lb1.setBounds(135, 3, 95, 50);
		contentPane.add(ES_img_lb1);

		ImageIcon image2 = new ImageIcon("C:\\Users\\user\\Desktop\\자바사진\\SM_bunny1.png");  //이미지 경로
		es_mainimg_lb = new JLabel(image2,JLabel.CENTER);
		es_mainimg_lb.setBounds(90, -3, 50, 70);
		contentPane.add(es_mainimg_lb);



		this.setVisible(true); // true = 화면에 보이게   false = 화면에 보이지 않게
	}

	private void Server_start()
	{
		try {
			server_socket = new ServerSocket(9000); //9000번 포트 사용
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "이미 사용중인 포트", "알림", JOptionPane.ERROR_MESSAGE);
		}


		if(server_socket != null) //정상적으로 포트가 열렸을 경우
		{
			Connection();
		}


	}


	private void Connection()
	{


		//1가지의 스레드에서는 1가지의 일만 처리할 수 있다.

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() { //스레드를 처리할 일을 기재한다.


				while(true) {
					try {

						textArea.append("사용자 접속 대기 중...\n");
						socket = server_socket.accept(); //사용자 접속 무한 대기
						textArea.append("사용자 접속\n");

						UserInfo user = new UserInfo(socket);

						user.start(); //객체의 스레드 실행

					} catch (IOException e) {

						break;
					}
				} //while문 끝

			}
		});

		th.start();

	}

	public static void main(String[] args) {


		new Server(); //익명으로 객체 생성

	}

	@Override
	public void actionPerformed(ActionEvent e)  {

		if(e.getSource() == start_btn)
		{
			System.out.println("서버 스타트 버튼 클릭");

			Server_start(); //소켓 새엉 및 사용자 접속 대기

			start_btn.setEnabled(false); // 서버가 실행되면 서버 실행 버튼이 눌리지 않게 함
			port_tf.setEditable(false); // 서버가 실행되면 포트번호 넣는 텍스트 필드 수정 못하게 함
			stop_btn.setEnabled(true); //서버를 실행 했을 때만 서버 종료버튼을 활성화 시킴
		}
		else if(e.getSource() == stop_btn)
		{
			stop_btn.setEnabled(false); // 서버 종료 버튼을 누르면 서버 중지 버튼 비활성화
			start_btn.setEnabled(true); // 서버 종료버튼을 누르면 서버 실행버튼을 누를 수 있게 함
			port_tf.setEditable(true); // 서버 종료버튼을 누르면 포트번호 넣는 텍스트 필드를 수정 가능하게 함


			try {
				server_socket.close();
				user_vc.removeAllElements();
				room_vc.removeAllElements();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("서버 스탑 버튼 클릭");
		} //stop 액션 이벤트 끝
		else if(e.getSource()==super_btn)
		{
			System.out.println("관리자 모드 버튼 클릭");
			new Super_login();
		}


	} //액션 이벤트 끝

	class UserInfo extends Thread
	{
		private OutputStream os;
		private InputStream is;
		private DataOutputStream dos;
		private DataInputStream dis;

		private Socket user_socket;
		private String Nickname = "";

		private boolean RoomCh = true;

		UserInfo(Socket soc) //생성자 메소드
		{
			this.user_socket = soc;

			UserNetwork();

		}

		private void UserNetwork() //네트워크 자원 설정
		{
			try {
				is = user_socket.getInputStream(); //내부 클래스이기 때문에 그냥 socket으로 설정하게 되면 sever자체의 소켓으로 잘못인식될수있다.
				dis = new DataInputStream(is);

				os = user_socket.getOutputStream();
				dos = new DataOutputStream(os);

				Nickname = dis.readUTF(); //사용자의 닉네임을 받는다.
				textArea.append(Nickname+ ": 사용자 접속!\n");

				//기존 사용자들에게 새로운 사용자 알림
				System.out.println("현재 접속된 사용자 수 : " + user_vc.size());

				BroadCast("NewUser/" + Nickname); // 기존 사용자에게 자신을 알린다

				//자신에게 기존 사용자를 알림
				for(int i = 0; i < user_vc.size(); i++)
				{
					UserInfo u = (UserInfo)user_vc.elementAt(i);//형 변화 시켜준다

					send_Message("OldUser/" + u.Nickname);

				}

				//자신에게 기존 방 목록을 불러오는 부분   
				for(int i=0; i < room_vc.size(); i++)
				{
					RoomInfo r = (RoomInfo)room_vc.elementAt(i);

					send_Message("OldRoom/" + r.Room_name);
				}

				send_Message("Room_list_update/ ");


				user_vc.add(this); //사용자에게 알린 후 Vector에 자신을 추가

				BroadCast("user_list_update/ ");

			}
			catch(IOException e) {

				JOptionPane.showMessageDialog(null, "Stream 설정 에러", "알림", JOptionPane.ERROR_MESSAGE);

			}


		}

		public void run() //Thread에서 처리할 내용
		{
			while(true)
			{
				try {
					String msg = dis.readUTF();
					textArea.append(Nickname+":사용자로부터 들어온 메세지 : " + msg + "\n");
					InMessage(msg);
				} catch (IOException e) {
					textArea.append(Nickname + " : 사용자 접속 끊어짐\n");

					try {
						dos.close();
						dis.close();
						user_socket.close();
						user_vc.remove(this);
						BroadCast("User_out/" + Nickname);
						BroadCast("user_list_update/ ");
					}
					catch(IOException e1) {}
					break;

				} //메세지 수신
			}


		} //run 메소드 끝

		private void InMessage(String str)//클라이언트로부터 들어오는 메세지 처리
		{
			st = new StringTokenizer(str,"/");

			String protocol = st.nextToken();
			String message = st.nextToken();

			System.out.println("프로토콜 : " + protocol);
			System.out.println("메세지 : " + message);

			if(protocol.equals("Note"))
			{
				// protocol = Note
				// message = user
				// note = 받는 내용


				String note = st.nextToken();

				System.out.println("받는사람 : " + message);
				System.out.println("보낼 내용 : " + note);

				//벡터에서 해당 사용자를 찾아서 메세지 전송
				for(int i = 0; i < user_vc.size(); i++)
				{
					UserInfo u = (UserInfo)user_vc.elementAt(i);

					if(u.Nickname.equals(message))
					{
						u.send_Message("Note/" + Nickname + "/" + note);
						//Note/User1/~~~~~~
					}
				}
			} //if문 끝
			else if(protocol.equals("CreateRoom"))
			{
				// 1.현재 같은 방이 존재 하는지 확인한다.

				for(int i=0; i < room_vc.size(); i++)
				{
					RoomInfo r = (RoomInfo)room_vc.elementAt(i);

					if(r.Room_name.equals(message)) //만들고자 하는 방이 이미 존재할 때
					{
						send_Message("CreateRoomFail/ok");
						RoomCh = false;
						break;
					}
				} //for문 끝

				if(RoomCh) //방을 만들 수 있을 때
				{
					RoomInfo new_room = new RoomInfo(message, this);
					room_vc.add(new_room); //전체 방 벡터에 추가

					send_Message("CreateRoom/" + message);

					BroadCast("New_Room/" + message); //방이 만들어졌을 때 브로드캐스트로 모두에게 알려준다
				}

				RoomCh = true;
			} //else if문 끝

			else if(protocol.equals("Chatting"))
			{
				String msg = st.nextToken();

				for(int i = 0; i < room_vc.size(); i++)
				{
					RoomInfo r = (RoomInfo)room_vc.elementAt(i);

					if(r.Room_name.equals(message)) //해당 방을 찾았을 때
					{
						r.BroadCast_Room("Chatting/" + Nickname + "/" + msg);
					}
				}
			} //else if 끝

			else if(protocol.equals("JoinRoom"))
			{
				for(int i = 0; i < room_vc.size(); i++)
				{
					RoomInfo r = (RoomInfo)room_vc.elementAt(i);
					if(r.Room_name.equals(message))
					{
						//새로운 사용자를 알린다
						r.BroadCast_Room("Chatting/알림/*******" + Nickname + "님이 입장하셨습니다*******");

						//사용자 추가
						r.Add_User(this);
						send_Message("JoinRoom/" + message);

					}
				}
			} // joinRoom if문 끝
			else if (protocol.equals("exitRoom")){
				for(int i = 0; i < room_vc.size(); i++)
				{
					RoomInfo r = (RoomInfo)room_vc.elementAt(i);
					if(r.Room_name.equals(message))
					{
						r.remove_User(this);
						send_Message("exitRoom/" + message);
						//퇴장한 사용자를 알린다
						r.BroadCast_Room("Chatting/알림/*******" + Nickname + "님이 퇴장하셨습니다*******");

						//사용자 삭제
					}
				}
			}
		}

		private void BroadCast(String str) //전체 사용자에게 메세지 보내는 부분
		{
			for(int i=0; i < user_vc.size(); i++) //현재 접속된 사용자에게 새로운 사용자 알림
			{
				UserInfo u = (UserInfo)user_vc.elementAt(i);

				u.send_Message(str); //NewUser는 프로토콜, Nickname 부분은 id이다.   
			}
		}

		private void send_Message(String str) // 문자열을 받아서 전송
		{
			try {
				dos.writeUTF(str); //dos는 서버에서 나가는 아웃풋 스트림이다.
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	} //UserInfo class 끝

	class RoomInfo
	{
		private String Room_name;
		private Vector Room_user_vc = new Vector();

		RoomInfo(String str, UserInfo u)
		{
			this.Room_name = str;
			this.Room_user_vc.add(u);
		}

		public void BroadCast_Room(String str) //현재 방의 모든 사람에게 알린다
		{
			for (int i = 0; i < Room_user_vc.size(); i++)
			{
				UserInfo u = (UserInfo)Room_user_vc.elementAt(i);

				u.send_Message(str);
			}
		}

		private void Add_User(UserInfo u)
		{
			this.Room_user_vc.add(u);

		}
		public int remove_User(UserInfo u){
			this.Room_user_vc.remove(u);
			return Room_user_vc.size();
		}
	}
}
