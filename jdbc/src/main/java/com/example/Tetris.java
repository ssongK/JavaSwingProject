package com.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Tetris extends JFrame{
    private JLabel scoreBoard = new JLabel("score : "); // 점수 기록
    private JLabel userInfo = new JLabel("name : "); // 유저 네임을 받아와서 기록
    private JButton restart = new JButton("restart"); // 다시 시작
    private JButton exit = new JButton("exit"); // 종료
    private String userName;
    private Font font = new Font("나눔고딕",Font.BOLD,16);

    private TetrisPanelMethod tp;
    private TetrisThread tt;

    // 생성자 : 전체 레이아웃 생성
    public Tetris(String name){
        this.userName = name;

        setTitle("테트리스");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setBackground(Color.ORANGE);
        setLayout(null);
        setResizable(false);
        setBounds(0,0,310,528);

        add(new Header()); // 컨텐츠 팬의 상단 -> 유저 이름, 점수 표시

        tp = new TetrisPanelMethod(scoreBoard, userName); // TetrisPanelMethod 객체 생성 및 참조
        add(tp); // 컨텐츠 팬의 중앙 -> 테트리스에 필요한 라인 및 블록 생성(TetrisPanel 생성자 참조)
        tp.setFocusable(true);
        tp.requestFocus();
        
        add(new Footer()); // 컨텐츠 팬의 하단 -> 다시하기, 그만하기 버튼

        setSize(300,530);
        setVisible(true);

        // 게임 시작
        startGame();
    }

    // 컨텐츠 팬의 상단 : 유저 이름, 점수 표시
    class Header extends JPanel{
        public Header(){
            setBackground(Color.LIGHT_GRAY);
            setLayout(new GridLayout(1,2));
            setBounds(0,0,300,50);
            
            add(userInfo);
            userInfo.setFont(font);
            userInfo.setHorizontalAlignment(JLabel.CENTER);

            add(scoreBoard);
            scoreBoard.setFont(font);
            scoreBoard.setHorizontalAlignment(JLabel.CENTER);
        }
    }

    // 컨텐츠 팬의 하단 : 다시하기, 그만하기 버튼 생성
    class Footer extends JPanel{
        public Footer(){
            setBackground(Color.LIGHT_GRAY);
            setBounds(0,450,300,50);
            add(restart);
            add(exit);

            // 테트리스 게임 다시 시작
            restart.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    restartGame();
                    tp.setFocusable(true);
                    tp.requestFocus();
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

    // 쓰레드 객체의 run() 호출(게임 시작)
    public void startGame(){
        userInfo.setText("name : "+userName);
        scoreBoard.setText("score : 0");
        tt = new TetrisThread(tp);
        tt.start();
    }

    // 새로운 쓰레드 객체 생성
    public void restartGame(){
        tt.interrupt();
        startGame();
    }
}
