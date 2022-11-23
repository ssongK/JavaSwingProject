package com.example.tetris;

import java.awt.*;
import java.util.Random;

public class TetrisBlock {

    private int[][] L_block = {{1,0},
                               {1,0},
                               {1,1}};

    private int[][] J_block = {{0,1},
                               {0,1},
                               {1,1}};

    private int[][] O_block = {{1,1},
                               {1,1}};

    private int[][] T_block = {{0,1,0},
                               {1,1,1}};

    private int[][] S_block = {{1,1,0},
                               {0,1,1}};

    private int[][] Z_block = {{0,1,1},
                               {1,1,0}};

    private int[][] I_block = {{1,1,1,1}};

    private int[][][] blockShapes = {L_block, J_block, O_block, T_block, S_block, Z_block, I_block};
    
    private Color blockColor[] = {Color.MAGENTA, Color.GREEN, Color.ORANGE, Color.RED, Color.CYAN, Color.YELLOW};

    private int[][] shape;
    private Color color;
    private int x,y;
    private int[][][] shapes;
    private int currnetRotation;

    Random r = new Random();

    public TetrisBlock(int panelCol){
        this.shape = blockShapes[r.nextInt(blockShapes.length)];
        this.color = blockColor[r.nextInt(blockColor.length)];

        x = r.nextInt(panelCol-getWidth());
        y = -getHeight();
    
        initShape();
    }

    private void initShape(){
        shapes = new int[4][][];

        for(int i=0; i<4; i++){
            int row = shape[0].length;
            int col = shape.length;

            shapes[i] = new int[row][col];
            for(int y=0; y<row; y++){
                for(int x=0; x<col; x++){
                    shapes[i][y][x] = shape[col-x-1][y];
                }
            }
            shape = shapes[i];
        }
        currnetRotation = 0;
        shape = shapes[currnetRotation];
    }

    public int[][] getshape(){return shape;}
    public Color getColor(){return color;}
    public int getWidth(){return shape[0].length;} 
    public int getHeight(){return shape.length;} 

    public int getX(){return x;}
    public int getY(){return y;}

    public void setX(int newX){ x = newX; }
    public void setY(int newY){ y = newY; }

    public void moveDown(){y++;}
    public void moveRight(){x++;}
    public void moveLeft(){x--;}
    public void rotate(){
        currnetRotation++;
        currnetRotation %= 4;
        shape = shapes[currnetRotation];
    }

    public int getBottom(){return y+getHeight();}
    public int getLeft(){return x;}
    public int getRight(){return x+getWidth();}
}
