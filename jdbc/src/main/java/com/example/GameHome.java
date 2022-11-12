package com.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// 패키지로 묶어서 각각의 게임을 받아올 것


//GameHome-----------------------------------------------------------------------------
public class GameHome extends JFrame {
    Container contentPane;
    CenterPanel centerPanel = new CenterPanel();
    JTextField txtInput;
    JLabel id, U_name;
    JButton btnLogin, btnLogout, btnSingup, btnRank;
    JButton btnGm1, btnGm2, btnGm3, btnGm4;
    public GameHome() {
        setTitle("JAVA 프로젝트 GAME");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = getContentPane();
        contentPane.add(new NorthPanel(), BorderLayout.NORTH);
        contentPane.add(centerPanel, BorderLayout.CENTER);
        
        setResizable(false);
        setSize(600,550);
        setVisible(true);    
    }
    
    
//Login and Sing up Panel() ------------------------------------------------------------------------------------------------
    class NorthPanel extends JPanel{
        public NorthPanel() {
        	setLayout(new FlowLayout(FlowLayout.RIGHT));
            this.setBackground(new Color(124,187,250));
            
            Jdbc j = new Jdbc();
            
            btnLogin = new JButton(" 로그인 ");
            add(btnLogin);           
            //Login Dialog() -----------------------------------------------------------------------------------------
            btnLogin.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		String name=JOptionPane.showInputDialog(NorthPanel.this,"닉네임을 입력해주세요.");
            		if(name!=null) {
            			if(j.login(name) == true) {
            				U_name = new JLabel(name);
            				U_name.setHorizontalAlignment(JLabel.LEFT);
            				add(U_name);
            				btnLogout.setVisible(true);
            				btnRank.setVisible(true);
            				btnLogin.setVisible(false);
            				btnSingup.setVisible(false);
            			}
            			return; }
            		else if(name==null) {j.login(null); return;}
            		}
            });
            //Login InputDialog -----------------------------------------------------------------------------------------
            
                    
            btnSingup = new JButton(" 회원 가입 ");
            add(btnSingup);
            //Sing up Dialog() -----------------------------------------------------------------------------------------
            btnSingup.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		String name=JOptionPane.showInputDialog(NorthPanel.this,"닉네임을 입력해주세요.");
            		if(name!=null) { j.join(name); return; }
            		else if(name==null) {j.join(null); return;}
            		}
            });
            //Sing up Dialog -----------------------------------------------------------------------------------------

            btnLogout = new JButton(" 로그아웃 ");
            add(btnLogout);
            btnLogout.setVisible(false);
            
            btnRank = new JButton(" 랭킹 화면 ");
            add(btnRank);
            btnRank.setVisible(false);
            
            //Rank page open Listener() ------------------------------------------
            btnRank.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		new GameRank();
            	}
            });
            //Rank page open Listener ------------------------------------------
            
            
            //logout Listener() ------------------------------------
            btnLogout.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		if(j.logout()==true) {
            			U_name = null; 
        				btnLogout.setVisible(true);
        				btnRank.setVisible(true);
        				btnLogin.setVisible(false);
        				btnSingup.setVisible(false);
            		}
            	}
            });
            //logout Listener() ------------------------------------
            
            
        }
     }
//Login and Sing up Panel ------------------------------------------------------------------------------------------------    

        
//MiniGame Button()----------------------------------------------------------------------------------------------------
    class CenterPanel extends JPanel{  
        public CenterPanel() {
            setLayout(new GridLayout(2, 2, 20, 20));
            
            ImageIcon nomalGM1 = new ImageIcon("imges/apple.jpg");   // 게임 이미지 어두운 배경으로 변경예정
            ImageIcon rolloverGM1 = new ImageIcon("imges/apple1.jpg");  // 게임 이미지 밝은 화면으로 변경 예정
            
            ImageIcon nomalGM2 = new ImageIcon("imges/apple.jpg");
            ImageIcon rolloverGM2 = new ImageIcon("imges/apple1.jpg");
            
            ImageIcon nomalGM3 = new ImageIcon("imges/apple.jpg");
            ImageIcon rolloverGM3 = new ImageIcon("imges/apple1.jpg");
            
            ImageIcon nomalGM4 = new ImageIcon("imges/apple.jpg");
            ImageIcon rolloverGM4 = new ImageIcon("imges/apple1.jpg");
            
            btnGm1 = new JButton(nomalGM1);
            add(btnGm1);
            btnGm1.setRolloverIcon(rolloverGM1);
            btnGm1.addActionListener(new NEWFrame());
            
            btnGm2 = new JButton(nomalGM2);
            add(btnGm2);
            btnGm2.setRolloverIcon(rolloverGM2);
            add(btnGm2);
            btnGm2.addActionListener(new NEWFrame());
            
            btnGm3 = new JButton(nomalGM3);
            add(btnGm3);
            btnGm3.setRolloverIcon(rolloverGM3);
            add(btnGm3);
            btnGm3.addActionListener(new NEWFrame());
            
            btnGm4 = new JButton(nomalGM4);
            add(btnGm4);
            btnGm4.setRolloverIcon(rolloverGM4);
            add(btnGm4);
            btnGm4.addActionListener(new NEWFrame());
        }
    }
//MiniGame Button  --------------------------------------------------
    
      
//MiniGame NewFrame Listener() ----------------------------------------------
    class NEWFrame implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
    		Object obj = e.getSource();
    		if(obj == btnGm1) {
    			new Tetris();
    		}
    		if(obj == btnGm2) {
    			//new testgame2();   // 패키지에 연결된 작성한 게임이름으로 변경후 사용
    		}
    		if(obj == btnGm3) {
    			//new testgame3();   // 패키지에 연결된 작성한 게임이름으로 변경후 사용
    		}
    		if(obj == btnGm4) {
    			//new testgame4();   // 패키지에 연결된 작성한 게임이름으로 변경후 사용
    		}
  
    	}
    }
//MiniGame NewFrame Listener -------------------------------------------------
        

    public static void main(String[] args) {
        new GameHome();
    }
}
//GameHome-----------------------------------------------------------------------------
