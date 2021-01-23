package db_p;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
 * 테이블 생성 ddl

CREATE TABLE CHATLIST 
(
  SELL_ID VARCHAR2(100 BYTE) 
, USER_ID VARCHAR2(100 BYTE) 
, CHATTIME DATE 
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
 * 테이블 생성 ddl

CREATE TABLE CHATLIST 
(
  SELL_ID VARCHAR2(100 BYTE) 
, USER_ID VARCHAR2(100 BYTE) 
, CHATTIME DATE 
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

class Chatlist2{
   String sell_id; // 채팅할 점술가 id
   String user_id; //채팅할 사용자id
   Date chattime; // 채팅시간 date
   String chatmenu;
   String chattimestr; // 채팅시작시간을 보기좋게
   
   public Chatlist2(String sell_id, String user_id, Date chattime, String chatmenu) {
      super();
      SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy년 MM월 dd일 a hh시 mm분");
      this.sell_id = sell_id;
      this.user_id = user_id;
      this.chatmenu = chatmenu;
      
      this.chattime = chattime;
      
      chattimestr = sdf2.format(chattime);
   }

   @Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((chatmenu == null) ? 0 : chatmenu.hashCode());
	result = prime * result + ((chattime == null) ? 0 : chattime.hashCode());
	result = prime * result + ((chattimestr == null) ? 0 : chattimestr.hashCode());
	result = prime * result + ((sell_id == null) ? 0 : sell_id.hashCode());
	result = prime * result + ((user_id == null) ? 0 : user_id.hashCode());
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Chatlist2 other = (Chatlist2) obj;
	if (chatmenu == null) {
		if (other.chatmenu != null)
			return false;
	} else if (!chatmenu.equals(other.chatmenu))
		return false;
	if (chattime == null) {
		if (other.chattime != null)
			return false;
	} else if (!chattime.equals(other.chattime))
		return false;
	if (chattimestr == null) {
		if (other.chattimestr != null)
			return false;
	} else if (!chattimestr.equals(other.chattimestr))
		return false;
	if (sell_id == null) {
		if (other.sell_id != null)
			return false;
	} else if (!sell_id.equals(other.sell_id))
		return false;
	if (user_id == null) {
		if (other.user_id != null)
			return false;
	} else if (!user_id.equals(other.user_id))
		return false;
	return true;
}

@Override
   public String toString() {
      return "Chatlist2 [sell_id=" + sell_id + ", user_id=" + user_id + ", chattimestr="
            + chattimestr + "]";
   }
}

public class ReservationDB {
   
   static final String host =IP_NumSet.host;
   
   static ArrayList<Chatlist2> getRESERVATION(String userID){ // 채팅리스트들 가져오기
      
      
      ArrayList<Chatlist2> chatlists2 = new ArrayList<Chatlist2>();
      
      Connection con = null;
      Statement stmt=null;
      ResultSet rs =null;
      
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         
         con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
         
         stmt = con.createStatement();
         
         rs = stmt.executeQuery("select * from reservationlist where sell_id ='"+userID+"' or user_id ='"+userID+"' ORDER BY CHATTIME ASC");
         
         while (rs.next()) {
            String sell_id = rs.getString("sell_id");
            String user_id = rs.getString("user_id");
            String chattime = rs.getString("CHATTIME");
            String chatmenu = rs.getString("CHATMENU");
            
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            
            chatlists2.add(new Chatlist2(sell_id, user_id, sdf1.parse(chattime), chatmenu));
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
   
      return chatlists2;
   }
   
   
   static boolean saveRESERVATION(String sell_id, String user_id, Date chattime, String chatmenu) { // 채팅리스트 만들기 
      boolean res = false;
      System.out.println("들어오니?");
      Connection con = null;
      Statement stmt=null;
      SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         
         con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
         
         stmt = con.createStatement();
         
         stmt.executeUpdate("INSERT INTO RESERVATIONLIST (SELL_ID, USER_ID, CHATTIME, CHATMENU) VALUES ('"+sell_id+"', '"+user_id+"', TO_DATE('"+sdf1.format(chattime)+"', 'YYYY-MM-DD HH24:MI:SS'),'"+chatmenu+"')");
         System.out.println("성공22");
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
   
   static boolean deleteRESERVATION(String sell_id, String user_id, Date chattime, String chatmenu) { // 채팅리스트 지우기
      boolean res = false;
      
      Connection con = null;
      Statement stmt=null;
      SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         
         con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
         
         stmt = con.createStatement();
         int a = stmt.executeUpdate("DELETE from reservationlist WHERE sell_id = '"+sell_id+"' and user_id = '"+user_id+"' and chattime = TO_DATE('"+sdf1.format(chattime)+"', 'YYYY-MM-DD HH24:MI:SS') and chatmenu ='"+chatmenu+"'");
         if(a>0) {
         res = true;
         }
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
   


   public static void main(String[] args) {
	  

   }

}

