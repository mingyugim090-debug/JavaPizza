package Gui;

import javax.swing.*;
import java.awt.*;
import model.Robot;

public class MyRobotPanel extends JPanel{
	
    private JLabel titleLabel;
    
    private RobotCardPanel transCard;   //변환로봇
    private RobotCardPanel deleteCard;    //삭제로봇
    private RobotCardPanel metaCard;  //메타몽로봇
	
	public MyRobotPanel() {
		
        setLayout(null);             
        setBackground(new Color(0xF2F2F2)); 

        titleLabel = new JLabel("내 로봇 사용하기");
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        add(titleLabel);
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
        
        placeCards();
        }

	 //로봇 구매 시 카드 추가
	 public boolean addRobot(String name, int count) {
		 RobotCardPanel targetCard = null;
		 int modeIndex = 0; //1:변환 , 2:삭제 , 3:메타몽

	        switch (name) {
	            case "변환 로봇":
	                if (transCard != null) {
	                	transCard.setCount(transCard.getCount() + count);
	                	repaint();
	                	return true;
	                }
	                	
	                transCard = createCard(name, "changebot.png", count, 0);
	                add(transCard);
	                targetCard = transCard;
	                modeIndex = 1;
	                break;

	            case "삭제 로봇":
	                if (deleteCard != null) {
	                	deleteCard.setCount(deleteCard.getCount()+count);
	                    repaint();
	                    return true;
	                }
	                deleteCard = createCard(name, "deletebot.png", count, 1);
	                add(deleteCard);
	                targetCard = deleteCard;
	                modeIndex = 2;
	                break;

	            case "메타몽 로봇":
	                if (metaCard != null) {
	                	metaCard.setCount(metaCard.getCount() + count);
	                	repaint();
	                	return true;
	                }
	                metaCard = createCard(name, "metamongbot.png", count, 2);
	                add(metaCard);
	                targetCard = metaCard;
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
            placeCards();
	        repaint();
	        revalidate();
	        return true;
	    }
	 //GameManager에서 저장된 로봇 정보를 받아와 화면에 다시 띄우는 메서드
	 public void restoreRobots(int[] counts) {
		 if(counts[0] != -1) {
			 if(transCard == null) addRobot("변환 로봇" , counts[0]);
			 else transCard.setCount(counts[0]);
		 }
		 if(counts[1] != -1) {
			 if (deleteCard == null) addRobot("삭제 로봇" , counts[1]);
			 else deleteCard.setCount(counts[1]);
		 }
		 if(counts[2] != -1) {
			 if (metaCard == null) addRobot("메타몽 로봇" , counts[2]);
			 else metaCard.setCount(counts[2]);
		 }
		 repaint();
	 }
	 
	 //아이템 사용시 화면 바뀌도록하는 메서드
	 public void useRobotOnce(String robotName) {
		 switch (robotName) {
		 case "변환 로봇" : if(transCard != null) transCard.useOnce(); break;
		 case "삭제 로봇": if(deleteCard != null) deleteCard.useOnce(); break;
         case "메타몽 로봇": if(metaCard != null) metaCard.useOnce(); break;
     }
 }
	 //카드 생성 메소드
	 private RobotCardPanel createCard(String name, String imageName, int count, int index) {

	        ImageIcon icon = new ImageIcon(getClass().getResource(imageName));
	        RobotCardPanel card = new RobotCardPanel(name, icon, count);

	        return card;
	    }

	 //내 보유 로봇 카드 고정 위치
	 private void placeCards() {

		    int width = getWidth();
		    int height = getHeight();

		    int cardW = width / 4;   
		    int cardH = height * 2 / 3;   
		    int y = height / 2 - cardH / 2 + 2;  

		    //card 위치 3개 고정
		    int card1X = width / 10;
		    int card2X = width / 2 - cardW / 2;
		    int card3X = width - width / 10 - cardW;

		    if (transCard != null) {
		        transCard.setLocation(card1X, y);
		        transCard.setSize(cardW, cardH);
		    }
		    if (deleteCard != null) {
		        deleteCard.setLocation(card2X, y);
		        deleteCard.setSize(cardW, cardH);
		    }
		    if (metaCard != null) {
		        metaCard.setLocation(card3X, y);
		        metaCard.setSize(cardW, cardH);
		    }
		}
}
