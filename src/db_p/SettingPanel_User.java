package db_p;

//import java.awt.CardLayout;
import java.awt.Color;

import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.Image;
//import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.IconView;
import javax.xml.crypto.Data;

class SellerReviseInfor2 extends JFrame implements ActionListener, WindowListener { // ������ ���� ���� ������ (�Һ��� ���� ������ ���)

   JLabel profile; // ������
   JLabel photo; // ����
   JLabel helloMsg; // �λ縻
   JLabel nowPw;
   JLabel pw; // ��й�ȣ
   JLabel pwChk;
   JLabel phoneNum; // ��ȭ��ȣ
   JLabel email; // �̸���
   JLabel address; // �ּ�
   JLabel cardNum; // ī���ȣ
   JLabel pwHint; // ��й�ȣ ��Ʈ
   Vector<String> passWordHint_S = new Vector<String>();
   JComboBox pwhint;
   JLabel hintAnswer; // ��й�ȣ ��Ʈ ��
   JLabel workplaceName; // ������ ����� �̸�
   JLabel workplaceAddress; // ������ ����� �ּ�
   ArrayList<JLabel> information; // �������� ����Ʈ
   JButton reviseGo; // �������� ���� �Ϸ� ��ư

   JButton photoButton;

   JLabel photoFileName;

   JTextField helloText; // �λ縻 �����Է�
   JPasswordField nowPwText;
   JPasswordField pwField; // ��й�ȣ �����Է�
   JPasswordField pwChkText;

   Vector<String> pnNum;// ����ȣ �����Է�
   JComboBox phoneNumBox;
   JTextField phoneNumText_1;
   JTextField phoneNumText_2;
   JLabel phoneLabel; // �ڵ��� ¦���
   JLabel phoneLabel_2; // �ڵ��� ¦���

   JTextField emailText; // �̸��� �����Է�
   JTextField addressText; // �ּ� �����Է�
   JTextField cardNumText; // ī���ȣ �����Է�
   JTextField pwHintText; // ��й�ȣ ��Ʈ �����Է�
   JTextField hintAnswerText; // ��Ʈ �� �����Է�
   JTextField workNameText; //
   JTextField workAddressText;
   ArrayList<JTextField> fieldArr = new ArrayList<JTextField>();
   Vector<String> bankChoice = new Vector<String>();
   JComboBox<String> bankBox;
   String userID;
   ProfileInOut pfio = ProfileInOut.getprofileInout();
   SellerSignUpChk sellerSignUpChk;

   boolean[] regularChk = new boolean[9];
   boolean signUpEnd = true;

   // ��ȿ���˻�
   String pwRegular = "^.*(?=^.{5,19}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$"; // ��й�ȣ ��ȿ�� --> 6~20�� �̳��� ����
                                                                  // ��ҹ���/����/Ư������ �ݵ�� ����
   String phoneNumRegular = "\\d{3,4}$"; // ��ȭ��ȣ ���� 4�� �̳���
   String emailRegular = "[a-zA-Z0-9]*@[a-zA-Z.]*"; // �̸��� ��ҹ���/����
   String nameRegular = "^[��-�R]*$"; // �̸� �ѱ۸� ����
   String addressRegular = "^[-a-zA-Z��-�R0-9]*$"; //
   String businessName_R = "^[a-zA-Z��-�R0-9]*$"; // ������
   String businessAddress_R = "^[0-9-]*$"; // ���¹�ȣ
   String sRegular = "\\S*"; // ����

   String phoneDB;

   OptionPanel optionPanel;

