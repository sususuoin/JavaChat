package Client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class Chat_init extends JFrame {

	private JPanel Chat_Pane;
	private JPanel Room_panel = new JPanel(); // 전체 방

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chat_init frame = new Chat_init();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Chat_init() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(490, 100, 354, 550);
		Chat_Pane = new JPanel();
		Chat_Pane.setBackground(new Color(251, 220, 235));
		Chat_Pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(Chat_Pane);
		Chat_Pane.setLayout(null);
		
		Room_panel.setBackground(Color.WHITE);
		Room_panel.setBounds(20, 71, 298, 361);
		Chat_Pane.add(Room_panel);
		Room_panel.setLayout(null);
		
		JButton Add_btn = new JButton("");
		Add_btn.setBackground(Color.WHITE);
		Add_btn.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\자바사진\\room+1.png"));
		Add_btn.setFocusPainted(false); // 포커스 표시 설정
		Add_btn.setBorderPainted(false); // 버튼 테두리 설정
		Add_btn.setContentAreaFilled(false); // 버튼 영역 배경 표시 설정

		Add_btn.setBounds(205, 285, 89, 70);
		Room_panel.add(Add_btn);
		
		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		textPane.setText("ㅋㅋ");
		textPane.setBounds(86, 10, 120, 326);
		Room_panel.add(textPane);
		
		JButton button = new JButton("");
		button.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\자바사진\\join3.png"));
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.setBackground(Color.WHITE);
		button.setBounds(205, 220, 89, 70);
		Room_panel.add(button);
		
		JButton UserPanel_btn = new JButton("");
		UserPanel_btn.setBackground(SystemColor.menu);
		UserPanel_btn.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\자바사진\\user2.png"));
		UserPanel_btn.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		UserPanel_btn.setBounds(0, 451, 114, 60);
		UserPanel_btn.setFocusPainted(false); // 포커스 표시 설정
		UserPanel_btn.setBorderPainted(false); // 버튼 테두리 설정
		Chat_Pane.add(UserPanel_btn);
		
		JButton RoomPanel_btn = new JButton("");
		RoomPanel_btn.setBackground(SystemColor.menu);
		RoomPanel_btn.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\자바사진\\message.png"));
		RoomPanel_btn.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		RoomPanel_btn.setFocusPainted(false); // 포커스 표시 설정
		RoomPanel_btn.setBorderPainted(false); // 버튼 테두리 설정
		RoomPanel_btn.setBounds(114, 451, 113, 60);
		Chat_Pane.add(RoomPanel_btn);
		
		JButton ProPanel_btn = new JButton("");
		ProPanel_btn.setBackground(SystemColor.menu);
		ProPanel_btn.setIcon(new ImageIcon("C:\\Users\\user\\Desktop\\자바사진\\set.png"));
		ProPanel_btn.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		ProPanel_btn.setFocusPainted(false); // 포커스 표시 설정
		ProPanel_btn.setBorderPainted(false); // 버튼 테두리 설정
		ProPanel_btn.setBounds(226, 451, 114, 60);
		Chat_Pane.add(ProPanel_btn);
		
	}
}
