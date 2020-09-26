package com.csu.root.roomInformation;

import com.csu.dao.RoomDao;

import java.awt.*;
import java.util.List;
import javax.swing.*;

public class DeleteRoom extends JFrame{
    public DeleteRoom(RoomDao roomDao, RoomIfo parentFrame) {
        Box vbox = Box.createVerticalBox();

        JPanel idPanel = new JPanel();
        JLabel idLabel = new JLabel("房间号");
        idLabel.setPreferredSize(new Dimension(80, 30));
        JTextField idField = new JTextField();
        idField.setPreferredSize(new Dimension(120, 30));
        idPanel.add(idLabel);
        idPanel.add(idField);

        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new JButton("确认");
        JButton cancelButton = new JButton("取消");
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        confirmButton.addActionListener(event -> {
            if (idField.getText().trim() == "") {
                JOptionPane.showMessageDialog(this, "请输入房间号", "删除房间", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if (roomDao.deleteRoomById(Integer.parseInt(idField.getText().trim())) == 1) {
                JOptionPane.showMessageDialog(this, "成功", "删除房间", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(this, "失败", "删除房间", JOptionPane.INFORMATION_MESSAGE);
            }

            idField.setText("");
            parentFrame.refresh();
        });
        cancelButton.addActionListener(event -> {
            setVisible(false);
        });

        vbox.add(idPanel);
        vbox.add(buttonPanel);

        setContentPane(vbox);

        pack();
        setLocationRelativeTo(null);
        setTitle("删除房间");
    }
}
