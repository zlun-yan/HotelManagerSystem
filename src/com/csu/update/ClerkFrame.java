package com.csu.update;

import javax.swing.JFrame;

public class ClerkFrame extends JFrame{
	public ClerkFrame(String jid) {
		ClerkPanel panel = new ClerkPanel();
		panel.initData(jid);
		setContentPane(panel);

		pack();
		setTitle("个人信息");
		setLocationRelativeTo(null);
	}
}
