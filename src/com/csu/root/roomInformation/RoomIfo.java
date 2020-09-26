package com.csu.root.roomInformation;



import com.csu.bean.Room;
import com.csu.dao.RoomDao;
import com.csu.dao.RoomImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.util.List;

public class RoomIfo extends JFrame{
    //写为成员变量才解决问题
    public JLabel tolPageLabel;

    private JTable table;         //表
    private String[] columnName;  //表头
    private Object[][] rowDate;   //表格数据
    private DefaultTableModel model;   //表格显示数据模型  表头+数据

    private int totPeople = 0;
    public int curPage = 1;
    private int pageSize = 20;
    private int totPage = 0;

    private String queryValue = null;

    private JButton preButton;
    private JButton nxtButton;

    private RoomDao roomIfoDao = new RoomImpl();

    public RoomIfo() {
        initMenu();
        setTotRecord();
        setTotPage();

        initSearchBlock();
        initData();

        initToolBar();

        pack();
        setLocationRelativeTo(null);
        setTitle("客房类型信息");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    //初始化菜单栏
    public void initMenu(){
        JMenuBar menuBar = new JMenuBar();

        JMenu menu1 = new JMenu("操作");
        JMenu menu2 = new JMenu("查看");

        JMenuItem addItem = new JMenuItem("新增房间类型");
        addItem.setAccelerator(KeyStroke.getKeyStroke('W', InputEvent.CTRL_DOWN_MASK));
        JMenuItem addRoom = new JMenuItem("新增房间");
        addRoom.setAccelerator(KeyStroke.getKeyStroke('W', InputEvent.ALT_DOWN_MASK));
        JMenuItem changeItem = new JMenuItem("修改房间类型");
        changeItem.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.CTRL_DOWN_MASK));
        JMenuItem deleteItem = new JMenuItem("删除房间类型");
        deleteItem.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.CTRL_DOWN_MASK));
        JMenuItem deleteRoom = new JMenuItem("删除房间");
        deleteRoom.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.ALT_DOWN_MASK));

        menu1.add(addItem);
        menu1.add(addRoom);
        menu1.add(changeItem);
        menu1.add(deleteItem);
        menu1.add(deleteRoom);

        JMenuItem displayItem = new JMenuItem("客房类型列表");
        displayItem.setAccelerator(KeyStroke.getKeyStroke('Q', InputEvent.CTRL_DOWN_MASK));
        menu2.add(displayItem);

        menuBar.add(menu1);
        menuBar.add(menu2);

        setJMenuBar(menuBar);

        addRoom.addActionListener(event -> {
            AddRoom frame = new AddRoom(roomIfoDao, this);
            frame.setVisible(true);
        });
        deleteRoom.addActionListener(event -> {
            DeleteRoom frame = new DeleteRoom(roomIfoDao, this);
            frame.setVisible(true);
        });





        addItem.addActionListener(event -> {
            AddTypeFrame frame = new AddTypeFrame(this);
            frame.setVisible(true);
        });

        changeItem.addActionListener(event -> {
            UpdateTypeFrame frame = new UpdateTypeFrame(this);
            frame.setVisible(true);
        });

        deleteItem.addActionListener(event -> {
            DeleteTypeFrame frame = new DeleteTypeFrame(this);
            frame.setVisible(true);
        });

        displayItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DisplayFrame frame = new DisplayFrame();
                frame.setVisible(true);
            }
        });
    }

    public void initSearchBlock(){
        JPanel searchPanel = new JPanel();
        JLabel typeLabel = new JLabel("房间类型");
        JTextField typeField = new JTextField(50);
        JButton searchBtn = new JButton("查询");

        //查询按钮事件
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                queryValue = typeField.getText().trim();
                typeField.setText(queryValue);
                if (queryValue.equals("")) {
                	queryValue = null;
                }
                
                //接下来的这一串指令是实现功能的核心
                setTotRecord();
                setTotPage();
                showRoomData();
                setPageButtonStatus();
                tolPageLabel.setText("总页数：" + totPage + "（每页显示" + pageSize + "条）");
            }
        });

        searchPanel.add(typeLabel);
        searchPanel.add(typeField);
        searchPanel.add(searchBtn);
        add(searchPanel,BorderLayout.NORTH);
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
        rowDate =getRowData(roomIfoDao.getRoomByPage((curPage - 1) * pageSize, pageSize, queryValue)) ;
        columnName = new String[] {"房间号", "类型", "楼层", "价格"};
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
        if (queryValue != null) {
            totPeople = roomIfoDao.getCount(queryValue);
        }
        else {
            totPeople = roomIfoDao.getCount();
        }
        System.out.println(totPeople + "");
    }

    private void setTotPage() {
        totPage = (int)Math.ceil(1.0 * totPeople / pageSize);
    }

    private  Object[][] getRowData(List<Room> list){
        if (list!=null&&list.size()>0){
            Object[][] data = new Object[list.size()][4];
            for (int i=0;i<list.size();i++){
                data[i][0] = list.get(i).getRoomId();
                data[i][1] = list.get(i).getType();
                data[i][2] = list.get(i).getFloor();
                data[i][3] = list.get(i).getPrice();
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
