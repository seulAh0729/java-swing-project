package db_p;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.Box;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


class BuyerFrame extends JPanel implements ActionListener {

   String userID;
   FortunePanel fortunepanel;
   ChargePanel chargepanel;
   JLabel haveCoin;

   public BuyerFrame(String userID) {
      this.userID = userID;
      
      setLayout(null);
      // 버튼 생성 및 버튼 그룹 만들기 - 패널 스위치용 버튼
      ArrayList<JButton> ckjbt = new ArrayList<JButton>();
      String[] CKBT = { "오늘의 쿠키", "코인 충전" };
      int[][] CKBTBounds = { { 20, 40, 120, 40 }, { 140, 40, 120, 40 } };
      ButtonGroup BTG = new ButtonGroup();
      for (int i = 0; i < CKBT.length; i++) {
         ckjbt.add(new JButton(CKBT[i]));
         ckjbt.get(i).setBounds(CKBTBounds[i][0], CKBTBounds[i][1], CKBTBounds[i][2], CKBTBounds[i][3]);
         add(ckjbt.get(i));
         ckjbt.get(i).addActionListener(this);
         BTG.add(ckjbt.get(i));
      }

      // 갖고 있는 코인의 갯수 라벨
      haveCoin = new JLabel();
      haveCoin.setBounds(320, 40, 200, 40);
      haveCoin.setText("코인 갯수 : " + UserDB.getCOIN(userID) + "개");
      add(haveCoin);

      // 두개의 패널 (이너클래스) 추가 //
      add(fortunepanel = new FortunePanel());
      add(chargepanel = new ChargePanel());
      
      setVisible(true);

   }
   // BuyerFrame ActionEvent
   @Override
   public void actionPerformed(ActionEvent e) {
      JButton Bt = (JButton) e.getSource();
      if (Bt.getText().equals("오늘의 쿠키")) {
         fortunepanel.setVisible(true);
         chargepanel.setVisible(false);
      } else {
         fortunepanel.setVisible(false);
         chargepanel.setVisible(true);
      }
   }
   /// 오늘의 포춘 쿠키 패널 - 스윙 1번 패널
   class FortunePanel extends JPanel implements ActionListener {

      JTextField clickResult;
      BuyerFrame buyerFrame;
      int bonus = (int) (Math.random() * 5 + 1);
      JButton CKClickBT;
      ImageIcon beforeCK = new ImageIcon("icon\\aaa.png");
      ImageIcon afterCK = new ImageIcon("icon\\bbb.png");
      String time;
      int portuneNUM = UserDB.getFORTUNE_NUM(userID);
      String texttt = TodayFortuneDB.getFORTUNEMESSAGE(portuneNUM);
      // 현 시간 나타내는 메소드 - 오늘의 쿠키 클릭 시 필요 //
      String times() {
         Calendar cal = Calendar.getInstance();
         SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
         time = simple.format(cal.getTime());

         return time;
      }

      public FortunePanel() {
         setBounds(0, 120, 480, 500);
         setLayout(null);

         // 오늘의 쿠키 버튼
         CKClickBT = new JButton(beforeCK);
         CKClickBT.setBounds(20, 20, 250, 250);
         CKClickBT.addActionListener(this);
         add(CKClickBT);

         // 오늘의 한 줄
         JLabel res = new JLabel("오늘의 한 줄");
         res.setBounds(20, 350, 100, 50);
         add(res);
         clickResult = new JTextField();
         clickResult.setLayout(null);
         clickResult.setBounds(20, 400, 450, 30);
         
         add(clickResult);
         clickResult.setEditable(false);

         // 하루 한번만 누를 수 있게 - 누른 시간이 그 날일 경우 버튼 비활성화
         if (UserDB.getFORTUNE_TIME(userID).equals(times())) {
            CKClickBT.setEnabled(false);
            CKClickBT.setIcon(afterCK);
         }
         clickResult.setText(texttt);
         
         setVisible(true);
      }

