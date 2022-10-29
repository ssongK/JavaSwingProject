package com.example;

import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
// import javax.swing.event.*;

public class SwingTest extends JFrame{
    public SwingTest(){
        setTitle("test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(new GridLayout(2,2));
        c.add(new JLabel("  game1"));
        c.add(new JLabel("  game2"));
        c.add(new JLabel("  game3"));
        c.add(new JLabel("  game4"));

        setSize(300,300);
        setVisible(true);
    }

    public static void main(String[] args) {
       new SwingTest();
       Jdbc myApp = new Jdbc();
       ArrayList<String> names = myApp.searchName();
       System.out.println(names);
    }
}
