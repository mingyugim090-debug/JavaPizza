package Gui;

import javax.swing.*;
import java.awt.*;

public class DeliveryListPanel extends JPanel {
	
	private JLabel titleLabel;
	
	public DeliveryListPanel() {
        setLayout(null);             
        setBackground(new Color(0xF2F2F2)); 

        titleLabel = new JLabel("배송 목록");
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        add(titleLabel);
    }
	
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width  = getWidth();
        int height = getHeight();

        //"배송 목록" 글자 크기/위치
        int labelWidth  = (int)(width  * 0.4);  
        int labelHeight = (int)(height * 0.07);  
        int labelX      = (int)(width  * 0.05);
        int labelY      = (int)(height * 0.01); 

        titleLabel.setLocation(labelX, labelY);
        titleLabel.setSize(labelWidth, labelHeight);

        //안쪽 흰색 영역 그리기
        Graphics2D g2 = (Graphics2D) g;
 
        int boxX   = (int)(width * 0.03);
        int boxY   = labelY + labelHeight + (int)(height * 0.01); 
        int boxWidth   = width  - boxX * 2;
        int boxHeight   = height - boxY - boxX;

        g2.setColor(Color.WHITE);
        g2.fillRect(boxX, boxY, boxWidth, boxHeight);

        g2.setColor(Color.LIGHT_GRAY);
        g2.drawRect(boxX, boxY, boxWidth, boxHeight);

        //나중에 여기 안에 박스 그림을 추가하면 됨 (지금은 빈 영역만)
    }

}
