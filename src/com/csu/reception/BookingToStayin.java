package com.csu.reception;

import java.awt.*;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.csu.bean.Room;
import com.csu.dao.IncomeDao;
import com.csu.dao.IncomeImpl;
import com.csu.dao.RoomDao;
import com.csu.dao.RoomImpl;

public class BookingToStayin extends JFrame{

	JPanel tabelPanel;
	JTable table;
	String columnNames[];
	Object[][] rowDate;
	DefaultTableModel model;
	RoomDao room = new RoomImpl();

	public BookingToStayin() {
		initMain();
		init();

//		setSize(800,600);
		setTitle("预定转入住");
		pack();
		setLocationRelativeTo(null);
	}

	private void initMain() {
		JPanel panel2 = new JPanel();
		JPanel panel4 = new JPanel();

		JPanel totPanel = new JPanel(new BorderLayout());

		JLabel Id = new JLabel("身份证号");
		JLabel roomid = new JLabel("房间号");
		JTextField IdField = new JTextField();

		JButton selbtn = new JButton("查询");

		JComboBox<String> roomCombo = new JComboBox<String>();

		JButton bTos = new JButton("确定入住");

		IdField.setPreferredSize(new Dimension(160, 30));
		roomCombo.setPreferredSize(new Dimension(160,30));

		panel2.add(Id);
		panel2.add(IdField);
		panel2.add(selbtn);
		panel4.add(roomid);
		panel4.add(roomCombo);
		panel4.add(bTos);
		totPanel.add(panel2, BorderLayout.NORTH);
		totPanel.add(panel4, BorderLayout.SOUTH);

		add(totPanel, BorderLayout.NORTH);


		selbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String id = IdField.getText();
				RoomDao room = new RoomImpl();
				roomCombo.removeAllItems();		//清空之前下拉框中的数�?
				List<String> list = room.getBookingRooms(id);
				if(list.size()>0) {
					for(int i=0;i<list.size();i++) {
						roomCombo.addItem(list.get(i));
					}	//为下拉框添加数据

				}
				else
					roomCombo.addItem("未预订");
				initDate(id);

			}
		});


		bTos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String roomid = roomCombo.getSelectedItem().toString();
				int roomId=0;	//将房间号字符串转化为int
				for(int i=0;i<roomid.length();i++) {
					roomId+=(roomid.charAt(i)-'0')*Math.pow(10, roomid.length()-i-1);
				}
				RoomDao room = new RoomImpl();
				Date t = new Date();
				java.sql.Date sqld = new java.sql.Date(t.getTime());
				String StayDate = sqld.toString();
				//获取日期并转化为YYYY-MM-DD形式
				int x = room.BookingToStay(roomId, StayDate);
				//执行预定转入住操作
				System.out.print(x+"");

				IncomeDao ID = new IncomeImpl();
				if(ID.isExsit(StayDate)) {
					int book = ID.getBookNum(StayDate);
					book--;
					int a = ID.updateBookNum(StayDate, book);
					//获取预定人数并再减少后存入报表
					int stay = ID.getStayNum(StayDate);
					stay++;
					int b = ID.updateStayNum(StayDate, stay);
					//获取住店人数并在增加后存入报表
				}

				JOptionPane.showMessageDialog(null,"入住成功");
			}
		});
	}

	public Object[][] getRowData(List<Room> list){
		if(list!=null&&list.size()>0) {
			Object[][] data = new Object[list.size()][3];
			for(int i=0;i<list.size();i++) {
				data[i][0] = list.get(i).getCustomId();
				data[i][1] = list.get(i).getRoomId();
				data[i][2] = list.get(i).getType();	
			}
			return data;
		}
		return null;
	} 
	public void initDate(String customId) {
		columnNames= new String[]{"身份证号","预定房间","房间类型"};
		rowDate = getRowData(room.GetRoom(customId));
		
		model = new DefaultTableModel(rowDate,columnNames);
		table.setModel(model);
	}
	//获取初始表格数据
	public void init(){
		tabelPanel = new JPanel(new BorderLayout());
		columnNames= new String[]{"身份证号","预定房间","房间类型"};
		rowDate = getRowData(room.GetBookRoom());
		
		model = new DefaultTableModel(rowDate,columnNames);
		table = new JTable();
		table.setModel(model);
		
		table.setFont(new Font(null,Font.BOLD,14));
		table.setRowHeight(30);
		
//		table.setSize(800, 60);
		tabelPanel.add(table.getTableHeader(),BorderLayout.NORTH);
		tabelPanel.add(table,BorderLayout.CENTER);
		add(tabelPanel, BorderLayout.CENTER);
	}
}