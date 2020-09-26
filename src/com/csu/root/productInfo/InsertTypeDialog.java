package com.csu.root.productInfo;

import com.csu.dao.ProductDao;

import java.awt.*;
import javax.swing.*;

public class InsertTypeDialog extends JFrame{


    public InsertTypeDialog(ProductDao productDao, ProductInfoFrame parentFrame) {
        Box vbox = Box.createVerticalBox();

        JPanel namePanel = new JPanel();
        JLabel nameLabel = new JLabel("商品名");
        nameLabel.setPreferredSize(new Dimension(50, 30));
        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(200, 30));
        namePanel.add(nameLabel);
        namePanel.add(nameField);

        JPanel pricePanel = new JPanel();
        JLabel priceLabel = new JLabel("单价");
        priceLabel.setPreferredSize(new Dimension(50, 30));
        JTextField priceField = new JTextField();
        priceField.setPreferredSize(new Dimension(200, 30));
        pricePanel.add(priceLabel);
        pricePanel.add(priceField);

        JPanel numPanel = new JPanel();
        JLabel numLabel = new JLabel("数量");
        numLabel.setPreferredSize(new Dimension(50, 30));
        JTextField numField = new JTextField();
        numField.setPreferredSize(new Dimension(200, 30));
        numPanel.add(numLabel);
        numPanel.add(numField);

        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new JButton("确认");
        JButton cancelButton = new JButton("取消");
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        confirmButton.addActionListener(event -> {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int num = Integer.parseInt(numField.getText());

            if (productDao.insertProductByName(name, price, num) == 1) {
                JOptionPane.showMessageDialog(this, "成功", "添加商品类型", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(this, "失败", "添加商品类型", JOptionPane.INFORMATION_MESSAGE);

            }
            nameField.setText("");
            priceField.setText("");
            numField.setText("");
            parentFrame.showProductData();
        });
        cancelButton.addActionListener(event -> {
            setVisible(false);
        });

        vbox.add(namePanel);
        vbox.add(pricePanel);
        vbox.add(numPanel);
        vbox.add(buttonPanel);

        setContentPane(vbox);

        pack();
        setLocationRelativeTo(null);
        setTitle("添加商品");
    }
}
