package db_p;

import java.awt.Color;
import java.awt.Dimension;


import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import javax.swing.table.DefaultTableModel;

import db_p.OptionPanel.SendMessageClick;
import db_p.ReservationPanel_Manager.UserListpanel.UserDataframe;

public class ReservationPanel_Manager extends JPanel {
	String userID;
	UserDataframe dataframe;
	MainFrame mainframe;
	public ReservationPanel_Manager(String userID, MainFrame mainframe) {
		this.userID = userID;
		this.mainframe =mainframe;
		setLayout(new GridLayout(2, 2, 10, 10));
		add(new UserListpanel(this,0));
		add(new UserListpanel(this,1));
		add(new UserListpanel(this,2));
		add(new UserListpanel(this,3));
		
		
		


		setBounds(700, 200, 500, 670);
		setVisible(true);
		
	}
	
	class UserListpanel extends JPanel implements MouseListener{
		JTable table;
		String [][] data;
		ReservationPanel_Manager resSuperuser;
		int userkind;
		public UserListpanel(ReservationPanel_Manager resSuperuser, int userkind) {
			this.userkind = userkind;
			setLayout(null);
			JLabel title = null;
			switch(userkind) {
			case 0 : {
				title = new JLabel("일반유저 리스트");
			}break;
			case 1 : {
				title = new JLabel("점술가 리스트");
			}break;
			case 2 : {
				title = new JLabel("블랙 리스트");
			}break;
			case 3 : {
				title = new JLabel("가입승인 리스트");
			}break;
			}
			
			title.setBounds(5, 0, 200, 30);
			this.resSuperuser =resSuperuser;
			add(title);
			System.out.println(getHeight()+", "+getWidth());
			String [] colum = {"ID","최근로그인시간"};
			ArrayList<normalUser> datalist;
			if(userkind<3) {
				datalist = UserDB.getUsers(userkind);
				
			}else {
				datalist = BufUserDB.getbufUsers();
			}
			data = new String [datalist.size()][] ;
			for (int i = 0; i < data.length; i++) {
				String [] dataindex = {datalist.get(i).id,datalist.get(i).loginTime};
				data[i] = dataindex;
			}
			table = new JTable(new NotEditTable(data, colum));
			table.getTableHeader().setReorderingAllowed(false);
			table.getTableHeader().setResizingAllowed(false);
			table.getColumn("ID").setPreferredWidth(50);
			table.getColumn("최근로그인시간").setPreferredWidth(150);
			table.addMouseListener(this);
			JScrollPane listscJScrollPane = new JScrollPane(table);
			listscJScrollPane.setBounds(5, 30, 230, 270);
			add(listscJScrollPane);
		}
		UserDataframe userDatafram ;
		@Override
		public void mouseClicked(MouseEvent e) {
			
				if (e.getClickCount() == 2) {
					if(resSuperuser.dataframe==null) {
						if(userkind<3) {
							resSuperuser.dataframe = new UserDataframe(data[table.getSelectedRow()][0],this);
						}else {
							resSuperuser.dataframe = new UserDataframe(data[table.getSelectedRow()][0],this,"가입승인");
						}
						
					}
				}
			
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO 자동 생성된 메소드 스텁
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO 자동 생성된 메소드 스텁
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO 자동 생성된 메소드 스텁
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO 자동 생성된 메소드 스텁
			
		}
		

