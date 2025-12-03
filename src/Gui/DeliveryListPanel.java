package Gui;

import javax.swing.*;
import java.awt.*;

public class DeliveryListPanel extends JPanel {
	
	private JLabel titleLabel; //"배송 목록"
	private int colorNumber = 0; //다음 블록색상
	
	public DeliveryListPanel() {
        setLayout(null);             
        setBackground(new Color(0xF2F2F2)); 

        titleLabel = new JLabel("배송 목록");
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        add(titleLabel);
    }
	
//==========GameManager --> GamePanel 거쳐서 호출되는 메서드================
	
	public void setNextBlockColor(int colorNumber) {
		this.colorNumber = colorNumber;
		repaint();
	}
	
//====================================================================
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

        int width  = getWidth();
        int height = getHeight();

        //"배송 목록" 글자 크기/위치
        int titlelabelWidth  = width  * 4 / 10;  
        int titlelabelHeight = height * 7 / 100;  
        int titlelabelX      = width  * 5 / 100;
        int titlelabelY      = height * 1 / 100; 
        titleLabel.setLocation(titlelabelX, titlelabelY);
        titleLabel.setSize(titlelabelWidth, titlelabelHeight);

        //안쪽 흰색 영역 그리기
        int whiteBoxX   = width * 3 / 100;
        int whiteBoxY   = titlelabelY + titlelabelHeight; 
        int whiteBoxWidth   = width  - whiteBoxX * 2;
        int whiteBoxHeight   = height - whiteBoxY - whiteBoxX;

        g.setColor(Color.WHITE);
        g.fillRect(whiteBoxX, whiteBoxY, whiteBoxWidth, whiteBoxHeight);

        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(whiteBoxX, whiteBoxY, whiteBoxWidth, whiteBoxHeight);
        
//======================다음 블록 그리기==================================  
        
        if (colorNumber != 0) {
            Color c = Color.GRAY;
            switch (colorNumber) {
                case 1: c = new Color(100, 149, 237); break; // 파랑
                case 2: c = new Color(255, 182, 193); break; // 분홍
                case 3: c = new Color(255, 255, 100); break; // 노랑
                case 4: c = new Color(144, 238, 144); break; // 초록
            }
            
            // 박스 중앙에 그리기
            int boxSize = whiteBoxHeight / 2;
            int boxX = whiteBoxX + (whiteBoxWidth - boxSize) / 2;
            int boxY = whiteBoxY + (whiteBoxHeight - boxSize) / 2;
     
            g.setColor(c);
            g.fillRect(boxX, boxY, boxSize, boxSize);
            g.setColor(Color.BLACK);
            g.drawRect(boxX, boxY, boxSize, boxSize);
        }
	}
}
//======================================================================