   public SellerReviseInfor2(String userID, OptionPanel optionPanel) { // �������� ������
      this.userID = userID;
      setTitle("�������� ����"); // �������� ���� ������ Ÿ��Ʋ
      this.optionPanel = optionPanel;
      setBounds(700, 0, 600, 900);
      setLayout(null);
      addWindowListener(this);

      // �������� �� ����
      profile = new JLabel("�� ������");
      photo = new JLabel("���� ����");
      helloMsg = new JLabel("�λ縻 ����");
      nowPw = new JLabel("���� PW");
      pw = new JLabel("PW ����");
      pwChk = new JLabel("PW Ȯ��");
      email = new JLabel("�̸��� ����");
      phoneNum = new JLabel("��ȭ��ȣ ����");
      address = new JLabel("�ּ� ����");
      cardNum = new JLabel("���¹�ȣ ����");
      pwHint = new JLabel("PW��Ʈ ����");
      hintAnswer = new JLabel("��Ʈ �� ����");
      workplaceName = new JLabel("����� �̸�");
      workplaceAddress = new JLabel("����� �ּ�");

      helloText = new JTextField(); // �λ縻
      nowPwText = new JPasswordField(); // ���� ��й�ȣ
      pwField = new JPasswordField(); // ��й�ȣ ����
      pwChkText = new JPasswordField(); // ��й�ȣ Ȯ��

      phoneNumBox = new JComboBox(); // �ڵ�����ȣ ����
      phoneNumText_1 = new JTextField(); // �ι�° �ڵ��� ��ȣ �Է�â
      phoneNumText_2 = new JTextField(); // ����° �ڵ��� ��ȣ �Է�â
      phoneLabel = new JLabel("-");
      phoneLabel_2 = new JLabel("-");

      emailText = new JTextField(); // �̸��� �Է�
      addressText = new JTextField(); // �ּ� �Է�
      cardNumText = new JTextField(); // ī���ȣ �Է�
      hintAnswerText = new JTextField(); // ��й�ȣ ��Ʈ ��
      workNameText = new JTextField(); // ������
      workAddressText = new JTextField(); // ������ּ�

      // �ڵ�����ȣ ���ڸ� ------------------------------------
      pnNum = new Vector<String>();
      pnNum.add("010");
      pnNum.add("011");
      pnNum.add("016");
      pnNum.add("017");
      pnNum.add("018");
      pnNum.add("019");

      phoneNumBox = new JComboBox<String>(pnNum);
      phoneNumBox.setBounds(140, 535, 90, 40);
      phoneNumBox.setSelectedItem("010");
      add(phoneNumBox);

      bankChoice.add("-���༱��-");
      bankChoice.add("�������");
      bankChoice.add("��������");
      bankChoice.add("��������");
      bankChoice.add("�������");
      bankChoice.add("�츮����");
      bankChoice.add("��Ƽ����");
      bankChoice.add("�ϳ�����");
      bankChoice.add("��������");
      bankChoice.add("�泲����");
      bankChoice.add("�뱸����");
      bankChoice.add("��������");
      bankChoice.add("�λ�����");
      bankChoice.add("��������");
      bankChoice.add("��������");
      bankChoice.add("��������");
      bankChoice.add("��������");
      bankChoice.add("�������ݰ�");
      bankChoice.add("��������");
      bankChoice.add("��������");
      bankChoice.add("��ü��");

      bankBox = new JComboBox<String>(bankChoice);

      ImageIcon ii = new ImageIcon(pfio.download(userID));
      Image img = ii.getImage();
      img = img.getScaledInstance(100, 100, img.SCALE_SMOOTH);
      ii = new ImageIcon(img);
      photoFileName = new JLabel(ii);

      photoButton = new JButton("���� ����");
      photoButton.addActionListener(this);
      passWordHint_S.add("���ϴ� ������ �����ϼ���.");
      passWordHint_S.add("���� ��￡ ���� ��Ҵ�?");
      passWordHint_S.add("���� ���� 1ȣ��?");
      passWordHint_S.add("������ �������?");
      passWordHint_S.add("���� �����ϴ� �ι���?");
      passWordHint_S.add("���� �¿����?");
      passWordHint_S.add("���� ������ �� ��ȭ��?");
      passWordHint_S.add("���� �����ϴ� ��������?");
      passWordHint_S.add("�λ��� ���� å ������?");
      passWordHint_S.add("���� �뷡�� ��â����?");

      pwhint = new JComboBox(passWordHint_S);

      // �������� ����Ʈ�� �������� �� ���
      information = new ArrayList<JLabel>();
      information.add(profile);
      information.add(photo);
      information.add(helloMsg);
      information.add(pw);
      information.add(phoneNum);
      information.add(email);
      information.add(address);
      information.add(cardNum);
      information.add(pwHint);
      information.add(hintAnswer);
      information.add(workplaceName);
      information.add(workplaceAddress);

      // �����Ϸ� ��ư
      reviseGo = new JButton("���� �Ϸ�");
      // �������� ���� ������ ������ ��ư

      reviseGo.setBounds(470, 10, 100, 40);

      photoFileName.setBounds(130, 10, 100, 100);
      photoButton.setBounds(330, 60, 100, 50);
      helloText.setBounds(130, 120, 300, 50);
      nowPwText.setBounds(130, 180, 300, 50);
      pwField.setBounds(130, 240, 300, 50);
      pwChkText.setBounds(130, 300, 300, 50);

      // �ڵ���
      phoneNumBox.setBounds(130, 360, 90, 50); // 010 ....
      phoneNumText_1.setBounds(230, 360, 90, 50); // �ι�° ��ȭ��ȣ�Է��ϴ� â
      phoneNumText_2.setBounds(330, 360, 90, 50); // ����° ��ȭ��ȣ �Է��ϴ� â
      phoneLabel.setBounds(220, 360, 10, 50); // ¦���
      phoneLabel_2.setBounds(320, 360, 10, 50); // ¦���

      emailText.setBounds(130, 420, 300, 50);
      addressText.setBounds(130, 480, 300, 50);
      cardNumText.setBounds(130, 540, 300, 50);
      pwhint.setBounds(130, 600, 300, 50);
      hintAnswerText.setBounds(130, 660, 300, 50);
      workNameText.setBounds(130, 720, 300, 50);
      workAddressText.setBounds(130, 780, 300, 50);

      bankBox.setBounds(450, 540, 100, 50);

      for (int i = 1; i < information.size(); i++) { // �������� �� ��ġ ���(����Ʈ)
         information.get(i).setBounds(10, i * 60 + 120, 100, 50);
      }

      photo.setBounds(10, 60, 100, 50);
      helloMsg.setBounds(10, 120, 100, 50);
      nowPw.setBounds(10, 180, 100, 50);
      pw.setBounds(10, 240, 100, 50);
      pwChk.setBounds(10, 300, 100, 50);

      reviseGo.addActionListener(this); // �����Ϸ� ��ư

      System.out.println(UserDB.getBANKNUMBER(userID));

      helloText.setText(UserDB.getPROFILE_TEXT(userID));
      nowPwText.setText(UserDB.getPW(userID));
      phoneNumBox.setSelectedItem(UserDB.getPHONE(userID).split("-")[0]);
      phoneNumText_1.setText(UserDB.getPHONE(userID).split("-")[1]);
      phoneNumText_2.setText(UserDB.getPHONE(userID).split("-")[2]);
      emailText.setText(UserDB.getEMAIL(userID));
      addressText.setText(UserDB.getADDRESS(userID));
      cardNumText.setText(UserDB.getBANKNUMBER(userID).split("/")[0]);
//             pwHintText.setText(UserDB.getpw);
      hintAnswerText.setText(UserDB.getPWRES(userID));
      bankBox.setSelectedItem(UserDB.getBANKNUMBER(userID).split("/")[1]);
      pwhint.setSelectedItem(UserDB.getPWHINT(userID));
      workNameText.setText(UserDB.getBUSINESSNAME(userID));
      workAddressText.setText(UserDB.getBUSINESSADDRESS(userID));

      add(reviseGo); // �������� ���� �����ӿ� �����Ϸ� ��ư �߰�

      add(nowPw);
      add(pwChk);

      add(helloText);
      add(nowPwText);
      add(pwField);
      add(pwChkText);
      add(phoneNumBox);
      add(emailText);
      add(addressText);
      add(cardNumText);
      add(hintAnswerText);
      add(workNameText);
      add(workAddressText);
      add(profile);
      add(pwhint);
      add(phoneNumBox);
      add(phoneNumText_1);
      add(phoneNumText_2);
      add(phoneLabel);
      add(phoneLabel_2);

      add(photoButton);
      add(photoFileName);
      add(bankBox);

      for (JLabel jl : information) {
         add(jl); // �������� ���� �����ӿ� �������� �� �߰�
      }

      sellerSignUpChk = new SellerSignUpChk();
      sellerSignUpChk.start();

      setVisible(true);

   }

   FileReviseFrame fileReviseFrame;

