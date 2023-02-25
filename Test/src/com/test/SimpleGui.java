package com.test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleGui implements ActionListener {
    JButton button;

    public void go() {
        JFrame frame = new JFrame();
        button = new JButton("look at me!!");
        button.addActionListener(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(button);
        frame.setSize(100, 100);
        frame.setVisible(true);

    }

    public void actionPerformed(ActionEvent event) {
        button.setText("yes you hit");
    }

}
