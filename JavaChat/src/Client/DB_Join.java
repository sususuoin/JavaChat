package Client;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class DB_Join {
    private String strDriver = "oracle.jdbc.driver.OracleDriver";
    private String strURL = "URL";//url;   
    private String strUser = "아이디";
    private String strPassword = "비밀번호"; 

    
    Connection DB_con;
    Statement DB_stmt; 
    ResultSet DB_rs; 
    
    public void dbOpen() throws IOException{ //db 열기 
        try{
            Class.forName(strDriver);
            DB_con = DriverManager.getConnection(strURL, strUser, strPassword);
            DB_stmt = DB_con.createStatement();
        }catch(Exception e){
            System.out.println("SQLException : " + e.getMessage());
        }
    }
    
    public void dbClose() throws IOException{ //db닫기 ??
        try{
            DB_stmt.close();
            DB_con.close();
        }catch(Exception e){
            System.out.println("SQLException : " + e.getMessage());
        }
        
    }
}
