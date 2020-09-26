package com.csu.root.roomInformation;

import com.csu.dao.RoomDao;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class AddRoom extends JFrame{
    public AddRoom(RoomDao roomDao, RoomIfo parentFrame) {
        Box vbox = Box.createVerticalBox();

        JPanel idPanel = new JPanel();
        JLabel idLabel = new JLabel("房间号", SwingConstants.CENTER);
        idLabel.setPreferredSize(new Dimension(80, 30));
        JTextField idField = new JTextField();
        idField.setPreferredSize(new Dimension(120, 30));
        idPanel.add(idLabel);
        idPanel.add(idField);

        JPanel typePanel = new JPanel();
        JLabel typeLabel = new JLabel("房间类型", SwingConstants.CENTER);
        typeLabel.setPreferredSize(new Dimension(80, 30));
        JComboBox<String> typeCombo = new JComboBox<>();
        typeCombo.setPreferredSize(new Dimension(120, 30));

        List<String> list = roomDao.queryRoomType();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
            typeCombo.addItem(list.get(i));
        }

        typePanel.add(typeLabel);
        typePanel.add(typeCombo);

        JPanel floorPanel = new JPanel();
        JLabel floorLabel = new JLabel("楼层", SwingConstants.CENTER);
        floorLabel.setPreferredSize(new Dimension(80, 30));
        JTextField floorField = new JTextField();
        floorField.setPreferredSize(new Dimension(120, 30));
        floorPanel.add(floorLabel);
        floorPanel.add(floorField);

        JPanel pricePanel = new JPanel();
        JLabel priceLabel = new JLabel("价格", SwingConstants.CENTER);
        priceLabel.setPreferredSize(new Dimension(80, 30));
        JTextField priceField = new JTextField();
        priceField.setPreferredSize(new Dimension(120, 30));
        pricePanel.add(priceLabel);
        pricePanel.add(priceField);


        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new JButton("确认");
        JButton cancelButton = new JButton("取消");
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        confirmButton.addActionListener(event -> {
            if (idField.getText().trim() == "") {
                JOptionPane.showMessageDialog(this, "请输入房间号", "新增房间", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            else if (floorField.getText().trim() == "") {
                JOptionPane.showMessageDialog(this, "请输入楼层", "新增房间", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            else if (priceField.getText().trim() == "") {
                JOptionPane.showMessageDialog(this, "请输入房间价格", "新增房间", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            int id = Integer.parseInt(idField.getText().trim());
            String type = typeCombo.getItemAt(typeCombo.getSelectedIndex());
            int floor = Integer.parseInt(floorField.getText().trim());
            double price = Double.parseDouble(priceField.getText().trim());

            if (roomDao.insertRoom(id, type, floor, price) == 1) {
                JOptionPane.showMessageDialog(this, "成功", "新增房间", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(this, "失败", "新增房间", JOptionPane.INFORMATION_MESSAGE);
            }

            idField.setText("");
            floorField.setText("");
            priceField.setText("");
            parentFrame.refresh();
        });
        cancelButton.addActionListener(event -> {
            setVisible(false);
        });

        vbox.add(idPanel);
        vbox.add(typePanel);
        vbox.add(floorPanel);
        vbox.add(pricePanel);
        vbox.add(buttonPanel);

        setContentPane(vbox);

        pack();
        setLocationRelativeTo(null);
        setTitle("新增房间");
    }
}
