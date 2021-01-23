package db_p;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TodayFortuneDB {
	
	static final String host =IP_NumSet.host;
	
	
	static String getFORTUNEMESSAGE(int number) {
		String res="";
		
		
		Connection con = null;
		Statement stmt=null;
		ResultSet rs =null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
			
			stmt = con.createStatement();
			
			rs = stmt.executeQuery("select FORTUNE from TODAY_FORTUNE where FORTUNE_NUMBER= '"+number+"'");
			
			while (rs.next()) {
				res = rs.getString("FORTUNE");
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		
		return res;
	}

	public static void main(String[] args) {
		//사용예시
		System.out.println(getFORTUNEMESSAGE((int)(Math.random()*10+1)));

	}

}
