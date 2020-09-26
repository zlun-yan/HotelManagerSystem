package com.csu.manager.incomeTableFrame;

import com.csu.dao.*;
import com.csu.dbUtil.DBUtils;
import com.csu.update.*;

import java.awt.*;
import java.awt.event.InputEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class IncomeTableFrame extends JFrame{
//    JPanel mainPanel = new ImgPanel();
    String jid;

    private String[] today = DBUtils.getDateString().split("-");

    private JTable table;         //表
    private String[] columnName;  //表头
    private Object[][] rowDate;   //表格数据
    private DefaultTableModel model;   //表格显示数据模型  表头+数据

    private int totPeople = 0;
    private int curPage = 1;
    private int pageSize = 20;
    private int totPage = 0;

    private JButton preButton;
    private JButton nxtButton;

    private IncomeDao incomeDao = new IncomeImpl();

    public IncomeTableFrame(String jid) {
        this.jid = jid;

        setTotRecord();
        setTotPage();

        initQuery();
        initData();
//        add(mainPanel, BorderLayout.CENTER);

        initMenu();
        initToolBar();

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("收入报表");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
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

    private void initQuery() {
        JPanel queryPanel = new JPanel();
        JButton queryButton = new JButton("查询");
        queryPanel.add(queryButton);

        queryButton.addActionListener(event -> {
            QueryDialog dialog = new QueryDialog(incomeDao);
            dialog.setVisible(true);
        });

        add(queryPanel, BorderLayout.NORTH);
    }

    private void initData() {
        table = new JTable();
        showRoomData();
        table.setFont(new Font(null, Font.BOLD, 14));   //改变表格字体
        table.setRowHeight(30);  //设置行高

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);
        tablePanel.add(table, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);
    }

    /**
     * 初始化工具栏
     */
    private void initToolBar() {
        JToolBar toolBar = new JToolBar();
        preButton = new JButton("上一页");
        nxtButton = new JButton("下一页");

        JLabel nowPage = new JLabel("1");
        JLabel tolPageLabel = new JLabel("总页数：" + totPage + "（每页显示" + pageSize + "条）");
        preButton.addActionListener(event -> {
            curPage--;
            nowPage.setText("" + curPage);
            setPageButtonStatus();
            showRoomData();
        });
        nxtButton.addActionListener(event -> {
            curPage++;
            nowPage.setText("" + curPage);
            setPageButtonStatus();
            showRoomData();
        });
        setPageButtonStatus();

        JLabel gotoLabel1 = new JLabel("前往第");
        JLabel gotoLabel2 = new JLabel("页");
        JTextField gotoField = new JTextField();
        JButton gotoButton = new JButton("前往");
        gotoButton.addActionListener(event -> {
            int temp = Integer.parseInt(gotoField.getText());
            if (1 <= temp && temp <= totPage) {
                curPage = temp;
                nowPage.setText("" + temp);
                setPageButtonStatus();
                showRoomData();
            }
            gotoField.setText("");
        });

        toolBar.add(preButton);
        toolBar.add(nowPage);
        toolBar.add(nxtButton);

        toolBar.add(gotoLabel1);
        toolBar.add(gotoField);
        toolBar.add(gotoLabel2);
        toolBar.add(gotoButton);

        toolBar.add(tolPageLabel);

        add(toolBar, BorderLayout.SOUTH);
    }

    private void showRoomData() {
        rowDate = incomeDao.getIncomeRowDate((curPage - 1) * pageSize, pageSize, null, 0);
        columnName = new String[] {"日期", "收入", "预定人数", "入住人数", "离店人数"};
        model = new DefaultTableModel(rowDate, columnName);
        table.setModel(model);  //设置模型就是把这个模型显示出来了
    }

    /**
     * 设置上一页下一页按钮点击后是否存在上一页或下一页
     */
    private void setPageButtonStatus() {
        preButton.setEnabled(true);
        nxtButton.setEnabled(true);
        if (curPage <= 1) {
            preButton.setEnabled(false);
        }
        if (curPage >= totPage) {
            nxtButton.setEnabled(false);
        }
    }

    private void setTotRecord() {
        totPeople = incomeDao.getCount();
    }

    private void setTotPage() {
        totPage = (int)Math.ceil(1.0 * totPeople / pageSize);
    }
}
