package com.example;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

abstract class block_breaker
{
   void draw(Graphics g) {};
}
// 블럭 클래스 
class Rect
{
   Rect(Point p, boolean tf)
   {
      pt=p;
      yellow=tf;
   }
   Point pt=new Point();
   boolean yellow=false;
}
// 볼 클래스
class Ball
{
   int bx=400;
   int by=650;
   int br=10;
   int ballcenter=(bx+br/2);
   Point []pt=new Point[4];
   int dx=-1;
   int dy=3;
   
   Ball()
   {
      pt[0]=new Point(bx, by+(br/2));
      pt[1]=new Point(bx+(br/2), by);
      pt[2]=new Point(bx+br, by+(br/2));
      pt[3]=new Point(bx+(br/2), by+br);
   }
   Ball(int x, int y, int _dx, int _dy) // item 블럭 획득시 볼 생성 위치
   {
      bx=x;
      by=y;
      ballcenter=(bx+br/2);
      dx=_dx;
      dy=_dy;
      
      pt[0]=new Point(bx, by+(br/2));
      pt[1]=new Point(bx+(br/2), by);
      pt[2]=new Point(bx+br, by+(br/2));
      pt[3]=new Point(bx+(br/2), by+br);
   }
}

// 게임 실행
class Play extends block_breaker
{
   LinkedList<Rect> rt=new LinkedList<Rect>();
   LinkedList<Ball> ball=new LinkedList<Ball>();
   // bar 사이즈 
   int x=350, y=670;
   int xsize=130, ysize=25;
   int score=0; 
   
   int stage=1; //스테이지
   
   //bar 분할 
   int bx_size, by_size;
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
      bx_size=180;
      by_size=60;
      int item_bl=0; //아이템 블럭 갯수
      
