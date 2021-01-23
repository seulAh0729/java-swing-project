package db_p;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import db_p.OptionPanel.GiveMessageClick;

// 관리자 설정 패널
class JumTalkOptionManager extends JPanel {

   NoticePanelManager noticePanel;
   MessagePanelManager messagePanel;
   String userID;

   public JumTalkOptionManager(String userID) {
      this.userID = userID;
      System.out.println("여기들어와?");
      noticePanel = new NoticePanelManager();
      messagePanel = new MessagePanelManager();
      setBounds(0, 200, 515, 600);
      setBackground(Color.white);
      JPanel jp = new JPanel();
      setLayout(new GridLayout(2, 1));
      jp.setLayout(new GridLayout(3, 1, 10, 10));

      jp.add(new JumOption());
      jp.add(noticePanel);
      jp.add(new JPanel());
      add(jp);
      add(messagePanel);

      setVisible(true);

   }

   class JumOption extends JPanel implements ActionListener {

      JButton opt1_Manager;
//               JButton opt2_Manager;
//               JButton opt3_Manager;
//               ReviseManager reviseManager;
//               CoinRevise coinRevise;

      public JumOption() {

         setBounds(0, 100, 515, 100);
         setLayout(new GridLayout());

         opt1_Manager = new JButton("로그아웃");
//                  opt2_Manager = new JButton("관리자 계정 생성");
//                  opt3_Manager = new JButton("코인 수정");

         add(opt1_Manager);
//                  add(opt2_Manager);
//                  add(opt3_Manager);

         opt1_Manager.addActionListener(this);
//                  opt2_Manager.addActionListener(this);
//                  opt3_Manager.addActionListener(this);

      }

      @Override
      public void actionPerformed(ActionEvent e) {
         if (e.getSource().equals(opt1_Manager)) {
            JOptionPane.showMessageDialog(null, "로그아웃 완료");
            System.exit(0);
         }
//                  else if (e.getSource().equals(opt2_Manager)) { // 개인정보 수정 클릭 했을 때,
//                     rm = new ReviseManager();
//                  } else if (e.getSource().equals(opt3_Manager)) {
//                     coinRevise = new CoinRevise();
//                  }

      }

   }

//   class CoinRevise extends JFrame implements ActionListener {
//
//      JLabel idLabel;
//      JLabel coinLabel;
//      JLabel coinNumLabel;
//      JLabel coinReviseLabel;
//
//      JButton idButton;
//      JButton complete;
//
//      JTextField idText;
//      JTextField coinText;
//
//      public CoinRevise() {
//         setTitle("코인 수정");
//         setBounds(700, 100, 400, 350);
//         setLayout(null);
//
//         idLabel = new JLabel("ID : ");
//         coinLabel = new JLabel("코인 개수 : ");
//         coinNumLabel = new JLabel();
//         idText = new JTextField();
//         idButton = new JButton("코인 확인");
//         coinReviseLabel = new JLabel("코인 : ");
//         coinText = new JTextField();
//         complete = new JButton("수정완료");
//
//         idLabel.setBounds(10, 10, 100, 50);
//         coinLabel.setBounds(10, 80, 100, 50);
//         coinNumLabel.setBounds(130, 80, 100, 50);
//         idText.setBounds(70, 10, 180, 50);
//         idButton.setBounds(270, 10, 100, 50);
//         coinReviseLabel.setBounds(10, 150, 100, 50);
//         coinText.setBounds(70, 150, 180, 50);
//         complete.setBounds(270, 150, 100, 50);
//
//         idButton.addActionListener(this);
//         complete.addActionListener(this);
//
//         add(idLabel);
//         add(coinLabel);
//         add(coinNumLabel);
//         add(idText);
//         add(idButton);
//         add(coinReviseLabel);
//         add(coinText);
//         add(complete);
//
//         setVisible(true);
//
//      }
//
//      @Override
//      public void actionPerformed(ActionEvent e) {
//
//         if (e.getSource().equals(idButton)) {
//            if (idText.getText().equals(UserDB.getID(idText.getText()))) {
//               coinNumLabel.setText(UserDB.getCOIN(idText.getText()) + "개");
//            } else {
//               JOptionPane.showMessageDialog(null, "없는 아이디 입니다.");
//            }
//         } else if (e.getSource().equals(complete)) {
//            UserDB.setCOIN(idText.getText(), Integer.parseInt(coinText.getText()));
//            JOptionPane.showMessageDialog(null, "수정완료");
//            setVisible(false);
//            
//         }
//
//      }
//
//   }

