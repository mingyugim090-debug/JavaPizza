package Gui;

import javax.swing.*;
import java.awt.*;

public class RobotPanel extends JPanel {
	
private JLabel titleLabel;
	
	public RobotPanel() {
        setLayout(null);             
        setBackground(new Color(0xF2F2F2)); 

        titleLabel = new JLabel("로봇 목록");
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        add(titleLabel);
    }
	
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width  = getWidth();
        int height = getHeight();

        //"로봇 목록" 글자 크기/위치
        int labelWidth  = width  * 4 / 10;  
        int labelHeight = height * 7 / 100;  
        int labelX      = (int)(width  * 0.95 - labelWidth*2.25);
        int labelY      = height / 100; 
        titleLabel.setLocation(labelX, labelY);
        titleLabel.setSize(labelWidth, labelHeight);

        //안쪽 흰색 영역 그리기
        Graphics2D g2 = (Graphics2D) g;
        
        int margin = width * 3 / 100;
        int boxWidth   = width  - margin * 2;
        int boxX   = width * 97 / 100 - boxWidth;
        int boxY   = labelY + labelHeight + height / 100; 
        int boxHeight   = height - boxY - margin;

        g2.setColor(Color.WHITE);
        g2.fillRect(boxX, boxY, boxWidth, boxHeight);

        g2.setColor(Color.LIGHT_GRAY);
        g2.drawRect(boxX, boxY, boxWidth, boxHeight);
    }
}
