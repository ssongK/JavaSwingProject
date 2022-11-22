package com.example;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

abstract class block_breaker
{
   void draw(Graphics g) {};
}
// 블럭 클래스 
class Block
{
   Block(Point p, boolean tf)
   {
      pt=p;
      red=tf;
   }
   Point pt=new Point();
   boolean red=false;
}
// 볼 클래스
class Ball
{
   int ballx=400;
   int bally=650;
   int ball_size=10;
   int ballcenter=(ballx+ball_size/2);
   Point []pt=new Point[4];
   int ball_bouncex=-1;
   int ball_bouncey=3;
   
   Ball()
   {
      pt[0]=new Point(ballx, bally+(ball_size/2));
      pt[1]=new Point(ballx+(ball_size/2), bally);
      pt[2]=new Point(ballx+ball_size, bally+(ball_size/2));
      pt[3]=new Point(ballx+(ball_size/2), bally+ball_size);
   }
   Ball(int x, int y, int _dx, int _dy) // item 블럭 획득시 볼 생성 위치
   {
      ballx=x;
      bally=y;
      ballcenter=(ballx+ball_size/2);
      ball_bouncex=_dx;
      ball_bouncey=_dy;
      
      pt[0]=new Point(ballx, bally+(ball_size/2));
      pt[1]=new Point(ballx+(ball_size/2), bally);
      pt[2]=new Point(ballx+ball_size, bally+(ball_size/2));
      pt[3]=new Point(ballx+(ball_size/2), bally+ball_size);
   }
}

// 게임 실행
class Play extends block_breaker
{
   LinkedList<Block> block=new LinkedList<Block>();
   LinkedList<Ball> ball=new LinkedList<Ball>();
   // bar 사이즈 
   int x=350, y=670;
   int xsize=130, ysize=25;
   int score=0; 
   
   int stage=5; //스테이지
   
   int bl_sizex, bl_sizey;
   
   //bar 분할 
   int n=xsize/5;
   int x1=x+n;
   int x2=x1+n;
   int x3=x2+n;
   int x4=x3+n;
   int x5=x4+n;
   
   Play()
   {
      Ball b=new Ball();
      ball.add(b);
      bl_sizex=180;
      bl_sizey=60;
      int item_bl=0; //아이템 블럭 갯수
      
      // stage 1 블럭 생성
      for (int y=0; y<5; y++)
      {
         for (int x=0; x<4; x++)
         {         
            Point c=new Point(25+(bl_sizex*x)+(5*x),25+(5*y)+(bl_sizey*y));
            boolean temp=false;
            if (item_bl%5==0) //아이템 블럭 생성 조건
               temp=true;
            else
               temp=false;
            Block r=new Block(c, temp);
            block.add(r);
            item_bl++;
         }
      }
   }

   
   void resetball()
   {
      for (int i=0; i<ball.size(); i++)
         ball.get(i).ballcenter=(ball.get(i).ballx+(ball.get(i).ball_size/2));
      
      // 볼의 위치
      for (int i=0; i<ball.size(); i++)
      {
    	  if (ball.size()<=0)
    		  break;
    	 // 볼 이동시 panel사이즈 내에서 움직이게 하는 조건
         if (ball.get(i).bally+ball.get(i).ball_size>=670 && ball.get(i).bally+ball.get(i).ball_size <= 695 && ball.get(i).ballcenter>=x && ball.get(i).ballcenter<=x+xsize)
         {   
        	 
            ball.get(i).bally=650;
            ball.get(i).ball_bouncey=-ball.get(i).ball_bouncey;
            // bar의 위치에 따른 튕김 방향 조절
            if ( x<=ball.get(i).ballcenter && x1>=ball.get(i).ballcenter)
               ball.get(i).ball_bouncex=-3;
            else if (x1<ball.get(i).ballcenter && ball.get(i).ballcenter<=x2)
               ball.get(i).ball_bouncex=-1;
            else if (x2<ball.get(i).ballcenter && ball.get(i).ballcenter<=x3)
               ball.get(i).ball_bouncex=1;
            else if (x3<ball.get(i).ballcenter && ball.get(i).ballcenter<=x4)
               ball.get(i).ball_bouncex=2;
            else if (x4<ball.get(i).ballcenter && ball.get(i).ballcenter<=x5)
               ball.get(i).ball_bouncex=3;
         }
      }
      // 바에 부딫히는 위치에 따라 볼의 방향 바꾸기
      for (int i=0; i<ball.size(); i++)
      {
         if (ball.get(i).ballx<20 || ball.get(i).ballx>=780-ball.get(i).ball_size*2)
            ball.get(i).ball_bouncex=-ball.get(i).ball_bouncex;
         if (ball.get(i).bally<20)
         {
            ball.get(i).bally=20;
            ball.get(i).ball_bouncey=-ball.get(i).ball_bouncey;
         }
      }
      for (int i=0; i<ball.size(); i++)
      {
         ball.get(i).ballx+=ball.get(i).ball_bouncex;
         ball.get(i).pt[0]=new Point(ball.get(i).ballx, ball.get(i).bally+(ball.get(i).ball_size/2));
         ball.get(i).pt[1]=new Point(ball.get(i).ballx+(ball.get(i).ball_size/2), ball.get(i).bally);
         ball.get(i).pt[2]=new Point(ball.get(i).ballx+ball.get(i).ball_size, ball.get(i).bally+(ball.get(i).ball_size/2));
         ball.get(i).pt[3]=new Point(ball.get(i).ballx+(ball.get(i).ball_size/2), ball.get(i).bally+ball.get(i).ball_size);
      }
      
      collision_block();
      
      if (block.size()<=0) //블럭이 없으면 다음 스테이지
      {
         stage++;
         nextstage();
      }
   }
   