      // stage 1 블럭 생성
      for (int y=0; y<5; y++)
      {
         for (int x=0; x<4; x++)
         {         
            Point c=new Point(25+(bx_size*x)+(5*x),25+(5*y)+(by_size*y));
            boolean temp=false;
            if (item_bl%5==0) //아이템 블럭 생성 조건
               temp=true;
            else
               temp=false;
            Rect r=new Rect(c, temp);
            rt.add(r);
            item_bl++;
         }
      }
   }

   
   void resetball()
   {
      for (int i=0; i<ball.size(); i++)
         ball.get(i).ballcenter=(ball.get(i).bx+(ball.get(i).br/2));
      
      // 볼의 위치
      for (int i=0; i<ball.size(); i++)
      {
    	  if (ball.size()<=0)
    		  break;
    	 // 볼 이동시 panel사이즈 내에서 움직이게 하는 조건
         if (ball.get(i).by+ball.get(i).br>=670 && ball.get(i).by+ball.get(i).br <= 695 && ball.get(i).ballcenter>=x && ball.get(i).ballcenter<=x+xsize)
         {   
        	 
            ball.get(i).by=650;
            ball.get(i).dy=-ball.get(i).dy;
            // bar의 위치에 따른 튕김 방향 조절
            if ( x<=ball.get(i).ballcenter && x1>=ball.get(i).ballcenter)
               ball.get(i).dx=-3;
            else if (x1<ball.get(i).ballcenter && ball.get(i).ballcenter<=x2)
               ball.get(i).dx=-2;
            else if (x2<ball.get(i).ballcenter && ball.get(i).ballcenter<=x3)
               ball.get(i).dx=1;
            else if (x3<ball.get(i).ballcenter && ball.get(i).ballcenter<=x4)
               ball.get(i).dx=2;
            else if (x4<ball.get(i).ballcenter && ball.get(i).ballcenter<=x5)
               ball.get(i).dx=3;
         }
      }
      // 바에 부딫히는 위치에 따라 볼의 방향 바꾸기
      for (int i=0; i<ball.size(); i++)
      {
         if (ball.get(i).bx<20 || ball.get(i).bx>=780-ball.get(i).br*2)
            ball.get(i).dx=-ball.get(i).dx;
         if (ball.get(i).by<20)
         {
            ball.get(i).by=20;
            ball.get(i).dy=-ball.get(i).dy;
         }
      }
      for (int i=0; i<ball.size(); i++)
      {
         ball.get(i).bx+=ball.get(i).dx;
         ball.get(i).pt[0]=new Point(ball.get(i).bx, ball.get(i).by+(ball.get(i).br/2));
         ball.get(i).pt[1]=new Point(ball.get(i).bx+(ball.get(i).br/2), ball.get(i).by);
         ball.get(i).pt[2]=new Point(ball.get(i).bx+ball.get(i).br, ball.get(i).by+(ball.get(i).br/2));
         ball.get(i).pt[3]=new Point(ball.get(i).bx+(ball.get(i).br/2), ball.get(i).by+ball.get(i).br);
      }
      
      collision_block();
      
      if (rt.size()<=0) //블럭이 없으면 다음 스테이지
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
            for (int j=0; j<rt.size(); j++)
            {
               if (collision(rt.get(j).pt, bx_size, by_size, ball.get(ij).pt[i]))
               {
                  score+=10;
                  if (i==1 || i==3)
                     ball.get(ij).dy=-ball.get(ij).dy;
                  else if (i==0 || i==2)
                     ball.get(ij).dx=-ball.get(ij).dx;
                  
                  if (rt.get(j).yellow==true)
                  {
                     Ball b1=new Ball(ball.get(ij).bx-50, ball.get(ij).by, ball.get(ij).dx, ball.get(ij).dy);
                     Ball b2=new Ball(ball.get(ij).bx+50, ball.get(ij).by, ball.get(ij).dx, ball.get(ij).dy);
                     ball.add(b1);
                     ball.add(b2);
                  }
                  rt.remove(j);
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
      b.bx=x+50;
      b.by=640;
      ball.add(b);
      
      if (stage==2)
      {
         rt=new LinkedList<Rect>();
         int cc2=0;
         bx_size=119;
         by_size=50;
         for (int y=0; y<6; y++)
         {
            for (int x=0; x<6; x++)
            {
               Point c=new Point(20+(bx_size*x)+(5*x),20+(5*y)+(by_size*y));
               boolean temp=false;
               if (cc2%4==0)
                  temp=true;
               else
                  temp=false;
               Rect r=new Rect(c, temp);
               rt.add(r);
               cc2++;
            }
         }
      }
      else if (stage==3)
      {
         rt=new LinkedList<Rect>();
         bx_size=77;
         by_size=40;
         int cc=0;
         for (int y=0; y<9; y++)
         {
            for (int x=0; x<9; x++)
            {
               Point c=new Point(20+(bx_size*x)+(5*x),20+(5*y)+(by_size*y));
               boolean temp=false;
               if (cc%7==0)
                  temp=true;
               else
                  temp=false;
               Rect r=new Rect(c, temp);
               rt.add(r);
               cc++;
            }
         }
      }
      else if (stage==4)
      {
         rt=new LinkedList<Rect>();
         bx_size=57;
         by_size=35;
         int cc=0;
         for (int y=0; y<12; y++)
         {
            for (int x=0; x<12; x++)
            {
               Point c=new Point(20+(bx_size*x)+(5*x),20+(5*y)+(by_size*y));
               boolean temp=false;
               if (cc%7==0)
                  temp=true;
               else
                  temp=false;
               Rect r=new Rect(c, temp);
               rt.add(r);
               cc++;
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
      for (Rect r: rt)
      {
         if (r.yellow)
         {
            GradientPaint gp=new GradientPaint(r.pt.x,r.pt.y,Color.ORANGE,r.pt.x+bx_size,r.pt.y+by_size,Color.RED); // item 블럭 색깔
            g2.setPaint(gp);
         }
         else
         {
            GradientPaint gp=new GradientPaint(r.pt.x,r.pt.y,Color.GREEN,r.pt.x+bx_size,r.pt.y+by_size,Color.GRAY); // 일반 블럭 색깔
            g.setColor(Color.BLACK);
            g2.setPaint(gp);
         }
         g2.fillRoundRect(r.pt.x, r.pt.y, bx_size, by_size, 10, 10);
      }
      resetball();
      g2.setColor(Color.pink);
      g2.fillRoundRect(x, y, xsize, ysize, 10, 10);
      g2.setColor(Color.WHITE);
      for (int i=0; i<ball.size(); i++)
         g2.fillOval(ball.get(i).bx, ball.get(i).by, ball.get(i).br, ball.get(i).br);
   }
}

class Over extends block_breaker
{
   int your_score=0;
   int score=0;
   Play play;
   
   Font font1=new Font("stencil", Font.BOLD, 110);
   Font font2=new Font("stencil", Font.BOLD, 40);
   
   String st1=new String("GAME OVER");
   String st3=new String("YOUR SCORE: "+score);
   String st4=new String("Re? PRESS ENTER!");
   
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
   
   final static int GAME_PLAY=1;
   final static int GAME_OVER=2;
   
   Thread G_Play=new Thread(this);
   int mode=GAME_PLAY;
   
   Game()
   {      
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
      g2.fillRect(0, 0, 800, 750);
      
      if (mode==GAME_PLAY)
      {   
         g2.fillRect(0, 0, 800, 750);
         g2.setColor(Color.GRAY);
         g2.fillRect(0, 0, 800, 20);
         g2.fillRect(0, 25, 20, 750);
         g2.fillRect(765, 25, 20, 750);
         
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
         g.drawString(o.st4, 197, 600);
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
            for (int i=0; i<p.rt.size(); i++)
               p.rt.remove(i); //게임오버시 남은 블럭 삭제
            
            //게임오버시 스테이지 1 재생성
            score=0;
            p.score=0;
            
            p.bx_size=180;
            p.by_size=60;
            
            p.rt=new LinkedList<Rect>();
            int item_bl=0;
            for (int y=0; y<5; y++)
            {
               for (int x=0; x<4; x++)
               {
                  Point c=new Point(20+(p.bx_size*x)+(5*x),20+(5*y)+(p.by_size*y));
                  boolean item=false;
                  if (item_bl%5==0)
                     item=true;
                  else
                     item=false;
                  Rect r=new Rect(c, item);
                  p.rt.add(r);
                  item_bl++;
               }
            }
            p.x=350;
            p.y=670;
            p.xsize=130;
            p.ysize=20;
            
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
            p.bx_size=180;
            p.by_size=60;
            
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
            if (p.stage>=5) // 스테이지 4이후 종료
            {
               mode=GAME_OVER;
            }
            // 스테이지별 볼 움직임 속도 증가
            if (p.stage==1)
               a=8;
            else if (p.stage==2)
               a=7;
            else if (p.stage==3)
               a=5;
            else if (p.stage==4)
               a=4;
            
            // 바닥을 넘어갈 시 볼 배열에서 볼 삭제
            for (int i=0; i<p.ball.size(); i++)
            {
               p.ball.get(i).by-=p.ball.get(i).dy;
            }
            for (int i=0; i<p.ball.size(); i++)
            {
               if (p.ball.get(i).by>750)
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
               G_Play.interrupt();
            }
         }
         repaint();
      } 
   }
}
class BlockGameFrame extends JFrame
{
   Game p;
   BlockGameFrame()
   {
      p=new Game();
      setSize(800,750);
      setTitle("블럭 부수기");
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setVisible(true);
      add(p);
      p.setFocusable(true);
   }
}
public class BlockCrash {

   public static void main(String[] args) {
      BlockGameFrame BGf = new BlockGameFrame();

   	}
}
