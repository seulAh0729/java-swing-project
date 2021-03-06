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

/*--------------------필수사항-----------------------------

1. 식별 할 수 있는 번호 기입!!!!

2.회원들이 가입한 정보를 (ID,PW,이름 등등..)  클래스로 일괄처리 to Sting 으로 return 해서 DB에 보낼것 

 */

/*--------------------해야할 것--------------------------

1. 회원가입 인적사항 창을 비워뒀을때 - '@@@은 필수 입력 사항입니다' 창 띄우기  --o
2. 아이디 중복확인 버튼을 눌렀을때 - '사용 가능한 아이디 입니다.'--기본 메세지창 , '이미 사용중인 아이디 입니다' --경고 메세지창  --o
3. JCombox index 0번째 적용안되게 만들기. ---o
4. 새로운 비밀번호 입력창에서 아이디 / 아이디+비밀번호 힌트 답 이 맞지 않을 경우 '정보가 일치하지 않습니다.' --경고 메세지창  --o
5. 로그인 id와 pw가 일치하지 않을경우 '가입하지 않은 아이디이거나, 잘못된 비밀번호입니다.' -- 경고 메세지창   --o
6. 이메일 직접입력 텍스트 안띄우게 해놓기. --o
7. 회원가입 -> 비밀번호 ,비밀번호 확인 두개가 일치하지않으면 회원가입 확인 눌렀을때 '비밀번호를 다시 확인 해주세요' 창 띄우기. ---o
8. 전화번호 ,카드번호 텍스트필드 글자수 제한!!!!!  ---o
9. 쓰레드 종료 --o
10. 창제어
*/

//로그인 창
class Login extends JFrame implements ActionListener {

   Login me = this;  
//   Signup signUp;
   // String userID = "admin";

   // 아이디 ,비밀번호 텍스트창
   JTextField idtxt;
   JPasswordField pwtxt;

   // 라벨
   JLabel idLabel; // 아이디
   JLabel pwLabel; // 비밀번호

   // 버튼
   JButton login_bt; // 로그인
   JButton idSearch_bt; // 아이디찾기
   JButton pwSearch_bt; // 비밀번호 찾기
   JButton signUp_Bt; // 회원가입

   // 액션리스너
   IDsearch idSearchFrame; // 아이디찾기 프레임클래스
   PWsearch pwSearchFrame; // 비번찾기 프레임클래스
//   MemberChoice memberChoiceFrame; // 일반,점술 회원 구분 프레임 클래스
   
   SignUp SignUp; // 회원가입 프레임 클레스 
   
   
   
   
   boolean signUpEnd = true;

   // 로그인 메소드 ---------------------------------------
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
      // 아이디
      idLabel = new JLabel("아이디");
      idLabel.setBounds(60, 400, 100, 50);
      idLabel.setFont(new Font("굴림체", Font.BOLD, 15));
      add(idLabel);

      // 아이디 입력 칸
      idtxt = new JTextField();
      idtxt.setBounds(130, 400, 200, 50);
      add(idtxt);
      // idtxt.setOpaque(false); //텍스트 상자 투명하게 만들기
      // idtxt.setBorder(javax.swing.BorderFactory.createEmptyBorder()); //뒷배경 투명하게
      // 만들기 (테두리 삭제)

      // 패스워드
      pwLabel = new JLabel("비밀번호");
      pwLabel.setBounds(60, 470, 300, 50);
      pwLabel.setFont(new Font("굴림체", Font.BOLD, 15));
      add(pwLabel);

      // 패스워드 입력
      pwtxt = new JPasswordField();
      pwtxt.setBounds(130, 470, 200, 50);
      add(pwtxt);

      // 로그인
      login_bt = new JButton("로그인");
      login_bt.setBounds(350, 407, 100, 100);
      add(login_bt);
      login_bt.addActionListener(this);

      // id찾기 --액션
      idSearch_bt = new JButton("아이디 찾기");
      idSearch_bt.setBounds(60, 560, 120, 40);
      add(idSearch_bt);
      idSearch_bt.addActionListener(this);
      
      

      // pw찾기 --액션
      pwSearch_bt = new JButton("비밀번호 찾기");
      pwSearch_bt.setBounds(190, 560, 120, 40);
      add(pwSearch_bt);
      pwSearch_bt.addActionListener(this);

      // 회원가입 --액션
      signUp_Bt = new JButton("회원가입");
      signUp_Bt.setBounds(320, 560, 130, 40);
      add(signUp_Bt);
      signUp_Bt.addActionListener(this);

