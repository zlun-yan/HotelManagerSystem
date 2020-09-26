package com.csu.manager.incomeTableFrame;

import com.csu.dao.IncomeDao;
import com.csu.dbUtil.DBUtils;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class QueryDialog extends JFrame{
    String[] firstDay;
    String[] nowDay;

    int[] days = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private JTable table;         //表
    String[] columnName = new String[] {"日期", "收入", "预定人数", "入住人数", "离店人数"};
    private Object[][] rowDate;   //表格数据
    private DefaultTableModel model;   //表格显示数据模型  表头+数据

    JComboBox<String> yearCombo;
    JComboBox<String> monthCombo;
    JComboBox<String> dayCombo;

    IncomeDao incomeDao = null;

    Box box = Box.createVerticalBox();

    public QueryDialog(IncomeDao incomeDao) {
        firstDay = incomeDao.getTheFirstDay();
        nowDay = DBUtils.getDateString().split("-");

        this.incomeDao = incomeDao;

        initQueryCombo();

        table = new JTable();
        model = new DefaultTableModel(rowDate, columnName);
        table.setModel(model);  //设置模型就是把这个模型显示出来了
        table.setFont(new Font(null, Font.BOLD, 14));   //改变表格字体
        table.setRowHeight(30);  //设置行高

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);
        tablePanel.add(table, BorderLayout.CENTER);

        box.add(tablePanel);

        setContentPane(box);
        pack();
        setTitle("查询");
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }

    private void initQueryCombo() {
        JPanel labelPanel = new JPanel();
        JPanel comboPanel = new JPanel();

        JLabel yearLabel = new JLabel("年份", SwingConstants.CENTER);
        JLabel monthLabel = new JLabel("月份", SwingConstants.CENTER);
        JLabel dayLabel = new JLabel("日期", SwingConstants.CENTER);
        yearLabel.setPreferredSize(new Dimension(80, 30));
        monthLabel.setPreferredSize(new Dimension(80, 30));
        dayLabel.setPreferredSize(new Dimension(80, 30));
        labelPanel.add(yearLabel);
        labelPanel.add(monthLabel);
        labelPanel.add(dayLabel);

        yearCombo = new JComboBox<>();
        monthCombo = new JComboBox<>();
        dayCombo = new JComboBox<>();
        yearCombo.setPreferredSize(new Dimension(80, 30));
        monthCombo.setPreferredSize(new Dimension(80, 30));
        dayCombo.setPreferredSize(new Dimension(80, 30));

        for (int i = Integer.parseInt(nowDay[0]); i >= Integer.parseInt(firstDay[0]); i--) {
            yearCombo.addItem(i + "");
        }

        monthCombo.addItem("---");
        for (int i = 1; i <= 12 ; i++) {
            monthCombo.addItem(i + "");
        }

        dayCombo.addItem("---");

        monthCombo.addActionListener(event -> {
            dayCombo.removeAllItems();
            dayCombo.addItem("---");
            for (int i = 1; i  <= days[monthCombo.getSelectedIndex()]; i++) {
                dayCombo.addItem(i + "");
            }

            if (judgeLeap() && monthCombo.getSelectedIndex() == 2) {
                dayCombo.addItem("29");
            }
        });

        comboPanel.add(yearCombo);
        comboPanel.add(monthCombo);
        comboPanel.add(dayCombo);

        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new JButton("确认");
        buttonPanel.add(confirmButton);
        confirmButton.addActionListener(event -> {
            String year = yearCombo.getItemAt(yearCombo.getSelectedIndex());
            int month = monthCombo.getSelectedIndex();
            int day = dayCombo.getSelectedIndex();

            if (month == 0) {
                //按年查询
                rowDate = incomeDao.getIncomeRowDate(0, 1, year, 2);
            }
            else {
                if (day == 0) {
                    //按月查询
                    rowDate = incomeDao.getIncomeRowDate(0, 1, year + "-" + String.format("%02d", month), 1);
                }
                else {
                    //按日查询
                    rowDate = incomeDao.getIncomeRowDate(0, 1, year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day), 0);
                }
            }
            model = new DefaultTableModel(rowDate, columnName);
            table.setModel(model);  //设置模型就是把这个模型显示出来了
            pack();
        });

        box.add(labelPanel);
        box.add(comboPanel);
        box.add(buttonPanel);
    }

    boolean judgeLeap() {
        int year = Integer.parseInt(yearCombo.getItemAt(yearCombo.getSelectedIndex()));
        if (year % 100 == 0) {
            if (year % 400 == 0) return true;
        }
        else {
            if (year % 4 == 0) return true;
        }

        return false;
    }
}
