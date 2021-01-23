package db_p;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

public class MoneyLogDB {
   
static final String host =IP_NumSet.host;
   
   
   static String [][] getMONEYLOG(String userID ,int kind) {
      
      
      
      Connection con = null;
      Statement stmt=null;
      ResultSet rs =null;
      String [][] retarr=null;
      
      ArrayList<String []> resarr = new ArrayList<String[]>();
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         
         con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
         
         stmt = con.createStatement();
         
         rs = stmt.executeQuery("select * from MONEYLOG where ID= '"+userID+"' and kind= '"+kind+"'");
         
         while (rs.next()) {
            rs.getString("ID");
            String money = rs.getInt("money")+"��";
            String coinnum = rs.getInt("coinnum")+"��";
            String time = rs.getString("time");
            
            String [] arr = {money,coinnum,time};
            
            resarr.add(arr);
         }
         
         retarr = new String[resarr.size()][];
         for (int i = 0; i < retarr.length; i++) {
            retarr[i] = resarr.get(i);
         }
         System.out.println("����");
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
   //0 =  ���� , 1 = �ܼ� ��� �ð�����
static String [][] getSTATSMONEYLOG(int kind , String date, int statkind) {
      
   Connection con = null;
   Statement stmt=null;
   ResultSet rs =null;
   String [][] retarr=null;
   
   String firstyear;
   String firstMonth;
   String firstdate;
   
   String lastyear;
   String lastMonth;
   String lastdate;
   String outdate;
   String [] datearr =  date.split("-");
   
   if(datearr[0].equals("��ü")) {
      firstyear = "1990";
      firstMonth = "1";
      firstdate = "1";
      
      lastyear = "2100";
      lastMonth = "12";
      lastdate = "31";
      outdate = "��ü";
   }else {
      firstyear = datearr[0];
      lastyear = datearr[0];
      if(datearr[1].equals("��ü")) {
         firstMonth = "1";
         firstdate = "1";
         lastMonth = "12";
         lastdate = "31";
         outdate = datearr[0];
      }else {
         firstMonth = datearr[1];
         lastMonth = datearr[1];
         firstdate = datearr[2];
         lastdate = datearr[2];
         outdate = datearr[0]+"-"+datearr[1]+"-"+datearr[2];
         if(datearr[2].equals("��ü")) {
         outdate = datearr[0]+"-"+datearr[1];
         firstdate = "1";
         Calendar cal = Calendar.getInstance();
         cal.set(Integer.parseInt(datearr[0]), Integer.parseInt(datearr[1])-1, 1);
         lastdate = cal.getActualMaximum(Calendar.DAY_OF_MONTH)+"";
         }
      }
   }
   System.out.println(firstyear+"�� "+firstMonth+"�� "+firstdate+"�� ~"+lastyear+"�� "+lastMonth+" ��"+lastdate+"��");
   
   
   ArrayList<String []> resarr = new ArrayList<String[]>();
   try {
      Class.forName("oracle.jdbc.driver.OracleDriver");
      
      con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
      
      stmt = con.createStatement();
      
      System.out.println("SELECT id,sum(money) as money,sum(coinnum) as coinnum, '"+outdate+"' as time "
               + "from(SELECT * FROM moneylog WHERE time >= TO_TIMESTAMP('"+firstyear+"-"+firstMonth+"-"+firstdate+" 00:00:00', 'YYYY-MM-DD HH24:MI:SS') and "
               + "time <= TO_TIMESTAMP('"+lastyear+"-"+lastMonth+"-"+lastdate+" 23:59:59', 'YYYY-MM-DD HH24:MI:SS') and kind = "+kind+") GROUP by id ORDER  by coinnum desc");
      if(statkind == 0) {
         rs = stmt.executeQuery("SELECT id,sum(money) as money,sum(coinnum) as coinnum, '"+outdate+"' as time "
               + "from(SELECT * FROM moneylog WHERE time >= TO_TIMESTAMP('"+firstyear+"-"+firstMonth+"-"+firstdate+" 00:00:00', 'YYYY-MM-DD HH24:MI:SS') and "
               + "time <= TO_TIMESTAMP('"+lastyear+"-"+lastMonth+"-"+lastdate+" 23:59:59', 'YYYY-MM-DD HH24:MI:SS') and kind = "+kind+") GROUP by id ORDER  by coinnum desc");
      }else {
         rs = stmt.executeQuery("SELECT * FROM moneylog WHERE time >= TO_TIMESTAMP('"+firstyear+"-"+firstMonth+"-"+firstdate+" 00:00:00', 'YYYY-MM-DD HH24:MI:SS') and "
               + "time <= TO_TIMESTAMP('"+lastyear+"-"+lastMonth+"-"+lastdate+" 23:59:59', 'YYYY-MM-DD HH24:MI:SS') and kind = "+kind+" ORDER  by time desc");
      }
      
      
   
      while (rs.next()) {
         String id = rs.getString("ID");
         String money = rs.getInt("money")+"";
         String coinnum = rs.getInt("coinnum")+"";
         String time = rs.getString("time");
         
         String [] arr = {id,money,coinnum,time};
         
         resarr.add(arr);
      }
      
      retarr = new String[resarr.size()][];
      for (int i = 0; i < retarr.length; i++) {
         retarr[i] = resarr.get(i);
      }
      System.out.println("����");
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
   
   
   
   
   
   
   
   
static String [][] getMONEYLOG(int kind) {
      
      
      
      Connection con = null;
      Statement stmt=null;
      ResultSet rs =null;
      String [][] retarr=null;
      
      ArrayList<String []> resarr = new ArrayList<String[]>();
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         
         con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
         
         stmt = con.createStatement();
         
         rs = stmt.executeQuery("select * from MONEYLOG where kind= '"+kind+"'");
         
         while (rs.next()) {
            String id = rs.getString("ID");
            String money = rs.getInt("money")+"��";
            String coinnum = rs.getInt("coinnum")+"��";
            String time = rs.getString("time");
            
            String [] arr = {id,money,coinnum,time};
            
            resarr.add(arr);
         }
         
         retarr = new String[resarr.size()][];
         for (int i = 0; i < retarr.length; i++) {
            retarr[i] = resarr.get(i);
         }
         System.out.println("����");
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
   
   
   static boolean saveMONEYLOG(String userID, int money, int coinnum, int kind) {
      boolean res = false;
      
      Connection con = null;
      Statement stmt=null;
      
      
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         
         con = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":1521:xe", "HR", "HR");
         
         stmt = con.createStatement();
         
         stmt.executeUpdate("INSERT INTO MONEYLOG (ID, MONEY, COINNUM, TIME, KIND) VALUES ('"+userID+"', '"+money+"', '"+coinnum+"', sysdate, '"+kind+"')");
         System.out.println("����");
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
      getMONEYLOG("admin",1);
      saveMONEYLOG("admin", 10000, 100,0);
   }

}