package Client;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author suin
 */
public class DB {
    private String strDriver = "oracle.jdbc.driver.OracleDriver";
    private String strURL = "url";   
    private String strUser = "아이디"; // 계정 id
    private String strPassword = "비밀번호"; // 계정 패스워드

    
    Connection DB_con;
    public Statement DB_stmt; //쿼리문 실행해 줄 수 있는 애
    public ResultSet DB_rs; // 결과 값 받아주는 애
    
    public void dbOpen() throws IOException{ //db오픈 메소드
        try{
            Class.forName(strDriver);
            DB_con = DriverManager.getConnection(strURL, strUser, strPassword);
            DB_stmt = DB_con.createStatement();
        }catch(Exception e){
            System.out.println("SQLException : " + e.getMessage());
        }
    }
    
    public void dbClose() throws IOException{ //db클로즈 메소드
        try{
            DB_stmt.close();
            DB_con.close();
        }catch(Exception e){
            System.out.println("SQLException : " + e.getMessage());
        }
        
    }
}
