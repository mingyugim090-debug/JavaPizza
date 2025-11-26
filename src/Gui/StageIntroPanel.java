package Gui;

import javax.swing.*;

import controller.GameManager;

import java.awt.*;
import java.awt.event.*;

public class StageIntroPanel extends JPanel{
	
	 private Frame frame;
	 private JLabel label;
	 
	 private int stage;
	 private int coins;
	 private int score;

	    public StageIntroPanel(Frame frame, int stage) {
	        this.frame = frame;
	        this.stage = stage;
	        this.coins = coins;
	        this.score = score;
	        
	        setLayout(null);
	        setBackground(Color.GRAY);

	        label = new JLabel("Stage " + stage, SwingConstants.CENTER);
	        label.setFont(new Font("Arial", Font.BOLD, 50));
	        label.setForeground(Color.WHITE);
	        add(label);
	        
	        //2초 후 GamePanel로 이동
	        Timer timer = new Timer(2000, new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	GamePanel gamePanel = new GamePanel(frame,stage,coins,score);
	            	GameManager gameManager = frame.getGameManager();
	            	gameManager.setGamePanel(gamePanel);
	            	frame.changePanel(gamePanel);
	            	gameManager.initGame();
	            	
	            }
	        });
	        timer.setRepeats(false);
	        timer.start();
	    }

	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        
	        //전체 패널의 폭/높이
	        int width = getWidth();
	        int height = getHeight();
	        
	        //"Stage N" 텍스트 위치/크기 
	        int labelWidth = (int)(width * 0.4);
	        int labelHeight = (int)(height * 0.3);
	        int labelX = (width - labelWidth) / 2;
	        int labelY = (int)(height - labelHeight) / 2;
	   
	        label.setLocation(labelX, labelY);
	        label.setSize(labelWidth,labelHeight);
	    }
}
