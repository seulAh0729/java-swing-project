package db_p;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;



public class ScheduleDB {

   static final String host = IP_NumSet.host;
   
   static String[] getScheduleDB(String userID, String date){
      Connection con = null;
      Statement stmt=null;
      ResultSet rs =null;
      String [] retarr=null;
      
      
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         
         con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
         
         stmt = con.createStatement();
         
         retarr=new String[24];
         
         

         rs = stmt.executeQuery("select * from SCHEDULE where id='"+userID+"' and to_char(sch_date,'yyyy-mm-dd') = '"+date+"'");
         while (rs.next()) {
            rs.getString("ID");
            rs.getString("SCH_DATE");
            
            for (int i = 0; i < retarr.length; i++) {
               retarr[i] = rs.getString("TIME"+i);
            }
            
            
         }
         
         
         System.out.println("己傍");
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
      return retarr;
   }
   
   static boolean setSchedule(String userID, String date , String time, String set) {
      boolean res = false;
      
      Connection con = null;
      Statement stmt=null;
      
      
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         
         con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
         
         stmt = con.createStatement();
         
         int upNum = stmt.executeUpdate("update schedule set "+time+" = '"+set+"' where id = '"+userID+"' and to_char(sch_date,'yyyy-mm-dd') = '"+date+"' and "+time+"= 'true'");
         if(upNum>0) {
         System.out.println("己傍");
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
   static boolean setSellerSchedule(String userID, String date , String time, String set) {
      boolean res = false;
      
      Connection con = null;
      Statement stmt=null;
      
      String notset="";
      if(set.equals("true")) {
    	notset= "false";
      }else {
    	notset= "true";
      }
      
      
      
      
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         
         con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
         
         stmt = con.createStatement();
         
         int upNum = stmt.executeUpdate("update schedule set "+time+" = '"+set+"' where id = '"+userID+"' and to_char(sch_date,'yyyy-mm-dd') = '"+date+"' and "+time+" = '"+notset+"'");
         if(upNum>0) {
            System.out.println("己傍");
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
   
   

   static boolean makeSchedule(String userID, String date) {
      boolean res = false;
      
      Connection con = null;
      Statement stmt=null;
      
      
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         
         con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
         
         stmt = con.createStatement();
         
         stmt.executeUpdate("INSERT INTO SCHEDULE VALUES ('"+userID+"', TO_DATE('"+date+"', 'YYYY-MM-DD'), 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false', 'false')");
         System.out.println("己傍");
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
      System.out.println(Arrays.toString(getScheduleDB("admin", "2020-06-21")));
      makeSchedule("admin", "2020-06-22");
      setSchedule("admin", "2020-06-21", "time3", "true");
   }
}