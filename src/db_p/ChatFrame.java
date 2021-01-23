package db_p;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.util.regex.Pattern;




public class ChatFrame extends JFrame implements ActionListener, WindowListener { // ä�ù� ���� ������ �ߴ� ������(��ȭâ)

  
   ChatRecord cr; // ä�� ��ȭ ��� �߰� �ϴ� �г�Ŭ����
   Chatwrite cw; // ä�� ���, �ؽ�Ʈ���� ������ �ִ� �г�Ŭ����
   JPanel chatrecord; // ���̴� ä�ó��� ����(�г�)
   String msg; // �޼���
   SingleSender ss; // �̱ۼ���
   SingleReciever sr; // �̱۸��ù�
   String ipadd = IP_NumSet.host; // ip����
   int portnum = 7777; // ��Ʈ�ѹ�
   Dimension size2; // chatrecord(ä�ô�ȭ ��� �߰��ϴ��г� Ŭ����) ������ ���
   Timer timerTH; // ���� �ð� �˷��ִ� �������� Ŭ����
   JLabel timer; // ���� �ð� ����ִ� ��
   JTextArea reviewWrite; // ���侲�� �ؽ�Ʈ����
   JRadioButton[] score; // ������ư
   JFrame review; // ���侲�� ������
   FileDialog fd;   //�̹�������
   ObjectOutputStream dos; // ������ �������� ��ü;-
   JFrame f4;
   String userID;
   String sellID;
   LetterClass content = null;
   JButton sendB;  //���۹�ư
   int kind;   //0 �մ� 1 ������ 2������
   Date chatdate;
   ChatFrame ucc;
   ChatlistPanel ucl;
   String chatmenu;
   class ChatRecord extends JScrollPane { // ä�� ��ȭ ��� �ߴ°�

      public ChatRecord() {

         chatrecord = new JPanel();
         chatrecord.setLayout(null);
         chatrecord.setOpaque(true);

         size2 = new Dimension();
         size2.setSize(500, 50);
         chatrecord.setPreferredSize(size2);
         setViewportView(chatrecord);
         
         setBounds(0, 90, 500, 450);
         setOpaque(true);
         chatrecord.setBackground(Color.white);
      }
   }

   class Chatwrite extends JPanel { // ä��ġ�� �г�

      JTextArea chatwrite; // ä��ġ���ؽ�Ʈ����

      public Chatwrite() {

         setBounds(0, 540, 515, 230); // �г���ġ
         setBackground(Color.white);
         setOpaque(true);
         setLayout(null);

         chatwrite = new JTextArea(); // ä�� ġ�°�
         chatwrite.setBackground(Color.white);
         chatwrite.setLineWrap(true);
         chatwrite.setFont(new Font("���", Font.BOLD, 15));

         JScrollPane chatwriteJS = new JScrollPane(chatwrite);
         chatwriteJS.setBackground(Color.white);
         chatwriteJS.setBounds(0, -1, 420, 170);
         add(chatwriteJS);
      }

      String getchat() { // ���۹�ư ������ ä�þ���ĭ ����ִ�(�����ִ�) �޼ҵ�

         String res = chatwrite.getText();
         chatwrite.setText("");
         return res;
      }
   }

   class SingleReciever extends Thread {
      ObjectInputStream dis; // ������ �޾ƿ��� ��ü
      boolean close = false; // �̰� true �� �ٲٸ� ������ ����(�����尡 ����), ������ ���� ������ true�� �ٲ��ָ��

      public SingleReciever(Socket socket) {

         try {
            dis = new ObjectInputStream(socket.getInputStream());
            // �Ű������� ������ ���� ��ü�� input
         } catch (Exception e) {
            e.printStackTrace();
         }

      }

      int cnt2 = 0;
      int tt = 25;
      
