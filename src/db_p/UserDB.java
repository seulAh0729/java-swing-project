package db_p;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

class sellUser {
   String id;
   String name;
   String phone;
   String profile_text;
   String loginTime;
   
   public sellUser(String id, String name, String phone, String profile_text, String loginTime) {
	super();
	this.id = id;
	this.name = name;
	this.phone = phone;
	this.profile_text = profile_text;
	this.loginTime = loginTime;
   }


   
}

class normalUser {
   String name;
   String id;
   String phone;
   String gender;
   String loginTime;
   
   public normalUser( String id,String name, String phone, String gender, String loginTime) {
		super();
		this.name = name;
		this.id = id;
		this.phone = phone;
		this.gender = gender;
		this.loginTime = loginTime;
	}
   
   

  
}

public class UserDB {

   static final String host = IP_NumSet.host;

   private static boolean creativeDBusers(int userkind, String id, String pw, String name, String gender,
         String birthYYYYMMDD, String phone, String email, String address, String cardnumber, String pwhint,
         String pwres, String businessname, String businessaddress, String banknum, int coin) {
      boolean res = false;

      Connection con = null;
      Statement stmt = null;

      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");

         con = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":1521:xe", "HR", "HR");

         stmt = con.createStatement();

         stmt.executeUpdate(
               "INSERT INTO USERDATA (USERKIND, ID, PW, NAME, GENDER ,BIRTHDATE, PHONE, EMAIL, ADDRESS, CARDNUMBER, PWHINT, PWRES, BUSINESSNAME, BUSINESSADDRESS, BANKNUMBER, COIN) "
                     + "VALUES  (" + userkind + ", '" + id + "', '" + pw + "', '" + name + "', '" + gender
                     + "', TO_DATE('" + birthYYYYMMDD + "', 'YYYYMMDD'), '" + phone + "', '" + email + "', "
                     + "'" + address + "', '" + cardnumber + "', '" + pwhint + "', '" + pwres + "', '"
                     + businessname + "', '" + businessaddress + "', '" + banknum + "', '" + coin + "')");
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

   static private boolean setDBString(String column, String userID, String setString) {
      boolean res = false;
      Connection con = null;
      Statement stmt = null;
      System.out.println("뜰어오니3");
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");

         con = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":1521:xe", "HR", "HR");

         stmt = con.createStatement();

         stmt.executeUpdate("UPDATE userdata set " + column + "='" + setString + "' where id= '" + userID + "'");

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

   static private boolean setDBInt(String column, String userID, int setInt) {
      boolean res = false;
      Connection con = null;
      Statement stmt = null;

      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");

         con = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":1521:xe", "HR", "HR");

         stmt = con.createStatement();

         stmt.executeUpdate("UPDATE userdata set " + column + "=" + setInt + " where id= '" + userID + "'");

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

   // 일반유저 카인드 =0
   static boolean signupNOMALUSER(String id, String pw, String name, String gender, String birthYYYYMMDD, String phone,
         String email, String address, String cardnumber, String pwhint, String pwres, int coin) {
      return creativeDBusers(0, id, pw, name, gender, birthYYYYMMDD, phone, email, address, cardnumber, pwhint, pwres,
            null, null, null, coin);
   }

   // 점술가 유저 카인드 =1
   static boolean signupSELLERUSER(String id, String pw, String name, String gender, String birthYYYYMMDD,
         String phone, String email, String address, String pwhint, String pwres, String businessname,
         String businessaddress, String banknum, int coin) {
      return creativeDBusers(1, id, pw, name, gender, birthYYYYMMDD, phone, email, address, null, pwhint, pwres,
            businessname, businessaddress, banknum, coin);
   }

   // 관리자 유저 카인드 =2
   static boolean signupSUPERUSER(String id, String pw) {
      return creativeDBusers(2, id, pw, null, null, "20200101", null, null, null, null, null, null, null, null, null,
            0);
   }

   static private String getDBString(String column, String userID) {
      String res = "";

      Connection con = null;
      Statement stmt = null;
      ResultSet rs = null;

      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");

         con = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":1521:xe", "HR", "HR");

         stmt = con.createStatement();

         rs = stmt.executeQuery("select " + column + " from userdata where id= '" + userID + "'");