   void collision_block()
   {
      for (int ij=0; ij<ball.size(); ij++)
      {  
         for (int i=0; i<4; i++) // 블럭충돌 검사와 해당 블럭 지우기
         {
            for (int j=0; j<block.size(); j++)
            {
               if (collision(block.get(j).pt, bl_sizex, bl_sizey, ball.get(ij).pt[i]))
               {
                  score+=10;
                  if (i==1 || i==3)
                     ball.get(ij).ball_bouncey=-ball.get(ij).ball_bouncey;
                  else if (i==0 || i==2)
                     ball.get(ij).ball_bouncex=-ball.get(ij).ball_bouncex;
                  
                  if (block.get(j).red==true)
                  {
                     Ball b1=new Ball(ball.get(ij).ballx-50, ball.get(ij).bally, ball.get(ij).ball_bouncex, ball.get(ij).ball_bouncey);
                     Ball b2=new Ball(ball.get(ij).ballx+50, ball.get(ij).bally, ball.get(ij).ball_bouncex, ball.get(ij).ball_bouncey);
                     ball.add(b1);
                     ball.add(b2);
                  }
                  block.remove(j);
               }
            }
         }
      }
   }
   
   // 2~4스테이지
   void nextstage()
   {
      ball.removeAll(ball);
      Ball b=new Ball();
      b.ballx=x+50;
      b.bally=640;
      ball.add(b);
      
      if (stage==2)
      {
         block=new LinkedList<Block>();
         int item_bl2=0;
         bl_sizex=119;
         bl_sizey=50;
         for (int y=0; y<6; y++)
         {
            for (int x=0; x<6; x++)
            {
               Point c=new Point(20+(bl_sizex*x)+(5*x),20+(5*y)+(bl_sizey*y));
               boolean temp=false;
               if (item_bl2%4==0)
                  temp=true;
               else
                  temp=false;
               Block r=new Block(c, temp);
               block.add(r);
               item_bl2++;
            }
         }
      }
      else if (stage==3)
      {
         block=new LinkedList<Block>();
         bl_sizex=77;
         bl_sizey=40;
         int item_bl3=0;
         for (int y=0; y<9; y++)
         {
            for (int x=0; x<9; x++)
            {
               Point c=new Point(20+(bl_sizex*x)+(5*x),20+(5*y)+(bl_sizey*y));
               boolean temp=false;
               if (item_bl3%7==0)
                  temp=true;
               else
                  temp=false;
               Block r=new Block(c, temp);
               block.add(r);
               item_bl3++;
            }
         }
      }
      else if (stage==4)
      {
         block=new LinkedList<Block>();
         bl_sizex=57;
         bl_sizey=35;
         int item_bl4=0;
         for (int y=0; y<12; y++)
         {
            for (int x=0; x<12; x++)
            {
               Point c=new Point(20+(bl_sizex*x)+(5*x),20+(5*y)+(bl_sizey*y));
               boolean temp=false;
               if (item_bl4%7==0)
                  temp=true;
               else
                  temp=false;
               Block r=new Block(c, temp);
               block.add(r);
               item_bl4++;
            }
         }
      }
      else if (stage==5)
      {
         block=new LinkedList<Block>();
         bl_sizex=45;
         bl_sizey=30;
         int item_bl5=0;
         for (int y=0; y<15; y++)
         {
            for (int x=0; x<15; x++)
            {
               Point c=new Point(20+(bl_sizex*x)+(5*x),20+(5*y)+(bl_sizey*y));
               boolean temp=false;
               if (item_bl5%8==0)
                  temp=true;
               else
                  temp=false;
               Block r=new Block(c, temp);
               block.add(r);
               item_bl5++;
            }
         }
      }
   }
   
