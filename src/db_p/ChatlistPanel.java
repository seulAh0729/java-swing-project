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
      setText("ä�ù�����");
      setBounds(350, 60, 110, 30);
      addActionListener(this);
      setEnabled(false);

   }

   @Override
   public void actionPerformed(ActionEvent e) {
      
      
      if(ucl.ucc == null) {
         
         if(kind==0) {
            System.out.println("�Ϲ�");
            ucl.ucc = new ChatFrame(kind, userID, sellerID, chatdate, ucl,chatmenu);
//         new User_Chat_Connect(kind, userID, sellerID,chatdate);
         }else {
            System.out.println("������");
            ucl.ucc = new ChatFrame(kind, sellerID, userID,chatdate, ucl,chatmenu);
         }
      } 
      
      else {System.out.println("�ȸԾ� �ȵ���");}
      

   }



}

public class ChatlistPanel extends JScrollPane  { // ä���� ������ ������ ä�ø���Ʈ �г�

   String userID;
   String sellerID;

   static boolean chk= true;
  
   JLabel id;
   ArrayList<JButton> jbs;
   ArrayList<Chatlist> cc;
   Chat_List_Timer ch;
   ChatFrame ucc;
   
   

   public ChatlistPanel(String userID) {
      chk =false; // ��羲���带 ���̱�
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
   

      JPanel chat = new JPanel(); // ��ũ���гο� ���� �г�

      Dimension size = new Dimension(); // ����� �����ϱ� ���� ��ü
      int a =  (cc.size() * 170);
      if(a==0) {
         a=50;
      }
      size.setSize(480, a); // ������ ����
      chat.setPreferredSize(size); // ������ ������ ������ �ִ� ��ü�� �̿��� �г��� ������ ����
      setViewportView(chat); // ��ũ�� �г� ���� �г� �ø���

      setBounds(0, 0, 500, 670); // ��ũ���г� ��ġ
      chat.setLayout(null);

      jbs = new ArrayList<JButton>();
      int cnt = 0;
      String[] name = { "������: ", "�մ�: ", "����ð�: ", "�����޴�: " };
      for (int i = 0; i < cc.size(); i++) {
         JLabel test = new JLabel(); // �гο� �� ä�� ����Ʈ ��
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
         test.add(chb); // �󺧿� ��ư �ֱ�
         chat.add(test); // �ٸ��� ���� �гο� �ֱ�
      }
      System.out.println(cc.size());
      if(cc.isEmpty()) {
        System.out.println("������?");
        JLabel emptypanel = new JLabel("ä�ù��� �����ϴ�.",JLabel.CENTER);
        emptypanel.setBounds(0,0,480,50);
        emptypanel.setVisible(true);
        chat.add(emptypanel); 
      }
      chk = true; //��������� �츮��
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