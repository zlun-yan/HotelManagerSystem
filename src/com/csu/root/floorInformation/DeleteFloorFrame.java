package com.csu.root.floorInformation;

import com.csu.dao.FloorDao;
import com.csu.dao.FloorDaoImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteFloorFrame extends JFrame {
    public  DeleteFloorFrame(FloorIfo parentFrame){
        JPanel inputPanel1 = new JPanel();
        JTextField floorField = new JTextField(15);
        JLabel floorLabel = new JLabel("楼层");
        inputPanel1.add(floorLabel);
        inputPanel1.add(floorField);

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
            if (!(floorField.getText().equals(""))) {
                int floor = Integer.parseInt(floorField.getText().trim());
                FloorDao hotelDao = new FloorDaoImpl();
                int res = hotelDao.deleteFloor(floor);
                if (res == 1) {
                    JOptionPane.showMessageDialog(
                            this,
                            "删除成功",
                            "成功提示",
                            JOptionPane.INFORMATION_MESSAGE

                    );
                    parentFrame.refresh();
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "删除失败",
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
            }
        });

        pack();
//        setSize(120,140);
        setTitle("删除楼层");
        setLocationRelativeTo(null);
    }
}
