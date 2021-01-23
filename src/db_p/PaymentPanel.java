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
      // ��ư ���� �� ��ư �׷� ����� - �г� ����ġ�� ��ư
      ArrayList<JButton> ckjbt = new ArrayList<JButton>();
      String[] CKBT = { "������ ��Ű", "���� ����" };
      int[][] CKBTBounds = { { 20, 40, 120, 40 }, { 140, 40, 120, 40 } };
      ButtonGroup BTG = new ButtonGroup();
      for (int i = 0; i < CKBT.length; i++) {
         ckjbt.add(new JButton(CKBT[i]));
         ckjbt.get(i).setBounds(CKBTBounds[i][0], CKBTBounds[i][1], CKBTBounds[i][2], CKBTBounds[i][3]);
         add(ckjbt.get(i));
         ckjbt.get(i).addActionListener(this);
         BTG.add(ckjbt.get(i));
      }

      // ���� �ִ� ������ ���� ��
      haveCoin = new JLabel();
      haveCoin.setBounds(320, 40, 200, 40);
      haveCoin.setText("���� ���� : " + UserDB.getCOIN(userID) + "��");
      add(haveCoin);

      // �ΰ��� �г� (�̳�Ŭ����) �߰� //
      add(fortunepanel = new FortunePanel());
      add(chargepanel = new ChargePanel());
      
      setVisible(true);

   }
   // BuyerFrame ActionEvent
   @Override
   public void actionPerformed(ActionEvent e) {
      JButton Bt = (JButton) e.getSource();
      if (Bt.getText().equals("������ ��Ű")) {
         fortunepanel.setVisible(true);
         chargepanel.setVisible(false);
      } else {
         fortunepanel.setVisible(false);
         chargepanel.setVisible(true);
      }
   }
   /// ������ ���� ��Ű �г� - ���� 1�� �г�
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
      // �� �ð� ��Ÿ���� �޼ҵ� - ������ ��Ű Ŭ�� �� �ʿ� //
      String times() {
         Calendar cal = Calendar.getInstance();
         SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
         time = simple.format(cal.getTime());

         return time;
      }

      public FortunePanel() {
         setBounds(0, 120, 480, 500);
         setLayout(null);

         // ������ ��Ű ��ư
         CKClickBT = new JButton(beforeCK);
         CKClickBT.setBounds(20, 20, 250, 250);
         CKClickBT.addActionListener(this);
         add(CKClickBT);

         // ������ �� ��
         JLabel res = new JLabel("������ �� ��");
         res.setBounds(20, 350, 100, 50);
         add(res);
         clickResult = new JTextField();
         clickResult.setLayout(null);
         clickResult.setBounds(20, 400, 450, 30);
         
         add(clickResult);
         clickResult.setEditable(false);

         // �Ϸ� �ѹ��� ���� �� �ְ� - ���� �ð��� �� ���� ��� ��ư ��Ȱ��ȭ
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

         // ������ ��Ű - �ݾ� ���� DB���� �޾ƿ���
         clickResult.setText(TodayFortuneDB.getFORTUNEMESSAGE(UserDB.getFORTUNE_NUM(userID)));
         // Ŭ�� �� ��ư �̹��� �ٲٱ�
         CKClickBT.setIcon(afterCK);
         // Ŭ�� �� ���� ���� ������Ʈ -> �� + DB
         haveCoin.setText("���� ���� : " + (UserDB.getCOIN(userID) + bonus) + "��");
         UserDB.setCOIN(userID, UserDB.getCOIN(userID) + bonus);
         // ��Ű ��ư Ŭ�� �� ��Ȱ��ȭ
         CKClickBT.setEnabled(false);
         // ��ư Ŭ���� �ð� DB�� ������//
         // ���ʽ� ���� DB�� ������ - ������ �����ϱ� ���� + ������ �ջ��� ���� ���ʽ� ��Ű ���� �з�//
         MoneyLogDB.saveMONEYLOG(userID, bonus * 100, bonus, 2);
         // Ŭ�� �� ���ʽ��� ���� ���� �ð������� Ȯ�ν�Ű��
         JOptionPane.showMessageDialog(this, "���� " + bonus + "���� �߰��� ������ϴ�.");
      }

   }

   // ���� ���� �г� - ���� 2�� �г�
   class ChargePanel extends JPanel implements ActionListener {

      JTable MtoCList;
      JScrollPane MtoCPan;
      ArrayList<JRadioButton> res = new ArrayList<JRadioButton>();

      public ChargePanel() {
         setBounds(0, 120, 490, 510);
         setLayout(null);
         ButtonGroup bg = new ButtonGroup();

         // ���� ���� ���� ��ư ���� //
         String[] label = { "10,000 ��      ��    100 ����", "20,000 ��      ��    200 ����", "30,000 ��      ��    300 ����",
               "50,000 ��      ��    500 ����", "100,000 ��    ��    1000 ����" };
         int[][] loken = { { 30, 10, 250, 30 }, { 30, 50, 250, 30 }, { 30, 90, 250, 30 }, { 30, 130, 250, 30 },
               { 30, 170, 250, 30 } };
         for (int i = 0; i < label.length; i++) {
            res.add(new JRadioButton(label[i]));
            res.get(i).setBounds(loken[i][0], loken[i][1], loken[i][2], loken[i][3]);
            add(res.get(i));
            bg.add(res.get(i));
         }

         // ���� ��û ���� ����Ʈ - ������ ���� ��
         String[][] data = MoneyLogDB.getMONEYLOG(userID, 0);
         String[] ListInfo = { "���� �ݾ�", "������ ���� ����", "�ð�" };
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

         // ���� �Ϸ� ��ư //
         JButton finBt = new JButton("���� �Ϸ�");
         finBt.setBounds(350, 440, 120, 50);
         finBt.addActionListener(this);
         add(finBt);

         MtoCList.getTableHeader().setReorderingAllowed(false);
         MtoCList.getTableHeader().setResizingAllowed(false);
         
         setVisible(false);
      }

      // �ٸ� �г� ���� ���� �� ���ΰ�ħ ���� ������Ʈ�� ���� �޼ҵ�
      void refresh() {
         haveCoin.setText("���� ���� : " + (UserDB.getCOIN(userID)) + "��");
      }

      @Override
      public void actionPerformed(ActionEvent e) {

         for (int i = 0; i < res.size(); i++) {
            int[] price = { 100, 200, 300, 500, 1000 };

         }

         Box box = Box.createHorizontalBox();
         JLabel jl = new JLabel("��й�ȣ: ");
         box.add(jl);
         JPasswordField jpf = new JPasswordField(30);
         box.add(jpf);
         int button = JOptionPane.showConfirmDialog(null, box, "��й�ȣ�� �Է��ϼ���", JOptionPane.OK_CANCEL_OPTION);
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
                  String[] ListInfo = { "���� �ݾ�", "������ ���� ����", "�ð�" };
                  chargepanel.remove(MtoCPan);
                  MtoCList = new JTable(data, ListInfo);
                  MtoCPan = new JScrollPane(MtoCList);
                  MtoCPan.setBounds(15, 250, 450, 150);
                  chargepanel.add(MtoCPan);
                  chargepanel.repaint();
                  JOptionPane.showMessageDialog(this,
                        "�Է��Ͻ� ī���ȣ " + UserDB.getCARDNUMBER(userID) + " �� �����Ǿ����ϴ�.");

                  refresh();
                  break;

               } else {
                  JOptionPane.showMessageDialog(this, "��й�ȣ�� �ٸ��ϴ�");
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
      // �������� ���� ������ ���� �����ִ� �г� + ��
      nowMyCoin = UserDB.getCOIN(userID);
      JPanel nowHaveCoinPan = new JPanel();
      nowHaveCoinPan.setLayout(null);
      nowHaveCoinPan.setBounds(20, 70, 200, 200);
      nowHaveCoinPan.setBackground(Color.lightGray);
      add(nowHaveCoinPan);
      haveCoinNumLB = new JLabel();
      haveCoinNumLB.setBounds(10, 50, 200, 100);
      haveCoinNumLB.setText("ȯ�� ������ ���� ���� : " + nowMyCoin + "��");
      nowHaveCoinPan.add(haveCoinNumLB);

      // �������� �����ִ� ������ ȯ������ �� ���� �� �ִ� �ݾ��� �����ִ� �г� + ��
      JPanel getMoneyPan = new JPanel();
      getMoneyPan.setLayout(null);
      getMoneyPan.setBounds(260, 70, 200, 200);
      getMoneyPan.setBackground(Color.lightGray);
      add(getMoneyPan);
      getMoneyLB = new JLabel();
      getMoneyLB.setBounds(10, 50, 200, 100);
      getMoneyLB.setText("ȯ�� ������ �ݾ� : " + nowMyCoin * 70 + "��");
      getMoneyPan.add(getMoneyLB);

      // ȯ�� ��û ��ư
      changeCtoMBt = new JButton("ȯ�� ��û");
      changeCtoMBt.addActionListener(this);
      changeCtoMBt.setBounds(300, 300, 100, 70);
      add(changeCtoMBt);

      // ȯ�� ��û ����Ʈ
      String[][] data = MoneyLogDB.getMONEYLOG(userID, 1);
      if (data == null) {
         String[][] data1 = new String[1][3];
         String[] ListInfo = { "ȯ�� �ݾ�", "ȯ���� ���� ����", "�ð�" };
         CtoMList = new JTable(data1, ListInfo);
         CtoMListPan = new JScrollPane(CtoMList);
         CtoMList.setLayout(null);
         CtoMListPan.setBounds(15, 400, 450, 150);
         add(CtoMListPan);
      } else {
         String[] ListInfo = { "ȯ�� �ݾ�", "ȯ���� ���� ����", "�ð�" };
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
      haveCoinNumLB.setText("ȯ�� ������ ���� ���� : " + Integer.toString(nowMyCoin) + "��");
      getMoneyLB.setText("��� ������ �ݾ� : " + Integer.toString(nowMyCoin * 70) + "��");
      String[][] data = MoneyLogDB.getMONEYLOG(userID, 1);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      JButton bt = (JButton) e.getSource();

      if (bt.getText().equals("ȯ�� ��û")) {
         int coinnum = UserDB.getCOIN(userID);
         if (coinnum == 0) {
            JOptionPane.showMessageDialog(this, "������ �����ϴ�.");
            return;
         }
         nowMyCoin = 0;
         haveCoinNumLB.setText("ȯ�� ������ ���� ���� : " + Integer.toString(nowMyCoin) + "��");
         UserDB.setCOIN(userID, nowMyCoin);
         getMoneyLB.setText("��� ������ �ݾ� : " + Integer.toString(nowMyCoin * 70) + "��");

         UserDB.setCOIN(userID, 0);
         MoneyLogDB.saveMONEYLOG(userID, coinnum * 70, coinnum, 1);

         String[][] data = MoneyLogDB.getMONEYLOG(userID, 1);
         String[] ListInfo = { "ȯ�� �ݾ�", "ȯ���� ���� ����", "�ð�" };
         remove(CtoMListPan);
         CtoMList = new JTable(data, ListInfo);
         CtoMListPan = new JScrollPane(CtoMList);
         CtoMList.setLayout(null);
         CtoMListPan.setBounds(15, 400, 450, 150);
         add(CtoMListPan);

         revalidate();
         CtoMListPan.repaint();
         JOptionPane.showMessageDialog(this, "�Է��Ͻ� ���¹�ȣ " + UserDB.getBANKNUMBER(userID) + "�� �ԱݵǾ����ϴ�.");

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
      String[] ListInfo = { "ID", "���� �ݾ�", "����", "���� �ð�" };
      String[][] dataB = MoneyLogDB.getSTATSMONEYLOG(0, date, statkind);
      JTable btc = new JTable(new NotEditTable(dataB, ListInfo));
      JScrollPane buyerToCoin = new JScrollPane(btc);

      btc.getColumn("ID").setPreferredWidth(70);
      btc.getColumn("���� �ݾ�").setPreferredWidth(70);
      btc.getColumn("����").setPreferredWidth(60);
      btc.getColumn("���� �ð�").setPreferredWidth(140);

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
      String[] ListInfoS = { "ID", "ȯ�� �ݾ�", "����", "ȯ�� �ð�" };
      String[][] dataS = MoneyLogDB.getSTATSMONEYLOG(1, date, statkind); //// (x ,x , 0 ) <--0���̸� ������ ������

      JTable stm = new JTable(new NotEditTable(dataS, ListInfoS));
      JScrollPane sellToMoney = new JScrollPane(stm);

      stm.getColumn("ID").setPreferredWidth(70);
      stm.getColumn("ȯ�� �ݾ�").setPreferredWidth(70);
      stm.getColumn("����").setPreferredWidth(60);
      stm.getColumn("ȯ�� �ð�").setPreferredWidth(140);
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
      yy.add("��ü");
      for (int i = 0; i < y.length; i++) {
         yy.add(y[i]);
      }

      Vector mm = new Vector();
      mm.add("��ü");
      for (int i = 1; i <= 12; i++) {
         mm.add(i);
      }

      Vector dd = new Vector();
      dd.add("��ü");
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

      conf = new JButton("�˻�");
      conf.setBounds(380, 20, 80, 30);
      conf.addActionListener(this);
      add(conf);

      JLabel ylb = new JLabel("��");
      JLabel mlb = new JLabel("��");
      JLabel dlb = new JLabel("��");
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

         if (boxM.getSelectedItem().equals("��ü")) {
            boxD.setSelectedItem("��ü");
            boxD.setEnabled(false);
            return;
         } else {
            boxD.setSelectedItem("��ü");
            boxD.setEnabled(true);
         }

         int a = (int) boxM.getSelectedItem();
         Calendar cal = Calendar.getInstance();
         cal.set(Calendar.YEAR, (int) boxY.getSelectedItem());
         cal.set(Calendar.DATE, 1);
         cal.set(Calendar.MONTH, a - 1);
         System.out.println(a);
         Vector dd = new Vector();
         dd.add("��ü");

         for (int i = 1; i <= cal.getActualMaximum(Calendar.DATE); i++) {
            dd.add(i);
         }
         remove(boxD);
         add(boxD = new JComboBox(dd));
         boxD.setBounds(250, 20, 80, 30);
         boxD.setSelectedItem("��ü");
         revalidate();
         repaint();

      } else if (e.getSource().equals(boxY)) {
         if (boxY.getSelectedItem().equals("��ü")) {
            boxM.setSelectedItem("��ü");
            boxM.setEnabled(false);
            boxD.setSelectedItem("��ü");
            boxD.setEnabled(false);
         } else {
            boxM.setSelectedItem("��ü");
            boxM.setEnabled(true);
            boxD.setSelectedItem("��ü");
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
         ef.salesIn.setText(ef.totprice+"��");
         ef.netIn.setText((int) (ef.totprice * 0.3 - ef.totbonus * 0.7) + " ��");

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

      // JLabel ����
      String[] labelName = { "COL", "MOL", "sales", "netgain" };
      String[] labelText = { "���� ���� ������ ����Ʈ", "���� ȯ�� ������ ����Ʈ", "�����", "������" };
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

      // �� ���� �ݾ� -- ��� �Һ��ڵ��� ���� �ݾ�(��) ++
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
      salesIn.setText(totprice + "��");

      // �� ���� �ݾ� �г� - (�ݾ�)
      salesPan.setLayout(null);
      salesPan.add(salesIn);
      salesPan.setBounds(30, 570, 100, 50);
      salesPan.setBackground(Color.LIGHT_GRAY);
      add(salesPan);
      
      // �� ������ �ݾ� --> (�� ���� *30%) - (���ʽ� ���� �ݾ�*70%) (��) ++
      netIn = new JLabel();
      netIn.setBounds(20, 0, 140, 50);
      netIn.setText((int) (totprice * 0.3 - totbonus * 0.7) + " ��");
      // �� ������ �ݾ� �г�
      netgainPan.setLayout(null);
      netgainPan.add(netIn);
      netgainPan.setBounds(260, 570, 100, 50);
      netgainPan.setBackground(Color.LIGHT_GRAY);
      add(netgainPan);
      
      // �� ���ʽ� ���� ���ϱ� - ������ �� �ʿ�
      String[][] dataC = MoneyLogDB.getMONEYLOG(2);
      for (int i = 0; i < dataC.length; i++) {
         try {
            totbonus += (long) df.parse(dataC[i][1]);
         } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }

      JButton chargeTot = new JButton("�������հ�");
      chargeTot.addActionListener(this);
      chargeTot.setBounds(360, 100, 100, 40);
      add(chargeTot);
      JButton monthCharge = new JButton("���� ����");
      monthCharge.addActionListener(this);
      monthCharge.setBounds(360, 160, 100, 40);
      add(monthCharge);
      JButton sellerList = new JButton("ȯ�����հ�");
      sellerList.addActionListener(this);
      sellerList.setBounds(360, 335, 100, 40);
      add(sellerList);
      JButton momthseller = new JButton("ȯ�� ����");
      momthseller.addActionListener(this);
      momthseller.setBounds(360, 395, 100, 40);
      add(momthseller);
      

      setVisible(true);
      
   }

   void refresh() {
      salesIn.setText(totprice + "��");
      netIn.setText((int) (totprice * 0.3 - totbonus * 0.7) + " ��");
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      //getSource �� �ּҸ� �����
      JButton b = (JButton) e.getSource();
      
      if(b.getText().equals("�������հ�")) {
         remove(kk);
         add(kk = new panelOne(
               cp.boxY.getSelectedItem() + "-" + cp.boxM.getSelectedItem() + "-" + cp.boxD.getSelectedItem(),
               1));
      }else if(b.getText().equals("���� ����")) {
         remove(kk);
         add(kk = new panelOne(
               cp.boxY.getSelectedItem() + "-" + cp.boxM.getSelectedItem() + "-" + cp.boxD.getSelectedItem(),
               0));
      }else if(b.getText().equals("ȯ�����հ�")) {
         remove(tt);
         add(tt = new panelTwo(
               cp.boxY.getSelectedItem() + "-" + cp.boxM.getSelectedItem() + "-" + cp.boxD.getSelectedItem(),
               1));
      }else if(b.getText().equals("ȯ�� ����")) {
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