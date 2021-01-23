package db_p;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;



class Notice{
   String title;
   String content;
   String maketime;
   String modi_time;
   String writer;
   
   public Notice(String title, String content, String maketime, String modi_time, String writer) {
      super();
      this.title = title;
      this.content = content;
      this.maketime = maketime;
      this.modi_time = modi_time;
      this.writer = writer;
   }

   @Override
   public String toString() {
      return "Notice [title=" + title + ", content=" + content + ", maketime=" + maketime + ", modi_time=" + modi_time
            + ", writer=" + writer + "]";
   }
   
   
}

public class NoticeDB {
   
   static final String host =IP_NumSet.host;
   
   static ArrayList<Notice> getNOTICE(){
      
      
      ArrayList<Notice> notices = new ArrayList<Notice>();
      
      Connection con = null;
      Statement stmt=null;
      ResultSet rs =null;
      
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         
         con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
         
         stmt = con.createStatement();
         
         rs = stmt.executeQuery("select * from NOTICE  ORDER BY MAKETIME DESC");
         
         while (rs.next()) {
            String title = rs.getString("title");
            String content = rs.getString("CONTENT");
            String maketime = rs.getString("MAKETIME");
            String modi_time = rs.getString("MODIFI_TIME");
            String writer = rs.getString("WRITER");
            
            
            notices.add(new Notice(title, content, maketime, modi_time, writer));
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
   
      return notices;
   }
   
   
   
   
   static boolean saveNOTICE(String title, String content, String writer) {
      boolean res = false;
      
      Connection con = null;
      Statement stmt=null;
      
      
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         
         con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
         
         stmt = con.createStatement();
         
         stmt.executeUpdate("INSERT INTO NOTICE (TITLE, CONTENT, MAKETIME, WRITER) VALUES ('"+title+"', '"+content+"', sysdate, '"+writer+"')");
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
   
   static boolean updateNOTICE(String title, String newtitle ,String content) {
      boolean res = false;
      
      Connection con = null;
      Statement stmt=null;
      
      
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         
         con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
         
         stmt = con.createStatement();
         
         stmt.executeUpdate("UPDATE  NOTICE "
               + "SET TITLE = '"+newtitle+"'"
               + ",CONTENT = '"+content+"'"
               + ",MODIFI_TIME = SYSDATE WHERE TITLE = '"+title+"'");
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
   
   static boolean deleteNOTICE(String title,String content) {
      boolean res = false;
      
      Connection con = null;
      Statement stmt=null;
      
      
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         
         con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
         
         stmt = con.createStatement();
         
         stmt.executeUpdate("delete from  NOTICE where title = '"+title+"' and content = '"+content+"'");
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
   
   

   public static void main(String[] args) {
      
      
   }

}