package db_p;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ProfileInOut {
   
   private static ProfileInOut me = new ProfileInOut();

   ObjectInputStream ois;
   ObjectOutputStream oos;
   private ProfileInOut() {
      try {
         Socket socket = new Socket("192.168.0.218", 8888);
         ois = new ObjectInputStream(socket.getInputStream());
         oos = new ObjectOutputStream(socket.getOutputStream());
         
      } catch (Exception e) {
         try {
            ois.close();
            oos.close();
         } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         }
         e.printStackTrace();
      }
   }
   
   void upload(String filepath, String id) {
      try {
         FileInputStream fis = new FileInputStream(filepath);
         byte[] buf = new byte[fis.available()];
         fis.read(buf);
         oos.writeObject(new LetterClass("","","upload",id+".jpg",buf));
         fis.close();
      } catch (Exception e) {
         // TODO: handle exception
      }
   }
   
   void login(String userID) {
	      try {
	    	 Thread.sleep(1000);
	         oos.writeObject(new LetterClass("", userID, "", "login"));
	         System.out.println(userID+" 로그인 성공");
	      } catch (Exception e) {
	         // TODO: handle exception
	      }
	   }
   
   byte[] download(String profileid) {
      LetterClass cc = null ;
      try {
      oos.writeObject(new LetterClass("", "", "download", profileid+".jpg", null));
       cc = (LetterClass) ois.readObject();
      } catch (Exception e) {
         // TODO: handle exception
      }
      return cc.filebyte;
   }
   
   static ProfileInOut getprofileInout() {
      return me;
   }

}