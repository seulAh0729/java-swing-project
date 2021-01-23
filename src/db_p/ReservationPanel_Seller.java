package db_p;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
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
import java.sql.Savepoint;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import db_p.SelectMenuAct.SelectDay;

public class ReservationPanel_Seller extends JPanel { // 나중에 JPanel로 바꿀거고 마지막에 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 이거지워주면됌

	String userID;
	int x = 600;
	int y = 200; // 좌표 여분 절대값
	ProfileInOut pfio = ProfileInOut.getprofileInout();

	public ReservationPanel_Seller(String userID) {
		this.userID = userID;

		setLayout(null);
		setBounds(x, y, 500, 670);
		JPanel jp = new JPanel();
		jp.setBounds(0, 0, 475, 370);
		jp.setLayout(null);
		

		ImageIcon imgIcon = new ImageIcon(pfio.download(userID));
		Image img = imgIcon.getImage();
		img = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		imgIcon.setImage(img);

		JLabel lblImg = new JLabel();
		lblImg.setIcon(imgIcon);
		lblImg.setBounds(50, 80, 100, 100);
		JLabel lblDetail = new JLabel("[간단소개]");
		lblDetail.setBounds(180, 10, 200, 20);
		JTextArea lbprofile_text = new JTextArea(UserDB.getPROFILE_TEXT(userID));
		lbprofile_text.setBounds(180, 35, 200, 110);
		lbprofile_text.setLineWrap(true);
		lbprofile_text.setEditable(false);
		lbprofile_text.setBackground(new Color(245,245,245));
		JScrollPane lbprofile_text_scroll =  new JScrollPane(lbprofile_text);
		lbprofile_text_scroll.setBounds(180, 35, 200, 110);
		lbprofile_text_scroll.setBorder(BorderFactory.createEmptyBorder());
	
	 		JLabel lblPhone = new JLabel("[전화번호]");
			lblPhone.setBounds(180, 130+20, 200, 20);
			JLabel lblBName = new JLabel("[상호명]");
			lblBName.setBounds(180, 180+20, 200, 20);
			JLabel lblBAddress = new JLabel("[찾아오시는곳]");
			lblBAddress.setBounds(180, 230+20, 200, 20);
			JLabel rev_avg = new JLabel("[평점]");
			rev_avg.setBounds(180, 280+20, 200, 20);
			JLabel lblPhone_txt = new JLabel(UserDB.getPHONE(userID));
			lblPhone_txt.setBounds(180, 130+20+20, 200, 20);
			JLabel lblBName_txt = new JLabel(UserDB.getBUSINESSNAME(userID));
			lblBName_txt.setBounds(180, 180+20+20, 200, 20);
			JLabel lblBAddress_txt = new JLabel(UserDB.getBUSINESSADDRESS(userID));
			lblBAddress_txt.setBounds(180, 230+20+20, 200, 20);
			JLabel rev_avg_txt = new JLabel(ReviewDB.getAVGPOINT(userID)+" 점");
			rev_avg_txt.setBounds(180, 280+20+20, 200, 20);
			jp.add(lblImg);
			jp.add(lblDetail);
			jp.add(lbprofile_text_scroll);
			jp.add(lblPhone);
			jp.add(lblBName);
			jp.add(lblBAddress);
			jp.add(lblPhone_txt);
			jp.add(lblBName_txt);
			jp.add(lblBAddress_txt);
			jp.add(rev_avg);
			jp.add(rev_avg_txt);
		

		Selectpanel jp1 = new Selectpanel(userID);
		add(jp);
		add(jp1);
		setVisible(true);

	}

}

class Selectpanel extends JPanel implements ActionListener {
	String userID;
	SellReviewAct sr;
	public Selectpanel(String userID) {
		this.userID = userID;
		setBounds(5, 390, 475, 230);

		setLayout(new GridLayout(3, 1, 10, 10));
		String[] arr = { "예약관리", "예약메뉴표관리", "본인리뷰보기" };
		for (int i = 0; i < arr.length; i++) {
			JButton jb;
			add(jb = new JButton(arr[i]));
			jb.addActionListener(this);
		}
	}
	SelectMenuAct2 sa2;
	DetailFrame2 df2;
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton jb = (JButton) e.getSource();
		if (jb.getText().equals("예약관리")) {
			if(sa2==null) {
				sa2 = new SelectMenuAct2(userID, 700, 200, 0, this); // 일단 다 로그인한 점술가아이디만보냄
			}
		} else if (jb.getText().equals("예약메뉴표관리")) {
			if(df2==null) {
				df2 = new DetailFrame2(userID, 700, 200, this);	
			}
		} else if (jb.getText().equals("본인리뷰보기")) {
			if(sr==null) {
				sr = new SellReviewAct(userID, userID, 700, 200, this);
				if(sr.emptyChk) { // 리뷰가 빈상태로 만들어져있다면
					sr = null;
				}
			}
		}

	}
}


