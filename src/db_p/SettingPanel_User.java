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

class SellerReviseInfor2 extends JFrame implements ActionListener, WindowListener { // Á¡¼ú°¡ Á¤º¸ ¼öÁ¤ ÇÁ·¹ÀÓ (¼ÒºñÀÚ ¼öÁ¤ ÇÁ·¹ÀÓ »ó¼Ó)

   JLabel profile; // »ó¼¼Á¤º¸
   JLabel photo; // »çÁø
   JLabel helloMsg; // ÀÎ»ç¸»
   JLabel nowPw;
   JLabel pw; // ºñ¹Ð¹øÈ£
   JLabel pwChk;
   JLabel phoneNum; // ÀüÈ­¹øÈ£
   JLabel email; // ÀÌ¸ÞÀÏ
   JLabel address; // ÁÖ¼Ò
   JLabel cardNum; // Ä«µå¹øÈ£
   JLabel pwHint; // ºñ¹Ð¹øÈ£ ÈùÆ®
   Vector<String> passWordHint_S = new Vector<String>();
   JComboBox pwhint;
   JLabel hintAnswer; // ºñ¹Ð¹øÈ£ ÈùÆ® ´ä
   JLabel workplaceName; // Á¡¼ú°¡ »ç¾÷Àå ÀÌ¸§
   JLabel workplaceAddress; // Á¡¼ú°¡ »ç¾÷Àå ÁÖ¼Ò
   ArrayList<JLabel> information; // °³ÀÎÁ¤º¸ ¸®½ºÆ®
   JButton reviseGo; // °³ÀÎÁ¤º¸ ¼öÁ¤ ¿Ï·á ¹öÆ°

   JButton photoButton;

   JLabel photoFileName;

   JTextField helloText; // ÀÎ»ç¸» ¼öÁ¤ÀÔ·Â
   JPasswordField nowPwText;
   JPasswordField pwField; // ºñ¹Ð¹øÈ£ ¼öÁ¤ÀÔ·Â
   JPasswordField pwChkText;

   Vector<String> pnNum;// Æù¹øÈ£ ¼öÁ¤ÀÔ·Â
   JComboBox phoneNumBox;
   JTextField phoneNumText_1;
   JTextField phoneNumText_2;
   JLabel phoneLabel; // ÇÚµåÆù Â¦´ë±â
   JLabel phoneLabel_2; // ÇÚµåÆù Â¦´ë±â

   JTextField emailText; // ÀÌ¸ÞÀÏ ¼öÁ¤ÀÔ·Â
   JTextField addressText; // ÁÖ¼Ò ¼öÁ¤ÀÔ·Â
   JTextField cardNumText; // Ä«µå¹øÈ£ ¼öÁ¤ÀÔ·Â
   JTextField pwHintText; // ºñ¹Ð¹øÈ£ ÈùÆ® ¼öÁ¤ÀÔ·Â
   JTextField hintAnswerText; // ÈùÆ® ´ä ¼öÁ¤ÀÔ·Â
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

   // À¯È¿¼º°Ë»ç
   String pwRegular = "^.*(?=^.{5,19}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$"; // ºñ¹Ð¹øÈ£ À¯È¿¼º --> 6~20ÀÚ ÀÌ³»ÀÇ ¿µ¹®
                                                                  // ´ë¼Ò¹®ÀÚ/¼ýÀÚ/Æ¯¼ö¹®ÀÚ ¹Ýµå½Ã Æ÷ÇÔ
   String phoneNumRegular = "\\d{3,4}$"; // ÀüÈ­¹øÈ£ ¼ýÀÚ 4ÀÚ ÀÌ³»·Î
   String emailRegular = "[a-zA-Z0-9]*@[a-zA-Z.]*"; // ÀÌ¸ÞÀÏ ´ë¼Ò¹®ÀÚ/¼ýÀÚ
   String nameRegular = "^[°¡-ÆR]*$"; // ÀÌ¸§ ÇÑ±Û¸¸ °¡´É
   String addressRegular = "^[-a-zA-Z°¡-ÆR0-9]*$"; //
   String businessName_R = "^[a-zA-Z°¡-ÆR0-9]*$"; // »ç¾÷Àå¸í
   String businessAddress_R = "^[0-9-]*$"; // °èÁÂ¹øÈ£
   String sRegular = "\\S*"; // °ø¹é

   String phoneDB;

   OptionPanel optionPanel;

