package com.csu.root;

import com.csu.root.clerkInfo.ClerkInfoFrame;
import com.csu.root.floorInformation.FloorIfo;
import com.csu.root.productInfo.ProductInfoFrame;
import com.csu.root.roomInformation.RoomIfo;
import com.csu.update.ClerkFrame;

import java.awt.*;
import java.awt.event.InputEvent;
import javax.swing.*;

public class RootFrontWindow extends JFrame{
    private String jid;

    public RootFrontWindow(String jid) {
        this.jid = jid;

        initButton();
        initMenu();

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu infoMenu = new JMenu("查看");

        JMenuItem personInfo = new JMenuItem("个人信息");
        personInfo.setAccelerator(KeyStroke.getKeyStroke('I', InputEvent.CTRL_DOWN_MASK));
        personInfo.addActionListener(event -> {
            ClerkFrame frame = new ClerkFrame(jid);
            frame.setVisible(true);
        });

        infoMenu.add(personInfo);
        menuBar.add(infoMenu);

        setJMenuBar(menuBar);
    }

    private void initButton() {
    	Icon clerk = new ImageIcon("clerk.png");
    	Icon product = new ImageIcon("product.png");
    	Icon floor = new ImageIcon("floor.png");
    	Icon room = new ImageIcon("room.png");
    	
        JButton  clerkButton= new JButton("员工信息");
        JButton productButton = new JButton("商品信息");
        JButton floorButton = new JButton("楼层信息");
        JButton roomButton = new JButton("房间信息");
        
        clerkButton.setIcon(clerk);
        productButton.setIcon(product);
        floorButton.setIcon(floor);
        roomButton.setIcon(room);
        
        JPanel p1 = new JPanel();

        clerkButton.setPreferredSize(new Dimension(150, 150));
        productButton.setPreferredSize(new Dimension(150, 150));
        floorButton.setPreferredSize(new Dimension(150, 150));
        roomButton.setPreferredSize(new Dimension(150, 150));

        clerkButton.addActionListener(event -> {
            ClerkInfoFrame frame = new ClerkInfoFrame();
            frame.setVisible(true);
        });

        productButton.addActionListener(event -> {
            ProductInfoFrame frame = new ProductInfoFrame();
            frame.setVisible(true);
        });

        floorButton.addActionListener(event -> {
            FloorIfo frame = new FloorIfo();
            frame.setVisible(true);
        });

        roomButton.addActionListener(event -> {
            RoomIfo frame = new RoomIfo();
            frame.setVisible(true);
        });

        p1.setLayout(new GridLayout(2, 2));
        p1.add(clerkButton);
        p1.add(productButton);
        p1.add(floorButton);
        p1.add(roomButton);

        add(p1);
    }
}
