package com.example;

import java.awt.*;

public class TetrisBlock {
    private int[][] shape;
    private Color color;
    private int x,y;
    private int[][][] shapes;
    private int currnetRotation;

    public TetrisBlock(int[][] shape, Color color, int panelCol){
        this.shape = shape;
        this.color = color;
        x = (int)(Math.random()*(panelCol-getWidth()+1));
        y = -(getHeight()+1);
        initShape();
    }

    private void initShape(){
        shapes = new int[4][][];

        for(int i=0; i<4; i++){
            
        }
    }

    public int[][] getshape(){return shape;}
    public Color getColor(){return color;}
    public int getWidth(){return shape[0].length;} 
    public int getHeight(){return shape.length;} 

    public int getX(){return x;}
    public int getY(){return y;}

    public void moveDown(){y++;}
    public void moveRight(){x++;}
    public void moveLeft(){x--;}
    public void rotate(){
        currnetRotation++;
        currnetRotation %= 4;
        shape = shapes[currnetRotation];
    }

    public int getBottom(){return y+getHeight();}
}
