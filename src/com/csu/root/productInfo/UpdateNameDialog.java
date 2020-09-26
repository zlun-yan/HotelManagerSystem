package com.csu.root.productInfo;

import com.csu.dao.ProductDao;

import java.awt.*;
import javax.swing.*;

public class UpdateNameDialog extends JFrame{
    public UpdateNameDialog(ProductDao productDao, ProductInfoFrame parentFrame) {
        Box vbox = Box.createVerticalBox();

        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel("商品名称", SwingConstants.CENTER);
        infoLabel.setPreferredSize(new Dimension(100, 30));
        JLabel numLabel = new JLabel("新的商品名称", SwingConstants.CENTER);
        numLabel.setPreferredSize(new Dimension(100, 30));
        infoPanel.add(infoLabel);
        infoPanel.add(numLabel);

        JPanel operPanel = new JPanel();
        JTextField idField = new JTextField();
        idField.setPreferredSize(new Dimension(100, 30));
        JTextField numField = new JTextField();
        numField.setPreferredSize(new Dimension(100, 30));
        operPanel.add(idField);
        operPanel.add(numField);

        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new JButton("确认");
        JButton cancelButton = new JButton("取消");
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        confirmButton.addActionListener(event -> {
            String productName = idField.getText();
            String newName = numField.getText();

            if (productDao.updateProductWithName(productName, newName) == 1) {
                JOptionPane.showMessageDialog(this, "成功", "修改商品名称", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(this, "失败", "修改商品名称", JOptionPane.INFORMATION_MESSAGE);
            }

            idField.setText("");
            numField.setText("");
            parentFrame.showProductData();
        });
        cancelButton.addActionListener(event -> {
            setVisible(false);
        });

        vbox.add(infoPanel);
        vbox.add(operPanel);
        vbox.add(buttonPanel);

        setContentPane(vbox);

        pack();
        setLocationRelativeTo(null);
        setTitle("修改商品名称");
    }
}
