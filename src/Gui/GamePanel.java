package Gui;

import javax.swing.*;		
import java.awt.*;
import java.awt.event.*;

import controller.GameManager;

public class GamePanel extends JPanel {
	
	private javax.swing.Timer timer;
	
	private GameManager gameManager;
	
	private boolean gameOver = false;
	
	
	private Frame frame;
	
//	private int stage;
//	private int coins;
//	private int score;
	
	private MapPanel mapPanel;
	private InformationPanel informationPanel;
	private Image logoImage;
	private JLabel missionLabel; 
	private JLabel timerLabel;
	private DeliveryListPanel deliveryListPanel;
	private RobotPanel robotPanel;
	private MyRobotPanel myRobotPanel;
	
	//Game Over가 되었을 때 뜨는 것들 그러나 문제가 있음!!
	//Game Over가 되는 경우는 두 가지. (맵을 다 채운 경우 / 타이머가 다 된 경우)
	//아래에 정의된 것들은 타이머가 다 되었을 경우에만 뜸
	private JLabel gameOverLabel;
	private JPanel darkPanel;
	private JButton gameOverButton;
	
	
	public GamePanel(Frame frame) {
		this.frame = frame;
		this.gameManager = frame.getGameManager();
		
		//선택적 참조 (나중에 다시 확인할 것 !!!!!!!!!!!!!!!!!!!!!!!!!)
//		this.stage = gameManager.getStage();
//		this.coins = gameManager.getCoins();
//		this.score = gameManager.getScore();
		
		setLayout(null); //배치관리자 없음(자유 배치)
		setBackground(new Color(0xEDEDED));
		
		//로고 이미지 불러오기
		logoImage = new ImageIcon(getClass().getResource("robohub_logo.png")).getImage();
	    
	    //DeliveryListPanel(배송 목록)
	    deliveryListPanel = new DeliveryListPanel();
	   
	    //RobotPanel(로봇 목록)
	    robotPanel = new RobotPanel(frame.getGameManager());
	    
	    //MyRobotPanel(내가 구매한 로봇이 뜨는 패널)
	    myRobotPanel = new MyRobotPanel();
	    
		//MapPanel(중앙 맵)
		mapPanel = new MapPanel(gameManager.getStage());
		
		//InformaionPanel(stage, coins, score을 표시하는 패널 / 하단 중앙 )
//		informationPanel = new InformationPanel(stage, coins, score); 
		informationPanel = new InformationPanel(gameManager.getStage(), gameManager.getCoins(), gameManager.getScore());
		
		//미션 라벨
		missionLabel = new JLabel("", SwingConstants.CENTER); //라벨 안에서 글자를 가로 방향으로 가운데 정렬
	    missionLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		
	    //타이머 라벨(stage 3에서만 / 하단 중앙)
	    timerLabel = new JLabel("", SwingConstants.CENTER);
	    timerLabel.setFont(new Font("맑은 고딕", Font.BOLD, 22));
	    timerLabel.setForeground(Color.RED);
	    
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
	            g2.setColor(new Color(0, 0, 0, 120)); 
	            g2.fillRect(0, 0, getWidth(), getHeight());
	            g2.dispose();
	        }
	    };
	    darkPanel.setOpaque(false);   
	    darkPanel.setVisible(false);
	    
	    //gameover 시 메인으로 돌아가는 버튼 (이거 뭔가 수정해야할 것 같음 왜냐하면 쓰이지가 않음)
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
	    
		add(deliveryListPanel);
		add(robotPanel);
		add(myRobotPanel);
		add(mapPanel);
		add(informationPanel);
		add(missionLabel);
		add(timerLabel);
		
		add(darkPanel);
		add(gameOverLabel);
		add(gameOverButton);
		
		setComponentZOrder(gameOverButton, 0);
		setComponentZOrder(gameOverLabel, 1); //컨테이너 안에서 가장 맨 위로 오도록 배치 설정
		setComponentZOrder(darkPanel, 2); //그 아래에는 darkPanel이 오도록
	}
	
	//타이머가 줄어들 때마다 실시간 호출되는 메소드
	public void updateTimer(int time) {  
		if (!timerLabel.isVisible()) {
			timerLabel.setVisible(true);
		}
	    timerLabel.setText("남은 시간 : " + time + "초");
	    
	    //time이 0이 되면 Game Over
	    if (time <= 0) {
	        gameOver = true;
	        
	        gameOverLabel.setVisible(true);
	        darkPanel.setVisible(true);
	        gameOverButton.setVisible(true);
	        
	        mapPanel.setGameOver(true); //mapPanel에 gameover 알림
	        repaint(); 
	    }
	}
	
    
   //GameManager가 RobotPanel에 접근하기 위한 연결고리 역할
    public RobotPanel getRobotPanel() {
        return robotPanel;
    }
    
