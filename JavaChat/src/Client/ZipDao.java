package Client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ZipDao {
	Connection conn;
    PreparedStatement pstmt;
    ResultSet rs;
   
   
    // 데이터베이스 연결
    public void connection() {
    	try {
                  Class.forName("oracle.jdbc.driver.OracleDriver");
                  conn = DriverManager.getConnection("url", "아이디", "비밀번호");
         	} catch (ClassNotFoundException e) {
         		
            } catch (SQLException e) {
            	
            }
    }
   
    // 데이터베이스 연결종료
    public void disconnection() {
    	try {
    		if(pstmt != null) pstmt.close();
                      
    		if(rs != null) rs.close();
                      
    		if(conn != null) conn.close();
             
    	} catch (SQLException e) {
    		
    	}
    }

    // 시도데이터=============================================
    public ArrayList<ZipDto> searchSido() {
             ArrayList<ZipDto> sidoList = new ArrayList<ZipDto>();
             try {
                      String query = "select distinct(sido) from zipcode order by sido";
                      pstmt = conn.prepareStatement(query);
                      rs = pstmt.executeQuery();
                      while(rs.next()){
                              ZipDto zipcode = new ZipDto();
                              zipcode.setSido(rs.getString("SIDO"));
                              sidoList.add(zipcode);
                      }
             } catch (SQLException e) {
             }

             return sidoList;
    }
   
    // 구군데이터=============================================
    public ArrayList<ZipDto> searchGugun(String sido) {
             ArrayList<ZipDto> gugunList = new ArrayList<>();
            
             try {
                      String query = "select distinct(gugun) from zipcode where sido = \'" + sido + "\' order by gugun";
                      pstmt = conn.prepareStatement(query);
                      rs = pstmt.executeQuery();
                      while(rs.next()){
                              ZipDto zipcode = new ZipDto();
                              zipcode.setGugun(rs.getString("GUGUN"));
                              gugunList.add(zipcode);
                      }
             } catch (SQLException e) {
             }
                             
             return gugunList;         
    }

    // 동데이터=============================================
    public ArrayList<ZipDto> searchDong(String sido, String gugun) {
             ArrayList<ZipDto> dongList = new ArrayList<>();

             try {
                      String query = "select distinct(dong) from zipcode where sido = \'" + sido + "\'  and gugun = \'" + gugun + "\' order by dong";
                      pstmt = conn.prepareStatement(query);
                      rs = pstmt.executeQuery();
                      while(rs.next()){
                              ZipDto zipcode = new ZipDto();
                              zipcode.setDong(rs.getString("DONG"));
                              dongList.add(zipcode);
                      }
             } catch (SQLException e) {
             }

             return dongList;          
    }

    // 전부주소 데이터 =============================================
    public ArrayList<ZipDto> searchAddress(String sido, String gugun, String dong) {

    	ArrayList<ZipDto> addressList = new ArrayList<>();

    	try {
    		String query = "select * from zipcode where sido = \'" + sido + "\'  and gugun = \'" + gugun + "\' and dong = \'" + dong +"\'";

    		pstmt = conn.prepareStatement(query);

    		rs = pstmt.executeQuery();

    		while(rs.next()){

    			ZipDto zipcode = new ZipDto();
    			
    			zipcode.setSeq(rs.getString("SEQ"));
                zipcode.setZipcode(rs.getString("ZIPCODE"));
                zipcode.setSido(rs.getString("SIDO"));
	            zipcode.setGugun(rs.getString("GUGUN"));
	            zipcode.setDong(rs.getString("DONG"));
	            zipcode.setRi(rs.getString("RI"));
	            zipcode.setBldg(rs.getString("BLDG"));
	            zipcode.setBunji(rs.getString("BUNJI"));
	            
	            addressList.add(zipcode);
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
             
    	return addressList;               
    }

	public ArrayList<ZipDto> searchKeyDong(String dongName) {
		
		ArrayList<ZipDto> addressList = new ArrayList<>();

    	try {
    		String query = "select * from zipcode where dong like \'%\' \'" + dongName + "\' || \'%\'";

    		pstmt = conn.prepareStatement(query);

    		rs = pstmt.executeQuery();

    		while(rs.next()){

    			ZipDto zipcode = new ZipDto();
    			
    			zipcode.setSeq(rs.getString("SEQ"));
                zipcode.setZipcode(rs.getString("ZIPCODE"));
                zipcode.setSido(rs.getString("SIDO"));
	            zipcode.setGugun(rs.getString("GUGUN"));
	            zipcode.setDong(rs.getString("DONG"));
	            zipcode.setRi(rs.getString("RI"));
	            zipcode.setBldg(rs.getString("BLDG"));
	            zipcode.setBunji(rs.getString("BUNJI"));
	            
	            addressList.add(zipcode);
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
             
    	return addressList;  
	}
}
