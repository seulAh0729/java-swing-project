package db_p;


import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
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





public class MainFrame extends JFrame implements ActionListener, WindowListener{
   
	String userID;
	JPanel catePanel;
	JScrollPane catescpamel;
	int userkind;
	JButton jb1,jb2,jb3,jb4;
	ReservationNoti test2;
   public MainFrame(String userID) {
      
      super("  점톡");
//      try {
//         UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//         JFrame.setDefaultLookAndFeelDecorated(true);
//      } catch (Exception e) {
//         // TODO Auto-generated catch block
//         e.printStackTrace();
//      } 
      this.userID = userID;
      
      userkind=UserDB.getUSERKIND(userID);
      test2 = new ReservationNoti(userkind, userID);
      
      if(UserDB.getUSERKIND(userID)==0) {
      setBounds(600,100,500+15,800);
      setLayout(null);
      JPanel cate = new JPanel();
      //cate.setBackground(Color.black);
      cate.setBounds(0, 0, 500, 80);
      cate.setLayout(new GridLayout(1,4,10,10));
      
      cate.add(jb1=new JButton("예약"));
      jb1.setBorderPainted(false);
      cate.add(jb2=new JButton("채팅"));
      jb2.setBorderPainted(false);
      cate.add(jb3=new JButton("코인"));
      jb3.setBorderPainted(false);
      cate.add(jb4=new JButton("설정"));
      jb4.setBorderPainted(false);
      jb1.addActionListener(this);
      jb2.addActionListener(this);
      jb3.addActionListener(this);
      jb4.addActionListener(this);
      addWindowListener(this);
      add(cate);
      catePanel = new JPanel(); // 첫번째 탭
      catePanel.setBounds(0, 90, 500, 670);
      add(catePanel);
      jb1.doClick();
      } else if(UserDB.getUSERKIND(userID)==1){
          setBounds(600,100,500+15,800);
          setLayout(null);
          JPanel cate = new JPanel();
          //cate.setBackground(Color.black);
          cate.setBounds(0, 0, 500, 80);
          cate.setLayout(new GridLayout(1,4,10,10));
          JButton jb1,jb2,jb3,jb4;
          cate.add(jb1=new JButton("예약"));
          jb1.setBorderPainted(false);
          cate.add(jb2=new JButton("채팅"));
          jb2.setBorderPainted(false);
          cate.add(jb3=new JButton("환전"));
          jb3.setBorderPainted(false);
          cate.add(jb4=new JButton("설정"));
          jb4.setBorderPainted(false);
          jb1.addActionListener(this);
          jb2.addActionListener(this);
          jb3.addActionListener(this);
          jb4.addActionListener(this);
          add(cate);
          catePanel = new JPanel(); // 첫번째 탭
          catePanel.setBounds(0, 90, 500, 670);
          add(catePanel);
          jb1.doClick();
      } else if(UserDB.getUSERKIND(userID)==2) {
    	  setBounds(600,100,500+15,800);
          setLayout(null);
          JPanel cate = new JPanel();
          //cate.setBackground(Color.black);
          cate.setBounds(0, 0, 500, 80);
          cate.setLayout(new GridLayout(1,3,10,10));
          
          cate.add(jb1=new JButton("회원관리"));
          jb1.setBorderPainted(false);
//          cate.add(jb2=new JButton("채팅"));
//          jb2.setBorderPainted(false);
          cate.add(jb3=new JButton("재화"));
          jb3.setBorderPainted(false);
          cate.add(jb4=new JButton("설정"));
          jb4.setBorderPainted(false);
          jb1.addActionListener(this);
          jb3.addActionListener(this);
          jb4.addActionListener(this);
          add(cate);
          catePanel = new JPanel(); // 첫번째 탭
          catePanel.setBounds(0, 90, 500, 670);
          add(catePanel);
          jb1.doClick();
      }
      
     
      
      
      
      ProfileInOut pfio = ProfileInOut.getprofileInout();
      pfio.login(userID);
    		  
      setVisible(true);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
   }

	@Override
	public void actionPerformed(ActionEvent e) {
		
		JButton jb = (JButton) e.getSource();
		if(userkind==0) {
			if(jb.getText().equals("코인")) {
				remove(catePanel);
				add(catePanel = new BuyerFrame(userID));
				catePanel.setBounds(0, 90, 500, 670);
				revalidate();
				repaint();
			}else if(jb.getText().equals("예약")){
				remove(catePanel);
				
				add(catePanel = new ReservationPanel_User(userID));
				catePanel.setBounds(0, 90, 500, 670);
				catePanel.setVisible(true);
				revalidate();
				repaint();
			}else if(jb.getText().equals("채팅")){
				remove(catePanel);
				add(catePanel = new JPanel());
				ChatlistPanel ucl = new ChatlistPanel(userID);
				catePanel.add(ucl);
				catePanel.setLayout(null);
				catePanel.setBounds(0, 90, 500, 670);
				revalidate();
				repaint();
			}else if(jb.getText().equals("설정")){
				remove(catePanel);
				add(catePanel =new OptionPanel(userID, this));
				catePanel.setBounds(0, 90, 500, 670);
				revalidate();
				repaint();
			}
		}else if(userkind==1) {
			if(jb.getText().equals("환전")) {
				remove(catePanel);
				add(catePanel = new SellerFrame(userID));
				catePanel.setBounds(0, 90, 500, 670);
				revalidate();
				repaint();
			}else if(jb.getText().equals("예약")){
				remove(catePanel);
				
				add(catePanel = new ReservationPanel_Seller(userID));
				catePanel.setVisible(true);
				catePanel.setBounds(0, 90, 500, 670);
				revalidate();
				repaint();
			}else if(jb.getText().equals("채팅")){
				remove(catePanel);
				add(catePanel = new JPanel());
				ChatlistPanel ucl = new ChatlistPanel(userID);
				catePanel.add(ucl);
				catePanel.setBounds(0, 90, 500, 670);
				catePanel.setLayout(null);
				revalidate();
				repaint();
			}else if(jb.getText().equals("설정")){
				remove(catePanel);
				add(catePanel = new OptionPanel(userID, this));
				catePanel.setBounds(0, 90, 500, 670);
				catePanel.repaint();
				revalidate();
				repaint();
			}
		}else if(userkind==2) {
			if(jb.getText().equals("재화")) {
				remove(catePanel);
				add(catePanel = new EditerFrame(userID));
				catePanel.setBounds(0, 90, 500, 670);
				revalidate();
				repaint();
			}else if(jb.getText().equals("회원관리")){
				remove(catePanel);
				
				add(catePanel = new ReservationPanel_Manager(userID,this));
				catePanel.setBounds(0, 90, 500, 670);
				catePanel.setVisible(true);
				revalidate();
				repaint();
			}else if(jb.getText().equals("설정")){
				remove(catePanel);
				add(catePanel = new JumTalkOptionManager(userID));
				catePanel.setBounds(0, 90, 500, 670);
				revalidate();
				repaint();
			}
		}
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO 자동 생성된 메소드 스텁
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.out.println("들어오니?");
		try {
			ProfileInOut.getprofileInout().ois.close();
			ProfileInOut.getprofileInout().oos.close();
		} catch (IOException e1) {
			// TODO 자동 생성된 catch 블록
			e1.printStackTrace();
		}
		test2.close =true;
		UserDB.setLOGINCHK(userID, "false");
		
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
	

		