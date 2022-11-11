package com.example;

// import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SwingTest extends JFrame{
    public SwingTest(){
        setTitle("test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = getContentPane();
        c.setLayout(new GridLayout(2,2));
        JLabel lb = new JLabel("    game1   ");
        c.add(lb);
        lb.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                new Tetris();
            }
        });

        c.add(new JLabel("   game2   "));
        c.add(new JLabel("   game3   "));
        c.add(new JLabel("   game4   "));

        setSize(300,300);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SwingTest();
        // Jdbc myDB = new Jdbc();

        // Scanner s = new Scanner(System.in);
        // System.out.println("게임에서 사용할 이름 입력(dialog활용)");
        // String name = s.nextLine();
        // s.close();
        // myDB.insert(name);

        // ArrayList<String> names = myDB.searchName();
        // System.out.println(names);

        // for(int i=0;i<names.size();i++){
        //     if(names.get(i).equals(name)){
        //         System.out.println(names.get(i)+" 로그인 성공!");
        //     }
        //     else{
        //         System.out.println("로그인 실패!");
        //     }
        // }
    }
}