   // 충돌 검사 코드
   boolean collision(Point p, int _x, int _y, Point bp)
   {   
      Rectangle2D r=new Rectangle2D.Float(p.x,p.y,_x,_y);
      if (r.contains(bp))
         return true;
      else
         return false;
   }
   // 블럭 및 볼 그리기
   void draw (Graphics g)
   {
      Graphics2D g2=(Graphics2D)g;
      for (Block r: block)
      {
         if (r.red)
         {
            GradientPaint gp=new GradientPaint(r.pt.x,r.pt.y,Color.ORANGE,r.pt.x+bl_sizex,r.pt.y+bl_sizey,Color.RED); // item 블럭 색깔
            g2.setPaint(gp);
         }
         else
         {
            GradientPaint gp=new GradientPaint(r.pt.x,r.pt.y,Color.GREEN,r.pt.x+bl_sizex,r.pt.y+bl_sizey,Color.GRAY); // 일반 블럭 색깔
            g.setColor(Color.BLACK);
            g2.setPaint(gp);
         }
         g2.fillRoundRect(r.pt.x, r.pt.y, bl_sizex, bl_sizey, 10, 10);
      }
      resetball();
      g2.setColor(Color.pink);
      g2.fillRoundRect(x, y, xsize, ysize, 10, 10);
      g2.setColor(Color.WHITE);
      for (int i=0; i<ball.size(); i++)
         g2.fillOval(ball.get(i).ballx, ball.get(i).bally, ball.get(i).ball_size, ball.get(i).ball_size);
   }
}

class Over extends block_breaker
{
   int your_score=0;
   int score=0;
   Play play;
   String name;
   
   Font font1=new Font("stencil", Font.BOLD, 110);
   Font font2=new Font("stencil", Font.BOLD, 40);
   
   String st1=new String("GAME OVER");
   String st3=new String("YOUR SCORE: "+score);
   String st4=new String("Re? PRESS ENTER!");
   String st5=new String("Exit? PRESS SPACE");
   
   void draw(Graphics g)
   {
      g.setFont(font1);
      
      g.setColor(Color.BLACK);
      g.drawString(st1, 60, 310);
      g.setColor(Color.white);
      g.drawString(st1, 57, 310);
      
      g.setFont(font2);
      g.setColor(Color.BLACK);
      g.setColor(Color.white);
      
      g.setColor(Color.BLACK);
      g.drawString(st3, 230, 490);
      g.setColor(Color.white);
      g.drawString(st3, 227, 490);
   }
} 

class Game extends JPanel implements KeyListener, Runnable
{
   Play p = new Play();
   Over o = new Over();
   
   int score=0;
   
   public String userName;
   
   final static int GAME_PLAY=1;
   final static int GAME_OVER=2;
   
   Thread G_Play=new Thread(this);
   int mode=GAME_PLAY;
   
   Game(String name)
   {
	  this.userName = name;
	  G_Play.start();
      requestFocus();
      setFocusable(true);
      addKeyListener(this);
   }
   @Override
   protected void paintComponent(Graphics g)
   {
      requestFocus();
      setFocusable(true);
      super.paintComponent(g);
      Graphics2D g2=(Graphics2D)g;
      GradientPaint gp=new GradientPaint(400,0,Color.BLACK,400,800,Color.GRAY);
      g2.setPaint(gp);
      g2.fillRect(0, 0, 800, 800);
      
      if (mode==GAME_PLAY)
      {   
         g2.fillRect(0, 0, 800, 800);
         g2.setColor(Color.GRAY);
         g2.fillRect(0, 0, 800, 20);
         g2.fillRect(0, 25, 20, 800);
         g2.fillRect(765, 25, 20, 800);
         
         p.draw(g);
      }
      else if (mode==GAME_OVER)
      {
         g2.fillRect(0, 0, 800, 700);
         o.your_score=p.score;
         o.st3="YOUR SCORE: "+o.your_score;
         o.draw(g);
         g.setColor(Color.black);
         g.drawString(o.st4, 200, 600);
         g.setColor(Color.red);
         g.drawString(o.st4, 196, 600);
         
         g.setColor(Color.black);
         g.drawString(o.st5, 190, 650);
         g.setColor(Color.red);
         g.drawString(o.st5, 186, 650);
         
      }
   }

