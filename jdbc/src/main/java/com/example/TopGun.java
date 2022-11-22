package com.example;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class TopGun extends JFrame {
    int xbound, ybound, n = 0, count = 30;
    boolean stop = true;
    int i;
    int [] X = new int[200];
    int [] Y = new int[200];
    JLabel player, timer, boolit;
    JLabel [] la = new JLabel[200];
    MoveThread[] th = new MoveThread[200];
    public TopGun(){
        setTitle("총알 피하기 게임");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(null); 
        c.setBackground(Color.BLACK);
        setSize(700,700);
        setVisible(true);
        c.setFocusable(true);
        c.requestFocus();
        set();
        c.addKeyListener(new MyKeyListener());
        startTread();
    }
    void set(){
        Container c = getContentPane();
        xbound = c.getWidth();
        ybound = c.getHeight();
        for(int i=0; i<count; i++){
            xy(i);
            la[i] = new JLabel(new ImageIcon("jdbc/src/main/java/com/image/bubble.jpg"));
            la[i].setBounds(X[i], Y[i], 10,10);
            c.add(la[i]);
        }
        player = new JLabel(new ImageIcon("jdbc/src/main/java/com/image/apple.jpg"));
        player.setBounds(xbound / 2, ybound / 2, 10,10);
        c.add(player);
        timer = new JLabel("0");
        timer.setFont(new Font("Gothic", Font.ITALIC, 20));
        timer.setForeground(Color.WHITE);
        timer.setBounds(xbound - 260, ybound - 30, 120,30);
        c.add(timer);
        boolit = new JLabel("30");
        boolit.setFont(new Font("Gothic", Font.ITALIC, 20));
        boolit.setForeground(Color.WHITE);
        boolit.setBounds(xbound - 130, ybound - 30, 120, 30);
        c.add(boolit);
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
    class MyKeyListener extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            if(stop == false) return;
            int keyCode = e.getKeyCode();
            int x1, y1;
            x1 = player.getX();
            y1 = player.getY();
            switch(keyCode){
                case KeyEvent.VK_UP:
                    if(y1 - 10 < 0)
                        player.setLocation(player.getX(), 0);
                    else
                        player.setLocation(player.getX(),player.getY()-10);
                    break;
                case KeyEvent.VK_DOWN:
                    if(y1 + 10 > ybound - 10)
                        player.setLocation(player.getX(),ybound - 10);
                    else
                        player.setLocation(player.getX(),player.getY()+10);
                    break;
                case KeyEvent.VK_LEFT:
                    if(x1 - 10 < 0)
                        player.setLocation(0,player.getY());
                    else
                        player.setLocation(player.getX()-10,player.getY());
                    break;
                case KeyEvent.VK_RIGHT:
                    if(x1 + 10 > xbound - 10)
                        player.setLocation(xbound - 10,player.getY());
                    else
                        player.setLocation(player.getX()+10,player.getY());
                    break;
            }
            check();
            if(stop == false){
                return;
            }
        }
    }
    void check(){
        int a1, a2, b1, b2;
        for(int i=0; i<count; i++){
            a1 = player.getX(); b1 = player.getY(); a2 = X[i]; b2 = Y[i];
            if((a1 >= a2 - 10 && a1 <= a2 + 10) && (b1 >= b2 - 10 && b1 <= b2 + 10)){
                stop = false;
            }
        }
    }
    class TimerThread extends Thread {
        @Override
        public void run(){
            i = count;
            Container c = getContentPane();
            while(true){
                timer.setText("시간 : " + Integer.toString(n) + "초");
                boolit.setText("총알 : " + Integer.toString(count) + "개");
                n++;
                count = (n/3) + 30;
                if(i < count){
                    System.out.println("count : " + count );
                    xy(i);
                    la[i] = new JLabel(new ImageIcon("jdbc/src/main/java/com/image/bubble.jpg"));
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
                if(stop == false){
                    int restart = JOptionPane.showConfirmDialog(null, "계속할 것입니까?", "End", JOptionPane.YES_NO_OPTION);
                    if(restart==JOptionPane.YES_OPTION){}
                        // restart
                    else dispose();
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
                
                int speed = (int)(Math.random()*80 + 50);
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
                        while(X1 > x && stop){
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
                        while(Y1 > y && stop){
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
                        while(X1 < x && stop){
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
                        while(Y1 < y && stop){
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
        for(int i = 0; i<count; i++){
            th[i] = new MoveThread();
            th[i].moveThread(i);
            th[i].start();
        }
        TimerThread thTime = new TimerThread();
        thTime.start();
    }
    public static void main(String[] args) {
        new TopGun();
    }
}