package com.csu.root.productInfo;

import com.csu.dao.*;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ProductInfoFrame extends JFrame{
    private JTable table;         //表
    private String[] columnName;  //表头
    private Object[][] rowDate;   //表格数据
    private DefaultTableModel model;   //表格显示数据模型  表头+数据

    private int totPeople = 0;
    private int curPage = 1;
    private int pageSize = 10;
    private int totPage = 0;

    private String queryValue = null;

    private JButton preButton;
    private JButton nxtButton;

    private ProductDao productDao = new ProductImpl();
    private Box vbox = Box.createVerticalBox();

    public ProductInfoFrame() {
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setTitle("商品信息");
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setTotRecord();
        setTotPage();

        initMenuBar();

        initQuery();
        initData();
//        setContentPane(vbox);
        add(vbox, BorderLayout.CENTER);

        initToolBar();

        pack();
        setTitle("商品信息");
//        setSize(800, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        //一级菜单
        JMenu editMenu = new JMenu("编辑");
        //一级菜单的菜单项
        JMenuItem addProductItem = new JMenuItem("补货");
        addProductItem.setAccelerator(KeyStroke.getKeyStroke('W', InputEvent.CTRL_DOWN_MASK));
        JMenuItem addItemType = new JMenuItem("添加商品种类");
        addItemType.setAccelerator(KeyStroke.getKeyStroke('W', InputEvent.ALT_DOWN_MASK));
        JMenuItem updateNameItem = new JMenuItem("修改商品名称");
        updateNameItem.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.CTRL_DOWN_MASK));
        JMenuItem updatePriceItem = new JMenuItem("修改商品单价");
        updatePriceItem.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.ALT_DOWN_MASK));
        JMenuItem deleteItem = new JMenuItem("删除商品");
        deleteItem.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.CTRL_DOWN_MASK));

        addProductItem.addActionListener(event -> {
            AddProductDialog dialog = new AddProductDialog(productDao, this);
            dialog.setVisible(true);
        });
        addItemType.addActionListener(event -> {
            InsertTypeDialog dialog = new InsertTypeDialog(productDao, this);
            dialog.setVisible(true);
        });
        updateNameItem.addActionListener(event -> {
            UpdateNameDialog dialog = new UpdateNameDialog(productDao, this);
            dialog.setVisible(true);
        });
        updatePriceItem.addActionListener(event -> {
            UpdatePriceDialog dialog = new UpdatePriceDialog(productDao, this);
            dialog.setVisible(true);
        });
        deleteItem.addActionListener(event -> {
            DeleteDialog dialog = new DeleteDialog(productDao, this);
            dialog.setVisible(true);
        });

        editMenu.add(addProductItem);
        editMenu.add(addItemType);
        editMenu.add(updateNameItem);
        editMenu.add(updatePriceItem);
        editMenu.add(deleteItem);
        menuBar.add(editMenu);

        setJMenuBar(menuBar);

    }

    private void initQuery() {
        JPanel queryPanel = new JPanel();
        JTextField input = new JTextField();
        input.setPreferredSize(new Dimension(300, 30));
        JButton confirmButton = new JButton("确认");

        confirmButton.addActionListener(event -> {
            String info = input.getText().trim();
            if (info.equals("")) {
                queryValue = null;
                input.setText("");
            }
            else {
                queryValue = info;
                input.setText(info);
            }

            showProductData();
        });

        queryPanel.add(input);
        queryPanel.add(confirmButton);
        vbox.add(queryPanel);
    }

    private void initData() {
        table = new JTable();
        showProductData();
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
        JLabel tolPageLabel = new JLabel("总页数：" + totPage + "（每页显示" + pageSize + "条）");
        preButton.addActionListener(event -> {
            curPage--;
            nowPage.setText("" + curPage);
            setPageButtonStatus();

            showProductData();
        });
        nxtButton.addActionListener(event -> {
            curPage++;
            nowPage.setText("" + curPage);
            setPageButtonStatus();

            showProductData();
        });
        setPageButtonStatus();

        JLabel sizeLabel = new JLabel("设置每页显示记录数：");
        JTextField sizeField = new JTextField();
        JButton sizeButton = new JButton("确定");
        sizeButton.addActionListener(event -> {
            pageSize = Integer.parseInt(sizeField.getText());
            setTotPage();
            sizeField.setText("");
            tolPageLabel.setText("总页数：" + totPage + "（每页显示" + pageSize + "条）");

            showProductData();
        });

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

                showProductData();
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

        toolBar.add(sizeLabel);
        toolBar.add(sizeField);
        toolBar.add(sizeButton);

        add(toolBar, BorderLayout.SOUTH);
    }

    public void showProductData() {
        rowDate = productDao.getProductRowDate((curPage - 1) * pageSize, pageSize, queryValue);
        columnName = new String[] {"ID", "商品名", "单价", "剩余数量"};
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
        totPeople = productDao.getCount();
    }

    private void setTotPage() {
        totPage = (int)Math.ceil(1.0 * totPeople / pageSize);
    }
}
