package Server;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;



public class login_DB extends JFrame implements KeyListener{
   
   private static final long serialVersionUID = 1L;
    private JFrame frame = new JFrame();
    private JPanel panel_1;
//    ImageIcon icon;   

	private JTextField chattingDB_tf;
    private JTable table;    
    private JScrollPane scrollPane;        // 테이블 스크롤바 자동으로 생성되게 하기
    private JComboBox chattingDB_combobox = new JComboBox();
    private JButton ok_btn = new JButton("검색");
	private String id = "";
	private String room = "";
	private JButton btnNewButton = new JButton("전체보기");

    private String driver = "oracle.jdbc.driver.OracleDriver";        
    private String url = "URL";        // @호스트 IP : 포트 : SID
    private String colNames[] = {"아이디","상태","시간"};  // 테이블 컬럼 값들
    private DefaultTableModel model = new DefaultTableModel(colNames, 0); //  테이블 데이터 모델 객체 생성
            
    private Connection con = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;   // 리턴받아 사용할 객체 생성 ( select에서 보여줄 때 필요 )
    
    public login_DB() {
    	
       panel_1 = new JPanel() {
        };
        
       frame.setTitle("로그인관리 ");
       frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       frame.setBounds(270, 120, 756, 467);
       panel_1.setBackground(new Color(251, 220, 235));
    
//       
//       setBackground(new Color(251, 220, 235));
       frame.setContentPane(panel_1);
      panel_1.setLayout(null); // 레이아웃 배치관리자 삭제
        table = new JTable(model);  // 테이블에 모델객체 삽입
        table.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
//        table.addMouseListener(new JTableMouseListener());        // 테이블에 마우스리스너 연결
        scrollPane = new JScrollPane(table);            // 테이블에 스크롤 생기게 하기
        scrollPane.setLocation(14, 99);
        scrollPane.setSize(722, 294);
        
        panel_1.add(scrollPane);
      
        
        JLabel lblNewLabel = new JLabel("로그인 관리");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBackground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 28));
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setBounds(295, 27, 165, 33);
        panel_1.add(lblNewLabel);
        
        chattingDB_tf = new JTextField();
		chattingDB_tf.setBounds(125, 400, 450, 23);
		panel_1.add(chattingDB_tf);
		chattingDB_tf.setColumns(10);
		chattingDB_tf.addKeyListener(this);

		
		ok_btn.setBackground(SystemColor.menu);
		ok_btn.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		ok_btn.setBounds(585, 400, 60, 23);
		panel_1.add(ok_btn);
		ok_btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if(e.getSource() == ok_btn)
	        	{
	        		if((String)chattingDB_combobox.getSelectedItem()=="ID")
	        		{
	        			model.setRowCount(0); // 전체 테이블 화면 지워줌
	        			ID_select(id);
	        		}
	        		else if((String)chattingDB_combobox.getSelectedItem()=="방이름"){
	        			model.setRowCount(0); // 전체 테이블 화면 지워줌
//	        			room_select(room);
	        		}
	        	}
				
			}
		});

		

		chattingDB_combobox.setBackground(SystemColor.menu);
		chattingDB_combobox.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		chattingDB_combobox.setModel(new DefaultComboBoxModel(new String[] {"ID"}));
		chattingDB_combobox.setBounds(13, 400, 97, 23);
		panel_1.add(chattingDB_combobox);
		btnNewButton.setBackground(SystemColor.menu);
		
		btnNewButton.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		btnNewButton.setBounds(656, 400, 80, 23);
		panel_1.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnNewButton){
				model.setRowCount(0);
				select();
			}
			}
		});
        
        frame.setVisible(true); //윈도우창에 보이기 지정
       frame.setResizable(false); //윈도우창 크기 고정
       frame.setLocationRelativeTo(null);
        select();

    }

    private void select(){        // 테이블에 보이기 위해 검색
        
        String query = "select * from CHATLOGIN ORDER BY time DESC";
        
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(url, "아이디", "비밀번호");
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery(); // 리턴받아와서 데이터를 사용할 객체생성
            
            while(rs.next()){            // 각각 값을 가져와서 테이블값들을 추가
            	
            	model.addRow(new Object[]{rs.getString("ID"),rs.getString("STATE"),rs.getString("TIME")});
            	System.out.println("G");
                
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally{
            try {
                rs.close(); 
                pstmt.close(); 
                con.close();   // 객체 생성한 반대 순으로 사용한 객체는 닫아준다.
            } catch (Exception e) {}
        }
    }
      
    


private void ID_select(String id){        // ID로 로그인기록  검색하기
	id = chattingDB_tf.getText();
        String query = "select * from CHATLOGIN where ID='"+id+"' ";
        
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(url, "아이디", "비밀번호");
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery(); // 리턴받아와서 데이터를 사용할 객체생성
            
            while(rs.next()){            // 각각 값을 가져와서 테이블값들을 추가
            	
            	model.addRow(new Object[]{rs.getString("ID"),rs.getString("STATE"),rs.getString("TIME")});
            	System.out.println("id="+id);
                
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally{
            try {
                rs.close(); 
                pstmt.close(); 
                con.close();   // 객체 생성한 반대 순으로 사용한 객체는 닫아준다.
            } catch (Exception e) {}
        }
    }
    

    
   
    
    
public static void main(String[] args) {
    
new login_DB();
}

@Override
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void keyPressed(KeyEvent e) {
	if(e.getSource() == chattingDB_tf) {
	       if(e.getKeyCode() == 10) {
	    	   System.out.println("확인");
	    	   ok_btn.doClick();
	       }
	}
	
}

@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
}