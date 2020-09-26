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
import com.csu.dao.ProductDao;
import com.csu.dao.ProductImpl;
import com.csu.dao.RoomDao;
import com.csu.dao.RoomImpl;

public class Invoicing extends JFrame {
	private ProductDao productDao = new ProductImpl();
	private Box box = Box.createVerticalBox();

	public Invoicing() {
		initMain();

		setContentPane(box);
		setTitle("结账");
		pack();
		setLocationRelativeTo(null);
	}

	private void initMain() {
		JLabel emptyLabel = new JLabel();
		emptyLabel.setPreferredSize(new Dimension(100, 30));
		JLabel emptyLabel2 = new JLabel();
		emptyLabel2.setPreferredSize(new Dimension(100, 30));
		JLabel emptyLabel3 = new JLabel();
		emptyLabel3.setPreferredSize(new Dimension(100, 30));

		JLabel productName = new JLabel("商品名", SwingConstants.CENTER);
		productName.setPreferredSize(new Dimension(100, 30));
		JLabel Name = new JLabel("姓名", SwingConstants.CENTER);
		Name.setPreferredSize(new Dimension(100, 30));
		JLabel Id = new JLabel("身份证号", SwingConstants.CENTER);
		Id.setPreferredSize(new Dimension(100, 30));
		JLabel roomId = new JLabel("房间号", SwingConstants.CENTER);
		roomId.setPreferredSize(new Dimension(100, 30));
		JLabel productNum = new JLabel("数量", SwingConstants.CENTER);
		productNum.setPreferredSize(new Dimension(100, 30));
		JTextField productNumField = new JTextField();
		productNumField.setPreferredSize(new Dimension(100, 30));
		JTextField NameField = new JTextField();
		NameField.setPreferredSize(new Dimension(100, 30));
		JTextField IdField = new JTextField();
		IdField.setPreferredSize(new Dimension(100, 30));

		JButton add = new JButton("确定添加");
		add.setPreferredSize(new Dimension(100, 30));
		JButton court = new JButton("结账");
		court.setPreferredSize(new Dimension(100, 30));
		JButton select = new JButton("查询");
		select.setPreferredSize(new Dimension(100, 30));
		JComboBox<String> roomIdBox = new JComboBox();
		roomIdBox.setPreferredSize(new Dimension(100, 30));
		JComboBox<String> productBox = new JComboBox();
		productBox.setPreferredSize(new Dimension(100, 30));

		List<String> list = productDao.getNames();
		if(list.size()>0) {
			for(int i=0;i<list.size();i++) {
				productBox.addItem(list.get(i));
			}

		}

		JPanel panel1 = new JPanel();
		panel1.add(Name);
		panel1.add(Id);
		panel1.add(emptyLabel2);
		JPanel panel2 = new JPanel();
		panel2.add(NameField);
		panel2.add(IdField);
		panel2.add(select);
		JPanel panel3 = new JPanel();
		panel3.add(emptyLabel3);
		panel3.add(roomId);
		panel3.add(roomIdBox);
		JPanel panel4 = new JPanel();
		panel4.add(productName);
		panel4.add(productNum);
		panel4.add(emptyLabel);
		JPanel panel5 = new JPanel();
		panel5.add(productBox);
		panel5.add(productNumField);
		panel5.add(add);
		JPanel panel6 = new JPanel();
		panel6.add(court);

		box.add(panel1);
		box.add(panel2);
		box.add(panel3);
		box.add(panel4);
		box.add(panel5);
		box.add(panel6);

		select.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String id = IdField.getText();
				RoomDao room = new RoomImpl();
				roomIdBox.removeAllItems();	//移除下拉框原有的数据
				List<String> list = room.getStayingRooms(id);
				if(list.size()>0) {
					for(int i=0;i<list.size();i++) {
						roomIdBox.addItem(list.get(i));
					}//为下拉框添加数据

				}
			}
		});

		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				RoomDao rd = new RoomImpl();

				String prN = productBox.getSelectedItem().toString();
				String roomid = roomIdBox.getSelectedItem().toString();
				String num = productNumField.getText();
				int roomId=0;
				for(int i=0;i<roomid.length();i++) {
					roomId+=(roomid.charAt(i)-'0')*Math.pow(10, roomid.length()-i-1);
				}//获取int型的房间�?
				int Num=0;
				for(int i=0;i<num.length();i++) {
					Num+=(num.charAt(i)-'0')*Math.pow(10, num.length()-i-1);
				}//获取输入的商品数�?
				double before = rd.getConsume(roomId);
				double addMoney = productDao.getPrice(prN)*Num;
				System.out.println(productDao.getPrice(prN));
				double consume = addMoney+before;
				int x = rd.insertConsume(roomId, consume);
				System.out.println(x+"");

				int lastNum = productDao.getProductNum(prN);
				lastNum = lastNum-Num;
				int a = productDao.minusProduct(prN, lastNum);

				JOptionPane.showMessageDialog(null,"消费"+productBox.getSelectedItem().toString()+
						"共"+addMoney+"元");

			}
		});

		court.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String id = IdField.getText();
				String roomid = roomIdBox.getSelectedItem().toString();
				int roomId=0;
				for(int i=0;i<roomid.length();i++) {
					roomId+=(roomid.charAt(i)-'0')*Math.pow(10, roomid.length()-i-1);
				}
				RoomDao rd = new RoomImpl();
				CustomInfoDao cid = new CustomInfoImpl();
				double totConsume = cid.getTotConsume(id);
				double consume = rd.getConsume(roomId);

				String StayDate = rd.getStayDate(roomId);
				Date t = new Date();
				java.sql.Date sqld = new java.sql.Date(t.getTime());
				String leaveDate = sqld.toString();

				double days = 0;
				if(cid.getTimes(id)>=3) {
					days = (getDay(StayDate,leaveDate)*rd.getRoomPrice(roomId)+totConsume+consume)*0.8;
					double deposit = rd.getRoomDeposit(roomId);
					JOptionPane.showMessageDialog(null,"您是尊贵的会员，已为您8折优惠,共计消费"+days+"元，找您"+(deposit-days)+"元，感谢惠顾");
				}
				else {
					days = getDay(StayDate,leaveDate)*rd.getRoomPrice(roomId)+totConsume+consume;
					double deposit = rd.getRoomDeposit(roomId);
					JOptionPane.showMessageDialog(null,"共计消费"+days+"元，找您"+(deposit-days)+"元，感谢惠顾");
				}
				int x = rd.resetRoom(roomId);

				IncomeDao ID = new IncomeImpl();

				if(ID.isExsit(leaveDate)) {
					double bIncome = ID.getIncome(leaveDate);
					bIncome+=days;
					int a = ID.updateIncome(leaveDate, bIncome);
					int leave = ID.getLeaveNum(leaveDate);
					leave++;
					int b = ID.updateLeaveNum(leaveDate, leave);
					int stay = ID.getStayNum(leaveDate);
					stay--;
					int c = ID.updateStayNum(leaveDate, stay);

				}
				else {
					int a=ID.insert(leaveDate, days, 0,-1,1);
				}

				
			}
		});
	}

	public int getDay(String s1,String s2) {

		String[] list1 = new String[3];
		String[] list2 = new String[3];

		list1[0] = s1.substring(0, 4);
		list1[1] = s1.substring(5, 7);
		list1[2] = s1.substring(8);

		list2[0] = s2.substring(0, 4);
		list2[1] = s2.substring(5, 7);
		list2[2] = s2.substring(8);


		int[] Date1 = new int[list1.length];
		int[] Date2 = new int[list2.length];

		for(int i=0;i<3;i++) {
			int tot=0;
			for(int j=0;j<list1[i].length();j++) {
				tot+=(list1[i].charAt(j)-'0')*Math.pow(10, list1[i].length()-j-1);
			}
			Date1[i]=tot;
			tot=0;
			for(int j=0;j<list2[i].length();j++) {
				tot+=(list2[i].charAt(j)-'0')*Math.pow(10, list2[i].length()-j-1);
			}
			Date2[i]=tot;
		}
		for(int i=0;i<list1.length;i++)
			System.out.println(Date1[i]);
		int days = (Date2[0]-Date1[0])*365+(Date2[1]-Date1[1])*30+(Date2[2]-Date1[2]);
		return days;
	}
}