		class UserDataframe extends JFrame implements ActionListener, WindowListener{
			String userID;
			JCheckBox setblack;
			JButton userdelete;
			JButton savebtn;
			UserListpanel userListpanel;
			public UserDataframe(String userID, UserListpanel userListpanel) {
				this.userID = userID;
				setBounds(600, 200, 475, 370);
				setTitle(userID);
				setLayout(new GridLayout(1,1));
				JPanel jp = new JPanel();
				jp.setBounds(0, 0, 475, 370);
				jp.setLayout(null);
				this.userListpanel = userListpanel;
				
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
				JLabel lblPhone_txt = new JLabel(UserDB.getPHONE(userID));
				lblPhone_txt.setBounds(180, 130+20+20, 200, 20);
				JLabel lblPhone = new JLabel("[전화번호]");
				lblPhone.setBounds(180, 130+20, 200, 20);
				setblack = new JCheckBox("블랙리스트 지정");
				setblack.setBounds(20, 230, 130, 20);
				if(UserDB.getUSERKIND(userID)>2) {
					setblack.setSelected(true);
				}
				userdelete = new JButton("회원 탈퇴");
				userdelete.setBounds(160, 230, 100, 20);
				userdelete.addActionListener(this);
				savebtn = new JButton("저장");
				savebtn.addActionListener(this);
				savebtn.setBounds(310, 230, 100, 20);
				
				
				jp.add(lblImg);
				jp.add(lblDetail);
				jp.add(lbprofile_text_scroll);
				jp.add(lblPhone);
				jp.add(setblack);
				jp.add(userdelete);
				jp.add(savebtn);
				addWindowListener(this);
				add(jp);
				
				
				setVisible(true);
			}
			public UserDataframe(String userID, UserListpanel userListpanel, String string2) {
				this.userID = userID;
				setBounds(600, 200, 475, 370);
				setTitle(userID+"가입승인창");
				setLayout(new GridLayout(1,1));
				JPanel jp = new JPanel();
				jp.setBounds(0, 0, 475, 370);
				jp.setLayout(null);
				this.userListpanel = userListpanel;
				
				ImageIcon imgIcon = new ImageIcon(pfio.download(userID));
				Image img = imgIcon.getImage();
				img = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
				imgIcon.setImage(img);
				
				JLabel lblImg = new JLabel();
				lblImg.setIcon(imgIcon);
				lblImg.setBounds(50, 80, 100, 100);
				JLabel lblDetail = new JLabel("[간단소개]");
				lblDetail.setBounds(180, 10, 200, 20);
				JTextArea lbprofile_text = new JTextArea("가입승인 절차중");
				lbprofile_text.setBounds(180, 35, 200, 110);
				lbprofile_text.setLineWrap(true);
				lbprofile_text.setEditable(false);
				lbprofile_text.setBackground(new Color(245,245,245));
				JScrollPane lbprofile_text_scroll =  new JScrollPane(lbprofile_text);
				lbprofile_text_scroll.setBounds(180, 35, 200, 110);
				lbprofile_text_scroll.setBorder(BorderFactory.createEmptyBorder());
				JLabel lblPhone_txt = new JLabel(BufUserDB.getPHONE(userID));
				lblPhone_txt.setBounds(180, 130+20+20, 200, 20);
				JLabel lblPhone = new JLabel("[전화번호]");
				lblPhone.setBounds(180, 130+20, 200, 20);
				
				
				userdelete = new JButton("승인거절");
				userdelete.setBounds(160, 230, 100, 20);
				userdelete.addActionListener(this);
				savebtn = new JButton("가입승인");
				savebtn.addActionListener(this);
				savebtn.setBounds(310, 230, 100, 20);
				
				
				jp.add(lblImg);
				jp.add(lblDetail);
				jp.add(lbprofile_text_scroll);
				jp.add(lblPhone);
				jp.add(userdelete);
				jp.add(savebtn);
				addWindowListener(this);
				add(jp);
				
				
				setVisible(true);
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource().equals(savebtn)) {
					if(savebtn.getText().equals("저장")) {
					if(setblack.isSelected()) {
						System.out.println("여기 들어오니?");
						System.out.println(userID);
						UserDB.setUSERKIND(userID, UserDB.getUSERKIND(userID)+3);
					}else {
						if(UserDB.getUSERKIND(userID)>2) {
							UserDB.setUSERKIND(userID, UserDB.getUSERKIND(userID)-3);
						}
					}
					JOptionPane.showMessageDialog(null, "회원정보 저장완료");
					userListpanel.resSuperuser.dataframe =null;
					System.out.println(userListpanel.resSuperuser.mainframe.jb1);
					userListpanel.resSuperuser.mainframe.jb1.doClick();
					dispose();
					}else {
						
						UserDB.signupSELLERUSER(BufUserDB.getID(userID), BufUserDB.getPW(userID), BufUserDB.getNAME(userID), BufUserDB.getGENDER(userID), BufUserDB.getBIRTHDATE(userID).replaceAll("-", ""), 
						BufUserDB.getPHONE(userID), BufUserDB.getEMAIL(userID), BufUserDB.getADDRESS(userID), BufUserDB.getPWHINT(userID), BufUserDB.getPWRES(userID), BufUserDB.getBUSINESSNAME(userID), 
						BufUserDB.getBUSINESSADDRESS(userID), BufUserDB.getBANKNUMBER(userID),0);
						BufUserDB.deleteUSER(userID);
						JOptionPane.showMessageDialog(null, "가입 승인 완료");
						userListpanel.resSuperuser.dataframe =null;
						userListpanel.resSuperuser.mainframe.jb1.doClick();
						dispose();
					}
				}else if(e.getSource().equals(userdelete)) {
					if(userdelete.getText().equals("회원 탈퇴")) {
					UserDB.deleteUSER(userID);
					JOptionPane.showMessageDialog(null, "회원 탈퇴 처리 완료");
					userListpanel.resSuperuser.dataframe =null;
					userListpanel.resSuperuser.mainframe.jb1.doClick();
					dispose();
					}else {
						BufUserDB.deleteUSER(userID);
						JOptionPane.showMessageDialog(null, "승인거절 처리 완료");
						userListpanel.resSuperuser.dataframe =null;
						userListpanel.resSuperuser.mainframe.jb1.doClick();
						dispose();
					}
				}
				
			}
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO 자동 생성된 메소드 스텁
				
			}
			@Override
			public void windowClosing(WindowEvent e) {
				userListpanel.resSuperuser.dataframe =null;
				userListpanel.resSuperuser.mainframe.jb1.doClick();
				
				
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
	}
	ProfileInOut pfio = ProfileInOut.getprofileInout();
	
	

	

}