   public SellerReviseInfor2(String userID, OptionPanel optionPanel) { // Á¤º¸¼öÁ¤ »ý¼ºÀÚ
      this.userID = userID;
      setTitle("°³ÀÎÁ¤º¸ ¼öÁ¤"); // °³ÀÎÁ¤º¸ ¼öÁ¤ ÇÁ·¹ÀÓ Å¸ÀÌÆ²
      this.optionPanel = optionPanel;
      setBounds(700, 0, 600, 900);
      setLayout(null);
      addWindowListener(this);

      // °³ÀÎÁ¤º¸ ¶óº§ »ý¼º
      profile = new JLabel("»ó¼¼ ÇÁ·ÎÇÊ");
      photo = new JLabel("»çÁø ¼öÁ¤");
      helloMsg = new JLabel("ÀÎ»ç¸» ¼öÁ¤");
      nowPw = new JLabel("±âÁ¸ PW");
      pw = new JLabel("PW ¼öÁ¤");
      pwChk = new JLabel("PW È®ÀÎ");
      email = new JLabel("ÀÌ¸ÞÀÏ ¼öÁ¤");
      phoneNum = new JLabel("ÀüÈ­¹øÈ£ ¼öÁ¤");
      address = new JLabel("ÁÖ¼Ò ¼öÁ¤");
      cardNum = new JLabel("°èÁÂ¹øÈ£ ¼öÁ¤");
      pwHint = new JLabel("PWÈùÆ® ¼öÁ¤");
      hintAnswer = new JLabel("ÈùÆ® ´ä ¼öÁ¤");
      workplaceName = new JLabel("»ç¾÷Àå ÀÌ¸§");
      workplaceAddress = new JLabel("»ç¾÷Àå ÁÖ¼Ò");

      helloText = new JTextField(); // ÀÎ»ç¸»
      nowPwText = new JPasswordField(); // ±âÁ¸ ºñ¹Ð¹øÈ£
      pwField = new JPasswordField(); // ºñ¹Ð¹øÈ£ º¯°æ
      pwChkText = new JPasswordField(); // ºñ¹Ð¹øÈ£ È®ÀÎ

      phoneNumBox = new JComboBox(); // ÇÚµåÆù¹øÈ£ ¼±ÅÃ
      phoneNumText_1 = new JTextField(); // µÎ¹øÂ° ÇÚµåÆù ¹øÈ£ ÀÔ·ÂÃ¢
      phoneNumText_2 = new JTextField(); // ¼¼¹øÂ° ÇÚµåÆù ¹øÈ£ ÀÔ·ÂÃ¢
      phoneLabel = new JLabel("-");
      phoneLabel_2 = new JLabel("-");

      emailText = new JTextField(); // ÀÌ¸ÞÀÏ ÀÔ·Â
      addressText = new JTextField(); // ÁÖ¼Ò ÀÔ·Â
      cardNumText = new JTextField(); // Ä«µå¹øÈ£ ÀÔ·Â
      hintAnswerText = new JTextField(); // ºñ¹Ð¹øÈ£ ÈùÆ® ´ä
      workNameText = new JTextField(); // »ç¾÷Àå¸í
      workAddressText = new JTextField(); // »ç¾÷ÀåÁÖ¼Ò

      // ÇÚµåÆù¹øÈ£ ¾ÕÀÚ¸® ------------------------------------
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

      bankChoice.add("-ÀºÇà¼±ÅÃ-");
      bankChoice.add("±â¾÷ÀºÇà");
      bankChoice.add("³óÇùÀºÇà");
      bankChoice.add("½ÅÇÑÀºÇà");
      bankChoice.add("»ê¾÷ÀºÇà");
      bankChoice.add("¿ì¸®ÀºÇà");
      bankChoice.add("¾¾Æ¼ÀºÇà");
      bankChoice.add("ÇÏ³ªÀºÇà");
      bankChoice.add("±¤ÁÖÀºÇà");
      bankChoice.add("°æ³²ÀºÇà");
      bankChoice.add("´ë±¸ÀºÇà");
      bankChoice.add("±¤ÁÖÀºÇà");
      bankChoice.add("ºÎ»êÀºÇà");
      bankChoice.add("¼öÇùÀºÇà");
      bankChoice.add("Á¦ÁÖÀºÇà");
      bankChoice.add("Á¦ÁÖÀºÇà");
      bankChoice.add("ÀüºÏÀºÇà");
      bankChoice.add("»õ¸¶À»±Ý°í");
      bankChoice.add("½ÅÇùÀºÇà");
      bankChoice.add("¼öÇùÀºÇà");
      bankChoice.add("¿ìÃ¼±¹");

      bankBox = new JComboBox<String>(bankChoice);

      ImageIcon ii = new ImageIcon(pfio.download(userID));
      Image img = ii.getImage();
      img = img.getScaledInstance(100, 100, img.SCALE_SMOOTH);
      ii = new ImageIcon(img);
      photoFileName = new JLabel(ii);

      photoButton = new JButton("»çÁø ¼öÁ¤");
      photoButton.addActionListener(this);
      passWordHint_S.add("¿øÇÏ´Â Áú¹®À» ¼±ÅÃÇÏ¼¼¿ä.");
      passWordHint_S.add("°¡Àå ±â¾ï¿¡ ³²´Â Àå¼Ò´Â?");
      passWordHint_S.add("³ªÀÇ º¸¹° 1È£´Â?");
      passWordHint_S.add("º»ÀÎÀÇ Ãâ»ýÁö´Â?");
      passWordHint_S.add("³»°¡ Á¸°æÇÏ´Â ÀÎ¹°Àº?");
      passWordHint_S.add("³ªÀÇ ÁÂ¿ì¸íÀº?");
      passWordHint_S.add("°¡Àå °¨¸í±í°Ô º» ¿µÈ­´Â?");
      passWordHint_S.add("³»°¡ ÁÁ¾ÆÇÏ´Â ¹ÂÁö¼ÇÀº?");
      passWordHint_S.add("ÀÎ»ó±í°Ô ÀÐÀº Ã¥ Á¦¸ñÀº?");
      passWordHint_S.add("³ªÀÇ ³ë·¡¹æ ¾ÖÃ¢°îÀº?");

      pwhint = new JComboBox(passWordHint_S);

      // °³ÀÎÁ¤º¸ ¸®½ºÆ®¿¡ °³ÀÎÁ¤º¸ ¶óº§ ´ã±â
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

      // ¼öÁ¤¿Ï·á ¹öÆ°
      reviseGo = new JButton("¼öÁ¤ ¿Ï·á");
      // °³ÀÎÁ¤º¸ ¼öÁ¤ ÇÁ·¹ÀÓ ³ª°¡±â ¹öÆ°

      reviseGo.setBounds(470, 10, 100, 40);

      photoFileName.setBounds(130, 10, 100, 100);
      photoButton.setBounds(330, 60, 100, 50);
      helloText.setBounds(130, 120, 300, 50);
      nowPwText.setBounds(130, 180, 300, 50);
      pwField.setBounds(130, 240, 300, 50);
      pwChkText.setBounds(130, 300, 300, 50);

      // ÇÚµåÆù
      phoneNumBox.setBounds(130, 360, 90, 50); // 010 ....
      phoneNumText_1.setBounds(230, 360, 90, 50); // µÎ¹øÂ° ÀüÈ­¹øÈ£ÀÔ·ÂÇÏ´Â Ã¢
      phoneNumText_2.setBounds(330, 360, 90, 50); // ¼¼¹øÂ° ÀüÈ­¹øÈ£ ÀÔ·ÂÇÏ´Â Ã¢
      phoneLabel.setBounds(220, 360, 10, 50); // Â¦´ë±â
      phoneLabel_2.setBounds(320, 360, 10, 50); // Â¦´ë±â

      emailText.setBounds(130, 420, 300, 50);
      addressText.setBounds(130, 480, 300, 50);
      cardNumText.setBounds(130, 540, 300, 50);
      pwhint.setBounds(130, 600, 300, 50);
      hintAnswerText.setBounds(130, 660, 300, 50);
      workNameText.setBounds(130, 720, 300, 50);
      workAddressText.setBounds(130, 780, 300, 50);

      bankBox.setBounds(450, 540, 100, 50);

      for (int i = 1; i < information.size(); i++) { // °³ÀÎÁ¤º¸ ¶óº§ À§Ä¡ Àâ±â(¸®½ºÆ®)
         information.get(i).setBounds(10, i * 60 + 120, 100, 50);
      }

      photo.setBounds(10, 60, 100, 50);
      helloMsg.setBounds(10, 120, 100, 50);
      nowPw.setBounds(10, 180, 100, 50);
      pw.setBounds(10, 240, 100, 50);
      pwChk.setBounds(10, 300, 100, 50);

      reviseGo.addActionListener(this); // ¼öÁ¤¿Ï·á ¹öÆ°

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

      add(reviseGo); // °³ÀÎÁ¤º¸ ¼öÁ¤ ÇÁ·¹ÀÓ¿¡ ¼öÁ¤¿Ï·á ¹öÆ° Ãß°¡

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
         add(jl); // °³ÀÎÁ¤º¸ ¼öÁ¤ ÇÁ·¹ÀÓ¿¡ °³ÀÎÁ¤º¸ ¶óº§ Ãß°¡
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

      if (e.getSource().equals(reviseGo)) { // °³ÀÎÁ¤º¸ ¼öÁ¤ ÇÁ·¹ÀÓ¿¡¼­ ¿Ï·á ¹öÆ° Å¬¸¯
         // ¼öÁ¤¿Ï·á ÆË¾÷ ¶ç¿îÈÄ ÇÁ·¹ÀÓ »ç¶óÁö±â
         System.out.println("µé¾î¿À´Ï");
         // DBµé¾î°¥¶§
         phoneDB = phoneNumBox.getSelectedItem().toString() + "-" + phoneNumText_1.getText() + "-"
               + phoneNumText_2.getText(); // ÇÚµåÆù¹øÈ£

         if (pwhint.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "ºñ¹Ð¹øÈ£ ÈùÆ®¸¦ ¼±ÅÃÇØ ÁÖ¼¼¿ä");
         } else if (!(pwField.getText().equals(pwChkText.getText()))) {
            JOptionPane.showMessageDialog(null, "ºñ¹Ð¹øÈ£¸¦ ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä");
         } else if (bankBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "ÀºÇàÀ» ¼±ÅÃÇØ ÁÖ¼¼¿ä");
         } else if (cnt != 9) {
            System.out.println(cnt);
            JOptionPane.showMessageDialog(null, "¿Ã¹Ù¸¥ Çü½ÄÀÌ ¾Æ´Õ´Ï´Ù");
         } else {
            JOptionPane.showMessageDialog(null, "¼öÁ¤¿Ï·á");
            signUpEnd = false;
            System.out.println(signUpEnd);
            if (!(helloText.getText().equals(""))) {
               System.out.println("µé¾î¿À´Ï1");
               UserDB.setPROFILE_TEXT(userID, helloText.getText());
            }
            if (!(pwField.getText().equals(""))) {
               System.out.println("µé¾î¿À´Ï2");
               UserDB.setPW(userID, pwField.getText());
            }
            if (!(phoneDB.equals(""))) {
               System.out.println("µé¾î¿À´Ï3");
               UserDB.setPHONE(userID, phoneDB);
            }
            if (!(emailText.getText().equals(""))) {
               System.out.println("µé¾î¿À´Ï4");
               UserDB.setEMAIL(userID, emailText.getText());
            }
            if (!(addressText.getText().equals(""))) {
               System.out.println("µé¾î¿À´Ï5");
               UserDB.setADDRESS(userID, addressText.getText());
            }
            if (!(cardNumText.getText().equals(""))) {
               System.out.println("µé¾î¿À´Ï6");
               String bankStr = (String) bankBox.getSelectedItem();
               UserDB.setBANKNUMBER(userID, cardNumText.getText() + "/" + bankStr);
            }
            if (!(pwhint.getSelectedItem().equals(""))) {
               System.out.println("µé¾î¿À´Ï7");
               UserDB.setPWHINT(userID, pwhint.getSelectedItem().toString());
            }
            if (!(hintAnswerText.getText().equals(""))) {
               System.out.println("µé¾î¿À´Ï8");
               System.out.println(hintAnswerText.getText());
               UserDB.setPWRES(userID, hintAnswerText.getText());
            }
            if (!(workNameText.getText().equals(""))) {
               System.out.println("µé¾î¿À´Ï9");
               System.out.println(workNameText.getText());
               UserDB.setBUSINESSNAME(userID, workNameText.getText());
            }
            if (!(workAddressText.getText().equals(""))) {
               System.out.println("µé¾î¿À´Ï10");
               System.out.println(workAddressText.getText() + userID);
               UserDB.setBUSINESSADDRESS(userID, workAddressText.getText());
            }
            optionPanel.ri = null;
//            setVisible(false);
            dispose();
            System.out.println("¾²·¹µå" + signUpEnd);
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
            JOptionPane.showMessageDialog(null, "jpgÆÄÀÏ¸¸ °¡´ÉÇÕ´Ï´Ù");
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

   // À¯È¿¼º °Ë»ç ¾²·¹µå
   class SellerSignUpChk extends Thread {

      @Override
      public void run() {
         while (signUpEnd) {
            // ºñ¹Ð¹øÈ£ À¯È¿¼º °Ë»ç --> ¼ýÀÚ,¿µ¹®, Æ¯¼ö¹®ÀÚ Æ÷ÇÔ (°ø¹é ±ÝÁö) ---------------------------------
            if (((!(Pattern.matches(pwRegular, pwField.getText())))
                  || !(Pattern.matches(sRegular, pwField.getText()))) && !pwField.getText().equals("")) {
               regularChk[0] = false;
               pwField.setForeground(Color.red);
               pwField.setOpaque(true);

            } else {
               regularChk[0] = true;
               pwField.setForeground(Color.black);

               // ºñ¹Ð¹øÈ£ == ºñ¹Ð¹øÈ£ È®ÀÎ ÀÏÄ¡ÇÑÁö °Ë»ç!!
            }
            if (!(pwField.getText().equals(pwChkText.getText()))) {
               regularChk[1] = false;
               pwChkText.setForeground(Color.red);

            } else {
               regularChk[1] = true;
               pwChkText.setForeground(Color.black);

               // ÀÌ¸§ À¯È¿¼º °Ë»ç --> ÇÑ±Û¸¸ »ç¿ë°¡´É --------------------------------
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

               // Ä«µå¹øÈ£ À¯È¿¼º °Ë»ç --> ±ÛÀÚ¼ö 4ÀÚ Á¦ÇÑ, ¼ýÀÚ¸¸ °¡´É ..--------------------------------
            }
            // ÁÖ¼Ò´Â ÇÑ±Û/¿µ¹®/¼ýÀÚ¸¸ °¡´É!!(ÃÊ¼º±ÝÁö)
            if ((!(Pattern.matches(addressRegular, addressText.getText().trim().replaceAll(" ", ""))))
                  && !addressText.getText().equals("")) {
               regularChk[5] = false;
               addressText.setForeground(Color.red);

            } else {
               regularChk[5] = true;
               addressText.setForeground(Color.black);

            } // »ç¾÷Àå¸í
            if ((!(Pattern.matches(businessName_R, workNameText.getText().trim().replaceAll(" ", ""))))
                  && !workNameText.getText().equals("")) {
               regularChk[6] = false;
               workNameText.setForeground(Color.red);

            } else {
               regularChk[6] = true;
               workNameText.setForeground(Color.black);
            }
            // »ç¾÷ÀåÁÖ¼Ò
            if ((!(Pattern.matches(addressRegular, workAddressText.getText().trim().replaceAll(" ", ""))))
                  && !workAddressText.getText().equals("")) {
               regularChk[7] = false;
               workAddressText.setForeground(Color.red);

            } else {
               regularChk[7] = true;
               workAddressText.setForeground(Color.black);

               // °èÁÂ¹øÈ£
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

      fd = new FileDialog(this, "ÆÄÀÏ¿­±â", FileDialog.LOAD);
      fd.setLocation(510, 210);
      fd.setVisible(true);
   }
}

//   class OptionAction implements ActionListener{   //°í°´¼¾ÅÍ, °³ÀÎÁ¤º¸ ¼öÁ¤ ¹öÆ° ¾×¼Ç ¸®½º³Ê
//      
////      JButton opt1;   //°í°´¼¾ÅÍ ¹öÆ°
////      JButton opt2;   //°³ÀÎÁ¤º¸ ¼öÁ¤ ¹öÆ°
////      JFrame help;
//      JPanel notice;   //°øÁö»çÇ× ÆÐ³Î
//      JPanel message;   //¸Þ¼¼Áö ÆÐ³Î
//      
////      JumTalkOption jt;
//      ReviseInfor ri;   //¼ÒºñÀÚ °³ÀÎÁ¤º¸ ¼öÁ¤ Å¬·¡½º 
//      JumTalkOption_User jto;
////      Opt1_Frame opt1_F;
//      
//      public OptionAction(JPanel notice, JPanel message) {   //°øÁö»çÇ×, ¸Þ¼¼Áö ÆÐ³Î(visible)À» À§ÇØ ¸Å°³º¯¼ö
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

class OptionPanel extends JPanel implements ActionListener { // °í°´, ¼öÁ¤¹öÆ° ¹Ø¿¡ ÆÐ³Î
   JPanel optPanel; // °í°´¼¾ÅÍ, °³ÀÎÁ¤º¸ ¼öÁ¤ ¹öÆ°ÀÌ ´ã±ä ÆÐ³Î

   JFrame ri;
   JLabel ntcLabel; // °øÁö»çÇ× ¶óº§
   ArrayList<JButton> optButton;
   NoticePanel ntcPanel; // °í°´¼¾ÅÍÀÇ °øÁö»çÇ× ÆÐ³Î
   MessagePanel msgPanel; // °í°´¼¾ÅÍÀÇ ¸Þ¼¼Áö ÆÐ³Î

   JButton opt1; // ¼³Á¤ÀÇ °í°´¼¾ÅÍ
   JButton opt2; // ¼³Á¤ÀÇ °³ÀÎÁ¤º¸ ¼öÁ¤

   String userID;
   JFrame jumTalkOption_User;

   public OptionPanel(String userID, JFrame jp) {
      this.userID = userID;
      this.jumTalkOption_User = jp;

      optButton = new ArrayList<JButton>(); // ¼³Á¤, °³ÀÎÁ¤º¸ ¼öÁ¤ ¹öÆ° ¸®½ºÆ®

      optPanel = new JPanel(); // ¼³Á¤¹öÆ° ÆÐ³Î
      optPanel.setBounds(0, 615, 500, 50);

      ntcPanel = new NoticePanel(); // °øÁö»çÇ× ÆÐ³Î
      ntcPanel.setBounds(0, 0, 500, 200);
      msgPanel = new MessagePanel(); // ¸Þ¼¼Áö ÆÐ³Î
      msgPanel.setBounds(0, 210, 500, 400);

      opt1 = new JButton("·Î±×¾Æ¿ô"); // °í°´¼¾ÅÍ ¹öÆ°
      opt2 = new JButton("°³ÀÎÁ¤º¸ ¼öÁ¤"); // °³ÀÎÁ¤º¸ ¼öÁ¤ ¹öÆ°

      ntcLabel = new JLabel("°øÁö»çÇ×"); // °øÁö»çÇ× ¶óº§ (ÀÓ½Ã)

      ntcPanel.setLayout(null); // °øÁö»çÇ× ÆÐ³Î ·¹ÀÌ¾Æ¿ô
      msgPanel.setLayout(null); // ¸Þ¼¼Áö ÆÐ³Î ·¹ÀÌ¾Æ¿ô

      ntcLabel.setBounds(220, 30, 200, 50); // °øÁö»çÇ× ¶óº§ À§Ä¡ (ÀÓ½Ã)

      optButton.add(opt1); // ¹öÆ° ¸®½ºÆ®¿¡ ¼³Á¤¹öÆ° ´ãÀ½
      optButton.add(opt2); // ¹öÆ° ¸®½ºÆ®¿¡ °³ÀÎÁ¤º¸ ¼öÁ¤ ¹öÆ° ´ãÀ½

      // °øÁö»çÇ× ÆÐ³Î
      ntcPanel.add(ntcLabel); // °øÁö»çÇ× ÆÐ³Î¿¡ °øÁö»çÇ× ¶óº§ Ãß°¡(ÀÓ½Ã)

      // °ü¸®ÀÚ¿¡°Ô ÂÊÁö

//         oa = new OptionAction(ntcPanel,msgPanel);   //°í°´¼¾ÅÍ, °³ÀÎÁ¤º¸ ¼öÁ¤ ¾×¼Ç ¸®½º³Ê »ý¼º

      ntcPanel.setVisible(true); // ¾×¼Ç ¸®½º³Ê·Î ³ª¿À°ÔÇÒ °Í (°øÁö»çÇ× ÆÐ³Î)
      msgPanel.setVisible(true); // ¾×¼Ç ¸®½º³Ê·Î ³ª¿À°ÔÇÒ °Í (¸Þ¼¼Áö ÆÐ³Î)

      setLayout(null); // °í°´¼¾ÅÍ, °³ÀÎÁ¤º¸ ¼öÁ¤ ¹öÆ° ¹Ø¿¡ ÆÐ³Î

      optPanel.setLayout(new GridLayout(1, 2)); // °í°´¼¾ÅÍ, °³ÀÎÁ¤º¸ ¼öÁ¤ ¹öÆ° ÆÐ³Î

      optPanel.add(opt2); // °í°´¼¾ÅÍ, °³ÀÎÁ¤º¸ ¼öÁ¤ ¹öÆ° ÆÐ³Î¿¡ °³ÀÎÁ¤º¸ ¼öÁ¤ ¹öÆ° Ãß°¡
      optPanel.add(opt1); // °í°´¼¾ÅÍ, °³ÀÎÁ¤º¸ ¼öÁ¤ ¹öÆ° ÆÐ³Î¿¡ °í°´¼¾ÅÍ ¹öÆ° Ãß°¡
      add(optPanel);
      add(ntcPanel); // °øÁö»çÇ× ÆÐ³Î Ãß°¡
      add(msgPanel); // ¸Þ¼¼Áö ÆÐ³Î Ãß°¡
      for (JButton jb : optButton) { // °í°´¼¾ÅÍ, °³ÀÎÁ¤º¸ ¼öÁ¤¹öÆ° ¸®½ºÆ®
         jb.addActionListener(this);
      }

      repaint();
      setVisible(true);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource().equals(optButton.get(0))) {
         // ·Î±×ÀÎ ÆÐ³Î·Î ¹Ù²ñ
         JOptionPane.showMessageDialog(null, "·Î±×¾Æ¿ô ¿Ï·á");
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

   class ReviseInfor extends JFrame implements ActionListener, WindowListener { // °³ÀÎÁ¤º¸ ¼öÁ¤ ÇÁ·¹ÀÓ

      JLabel profile; // »ó¼¼Á¤º¸
      JLabel photo; // »çÁø
      JLabel helloMsg; // ÀÎ»ç¸»
      JLabel pw; // ºñ¹Ð¹øÈ£
      JLabel phoneNum; // ÀüÈ­¹øÈ£
      JLabel phoneLabel_1;
      JLabel phoneLabel_2;
      JLabel email; // ÀÌ¸ÞÀÏ
      JLabel address; // ÁÖ¼Ò
      JLabel cardNum; // Ä«µå¹øÈ£
      JLabel cardLabel_1;
      JLabel cardLabel_2;
      JLabel cardLabel_3;
      JLabel pwHint; // ºñ¹Ð¹øÈ£ ÈùÆ®
      JLabel hintAnswer; // ºñ¹Ð¹øÈ£ ÈùÆ® ´ä
      ArrayList<JLabel> information; // °³ÀÎÁ¤º¸ ¸®½ºÆ®
      JButton reviseGo; // °³ÀÎÁ¤º¸ ¼öÁ¤ ¿Ï·á ¹öÆ°

      JButton photoButton;
      JLabel photoFileName;
      JLabel nowPw;
      JLabel pwChk;

      JTextField helloText;
      JPasswordField nowPwField;
      JPasswordField pwField;
      JPasswordField pwChkText;
      Vector<String> pnNum = new Vector<String>();// Æù¹øÈ£ ¼öÁ¤ÀÔ·Â
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

      String pwRegular = "^.*(?=^.{5,19}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$"; // ºñ¹Ð¹øÈ£ À¯È¿¼º --> 6~20ÀÚ ÀÌ³»ÀÇ ¿µ¹®
      // ´ë¼Ò¹®ÀÚ/¼ýÀÚ/Æ¯¼ö¹®ÀÚ ¹Ýµå½Ã Æ÷ÇÔ
      String phoneNumRegular = "\\d{3,4}$"; // ÀüÈ­¹øÈ£ ¼ýÀÚ 4ÀÚ ÀÌ³»·Î
      String emailRegular = "^[0-9a-zA-Z@.].*"; // ÀÌ¸ÞÀÏ ´ë¼Ò¹®ÀÚ/¼ýÀÚ
      String nameRegular = "^[°¡-ÆR]*$"; // ÀÌ¸§ ÇÑ±Û¸¸ °¡´É
      String cardRegular = "\\d{4}$"; // ¼ýÀÚ
      String addressRegular = "^[-a-zA-Z°¡-ÆR0-9]*$"; // ÁÖ¼Ò
      String businessName_R = "^[a-zA-Z°¡-ÆR0-9]*$"; // »ç¾÷Àå¸í
      String businessAddress_R = "^[0-9-]*$"; // °èÁÂ¹øÈ£
      String sRegular = "\\S*"; // °ø¹é
      String phoneDB;
      NormalSignUpChk normalSignUpChk;
      boolean[] regularChk = new boolean[10];

      boolean signUpEnd = true;

      public ReviseInfor() { // Á¤º¸¼öÁ¤ »ý¼ºÀÚ

         setTitle("°³ÀÎÁ¤º¸ ¼öÁ¤"); // °³ÀÎÁ¤º¸ ¼öÁ¤ ÇÁ·¹ÀÓ Å¸ÀÌÆ²

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

         // °³ÀÎÁ¤º¸ ¶óº§ »ý¼º
         profile = new JLabel("»ó¼¼ ÇÁ·ÎÇÊ");
         photo = new JLabel("»çÁø ¼öÁ¤");
         helloMsg = new JLabel("ÀÎ»ç¸» ¼öÁ¤");
         nowPw = new JLabel("±âÁ¸ ºñ¹Ð¹øÈ£");
         pw = new JLabel("PW ¼öÁ¤");
         pwChk = new JLabel("pw È®ÀÎ");
         email = new JLabel("ÀÌ¸ÞÀÏ ¼öÁ¤");
         phoneNum = new JLabel("ÀüÈ­¹øÈ£ ¼öÁ¤");
         address = new JLabel("ÁÖ¼Ò ¼öÁ¤");
         cardNum = new JLabel("Ä«µå¹øÈ£ ¼öÁ¤");
         pwHint = new JLabel("PWÈùÆ® ¼öÁ¤");
         hintAnswer = new JLabel("ÈùÆ® ´ä ¼öÁ¤");
         photoButton = new JButton("»çÁø ¼öÁ¤");
         phoneLabel_1 = new JLabel("-");
         phoneLabel_2 = new JLabel("-");
         ImageIcon ii = new ImageIcon(pfio.download(userID));
         Image img = ii.getImage();
         img = img.getScaledInstance(100, 100, img.SCALE_SMOOTH);
         ii = new ImageIcon(img);

//           bankChoice.add("-ÀºÇà¼±ÅÃ-");
//           bankChoice.add("±â¾÷ÀºÇà");
//           bankChoice.add("³óÇùÀºÇà");
//           bankChoice.add("½ÅÇÑÀºÇà");
//           bankChoice.add("»ê¾÷ÀºÇà");
//           bankChoice.add("¿ì¸®ÀºÇà");
//           bankChoice.add("¾¾Æ¼ÀºÇà");
//           bankChoice.add("ÇÏ³ªÀºÇà");
//           bankChoice.add("±¤ÁÖÀºÇà");
//           bankChoice.add("°æ³²ÀºÇà");
//           bankChoice.add("´ë±¸ÀºÇà");
//           bankChoice.add("±¤ÁÖÀºÇà");
//           bankChoice.add("ºÎ»êÀºÇà");
//           bankChoice.add("¼öÇùÀºÇà");
//           bankChoice.add("Á¦ÁÖÀºÇà");
//           bankChoice.add("Á¦ÁÖÀºÇà");
//           bankChoice.add("ÀüºÏÀºÇà");
//           bankChoice.add("»õ¸¶À»±Ý°í");
//           bankChoice.add("½ÅÇùÀºÇà");
//           bankChoice.add("¼öÇùÀºÇà");
//           bankChoice.add("¿ìÃ¼±¹");

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

         // °³ÀÎÁ¤º¸ ¸®½ºÆ®¿¡ °³ÀÎÁ¤º¸ ¶óº§ ´ã±â
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

         passWordHint_S.add("¿øÇÏ´Â Áú¹®À» ¼±ÅÃÇÏ¼¼¿ä.");
         passWordHint_S.add("°¡Àå ±â¾ï¿¡ ³²´Â Àå¼Ò´Â?");
         passWordHint_S.add("³ªÀÇ º¸¹° 1È£´Â?");
         passWordHint_S.add("º»ÀÎÀÇ Ãâ»ýÁö´Â?");
         passWordHint_S.add("³»°¡ Á¸°æÇÏ´Â ÀÎ¹°Àº?");
         passWordHint_S.add("³ªÀÇ ÁÂ¿ì¸íÀº?");
         passWordHint_S.add("°¡Àå °¨¸í±í°Ô º» ¿µÈ­´Â?");
         passWordHint_S.add("³»°¡ ÁÁ¾ÆÇÏ´Â ¹ÂÁö¼ÇÀº?");
         passWordHint_S.add("ÀÎ»ó±í°Ô ÀÐÀº Ã¥ Á¦¸ñÀº?");
         passWordHint_S.add("³ªÀÇ ³ë·¡¹æ ¾ÖÃ¢°îÀº?");

         pwhint = new JComboBox(passWordHint_S);

         // ¼öÁ¤¿Ï·á ¹öÆ°
         reviseGo = new JButton("¼öÁ¤ ¿Ï·á");
         // °³ÀÎÁ¤º¸ ¼öÁ¤ ÇÁ·¹ÀÓ ³ª°¡±â ¹öÆ°

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

         for (int i = 1; i < information.size(); i++) { // °³ÀÎÁ¤º¸ ¶óº§ À§Ä¡ Àâ±â(¸®½ºÆ®)
            information.get(i).setBounds(10, (i * 60) + 220, 100, 50);
         }
         helloMsg.setBounds(10, 220, 100, 50);
         photo.setBounds(10, 60, 100, 100);
         nowPw.setBounds(10, 280, 100, 50);
         pw.setBounds(10, 340, 100, 50);
         pwChk.setBounds(10, 400, 100, 50);

         reviseGo.addActionListener(this); // ¼öÁ¤¿Ï·á ¹öÆ°

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

         add(reviseGo); // °³ÀÎÁ¤º¸ ¼öÁ¤ ÇÁ·¹ÀÓ¿¡ ¼öÁ¤¿Ï·á ¹öÆ° Ãß°¡

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
            add(jl); // °³ÀÎÁ¤º¸ ¼öÁ¤ ÇÁ·¹ÀÓ¿¡ °³ÀÎÁ¤º¸ ¶óº§ Ãß°¡
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

         if (e.getSource().equals(reviseGo)) { // °³ÀÎÁ¤º¸ ¼öÁ¤ ÇÁ·¹ÀÓ¿¡¼­ ¿Ï·á ¹öÆ° Å¬¸¯
            // ¼öÁ¤¿Ï·á ÆË¾÷ ¶ç¿îÈÄ ÇÁ·¹ÀÓ »ç¶óÁö±â

            boolean pwhintChk = pwhint.getSelectedIndex() == 0;
            phoneDB = phoneNumBox.getSelectedItem().toString() + "-" + phoneNumText_1.getText() + "-"
                  + phoneNumText_2.getText();
            String cardNumText = cardNumText_1.getText() + "-" + cardNumText_2.getText() + "-"
                  + cardNumText_3.getText() + "-" + cardNumText_4.getText();
            if (pwhintChk == true) {
               JOptionPane.showMessageDialog(null, "ºñ¹Ð¹øÈ£ ÈùÆ®¸¦ ¼±ÅÃÇØÁÖ¼¼¿ä");
            } else if (!(pwField.getText().equals(pwChkText.getText()))) {
               JOptionPane.showMessageDialog(null, "ºñ¹Ð¹øÈ£¸¦ ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä");
            } else if (cnt != 10) {
               JOptionPane.showMessageDialog(null, "¿Ã¹Ù¸¥ Çü½ÄÀÌ ¾Æ´Õ´Ï´Ù");
            } else {
               JOptionPane.showMessageDialog(null, "¼öÁ¤¿Ï·á");
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
               if (!(pwhint.getSelectedItem().equals("¿øÇÏ´Â Áú¹®À» ¼±ÅÃÇÏ¼¼¿ä."))) {
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
               JOptionPane.showMessageDialog(null, "jpgÆÄÀÏ¸¸ °¡´ÉÇÕ´Ï´Ù");
            }
         }

      }

      @Override
      public void windowOpened(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }

      @Override
      public void windowClosing(WindowEvent e) {
         signUpEnd = false;
         ri = null;

      }

      @Override
      public void windowClosed(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }

      @Override
      public void windowIconified(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }

      @Override
      public void windowDeiconified(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }

      @Override
      public void windowActivated(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }

      @Override
      public void windowDeactivated(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }

      // À¯È¿¼º °Ë»ç ¾²·¹µå
      class NormalSignUpChk extends Thread {

         @Override
         public void run() {
            while (signUpEnd) {
               // ºñ¹Ð¹øÈ£ À¯È¿¼º °Ë»ç --> ¼ýÀÚ,¿µ¹®, Æ¯¼ö¹®ÀÚ Æ÷ÇÔ (°ø¹é ±ÝÁö) ---------------------------------
               if (((!(Pattern.matches(pwRegular, pwField.getText())))
                     || !(Pattern.matches(sRegular, pwField.getText()))) && !pwField.getText().equals("")) {
                  regularChk[0] = false;
                  pwField.setForeground(Color.red);
                  pwField.setOpaque(true);

               } else {
                  regularChk[0] = true;
                  pwField.setForeground(Color.black);

                  // ºñ¹Ð¹øÈ£ == ºñ¹Ð¹øÈ£ È®ÀÎ ÀÏÄ¡ÇÑÁö °Ë»ç!!
               }
               if (!(pwField.getText().equals(pwChkText.getText()))) {
                  regularChk[1] = false;
                  pwChkText.setForeground(Color.red);

               } else {
                  regularChk[1] = true;
                  pwChkText.setForeground(Color.black);

                  // ÀÌ¸§ À¯È¿¼º °Ë»ç --> ÇÑ±Û¸¸ »ç¿ë°¡´É --------------------------------
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

                  // Ä«µå¹øÈ£ À¯È¿¼º °Ë»ç --> ±ÛÀÚ¼ö 4ÀÚ Á¦ÇÑ, ¼ýÀÚ¸¸ °¡´É ..--------------------------------
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

               } // ÁÖ¼Ò´Â ÇÑ±Û/¿µ¹®/¼ýÀÚ¸¸ °¡´É!!(ÃÊ¼º±ÝÁö)
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

   class MessagePanel extends JPanel implements ActionListener { // °í°´¼¾ÅÍ_ °ü¸®ÀÚ¿¡°Ô ¸Þ¼¼Áö ÆÐ³Î

      JLabel msgLabel; // ¸Þ¼¼Áö ¶óº§
      JButton msgSend; // º¸³½ ¸Þ¼¼ÁöÇÔ
      JButton msgGive; // ¹ÞÀº ¸Þ¼¼ÁöÇÔ
      JButton msgTextBt; // ¸Þ¼¼Áö º¸³»±â
      JTextArea msgText; // ¸Þ¼¼Áö ÀÔ·ÂÃ¢
      SendMessage sendMessage; // º¸³½ ¸Þ¼¼ÁöÇÔ ÇÁ·¹ÀÓ Å¬·¡½º
      GiveMessage giveMessage; // ¹ÞÀº ¸Þ¼¼ÁöÇÔ ÇÁ·¹ÀÓ Å¬·¡½º

      JScrollPane msgTextScroll; // ¸Þ¼¼Áö ÀÔ·Â Ã¢ ½ºÅ©·Ñ

      ArrayList<JButton> msgButton; // ¸Þ¼¼Áö º¸³»±â, ¹ÞÀº ¸Þ¼¼ÁöÇÔ, º¸³½¸Þ¼¼ÁöÇÔ ¹öÆ° ¸®½ºÆ®

      public MessagePanel() { // ¸Þ¼¼Áö ÆÐ³Î »ý¼ºÀÚ

         msgButton = new ArrayList<JButton>(); // ¸Þ¼¼Áö º¸³»±â, ¹ÞÀº/º¸³½ ¸Þ¼¼ÁöÇÔ ¹öÆ° ¸®½ºÆ®

         msgLabel = new JLabel("°ü¸®ÀÚ¿¡°Ô ¸Þ¼¼Áö"); // ¸Þ¼¼Áö ÀÔ·ÂÃ¢ ¶óº§
         msgSend = new JButton("º¸³½ ¸Þ¼¼Áö"); // º¸³½ ¸Þ¼¼ÁöÇÔ ¹öÆ°
         msgGive = new JButton("¹ÞÀº ¸Þ¼¼Áö"); // ¹ÞÀº ¸Þ¼¼ÁöÇÔ ¹öÆ°
         msgTextBt = new JButton("º¸³»±â"); // ¸Þ¼¼Áö º¸³»±â ¹öÆ°
         msgText = new JTextArea(); // ¸Þ¼¼Áö ÀÔ·Â Ã¢
         msgTextScroll = new JScrollPane(msgText); // ¸Þ¼¼Áö ÀÔ·Â Ã¢ ½ºÅ©·Ñ

         msgText.setLineWrap(true);

         msgButton.add(msgTextBt); // ¹öÆ° ¸®½ºÆ®¿¡ ¸Þ¼¼Áö º¸³»±â ¹öÆ° Ãß°¡
         msgButton.add(msgSend); // ¹öÆ° ¸®½ºÆ®¿¡ º¸³½ ¸Þ¼¼ÁöÇÔ Ãß°¡
         msgButton.add(msgGive); // ¹öÆ° ¸®½ºÆ®¿¡ ¹ÞÀº ¸Þ¼¼ÁöÇÔ Ãß°¡

         msgLabel.setBounds(10, 50, 200, 50);
         msgSend.setBounds(30, 100, 200, 40);
         msgGive.setBounds(260, 100, 200, 40);
         msgTextScroll.setBounds(10, 180, 270, 200);
         msgTextBt.setBounds(290, 300, 100, 80);

         for (JButton jb : msgButton) {
            jb.addActionListener(this); // ¹öÆ° ¸®½ºÆ®¿¡ ¾×¼Ç ¸®½º³Ê
         }

         add(msgLabel);
         add(msgSend);
         add(msgGive);
         add(msgTextScroll);
         add(msgTextBt);
      }

      @Override
      public void actionPerformed(ActionEvent e) {
         if (e.getSource().equals(msgButton.get(0))) { // ¸Þ¼¼Áö º¸³»±â ¹öÆ° Å¬¸¯ ÇßÀ» ¶§
            if (!(msgText.getText().isEmpty())) {
               MessageDB.saveMESSAGE("admin", userID, msgText.getText());
               JOptionPane.showMessageDialog(null, "Àü¼Û ¿Ï·á");
               // ¸Þ¼¼Áö ÀÔ·ÂÃ¢ ÃÊ±âÈ­
               msgText.setText("");
            } else {
               JOptionPane.showMessageDialog(null, "¸Þ¼¼Áö¸¦ ÀÔ·ÂÇÏ¼¼¿ä");
            }
         } else if (e.getSource().equals(msgButton.get(1))) { // º¸³½ ¸Þ¼¼ÁöÇÔ ¹öÆ° Å¬¸¯ ÇßÀ» ¶§
            if (sendMessage == null) {
               sendMessage = new SendMessage(this);
               if (!sendMessage.chk) {
                  sendMessage = null;
               }
            }
         } else if (e.getSource().equals(msgButton.get(2))) { // ¹ÞÀº ¸Þ¼¼ÁöÇÔ ¹öÆ° Å¬¸¯ ÇßÀ» ¶§
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

   class SendMessage extends JFrame implements ActionListener, MouseListener, WindowListener { // º¸³½¸Þ¼¼ÁöÇÔ ÇÁ·¹ÀÓ Å¬·¡½º

      JTable sendList;
      JScrollPane scroll;
      JButton delete;

      boolean chk = true;
      String[][] arr2;
      MessagePanel messagePanel;

      public SendMessage(MessagePanel messagePanel) {
         setTitle("º¸³½ ¸Þ¼¼ÁöÇÔ");
         setBounds(600, 100, 515, 800);
         setLayout(null);
         this.messagePanel = messagePanel;
         arr2 = MessageDB.getFROM_MESSAGE(userID);
         if (arr2 == null) {
            JOptionPane.showMessageDialog(null, "º¸³½¸Þ¼¼ÁöÇÔÀÌ ºñ¾ú½À´Ï´Ù.");
            chk = false;
            return;
         }

         String[] arr = new String[] { "¹ÞÀº»ç¶÷", "³»¿ë", "½Ã°£" };

         sendList = new JTable(new NotEditTable(arr2, arr));
         scroll = new JScrollPane(sendList);
         delete = new JButton("»èÁ¦");

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
            JOptionPane.showMessageDialog(null, "»èÁ¦¿Ï·á");
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
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }

      @Override
      public void windowClosing(WindowEvent e) {
         messagePanel.sendMessage = null;

      }

      @Override
      public void windowClosed(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }

      @Override
      public void windowIconified(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }

      @Override
      public void windowDeiconified(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }

      @Override
      public void windowActivated(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }

      @Override
      public void windowDeactivated(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

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
         writerLabel = new JLabel("¹ÞÀº»ç¶÷ : ");
         timeLabel = new JLabel("º¸³½½Ã°£ : ");
         contentLabel = new JLabel("³»¿ë");

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
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }

      @Override
      public void windowClosing(WindowEvent e) {
         sendMessageClick = null;

      }

      @Override
      public void windowClosed(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }

      @Override
      public void windowIconified(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }

      @Override
      public void windowDeiconified(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }

      @Override
      public void windowActivated(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }

      @Override
      public void windowDeactivated(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

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

   GiveMessageClick giveMessageClick; // ÇöÀç ¶°ÀÖ´Â ¸Þ½ÃÁöÃ¢

   class GiveMessage extends JFrame implements ActionListener, MouseListener, WindowListener { // ¹ÞÀº¸Þ¼¼ÁöÇÔ ÇÁ·¹ÀÓ Å¬·¡½º

      JTable giveList;
      JScrollPane scroll;
      JButton delete;
      String[][] arr2;
      MessagePanel messagePanel;
      boolean chk = true;

      public GiveMessage(MessagePanel messagePanel) {
         this.messagePanel = messagePanel;
         setTitle("¹ÞÀº ¸Þ¼¼ÁöÇÔ");
         setBounds(600, 100, 515, 800);
         setLayout(null);

         arr2 = MessageDB.getTO_MESSAGE(userID);
         String[] arr = new String[] { "º¸³½»ç¶÷", "³»¿ë", "½Ã°£" };
         if (arr2 == null) {
            JOptionPane.showMessageDialog(null, "¹ÞÀº¸Þ¼¼ÁöÇÔÀÌ ºñ¾ú½À´Ï´Ù.");
            chk = false; // ºñÁ¤»óÀû »ý¼º
            return;
         }
         addWindowListener(this);
         giveList = new JTable(new NotEditTable(arr2, arr));
         scroll = new JScrollPane(giveList);
         delete = new JButton("»èÁ¦");

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
         JOptionPane.showMessageDialog(null, "»èÁ¦¿Ï·á");
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
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }

      @Override
      public void windowClosing(WindowEvent e) {
         messagePanel.giveMessage = null;

      }

      @Override
      public void windowClosed(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }

      @Override
      public void windowIconified(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }

      @Override
      public void windowDeiconified(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }

      @Override
      public void windowActivated(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }

      @Override
      public void windowDeactivated(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

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

         setTitle("¹ÞÀº¸Þ¼¼Áö");
         setBounds(600, 100, 400, 500);
         setLayout(null);

         writerLabel = new JLabel("º¸³½»ç¶÷ : ");
         timeLabel = new JLabel("¹ÞÀº½Ã°£ : ");
         contentLabel = new JLabel("³»¿ë");

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
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

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
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }

      @Override
      public void windowDeiconified(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }

      @Override
      public void windowActivated(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }

      @Override
      public void windowDeactivated(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }

   }

   class NoticePanel extends JPanel implements ActionListener { // °í°´¼¾ÅÍ_ °øÁö»çÇ× ÆÐ³Î

      Vector<String> notice; // °øÁö»çÇ× (ÀÓ½Ã)
      JButton noticeGo; // °øÁöÈ®ÀÎ ¹öÆ° (ÀÓ½Ã)
      JComboBox noticeBox;
      NoticeFrame noticeFrame;
      MessagePanel msgPanel;
      ArrayList<Notice> notices;

      public NoticePanel() {

         notices = NoticeDB.getNOTICE();
         notice = new Vector<String>(); // º¤ÅÍ.add·Î ÄÞº¸¹Ú½º Ãß°¡
         for (Notice ntc : notices) {
            notice.add(ntc.title);
         }
         if (notice.isEmpty()) {
            notice.add("°øÁö»çÇ×¾øÀ½");
         }
         noticeGo = new JButton("°øÁöÈ®ÀÎ");
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

   class NoticeFrame extends JFrame implements WindowListener { // °øÁö È®ÀÎ ÇÁ·¹ÀÓ
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

         setTitle("°øÁö»çÇ×");

         setBounds(jumTalkOption_User.getX() + jumTalkOption_User.getWidth() + 50, jumTalkOption_User.getY(), 700,
               800);
         setLayout(null);
         this.np = np;
         noticeLabel = new JLabel(notice.title);
         contentLabel = new JTextArea(notice.content);
         writerLabel = new JLabel(notice.writer);
         modifLabel = new JLabel(notice.modi_time);
         makeTimeLabel = new JLabel(notice.maketime);

         title = new JLabel("Á¦¸ñ : ");
         makeTime = new JLabel("ÀÛ¼º½Ã°£ : ");
         modifTime = new JLabel("¼öÁ¤½Ã°£ : ");
         writer = new JLabel("ÀÛ¼ºÀÚ : ");

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
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

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
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }

      @Override
      public void windowDeiconified(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }

      @Override
      public void windowActivated(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }

      @Override
      public void windowDeactivated(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

      }
   }
}