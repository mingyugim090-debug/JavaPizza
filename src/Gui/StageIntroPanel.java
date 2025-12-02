package Gui;

import javax.swing.*;

import controller.GameManager;

import java.awt.*;
import java.awt.event.*;

public class StageIntroPanel extends JPanel{
	
	 private Frame frame;
	 private GameManager gameManager;

	    public StageIntroPanel(Frame frame) {
	    	
	        this.frame = frame;
	        this.gameManager = frame.getGameManager();
	       
	        setLayout(null);
	        setBackground(Color.GRAY);

	        int width = frame.getWidth();
	        int height = frame.getHeight();
	        
	        int labelWidth = width * 4 / 10;
	        int labelHeight = height * 3 / 10;
	        int labelX = (width - labelWidth) / 2;
	        int labelY = (int)(height - labelHeight) / 2;
	        
	        JLabel label = new JLabel("Stage " + gameManager.getStage(), SwingConstants.CENTER);
	        label.setFont(new Font("Arial", Font.BOLD, 50));
	        label.setForeground(Color.WHITE);
	        
	        label.setLocation(labelX, labelY);
	        label.setSize(labelWidth,labelHeight);
	        add(label);
	        
	        //2초 후 GamePanel로 이동
	        Timer timer = new Timer(2000, new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	GamePanel gamePanel = new GamePanel(frame);
	            	frame.showGamePanel();
	            	gameManager.initGame();
	            }
	        });
	        timer.setRepeats(false);
	        timer.start();
	    }
}