   @Override
   public void keyPressed(KeyEvent e) {

      setFocusable(true);
      // 게임 오버
      if (e.getKeyCode()==KeyEvent.VK_ENTER)
      {
    	  if (mode==GAME_OVER)
         {
            for (int i=0; i<p.block.size(); i++)
               p.block.remove(i); //게임오버시 남은 블럭 삭제
            
            //게임오버시 스테이지 1 재생성
            score=0;
            p.score=0;
            
            p.bl_sizex=180;
            p.bl_sizey=60;
            
            p.block=new LinkedList<Block>();
            int item_bl=0;
            for (int y=0; y<5; y++)
            {
               for (int x=0; x<4; x++)
               {         
                  Point c=new Point(25+(p.bl_sizex*x)+(5*x),25+(5*y)+(p.bl_sizey*y));
                  boolean temp=false;
                  if (item_bl%5==0) //아이템 블럭 생성 조건
                     temp=true;
                  else
                     temp=false;
                  Block r=new Block(c, temp);
                  p.block.add(r);
                  item_bl++;
               }
            }
            p.x=350;
            p.y=670;
            p.xsize=130;
            p.ysize=25;
            
            p.n=p.xsize/5;
            p.x1=p.x+p.n;
            p.x2=p.x1+p.n;
            p.x3=p.x2+p.n;
            p.x4=p.x3+p.n;
            p.x5=p.x4+p.n;
            
            p.ball=new LinkedList<Ball>();
            Ball b=new Ball();
            p.ball.add(b);
            
            p.x=350;
            p.y=670;
         
            p.stage=1;
            p.bl_sizex=180;
            p.bl_sizey=60;
            
            mode=GAME_PLAY;
         }
      }
      // 게임플레이 좌우 키보드 사용
      if (mode==GAME_PLAY)
      {
         if (e.getKeyCode()==KeyEvent.VK_LEFT)
         {
            if (p.x-80<=20)
               p.x=20;
            else
               p.x-=80;
         }
         else if (e.getKeyCode()==KeyEvent.VK_RIGHT)
         {
            if (p.x+p.xsize>=700)
               p.x=635;
            else
               p.x+=80;
         }
      }
      p.resetball();
      repaint();
   }
protected void dispose() {
	// TODO Auto-generated method stub
	
}
@Override
   public void keyReleased(KeyEvent e) {
      // TODO Auto-generated method stub
      
   }
   @Override
   public void keyTyped(KeyEvent e) {
      // TODO Auto-generated method stub
      
   }
   @Override
   public void run() {
      
	  //볼 스피드
      int a=100;
      setFocusable(true);           
      while(true)
      {
         try {
            Thread.sleep(a);
         } catch (InterruptedException e) {
            e.printStackTrace(); // 에러 발생시 지나간 코드 출력
         }
         if (mode==GAME_PLAY)
         {
            if (p.stage>=6) // 스테이지 4이후 종료
            {
               mode=GAME_OVER;
            }
            // 스테이지 별 볼 움직임 속도 증가
            if (p.stage==1)
               a=8;
            else if (p.stage==2)
               a=6;
            else if (p.stage==3)
               a=4;
            else if (p.stage==4)
               a=3;
            else if (p.stage==5)
               a=2;
            
            // 바닥을 넘어갈 시 볼 배열에서 볼 삭제
            for (int i=0; i<p.ball.size(); i++)
            {
               p.ball.get(i).bally-=p.ball.get(i).ball_bouncey;
            }
            for (int i=0; i<p.ball.size(); i++)
            {
               if (p.ball.get(i).bally>800)
               {
                  p.ball.remove(i);
                  
               }
            }
            // 존재하는 볼의 갯수가 0개 일때
            if (p.ball.size()<=0)
            {
               mode=GAME_OVER;
               score=p.score;
               a=100;
               /* 점수기록 */
               Jdbc j = new Jdbc();
               j.saveGameScore("game2", o.your_score, userName);

               G_Play.interrupt();
            }
         }
         repaint();
      } 
   }
}

public class BlockCrash extends JFrame
{
   public String userName;
   public JLabel u_name = new JLabel("NAME : ", JLabel.CENTER);
   Font font=new Font("stencil", Font.BOLD, 25);
   
   BlockCrash(String name)
   {
	  this.userName = name;
	  u_name.setText("NAME : "+name);
      Game G = new Game(userName);
      setSize(800,820);
      setTitle("블럭 부수기");
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setVisible(true);
      G.addKeyListener(new KeyAdapter() {
    	  public void keyPressed(KeyEvent e) {
    		  if(G.mode==G.GAME_OVER)
    			  if(e.getKeyCode()==KeyEvent.VK_SPACE) {
    				  G.G_Play.interrupt();
    				  dispose();
    				  
    			  }
    	  }});
      add(G);
      G.setFocusable(true);
      u_name.setFont(font);
      u_name.setForeground(Color.WHITE);
      G.setLayout(new BorderLayout());
      G.add(u_name, BorderLayout.SOUTH);
      
   }
   /**/public static void main(String[] args) {
	    BlockCrash v = new BlockCrash("test"); 
	/**/ }
}
