package Gui;

import javax.swing.*;
import java.awt.*;

public class MyRobotPanel extends JPanel{
	
    private JLabel titleLabel;
    
    private RobotCardPanel convertCard;   //변환로봇
    private RobotCardPanel deleteCard;    //삭제로봇
    private RobotCardPanel metamongCard;  //메타몽로봇
	
	public MyRobotPanel() {
		
        setLayout(null);             
        setBackground(new Color(0xF2F2F2)); 

        titleLabel = new JLabel("내 로봇 사용하기");
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        add(titleLabel);
        
        //테스트 코드 (이후에 반드시 주석 처리)
        //convertCard = createCard("변환로봇", "changebot.png", 2, 0);
        //add(convertCard);

        //deleteCard = createCard("삭제로봇", "deletebot.png", 2, 1);
        //add(deleteCard);

        //metamongCard = createCard("메타몽로봇", "metamongbot.png", 2, 2);
        //add(metamongCard);
    }
	
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width  = getWidth();
        int height = getHeight();

        //"내 로봇" 글자 크기/위치
        int labelWidth  = width  * 4 / 10;  
        int labelHeight = height * 7 / 100;  
        int labelX      = width  / 20;
        int labelY      = height / 100;
        titleLabel.setLocation(labelX, labelY);
        titleLabel.setSize(labelWidth, labelHeight);

        //안쪽 흰색 영역 그리기
        Graphics2D g2 = (Graphics2D) g;
 
        int boxX   = width * 3 / 100;
        int boxY   = labelY + labelHeight + height / 100; 
        int boxWidth   = width  - boxX * 2;
        int boxHeight   = height - boxY - boxX;

        g2.setColor(Color.WHITE);
        g2.fillRect(boxX, boxY, boxWidth, boxHeight);

        g2.setColor(Color.LIGHT_GRAY);
        g2.drawRect(boxX, boxY, boxWidth, boxHeight);

        //로봇 하나를 담는 작은 박스
        int areaX = boxX;
        int areaY = boxY;
        int areaWidth  = boxWidth;
        int areaHeight = boxHeight;

        // 현재 보유한 카드 개수
        RobotCardPanel[] cards = {convertCard, deleteCard, metamongCard};
        int cardCount = 0;
        for (RobotCardPanel c : cards) {
            if (c != null) cardCount++;
        }

        // 배치할 카드가 있을 때만 계산
        if (cardCount > 0) {

            double gapRatio = 0.03;    // 카드 간 간격 비율
            int cardWidth  = (int)(areaWidth * (1.0 - gapRatio * (cardCount + 1)) / cardCount);
            int cardHeight = areaHeight * 85 / 100;

            int index = 0;
            for (RobotCardPanel c : cards) {
                if (c != null) {

                    int x = areaX 
                             + (int)(areaWidth * gapRatio) * (index + 1)
                             + cardWidth * index;

                    int y = areaY + areaHeight * 7 / 100;

                    c.setLocation(x, y);
                    c.setSize(cardWidth, cardHeight);
                    index++;
                }
            }
        }
    }
	
	 //로봇 구매 시 카드 추가
	 public boolean addRobot(String robotName, int count) {
		 RobotCardPanel targetCard = null;
		 int modeIndex = 0; //1:변환 , 2:삭제 , 3:메타몽

	        switch (robotName) {

	            case "변환로봇":
	                if (convertCard != null) return false;
	                convertCard = createCard(robotName, "changebot.png", count, 0);
	                add(convertCard);
	                targetCard = convertCard;
	                modeIndex = 1;
	                break;

	            case "삭제로봇":
	                if (deleteCard != null) return false;
	                deleteCard = createCard(robotName, "deletebot.png", count, 1);
	                add(deleteCard);
	                targetCard = deleteCard;
	                modeIndex = 2;
	                break;

	            case "메타몽로봇":
	                if (metamongCard != null) return false;
	                metamongCard = createCard(robotName, "metamongbot.png", count, 2);
	                add(metamongCard);
	                targetCard = metamongCard;
	                modeIndex = 3;
	                break;
	        }
	        //생성된 카드의 버튼에 실제 기능을 연결하는 부분
	        if (targetCard != null) {
	        	final int selectMode = modeIndex;
	        	targetCard.getUseButton().addActionListener(new java.awt.event.ActionListener(){
	        		public void actionPerformed(java.awt.event.ActionEvent e) {
	        			//GameManager에게 모드 변경 요청
	        			Frame parent = (Frame) SwingUtilities.getWindowAncestor(MyRobotPanel.this);
	        			if(parent != null) {
	        				if(selectMode == 1) {
	        					//변환 로봇은 맵 클릭이 아니라 즉시발동하도록 처리
	        					parent.getGameManager().handleConvertRobot();
	        				}else {
	        					//메타몽은 맵 클릭해야하므로 모드 변경
	        					parent.getGameManager().setRobotMode(selectMode);
	        				}
	        			}
	        		}
	        	});
	        }

	        repaint();
	        revalidate();
	        return true;
	    }
	 //아이템 사용시 화면 바뀌도록하는 메서드
	 public void useRobotOnce(String robotName) {
		 switch (robotName) {
		 case "변환로봇" : if(convertCard != null) convertCard.useOnce(); break;
		 case "삭제로봇": if(deleteCard != null) deleteCard.useOnce(); break;
         case "메타몽로봇": if(metamongCard != null) metamongCard.useOnce(); break;
     }
 }
	 //카드 생성 메소드
	 private RobotCardPanel createCard(String name, String imageName, int count, int index) {

	        ImageIcon icon = new ImageIcon(getClass().getResource(imageName));
	        RobotCardPanel card = new RobotCardPanel(name, icon, count);

	        return card;
	    }
	 
	 //남은 횟수 업데이트 (게임 로직에서 호출하는 용도. 다른 클래스에서 작성해도 될듯)
	 public void updateRobotCount(String robotName, int newCount) {
	        switch (robotName) {
	            case "변환로봇":
	                if (convertCard != null) convertCard.setCount(newCount);
	                break;
	            case "삭제로봇":
	                if (deleteCard != null) deleteCard.setCount(newCount);
	                break;
	            case "메타몽로봇":
	                if (metamongCard != null) metamongCard.setCount(newCount);
	                break;
	        }
	    }
	


	

}