      class actl implements ActionListener{
       String filename;
       byte [] data;
      public actl(String filename, byte[] data) {
         super();
         this.filename = filename;
         this.data = data;
      }
      @Override
      public void actionPerformed(ActionEvent e) {
         filesave(filename, data);
         
      }
         
      }
      
      @Override
      public void run() {
         try {
            while (dis != null && close == false) { // �Ű����� input�� ���� �ְ�, ������ �Ȼ����϶�
               sleep(1);
//               Content content = null;
               try {
                  content = (LetterClass) dis.readObject(); // dis�� �ִ� ���� �о content�� �־��ֱ�
                  
               }catch (Exception e) {
            // TODO: handle exception
               }
               
               if (content != null) { // content�� ������ ������(ä�ó����� ������)
                sleep(500);
                 JLabel speechbubble = new JLabel();// ��ǳ����
                 
                  if(content.kind.equals("String")) {
                    String coment = content.from_id+" : "+content.coment;
                     System.out.println(coment.length());
                     speechbubble = new JLabel();// ��ǳ����
                     speechbubble.setVisible(false);
                     
                     int yy = (int)(coment.length()/30);
                     if(yy<=0) {
                        yy = 1;
                     }
                     yy += coment.split("\n").length-1;
                     if(content.from_id.equals(userID)) {
                        speechbubble.setBounds((515-58 * 8+80)-50, 50 + cnt2, 58 * 8-50, tt*yy);
                     }else {
                        speechbubble.setBounds(10, 50 + cnt2, 58 * 8, tt*yy);     
                     }
                     speechbubble.setOpaque(true);
                     speechbubble.setLayout(new GridLayout(1,1));
                     JTextArea speech = new JTextArea(); // ��ǳ���� �� �ؽ�Ʈ����
                     speech.setText(coment + "\n");
                     speech.setOpaque(true);
                     speech.setBackground(Color.LIGHT_GRAY);
                     speech.setEditable(false);
                     speech.setLineWrap(true);
                     speechbubble.add(speech);
                  }else if(content.kind.equals("image")){
                     
                     ImageIcon ii = new ImageIcon(content.filebyte);
                     Image i = ii.getImage();
                     Image i2 = i.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                     ii = new ImageIcon(i2);
                      speechbubble = new JLabel(ii,JLabel.LEFT);// ��ǳ����
                      speechbubble.setLayout(null);
                      speechbubble.setVisible(false);
                      
                      
                      if(content.from_id.equals(userID)) {
                         speechbubble.setBounds(515-310, 50 + cnt2, 300, 200);
                      }else {
                         speechbubble.setBounds(10, 50 + cnt2, 300, 200);     
                      }
                      speechbubble.setOpaque(true);
                      
                      JButton jb = new JButton("����");
                      jb.setBounds(200,125,80,50);
                      jb.addActionListener(new actl(content.filename, content.filebyte));
                      speechbubble.add(jb);
                      
                  }else {
                     
                      speechbubble = new JLabel("���� : "+content.filename);// ��ǳ����
                      speechbubble.setVerticalAlignment(JLabel.TOP);
                      speechbubble.setHorizontalAlignment(JLabel.CENTER);
                      speechbubble.setLayout(null);
                      speechbubble.setVisible(false);
                      
                      
                      if(content.from_id.equals(userID)) {
                         speechbubble.setBounds(515-230, 50 + cnt2, 200, 100);
                      }else {
                         speechbubble.setBounds(10, 50 + cnt2, 200, 100);     
                      }
                      speechbubble.setOpaque(true);
                      
                      JButton jb = new JButton("����");
                      jb.setBounds(75,50,80,50);
                      jb.addActionListener(new actl(content.filename, content.filebyte));
                      speechbubble.add(jb);
                  }
                  
                  
                  cnt2 += speechbubble.getHeight()+10;
                  chatrecord.add(speechbubble);
                  size2.setSize(480, cnt2+35);
                  chatrecord.setSize(size2);
                  speechbubble.setVisible(true);
                  cr.getVerticalScrollBar().setValue(cr.getVerticalScrollBar().getMaximum());
                  
               }
            }
         } catch (Exception e) {
            e.printStackTrace();
         } finally {
            try {
               dis.close();
            } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         }
      }



   
   }

