package com.csu.root.roomInformation;

import com.csu.bean.RoomType;
import com.csu.dao.RoomDao;
import com.csu.dao.RoomImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DisplayFrame extends JFrame {
    private JLabel tolPageLabel;

    private JTable table;         //表
    private String[] columnName;  //表头
    private Object[][] rowDate;   //表格数据
    private DefaultTableModel model;   //表格显示数据模型  表头+数据

    private int totPeople = 0;
    private int curPage = 1;
    private int pageSize = 4;
    private int totPage = 0;


    private JButton preButton;
    private JButton nxtButton;

    private RoomDao roomIfoDao = new RoomImpl();

    public DisplayFrame() {
        setTotRecord();
        setTotPage();

        initData();

        initToolBar();

        pack();
        setLocationRelativeTo(null);
        setTitle("客房类型信息");
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
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

        add(tablePanel,BorderLayout.CENTER);
    }

    /**
     * 初始化工具栏
     */
    private void initToolBar() {
        JToolBar toolBar = new JToolBar();
        preButton = new JButton("上一页");
        nxtButton = new JButton("下一页");

        JLabel nowPage = new JLabel("1");
        tolPageLabel = new JLabel("总页数：" + totPage);
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



        toolBar.add(preButton);
        toolBar.add(nowPage);
        toolBar.add(nxtButton);

        toolBar.add(tolPageLabel);

        add(toolBar, BorderLayout.SOUTH);
    }

    private void showRoomData() {
        rowDate =getRowData(roomIfoDao.getRoomTypeByPage((curPage - 1) * pageSize, pageSize));
        columnName = new String[] {"编号", "房间类型", "价格"};
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
        totPeople = roomIfoDao.getCount();
        System.out.println(totPeople + "");
    }

    private void setTotPage() {
        totPage = (int)Math.ceil(1.0 * totPeople / pageSize);
        System.out.println(totPage + "");
    }

    private  Object[][] getRowData(List<RoomType> list){
        if (list!=null&&list.size()>0){
            Object[][] data = new Object[list.size()][3];
            for (int i=0;i<list.size();i++){
                data[i][0] = list.get(i).getId();
                data[i][1] = list.get(i).getTypeName();
                data[i][2] = list.get(i).getLimitNum();
            }
            return data;
        }
        return  null;
    }
}
