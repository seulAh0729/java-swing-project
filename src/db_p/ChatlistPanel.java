package db_p;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

class ChatInButton extends JButton implements ActionListener {

   
   String userID;
   String sellerID;
   Date chatdate;
   int kind;
   ChatlistPanel ucl;
   ChatFrame ucc;
   String chatmenu;
   public ChatInButton(int kind,String userID,String sellerID, Date chatdate, ChatlistPanel ucl,String chatmenu) {

      this.kind = kind;
      this.userID = userID;
      this.sellerID = sellerID;
      this.chatdate = chatdate;
      this.ucl = ucl;
      this.chatmenu= chatmenu;
      setText("채팅방입장");
      setBounds(350, 60, 110, 30);
      addActionListener(this);
      setEnabled(false);

   }

   @Override
   public void actionPerformed(ActionEvent e) {
      
      
      if(ucl.ucc == null) {
         
         if(kind==0) {
            System.out.println("일반");
            ucl.ucc = new ChatFrame(kind, userID, sellerID, chatdate, ucl,chatmenu);
//         new User_Chat_Connect(kind, userID, sellerID,chatdate);
         }else {
            System.out.println("점술가");
            ucl.ucc = new ChatFrame(kind, sellerID, userID,chatdate, ucl,chatmenu);
         }
      } 
      
      else {System.out.println("안먹어 안들어와");}
      

   }



}

public class ChatlistPanel extends JScrollPane  { // 채팅탭 누르면 나오는 채팅리스트 패널

   String userID;
   String sellerID;

   static boolean chk= true;
  
   JLabel id;
   ArrayList<JButton> jbs;
   ArrayList<Chatlist> cc;
   Chat_List_Timer ch;
   ChatFrame ucc;
   
   

   public ChatlistPanel(String userID) {
      chk =false; // 모든쓰레드를 죽이기
      this.userID = userID;
     
      
      
      cc = ChatListDB.getCHATLIST(userID);
      for (Chatlist chatlist : cc) {
         Date date = chatlist.chattime;
         Calendar cal = Calendar.getInstance();
         cal.setTime(date);
         cal.add(Calendar.HOUR, 1);
         date = cal.getTime();
         if(!date.after(new Date())) {
            ChatListDB.deleteCHATLIST(chatlist.sell_id, chatlist.user_id, chatlist.chattime, chatlist.chatmenu);
         }
         
      }
      cc = ChatListDB.getCHATLIST(userID);
   

      JPanel chat = new JPanel(); // 스크롤패널에 붙일 패널

      Dimension size = new Dimension(); // 사이즈를 지정하기 위한 객체
      int a =  (cc.size() * 170);
      if(a==0) {
         a=50;
      }
      size.setSize(480, a); // 사이즈 지정
      chat.setPreferredSize(size); // 사이즈 정보를 가지고 있는 객체를 이용해 패널의 사이즈 지정
      setViewportView(chat); // 스크롤 패널 위에 패널 올리기

      setBounds(0, 0, 500, 670); // 스크롤패널 위치
      chat.setLayout(null);

      jbs = new ArrayList<JButton>();
      int cnt = 0;
      String[] name = { "점술가: ", "손님: ", "예약시간: ", "점술메뉴: " };
      for (int i = 0; i < cc.size(); i++) {
         JLabel test = new JLabel(); // 패널에 들어갈 채팅 리스트 라벨
         test.setOpaque(true);
         test.setBackground(Color.white);
         test.setBounds(0, 0 + cnt, 480, 150);

         cnt += 170;


         int cnt2 = 0;
         
         id = new JLabel(name[0] + cc.get(i).sell_id);
         id.setBounds(0, 20 + cnt2, 300, 30);
         cnt2 += 20;
         test.add(id);
         
         id = new JLabel(name[1] + cc.get(i).user_id);
         id.setBounds(0, 20 + cnt2, 300, 30);
         cnt2 += 20;
         test.add(id);
         
         id = new JLabel(name[2] + cc.get(i).chattimestr);
         id.setBounds(0, 20 + cnt2, 300, 30);
         cnt2 += 20;
         test.add(id);
         
         id = new JLabel(name[3] + cc.get(i).chatmenu);
         id.setBounds(0, 20 + cnt2, 300, 30);
         cnt2 += 20;
         test.add(id);
         
         ChatInButton chb = new ChatInButton(UserDB.getUSERKIND(userID), cc.get(i).user_id, cc.get(i).sell_id, cc.get(i).chattime, this,cc.get(i).chatmenu);
         jbs.add(chb);
         test.add(chb); // 라벨에 버튼 넣기
         chat.add(test); // 다만든 라벨을 패널에 넣기
      }
      System.out.println(cc.size());
      if(cc.isEmpty()) {
        System.out.println("들어오니?");
        JLabel emptypanel = new JLabel("채팅방이 없습니다.",JLabel.CENTER);
        emptypanel.setBounds(0,0,480,50);
        emptypanel.setVisible(true);
        chat.add(emptypanel); 
      }
      chk = true; //내쓰레드는 살리기
      ch = new Chat_List_Timer();
      ch.start();
   }

   class Chat_List_Timer extends Thread {

      @Override
      public void run() {
         while (chk && ch != null) {
            for (int i = 0; i < cc.size(); i++) {
               Date date = cc.get(i).chattime;
               Calendar cal = Calendar.getInstance();
               cal.setTime(date);
               cal.add(Calendar.HOUR, 1);
               date = cal.getTime();
               if (!cc.get(i).chattime.after(new Date())&&date.after(new Date())) {
                  jbs.get(i).setEnabled(true);
               }
               
               
            }

         }

      }

   }



}