   boolean go = false; // �޼��� ���� ������ true�� �Ǹ� �޼����� ������
                  // false�� ������

   class SingleSender extends Thread {

      
      boolean close = false;// �̰� true �� �ٲٸ� ������ ����(�����尡 ����), ������ ���� ������ true�� �ٲ��ָ��

      public SingleSender(Socket socket) {
         try {
            dos = new ObjectOutputStream(socket.getOutputStream());
            // dos�� �Ű������� ���� ������ output
            dos.writeUTF(userID);
            sleep(10);
            dos.writeObject(new LetterClass(sellID, userID, "���� �����ϼ̽��ϴ�.", "String"));
         } catch (Exception e) {
            e.printStackTrace();
         }
      }

      @Override
      public void run() {
         try {
            while (dos != null && close == false) {
               sleep(1);
               if (go) {
                  String content = msg;
                  dos.writeObject(new LetterClass(sellID, userID, content, "String"));
                  go = false; // �޼����� �������ϱ� �ٽ� false�� �ٲ���(������)
               }
            }
         } catch (Exception e) {
            e.printStackTrace();
         }
         finally {
            try {
               dos.close();
            } catch (Exception e2) {
               e2.printStackTrace();
            }
         }
      }
   }

   class Timer extends Thread { // �����ð� Ÿ�̸�

      int sec;
      int mi;
      boolean close = false;
      public Timer() {
        
         Date d1 = new Date(); // ����Ƚð�
         
         d1 = (Date) chatdate.clone(); // ����Ƚð�
        
         Date d2 = new Date(); // 1��1 ä�ÿ� ���½ð�
         long diff = d1.getTime() - d2.getTime(); // ����Ƚð����� 1��1ä�� ���½ð��� ����

         d1.setTime(diff);
         try {
         sleep(1000);
      } catch (InterruptedException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
         SimpleDateFormat sdf = new SimpleDateFormat("m-s");
         String str = sdf.format(d1);
         mi = Integer.parseInt(str.split("-")[0]);
         System.out.println(mi);
         sec = Integer.parseInt(str.split("-")[1]);
         System.out.println(sec);
      }

      @Override
      public void run() {
        
         for (int i = mi; i >= 0; i--) {
            if(close) {
               return;
            }
            if(i!=mi) {
               sec = 60;
            }
         for (int j = sec ; j > 0; j--) {
           if(close) {
              return;
           }
            try {
               sleep(1000);
            } catch (InterruptedException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
            timer.setText("�����ð� "+i+"�� "+j+"��");
         }
      }
         chatout();
      }
   }
   
   public ChatFrame(int kind, String user1, String user2, Date chatdate, ChatlistPanel ucl,String chatmenu) {
      System.out.println(user1+" , "+user2);
     this.kind = kind;
     this.userID = user1;
     this.sellID = user2;
     this.chatdate = chatdate;
     this.ucl = ucl;
     this.chatmenu =chatmenu;
      setBounds(700, 300, 515, 800);
      setLayout(null);
      System.out.println(sellID);

      connect();
      
      ImageIcon ii =  new ImageIcon(pfio.download(sellID));
      Image img = ii.getImage();
      img = img.getScaledInstance(60, 55, img.SCALE_SMOOTH);
      ii = new ImageIcon(img);

      JButton photo = new JButton(ii); // ������ ���� ��ư
      photo.setName("������");
      photo.setBounds(10, 19, 60, 55);
      photo.setBorderPainted(true);
      photo.addActionListener(this);

      JLabel name = new JLabel(); // ���� �̸� ����ֱ�
      // �մ��̶� ������ �����ֱ� - 0�� �մ�
      name.setText(sellID);
      name.setBounds(80, 22, 200, 30);
      name.setFont(new Font("���", Font.BOLD, 20));

      timer = new JLabel("�����ð�");
      timer.setBounds(360, 8, 200, 50);
      timer.setBackground(Color.blue);

      sendB = new JButton("����");
      sendB.setName("����");
      sendB.setBounds(430, 10, 60, 30);
      sendB.setBackground(Color.yellow);
      sendB.setBorderPainted(false); // ��ư �ܰ��� ���ֱ�
      sendB.addActionListener(this);

      add(timer);
      add(name);
      add(photo);
//      add(join);
      add(cr = new ChatRecord());
      add(cw = new Chatwrite());
      cw.add(sendB);

      ImageIcon clip = new ImageIcon("icon\\clip.png");
      img = clip.getImage();
      img = img.getScaledInstance(30, 30, img.SCALE_SMOOTH);
      clip = new ImageIcon(img);
      JButton filesend = new JButton(clip); // ����÷�� ��ư
      filesend.setName("����÷��");
      filesend.setBounds(5, 175, 30, 30);
      filesend.setContentAreaFilled(false); // ��ư�� �̹��� ������ ��� ����ȭ
      filesend.setBorderPainted(false); // ��ư �ܰ��� ���ֱ�
      filesend.addActionListener(this);
      cw.add(filesend);

      ImageIcon exitP = new ImageIcon("icon\\exit.png");
      img = exitP.getImage();
      img = img.getScaledInstance(30, 30, img.SCALE_SMOOTH);
      exitP = new ImageIcon(img);
      JButton exit = new JButton(exitP); // ������ ��ư
      exit.setName("������");
      exit.setBounds(450, 175, 30, 30);
      exit.setContentAreaFilled(false); // ��ư�� �̹��� ������ ��� ����ȭ
      exit.setBorderPainted(false);
      exit.addActionListener(this);
      cw.add(exit);

      addWindowListener(this);
      setVisible(true);
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);

   }

