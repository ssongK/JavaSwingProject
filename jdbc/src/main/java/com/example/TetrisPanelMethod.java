package com.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// 중앙 : 테트리스 게임 panel
public class TetrisPanelMethod extends JPanel{
    private int panelHeight = 400;
    private int panelWidth = 300;
    private int blockSize = 25;
    private int panelRow = panelHeight/blockSize; // 16
    private int panelCol = panelWidth/blockSize; // 12

    private TetrisBlock block;

    private Color[][] settedBlock;
    

    // 생성자 : panel size, bgColor 설정, createBlock 메서드 호출
    TetrisPanelMethod(){
        setBackground(Color.GRAY);
        setBounds(0,50,panelWidth,panelHeight);

        settedBlock = new Color[panelRow][panelCol];

        createBlock();
        addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                int key = e.getKeyCode();
                switch(key){
                    case KeyEvent.VK_DOWN:
                        moveBlockDown();
                        break;
                    case KeyEvent.VK_LEFT:
                        moveBlockLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                        moveBlockRight();
                        break;
                    case KeyEvent.VK_SPACE:
                        rotateBlock();
                        break;
                }
            }
        });

        setFocusable(true);
        requestFocus();
    }

    // TetrisBlock 클래스에서 생성(모양, 크기, ColSize)
    public void createBlock(){
        block = new TetrisBlock(new int[][]{{0,0,0,0},{0,0,0,0},{0,0,0,0},{1,1,1,1}}, Color.BLUE, panelCol);
    }

    // Tetris panel의 line 및 block 생성 메서드 호출
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        drawLine(g);
        drawBlock(g);
        drawSettedBlock(g);
    }

    // 격자 모양 라인 생성
    private void drawLine(Graphics g){
        g.setColor(Color.CYAN);
        for(int j=0; j<panelRow; j++){
            for(int i=0;i<panelCol;i++){
                g.drawRect(i*blockSize, j*blockSize, blockSize, blockSize);
            }
        }
    }

    // 블록 생성 : fill3DRect() 메서드 활용
    private void drawBlock(Graphics g){
        int[][] shape = block.getshape();
        int h = block.getHeight();
        int w = block.getWidth();
        Color color = block.getColor();

        for(int row=0; row<h; row++){
            for(int col=0; col<w; col++){
                if(shape[row][col]==1){
                    int x = (block.getX()+col)*blockSize;
                    int y = (block.getY()+row)*blockSize;

                    g.setColor(color);
                    g.fill3DRect(x, y, blockSize, blockSize, true);
                }
            }
        }
    }

    // 블록을 세팅하면 테트리스 패널에 저장해줌
    private void afterSetBlock(){
        int[][] shape = block.getshape();
        int h = block.getHeight();
        int w = block.getWidth();
        int offsetX = block.getX();
        int offsetY = block.getY();
        Color color = block.getColor();

        for(int row=0;row<h;row++){
            for(int col=0;col<w;col++){
                if(shape[row][col]==1){
                    settedBlock[row+offsetY][col+offsetX] = color;
                }
            }
        }
    }

    // 셋팅된 블록들을 테트리스 패널에 그려줌
    private void drawSettedBlock(Graphics g){
        Color color;
        for(int r=0; r<panelRow; r++){
            for(int c=0; c<panelCol; c++){
                color = settedBlock[r][c];

                if(color!=null){
                    int x = c*blockSize;
                    int y = r*blockSize;

                    g.setColor(color);
                    g.fill3DRect(x, y, blockSize, blockSize, true);
                }
            }
        }
    }

    // 블록이 시간 텀을 두며 계속 떨어짐(쓰레드에서 활용)
    // 블록이 바닥에 닿으면 dropBlock() 멈춤
    public boolean dropBlock(){
        if(checkBottom()==true) return false;
        
        block.moveDown();
        repaint();
        return true;
    }

    // 왼쪽 방향키로 블럭 위치 이동
    private void moveBlockDown(){
        if(checkBottom()==true) return;

        block.moveDown();
        repaint();
    }

    // 블록이 바닥에 닿았는지 체크
    private boolean checkBottom(){
        if(block.getBottom()==panelHeight/blockSize){
            afterSetBlock();
            return true;

        }
        return false;
    }

    // 왼쪽 방향키로 블럭 위치 이동
    private void moveBlockLeft(){
        if(checkLeft()==true) return;

        block.moveLeft();
        repaint();
    }

    // 왼쪽 벽에 닿았는지 체크
    private boolean checkLeft(){
        if(block.getX()<=0)
            return true;
        return false;
    }

    // 오른쪽 방향키로 블럭 위치 이동
    private void moveBlockRight(){
        if(checkRight()==true) return;

        block.moveRight();
        repaint();
    }

    // 오른쪽 벽에 닿았는지 체크
    private boolean checkRight(){
        if(block.getX()>=(panelCol-block.getWidth()))
            return true;
        return false;
    }

    // 블록 회전
    public void rotateBlock(){
        block.rotate();
    }
}

