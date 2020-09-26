package com.csu.root.clerkInfo;

import com.csu.dao.ClerkDao;

import java.awt.*;
import javax.swing.*;

public class UpdateJobDialog extends JFrame{
    private String info;

    public UpdateJobDialog(ClerkDao clerkDao, ClerkInfoFrame parentFrame) {
        JPanel queryPanel = new JPanel();
        JLabel queryLabel = new JLabel("输入员工工号");
        JTextField queryField = new JTextField();
        queryField.setPreferredSize(new Dimension(200, 30));
        JButton queryButton = new JButton("查询");
        JButton cancelButton = new JButton("取消");
        cancelButton.setEnabled(false);
        queryPanel.add(queryLabel);
        queryPanel.add(queryField);
        queryPanel.add(queryButton);
        queryPanel.add(cancelButton);

        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel();
        infoPanel.add(infoLabel);
        infoPanel.setVisible(false);

        JPanel confirmPanel = new JPanel();
        JLabel confirmLabel = new JLabel("选择职位");
        JComboBox<String> confirmCombo = new JComboBox<>();
        confirmCombo.addItem("前台");
        confirmCombo.addItem("经理");
        confirmCombo.addItem("管理员");
        JButton confirmButton = new JButton("确认");
        confirmButton.setEnabled(false);
        confirmPanel.add(confirmLabel);
        confirmPanel.add(confirmCombo);
        confirmPanel.add(confirmButton);

        queryButton.addActionListener(event -> {
            info = queryField.getText().trim();
            if (!info.equals("")) {
                Object[][] clerkInfo = clerkDao.getClerkRowDateWithJid(0, 1, info);

                if (clerkInfo != null) {
                    infoLabel.setText("员工信息：  " + clerkInfo[0][1] + "  " + clerkInfo[0][2]);

                    infoPanel.setVisible(true);
                    confirmButton.setEnabled(true);
                    queryField.setEnabled(false);
                    queryButton.setEnabled(false);
                    cancelButton.setEnabled(true);
                    pack();
                }
            }
            queryField.setText(info);
        });
        cancelButton.addActionListener(event -> {
            queryField.setEnabled(true);
            queryButton.setEnabled(true);
            queryField.setText("");
            confirmButton.setEnabled(false);
            cancelButton.setEnabled(false);
            infoPanel.setVisible(false);
        });
        confirmButton.addActionListener(event -> {
            String job;
            switch (confirmCombo.getSelectedIndex()) {
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
            if (clerkDao.updateJobWithJid(info, job) == 1) {
                JOptionPane.showMessageDialog(this ,"成功", "修改员工职位", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(this ,"失败", "修改员工职位", JOptionPane.INFORMATION_MESSAGE);
            }

            queryField.setEnabled(true);
            queryButton.setEnabled(true);
            queryField.setText("");
            confirmButton.setEnabled(false);
            cancelButton.setEnabled(false);
            infoPanel.setVisible(false);

            parentFrame.showRoomData();
        });

        Box box = Box.createVerticalBox();
        box.add(queryPanel);
        box.add(infoPanel);
        box.add(confirmPanel);
        add(box);
        pack();
        setTitle("修改员工职位");
        setLocationRelativeTo(null);
    }
}