   void connect() { // �����ϴ� �޼ҵ�

      if (ss != null && sr != null) { // ss
         ss.close = true;
         sr.close = true;
      }

      try {
         Socket socket = new Socket(ipadd, portnum);

//         JLabel chatin = new JLabel(userID + "���� �����Ͽ����ϴ�.");
//         chatrecord.setVisible(false);
//         chatin.setBounds(5, 5, 400, 40);
//         chatrecord.add(chatin);
//         chatrecord.setVisible(true);
         
         
         

         ss = new SingleSender(socket);
         sr = new SingleReciever(socket);
         timerTH = new Timer();

         ss.start();
         sr.start();
         timerTH.start();

      } catch (Exception e) {
         // TODO: handle exception
         e.printStackTrace();
      }

   }
   JFrame pro ;
   ProfileInOut pfio = ProfileInOut.getprofileInout();
   void profile() { // ������â
     if(pro==null) {
      pro = new JFrame(); // ������ ��â
      pro.setBounds(100, 100, 400, 350);
      pro.setLayout(new GridLayout(1,1));
      DetailFrame df = new DetailFrame(sellID,UserDB.getUSERKIND(userID));
      pro.add(df.jp);
      df = null;
      pro.addWindowListener(new WindowListener() {
      
      public void windowOpened(WindowEvent e) {}
      
      public void windowIconified(WindowEvent e) {}
      
      public void windowDeiconified(WindowEvent e) {}
      
      public void windowDeactivated(WindowEvent e) {}
      
      
      public void windowClosing(WindowEvent e) {
         pro=null;
      }

      public void windowClosed(WindowEvent e) {}

      public void windowActivated(WindowEvent e) {}
   });
      pro.setVisible(true);
     }
   }

