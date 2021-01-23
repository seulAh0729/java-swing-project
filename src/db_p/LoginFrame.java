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

/*--------------------�ʼ�����-----------------------------

1. �ĺ� �� �� �ִ� ��ȣ ����!!!!

2.ȸ������ ������ ������ (ID,PW,�̸� ���..)  Ŭ������ �ϰ�ó�� to Sting ���� return �ؼ� DB�� ������ 

 */

/*--------------------�ؾ��� ��--------------------------

1. ȸ������ �������� â�� ��������� - '@@@�� �ʼ� �Է� �����Դϴ�' â ����  --o
2. ���̵� �ߺ�Ȯ�� ��ư�� �������� - '��� ������ ���̵� �Դϴ�.'--�⺻ �޼���â , '�̹� ������� ���̵� �Դϴ�' --��� �޼���â  --o
3. JCombox index 0��° ����ȵǰ� �����. ---o
4. ���ο� ��й�ȣ �Է�â���� ���̵� / ���̵�+��й�ȣ ��Ʈ �� �� ���� ���� ��� '������ ��ġ���� �ʽ��ϴ�.' --��� �޼���â  --o
5. �α��� id�� pw�� ��ġ���� ������� '�������� ���� ���̵��̰ų�, �߸��� ��й�ȣ�Դϴ�.' -- ��� �޼���â   --o
6. �̸��� �����Է� �ؽ�Ʈ �ȶ��� �س���. --o
7. ȸ������ -> ��й�ȣ ,��й�ȣ Ȯ�� �ΰ��� ��ġ���������� ȸ������ Ȯ�� �������� '��й�ȣ�� �ٽ� Ȯ�� ���ּ���' â ����. ---o
8. ��ȭ��ȣ ,ī���ȣ �ؽ�Ʈ�ʵ� ���ڼ� ����!!!!!  ---o
9. ������ ���� --o
10. â����
*/

//�α��� â
class Login extends JFrame implements ActionListener {

   Login me = this;  
//   Signup signUp;
   // String userID = "admin";

   // ���̵� ,��й�ȣ �ؽ�Ʈâ
   JTextField idtxt;
   JPasswordField pwtxt;

   // ��
   JLabel idLabel; // ���̵�
   JLabel pwLabel; // ��й�ȣ

   // ��ư
   JButton login_bt; // �α���
   JButton idSearch_bt; // ���̵�ã��
   JButton pwSearch_bt; // ��й�ȣ ã��
   JButton signUp_Bt; // ȸ������

   // �׼Ǹ�����
   IDsearch idSearchFrame; // ���̵�ã�� ������Ŭ����
   PWsearch pwSearchFrame; // ���ã�� ������Ŭ����
//   MemberChoice memberChoiceFrame; // �Ϲ�,���� ȸ�� ���� ������ Ŭ����
   
   SignUp SignUp; // ȸ������ ������ Ŭ���� 
   
   
   
   
   boolean signUpEnd = true;

   // �α��� �޼ҵ� ---------------------------------------
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
      // ���̵�
      idLabel = new JLabel("���̵�");
      idLabel.setBounds(60, 400, 100, 50);
      idLabel.setFont(new Font("����ü", Font.BOLD, 15));
      add(idLabel);

      // ���̵� �Է� ĭ
      idtxt = new JTextField();
      idtxt.setBounds(130, 400, 200, 50);
      add(idtxt);
      // idtxt.setOpaque(false); //�ؽ�Ʈ ���� �����ϰ� �����
      // idtxt.setBorder(javax.swing.BorderFactory.createEmptyBorder()); //�޹�� �����ϰ�
      // ����� (�׵θ� ����)

      // �н�����
      pwLabel = new JLabel("��й�ȣ");
      pwLabel.setBounds(60, 470, 300, 50);
      pwLabel.setFont(new Font("����ü", Font.BOLD, 15));
      add(pwLabel);

      // �н����� �Է�
      pwtxt = new JPasswordField();
      pwtxt.setBounds(130, 470, 200, 50);
      add(pwtxt);

      // �α���
      login_bt = new JButton("�α���");
      login_bt.setBounds(350, 407, 100, 100);
      add(login_bt);
      login_bt.addActionListener(this);

      // idã�� --�׼�
      idSearch_bt = new JButton("���̵� ã��");
      idSearch_bt.setBounds(60, 560, 120, 40);
      add(idSearch_bt);
      idSearch_bt.addActionListener(this);
      
      

      // pwã�� --�׼�
      pwSearch_bt = new JButton("��й�ȣ ã��");
      pwSearch_bt.setBounds(190, 560, 120, 40);
      add(pwSearch_bt);
      pwSearch_bt.addActionListener(this);

      // ȸ������ --�׼�
      signUp_Bt = new JButton("ȸ������");
      signUp_Bt.setBounds(320, 560, 130, 40);
      add(signUp_Bt);
      signUp_Bt.addActionListener(this);

