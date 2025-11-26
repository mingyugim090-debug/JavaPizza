package Gui;

import javax.swing.*;
import java.awt.*;
import controller.GameManager;

public class Frame extends JFrame {
	
	private GameManager gameManager;
	
	public Frame() {
	    setTitle("ROBO HUB");
	    getContentPane().setBackground(new Color(0xEDEDED)); //컬러
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setSize(960, 540); //창 크기
	    setLocationRelativeTo(null);

	    setContentPane(new MainPanel(this));
	    setVisible(true);
    }
	
	public void setGameManager(GameManager gameManager) {
	        this.gameManager = gameManager;
	    }
	public GameManager getGameManager() {
        return gameManager;
	}
	 
	    
	public void changePanel(JPanel newPanel) {
	    setContentPane(newPanel);
	    revalidate();
	    repaint();
	}

}