   void chatout() { // ä�ó�����

	   
	   try {
	         dos.writeObject(new LetterClass(sellID, userID, "���� �����ϼ̽��ϴ�. ", "String"));
	      } catch (IOException e2) {
	         // TODO Auto-generated catch block
	         e2.printStackTrace();
	      }
	   
	  timerTH.close =true;
      ss.close = true; // ��������ֱ�
      sr.close = true; // ��������ֱ�
      ucl.ucc = null;
     
      dispose();
      

      if(kind==0) {
         
         review = new JFrame("���侲��");
         review.getContentPane().setBackground(new Color(255, 254, 215));
         review.setBounds(250, 210, 600, 800);
         review.setLayout(null);
         
         JLabel reviewTitle = new JLabel("���侲��");
         reviewTitle.setBounds(250, 10, 100, 50);
         
         reviewWrite = new JTextArea();   //���侲��(�۾�)��
         reviewWrite.setLineWrap(true);
         
         JScrollPane reviewWriteJP = new JScrollPane(reviewWrite);
         reviewWriteJP.setBounds(20, 80, 540, 400);
         
         String[] radio = { "1��", "2��", "3��", "4��", "5��" };
         ButtonGroup allgrade = new ButtonGroup();
         score = new JRadioButton[5];
         
         int cnt = 0;
         for (int i = 0; i < score.length; i++) {
            score[i] = new JRadioButton(radio[i]);
            score[i].setBounds(85 + cnt, 570, 50, 30);
            allgrade.add(score[i]);
            review.add(score[i]);
            cnt += 90;
         }
         
         JButton up = new JButton("�ø���");
         up.setName("�ø���");
         up.setBounds(250, 650, 100, 50);
         up.addActionListener(this);
         
         review.add(up);
         review.add(reviewTitle);
         review.add(reviewWriteJP);
         review.setVisible(true);
      }

   }

   void fileAttach() { // ����÷��

      f4 = new JFrame("����Ȯ���ϱ�");
      f4.setBounds(310, 210, 400, 300);
      f4.setLayout(null);
      fd = new FileDialog(f4, "���Ͽ���", FileDialog.LOAD); // ���� �����
      fd.setLocation(510, 210);
      fd.setDirectory("D:\\lee");
      fd.setVisible(true);

      if(fd.getFile()==null) {
        JOptionPane.showMessageDialog(null, "��μ�������!");
        return;  
     }
      JLabel filenameChk = new JLabel(fd.getFile() + "�� �����ðڽ��ϱ�?");
      filenameChk.setBounds(70, 40, 400, 60);

      JButton fileyes = new JButton("��");
      fileyes.setName("����÷�μ���");
      fileyes.setBounds(60, 150, 100, 50);
      f4.add(fileyes);
      fileyes.addActionListener(this);
     
      

      JButton fileno = new JButton("�ƴϿ�");
      fileno.setName("����÷�ΰź�");
      fileno.setBounds(210, 150, 100, 50);
      fileno.addActionListener(this);
      f4.add(fileno);
      f4.add(filenameChk);

      f4.setVisible(true);
      
   }
   
   void filesave(String filename, byte [] data) { // ����÷��

         String filepath =null;
         JFileChooser jf = new JFileChooser();
         File file;
         jf.setCurrentDirectory(new File("C:\\"));
         jf.setFileSelectionMode(jf.DIRECTORIES_ONLY);
         int re = jf.showSaveDialog(null);
         if(re == JFileChooser.APPROVE_OPTION) {
            file = jf.getSelectedFile();
            filepath = file.getAbsolutePath()+"\\"+filename;
            FileOutputStream fis=null;
            try {
            fis = new FileOutputStream(filepath);
         } catch (FileNotFoundException e) {
            // TODO �ڵ� ������ catch ���
            e.printStackTrace();
         }
            
            try {
            fis.write(data);
         } catch (IOException e1) {
            // TODO �ڵ� ������ catch ���
            e1.printStackTrace();
         }
            
            
            
            try {
            fis.close();
         } catch (IOException e) {
            // TODO �ڵ� ������ catch ���
            e.printStackTrace();
         }
           
         }else {
            JOptionPane.showMessageDialog(null, "��θ� �����Ͻ��� �ʾҽ��ϴ�.");
            return;
         }
      }
  
   


