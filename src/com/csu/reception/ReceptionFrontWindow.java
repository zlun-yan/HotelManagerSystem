package com.csu.reception;

import com.csu.update.ClerkFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

import javax.swing.*;

public class ReceptionFrontWindow extends JFrame{
	private String jid;

	public ReceptionFrontWindow(String  jid) {
		this.jid = jid;

		initButton();
		initMenu();

		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initButton() {
		
		Icon bgSAb = new ImageIcon("StayAndBooking.png");
		Icon bgBtoS = new ImageIcon("BToS.png");
		Icon bgChange = new ImageIcon("ChangeRoom.png");
		Icon bginvoicing = new ImageIcon("invoicing.png");
		
		JButton SAB = new JButton("入住/预定");
		JButton BtoS = new JButton("预定转入住");
		JButton Change = new JButton("换房");
		JButton invoicing = new JButton("结账");
		
		SAB.setIcon(bgSAb);
		BtoS.setIcon(bgBtoS);
		Change.setIcon(bgChange);
		invoicing.setIcon(bginvoicing);
		JPanel p1 = new JPanel();

		SAB.setBounds(125, 100,150, 150);
		SAB.setPreferredSize(new Dimension(150, 150));
		BtoS.setPreferredSize(new Dimension(150, 150));
		Change.setPreferredSize(new Dimension(150, 150));
		invoicing.setPreferredSize(new Dimension(150, 150));

		SAB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				StayAndBooking sab = new StayAndBooking();
				sab.setVisible(true);

			}
		});

		BtoS.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				BookingToStayin bts = new BookingToStayin();
				bts.setVisible(true);
			}
		});

		Change.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeRoomFrame crf = new ChangeRoomFrame();
				crf.setVisible(true);

			}
		});

		invoicing.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Invoicing In = new Invoicing();
				In.setVisible(true);
			}
		});

		p1.setLayout(new GridLayout(2, 2));
		p1.add(SAB);
		p1.add(BtoS);
		p1.add(Change);
		p1.add(invoicing);
		add(p1);
	}

	private void initMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu infoMenu = new JMenu("查看");

		JMenuItem personInfo = new JMenuItem("个人信息");
		personInfo.setAccelerator(KeyStroke.getKeyStroke('I', InputEvent.CTRL_DOWN_MASK));
		personInfo.addActionListener(event -> {
			ClerkFrame frame = new ClerkFrame(jid);
			frame.setVisible(true);
		});

		infoMenu.add(personInfo);
		menuBar.add(infoMenu);

		setJMenuBar(menuBar);
	}
}