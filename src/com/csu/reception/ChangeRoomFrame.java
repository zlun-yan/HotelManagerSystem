package com.csu.reception;

import com.csu.dao.CustomInfoDao;
import com.csu.dao.CustomInfoImpl;
import com.csu.dao.RoomDao;
import com.csu.dao.RoomImpl;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ChangeRoomFrame extends JFrame{
    private JTable table;         //表
    private String[] columnName;  //表头
    private Object[][] rowDate;   //表格数据
    private DefaultTableModel model;   //表格显示数据模型  表头+数据

    private int totPeople = 0;
    private int curPage = 1;
    private int pageSize = 20;
    private int totPage = 0;
    private JLabel tolPageLabel;

    private int state = 0;
    private String queryValue = null;

    private JButton preButton;
    private JButton nxtButton;

    private JButton queryButton;
    private JComboBox<String> queryCombo;

    Box box;

    private CustomInfoDao customInfoDao = new CustomInfoImpl();
    private RoomDao roomDao = new RoomImpl();

    public ChangeRoomFrame() {
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        box = Box.createVerticalBox();

        initConfirmPanel();
        initQueryPanel();
        add(box, BorderLayout.NORTH);

        initData();

        initToolBar();

        pack();
        setLocationRelativeTo(null);
        setTitle("换房");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void initConfirmPanel() {
        JPanel confirmPanel = new JPanel();
        JLabel tipLabel = new JLabel("请输入房间号：");
        JTextField infoField = new JTextField();
        infoField.setPreferredSize(new Dimension(200, 30));
        JButton confirmButton = new JButton("确定");
        JButton cancelButton = new JButton("取消");

        JPanel changePanel = new JPanel();
        JLabel changeLabel = new JLabel("输入更换的房间号：");
        JTextField changeField = new JTextField();
        changeField.setPreferredSize(new Dimension(200, 30));
        JButton changeButton = new JButton("换房");
        changeField.setEnabled(false);
        changeButton.setEnabled(false);

        confirmButton.addActionListener(event -> {
            //这里确定后 表格中显示信息
            state = 0;
            String info = infoField.getText().trim();  //info  里面现在选择的就是房间号
            if (info.equals("")) {
                //显示提示输入信息
                return;
            }
            else {
                queryValue = info;
                infoField.setText(info);
            }

            if (setPageData() != 0) {
                queryButton.setEnabled(false);
                queryCombo.setEnabled(false);
                confirmButton.setEnabled(false);
                changeField.setEnabled(true);
                changeButton.setEnabled(true);
                
            }
            else {
                //提示当前房间不存在
                JOptionPane.showMessageDialog(this, "当前房间不存在", "提示", JOptionPane.INFORMATION_MESSAGE);
                queryValue = null;
                infoField.setText("");
                setPageData();
            }
            setPageButtonStatus();
        });

        cancelButton.addActionListener(event -> {
            //表格中应该也要改变
            infoField.setText("");
            changeField.setText("");
            queryButton.setEnabled(true);
            queryCombo.setEnabled(true);
            confirmButton.setEnabled(true);
            changeButton.setEnabled(false);
            changeField.setEnabled(false);

            queryValue = null;
            setPageData();
            setPageButtonStatus();
        });

        changeButton.addActionListener(event -> {
            int result = roomDao.changeRoom(Integer.parseInt(infoField.getText()), Integer.parseInt(changeField.getText()));
            if (result == 0) {
                JOptionPane.showMessageDialog(this, "目标房间不可用", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
            else if (result == 1) {
                JOptionPane.showMessageDialog(this, "成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(this, "当前房间与目标房间类型不同", "提示", JOptionPane.INFORMATION_MESSAGE);
            }


            infoField.setText("");
            changeField.setText("");
            queryButton.setEnabled(true);
            queryCombo.setEnabled(true);
            confirmButton.setEnabled(true);
            changeButton.setEnabled(false);
            changeField.setEnabled(false);

            queryValue = null;
            setPageData();
            setPageButtonStatus();
        });

        changePanel.add(changeLabel);
        changePanel.add(changeField);
        changePanel.add(changeButton);

        confirmPanel.add(tipLabel);
        confirmPanel.add(infoField);
        confirmPanel.add(confirmButton);
        confirmPanel.add(cancelButton);

        box.add(confirmPanel);
        box.add(changePanel);
    }

    private void initData() {
        table = new JTable();
        setPageData();
        table.setFont(new Font(null, Font.BOLD, 14));   //改变表格字体
        table.setRowHeight(30);  //设置行高

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);
        tablePanel.add(table, BorderLayout.CENTER);

        add(tablePanel);
    }

    private void initQueryPanel() {
        JTextField queryField = new JTextField();
        queryField.setPreferredSize(new Dimension(200, 30));
        queryButton = new JButton("查询");
        JPanel queryPanel = new JPanel();

        queryCombo = new JComboBox<>();
        queryCombo.addItem("房间号查询");
        queryCombo.addItem("房客身份证号查询");

        queryCombo.addActionListener(event -> {
            state = queryCombo.getSelectedIndex();
            setPageData();
            setPageButtonStatus();
            tolPageLabel.setText("总页数：" + totPage + "（每页显示" + pageSize + "条）");
        });
        queryButton.addActionListener(event -> {
            String info = queryField.getText().trim();
            if (info.equals("")) {
                queryValue = null;
                queryField.setText("");
            }
            else {
                queryValue = info;
                queryField.setText(info);
            }

            setPageData();
            setPageButtonStatus();
        });


        queryPanel.add(queryField);
        queryPanel.add(queryCombo);
        queryPanel.add(queryButton);

//        add(queryPanel, BorderLayout.NORTH);
        box.add(queryPanel);
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
            setPageData();
        });
        nxtButton.addActionListener(event -> {
            curPage++;
            nowPage.setText("" + curPage);
            setPageButtonStatus();
            setPageData();
        });
        setPageButtonStatus();

//        JLabel sizeLabel = new JLabel("设置每页显示记录数：");
//        JTextField sizeField = new JTextField();
//        JButton sizeButton = new JButton("确定");
//        sizeButton.addActionListener(event -> {
//            pageSize = Integer.parseInt(sizeField.getText());
//            setPageData();
//            sizeField.setText("");
//            tolPageLabel.setText("总页数：" + totPage + "（每页显示" + pageSize + "条）");
//            setPageButtonStatus();
//        });

        JLabel gotoLabel1 = new JLabel("前往第");
        JLabel gotoLabel2 = new JLabel("页");
        JTextField gotoField = new JTextField();
        JButton gotoButton = new JButton("前往");
        gotoButton.addActionListener(event -> {
            int temp = Integer.parseInt(gotoField.getText());
            if (1 <= temp && temp <= totPage) {
                curPage = temp;
                nowPage.setText("" + temp);
                setPageData();
                setPageButtonStatus();
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
//
//        toolBar.add(sizeLabel);
//        toolBar.add(sizeField);
//        toolBar.add(sizeButton);

        add(toolBar, BorderLayout.SOUTH);
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

    private int setPageData() {
        //设置模型就是把这个模型显示出来了
        if (state == 0) {
            totPage = (int)Math.ceil(1.0 * roomDao.getCount() / pageSize);
            rowDate = roomDao.getRoomRowDateWithName((curPage - 1) * pageSize, pageSize, queryValue);
            columnName = new String[] {"房间号", "房间类型", "楼层", "价格（元/天）", "状态", "住客ID", "入住（预订）时间", "房间额外消费"};
        }
        else {
            totPage = (int)Math.ceil(1.0 * customInfoDao.getCount() / pageSize);
            rowDate = customInfoDao.getCustomInfoRowDate((curPage - 1) * pageSize, pageSize, queryValue);
            columnName = new String[] {"身份证号", "姓名", "联系电话", "总消费"};
        }
        model = new DefaultTableModel(rowDate, columnName);
        table.setModel(model);  //设置模型就是把这个模型显示出来了

        if (rowDate == null) {
            return 0;
        }
        else {
            return rowDate.length;
        }
    }
}