      @Override
      public void actionPerformed(ActionEvent e) {
         UserDB.setFORTUNE_TIME(userID, times());
         UserDB.setFORTUNE_NUM(userID, (int)(Math.random()*10+1));

         // 오늘의 쿠키 - 격언 정보 DB에서 받아오기
         clickResult.setText(TodayFortuneDB.getFORTUNEMESSAGE(UserDB.getFORTUNE_NUM(userID)));
         // 클릭 후 버튼 이미지 바꾸기
         CKClickBT.setIcon(afterCK);
         // 클릭 후 코인 갯수 업데이트 -> 라벨 + DB
         haveCoin.setText("코인 갯수 : " + (UserDB.getCOIN(userID) + bonus) + "개");
         UserDB.setCOIN(userID, UserDB.getCOIN(userID) + bonus);
         // 쿠키 버튼 클릭 후 비활겅화
         CKClickBT.setEnabled(false);
         // 버튼 클릭한 시간 DB로 보내기//
         // 보너스 갯수 DB로 보내기 - 충전과 구분하기 위해 + 순수익 합산을 위한 보너스 쿠키 갯수 분류//
         MoneyLogDB.saveMONEYLOG(userID, bonus * 100, bonus, 2);
         // 클릭 후 보너스로 얻은 갯수 시각적으로 확인시키기
         JOptionPane.showMessageDialog(this, "코인 " + bonus + "개를 추가로 얻었습니다.");
      }

   }

   // 코인 충전 패널 - 스윙 2번 패널
   class ChargePanel extends JPanel implements ActionListener {

      JTable MtoCList;
      JScrollPane MtoCPan;
      ArrayList<JRadioButton> res = new ArrayList<JRadioButton>();

      public ChargePanel() {
         setBounds(0, 120, 490, 510);
         setLayout(null);
         ButtonGroup bg = new ButtonGroup();

         // 가격 결정 라디오 버튼 생성 //
         String[] label = { "10,000 원      →    100 코인", "20,000 원      →    200 코인", "30,000 원      →    300 코인",
               "50,000 원      →    500 코인", "100,000 원    →    1000 코인" };
         int[][] loken = { { 30, 10, 250, 30 }, { 30, 50, 250, 30 }, { 30, 90, 250, 30 }, { 30, 130, 250, 30 },
               { 30, 170, 250, 30 } };
         for (int i = 0; i < label.length; i++) {
            res.add(new JRadioButton(label[i]));
            res.get(i).setBounds(loken[i][0], loken[i][1], loken[i][2], loken[i][3]);
            add(res.get(i));
            bg.add(res.get(i));
         }

         // 충전 요청 내역 리스트 - 충전자 개인 것
         String[][] data = MoneyLogDB.getMONEYLOG(userID, 0);
         String[] ListInfo = { "충전 금액", "충전한 코인 갯수", "시간" };
         if (data == null) {
            String[][] nodata = new String[1][3];
            MtoCList = new JTable(nodata, ListInfo);
            MtoCPan = new JScrollPane(MtoCList);
            MtoCPan.setBounds(15, 250, 450, 150);
            add(MtoCPan);
         } else {
            MtoCList = new JTable(data, ListInfo);
            MtoCPan = new JScrollPane(MtoCList);
            MtoCPan.setBounds(15, 250, 450, 150);
            add(MtoCPan);
         }

         // 결제 완료 버튼 //
         JButton finBt = new JButton("결제 완료");
         finBt.setBounds(350, 440, 120, 50);
         finBt.addActionListener(this);
         add(finBt);

         MtoCList.getTableHeader().setReorderingAllowed(false);
         MtoCList.getTableHeader().setResizingAllowed(false);
         
         setVisible(false);
      }

