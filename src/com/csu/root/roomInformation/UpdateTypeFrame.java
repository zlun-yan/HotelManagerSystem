package com.csu.root.roomInformation;

import com.csu.bean.Room;
import com.csu.dao.RoomDao;
import com.csu.dao.RoomImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateTypeFrame extends JFrame{
    public UpdateTypeFrame(RoomIfo parentFrame){
        JPanel inputPanel1 = new JPanel();
        JPanel inputPanel2 = new JPanel();
        JPanel inputPanel3 = new JPanel();
        JTextField idField = new JTextField(15);
        JTextField typeField = new JTextField(15);
        JTextField numField = new JTextField(15);
        JLabel idLabel = new JLabel("编号");
        JLabel typeLabel = new JLabel("类型");
        JLabel numLabel = new JLabel("定额");
        inputPanel1.add(idLabel);
        inputPanel1.add(idField);
        inputPanel2.add(typeLabel);
        inputPanel2.add(typeField);
        inputPanel3.add(numLabel);
        inputPanel3.add(numField);

        JPanel submitPanel = new JPanel();
        JButton submitBtn = new JButton("提交");
        JButton clearBtn = new JButton("清空");
        submitPanel.add(submitBtn);
        submitPanel.add(clearBtn);

        Box vBox = Box.createVerticalBox();
        vBox.add(inputPanel1);
        vBox.add(inputPanel2);
        vBox.add(inputPanel3);
        vBox.add(submitPanel);
        setContentPane(vBox);

        //提交按钮事件
        submitBtn.addActionListener(event -> {
            if (!(idField.getText().equals("")||numField.getText().equals("")||typeField.getText().equals(""))) {
                int id = Integer.parseInt(idField.getText());
                String type = typeField.getText().trim();
                int limitNum = Integer.parseInt(numField.getText());
                RoomDao roomIfoDao = new RoomImpl();
                int res = roomIfoDao.updateRoom(id, type, limitNum);
                if (res == 1) {
                    JOptionPane.showMessageDialog(
                            this,
                            "修改成功",
                            "成功提示",
                            JOptionPane.INFORMATION_MESSAGE

                    );
                    parentFrame.refresh();
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "修改失败",
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
                idField.setText("");
                typeField.setText("");
                numField.setText("");
            }
        });

        pack();
        setTitle("修改房型");
        setLocationRelativeTo(null);
    }
}
