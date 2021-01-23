package db_p;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MenuDB {
   
   static final String host =IP_NumSet.host;
   
   static boolean makeMENU(String userID) {
      boolean res = false;
      
      Connection con = null;
      Statement stmt=null;
      
      
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         
         con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
         
         stmt = con.createStatement();
         
         stmt.executeUpdate("INSERT INTO SELLMENU (ID, MENU1, MENU2, MENU3, MENU4, MENU5) VALUES ('"+userID+"', '包惑 500', '荤林钱捞 400', '楷局款技 300', '流厘款技 300', '脚斥款技 300')");
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
   
   static boolean setMENU(String userID, int menuNum, String setMenu) {
      boolean res = false;
      Connection con = null;
      Statement stmt=null;
      
      
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         
         con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
         
         stmt = con.createStatement();
      
         stmt.executeUpdate("UPDATE sellmenu set menu"+menuNum+"='"+setMenu+"' where id= '"+userID+"'");
         
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
   
   static String [] getMENU(String userID) {
      Connection con = null;
      Statement stmt=null;
      ResultSet rs =null;
      String [] retarr=null;
      
      
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         
         con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
         
         stmt = con.createStatement();
         
         retarr = new String[5];
         
         

         rs = stmt.executeQuery("select * from sellmenu where id='"+userID+"'");
         while (rs.next()) {
            rs.getString("ID");
            
            for (int i = 0; i < retarr.length; i++) {
               retarr[i] = rs.getString("menu"+(i+1));
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
   
   

   public static void main(String[] args) {
      
      
   }

}