package db_p;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.Collections;
import java.util.HashMap;

import javax.swing.JOptionPane;






public class ProfileServer {
   
   public ProfileServer() {
	
      
     
      
      try {
         ServerSocket server = new ServerSocket(8888);   
         
         System.out.println("������ ���� �ξƿ� ��������");
         
         while(true) {
            Socket client = server.accept();
            System.out.println("���Ἲ��");
            chkclass chk = new chkclass();
            MulReceiver mulReceiver =  new MulReceiver(client,chk);
          
            mulReceiver.start();
           
         }
         
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   
   }
   
   class chkclass{
	   boolean chk = true;
   }
   
   class MulReceiver extends Thread{
      
      String name;
      ObjectOutputStream dos;
      ObjectInputStream dis;
      chkclass chk;
      public MulReceiver(Socket client, chkclass chk) {
         this.chk = chk;
         try {
            System.out.println(client.getInetAddress().toString());
           
            dos = new ObjectOutputStream(client.getOutputStream());
            dis = new ObjectInputStream(client.getInputStream());
            
            
         } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         
      }
      
      
      @Override
      public void run() {
         // TODO Auto-generated method stub
         
         
         try {
        	
            while(dis!=null&&chk.chk) {
            try {
            	LetterClass contant=null;
            	try {
            		
            		contant = (LetterClass) dis.readObject();
					System.out.println("�޾Ҿ�");
				} catch (Exception e) {
					
				}
            	
            	if(contant.kind.equals("upload")) {
            		upload(contant);
            		System.out.println("�ø���");
            	}else if(contant.kind.equals("download")){
            		download(contant);
            		System.out.println("�ٿ�ε�");
            	}else if(contant.kind.equals("login")) {
            		name=contant.from_id;
            		System.out.println(contant.from_id+"�α���");
            	}
            	
            	
				
			} catch (Exception e) {
				// TODO: handle exception
			}
            }

         } catch (Exception e) {
        	UserDB.setLOGINCHK(name, "false");
           System.out.println(name+ "�α׾ƿ�");
         }finally {
   
            
            try {
               dis.close();
               dos.close();
            } catch (IOException e) {
              
            }
            
         }   
      }
   
      void upload(LetterClass co) {
    	  try {
    		  FileOutputStream fio = new FileOutputStream("icon\\"+co.filename);
    		  fio.write(co.filebyte);
    		  fio.close();
    	  } catch (Exception e) {
    		  e.printStackTrace();
    	  }
      }
      
      void download(LetterClass co) {
    	  try {
    		  File file = new File("icon\\"+co.filename);
    		  byte[]  buf;
    		  if(!file.exists()) {
    			  FileInputStream fis = new FileInputStream("icon\\smile.png");
    			  buf = new byte[fis.available()];
    			  fis.read(buf);
    			  fis.close();
    			  System.out.println("����");
    		  }else {
    			  FileInputStream fis = new FileInputStream(file);
    			  buf = new byte[fis.available()];
    			  fis.read(buf);
    			  fis.close();
    			  System.out.println("�־�");
    		  }
    		  dos.writeObject(new LetterClass("", "", "", "", buf));
    		  
    		  
    	  } catch (Exception e) {
    		  e.printStackTrace();
    	  }
    	  
      }
      
      
      
      
      
   }
   
   class LoginReceiver extends Thread{
	      
	      String name;
	      ObjectOutputStream dos;
	      ObjectInputStream dis;
	      chkclass chk;
	      public LoginReceiver(Socket client, chkclass chk) {
	         this.chk =chk;
	         try {
    
	            dos = new ObjectOutputStream(client.getOutputStream());
	            dis = new ObjectInputStream(client.getInputStream());
	            System.out.println("�޳�?2");
	         } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	         }
	         
	      }
	      
	      
	      @Override
	      public void run() {
	         System.out.println("������?");
	         
	         try {
	        	 while (name!=null) {
	        		 System.out.println("����?");
	        		 name = dis.readUTF();
	        		 System.out.println(name + " �α���");	
	        	 }

	            while(dis!=null&&dos!=null&&chk.chk) {

	            sleep(1000);
	           
	            dos.writeByte(1);
			
	            }

	         } catch (Exception e) {
	        	 System.out.println(name + " �α׾ƿ�");
	         }finally {
	            
	        	
         
	            try {
	               chk.chk =false;
	               dis.close();
	               dos.close();
	            } catch (IOException e) {
	               // TODO Auto-generated catch block
	               e.printStackTrace();
	            }
	            
	         }   
	      }
	   
	     
	      
	   
   }

   
   
   

   public static void main(String[] args) {
      
      
      new ProfileServer();

   }

}