   @Override
   public void actionPerformed(ActionEvent e) {

      int cnt = 0;

      for (boolean rgc : regularChk) {
         if (rgc == true) {
            cnt++;
         }

      }

      if (e.getSource().equals(reviseGo)) { // �������� ���� �����ӿ��� �Ϸ� ��ư Ŭ��
         // �����Ϸ� �˾� ����� ������ �������
         System.out.println("������");
         // DB����
         phoneDB = phoneNumBox.getSelectedItem().toString() + "-" + phoneNumText_1.getText() + "-"
               + phoneNumText_2.getText(); // �ڵ�����ȣ

         if (pwhint.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "��й�ȣ ��Ʈ�� ������ �ּ���");
         } else if (!(pwField.getText().equals(pwChkText.getText()))) {
            JOptionPane.showMessageDialog(null, "��й�ȣ�� �ٽ� �Է����ּ���");
         } else if (bankBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "������ ������ �ּ���");
         } else if (cnt != 9) {
            System.out.println(cnt);
            JOptionPane.showMessageDialog(null, "�ùٸ� ������ �ƴմϴ�");
         } else {
            JOptionPane.showMessageDialog(null, "�����Ϸ�");
            signUpEnd = false;
            System.out.println(signUpEnd);
            if (!(helloText.getText().equals(""))) {
               System.out.println("������1");
               UserDB.setPROFILE_TEXT(userID, helloText.getText());
            }
            if (!(pwField.getText().equals(""))) {
               System.out.println("������2");
               UserDB.setPW(userID, pwField.getText());
            }
            if (!(phoneDB.equals(""))) {
               System.out.println("������3");
               UserDB.setPHONE(userID, phoneDB);
            }
            if (!(emailText.getText().equals(""))) {
               System.out.println("������4");
               UserDB.setEMAIL(userID, emailText.getText());
            }
            if (!(addressText.getText().equals(""))) {
               System.out.println("������5");
               UserDB.setADDRESS(userID, addressText.getText());
            }
            if (!(cardNumText.getText().equals(""))) {
               System.out.println("������6");
               String bankStr = (String) bankBox.getSelectedItem();
               UserDB.setBANKNUMBER(userID, cardNumText.getText() + "/" + bankStr);
            }
            if (!(pwhint.getSelectedItem().equals(""))) {
               System.out.println("������7");
               UserDB.setPWHINT(userID, pwhint.getSelectedItem().toString());
            }
            if (!(hintAnswerText.getText().equals(""))) {
               System.out.println("������8");
               System.out.println(hintAnswerText.getText());
               UserDB.setPWRES(userID, hintAnswerText.getText());
            }
            if (!(workNameText.getText().equals(""))) {
               System.out.println("������9");
               System.out.println(workNameText.getText());
               UserDB.setBUSINESSNAME(userID, workNameText.getText());
            }
            if (!(workAddressText.getText().equals(""))) {
               System.out.println("������10");
               System.out.println(workAddressText.getText() + userID);
               UserDB.setBUSINESSADDRESS(userID, workAddressText.getText());
            }
            optionPanel.ri = null;
//            setVisible(false);
            dispose();
            System.out.println("������" + signUpEnd);
         }

      } else if (e.getSource().equals(photoButton)) {
         fileReviseFrame = new FileReviseFrame();
         String fileStr = fileReviseFrame.fd.getDirectory() + "\\" + fileReviseFrame.fd.getFile();

         if (fileStr.substring(fileStr.lastIndexOf(".") + 1).toLowerCase().equals("jpg")) {
            pfio.upload(fileReviseFrame.fd.getDirectory() + "\\" + fileReviseFrame.fd.getFile(), userID);
            ImageIcon ii = new ImageIcon(pfio.download(userID));
            Image img = ii.getImage();
            img = img.getScaledInstance(100, 100, img.SCALE_SMOOTH);
            ii = new ImageIcon(img);

            photoFileName.setIcon(ii);
            repaint();
         } else if (fileStr.equals("null\\null")) {

         } else if (!fileStr.substring(fileStr.lastIndexOf(".") + 1).toLowerCase().equals("jpg")) {
            JOptionPane.showMessageDialog(null, "jpg���ϸ� �����մϴ�");
         }

      }

   }

   @Override
   public void windowOpened(WindowEvent e) {
      // TODO Auto-generated method stub

   }

   @Override
   public void windowClosing(WindowEvent e) {
      signUpEnd = false;
      optionPanel.ri = null;

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

   // ��ȿ�� �˻� ������
   class SellerSignUpChk extends Thread {

      @Override
      public void run() {
         while (signUpEnd) {
            // ��й�ȣ ��ȿ�� �˻� --> ����,����, Ư������ ���� (���� ����) ---------------------------------
            if (((!(Pattern.matches(pwRegular, pwField.getText())))
                  || !(Pattern.matches(sRegular, pwField.getText()))) && !pwField.getText().equals("")) {
               regularChk[0] = false;
               pwField.setForeground(Color.red);
               pwField.setOpaque(true);

            } else {
               regularChk[0] = true;
               pwField.setForeground(Color.black);

               // ��й�ȣ == ��й�ȣ Ȯ�� ��ġ���� �˻�!!
            }
            if (!(pwField.getText().equals(pwChkText.getText()))) {
               regularChk[1] = false;
               pwChkText.setForeground(Color.red);

            } else {
               regularChk[1] = true;
               pwChkText.setForeground(Color.black);

               // �̸� ��ȿ�� �˻� --> �ѱ۸� ��밡�� --------------------------------
            }
            if ((!(Pattern.matches(emailRegular, emailText.getText())))
                  || !(Pattern.matches(sRegular, emailText.getText())) && !emailText.getText().equals("")) {
               regularChk[2] = false;
               emailText.setForeground(Color.red);

            } else {
               regularChk[2] = true;
               emailText.setForeground(Color.black);

            }
            if ((!(Pattern.matches(phoneNumRegular, phoneNumText_1.getText())))
                  || !((Pattern.matches(sRegular, phoneNumText_1.getText()))
                        && !phoneNumText_1.getText().equals(""))) {
               regularChk[3] = false;
               phoneNumText_1.setForeground(Color.red);

            } else {
               regularChk[3] = true;
               phoneNumText_1.setForeground(Color.black);

            }
            if ((!(Pattern.matches(phoneNumRegular, phoneNumText_2.getText())))
                  || !((Pattern.matches(sRegular, phoneNumText_2.getText()))
                        && !phoneNumText_2.getText().equals(""))) {
               regularChk[4] = false;
               phoneNumText_2.setForeground(Color.red);

            } else {
               regularChk[4] = true;
               phoneNumText_2.setForeground(Color.black);

               // ī���ȣ ��ȿ�� �˻� --> ���ڼ� 4�� ����, ���ڸ� ���� ..--------------------------------
            }
            // �ּҴ� �ѱ�/����/���ڸ� ����!!(�ʼ�����)
            if ((!(Pattern.matches(addressRegular, addressText.getText().trim().replaceAll(" ", ""))))
                  && !addressText.getText().equals("")) {
               regularChk[5] = false;
               addressText.setForeground(Color.red);

            } else {
               regularChk[5] = true;
               addressText.setForeground(Color.black);

            } // ������
            if ((!(Pattern.matches(businessName_R, workNameText.getText().trim().replaceAll(" ", ""))))
                  && !workNameText.getText().equals("")) {
               regularChk[6] = false;
               workNameText.setForeground(Color.red);

            } else {
               regularChk[6] = true;
               workNameText.setForeground(Color.black);
            }
            // ������ּ�
            if ((!(Pattern.matches(addressRegular, workAddressText.getText().trim().replaceAll(" ", ""))))
                  && !workAddressText.getText().equals("")) {
               regularChk[7] = false;
               workAddressText.setForeground(Color.red);

            } else {
               regularChk[7] = true;
               workAddressText.setForeground(Color.black);

               // ���¹�ȣ
            }
            if ((!(Pattern.matches(businessAddress_R, cardNumText.getText().trim().replaceAll(" ", ""))))
                  && !cardNumText.getText().equals("")) {
               regularChk[8] = false;
               cardNumText.setForeground(Color.red);

            } else {
               regularChk[8] = true;
               cardNumText.setForeground(Color.black);

            }
         }
      }
   }

}

class FileReviseFrame extends JFrame {

   FileDialog fd;

   public FileReviseFrame() {

      fd = new FileDialog(this, "���Ͽ���", FileDialog.LOAD);
      fd.setLocation(510, 210);
      fd.setVisible(true);
   }
}

//   class OptionAction implements ActionListener{   //������, �������� ���� ��ư �׼� ������
//      
////      JButton opt1;   //������ ��ư
////      JButton opt2;   //�������� ���� ��ư
////      JFrame help;
//      JPanel notice;   //�������� �г�
//      JPanel message;   //�޼��� �г�
//      
////      JumTalkOption jt;
//      ReviseInfor ri;   //�Һ��� �������� ���� Ŭ���� 
//      JumTalkOption_User jto;
////      Opt1_Frame opt1_F;
//      
//      public OptionAction(JPanel notice, JPanel message) {   //��������, �޼��� �г�(visible)�� ���� �Ű�����
//         this.notice = notice;
//         this.message = message;
//      }
//      
//      @Override
//      public void actionPerformed(ActionEvent e) {
//         
//      
//            
//      }
//   }

class OptionPanel extends JPanel implements ActionListener { // ��, ������ư �ؿ� �г�
   JPanel optPanel; // ������, �������� ���� ��ư�� ��� �г�

   JFrame ri;
   JLabel ntcLabel; // �������� ��
   ArrayList<JButton> optButton;
   NoticePanel ntcPanel; // �������� �������� �г�
   MessagePanel msgPanel; // �������� �޼��� �г�

   JButton opt1; // ������ ������
   JButton opt2; // ������ �������� ����

