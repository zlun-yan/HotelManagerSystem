package com.csu.root.clerkInfo;

import com.csu.dao.ClerkDao;
import com.csu.dao.ClerkImpl;

import java.awt.*;
import java.awt.event.InputEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ClerkInfoFrame extends JFrame{
    JLabel tolPageLabel;

    private JTable table;         //表
    private String[] columnName;  //表头
    private Object[][] rowDate;   //表格数据
    private DefaultTableModel model;   //表格显示数据模型  表头+数据

    private int totPeople = 0;
    private int curPage = 1;
    private int pageSize = 22;
    private int totPage = 0;

    private int state = 0;
    private String queryValue = null;

    private JButton preButton;
    private JButton nxtButton;

    private ClerkDao clerkDao = new ClerkImpl();
    private Box vbox = Box.createVerticalBox();

    public ClerkInfoFrame() {
        setTotRecord();
        setTotPage();

        initMenuBar();

        initQuery();
        initData();
        add(vbox, BorderLayout.CENTER);

        initToolBar();

        pack();
        setTitle("员工信息");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        //一级菜单
        JMenu editMenu = new JMenu("编辑");
        //一级菜单的菜单项
        JMenuItem addClerk = new JMenuItem("增加员工");
        addClerk.setAccelerator(KeyStroke.getKeyStroke('W', InputEvent.CTRL_DOWN_MASK));
//        JMenuItem updateClerkName = new JMenuItem("修改员工姓名");
        JMenuItem updateClerkJob = new JMenuItem("修改员工职位");
        updateClerkJob.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.CTRL_DOWN_MASK));
        JMenuItem deleteClerk = new JMenuItem("删除员工");
        deleteClerk.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.CTRL_DOWN_MASK));

        addClerk.addActionListener(event -> {
            AddClerkDialog dialog = new AddClerkDialog(clerkDao, this);
            dialog.setVisible(true);
        });
        updateClerkJob.addActionListener(event -> {
            UpdateJobDialog dialog = new UpdateJobDialog(clerkDao, this);
            dialog.setVisible(true);
        });
        deleteClerk.addActionListener(event -> {
            DeleteClerkDialog dialog = new DeleteClerkDialog(clerkDao, this);
            dialog.setVisible(true);
        });

        editMenu.add(addClerk);
//        editMenu.add(updateClerkName);
        editMenu.add(updateClerkJob);
        editMenu.add(deleteClerk);
        menuBar.add(editMenu);

        setJMenuBar(menuBar);
    }

    private void initQuery() {
        JPanel queryPanel = new JPanel();
        JTextField input = new JTextField();
        input.setPreferredSize(new Dimension(300, 30));
        JComboBox<String> queryCombo = new JComboBox<>();
        queryCombo.addItem("员工姓名");
        queryCombo.addItem("员工职位");
        JButton confirmButton = new JButton("确认");

        queryCombo.addActionListener(event -> {
            state = queryCombo.getSelectedIndex();
        });
        confirmButton.addActionListener(event -> {
            String info = input.getText().trim();
            if (info.equals("")) {
                queryValue = null;
            }
            else {
                queryValue = info;
            }
            input.setText(info);

            showRoomData();
            setPageButtonStatus();
            tolPageLabel.setText("总页数：" + totPage + "（每页显示" + pageSize + "条）");
        });

        queryPanel.add(input);
        queryPanel.add(queryCombo);
        queryPanel.add(confirmButton);
        vbox.add(queryPanel);
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

        vbox.add(tablePanel);
    }

    /**
     * 初始化工具栏
     */
    private void initToolBar() {
        JToolBar toolBar = new JToolBar();
        preButton = new JButton("上一页");
        nxtButton = new JButton("下一页");

        JLabel nowPage = new JLabel("1");
        tolPageLabel = new JLabel("总页数：" + totPage + "（每页显示" + pageSize + "条）");
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

    public void showRoomData() {
        //设置模型就是把这个模型显示出来了
        if (state == 0) {
            rowDate = clerkDao.getClerkRowDateWithName((curPage - 1) * pageSize, pageSize, queryValue);
        }
        else {
            rowDate = clerkDao.getClerkRowDateWithJob((curPage - 1) * pageSize, pageSize, queryValue);
        }
        columnName = new String[] {"ID", "姓名", "职位", "工号"};   //不显示密码
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
        totPeople = clerkDao.getCount();
    }

    private void setTotPage() {
        totPage = (int)Math.ceil(1.0 * totPeople / pageSize);
    }
}
