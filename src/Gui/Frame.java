//Frame 클래스의 역할 : 화면 전환 담당 (MainPanel, StageIntroPanel, GamePanel)

package Gui;

import javax.swing.*;
import java.awt.*;
import controller.GameManager;


public class Frame extends JFrame {
	
	private GameManager gameManager;
	
	public Frame() {
	    setTitle("ROBO HUB");
	    getContentPane().setBackground(new Color(0xEDEDED)); 
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setSize(960, 540); //창 크기(하지만 전체 화면으로 사용하라고 할 예정)
	    
	    GameManager gameManager= new GameManager(this);
	    setGameManager(gameManager);

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
	
	//MainPanel을 보여주는 메소드(gameover 후 다시 시작할 때)
	public void showMainPanel() {
	    changePanel(new MainPanel(this));
	}
	
	//StageIntroPanel을 보여주는 메소드
	public void showStageIntro() {
	    changePanel(new StageIntroPanel(this));
	}
	
	//GamePanel을 보여주는 메소드
	public void showGamePanel() {
		GamePanel gamePanel = new GamePanel(this);
		gameManager.setGamePanel(gamePanel);
		changePanel(gamePanel);
	}

}
	