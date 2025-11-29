package Gui;

import javax.swing.*;
import java.awt.*;

public class DeliveryListPanel extends JPanel {
	
	private JLabel titleLabel;
	private int nextColor = 0; //다음 블록색상
	
	public DeliveryListPanel() {
        setLayout(null);             
        setBackground(new Color(0xF2F2F2)); 

        titleLabel = new JLabel("배송 목록");
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        add(titleLabel);
    }
//==========GameManager --> GamePanel 거쳐서 호출되는 메서드==============
	public void setNextBlockColor(int color) {
		this.nextColor = color;
		repaint();
	}
//====================================================================
	
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width  = getWidth();
        int height = getHeight();

        //"배송 목록" 글자 크기/위치
        int labelWidth  = width  * 4 / 10;  
        int labelHeight = height * 7 / 100;  
        int labelX      = width  * 5 / 100;
        int labelY      = height * 1 / 100; 
        titleLabel.setLocation(labelX, labelY);
        titleLabel.setSize(labelWidth, labelHeight);

        //안쪽 흰색 영역 그리기
        Graphics2D g2 = (Graphics2D) g;
 
        int boxX   = width * 3 / 100;
        int boxY   = labelY + labelHeight + height * 1 / 100; 
        int boxWidth   = width  - boxX * 2;
        int boxHeight   = height - boxY - boxX;

        g2.setColor(Color.WHITE);
        g2.fillRect(boxX, boxY, boxWidth, boxHeight);

        g2.setColor(Color.LIGHT_GRAY);
        g2.drawRect(boxX, boxY, boxWidth, boxHeight);
        
//======================다음 블록 그리기 ==================================  
        if (nextColor != 0) {
            Color c = Color.GRAY;
            switch (nextColor) {
                case 1: c = new Color(100, 149, 237); break; // 파랑
                case 2: c = new Color(255, 182, 193); break; // 분홍
                case 3: c = new Color(255, 255, 100); break; // 노랑
                case 4: c = new Color(144, 238, 144); break; // 초록
            }
            
            // 박스 중앙에 그리기
            int blockSize = Math.min(boxWidth, boxHeight) / 2;
            int blockX = boxX + (boxWidth - blockSize) / 2;
            int blockY = boxY + (boxHeight - blockSize) / 2;
            
            g2.setColor(c);
            g2.fillRect(blockX, blockY, blockSize, blockSize);
            g2.setColor(Color.BLACK);
            g2.drawRect(blockX, blockY, blockSize, blockSize);
        }
	}
}
//======================================================================