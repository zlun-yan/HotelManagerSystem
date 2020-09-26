package com.csu.root.clerkInfo;

import com.csu.dao.ClerkDao;

import java.awt.*;
import javax.swing.*;

public class AddClerkDialog extends JFrame{
    public AddClerkDialog(ClerkDao clerkDao, ClerkInfoFrame parentFrame) {
        //密码默认设置与工号相同
        JPanel inputPanel = new JPanel();
        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(200, 30));
        JComboBox<String> jobCombo = new JComboBox<>();
        jobCombo.addItem("前台");
        jobCombo.addItem("经理");
        jobCombo.addItem("管理员");
        inputPanel.add(nameField);
        inputPanel.add(jobCombo);

        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new JButton("确定");
        JButton cancelButton = new JButton("取消");
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        confirmButton.addActionListener(event -> {
            String job;
            switch (jobCombo.getSelectedIndex()) {
                case 0:
                    job = "waiter";
                    break;
                case 1:
                    job = "manager";
                    break;
                case 2:
                    job = "root";
                    break;
                default:
                    return;
            }
            if (clerkDao.insertClerk(nameField.getText().trim(), job) == 0) {
                JOptionPane.showMessageDialog(this ,"失败", "增加员工", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(this ,"成功", "增加员工", JOptionPane.INFORMATION_MESSAGE);
            }
            nameField.setText("");
            parentFrame.showRoomData();
        });
        cancelButton.addActionListener(event -> {
            setVisible(false);
        });

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
        setTitle("增加员工");
        setLocationRelativeTo(null);
    }
}
