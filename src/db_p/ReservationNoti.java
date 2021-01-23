package db_p;

import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ReservationNoti extends Thread{

   String userID;
   ArrayList<Chatlist2> chatlists;
   ArrayList<Chatlist2> chatlists2;
   boolean close = false;
      
      public ReservationNoti(int kind, String userID) {
         
      this.userID = userID;
      if(kind==1) {
         start();
         
      }
//      ChatListDB.getCHATLIST(userID);
         
      }
      
      @Override
      public void run() {
      
         while(!close) {
            
            try {
               chatlists = ReservationDB.getRESERVATION(userID);
//               chatlists2 = chatlists;
               
               System.out.println(chatlists);
               System.out.println(chatlists2);
//               System.out.println(chatlists.equals(chatlists2));
//               System.out.println(ReservationDB.getRESERVATION(userID).get(0).sell_id);
               
               sleep(20000);
               System.out.println("ddd");
               chatlists2 = ReservationDB.getRESERVATION(userID);
               
               if(chatlists.size() != chatlists2.size()) {
                  System.out.println("예약이 추가 되어서 둘이 달라요");
                  
                   int tt = chatlists2.size()-chatlists.size();
                   int cnt = tt;
                   System.out.println("tt의 값"+tt);
                     
                   
                   for (int i = 0; i < tt; i++) {
                     System.out.println("포문들어왔습니다~");
                     JFrame reschk = new JFrame();
                     System.out.println(chatlists2.size());
                     
                     String str = chatlists2.get(chatlists2.size()-cnt).chattimestr+ " " + chatlists2.get(chatlists2.size()-cnt).chatmenu+"이 예약되었습니다.   확인해주세요 !";
                     
                     cnt--;
                     System.out.println(str+"\nstr넣엇어요~");
                     JLabel reschk2 = new JLabel(str, JLabel.CENTER);
                     reschk2.setFont(new Font("고딕", Font.BOLD, 20));
                     
                     System.out.println("라벨에 박았어요~");
                     reschk.add(reschk2);
                     reschk.setBounds(100, 100, 1000, 100);
                     
                     
                     
                     reschk.setVisible(true);
                      
                  }
//                     System.out.println(chatlists.get(i) == chatlists2.get(i));
                     
                  
                  
                  
//                  JOptionPane.showMessageDialog(null, "예약이 추가되었어요~! 확인해주세요!");
               }
               Date dd = new Date();
               ArrayList<Chatlist> aa = ChatListDB.getCHATLIST(userID);
               for (Chatlist chatlist : aa) {
                  if((((chatlist.chattime.getTime()-dd.getTime())/1000))/60==4) {
                     System.out.println("들어오니?");
                     JOptionPane.showMessageDialog(null, "채팅시작 5분전 입니다. 채팅을 준비해주세요"
                     +new SimpleDateFormat("hh:mm:ss").format(new Date()));
                  }
                  
               }
               
               
               
            } catch (InterruptedException e) {
               
               e.printStackTrace();
            }
         }
         
         
      }
      
   
   
   public static void main(String[] args) {
//      Test2 tt = new Test2(1,"이지연");   
//      tt.start();
      
   }

}