   String userID;
   JFrame jumTalkOption_User;

   public OptionPanel(String userID, JFrame jp) {
      this.userID = userID;
      this.jumTalkOption_User = jp;

      optButton = new ArrayList<JButton>(); // ����, �������� ���� ��ư ����Ʈ

      optPanel = new JPanel(); // ������ư �г�
      optPanel.setBounds(0, 615, 500, 50);

      ntcPanel = new NoticePanel(); // �������� �г�
      ntcPanel.setBounds(0, 0, 500, 200);
      msgPanel = new MessagePanel(); // �޼��� �г�
      msgPanel.setBounds(0, 210, 500, 400);

      opt1 = new JButton("�α׾ƿ�"); // ������ ��ư
      opt2 = new JButton("�������� ����"); // �������� ���� ��ư

      ntcLabel = new JLabel("��������"); // �������� �� (�ӽ�)

      ntcPanel.setLayout(null); // �������� �г� ���̾ƿ�
      msgPanel.setLayout(null); // �޼��� �г� ���̾ƿ�

      ntcLabel.setBounds(220, 30, 200, 50); // �������� �� ��ġ (�ӽ�)

      optButton.add(opt1); // ��ư ����Ʈ�� ������ư ����
      optButton.add(opt2); // ��ư ����Ʈ�� �������� ���� ��ư ����

      // �������� �г�
      ntcPanel.add(ntcLabel); // �������� �гο� �������� �� �߰�(�ӽ�)

      // �����ڿ��� ����

//         oa = new OptionAction(ntcPanel,msgPanel);   //������, �������� ���� �׼� ������ ����

      ntcPanel.setVisible(true); // �׼� �����ʷ� �������� �� (�������� �г�)
      msgPanel.setVisible(true); // �׼� �����ʷ� �������� �� (�޼��� �г�)

      setLayout(null); // ������, �������� ���� ��ư �ؿ� �г�

      optPanel.setLayout(new GridLayout(1, 2)); // ������, �������� ���� ��ư �г�

      optPanel.add(opt2); // ������, �������� ���� ��ư �гο� �������� ���� ��ư �߰�
      optPanel.add(opt1); // ������, �������� ���� ��ư �гο� ������ ��ư �߰�
      add(optPanel);
      add(ntcPanel); // �������� �г� �߰�
      add(msgPanel); // �޼��� �г� �߰�
      for (JButton jb : optButton) { // ������, �������� ������ư ����Ʈ
         jb.addActionListener(this);
      }

      repaint();
      setVisible(true);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource().equals(optButton.get(0))) {
         // �α��� �гη� �ٲ�
         JOptionPane.showMessageDialog(null, "�α׾ƿ� �Ϸ�");
         System.exit(0);
      } else if (e.getSource().equals(optButton.get(1))) {
         if (UserDB.getUSERKIND(userID) == 0) {
            if (ri == null) {
               ri = new ReviseInfor();
            }
         } else {
            if (ri == null) {
               ri = new SellerReviseInfor2(userID, this);
            }
         }
      }

   }

   class ReviseInfor extends JFrame implements ActionListener, WindowListener { // �������� ���� ������

      JLabel profile; // ������
      JLabel photo; // ����
      JLabel helloMsg; // �λ縻
      JLabel pw; // ��й�ȣ
      JLabel phoneNum; // ��ȭ��ȣ
      JLabel phoneLabel_1;
      JLabel phoneLabel_2;
      JLabel email; // �̸���
      JLabel address; // �ּ�
      JLabel cardNum; // ī���ȣ
      JLabel cardLabel_1;
      JLabel cardLabel_2;
      JLabel cardLabel_3;
      JLabel pwHint; // ��й�ȣ ��Ʈ
      JLabel hintAnswer; // ��й�ȣ ��Ʈ ��
      ArrayList<JLabel> information; // �������� ����Ʈ
      JButton reviseGo; // �������� ���� �Ϸ� ��ư

      JButton photoButton;
      JLabel photoFileName;
      JLabel nowPw;
      JLabel pwChk;

      JTextField helloText;
      JPasswordField nowPwField;
      JPasswordField pwField;
      JPasswordField pwChkText;
      Vector<String> pnNum = new Vector<String>();// ����ȣ �����Է�
      JComboBox phoneNumBox;
      JTextField phoneNumText_1;
      JTextField phoneNumText_2;
      JTextField emailText;
      JTextField addressText;
      JTextField cardNumText_1;
      JTextField cardNumText_2;
      JTextField cardNumText_3;
      JTextField cardNumText_4;
//      JTextField pwHintText;
      JTextField hintAnswerText;
      ArrayList<JTextField> fieldArr = new ArrayList<JTextField>();
      Vector<String> passWordHint_S = new Vector<String>();
//        Vector<String> bankChoice = new Vector<String>();
//        JComboBox<String> bankBox;
      JComboBox pwhint;
      FileReviseFrame fileReviseFrame;

      String pwRegular = "^.*(?=^.{5,19}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$"; // ��й�ȣ ��ȿ�� --> 6~20�� �̳��� ����
      // ��ҹ���/����/Ư������ �ݵ�� ����
      String phoneNumRegular = "\\d{3,4}$"; // ��ȭ��ȣ ���� 4�� �̳���
      String emailRegular = "^[0-9a-zA-Z@.].*"; // �̸��� ��ҹ���/����
      String nameRegular = "^[��-�R]*$"; // �̸� �ѱ۸� ����
      String cardRegular = "\\d{4}$"; // ����
      String addressRegular = "^[-a-zA-Z��-�R0-9]*$"; // �ּ�
      String businessName_R = "^[a-zA-Z��-�R0-9]*$"; // ������
      String businessAddress_R = "^[0-9-]*$"; // ���¹�ȣ
      String sRegular = "\\S*"; // ����
      String phoneDB;
      NormalSignUpChk normalSignUpChk;
      boolean[] regularChk = new boolean[10];

      boolean signUpEnd = true;

      public ReviseInfor() { // �������� ������

         setTitle("�������� ����"); // �������� ���� ������ Ÿ��Ʋ

         setBounds(700, 100, 500, 870);
         setLayout(null);
         addWindowListener(this);

         pnNum = new Vector<String>();
         pnNum.add("010");
         pnNum.add("011");
         pnNum.add("016");
         pnNum.add("017");
         pnNum.add("018");
         pnNum.add("019");

         // �������� �� ����
         profile = new JLabel("�� ������");
         photo = new JLabel("���� ����");
         helloMsg = new JLabel("�λ縻 ����");
         nowPw = new JLabel("���� ��й�ȣ");
         pw = new JLabel("PW ����");
         pwChk = new JLabel("pw Ȯ��");
         email = new JLabel("�̸��� ����");
         phoneNum = new JLabel("��ȭ��ȣ ����");
         address = new JLabel("�ּ� ����");
         cardNum = new JLabel("ī���ȣ ����");
         pwHint = new JLabel("PW��Ʈ ����");
         hintAnswer = new JLabel("��Ʈ �� ����");
         photoButton = new JButton("���� ����");
         phoneLabel_1 = new JLabel("-");
         phoneLabel_2 = new JLabel("-");
         ImageIcon ii = new ImageIcon(pfio.download(userID));
         Image img = ii.getImage();
         img = img.getScaledInstance(100, 100, img.SCALE_SMOOTH);
         ii = new ImageIcon(img);

//           bankChoice.add("-���༱��-");
//           bankChoice.add("�������");
//           bankChoice.add("��������");
//           bankChoice.add("��������");
//           bankChoice.add("�������");
//           bankChoice.add("�츮����");
//           bankChoice.add("��Ƽ����");
//           bankChoice.add("�ϳ�����");
//           bankChoice.add("��������");
//           bankChoice.add("�泲����");
//           bankChoice.add("�뱸����");
//           bankChoice.add("��������");
//           bankChoice.add("�λ�����");
//           bankChoice.add("��������");
//           bankChoice.add("��������");
//           bankChoice.add("��������");
//           bankChoice.add("��������");
//           bankChoice.add("�������ݰ�");
//           bankChoice.add("��������");
//           bankChoice.add("��������");
//           bankChoice.add("��ü��");

//           bankBox = new JComboBox<String>(bankChoice);

         photoFileName = new JLabel(ii);

         helloText = new JTextField();
         pwField = new JPasswordField();
         phoneNumBox = new JComboBox<String>(pnNum);
         phoneNumText_1 = new JTextField();
         phoneNumText_2 = new JTextField();
         emailText = new JTextField();
         addressText = new JTextField();
         cardNumText_1 = new JTextField();
         cardNumText_2 = new JTextField();
         cardNumText_3 = new JTextField();
         cardNumText_4 = new JTextField();
         cardLabel_1 = new JLabel("-");
         cardLabel_2 = new JLabel("-");
         cardLabel_3 = new JLabel("-");
//         pwHintText = new JTextField();
         hintAnswerText = new JTextField();
         nowPwField = new JPasswordField();
         pwChkText = new JPasswordField();

         // �������� ����Ʈ�� �������� �� ���
         information = new ArrayList<JLabel>();
         information.add(profile);
         information.add(photo);
         information.add(helloMsg);
         information.add(pw);
         information.add(email);
         information.add(phoneNum);
         information.add(address);
         information.add(cardNum);
         information.add(pwHint);
         information.add(hintAnswer);

         passWordHint_S.add("���ϴ� ������ �����ϼ���.");
         passWordHint_S.add("���� ��￡ ���� ��Ҵ�?");
         passWordHint_S.add("���� ���� 1ȣ��?");
         passWordHint_S.add("������ �������?");
         passWordHint_S.add("���� �����ϴ� �ι���?");
         passWordHint_S.add("���� �¿����?");
         passWordHint_S.add("���� ������ �� ��ȭ��?");
         passWordHint_S.add("���� �����ϴ� ��������?");
         passWordHint_S.add("�λ��� ���� å ������?");
         passWordHint_S.add("���� �뷡�� ��â����?");

         pwhint = new JComboBox(passWordHint_S);

         // �����Ϸ� ��ư
         reviseGo = new JButton("���� �Ϸ�");
         // �������� ���� ������ ������ ��ư

         reviseGo.setBounds(370, 10, 100, 40);

         pwhint.setBounds(130, 700, 300, 50);

         photoFileName.setBounds(100, 60, 200, 100);
         photoButton.setBounds(330, 80, 100, 50);
         helloText.setBounds(130, 220, 300, 50);
         nowPwField.setBounds(130, 280, 300, 50);
         pwField.setBounds(130, 340, 300, 50);
         pwChkText.setBounds(130, 400, 300, 50);
         emailText.setBounds(130, 460, 300, 50);
         phoneNumBox.setBounds(130, 520, 90, 50);
         phoneNumText_1.setBounds(230, 520, 90, 50);
         phoneNumText_2.setBounds(330, 520, 90, 50);
         phoneLabel_1.setBounds(220, 520, 10, 50);
         phoneLabel_2.setBounds(320, 520, 10, 50);
         addressText.setBounds(130, 580, 300, 50);
         cardNumText_1.setBounds(130, 640, 60, 50);
         cardNumText_2.setBounds(200, 640, 60, 50);
         cardNumText_3.setBounds(270, 640, 60, 50);
         cardNumText_4.setBounds(340, 640, 60, 50);
         cardLabel_1.setBounds(190, 640, 10, 50);
         cardLabel_2.setBounds(260, 640, 10, 50);
         cardLabel_3.setBounds(330, 640, 10, 50);
//         pwHintText.setBounds(130, 480, 300, 50);
         hintAnswerText.setBounds(130, 760, 300, 50);
//         bankBox.setBounds(450, 640, 100, 50);

         for (int i = 1; i < information.size(); i++) { // �������� �� ��ġ ���(����Ʈ)
            information.get(i).setBounds(10, (i * 60) + 220, 100, 50);
         }
         helloMsg.setBounds(10, 220, 100, 50);
         photo.setBounds(10, 60, 100, 100);
         nowPw.setBounds(10, 280, 100, 50);
         pw.setBounds(10, 340, 100, 50);
         pwChk.setBounds(10, 400, 100, 50);

         reviseGo.addActionListener(this); // �����Ϸ� ��ư

         photoButton.addActionListener(this);

         helloText.setText(UserDB.getPROFILE_TEXT(userID));
         nowPwField.setText(UserDB.getPW(userID));
         phoneNumBox.setSelectedItem(UserDB.getPHONE(userID).split("-")[0]);
         phoneNumText_1.setText(UserDB.getPHONE(userID).split("-")[1]);
         phoneNumText_2.setText(UserDB.getPHONE(userID).split("-")[2]);
         emailText.setText(UserDB.getEMAIL(userID));
         addressText.setText(UserDB.getADDRESS(userID));
         cardNumText_1.setText(UserDB.getCARDNUMBER(userID).split("-")[0]);
         cardNumText_2.setText(UserDB.getCARDNUMBER(userID).split("-")[1]);
         cardNumText_3.setText(UserDB.getCARDNUMBER(userID).split("-")[2]);
         cardNumText_4.setText(UserDB.getCARDNUMBER(userID).split("-")[3]);
//         pwHintText.setText(UserDB.getpw);
         hintAnswerText.setText(UserDB.getPWRES(userID));

         pwhint.setSelectedItem(UserDB.getPWHINT(userID));

         add(reviseGo); // �������� ���� �����ӿ� �����Ϸ� ��ư �߰�

//         add(passWordHint_S);
         add(nowPw);
         add(pwChk);

         add(photoFileName);
         add(photoButton);
         add(helloText);
         add(pwField);
         add(phoneNumBox);
         add(phoneLabel_1);
         add(phoneLabel_2);
         add(phoneNumText_1);
         add(phoneNumText_2);
         add(emailText);
         add(addressText);
         add(cardNumText_1);
         add(cardNumText_2);
         add(cardNumText_3);
         add(cardNumText_4);
         add(cardLabel_1);
         add(cardLabel_2);
         add(cardLabel_3);
//         add(pwHintText);
         add(hintAnswerText);
         add(pwhint);
         add(nowPwField);
         add(pwChkText);
//         add(bankBox);

         for (JLabel jl : information) {
            add(jl); // �������� ���� �����ӿ� �������� �� �߰�
         }

         normalSignUpChk = new NormalSignUpChk();
         normalSignUpChk.start();
         setVisible(true);

      }

      ProfileInOut pfio = ProfileInOut.getprofileInout();

      @Override
      public void actionPerformed(ActionEvent e) {

         int cnt = 0;

         for (boolean rgc : regularChk) {
            if (rgc == true) {
               cnt++;
            }

         }

         if (e.getSource().equals(reviseGo)) { // �������� ���� �����ӿ��� �Ϸ� ��ư Ŭ��
            // �����Ϸ� �˾� ����� ������ �������

            boolean pwhintChk = pwhint.getSelectedIndex() == 0;
            phoneDB = phoneNumBox.getSelectedItem().toString() + "-" + phoneNumText_1.getText() + "-"
                  + phoneNumText_2.getText();
            String cardNumText = cardNumText_1.getText() + "-" + cardNumText_2.getText() + "-"
                  + cardNumText_3.getText() + "-" + cardNumText_4.getText();
            if (pwhintChk == true) {
               JOptionPane.showMessageDialog(null, "��й�ȣ ��Ʈ�� �������ּ���");
            } else if (!(pwField.getText().equals(pwChkText.getText()))) {
               JOptionPane.showMessageDialog(null, "��й�ȣ�� �ٽ� �Է����ּ���");
            } else if (cnt != 10) {
               JOptionPane.showMessageDialog(null, "�ùٸ� ������ �ƴմϴ�");
            } else {
               JOptionPane.showMessageDialog(null, "�����Ϸ�");
               signUpEnd = false;
        
               if (!(helloText.getText().equals(""))) {
                  UserDB.setPROFILE_TEXT(userID, helloText.getText());
               }
               if (!(pwField.getText().equals(""))) {
                  UserDB.setPW(userID, pwField.getText());
               }
               if (!(phoneDB.equals(""))) {
                  UserDB.setPHONE(userID, phoneDB);
               }
               if (!(emailText.getText().equals(""))) {
                  UserDB.setEMAIL(userID, emailText.getText());
               }
               if (!(addressText.getText().equals(""))) {
                  UserDB.setADDRESS(userID, addressText.getText());
               }
               if (!(cardNumText.equals(""))) {
                  UserDB.setCARDNUMBER(userID, cardNumText);
               }
               if (!(pwhint.getSelectedItem().equals("���ϴ� ������ �����ϼ���."))) {
                  UserDB.setPWHINT(userID, pwhint.getSelectedItem().toString());
               }
               if (!(hintAnswerText.getText().equals(""))) {
                  UserDB.setPWRES(userID, hintAnswerText.getText());
               }
//               setVisible(false);
               ri = null;
               dispose();
            }

         } else if (e.getSource().equals(photoButton)) {
            fileReviseFrame = new FileReviseFrame();
            String fileStr = fileReviseFrame.fd.getDirectory() + "\\" + fileReviseFrame.fd.getFile();

            if (fileStr.substring(fileStr.lastIndexOf(".") + 1).toLowerCase().equals("jpg")) {
               pfio.upload(fileReviseFrame.fd.getDirectory() + "\\" + fileReviseFrame.fd.getFile(), userID);
               ImageIcon ii = new ImageIcon(pfio.download(userID));
               Image img = ii.getImage();
               img = img.getScaledInstance(100, 100, img.SCALE_SMOOTH);
               ii = new ImageIcon(img);

               photoFileName.setIcon(ii);
               repaint();
            } else if (fileStr.equals("null\\null")) {

            } else if (!fileStr.substring(fileStr.lastIndexOf(".") + 1).toLowerCase().equals("jpg")) {
               JOptionPane.showMessageDialog(null, "jpg���ϸ� �����մϴ�");
            }
         }

      }

      @Override
      public void windowOpened(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      @Override
      public void windowClosing(WindowEvent e) {
         signUpEnd = false;
         ri = null;

      }

      @Override
      public void windowClosed(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      @Override
      public void windowIconified(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      @Override
      public void windowDeiconified(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      @Override
      public void windowActivated(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      @Override
      public void windowDeactivated(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      // ��ȿ�� �˻� ������
      class NormalSignUpChk extends Thread {

         @Override
         public void run() {
            while (signUpEnd) {
               // ��й�ȣ ��ȿ�� �˻� --> ����,����, Ư������ ���� (���� ����) ---------------------------------
               if (((!(Pattern.matches(pwRegular, pwField.getText())))
                     || !(Pattern.matches(sRegular, pwField.getText()))) && !pwField.getText().equals("")) {
                  regularChk[0] = false;
                  pwField.setForeground(Color.red);
                  pwField.setOpaque(true);

               } else {
                  regularChk[0] = true;
                  pwField.setForeground(Color.black);

                  // ��й�ȣ == ��й�ȣ Ȯ�� ��ġ���� �˻�!!
               }
               if (!(pwField.getText().equals(pwChkText.getText()))) {
                  regularChk[1] = false;
                  pwChkText.setForeground(Color.red);

               } else {
                  regularChk[1] = true;
                  pwChkText.setForeground(Color.black);

                  // �̸� ��ȿ�� �˻� --> �ѱ۸� ��밡�� --------------------------------
               }
               if ((!(Pattern.matches(emailRegular, emailText.getText())))
                     || !(Pattern.matches(sRegular, emailText.getText())) && !emailText.getText().equals("")) {
                  regularChk[2] = false;
                  emailText.setForeground(Color.red);

               } else {
                  regularChk[2] = true;
                  emailText.setForeground(Color.black);

               }
               if ((!(Pattern.matches(phoneNumRegular, phoneNumText_1.getText())))
                     || !(Pattern.matches(sRegular, phoneNumText_1.getText()))
                           && !phoneNumText_1.getText().equals("")) {
                  regularChk[3] = false;
                  phoneNumText_1.setForeground(Color.red);

               } else {
                  regularChk[3] = true;
                  phoneNumText_1.setForeground(Color.black);

               }
               if ((!(Pattern.matches(phoneNumRegular, phoneNumText_2.getText())))
                     || !(Pattern.matches(sRegular, phoneNumText_2.getText()))
                           && !phoneNumText_2.getText().equals("")) {
                  regularChk[4] = false;
                  phoneNumText_2.setForeground(Color.red);

               } else {
                  regularChk[4] = true;
                  phoneNumText_2.setForeground(Color.black);

                  // ī���ȣ ��ȿ�� �˻� --> ���ڼ� 4�� ����, ���ڸ� ���� ..--------------------------------
               }
               if ((!(Pattern.matches(cardRegular, cardNumText_1.getText())))
                     || !(Pattern.matches(sRegular, cardNumText_1.getText()))
                           && !cardNumText_1.getText().equals("")) {
                  regularChk[5] = false;
                  cardNumText_1.setForeground(Color.red);

               } else {
                  regularChk[5] = true;
                  cardNumText_1.setForeground(Color.black);

               }
               if ((!(Pattern.matches(cardRegular, cardNumText_2.getText())))
                     || !(Pattern.matches(sRegular, cardNumText_2.getText()))
                           && !cardNumText_2.getText().equals("")) {
                  regularChk[6] = false;
                  cardNumText_2.setForeground(Color.red);

               } else {
                  regularChk[6] = true;
                  cardNumText_2.setForeground(Color.black);

               }
               if ((!(Pattern.matches(cardRegular, cardNumText_3.getText())))
                     || !(Pattern.matches(sRegular, cardNumText_3.getText()))
                           && !cardNumText_3.getText().equals("")) {
                  regularChk[7] = false;
                  cardNumText_3.setForeground(Color.red);

               } else {
                  regularChk[7] = true;
                  cardNumText_3.setForeground(Color.black);

               }
               if ((!(Pattern.matches(cardRegular, cardNumText_4.getText())))
                     || !(Pattern.matches(sRegular, cardNumText_4.getText()))
                           && !cardNumText_4.getText().equals("")) {
                  regularChk[8] = false;
                  cardNumText_4.setForeground(Color.red);

               } else {
                  regularChk[8] = true;
                  cardNumText_4.setForeground(Color.black);

               } // �ּҴ� �ѱ�/����/���ڸ� ����!!(�ʼ�����)
               if ((!(Pattern.matches(addressRegular, addressText.getText().trim().replaceAll(" ", ""))))
                     && !addressText.getText().equals("")) {
                  regularChk[9] = false;
                  addressText.setForeground(Color.red);

               } else {
                  regularChk[9] = true;
                  addressText.setForeground(Color.black);

               }
            }
         }
      }

   }

   class MessagePanel extends JPanel implements ActionListener { // ������_ �����ڿ��� �޼��� �г�

      JLabel msgLabel; // �޼��� ��
      JButton msgSend; // ���� �޼�����
      JButton msgGive; // ���� �޼�����
      JButton msgTextBt; // �޼��� ������
      JTextArea msgText; // �޼��� �Է�â
      SendMessage sendMessage; // ���� �޼����� ������ Ŭ����
      GiveMessage giveMessage; // ���� �޼����� ������ Ŭ����

      JScrollPane msgTextScroll; // �޼��� �Է� â ��ũ��

      ArrayList<JButton> msgButton; // �޼��� ������, ���� �޼�����, �����޼����� ��ư ����Ʈ

      public MessagePanel() { // �޼��� �г� ������

         msgButton = new ArrayList<JButton>(); // �޼��� ������, ����/���� �޼����� ��ư ����Ʈ

         msgLabel = new JLabel("�����ڿ��� �޼���"); // �޼��� �Է�â ��
         msgSend = new JButton("���� �޼���"); // ���� �޼����� ��ư
         msgGive = new JButton("���� �޼���"); // ���� �޼����� ��ư
         msgTextBt = new JButton("������"); // �޼��� ������ ��ư
         msgText = new JTextArea(); // �޼��� �Է� â
         msgTextScroll = new JScrollPane(msgText); // �޼��� �Է� â ��ũ��

         msgText.setLineWrap(true);

         msgButton.add(msgTextBt); // ��ư ����Ʈ�� �޼��� ������ ��ư �߰�
         msgButton.add(msgSend); // ��ư ����Ʈ�� ���� �޼����� �߰�
         msgButton.add(msgGive); // ��ư ����Ʈ�� ���� �޼����� �߰�

         msgLabel.setBounds(10, 50, 200, 50);
         msgSend.setBounds(30, 100, 200, 40);
         msgGive.setBounds(260, 100, 200, 40);
         msgTextScroll.setBounds(10, 180, 270, 200);
         msgTextBt.setBounds(290, 300, 100, 80);

         for (JButton jb : msgButton) {
            jb.addActionListener(this); // ��ư ����Ʈ�� �׼� ������
         }

         add(msgLabel);
         add(msgSend);
         add(msgGive);
         add(msgTextScroll);
         add(msgTextBt);
      }

      @Override
      public void actionPerformed(ActionEvent e) {
         if (e.getSource().equals(msgButton.get(0))) { // �޼��� ������ ��ư Ŭ�� ���� ��
            if (!(msgText.getText().isEmpty())) {
               MessageDB.saveMESSAGE("admin", userID, msgText.getText());
               JOptionPane.showMessageDialog(null, "���� �Ϸ�");
               // �޼��� �Է�â �ʱ�ȭ
               msgText.setText("");
            } else {
               JOptionPane.showMessageDialog(null, "�޼����� �Է��ϼ���");
            }
         } else if (e.getSource().equals(msgButton.get(1))) { // ���� �޼����� ��ư Ŭ�� ���� ��
            if (sendMessage == null) {
               sendMessage = new SendMessage(this);
               if (!sendMessage.chk) {
                  sendMessage = null;
               }
            }
         } else if (e.getSource().equals(msgButton.get(2))) { // ���� �޼����� ��ư Ŭ�� ���� ��
            if (giveMessage == null) {
               giveMessage = new GiveMessage(this);
               if (!giveMessage.chk) {
                  giveMessage = null;
               }
            }
         }

      }

   }

   SendMessageClick sendMessageClick;

   class SendMessage extends JFrame implements ActionListener, MouseListener, WindowListener { // �����޼����� ������ Ŭ����

      JTable sendList;
      JScrollPane scroll;
      JButton delete;

      boolean chk = true;
      String[][] arr2;
      MessagePanel messagePanel;

      public SendMessage(MessagePanel messagePanel) {
         setTitle("���� �޼�����");
         setBounds(600, 100, 515, 800);
         setLayout(null);
         this.messagePanel = messagePanel;
         arr2 = MessageDB.getFROM_MESSAGE(userID);
         if (arr2 == null) {
            JOptionPane.showMessageDialog(null, "�����޼������� ������ϴ�.");
            chk = false;
            return;
         }

         String[] arr = new String[] { "�������", "����", "�ð�" };

         sendList = new JTable(new NotEditTable(arr2, arr));
         scroll = new JScrollPane(sendList);
         delete = new JButton("����");

         scroll.setBounds(0, 150, 500, 500);
         delete.setBounds(390, 660, 100, 50);

         sendList.addMouseListener(this);
         delete.addActionListener(this);
         addWindowListener(this);
         add(delete);
         add(scroll);

         setVisible(true);
      }

      @Override
      public void actionPerformed(ActionEvent e) {

         if (e.getSource().equals(delete)) {
            MessageDB.deleteSendMESSAGE(userID);
            JOptionPane.showMessageDialog(null, "�����Ϸ�");
//         setVisible(false);
            messagePanel.sendMessage = null;
            dispose();
         }

      }

      @Override
      public void mouseClicked(MouseEvent e) {

         if (e.getSource().equals(sendList)) {
            if (e.getClickCount() == 2) {
               System.out.println(sendMessageClick);
               if (sendMessageClick == null) {
                  sendMessageClick = new SendMessageClick();
                  sendMessageClick.writer.setText(arr2[sendList.getSelectedRow()][0]);
                  sendMessageClick.content.setText(arr2[sendList.getSelectedRow()][1]);
                  sendMessageClick.time.setText(arr2[sendList.getSelectedRow()][2]);
               }
            }
         }

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
      public void mousePressed(MouseEvent e) {
         // TODO Auto-generated method stub

      }

      @Override
      public void mouseReleased(MouseEvent e) {
         // TODO Auto-generated method stub

      }

      @Override
      public void windowOpened(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      @Override
      public void windowClosing(WindowEvent e) {
         messagePanel.sendMessage = null;

      }

      @Override
      public void windowClosed(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      @Override
      public void windowIconified(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      @Override
      public void windowDeiconified(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      @Override
      public void windowActivated(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      @Override
      public void windowDeactivated(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

   }

   class SendMessageClick extends JFrame implements WindowListener {

      JLabel writerLabel;
      JLabel contentLabel;
      JLabel timeLabel;

      JLabel writer;
      JLabel time;
      JTextArea content;

      public SendMessageClick() {

         setBounds(600, 100, 400, 500);
         setLayout(null);
         addWindowListener(this);
         writerLabel = new JLabel("������� : ");
         timeLabel = new JLabel("�����ð� : ");
         contentLabel = new JLabel("����");

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
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      @Override
      public void windowClosing(WindowEvent e) {
         sendMessageClick = null;

      }

      @Override
      public void windowClosed(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      @Override
      public void windowIconified(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      @Override
      public void windowDeiconified(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      @Override
      public void windowActivated(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      @Override
      public void windowDeactivated(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

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

   GiveMessageClick giveMessageClick; // ���� ���ִ� �޽���â

   class GiveMessage extends JFrame implements ActionListener, MouseListener, WindowListener { // �����޼����� ������ Ŭ����

      JTable giveList;
      JScrollPane scroll;
      JButton delete;
      String[][] arr2;
      MessagePanel messagePanel;
      boolean chk = true;

      public GiveMessage(MessagePanel messagePanel) {
         this.messagePanel = messagePanel;
         setTitle("���� �޼�����");
         setBounds(600, 100, 515, 800);
         setLayout(null);

         arr2 = MessageDB.getTO_MESSAGE(userID);
         String[] arr = new String[] { "�������", "����", "�ð�" };
         if (arr2 == null) {
            JOptionPane.showMessageDialog(null, "�����޼������� ������ϴ�.");
            chk = false; // �������� ����
            return;
         }
         addWindowListener(this);
         giveList = new JTable(new NotEditTable(arr2, arr));
         scroll = new JScrollPane(giveList);
         delete = new JButton("����");

         scroll.setBounds(0, 150, 500, 500);
         delete.setBounds(390, 660, 100, 50);

         delete.addActionListener(this);

         giveList.setCellEditor(null);
         giveList.addMouseListener(this);

         add(delete);
         add(scroll);

         setVisible(true);
      }

      @Override
      public void actionPerformed(ActionEvent e) {

         MessageDB.deleteGiveMESSAGE(userID);
         JOptionPane.showMessageDialog(null, "�����Ϸ�");
//         setVisible(false);
         messagePanel.giveMessage = null;
         dispose();

      }

      @Override
      public void mouseClicked(MouseEvent e) {

         if (e.getSource().equals(giveList)) {
            if (e.getClickCount() == 2) {
               if (giveMessageClick == null) {
                  giveMessageClick = new GiveMessageClick();
                  giveMessageClick.writer.setText(arr2[giveList.getSelectedRow()][0]);
                  giveMessageClick.content.setText(arr2[giveList.getSelectedRow()][1]);
                  giveMessageClick.time.setText(arr2[giveList.getSelectedRow()][2]);
               }
            }
         }

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
      public void mousePressed(MouseEvent e) {
         // TODO Auto-generated method stub

      }

      @Override
      public void mouseReleased(MouseEvent e) {
         // TODO Auto-generated method stub

      }

      @Override
      public void windowOpened(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      @Override
      public void windowClosing(WindowEvent e) {
         messagePanel.giveMessage = null;

      }

      @Override
      public void windowClosed(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      @Override
      public void windowIconified(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      @Override
      public void windowDeiconified(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      @Override
      public void windowActivated(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      @Override
      public void windowDeactivated(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

   }

   class GiveMessageClick extends JFrame implements WindowListener {

      JLabel writerLabel;
      JLabel contentLabel;
      JLabel timeLabel;

      JLabel writer;
      JLabel time;
      JTextArea content;

      public GiveMessageClick() {

         setTitle("�����޼���");
         setBounds(600, 100, 400, 500);
         setLayout(null);

         writerLabel = new JLabel("������� : ");
         timeLabel = new JLabel("�����ð� : ");
         contentLabel = new JLabel("����");

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
         addWindowListener(this);
         setVisible(true);

      }

      @Override
      public void windowOpened(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      @Override
      public void windowClosing(WindowEvent e) {
         giveMessageClick = null;

      }

      @Override
      public void windowClosed(WindowEvent e) {

      }

      @Override
      public void windowIconified(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      @Override
      public void windowDeiconified(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      @Override
      public void windowActivated(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      @Override
      public void windowDeactivated(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

   }

   class NoticePanel extends JPanel implements ActionListener { // ������_ �������� �г�

      Vector<String> notice; // �������� (�ӽ�)
      JButton noticeGo; // ����Ȯ�� ��ư (�ӽ�)
      JComboBox noticeBox;
      NoticeFrame noticeFrame;
      MessagePanel msgPanel;
      ArrayList<Notice> notices;

      public NoticePanel() {

         notices = NoticeDB.getNOTICE();
         notice = new Vector<String>(); // ����.add�� �޺��ڽ� �߰�
         for (Notice ntc : notices) {
            notice.add(ntc.title);
         }
         if (notice.isEmpty()) {
            notice.add("�������׾���");
         }
         noticeGo = new JButton("����Ȯ��");
         noticeGo.setBounds(400, 100, 90, 50);

         noticeBox = new JComboBox(notice);
         noticeBox.setBounds(10, 100, 370, 50);

         noticeBox.setBackground(Color.white);

         noticeGo.addActionListener(this);

         add(noticeBox);
         add(noticeGo);
      }

      @Override
      public void actionPerformed(ActionEvent e) {
         String title = (String) noticeBox.getSelectedItem();
         Notice ntc = null;
         for (Notice ntc1 : notices) {
            if (ntc1.title.equals(title)) {
               ntc = ntc1;
               break;
            }
         }
         if (noticeFrame == null) {
            noticeFrame = new NoticeFrame(ntc, this);
         }
      }
   }

   class NoticeFrame extends JFrame implements WindowListener { // ���� Ȯ�� ������
      JLabel noticeLabel;
      JTextArea contentLabel;
      JLabel writerLabel;
      JLabel modifLabel;
      JLabel makeTimeLabel;

      JLabel title;
      JLabel makeTime;
      JLabel modifTime;
      JLabel writer;
      NoticePanel np;

      public NoticeFrame(Notice notice, NoticePanel np) {

         setTitle("��������");

         setBounds(jumTalkOption_User.getX() + jumTalkOption_User.getWidth() + 50, jumTalkOption_User.getY(), 700,
               800);
         setLayout(null);
         this.np = np;
         noticeLabel = new JLabel(notice.title);
         contentLabel = new JTextArea(notice.content);
         writerLabel = new JLabel(notice.writer);
         modifLabel = new JLabel(notice.modi_time);
         makeTimeLabel = new JLabel(notice.maketime);

         title = new JLabel("���� : ");
         makeTime = new JLabel("�ۼ��ð� : ");
         modifTime = new JLabel("�����ð� : ");
         writer = new JLabel("�ۼ��� : ");

         noticeLabel.setBounds(300, 70, 100, 50);
         contentLabel.setBounds(40, 160, 600, 600);
         writerLabel.setBounds(600, 100, 100, 50);
         makeTimeLabel.setBounds(80, 10, 200, 30);
         modifLabel.setBounds(80, 50, 200, 30);
         addWindowListener(this);
         title.setBounds(255, 70, 50, 50);
         makeTime.setBounds(10, 10, 100, 30);
         modifTime.setBounds(10, 50, 100, 30);
         writer.setBounds(530, 100, 100, 50);

         contentLabel.setEditable(false);
         contentLabel.setLineWrap(true);

         add(noticeLabel);
         add(contentLabel);
         add(writerLabel);
         add(modifLabel);
         add(makeTimeLabel);
         add(title);
         add(makeTime);
         add(modifTime);
         add(writer);

         setVisible(true);
      }

      @Override
      public void windowOpened(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      @Override
      public void windowClosing(WindowEvent e) {
         np.noticeFrame = null;

      }

      @Override
      public void windowClosed(WindowEvent e) {

      }

      @Override
      public void windowIconified(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      @Override
      public void windowDeiconified(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      @Override
      public void windowActivated(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }

      @Override
      public void windowDeactivated(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����

      }
   }
}