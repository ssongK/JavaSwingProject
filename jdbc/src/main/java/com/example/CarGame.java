package com.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CarGame extends JPanel implements KeyListener {

    private int rx =220, ry =450 , life = 3 , score = 0;
    private double ry1=-350, ry2=-2500 , ry3=-3700 ,ry4=-1300 ;

    public CarGame(){
        JPanel panel = new JPanel();
        panel.setSize(500,600);
        panel.setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    public void update(Graphics g){
        paint(g);
    }

    public void paint(Graphics g){
        super.paint(g);

        if(life>0) {
            //경계선
            g.setColor(Color.RED);
            g.fillRect(355, 0, 2, getHeight());
            g.fillRect(122, 0, 2, getHeight());

            //길
            g.setColor(Color.BLUE);
            g.fillRect(124, 0, 231, getHeight());

            //내차
            g.setColor(Color.YELLOW);
            g.fillRect(rx, ry, 40, 80);

            //사이드
            g.setColor(Color.GREEN);
            g.fillRect(0, 0, 122, getHeight());
            g.fillRect(357, 0, getWidth(), getHeight());

            //차들
            g.setColor(Color.RED);
            g.fillRect(140, (int) ry1, 40, 80);
            g.fillRect(280, (int) ry2, 40, 80);
            g.fillRect(210, (int) ry3, 40, 80);
            g.fillRect(300, (int) ry4, 40, 80);


            ry1 = ry1 + 0.5;
            if (ry1 >= getHeight()) {
                ry1 = -1350;
            }

            ry2 = ry2 + 0.5;
            if (ry2 >= getHeight()) {
                ry2 = -800;
            }

            ry3 = ry3 + 0.5;
            if (ry3 >= getHeight()) {
                ry3 = -3400;
            }

            ry4 = ry4 + 0.5;
            if (ry4 >= getHeight()) {
                ry4 = -4800;
            }

            //라이프 수, 게임 패배
            if (ry1 >= 375) {
                if (rx + 40 >= 140 && rx <= 180) {
                    life--;
                    ry1 = -1350;
                }
            }

            if (ry2 >= 375) {
                if (rx + 40 >= 280 && rx <= 320) {
                    life--;
                    ry2 = -2000;
                }
            }

            if (ry3 >= 375) {
                if (rx + 40 >= 210 && rx <= 250) {
                    life--;
                    ry3 = -3400;
                }
            }

            if (ry4 >= 375) {
                if (rx + 40 >= 300 && rx <= 340) {
                    life--;
                    ry4 = -4800;
                }
            }

            //라이프
            g.setColor(Color.BLACK);
            Font Fon = new Font ("TimesRoman", 1, 17);
            g.setFont(Fon);
            g.drawString("라이프 수 : " + life, 12, 200);

            //score
            g.setColor(Color.GRAY);
            g.setFont(Fon);
            g.drawString("점수 : "+score/10 ,370,200);
            score+=1;
        }

        //게임 패배, 게임 점수
        if(life == 0){
            g.setColor(Color.RED);
            Font myFont = new Font ("Courier New", 1, 37);
            g.setFont(myFont);
            g.drawString("GAEM OVER",160,280);

            g.setColor(Color.ORANGE);
            Font Font = new Font ("TimesRoman", 1, 17);
            g.setFont(Font);
            g.drawString("점수 :"+ score/10, 180,340);
        }

        repaint();

    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("luck car game");
        CarGame ball = new CarGame();
        frame.add(ball);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,600);
        frame.setVisible(true);

    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();

        if(c==KeyEvent.VK_RIGHT && rx+40 <= 350){
            rx = rx + 15;
        }
        if(c==KeyEvent.VK_LEFT && rx >= 130){
            rx = rx - 15;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
