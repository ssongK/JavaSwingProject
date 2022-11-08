package com.example;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;


public class test extends JFrame {
    Container contentPane;
    CenterPanel centerPanel = new CenterPanel();
    JTextField txtInput;
    JLabel id;
    JButton btnLogin, btnLogout;
    JButton btnRank;
    JButton btnGm1, btnGm2, btnGm3, btnGm4;
    public test() {
        setTitle("Open Challenge 11");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = getContentPane();
        contentPane.add(new NorthPanel(), BorderLayout.NORTH);
        contentPane.add(centerPanel, BorderLayout.CENTER);

        setSize(500,350);
        setVisible(true);
        
    }

    public static void main(String[] args) {
        new test();
    }
    class NorthPanel extends JPanel{
        public NorthPanel() {
           this.setBackground(Color.LIGHT_GRAY);
            txtInput = new JTextField(5);
            add(txtInput);
            btnLogin = new JButton(" 로그인 ");
            add(btnLogin);
            btnLogout = new JButton(" 로그아웃 ");
            btnLogout.setVisible(false);
            btnRank = new JButton(" 랭킹 화면 ");
            btnRank.setVisible(false);
        }
     }

    class CenterPanel extends JPanel{
        public CenterPanel() {
            setLayout(new GridLayout(2, 2, 20, 20));
            btnGm1 = new JButton("1");
            add(btnGm1);
            btnGm2 = new JButton("2");
            add(btnGm2);
            btnGm3 = new JButton("3");
            add(btnGm3);
            btnGm4 = new JButton("4");
            add(btnGm4);
        }
    }
   
}