   // 공지사항 패널
   class NoticePanelManager extends JPanel implements ActionListener {

      JLabel noticeLabel;
      JButton noticeWrite;
      JButton noticeRevise;
      JFrame preFrame; // 현재뛰어져있는 프레임

      public NoticePanelManager() {

         setLayout(new GridLayout(2, 1));

         noticeLabel = new JLabel("공지사항 관리");
         noticeWrite = new JButton("공지사항 작성");
         noticeRevise = new JButton("공지사항 수정");
         JPanel jp = new JPanel();
         jp.setLayout(new GridLayout(1, 2, 10, 10));
         noticeWrite.addActionListener(this);
         noticeRevise.addActionListener(this);

         noticeLabel.setBounds(0, 0, 100, 30);
         noticeWrite.setBounds(0, 50, 243, 200);
         noticeRevise.setBounds(253, 50, 243, 200);

         add(noticeLabel);
         add(jp);
         jp.add(noticeWrite);
         jp.add(noticeRevise);

      }

      @Override
      public void actionPerformed(ActionEvent e) {
         if (preFrame == null) {
            if (e.getSource().equals(noticeWrite)) {// 공지 사항 작성
               preFrame = new NoticeWrite(this);
            } else if (e.getSource().equals(noticeRevise)) {
               preFrame = new NoticeRevise(this);
            }
         }
      }
   }

   // 메세지 패널
   class MessagePanelManager extends JPanel implements ActionListener {

      GiveMessageManager give;
      SendMessageManager send;
      JTextArea messageText;
      JTextArea toMessage;
      JScrollPane scroll;

      JButton sendMessage;
      JButton giveMessage;
      JButton complete;

      JLabel messageLabel;
      JLabel toLabel;
      JLabel content;
      JFrame preFrame;

      public MessagePanelManager() {

         setLayout(null);

         messageText = new JTextArea();
         scroll = new JScrollPane(messageText);
         toMessage = new JTextArea();
         sendMessage = new JButton("보낸 메세지함");
         giveMessage = new JButton("받은 메세지함");
         complete = new JButton("보내기");
         messageLabel = new JLabel("메세지 관리");
         toLabel = new JLabel("받는 사람");
         content = new JLabel("내용");

         sendMessage.addActionListener(this);
         giveMessage.addActionListener(this);
         complete.addActionListener(this);

         messageLabel.setBounds(0, 0, 100, 30);
         toLabel.setBounds(10, 30, 100, 30);
         content.setBounds(40, 70, 100, 30);
         scroll.setBounds(80, 70, 230, 160);
         toMessage.setBounds(80, 30, 230, 30);
         sendMessage.setBounds(330, 30, 150, 50);
         giveMessage.setBounds(330, 80, 150, 50);
         complete.setBounds(330, 150, 100, 80);

         messageText.setLineWrap(true);

         add(scroll);
         add(toMessage);
         add(sendMessage);
         add(giveMessage);
         add(complete);
         add(messageLabel);
         add(toLabel);
         add(content);

      }

      @Override
      public void actionPerformed(ActionEvent e) {
         if (e.getSource().equals(sendMessage)) {
            if (preFrame == null) {
               preFrame = new SendMessageManager(this);
               if (!((SendMessageManager) preFrame).chk) {
                  preFrame = null;
               }
            }
         } else if (e.getSource().equals(giveMessage)) {
            if (preFrame == null) {
               preFrame = new GiveMessageManager(this);
               if (!((GiveMessageManager) preFrame).chk) {
                  preFrame = null;
               }
            }
         } else if (e.getSource().equals(complete)) {
            if (messageText.getText().isEmpty() || toMessage.getText().isEmpty()) {
               JOptionPane.showMessageDialog(null, "빈 칸을 입력해 주세요");
            } else if (UserDB.getID(toMessage.getText()).equals("")) {
               JOptionPane.showMessageDialog(null, "없는 아이디 입니다");
            } else {
               MessageDB.saveMESSAGE(toMessage.getText(), "admin", messageText.getText());
               JOptionPane.showMessageDialog(null, "전송완료");
               messageText.setText("");
               toMessage.setText("");
            }
         }
      }
   }

