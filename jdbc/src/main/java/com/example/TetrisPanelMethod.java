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
    private Color[][] settedBlock = new Color[panelRow][panelCol];
    private JLabel scoreBoard;
    private int score;

    // 생성자 : panel size, bgColor 설정, createBlock 메서드 호출
    public TetrisPanelMethod(JLabel scoreBoard){
        this.scoreBoard = scoreBoard;
        this.score = 0;

        setBackground(Color.ORANGE);
        setBounds(0,50,panelWidth,panelHeight);

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
    }

    // TetrisBlock 클래스에서 생성(모양, 크기, ColSize)
    public void createBlock(){
        block = new TetrisBlock(panelCol);
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
                // settedBlock[i][j] = null;
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

        if(checkGameOver()) return;
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
        if(checkBottom()){
            afterSetBlock();
            clearLines();
            return false;
        }

        block.moveDown();
        repaint();
        return true;
    }

    // 아래쪽 방향키로 블럭 위치 이동
    private void moveBlockDown(){
        if(checkBottom()) return;

        block.moveDown();
        repaint();
    }

    // 블록이 바닥에 닿았는지 체크
    private boolean checkBottom(){
        if(block.getBottom()==panelRow) return true;

        int[][] shape = block.getshape();
        int w = block.getWidth();
        int h = block.getHeight();

        for(int col=0; col<w; col++){
            for(int row=h-1; row>=0; row--){
                if(shape[row][col]!=0){
                    int x = col + block.getX();
                    int y = row + block.getY()+1;
                    if(x<0||x>=panelCol) break;
                    if(y<0||y>=panelRow) break;
                    if(settedBlock[y][x]!=null) return true;
                    break;
                }
            }
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
        if(block.getX()<=0) return true;

        int[][] shape = block.getshape();
        int w = block.getWidth();
        int h = block.getHeight();

        for(int row=0; row<h; row++){
            for(int col=0; col<w; col++){
                if(shape[row][col]!=0){
                    int x = col + block.getX()-1;
                    int y = row + block.getY();
                    if(y<0) break;
                    if(settedBlock[y][x]!=null) return true;
                    break;
                }
            }
        }

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
        if(block.getX()>=(panelCol-block.getWidth())) return true;

        int[][] shape = block.getshape();
        int w = block.getWidth();
        int h = block.getHeight();

        for(int row=0; row<h; row++){
            for(int col=w-1; col>=0; col--){
                if(shape[row][col]!=0){
                    int x = col + block.getX()+1;
                    int y = row + block.getY();
                    if(y<0) break;
                    if(settedBlock[y][x]!=null) return true;
                    break;
                }
            }
        }

        return false;
    }

    // 블록 회전
    public void rotateBlock(){
        if(block==null) return;
        block.rotate();

        if(block.getLeft()<0) block.setX(0);
        if(block.getRight()>=panelCol) block.setX(panelCol-block.getWidth());
        if(block.getBottom()>=panelRow) block.setY(panelRow-block.getHeight());

        repaint();
    }

    // 다 채워지면 라인 클리어 -> 점수 부여
    public void clearLines(){
        boolean lineFilled;

        for(int row=panelRow-1; row>=0; row--){
            lineFilled = true;

            for(int col=0; col<=panelCol-1; col++){
                if(settedBlock[row][col]==null){
                    lineFilled = false;
                    break;
                }
            }

            if(lineFilled){
                score += 100;
                for(int i=0; i<=panelCol-1; i++){
                    settedBlock[row][i]=null;
                }
                for(int r=row; r>0; r--){
                    for(int col=0; col<=panelCol-1; col++){
                        settedBlock[r][col] = settedBlock[r-1][col];
                    }
                }
                repaint();
                scoreBoard.setText("score : "+score);;
                row++;
            }
        }
    }

    // 블록이 천장에 닿았는지 체크 -> 닿으면 게임 종료
    public boolean checkGameOver(){
        if(block.getY()<0)
            return true;
        return false;
    }
}

