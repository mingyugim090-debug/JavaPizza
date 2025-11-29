package Gui;

import javax.swing.*;
import java.awt.*;

public class RobotPanel extends JPanel {
	
private JLabel titleLabel;

private JCheckBox transferBot;
private JCheckBox deleteBot;
private JCheckBox metaBot;

private JLabel trans_manual;
private JLabel delete_manual;
private JLabel meta_manual;


private JButton trans_coin;
private JButton delete_coin;
private JButton meta_coin;

private JButton buying;
	
	public RobotPanel() {
        setLayout(null);
        
        //연한 회색으로 배경색 지정
        setBackground(new Color(0xF2F2F2)); 
        
        //title - 로봇목록
        titleLabel = new JLabel("로봇 목록");
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        add(titleLabel);
        
        //manual
        trans_manual = new JLabel("대기중인 물건의 색상을 랜덤 변경.");
        
        //체크박스
        transferBot = new JCheckBox("변환 로봇");
        deleteBot = new JCheckBox("삭제 로봇");
        metaBot = new JCheckBox("메타몽 로봇");
        add(transferBot);
        add(deleteBot);
        add(metaBot);
        
        //coin 버튼
        trans_coin = new JButton("1500");
        delete_coin = new JButton("2500");
        meta_coin = new JButton("4000");
        add(trans_coin);
        add(delete_coin);
        add(meta_coin);
        
        //구매버튼
        buying = new JButton("구매하기");
        add(buying);
    }
	
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width  = getWidth();	//패널의 전체 가로길이
        int height = getHeight();	//패널의 전체 세로길이

        //"로봇 목록" 글자 크기/위치
        int labelWidth  = width  * 4 / 10;  
        int labelHeight = height * 7 / 100;  
        int labelX      = (int)(width  * 0.95 - labelWidth*2.25);
        int labelY      = height / 100; 
        titleLabel.setLocation(labelX, labelY);
        titleLabel.setSize(labelWidth, labelHeight);

        //Graphics2D g2 = (Graphics2D) g; <- 투명도 설정 등 고급기능 사용할때 주석해제
        
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
