package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import controller.GameManager;

import model.Robot;
import model.TransferRobot;
import model.DeleteRobot;
import model.MetaRobot;

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

private ImageIcon trans_icon; 
private ImageIcon delete_icon;
private ImageIcon meta_icon;

private Image trans_img;
private Image delete_img;
private Image meta_img;

public TransferRobot tbot;
public DeleteRobot dbot;
public MetaRobot mbot;

//private int[] total_price;
public ArrayList <Robot> selected_robots;

//GameManager 변수 선언
private GameManager gameManager;

	//파라미터에 GameManager 추가함
	public RobotPanel(GameManager gameManager) {
		this.gameManager = gameManager;
        setLayout(null);
        
        tbot = new TransferRobot("변환 로봇", "대기중인 물건의 색상을 랜덤 변경.", 1500);
        dbot = new DeleteRobot("삭제 로봇", "원하는 1칸 삭제 가능.", 2500);
        mbot = new MetaRobot("메타몽 로봇", "모든 색상과 매칭되는 무지개 아이템으로 변환.", 4000);
        selected_robots = new ArrayList<>();
        
        //연한 회색으로 배경색 지정
        setBackground(new Color(0xF2F2F2)); 
        
        //title - 로봇목록
        titleLabel = new JLabel("로봇 목록");
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        add(titleLabel);
       
        //manual
        trans_manual = new JLabel(tbot.getManual());
        delete_manual = new JLabel(dbot.getManual());
        meta_manual = new JLabel(mbot.getManual());
        
        trans_manual.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        delete_manual.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        meta_manual.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        
        add(trans_manual);
        add(delete_manual);
        add(meta_manual);
        
        
        //체크박스
        transferBot = new JCheckBox(tbot.getName());
        deleteBot = new JCheckBox(dbot.getName());
        metaBot = new JCheckBox(mbot.getName());
        
        JCheckBox[] CRobots = {transferBot, deleteBot, metaBot};
        Robot[] Robots = {tbot, dbot, mbot};
       
        
        for (int i = 0; i <Robots.length; i++) {
        	//익명 내부 클래스에서 사용
        	int idx = i;
            CRobots[i].addItemListener(new ItemListener(){
                public void itemStateChanged(ItemEvent e) {
                	//gameManager.setTotalCoin(CRobots[idx], Robots[idx], total_price);
                	gameManager.addSelectedRobots(CRobots[idx], Robots[idx], selected_robots);
                }
            }); 
            	
            CRobots[i].setFont(new Font("맑은 고딕", Font.BOLD, 14));
            add(CRobots[i]);
        }
        
        //=======================================================
        //버튼 생성하고 GameManager와 기능 연결하는 코드 추가
        //coin 버튼
        
        //1. 변환로봇
        trans_coin = new JButton(Integer.toString(tbot.getPrice()));
        trans_coin.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		gameManager.PrintRobot(tbot.getName() , tbot.getPrice());
        	}
        });
        
        //2.삭제 로봇
        delete_coin = new JButton(Integer.toString(dbot.getPrice()));
        delete_coin.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		gameManager.PrintRobot(dbot.getName() , dbot.getPrice());
        	}
        });
        
        //3.메타몽 로봇
        meta_coin = new JButton(Integer.toString(mbot.getPrice()));
        meta_coin.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		gameManager.PrintRobot(mbot.getName() , mbot.getPrice());
        	}
        });
        //======================================================
        add(trans_coin);
        add(delete_coin);
        add(meta_coin);
        
        //구매버튼
        buying = new JButton("구매하기");
        buying.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		gameManager.buyMain(selected_robots);
        		
        	}
        });
        add(buying);
        
        //이미지
        trans_icon = new ImageIcon(getClass().getResource("changebot.png"));
        delete_icon = new ImageIcon(getClass().getResource("deletebot.png"));
        meta_icon = new ImageIcon(getClass().getResource("metamongbot.png"));
        
        trans_img = trans_icon.getImage();
        delete_img = delete_icon.getImage();
        meta_img = meta_icon.getImage();
        
        
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
        
        int image_width = boxWidth / 4;
        int image_height = boxHeight * 6/10;
        
        
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
        int checkX = boxX+d;
        int checkY = boxY+d;
        //체크박스
        transferBot.setBounds(checkX, checkY, checkWidth, checkHeight);
        transferBot.setOpaque(false); //배경색 지우기
        
        //coin 버튼
        int coinWidth = boxWidth / 7;
        int coinHeight = coinWidth / 2;
        int lastx = boxX + boxWidth - d;
        int lasty = boxY + boxHeight - d;
        trans_coin.setBounds(lastx - coinWidth, lasty - coinHeight, coinWidth, coinHeight);
        trans_coin.setBackground(new Color(255, 250, 200));
        
        //changebot.png 이미지 그리기(크기 조정)
        if (trans_img != null) {
            g.drawImage(trans_img, boxX+d, boxY+(4*d), image_width, image_height, this);
        }
        
        //설명
        int xd = boxWidth / 3;
        int manualX = boxX + xd;
        int manualWidth = boxWidth * 75 / 100;
        int manualHeight = boxHeight / 3;
        int manualY = boxY + (boxHeight-manualHeight) / 2;
        trans_manual.setLocation(manualX, manualY);
        trans_manual.setSize(manualWidth, manualHeight);
        
        
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
        
        //deletebot.png 이미지 그리기(크기 조정)
        if (delete_img != null) {
            g.drawImage(delete_img, boxX+d, boxY + boxHeight + gap + (4*d), image_width, image_height, this);
        }
        
        //설명
        int del_manualY = boxY + boxHeight + gap + (boxHeight - manualHeight) / 2;
        delete_manual.setLocation(manualX, del_manualY);
        delete_manual.setSize(manualWidth, manualHeight);
        
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
        
        //metabot.png 이미지 그리기(크기 조정)
        if (meta_img != null) {
            g.drawImage(meta_img, boxX+d, boxY + (2 * boxHeight) + (4 * gap) + (2*d), image_width, image_height, this);
        }
        
        //구매버튼
        int dis = d * 4;
        int buying_y = metacoin +coinHeight + dis;
        int buying_width = coinWidth * 3 /2;
        int buying_height = coinHeight * 3 /2;
        buying.setBounds(lastx - buying_width, buying_y,  buying_width, buying_height);
        buying.setBackground(new Color(255, 228, 195));
        
        //설명
        int meta_manualY = boxY + 2 * boxHeight + 2 * gap + (boxHeight- manualHeight) / 2;
        meta_manual.setLocation(manualX, meta_manualY);
        meta_manual.setSize(manualWidth, manualHeight);
	}
	
	//GameManager가 이 메서드 호출해서 로봇 구매완료 시 버튼 상태 변경함.
    public void setPurchased(String robotName) {
    	switch(robotName) {
    	case "변환로봇":
    		trans_coin.setText("구매완료");
    		trans_coin.setEnabled(false); //구매하면 버튼 비활성화
    		break;
    	case "삭제로봇":
    		delete_coin.setText("구매완료");
    		delete_coin.setEnabled(false);
    		break;
    	case "메타몽로봇":
    		meta_coin.setText("구매완료");
    		meta_coin.setEnabled(false);
    		break;
    	}
        
	}
}












