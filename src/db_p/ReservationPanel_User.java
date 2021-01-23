package db_p;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

import db_p.SelectMenuAct2.SelectTime2;

public class ReservationPanel_User extends JPanel { // ���߿� JPanel�� �ٲܰŰ� �������� setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); �̰������ָ��

	String userID;
	int x = 600;
	int y = 200; // ��ǥ ���� ���밪
	DetailFrame preDetailFrame; // ���� ���� DetailFrame
	public ReservationPanel_User(String userID) {
		this.userID = userID;

		setLayout(null);
		setBounds(x, y, 500, 670);
//         setResizable(false);
		JPanel jps = new JPanel();
		jps.setBounds(0, 0, 500, 670);
		jps.setLayout(null);
		JPanel jp = new JPanel();

		int row = UserDB.getSELLER().size();
		if (row < 5) {
			row = 5;
		}
		jp.setLayout(new GridLayout(row, 1));
		for (sellUser ss : UserDB.getSELLER()) {
			jp.add(new SellerListPane(userID, ss.id, x, y,this)); // �α������Ϲ��������̵�, ��ü���������̵�
		}
		for (int i = UserDB.getSELLER().size(); i < 5; i++) { // �������� 5���� �������� Grid ������
			jp.add(new JPanel());
		}

		Dimension size = new Dimension();
		size.setSize(400, (row * 100) + 50);
		jp.setPreferredSize(size);
		JScrollPane scp = new JScrollPane(jp);

		scp.setBounds(0, 0, 485, 630);
		jps.add(scp);
		scp.setVisible(true);
		add(jps);
		setVisible(true);

	}
}

class SellerListPane extends JPanel implements ActionListener {
	String userID;
	String sellerID;
	int x;
	int y;
	ReservationPanel_User rs ; //���� �θ� �г�
	public SellerListPane(String userID, String sellerID, int x, int y,ReservationPanel_User rs) { // �α������Ϲ��������̵�, ��ü���������̵��� �� �г�������
		this.userID = userID;
		this.sellerID = sellerID;
		this.x = x;
		this.y = y;
		this.rs= rs;

		setLayout(new GridLayout(2, 0));

		JLabel lblSellerId = new JLabel();
		lblSellerId.setOpaque(true);
		lblSellerId.setHorizontalAlignment(JLabel.CENTER);
		lblSellerId.setText("������ ���̵� : [ "+sellerID+" ]        ���� : "+ReviewDB.getAVGPOINT(sellerID));
		JButton btnDetail = new JButton("��������");
		btnDetail.addActionListener(this);

		add(lblSellerId);
		add(btnDetail);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) { // ��������
		if(rs.preDetailFrame==null) {
			rs.preDetailFrame = new DetailFrame(userID, sellerID, x, y, this);
		}
	}
}

