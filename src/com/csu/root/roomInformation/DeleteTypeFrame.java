package com.csu.root.roomInformation;

import com.csu.dao.RoomDao;
import com.csu.dao.RoomImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteTypeFrame extends JFrame{
    public DeleteTypeFrame(RoomIfo parentFrame){
        JPanel inputPanel1 = new JPanel();
        JTextField typeField = new JTextField(15);
        JLabel typeLabel = new JLabel("类型");
        inputPanel1.add(typeLabel);
        inputPanel1.add(typeField);

        JPanel submitPanel = new JPanel();
        JButton submitBtn = new JButton("提交");
        JButton clearBtn = new JButton("清空");
        submitPanel.add(submitBtn);
        submitPanel.add(clearBtn);

        Box vBox = Box.createVerticalBox();
        vBox.add(inputPanel1);
        vBox.add(submitPanel);
        setContentPane(vBox);

        //提交按钮事件
        submitBtn.addActionListener(event -> {
            String type = typeField.getText().trim();
            RoomDao roomIfoDao = new RoomImpl();
            int res = roomIfoDao.deleteRoom(type);
            if (res==1){
                JOptionPane.showMessageDialog(
                        this,
                        "删除成功",
                        "成功提示",
                        JOptionPane.INFORMATION_MESSAGE
                );
                parentFrame.refresh();
            }
            else {
                JOptionPane.showMessageDialog(
                        this,
                        "删除失败",
                        "失败提示",
                        JOptionPane.INFORMATION_MESSAGE

                );
            }
        });

        //清空按钮事件
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                typeField.setText("");
            }
        });

        pack();
        setTitle("删除房型");
        setLocationRelativeTo(null);
    }
}
