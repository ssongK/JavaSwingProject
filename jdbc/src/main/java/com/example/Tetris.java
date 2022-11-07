package com.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Tetris extends JFrame{
    public Tetris(){
        setTitle("테트리스");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new MyTetrisPanel());
        setSize(500,800);
        setVisible(true);
    }

    class MyTetrisPanel extends JPanel{
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            
        }
    }

    public static void main(String[] args) {
        new Tetris();
    }
}