      // 다른 패널 갔다 왔을 시 새로고침 없이 업데이트를 위한 메소드
      void refresh() {
         haveCoin.setText("코인 갯수 : " + (UserDB.getCOIN(userID)) + "개");
      }

      @Override
      public void actionPerformed(ActionEvent e) {

         for (int i = 0; i < res.size(); i++) {
            int[] price = { 100, 200, 300, 500, 1000 };

         }

         Box box = Box.createHorizontalBox();
         JLabel jl = new JLabel("비밀번호: ");
         box.add(jl);
         JPasswordField jpf = new JPasswordField(30);
         box.add(jpf);
         int button = JOptionPane.showConfirmDialog(null, box, "비밀번호를 입력하세요", JOptionPane.OK_CANCEL_OPTION);
         char[] input = jpf.getPassword();

         String pop = new String(input);
         for (int i = 0; i < res.size(); i++) {
            int[] price = { 100, 200, 300, 500, 1000 };
            if (res.get(i).isSelected()) {

               if (pop.equals(UserDB.getPW(userID))) {

                  int coinnum = UserDB.getCOIN(userID) + price[i];
                  UserDB.setCOIN(userID, coinnum);
                  MoneyLogDB.saveMONEYLOG(userID, price[i] * 100, price[i], 0);
                  String[][] data = MoneyLogDB.getMONEYLOG(userID, 0);
                  String[] ListInfo = { "충전 금액", "충전한 코인 갯수", "시간" };
                  chargepanel.remove(MtoCPan);
                  MtoCList = new JTable(data, ListInfo);
                  MtoCPan = new JScrollPane(MtoCList);
                  MtoCPan.setBounds(15, 250, 450, 150);
                  chargepanel.add(MtoCPan);
                  chargepanel.repaint();
                  JOptionPane.showMessageDialog(this,
                        "입력하신 카드번호 " + UserDB.getCARDNUMBER(userID) + " 로 결제되었습니다.");

                  refresh();
                  break;

               } else {
                  JOptionPane.showMessageDialog(this, "비밀번호가 다릅니다");
                  break;
               }
            }

         }
      }
   }
}

class SellerFrame extends JPanel implements ActionListener {

   String userID;
   JTable CtoMList;
   JScrollPane CtoMListPan;
   JButton changeCtoMBt;
   JLabel haveCoinNumLB, getMoneyLB;
   int nowMyCoin;

   public SellerFrame(String userID) {
      this.userID = userID;
      setLayout(null);
      // 점술가가 얻은 코인의 수를 보여주는 패널 + 라벨
      nowMyCoin = UserDB.getCOIN(userID);
      JPanel nowHaveCoinPan = new JPanel();
      nowHaveCoinPan.setLayout(null);
      nowHaveCoinPan.setBounds(20, 70, 200, 200);
      nowHaveCoinPan.setBackground(Color.lightGray);
      add(nowHaveCoinPan);
      haveCoinNumLB = new JLabel();
      haveCoinNumLB.setBounds(10, 50, 200, 100);
      haveCoinNumLB.setText("환전 가능한 코인 갯수 : " + nowMyCoin + "개");
      nowHaveCoinPan.add(haveCoinNumLB);

      // 점술가가 갖고있는 코인을 환전했을 시 얻을 수 있는 금액을 보여주는 패널 + 라벨
      JPanel getMoneyPan = new JPanel();
      getMoneyPan.setLayout(null);
      getMoneyPan.setBounds(260, 70, 200, 200);
      getMoneyPan.setBackground(Color.lightGray);
      add(getMoneyPan);
      getMoneyLB = new JLabel();
      getMoneyLB.setBounds(10, 50, 200, 100);
      getMoneyLB.setText("환전 가능한 금액 : " + nowMyCoin * 70 + "원");
      getMoneyPan.add(getMoneyLB);

      // 환전 요청 버튼
      changeCtoMBt = new JButton("환전 요청");
      changeCtoMBt.addActionListener(this);
      changeCtoMBt.setBounds(300, 300, 100, 70);
      add(changeCtoMBt);

      // 환전 요청 리스트
      String[][] data = MoneyLogDB.getMONEYLOG(userID, 1);
      if (data == null) {
         String[][] data1 = new String[1][3];
         String[] ListInfo = { "환전 금액", "환전한 코인 갯수", "시간" };
         CtoMList = new JTable(data1, ListInfo);
         CtoMListPan = new JScrollPane(CtoMList);
         CtoMList.setLayout(null);
         CtoMListPan.setBounds(15, 400, 450, 150);
         add(CtoMListPan);
      } else {
         String[] ListInfo = { "환전 금액", "환전한 코인 갯수", "시간" };
         CtoMList = new JTable(data, ListInfo);
         CtoMListPan = new JScrollPane(CtoMList);
         CtoMList.setLayout(null);
         CtoMListPan.setBounds(15, 400, 450, 150);
         add(CtoMListPan);
      }

      CtoMList.getTableHeader().setReorderingAllowed(false);
      CtoMList.getTableHeader().setResizingAllowed(false);
      
      setVisible(true);

   }

