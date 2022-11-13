package com.example;

import java.awt.*;
import java.util.Random;

public class TetrisBlock {

    int[][] L_block = {{1,0},
                       {1,0},
                       {1,1}};

    int[][] J_block = {{0,1},
                       {0,1},
                       {1,1}};

    int[][] O_block = {{1,1},
                       {1,1}};

    int[][] T_block = {{0,1,0},
                       {1,1,1}};

    int[][] S_block = {{1,1,0},
                       {0,1,1}};

    int[][] Z_block = {{0,1,1},
                       {1,1,0}};

    int[][] I_block = {{1,1,1,1}};

    int[][][] blockShapes = {L_block, J_block, O_block, T_block, S_block, Z_block, I_block};
    
    Color blockColor[] = {Color.MAGENTA, Color.GREEN, Color.GRAY, Color.RED};

    private int[][] shape;
    private Color color;
    private int x,y;
    private int[][][] shapes;
    private int currnetRotation;

    Random r = new Random();

    public TetrisBlock(int panelCol){
        this.shape = blockShapes[r.nextInt(blockShapes.length)];
        this.color = blockColor[r.nextInt(blockColor.length)];

        x = (int)(Math.random()*(panelCol-getWidth()));
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
