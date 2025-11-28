package Gui;

import javax.swing.*;	
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel {
	
	private javax.swing.Timer timer;
	private int timeLeft = 60;
	
	private boolean gameOver = false;
	
	private Frame frame;
	
	private int stage;
	private int coins;
	private int score;
	
	private MapPanel mapPanel;
	private InformationPanel informationPanel;
	private JLabel logoLabel;
	private JLabel missionLabel; 
	private JLabel timerLabel;
	private DeliveryListPanel deliveryListPanel;
	private RobotPanel robotPanel;
	
	private JLabel gameOverLabel;
	private JPanel darkPanel;
	private JButton gameOverButton;
	
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
	        
	    
	    //DeliveryListPanel(배송목록)
	    deliveryListPanel = new DeliveryListPanel();
	   
	  //RobotPanel(로봇목록)
	    robotPanel = new RobotPanel();
	    
		//MapPanel
		mapPanel = new MapPanel(stage);
		
		//InformaionPanel
		informationPanel = new InformationPanel(stage, coins, score); 
		
		//미션 라벨
		missionLabel = new JLabel("최대한 빠르게 칸을 채우자!", SwingConstants.CENTER);
	    missionLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		
	    //타이머 라벨
	    timerLabel = new JLabel(" ", SwingConstants.CENTER);
	    timerLabel.setFont(new Font("맑은 고딕", Font.BOLD, 22));
	    timerLabel.setForeground(Color.DARK_GRAY);
	    
	    //GameOver라벨
	    gameOverLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
	    gameOverLabel.setFont(new Font("맑은 고딕", Font.BOLD, 80));
	    gameOverLabel.setForeground(Color.RED);
	    gameOverLabel.setVisible(false);
	    
	    //어두운 화면
	    darkPanel = new JPanel() {
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            Graphics2D g2 = (Graphics2D) g.create();
	            g2.setColor(new Color(0, 0, 0, 120)); // 알파값 120짜리 검정
	            g2.fillRect(0, 0, getWidth(), getHeight());
	            g2.dispose();
	        }
	    };
	    darkPanel.setOpaque(false);      // 투명 패널
	    darkPanel.setVisible(false);
	    
	    //gameover 시 메인으로 돌아가는 버튼
	    gameOverButton = new JButton("메인 화면으로"); //버튼 문구
	    gameOverButton.setBackground(Color.WHITE); //배경색
	    gameOverButton.setForeground(Color.BLACK); //글씨색
	    gameOverButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5)); //테두리
	    gameOverButton.setOpaque(true); //배경색 적용
	    gameOverButton.setFocusPainted(false);
	    gameOverButton.setFont(new Font("맑은 고딕", Font.BOLD, 20));
	    gameOverButton.setVisible(false);
	    
	    gameOverButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 if (timer != null) {
     	            timer.stop();
     	        }
     	        frame.showMainPanel();
            }
        });
	    
		add(logoLabel);
		add(deliveryListPanel);
		add(robotPanel);
		add(mapPanel);
		add(informationPanel);
		add(missionLabel);
		add(timerLabel);
		
		add(darkPanel);
		add(gameOverLabel);
		
		setComponentZOrder(gameOverButton, 0);
		setComponentZOrder(gameOverLabel, 1); //컨테이너 안에서 가장 맨 위로 오도록 설정
		setComponentZOrder(darkPanel, 2); //그 아래에는 darkPanel이 오도록
	}
	
	public void updateTimer(int time) {                      
	    timerLabel.setText("남은 시간 : " + time + "초");
	    
	    //time이 0이 되면 Game Over
	    if (time <= 0) {
	        gameOver = true;
	        gameOverLabel.setVisible(true);
	        
	        darkPanel.setVisible(true);
	        mapPanel.setGameOver(true); //mapPanel에 gameover 알림
	        gameOverButton.setVisible(true);
	        repaint(); 
	    }
	}
	
	//GameManager와 GamePanel이 연결되는 부분
	//GameManager가 맵을 제어할 수 있도록 MapPanel을 건네주는 통로역할
	 public MapPanel getMapPanel() {
		 return mapPanel;
	 }
	 //GameManager가 점수 올랐다고 알려주면 화면을 갱신하는 역할
	 public void updateScore(int score) {
		 if(informationPanel != null) {
			 informationPanel.setScore(score);	 
			 
		 }
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
        mapPanel.setLocation(mapX,mapY);
        mapPanel.setSize(mapSize,mapSize);
        
        //DeliveryListPanel 크기/위치
        int deliveryWidth  = (int)(width * 0.33);   
        int deliveryHeight = (int)(height * 0.75);  
        int deliveryX      = (int)(width * 0.03);   
        int deliveryY      = (int)(height * 0.06); ;               
        deliveryListPanel.setLocation(deliveryX, deliveryY);
        deliveryListPanel.setSize(deliveryWidth, deliveryHeight);
        
        //RobotPanel 크기/위치
        int robotWidth  = (int)(width * 0.33);   
        int robotHeight = (int)(height * 0.75);  
        int robotX      = (int)(width * 0.97 - robotWidth);   
        int robotY      = (int)(height * 0.06); ;               
        robotPanel.setLocation(robotX, robotY);
        robotPanel.setSize(robotWidth, robotHeight);

        //타이머 크기/위치
        int timerWidth = (int)(width * 0.2);
        int timerHeight = (int)(height * 0.05);
        int timerX = (width - timerWidth) / 2;
        int timerY = mapY + mapSize + 20;   // 맵 바로 밑에 20px 여백
        timerLabel.setLocation(timerX,timerY);
        timerLabel.setSize(timerWidth, timerHeight);

       
        //InformationPanel 크기/위치
        int infoWidth = (int)(width * 0.5);
        int infoHeight = 60;  
        int infoX = (width - infoWidth) / 2;
        int infoY = (int)(height * 0.85);
        informationPanel.setLocation(infoX,infoY);
        informationPanel.setSize(infoWidth, infoHeight);
        
        //gameover시 나오는 어두운 배경 크기/위치
        darkPanel.setLocation(0,0);
        darkPanel.setSize(width, height);
        
        //gameover 문구 
        int gameoverWidth = (int)(width * 0.8);
        int gameoverHeight = (int)(height * 0.15);
        int gameoverX = (width - gameoverWidth) / 2;
        int gameoverY = (height - gameoverHeight) / 2;
        gameOverLabel.setLocation(gameoverX,gameoverY);
        gameOverLabel.setSize(gameoverWidth, gameoverHeight);
        
        //gameover버튼 크기/위치
        int buttonWidth = 220;
        int buttonHeight = 50;
        int buttonX = (width - buttonWidth) / 2;
        int buttonY = gameoverY + gameoverHeight + 20;  //텍스트 아래 20px 띄움
        gameOverButton.setLocation(buttonX,buttonY);
        gameOverButton.setSize(buttonWidth, buttonHeight);
        
    
     }
    
    }
  