class DetailFrame extends JFrame implements ActionListener, WindowListener { // ��������
	String userID;
	String sellerID;
	int x;
	int y;
	ProfileInOut pfio;
	JButton btnReview;
	JButton btnFace;
	JButton btnSaju;
	JButton btnLove;
	JButton btnNewYear;
	JButton btnCompany;
	String[] getMenu;
	JPanel jp;
	// �ߺ����� ����(â)
	SelectMenuAct sm;
	SellReviewAct sr;
	SellerListPane sl; // ���� �θ� �г�
	public DetailFrame(String userID, String sellerID, int x, int y,SellerListPane sl) {
		this.userID = userID;
		this.sellerID = sellerID;
		this.x = x;
		this.y = y;
		this.sl = sl;
		this.pfio = ProfileInOut.getprofileInout();
		setLayout(new GridLayout(2, 1));
		setBounds(x + 100, y, 400, 670);
		
		jp = new JPanel();
		jp.setLayout(null);
		JPanel jp2 = new JPanel(new GridLayout(6, 0));

		ImageIcon imgIcon = new ImageIcon(pfio.download(sellerID));
		Image img = imgIcon.getImage();
		img = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		imgIcon.setImage(img);

		JLabel lblImg = new JLabel();
		lblImg.setIcon(imgIcon);
		lblImg.setBounds(50, 80, 100, 100);
		JLabel lblDetail = new JLabel("[���ܼҰ�]");
		lblDetail.setBounds(180, 10, 200, 20);
		JTextArea lbprofile_text = new JTextArea(UserDB.getPROFILE_TEXT(sellerID));
		lbprofile_text.setBounds(180, 35, 200, 110);
		lbprofile_text.setLineWrap(true);
		lbprofile_text.setEditable(false);
		lbprofile_text.setBackground(new Color(245,245,245));
		JScrollPane lbprofile_text_scroll =  new JScrollPane(lbprofile_text);
		lbprofile_text_scroll.setBounds(180, 35, 200, 110);
		lbprofile_text_scroll.setBorder(BorderFactory.createEmptyBorder());
		JLabel lblPhone = new JLabel("[��ȭ��ȣ]");
		lblPhone.setBounds(180, 130+20, 200, 20);
		JLabel lblBName = new JLabel("[��ȣ��]");
		lblBName.setBounds(180, 180+20, 200, 20);
		JLabel lblBAddress = new JLabel("[ã�ƿ��ô°�]");
		lblBAddress.setBounds(180, 230+20, 200, 20);
		JLabel lblPhone_txt = new JLabel(UserDB.getPHONE(sellerID));
		lblPhone_txt.setBounds(180, 130+20+20, 200, 20);
		JLabel lblBName_txt = new JLabel(UserDB.getBUSINESSNAME(sellerID));
		lblBName_txt.setBounds(180, 180+20+20, 200, 20);
		JLabel lblBAddress_txt = new JLabel(UserDB.getBUSINESSADDRESS(sellerID));
		lblBAddress_txt.setBounds(180, 230+20+20, 200, 20);
		
		jp.add(lblImg);
		jp.add(lblDetail);
		jp.add(lbprofile_text_scroll);
		jp.add(lblPhone);
		jp.add(lblBName);
		jp.add(lblBAddress);
		jp.add(lblPhone_txt);
		jp.add(lblBName_txt);
		jp.add(lblBAddress_txt);

		btnReview = new JButton("���亸��");
		btnReview.addActionListener(this);
		getMenu = MenuDB.getMENU(sellerID);
		btnFace = new JButton(getMenu[0]);
		if(getMenu[0].contains("��Ȱ��ȭ")) {
			btnFace.setEnabled(false);
			btnFace.setText("�޴� ����");
		}
		btnFace.addActionListener(this);
		
		btnSaju = new JButton(getMenu[1]);
		if(getMenu[1].contains("��Ȱ��ȭ")) {
			btnSaju.setEnabled(false);
			btnSaju.setText("�޴� ����");
		}
		btnSaju.addActionListener(this);
		
		btnLove = new JButton(getMenu[2]);
		if(getMenu[2].contains("��Ȱ��ȭ")) {
			btnLove.setEnabled(false);
			btnLove.setText("�޴� ����");
		}
		btnLove.addActionListener(this);
		
		btnNewYear = new JButton(getMenu[3]);
		if(getMenu[3].contains("��Ȱ��ȭ")) {
			btnNewYear.setEnabled(false);
			btnNewYear.setText("�޴� ����");
		}
		btnNewYear.addActionListener(this);
		
		btnCompany = new JButton(getMenu[4]);
		if(getMenu[4].contains("��Ȱ��ȭ")) {
			btnCompany.setEnabled(false);
			btnCompany.setText("�޴� ����");
		}
		btnCompany.addActionListener(this);

		jp2.add(btnReview);
		jp2.add(btnFace);
		jp2.add(btnSaju);
		jp2.add(btnLove);
		jp2.add(btnNewYear);
		jp2.add(btnCompany);

		add(jp);
		add(jp2);
		addWindowListener(this);
		setVisible(true);
	}
	
