package Server;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;



public class member_DB extends JFrame{
   
   private static final long serialVersionUID = 1L;
    private JFrame frame = new JFrame();
    private JPanel panel_1;
    ImageIcon icon;
    private JButton jBtnAddRow = null;    // 테이블 한줄 추가 버튼
    private JButton jBtnSaveRow = null;    // 테이블 한줄 저장 버튼
    private JButton jBtnEditRow = null;    // 테이블 한줄 저장 버튼
    private JButton jBtnDelRow = null;        // 테이블 한줄 삭제 벅튼
    
    
    private JTable table;    
    private JScrollPane scrollPane;        // 테이블 스크롤바 자동으로 생성되게 하기

    private String driver = "oracle.jdbc.driver.OracleDriver";        
    private String url = "URL";        // @호스트 IP : 포트 : SID
    private String colNames[] = {"아이디","비밀번호","이름","생년월일","연락처","이메일","주소","성별"};  // 테이블 컬럼 값들
    private DefaultTableModel model = new DefaultTableModel(colNames, 0); //  테이블 데이터 모델 객체 생성
            
    private Connection con = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;   // 리턴받아 사용할 객체 생성 ( select에서 보여줄 때 필요 )
    
    public member_DB() {
       panel_1 = new JPanel() {
          
        };
        
       frame.setTitle("회원관리 ");
       frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       frame.setBounds(270, 120, 756, 442);
       panel_1.setBackground(new Color(251, 220, 235));
    
       
       setBackground(new Color(255, 250, 240));
       frame.setContentPane(panel_1);
      panel_1.setLayout(null); // 레이아웃 배치관리자 삭제
        table = new JTable(model);  // 테이블에 모델객체 삽입
        table.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
        table.addMouseListener(new JTableMouseListener());        // 테이블에 마우스리스너 연결
        scrollPane = new JScrollPane(table);            // 테이블에 스크롤 생기게 하기
        scrollPane.setLocation(14, 99);
        scrollPane.setSize(722, 256);
        
        panel_1.add(scrollPane);
      
        
        JLabel lblNewLabel = new JLabel("\uD68C\uC6D0 \uAD00\uB9AC");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBackground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 28));
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setBounds(295, 27, 165, 33);
        panel_1.add(lblNewLabel);
        
    // 테이블 새로 한줄 추가하는 부분
        jBtnAddRow = new JButton();
        jBtnAddRow.setBackground(SystemColor.menu);
        jBtnAddRow.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        jBtnAddRow.setForeground(SystemColor.desktop);
        jBtnAddRow.setText("추가");
        jBtnAddRow.addActionListener(new ActionListener() {   
           
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getActionCommand());        // 선택된 버튼의 텍스트값 출력
                DefaultTableModel model2 = (DefaultTableModel)table.getModel();
                model2.addRow(new String[]{"","","","","","","","","",""});            // 새테이블의 초기값
            }
        });
        jBtnAddRow.setBounds(63,375,120, 25);
        jBtnAddRow.setText("추가");
        panel_1.add(jBtnAddRow);
        

    // 테이블 새로 저장하는 부분
        jBtnSaveRow = new JButton();
        jBtnSaveRow.setForeground(SystemColor.desktop);
        jBtnSaveRow.setBackground(SystemColor.menu);
        jBtnSaveRow.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        jBtnSaveRow.setText("저장");
        jBtnSaveRow.addActionListener(new ActionListener() { // 저장 버튼을 누르면
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getActionCommand());        // 선택된 버튼의 텍스트값 출력
                DefaultTableModel model2 = (DefaultTableModel)table.getModel();
                int row = table.getSelectedRow();
                if(row<0) return;     // 선택이 안된 상태면 -1리턴
                String query = "insert into CH_USER(id,pw,name,birthday,phone,email,address_2,sex)" 
                      + "values (?,?,?,?,?,?,?,?)"; 

                try{
                    Class.forName(driver);  // 드라이버 로딩
                    con = DriverManager.getConnection("URL","아이디", "비밀번호"); // DB2 연결
                    pstmt = con.prepareStatement(query);   
                    
                    // 물음표가 7개 이므로 7개 각각 입력해줘야한다.
                    pstmt.setString(1, (String) model2.getValueAt(row, 0));
                    pstmt.setString(2, (String) model2.getValueAt(row, 1));
                    pstmt.setString(3, (String) model2.getValueAt(row, 2));
                    pstmt.setString(4, (String) model2.getValueAt(row, 3));
                    pstmt.setString(5, (String) model2.getValueAt(row, 4));
                    pstmt.setString(6, (String) model2.getValueAt(row, 5));
                    pstmt.setString(7, (String) model2.getValueAt(row, 6));
                    pstmt.setString(8, (String) model2.getValueAt(row, 7));
                    

                    pstmt.executeUpdate();
                    //pstmt.executeUpdate(); create insert update delete 
                    //pstmt.executeQuery(); select 
                }catch(Exception eeee){
                    System.out.println(eeee.getMessage());
                }finally{
                    try {
                        pstmt.close();
                        con.close();
                    } catch (Exception e2) {}
                }
                model2.setRowCount(0);         // 전체 테이블 화면을 지워줌
                select();          // 저장 후 다시 전체 값들을 받아옴.
            }
        });
        jBtnSaveRow.setBounds(239,375,120, 25);
        jBtnSaveRow.setText("저장");
        panel_1.add(jBtnSaveRow);
        
        
        // 선택된 테이블 한줄 수정하는 부분
        jBtnEditRow = new JButton();
        jBtnEditRow.setForeground(SystemColor.desktop);
        jBtnEditRow.setBackground(SystemColor.menu);
        jBtnEditRow.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        jBtnEditRow.setText("수정");
        jBtnEditRow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {        
                        
                System.out.println(e.getActionCommand());   // 선택된 버튼의 텍스트값 출력
                DefaultTableModel model2 = (DefaultTableModel)table.getModel();
                int row = table.getSelectedRow();
                if(row<0) return;     // 선택이 안된 상태면 -1리턴

                String query = "UPDATE CH_USER SET id=?, pw=?, name=?, birthday=?, phone=?, email=?, address_2=?, sex =? "
                                +"where id=?";
                
                try{
                    Class.forName(driver);  // 드라이버 로딩
                    con = DriverManager.getConnection("URL", "아이디", "비밀번호"); // DB 연결
                    pstmt = con.prepareStatement(query);   
                    
                    // 물음표가 8개 이므로 8개 각각 입력해줘야한다.
                    pstmt.setString(1, (String) model2.getValueAt(row, 0));
                    pstmt.setString(2, (String) model2.getValueAt(row, 1));
                    pstmt.setString(3, (String) model2.getValueAt(row, 2));
                    pstmt.setString(4, (String) model2.getValueAt(row, 3));
                    pstmt.setString(5, (String) model2.getValueAt(row, 4));
                    pstmt.setString(6, (String) model2.getValueAt(row, 5));
                    pstmt.setString(7, (String) model2.getValueAt(row, 6));
                    pstmt.setString(8, (String) model2.getValueAt(row, 7));
                    pstmt.setString(9, (String) model2.getValueAt(row, 0));

                    pstmt.executeUpdate();
                    //pstmt.executeUpdate(); create insert update delete 
                    //pstmt.executeQuery(); select 
                }catch(Exception eeee){
                    System.out.println(eeee.getMessage());
                }finally{
                    try {
                        pstmt.close();
                        con.close();
                    } catch (Exception e2) {}
                }
                model2.setRowCount(0);         // 전체 테이블 화면을 지워줌
                select();          // 수정 후다시 전체 값들을 받아옴.
            }
        });
        jBtnEditRow.setBounds(415,375,120, 25);
        jBtnEditRow.setText("수정");
        panel_1.add(jBtnEditRow);
        
                    
                // 선택된 테이블 한줄 삭제하는 부분
                    jBtnDelRow = new JButton();
                    jBtnDelRow.setForeground(SystemColor.desktop);
                    jBtnDelRow.setBackground(SystemColor.menu);
                    jBtnDelRow.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
                    jBtnDelRow.setText("삭제");
                    jBtnDelRow.addActionListener(new ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            System.out.println(e.getActionCommand());        // 선택된 버튼의 텍스트값 출력
                            DefaultTableModel model2 = (DefaultTableModel)table.getModel();
                            int row = table.getSelectedRow();
                            if(row<0) return; // 선택이 안된 상태면 -1리턴
                            String query = "delete from CH_USER where id= ?";

                            try{
                                Class.forName(driver);  // 드라이버 로딩
                                con = DriverManager.getConnection("URL", "아이디", "비밀번호"); // DB 연결
                                pstmt = con.prepareStatement(query);   
                                
                                pstmt.setString(1, (String) model2.getValueAt(row, 0));
                                int cnt = pstmt.executeUpdate();
                                //pstmt.executeUpdate(); create insert update delete 
                                //pstmt.executeQuery(); select 
                            }catch(Exception eeee){
                                System.out.println(eeee.getMessage());
                            }
                            finally{
                                try {
                                    pstmt.close();con.close();
                                } catch (Exception e2) {}
                            }
                            model2.removeRow(row);    // 테이블 상의 한줄 삭제
                        }
                    });
                    jBtnDelRow.setBounds(new Rectangle(591, 375, 120, 25));
                    jBtnDelRow.setText("삭제");
                    panel_1.add(jBtnDelRow);
        
        frame.setVisible(true); //윈도우창에 보이기 지정
       frame.setResizable(false); //윈도우창 크기 고정
       frame.setLocationRelativeTo(null);
        select();

    }

    private class JTableMouseListener implements MouseListener{    // 마우스로 눌려진값확인하기
        public void mouseClicked(java.awt.event.MouseEvent e) {    // 선택된 위치의 값을 출력
            
            JTable jtable = (JTable)e.getSource();
            int row = jtable.getSelectedRow();                // 선택된 테이블의 행값
            int col = jtable.getSelectedColumn();         // 선택된 테이블의 열값
        
            System.out.println(model.getValueAt(row, col));   // 선택된 위치의 값을 얻어내서 출력
                
        }
        public void mouseEntered(java.awt.event.MouseEvent e) {
        }
        public void mouseExited(java.awt.event.MouseEvent e) {    
        }
        public void mousePressed(java.awt.event.MouseEvent e) {
        }
        public void mouseReleased(java.awt.event.MouseEvent e) {
        }
    }
            
    private void select(){        // 테이블에 보이기 위해 검색
        
        String query = "select * from CH_USER";
        
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(url, "아이디", "비밀번호");
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery(); // 리턴받아와서 데이터를 사용할 객체생성
            
            while(rs.next()){            // 각각 값을 가져와서 테이블값들을 추가
            	
            	model.addRow(new Object[]{rs.getString("id"),rs.getString("pw"),rs.getString("name"),rs.getString("birthday"),rs.getString("phone"),rs.getString("email"),rs.getString("address_2"),
                                                        rs.getString("sex")});
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
    
    
    
    
   
    
    
public static void main(String[] args) {
    
new member_DB();
}
   
}