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
        
        int labelX      = (width  * 95 / 100) - (labelWidth*225 / 100);
        int labelY      = height * 1 / 100; 

        titleLabel.setLocation(labelX, labelY);
        titleLabel.setSize(labelWidth, labelHeight);

        //Graphics2D g2 = (Graphics2D) g; <- 투명도 설정 등 고급기능 사용할때 주석해제
        
        int margin = width * 3 / 100;
        int boxWidth   = width  - margin * 2;
        int boxX   = (width * 97 / 100) - boxWidth;
        int boxY   = labelY + labelHeight + (height * 1 / 100); 
        int fullboxHeight   = (height - boxY - margin);
        int loss = boxWidth / 6;
        
        int gap = fullboxHeight / 33;
        int boxHeight = ((fullboxHeight-loss) / 3) - (2 * gap / 3); 
        
        
        //1) 변환로봇
        //외곽선 그리기(LIGHT GRAY)
        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(boxX, boxY, boxWidth, boxHeight);
        //안쪽 흰색으로 채우기(WHITE)
        g.setColor(Color.WHITE);
        g.fillRect(boxX, boxY, boxWidth, boxHeight);
        
        //체크박스 위치
        int d = boxHeight / 14; 	//흰색 박스와의 거리
        int checkWidth = 10 * d;
        int checkHeight = 2 * d;
        //체크박스
        transferBot.setBounds(boxX+d, boxY+d, checkWidth, checkHeight);
        transferBot.setOpaque(false); //배경색 지우기
        
        //coin 버튼
        int coinWidth = boxWidth / 7;
        int coinHeight = coinWidth / 2;
        int lastx = boxX + boxWidth - d;
        int lasty = boxY + boxHeight - d;
        trans_coin.setBounds(lastx - coinWidth, lasty - coinHeight, coinWidth, coinHeight);
        trans_coin.setBackground(new Color(255, 250, 200));
       
        
        //2) 삭제로봇
        //외곽선 그리기(LIGHT GRAY)
        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(boxX, boxY + boxHeight + gap, boxWidth, boxHeight);
        //안쪽 흰색으로 채우기(WHITE)
        g.setColor(Color.WHITE);
        g.fillRect(boxX, boxY + boxHeight + gap, boxWidth, boxHeight);
        //체크박스
        deleteBot.setBounds(boxX+d, boxY + boxHeight + gap+d, checkWidth, checkHeight);
        deleteBot.setOpaque(false); //배경색 지우기
        
        //coin 버튼
        int diff =  boxY + 2 * boxHeight + gap - d - coinHeight;
        delete_coin.setBounds(lastx - coinWidth, diff, coinWidth, coinHeight);
        delete_coin.setBackground(new Color(255, 250, 200));
        
        //3) 메타몽로봇
        //외곽선 그리기(LIGHT GRAY)
        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(boxX, boxY + (2 * boxHeight) + (2 * gap), boxWidth, boxHeight);
        //안쪽 흰색으로 채우기(WHITE)
        g.setColor(Color.WHITE);
        g.fillRect(boxX, boxY + (2 * boxHeight) + (2 * gap), boxWidth, boxHeight);
        
        //체크박스
        metaBot.setBounds(boxX+d, boxY + (2 * boxHeight) + (2 * gap) +d, checkWidth, checkHeight);
        metaBot.setOpaque(false); //배경색 지우기
        
        //coin 버튼
        int metacoin = boxY + (2 * boxHeight) + (2 * gap) + boxHeight - d - coinHeight;
        meta_coin.setBounds(lastx - coinWidth, metacoin, coinWidth, coinHeight);
        meta_coin.setBackground(new Color(255, 250, 200));
        
        //구매버튼
        //int buying_height = robotY - d - coinHeight;
        
        //나중에 여기 안에 박스 그림을 추가하면 됨 (지금은 빈 영역만)
	}
}