	public DetailFrame(String sellerID, int userkind) {
		
		setBounds(x + 100, y, 400, 670);
		this.pfio = ProfileInOut.getprofileInout();
		jp = new JPanel();
		jp.setLayout(null);
		

		ImageIcon imgIcon = new ImageIcon(pfio.download(sellerID));
		Image img = imgIcon.getImage();
		img = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		imgIcon.setImage(img);

		JLabel lblImg = new JLabel();
		lblImg.setIcon(imgIcon);
		lblImg.setBounds(50, 80, 100, 100);
		JLabel lblDetail = new JLabel("[���ܼҰ�]");
		lblDetail.setBounds(180, 10, 200, 20);
		JTextArea lbprofile_text = new JTextArea(UserDB.getPROFILE_TEXT(sellerID));
		lbprofile_text.setBounds(180, 35, 200, 110);
		lbprofile_text.setLineWrap(true);
		lbprofile_text.setEditable(false);
		lbprofile_text.setBackground(new Color(245,245,245));
		JScrollPane lbprofile_text_scroll =  new JScrollPane(lbprofile_text);
		lbprofile_text_scroll.setBounds(180, 35, 200, 110);
		lbprofile_text_scroll.setBorder(BorderFactory.createEmptyBorder());
		if(userkind==0) {
			JLabel lblPhone = new JLabel("[��ȭ��ȣ]");
			lblPhone.setBounds(180, 130+20, 200, 20);
			JLabel lblBName = new JLabel("[��ȣ��]");
			lblBName.setBounds(180, 180+20, 200, 20);
			JLabel lblBAddress = new JLabel("[ã�ƿ��ô°�]");
			lblBAddress.setBounds(180, 230+20, 200, 20);
			JLabel lblPhone_txt = new JLabel(UserDB.getPHONE(sellerID));
			lblPhone_txt.setBounds(180, 130+20+20, 200, 20);
			JLabel lblBName_txt = new JLabel(UserDB.getBUSINESSNAME(sellerID));
			lblBName_txt.setBounds(180, 180+20+20, 200, 20);
			JLabel lblBAddress_txt = new JLabel(UserDB.getBUSINESSADDRESS(sellerID));
			lblBAddress_txt.setBounds(180, 230+20+20, 200, 20);
			jp.add(lblImg);
			jp.add(lblDetail);
			jp.add(lbprofile_text_scroll);
			jp.add(lblPhone);
			jp.add(lblBName);
			jp.add(lblBAddress);
			jp.add(lblPhone_txt);
			jp.add(lblBName_txt);
			jp.add(lblBAddress_txt);
			
		}else {
			JLabel lblPhone = new JLabel("[��ȭ��ȣ]");
			lblPhone.setBounds(180, 130+20, 200, 20);
			JLabel lblPhone_txt = new JLabel(UserDB.getPHONE(sellerID));
			lblPhone_txt.setBounds(180, 130+20+20, 200, 20);
			jp.add(lblImg);
			jp.add(lblDetail);
			jp.add(lbprofile_text_scroll);
			jp.add(lblPhone);
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(sr);
		if (e.getSource().equals(btnReview) && sr == null)
			if(sr==null) {
				
				sr = new SellReviewAct(userID, sellerID, x, y, this);
				if(sr.emptyChk) { // ���䰡 ����·� ��������ִٸ�
					sr = null;
				}
			}

		if (sm == null) {
			if (e.getSource().equals(btnFace))
				sm = new SelectMenuAct(userID, sellerID, x, y, 0, getMenu, this);
			else if (e.getSource().equals(btnSaju))
				sm = new SelectMenuAct(userID, sellerID, x, y, 1, getMenu, this);
			else if (e.getSource().equals(btnLove))
				sm = new SelectMenuAct(userID, sellerID, x, y, 2, getMenu, this);
			else if (e.getSource().equals(btnNewYear))
				sm = new SelectMenuAct(userID, sellerID, x, y, 3, getMenu, this);
			else if (e.getSource().equals(btnCompany))
				sm = new SelectMenuAct(userID, sellerID, x, y, 4, getMenu, this);
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) { // �����찡 ������ ���ÿ�
		// TODO Auto-generated method stub
		sr = null;
		sm = null;
		sl.rs.preDetailFrame = null;
	}

	@Override
	public void windowClosed(WindowEvent e) { // �����찡 ����������
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

class SellReviewAct extends JFrame implements WindowListener {
	String userID;
	String sellerID;
	int x;
	int y;
	DetailFrame df;
	Selectpanel sp;
	boolean emptyChk;
	public SellReviewAct(String userID, String sellerID, int x, int y, DetailFrame df) {
		this.userID = userID;
		this.sellerID = sellerID;
		this.x = x;
		this.y = y;
		this.df=  df;
		setLayout(null);
		setBounds(x + 200, y, 400, 670);
		emptyChk = false; // ���䰡 ������� üũ
		JPanel showReviewP = new JPanel();
		showReviewP.setLayout(null);
		showReviewP.setBounds(0, 0, 400, 670);
		JPanel showReviewP2 = new JPanel();
		showReviewP2.setLayout(new GridLayout(ReviewDB.getREVIEW(sellerID).size(), 1));

		ArrayList<String> nomComent = new ArrayList<String>();
		ArrayList<Integer> nomPoint = new ArrayList<Integer>();

		ArrayList<Review> reviews = ReviewDB.getREVIEW(sellerID);
		if(reviews.isEmpty()) {
			JOptionPane.showMessageDialog(null, "�ش� ���������� ������ ���䰡 �����ϴ�.");
			emptyChk = true; // ���䰡��
			return;
		}
		for (Review rv : reviews) {
			nomComent.add(rv.coment);
			nomPoint.add(rv.point);
		}

		String str = "";
		int j = 0;
		for (String comm : nomComent) {
			str += comm + "\n���� : [" + nomPoint.get(j) + "] " + "\n\n";
			j++;
		}

		JTextArea t1 = new JTextArea(str);
		t1.setBounds(0, 0, 400, 670);
		t1.setLineWrap(true);
		t1.setFont(new Font("���", Font.BOLD, 15));
		t1.setEditable(false);

		JScrollPane scp = new JScrollPane(t1);
		scp.setBounds(0, 0, 800, 600);
		scp.setVisible(true);
		addWindowListener(this);
		showReviewP.add(t1);
		add(showReviewP);
		setVisible(true);
	}
	
	public SellReviewAct(String userID, String sellerID, int x, int y, Selectpanel sp) {
		this.userID = userID;
		this.sellerID = sellerID;
		this.x = x;
		this.y = y;
		this.sp=  sp;
		emptyChk = false; // ���䰡 ������� üũ
		setLayout(null);
		setBounds(x + 200, y, 400, 670);

		JPanel showReviewP = new JPanel();
		showReviewP.setLayout(null);
		showReviewP.setBounds(0, 0, 400, 670);
		JPanel showReviewP2 = new JPanel();
		showReviewP2.setLayout(new GridLayout(ReviewDB.getREVIEW(sellerID).size(), 1));

		ArrayList<String> nomComent = new ArrayList<String>();
		ArrayList<Integer> nomPoint = new ArrayList<Integer>();
		ArrayList<Review> reviews = ReviewDB.getREVIEW(sellerID);
		if(reviews.isEmpty()) {
			JOptionPane.showMessageDialog(null, "�������Կ��� ������ ���䰡 �����ϴ�.");
			emptyChk = true; // ���䰡��
			return;
		}
		for (Review rv : reviews) {
			nomComent.add(rv.coment);
			nomPoint.add(rv.point);
		}

		String str = "";
		int j = 0;
		for (String comm : nomComent) {
			str += comm + "\n���� : [" + nomPoint.get(j) + "] " + "\n\n";
			j++;
		}

		JTextArea t1 = new JTextArea(str);
		t1.setBounds(0, 0, 400, 670);
		t1.setLineWrap(true);
		t1.setFont(new Font("���", Font.BOLD, 15));
		t1.setEditable(false);

		JScrollPane scp = new JScrollPane(t1);
		scp.setBounds(0, 0, 800, 600);
		scp.setVisible(true);
		addWindowListener(this);
		showReviewP.add(t1);
		add(showReviewP);
		setVisible(true);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO �ڵ� ������ �޼ҵ� ����
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if(df!=null) {
			df.sr =null;
		}else {
			sp.sr = null;
		}
		
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

class SelectMenuAct extends JFrame implements ActionListener, WindowListener { // �����޴��� �� �� â
	String userID;
	String sellerID;
	int x;
	int y;
	int menuChoice;
	SelectMenuAct me;
	ArrayList<JToggleButton> btnTimeSeller;
	String[] getMenu;
	SelectMonth sm;
	SelectDay sd;
	SelectTime st;
	String date;
	ArrayList<JToggleButton> btnDay;
	JButton btnMonthChange;
	int selectCalDay;
	int selectMon;
	BtnResExe bre; // �ð������ٰ��� �����ϱ� ��ư
	String selectCalStr; // ����¥
	int choiceTime; // ���ð�
	DetailFrame df;

	static boolean chkInOut = true;

	public SelectMenuAct(String userID, String sellerID, int x, int y, int menuChoice, String[] getMenu,
			DetailFrame df) {
		this.userID = userID;
		this.sellerID = sellerID;
		this.x = x;
		this.y = y;
		this.menuChoice = menuChoice;
		this.getMenu = getMenu;
		me = this;
		this.df = df;

		setLayout(null);
		setBounds(x + 300, y - 100, 825, 880);

		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(1, 7)); // �Ͽ�ȭ��~
		jp.setBounds(0, 230, 500, 70);
		jp.setVisible(true);

		Font font = new Font("���", Font.BOLD, 20);
		for (int i = 0; i < 7; i++) {
			JLabel ll = new JLabel();
			String temp = Character.toString("�Ͽ�ȭ�������".charAt(i));
			ll.setText(temp);
			ll.setFont(font);
			ll.setHorizontalAlignment(JLabel.CENTER);
			if (i == 0)
				ll.setForeground(Color.RED);
			else if (i == 6)
				ll.setForeground(Color.BLUE);
			jp.add(ll);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.date = sdf.format(new Date());
		add(sm = new SelectMonth(userID, sellerID, x, y));
		add(jp);
		add(sd = new SelectDay(date));

		selectMon = Calendar.getInstance().get(Calendar.MONTH) + 1;
		selectCalDay = Calendar.getInstance().get(Calendar.DATE);
		add(st = new SelectTime(userID, sellerID, x, y, menuChoice, selectCalDay - 1));
		add(bre = new BtnResExe());
		addWindowListener(this);

		btnMonthChange.doClick();
		setVisible(true);
	}

	class SelectMonth extends JPanel implements ActionListener { // ������� �����г�

		String userID;
		String sellerID;
		int x;
		int y;
		JButton btnSave;
		String tempDate;
		Vector<Integer> monthList;
		JComboBox<Integer> monthListSeller;

		public SelectMonth(String userID, String sellerID, int x, int y) {
			this.userID = userID;
			this.sellerID = sellerID;
			this.x = x;
			this.y = y;

			setBounds(0, 0, 500, 200);
			setLayout(null);

			Font font = new Font("���", Font.BOLD, 30);
			Calendar today = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			JLabel lblYear = new JLabel(sdf.format(today.getTime()) + " ��");
			lblYear.setBounds(110, 80, 120, 30);
			lblYear.setFont(font);

			sdf = new SimpleDateFormat("MM");
			int monthChk = Integer.parseInt(sdf.format(today.getTime())); // ���� ��

			monthList = new Vector<Integer>();
			for (int i = monthChk; i <= 12; i++) {
				monthList.add(i);
			}
			monthListSeller = new JComboBox<Integer>(monthList);
			monthListSeller.addActionListener(this);
			monthListSeller.setBounds(235, 70, 100, 40);
			monthListSeller.addActionListener(this);
			font = new Font("���", Font.BOLD, 25);
			monthListSeller.setFont(font);

			JLabel lblMonth = new JLabel("��");
			lblMonth.setBounds(360, 80, 60, 30);
			font = new Font("���", Font.BOLD, 25);
			lblMonth.setFont(font);

			btnMonthChange = new JButton("����");
			btnMonthChange.setBounds(370, 35, 80, 30);
			btnMonthChange.addActionListener(this);
			btnMonthChange.setVisible(false);

			add(lblYear);
			add(monthListSeller);
			add(lblMonth);
			add(btnMonthChange);

			setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// ���⼭ �ٱ������� ��������� �ٲ� �⵵, �ð���
			if (e.getSource().equals(monthListSeller)) {
				selectMon = (int) monthListSeller.getSelectedItem(); // ����� �������
				me.remove(sd);
				me.add(sd = new SelectDay(date));
				me.revalidate();
				me.repaint();
			} else {
				selectMon = (int) monthListSeller.getSelectedItem();
				me.remove(sd);
				me.add(sd = new SelectDay(date));
				me.revalidate();
				me.repaint();
			}

		}

	}

	class SelectDay extends JPanel implements ActionListener { // ��¥�����г�

		String date;

		public SelectDay(String date) {
			this.date = date;

			setLayout(new GridLayout(5, 7));
			setBounds(0, 300, 500, 400);

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MONTH, selectMon - 1);

			ButtonGroup bg = new ButtonGroup();
			Font font = new Font("���", Font.BOLD, 20);
			btnDay = new ArrayList<JToggleButton>();
			for (int i = 1; i <= cal.getActualMaximum(Calendar.DATE); i++) {
				cal.set(Calendar.DATE, i);
				JToggleButton btn;
				if (i == 1) {
					for (int j = 1; j < cal.get(Calendar.DAY_OF_WEEK); j++) {
						btn = new JToggleButton();
						btn.setEnabled(false);
						btn.setVisible(false);
						add(btn);
					}
				}
				btn = new JToggleButton(i + "");
				btn.setFont(font);
				btn.addActionListener(this);
				bg.add(btn);
				btnDay.add(btn);
				add(btn);
			}
			setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < btnDay.size(); i++) {
				if (btnDay.get(i).equals(e.getSource()))
					selectCalDay = i;
			}

			me.remove(st);
			me.add(st = new SelectTime(userID, sellerID, x, y, menuChoice, selectCalDay));
			me.revalidate();
			me.repaint();
		}
	}

	class SelectTime extends JPanel implements ActionListener { // �ð������г�

		String userID;
		String sellerID;
		int selectCalDay;
		int x;
		int y;
		int menuChoice;
		boolean chk = true;

		public SelectTime(String userID, String sellerID, int x, int y, int menuChoice, int selectCalDay) {
			this.userID = userID;
			this.sellerID = sellerID;
			this.x = x;
			this.y = y;
			this.menuChoice = menuChoice;
			this.selectCalDay = selectCalDay; // �޷¿���������¥
			selectCalStr = (selectCalDay + 1) + ""; // +1 �ؾ߸���

//			setBounds(503, 93, 480, 750);
			setBounds(503, 93, 300, 750);
			setLayout(new GridLayout(24, 0));

			SimpleDateFormat sss = new SimpleDateFormat("yyyy-MM-dd");
			Date d = new Date();
			d.setMonth(selectMon - 1);
			d.setDate(selectCalDay + 1);

			date = sss.format(d);

			String[] schedule = null;
			if (ScheduleDB.getScheduleDB(sellerID, date)[0] == null) {
				chk = false;
			} else {
				schedule = ScheduleDB.getScheduleDB(sellerID, date);
			}

			btnTimeSeller = new ArrayList<JToggleButton>();
			ButtonGroup bg = new ButtonGroup();

			for (int i = 0; i < 24; i++) {
				if (chk) { // �ش��ϴ� ��¥�� �������� �����Ǿ�������
					JToggleButton bb;
					if (i < 9)
						bb = new JToggleButton("0" + i + " ~ " + "0" + (i + 1) + "��");
					else if (i == 9)
						bb = new JToggleButton("0" + i + " ~ " + (i + 1) + "��");
					else
						bb = new JToggleButton(i + " ~ " + (i + 1) + "��");

					SimpleDateFormat sdf = new SimpleDateFormat("HH");
					SimpleDateFormat sdf2 = new SimpleDateFormat("dd");
					SimpleDateFormat sdf3 = new SimpleDateFormat("MM");
					SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd");
					String a = sdf.format(new Date());
					String b = sdf3.format(new Date());
					Date nowdate =null;
					try {
						 nowdate = sdf4.parse(sdf4.format(new Date()));
					} catch (ParseException e) {
						// TODO �ڵ� ������ catch ���
						e.printStackTrace();
					}
					
					if (schedule[i].equals("false")
							|| (date.split("-")[2].equals(sdf2.format(new Date())) && (i <= Integer.parseInt(a)) &&true )) {
						bb.setEnabled(false);
						bb.setText("����Ұ�");
					} else if (d.before(nowdate)) {
						bb.setEnabled(false);
						bb.setText("����Ұ�");
					}else if (schedule[i].equals(userID)) {
						bb.setEnabled(false);
						bb.setText("���� ����ð�");
					} else if (schedule[i] != null && !schedule[i].equals("true")) {
						bb.setEnabled(false);
						bb.setText("�ٸ� �� ����Ϸ�");
					}
					bg.add(bb);
					bb.addActionListener(this);
					btnTimeSeller.add(bb);
					add(btnTimeSeller.get(i));
				} else { // �ش罺������ �����Ǿ�����������
					JToggleButton bb;
					bb = new JToggleButton("����Ұ�");
					bb.addActionListener(this);
					bb.setEnabled(false);
					btnTimeSeller.add(bb);
					add(btnTimeSeller.get(i));
				}
			}

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			for (int i = 0; i < btnTimeSeller.size(); i++) {
				if (e.getSource().equals(btnTimeSeller.get(i)))
					choiceTime = i;
			}
		}

	}

	class BtnResExe extends JButton implements ActionListener { // �����ϱ� ��ư

		public BtnResExe() {
			setLayout(null);
			setBounds(590, 30, 120, 40);
			setText("�����ϱ�");
			addActionListener(this);

			setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			new ChkFrame(userID, sellerID, x, y, btnTimeSeller, selectMon, getMenu, menuChoice, selectCalStr, me,
					btnDay);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		df.sr = null;
		df.sm = null;
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

class ChkFrame extends JFrame implements ActionListener {

	String userID;
	String sellerID;
	int x;
	int y;
	int selectMon;
	ArrayList<JToggleButton> btnTimeSeller;
	JLabel lbl;
	JLabel lbl2;
	JButton btnChkY;
	JButton btnChkN;
	int chkTog;
	String[] getMenu;
	int menuChoice; // ���޴�
	String selectCalStr; // ���೯¥
	SelectMenuAct sm;
	ArrayList<JToggleButton> btnDay;

	public ChkFrame(String userID, String sellerID, int x, int y, ArrayList<JToggleButton> btnTimeSeller, int selectMon,
			String[] getMenu, int menuChoice, String selectCalStr, SelectMenuAct me, ArrayList<JToggleButton> btnDay) {
		this.userID = userID;
		this.sellerID = sellerID;
		this.x = x;
		this.y = y;
		this.selectMon = selectMon; // �����ѿ�
		this.btnTimeSeller = btnTimeSeller;
		this.getMenu = getMenu;
		this.menuChoice = menuChoice;
		this.selectCalStr = selectCalStr; // ������ ��¥
		this.selectMon = selectMon;
		this.sm = me;
		this.btnDay = btnDay;

		setLayout(null);
		setBounds(x + 400, y, 400, 200);
		chkTog = 0;
		for (int i = 0; i < btnTimeSeller.size(); i++) {
			if (btnTimeSeller.get(i).isSelected())
				chkTog = i; // ������ �ð�
		}
		System.out.println(Arrays.deepToString(getMenu));
		lbl = new JLabel("[" + selectMon + "]��    [" + selectCalStr + "]��     [" + chkTog + "]��     ["
				+ getMenu[menuChoice].split(" ")[0] + "]     �����Ͻðڽ��ϱ�");
		lbl.setBounds(30, 30, 350, 30);
		lbl2 = new JLabel("[" + getMenu[menuChoice].split(" ")[1] + "] ���� �����˴ϴ�");
		lbl2.setBounds(130, 70, 350, 30);
		btnChkY = new JButton("��");
		btnChkY.setBounds(100, 120, 50, 30);
		btnChkY.addActionListener(this);
		btnChkN = new JButton("�ƴϿ�");
		btnChkN.setBounds(200, 120, 80, 30);
		btnChkN.addActionListener(this);

		add(lbl);
		add(lbl2);
		add(btnChkY);
		add(btnChkN);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		Date dd = new Date();
		dd.setMonth(selectMon);
		int dayy = Integer.parseInt(selectCalStr);
		dd.setDate(dayy);
		String dd2 = sdf2.format(dd);

		String[] scheduleTemp = null;
		scheduleTemp = ScheduleDB.getScheduleDB(sellerID, dd2);

		if (e.getSource().equals(btnChkY)) { //// DB ������������ where�� true�� ó��

			if (UserDB.getCOIN(userID) < (Integer.parseInt(getMenu[menuChoice].split(" ")[1]))) {
				JOptionPane.showMessageDialog(null, "������ �����մϴ�");
				setVisible(false);
			} else {
				String str = Calendar.getInstance().get(Calendar.YEAR) + "-" + selectMon + "-" + selectCalStr + " "
						+ chkTog + ":00:00";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					// ä�ø���Ʈ ����
					ChatListDB.saveCHATLIST(sellerID, userID, sdf.parse(str), getMenu[menuChoice].split(" ")[0]);
					ReservationDB.saveRESERVATION(sellerID, userID, sdf.parse(str), getMenu[menuChoice].split(" ")[0]);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// ���� ��������
				UserDB.setCOIN(userID,
						(UserDB.getCOIN(userID) - (Integer.parseInt(getMenu[menuChoice].split(" ")[1]))));
				// ������ ��������
				UserDB.setCOIN(sellerID,
						(UserDB.getCOIN(sellerID) + (Integer.parseInt(getMenu[menuChoice].split(" ")[1]))));

				// ���� DB����
				int ss = Integer.parseInt(selectCalStr);
				SimpleDateFormat sss = new SimpleDateFormat("yyyy-MM-dd");
				Date d = new Date();
				d.setMonth(selectMon - 1);
				d.setDate(ss);
				String date = sss.format(d);

				int aa = Integer.parseInt(selectCalStr);
				if (ScheduleDB.setSchedule(sellerID, date, "time" + chkTog, userID)) {
					JOptionPane.showMessageDialog(null, "������ �Ϸ�Ǿ����ϴ�");
					btnDay.get(aa - 1).doClick();
					sm.st.repaint();
					setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "�������");
					btnDay.get(aa - 1).doClick();
					sm.st.repaint();
					setVisible(false);
				}
			}

		} else if (e.getSource().equals(btnChkN)) {
			setVisible(false);
		} else {
			JOptionPane.showMessageDialog(null, "�̹� ����Ǿ����ϴ�. �ٽ� �������ּ���");
			setVisible(false);
		}

	}

}