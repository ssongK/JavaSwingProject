package com.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Tetris extends JFrame{
    private JLabel score = new JLabel("score : ");
    private JLabel info = new JLabel("user name : ");
    private JButton restart = new JButton("restart");
    private JButton exit = new JButton("exit");

    private TetrisPanelMethod tp;

    // 생성자 : 전체 레이아웃
    public Tetris(){
        setTitle("테트리스");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setBackground(Color.ORANGE);
        setLayout(null);
        // setResizable(false);
        setBounds(0,0,300,530);

        add(new Header());

        tp = new TetrisPanelMethod(); // TetrisPanelMethod 객체 생성 및 참조
        add(tp); // 중앙 : 테트리스에 필요한 라인 및 블록 생성(TetrisPanel 생성자 참조)
        tp.setFocusable(true);
        tp.requestFocus();
        
        add(new Footer());


        setSize(300,530);
        setVisible(true);

        // 게임 시작
        startGame();
    }

    // 쓰레드 start
    public void startGame(){
        new TetrisThread(tp).start();
    }

    // 상단 : 유저이름, 점수 등의 정보를 표시
    class Header extends JPanel{
        public Header(){
            setBackground(Color.MAGENTA);
            setBounds(0,0,300,50);
            add(info);
            add(score);
        }
    }

    // 하단 : 다시하기, 그만하기 버튼 생성
    class Footer extends JPanel{
        public Footer(){
            setBackground(Color.LIGHT_GRAY);
            setBounds(0,450,300,50);
            add(restart);
            add(exit);

            // 테트리스 게임 다시 시작
            restart.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    startGame();
                }
            });

            // 테트리스 프레임 종료(게임이 끝나기 전 종료는 점수 저장 안 함)
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
