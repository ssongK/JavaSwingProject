package com.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Tetris extends JFrame{
    private JLabel score; 
    private JLabel info;
    private JButton restart = new JButton("restart");
    private JButton exit = new JButton("exit");

    // 생성자 : 전체적인 레이아웃
    public Tetris(){
        setTitle("테트리스");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new MyTetrisPanel());
        setLayout(new BorderLayout());
        add(new Footer(),BorderLayout.SOUTH);
        add(new Header(),BorderLayout.NORTH);
        setSize(400,600);
        setVisible(true);
        setFocusable(true);
        requestFocus();
    }

    // 중앙 : 테트리스 게임 판 생성
    class MyTetrisPanel extends JPanel{
        MyTetrisPanel(){
            setBackground(Color.DARK_GRAY);
        }
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(Color.CYAN);
            g.fill3DRect(100, 100, 20, 20, true);
            g.fill3DRect(200, 200, 20, 20, true);
            g.fill3DRect(100, 120, 20, 20, true);
            g.fill3DRect(120, 120, 20, 20, true);
            
        }
    }

    // 상단 : 유저이름, 점수 등의 정보를 표시
    class Header extends JPanel{
        Header(){
            setBackground(Color.LIGHT_GRAY);
            setLayout(new FlowLayout());
            score = new JLabel("score : ");
            info = new JLabel("user name : ");
            add(info);
            add(score);
        }
    }

    // 하단 : 다시하기, 그만하기 버튼 생성
    class Footer extends JPanel{
        Footer(){
            setBackground(Color.LIGHT_GRAY);
            restart.setLocation(450,700);
            exit.setLocation(500,700);
            add(restart);
            add(exit);

            // 테트리스 게임 다시 시작
            restart.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    dispose();
                }
            });

            // 테트리스 프레임 종료
            exit.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    dispose();
                }
            });
        }
    }

    public static void main(String[] args) {
        new Tetris();
    }
}