      System.out.println(idtxt);
      setVisible(true);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

   }

   @Override
   public void actionPerformed(ActionEvent e) {

      // ���̵�ã�� ��ư ��������
      if (e.getSource().equals(idSearch_bt)) {
         if(idSearchFrame == null) {
            idSearchFrame = new IDsearch(this);
         }
         // ��й�ȣ ã�� ��ư ��������
      } else if (e.getSource().equals(pwSearch_bt)) {
         if(pwSearchFrame==null) {
            pwSearchFrame = new PWsearch(this);   
         }

         // ȸ������ ��ư �������� -> �Ϲ�,����ȸ�� ���������� ����
      } else if (e.getSource().equals(signUp_Bt)) {
//         memberChoiceFrame = new MemberChoice();
         if(SignUp==null) {
            SignUp = new SignUp(this);
         }

         // �α��� ��ư ��������
      } else if (e.getSource().equals(login_bt)) {
       
         if (idtxt.getText().isEmpty() || pwtxt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "���̵�� ��й�ȣ�� �Է����ּ���.  ", "�α��� ����", JOptionPane.WARNING_MESSAGE);

            // ���̵�� ��й�ȣ�� ��ġ���� ���� ���
         } else if (!(UserDB.getPW(idtxt.getText()).equals(pwtxt.getText()))) {
            JOptionPane.showMessageDialog(null, "���̵�� ��й�ȣ�� ���� �ʽ��ϴ�.");

            //�ߺ� �α����� ���
         }else if (UserDB.getLOGINCHK(idtxt.getText()).equals("true")) {
           JOptionPane.showMessageDialog(null, "�������̵�� �α������Դϴ�.");

              // �� �ƴϸ� �α��� ����!
         } else {
           JOptionPane.showMessageDialog(null, "            �α��� ����! \n���忡 ���� ���� ȯ���մϴ� ��");
           dispose();
           UserDB.setLOGINCHK(idtxt.getText(), "true");
           new MainFrame(idtxt.getText());

        }
      }

   }
   



   // ���̵�ã��
   class IDsearch extends JFrame implements ActionListener, WindowListener{

      // �̸�/��ȭ��ȣ ��
      JLabel idName_s;
      JLabel idPhone_s;

      // �̸�/��ȭ��ȣ �ؽ�Ʈâ
      JTextField idName_t;
      JTextField idPhone_t;
      JTextField idPhone_t2;

      // ���̵�ã�� /���
      JButton idChk_s;
      JButton idCancellation;

      Vector<String> phoneNumber;
      JComboBox<String> pnBox;
      
      // UserDB
      String userNAME; 
      String userPHONE;
      
      String resID;

      Login lg;

      // ���̵� ã�� ������ Ŭ����
//      ResID resIDFrame;

      public IDsearch(Login lg) {
         setTitle("���̵� ã��");
         setBounds(600, 230, 500, 600);
         setLayout(null);
         this.lg = lg;

         // �̸� �Է�â
         idName_s = new JLabel("�̸�");
         idName_s.setBounds(225, 110, 100, 100);
         add(idName_s);
         idName_t = new JTextField();
         idName_t.setBounds(165, 190, 155, 40);
         add(idName_t);

         // ��ȭ��ȣ �Է�â
         idPhone_s = new JLabel("��ȭ��ȣ");
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

         // �ڵ�����ȣ ¦���
         idPhone_s = new JLabel("��");
         idPhone_s.setBounds(169, 315, 30, 30);
         add(idPhone_s);
         idPhone_s = new JLabel("��");
         idPhone_s.setBounds(287, 315, 30, 30);
         add(idPhone_s);

         // �ڵ�����ȣ �߰�, ���ڸ�
         idPhone_t = new JTextField();
         idPhone_t.setBounds(190, 310, 90, 40);
         add(idPhone_t);
         idPhone_t2 = new JTextField();
         idPhone_t2.setBounds(310, 310, 90, 40);
         add(idPhone_t2);

         // ���̵�ã�� ��ư
         idChk_s = new JButton("���̵� ã��");
         idChk_s.setBounds(100, 430, 120, 50);
         add(idChk_s);
         idChk_s.addActionListener(this);

         // ��ҹ�ư
         idCancellation = new JButton("���");
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
         
         if (e.getSource().equals(idChk_s)) { // ���̵�ã�� ��ư ��������
            
            if(resID.equals("")) {
               JOptionPane.showMessageDialog(null, "���̵�� ��ȭ��ȣ�� ��ġ���� �ʽ��ϴ� !  ", "���̵� ã��", JOptionPane.ERROR_MESSAGE);
               idName_t.setText("");
               idPhone_t.setText("");
               pnBox.setSelectedIndex(0);
               idPhone_t2.setText("");
               
               
            }else {
               
               JOptionPane.showMessageDialog(null, "ȸ������ ���̵��   "+resID+"   �Դϴ�.", "���̵� ã��",  JOptionPane.INFORMATION_MESSAGE);
               lg.idSearchFrame = null; 
               dispose();
              
               
            }
//            if(resIDFrame==null) {
//               resIDFrame = new ResID();
//            }
//            lg.idSearchFrame = null;

         } else if (e.getSource().equals(idCancellation)) { // ��� ��ư ��������

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

   // ��й�ȣ ã��
   class PWsearch extends JFrame implements ActionListener , WindowListener{

      JLabel idSearch; // ���̵� �Է�
      JLabel pwHint_S; // ��й�ȣ ��Ʈ
      JLabel pwAnswer_S; // ��й�ȣ ��

      JTextField idSearch_t; // ���̵� �ؽ�Ʈâ
      JTextField pwSearch_t; // ��й�ȣ �ؽ�Ʈâ
      Vector<String> passWordHint_S; // ��й�ȣ ��Ʈ
      JComboBox<String> hintBox_S; // ��й�ȣ ��Ʈ comboBox
      
      //DB
      String userID;
      String userPWHINT;
      String userPWRES;
      
      String resPW;

      JButton passWordChk; // ��й�ȣ ã��
      JButton pwCancellation; // ��� ��ư

      // �׼Ǹ�����
      
      Login lg; // �θ���
      public PWsearch(Login lg) {
         this.lg=lg;
         setTitle("��й�ȣ ã��");
         setBounds(600, 230, 500, 600);
         setLayout(null);

         // ���̵� �Է�ĭ
         idSearch = new JLabel("���̵�");
         idSearch.setBounds(225, 70, 100, 100);
         add(idSearch);
         idSearch_t = new JTextField();
         idSearch_t.setBounds(165, 150, 155, 40);
         add(idSearch_t);

         // ��й�ȣ ��Ʈ ĭ
         pwHint_S = new JLabel("��й�ȣ ��Ʈ");
         pwHint_S.setBounds(205, 220, 100, 30);
         add(pwHint_S);

         passWordHint_S = new Vector<String>();
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

         hintBox_S = new JComboBox<String>(passWordHint_S);
         hintBox_S.setBounds(130, 260, 230, 40);
//         hintBox_S.setSelectedItem("���ϴ� ������ �����ϼ���."); // �������� ����
         add(hintBox_S);

         // ��й�ȣ ��Ʈ �� �Է� ĭ
         pwAnswer_S = new JLabel("��й�ȣ ��Ʈ ��");
         pwAnswer_S.setBounds(200, 320, 230, 40);
         add(pwAnswer_S);

         pwSearch_t = new JTextField();
         pwSearch_t.setBounds(130, 370, 240, 40);
         add(pwSearch_t);

         // ��й�ȣ ã��
         passWordChk = new JButton("��й�ȣ ã��");
         passWordChk.setBounds(100, 470, 120, 50);
         add(passWordChk);
         passWordChk.addActionListener(this);

         // ��ҹ�ư
         pwCancellation = new JButton("���");
         pwCancellation.setBounds(280, 470, 100, 50);
         add(pwCancellation);
         pwCancellation.addActionListener(this);
         addWindowListener(this);
         
         setResizable(false);
         setVisible(true);

      }

      @Override
      public void actionPerformed(ActionEvent e) {

         if (e.getSource().equals(passWordChk)) { // ��й�ȣã�� ��ư ��������
           
            userID = idSearch_t.getText();
            userPWHINT = hintBox_S.getSelectedItem().toString();
            userPWRES = pwSearch_t.getText();
            resPW = UserDB.searchPW(userID, userPWHINT, userPWRES);
            
            
            if (UserDB.searchPW(userID, userPWHINT, userPWRES).equals("")) {
        
               JOptionPane.showMessageDialog(null, "���̵�� ��й�ȣ ��Ʈ�� ��ġ���� �ʽ��ϴ� !", "��й�ȣ ã��", JOptionPane.ERROR_MESSAGE);
               idSearch_t.setText("");
               pwSearch_t.setText("");
               hintBox_S.setSelectedIndex(0);
               
               
            
            } else {
            
               JOptionPane.showMessageDialog(null, "ȸ������ ���̵��   "+resPW+"   �Դϴ�.", "���̵� ã��",  JOptionPane.INFORMATION_MESSAGE);
               lg.pwSearchFrame = null;
               dispose();
//            passWordFind = new PassWordFind(this);
//            if(!passWordFind.chk) {
//               lg.pwSearchFrame = null;
//               passWordFind =null;
//            }
//            dispose();

            }
            
           }else if(e.getSource().equals(pwCancellation)) { // ��� ��ư ��������
            
            lg.pwSearchFrame = null;
            dispose();

         }

      }



      @Override
      public void windowOpened(WindowEvent e) {
         // TODO �ڵ� ������ �޼ҵ� ����
         
      }

      @Override
      public void windowClosing(WindowEvent e) {
         lg.pwSearchFrame = null;
         
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

   // �Ϲ�+����� ȸ������
   class SignUp extends JFrame implements ActionListener ,WindowListener{

//    ArrayList<Boolean> regularChk = new ArrayList<Boolean>();
      
     boolean [] regularChk_N = new boolean[11]; //�Ϲ�ȸ�� ���Խ� üũ
     boolean [] regularChk_S = new boolean[10]; //����ȸ�� ���Խ� üũ 
     
//     boolean regularAllChk = false;
     
      
      // ȸ���з� ��ư
      ButtonGroup bg; // ��ư�׷�
      JToggleButton nomalUserKind; // �Ϲ�ȸ����ư
      JToggleButton sellerUserKind; // ����ȸ����ư
      JPanel kindButtonPanel; // ��ư�г�
      JLabel idChkLabel;

      
      // ��
      JLabel id_L; // ���̵�
      JLabel pw_L; // ��й�ȣ
      JLabel passWord_L; // ��й�ȣ Ȯ��
      JLabel pwHint_L; // ��й�ȣ ��Ʈ
      JLabel pwHintAnswer_L; // ��й�ȣ ��Ʈ ��
      JLabel name_L; // �̸�
      JLabel gender_L; // ����
      JLabel birthDate_L; // �������
      JLabel phoneNumber_L; // �޴�����ȣ
      JLabel address_L; // �ּ�
      JLabel email_L; // �̸���
      JLabel business_L; // ������
      JLabel businessPlace_L; // ����� �ּ�
      JLabel bankNumber; //���¹�ȣ
      JLabel pwchk_L ;  //��й�ȣ üũ ��
      // �󺧳��� ����
      ArrayList<JLabel> information;
      
      
       //�ο����� ��
       JLabel idForm_L; // ���̵� ����
       JLabel pwForm_L; //��й�ȣ ����
       JLabel bankNumBer_L; // �ּ� ���� 

      // ��ư
      JButton check; // Ȯ��
      JButton cancellation; // ���


      // ��̸���Ʈ�� �ؽ�Ʈ�ʵ带 �߰��ϰ� ��ĭ�� ��� �ٽ� �Է��϶�� â ����.
      ArrayList<JTextField> textFieldList = new ArrayList<JTextField>();
      ArrayList<String> textFieldList_2 ;

      // �ؽ�Ʈ â
      JTextField id_t; // ���̵�
      JPasswordField pw_t; // ��й�ȣ
      JPasswordField passWord; // ��й�ȣ Ȯ��
      JTextField pwHintAnswer; // ��й�ȣ ��Ʈ ��
      JTextField name_t; // �̸�
      JTextField pnNum1; // �ڵ�����ȣ ���
      JTextField pnNum2; // �ڵ�����ȣ ���ڸ�
      JTextField address_t; // �ּ�
      JTextField email_1; // �̸��� ���ڸ�
      JTextField email_2; // �̸��� ���ڸ�
      JTextField business_1; // ������
      JTextField business_2; // ������ּ�

      // ���̵� �ߺ�Ȯ�� ��ư
      JButton idCheck;

      ButtonGroup gender_bt = new ButtonGroup();// ����
      JRadioButton man_bt; // ���ڹ�ư
      JRadioButton woman_bt; // ���ڹ�ư

      // ��й�ȣ ��Ʈ
      Vector<String> passWordHint;
      JComboBox<String> hintBox;

      // �⵵
      Vector<String> year;
      JComboBox<String> yearBox;
      JLabel yearLabel;

      // ��
      Vector<String> month;
      JComboBox<String> monthBox;
      JLabel monthLabel;

      // ��
      Vector<String> day;
      JComboBox<String> dayBox;
      JLabel dayLabel;

      // �޴�����ȣ ��
      Vector<String> phoneNumber;
      JComboBox<String> pnBox;
      JLabel pnLabel;
      JLabel pnLabe2;

      // �޴�����ȣ ��Ż�
//         ButtonGroup mobile_bt = new ButtonGroup();
//         JCheckBox mobileCompany1;
//         JCheckBox mobileCompany2;
//         JCheckBox mobileCompany3;

      // �̸���
      Vector<String> email_V; // �̸��� ����
      JComboBox<String> emailbox;

      // ī���ȣ �ؽ�Ʈâ
      JButton cardCompany; // ī���̸� ��ư
//      JTextField cardNum_1;
//      JTextField cardNum_2;
//      JPasswordField cardNum_3;
//      JPasswordField cardNum_4;
      JLabel cardNum_L1;
      JLabel cardNum_L2;
      JLabel cardNum_L3;

      // ���¹�ȣ

      JTextField bankNumber_T;

      // ���༱��
      Vector<String> bankChoice;
      JComboBox<String> bankBox;

      // ī���ȣ
      ArrayList<JTextField> cardNumberArr = new ArrayList<JTextField>();
      JLabel cardNumber;
      JTextField cardNumber_T1;
      JTextField cardNumber_T2;
      JTextField cardNumber_T3;
      JTextField cardNumber_T4;

      boolean idChkBl = false; // ���̵� �ߺ�üũ

      // ���Խ�(��ȿ���˻�)
 
      String idRegular = "^[a-zA-Z0-9][a-zA-Z0-9]{4,8}$"; // ���̵� ��ȿ�� --> ���� (��ҹ���)/���� 5~9�� �̳���  --o
      String pwRegular = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{4,19}$"; // ��й�ȣ ��ȿ�� --> 6~20�� �̳��� ����  + ��ҹ���/����/Ư������ �ݵ�� ����   --o                                                         
      String phoneNumRegular = "\\d{3,4}$"; // ��ȭ��ȣ ���� 4�� �̳���
      String emailRegular = "^[0-9a-zA-Z\\.]*"; // �̸��� ��ҹ���/����  ��
      String emailRegular_2 = "^[a-zA-Z]{2,}.[a-zA-Z]{2,}"; // �̸��� ��ҹ���/����   ��
      String nameRegular = "^[��-�R]*$"; // �̸� �ѱ۸� ����
      String cardRegular = "^[0-9]{4,6}$"; // ����
      String addressRegular = "^[-a-zA-Z��-�R0-9]*$"; // �ּ�
      String businessName_R = "^[a-zA-Z��-�R0-9]*$";  //������ 
      String businessAddress_R = "^[0-9-]*$"; //���¹�ȣ
      String sRegular = "\\S*"; // ����

      SignUpChk signUpChk;
      
      Login lg;
      public SignUp(Login lg) {
         this.lg = lg;
         setTitle("���� ȸ������ ");
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

         nomalUserKind = new JToggleButton("�Ϲ�", true);
         nomalUserKind.addActionListener(this);
         kindButtonPanel.add(nomalUserKind);

         sellerUserKind = new JToggleButton("������");
         sellerUserKind.addActionListener(this);
         kindButtonPanel.add(sellerUserKind);

         bg = new ButtonGroup();
         bg.add(nomalUserKind);
         bg.add(sellerUserKind);

         add(kindButtonPanel);

         // ���̵�
         id_L = new JLabel("���̵� ");
//         id_L.setBounds(20,90,50,30);  
         information.add(id_L);

         // ���̵� �ؽ�Ʈâ
         id_t = new JTextField();
         id_t.setBounds(140, 93, 200, 40);
         add(id_t);
         textFieldList.add(id_t);

         // ���̵� �ߺ�Ȯ�� ��ư
         idCheck = new JButton("�ߺ�Ȯ��");
         idCheck.setBounds(350, 95, 110, 35);
         add(idCheck);
         idCheck.addActionListener(this);

         //���̵� ���� ��
           idForm_L = new JLabel("*������� 5~9�� �̳��� ����/���ڸ� ��밡��");
           idForm_L.setBounds(472,93,300,35);
           add(idForm_L);
         
         // ��й�ȣ
         pw_L = new JLabel("��й�ȣ ");
//         pw_L.setBounds(20,145,100,30);  
         information.add(pw_L);

         // ��й�ȣ �ؽ�Ʈâ
         pw_t = new JPasswordField();
         pw_t.setBounds(140, 148, 200, 40);
         add(pw_t);
         
         //��й�ȣ ���� ��
           pwForm_L = new JLabel("*������� 5~9�� �̳��� ���� ��ҹ���/����/Ư������ �ݵ�� ���� ");
           pwForm_L.setBounds(360,155,600,35);
           add(pwForm_L);
         

         // ��й�ȣ Ȯ��
         passWord_L = new JLabel("��й�ȣ Ȯ��");
//         passWord_L.setBounds(20,200,100,30);  
         information.add(passWord_L);

         // ��й�ȣ Ȯ�� �ؽ�Ʈâ
         passWord = new JPasswordField();
         passWord.setBounds(140, 203, 200, 40);
         add(passWord);
         
//         // ��й�ȣ üũ ��
//         pwchk_L = new JLabel("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
//         pwchk_L.setBounds(360,203,200,40);
//         add(pwchk_L);
         
         // ��й�ȣ ��Ʈ
         pwHint_L = new JLabel("��й�ȣ ��Ʈ");
//         pwHint_L.setBounds(20,255,100,30);  
         information.add(pwHint_L);

         // ��й�ȣ ��Ʈ ����
         passWordHint = new Vector<String>();
         passWordHint.add("���ϴ� ������ �����ϼ���.");
         passWordHint.add("���� ��￡ ���� ��Ҵ�?");
         passWordHint.add("���� ���� 1ȣ��?");
         passWordHint.add("������ �������?");
         passWordHint.add("���� �����ϴ� �ι���?");
         passWordHint.add("���� �¿����?");
         passWordHint.add("���� ������ �� ��ȭ��?");
         passWordHint.add("���� �����ϴ� ��������?");
         passWordHint.add("�λ��� ���� å ������?");
         passWordHint.add("���� �뷡�� ��â����?");

         hintBox = new JComboBox<String>(passWordHint);
         hintBox.setBounds(140, 260, 230, 40);
         // box.setSelectedIndex(2);
//         hintBox.setSelectedItem("���ϴ� ������ �����ϼ���."); // �������� ����
         add(hintBox);

         // ��Ʈ ��
         pwHintAnswer_L = new JLabel("��й�ȣ ��Ʈ ��");
//         pwHintAnswer_L.setBounds(20,310,100,30);  
         information.add(pwHintAnswer_L);

         // ��Ʈ �� �ؽ�Ʈâ
         pwHintAnswer = new JTextField();
         pwHintAnswer.setBounds(140, 315, 230, 40);
         add(pwHintAnswer);
         textFieldList.add(pwHintAnswer);

         // �̸�
         name_L = new JLabel("�̸� ");
//         name_L.setBounds(20,365,100,30);  
         information.add(name_L);

         // �̸� �ؽ�Ʈâ
         name_t = new JTextField();
         name_t.setBounds(140, 368, 200, 40);
         add(name_t);
         textFieldList.add(name_t);

         // ����
         gender_L = new JLabel("���� ");
//         gender_L.setBounds(20,420,100,30);  
         information.add(gender_L);

         // ���� ��ư
         man_bt = new JRadioButton("����");
         man_bt.setBounds(140, 425, 80, 35);
         gender_bt.add(man_bt);
         add(man_bt);
         man_bt.addActionListener(this);

         woman_bt = new JRadioButton("����");
         woman_bt.setBounds(270, 425, 80, 35);
         gender_bt.add(woman_bt);
         add(woman_bt);
         woman_bt.addActionListener(this);

         // �������
         birthDate_L = new JLabel("������� ");
//         birthDate_L.setBounds(20,475,100,30);  
         information.add(birthDate_L);

         // �⵵
         year = new Vector<String>();
         year.add("�⵵");
         for (int i = 2018; i >= 1940; i--) {
            year.add(i + "");
         }
         yearLabel = new JLabel("��");
         yearLabel.setBounds(250, 486, 30, 30);
         add(yearLabel);

         yearBox = new JComboBox<String>(year);
         yearBox.setBounds(140, 480, 100, 35);
         yearBox.setSelectedItem("�⵵");
         add(yearBox);

         // ��
         month = new Vector<String>();
         month.add("��");
         for (int i = 1; i <= 12; i++) {
            if (i < 10) {
               month.add("0" + i);
            } else {
               month.add(i + "");

            }
         }

         monthBox = new JComboBox<String>(month);
         monthBox.setBounds(280, 480, 100, 35);
         monthBox.setSelectedItem("��");
         add(monthBox);

         monthLabel = new JLabel("��");
         monthLabel.setBounds(390, 486, 30, 30);
         add(monthLabel);

         // ��
         day = new Vector<String>();
         day.add("��");

         for (int i = 1; i <= 31; i++) {
            if (i < 10) {
               day.add("0" + i);

            } else {
               day.add(i + "");

            }
         }

         dayBox = new JComboBox<String>(day);
         dayBox.setBounds(420, 480, 100, 35);
         dayBox.setSelectedItem("��");
         add(dayBox);

         dayLabel = new JLabel("��");
         dayLabel.setBounds(530, 486, 30, 30);
         add(dayLabel);

         // ��ȭ��ȣ
         phoneNumber_L = new JLabel("��ȭ��ȣ");
//         phoneNumber_L.setBounds(20,540,100,30);  
         information.add(phoneNumber_L);

         // �ڵ�����ȣ ���ڸ�
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

         // �ڵ�����ȣ ¦���
         pnLabel = new JLabel("��");
         pnLabel.setBounds(240, 540, 30, 30);
         add(pnLabel);
         pnLabe2 = new JLabel("��");
         pnLabe2.setBounds(365, 540, 30, 30);
         add(pnLabe2);

         // �ڵ�����ȣ �߰�, ���ڸ�
         pnNum1 = new JTextField();
         pnNum1.setBounds(265, 535, 90, 40);
         add(pnNum1);
         textFieldList.add(pnNum1);

         pnNum2 = new JTextField();
         pnNum2.setBounds(390, 535, 90, 40);
         add(pnNum2);
         textFieldList.add(pnNum2);

//         //�ڵ��� ��Ż� ����
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

         // �̸���
         email_L = new JLabel("E-mail");
//         email_L.setBounds(20,595,100,30);  
         information.add(email_L);

         // �̸��� �ؽ�Ʈâ
         email_1 = new JTextField();
         email_1.setBounds(140, 590, 170, 40);
         add(email_1);
         textFieldList.add(email_1);

         JLabel e1 = new JLabel(" @ ");

         e1.setBounds(320, 593, 120, 35);
         add(e1);

         // �̸��� �ι�° �ؽ�Ʈâ
         email_2 = new JTextField();
         email_2.setBounds(350, 590, 170, 40);
         add(email_2);
         textFieldList.add(email_2);

         email_V = new Vector<String>(); // emailâ ��� ����
         email_V.add("�����Է�");
         email_V.add("naver.com");
         email_V.add("nate.com");
         email_V.add("hanmail.net");
         email_V.add("yahoo.co.kr");
         email_V.add("daum.net");
         email_V.add("gmail.com");

         // �̸��� �ּ�
         emailbox = new JComboBox<String>(email_V);
         emailbox.setBounds(540, 590, 160, 35);
         emailbox.setSelectedItem("�����Է�"); // �������� ����
         add(emailbox);
         emailbox.addActionListener(this);

         // �ּ�
         address_L = new JLabel("�ּ�");
         information.add(address_L);

         // �ּ� �ؽ�Ʈâ
         address_t = new JTextField();
         address_t.setBounds(140, 642, 360, 40);
         add(address_t);
         textFieldList.add(address_t);

         // ������
         business_L = new JLabel("������");
//         cardNumber_L.setBounds(20,650,100,30);  
         information.add(business_L);

         // ������� �Է�â
         business_1 = new JTextField();
         business_1.setBounds(140, 698, 360, 40);
         add(business_1);
         business_1.setEnabled(false);
//         textFieldList.add(business_1);

         // ����� �ּ�
         businessPlace_L = new JLabel("����� �ּ�");
         information.add(businessPlace_L);

         // ������ּ� �ؽ�Ʈâ
         business_2 = new JTextField();
         business_2.setBounds(140, 753, 360, 40);
         add(business_2);
         business_2.setEnabled(false);
//         textFieldList.add(business_2);

         // ���¹�ȣ
         bankNumber = new JLabel("���¹�ȣ");
         bankNumber_T = new JTextField();
         information.add(bankNumber);

         // ���� ����
         bankChoice = new Vector<String>();
         bankChoice.add("���༱��");
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
         bankBox.setBounds(140, 813, 100, 35);
//         bankBox.setSelectedItem("-���༱��-"); // �������� ����
         add(bankBox);
         bankBox.setEnabled(false);  //��Ȱ��ȭ

         // ���¹�ȣ �Է�â
         bankNumber_T = new JTextField();
         bankNumber_T.setBounds(250, 812, 250, 40);
         add(bankNumber_T);
         bankNumber_T.setEnabled(false); //��Ȱ��ȭ
         
         //���¹�ȣ ���� �� 
         bankNumBer_L = new JLabel("'   ��   ' �� �߰��ؼ� �Է��ϼ���.");
         bankNumBer_L.setBounds(510,816,200,35);
         add(bankNumBer_L);

//         textFieldList.add(bankNumber_T);

         //ī���ȣ �Է� â
         cardNumber = new JLabel("ī���ȣ");
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

         //ī���ȣ �Է�â ��� ����Ʈ�� ���
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

         // Ȯ�� ��ư
         check = new JButton("Ȯ��");
         check.setBounds(130, 945, 200, 50);
         add(check);
         check.addActionListener(this);
         
         //��� ��ư 
         cancellation = new JButton("���");
         cancellation.setBounds(410, 945, 200, 50);
         add(cancellation);
         cancellation.addActionListener(this);

         // �� ���� ���߱�
         for (int i = 0; i < information.size(); i++) {
            information.get(i).setBounds(20, i * 55 + 100, 100, 30);

         }

         for (JLabel jL : information) {
            add(jL);
         }

         //�Ϲ�ȸ�� ȸ������â ---> ����ڸ�, ���¹�ȣ �κ� setText ����
         business_1.setText("\t        ����ȸ���� �Է� �� �ּ���.");
         business_2.setText("\t        ����ȸ���� �Է� �� �ּ���.");
         bankNumber_T.setText("                  ����ȸ���� �Է� �� �ּ���.");

         
         
//         cardNumber_T1.setText("�Ϲ�");
//         cardNumber_T2.setText("ȸ����");
//         cardNumber_T3.setText("�Է���");
//         cardNumber_T4.setText("�ּ���");

         
         //�ǽð� �˻���....
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
      
      
      
      //�׼Ǹ����� ------------------------------------------------------------
      @Override
      public void actionPerformed(ActionEvent e) {

         boolean textChk = false; //�ؽ�Ʈ�ʵ� â ��ĭ Ȯ�� 
         
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
         
         

         // DB����
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
         
         //�޺��ڽ� 0��° 
         boolean hintChk = hintBox.getSelectedIndex() == 0;
         boolean bankChk = bankBox.getSelectedIndex() == 0;
         boolean dayChk = yearBox.getSelectedIndex() == 0 || monthBox.getSelectedIndex() == 0
               || dayBox.getSelectedIndex() == 0;

         // �Ϲ�ȸ���϶� ---------------
         if (nomalUserKind.isSelected()) {
            
            // ȸ������ ��ĭ���θ� Ȯ�����ִ� ����Ʈ --���� Ȯ���ϴ� �������� �����
            // �ؽ�Ʈâ�� �Էµ����� �߰����ִ� ����Ʈ (ȸ������ �ؽ�Ʈ�ʵ忡 �Է��� �ȵǸ� add����)
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
            
            
            //����ȸ�� �ؽ�Ʈ�ʵ� â ��Ȱ��ȭ 
            business_1.setEnabled(false);
            business_2.setEnabled(false);
            bankBox.setEnabled(false);
            bankNumber_T.setEnabled(false);
            
            //�Ϲ�ȸ�������� ����ΰ� ����ȸ����ư ������ ��Ȱ��ȭ�� ��
            cardNumber_T1.setEnabled(true);
            cardNumber_T2.setEnabled(true);
            cardNumber_T3.setEnabled(true);
            cardNumber_T4.setEnabled(true);

            // ȸ������â ������ �Ϲ�ȸ�� ī���ȣ �ؽ�Ʈ ���
            if(!e.getSource().equals(check) && !e.getSource().equals(idCheck)) {
            cardNumber_T1.setText("");
            cardNumber_T2.setText("");
            cardNumber_T3.setText("");
            cardNumber_T4.setText("");
            }
            
            
            //ȸ�� �з� ��ư ������ ������ ��� ����ٰ� �������� ��.
            business_1.setText("\t      ����ȸ���� �Է� �� �ּ���.");
            business_2.setText("\t      ����ȸ���� �Է� �� �ּ���.");
            bankNumber_T.setText("          ����ȸ���� �Է� �� �ּ���.");

            
            
            
            // ����ȸ���϶� ------------------------
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

            
            //����ȸ�������϶� �����
            business_1.setEnabled(true);
            business_2.setEnabled(true);
            bankBox.setEnabled(true);
            bankNumber_T.setEnabled(true);
            
            //ī���ȣ �Ⱦ��̱⶧���� ��Ȱ��ȭ
            cardNumber_T1.setEnabled(false);
            cardNumber_T2.setEnabled(false);
            cardNumber_T3.setEnabled(false);
            cardNumber_T4.setEnabled(false);
            cardNumber_T1.setText("    �Ϲ�");
            cardNumber_T2.setText("   ȸ����");
            cardNumber_T3.setText("   �Է���");
            cardNumber_T4.setText("   �ּ���");

            //�Է��� �� �ְ� ����α�
            if(!e.getSource().equals(check) && !e.getSource().equals(idCheck)) {
            business_1.setText("");
            business_2.setText("");
            bankNumber_T.setText("");
            }
         } 
         

         // �޺��ڽ��� �ִ� �̸����� ������ �̸��� �ι�° â�� �ٷ� �ԷµǴ� �׼� ������
         if (e.getSource().equals(emailbox)) { // �̸����� �������

            // �����Է� �ܿ� �̸��� �Է�������
            if (!((String) emailbox.getSelectedItem()).equals("�����Է�")) {
               email_2.setText((String) emailbox.getSelectedItem()); // �ؽ�Ʈâ�� �̸��� ���

            } else { // ���� �Է��� ��� �ؽ�Ʈâ ����
               email_2.setText("");
            }

         }

         // 1) ��ҹ�ư �������� ------------------------------------
         if (e.getSource().equals(cancellation)) {

            signUpEnd = false;  
            lg.SignUp = null;
            dispose();

            // 2) ���̵� �ߺ�Ȯ�� ��ư ��������----------------------------------------
         } else if (e.getSource().equals(idCheck)) {

            // �Ϲ�ȸ�� �ߺ�Ȯ�� -----------------------------------------
            if (nomalUserKind.isSelected()) {

               // ���̵��ؽ�Ʈ â�� ��������� -- ���̵� �Է����ּ���
               if (id_t.getText().isEmpty()) {
                  JOptionPane.showMessageDialog(null, " ���̵� �Է����ּ���. ", "ȸ������ ", JOptionPane.WARNING_MESSAGE);
                  idChkBl = false;

                  // ����ڰ� �Է��� ���̵�� UserDB�� id�� ������ -- �ߺ����� �˸�
               } else if (id_t.getText().equals(UserDB.getID(id))) {
                  JOptionPane.showMessageDialog(null, " �̹� ������� ���̵��Դϴ�. ", "ȸ������ ", JOptionPane.WARNING_MESSAGE);
                  id_t.setText("");
                  idChkBl = false;

                  // ���̵� ��ȿ�� �˻� --> �����϶� XX
               } else if (!(Pattern.matches(idRegular, id_t.getText()))
                     || !(Pattern.matches(sRegular, id_t.getText()))) {
                  JOptionPane.showMessageDialog(null, "���̵�� ������� 5~9�� �̳��� ����/���ڸ� ��밡���մϴ�.");
                  id_t.setText("");

                  // �������� �� �ƴ϶�� ��밡�� !!
               } else {
                  idChkBl = true;
                  JOptionPane.showMessageDialog(null, " ��밡���� ���̵��Դϴ�. ");
               }

               // ����ȸ�� �ߺ�Ȯ��----------------------------------------------
            } else if (sellerUserKind.isSelected()) {
               System.out.println("������");

               // ���̵��ؽ�Ʈ â�� ��������� -- ���̵� �Է����ּ���
               if (id_t.getText().trim().isEmpty()) {
                  JOptionPane.showMessageDialog(null, " ���̵� �Է����ּ���. ", "ȸ������ ", JOptionPane.WARNING_MESSAGE);
                  idChkBl = false;

                  // ����ڰ� �Է��� ���̵�� UserDB�� id�� ������ -- �ߺ����� �˸�
               } else if (id_t.getText().equals(UserDB.getID(id))) {
                  JOptionPane.showMessageDialog(null, " �̹� ������� ���̵��Դϴ�. ", "ȸ������ ", JOptionPane.WARNING_MESSAGE);
                  idChkBl = false;
                  id_t.setText("");

                  // ���̵� ��ȿ�� �˻� --> �����϶� XX
               } else if (!(Pattern.matches(idRegular, id_t.getText().trim()))
                     || !(Pattern.matches(sRegular, id_t.getText().trim()))) {
                  JOptionPane.showMessageDialog(null, "���̵�� ������� 5~9�� �̳��� ����/���ڸ� ��밡���մϴ�.");
                  id_t.setText("");

                  // �������� �� �ƴ϶�� ��밡�� !!
               } else {
                  idChkBl = true;
                  JOptionPane.showMessageDialog(null, " ��밡���� ���̵��Դϴ�. ");
               }

            }

            // 3) Ȯ�� ��ư �������� ------------------------------------
         } else if (e.getSource().equals(check)) {

            // ��ĭ�� ������ �ؽ�Ʈchk�� false�� / �Է��� �������� true
            for (int i = 0; i < textFieldList_2.size(); i++) {
               if (textFieldList_2.get(i).equals("")) {
                  textChk = false;
                  break;
               } else {
                  textChk = true;
               }
            }

            System.out.println(textFieldList_2);


            // �Ϲ�ȸ�� ��ȿ�� �˻�-------------------------------------------------------------------------------
            if (nomalUserKind.isSelected()) {
               System.out.println("üũ!");

               // �ؽ�Ʈ�ʵ�â ���� Ȯ��
               //System.out.println(textFieldList.size()); 
               System.out.println(textFieldList_2.size());

               // ������ �������� ����,���� Ȯ��
               if (man_bt.isSelected()) {
                  System.out.println("����");
                  gender = "����";

               } else if (woman_bt.isSelected()) {
                  System.out.println("����");
                  gender = "����";

                  // ����� ��й�ȣ�� ����� ��й�ȣ Ȯ���� ���� ������ -- �ٽ� Ȯ���ش޶�� â�� ���.
               }
               if (!(pw_t.getText().equals(passWord.getText()))) {

                  JOptionPane.showMessageDialog(null, "��й�ȣ�� �ٽ� Ȯ���� �ּ���.", "ȸ������", JOptionPane.WARNING_MESSAGE);

                  
                  // �ؽ�Ʈ�ʵ� â ������ 12������ ���� �Է������� --- ȸ������ �Է��϶�� â ���.
               } else if (textChk == false) {
                  System.out.println("ȸ������");
                  JOptionPane.showMessageDialog(null, "ȸ�������� �Է����ּ���.", "ȸ������", JOptionPane.WARNING_MESSAGE);

                  
                  // ���̵� �ߺ�üũ�� �����ʰ� ȸ�������ϴ°��� ���� -- �ߺ�Ȯ���� ������ �ʰ� ȸ������Ȯ���� ������ �ߺ�Ȯ���� �ش޶�� â�� ���.
               } else if (idChkBl == false) {

                  JOptionPane.showMessageDialog(null, "�ߺ�Ȯ���� ���ּ���.", "ȸ������", JOptionPane.WARNING_MESSAGE);


                  // ��й�ȣ ��Ʈ�� ���� ������ �� true
               } else if (hintChk == true) {
                  JOptionPane.showMessageDialog(null, "��й�ȣ ��Ʈ�� ������ �ּ���", "ȸ������", JOptionPane.WARNING_MESSAGE);
                  
                  
                  // ��������� ���� ������ �� true
               } else if (dayChk == true) {
                  JOptionPane.showMessageDialog(null, "��������� ������ �ּ���.", "ȸ������", JOptionPane.WARNING_MESSAGE);
                  
                  
                  // ������ ���� ������ �� true
               } else if (gender == "") {
                  JOptionPane.showMessageDialog(null, "������ ������ �ּ���.", "ȸ������", JOptionPane.WARNING_MESSAGE);

                  
               
               }else if(nomalUserKind.isSelected() && cnt_n != 11) {
                  JOptionPane.showMessageDialog(null, "�ùٸ� �������� �ٽ� �Է��� �ּ���.", "ȸ������", JOptionPane.WARNING_MESSAGE);
        
                  // �� ������ �ƴҰ�� �������ǿ� �����Ǿ� ���ԿϷ� â�� ���.
               }else  {
                  System.out.println(textFieldList.size());
                  UserDB.signupNOMALUSER(id, pwChkStr, name, gender, birthYYYYMMDD, phone, email, address, card,
                        pwhint, pwres, coin);
                  MenuDB.makeMENU(id);
                  JOptionPane.showMessageDialog(null, "          ȸ������ �Ϸ�! \n���忡 ���Ű��� ȯ���մϴ�.");
                  
                  lg.SignUp = null;
                  signUpEnd = false;
                  dispose();
               }

               // ����ȸ�� ��ȿ��
               // �˻�-------------------------------------------------------------------------------

            } else if (sellerUserKind.isSelected()) {
               System.out.println("üũ!");

               // �ؽ�Ʈ�ʵ�â ���� Ȯ��
               // System.out.println(textFieldList.size());

               // ������ �������� ����,���� Ȯ��
               if (man_bt.isSelected()) {
                  System.out.println("����");
                  gender = "����";

               } else if (woman_bt.isSelected()) {
                  System.out.println("����");
                  gender = "����";

                  // ����� ��й�ȣ�� ����� ��й�ȣ Ȯ���� ���� ������ -- �ٽ� Ȯ���ش޶�� â�� ���.
               }
               if (!(pw_t.getText().equals(passWord.getText()))) {
                  JOptionPane.showMessageDialog(null, "��й�ȣ�� �ٽ� Ȯ�����ּ���.", "ȸ������", JOptionPane.WARNING_MESSAGE);

                  // �ؽ�Ʈ�ʵ� â ������ 12������ ���� �Է������� --- ȸ������ �Է��϶�� â ���. 17��
               } else if (textChk == false) {
                  System.out.println("ȸ������");
                  JOptionPane.showMessageDialog(null, "ȸ�������� �Է����ּ���.", "ȸ������", JOptionPane.WARNING_MESSAGE);

                  // ���̵� �ߺ�üũ�� �����ʰ� ȸ�������ϴ°��� ���� -- �ߺ�Ȯ���� ������ �ʰ� ȸ������Ȯ���� ������ �ߺ�Ȯ���� �ش޶�� â�� ���.
               } else if (idChkBl == false) {
                  JOptionPane.showMessageDialog(null, "�ߺ�Ȯ���� ���ּ���.", "ȸ������", JOptionPane.WARNING_MESSAGE);

                  // ��й�ȣ ��Ʈ�� ���� ������ �� true
               } else if (hintChk == true) {
                  JOptionPane.showMessageDialog(null, "��й�ȣ ��Ʈ�� �������ּ���", "ȸ������", JOptionPane.WARNING_MESSAGE);
                  
                  
                  //������ ���� �������� true
               } else if (bankChk == true) {
                  JOptionPane.showMessageDialog(null, "������ ������ �ּ���.", "ȸ������", JOptionPane.WARNING_MESSAGE);
                  
                  // ��������� ���� ������ �� true
               } else if (dayChk == true) {
                  JOptionPane.showMessageDialog(null, "��������� �������ּ���.", "ȸ������", JOptionPane.WARNING_MESSAGE);
                  
                  // ������ ���� ������ �� true
               } else if (gender == "") {
                  JOptionPane.showMessageDialog(null, "������ �������ּ���.", "ȸ������", JOptionPane.WARNING_MESSAGE);
                  
                  
               }else if(sellerUserKind.isSelected() && cnt_s != 10) {
                  JOptionPane.showMessageDialog(null, "�ùٸ� �������� �ٽ� �Է��� �ּ���.", "ȸ������", JOptionPane.WARNING_MESSAGE);
                 
                  
                  // �� ������ �ƴҰ�� �������ǿ� �����Ǿ� ���ԿϷ� â�� ���.
               } else {
                  System.out.println(textFieldList.size());
                  UserDB.signupSELLERUSER(id, pwChkStr, name, gender, birthYYYYMMDD, phone, email, address,
                        pwhint, pwres, businessname, businessaddress, banknum, coin);
                  MenuDB.makeMENU(id);
                  JOptionPane.showMessageDialog(null, "           ȸ������ �Ϸ� !\n���忡 ���Ű��� ȯ���մϴ�.");
                  
                  //������ ����!!!!!
                  signUpEnd = false;  
                  lg.SignUp = null;
                  dispose();

               }

            }
         }

      }

      // ��ȿ�� �˻� ������
      class SignUpChk extends Thread {



         @Override
         public void run() {
            while (signUpEnd) {
               // ��й�ȣ ��ȿ�� �˻� --> ����,����, Ư������ ���� (���� ����) ---------------------------------
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

                  //��й�ȣ == ��й�ȣ Ȯ��  ��ġ���� �˻�!!
               }if(!(pw_t.getText().equals(passWord.getText()))) {
                  
                  passWord.setForeground(Color.red);
                  
                  
               }else {
                
                  passWord.setForeground(Color.black);
                  
                  
                  // �̸� ��ȿ�� �˻� --> �ѱ۸� ��밡�� --------------------------------
               }if ((!(Pattern.matches(nameRegular, name_t.getText())))
                     || !(Pattern.matches(sRegular, name_t.getText())) && !name_t.getText().equals("")) {
                  regularChk_N[1] = false;
                  regularChk_S[1] = false;
                  name_t.setForeground(Color.red);

               } else {
                  regularChk_N[1] = true;
                  regularChk_S[1] = true;
                  name_t.setForeground(Color.black);

                  // �̸��� ��ȿ�� �˻� --> ��ҹ��� /���� --------------------------------
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

                  // �ڵ�����ȣ ��ȿ�� �˻� --> ���ڼ� 3~4�� ����, ���ڸ� ���� --------------------------------
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

                  // ī���ȣ ��ȿ�� �˻� --> ���ڼ� 4�� ����, ���ڸ� ���� ..--------------------------------
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

                  
               }// �ּҴ� �ѱ�/����/���ڸ� ����!!(�ʼ�����)
               if ((!(Pattern.matches(addressRegular, address_t.getText().trim().replaceAll(" ", ""))))
                      && !address_t.getText().equals("")) {
                  regularChk_N[10] = false;
                  regularChk_S[6] = false;
                  address_t.setForeground(Color.red);

               } else {
                  regularChk_N[10] = true;
                  regularChk_S[6] = true;
                  address_t.setForeground(Color.black);
                  
               }//������
               if ((!(Pattern.matches(businessName_R, business_1.getText().trim().replaceAll(" ", ""))))
                     && !business_1.getText().equals("") && sellerUserKind.isSelected()) {

                  regularChk_S[7] = false;
                  business_1.setForeground(Color.red);
                  
               } else {
                  regularChk_S[7] = true;
                  business_1.setForeground(Color.black);
               }
               //������ּ�
               if ((!(Pattern.matches(addressRegular, business_2.getText().trim().replaceAll(" ", ""))))
                     && !business_2.getText().equals("")&& sellerUserKind.isSelected()) {
                  regularChk_S[8] = false;
                  business_2.setForeground(Color.red);
                  
               } else {
                  regularChk_S[8] = true;
                  business_2.setForeground(Color.black);
               
                  //���¹�ȣ
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
         // TODO �ڵ� ������ �޼ҵ� ����
         
      }



      @Override
      public void windowClosing(WindowEvent e) {
         lg.SignUp = null;
         signUpEnd = false;
         
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

//             if(idChkBl == true && pwChkBl == true && textFieldList_2.size() == 12)
   }

   

}



public class LoginFrame {

   public static void main(String[] args) {

      new Login(); // �α���
//      new MemberChoice(); //ȸ�� �з� â
//      new Signup(); //�Ϲ� ȸ������ â
//      new SignUp(); //����� ȸ������ â
//      new IDsearch();  //���̵� ã��
//      new PWsearch();  //��й�ȣ ã��
//      new CardChoice(); //ī��� ����
//      new NewPassWord(); //���ο� ��й�ȣ �Է� â

   }

}