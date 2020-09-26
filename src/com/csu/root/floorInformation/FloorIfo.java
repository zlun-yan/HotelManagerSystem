package com.csu.root.floorInformation;

import com.csu.bean.Floor;
import com.csu.dao.FloorDao;
import com.csu.dao.FloorDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.util.List;

public class FloorIfo extends JFrame {

    private JLabel tolPageLabel;

    private JTable table;         //表
    private String[] columnName;  //表头
    private Object[][] rowDate;   //表格数据
    private DefaultTableModel model;   //表格显示数据模型  表头+数据

    private int totPeople = 0;
    private int curPage = 1;
    private int pageSize = 2;
    private int totPage = 0;

    private String queryValue = null;

    private JButton preButton;
    private JButton nxtButton;

    private FloorDao floorDao = new FloorDaoImpl();

    public FloorIfo() {
        initMenu();
        setTotRecord();
        setTotPage();

        initData();

        initToolBar();

        pack();
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setSize(800,600);
        setLocationRelativeTo(null);
        setTitle("楼层信息");
    }

    //初始化菜单栏
    public void initMenu(){
        JMenuBar menuBar = new JMenuBar();

        JMenu menu1 = new JMenu("编辑");

        JMenuItem addItem = new JMenuItem("新增");
        addItem.setAccelerator(KeyStroke.getKeyStroke('W', InputEvent.CTRL_DOWN_MASK));
        JMenuItem changeItem = new JMenuItem("修改");
        changeItem.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.CTRL_DOWN_MASK));
        JMenuItem deleteItem = new JMenuItem("删除");
        deleteItem.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.CTRL_DOWN_MASK));

        menu1.add(addItem);
        menu1.add(changeItem);
        menu1.add(deleteItem);

        menuBar.add(menu1);

        setJMenuBar(menuBar);

        addItem.addActionListener(event -> {
            AddFloorFrame frame = new AddFloorFrame(this);
            frame.setVisible(true);
        });

        changeItem.addActionListener(event -> {
            ChangeFloorFrame frame = new ChangeFloorFrame(this);
            frame.setVisible(true);
        });

        deleteItem.addActionListener(event -> {
            DeleteFloorFrame frame = new DeleteFloorFrame(this);
            frame.setVisible(true);
        });
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

//        JLabel sizeLabel = new JLabel("设置每页显示记录数：");
//        JTextField sizeField = new JTextField();
//        JButton sizeButton = new JButton("确定");
//        sizeButton.addActionListener(event -> {
//            pageSize = Integer.parseInt(sizeField.getText());
//            setTotPage();
//            sizeField.setText("");
//            tolPageLabel.setText("总页数：" + totPage + "（每页显示" + pageSize + "条）");
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

//        toolBar.add(sizeLabel);
//        toolBar.add(sizeField);
//        toolBar.add(sizeButton);

        add(toolBar, BorderLayout.SOUTH);
    }

    private void showRoomData() {
        rowDate =getRowData(floorDao.getFloorByPage((curPage - 1) * pageSize, pageSize)) ;
        columnName = new String[] { "楼层", "房间数量"};
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
        totPeople = floorDao.getCount();
        System.out.println(totPeople + "");
    }

    private void setTotPage() {
        totPage = (int)Math.ceil(1.0 * totPeople / pageSize);
    }

    private  Object[][] getRowData(List<Floor> list){
        if (list!=null&&list.size()>0){
            Object[][] data = new Object[list.size()][2];
            for (int i=0;i<list.size();i++){
                data[i][0] = list.get(i).getFloor();
                data[i][1] = list.get(i).getLastRoom();
            }
            return data;
        }
        return  null;
    }

    public void refresh() {
        curPage = 1;
        setTotRecord();
        setTotPage();
        setPageButtonStatus();
        tolPageLabel.setText("总页数：" + totPage + "（每页显示" + pageSize + "条）");
        showRoomData();
    }
}
