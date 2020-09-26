package com.csu.update;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.csu.bean.Clerk;
import com.csu.dao.ClerkDao;
import com.csu.dao.ClerkImpl;

public class ClerkPanel extends JPanel{

	private JTable table;
	private String[] columnNames;//表头
	private Object[][] rowData;//表格的数据
	private DefaultTableModel model;//表格显示的数据模型
	private ClerkDao clerkDao = new ClerkImpl();
	private JButton sureButton = new JButton("保存");
	
	public ClerkPanel() {
		//设置边界布局
		setLayout(new BorderLayout());
	}
	
	/**
	 * 初始化表格数据
	 */
	public void initData(String jid){
		
		columnNames = new String[]{"员工id","姓名","职位","员工号","密码"};
		//从数据库中取第一页的部门信息，并转为二维数据
		List<Clerk> clerkList = clerkDao.getClerkByJid(jid);
		rowData = getRowData(clerkList);
		//总页数
		model = new DefaultTableModel(rowData, columnNames) {
			//重写isCellEditable方法，设置第一、三、四列不可编辑
			@Override
			public boolean isCellEditable(int row,int colum) {
				if(colum == 0 || colum == 2 || colum == 3) {
					return false;
				}else {
					return true;
				}
			}
		};
		table = new JTable();
//		table.setCellEditor(tCellEditor);
		table.setModel(model);
		//给table添加键盘事件，按回车健时将数据传回数据库
		table.addKeyListener(new KeyAdapter()  {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					//获取按回车时所选的行号和列号
					int row = table.getSelectedRow();
					int column = table.getSelectedColumn();
					String id = table.getValueAt(row, 0).toString();
					//获得单元格的值
					String value = table.getValueAt(row, column).toString();
					//判断输入的时是否为空
					if (id.equals("") || value.equals("")) {
						JOptionPane.showMessageDialog(null, "输入的值不能为空","警告",JOptionPane.WARNING_MESSAGE);
					}else {
					//判断该值属于哪一列
						if(column == 1) {
							clerkDao.updateName(id, value);
						}if(column == 4) {
							clerkDao.updatePwd(id, value);
						}
					}
				}
			}
		});
		
		//设置表格的内容相关
//		table.setFont(new Font(null,Font.BOLD,20));
		table.setRowHeight(40);
//		table.getTableHeader().setFont(new Font(null,Font.BOLD,22));
		table.setFont(new Font(null, Font.BOLD, 14));   //改变表格字体

		//设计Box布局
		Box vBox1 = Box.createVerticalBox();
		JLabel label = new JLabel("您的信息如下:");
//		label.setFont(new Font("宋体",Font.BOLD,32));
		JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jPanel.add(label);
		//将表头和panel放在vBox1中
		vBox1.add(jPanel);
		vBox1.add(table.getTableHeader());
		
		Box vBox2 = Box.createVerticalBox();
		
		sureButton.setFocusPainted(false);
//		sureButton.setFont(new Font("黑体",Font.BOLD,22));
//		sureButton.setBorder(BorderFactory.createLoweredBevelBorder());
		sureButton.setPreferredSize(new Dimension(70,30));
		JPanel panel1 = new JPanel();
		panel1.add(sureButton);
		
		JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel label2 = new JLabel("注：只能修改姓名、密码");
//		label2.setFont(new Font("宋体",Font.BOLD,22));
		panel2.add(label2);
		
		vBox2.add(table);
		vBox2.add(panel1);
		vBox2.add(panel2);
		
		//将vBox添加在边界布局的北部（上面）
		add(vBox1,BorderLayout.NORTH);
		//表格内容添加在中部
		add(vBox2,BorderLayout.CENTER);
		//给sureButton添加事件监听器
		sureButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
//				sureButton.setFont(new Font("黑体",Font.BOLD,22));
//				sureButton.setForeground(Color.black);
				//结束编辑状态
				if(table.isEditing()) {
					table.getCellEditor().stopCellEditing();
				}
				//获取表格的值
				String id = table.getValueAt(0, 0).toString();
				String name = table.getValueAt(0, 1).toString();
				String job = table.getValueAt(0, 2).toString();
				String password = table.getValueAt(0, 4).toString();
				//判断输入的值是否为空
				if(name.equals("") || password.equals("")) {
					model = new DefaultTableModel(rowData,columnNames);
					table.setModel(model);
					JOptionPane.showMessageDialog(null, "姓名或密码不能为空！","警告",JOptionPane.WARNING_MESSAGE);
				}else {
					//将值传入数据库
					int succ = clerkDao.updateClerk(id,name, job, password);
					//返回是否修改成功
					if(succ>=1) {
						JOptionPane.showMessageDialog(null, "修改成功！","提示",JOptionPane.WARNING_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(null, "修改失败！","提示",JOptionPane.WARNING_MESSAGE);
					}
				}
				
			}
		});
		
		//给按钮添加鼠标事件
//		sureButton.addMouseListener(new MouseListener() {
//
//			@Override
//			public void mouseReleased(MouseEvent e) {}
//
//			@Override
//			public void mousePressed(MouseEvent e) {
//				// TODO 自动生成的方法存根
//				sureButton.setFont(new Font("黑体",Font.BOLD,22));
//				sureButton.setForeground(Color.red);
//			}
//
//			@Override
//			public void mouseExited(MouseEvent e) {
//				// TODO 自动生成的方法存根
//				sureButton.setFont(new Font("黑体",Font.BOLD,22));
//				sureButton.setForeground(Color.black);
//			}
//
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				// TODO 自动生成的方法存根
//				sureButton.setFont(new Font("黑体",Font.BOLD,25));
//				sureButton.setForeground(Color.blue);
//			}
//
//			@Override
//			public void mouseClicked(MouseEvent e) {}
//		});
	}
	
	/**
	 * 将部门信息list转为二维数组
	 * @param list
	 * @return
	 */
	private Object[][] getRowData(List<Clerk> list){
		if(list!=null && list.size()>0){
			Object[][] data = new Object[list.size()][7];
			for(int i=0;i<list.size();i++){
				data[i][0] = list.get(i).getId();
				data[i][1] = list.get(i).getName();
				data[i][2] = list.get(i).getJob();
				data[i][3] = list.get(i).getJid();
				data[i][4] = list.get(i).getPassword();
			}
			return data;
		}
		return null;
	}
}