   void uploadReview() {   //����پ��� �ø����ư ������

      String finalreview = userID+" : " + reviewWrite.getText(); // ���䳻�� ��°�
      System.out.println(finalreview);
      int finalscore=0; // ��������
      

      if (score[0].isSelected()) {
         finalscore = 1;
         System.out.println(finalscore);
      } else if (score[1].isSelected()) {
         finalscore = 2;
         System.out.println(finalscore);
      } else if (score[2].isSelected()) {
         finalscore = 3;
         System.out.println(finalscore);
      } else if (score[3].isSelected()) {
         finalscore = 4;
         System.out.println(finalscore);
      } else if (score[4].isSelected()) {
         finalscore = 5;
         System.out.println(finalscore);
      }

      ReviewDB.saveREVIEW(sellID, finalreview, finalscore);
      ChatListDB.deleteCHATLIST(sellID, userID, chatdate, chatmenu);
      review.dispose();

   }
   
   

   public static void main(String[] args) {

//      new User_Chat_Connect();

   }

   @Override
   public void actionPerformed(ActionEvent e) {
      JButton jb = (JButton) e.getSource();

      switch (jb.getName()) {


      case "����":
         if (ss != null) {
            msg = cw.getchat();
            go = true;
         }
         break;

      case "������":
         profile();
         break;

      case "������":
         chatout();
         break;

      case "����÷��":
         
         fileAttach();
         break;

      case "�ø���":
         // ������ id , �մ� id?
         uploadReview();
         break;
         
      case "����÷�μ���" :
        FileInputStream fis;
      try {
         System.out.println(fd.getDirectory()+fd.getFile());
         fis = new FileInputStream(fd.getDirectory()+fd.getFile());
         byte [] data = new byte [fis.available()];
         
         fis.read(data);
         System.out.println(fd.getFile());
         System.out.println(fd.getFile().toString().substring(fd.getFile().toString().indexOf(".")+1).toLowerCase());
         if(Pattern.matches("[jpg:jpeg:bmp:png:gif]*", fd.getFile().toString().substring(fd.getFile().toString().indexOf(".")+1).toLowerCase())) {
            dos.writeObject(new LetterClass(sellID, userID, "image", fd.getFile(), data));
         }else {
            dos.writeObject(new LetterClass(sellID, userID, "file", fd.getFile(), data));
         }
         
         fis.close();
         
         f4.dispose();
         
      } catch (Exception e1) {
         // TODO �ڵ� ������ catch ���
         e1.printStackTrace();
      }
        
         break;
         
      case "����÷�ΰź�" : 
         f4.dispose();

      }

   }

@Override
public void windowOpened(WindowEvent e) {
   // TODO Auto-generated method stub
   
}

@Override
public void windowClosing(WindowEvent e) {
	
	
	 try {
         dos.writeObject(new LetterClass(sellID, userID, "���� �����ϼ̽��ϴ�. ", "String"));
      } catch (IOException e2) {
         // TODO Auto-generated catch block
         e2.printStackTrace();
      }
	
	
   ucl.ucc = null;
   ss.close = true;
   sr.close = true;
   timerTH.close = true;
   
}

@Override
public void windowClosed(WindowEvent e) {
   // TODO Auto-generated method stub
   
}

@Override
public void windowIconified(WindowEvent e) {
   // TODO Auto-generated method stub
   
}

@Override
public void windowDeiconified(WindowEvent e) {
   // TODO Auto-generated method stub
   
}

@Override
public void windowActivated(WindowEvent e) {
   // TODO Auto-generated method stub
   
}

@Override
public void windowDeactivated(WindowEvent e) {
   // TODO Auto-generated method stub
   
}

}