      System.out.println(idtxt);
      setVisible(true);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

   }

   @Override
   public void actionPerformed(ActionEvent e) {

      // 아이디찾기 버튼 눌렀을때
      if (e.getSource().equals(idSearch_bt)) {
         if(idSearchFrame == null) {
            idSearchFrame = new IDsearch(this);
         }
         // 비밀번호 찾기 버튼 눌렀을때
      } else if (e.getSource().equals(pwSearch_bt)) {
         if(pwSearchFrame==null) {
            pwSearchFrame = new PWsearch(this);   
         }

         // 회원가입 버튼 눌렀을때 -> 일반,점술회원 구분프레임 띄우기
      } else if (e.getSource().equals(signUp_Bt)) {
//         memberChoiceFrame = new MemberChoice();
         if(SignUp==null) {
            SignUp = new SignUp(this);
         }

         // 로그인 버튼 눌렀을때
      } else if (e.getSource().equals(login_bt)) {
       
         if (idtxt.getText().isEmpty() || pwtxt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 입력해주세요.  ", "로그인 오류", JOptionPane.WARNING_MESSAGE);

            // 아이디와 비밀번호가 일치하지 않을 경우
         } else if (!(UserDB.getPW(idtxt.getText()).equals(pwtxt.getText()))) {
            JOptionPane.showMessageDialog(null, "아이디와 비밀번호가 맞지 않습니다.");

            //중복 로그인일 경우
         }else if (UserDB.getLOGINCHK(idtxt.getText()).equals("true")) {
           JOptionPane.showMessageDialog(null, "같은아이디로 로그인중입니다.");

              // 다 아니면 로그인 성공!
         } else {
           JOptionPane.showMessageDialog(null, "            로그인 성공! \n점톡에 오신 것을 환영합니다 ♥");
           dispose();
           UserDB.setLOGINCHK(idtxt.getText(), "true");
           new MainFrame(idtxt.getText());

        }
      }

   }
   



   // 아이디찾기
   class IDsearch extends JFrame implements ActionListener, WindowListener{

      // 이름/전화번호 라벨
      JLabel idName_s;
      JLabel idPhone_s;

      // 이름/전화번호 텍스트창
      JTextField idName_t;
      JTextField idPhone_t;
      JTextField idPhone_t2;

      // 아이디찾기 /취소
      JButton idChk_s;
      JButton idCancellation;

      Vector<String> phoneNumber;
      JComboBox<String> pnBox;
      
      // UserDB
      String userNAME; 
      String userPHONE;
      
      String resID;

      Login lg;

      // 아이디 찾기 프레임 클래스
//      ResID resIDFrame;

      public IDsearch(Login lg) {
         setTitle("아이디 찾기");
         setBounds(600, 230, 500, 600);
         setLayout(null);
         this.lg = lg;

         // 이름 입력창
         idName_s = new JLabel("이름");
         idName_s.setBounds(225, 110, 100, 100);
         add(idName_s);
         idName_t = new JTextField();
         idName_t.setBounds(165, 190, 155, 40);
         add(idName_t);

         // 전화번호 입력창
         idPhone_s = new JLabel("전화번호");
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

         // 핸드폰번호 짝대기
         idPhone_s = new JLabel("─");
         idPhone_s.setBounds(169, 315, 30, 30);
         add(idPhone_s);
         idPhone_s = new JLabel("─");
         idPhone_s.setBounds(287, 315, 30, 30);
         add(idPhone_s);

         // 핸드폰번호 중간, 뒷자리
         idPhone_t = new JTextField();
         idPhone_t.setBounds(190, 310, 90, 40);
         add(idPhone_t);
         idPhone_t2 = new JTextField();
         idPhone_t2.setBounds(310, 310, 90, 40);
         add(idPhone_t2);

         // 아이디찾기 버튼
         idChk_s = new JButton("아이디 찾기");
         idChk_s.setBounds(100, 430, 120, 50);
         add(idChk_s);
         idChk_s.addActionListener(this);

         // 취소버튼
         idCancellation = new JButton("취소");
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
         
         if (e.getSource().equals(idChk_s)) { // 아이디찾기 버튼 눌렀을때
            
            if(resID.equals("")) {
               JOptionPane.showMessageDialog(null, "아이디와 전화번호가 일치하지 않습니다 !  ", "아이디 찾기", JOptionPane.ERROR_MESSAGE);
               idName_t.setText("");
               idPhone_t.setText("");
               pnBox.setSelectedIndex(0);
               idPhone_t2.setText("");
               
               
            }else {
               
               JOptionPane.showMessageDialog(null, "회원님의 아이디는   "+resID+"   입니다.", "아이디 찾기",  JOptionPane.INFORMATION_MESSAGE);
               lg.idSearchFrame = null; 
               dispose();
              
               
            }
//            if(resIDFrame==null) {
//               resIDFrame = new ResID();
//            }
//            lg.idSearchFrame = null;

         } else if (e.getSource().equals(idCancellation)) { // 취소 버튼 눌렀을때

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

   // 비밀번호 찾기
   class PWsearch extends JFrame implements ActionListener , WindowListener{

      JLabel idSearch; // 아이디 입력
      JLabel pwHint_S; // 비밀번호 힌트
      JLabel pwAnswer_S; // 비밀번호 답

      JTextField idSearch_t; // 아이디 텍스트창
      JTextField pwSearch_t; // 비밀번호 텍스트창
      Vector<String> passWordHint_S; // 비밀번호 힌트
      JComboBox<String> hintBox_S; // 비밀번호 힌트 comboBox
      
      //DB
      String userID;
      String userPWHINT;
      String userPWRES;
      
      String resPW;

      JButton passWordChk; // 비밀번호 찾기
      JButton pwCancellation; // 취소 버튼

      // 액션리스너
      
      Login lg; // 부른애
      public PWsearch(Login lg) {
         this.lg=lg;
         setTitle("비밀번호 찾기");
         setBounds(600, 230, 500, 600);
         setLayout(null);

         // 아이디 입력칸
         idSearch = new JLabel("아이디");
         idSearch.setBounds(225, 70, 100, 100);
         add(idSearch);
         idSearch_t = new JTextField();
         idSearch_t.setBounds(165, 150, 155, 40);
         add(idSearch_t);

         // 비밀번호 힌트 칸
         pwHint_S = new JLabel("비밀번호 힌트");
         pwHint_S.setBounds(205, 220, 100, 30);
         add(pwHint_S);

         passWordHint_S = new Vector<String>();
         passWordHint_S.add("원하는 질문을 선택하세요.");
         passWordHint_S.add("가장 기억에 남는 장소는?");
         passWordHint_S.add("나의 보물 1호는?");
         passWordHint_S.add("본인의 출생지는?");
         passWordHint_S.add("내가 존경하는 인물은?");
         passWordHint_S.add("나의 좌우명은?");
         passWordHint_S.add("가장 감명깊게 본 영화는?");
         passWordHint_S.add("내가 좋아하는 뮤지션은?");
         passWordHint_S.add("인상깊게 읽은 책 제목은?");
         passWordHint_S.add("나의 노래방 애창곡은?");

         hintBox_S = new JComboBox<String>(passWordHint_S);
         hintBox_S.setBounds(130, 260, 230, 40);
//         hintBox_S.setSelectedItem("원하는 질문을 선택하세요."); // 다음으로 고정
         add(hintBox_S);

         // 비밀번호 힌트 답 입력 칸
         pwAnswer_S = new JLabel("비밀번호 힌트 답");
         pwAnswer_S.setBounds(200, 320, 230, 40);
         add(pwAnswer_S);

         pwSearch_t = new JTextField();
         pwSearch_t.setBounds(130, 370, 240, 40);
         add(pwSearch_t);

         // 비밀번호 찾기
         passWordChk = new JButton("비밀번호 찾기");
         passWordChk.setBounds(100, 470, 120, 50);
         add(passWordChk);
         passWordChk.addActionListener(this);

         // 취소버튼
         pwCancellation = new JButton("취소");
         pwCancellation.setBounds(280, 470, 100, 50);
         add(pwCancellation);
         pwCancellation.addActionListener(this);
         addWindowListener(this);
         
         setResizable(false);
         setVisible(true);

      }

      @Override
      public void actionPerformed(ActionEvent e) {

         if (e.getSource().equals(passWordChk)) { // 비밀번호찾기 버튼 눌렀을때
           
            userID = idSearch_t.getText();
            userPWHINT = hintBox_S.getSelectedItem().toString();
            userPWRES = pwSearch_t.getText();
            resPW = UserDB.searchPW(userID, userPWHINT, userPWRES);
            
            
            if (UserDB.searchPW(userID, userPWHINT, userPWRES).equals("")) {
        
               JOptionPane.showMessageDialog(null, "아이디와 비밀번호 힌트가 일치하지 않습니다 !", "비밀번호 찾기", JOptionPane.ERROR_MESSAGE);
               idSearch_t.setText("");
               pwSearch_t.setText("");
               hintBox_S.setSelectedIndex(0);
               
               
            
            } else {
            
               JOptionPane.showMessageDialog(null, "회원님의 아이디는   "+resPW+"   입니다.", "아이디 찾기",  JOptionPane.INFORMATION_MESSAGE);
               lg.pwSearchFrame = null;
               dispose();
//            passWordFind = new PassWordFind(this);
//            if(!passWordFind.chk) {
//               lg.pwSearchFrame = null;
//               passWordFind =null;
//            }
//            dispose();

            }
            
           }else if(e.getSource().equals(pwCancellation)) { // 취소 버튼 눌렀을때
            
            lg.pwSearchFrame = null;
            dispose();

         }

      }



      @Override
      public void windowOpened(WindowEvent e) {
         // TODO 자동 생성된 메소드 스텁
         
      }

      @Override
      public void windowClosing(WindowEvent e) {
         lg.pwSearchFrame = null;
         
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

   // 일반+사업자 회원가입
   class SignUp extends JFrame implements ActionListener ,WindowListener{

//    ArrayList<Boolean> regularChk = new ArrayList<Boolean>();
      
     boolean [] regularChk_N = new boolean[11]; //일반회원 정규식 체크
     boolean [] regularChk_S = new boolean[10]; //점술회원 정규식 체크 
     
//     boolean regularAllChk = false;
     
      
      // 회원분류 버튼
      ButtonGroup bg; // 버튼그룹
      JToggleButton nomalUserKind; // 일반회원버튼
      JToggleButton sellerUserKind; // 점술회원버튼
      JPanel kindButtonPanel; // 버튼패널
      JLabel idChkLabel;

      
      // 라벨
      JLabel id_L; // 아이디
      JLabel pw_L; // 비밀번호
      JLabel passWord_L; // 비밀번호 확인
      JLabel pwHint_L; // 비밀번호 힌트
      JLabel pwHintAnswer_L; // 비밀번호 힌트 답
      JLabel name_L; // 이름
      JLabel gender_L; // 성별
      JLabel birthDate_L; // 생년월일
      JLabel phoneNumber_L; // 휴대폰번호
      JLabel address_L; // 주소
      JLabel email_L; // 이메일
      JLabel business_L; // 사업장명
      JLabel businessPlace_L; // 사업장 주소
      JLabel bankNumber; //계좌번호
      JLabel pwchk_L ;  //비밀번호 체크 라벨
      // 라벨끼리 묶음
      ArrayList<JLabel> information;
      
      
       //부연설명 라벨
       JLabel idForm_L; // 아이디 설명
       JLabel pwForm_L; //비밀번호 설명
       JLabel bankNumBer_L; // 주소 설명 

      // 버튼
      JButton check; // 확인
      JButton cancellation; // 취소


      // 어레이리스트에 텍스트필드를 추가하고 빈칸일 경우 다시 입력하라는 창 띄우기.
      ArrayList<JTextField> textFieldList = new ArrayList<JTextField>();
      ArrayList<String> textFieldList_2 ;

      // 텍스트 창
      JTextField id_t; // 아이디
      JPasswordField pw_t; // 비밀번호
      JPasswordField passWord; // 비밀번호 확인
      JTextField pwHintAnswer; // 비밀번호 힌트 답
      JTextField name_t; // 이름
      JTextField pnNum1; // 핸드폰번호 가운데
      JTextField pnNum2; // 핸드폰번호 뒷자리
      JTextField address_t; // 주소
      JTextField email_1; // 이메일 앞자리
      JTextField email_2; // 이메일 뒷자리
      JTextField business_1; // 사업장명
      JTextField business_2; // 사업장주소

      // 아이디 중복확인 버튼
      JButton idCheck;

      ButtonGroup gender_bt = new ButtonGroup();// 성별
      JRadioButton man_bt; // 남자버튼
      JRadioButton woman_bt; // 여자버튼

      // 비밀번호 힌트
      Vector<String> passWordHint;
      JComboBox<String> hintBox;

      // 년도
      Vector<String> year;
      JComboBox<String> yearBox;
      JLabel yearLabel;

      // 월
      Vector<String> month;
      JComboBox<String> monthBox;
      JLabel monthLabel;

      // 일
      Vector<String> day;
      JComboBox<String> dayBox;
      JLabel dayLabel;

      // 휴대폰번호 앞
      Vector<String> phoneNumber;
      JComboBox<String> pnBox;
      JLabel pnLabel;
      JLabel pnLabe2;

      // 휴대폰번호 통신사
//         ButtonGroup mobile_bt = new ButtonGroup();
//         JCheckBox mobileCompany1;
//         JCheckBox mobileCompany2;
//         JCheckBox mobileCompany3;

      // 이메일
      Vector<String> email_V; // 이메일 종류
      JComboBox<String> emailbox;

      // 카드번호 텍스트창
      JButton cardCompany; // 카드이름 버튼
//      JTextField cardNum_1;
//      JTextField cardNum_2;
//      JPasswordField cardNum_3;
//      JPasswordField cardNum_4;
      JLabel cardNum_L1;
      JLabel cardNum_L2;
      JLabel cardNum_L3;

      // 계좌번호

      JTextField bankNumber_T;

      // 은행선택
      Vector<String> bankChoice;
      JComboBox<String> bankBox;

      // 카드번호
      ArrayList<JTextField> cardNumberArr = new ArrayList<JTextField>();
      JLabel cardNumber;
      JTextField cardNumber_T1;
      JTextField cardNumber_T2;
      JTextField cardNumber_T3;
      JTextField cardNumber_T4;

      boolean idChkBl = false; // 아이디 중복체크

      // 정규식(유효성검사)
 
      String idRegular = "^[a-zA-Z0-9][a-zA-Z0-9]{4,8}$"; // 아이디 유효성 --> 영문 (대소문자)/숫자 5~9자 이내로  --o
      String pwRegular = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{4,19}$"; // 비밀번호 유효성 --> 6~20자 이내의 영문  + 대소문자/숫자/특수문자 반드시 포함   --o                                                         
      String phoneNumRegular = "\\d{3,4}$"; // 전화번호 숫자 4자 이내로
      String emailRegular = "^[0-9a-zA-Z\\.]*"; // 이메일 대소문자/숫자  앞
      String emailRegular_2 = "^[a-zA-Z]{2,}.[a-zA-Z]{2,}"; // 이메일 대소문자/숫자   뒤
      String nameRegular = "^[가-힣]*$"; // 이름 한글만 가능
      String cardRegular = "^[0-9]{4,6}$"; // 숫자
      String addressRegular = "^[-a-zA-Z가-힣0-9]*$"; // 주소
      String businessName_R = "^[a-zA-Z가-힣0-9]*$";  //사업장명 
      String businessAddress_R = "^[0-9-]*$"; //계좌번호
      String sRegular = "\\S*"; // 공백

      SignUpChk signUpChk;
      
      Login lg;
      public SignUp(Login lg) {
         this.lg = lg;
         setTitle("점톡 회원가입 ");
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

         nomalUserKind = new JToggleButton("일반", true);
         nomalUserKind.addActionListener(this);
         kindButtonPanel.add(nomalUserKind);

         sellerUserKind = new JToggleButton("점술가");
         sellerUserKind.addActionListener(this);
         kindButtonPanel.add(sellerUserKind);

         bg = new ButtonGroup();
         bg.add(nomalUserKind);
         bg.add(sellerUserKind);

         add(kindButtonPanel);

         // 아이디
         id_L = new JLabel("아이디 ");
//         id_L.setBounds(20,90,50,30);  
         information.add(id_L);

         // 아이디 텍스트창
         id_t = new JTextField();
         id_t.setBounds(140, 93, 200, 40);
         add(id_t);
         textFieldList.add(id_t);

         // 아이디 중복확인 버튼
         idCheck = new JButton("중복확인");
         idCheck.setBounds(350, 95, 110, 35);
         add(idCheck);
         idCheck.addActionListener(this);

         //아이디 설명 라벨
           idForm_L = new JLabel("*공백없이 5~9자 이내의 영문/숫자만 사용가능");
           idForm_L.setBounds(472,93,300,35);
           add(idForm_L);
         
         // 비밀번호
         pw_L = new JLabel("비밀번호 ");
//         pw_L.setBounds(20,145,100,30);  
         information.add(pw_L);

         // 비밀번호 텍스트창
         pw_t = new JPasswordField();
         pw_t.setBounds(140, 148, 200, 40);
         add(pw_t);
         
         //비밀번호 설명 라벨
           pwForm_L = new JLabel("*공백없이 5~9자 이내의 영문 대소문자/숫자/특수문자 반드시 포함 ");
           pwForm_L.setBounds(360,155,600,35);
           add(pwForm_L);
         

         // 비밀번호 확인
         passWord_L = new JLabel("비밀번호 확인");
//         passWord_L.setBounds(20,200,100,30);  
         information.add(passWord_L);

         // 비밀번호 확인 텍스트창
         passWord = new JPasswordField();
         passWord.setBounds(140, 203, 200, 40);
         add(passWord);
         
//         // 비밀번호 체크 라벨
//         pwchk_L = new JLabel("비밀번호가 일치하지 않습니다.");
//         pwchk_L.setBounds(360,203,200,40);
//         add(pwchk_L);
         
         // 비밀번호 힌트
         pwHint_L = new JLabel("비밀번호 힌트");
//         pwHint_L.setBounds(20,255,100,30);  
         information.add(pwHint_L);

         // 비밀번호 힌트 내용
         passWordHint = new Vector<String>();
         passWordHint.add("원하는 질문을 선택하세요.");
         passWordHint.add("가장 기억에 남는 장소는?");
         passWordHint.add("나의 보물 1호는?");
         passWordHint.add("본인의 출생지는?");
         passWordHint.add("내가 존경하는 인물은?");
         passWordHint.add("나의 좌우명은?");
         passWordHint.add("가장 감명깊게 본 영화는?");
         passWordHint.add("내가 좋아하는 뮤지션은?");
         passWordHint.add("인상깊게 읽은 책 제목은?");
         passWordHint.add("나의 노래방 애창곡은?");

         hintBox = new JComboBox<String>(passWordHint);
         hintBox.setBounds(140, 260, 230, 40);
         // box.setSelectedIndex(2);
//         hintBox.setSelectedItem("원하는 질문을 선택하세요."); // 다음으로 고정
         add(hintBox);

         // 힌트 답
         pwHintAnswer_L = new JLabel("비밀번호 힌트 답");
//         pwHintAnswer_L.setBounds(20,310,100,30);  
         information.add(pwHintAnswer_L);

         // 힌트 답 텍스트창
         pwHintAnswer = new JTextField();
         pwHintAnswer.setBounds(140, 315, 230, 40);
         add(pwHintAnswer);
         textFieldList.add(pwHintAnswer);

         // 이름
         name_L = new JLabel("이름 ");
//         name_L.setBounds(20,365,100,30);  
         information.add(name_L);

         // 이름 텍스트창
         name_t = new JTextField();
         name_t.setBounds(140, 368, 200, 40);
         add(name_t);
         textFieldList.add(name_t);

         // 성별
         gender_L = new JLabel("성별 ");
//         gender_L.setBounds(20,420,100,30);  
         information.add(gender_L);

         // 성별 버튼
         man_bt = new JRadioButton("남자");
         man_bt.setBounds(140, 425, 80, 35);
         gender_bt.add(man_bt);
         add(man_bt);
         man_bt.addActionListener(this);

         woman_bt = new JRadioButton("여자");
         woman_bt.setBounds(270, 425, 80, 35);
         gender_bt.add(woman_bt);
         add(woman_bt);
         woman_bt.addActionListener(this);

         // 생년월일
         birthDate_L = new JLabel("생년월일 ");
//         birthDate_L.setBounds(20,475,100,30);  
         information.add(birthDate_L);

         // 년도
         year = new Vector<String>();
         year.add("년도");
         for (int i = 2018; i >= 1940; i--) {
            year.add(i + "");
         }
         yearLabel = new JLabel("년");
         yearLabel.setBounds(250, 486, 30, 30);
         add(yearLabel);

         yearBox = new JComboBox<String>(year);
         yearBox.setBounds(140, 480, 100, 35);
         yearBox.setSelectedItem("년도");
         add(yearBox);

         // 월
         month = new Vector<String>();
         month.add("월");
         for (int i = 1; i <= 12; i++) {
            if (i < 10) {
               month.add("0" + i);
            } else {
               month.add(i + "");

            }
         }

         monthBox = new JComboBox<String>(month);
         monthBox.setBounds(280, 480, 100, 35);
         monthBox.setSelectedItem("월");
         add(monthBox);

         monthLabel = new JLabel("월");
         monthLabel.setBounds(390, 486, 30, 30);
         add(monthLabel);

         // 일
         day = new Vector<String>();
         day.add("일");

         for (int i = 1; i <= 31; i++) {
            if (i < 10) {
               day.add("0" + i);

            } else {
               day.add(i + "");

            }
         }

         dayBox = new JComboBox<String>(day);
         dayBox.setBounds(420, 480, 100, 35);
         dayBox.setSelectedItem("일");
         add(dayBox);

         dayLabel = new JLabel("일");
         dayLabel.setBounds(530, 486, 30, 30);
         add(dayLabel);

         // 전화번호
         phoneNumber_L = new JLabel("전화번호");
//         phoneNumber_L.setBounds(20,540,100,30);  
         information.add(phoneNumber_L);

         // 핸드폰번호 앞자리
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

         // 핸드폰번호 짝대기
         pnLabel = new JLabel("─");
         pnLabel.setBounds(240, 540, 30, 30);
         add(pnLabel);
         pnLabe2 = new JLabel("─");
         pnLabe2.setBounds(365, 540, 30, 30);
         add(pnLabe2);

         // 핸드폰번호 중간, 뒷자리
         pnNum1 = new JTextField();
         pnNum1.setBounds(265, 535, 90, 40);
         add(pnNum1);
         textFieldList.add(pnNum1);

         pnNum2 = new JTextField();
         pnNum2.setBounds(390, 535, 90, 40);
         add(pnNum2);
         textFieldList.add(pnNum2);

//         //핸드폰 통신사 선택
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

         // 이메일
         email_L = new JLabel("E-mail");
//         email_L.setBounds(20,595,100,30);  
         information.add(email_L);

         // 이메일 텍스트창
         email_1 = new JTextField();
         email_1.setBounds(140, 590, 170, 40);
         add(email_1);
         textFieldList.add(email_1);

         JLabel e1 = new JLabel(" @ ");

         e1.setBounds(320, 593, 120, 35);
         add(e1);

         // 이메일 두번째 텍스트창
         email_2 = new JTextField();
         email_2.setBounds(350, 590, 170, 40);
         add(email_2);
         textFieldList.add(email_2);

         email_V = new Vector<String>(); // email창 목록 생성
         email_V.add("직접입력");
         email_V.add("naver.com");
         email_V.add("nate.com");
         email_V.add("hanmail.net");
         email_V.add("yahoo.co.kr");
         email_V.add("daum.net");
         email_V.add("gmail.com");

         // 이메일 주소
         emailbox = new JComboBox<String>(email_V);
         emailbox.setBounds(540, 590, 160, 35);
         emailbox.setSelectedItem("직접입력"); // 다음으로 고정
         add(emailbox);
         emailbox.addActionListener(this);

         // 주소
         address_L = new JLabel("주소");
         information.add(address_L);

         // 주소 텍스트창
         address_t = new JTextField();
         address_t.setBounds(140, 642, 360, 40);
         add(address_t);
         textFieldList.add(address_t);

         // 사업장명
         business_L = new JLabel("사업장명");
//         cardNumber_L.setBounds(20,650,100,30);  
         information.add(business_L);

         // 사업명장 입력창
         business_1 = new JTextField();
         business_1.setBounds(140, 698, 360, 40);
         add(business_1);
         business_1.setEnabled(false);
//         textFieldList.add(business_1);

         // 사업장 주소
         businessPlace_L = new JLabel("사업장 주소");
         information.add(businessPlace_L);

         // 사업장주소 텍스트창
         business_2 = new JTextField();
         business_2.setBounds(140, 753, 360, 40);
         add(business_2);
         business_2.setEnabled(false);
//         textFieldList.add(business_2);

         // 계좌번호
         bankNumber = new JLabel("계좌번호");
         bankNumber_T = new JTextField();
         information.add(bankNumber);

         // 은행 선택
         bankChoice = new Vector<String>();
         bankChoice.add("은행선택");
         bankChoice.add("기업은행");
         bankChoice.add("농협은행");
         bankChoice.add("신한은행");
         bankChoice.add("산업은행");
         bankChoice.add("우리은행");
         bankChoice.add("씨티은행");
         bankChoice.add("하나은행");
         bankChoice.add("광주은행");
         bankChoice.add("경남은행");
         bankChoice.add("대구은행");
         bankChoice.add("광주은행");
         bankChoice.add("부산은행");
         bankChoice.add("수협은행");
         bankChoice.add("제주은행");
         bankChoice.add("제주은행");
         bankChoice.add("전북은행");
         bankChoice.add("새마을금고");
         bankChoice.add("신협은행");
         bankChoice.add("수협은행");
         bankChoice.add("우체국");

         bankBox = new JComboBox<String>(bankChoice);
         bankBox.setBounds(140, 813, 100, 35);
//         bankBox.setSelectedItem("-은행선택-"); // 다음으로 고정
         add(bankBox);
         bankBox.setEnabled(false);  //비활성화

         // 계좌번호 입력창
         bankNumber_T = new JTextField();
         bankNumber_T.setBounds(250, 812, 250, 40);
         add(bankNumber_T);
         bankNumber_T.setEnabled(false); //비활성화
         
         //계좌번호 설명 라벨 
         bankNumBer_L = new JLabel("'   ―   ' 를 추가해서 입력하세요.");
         bankNumBer_L.setBounds(510,816,200,35);
         add(bankNumBer_L);

//         textFieldList.add(bankNumber_T);

         //카드번호 입력 창
         cardNumber = new JLabel("카드번호");
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

         //카드번호 입력창 어레이 리스트에 담기
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

         // 확인 버튼
         check = new JButton("확인");
         check.setBounds(130, 945, 200, 50);
         add(check);
         check.addActionListener(this);
         
         //취소 버튼 
         cancellation = new JButton("취소");
         cancellation.setBounds(410, 945, 200, 50);
         add(cancellation);
         cancellation.addActionListener(this);

         // 라벨 간격 맞추기
         for (int i = 0; i < information.size(); i++) {
            information.get(i).setBounds(20, i * 55 + 100, 100, 30);

         }

         for (JLabel jL : information) {
            add(jL);
         }

         //일반회원 회원가입창 ---> 사업자명, 계좌번호 부분 setText 해줌
         business_1.setText("\t        점술회원만 입력 해 주세요.");
         business_2.setText("\t        점술회원만 입력 해 주세요.");
         bankNumber_T.setText("                  점술회원만 입력 해 주세요.");

         
         
//         cardNumber_T1.setText("일반");
//         cardNumber_T2.setText("회원만");
//         cardNumber_T3.setText("입력해");
//         cardNumber_T4.setText("주세요");

         
         //실시간 검사중....
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
      
      
      
      //액션리스너 ------------------------------------------------------------
      @Override
      public void actionPerformed(ActionEvent e) {

         boolean textChk = false; //텍스트필드 창 빈칸 확인 
         
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
         
         

         // DB연동
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
         
         //콤보박스 0번째 
         boolean hintChk = hintBox.getSelectedIndex() == 0;
         boolean bankChk = bankBox.getSelectedIndex() == 0;
         boolean dayChk = yearBox.getSelectedIndex() == 0 || monthBox.getSelectedIndex() == 0
               || dayBox.getSelectedIndex() == 0;

         // 일반회원일때 ---------------
         if (nomalUserKind.isSelected()) {
            
            // 회원가입 빈칸여부를 확인해주는 리스트 --갯수 확인하는 곳에서만 써야함
            // 텍스트창이 입력됐을때 추가해주는 리스트 (회원가입 텍스트필드에 입력이 안되면 add안함)
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
            
            
            //점술회원 텍스트필드 창 비활성화 
            business_1.setEnabled(false);
            business_2.setEnabled(false);
            bankBox.setEnabled(false);
            bankNumber_T.setEnabled(false);
            
            //일반회원에서는 열어두고 점술회원버튼 누르면 비활성화가 됨
            cardNumber_T1.setEnabled(true);
            cardNumber_T2.setEnabled(true);
            cardNumber_T3.setEnabled(true);
            cardNumber_T4.setEnabled(true);

            // 회원가입창 들어갔을때 일반회원 카드번호 텍스트 비움
            if(!e.getSource().equals(check) && !e.getSource().equals(idCheck)) {
            cardNumber_T1.setText("");
            cardNumber_T2.setText("");
            cardNumber_T3.setText("");
            cardNumber_T4.setText("");
            }
            
            
            //회원 분류 버튼 여러번 눌러도 계속 생겼다가 지워졌다 함.
            business_1.setText("\t      점술회원만 입력 해 주세요.");
            business_2.setText("\t      점술회원만 입력 해 주세요.");
            bankNumber_T.setText("          점술회원만 입력 해 주세요.");

            
            
            
            // 점술회원일때 ------------------------
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

            
            //점술회원가입일땐 열어둠
            business_1.setEnabled(true);
            business_2.setEnabled(true);
            bankBox.setEnabled(true);
            bankNumber_T.setEnabled(true);
            
            //카드번호 안쓰이기때문에 비활성화
            cardNumber_T1.setEnabled(false);
            cardNumber_T2.setEnabled(false);
            cardNumber_T3.setEnabled(false);
            cardNumber_T4.setEnabled(false);
            cardNumber_T1.setText("    일반");
            cardNumber_T2.setText("   회원만");
            cardNumber_T3.setText("   입력해");
            cardNumber_T4.setText("   주세요");

            //입력할 수 있게 비워두기
            if(!e.getSource().equals(check) && !e.getSource().equals(idCheck)) {
            business_1.setText("");
            business_2.setText("");
            bankNumber_T.setText("");
            }
         } 
         

         // 콤보박스에 있는 이메일을 누르면 이메일 두번째 창에 바로 입력되는 액션 리스너
         if (e.getSource().equals(emailbox)) { // 이메일을 골랐을때

            // 직접입력 외에 이메일 입력했을때
            if (!((String) emailbox.getSelectedItem()).equals("직접입력")) {
               email_2.setText((String) emailbox.getSelectedItem()); // 텍스트창에 이메일 띄움

            } else { // 직접 입력일 경우 텍스트창 비우기
               email_2.setText("");
            }

         }

         // 1) 취소버튼 눌렀을때 ------------------------------------
         if (e.getSource().equals(cancellation)) {

            signUpEnd = false;  
            lg.SignUp = null;
            dispose();

            // 2) 아이디 중복확인 버튼 눌렀을때----------------------------------------
         } else if (e.getSource().equals(idCheck)) {

            // 일반회원 중복확인 -----------------------------------------
            if (nomalUserKind.isSelected()) {

               // 아이디텍스트 창이 비어있을때 -- 아이디 입력해주세요
               if (id_t.getText().isEmpty()) {
                  JOptionPane.showMessageDialog(null, " 아이디를 입력해주세요. ", "회원가입 ", JOptionPane.WARNING_MESSAGE);
                  idChkBl = false;

                  // 사용자가 입력한 아이디와 UserDB에 id와 같으면 -- 중복임을 알림
               } else if (id_t.getText().equals(UserDB.getID(id))) {
                  JOptionPane.showMessageDialog(null, " 이미 사용중인 아이디입니다. ", "회원가입 ", JOptionPane.WARNING_MESSAGE);
                  id_t.setText("");
                  idChkBl = false;

                  // 아이디 유효성 검사 --> 공백일때 XX
               } else if (!(Pattern.matches(idRegular, id_t.getText()))
                     || !(Pattern.matches(sRegular, id_t.getText()))) {
                  JOptionPane.showMessageDialog(null, "아이디는 공백없이 5~9자 이내로 영문/숫자만 사용가능합니다.");
                  id_t.setText("");

                  // 나머지가 다 아니라면 사용가능 !!
               } else {
                  idChkBl = true;
                  JOptionPane.showMessageDialog(null, " 사용가능한 아이디입니다. ");
               }

               // 점술회원 중복확인----------------------------------------------
            } else if (sellerUserKind.isSelected()) {
               System.out.println("점술가");

               // 아이디텍스트 창이 비어있을때 -- 아이디 입력해주세요
               if (id_t.getText().trim().isEmpty()) {
                  JOptionPane.showMessageDialog(null, " 아이디를 입력해주세요. ", "회원가입 ", JOptionPane.WARNING_MESSAGE);
                  idChkBl = false;

                  // 사용자가 입력한 아이디와 UserDB에 id와 같으면 -- 중복임을 알림
               } else if (id_t.getText().equals(UserDB.getID(id))) {
                  JOptionPane.showMessageDialog(null, " 이미 사용중인 아이디입니다. ", "회원가입 ", JOptionPane.WARNING_MESSAGE);
                  idChkBl = false;
                  id_t.setText("");

                  // 아이디 유효성 검사 --> 공백일때 XX
               } else if (!(Pattern.matches(idRegular, id_t.getText().trim()))
                     || !(Pattern.matches(sRegular, id_t.getText().trim()))) {
                  JOptionPane.showMessageDialog(null, "아이디는 공백없이 5~9자 이내로 영문/숫자만 사용가능합니다.");
                  id_t.setText("");

                  // 나머지가 다 아니라면 사용가능 !!
               } else {
                  idChkBl = true;
                  JOptionPane.showMessageDialog(null, " 사용가능한 아이디입니다. ");
               }

            }

            // 3) 확인 버튼 눌렀을때 ------------------------------------
         } else if (e.getSource().equals(check)) {

            // 빈칸이 있을때 텍스트chk가 false임 / 입력이 됐을때는 true
            for (int i = 0; i < textFieldList_2.size(); i++) {
               if (textFieldList_2.get(i).equals("")) {
                  textChk = false;
                  break;
               } else {
                  textChk = true;
               }
            }

            System.out.println(textFieldList_2);


            // 일반회원 유효성 검사-------------------------------------------------------------------------------
            if (nomalUserKind.isSelected()) {
               System.out.println("체크!");

               // 텍스트필드창 갯수 확인
               //System.out.println(textFieldList.size()); 
               System.out.println(textFieldList_2.size());

               // 성별을 눌렀을때 남자,여자 확인
               if (man_bt.isSelected()) {
                  System.out.println("남자");
                  gender = "남자";

               } else if (woman_bt.isSelected()) {
                  System.out.println("여자");
                  gender = "여자";

                  // 사용자 비밀번호와 사용자 비밀번호 확인이 같지 않을때 -- 다시 확인해달라는 창을 띄움.
               }
               if (!(pw_t.getText().equals(passWord.getText()))) {

                  JOptionPane.showMessageDialog(null, "비밀번호를 다시 확인해 주세요.", "회원가입", JOptionPane.WARNING_MESSAGE);

                  
                  // 텍스트필드 창 갯수가 12개보다 적게 입력했을때 --- 회원정보 입력하라는 창 띄움.
               } else if (textChk == false) {
                  System.out.println("회원정보");
                  JOptionPane.showMessageDialog(null, "회원정보를 입력해주세요.", "회원가입", JOptionPane.WARNING_MESSAGE);

                  
                  // 아이디 중복체크를 하지않고 회원가입하는것을 방지 -- 중복확인을 누르지 않고 회원가입확인을 누르면 중복확인을 해달라는 창을 띄움.
               } else if (idChkBl == false) {

                  JOptionPane.showMessageDialog(null, "중복확인을 해주세요.", "회원가입", JOptionPane.WARNING_MESSAGE);


                  // 비밀번호 힌트를 선택 안했을 때 true
               } else if (hintChk == true) {
                  JOptionPane.showMessageDialog(null, "비밀번호 힌트를 선택해 주세요", "회원가입", JOptionPane.WARNING_MESSAGE);
                  
                  
                  // 생년월일을 선택 안했을 때 true
               } else if (dayChk == true) {
                  JOptionPane.showMessageDialog(null, "생년월일을 선택해 주세요.", "회원가입", JOptionPane.WARNING_MESSAGE);
                  
                  
                  // 성별을 선택 안했을 때 true
               } else if (gender == "") {
                  JOptionPane.showMessageDialog(null, "성별을 선택해 주세요.", "회원가입", JOptionPane.WARNING_MESSAGE);

                  
               
               }else if(nomalUserKind.isSelected() && cnt_n != 11) {
                  JOptionPane.showMessageDialog(null, "올바른 형식으로 다시 입력해 주세요.", "회원가입", JOptionPane.WARNING_MESSAGE);
        
                  // 다 오류가 아닐경우 가입조건에 충족되어 가입완료 창을 띄움.
               }else  {
                  System.out.println(textFieldList.size());
                  UserDB.signupNOMALUSER(id, pwChkStr, name, gender, birthYYYYMMDD, phone, email, address, card,
                        pwhint, pwres, coin);
                  MenuDB.makeMENU(id);
                  JOptionPane.showMessageDialog(null, "          회원가입 완료! \n점톡에 오신것을 환영합니다.");
                  
                  lg.SignUp = null;
                  signUpEnd = false;
                  dispose();
               }

               // 점술회원 유효성
               // 검사-------------------------------------------------------------------------------

            } else if (sellerUserKind.isSelected()) {
               System.out.println("체크!");

               // 텍스트필드창 갯수 확인
               // System.out.println(textFieldList.size());

               // 성별을 눌렀을때 남자,여자 확인
               if (man_bt.isSelected()) {
                  System.out.println("남자");
                  gender = "남자";

               } else if (woman_bt.isSelected()) {
                  System.out.println("여자");
                  gender = "여자";

                  // 사용자 비밀번호와 사용자 비밀번호 확인이 같지 않을때 -- 다시 확인해달라는 창을 띄움.
               }
               if (!(pw_t.getText().equals(passWord.getText()))) {
                  JOptionPane.showMessageDialog(null, "비밀번호를 다시 확인해주세요.", "회원가입", JOptionPane.WARNING_MESSAGE);

                  // 텍스트필드 창 갯수가 12개보다 적게 입력했을때 --- 회원정보 입력하라는 창 띄움. 17개
               } else if (textChk == false) {
                  System.out.println("회원정보");
                  JOptionPane.showMessageDialog(null, "회원정보를 입력해주세요.", "회원가입", JOptionPane.WARNING_MESSAGE);

                  // 아이디 중복체크를 하지않고 회원가입하는것을 방지 -- 중복확인을 누르지 않고 회원가입확인을 누르면 중복확인을 해달라는 창을 띄움.
               } else if (idChkBl == false) {
                  JOptionPane.showMessageDialog(null, "중복확인을 해주세요.", "회원가입", JOptionPane.WARNING_MESSAGE);

                  // 비밀번호 힌트를 선택 안했을 때 true
               } else if (hintChk == true) {
                  JOptionPane.showMessageDialog(null, "비밀번호 힌트를 선택해주세요", "회원가입", JOptionPane.WARNING_MESSAGE);
                  
                  
                  //은행을 선택 안했을때 true
               } else if (bankChk == true) {
                  JOptionPane.showMessageDialog(null, "은행을 선택해 주세요.", "회원가입", JOptionPane.WARNING_MESSAGE);
                  
                  // 생년월일을 선택 안했을 때 true
               } else if (dayChk == true) {
                  JOptionPane.showMessageDialog(null, "생년월일을 선택해주세요.", "회원가입", JOptionPane.WARNING_MESSAGE);
                  
                  // 성별을 선택 안했을 때 true
               } else if (gender == "") {
                  JOptionPane.showMessageDialog(null, "성별을 선택해주세요.", "회원가입", JOptionPane.WARNING_MESSAGE);
                  
                  
               }else if(sellerUserKind.isSelected() && cnt_s != 10) {
                  JOptionPane.showMessageDialog(null, "올바른 형식으로 다시 입력해 주세요.", "회원가입", JOptionPane.WARNING_MESSAGE);
                 
                  
                  // 다 오류가 아닐경우 가입조건에 충족되어 가입완료 창을 띄움.
               } else {
                  System.out.println(textFieldList.size());
                  UserDB.signupSELLERUSER(id, pwChkStr, name, gender, birthYYYYMMDD, phone, email, address,
                        pwhint, pwres, businessname, businessaddress, banknum, coin);
                  MenuDB.makeMENU(id);
                  JOptionPane.showMessageDialog(null, "           회원가입 완료 !\n점톡에 오신것을 환영합니다.");
                  
                  //쓰레드 종료!!!!!
                  signUpEnd = false;  
                  lg.SignUp = null;
                  dispose();

               }

            }
         }

      }

      // 유효성 검사 쓰레드
      class SignUpChk extends Thread {



         @Override
         public void run() {
            while (signUpEnd) {
               // 비밀번호 유효성 검사 --> 숫자,영문, 특수문자 포함 (공백 금지) ---------------------------------
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

                  //비밀번호 == 비밀번호 확인  일치한지 검사!!
               }if(!(pw_t.getText().equals(passWord.getText()))) {
                  
                  passWord.setForeground(Color.red);
                  
                  
               }else {
                
                  passWord.setForeground(Color.black);
                  
                  
                  // 이름 유효성 검사 --> 한글만 사용가능 --------------------------------
               }if ((!(Pattern.matches(nameRegular, name_t.getText())))
                     || !(Pattern.matches(sRegular, name_t.getText())) && !name_t.getText().equals("")) {
                  regularChk_N[1] = false;
                  regularChk_S[1] = false;
                  name_t.setForeground(Color.red);

               } else {
                  regularChk_N[1] = true;
                  regularChk_S[1] = true;
                  name_t.setForeground(Color.black);

                  // 이메일 유효성 검사 --> 대소문자 /숫자 --------------------------------
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

                  // 핸드폰번호 유효성 검사 --> 글자수 3~4자 제한, 숫자만 가능 --------------------------------
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

                  // 카드번호 유효성 검사 --> 글자수 4자 제한, 숫자만 가능 ..--------------------------------
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

                  
               }// 주소는 한글/영문/숫자만 가능!!(초성금지)
               if ((!(Pattern.matches(addressRegular, address_t.getText().trim().replaceAll(" ", ""))))
                      && !address_t.getText().equals("")) {
                  regularChk_N[10] = false;
                  regularChk_S[6] = false;
                  address_t.setForeground(Color.red);

               } else {
                  regularChk_N[10] = true;
                  regularChk_S[6] = true;
                  address_t.setForeground(Color.black);
                  
               }//사업장명
               if ((!(Pattern.matches(businessName_R, business_1.getText().trim().replaceAll(" ", ""))))
                     && !business_1.getText().equals("") && sellerUserKind.isSelected()) {

                  regularChk_S[7] = false;
                  business_1.setForeground(Color.red);
                  
               } else {
                  regularChk_S[7] = true;
                  business_1.setForeground(Color.black);
               }
               //사업장주소
               if ((!(Pattern.matches(addressRegular, business_2.getText().trim().replaceAll(" ", ""))))
                     && !business_2.getText().equals("")&& sellerUserKind.isSelected()) {
                  regularChk_S[8] = false;
                  business_2.setForeground(Color.red);
                  
               } else {
                  regularChk_S[8] = true;
                  business_2.setForeground(Color.black);
               
                  //계좌번호
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
         // TODO 자동 생성된 메소드 스텁
         
      }



      @Override
      public void windowClosing(WindowEvent e) {
         lg.SignUp = null;
         signUpEnd = false;
         
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

//             if(idChkBl == true && pwChkBl == true && textFieldList_2.size() == 12)
   }

   

}



public class LoginFrame {

   public static void main(String[] args) {

      new Login(); // 로그인
//      new MemberChoice(); //회원 분류 창
//      new Signup(); //일반 회원가입 창
//      new SignUp(); //사업자 회원가입 창
//      new IDsearch();  //아이디 찾기
//      new PWsearch();  //비밀번호 찾기
//      new CardChoice(); //카드사 선택
//      new NewPassWord(); //새로운 비밀번호 입력 창

   }

}