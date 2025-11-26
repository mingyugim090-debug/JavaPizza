package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel {
	
	private Frame frame;
	
	private int stage;
	private int coins;
	private int score;
	
	private MapPanel mapPanel;
	private InformationPanel informationPanel;
	private JLabel logoLabel;
	private JLabel missionLabel; //ex."배송해야 할 물건의 수 : 5"
	private JLabel timerLabel;
	
	public GamePanel(Frame frame, int stage, int coins, int score) {
		this.frame = frame;
		this.stage = stage;
		this.coins = coins;
		this.score = score;
		
		setLayout(null); //배치관리자 없음
		setBackground(new Color(0xEDEDED));
		
		//로고
	    ImageIcon logo = new ImageIcon(getClass().getResource("robohub_logo1.png"));
	    logoLabel = new JLabel(logo);
	        
		//MapPanel
		mapPanel = new MapPanel(stage);
		
		//InformaionPanel
		informationPanel = new InformationPanel(stage, coins, score); 
		
		//미션 라벨
		missionLabel = new JLabel("최대한 빠르게 칸을 채우자!", SwingConstants.CENTER);
	    missionLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		
	    //타이머 라벨
	    timerLabel = new JLabel("Time : ", SwingConstants.CENTER);
	    timerLabel.setFont(new Font("맑은 고딕", Font.BOLD, 22));
	    timerLabel.setForeground(Color.DARK_GRAY);
	    
		add(logoLabel);
		add(mapPanel);
		add(informationPanel);
		add(missionLabel);
		add(timerLabel);
		
	}
	
	public void updateTimer(int time) {                      
	    timerLabel.setText("남은 시간 : " + time + "초");   
	}
	  
	//GamePanel 위에 있는 요소들의 위치/크기 지정
    public void doLayout() {

        int width  = getWidth();
        int height = getHeight();

        //로고 크기/위치
        int logoWidth = logoLabel.getPreferredSize().width;
        int logoHeight = logoLabel.getPreferredSize().height;
        int logoX = (width - logoWidth) / 2;
        int logoY = (int)(height * 0.05);
      
        logoLabel.setLocation(logoX,logoY);
        logoLabel.setSize(logoWidth,logoHeight);

        //미션 문구 크기/위치
        int missionWidth = (int)(width * 0.4);
        int missionHeight = (int)(height * 0.05);
        int missionX = (width - missionWidth) / 2;
        int missionY = logoY + logoHeight + 10;
       
        missionLabel.setLocation(missionX,missionY);
        missionLabel.setSize(missionWidth,missionHeight);

        //맵 크기/위치
        int mapSize = (int)Math.min(width * 0.25, height * 0.4);
        int mapX = (width - mapSize) / 2;
        int mapY = missionY + missionHeight + 20;
        mapPanel.setBounds(mapX, mapY, mapSize, mapSize);

        int timerWidth = (int)(width * 0.2);
        int timerHeight = (int)(height * 0.05);
        int timerX = (width - timerWidth) / 2;
        int timerY = mapY + mapSize + 20;   // 맵 바로 밑에 20px 여백
        timerLabel.setBounds(timerX, timerY, timerWidth, timerHeight);
       
        //InformationPanel 크기/위치
        int infoWidth = (int)(width * 0.5);
        int infoHeight = 60;  
        int infoX = (width - infoWidth) / 2;
        int infoY = (int)(height * 0.85);
        informationPanel.setBounds(infoX, infoY, infoWidth, infoHeight);
    }
}



