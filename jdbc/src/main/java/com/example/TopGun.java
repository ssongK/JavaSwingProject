package com.example;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class TopGun extends JFrame {
    int xbound, ybound, n, count, px, py;
    boolean start = false, left = false, right = false, up = false, down = false;
    int i;
    int [] X = new int[200];
    int [] Y = new int[200];
    String name;
    JLabel player, timer, boolit, set, score;
    JLabel [] la = new JLabel[200];
    MoveThread[] th;
    TimerThread thTime;
    KeyThread thKey;
    Container c = getContentPane();
    public TopGun(String name){
        this.name = name;
        setTitle("총알 피하기 게임");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        c.setLayout(null); 
        c.setBackground(Color.BLACK);
        for(int i = 0; i < 200; i++){
            la[i] = new JLabel(new ImageIcon("jdbc/src/main/java/com/image/boolit.jpg"));
        }
        player = new JLabel(new ImageIcon("jdbc/src/main/java/com/image/player.png"));
        timer = new JLabel();
        boolit = new JLabel();
        score = new JLabel();
        set = new JLabel("시작 하려면 ENTER 키를 누르세요.");
        set.setFont(new Font("Gothic", Font.ITALIC, 20));
        set.setForeground(Color.WHITE);
        set.setBounds(210, 335, 500,30);
        set.setVisible(true);
        c.add(set);
        setSize(700,700);
        setVisible(true);
        c.setFocusable(true);
        c.requestFocus();
        c.addKeyListener(new EnterKeyListener());
    }
    void restart(){
        n = 0; count = 30;
        start = true; left = false; right = false; up = false; down = false;
        Container c = getContentPane();
        c.setFocusable(true);
        c.requestFocus();
        set();
        c.addKeyListener(new MyKeyListener());
        startTread();
    }
    void set(){
        xbound = c.getWidth();
        ybound = c.getHeight();
        for(int i=0; i<count; i++){
            xy(i);
            la[i].setBounds(X[i], Y[i], 10,10);
            System.out.println(i + " : " + la[i].getX() + ", " + la[i].getY());
            c.add(la[i]);
        }
        player.setBounds(xbound / 2, ybound / 2, 25,25);
        c.add(player);
        timer.setText("시간 : " + Integer.toString(n) + "초");
        timer.setFont(new Font("Gothic", Font.ITALIC, 20));
        timer.setForeground(Color.WHITE);
        timer.setBounds(xbound - 260, ybound - 30, 120,30);
        c.add(timer);
        boolit.setText("총알 : " + Integer.toString(count) + "개");
        boolit.setFont(new Font("Gothic", Font.ITALIC, 20));
        boolit.setForeground(Color.WHITE);
        boolit.setBounds(xbound - 130, ybound - 30, 120, 30);
        c.add(boolit);
        score.setText(name + " : " + Integer.toString(n) + "점");
        score.setFont(new Font("Gothic", Font.ITALIC, 20));
        score.setForeground(Color.WHITE);
        score.setBounds(20, 10, 200,30);
        c.add(score);
    }
    void xy(int i){
        switch(i%4){
            case 0:
                X[i] = (int)(Math.random()*xbound);
                Y[i] = (int)(Math.random()*-10 - 10);
                break;
            case 1:
                X[i] = (int)(Math.random()*-10 - 10);
                Y[i] = (int)(Math.random()*ybound);
                break;
            case 2:
                X[i] = (int)(Math.random()*10 + xbound);
                Y[i] = (int)(Math.random()*ybound);
                break;
            case 3:
                X[i] = (int)(Math.random()*xbound);
                Y[i] = (int)(Math.random()*10 +ybound);
                break;
        }
    }
    class EnterKeyListener extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                start = true;
                set.setVisible(false);
                restart();
            }
        }
    }
    class MyKeyListener extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            check();
            switch(e.getKeyCode()){
                case KeyEvent.VK_UP:
                    up = true;
                    break;
                case KeyEvent.VK_DOWN:
                    down = true;
                    break;
                case KeyEvent.VK_LEFT:
                    left = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    right = true;
                    break;
            }
        }
        public void keyReleased(KeyEvent e) {
            check();
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    left = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    right = false;
                    break;
                case KeyEvent.VK_UP:
                    up = false;
                    break;
                case KeyEvent.VK_DOWN:
                    down = false;
                    break;
            }
        } 
    }
    public void keyControl() {
        check();
        if(!start) return;
        px = player.getX();
        py = player.getY();
        if(left) {
            if(px -10 <= 0)px = 0;
            else px -= 10;
        }
        if(right) {
             if(px + 10 >= xbound - 10) px = xbound-10;
             else px += 10;
        }
        if(up) {
            if(py - 10 <= 0) py = 0;
            else py -= 10;
        }
        if(down) {
            if(py + 10 >= ybound - 10) py = ybound-10;
            else py += 10;
        }
        player.setLocation(px, py);
        check();
        if(!start) return;
    }
    void check(){
        int a1, a2, b1, b2;
        for(int i=0; i<count; i++){
            a1 = player.getX(); b1 = player.getY(); a2 = X[i]; b2 = Y[i];
            for(int j=1; j<24; j++){
                if((a1 + j >= a2 && a1 + j <= a2 + 10) && (b1 + j >= b2 && b1 + j <= b2 + 10)){
                    start = false; left = false; right = false; up = false; down = false;
                    return;
                 }
            }
        }
    }
    class KeyThread extends Thread{
        @Override
        public void run(){
            while(true){
                try{
                    keyControl();
                    check();
                    Thread.sleep(70);
                } catch(InterruptedException e){
                    return;
                }
            }
        }
    }
    class TimerThread extends Thread {
        @Override
        public void run(){
            i = count;
            int sc;
            while(true){
                sc = n*count/20;
                timer.setText("시간 : " + Integer.toString(n) + "초");
                boolit.setText("총알 : " + Integer.toString(count) + "개");
                score.setText(name + " : " + Integer.toString(sc) + "점");
                n++;
                count = (n/3) + 30;
                System.out.println(n + "ok");
                if(i < count){
                    System.out.println("count : " + count );
                    xy(i);
                    la[i].setBounds(X[i], Y[i], 10,10);
                    c.add(la[i]);
                    th[i] = new MoveThread();
                    th[i].moveThread(i);
                    th[i].start();
                    i++;
                }
                try{
                    Thread.sleep(1000);
                } catch(InterruptedException e){
                    return;
                }
                check();
                if(!start){
                    Jdbc j = new Jdbc();
                    j.saveGameScore("game3", sc, name);
                    int restart = JOptionPane.showConfirmDialog(null, "[Score : "+sc + "] 계속할 것입니까?", "End", JOptionPane.YES_NO_OPTION);
                    if(restart==JOptionPane.YES_OPTION) {stopThread(); restart();}
                    else {stopThread(); dispose();}     // thread.안터럽트
                    return;
                }
            }
        }
    }
    class MoveThread extends Thread{
        int i;
        public void moveThread(int i){
            this.i = i;
        }
        @Override
        public void run(){
            while(true){
                int x = la[i].getX(); int y = la[i].getY();
                int X1 = X[i]; int Y1 = Y[i];
                int x1, y1;
                int num = 0;
                
                int speed = (int)(Math.random()*20 + 50);
                if(x < 0){
                    while(true){
                        num = (int)(Math.random()*3);
                        if(num !=0) break;
                    }
                }
                else if(y < 0){
                    while(true){
                        num = (int)(Math.random()*3);
                        if(num !=1) break;
                    }
                }
                else if(x > xbound){
                    while(true){
                        num = (int)(Math.random()*3);
                        if(num !=2) break;
                    }
                }
                else if(y > ybound){
                    while(true){
                        num = (int)(Math.random()*3);
                        if(num !=3) break;
                    }
                }
                switch(num){
                    case 0:
                        x = (int)(Math.random()*-40);
                        y = (int)(Math.random()*ybound);
                        x1 = ((int)(Math.abs(X1 + Math.abs(x))) / 10) / 6;
                        y1 = ((y - Y1) / 10)/ 6;
                        if(x1 == 0) x1 = 1;
                        if(y1 == 0) {if(y - Y1 < 0)y1 = -1; else y1 = 1;}
                        while(X1 > x && start){
                            try{
                                Thread.sleep(speed);
                            } catch(InterruptedException e){return;}        
                            la[i].setLocation(X1 - x1, Y1 + y1);
                            X1 -= x1; Y1 += y1;
                            X[i] = X1;
                            Y[i] = Y1;
                            check();
                        }
                        break;
                    case 1:
                        x = (int)(Math.random()*xbound);
                        y = (int)(Math.random()*-40);
                        x1 = ((x - X1) / 10)/ 6;
                        y1 = ((int)(Math.abs(Y1 + Math.abs(y))) / 10)/ 6;
                        if(x1 == 0) {if(x - X1 < 0)x1 = -1; else x1 = 1;}
                        if(y1 == 0) y1 = 1;
                        while(Y1 > y && start){
                            try{
                                Thread.sleep(speed);
                            } catch(InterruptedException e){return;}        
                            la[i].setLocation(X1 + x1, Y1 - y1);
                            X1 += x1; Y1 -= y1;
                            X[i] = X1;
                            Y[i] = Y1;
                            check();
                        }
                        break;
                    case 2:
                        x = (int)(Math.random()*10 + xbound);
                        y = (int)(Math.random()*ybound);
                        x1 = ((x - X1) / 10)/ 6;
                        y1 = ((y - Y1) / 10) / 6;
                        if(x1 == 0) {if(x - X1 < 0)x1 = -1; else x1 = 1;}
                        if(y1 == 0) {if(y - Y1 < 0)y1 = -1; else y1 = 1;}
                        while(X1 < x && start){
                            try{
                                Thread.sleep(speed);
                            } catch(InterruptedException e){return;}        
                            la[i].setLocation(X1 + x1, Y1 + y1);
                            X1 += x1; Y1 += y1;
                            X[i] = X1;
                            Y[i] = Y1;
                            check();
                        }
                        break;
                    case 3:
                        x = (int)(Math.random()*xbound);
                        y = (int)(Math.random()*10 + ybound);
                        x1 = ((x - X1) / 10) / 6;
                        y1 = ((y - Y1) / 10) / 6;
                        if(x1 == 0) {if(x - X1 < 0)x1 = -1; else x1 = 1;}
                        if(y1 == 0) {if(y - Y1 < 0)y1 = -1; else y1 = 1;}
                        while(Y1 < y && start){
                            try{
                                Thread.sleep(speed);
                            } catch(InterruptedException e){return;}        
                            la[i].setLocation(X1 + x1, Y1 + y1);
                            X1 += x1; Y1 += y1;
                            X[i] = X1;
                            Y[i] = Y1;
                            check();
                        }
                        break;
                }
            }
        }
    }
    void startTread(){
        th = new MoveThread[200];
        for(int i = 0; i<count; i++){
            th[i] = new MoveThread();
            th[i].moveThread(i);
            th[i].start();
        }
        thKey = new KeyThread(); 
        thKey.start();
        thTime = new TimerThread(); 
        thTime.start();
    }
    void stopThread(){
        for(int i=0; i<count; i++){
            th[i].interrupt();
            la[i].setLocation(-20, -20);
        }
        thTime.interrupt();
        thKey.interrupt();
        n = 0; count = 30;
    }
}