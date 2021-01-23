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




public class ChatFrame extends JFrame implements ActionListener, WindowListener { // 채팅방 입장 누르면 뜨는 프레임(대화창)

  
   ChatRecord cr; // 채팅 대화 기록 뜨게 하는 패널클래스
   Chatwrite cw; // 채팅 기능, 텍스트공간 가지고 있는 패널클래스
   JPanel chatrecord; // 보이는 채팅내용 공간(패널)
   String msg; // 메세지
   SingleSender ss; // 싱글센터
   SingleReciever sr; // 싱글리시버
   String ipadd = IP_NumSet.host; // ip적힌
   int portnum = 7777; // 포트넘버
   Dimension size2; // chatrecord(채팅대화 기록 뜨게하는패널 클래스) 사이즈 잡기
   Timer timerTH; // 남은 시간 알려주는 스레드상속 클래스
   JLabel timer; // 남은 시간 띄워주는 라벨
   JTextArea reviewWrite; // 리뷰쓰는 텍스트공간
   JRadioButton[] score; // 평점버튼
   JFrame review; // 리뷰쓰는 프레임
   FileDialog fd;   //이미지파일
   ObjectOutputStream dos; // 데이터 내보내는 객체;-
   JFrame f4;
   String userID;
   String sellID;
   LetterClass content = null;
   JButton sendB;  //전송버튼
   int kind;   //0 손님 1 점술가 2관리자
   Date chatdate;
   ChatFrame ucc;
   ChatlistPanel ucl;
   String chatmenu;
   class ChatRecord extends JScrollPane { // 채팅 대화 기록 뜨는곳

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

   class Chatwrite extends JPanel { // 채팅치는 패널

      JTextArea chatwrite; // 채팅치는텍스트공간

      public Chatwrite() {

         setBounds(0, 540, 515, 230); // 패널위치
         setBackground(Color.white);
         setOpaque(true);
         setLayout(null);

         chatwrite = new JTextArea(); // 채팅 치는곳
         chatwrite.setBackground(Color.white);
         chatwrite.setLineWrap(true);
         chatwrite.setFont(new Font("고딕", Font.BOLD, 15));

         JScrollPane chatwriteJS = new JScrollPane(chatwrite);
         chatwriteJS.setBackground(Color.white);
         chatwriteJS.setBounds(0, -1, 420, 170);
         add(chatwriteJS);
      }

      String getchat() { // 전송버튼 누르면 채팅쓰는칸 비워주는(지워주는) 메소드

         String res = chatwrite.getText();
         chatwrite.setText("");
         return res;
      }
   }

   class SingleReciever extends Thread {
      ObjectInputStream dis; // 데이터 받아오는 객체
      boolean close = false; // 이걸 true 로 바꾸면 연결이 끊김(스레드가 죽음), 연결을 끊고 싶을때 true로 바꿔주면댐