   void refresh() {
      haveCoinNumLB.setText("환전 가능한 코인 갯수 : " + Integer.toString(nowMyCoin) + "개");
      getMoneyLB.setText("출금 가능한 금액 : " + Integer.toString(nowMyCoin * 70) + "원");
      String[][] data = MoneyLogDB.getMONEYLOG(userID, 1);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      JButton bt = (JButton) e.getSource();

      if (bt.getText().equals("환전 요청")) {
         int coinnum = UserDB.getCOIN(userID);
         if (coinnum == 0) {
            JOptionPane.showMessageDialog(this, "코인이 없습니다.");
            return;
         }
         nowMyCoin = 0;
         haveCoinNumLB.setText("환전 가능한 코인 갯수 : " + Integer.toString(nowMyCoin) + "개");
         UserDB.setCOIN(userID, nowMyCoin);
         getMoneyLB.setText("출금 가능한 금액 : " + Integer.toString(nowMyCoin * 70) + "원");

         UserDB.setCOIN(userID, 0);
         MoneyLogDB.saveMONEYLOG(userID, coinnum * 70, coinnum, 1);

         String[][] data = MoneyLogDB.getMONEYLOG(userID, 1);
         String[] ListInfo = { "환전 금액", "환전한 코인 갯수", "시간" };
         remove(CtoMListPan);
         CtoMList = new JTable(data, ListInfo);
         CtoMListPan = new JScrollPane(CtoMList);
         CtoMList.setLayout(null);
         CtoMListPan.setBounds(15, 400, 450, 150);
         add(CtoMListPan);

         revalidate();
         CtoMListPan.repaint();
         JOptionPane.showMessageDialog(this, "입력하신 계좌번호 " + UserDB.getBANKNUMBER(userID) + "로 입금되었습니다.");

      }
      refresh();
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

class panelOne extends JPanel {

   public panelOne(String date, int statkind) {
      setBounds(5, 100, 340, 200);
      setLayout(new BorderLayout());
      String[] ListInfo = { "ID", "충전 금액", "코인", "충전 시간" };
      String[][] dataB = MoneyLogDB.getSTATSMONEYLOG(0, date, statkind);
      JTable btc = new JTable(new NotEditTable(dataB, ListInfo));
      JScrollPane buyerToCoin = new JScrollPane(btc);

      btc.getColumn("ID").setPreferredWidth(70);
      btc.getColumn("충전 금액").setPreferredWidth(70);
      btc.getColumn("코인").setPreferredWidth(60);
      btc.getColumn("충전 시간").setPreferredWidth(140);

      add(buyerToCoin);
      
      btc.getTableHeader().setReorderingAllowed(false);
      btc.getTableHeader().setResizingAllowed(false);
      setVisible(true);

   }
}

class panelTwo extends JPanel {

   public panelTwo(String date, int statkind) {
      setBounds(5, 335, 340, 200);
      setLayout(new BorderLayout());
      String[] ListInfoS = { "ID", "환전 금액", "코인", "환전 시간" };
      String[][] dataS = MoneyLogDB.getSTATSMONEYLOG(1, date, statkind); //// (x ,x , 0 ) <--0번이면 가공된 데이터

      JTable stm = new JTable(new NotEditTable(dataS, ListInfoS));
      JScrollPane sellToMoney = new JScrollPane(stm);

      stm.getColumn("ID").setPreferredWidth(70);
      stm.getColumn("환전 금액").setPreferredWidth(70);
      stm.getColumn("코인").setPreferredWidth(60);
      stm.getColumn("환전 시간").setPreferredWidth(140);
      sellToMoney.setBounds(15, 320, 340, 200);

      add(sellToMoney);

      stm.getTableHeader().setReorderingAllowed(false);
      stm.getTableHeader().setResizingAllowed(false);
      setVisible(true);
   }
}

class Calenderpane extends JPanel implements ActionListener {
   JComboBox boxM;
   JComboBox boxD;
   JComboBox boxY;
   JButton conf;
   EditerFrame ef;
   DecimalFormat df = new DecimalFormat("###");
   
   public Calenderpane(EditerFrame ef) {
      this.ef = ef;
      setBounds(0, 10, 500, 50);
      setLayout(null);
      Calendar cal = Calendar.getInstance();

      int[] y = { 2020, 2019, 2018 };
      Vector yy = new Vector();
      yy.add("전체");
      for (int i = 0; i < y.length; i++) {
         yy.add(y[i]);
      }

      Vector mm = new Vector();
      mm.add("전체");
      for (int i = 1; i <= 12; i++) {
         mm.add(i);
      }

      Vector dd = new Vector();
      dd.add("전체");
      for (int i = 1; i <= 31; i++) {
         dd.add(i);
      }

      boxY = new JComboBox(yy);
      boxY.setSelectedItem(cal.get(Calendar.YEAR));
      boxY.addActionListener(this);
      boxM = new JComboBox(mm);
      boxM.setSelectedItem(cal.get(Calendar.MONTH) + 1);
      boxM.addActionListener(this);
      boxD = new JComboBox(dd);
      boxD.setSelectedItem(cal.get(Calendar.DATE));

      boxY.setBounds(10, 20, 80, 30);
      boxM.setBounds(130, 20, 80, 30);
      boxD.setBounds(250, 20, 80, 30);
      add(boxY);
      add(boxM);
      add(boxD);

      conf = new JButton("검색");
      conf.setBounds(380, 20, 80, 30);
      conf.addActionListener(this);
      add(conf);

      JLabel ylb = new JLabel("년");
      JLabel mlb = new JLabel("월");
      JLabel dlb = new JLabel("일");
      ylb.setBounds(100, 20, 30, 30);
      mlb.setBounds(220, 20, 30, 30);
      dlb.setBounds(340, 20, 30, 30);
      add(ylb);
      add(mlb);
      add(dlb);

   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource().equals(boxM)) {

         if (boxM.getSelectedItem().equals("전체")) {
            boxD.setSelectedItem("전체");
            boxD.setEnabled(false);
            return;
         } else {
            boxD.setSelectedItem("전체");
            boxD.setEnabled(true);
         }

         int a = (int) boxM.getSelectedItem();
         Calendar cal = Calendar.getInstance();
         cal.set(Calendar.YEAR, (int) boxY.getSelectedItem());
         cal.set(Calendar.DATE, 1);
         cal.set(Calendar.MONTH, a - 1);
         System.out.println(a);
         Vector dd = new Vector();
         dd.add("전체");

         for (int i = 1; i <= cal.getActualMaximum(Calendar.DATE); i++) {
            dd.add(i);
         }
         remove(boxD);
         add(boxD = new JComboBox(dd));
         boxD.setBounds(250, 20, 80, 30);
         boxD.setSelectedItem("전체");
         revalidate();
         repaint();

      } else if (e.getSource().equals(boxY)) {
         if (boxY.getSelectedItem().equals("전체")) {
            boxM.setSelectedItem("전체");
            boxM.setEnabled(false);
            boxD.setSelectedItem("전체");
            boxD.setEnabled(false);
         } else {
            boxM.setSelectedItem("전체");
            boxM.setEnabled(true);
            boxD.setSelectedItem("전체");
            boxD.setEnabled(false);
         }
      } else if (e.getSource().equals(conf)) {
         System.out.println(boxY.getSelectedItem() + "-" + boxM.getSelectedItem() + "-" + boxD.getSelectedItem());
         ef.remove(ef.kk);
         ef.remove(ef.tt);
         ef.add(ef.kk = new panelOne(
               boxY.getSelectedItem() + "-" + boxM.getSelectedItem() + "-" + boxD.getSelectedItem(), 1));
         ef.add(ef.tt = new panelTwo(
               boxY.getSelectedItem() + "-" + boxM.getSelectedItem() + "-" + boxD.getSelectedItem(), 1));
         ef.revalidate();
         ef.repaint();
         
         
         ef.totprice = 0;
         String[][] dataB = MoneyLogDB.getSTATSMONEYLOG(0,
               boxY.getSelectedItem() + "-" + boxM.getSelectedItem() + "-" + boxD.getSelectedItem(), 0);
         for (int i = 0; i < dataB.length; i++) {
            try {
               ef.totprice += (long) df.parse(dataB[i][1]);
            } catch (ParseException f) {
               // TODO Auto-generated catch block
               f.printStackTrace();
            }
         }
         ef.totbonus = 0;
         String[][] dataC = MoneyLogDB.getMONEYLOG(2);
         for (int i = 0; i < dataC.length; i++) {
            try {
               ef.totbonus += (long) df.parse(dataC[i][1]);
            } catch (ParseException f) {
               // TODO Auto-generated catch block
               f.printStackTrace();
            }
         }
         ef.salesIn.setText(ef.totprice+"원");
         ef.netIn.setText((int) (ef.totprice * 0.3 - ef.totbonus * 0.7) + " 원");

      }

   }
}

class EditerFrame extends JPanel implements ActionListener{