   // 계정 생성 프레임
//   class ReviseManager extends JFrame implements ActionListener {
//
//      JLabel title;
//      JLabel idLabel;
//      JLabel pwLabel;
//      JButton idChk;
//      JButton complete;
//      JButton exit;
//
//      JTextField idField;
//      JTextField pwField;
//      boolean idChkBl = false;
//
//      public ReviseManager() {
//
//         setTitle("관리자 계정 생성");
//         setBounds(700, 100, 500, 400);
//         setLayout(null);
//
//         title = new JLabel("관리자 계정 생성");
//         idLabel = new JLabel("ID");
//         pwLabel = new JLabel("PW");
//         idChk = new JButton("중복확인");
//         complete = new JButton("완료");
//         exit = new JButton("나가기");
//         idField = new JTextField();
//         pwField = new JTextField();
//
//         title.setBounds(175, 0, 150, 50);
//         idLabel.setBounds(30, 100, 50, 50);
//         pwLabel.setBounds(30, 200, 50, 50);
//         idChk.setBounds(330, 100, 100, 50);
//         complete.setBounds(360, 300, 100, 50);
//         exit.setBounds(250, 300, 100, 50);
//         idField.setBounds(100, 100, 200, 50);
//         pwField.setBounds(100, 200, 200, 50);
//
//         idChk.addActionListener(this);
//         exit.addActionListener(this);
//         complete.addActionListener(this);
//
//         add(title);
//         add(idLabel);
//         add(pwLabel);
//         add(idChk);
//         add(complete);
//         add(exit);
//         add(idField);
//         add(pwField);
//
//         setVisible(true);
//      }
//
//      @Override
//      public void actionPerformed(ActionEvent e) {
//
//         String gettext = idField.getText();
//
//         if (e.getSource().equals(idChk)) {
//            if (idField.getText().equals("") || idField.getText().equals("")) {
//               JOptionPane.showMessageDialog(null, "빈칸을 입력하세요");
//            } else if (!UserDB.getID(gettext).equals("")) {
//               JOptionPane.showMessageDialog(null, "중복된 ID");
//            } else {
//               idChkBl = true;
//               JOptionPane.showMessageDialog(null, "사용가능");
//            }
//         } else if (e.getSource().equals(exit)) {
//            setVisible(false);
//         } else if (e.getSource().equals(complete)) {
//            if (idChkBl == true) {
//               UserDB.signupSUPERUSER(idField.getText(), pwField.getText());
//               JOptionPane.showMessageDialog(null, "생성완료");
//               setVisible(false);
//               idChkBl = false;
//            } else if (idChkBl == false) {
//               JOptionPane.showMessageDialog(null, "중복확인이 불가");
//            }
//         }
//      }
//   }

   Vector<String> noticeTitleVt = new Vector<String>();

   // 공지사항 작성 프레임
   class NoticeWrite extends JFrame implements ActionListener, WindowListener {

      JLabel titleLabel;
      JLabel noticeLable;
      JLabel writerLabel;
      JLabel writer;

      JButton complete;
      JTextArea titleText;
      JTextArea noticeText;
      NoticePanelManager noticePanelManager;

      public NoticeWrite(NoticePanelManager noticePanelManager) {
         this.noticePanelManager = noticePanelManager;
         setTitle("공지사항 작성");
         setBounds(500, 100, 700, 800);
         setLayout(null);

         titleLabel = new JLabel("제목");
         noticeLable = new JLabel("공지사항");
         writerLabel = new JLabel("작성자:");
         writer = new JLabel(userID);

         titleText = new JTextArea();
         noticeText = new JTextArea();
         noticeText.setLineWrap(true);
         complete = new JButton("작성완료");

         titleLabel.setBounds(50, 50, 50, 50);
         noticeLable.setBounds(50, 100, 100, 50);
         writerLabel.setBounds(50, 20, 50, 30);
         writer.setBounds(100, 20, 100, 30);

         titleText.setBounds(100, 60, 350, 30);
         noticeText.setBounds(50, 150, 600, 550);
         complete.setBounds(550, 50, 100, 50);
         addWindowListener(this);
         complete.addActionListener(this);

         add(titleLabel);
         add(noticeLable);
         add(writerLabel);
         add(writer);

         add(titleText);
         add(noticeText);
         add(complete);

         setVisible(true);
      }

