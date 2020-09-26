package com.csu.root.productInfo;

import com.csu.dao.ProductDao;

import java.awt.*;
import javax.swing.*;

public class DeleteDialog extends JFrame{
    public DeleteDialog(ProductDao productDao, ProductInfoFrame parentFrame) {
        Box vbox = Box.createVerticalBox();

        JPanel radioButtonPanel = new JPanel();
        JLabel wordLabel = new JLabel("选择关键字：");
        JRadioButton nameRbt = new JRadioButton("商品名", true);
        JRadioButton idRbt = new JRadioButton("商品ID");
        ButtonGroup rbtGroup = new ButtonGroup();
        rbtGroup.add(nameRbt);
        rbtGroup.add(idRbt);
        radioButtonPanel.add(wordLabel);
        radioButtonPanel.add(nameRbt);
        radioButtonPanel.add(idRbt);

        JPanel inputPanel = new JPanel();
        JTextField input = new JTextField();
        input.setPreferredSize(new Dimension(200, 30));
        inputPanel.add(input);


        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new JButton("确认");
        JButton cancelButton = new JButton("取消");
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        confirmButton.addActionListener(event -> {
        	int result = 0;
            if (nameRbt.isSelected()) {
                String name = input.getText();
                result = productDao.deleteProductByName(name);
            }
            else {
                int id = Integer.parseInt(input.getText());
                result = productDao.deleteProductById(id);
            }
            
            if (result == 1) {
            	JOptionPane.showMessageDialog(this, "成功", "删除商品", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
            	JOptionPane.showMessageDialog(this, "失败", "删除商品", JOptionPane.INFORMATION_MESSAGE);
            }

            input.setText("");
            parentFrame.showProductData();
        });
        cancelButton.addActionListener(event -> {
            setVisible(false);
        });

        vbox.add(radioButtonPanel);
        vbox.add(inputPanel);
        vbox.add(buttonPanel);

        setContentPane(vbox);

        pack();
        setLocationRelativeTo(null);
        setTitle("删除商品");
    }
}
