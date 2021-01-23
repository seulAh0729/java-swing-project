package db_p;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.Collections;
import java.util.HashMap;






public class ChatServer {
   
	HashMap<String, ObjectOutputStream> usermap;
   
   public ChatServer() {
	  usermap = new HashMap<String, ObjectOutputStream>();
      
      Collections.synchronizedMap(usermap);
      
      try {
         ServerSocket server = new ServerSocket(7777);   
         
         System.out.println("채팅 서버시작");
         
         while(true) {
            Socket client = server.accept();
            new MulReceiver(client).start();
         }
         
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   
   }
   
   class MulReceiver extends Thread{
      
      String name;
      ObjectOutputStream dos;
      ObjectInputStream dis;
      
      public MulReceiver(Socket client) {
         
         try {
            System.out.println(client.getInetAddress().toString());
            System.out.println(name);
            dos = new ObjectOutputStream(client.getOutputStream());
            dis = new ObjectInputStream(client.getInputStream());
            name = dis.readUTF();
            System.out.println(name+ " 채팅 서버 입장");
         } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         
      }
      
      
      @Override
      public void run() {
         // TODO Auto-generated method stub
         
         
         try {
            
            usermap.put(name, dos);
            
            while(dis!=null&&dos!=null) {
            	
            	LetterClass contant=null;
            try {
            	try {
            		
            		contant = (LetterClass) dis.readObject();
					
				} catch (Exception e) {
					
				}
            	
            	
				
			} catch (Exception e) {
				
			}
            sendToClass(contant);
            
            }

         } catch (Exception e) {
           e.printStackTrace();
         }finally {
            usermap.remove(name);
            
            
            try {
               dis.close();
               dos.close();
            } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
            
         }   
      }
   
   }

   void sendToClass(LetterClass co) throws Exception {
      String to_id = co.to_id;
      String from_id = co.from_id;
     
    	ObjectOutputStream from = usermap.get(from_id);
		from.writeObject(co);
		System.out.println(usermap);
		ObjectOutputStream to = usermap.get(to_id);
		if(!(to==null)) {
		to.writeObject(co);  
		}else {
			
		}

   }
   
   
   

   public static void main(String[] args) {
      
      
      new ChatServer();

   }

}