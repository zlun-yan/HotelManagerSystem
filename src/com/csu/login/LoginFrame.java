package com.csu.login;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.csu.dao.UserDao;
import com.csu.dao.UserDaoImpl;
import com.csu.manager.incomeTableFrame.IncomeTableFrame;
import com.csu.reception.ReceptionFrontWindow;
import com.csu.root.RootFrontWindow;

public class LoginFrame extends JFrame{
//	private Box vBox = Box.createVerticalBox();

	public LoginFrame(){
//		setContentPane(vBox);
//		pack();
		JFrame Frame = new JFrame(); 
		Frame.setTitle("登录");
		Frame.setSize(400, 300);
		Frame.setLocationRelativeTo(null);
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel bgPanel = new ImagePanel();
		getContentPane().add(bgPanel);
		bgPanel.setLayout(null);
		JLabel nameLabel = new JLabel("<html><body><font size=4>账号:</font></body></html>");
//		nameLabel.setBounds(60, 15, 40, 20);
//		nameLabel.setPreferredSize(new Dimension(40, 30));

		JLabel pswLabel = new JLabel("<html><body><font size=4>密码:</font></body></html>");
//		pswLabel.setBounds(60, 0, 40, 20);
//		pswLabel.setPreferredSize(new Dimension(40, 30));
		
		ImageIcon background =new ImageIcon(LoginFrame.class.getResource("login.jpg"));
        background.setImage(
      		  background.getImage().
      		  getScaledInstance(background.getIconWidth(),background.getIconHeight(), Image.SCALE_DEFAULT)); 
		JLabel regState = new JLabel();
		regState.setIcon(background);
		regState.setHorizontalAlignment(0);
//		regState.setPreferredSize(new Dimension(80, 30));
//		regState.setFont(new Font("黑体",Font.BOLD,50));
//		regState.setForeground(Color.blue);

		JTextField userField = new JTextField();
//		userField.setBounds(140,15,180,30);
//		userField.setPreferredSize(new Dimension(180, 30));
//		userField.setBorder(new MatteBorder(0, 0, 2, 0, Color.black));

//		userField.addMouseListener(new MouseListener() {
//
//			@Override
//			public void mouseReleased(MouseEvent e) {}
//
//			@Override
//			public void mousePressed(MouseEvent e) {
//				userField.grabFocus();
//			}
//
//			@Override
//			public void mouseExited(MouseEvent e) {
//				// TODO 自动生成的方法存根
//				userField.setBounds(140,15,180,20);
//			}
//
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				// TODO 自动生成的方法存根
//				userField.grabFocus();
//				userField.setBounds(140,15,186,23);
//			}
//
//			@Override
//			public void mouseClicked(MouseEvent e) {}
//		});

		JPasswordField password = new JPasswordField();
//		password.setBounds(140, 0, 180, 30);
//		password.setPreferredSize(new Dimension(180, 30));
//		password.setBorder(new MatteBorder(0, 0, 1, 0, Color.black));

//		password.addMouseListener(new MouseListener() {
//
//			@Override
//			public void mouseReleased(MouseEvent e) {}
//
//			@Override
//			public void mousePressed(MouseEvent e) {
//				password.grabFocus();
//			}
//
//			@Override
//			public void mouseExited(MouseEvent e) {
//				// TODO 自动生成的方法存根
//				password.setBounds(140, 0,180,20);
//				password.setBorder(new MatteBorder(0, 0, 1, 0, Color.black));
//			}
//
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				// TODO 自动生成的方法存根
//				password.grabFocus();
//				password.setBorder(new MatteBorder(0, 0, 3, 0, Color.black));
//				password.setBounds(140, 0,186,23);
//			}
//
//			@Override
//			public void mouseClicked(MouseEvent e) {}
//		});
		
		JButton regBtn1 = new JButton("登录");
//		regBtn1.setBorderPainted(false);
//		regBtn1.setFocusPainted(false);
//		regBtn1.setFont(new Font("黑体", 1, 20));
//		regBtn1.setBorder(BorderFactory.createLoweredBevelBorder());
//		regBtn1.setPreferredSize(new Dimension(60,30));

//		JPanel panel1 = new JPanel();
//		panel1.add(nameLabel);
//		panel1.add(userField);
//
//		JPanel panel2 = new JPanel();
//		panel2.add(pswLabel);
//		panel2.add(password);
//
//		JPanel panel3 = new JPanel();
//		panel3.add(regBtn1);
//
//		JPanel panel4 = new JPanel();
//		panel4.add(regState);
//
//		vBox.add(panel4);
//		vBox.add(panel1);
//		vBox.add(panel2);
//		vBox.add(panel3);
		
		regState.setBounds(165, 0, 75, 75);
		nameLabel.setBounds(80, 80, 40, 30);
		userField.setBounds(130,80,160,30);
		pswLabel.setBounds(80,135,40,30);
		password.setBounds(130, 135, 160, 30);
		regBtn1.setBounds(160, 195, 80, 30);
		bgPanel.add(regBtn1);
		bgPanel.add(password);
		bgPanel.add(userField);
		bgPanel.add(regState);
		bgPanel.add(pswLabel);
		bgPanel.add(nameLabel);
		add(bgPanel);
		regBtn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				regBtn1.setFont(new Font("黑体", 1, 22));
//				regBtn1.setForeground(Color.black);
				if(userField.getText().equals("")||password.getText().equals("")) {

					password.setText("");

					JOptionPane.showMessageDialog(null, "账号或密码不能为空！","登录",JOptionPane.WARNING_MESSAGE);
				}else {
					UserDao userDao = new UserDaoImpl();
					String a = userDao.isClerk(userField.getText(), password.getText());

					if(a == null) {
						password.setText("");
						JOptionPane.showMessageDialog(null, "密码不正确！","警告",JOptionPane.WARNING_MESSAGE);
					}else if(a.equals("waiter")) {

						JOptionPane.showMessageDialog(null, "前台登录成功","提示",JOptionPane.WARNING_MESSAGE);
						ReceptionFrontWindow frame = new ReceptionFrontWindow(userField.getText());
						frame.setVisible(true);
						Frame.setVisible(false);

						//隐藏登录窗口
						setVisible(false);
					}else if(a.equals("root")) {

						JOptionPane.showMessageDialog(null, "管理员登录成功","提示",JOptionPane.WARNING_MESSAGE);
						RootFrontWindow frame = new RootFrontWindow(userField.getText());
						frame.setVisible(true);
						Frame.setVisible(false);


						//隐藏登录窗口
						setVisible(false);
					}else if(a.equals("manager")) {

						JOptionPane.showMessageDialog(null, "经理登录成功","提示",JOptionPane.WARNING_MESSAGE);
						IncomeTableFrame frame = new IncomeTableFrame(userField.getText());
						frame.setVisible(true);
						Frame.setVisible(false);
					}
				}

			}
		});

//		regBtn1.addMouseListener(new MouseListener() {
//
//			@Override
//			public void mouseReleased(MouseEvent e) {}
//
//			@Override
//			public void mousePressed(MouseEvent e) {
//				// TODO 自动生成的方法存根
//				regBtn1.setFont(new Font("黑体", 1, 20));
//				regBtn1.setForeground(Color.red);
//			}
//
//			@Override
//			public void mouseExited(MouseEvent e) {
//				// TODO 自动生成的方法存根
//				regBtn1.setFont(new Font("黑体", 1, 20));
//				regBtn1.setForeground(Color.black);
//			}
//
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				// TODO 自动生成的方法存根
//				regBtn1.setFont(new Font("黑体", 1, 23));
//				regBtn1.setForeground(Color.blue);
//			}
//
//			@Override
//			public void mouseClicked(MouseEvent e) {}
//		});
		Frame.add(bgPanel);
		Frame.setVisible(true);
	}
	class ImagePanel extends JPanel {

		@Override
		protected void paintComponent(Graphics g) {

			super.paintComponent(g);

			ImageIcon icon = new ImageIcon("src/pic/bg.jpg");

			g.drawImage(icon.getImage(), 0, 0, null);

		}

	}
}


