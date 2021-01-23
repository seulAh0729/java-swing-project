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

class Chatlist{
   String sell_id; // 채팅할 점술가 id
   String user_id; //채팅할 사용자id
   Date chattime; // 채팅시간 date
   String chatmenu;
   String chattimestr; // 채팅시작시간을 보기좋게
   
   public Chatlist(String sell_id, String user_id, Date chattime, String chatmenu) {
      super();
      SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy년 MM월 dd일 a hh시 mm분");
      this.sell_id = sell_id;
      this.user_id = user_id;
      this.chatmenu = chatmenu;
      
      this.chattime = chattime;
      
      chattimestr = sdf2.format(chattime);
   }

   @Override
   public String toString() {
      return "Chatlist [sell_id=" + sell_id + ", user_id=" + user_id + ", chattimestr="
            + chattimestr + "]";
   }
}

public class ChatListDB {
   
   static final String host =IP_NumSet.host;
   
   static ArrayList<Chatlist> getCHATLIST(String userID){ // 채팅리스트들 가져오기
      
      
      ArrayList<Chatlist> chatlists = new ArrayList<Chatlist>();
      
      Connection con = null;
      Statement stmt=null;
      ResultSet rs =null;
      
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         
         con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
         
         stmt = con.createStatement();
         
         rs = stmt.executeQuery("select * from chatlist where sell_id ='"+userID+"' or user_id ='"+userID+"' ORDER BY CHATTIME ASC");
         
         while (rs.next()) {
            String sell_id = rs.getString("sell_id");
            String user_id = rs.getString("user_id");
            String chattime = rs.getString("CHATTIME");
            String chatmenu = rs.getString("CHATMENU");
            
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            
            chatlists.add(new Chatlist(sell_id, user_id, sdf1.parse(chattime), chatmenu));
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
   
      return chatlists;
   }
   
   
   static boolean saveCHATLIST(String sell_id, String user_id, Date chattime, String chatmenu) { // 채팅리스트 만들기 
      boolean res = false;
      
      Connection con = null;
      Statement stmt=null;
      SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         
         con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
         
         stmt = con.createStatement();
         
         stmt.executeUpdate("INSERT INTO CHATLIST (SELL_ID, USER_ID, CHATTIME, CHATMENU) VALUES ('"+sell_id+"', '"+user_id+"', TO_DATE('"+sdf1.format(chattime)+"', 'YYYY-MM-DD HH24:MI:SS'),'"+chatmenu+"')");
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
   
   static boolean deleteCHATLIST(String sell_id, String user_id, Date chattime, String chatmenu) { // 채팅리스트 지우기
      boolean res = false;
      
      Connection con = null;
      Statement stmt=null;
      SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         
         con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
         
         stmt = con.createStatement();
         int a = stmt.executeUpdate("DELETE from chatlist WHERE sell_id = '"+sell_id+"' and user_id = '"+user_id+"' and chattime = TO_DATE('"+sdf1.format(chattime)+"', 'YYYY-MM-DD HH24:MI:SS') and chatmenu ='"+chatmenu+"'");
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