      @Override
      public void actionPerformed(ActionEvent e) {

         if (e.getSource().equals(complete)) {
            if (titleText.getText().length() > 0 && noticeText.getText().length() > 0) {
               JOptionPane.showMessageDialog(null, "공지사항 등록 완료");
               noticeTitleVt.add(titleText.getText());
               NoticeDB.saveNOTICE(titleText.getText(), noticeText.getText(), userID);
               noticePanelManager.preFrame = null;
               dispose();
            } else {
               JOptionPane.showMessageDialog(null, "빈 칸을 입력해 주세요");
            }
         }

      }

      @Override
      public void windowOpened(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowClosing(WindowEvent e) {
         noticePanelManager.preFrame = null;

      }

      @Override
      public void windowClosed(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowIconified(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowDeiconified(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowActivated(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowDeactivated(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }
   }

   ArrayList<String> noticeTextArr = new ArrayList<String>();
   JComboBox noticeBox = new JComboBox<String>(noticeTitleVt);

   class NoticeReviseFrame extends JFrame implements ActionListener, WindowListener {

      JLabel titleReviseLabel;
      JLabel noticeReviseLabel;

      JLabel writeTime;
      JLabel reviseTime;
      JLabel writer;

      JLabel writerLabel;
      JLabel makeTimeLabel;
      JLabel modifTimeLabel;

      JLabel titleLabel;
      JLabel contentLabel;

      JTextArea titleReviseText;
      JTextArea noticeReviseText;

      JButton complete;
      String title;

      NoticeRevise noticeRevise2;

      public NoticeReviseFrame(String title, NoticeRevise noticeRevise2) {
         this.title = title;
         setTitle("공지사항 수정");
         setBounds(700, 100, 600, 700);
         setLayout(null);
         this.noticeRevise2 = noticeRevise2;
         titleReviseText = new JTextArea();
         noticeReviseText = new JTextArea();
         writeTime = new JLabel();
         reviseTime = new JLabel();
         writer = new JLabel();
         complete = new JButton("수정완료");
         writerLabel = new JLabel("작성자 : ");
         makeTimeLabel = new JLabel("작성시간 : ");
         modifTimeLabel = new JLabel("수정시간 : ");
         titleLabel = new JLabel("제목 : ");
         contentLabel = new JLabel("내용");
         addWindowListener(this);
         titleReviseText.setBounds(150, 110, 300, 30);
         noticeReviseText.setBounds(150, 200, 300, 400);
         writeTime.setBounds(100, 0, 200, 30);
         reviseTime.setBounds(100, 50, 200, 30);
         writer.setBounds(500, 50, 100, 30);
         complete.setBounds(470, 550, 100, 50);

         writerLabel.setBounds(430, 50, 100, 30);
         makeTimeLabel.setBounds(30, 0, 100, 30);
         modifTimeLabel.setBounds(30, 40, 100, 30);
         titleLabel.setBounds(100, 110, 50, 30);
         contentLabel.setBounds(150, 160, 100, 30);

         complete.addActionListener(this);

         add(titleReviseText);
         add(noticeReviseText);
         add(writeTime);
         add(reviseTime);
         add(writer);
         add(complete);

         add(writerLabel);
         add(makeTimeLabel);
         add(modifTimeLabel);
         add(titleLabel);
         add(contentLabel);

      }

      @Override
      public void actionPerformed(ActionEvent e) {
         complete.setEnabled(false);
         String newTitle = titleReviseText.getText();
         String newContent = noticeReviseText.getText();
         System.out.println("완료");
         NoticeDB.updateNOTICE(title, newTitle, newContent);

         noticeTitleVt = new Vector<String>();
         noticeArr = NoticeDB.getNOTICE();
         for (Notice notice : noticeArr) {
            noticeTitleVt.add(notice.title);
         }
         noticeRevise2.remove(noticeBox);
         noticeBox = new JComboBox<String>(noticeTitleVt);
         noticeBox.setBounds(75, 70, 350, 50);
         noticeBox.setBackground(Color.white);
         noticeRevise2.add(noticeBox);
         noticeRevise2.repaint();

         JOptionPane.showMessageDialog(null, "수정완료");
         noticeRevise2.noticePanelManager.preFrame = null;
         noticeRevise2.noticeReviseFrame = null;
         dispose();
      }

      @Override
      public void windowOpened(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowClosing(WindowEvent e) {
         noticeRevise2.noticePanelManager.preFrame = null;
         noticeRevise2.noticeReviseFrame = null;
      }

      @Override
      public void windowClosed(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowIconified(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowDeiconified(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowActivated(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowDeactivated(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }
   }

   ArrayList<Notice> noticeArr = new ArrayList<Notice>();

   class NoticeRevise extends JFrame implements ActionListener, WindowListener {

      JButton noticeChk;
      JButton noticeDel;
      NoticeReviseFrame noticeReviseFrame;
      NoticePanelManager noticePanelManager;

      public NoticeRevise(NoticePanelManager noticePanelManager) {
         this.noticePanelManager = noticePanelManager;
         setTitle("공지사항 수정");
         setBounds(600, 100, 500, 300);
         setLayout(null);

         noticeChk = new JButton("수정");
         noticeDel = new JButton("삭제");
         noticeChk.setBounds(370, 180, 100, 50);
         noticeDel.setBounds(260, 180, 100, 50);

         noticeTitleVt = new Vector<String>();
         noticeArr = NoticeDB.getNOTICE();
         noticeTitleVt.clear();
         for (Notice notice : noticeArr) {
            noticeTitleVt.add(notice.title);
         }
         noticeBox = new JComboBox<String>(noticeTitleVt);
         noticeChk.addActionListener(this);
         noticeDel.addActionListener(this);
         addWindowListener(this);
         noticeBox.setBounds(75, 70, 350, 50);
         noticeBox.setBackground(Color.white);
         add(noticeChk);
         add(noticeDel);
         add(noticeBox);

         setVisible(true);
      }

      @Override
      public void actionPerformed(ActionEvent e) {
         if (e.getSource().equals(noticeDel)) {
            Notice ntc = null;
            noticeTitleVt = new Vector<String>();
            noticeArr = NoticeDB.getNOTICE();
            for (Notice notice : noticeArr) {
               noticeTitleVt.add(notice.title);
            }
            for (Notice notice : noticeArr) {
               if (notice.title.equals(noticeTitleVt.get(noticeBox.getSelectedIndex()))) {
                  ntc = notice;
                  break;

               }
            }

            if (noticeTitleVt.size() != 0) {
               noticeTitleVt.remove(noticeBox.getSelectedIndex());
               NoticeDB.deleteNOTICE(ntc.title, ntc.content);
               JOptionPane.showMessageDialog(null, "삭제완료");
               noticeTitleVt = new Vector<String>();
               noticeArr = NoticeDB.getNOTICE();
               for (Notice notice : noticeArr) {
                  noticeTitleVt.add(notice.title);
               }
               remove(noticeBox);
               add(noticeBox = new JComboBox<String>(noticeTitleVt));
               noticeBox.setBounds(75, 70, 350, 50);
               noticeBox.setBackground(Color.white);
               revalidate();
               repaint();

            } else {
               JOptionPane.showMessageDialog(null, "수정 할 공지가 없습니다");
            }

         } else if (e.getSource().equals(noticeChk)) {

            Notice ntc = null;
            noticeTitleVt = new Vector<String>();
            noticeArr = NoticeDB.getNOTICE();
            for (Notice notice : noticeArr) {
               noticeTitleVt.add(notice.title);
            }
            for (Notice notice : noticeArr) {
               if (notice.title.equals(noticeTitleVt.get(noticeBox.getSelectedIndex()))) {
                  ntc = notice;
                  noticeReviseFrame = new NoticeReviseFrame(ntc.title, this);
                  noticeReviseFrame.titleReviseText.setText(ntc.title);
                  noticeReviseFrame.noticeReviseText.setText(ntc.content);
                  noticeReviseFrame.writeTime.setText(ntc.maketime);
                  noticeReviseFrame.reviseTime.setText(ntc.modi_time);
                  noticeReviseFrame.writer.setText(userID);
                  noticeReviseFrame.setVisible(true);
                  System.out.println("찾앗음");
                  dispose();
                  break;

               }
            }

         }

      }

      @Override
      public void windowOpened(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowClosing(WindowEvent e) {
         noticePanelManager.preFrame = null;

      }

      @Override
      public void windowClosed(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowIconified(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowDeiconified(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowActivated(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowDeactivated(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }
   }

   class SendMessageManager extends JFrame implements ActionListener, MouseListener, WindowListener {
      MessagePanelManager messagePanelManager;

      JTable sendList;
      JScrollPane scroll;

      JLabel idSearchLabel;
      JButton search;
      JButton allMessage;
      JTextField searchText;

      String messageStr;
      String[][] arr2;
      String[] arr;
      MessagePanelManager messagePanelManager2;
      boolean chk = true;

      public SendMessageManager(MessagePanelManager messagePanelManager2) {
         this.messagePanelManager2 = messagePanelManager2;
         setTitle("보낸메세지함");
         setBounds(600, 100, 515, 800);
         setLayout(null);
         addWindowListener(this);
         arr2 = MessageDB.getFROM_MESSAGE("admin");

         if (arr2 == null) {
            JOptionPane.showMessageDialog(null, "보낸메세지함이 비었습니다.");
            chk = false;
            return;
         }

         arr = new String[] { "받은사람", "내용", "시간" };

         sendList = new JTable(new NotEditTable(arr2, arr));
         scroll = new JScrollPane(sendList);
         search = new JButton("찾기");
         allMessage = new JButton("전체");
         searchText = new JTextField();
         idSearchLabel = new JLabel("받은사람 : ");

         scroll.setBounds(0, 150, 500, 500);

         search.setBounds(360, 90, 100, 50);
         allMessage.setBounds(250, 90, 100, 50);
         searchText.setBounds(130, 30, 330, 50);
         idSearchLabel.setBounds(50, 30, 100, 50);

         search.addActionListener(this);
         sendList.addMouseListener(this);
         allMessage.addActionListener(this);

         add(scroll);
         add(idSearchLabel);
         add(searchText);
         add(search);
         add(allMessage);

         setVisible(true);
      }

      @Override
      public void actionPerformed(ActionEvent e) {

         if (e.getSource().equals(search)) {
            if (UserDB.getID(searchText.getText()).equals("")) {
               JOptionPane.showMessageDialog(null, "없는 아이디입니다");
            } else {
               remove(scroll);
               arr2 = MessageDB.getFROM_MESSAGE("admin", searchText.getText());
               arr = new String[] { "보낸사람", "내용", "시간" };
               if (arr2 == null) {
                  JOptionPane.showMessageDialog(null, "보낸메세지함이 비었습니다.");
                  return;
               }
               sendList = new JTable(new NotEditTable(arr2, arr));
               scroll = new JScrollPane(sendList);
               scroll.setBounds(0, 150, 500, 500);
               sendList.addMouseListener(this);
               add(scroll);
               revalidate();
               repaint();
            }
         } else if (e.getSource().equals(allMessage)) {
            remove(scroll);
            arr2 = MessageDB.getFROM_MESSAGE("admin");
            arr = new String[] { "보낸사람", "내용", "시간" };
            if (arr2 == null) {
               JOptionPane.showMessageDialog(null, "보낸메세지함이 비었습니다.");
               return;
            }
            sendList = new JTable(new NotEditTable(arr2, arr));
            scroll = new JScrollPane(sendList);
            scroll.setBounds(0, 150, 500, 500);
            sendList.addMouseListener(this);
            searchText.setText("");
            add(scroll);
            revalidate();
            repaint();
         }

      }

      SendMessageClickManager sendMessageClickManager;

      @Override
      public void mouseClicked(MouseEvent e) {

         if (e.getSource().equals(sendList)) {
            if (sendMessageClickManager == null) {
               if (e.getClickCount() == 2) {

                  sendMessageClickManager = new SendMessageClickManager(this);
                  sendMessageClickManager.writer.setText(arr2[sendList.getSelectedRow()][0]);
                  sendMessageClickManager.content.setText(arr2[sendList.getSelectedRow()][1]);
                  sendMessageClickManager.time.setText(arr2[sendList.getSelectedRow()][2]);
               }
            }
         }

      }

      @Override
      public void mousePressed(MouseEvent e) {
         // TODO Auto-generated method stub

      }

      @Override
      public void mouseReleased(MouseEvent e) {
         // TODO Auto-generated method stub

      }

      @Override
      public void mouseEntered(MouseEvent e) {
         // TODO Auto-generated method stub

      }

      @Override
      public void mouseExited(MouseEvent e) {
         // TODO Auto-generated method stub

      }

      @Override
      public void windowOpened(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowClosing(WindowEvent e) {
         messagePanelManager2.preFrame = null;

      }

      @Override
      public void windowClosed(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowIconified(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowDeiconified(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowActivated(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowDeactivated(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

   }

   class SendMessageClickManager extends JFrame implements WindowListener {

      JLabel writerLabel;
      JLabel contentLabel;
      JLabel timeLabel;

      JLabel writer;
      JLabel time;
      JTextArea content;
      SendMessageManager sendMessageManager;

      public SendMessageClickManager(SendMessageManager sendMessageManager) {

         setBounds(600, 100, 400, 500);
         setLayout(null);
         this.sendMessageManager = sendMessageManager;
         writerLabel = new JLabel("받은사람 : ");
         timeLabel = new JLabel("보낸시간 : ");
         contentLabel = new JLabel("내용");
         addWindowListener(this);
         writer = new JLabel();
         time = new JLabel();
         content = new JTextArea();

         content.setLineWrap(true);
         content.setEditable(false);

         writerLabel.setBounds(10, 10, 70, 30);
         timeLabel.setBounds(10, 50, 70, 30);
         contentLabel.setBounds(10, 90, 50, 30);

         writer.setBounds(100, 10, 100, 30);
         time.setBounds(100, 50, 150, 30);
         content.setBounds(10, 120, 350, 300);

         add(writerLabel);
         add(timeLabel);
         add(contentLabel);
         add(writer);
         add(time);
         add(content);

         setVisible(true);

      }

      @Override
      public void windowOpened(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowClosing(WindowEvent e) {
         sendMessageManager.sendMessageClickManager = null;

      }

      @Override
      public void windowClosed(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowIconified(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowDeiconified(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowActivated(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowDeactivated(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

   }

   class NotEditTable extends DefaultTableModel {

      public NotEditTable(final Object[][] rowData, final Object[] columnNames) {
         super(rowData, columnNames);
         // TODO Auto-generated constructor stub
      }

      public boolean isCellEditable(int row, int column) {
         // TODO Auto-generated method stub
         return false;
      }
   }

   class GiveMessageManager extends JFrame implements ActionListener, MouseListener, WindowListener {

      JButton delete;
      JTable giveList;
      JScrollPane scroll;
      String[][] arr2;
      String[] arr;

      JLabel idSearchLabel;
      JButton search;
      JButton allMessage;
      JTextField searchText;
      MessagePanelManager messagePanelManager;

      boolean chk = true;

      public GiveMessageManager(MessagePanelManager messagePanelManager) {

         setTitle("받은메세지함");
         setBounds(600, 100, 515, 800);
         setLayout(null);
         this.messagePanelManager = messagePanelManager;
         arr2 = MessageDB.getTO_MESSAGE("admin");
         arr = new String[] { "보낸사람", "내용", "시간" };
         if (arr2 == null) {
            JOptionPane.showMessageDialog(null, "받은메세지함이 비었습니다.");
            chk = false;
            return;
         }
         giveList = new JTable(new NotEditTable(arr2, arr));
         scroll = new JScrollPane(giveList);
         addWindowListener(this);
         search = new JButton("찾기");
         allMessage = new JButton("전체");
         searchText = new JTextField();
         idSearchLabel = new JLabel("보낸사람 : ");

         delete = new JButton("삭제");

         delete.setBounds(390, 660, 100, 50);
         scroll.setBounds(0, 150, 500, 500);
         search.setBounds(360, 90, 100, 50);
         allMessage.setBounds(250, 90, 100, 50);
         searchText.setBounds(130, 30, 330, 50);
         idSearchLabel.setBounds(50, 30, 100, 50);

         delete.addActionListener(this);
         giveList.addMouseListener(this);
         search.addActionListener(this);
         allMessage.addActionListener(this);
         add(delete);
         add(scroll);
         add(idSearchLabel);
         add(searchText);
         add(search);
         add(allMessage);

         setVisible(true);
      }

      @Override
      public void actionPerformed(ActionEvent e) {

         if (e.getSource().equals(delete)) {
            MessageDB.deleteGiveMESSAGE("admin");
            JOptionPane.showMessageDialog(null, "삭제완료");
            messagePanelManager.preFrame = null;
            dispose();
         } else if (e.getSource().equals(search)) {
            if (UserDB.getID(searchText.getText()).equals("")) {
               JOptionPane.showMessageDialog(null, "없는 아이디입니다");
            } else {
               remove(scroll);
               arr2 = MessageDB.getTO_MESSAGE("admin", searchText.getText());
               arr = new String[] { "보낸사람", "내용", "시간" };
               if (arr2 == null) {
                  JOptionPane.showMessageDialog(null, "받은메세지함이 비었습니다.");
                  return;
               }
               giveList = new JTable(new NotEditTable(arr2, arr));
               scroll = new JScrollPane(giveList);
               scroll.setBounds(0, 150, 500, 500);
               giveList.addMouseListener(this);
               add(scroll);
               revalidate();
               repaint();
            }
         } else if (e.getSource().equals(allMessage)) {
            remove(scroll);
            arr2 = MessageDB.getTO_MESSAGE("admin");
            arr = new String[] { "보낸사람", "내용", "시간" };
            if (arr2 == null) {
               JOptionPane.showMessageDialog(null, "받은메세지함이 비었습니다.");
               return;
            }
            giveList = new JTable(new NotEditTable(arr2, arr));
            scroll = new JScrollPane(giveList);
            scroll.setBounds(0, 150, 500, 500);
            giveList.addMouseListener(this);
            searchText.setText("");
            add(scroll);
            revalidate();
            repaint();
         }

      }

      GiveMessageClickManager giveMessageClickManager;

      @Override
      public void mouseClicked(MouseEvent e) {

         if (e.getSource().equals(giveList)) {
            if (e.getClickCount() == 2) {
               if (giveMessageClickManager == null) {
                  giveMessageClickManager = new GiveMessageClickManager(this);
                  giveMessageClickManager.writer.setText(arr2[giveList.getSelectedRow()][0]);
                  giveMessageClickManager.content.setText(arr2[giveList.getSelectedRow()][1]);
                  giveMessageClickManager.time.setText(arr2[giveList.getSelectedRow()][2]);
               }
            }
         }

      }

      @Override
      public void mousePressed(MouseEvent e) {
         // TODO Auto-generated method stub

      }

      @Override
      public void mouseReleased(MouseEvent e) {
         // TODO Auto-generated method stub

      }

      @Override
      public void mouseEntered(MouseEvent e) {
         // TODO Auto-generated method stub

      }

      @Override
      public void mouseExited(MouseEvent e) {
         // TODO Auto-generated method stub

      }

      @Override
      public void windowOpened(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowClosing(WindowEvent e) {
         messagePanelManager.preFrame = null;
      }

      @Override
      public void windowClosed(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowIconified(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowDeiconified(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowActivated(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowDeactivated(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

   }

   class GiveMessageClickManager extends JFrame implements WindowListener {

      JLabel writerLabel;
      JLabel contentLabel;
      JLabel timeLabel;

      JLabel writer;
      JLabel time;
      JTextArea content;
      GiveMessageManager giveMessageManager;

      public GiveMessageClickManager(GiveMessageManager giveMessageManager) {

         setTitle("받은메세지");
         setBounds(600, 100, 400, 500);
         setLayout(null);
         this.giveMessageManager = giveMessageManager;
         writerLabel = new JLabel("보낸사람 : ");
         timeLabel = new JLabel("받은시간 : ");
         contentLabel = new JLabel("내용");
         addWindowListener(this);
         writer = new JLabel();
         time = new JLabel();
         content = new JTextArea();

         content.setLineWrap(true);
         content.setEditable(false);

         writerLabel.setBounds(10, 10, 70, 30);
         timeLabel.setBounds(10, 50, 70, 30);
         contentLabel.setBounds(10, 90, 50, 30);

         writer.setBounds(100, 10, 100, 30);
         time.setBounds(100, 50, 150, 30);
         content.setBounds(10, 120, 350, 300);

         add(writerLabel);
         add(timeLabel);
         add(contentLabel);
         add(writer);
         add(time);
         add(content);

         setVisible(true);

      }

      @Override
      public void windowOpened(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowClosing(WindowEvent e) {
         giveMessageManager.giveMessageClickManager = null;

      }

      @Override
      public void windowClosed(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowIconified(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowDeiconified(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowActivated(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

      @Override
      public void windowDeactivated(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁

      }

   }

}