   String userID;
   
   JLabel salesIn, netIn;
   int totprice = 0;
   int totbonus = 0;
   panelOne kk;
   panelTwo tt;
   Calenderpane cp;
   DecimalFormat df = new DecimalFormat("###");
   
   public EditerFrame(String userID) {
      setBounds(1020 + 0, 70, 500, 670);
      setLayout(null);

      JPanel salesPan = new JPanel();
      JPanel netgainPan = new JPanel();
      ArrayList<JLabel> jlb = new ArrayList<JLabel>();

      // JLabel 모음
      String[] labelName = { "COL", "MOL", "sales", "netgain" };
      String[] labelText = { "코인 충전 구매자 리스트", "코인 환전 점술가 리스트", "매출액", "순수익" };
      int[][] labelBounds = { { 30, 55, 220, 50 }, { 30, 295, 420, 50 }, { 30, 525, 200, 50 },
            { 270, 525, 200, 50 } };
      for (int i = 0; i < labelName.length; i++) {
         jlb.add(new JLabel(labelName[i]));
         jlb.get(i).setBounds(labelBounds[i][0], labelBounds[i][1], labelBounds[i][2], labelBounds[i][3]);
         jlb.get(i).setText(labelText[i]);
         add(jlb.get(i));
      }

      add(cp = new Calenderpane(this));
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
      add(kk = new panelOne(sdf.format(new Date()), 1));
      add(tt = new panelTwo(sdf.format(new Date()), 1));

      String[][] dataB = MoneyLogDB.getSTATSMONEYLOG(0,
            cp.boxY.getSelectedItem() + "-" + cp.boxM.getSelectedItem() + "-" + cp.boxD.getSelectedItem(), 0);

      // 총 매출 금액 -- 모든 소비자들의 충전 금액(원) ++
      salesIn = new JLabel();
      salesIn.setBounds(20, 0, 140, 50);
      for (int i = 0; i < dataB.length; i++) {
         try {
            totprice += (long) df.parse(dataB[i][1]);
         } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      salesIn.setText(totprice + "원");

      // 총 매출 금액 패널 - (금액)
      salesPan.setLayout(null);
      salesPan.add(salesIn);
      salesPan.setBounds(30, 570, 100, 50);
      salesPan.setBackground(Color.LIGHT_GRAY);
      add(salesPan);
      
      // 총 순수익 금액 --> (총 매출 *30%) - (보너스 코인 금액*70%) (원) ++
      netIn = new JLabel();
      netIn.setBounds(20, 0, 140, 50);
      netIn.setText((int) (totprice * 0.3 - totbonus * 0.7) + " 원");
      // 총 순수익 금액 패널
      netgainPan.setLayout(null);
      netgainPan.add(netIn);
      netgainPan.setBounds(260, 570, 100, 50);
      netgainPan.setBackground(Color.LIGHT_GRAY);
      add(netgainPan);
      
      // 총 보너스 갯수 구하기 - 순수익 때 필요
      String[][] dataC = MoneyLogDB.getMONEYLOG(2);
      for (int i = 0; i < dataC.length; i++) {
         try {
            totbonus += (long) df.parse(dataC[i][1]);
         } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }

      JButton chargeTot = new JButton("충전총합계");
      chargeTot.addActionListener(this);
      chargeTot.setBounds(360, 100, 100, 40);
      add(chargeTot);
      JButton monthCharge = new JButton("충전 순위");
      monthCharge.addActionListener(this);
      monthCharge.setBounds(360, 160, 100, 40);
      add(monthCharge);
      JButton sellerList = new JButton("환전총합계");
      sellerList.addActionListener(this);
      sellerList.setBounds(360, 335, 100, 40);
      add(sellerList);
      JButton momthseller = new JButton("환전 순위");
      momthseller.addActionListener(this);
      momthseller.setBounds(360, 395, 100, 40);
      add(momthseller);
      

      setVisible(true);
      
   }

   void refresh() {
      salesIn.setText(totprice + "원");
      netIn.setText((int) (totprice * 0.3 - totbonus * 0.7) + " 원");
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      //getSource 는 주소를 갖고옴
      JButton b = (JButton) e.getSource();
      
      if(b.getText().equals("충전총합계")) {
         remove(kk);
         add(kk = new panelOne(
               cp.boxY.getSelectedItem() + "-" + cp.boxM.getSelectedItem() + "-" + cp.boxD.getSelectedItem(),
               1));
      }else if(b.getText().equals("충전 순위")) {
         remove(kk);
         add(kk = new panelOne(
               cp.boxY.getSelectedItem() + "-" + cp.boxM.getSelectedItem() + "-" + cp.boxD.getSelectedItem(),
               0));
      }else if(b.getText().equals("환전총합계")) {
         remove(tt);
         add(tt = new panelTwo(
               cp.boxY.getSelectedItem() + "-" + cp.boxM.getSelectedItem() + "-" + cp.boxD.getSelectedItem(),
               1));
      }else if(b.getText().equals("환전 순위")) {
         remove(tt);
         add(tt = new panelTwo(
               cp.boxY.getSelectedItem() + "-" + cp.boxM.getSelectedItem() + "-" + cp.boxD.getSelectedItem(),
               0));
      }
      
      revalidate();
      repaint();
   }
}

public class PaymentPanel {

}