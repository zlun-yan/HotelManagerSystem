package com.csu.reception;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.csu.dao.CustomInfoDao;
import com.csu.dao.CustomInfoImpl;
import com.csu.dao.IncomeDao;
import com.csu.dao.IncomeImpl;
import com.csu.dao.RoomDao;
import com.csu.dao.RoomImpl;

public class StayAndBooking extends JFrame {
	private JComboBox<String> roomCombo;
	private JComboBox<String> typeCombo;
	private RoomDao roomDao = new RoomImpl();
	private Box box = Box.createVerticalBox();
	private int state = 0;

	public StayAndBooking() {
		initMain();

//		setSize(800,600);
		setTitle("预订和入住");
		setContentPane(box);
		pack();
		setLocationRelativeTo(null);
	}

	private void initMain() {
		JLabel Name = new JLabel("姓名", SwingConstants.CENTER);
		Name.setPreferredSize(new Dimension(80, 30));
		JLabel Telephone = new JLabel("联系方式", SwingConstants.CENTER);
		Telephone.setPreferredSize(new Dimension(80, 30));
		JLabel Idnum = new JLabel("身份证号", SwingConstants.CENTER);
		Idnum.setPreferredSize(new Dimension(80, 30));
		JLabel roomId = new JLabel("房间号", SwingConstants.CENTER);
		roomId.setPreferredSize(new Dimension(100, 30));
		JLabel roomType = new JLabel("房间类型", SwingConstants.CENTER);
		roomType.setPreferredSize(new Dimension(100, 30));

		JTextField NameField = new JTextField();
		NameField.setPreferredSize(new Dimension(120, 30));
		JTextField TelephoneField = new JTextField();
		TelephoneField.setPreferredSize(new Dimension(120, 30));
		JTextField IdNumField = new JTextField();
		IdNumField.setPreferredSize(new Dimension(120, 30));

		roomCombo = new JComboBox<>();
		roomCombo.setPreferredSize(new Dimension(100, 30));
		typeCombo = new JComboBox<>();
		typeCombo.setPreferredSize(new Dimension(100, 30));

		List<String> list = roomDao.getTypes();
		if(list.size()>0) {
			for(int i=0;i<list.size();i++) {
				typeCombo.addItem(list.get(i));
			}
		}
		setRoomCombo();

		JButton Stay = new JButton("入住");
		Stay.setPreferredSize(new Dimension(100, 30));
		JButton Booking = new JButton("预定");
		Booking.setPreferredSize(new Dimension(100, 30));

		JPanel panel1 = new JPanel();
		panel1.add(Name);
		panel1.add(NameField);
		JPanel panel2 = new JPanel();
		panel2.add(Telephone);
		panel2.add(TelephoneField);
		JPanel panel3 = new JPanel();
		panel3.add(Idnum);
		panel3.add(IdNumField);
		JPanel comboLabel = new JPanel();
		comboLabel.add(roomType);
		comboLabel.add(roomId);
		JPanel comboPanel = new JPanel();
		comboPanel.add(typeCombo);
		comboPanel.add(roomCombo);
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(Stay);
		buttonPanel.add(Booking);

		box.add(panel1);
		box.add(panel2);
		box.add(panel3);
		box.add(comboLabel);
		box.add(comboPanel);
		box.add(buttonPanel);

		Stay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = NameField.getText();
				String telephone = TelephoneField.getText();
				String idnum = IdNumField.getText();
				String roomid = roomCombo.getSelectedItem().toString();
				Date t = new Date();
				java.sql.Date sqld = new java.sql.Date(t.getTime());
				String date = sqld.toString();
				if (!idnum.equals("") && !name.equals("")&&!telephone.equals("")) {
					int x = 0;
					CustomInfoDao cus = new CustomInfoImpl();
					if (!cus.isExsit(idnum))
						x = cus.customsInsert(name, telephone, idnum);
					else {
						int times = cus.getTimes(idnum);
						times+=1;
						x = cus.addTimes(idnum, times);
					}

					int roomId = 0;
					for (int i = 0; i < roomid.length(); i++) {
						roomId += (roomid.charAt(i) - '0') * Math.pow(10, roomid.length() - i - 1);
					}
					System.out.print(roomId + "");
					System.out.print(" ");
					RoomDao room = new RoomImpl();
					int y = room.StayInRoom(roomId, 2, date, idnum);

					IncomeDao ID = new IncomeImpl();
					if (ID.isExsit(date)) {
						int stay = ID.getStayNum(date);
						stay++;
						int c = ID.updateStayNum(date, stay);
					} else
						y = ID.insert(date, 0, 1, 0, 0);
					double deposit = room.getRoomDeposit(roomId);
					JOptionPane.showMessageDialog(null, "入住成功，收取您押金"+deposit+"元");
				}
				else{
					JOptionPane.showMessageDialog(null, "信息不能为空");
				}
			}

		});

		typeCombo.addActionListener(event -> {
			setRoomCombo();
		});

		Booking.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = NameField.getText();
				String telephone = TelephoneField.getText();
				String idnum = IdNumField.getText();
				String roomid = roomCombo.getSelectedItem().toString();
				Date t = new Date();
				java.sql.Date sqld = new java.sql.Date(t.getTime());
				String date = sqld.toString();
				if(!idnum.equals("") && !name.equals("")&&!telephone.equals("")) {
					int x = 0;
					CustomInfoDao cus = new CustomInfoImpl();
					if (!cus.isExsit(idnum))
						x = cus.customsInsert(name, telephone, idnum);
					else {
						int times = cus.getTimes(idnum);
						times+=1;
						x = cus.addTimes(idnum, times);
					}
					int roomId = 0;
					for (int i = 0; i < roomid.length(); i++) {
						roomId += (roomid.charAt(i) - '0') * Math.pow(10, roomid.length() - i - 1);
					}
					System.out.print(roomId + "");
					System.out.print(" ");
					RoomDao room = new RoomImpl();
					int y = room.StayInRoom(roomId, 1, date, idnum);

					IncomeDao ID = new IncomeImpl();
					if (ID.isExsit(date)) {
						int book = ID.getBookNum(date);
						int c = ID.updateBookNum(date, book + 1);
					} else
						y = ID.insert(date, 0, 1, 0, 0);
					double deposit = room.getRoomDeposit(roomId);
					JOptionPane.showMessageDialog(null, "预定成功，收取您押金"+deposit+"元");
				}
				else{
					JOptionPane.showMessageDialog(null, "信息不能为空");
				}
			}
		});
	}

	private void setRoomCombo() {
		roomCombo.removeAllItems();
		List<String> list = roomDao.getRooms(typeCombo.getSelectedItem().toString());
		if(list.size()>0) {
			state = 1;
			for(int i=0;i<list.size();i++) {
				roomCombo.addItem(list.get(i));
			}
		}
		else {
			state = 0;
			roomCombo.addItem("---");
		}
	}
}
