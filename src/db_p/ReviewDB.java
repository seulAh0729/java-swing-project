package db_p;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * 테이블 생성 ddl
 CREATE TABLE REVIEW 
(
  ID VARCHAR2(100 BYTE) 
, COMENT VARCHAR2(200 BYTE) 
, POINT NUMBER(*, 0) 
, MAKETIME DATE 
) 
LOGGING 
TABLESPACE USERS 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1 
  MAXEXTENTS UNLIMITED 
  BUFFER_POOL DEFAULT 
) 
NOPARALLEL;

 */

class Review{
	String id;
	String coment;
	String maketime;
	int point;
	public Review(String id, String coment, String maketime, int point) {
		super();
		this.id = id; // 리뷰의 대상 (점술가ID)
		this.coment = coment; // 내용 (사용자이름 (사용자 ID) : 내용)
		this.maketime = maketime; // 남긴시간
		this.point = point; // 평점
	}
	@Override
	public String toString() {
		return "Review [점술가 id=" + id + ", 내용=" + coment + ", 리뷰남긴시간=" + maketime + ", 평점=" + point + "]";
	}
	
	
	
	
}

public class ReviewDB {
	
	static final String host ="localhost";
	
	static ArrayList<Review> getREVIEW(String sellerID){ // 리뷰들 가져오기
		
		
		ArrayList<Review> reviews = new ArrayList<Review>();
		
		Connection con = null;
		Statement stmt=null;
		ResultSet rs =null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
			
			stmt = con.createStatement();
			
			rs = stmt.executeQuery("select * from review where id ='"+sellerID+"' ORDER BY MAKETIME ASC");
			
			while (rs.next()) {
				String id = rs.getString("ID");
				String coment = rs.getString("COMENT");
				String maketime = rs.getString("MAKETIME");
				int point = rs.getInt("point");
				
				
				reviews.add(new Review(id, coment, maketime, point));
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
	
		return reviews;
	}
	
	
	static boolean saveREVIEW(String id, String coment, int point) { //리뷰남기기
		boolean res = false;
		
		Connection con = null;
		Statement stmt=null;
		
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
			
			stmt = con.createStatement();
			
			stmt.executeUpdate("INSERT INTO REVIEW (ID, COMENT, MAKETIME, POINT) VALUES ('"+id+"', '"+coment+"', sysdate, "+point+")");
			System.out.println("성공");
			res = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return res;
		
		
	}
	
	static int getAVGPOINT(String sellerID){ //리뷰 평점 평균 가져오기
		
		
		int avgpoint = 0;
		
		Connection con = null;
		Statement stmt=null;
		ResultSet rs =null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
			
			stmt = con.createStatement();
			
			rs = stmt.executeQuery("SELECT avg(point) as avg from(SELECT point FROM review WHERE id = '"+sellerID+"')");
			
			while (rs.next()) {
				avgpoint = rs.getInt("avg");
								
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
	
		return avgpoint;
	}
	
	
	
	

	public static void main(String[] args) {
		
		
	}

}
