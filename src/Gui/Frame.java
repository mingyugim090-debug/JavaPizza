package Gui;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
	
	public Frame() {
	        setTitle("ROBO HUB");
	        getContentPane().setBackground(new Color(0xEDEDED)); //컬러
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(600, 300); //창 크기
	        setLocationRelativeTo(null);

	        setContentPane(new MainPanel(this));
	        setVisible(true);
	    }
	    
	    public void changePanel(JPanel newPanel) {
	        setContentPane(newPanel);
	        revalidate();
	        repaint();
	    }

}