class DetailFrame2 extends JFrame implements ActionListener,WindowListener { // 메뉴표수정
	String userID;
	int x;
	int y;

	JButton btnFace;
	JButton btnSaju;
	JButton btnLove;
	JButton btnNewYear;
	JButton btnCompany;
	String[] arr;
	Selectpanel selectpanel;
	public DetailFrame2(String userID, int x, int y, Selectpanel selectpanel) {
		this.userID = userID;
		this.x = x;
		this.y = y;
		this.selectpanel = selectpanel;
		setBounds(x + 100, y, 500, 670);

		JPanel jp = new JPanel();
		jp.setLayout(null);
		JPanel jp2 = new JPanel(new GridLayout(5, 0));

		arr = MenuDB.getMENU(userID);
		btnFace = new JButton(arr[0].split(" ")[0] + arr[0].split(" ")[1]);
		btnFace.addActionListener(this);
		btnSaju = new JButton(arr[1].split(" ")[0] + arr[1].split(" ")[1]);
		btnSaju.addActionListener(this);
		btnLove = new JButton(arr[2].split(" ")[0] + arr[2].split(" ")[1]);
		btnLove.addActionListener(this);
		btnNewYear = new JButton(arr[3].split(" ")[0] + arr[3].split(" ")[1]);
		btnNewYear.addActionListener(this);
		btnCompany = new JButton(arr[4].split(" ")[0] + arr[4].split(" ")[1]);
		btnCompany.addActionListener(this);
		addWindowListener(this);
		jp2.add(btnFace);
		jp2.add(btnSaju);
		jp2.add(btnLove);
		jp2.add(btnNewYear);
		jp2.add(btnCompany);

		add(jp2);

		setVisible(true);
	}
	Sellmenuset smt;
	@Override
	public void actionPerformed(ActionEvent e) {
		if(smt==null) {
		if (e.getSource().equals(btnFace))
			smt=new Sellmenuset(userID, arr[0].split(" ")[0], arr[0].split(" ")[1], 0, this);
		else if (e.getSource().equals(btnSaju))
			smt=new Sellmenuset(userID, arr[1].split(" ")[0], arr[1].split(" ")[1], 1, this);
		else if (e.getSource().equals(btnLove))
			smt=new Sellmenuset(userID, arr[2].split(" ")[0], arr[2].split(" ")[1], 2, this);
		else if (e.getSource().equals(btnNewYear))
			smt=new Sellmenuset(userID, arr[3].split(" ")[0], arr[3].split(" ")[1], 3, this);
		else if (e.getSource().equals(btnCompany))
			smt=new Sellmenuset(userID, arr[4].split(" ")[0], arr[4].split(" ")[1], 4, this);
		}

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO 자동 생성된 메소드 스텁
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		selectpanel.df2 = null;
		
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

class Sellmenuset extends JFrame implements ActionListener, WindowListener {
	String userID;
	String menu;
	int price;
	int kind;
	JTextField jf1;
	JTextField jf2;

	DetailFrame2 detailFrame2;
	public Sellmenuset(String userID, String menu, String price, int kind, DetailFrame2 detailFrame2) {
		super();
		this.userID = userID;
		this.menu = menu;
		this.detailFrame2 = detailFrame2;
		this.price = Integer.parseInt(price);
		this.kind = kind;
		setBounds(800, 200, 300, 400);
		setLayout(new GridLayout(5, 1, 10, 10));
		JLabel jl1 = new JLabel("메뉴명   (메뉴 비활성화시 '비활성화' 입력");
		jf1 = new JTextField(menu);
		JLabel jl2 = new JLabel("금액");
		jf2 = new JTextField(price + "");
		JButton jb1 = new JButton("저장");
		jb1.addActionListener(this);
		add(jl1);
		add(jf1);
		add(jl2);
		add(jf2);
		add(jb1);
		addWindowListener(this);
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MenuDB.setMENU(userID, kind + 1, jf1.getText() + " " + jf2.getText());
		JOptionPane.showMessageDialog(null, "저장완료");
		setVisible(false);
		detailFrame2.smt = null;
		detailFrame2.selectpanel.df2=null;
		detailFrame2.dispose();
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO 자동 생성된 메소드 스텁
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		detailFrame2.smt = null;
		
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



class SelectMenuAct2 extends JFrame implements ActionListener, WindowListener { // 예약시간관리
	String userID; // user가 점술가
	// String sellerID; // seller가 손님?
	int x;
	int y;
	int menuChoice;
	SelectMenuAct2 me2;
	ArrayList<JToggleButton> btnTimeSeller;
	SelectMonth2 sm2;
	SelectDay2 sd2;
	SelectTime2 st2;
	String date;
	ArrayList<JToggleButton> btnDay;
	JButton btnMonthChange;
	int selectCalDay;
	int selectMon;
	BtnResExe2 bre2; // 시간까지다고르고 예약하기 버튼
	String selectCalStr; // 고른날짜
	int choiceTime; // 고른시간
	Selectpanel selectpanel ;
	public SelectMenuAct2(String userID, int x, int y, int menuChoice, Selectpanel selectpanel) {
		this.userID = userID;
		this.x = x;
		this.y = y;
		this.menuChoice = menuChoice;
		this.selectpanel = selectpanel;
		me2 = this;

		setLayout(null);
		setBounds(x + 100, y - 100, 825, 880);

		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(1, 7)); // 일월화수~
		jp.setBounds(0, 230, 500, 70);
		jp.setVisible(true);

		Font font = new Font("고딕", Font.BOLD, 20);
		for (int i = 0; i < 7; i++) {
			JLabel ll = new JLabel();
			String temp = Character.toString("일월화수목금토".charAt(i));
			ll.setText(temp);
			ll.setFont(font);
			ll.setHorizontalAlignment(JLabel.CENTER);
			if(i==0)
				ll.setForeground(Color.RED);
			else if(i==6)
				ll.setForeground(Color.BLUE);
			jp.add(ll);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.date = sdf.format(new Date());
		add(sm2 = new SelectMonth2(userID, x, y));
		add(jp);
		add(sd2 = new SelectDay2(date));
		
		selectMon = Calendar.getInstance().get(Calendar.MONTH)+1;
		selectCalDay = Calendar.getInstance().get(Calendar.DATE);
		add(st2 = new SelectTime2(userID, x, y, menuChoice, selectCalDay-1));
		add(bre2 = new BtnResExe2());
		addWindowListener(this);

		btnMonthChange.doClick();
		setVisible(true);
	}

	class SelectMonth2 extends JPanel implements ActionListener { // 몇월인지 선택패널

		String userID;
		int x;
		int y;
		JButton btnSave;
		String tempDate;
		Vector<Integer> monthList;
		JComboBox<Integer> monthListSeller;

		public SelectMonth2(String userID, int x, int y) {
			this.userID = userID;
			this.x = x;
			this.y = y;

			setBounds(0, 0, 500, 200);
			setLayout(null);

			Font font = new Font("고딕",Font.BOLD,30);
			Calendar today = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			JLabel lblYear = new JLabel(sdf.format(today.getTime()) + " 년");
			lblYear.setBounds(110, 80, 120, 30);
			lblYear.setFont(font);

			sdf = new SimpleDateFormat("MM");
			int monthChk = Integer.parseInt(sdf.format(today.getTime())); // 현재 월

			monthList = new Vector<Integer>();
			for (int i = monthChk; i <= 12; i++) {
				monthList.add(i);
			}
			monthListSeller = new JComboBox<Integer>(monthList);
			monthListSeller.addActionListener(this);
			font = new Font("고딕",Font.BOLD,25);
			monthListSeller.setFont(font);
			monthListSeller.setBounds(235, 70, 100, 40);

			JLabel lblMonth = new JLabel("월");
			lblMonth.setBounds(360, 80, 60, 30);
			font = new Font("고딕",Font.BOLD,25);
			lblMonth.setFont(font);

			btnMonthChange = new JButton("변경");
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
			// 여기서 바깥프레임 멤버변수로 바뀐 년도, 시간을
			if (e.getSource().equals(monthListSeller)) {
				selectMon = (int) monthListSeller.getSelectedItem(); 
				me2.remove(sd2);
				me2.add(sd2 = new SelectDay2(date));
				me2.revalidate();
				me2.repaint();
			} else {
				selectMon = (int) monthListSeller.getSelectedItem(); 
				me2.remove(sd2);
				me2.add(sd2 = new SelectDay2(date));
				me2.revalidate();
				me2.repaint();
			}

		}

	}

	
	class SelectDay2 extends JPanel implements ActionListener { // 날짜선택패널

		String date;

		public SelectDay2(String date) {
			this.date = date;

			setLayout(new GridLayout(5, 7));
			setBounds(0, 300, 500, 400);

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MONTH, selectMon - 1);

			ButtonGroup bg = new ButtonGroup();
			Font font = new Font("고딕", Font.BOLD, 20);
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
			
			me2.remove(st2);
			me2.add(st2 = new SelectTime2(userID, x, y, menuChoice, selectCalDay));			
			me2.revalidate();
			me2.repaint();
			
        
		}
	}

	class SelectTime2 extends JPanel implements ActionListener { // 시간선택패널

		String userID;
		int selectCalDay;
		int x;
		int y;
		int menuChoice;
		boolean chk = true;

		public SelectTime2(String userID, int x, int y, int menuChoice, int selectCalDay) {
			this.userID = userID;
			this.x = x;
			this.y = y;
			this.menuChoice = menuChoice;
			this.selectCalDay = selectCalDay; // 달력에서누른날짜
			selectCalStr = (selectCalDay + 1) + ""; // +1 해야맞음

			setBounds(503, 93, 300, 750);
			setLayout(new GridLayout(24, 0));

			SimpleDateFormat sss = new SimpleDateFormat("yyyy-MM-dd");
			Date d = new Date();
			d.setMonth(selectMon - 1);
			d.setDate(selectCalDay + 1);

			date = sss.format(d);

			String[] schedule = null;
			if (ScheduleDB.getScheduleDB(userID, date)[0] == null) {
			
				ScheduleDB.makeSchedule(userID, date);
				schedule = ScheduleDB.getScheduleDB(userID, date);
				
			} else {
				schedule = ScheduleDB.getScheduleDB(userID, date);
				System.out.println("exist");
			}

			btnTimeSeller = new ArrayList<JToggleButton>();

			for (int i = 0; i < 24; i++) {
				
					JToggleButton bb;
					if (i < 9)
						bb = new JToggleButton("0" + i + " ~ " + "0" + (i + 1) + "시");
					else if (i == 9)
						bb = new JToggleButton("0" + i + " ~ " + (i + 1) + "시");
					else
						bb = new JToggleButton(i + " ~ " + (i + 1) + "시");

					SimpleDateFormat sdf = new SimpleDateFormat("HH");
					SimpleDateFormat sdf2 = new SimpleDateFormat("dd");
					String a = sdf.format(new Date());

					if ((schedule[i].equals("true"))) {
						bb.setSelected(true);
						bb.setText(bb.getText()+":"+" 예약가능 ");
					} else if ((schedule[i].equals("false"))) {
						bb.setSelected(false);
						bb.setText(bb.getText()+":"+" 예약불가 ");
					} else {
						bb.setEnabled(false);
						bb.setText("["+schedule[i] + "] 님 예약완료");
					}
					bb.addActionListener(this);
					btnTimeSeller.add(bb);
					add(btnTimeSeller.get(i));
				
				
			}

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JToggleButton b = (JToggleButton) e.getSource();
			if(b.getText().contains("예약가능")) {
				System.out.println("버튼바뀌나");
				b.setText(b.getText().split(":")[0]+":"+" 예약불가");
			}
			else {
				System.out.println("가능으로 바뀜?");
				b.setText(b.getText().split(":")[0]+":"+" 예약가능");
			}
				
			
		}


	}

	class BtnResExe2 extends JButton implements ActionListener { // 예약하기 버튼

		public BtnResExe2() {
			setLayout(null);
			setBounds(590, 30, 120, 40);
			setText("저장하기");
			addActionListener(this);

			setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			for (JToggleButton jT : btnTimeSeller) {
				if (!jT.isEnabled()) {
					
				} else if (jT.getText().contains("예약가능")) { 
					System.out.println("가능 들어옴");
					ScheduleDB.setSellerSchedule(userID, date, "time" + btnTimeSeller.indexOf(jT), "true");
				} else if(jT.getText().contains("예약불가")){
					System.out.println("불가능 들어옴");
					ScheduleDB.setSellerSchedule(userID, date, "time" + btnTimeSeller.indexOf(jT), "false");
				}
			}
			JOptionPane.showMessageDialog(null, "예약 가능 시간이 변경 되었습니다");
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
		selectpanel.sa2 = null;
		
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