//=======================GameManager와 연결되는 부분========================	
    
	//GameManager와 GamePanel이 연결되는 부분
	//GameManager가 맵을 제어할 수 있도록 MapPanel을 건네주는 통로역할
	 public MapPanel getMapPanel() {
		 return mapPanel;
	 }
	 public MyRobotPanel getMyRobotPanel() {
		 return myRobotPanel;
	 }
	 //GameManager가 점수 올랐다고 알려주면 화면을 갱신하는 역할
	 public void updateScore(int score) {
		 if(informationPanel != null) {
			 informationPanel.setScore(score);	 
		 }
	 }
	 public void updateCoins(int coins) {
		 if(informationPanel != null) {
			 informationPanel.setCoins(coins);
		 }
	 }
	 
	//위의 코드랑 중복되지만 이 메소드를 빼면 게임 진행이 되지 않음 (Gamemanager에서 호출하니까)
	    public void updateStage(int stage) {
	      
	        if (informationPanel != null) {
	            informationPanel.setStage(stage);
	        }
	        if (stage == 1 || stage == 2) {
//	            missionLabel.setText("최대한 많은 코인을 얻자!");
	        } else if (stage == 3) {
//	            missionLabel.setText("최고 점수를 향하여!");
	        }
	        if (timerLabel != null) {
	            timerLabel.setVisible(stage == 3);
	        }
	    }
	    
	 public void updateNextBlock(int color) {
		 if(deliveryListPanel != null) {
			 deliveryListPanel.setNextBlockColor(color);
		 }
	 }
	  
//=================================================================== 
	
	//GamePanel 위에 있는 요소들의 위치/크기 지정
    public void doLayout() {

        int width  = getWidth();
        int height = getHeight();

        //미션 문구 크기/위치
        int missionWidth = width * 4 / 10;
        int missionHeight = height * 5 / 100;
        int missionX = (width - missionWidth) / 2;
        int missionY = height * 1/5;
        missionLabel.setLocation(missionX,missionY);
        missionLabel.setSize(missionWidth,missionHeight);

        //맵 크기/위치
        int mapSize = Math.min(width * 2 / 10 , height * 4 / 10); //map은 정사각형이므로, 가로와 세로 길이 통합
        int mapX = (width - mapSize) / 2;
        int mapY = (height - mapSize) / 2;
        mapPanel.setLocation(mapX,mapY);
        mapPanel.setSize(mapSize,mapSize);
        
        //DeliveryListPanel "배송 목록" 크기/위치
        int deliveryWidth  = width * 33 / 100;   
        int deliveryHeight = height * 37 / 100;  
        int deliveryX      = width * 3 / 100;   
        int deliveryY      = height * 6 / 100;               
        deliveryListPanel.setLocation(deliveryX, deliveryY);
        deliveryListPanel.setSize(deliveryWidth, deliveryHeight);
        
        //RobotPanel "로봇 목록" 크기/위치
        int robotWidth  = width * 33 / 100;   
        int robotHeight = height * 75 / 100;  
        int robotX      = width * 97 / 100 - robotWidth;   
        int robotY      = height * 6 / 100;               
        robotPanel.setLocation(robotX, robotY);
        robotPanel.setSize(robotWidth, robotHeight);
        
        //MyRobotPanel "내 로봇 사용하기 " 패널 크기/위치
        int MyRobotWidth  = width * 33 / 100;   
        int MyRobotHeight = height * 35 / 100;  
        int MyRobotX      = width * 3 / 100;   
        int MyRobotY      = deliveryY + deliveryHeight + deliveryHeight * 8 / 100;               
        myRobotPanel.setLocation(MyRobotX, MyRobotY);
        myRobotPanel.setSize(MyRobotWidth, MyRobotHeight);

        //타이머 크기/위치
        int timerWidth = width * 2 / 10;
        int timerHeight = height * 5 / 100;
        int timerX = (width - timerWidth) / 2;
        int timerY = mapY + mapSize + 20;   // 맵 바로 밑에 20px 여백
        timerLabel.setLocation(timerX,timerY);
        timerLabel.setSize(timerWidth, timerHeight);
       
        //InformationPanel 크기/위치
        int infoWidth = width / 2;
        int infoHeight = 60;  
        int infoX = (width - infoWidth) / 2;
        int infoY = height * 85 / 100;
        informationPanel.setLocation(infoX,infoY);
        informationPanel.setSize(infoWidth, infoHeight);
        
        //gameover시 나오는 어두운 배경 크기/위치
        darkPanel.setLocation(0,0);
        darkPanel.setSize(width, height);
        
        //gameover 문구 
        int gameoverWidth = width * 8 / 10;
        int gameoverHeight = height * 15 / 100;
        int gameoverX = (width - gameoverWidth) / 2;
        int gameoverY = (height - gameoverHeight) / 2;
        gameOverLabel.setLocation(gameoverX,gameoverY);
        gameOverLabel.setSize(gameoverWidth, gameoverHeight);
        
        //gameover버튼(메인으로) 크기/위치
        int buttonWidth = 220;
        int buttonHeight = 50;
        int buttonX = (width - buttonWidth) / 2;
        int buttonY = gameoverY + gameoverHeight + 20;  //텍스트 아래 20px 띄움
        gameOverButton.setLocation(buttonX,buttonY);
        gameOverButton.setSize(buttonWidth, buttonHeight);
     }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        //로고 크기
        int logoWidth = width * 25 / 100;   
        int logoHeight = height * 9 / 100;   

        //로고 이미지 위치
        int logoX = (width - logoWidth) / 2;
        int logoY = height * 7 / 100;

        //이미지 크기 조절하여 그리기
        g2.drawImage(logoImage, logoX, logoY, logoWidth, logoHeight, this);
    }
}



