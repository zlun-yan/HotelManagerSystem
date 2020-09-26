package com.csu.root.floorInformation;

import com.csu.dao.FloorDao;
import com.csu.dao.FloorDaoImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddFloorFrame extends JFrame {
    public  AddFloorFrame(FloorIfo parentFrame){
        //id-floor   type-roomNum
        JPanel inputPanel1 = new JPanel();
        JPanel inputPanel2 = new JPanel();
        JTextField floorField = new JTextField(15);
        JTextField roomNumField = new JTextField(15);
        JLabel floorLabel = new JLabel("楼层");
        JLabel roomNumLabel = new JLabel("房数");
        inputPanel1.add(floorLabel);
        inputPanel1.add(floorField);
        inputPanel2.add(roomNumLabel);
        inputPanel2.add(roomNumField);

        JPanel submitPanel = new JPanel();
        JButton submitBtn = new JButton("提交");
        JButton clearBtn = new JButton("清空");
        submitPanel.add(submitBtn);
        submitPanel.add(clearBtn);

        Box vBox = Box.createVerticalBox();
        vBox.add(inputPanel1);
        vBox.add(inputPanel2);
        vBox.add(submitPanel);
        setContentPane(vBox);

        //提交按钮事件
        submitBtn.addActionListener(event -> {
            if (!(floorField.getText().equals("")||roomNumField.getText().equals(""))) {
                int floor = Integer.parseInt(floorField.getText().trim());
                int roomNum = Integer.parseInt(roomNumField.getText().trim());
                FloorDao hotelDao = new FloorDaoImpl();
                int res = hotelDao.addFloor(floor, roomNum);
                if (res == 1) {
                    JOptionPane.showMessageDialog(
                            this,
                            "添加成功",
                            "成功提示",
                            JOptionPane.INFORMATION_MESSAGE

                    );
                    parentFrame.refresh();
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "添加失败",
                            "失败提示",
                            JOptionPane.INFORMATION_MESSAGE

                    );
                }
            }
        });

        //清空按钮事件
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                floorField.setText("");
                roomNumField.setText("");
            }
        });

        pack();
        setTitle("新增楼层");
//        setSize(120,140);
        setLocationRelativeTo(null);
    }
}
