package db_p;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import com.formdev.flatlaf.intellijthemes.FlatArcIJTheme;

/*--------------------ÇÊ¼ö»çÇ×-----------------------------

1. ½Äº° ÇÒ ¼ö ÀÖ´Â ¹øÈ£ ±âÀÔ!!!!

2.È¸¿øµéÀÌ °¡ÀÔÇÑ Á¤º¸¸¦ (ID,PW,ÀÌ¸§ µîµî..)  Å¬·¡½º·Î ÀÏ°ýÃ³¸® to Sting À¸·Î return ÇØ¼­ DB¿¡ º¸³¾°Í 

 */

/*--------------------ÇØ¾ßÇÒ °Í--------------------------

1. È¸¿ø°¡ÀÔ ÀÎÀû»çÇ× Ã¢À» ºñ¿öµ×À»¶§ - '@@@Àº ÇÊ¼ö ÀÔ·Â »çÇ×ÀÔ´Ï´Ù' Ã¢ ¶ç¿ì±â  --o
2. ¾ÆÀÌµð Áßº¹È®ÀÎ ¹öÆ°À» ´­·¶À»¶§ - '»ç¿ë °¡´ÉÇÑ ¾ÆÀÌµð ÀÔ´Ï´Ù.'--±âº» ¸Þ¼¼ÁöÃ¢ , 'ÀÌ¹Ì »ç¿ëÁßÀÎ ¾ÆÀÌµð ÀÔ´Ï´Ù' --°æ°í ¸Þ¼¼ÁöÃ¢  --o
3. JCombox index 0¹øÂ° Àû¿ë¾ÈµÇ°Ô ¸¸µé±â. ---o
4. »õ·Î¿î ºñ¹Ð¹øÈ£ ÀÔ·ÂÃ¢¿¡¼­ ¾ÆÀÌµð / ¾ÆÀÌµð+ºñ¹Ð¹øÈ£ ÈùÆ® ´ä ÀÌ ¸ÂÁö ¾ÊÀ» °æ¿ì 'Á¤º¸°¡ ÀÏÄ¡ÇÏÁö ¾Ê½À´Ï´Ù.' --°æ°í ¸Þ¼¼ÁöÃ¢  --o
5. ·Î±×ÀÎ id¿Í pw°¡ ÀÏÄ¡ÇÏÁö ¾ÊÀ»°æ¿ì '°¡ÀÔÇÏÁö ¾ÊÀº ¾ÆÀÌµðÀÌ°Å³ª, Àß¸øµÈ ºñ¹Ð¹øÈ£ÀÔ´Ï´Ù.' -- °æ°í ¸Þ¼¼ÁöÃ¢   --o
6. ÀÌ¸ÞÀÏ Á÷Á¢ÀÔ·Â ÅØ½ºÆ® ¾È¶ç¿ì°Ô ÇØ³õ±â. --o
7. È¸¿ø°¡ÀÔ -> ºñ¹Ð¹øÈ£ ,ºñ¹Ð¹øÈ£ È®ÀÎ µÎ°³°¡ ÀÏÄ¡ÇÏÁö¾ÊÀ¸¸é È¸¿ø°¡ÀÔ È®ÀÎ ´­·¶À»¶§ 'ºñ¹Ð¹øÈ£¸¦ ´Ù½Ã È®ÀÎ ÇØÁÖ¼¼¿ä' Ã¢ ¶ç¿ì±â. ---o
8. ÀüÈ­¹øÈ£ ,Ä«µå¹øÈ£ ÅØ½ºÆ®ÇÊµå ±ÛÀÚ¼ö Á¦ÇÑ!!!!!  ---o
9. ¾²·¹µå Á¾·á --o
10. Ã¢Á¦¾î
*/

//·Î±×ÀÎ Ã¢
class Login extends JFrame implements ActionListener {

   Login me = this;  
//   Signup signUp;
   // String userID = "admin";

   // ¾ÆÀÌµð ,ºñ¹Ð¹øÈ£ ÅØ½ºÆ®Ã¢
   JTextField idtxt;
   JPasswordField pwtxt;

   // ¶óº§
   JLabel idLabel; // ¾ÆÀÌµð
   JLabel pwLabel; // ºñ¹Ð¹øÈ£

   // ¹öÆ°
   JButton login_bt; // ·Î±×ÀÎ
   JButton idSearch_bt; // ¾ÆÀÌµðÃ£±â
   JButton pwSearch_bt; // ºñ¹Ð¹øÈ£ Ã£±â
   JButton signUp_Bt; // È¸¿ø°¡ÀÔ

   // ¾×¼Ç¸®½º³Ê
   IDsearch idSearchFrame; // ¾ÆÀÌµðÃ£±â ÇÁ·¹ÀÓÅ¬·¡½º
   PWsearch pwSearchFrame; // ºñ¹øÃ£±â ÇÁ·¹ÀÓÅ¬·¡½º
//   MemberChoice memberChoiceFrame; // ÀÏ¹Ý,Á¡¼ú È¸¿ø ±¸ºÐ ÇÁ·¹ÀÓ Å¬·¡½º
   
   SignUp SignUp; // È¸¿ø°¡ÀÔ ÇÁ·¹ÀÓ Å¬·¹½º 
   
   
   
   
   boolean signUpEnd = true;

   // ·Î±×ÀÎ ¸Þ¼Òµå ---------------------------------------
   public Login() {
      FlatArcIJTheme.install();
      
      setTitle("Login");
      setBounds(600, 100, 515, 800);
      setResizable(false);
      setLayout(null);
      ImageIcon title = new ImageIcon("icon\\title.jpg");
      Image ii = title.getImage();
      ii = ii.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
      title = new ImageIcon(ii);
      
      JLabel titlelb = new JLabel(title);
      titlelb.setBounds(100, 60, 300, 300);
      add(titlelb);
      // ¾ÆÀÌµð
      idLabel = new JLabel("¾ÆÀÌµð");
      idLabel.setBounds(60, 400, 100, 50);
      idLabel.setFont(new Font("±¼¸²Ã¼", Font.BOLD, 15));
      add(idLabel);

      // ¾ÆÀÌµð ÀÔ·Â Ä­
      idtxt = new JTextField();
      idtxt.setBounds(130, 400, 200, 50);
      add(idtxt);
      // idtxt.setOpaque(false); //ÅØ½ºÆ® »óÀÚ Åõ¸íÇÏ°Ô ¸¸µé±â
      // idtxt.setBorder(javax.swing.BorderFactory.createEmptyBorder()); //µÞ¹è°æ Åõ¸íÇÏ°Ô
      // ¸¸µé±â (Å×µÎ¸® »èÁ¦)

      // ÆÐ½º¿öµå
      pwLabel = new JLabel("ºñ¹Ð¹øÈ£");
      pwLabel.setBounds(60, 470, 300, 50);
      pwLabel.setFont(new Font("±¼¸²Ã¼", Font.BOLD, 15));
      add(pwLabel);

      // ÆÐ½º¿öµå ÀÔ·Â
      pwtxt = new JPasswordField();
      pwtxt.setBounds(130, 470, 200, 50);
      add(pwtxt);

      // ·Î±×ÀÎ
      login_bt = new JButton("·Î±×ÀÎ");
      login_bt.setBounds(350, 407, 100, 100);
      add(login_bt);
      login_bt.addActionListener(this);

      // idÃ£±â --¾×¼Ç
      idSearch_bt = new JButton("¾ÆÀÌµð Ã£±â");
      idSearch_bt.setBounds(60, 560, 120, 40);
      add(idSearch_bt);
      idSearch_bt.addActionListener(this);
      
      

      // pwÃ£±â --¾×¼Ç
      pwSearch_bt = new JButton("ºñ¹Ð¹øÈ£ Ã£±â");
      pwSearch_bt.setBounds(190, 560, 120, 40);
      add(pwSearch_bt);
      pwSearch_bt.addActionListener(this);

      // È¸¿ø°¡ÀÔ --¾×¼Ç
      signUp_Bt = new JButton("È¸¿ø°¡ÀÔ");
      signUp_Bt.setBounds(320, 560, 130, 40);
      add(signUp_Bt);
      signUp_Bt.addActionListener(this);

      System.out.println(idtxt);
      setVisible(true);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

   }

