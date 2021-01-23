package db_p;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JTable;

class Message{
   String to_id;
   String from_id;
   String content;
   String maketime;
   
   public Message(String to_id, String from_id, String content, String maketime) {
      super();
      this.to_id = to_id;
      this.from_id = from_id;
      this.content = content;
      this.maketime = maketime;
   }
   
  String [] getarray(int set) {
     if(set==1) {
        return new String[] {from_id,content,maketime};
     }else {
        return new String[] {to_id,content,maketime};     
     }
  }
   
   

   
   
   
}

public class MessageDB {
   

   
   static final String host =IP_NumSet.host;
                  //받은 메세지함
   static String[][] getTO_MESSAGE(String userID){
      
      
      ArrayList<Message> notices = new ArrayList<Message>();
      
      Connection con = null;
      Statement stmt=null;
      ResultSet rs =null;
      String [][] to_array=null;
      
      
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         
         con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
         
         stmt = con.createStatement();
         
         rs = stmt.executeQuery("select * from to_MESSAGE where to_id = '"+userID+"' ORDER BY MAKETIME DESC");
         
         while (rs.next()) {
            String to_id = rs.getString("to_id");
            String from_id = rs.getString("from_id");
            String content = rs.getString("content");
            String maketime = rs.getString("maketime");
            
            
            
            notices.add(new Message(to_id, from_id, content, maketime));
         }
         
         if(!notices.isEmpty()) {
         to_array = new String[notices.size()][];
         for (int i = 0; i < notices.size(); i++) {
            to_array[i] = notices.get(i).getarray(1);
         }
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
   
      return to_array;
   }
   static String[][] getTO_MESSAGE(String userID, String from_userID){
	      
	      
	      ArrayList<Message> notices = new ArrayList<Message>();
	      
	      Connection con = null;
	      Statement stmt=null;
	      ResultSet rs =null;
	      String [][] to_array=null;
	      
	      
	      try {
	         Class.forName("oracle.jdbc.driver.OracleDriver");
	         
	         con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
	         
	         stmt = con.createStatement();
	         
	         rs = stmt.executeQuery("select * from to_MESSAGE where to_id = '"+userID+"' AND FROM_id = '"+from_userID+"'  ORDER BY MAKETIME DESC");
	         
	         while (rs.next()) {
	            String to_id = rs.getString("to_id");
	            String from_id = rs.getString("from_id");
	            String content = rs.getString("content");
	            String maketime = rs.getString("maketime");
	            
	            
	            
	            notices.add(new Message(to_id, from_id, content, maketime));
	         }
	         
	         if(!notices.isEmpty()) {
	         to_array = new String[notices.size()][];
	         for (int i = 0; i < notices.size(); i++) {
	            to_array[i] = notices.get(i).getarray(1);
	         }
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
	   
	      return to_array;
	   }
                  //보낸 메세지함
static String[][] getFROM_MESSAGE(String userID){
      
      
      ArrayList<Message> notices = new ArrayList<Message>();
      
      Connection con = null;
      Statement stmt=null;
      ResultSet rs =null;
      String [][] to_array=null;
      
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         
         con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
         
         stmt = con.createStatement();
         
         rs = stmt.executeQuery("select * from from_MESSAGE where from_id = '"+userID+"' ORDER BY MAKETIME DESC");
         
         while (rs.next()) {
            String to_id = rs.getString("to_id");
            String from_id = rs.getString("from_id");
            String content = rs.getString("content");
            String maketime = rs.getString("maketime");
            
            
            
            notices.add(new Message(to_id, from_id, content, maketime));
         }
         if(!notices.isEmpty()) {
         to_array = new String[notices.size()][];
         for (int i = 0; i < notices.size(); i++) {
            to_array[i] = notices.get(i).getarray(0);
         }
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
   
      return to_array;
   }

static String[][] getFROM_MESSAGE(String userID, String to_userID){
    
    
    ArrayList<Message> notices = new ArrayList<Message>();
    
    Connection con = null;
    Statement stmt=null;
    ResultSet rs =null;
    String [][] to_array=null;
    
    try {
       Class.forName("oracle.jdbc.driver.OracleDriver");
       
       con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
       
       stmt = con.createStatement();
       
       rs = stmt.executeQuery("select * from from_MESSAGE where from_id = '"+userID+"' AND to_id = '"+to_userID+"' ORDER BY MAKETIME DESC");
       
       while (rs.next()) {
          String to_id = rs.getString("to_id");
          String from_id = rs.getString("from_id");
          String content = rs.getString("content");
          String maketime = rs.getString("maketime");
          
          
          
          notices.add(new Message(to_id, from_id, content, maketime));
       }
       if(!notices.isEmpty()) {
       to_array = new String[notices.size()][];
       for (int i = 0; i < notices.size(); i++) {
          to_array[i] = notices.get(i).getarray(0);
       }
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
 
    return to_array;
 }
   
                        //받는 사람(admin)    보낸사람 (나)         내용
   static boolean saveMESSAGE(String to_id, String from_id, String contant) { //메세지보내기
      boolean res = false;
      
      Connection con = null;
      Statement stmt=null;
      
      
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         
         con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
         
         stmt = con.createStatement();
         
         stmt.executeUpdate("INSERT INTO TO_MESSAGE (TO_ID, FROM_ID, CONTENT, MAKETIME) VALUES ('"+to_id+"', '"+from_id+"', '"+contant+"', systimestamp)");
         stmt.executeUpdate("INSERT INTO FROM_MESSAGE (TO_ID, FROM_ID, CONTENT, MAKETIME) VALUES ('"+to_id+"', '"+from_id+"', '"+contant+"', systimestamp)");
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
   
   
   static boolean deleteSendMESSAGE(String userID) { //보낸메세지함 삭제
         boolean res = false;
         
         Connection con = null;
         Statement stmt=null;
         
         
         try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
            
            stmt = con.createStatement();
            
            stmt.executeUpdate("delete from from_message where from_id = '"+userID+"'");
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
   
   
   static boolean deleteGiveMESSAGE(String userID) { //받는 메세지함 삭제
         boolean res = false;
         
         Connection con = null;
         Statement stmt=null;
         
         
         try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
            
            stmt = con.createStatement();
            
            stmt.executeUpdate("delete from to_message where to_id = '"+userID+"'");
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
      saveMESSAGE("pray", "admin", "히히히");
   }

}