         while (rs.next()) {
            res = rs.getString(column);
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

   static ArrayList<sellUser> getSELLER() {
      ArrayList<sellUser> res = new ArrayList<sellUser>();
      Connection con = null;
      Statement stmt = null;
      ResultSet rs = null;

      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");

         con = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":1521:xe", "HR", "HR");

         stmt = con.createStatement();

         rs = stmt.executeQuery("SELECT id,name,phone, profile_text,LOGINTIME from userdata WHERE userkind = 1");

         while (rs.next()) {
            String id = rs.getString("ID");
            String name = rs.getString("name");
            String phone = rs.getString("phone");
            String profile_text = rs.getString("profile_text");
            String login_time = rs.getString("LOGINTIME");

            res.add(new sellUser(id, name, phone, profile_text,login_time));
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

   static ArrayList<normalUser> getNomUser() {
      ArrayList<normalUser> res = new ArrayList<normalUser>();
      Connection con = null;
      Statement stmt = null;
      ResultSet rs = null;

      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");

         con = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":1521:xe", "HR", "HR");

         stmt = con.createStatement();

         rs = stmt.executeQuery("SELECT id,name,phone, gender,LOGINTIME from userdata WHERE userkind = 0");

         while (rs.next()) {
            String id = rs.getString("ID");
            String name = rs.getString("name");
            String phone = rs.getString("phone");
            String gender = rs.getString("gender");
            String login_time = rs.getString("LOGINTIME");

            res.add(new normalUser(id, name, phone, gender,login_time));
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
   
   static ArrayList<normalUser> getUsers(int kind) {
	      ArrayList<normalUser> res = new ArrayList<normalUser>();
	      Connection con = null;
	      Statement stmt = null;
	      ResultSet rs = null;

	      try {
	         Class.forName("oracle.jdbc.driver.OracleDriver");

	         con = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":1521:xe", "HR", "HR");

	         stmt = con.createStatement();
	         if(kind==2) {
	        	 rs = stmt.executeQuery("SELECT id,name,phone, gender,LOGINTIME from userdata WHERE userkind > 2 order by logintime desc");
	         }else if(kind ==3){
	        	 rs = stmt.executeQuery("SELECT id,name,phone, gender,LOGINTIME from bufuserdata WHERE userkind = "+2+" order by logintime desc");
	         }else {
	        	 rs = stmt.executeQuery("SELECT id,name,phone, gender,LOGINTIME from userdata WHERE userkind = "+kind+" order by logintime desc");
	        	 
	         }

	         while (rs.next()) {
	            String id = rs.getString("ID");
	            String name = rs.getString("name");
	            String phone = rs.getString("phone");
	            String gender = rs.getString("gender");
	            String login_time = rs.getString("LOGINTIME");

	            res.add(new normalUser(id, name, phone, gender,login_time));
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

   static private int getDBInt(String column, String userID) {
      int res = 0;

      Connection con = null;
      Statement stmt = null;
      ResultSet rs = null;

      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");

         con = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":1521:xe", "HR", "HR");

         stmt = con.createStatement();

         rs = stmt.executeQuery("select " + column + " from userdata where id= '" + userID + "'");

         while (rs.next()) {
            res = rs.getInt(column);
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

   static int getUSERKIND(String userID) {
      return getDBInt("userkind", userID);
   }

   static String getID(String userID) {
      return getDBString("id", userID);
   }

   static String getPW(String userID) {
      return getDBString("PW", userID);
   }

   static String getNAME(String userID) {
      return getDBString("name", userID);
   }

   static String getBIRTHDATE(String userID) {
      return getDBString("birthdate", userID).split(" ")[0];
   }

   static String getPHONE(String userID) {
      return getDBString("phone", userID);
   }

   static String getEMAIL(String userID) {
      return getDBString("email", userID);
   }

   static String getADDRESS(String userID) {
      return getDBString("ADDRESS", userID);
   }

   static String getCARDNUMBER(String userID) {
      return getDBString("CARDNUMBER", userID);
   }

   static String getPWHINT(String userID) {
      return getDBString("PWHINT", userID);
   }

   static String getPWRES(String userID) {
      return getDBString("PWRES", userID);
   }

   static String getBUSINESSNAME(String userID) {
      return getDBString("BUSINESSNAME", userID);
   }

   static String getBUSINESSADDRESS(String userID) {
      return getDBString("BUSINESSADDRESS", userID);
   }

   static String getBANKNUMBER(String userID) {
      return getDBString("BANKNUMBER", userID);
   }

   static String getGENDER(String userID) {
      return getDBString("GENDER", userID);
   }

   static String getLOGINCHK(String userID) {
      return getDBString("LOGINCHK", userID);
   }

   static int getCOIN(String userID) {
      return getDBInt("COIN", userID);
   }

   static String getFORTUNE_TIME(String userID) {
      return getDBString("FORTUNE_TIME", userID).split(" ")[0];
   }

   static String getPROFILE_TEXT(String userID) {
      return getDBString("PROFILE_TEXT", userID);
   }

   static boolean setPROFILE_TEXT(String userID, String setString) {
      return setDBString("PROFILE_TEXT", userID, setString);
   }

   static boolean setUSERKIND(String userID, int setInt) {
	   System.out.println("여기들어오니");
      return setDBInt("USERKIND", userID, setInt);
   }

   static boolean setPW(String userID, String setString) {
      return setDBString("PW", userID, setString);
   }

   static boolean setNAME(String userID, String setString) {
      return setDBString("NAME", userID, setString);
   }

   static boolean setBIRTHDATE(String userID, String setString) {
      return setDBString("BIRTHDATE", userID, setString);
   }

   static boolean setPHONE(String userID, String setString) {
      return setDBString("PHONE", userID, setString);
   }

   static boolean setEMAIL(String userID, String setString) {
      return setDBString("EMAIL", userID, setString);
   }

   static boolean setADDRESS(String userID, String setString) {
      return setDBString("ADDRESS", userID, setString);
   }

   static boolean setCARDNUMBER(String userID, String setString) {
      return setDBString("CARDNUMBER", userID, setString);
   }

   static boolean setPWHINT(String userID, String setString) {
      return setDBString("PWHINT", userID, setString);
   }

   static boolean setPWRES(String userID, String setString) {
      return setDBString("PWRES", userID, setString);
   }

   static boolean setBUSINESSNAME(String userID, String setString) {
      return setDBString("BUSINESSNAME", userID, setString);
   }

   static boolean setBUSINESSADDRESS(String userID, String setString) {
      return setDBString("BUSINESSADDRESS", userID, setString);
   }

   static boolean setBANKNUMBER(String userID, String setString) {
      return setDBString("BANKNUMBER", userID, setString);
   }

   static boolean setCOIN(String userID, int setInt) {
      return setDBInt("COIN", userID, setInt);
   }

   static boolean setFORTUNE_TIME(String userID, String setString) {
      return setDBString("FORTUNE_TIME", userID, setString);
   }
   
   static boolean setLOGIN_TIME(String userID) {
	      boolean res = false;
	      Connection con = null;
	      Statement stmt = null;

	      try {
	         Class.forName("oracle.jdbc.driver.OracleDriver");

	         con = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":1521:xe", "HR", "HR");

	         stmt = con.createStatement();

	         stmt.executeUpdate("UPDATE USERDATA SET LOGINTIME = systimestamp WHERE id = '"+userID+"'");
	         
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

   static boolean setLOGINCHK(String userID, String setString) {
	   System.out.println("들어오니2");
      return setDBString("LOGINCHK", userID, setString);
   }

   static boolean setFORTUNE_NUM(String userID, int setInt) {
      return setDBInt("FORTUNE_NUM", userID, setInt);
   }

   static int getFORTUNE_NUM(String userID) {
      return getDBInt("FORTUNE_NUM", userID);
   }

   static String searchID(String userNAME, String userPHONE) {

      String res = ""; // 없으면 "" 리턴

      Connection con = null;
      Statement stmt = null;
      ResultSet rs = null;

      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");

         con = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":1521:xe", "HR", "HR");

         stmt = con.createStatement();

         rs = stmt.executeQuery(
               "select id from userdata where name= '" + userNAME + "' and phone= '" + userPHONE + "'");

         while (rs.next()) {
            res = rs.getString("id");
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

   static String searchIP(String userIP) {

      String res = ""; // 없으면 "" 리턴

      Connection con = null;
      Statement stmt = null;
      ResultSet rs = null;

      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");

         con = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":1521:xe", "HR", "HR");

         stmt = con.createStatement();

         rs = stmt.executeQuery("select id from userdata where USER_IP= '" + userIP + "'");

         while (rs.next()) {
            res = rs.getString("id");
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

   static String searchPW(String userID, String userPWHINT, String userPWRES) {

      String res = ""; // 없으면 "" 리턴

      Connection con = null;
      Statement stmt = null;
      ResultSet rs = null;

      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");

         con = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":1521:xe", "HR", "HR");

         stmt = con.createStatement();

         rs = stmt.executeQuery("select PW from userdata where ID= '" + userID + "' AND PWHINT= '" + userPWHINT
               + "' AND PWRES ='" + userPWRES + "'");

         while (rs.next()) {
            res = rs.getString("PW");
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
   
   static boolean deleteUSER(String userID) {
	   boolean res = false;
	      
	      Connection con = null;
	      Statement stmt=null;
	      
	      
	      try {
	         Class.forName("oracle.jdbc.driver.OracleDriver");
	         
	         con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
	         
	         stmt = con.createStatement();
	         
	         stmt.executeUpdate("delete from  userdata where ID = '"+userID+"'");
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