package Client;

import java.awt.EventQueue;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class ZipSearch extends JFrame {

	private JPanel contentPane;
    private JTable table;
    private JComboBox comboBox;
    private JComboBox comboBox_1;
    private JComboBox comboBox_2;
   
    private Connection conn = null;
    private PreparedStatement pstmt = null;      
    private ResultSet rs = null;         
    private JScrollPane scrollPane;
    private JPanel panel;
    private JTextField tfDong;
   
    /**
     * Launch the application.
     */
    public static void main(String[] args) {    
           EventQueue.invokeLater(new Runnable() {
                   public void run() {
                          try {
                                  ZipSearch frame = new ZipSearch();
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
    public ZipSearch() {
          
           setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           setBounds(100, 100, 629, 515);
           contentPane = new JPanel();
           contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
           setContentPane(contentPane);
           contentPane.setLayout(null);
          
           panel = new JPanel();
           panel.setBorder(new TitledBorder(null, "???????????? ??????", TitledBorder.LEADING, TitledBorder.TOP, null, null));
           panel.setBounds(6, 10, 594, 70);
           contentPane.add(panel);
           panel.setLayout(null);
          
           scrollPane = new JScrollPane();
           scrollPane.setBounds(6, 105, 588, 361);
           contentPane.add(scrollPane);
          
           table = new JTable();
           table.setModel(new DefaultTableModel(
                   new Object[][] {
                          {" ", " ", " ", " ", " ", " ", " ", " "},
                   },
                   new String[] {
                          "????????????", "????????????", "??????", "???.???", "???", "???", "??????", "??????"
                   }
           ) {
                   boolean[] columnEditables = new boolean[] {
                          false, false, false, false, false, false, false, false
                   };
                   public boolean isCellEditable(int row, int column) {
                          return columnEditables[column];
                   }
           });
          
           scrollPane.setViewportView(table);
          
           table.addMouseListener(new MouseAdapter() {
        	   @Override
        	   public void mouseClicked(MouseEvent e) {
        		   if (e.getClickCount() == 2 && !e.isConsumed()) {
        			   join.postnum_tf.setText((String) table.getValueAt(table.getSelectedRow(), 1));

    				   String sido = (String) table.getValueAt(table.getSelectedRow(), 2);
    				   String gugun = (String) table.getValueAt(table.getSelectedRow(), 3);
    				   String dong = (String) table.getValueAt(table.getSelectedRow(), 4);
    				   String ri = (String) table.getValueAt(table.getSelectedRow(), 5);
    				   String bldg = (String) table.getValueAt(table.getSelectedRow(), 6);
    				   
    				   if(ri == null) {
           				ri = (" ");
           				if(bldg == null) { 
           					bldg = (" ");
           					join.address_tf1.setText(sido + " " + gugun + " " + dong + " " + ri + " " + bldg);
           				}
           				join.address_tf1.setText(sido + " " + gugun + " " + dong + " " + ri + " " + bldg);
           			}
           			else {
           				if(bldg == null) {
           					bldg = (" ");
           					join.address_tf1.setText(sido + " " + gugun + " " + dong + " " + ri + " " + bldg);
           				}
           				else 
           					join.address_tf1.setText(sido + " " + gugun + " " + dong + " " + ri + " " + bldg);
           			}
           			dispose();
           		  }
        	   }
        	   
           });
           
          
           //????????? combobox ??????
           comboBox = new JComboBox();  
           comboBox.setBounds(146, 40, 100, 20);
           panel.add(comboBox);
           comboBox.addItem("???.??? ??????");
          
           displaySido();
           //???.??? ????????????=============================================
           comboBox.addItemListener(new ItemListener() {
                   public void itemStateChanged(ItemEvent e) {
           if(e.getStateChange()==ItemEvent.SELECTED)
                   selectSido(comboBox.getSelectedItem().toString());
                         
                   }
           });
           comboBox.setToolTipText("");
          
          
           JLabel label = new JLabel("???.???");
           label.setBounds(146, 14, 100, 20);
           panel.add(label);
           label.setHorizontalAlignment(SwingConstants.CENTER);
          
           //???.??? ComboBox=============================================
           comboBox_1 = new JComboBox();
           comboBox_1.setBounds(258, 40, 100, 20);
           panel.add(comboBox_1);
          
           JLabel label_1 = new JLabel("???.???");
           label_1.setBounds(258, 14, 100, 20);
           panel.add(label_1);
           label_1.setHorizontalAlignment(SwingConstants.CENTER);
          
           comboBox_1.addItemListener(new ItemListener() {
                   public void itemStateChanged(ItemEvent e) {
                          if(e.getStateChange()==ItemEvent.SELECTED)
                                  selectGugun(comboBox.getSelectedItem().toString() ,comboBox_1.getSelectedItem().toString());
                   }
           });
          
           //??? ComboBox=============================================
           comboBox_2 = new JComboBox();
           comboBox_2.setBounds(370, 40, 100, 20);
           panel.add(comboBox_2);
          
           JLabel label_2 = new JLabel("???");
           label_2.setBounds(370, 14, 100, 20);
           panel.add(label_2);
           label_2.setHorizontalAlignment(SwingConstants.CENTER);
           
           comboBox_2.addItemListener(new ItemListener() {
               public void itemStateChanged(ItemEvent e) {
                      if(e.getStateChange()==ItemEvent.SELECTED)
                     
                      //table??? ???????????? ??????=====================================
                      selectDong(comboBox.getSelectedItem().toString(), comboBox_1.getSelectedItem().toString(), comboBox_2.getSelectedItem().toString());
               }             
       });
    }
    
    //???????????? ????????? ???.??? ????????????====================================================================
    public void displaySido(){
           //??????
           ZipDao controller = new ZipDao();
           //DB??????
           controller.connection();             
           //
           ArrayList<ZipDto> sidoList = controller.searchSido();
           for(int i = 0 ; i < sidoList.size() ; i++){
                   ZipDto zipcode = sidoList.get(i);
                   comboBox.addItem(zipcode.getSido());
           }             
           //DB?????? ??????
           controller.disconnection();
    }
    //sido ??????(gugun ??????)====================================================================
    public void selectSido(String sido){
           System.out.println(sido);
           ZipDao controller = new ZipDao();
           //DB??????
           controller.connection();             
           //
           ArrayList<ZipDto> gugunList = controller.searchGugun(sido);
           comboBox_1.removeAllItems();
           comboBox_2.removeAllItems();
           comboBox_1.addItem("???.??? ??????");
           for(int i = 0 ; i < gugunList.size() ; i++){
                   ZipDto zipcode = gugunList.get(i);
                   comboBox_1.insertItemAt(zipcode.getGugun(), i);
           }
           table.setModel(new ZipTableModel());
           //DB?????? ??????
           controller.disconnection();
    }      
    //gugun ??????(dong ??????)====================================================================
    public void selectGugun(String sido, String gugun){
           System.out.println(gugun);
           ZipDao controller = new ZipDao();
           //DB??????
           controller.connection();             
           //
           ArrayList<ZipDto> dongList = controller.searchDong(sido, gugun);
           comboBox_2.removeAllItems();
           comboBox_2.addItem("??? ??????");
           for(int i = 0 ; i < dongList.size() ; i++){
                   ZipDto zipcode = dongList.get(i);
                   comboBox_2.insertItemAt(zipcode.getDONG(),i);
           }
           table.setModel(new ZipTableModel());
           //DB?????? ??????
           controller.disconnection();                 
    }
   
    //????????? Dong ??????(???????????? ??????)====================================================================
    public void selectDong(String sido, String gugun, String dong){
          System.out.println("Selected Dong : " + dong);
           ZipDao controller = new ZipDao();
           //DB??????
           controller.connection();             
           //
           ArrayList<ZipDto> addressList = controller.searchAddress(sido, gugun, dong);
          
           Object[][] arrAdd = new Object[addressList.size()][8];
          
           for(int i = 0 ; i < addressList.size() ; i++){
                   ZipDto zipcode = addressList.get(i);
                   //??????!
                   System.out.println(zipcode.getSeq() + " " + zipcode.getZipcode()+ " " +zipcode.getSido()+ " " +zipcode.getGugun()+ " " +zipcode.getDONG() + " " + zipcode.getRi() + " " + zipcode.getBldg() + " " + zipcode.getBunji());                      
                   //???????????? ??????!
                   arrAdd[i][0] = zipcode.getSeq();
                   arrAdd[i][1] = zipcode.getZipcode();
                   arrAdd[i][2] = zipcode.getSido();
                   arrAdd[i][3] = zipcode.getGugun();
                   arrAdd[i][4] = zipcode.getDONG();
                   arrAdd[i][5] = zipcode.getRi();
                   arrAdd[i][6] = zipcode.getBldg();
                   arrAdd[i][7] = zipcode.getBunji();
                  
                   table.setModel(new ZipTableModel(arrAdd));
                   System.out.println("table Setting ");
           }
           //DB?????? ??????
           controller.disconnection();
          
    }
}