      public SingleReciever(Socket socket) {

         try {
            dis = new ObjectInputStream(socket.getInputStream());
            // 매개변수로 들어오는 소켓 객체의 input
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
            while (dis != null && close == false) { // 매개변수 input에 값이 있고, 연결이 된상태일때
               sleep(1);
//               Content content = null;
               try {
                  content = (LetterClass) dis.readObject(); // dis에 있는 내용 읽어서 content에 넣어주기
                  
               }catch (Exception e) {
            // TODO: handle exception
               }
               
               if (content != null) { // content에 내용이 있으면(채팅내용이 있으면)
                sleep(500);
                 JLabel speechbubble = new JLabel();// 말풍선라벨
                 
                  if(content.kind.equals("String")) {
                    String coment = content.from_id+" : "+content.coment;
                     System.out.println(coment.length());
                     speechbubble = new JLabel();// 말풍선라벨
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
                     JTextArea speech = new JTextArea(); // 말풍선에 들어갈 텍스트공간
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
                      speechbubble = new JLabel(ii,JLabel.LEFT);// 말풍선라벨
                      speechbubble.setLayout(null);
                      speechbubble.setVisible(false);
                      
                      
                      if(content.from_id.equals(userID)) {
                         speechbubble.setBounds(515-310, 50 + cnt2, 300, 200);
                      }else {
                         speechbubble.setBounds(10, 50 + cnt2, 300, 200);     
                      }
                      speechbubble.setOpaque(true);
                      
                      JButton jb = new JButton("저장");
                      jb.setBounds(200,125,80,50);
                      jb.addActionListener(new actl(content.filename, content.filebyte));
                      speechbubble.add(jb);
                      
                  }else {
                     
                      speechbubble = new JLabel("파일 : "+content.filename);// 말풍선라벨
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
                      
                      JButton jb = new JButton("저장");
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

   boolean go = false; // 메세지 보낸 대기상태 true가 되면 메세지를 보내고
                  // false는 대기상태

   class SingleSender extends Thread {

      
      boolean close = false;// 이걸 true 로 바꾸면 연결이 끊김(스레드가 죽음), 연결을 끊고 싶을때 true로 바꿔주면댐

      public SingleSender(Socket socket) {
         try {
            dos = new ObjectOutputStream(socket.getOutputStream());
            // dos에 매개변수로 들어온 소켓의 output
            dos.writeUTF(userID);
            sleep(10);
            dos.writeObject(new LetterClass(sellID, userID, "님이 입장하셨습니다.", "String"));
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
                  go = false; // 메세지를 보냈으니까 다시 false로 바꿔줌(대기상태)
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

   class Timer extends Thread { // 남은시간 타이머

      int sec;
      int mi;
      boolean close = false;
      public Timer() {
        
         Date d1 = new Date(); // 예약된시간
         
         d1 = (Date) chatdate.clone(); // 예약된시간
        
         Date d2 = new Date(); // 1대1 채팅에 들어온시간
         long diff = d1.getTime() - d2.getTime(); // 예약된시간에서 1대1채팅 들어온시간의 차이

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
            timer.setText("남은시간 "+i+"분 "+j+"초");
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

      JButton photo = new JButton(ii); // 프로필 보는 버튼
      photo.setName("프로필");
      photo.setBounds(10, 19, 60, 55);
      photo.setBorderPainted(true);
      photo.addActionListener(this);

      JLabel name = new JLabel(); // 상대방 이름 띄워주기
      // 손님이랑 점술가 나눠주기 - 0은 손님
      name.setText(sellID);
      name.setBounds(80, 22, 200, 30);
      name.setFont(new Font("고딕", Font.BOLD, 20));

      timer = new JLabel("남은시간");
      timer.setBounds(360, 8, 200, 50);
      timer.setBackground(Color.blue);

      sendB = new JButton("전송");
      sendB.setName("전송");
      sendB.setBounds(430, 10, 60, 30);
      sendB.setBackground(Color.yellow);
      sendB.setBorderPainted(false); // 버튼 외곽선 없애기
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
      JButton filesend = new JButton(clip); // 파일첨부 버튼
      filesend.setName("파일첨부");
      filesend.setBounds(5, 175, 30, 30);
      filesend.setContentAreaFilled(false); // 버튼에 이미지 입히고 배경 투명화
      filesend.setBorderPainted(false); // 버튼 외곽선 없애기
      filesend.addActionListener(this);
      cw.add(filesend);

      ImageIcon exitP = new ImageIcon("icon\\exit.png");
      img = exitP.getImage();
      img = img.getScaledInstance(30, 30, img.SCALE_SMOOTH);
      exitP = new ImageIcon(img);
      JButton exit = new JButton(exitP); // 나가기 버튼
      exit.setName("나가기");
      exit.setBounds(450, 175, 30, 30);
      exit.setContentAreaFilled(false); // 버튼에 이미지 입히고 배경 투명화
      exit.setBorderPainted(false);
      exit.addActionListener(this);
      cw.add(exit);

      addWindowListener(this);
      setVisible(true);
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);

   }

   void connect() { // 연결하는 메소드

      if (ss != null && sr != null) { // ss
         ss.close = true;
         sr.close = true;
      }

      try {
         Socket socket = new Socket(ipadd, portnum);

//         JLabel chatin = new JLabel(userID + "님이 입장하였습니다.");
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
   void profile() { // 프로필창
     if(pro==null) {
      pro = new JFrame(); // 프로필 새창
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

   void chatout() { // 채팅나가기

	   
	   try {
	         dos.writeObject(new LetterClass(sellID, userID, "님이 퇴장하셨습니다. ", "String"));
	      } catch (IOException e2) {
	         // TODO Auto-generated catch block
	         e2.printStackTrace();
	      }
	   
	  timerTH.close =true;
      ss.close = true; // 연결끊어주기
      sr.close = true; // 연결끊어주기
      ucl.ucc = null;
     
      dispose();
      

      if(kind==0) {
         
         review = new JFrame("리뷰쓰기");
         review.getContentPane().setBackground(new Color(255, 254, 215));
         review.setBounds(250, 210, 600, 800);
         review.setLayout(null);
         
         JLabel reviewTitle = new JLabel("리뷰쓰기");
         reviewTitle.setBounds(250, 10, 100, 50);
         
         reviewWrite = new JTextArea();   //리뷰쓰는(글씨)곳
         reviewWrite.setLineWrap(true);
         
         JScrollPane reviewWriteJP = new JScrollPane(reviewWrite);
         reviewWriteJP.setBounds(20, 80, 540, 400);
         
         String[] radio = { "1점", "2점", "3점", "4점", "5점" };
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
         
         JButton up = new JButton("올리기");
         up.setName("올리기");
         up.setBounds(250, 650, 100, 50);
         up.addActionListener(this);
         
         review.add(up);
         review.add(reviewTitle);
         review.add(reviewWriteJP);
         review.setVisible(true);
      }

   }

   void fileAttach() { // 파일첨부

      f4 = new JFrame("파일확인하기");
      f4.setBounds(310, 210, 400, 300);
      f4.setLayout(null);
      fd = new FileDialog(f4, "파일열기", FileDialog.LOAD); // 파일 입출력
      fd.setLocation(510, 210);
      fd.setDirectory("D:\\lee");
      fd.setVisible(true);

      if(fd.getFile()==null) {
        JOptionPane.showMessageDialog(null, "경로설정안함!");
        return;  
     }
      JLabel filenameChk = new JLabel(fd.getFile() + "을 보내시겠습니까?");
      filenameChk.setBounds(70, 40, 400, 60);

      JButton fileyes = new JButton("예");
      fileyes.setName("파일첨부수락");
      fileyes.setBounds(60, 150, 100, 50);
      f4.add(fileyes);
      fileyes.addActionListener(this);
     
      

      JButton fileno = new JButton("아니요");
      fileno.setName("파일첨부거부");
      fileno.setBounds(210, 150, 100, 50);
      fileno.addActionListener(this);
      f4.add(fileno);
      f4.add(filenameChk);

      f4.setVisible(true);
      
   }
   
   void filesave(String filename, byte [] data) { // 파일첨부

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
            // TODO 자동 생성된 catch 블록
            e.printStackTrace();
         }
            
            try {
            fis.write(data);
         } catch (IOException e1) {
            // TODO 자동 생성된 catch 블록
            e1.printStackTrace();
         }
            
            
            
            try {
            fis.close();
         } catch (IOException e) {
            // TODO 자동 생성된 catch 블록
            e.printStackTrace();
         }
           
         }else {
            JOptionPane.showMessageDialog(null, "경로를 선택하시지 않았습니다.");
            return;
         }
      }
  
   


   void uploadReview() {   //리뷰다쓰고 올리기버튼 누를때

      String finalreview = userID+" : " + reviewWrite.getText(); // 리뷰내용 담는곳
      System.out.println(finalreview);
      int finalscore=0; // 평점점수
      

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


      case "전송":
         if (ss != null) {
            msg = cw.getchat();
            go = true;
         }
         break;

      case "프로필":
         profile();
         break;

      case "나가기":
         chatout();
         break;

      case "파일첨부":
         
         fileAttach();
         break;

      case "올리기":
         // 점술가 id , 손님 id?
         uploadReview();
         break;
         
      case "파일첨부수락" :
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
         // TODO 자동 생성된 catch 블록
         e1.printStackTrace();
      }
        
         break;
         
      case "파일첨부거부" : 
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
         dos.writeObject(new LetterClass(sellID, userID, "님이 퇴장하셨습니다. ", "String"));
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