   @Override
   public void actionPerformed(ActionEvent e) {

      // ¾ÆÀÌµðÃ£±â ¹öÆ° ´­·¶À»¶§
      if (e.getSource().equals(idSearch_bt)) {
         if(idSearchFrame == null) {
            idSearchFrame = new IDsearch(this);
         }
         // ºñ¹Ð¹øÈ£ Ã£±â ¹öÆ° ´­·¶À»¶§
      } else if (e.getSource().equals(pwSearch_bt)) {
         if(pwSearchFrame==null) {
            pwSearchFrame = new PWsearch(this);   
         }

         // È¸¿ø°¡ÀÔ ¹öÆ° ´­·¶À»¶§ -> ÀÏ¹Ý,Á¡¼úÈ¸¿ø ±¸ºÐÇÁ·¹ÀÓ ¶ç¿ì±â
      } else if (e.getSource().equals(signUp_Bt)) {
//         memberChoiceFrame = new MemberChoice();
         if(SignUp==null) {
            SignUp = new SignUp(this);
         }

         // ·Î±×ÀÎ ¹öÆ° ´­·¶À»¶§
      } else if (e.getSource().equals(login_bt)) {
       
         if (idtxt.getText().isEmpty() || pwtxt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "¾ÆÀÌµð¿Í ºñ¹Ð¹øÈ£¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä.  ", "·Î±×ÀÎ ¿À·ù", JOptionPane.WARNING_MESSAGE);

            // ¾ÆÀÌµð¿Í ºñ¹Ð¹øÈ£°¡ ÀÏÄ¡ÇÏÁö ¾ÊÀ» °æ¿ì
         } else if (!(UserDB.getPW(idtxt.getText()).equals(pwtxt.getText()))) {
            JOptionPane.showMessageDialog(null, "¾ÆÀÌµð¿Í ºñ¹Ð¹øÈ£°¡ ¸ÂÁö ¾Ê½À´Ï´Ù.");

            //Áßº¹ ·Î±×ÀÎÀÏ °æ¿ì
         }else if (UserDB.getLOGINCHK(idtxt.getText()).equals("true")) {
           JOptionPane.showMessageDialog(null, "°°Àº¾ÆÀÌµð·Î ·Î±×ÀÎÁßÀÔ´Ï´Ù.");

              // ´Ù ¾Æ´Ï¸é ·Î±×ÀÎ ¼º°ø!
         } else {
           JOptionPane.showMessageDialog(null, "            ·Î±×ÀÎ ¼º°ø! \nÁ¡Åå¿¡ ¿À½Å °ÍÀ» È¯¿µÇÕ´Ï´Ù ¢¾");
           dispose();
           UserDB.setLOGINCHK(idtxt.getText(), "true");
           new MainFrame(idtxt.getText());

        }
      }

   }
   



   // ¾ÆÀÌµðÃ£±â
   class IDsearch extends JFrame implements ActionListener, WindowListener{

      // ÀÌ¸§/ÀüÈ­¹øÈ£ ¶óº§
      JLabel idName_s;
      JLabel idPhone_s;

      // ÀÌ¸§/ÀüÈ­¹øÈ£ ÅØ½ºÆ®Ã¢
      JTextField idName_t;
      JTextField idPhone_t;
      JTextField idPhone_t2;

      // ¾ÆÀÌµðÃ£±â /Ãë¼Ò
      JButton idChk_s;
      JButton idCancellation;

      Vector<String> phoneNumber;
      JComboBox<String> pnBox;
      
      // UserDB
      String userNAME; 
      String userPHONE;
      
      String resID;

      Login lg;

      // ¾ÆÀÌµð Ã£±â ÇÁ·¹ÀÓ Å¬·¡½º
//      ResID resIDFrame;

      public IDsearch(Login lg) {
         setTitle("¾ÆÀÌµð Ã£±â");
         setBounds(600, 230, 500, 600);
         setLayout(null);
         this.lg = lg;

         // ÀÌ¸§ ÀÔ·ÂÃ¢
         idName_s = new JLabel("ÀÌ¸§");
         idName_s.setBounds(225, 110, 100, 100);
         add(idName_s);
         idName_t = new JTextField();
         idName_t.setBounds(165, 190, 155, 40);
         add(idName_t);

         // ÀüÈ­¹øÈ£ ÀÔ·ÂÃ¢
         idPhone_s = new JLabel("ÀüÈ­¹øÈ£");
         idPhone_s.setBounds(210, 260, 160, 40);
         add(idPhone_s);
         phoneNumber = new Vector<String>();
         phoneNumber.add("010");
         phoneNumber.add("011");
         phoneNumber.add("016");
         phoneNumber.add("017");
         phoneNumber.add("018");
         phoneNumber.add("019");

         pnBox = new JComboBox<String>(phoneNumber);
         pnBox.setBounds(70, 310, 90, 40);
         pnBox.setSelectedItem("010");
         add(pnBox);

         // ÇÚµåÆù¹øÈ£ Â¦´ë±â
         idPhone_s = new JLabel("¦¡");
         idPhone_s.setBounds(169, 315, 30, 30);
         add(idPhone_s);
         idPhone_s = new JLabel("¦¡");
         idPhone_s.setBounds(287, 315, 30, 30);
         add(idPhone_s);

         // ÇÚµåÆù¹øÈ£ Áß°£, µÞÀÚ¸®
         idPhone_t = new JTextField();
         idPhone_t.setBounds(190, 310, 90, 40);
         add(idPhone_t);
         idPhone_t2 = new JTextField();
         idPhone_t2.setBounds(310, 310, 90, 40);
         add(idPhone_t2);

         // ¾ÆÀÌµðÃ£±â ¹öÆ°
         idChk_s = new JButton("¾ÆÀÌµð Ã£±â");
         idChk_s.setBounds(100, 430, 120, 50);
         add(idChk_s);
         idChk_s.addActionListener(this);

         // Ãë¼Ò¹öÆ°
         idCancellation = new JButton("Ãë¼Ò");
         idCancellation.setBounds(280, 430, 100, 50);
         add(idCancellation);
         idCancellation.addActionListener(this);
         
         
  

         addWindowListener(this);
         setResizable(false);
         setVisible(true);

      }
      
      @Override
      public void actionPerformed(ActionEvent e) {
         
          userNAME = idName_t.getText();
          userPHONE = pnBox.getSelectedItem().toString() + "-" + idPhone_t.getText() + "-" + idPhone_t2.getText();
         resID = (UserDB.searchID(userNAME, userPHONE));
         
         if (e.getSource().equals(idChk_s)) { // ¾ÆÀÌµðÃ£±â ¹öÆ° ´­·¶À»¶§
            
            if(resID.equals("")) {
               JOptionPane.showMessageDialog(null, "¾ÆÀÌµð¿Í ÀüÈ­¹øÈ£°¡ ÀÏÄ¡ÇÏÁö ¾Ê½À´Ï´Ù !  ", "¾ÆÀÌµð Ã£±â", JOptionPane.ERROR_MESSAGE);
               idName_t.setText("");
               idPhone_t.setText("");
               pnBox.setSelectedIndex(0);
               idPhone_t2.setText("");
               
               
            }else {
               
               JOptionPane.showMessageDialog(null, "È¸¿ø´ÔÀÇ ¾ÆÀÌµð´Â   "+resID+"   ÀÔ´Ï´Ù.", "¾ÆÀÌµð Ã£±â",  JOptionPane.INFORMATION_MESSAGE);
               lg.idSearchFrame = null; 
               dispose();
              
               
            }
//            if(resIDFrame==null) {
//               resIDFrame = new ResID();
//            }
//            lg.idSearchFrame = null;

         } else if (e.getSource().equals(idCancellation)) { // Ãë¼Ò ¹öÆ° ´­·¶À»¶§

            lg.idSearchFrame = null;
            dispose();

         }

      }



      @Override
      public void windowOpened(WindowEvent e) {
         // TODO Auto-generated method stub
         
      }

      @Override
      public void windowClosing(WindowEvent e) {
         // TODO Auto-generated method stub
         lg.idSearchFrame = null;
      }

      @Override
      public void windowClosed(WindowEvent e) {
         
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

   // ºñ¹Ð¹øÈ£ Ã£±â
   class PWsearch extends JFrame implements ActionListener , WindowListener{

      JLabel idSearch; // ¾ÆÀÌµð ÀÔ·Â
      JLabel pwHint_S; // ºñ¹Ð¹øÈ£ ÈùÆ®
      JLabel pwAnswer_S; // ºñ¹Ð¹øÈ£ ´ä

      JTextField idSearch_t; // ¾ÆÀÌµð ÅØ½ºÆ®Ã¢
      JTextField pwSearch_t; // ºñ¹Ð¹øÈ£ ÅØ½ºÆ®Ã¢
      Vector<String> passWordHint_S; // ºñ¹Ð¹øÈ£ ÈùÆ®
      JComboBox<String> hintBox_S; // ºñ¹Ð¹øÈ£ ÈùÆ® comboBox
      
      //DB
      String userID;
      String userPWHINT;
      String userPWRES;
      
      String resPW;

      JButton passWordChk; // ºñ¹Ð¹øÈ£ Ã£±â
      JButton pwCancellation; // Ãë¼Ò ¹öÆ°

      // ¾×¼Ç¸®½º³Ê
      
      Login lg; // ºÎ¸¥¾Ö
      public PWsearch(Login lg) {
         this.lg=lg;
         setTitle("ºñ¹Ð¹øÈ£ Ã£±â");
         setBounds(600, 230, 500, 600);
         setLayout(null);

         // ¾ÆÀÌµð ÀÔ·ÂÄ­
         idSearch = new JLabel("¾ÆÀÌµð");
         idSearch.setBounds(225, 70, 100, 100);
         add(idSearch);
         idSearch_t = new JTextField();
         idSearch_t.setBounds(165, 150, 155, 40);
         add(idSearch_t);

         // ºñ¹Ð¹øÈ£ ÈùÆ® Ä­
         pwHint_S = new JLabel("ºñ¹Ð¹øÈ£ ÈùÆ®");
         pwHint_S.setBounds(205, 220, 100, 30);
         add(pwHint_S);

         passWordHint_S = new Vector<String>();
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

         hintBox_S = new JComboBox<String>(passWordHint_S);
         hintBox_S.setBounds(130, 260, 230, 40);
//         hintBox_S.setSelectedItem("¿øÇÏ´Â Áú¹®À» ¼±ÅÃÇÏ¼¼¿ä."); // ´ÙÀ½À¸·Î °íÁ¤
         add(hintBox_S);

         // ºñ¹Ð¹øÈ£ ÈùÆ® ´ä ÀÔ·Â Ä­
         pwAnswer_S = new JLabel("ºñ¹Ð¹øÈ£ ÈùÆ® ´ä");
         pwAnswer_S.setBounds(200, 320, 230, 40);
         add(pwAnswer_S);

         pwSearch_t = new JTextField();
         pwSearch_t.setBounds(130, 370, 240, 40);
         add(pwSearch_t);

         // ºñ¹Ð¹øÈ£ Ã£±â
         passWordChk = new JButton("ºñ¹Ð¹øÈ£ Ã£±â");
         passWordChk.setBounds(100, 470, 120, 50);
         add(passWordChk);
         passWordChk.addActionListener(this);

         // Ãë¼Ò¹öÆ°
         pwCancellation = new JButton("Ãë¼Ò");
         pwCancellation.setBounds(280, 470, 100, 50);
         add(pwCancellation);
         pwCancellation.addActionListener(this);
         addWindowListener(this);
         
         setResizable(false);
         setVisible(true);

      }

      @Override
      public void actionPerformed(ActionEvent e) {

         if (e.getSource().equals(passWordChk)) { // ºñ¹Ð¹øÈ£Ã£±â ¹öÆ° ´­·¶À»¶§
           
            userID = idSearch_t.getText();
            userPWHINT = hintBox_S.getSelectedItem().toString();
            userPWRES = pwSearch_t.getText();
            resPW = UserDB.searchPW(userID, userPWHINT, userPWRES);
            
            
            if (UserDB.searchPW(userID, userPWHINT, userPWRES).equals("")) {
        
               JOptionPane.showMessageDialog(null, "¾ÆÀÌµð¿Í ºñ¹Ð¹øÈ£ ÈùÆ®°¡ ÀÏÄ¡ÇÏÁö ¾Ê½À´Ï´Ù !", "ºñ¹Ð¹øÈ£ Ã£±â", JOptionPane.ERROR_MESSAGE);
               idSearch_t.setText("");
               pwSearch_t.setText("");
               hintBox_S.setSelectedIndex(0);
               
               
            
            } else {
            
               JOptionPane.showMessageDialog(null, "È¸¿ø´ÔÀÇ ¾ÆÀÌµð´Â   "+resPW+"   ÀÔ´Ï´Ù.", "¾ÆÀÌµð Ã£±â",  JOptionPane.INFORMATION_MESSAGE);
               lg.pwSearchFrame = null;
               dispose();
//            passWordFind = new PassWordFind(this);
//            if(!passWordFind.chk) {
//               lg.pwSearchFrame = null;
//               passWordFind =null;
//            }
//            dispose();

            }
            
           }else if(e.getSource().equals(pwCancellation)) { // Ãë¼Ò ¹öÆ° ´­·¶À»¶§
            
            lg.pwSearchFrame = null;
            dispose();

         }

      }



      @Override
      public void windowOpened(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ
         
      }

      @Override
      public void windowClosing(WindowEvent e) {
         lg.pwSearchFrame = null;
         
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

   // ÀÏ¹Ý+»ç¾÷ÀÚ È¸¿ø°¡ÀÔ
   class SignUp extends JFrame implements ActionListener ,WindowListener{

//    ArrayList<Boolean> regularChk = new ArrayList<Boolean>();
      
     boolean [] regularChk_N = new boolean[11]; //ÀÏ¹ÝÈ¸¿ø Á¤±Ô½Ä Ã¼Å©
     boolean [] regularChk_S = new boolean[10]; //Á¡¼úÈ¸¿ø Á¤±Ô½Ä Ã¼Å© 
     
//     boolean regularAllChk = false;
     
      
      // È¸¿øºÐ·ù ¹öÆ°
      ButtonGroup bg; // ¹öÆ°±×·ì
      JToggleButton nomalUserKind; // ÀÏ¹ÝÈ¸¿ø¹öÆ°
      JToggleButton sellerUserKind; // Á¡¼úÈ¸¿ø¹öÆ°
      JPanel kindButtonPanel; // ¹öÆ°ÆÐ³Î
      JLabel idChkLabel;

      
      // ¶óº§
      JLabel id_L; // ¾ÆÀÌµð
      JLabel pw_L; // ºñ¹Ð¹øÈ£
      JLabel passWord_L; // ºñ¹Ð¹øÈ£ È®ÀÎ
      JLabel pwHint_L; // ºñ¹Ð¹øÈ£ ÈùÆ®
      JLabel pwHintAnswer_L; // ºñ¹Ð¹øÈ£ ÈùÆ® ´ä
      JLabel name_L; // ÀÌ¸§
      JLabel gender_L; // ¼ºº°
      JLabel birthDate_L; // »ý³â¿ùÀÏ
      JLabel phoneNumber_L; // ÈÞ´ëÆù¹øÈ£
      JLabel address_L; // ÁÖ¼Ò
      JLabel email_L; // ÀÌ¸ÞÀÏ
      JLabel business_L; // »ç¾÷Àå¸í
      JLabel businessPlace_L; // »ç¾÷Àå ÁÖ¼Ò
      JLabel bankNumber; //°èÁÂ¹øÈ£
      JLabel pwchk_L ;  //ºñ¹Ð¹øÈ£ Ã¼Å© ¶óº§
      // ¶óº§³¢¸® ¹­À½
      ArrayList<JLabel> information;
      
      
       //ºÎ¿¬¼³¸í ¶óº§
       JLabel idForm_L; // ¾ÆÀÌµð ¼³¸í
       JLabel pwForm_L; //ºñ¹Ð¹øÈ£ ¼³¸í
       JLabel bankNumBer_L; // ÁÖ¼Ò ¼³¸í 

      // ¹öÆ°
      JButton check; // È®ÀÎ
      JButton cancellation; // Ãë¼Ò


      // ¾î·¹ÀÌ¸®½ºÆ®¿¡ ÅØ½ºÆ®ÇÊµå¸¦ Ãß°¡ÇÏ°í ºóÄ­ÀÏ °æ¿ì ´Ù½Ã ÀÔ·ÂÇÏ¶ó´Â Ã¢ ¶ç¿ì±â.
      ArrayList<JTextField> textFieldList = new ArrayList<JTextField>();
      ArrayList<String> textFieldList_2 ;

      // ÅØ½ºÆ® Ã¢
      JTextField id_t; // ¾ÆÀÌµð
      JPasswordField pw_t; // ºñ¹Ð¹øÈ£
      JPasswordField passWord; // ºñ¹Ð¹øÈ£ È®ÀÎ
      JTextField pwHintAnswer; // ºñ¹Ð¹øÈ£ ÈùÆ® ´ä
      JTextField name_t; // ÀÌ¸§
      JTextField pnNum1; // ÇÚµåÆù¹øÈ£ °¡¿îµ¥
      JTextField pnNum2; // ÇÚµåÆù¹øÈ£ µÞÀÚ¸®
      JTextField address_t; // ÁÖ¼Ò
      JTextField email_1; // ÀÌ¸ÞÀÏ ¾ÕÀÚ¸®
      JTextField email_2; // ÀÌ¸ÞÀÏ µÞÀÚ¸®
      JTextField business_1; // »ç¾÷Àå¸í
      JTextField business_2; // »ç¾÷ÀåÁÖ¼Ò

      // ¾ÆÀÌµð Áßº¹È®ÀÎ ¹öÆ°
      JButton idCheck;

      ButtonGroup gender_bt = new ButtonGroup();// ¼ºº°
      JRadioButton man_bt; // ³²ÀÚ¹öÆ°
      JRadioButton woman_bt; // ¿©ÀÚ¹öÆ°

      // ºñ¹Ð¹øÈ£ ÈùÆ®
      Vector<String> passWordHint;
      JComboBox<String> hintBox;

      // ³âµµ
      Vector<String> year;
      JComboBox<String> yearBox;
      JLabel yearLabel;

      // ¿ù
      Vector<String> month;
      JComboBox<String> monthBox;
      JLabel monthLabel;

      // ÀÏ
      Vector<String> day;
      JComboBox<String> dayBox;
      JLabel dayLabel;

      // ÈÞ´ëÆù¹øÈ£ ¾Õ
      Vector<String> phoneNumber;
      JComboBox<String> pnBox;
      JLabel pnLabel;
      JLabel pnLabe2;

      // ÈÞ´ëÆù¹øÈ£ Åë½Å»ç
//         ButtonGroup mobile_bt = new ButtonGroup();
//         JCheckBox mobileCompany1;
//         JCheckBox mobileCompany2;
//         JCheckBox mobileCompany3;

      // ÀÌ¸ÞÀÏ
      Vector<String> email_V; // ÀÌ¸ÞÀÏ Á¾·ù
      JComboBox<String> emailbox;

      // Ä«µå¹øÈ£ ÅØ½ºÆ®Ã¢
      JButton cardCompany; // Ä«µåÀÌ¸§ ¹öÆ°
//      JTextField cardNum_1;
//      JTextField cardNum_2;
//      JPasswordField cardNum_3;
//      JPasswordField cardNum_4;
      JLabel cardNum_L1;
      JLabel cardNum_L2;
      JLabel cardNum_L3;

      // °èÁÂ¹øÈ£

      JTextField bankNumber_T;

      // ÀºÇà¼±ÅÃ
      Vector<String> bankChoice;
      JComboBox<String> bankBox;

      // Ä«µå¹øÈ£
      ArrayList<JTextField> cardNumberArr = new ArrayList<JTextField>();
      JLabel cardNumber;
      JTextField cardNumber_T1;
      JTextField cardNumber_T2;
      JTextField cardNumber_T3;
      JTextField cardNumber_T4;

      boolean idChkBl = false; // ¾ÆÀÌµð Áßº¹Ã¼Å©

      // Á¤±Ô½Ä(À¯È¿¼º°Ë»ç)
 
      String idRegular = "^[a-zA-Z0-9][a-zA-Z0-9]{4,8}$"; // ¾ÆÀÌµð À¯È¿¼º --> ¿µ¹® (´ë¼Ò¹®ÀÚ)/¼ýÀÚ 5~9ÀÚ ÀÌ³»·Î  --o
      String pwRegular = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{4,19}$"; // ºñ¹Ð¹øÈ£ À¯È¿¼º --> 6~20ÀÚ ÀÌ³»ÀÇ ¿µ¹®  + ´ë¼Ò¹®ÀÚ/¼ýÀÚ/Æ¯¼ö¹®ÀÚ ¹Ýµå½Ã Æ÷ÇÔ   --o                                                         
      String phoneNumRegular = "\\d{3,4}$"; // ÀüÈ­¹øÈ£ ¼ýÀÚ 4ÀÚ ÀÌ³»·Î
      String emailRegular = "^[0-9a-zA-Z\\.]*"; // ÀÌ¸ÞÀÏ ´ë¼Ò¹®ÀÚ/¼ýÀÚ  ¾Õ
      String emailRegular_2 = "^[a-zA-Z]{2,}.[a-zA-Z]{2,}"; // ÀÌ¸ÞÀÏ ´ë¼Ò¹®ÀÚ/¼ýÀÚ   µÚ
      String nameRegular = "^[°¡-ÆR]*$"; // ÀÌ¸§ ÇÑ±Û¸¸ °¡´É
      String cardRegular = "^[0-9]{4,6}$"; // ¼ýÀÚ
      String addressRegular = "^[-a-zA-Z°¡-ÆR0-9]*$"; // ÁÖ¼Ò
      String businessName_R = "^[a-zA-Z°¡-ÆR0-9]*$";  //»ç¾÷Àå¸í 
      String businessAddress_R = "^[0-9-]*$"; //°èÁÂ¹øÈ£
      String sRegular = "\\S*"; // °ø¹é

      SignUpChk signUpChk;
      
      Login lg;
      public SignUp(Login lg) {
         this.lg = lg;
         setTitle("Á¡Åå È¸¿ø°¡ÀÔ ");
         setBounds(600, 0, 785, 1120);
         setLayout(null);
         addWindowListener(this);
         idChkLabel = new JLabel();
         idChkLabel.setBounds(110, 93, 30, 30);
         add(idChkLabel);

         information = new ArrayList<JLabel>();
         kindButtonPanel = new JPanel();
         kindButtonPanel.setBounds(90, 0, 600, 50);
         kindButtonPanel.setLayout(new GridLayout(1, 2));

         nomalUserKind = new JToggleButton("ÀÏ¹Ý", true);
         nomalUserKind.addActionListener(this);
         kindButtonPanel.add(nomalUserKind);

         sellerUserKind = new JToggleButton("Á¡¼ú°¡");
         sellerUserKind.addActionListener(this);
         kindButtonPanel.add(sellerUserKind);

         bg = new ButtonGroup();
         bg.add(nomalUserKind);
         bg.add(sellerUserKind);

         add(kindButtonPanel);

         // ¾ÆÀÌµð
         id_L = new JLabel("¾ÆÀÌµð ");
//         id_L.setBounds(20,90,50,30);  
         information.add(id_L);

         // ¾ÆÀÌµð ÅØ½ºÆ®Ã¢
         id_t = new JTextField();
         id_t.setBounds(140, 93, 200, 40);
         add(id_t);
         textFieldList.add(id_t);

         // ¾ÆÀÌµð Áßº¹È®ÀÎ ¹öÆ°
         idCheck = new JButton("Áßº¹È®ÀÎ");
         idCheck.setBounds(350, 95, 110, 35);
         add(idCheck);
         idCheck.addActionListener(this);

         //¾ÆÀÌµð ¼³¸í ¶óº§
           idForm_L = new JLabel("*°ø¹é¾øÀÌ 5~9ÀÚ ÀÌ³»ÀÇ ¿µ¹®/¼ýÀÚ¸¸ »ç¿ë°¡´É");
           idForm_L.setBounds(472,93,300,35);
           add(idForm_L);
         
         // ºñ¹Ð¹øÈ£
         pw_L = new JLabel("ºñ¹Ð¹øÈ£ ");
//         pw_L.setBounds(20,145,100,30);  
         information.add(pw_L);

         // ºñ¹Ð¹øÈ£ ÅØ½ºÆ®Ã¢
         pw_t = new JPasswordField();
         pw_t.setBounds(140, 148, 200, 40);
         add(pw_t);
         
         //ºñ¹Ð¹øÈ£ ¼³¸í ¶óº§
           pwForm_L = new JLabel("*°ø¹é¾øÀÌ 5~9ÀÚ ÀÌ³»ÀÇ ¿µ¹® ´ë¼Ò¹®ÀÚ/¼ýÀÚ/Æ¯¼ö¹®ÀÚ ¹Ýµå½Ã Æ÷ÇÔ ");
           pwForm_L.setBounds(360,155,600,35);
           add(pwForm_L);
         

         // ºñ¹Ð¹øÈ£ È®ÀÎ
         passWord_L = new JLabel("ºñ¹Ð¹øÈ£ È®ÀÎ");
//         passWord_L.setBounds(20,200,100,30);  
         information.add(passWord_L);

         // ºñ¹Ð¹øÈ£ È®ÀÎ ÅØ½ºÆ®Ã¢
         passWord = new JPasswordField();
         passWord.setBounds(140, 203, 200, 40);
         add(passWord);
         
//         // ºñ¹Ð¹øÈ£ Ã¼Å© ¶óº§
//         pwchk_L = new JLabel("ºñ¹Ð¹øÈ£°¡ ÀÏÄ¡ÇÏÁö ¾Ê½À´Ï´Ù.");
//         pwchk_L.setBounds(360,203,200,40);
//         add(pwchk_L);
         
         // ºñ¹Ð¹øÈ£ ÈùÆ®
         pwHint_L = new JLabel("ºñ¹Ð¹øÈ£ ÈùÆ®");
//         pwHint_L.setBounds(20,255,100,30);  
         information.add(pwHint_L);

         // ºñ¹Ð¹øÈ£ ÈùÆ® ³»¿ë
         passWordHint = new Vector<String>();
         passWordHint.add("¿øÇÏ´Â Áú¹®À» ¼±ÅÃÇÏ¼¼¿ä.");
         passWordHint.add("°¡Àå ±â¾ï¿¡ ³²´Â Àå¼Ò´Â?");
         passWordHint.add("³ªÀÇ º¸¹° 1È£´Â?");
         passWordHint.add("º»ÀÎÀÇ Ãâ»ýÁö´Â?");
         passWordHint.add("³»°¡ Á¸°æÇÏ´Â ÀÎ¹°Àº?");
         passWordHint.add("³ªÀÇ ÁÂ¿ì¸íÀº?");
         passWordHint.add("°¡Àå °¨¸í±í°Ô º» ¿µÈ­´Â?");
         passWordHint.add("³»°¡ ÁÁ¾ÆÇÏ´Â ¹ÂÁö¼ÇÀº?");
         passWordHint.add("ÀÎ»ó±í°Ô ÀÐÀº Ã¥ Á¦¸ñÀº?");
         passWordHint.add("³ªÀÇ ³ë·¡¹æ ¾ÖÃ¢°îÀº?");

         hintBox = new JComboBox<String>(passWordHint);
         hintBox.setBounds(140, 260, 230, 40);
         // box.setSelectedIndex(2);
//         hintBox.setSelectedItem("¿øÇÏ´Â Áú¹®À» ¼±ÅÃÇÏ¼¼¿ä."); // ´ÙÀ½À¸·Î °íÁ¤
         add(hintBox);

         // ÈùÆ® ´ä
         pwHintAnswer_L = new JLabel("ºñ¹Ð¹øÈ£ ÈùÆ® ´ä");
//         pwHintAnswer_L.setBounds(20,310,100,30);  
         information.add(pwHintAnswer_L);

         // ÈùÆ® ´ä ÅØ½ºÆ®Ã¢
         pwHintAnswer = new JTextField();
         pwHintAnswer.setBounds(140, 315, 230, 40);
         add(pwHintAnswer);
         textFieldList.add(pwHintAnswer);

         // ÀÌ¸§
         name_L = new JLabel("ÀÌ¸§ ");
//         name_L.setBounds(20,365,100,30);  
         information.add(name_L);

         // ÀÌ¸§ ÅØ½ºÆ®Ã¢
         name_t = new JTextField();
         name_t.setBounds(140, 368, 200, 40);
         add(name_t);
         textFieldList.add(name_t);

         // ¼ºº°
         gender_L = new JLabel("¼ºº° ");
//         gender_L.setBounds(20,420,100,30);  
         information.add(gender_L);

         // ¼ºº° ¹öÆ°
         man_bt = new JRadioButton("³²ÀÚ");
         man_bt.setBounds(140, 425, 80, 35);
         gender_bt.add(man_bt);
         add(man_bt);
         man_bt.addActionListener(this);

         woman_bt = new JRadioButton("¿©ÀÚ");
         woman_bt.setBounds(270, 425, 80, 35);
         gender_bt.add(woman_bt);
         add(woman_bt);
         woman_bt.addActionListener(this);

         // »ý³â¿ùÀÏ
         birthDate_L = new JLabel("»ý³â¿ùÀÏ ");
//         birthDate_L.setBounds(20,475,100,30);  
         information.add(birthDate_L);

         // ³âµµ
         year = new Vector<String>();
         year.add("³âµµ");
         for (int i = 2018; i >= 1940; i--) {
            year.add(i + "");
         }
         yearLabel = new JLabel("³â");
         yearLabel.setBounds(250, 486, 30, 30);
         add(yearLabel);

         yearBox = new JComboBox<String>(year);
         yearBox.setBounds(140, 480, 100, 35);
         yearBox.setSelectedItem("³âµµ");
         add(yearBox);

         // ¿ù
         month = new Vector<String>();
         month.add("¿ù");
         for (int i = 1; i <= 12; i++) {
            if (i < 10) {
               month.add("0" + i);
            } else {
               month.add(i + "");

            }
         }

         monthBox = new JComboBox<String>(month);
         monthBox.setBounds(280, 480, 100, 35);
         monthBox.setSelectedItem("¿ù");
         add(monthBox);

         monthLabel = new JLabel("¿ù");
         monthLabel.setBounds(390, 486, 30, 30);
         add(monthLabel);

         // ÀÏ
         day = new Vector<String>();
         day.add("ÀÏ");

         for (int i = 1; i <= 31; i++) {
            if (i < 10) {
               day.add("0" + i);

            } else {
               day.add(i + "");

            }
         }

         dayBox = new JComboBox<String>(day);
         dayBox.setBounds(420, 480, 100, 35);
         dayBox.setSelectedItem("ÀÏ");
         add(dayBox);

         dayLabel = new JLabel("ÀÏ");
         dayLabel.setBounds(530, 486, 30, 30);
         add(dayLabel);

         // ÀüÈ­¹øÈ£
         phoneNumber_L = new JLabel("ÀüÈ­¹øÈ£");
//         phoneNumber_L.setBounds(20,540,100,30);  
         information.add(phoneNumber_L);

         // ÇÚµåÆù¹øÈ£ ¾ÕÀÚ¸®
         phoneNumber = new Vector<String>();
         phoneNumber.add("010");
         phoneNumber.add("011");
         phoneNumber.add("016");
         phoneNumber.add("017");
         phoneNumber.add("018");
         phoneNumber.add("019");

         pnBox = new JComboBox<String>(phoneNumber);
         pnBox.setBounds(140, 535, 90, 40);
         pnBox.setSelectedItem("010");
         add(pnBox);

         // ÇÚµåÆù¹øÈ£ Â¦´ë±â
         pnLabel = new JLabel("¦¡");
         pnLabel.setBounds(240, 540, 30, 30);
         add(pnLabel);
         pnLabe2 = new JLabel("¦¡");
         pnLabe2.setBounds(365, 540, 30, 30);
         add(pnLabe2);

         // ÇÚµåÆù¹øÈ£ Áß°£, µÞÀÚ¸®
         pnNum1 = new JTextField();
         pnNum1.setBounds(265, 535, 90, 40);
         add(pnNum1);
         textFieldList.add(pnNum1);

         pnNum2 = new JTextField();
         pnNum2.setBounds(390, 535, 90, 40);
         add(pnNum2);
         textFieldList.add(pnNum2);

//         //ÇÚµåÆù Åë½Å»ç ¼±ÅÃ
//         mobileCompany1 = new JCheckBox("LGU+");
//         mobileCompany2 = new JCheckBox("KT");
//         mobileCompany3 = new JCheckBox("SKT");
         //
//         mobileCompany1.setBounds(500, 535, 60, 40);
//         mobileCompany2.setBounds(570, 535, 40, 40);
//         mobileCompany3.setBounds(630, 535, 60, 40);
         //
         //
//         mobile_bt.add(mobileCompany1);
//         mobile_bt.add(mobileCompany2);
//         mobile_bt.add(mobileCompany3);
//         add(mobileCompany1);
//         add(mobileCompany2);
//         add(mobileCompany3);

         // ÀÌ¸ÞÀÏ
         email_L = new JLabel("E-mail");
//         email_L.setBounds(20,595,100,30);  
         information.add(email_L);

         // ÀÌ¸ÞÀÏ ÅØ½ºÆ®Ã¢
         email_1 = new JTextField();
         email_1.setBounds(140, 590, 170, 40);
         add(email_1);
         textFieldList.add(email_1);

         JLabel e1 = new JLabel(" @ ");

         e1.setBounds(320, 593, 120, 35);
         add(e1);

         // ÀÌ¸ÞÀÏ µÎ¹øÂ° ÅØ½ºÆ®Ã¢
         email_2 = new JTextField();
         email_2.setBounds(350, 590, 170, 40);
         add(email_2);
         textFieldList.add(email_2);

         email_V = new Vector<String>(); // emailÃ¢ ¸ñ·Ï »ý¼º
         email_V.add("Á÷Á¢ÀÔ·Â");
         email_V.add("naver.com");
         email_V.add("nate.com");
         email_V.add("hanmail.net");
         email_V.add("yahoo.co.kr");
         email_V.add("daum.net");
         email_V.add("gmail.com");

         // ÀÌ¸ÞÀÏ ÁÖ¼Ò
         emailbox = new JComboBox<String>(email_V);
         emailbox.setBounds(540, 590, 160, 35);
         emailbox.setSelectedItem("Á÷Á¢ÀÔ·Â"); // ´ÙÀ½À¸·Î °íÁ¤
         add(emailbox);
         emailbox.addActionListener(this);

         // ÁÖ¼Ò
         address_L = new JLabel("ÁÖ¼Ò");
         information.add(address_L);

         // ÁÖ¼Ò ÅØ½ºÆ®Ã¢
         address_t = new JTextField();
         address_t.setBounds(140, 642, 360, 40);
         add(address_t);
         textFieldList.add(address_t);

         // »ç¾÷Àå¸í
         business_L = new JLabel("»ç¾÷Àå¸í");
//         cardNumber_L.setBounds(20,650,100,30);  
         information.add(business_L);

         // »ç¾÷¸íÀå ÀÔ·ÂÃ¢
         business_1 = new JTextField();
         business_1.setBounds(140, 698, 360, 40);
         add(business_1);
         business_1.setEnabled(false);
//         textFieldList.add(business_1);

         // »ç¾÷Àå ÁÖ¼Ò
         businessPlace_L = new JLabel("»ç¾÷Àå ÁÖ¼Ò");
         information.add(businessPlace_L);

         // »ç¾÷ÀåÁÖ¼Ò ÅØ½ºÆ®Ã¢
         business_2 = new JTextField();
         business_2.setBounds(140, 753, 360, 40);
         add(business_2);
         business_2.setEnabled(false);
//         textFieldList.add(business_2);

         // °èÁÂ¹øÈ£
         bankNumber = new JLabel("°èÁÂ¹øÈ£");
         bankNumber_T = new JTextField();
         information.add(bankNumber);

         // ÀºÇà ¼±ÅÃ
         bankChoice = new Vector<String>();
         bankChoice.add("ÀºÇà¼±ÅÃ");
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
         bankBox.setBounds(140, 813, 100, 35);
//         bankBox.setSelectedItem("-ÀºÇà¼±ÅÃ-"); // ´ÙÀ½À¸·Î °íÁ¤
         add(bankBox);
         bankBox.setEnabled(false);  //ºñÈ°¼ºÈ­

         // °èÁÂ¹øÈ£ ÀÔ·ÂÃ¢
         bankNumber_T = new JTextField();
         bankNumber_T.setBounds(250, 812, 250, 40);
         add(bankNumber_T);
         bankNumber_T.setEnabled(false); //ºñÈ°¼ºÈ­
         
         //°èÁÂ¹øÈ£ ¼³¸í ¶óº§ 
         bankNumBer_L = new JLabel("'   ¡ª   ' ¸¦ Ãß°¡ÇØ¼­ ÀÔ·ÂÇÏ¼¼¿ä.");
         bankNumBer_L.setBounds(510,816,200,35);
         add(bankNumBer_L);

//         textFieldList.add(bankNumber_T);

         //Ä«µå¹øÈ£ ÀÔ·Â Ã¢
         cardNumber = new JLabel("Ä«µå¹øÈ£");
         cardNumber.setBounds(20, 868, 100, 35);
         add(cardNumber);

         JLabel cardNumberLabel1 = new JLabel("-");
         JLabel cardNumberLabel2 = new JLabel("-");
         JLabel cardNumberLabel3 = new JLabel("-");

         cardNumberLabel1.setBounds(220, 868, 30, 35);
         cardNumberLabel2.setBounds(320, 868, 30, 35);
         cardNumberLabel3.setBounds(420, 868, 30, 35);

         add(cardNumberLabel1);
         add(cardNumberLabel2);
         add(cardNumberLabel3);

         //Ä«µå¹øÈ£ ÀÔ·ÂÃ¢ ¾î·¹ÀÌ ¸®½ºÆ®¿¡ ´ã±â
         ArrayList<JTextField> cardNumber_T = new ArrayList<JTextField>();
         cardNumber_T.add(cardNumber_T1);
         cardNumber_T.add(cardNumber_T2);
         cardNumber_T.add(cardNumber_T3);
         cardNumber_T.add(cardNumber_T4);

         cardNumber_T1 = new JTextField();
         cardNumber_T1.setBounds(140, 868, 70, 35);
         add(cardNumber_T1);
         cardNumberArr.add(cardNumber_T1);

         cardNumber_T2 = new JTextField();
         cardNumber_T2.setBounds(240, 868, 70, 35);
         add(cardNumber_T2);
         cardNumberArr.add(cardNumber_T2);

         cardNumber_T3 = new JTextField();
         cardNumber_T3.setBounds(340, 868, 70, 35);
         add(cardNumber_T3);
         cardNumberArr.add(cardNumber_T3);

         cardNumber_T4 = new JTextField();
         cardNumber_T4.setBounds(440, 868, 70, 35);
         add(cardNumber_T4);
         cardNumberArr.add(cardNumber_T4);

         // È®ÀÎ ¹öÆ°
         check = new JButton("È®ÀÎ");
         check.setBounds(130, 945, 200, 50);
         add(check);
         check.addActionListener(this);
         
         //Ãë¼Ò ¹öÆ° 
         cancellation = new JButton("Ãë¼Ò");
         cancellation.setBounds(410, 945, 200, 50);
         add(cancellation);
         cancellation.addActionListener(this);

         // ¶óº§ °£°Ý ¸ÂÃß±â
         for (int i = 0; i < information.size(); i++) {
            information.get(i).setBounds(20, i * 55 + 100, 100, 30);

         }

         for (JLabel jL : information) {
            add(jL);
         }

         //ÀÏ¹ÝÈ¸¿ø È¸¿ø°¡ÀÔÃ¢ ---> »ç¾÷ÀÚ¸í, °èÁÂ¹øÈ£ ºÎºÐ setText ÇØÁÜ
         business_1.setText("\t        Á¡¼úÈ¸¿ø¸¸ ÀÔ·Â ÇØ ÁÖ¼¼¿ä.");
         business_2.setText("\t        Á¡¼úÈ¸¿ø¸¸ ÀÔ·Â ÇØ ÁÖ¼¼¿ä.");
         bankNumber_T.setText("                  Á¡¼úÈ¸¿ø¸¸ ÀÔ·Â ÇØ ÁÖ¼¼¿ä.");

         
         
//         cardNumber_T1.setText("ÀÏ¹Ý");
//         cardNumber_T2.setText("È¸¿ø¸¸");
//         cardNumber_T3.setText("ÀÔ·ÂÇØ");
//         cardNumber_T4.setText("ÁÖ¼¼¿ä");

         
         //½Ç½Ã°£ °Ë»çÁß....
         try {
            Thread.sleep(100);
         } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         
         signUpChk = new SignUpChk();
         signUpEnd = true;
         signUpChk.start();
         setVisible(true);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      }
      
      
      
      //¾×¼Ç¸®½º³Ê ------------------------------------------------------------
      @Override
      public void actionPerformed(ActionEvent e) {

         boolean textChk = false; //ÅØ½ºÆ®ÇÊµå Ã¢ ºóÄ­ È®ÀÎ 
         
         int cnt_n = 0;
         int cnt_s = 0;
         
         for (boolean rgc1 : regularChk_N) {
            if(rgc1 == true) {
               cnt_n++;
               
            }
         }
            for (boolean rgc2 : regularChk_S) {
               if(rgc2 == true) {
                  cnt_s++;
                  
               }
         
      }
         
         

         // DB¿¬µ¿
         String id = id_t.getText();
         String pw = pw_t.getText();
         String pwChkStr = passWord.getText();
         String name = name_t.getText();
         String gender = "";
         String birthYYYYMMDD = yearBox.getSelectedItem().toString() + monthBox.getSelectedItem().toString()
               + dayBox.getSelectedItem().toString();
         String phone = pnBox.getSelectedItem() + "-" + pnNum1.getText() + "-" + pnNum2.getText();
         String email = email_1.getText() + "@" + email_2.getText();
         String address = address_t.getText();
         String pwhint = hintBox.getSelectedItem().toString();
         String pwres = pwHintAnswer.getText();
         String card = cardNumber_T1.getText() + "-" + cardNumber_T2.getText() + "-" + cardNumber_T3.getText() + "-"
               + cardNumber_T4.getText();
         
         String businessname = business_1.getText();
         String businessaddress = business_2.getText();
         String banknum = bankNumber_T.getText() + "/" + bankBox.getSelectedItem();
         int coin = 0;
         
         //ÄÞº¸¹Ú½º 0¹øÂ° 
         boolean hintChk = hintBox.getSelectedIndex() == 0;
         boolean bankChk = bankBox.getSelectedIndex() == 0;
         boolean dayChk = yearBox.getSelectedIndex() == 0 || monthBox.getSelectedIndex() == 0
               || dayBox.getSelectedIndex() == 0;

         // ÀÏ¹ÝÈ¸¿øÀÏ¶§ ---------------
         if (nomalUserKind.isSelected()) {
            
            // È¸¿ø°¡ÀÔ ºóÄ­¿©ºÎ¸¦ È®ÀÎÇØÁÖ´Â ¸®½ºÆ® --°¹¼ö È®ÀÎÇÏ´Â °÷¿¡¼­¸¸ ½á¾ßÇÔ
            // ÅØ½ºÆ®Ã¢ÀÌ ÀÔ·ÂµÆÀ»¶§ Ãß°¡ÇØÁÖ´Â ¸®½ºÆ® (È¸¿ø°¡ÀÔ ÅØ½ºÆ®ÇÊµå¿¡ ÀÔ·ÂÀÌ ¾ÈµÇ¸é add¾ÈÇÔ)
            textFieldList_2 = new ArrayList<String>();
            textFieldList_2.add(id_t.getText());
            textFieldList_2.add(pw_t.getText());
            textFieldList_2.add(passWord.getText());
            textFieldList_2.add(pwHintAnswer.getText());
            textFieldList_2.add(name_t.getText());
            textFieldList_2.add(pnNum1.getText());
            textFieldList_2.add(pnNum2.getText());
            textFieldList_2.add(email_1.getText());
            textFieldList_2.add(email_2.getText());
            textFieldList_2.add(address_t.getText());
            textFieldList_2.add(cardNumber_T1.getText());
            textFieldList_2.add(cardNumber_T2.getText());
            textFieldList_2.add(cardNumber_T3.getText());
            textFieldList_2.add(cardNumber_T4.getText());
            
            
            //Á¡¼úÈ¸¿ø ÅØ½ºÆ®ÇÊµå Ã¢ ºñÈ°¼ºÈ­ 
            business_1.setEnabled(false);
            business_2.setEnabled(false);
            bankBox.setEnabled(false);
            bankNumber_T.setEnabled(false);
            
            //ÀÏ¹ÝÈ¸¿ø¿¡¼­´Â ¿­¾îµÎ°í Á¡¼úÈ¸¿ø¹öÆ° ´©¸£¸é ºñÈ°¼ºÈ­°¡ µÊ
            cardNumber_T1.setEnabled(true);
            cardNumber_T2.setEnabled(true);
            cardNumber_T3.setEnabled(true);
            cardNumber_T4.setEnabled(true);

            // È¸¿ø°¡ÀÔÃ¢ µé¾î°¬À»¶§ ÀÏ¹ÝÈ¸¿ø Ä«µå¹øÈ£ ÅØ½ºÆ® ºñ¿ò
            if(!e.getSource().equals(check) && !e.getSource().equals(idCheck)) {
            cardNumber_T1.setText("");
            cardNumber_T2.setText("");
            cardNumber_T3.setText("");
            cardNumber_T4.setText("");
            }
            
            
            //È¸¿ø ºÐ·ù ¹öÆ° ¿©·¯¹ø ´­·¯µµ °è¼Ó »ý°å´Ù°¡ Áö¿öÁ³´Ù ÇÔ.
            business_1.setText("\t      Á¡¼úÈ¸¿ø¸¸ ÀÔ·Â ÇØ ÁÖ¼¼¿ä.");
            business_2.setText("\t      Á¡¼úÈ¸¿ø¸¸ ÀÔ·Â ÇØ ÁÖ¼¼¿ä.");
            bankNumber_T.setText("          Á¡¼úÈ¸¿ø¸¸ ÀÔ·Â ÇØ ÁÖ¼¼¿ä.");

            
            
            
            // Á¡¼úÈ¸¿øÀÏ¶§ ------------------------
         } else if (sellerUserKind.isSelected()) {
            
           signUpEnd = true;
           
            textFieldList_2 = new ArrayList<String>();
            textFieldList_2.add(id_t.getText());
            textFieldList_2.add(pw_t.getText());
            textFieldList_2.add(passWord.getText());
            textFieldList_2.add(pwHintAnswer.getText());
            textFieldList_2.add(name_t.getText());
            textFieldList_2.add(pnNum1.getText());
            textFieldList_2.add(pnNum2.getText());
            textFieldList_2.add(email_1.getText());
            textFieldList_2.add(email_2.getText());
            textFieldList_2.add(address_t.getText());
            textFieldList_2.add(business_1.getText());
            textFieldList_2.add(business_2.getText());
            textFieldList_2.add(bankNumber_T.getText());

            
            //Á¡¼úÈ¸¿ø°¡ÀÔÀÏ¶© ¿­¾îµÒ
            business_1.setEnabled(true);
            business_2.setEnabled(true);
            bankBox.setEnabled(true);
            bankNumber_T.setEnabled(true);
            
            //Ä«µå¹øÈ£ ¾È¾²ÀÌ±â¶§¹®¿¡ ºñÈ°¼ºÈ­
            cardNumber_T1.setEnabled(false);
            cardNumber_T2.setEnabled(false);
            cardNumber_T3.setEnabled(false);
            cardNumber_T4.setEnabled(false);
            cardNumber_T1.setText("    ÀÏ¹Ý");
            cardNumber_T2.setText("   È¸¿ø¸¸");
            cardNumber_T3.setText("   ÀÔ·ÂÇØ");
            cardNumber_T4.setText("   ÁÖ¼¼¿ä");

            //ÀÔ·ÂÇÒ ¼ö ÀÖ°Ô ºñ¿öµÎ±â
            if(!e.getSource().equals(check) && !e.getSource().equals(idCheck)) {
            business_1.setText("");
            business_2.setText("");
            bankNumber_T.setText("");
            }
         } 
         

         // ÄÞº¸¹Ú½º¿¡ ÀÖ´Â ÀÌ¸ÞÀÏÀ» ´©¸£¸é ÀÌ¸ÞÀÏ µÎ¹øÂ° Ã¢¿¡ ¹Ù·Î ÀÔ·ÂµÇ´Â ¾×¼Ç ¸®½º³Ê
         if (e.getSource().equals(emailbox)) { // ÀÌ¸ÞÀÏÀ» °ñ¶úÀ»¶§

            // Á÷Á¢ÀÔ·Â ¿Ü¿¡ ÀÌ¸ÞÀÏ ÀÔ·ÂÇßÀ»¶§
            if (!((String) emailbox.getSelectedItem()).equals("Á÷Á¢ÀÔ·Â")) {
               email_2.setText((String) emailbox.getSelectedItem()); // ÅØ½ºÆ®Ã¢¿¡ ÀÌ¸ÞÀÏ ¶ç¿ò

            } else { // Á÷Á¢ ÀÔ·ÂÀÏ °æ¿ì ÅØ½ºÆ®Ã¢ ºñ¿ì±â
               email_2.setText("");
            }

         }

         // 1) Ãë¼Ò¹öÆ° ´­·¶À»¶§ ------------------------------------
         if (e.getSource().equals(cancellation)) {

            signUpEnd = false;  
            lg.SignUp = null;
            dispose();

            // 2) ¾ÆÀÌµð Áßº¹È®ÀÎ ¹öÆ° ´­·¶À»¶§----------------------------------------
         } else if (e.getSource().equals(idCheck)) {

            // ÀÏ¹ÝÈ¸¿ø Áßº¹È®ÀÎ -----------------------------------------
            if (nomalUserKind.isSelected()) {

               // ¾ÆÀÌµðÅØ½ºÆ® Ã¢ÀÌ ºñ¾îÀÖÀ»¶§ -- ¾ÆÀÌµð ÀÔ·ÂÇØÁÖ¼¼¿ä
               if (id_t.getText().isEmpty()) {
                  JOptionPane.showMessageDialog(null, " ¾ÆÀÌµð¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä. ", "È¸¿ø°¡ÀÔ ", JOptionPane.WARNING_MESSAGE);
                  idChkBl = false;

                  // »ç¿ëÀÚ°¡ ÀÔ·ÂÇÑ ¾ÆÀÌµð¿Í UserDB¿¡ id¿Í °°À¸¸é -- Áßº¹ÀÓÀ» ¾Ë¸²
               } else if (id_t.getText().equals(UserDB.getID(id))) {
                  JOptionPane.showMessageDialog(null, " ÀÌ¹Ì »ç¿ëÁßÀÎ ¾ÆÀÌµðÀÔ´Ï´Ù. ", "È¸¿ø°¡ÀÔ ", JOptionPane.WARNING_MESSAGE);
                  id_t.setText("");
                  idChkBl = false;

                  // ¾ÆÀÌµð À¯È¿¼º °Ë»ç --> °ø¹éÀÏ¶§ XX
               } else if (!(Pattern.matches(idRegular, id_t.getText()))
                     || !(Pattern.matches(sRegular, id_t.getText()))) {
                  JOptionPane.showMessageDialog(null, "¾ÆÀÌµð´Â °ø¹é¾øÀÌ 5~9ÀÚ ÀÌ³»·Î ¿µ¹®/¼ýÀÚ¸¸ »ç¿ë°¡´ÉÇÕ´Ï´Ù.");
                  id_t.setText("");

                  // ³ª¸ÓÁö°¡ ´Ù ¾Æ´Ï¶ó¸é »ç¿ë°¡´É !!
               } else {
                  idChkBl = true;
                  JOptionPane.showMessageDialog(null, " »ç¿ë°¡´ÉÇÑ ¾ÆÀÌµðÀÔ´Ï´Ù. ");
               }

               // Á¡¼úÈ¸¿ø Áßº¹È®ÀÎ----------------------------------------------
            } else if (sellerUserKind.isSelected()) {
               System.out.println("Á¡¼ú°¡");

               // ¾ÆÀÌµðÅØ½ºÆ® Ã¢ÀÌ ºñ¾îÀÖÀ»¶§ -- ¾ÆÀÌµð ÀÔ·ÂÇØÁÖ¼¼¿ä
               if (id_t.getText().trim().isEmpty()) {
                  JOptionPane.showMessageDialog(null, " ¾ÆÀÌµð¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä. ", "È¸¿ø°¡ÀÔ ", JOptionPane.WARNING_MESSAGE);
                  idChkBl = false;

                  // »ç¿ëÀÚ°¡ ÀÔ·ÂÇÑ ¾ÆÀÌµð¿Í UserDB¿¡ id¿Í °°À¸¸é -- Áßº¹ÀÓÀ» ¾Ë¸²
               } else if (id_t.getText().equals(UserDB.getID(id))) {
                  JOptionPane.showMessageDialog(null, " ÀÌ¹Ì »ç¿ëÁßÀÎ ¾ÆÀÌµðÀÔ´Ï´Ù. ", "È¸¿ø°¡ÀÔ ", JOptionPane.WARNING_MESSAGE);
                  idChkBl = false;
                  id_t.setText("");

                  // ¾ÆÀÌµð À¯È¿¼º °Ë»ç --> °ø¹éÀÏ¶§ XX
               } else if (!(Pattern.matches(idRegular, id_t.getText().trim()))
                     || !(Pattern.matches(sRegular, id_t.getText().trim()))) {
                  JOptionPane.showMessageDialog(null, "¾ÆÀÌµð´Â °ø¹é¾øÀÌ 5~9ÀÚ ÀÌ³»·Î ¿µ¹®/¼ýÀÚ¸¸ »ç¿ë°¡´ÉÇÕ´Ï´Ù.");
                  id_t.setText("");

                  // ³ª¸ÓÁö°¡ ´Ù ¾Æ´Ï¶ó¸é »ç¿ë°¡´É !!
               } else {
                  idChkBl = true;
                  JOptionPane.showMessageDialog(null, " »ç¿ë°¡´ÉÇÑ ¾ÆÀÌµðÀÔ´Ï´Ù. ");
               }

            }

            // 3) È®ÀÎ ¹öÆ° ´­·¶À»¶§ ------------------------------------
         } else if (e.getSource().equals(check)) {

            // ºóÄ­ÀÌ ÀÖÀ»¶§ ÅØ½ºÆ®chk°¡ falseÀÓ / ÀÔ·ÂÀÌ µÆÀ»¶§´Â true
            for (int i = 0; i < textFieldList_2.size(); i++) {
               if (textFieldList_2.get(i).equals("")) {
                  textChk = false;
                  break;
               } else {
                  textChk = true;
               }
            }

            System.out.println(textFieldList_2);


            // ÀÏ¹ÝÈ¸¿ø À¯È¿¼º °Ë»ç-------------------------------------------------------------------------------
            if (nomalUserKind.isSelected()) {
               System.out.println("Ã¼Å©!");

               // ÅØ½ºÆ®ÇÊµåÃ¢ °¹¼ö È®ÀÎ
               //System.out.println(textFieldList.size()); 
               System.out.println(textFieldList_2.size());

               // ¼ºº°À» ´­·¶À»¶§ ³²ÀÚ,¿©ÀÚ È®ÀÎ
               if (man_bt.isSelected()) {
                  System.out.println("³²ÀÚ");
                  gender = "³²ÀÚ";

               } else if (woman_bt.isSelected()) {
                  System.out.println("¿©ÀÚ");
                  gender = "¿©ÀÚ";

                  // »ç¿ëÀÚ ºñ¹Ð¹øÈ£¿Í »ç¿ëÀÚ ºñ¹Ð¹øÈ£ È®ÀÎÀÌ °°Áö ¾ÊÀ»¶§ -- ´Ù½Ã È®ÀÎÇØ´Þ¶ó´Â Ã¢À» ¶ç¿ò.
               }
               if (!(pw_t.getText().equals(passWord.getText()))) {

                  JOptionPane.showMessageDialog(null, "ºñ¹Ð¹øÈ£¸¦ ´Ù½Ã È®ÀÎÇØ ÁÖ¼¼¿ä.", "È¸¿ø°¡ÀÔ", JOptionPane.WARNING_MESSAGE);

                  
                  // ÅØ½ºÆ®ÇÊµå Ã¢ °¹¼ö°¡ 12°³º¸´Ù Àû°Ô ÀÔ·ÂÇßÀ»¶§ --- È¸¿øÁ¤º¸ ÀÔ·ÂÇÏ¶ó´Â Ã¢ ¶ç¿ò.
               } else if (textChk == false) {
                  System.out.println("È¸¿øÁ¤º¸");
                  JOptionPane.showMessageDialog(null, "È¸¿øÁ¤º¸¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä.", "È¸¿ø°¡ÀÔ", JOptionPane.WARNING_MESSAGE);

                  
                  // ¾ÆÀÌµð Áßº¹Ã¼Å©¸¦ ÇÏÁö¾Ê°í È¸¿ø°¡ÀÔÇÏ´Â°ÍÀ» ¹æÁö -- Áßº¹È®ÀÎÀ» ´©¸£Áö ¾Ê°í È¸¿ø°¡ÀÔÈ®ÀÎÀ» ´©¸£¸é Áßº¹È®ÀÎÀ» ÇØ´Þ¶ó´Â Ã¢À» ¶ç¿ò.
               } else if (idChkBl == false) {

                  JOptionPane.showMessageDialog(null, "Áßº¹È®ÀÎÀ» ÇØÁÖ¼¼¿ä.", "È¸¿ø°¡ÀÔ", JOptionPane.WARNING_MESSAGE);


                  // ºñ¹Ð¹øÈ£ ÈùÆ®¸¦ ¼±ÅÃ ¾ÈÇßÀ» ¶§ true
               } else if (hintChk == true) {
                  JOptionPane.showMessageDialog(null, "ºñ¹Ð¹øÈ£ ÈùÆ®¸¦ ¼±ÅÃÇØ ÁÖ¼¼¿ä", "È¸¿ø°¡ÀÔ", JOptionPane.WARNING_MESSAGE);
                  
                  
                  // »ý³â¿ùÀÏÀ» ¼±ÅÃ ¾ÈÇßÀ» ¶§ true
               } else if (dayChk == true) {
                  JOptionPane.showMessageDialog(null, "»ý³â¿ùÀÏÀ» ¼±ÅÃÇØ ÁÖ¼¼¿ä.", "È¸¿ø°¡ÀÔ", JOptionPane.WARNING_MESSAGE);
                  
                  
                  // ¼ºº°À» ¼±ÅÃ ¾ÈÇßÀ» ¶§ true
               } else if (gender == "") {
                  JOptionPane.showMessageDialog(null, "¼ºº°À» ¼±ÅÃÇØ ÁÖ¼¼¿ä.", "È¸¿ø°¡ÀÔ", JOptionPane.WARNING_MESSAGE);

                  
               
               }else if(nomalUserKind.isSelected() && cnt_n != 11) {
                  JOptionPane.showMessageDialog(null, "¿Ã¹Ù¸¥ Çü½ÄÀ¸·Î ´Ù½Ã ÀÔ·ÂÇØ ÁÖ¼¼¿ä.", "È¸¿ø°¡ÀÔ", JOptionPane.WARNING_MESSAGE);
        
                  // ´Ù ¿À·ù°¡ ¾Æ´Ò°æ¿ì °¡ÀÔÁ¶°Ç¿¡ ÃæÁ·µÇ¾î °¡ÀÔ¿Ï·á Ã¢À» ¶ç¿ò.
               }else  {
                  System.out.println(textFieldList.size());
                  UserDB.signupNOMALUSER(id, pwChkStr, name, gender, birthYYYYMMDD, phone, email, address, card,
                        pwhint, pwres, coin);
                  MenuDB.makeMENU(id);
                  JOptionPane.showMessageDialog(null, "          È¸¿ø°¡ÀÔ ¿Ï·á! \nÁ¡Åå¿¡ ¿À½Å°ÍÀ» È¯¿µÇÕ´Ï´Ù.");
                  
                  lg.SignUp = null;
                  signUpEnd = false;
                  dispose();
               }

               // Á¡¼úÈ¸¿ø À¯È¿¼º
               // °Ë»ç-------------------------------------------------------------------------------

            } else if (sellerUserKind.isSelected()) {
               System.out.println("Ã¼Å©!");

               // ÅØ½ºÆ®ÇÊµåÃ¢ °¹¼ö È®ÀÎ
               // System.out.println(textFieldList.size());

               // ¼ºº°À» ´­·¶À»¶§ ³²ÀÚ,¿©ÀÚ È®ÀÎ
               if (man_bt.isSelected()) {
                  System.out.println("³²ÀÚ");
                  gender = "³²ÀÚ";

               } else if (woman_bt.isSelected()) {
                  System.out.println("¿©ÀÚ");
                  gender = "¿©ÀÚ";

                  // »ç¿ëÀÚ ºñ¹Ð¹øÈ£¿Í »ç¿ëÀÚ ºñ¹Ð¹øÈ£ È®ÀÎÀÌ °°Áö ¾ÊÀ»¶§ -- ´Ù½Ã È®ÀÎÇØ´Þ¶ó´Â Ã¢À» ¶ç¿ò.
               }
               if (!(pw_t.getText().equals(passWord.getText()))) {
                  JOptionPane.showMessageDialog(null, "ºñ¹Ð¹øÈ£¸¦ ´Ù½Ã È®ÀÎÇØÁÖ¼¼¿ä.", "È¸¿ø°¡ÀÔ", JOptionPane.WARNING_MESSAGE);

                  // ÅØ½ºÆ®ÇÊµå Ã¢ °¹¼ö°¡ 12°³º¸´Ù Àû°Ô ÀÔ·ÂÇßÀ»¶§ --- È¸¿øÁ¤º¸ ÀÔ·ÂÇÏ¶ó´Â Ã¢ ¶ç¿ò. 17°³
               } else if (textChk == false) {
                  System.out.println("È¸¿øÁ¤º¸");
                  JOptionPane.showMessageDialog(null, "È¸¿øÁ¤º¸¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä.", "È¸¿ø°¡ÀÔ", JOptionPane.WARNING_MESSAGE);

                  // ¾ÆÀÌµð Áßº¹Ã¼Å©¸¦ ÇÏÁö¾Ê°í È¸¿ø°¡ÀÔÇÏ´Â°ÍÀ» ¹æÁö -- Áßº¹È®ÀÎÀ» ´©¸£Áö ¾Ê°í È¸¿ø°¡ÀÔÈ®ÀÎÀ» ´©¸£¸é Áßº¹È®ÀÎÀ» ÇØ´Þ¶ó´Â Ã¢À» ¶ç¿ò.
               } else if (idChkBl == false) {
                  JOptionPane.showMessageDialog(null, "Áßº¹È®ÀÎÀ» ÇØÁÖ¼¼¿ä.", "È¸¿ø°¡ÀÔ", JOptionPane.WARNING_MESSAGE);

                  // ºñ¹Ð¹øÈ£ ÈùÆ®¸¦ ¼±ÅÃ ¾ÈÇßÀ» ¶§ true
               } else if (hintChk == true) {
                  JOptionPane.showMessageDialog(null, "ºñ¹Ð¹øÈ£ ÈùÆ®¸¦ ¼±ÅÃÇØÁÖ¼¼¿ä", "È¸¿ø°¡ÀÔ", JOptionPane.WARNING_MESSAGE);
                  
                  
                  //ÀºÇàÀ» ¼±ÅÃ ¾ÈÇßÀ»¶§ true
               } else if (bankChk == true) {
                  JOptionPane.showMessageDialog(null, "ÀºÇàÀ» ¼±ÅÃÇØ ÁÖ¼¼¿ä.", "È¸¿ø°¡ÀÔ", JOptionPane.WARNING_MESSAGE);
                  
                  // »ý³â¿ùÀÏÀ» ¼±ÅÃ ¾ÈÇßÀ» ¶§ true
               } else if (dayChk == true) {
                  JOptionPane.showMessageDialog(null, "»ý³â¿ùÀÏÀ» ¼±ÅÃÇØÁÖ¼¼¿ä.", "È¸¿ø°¡ÀÔ", JOptionPane.WARNING_MESSAGE);
                  
                  // ¼ºº°À» ¼±ÅÃ ¾ÈÇßÀ» ¶§ true
               } else if (gender == "") {
                  JOptionPane.showMessageDialog(null, "¼ºº°À» ¼±ÅÃÇØÁÖ¼¼¿ä.", "È¸¿ø°¡ÀÔ", JOptionPane.WARNING_MESSAGE);
                  
                  
               }else if(sellerUserKind.isSelected() && cnt_s != 10) {
                  JOptionPane.showMessageDialog(null, "¿Ã¹Ù¸¥ Çü½ÄÀ¸·Î ´Ù½Ã ÀÔ·ÂÇØ ÁÖ¼¼¿ä.", "È¸¿ø°¡ÀÔ", JOptionPane.WARNING_MESSAGE);
                 
                  
                  // ´Ù ¿À·ù°¡ ¾Æ´Ò°æ¿ì °¡ÀÔÁ¶°Ç¿¡ ÃæÁ·µÇ¾î °¡ÀÔ¿Ï·á Ã¢À» ¶ç¿ò.
               } else {
                  System.out.println(textFieldList.size());
                  UserDB.signupSELLERUSER(id, pwChkStr, name, gender, birthYYYYMMDD, phone, email, address,
                        pwhint, pwres, businessname, businessaddress, banknum, coin);
                  MenuDB.makeMENU(id);
                  JOptionPane.showMessageDialog(null, "           È¸¿ø°¡ÀÔ ¿Ï·á !\nÁ¡Åå¿¡ ¿À½Å°ÍÀ» È¯¿µÇÕ´Ï´Ù.");
                  
                  //¾²·¹µå Á¾·á!!!!!
                  signUpEnd = false;  
                  lg.SignUp = null;
                  dispose();

               }

            }
         }

      }

      // À¯È¿¼º °Ë»ç ¾²·¹µå
      class SignUpChk extends Thread {



         @Override
         public void run() {
            while (signUpEnd) {
               // ºñ¹Ð¹øÈ£ À¯È¿¼º °Ë»ç --> ¼ýÀÚ,¿µ¹®, Æ¯¼ö¹®ÀÚ Æ÷ÇÔ (°ø¹é ±ÝÁö) ---------------------------------
               if (((!(Pattern.matches(pwRegular, pw_t.getText())))
                     || !(Pattern.matches(sRegular, pw_t.getText()))) && !pw_t.getText().equals("")) {
                  regularChk_N[0] = false;
                  regularChk_S[0] = false;
                  
                  
                  pw_t.setForeground(Color.red);
                  pw_t.setOpaque(true);

               } else {
                  regularChk_N[0] = true;
                  regularChk_S[0] = true;
                  pw_t.setForeground(Color.black);

                  //ºñ¹Ð¹øÈ£ == ºñ¹Ð¹øÈ£ È®ÀÎ  ÀÏÄ¡ÇÑÁö °Ë»ç!!
               }if(!(pw_t.getText().equals(passWord.getText()))) {
                  
                  passWord.setForeground(Color.red);
                  
                  
               }else {
                
                  passWord.setForeground(Color.black);
                  
                  
                  // ÀÌ¸§ À¯È¿¼º °Ë»ç --> ÇÑ±Û¸¸ »ç¿ë°¡´É --------------------------------
               }if ((!(Pattern.matches(nameRegular, name_t.getText())))
                     || !(Pattern.matches(sRegular, name_t.getText())) && !name_t.getText().equals("")) {
                  regularChk_N[1] = false;
                  regularChk_S[1] = false;
                  name_t.setForeground(Color.red);

               } else {
                  regularChk_N[1] = true;
                  regularChk_S[1] = true;
                  name_t.setForeground(Color.black);

                  // ÀÌ¸ÞÀÏ À¯È¿¼º °Ë»ç --> ´ë¼Ò¹®ÀÚ /¼ýÀÚ --------------------------------
               }
               if ((!(Pattern.matches(emailRegular, email_1.getText())))
                     || !(Pattern.matches(sRegular, email_1.getText())) && !email_1.getText().equals("")) {
                  regularChk_N[2] = false;
                  regularChk_S[2] = false;
                  email_1.setForeground(Color.red);

               } else {
                  regularChk_N[2] = true;
                  regularChk_S[2] = true;
                  email_1.setForeground(Color.black);

               }
               if ((!(Pattern.matches(emailRegular_2, email_2.getText())))
                     || !(Pattern.matches(sRegular, email_2.getText())) && !email_2.getText().equals("")) {
                  regularChk_N[3] = false;
                  regularChk_S[3] = false;
                  email_2.setForeground(Color.red);

               } else {
                  regularChk_N[3] = true;
                  regularChk_S[3] = true;
                  email_2.setForeground(Color.black);

                  // ÇÚµåÆù¹øÈ£ À¯È¿¼º °Ë»ç --> ±ÛÀÚ¼ö 3~4ÀÚ Á¦ÇÑ, ¼ýÀÚ¸¸ °¡´É --------------------------------
               }
               if ((!(Pattern.matches(phoneNumRegular, pnNum1.getText())))
                     || !(Pattern.matches(sRegular, pnNum1.getText())) && !pnNum1.getText().equals("")) {
                  regularChk_N[4] = false;
                  regularChk_S[4] = false;
                  pnNum1.setForeground(Color.red);

               } else {
                  regularChk_N[4] = true;
                  regularChk_S[4] = true;
                  pnNum1.setForeground(Color.black);

               }
               if ((!(Pattern.matches(phoneNumRegular, pnNum2.getText())))
                     || !(Pattern.matches(sRegular, pnNum2.getText())) && !pnNum2.getText().equals("")) {
                  regularChk_N[5] = false;
                  regularChk_S[5] = false;
                  pnNum2.setForeground(Color.red);

               } else {
                  regularChk_N[5] = true;
                  regularChk_S[5] = true;
                  pnNum2.setForeground(Color.black);

                  // Ä«µå¹øÈ£ À¯È¿¼º °Ë»ç --> ±ÛÀÚ¼ö 4ÀÚ Á¦ÇÑ, ¼ýÀÚ¸¸ °¡´É ..--------------------------------
               }
               if ((!(Pattern.matches(cardRegular, cardNumber_T1.getText())))
                     || !(Pattern.matches(sRegular, cardNumber_T1.getText()))
                           && !cardNumber_T1.getText().equals("") && nomalUserKind.isSelected()) {
                  regularChk_N[6] = false;

                  cardNumber_T1.setForeground(Color.red);

               } else {
                  regularChk_N[6] = true;
                  cardNumber_T1.setForeground(Color.black);

               }
               if ((!(Pattern.matches(cardRegular, cardNumber_T2.getText())))
                     || !(Pattern.matches(sRegular, cardNumber_T2.getText()))
                           && !cardNumber_T2.getText().equals("") && nomalUserKind.isSelected()) {
                  regularChk_N[7] = false;
 
                  cardNumber_T2.setForeground(Color.red);

               } else {
                  regularChk_N[7] = true;

                  cardNumber_T2.setForeground(Color.black);

               }
               if ((!(Pattern.matches(cardRegular, cardNumber_T3.getText())))
                     || !(Pattern.matches(sRegular, cardNumber_T3.getText()))
                           && !cardNumber_T3.getText().equals("") && nomalUserKind.isSelected()) {
                  regularChk_N[8] = false;

                  cardNumber_T3.setForeground(Color.red);

               } else {
                  regularChk_N[8] = true;

                  cardNumber_T3.setForeground(Color.black);

               }
               if ((!(Pattern.matches(cardRegular, cardNumber_T4.getText())))
                     || !(Pattern.matches(sRegular, cardNumber_T4.getText()))
                           && !cardNumber_T4.getText().equals("") && nomalUserKind.isSelected()) {
                  regularChk_N[9] = false;

                  cardNumber_T4.setForeground(Color.red);

               } else {
                  regularChk_N[9] = true;

                  cardNumber_T4.setForeground(Color.black);

                  
               }// ÁÖ¼Ò´Â ÇÑ±Û/¿µ¹®/¼ýÀÚ¸¸ °¡´É!!(ÃÊ¼º±ÝÁö)
               if ((!(Pattern.matches(addressRegular, address_t.getText().trim().replaceAll(" ", ""))))
                      && !address_t.getText().equals("")) {
                  regularChk_N[10] = false;
                  regularChk_S[6] = false;
                  address_t.setForeground(Color.red);

               } else {
                  regularChk_N[10] = true;
                  regularChk_S[6] = true;
                  address_t.setForeground(Color.black);
                  
               }//»ç¾÷Àå¸í
               if ((!(Pattern.matches(businessName_R, business_1.getText().trim().replaceAll(" ", ""))))
                     && !business_1.getText().equals("") && sellerUserKind.isSelected()) {

                  regularChk_S[7] = false;
                  business_1.setForeground(Color.red);
                  
               } else {
                  regularChk_S[7] = true;
                  business_1.setForeground(Color.black);
               }
               //»ç¾÷ÀåÁÖ¼Ò
               if ((!(Pattern.matches(addressRegular, business_2.getText().trim().replaceAll(" ", ""))))
                     && !business_2.getText().equals("")&& sellerUserKind.isSelected()) {
                  regularChk_S[8] = false;
                  business_2.setForeground(Color.red);
                  
               } else {
                  regularChk_S[8] = true;
                  business_2.setForeground(Color.black);
               
                  //°èÁÂ¹øÈ£
               }if ((!(Pattern.matches(businessAddress_R, bankNumber_T.getText().trim().replaceAll(" ", ""))))
                  && !bankNumber_T.getText().equals("")&& sellerUserKind.isSelected()) {
                  regularChk_S[9] = false;
                  bankNumber_T.setForeground(Color.red);
               
               } else {
                  regularChk_S[9] = true;
                  bankNumber_T.setForeground(Color.black);
      
               }
            }
         }
      }

      @Override
      public void windowOpened(WindowEvent e) {
         // TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ
         
      }



      @Override
      public void windowClosing(WindowEvent e) {
         lg.SignUp = null;
         signUpEnd = false;
         
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

//             if(idChkBl == true && pwChkBl == true && textFieldList_2.size() == 12)
   }

   

}



public class LoginFrame {

   public static void main(String[] args) {

      new Login(); // ·Î±×ÀÎ
//      new MemberChoice(); //È¸¿ø ºÐ·ù Ã¢
//      new Signup(); //ÀÏ¹Ý È¸¿ø°¡ÀÔ Ã¢
//      new SignUp(); //»ç¾÷ÀÚ È¸¿ø°¡ÀÔ Ã¢
//      new IDsearch();  //¾ÆÀÌµð Ã£±â
//      new PWsearch();  //ºñ¹Ð¹øÈ£ Ã£±â
//      new CardChoice(); //Ä«µå»ç ¼±ÅÃ
//      new NewPassWord(); //»õ·Î¿î ºñ¹Ð¹øÈ£ ÀÔ·Â Ã¢

   }

}