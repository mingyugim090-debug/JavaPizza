package controller;
/**
 * [GameManager 클래스 역할]
 * 게임의 전반적인 규칙,데이터,흐름 총괄 
 * 맵 데이터 관리
 * 점수 및 코인 시스템 관리
 * GUI 패키지의 클래스들과의 연결고리 역할
 */

//======================[패키지 임포트]===================================
import javax.swing.JOptionPane;	
import javax.swing.JCheckBox;
import javax.swing.SwingUtilities;

import java.util.Random; 
import java.util.ArrayList;

import Gui.Frame;               
import Gui.GamePanel;
import util.TimeThread;
import util.FileManager;
import model.Robot;

public class GameManager {  
	
//======================[멤버 변수]======================================
//게임 운영에 필요한 모든 상태값 변수를 private으로 선언하여 캡슐화
    
	//UI 관련 변수
	private Frame frame; // 화면전환(Intro <-> Game <-> Main)을 위해 필요
    private GamePanel gamePanel;  //실제 게임이 진행되는 패널
    private TimeThread timer;     //Stage3에 사용되는 제한시간 타이머스레드
    private FileManager fileManager; //랭킹저장 , 파일 입출력담당
     
    
    //게임 상태 변수
    private int score = 0;  //처음 점수 0점에서 시작
    private int coins = 0;  //처음 코인 0원으로 시작
    private int stage = 1; // 현재 스테이지
    private int gridSize = 4;     // 맵 크기 (1단계:4x4, 2단계:3x3 , 3단계 : 5X5)
    private boolean isGameRunning = false; // 게임 진행상태 (false : 종료 , true : 진행)
    private boolean firstStart = true; //처음 시작인가?
    
    
    // 맵 데이터 (0: 빈칸,1:파랑,2:분홍,3:노랑,4:초록,5:보라(메타몽))
    private int[][] mapData; 
    private int colorNumber;      //다음에 놓을 블록 생상(1~4 랜덤으로 생성)
    private Random random = new Random();
    
    //로봇 스킬 관련 (-1 : 미구매 , 0: 소진 , >0 : 보유)
    private int robotMode = 0;
    private int[] myRobotCounts = {-1,-1,-1};
    

    
//======================[생성자 및 초기화]====================================    
    
    public GameManager(Frame frame) {
        this.frame = frame;
        this.fileManager = new FileManager();
    }
    
    //화면이 전환될 때 GamePanel을 연결하고 로봇 정보 복구하는 메서드
    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        if(this.gamePanel != null && this.gamePanel.getMyRobotPanel()!=null) {
        	this.gamePanel.getMyRobotPanel().restoreRobots(myRobotCounts);
        }
    }
     
//========================[게임 흐름 제어]===========================
    
    //게임 시작 및 스테이지 초기화 메서드
    public void initGame() {
        if (firstStart) {  //1단계 시작 시 점수/코인 0으로 설정
            score = 0;
            coins = 0;
            firstStart = false;
        }
        this.isGameRunning = true;
        
        //스테이지 별 맵 크기 설정
        if (stage == 1) gridSize = 4; 
        else if (stage == 2) gridSize = 3;
        else gridSize = 5;
        
        //데이터 초기화
        mapData = new int[gridSize][gridSize];
        generateNextBlock(); //첫번 째 랜덤 박스 생성
        
        //UI 갱신 요청
        
        gamePanel.getMapPanel().setStage(stage);
        gamePanel.getMyRobotPanel().restoreRobots(myRobotCounts);
            
        gamePanel.updateScore(score);
        gamePanel.updateCoins(coins);
        gamePanel.updateNextBlock(colorNumber);
        
        
        //타이머 설정 : Stage3에서만 60초 타이머 시작
        if (timer != null) {
        	timer.stopTimer(); 
            timer = null;
        }
        if(stage == 3) {
        	timer = new TimeThread(this, 60);    
        	timer.start();
        } 
        frame.repaint();
        }
    
    //게임 오버 처리
    public void gameOver() {
        isGameRunning = false;
        if (timer != null) {
        	timer.stopTimer();
        	timer = null;
        }
        
        SwingUtilities.invokeLater(()->{
        if(gamePanel != null) gamePanel.showGameOver();
        frame.repaint();
       
        String name = JOptionPane.showInputDialog(frame, 
            "Game Over! \n점수: " + score + "\n이름을 입력하세요");
        if (name != null && !name.trim().isEmpty()) {
            fileManager.rank(score, name); 
        }
        
        //게임 초기화(재시작할 때)
        stage = 1;
        firstStart = true;
        myRobotCounts[0] = -1;
        myRobotCounts[1] = -1;
        myRobotCounts[2] = -1;
        //메인 화면으로 복귀
        frame.showMainPanel();
    });
    }

//=====================[데이터 관리 & 판정]========================
    //다음 블록 생성
    private void generateNextBlock() {
    	colorNumber = random.nextInt(4) + 1;
    	if(gamePanel != null) gamePanel.updateNextBlock(colorNumber);
    }
    
    //점수 및 코인 획득 메서드
    public void addScore(int addScore , int addCoins) {
    	if (stage ==3) addCoins=0;
    	
        this.score += addScore;
        this.coins += addCoins;
        
        //  화면갱신
        
        gamePanel.updateScore(this.score);
        gamePanel.updateCoins(this.coins);
        gamePanel.updateStage(stage);
       
    }
    
  //타이머 시간 갱신(TimeThread에서 호출)
    public void updateTime(int time) {
    	SwingUtilities.invokeLater(()-> {
            if(gamePanel != null) gamePanel.updateTimer(time);
            if(time<=0) gameOver();
        });
    }

    
// ======================[상호작용 및 판정]===========================
    //사용자가 맵을 클릭했을 때 호출되는 핵심 로직
    //사용자가 맵의 (row, col) 위치에 물건을 놓았을 때 MapPanel에서 호출하는 메서드 
    public void handleMapAction(int row, int col) {
        if (!isGameRunning) return;
        if (row < 0 || col < 0 || row >= gridSize || col >= gridSize) return;
        
        // --- 로봇 스킬 모드일 경우 따른 분류 ---
        //1. 삭제 로봇 : 클릭한 로봇 제거
        if (robotMode == 2) {
            if (mapData[row][col] != 0) {
                mapData[row][col] = 0; // 블록 삭제
                useRobotItem("삭제 로봇"); // 사용횟수 차감
                frame.repaint();
            }
            return; 
        }
        
        // 메타몽 로봇 : 빈칸에 만능 블록(보라색) 배치
        if (robotMode == 3) { 
            if (mapData[row][col] == 0) {
                mapData[row][col] = 5; //5번 : 보라색
                useRobotItem("메타몽 로봇");
                checkLineMatch(row, col);
                generateNextBlock();
                frame.repaint();
                
                if(stage < 3) checkStageClear();
                else checkGameOverStage3();
            }
            return;
        }
        
        //기본 모드 : 블록배치
        if (mapData[row][col] != 0) return;
        
        // 맵이 현재 대기 중인 블록 배치
        mapData[row][col] = colorNumber;
        addScore(0,50);           // 기본 적재 보상(+50 coins)
        checkLineMatch(row,col);  //라인 매칭(가로/세로)확인
        generateNextBlock();      //화면 갱신
        frame.repaint();          //화면 갱신
        
        //스테이지 클리어 확인(Stage1 , Stage2만 해당)
        if (stage < 3) checkStageClear();
        else checkGameOverStage3(); // Stage3는 꽉 차면 게임 오버
        }
    
    
    //라인 매칭 알고리즘
    private void checkLineMatch(int row , int col) {
    	
    	//가로 줄 검사
    	boolean rowMatch = true;
    	int targetColor = -1;
    	for (int c=0; c < gridSize; c++) {
    		if(mapData[row][c] != 0 && mapData[row][c] !=5) {
    		    targetColor = mapData[row][c];
    		    break;
            }
        }
    	
    	//라인 매칭 판단
    	if (targetColor == -1) { // 모두 0이거나 5인 경우
            for (int c = 0; c < gridSize; c++) if (mapData[row][c] == 0) rowMatch = false;
        } else {
            for (int c = 0; c < gridSize; c++) if (mapData[row][c] != targetColor && mapData[row][c] != 5) rowMatch = false;
        }
        
    	//세로 줄 검사
        boolean colMatch = true;
        int targetColColor = -1;
       for (int r = 0; r < gridSize; r++) {
    	   if (mapData[r][col] != 0 && mapData[r][col] != 5) {
               targetColColor = mapData[r][col];
               break;
           }
       }

        // 세로 줄 매칭 판단
       if (targetColColor == -1) {
           for (int r = 0; r < gridSize; r++) if (mapData[r][col] == 0) colMatch = false;
       } else {
           for (int r = 0; r < gridSize; r++) if (mapData[r][col] != targetColColor && mapData[r][col] != 5) colMatch = false;
       }
       
    	//보상 지급 및 라인처리
    	if (rowMatch) processLineClear(true, row);
    	if (colMatch) processLineClear(false, col);
    	
    }
    
    //라인 완성 시 보상 및 처리
    private void processLineClear(boolean isRow , int index) {
    	addScore(300,1500); //보상: +1000코인 , + 300 점수
    	
    	//Stage3인 경우 줄 삭제하는 로직(애니팡과 비슷한 느낌)
    	if (stage == 3) {
    		if (isRow) for(int c = 0; c < gridSize; c++) mapData[index][c] = 0;
    		else for (int r = 0 ; r < gridSize; r++) mapData[r][index] = 0;
    		}
    	}
    
    
    // 스테이지 1,2 클리어 조건(맵이 꽉 차면 다음 스테이지로 이동)
    private void checkStageClear() {
        int count = 0;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (mapData[i][j] != 0) count++;
            }
        }
        if (count == gridSize * gridSize) {
        	if (timer != null) {
                timer.stopTimer();	
        	}
            JOptionPane.showMessageDialog(frame, "Stage " + stage + " Clear ! \n 다음단계로 이동합니다.");
            
            if (stage < 3) {
                stage++;
                frame.showStageIntro();
                initGame(); // 다음 스테이지로 초기화 및 시작
            }
        }
    }
    //stage3 게임 오버 판정(맵이 꽉 차면 종료)
    private void checkGameOverStage3() {
    	int count = 0;
    	for(int i = 0; i < gridSize; i++) {
    		for(int j = 0; j < gridSize; j++) {
    			if(mapData[i][j] != 0) count++;
    		}
    	}
    	if (count == gridSize * gridSize) {
    		gameOver();
    	}
    }
    
    
    
//=======================[로봇 스킬 & 상점]=======================================    
    //로봇 사용 모드 설정(0:기본,1:변환,2:삭제,3:메타몽)
    public void setRobotMode(int robotmode) {
    	this.robotMode = robotmode;
    	if(robotmode != 0) JOptionPane.showMessageDialog(frame, "로봇스킬 활성화!");
    }
    
    //로봇 아이템 사용 완료 후 차감되고 , 기본로봇으로 복귀
    public void useRobotItem(String robotName) {
    	gamePanel.getMyRobotPanel().useRobotOnce(robotName);
    	if (robotName.equals("변환 로봇")) myRobotCounts[0]--;
    	else if (robotName.equals("삭제 로봇")) myRobotCounts[1]--;
    	else if (robotName.equals("메타몽 로봇")) myRobotCounts[2]--;
    	this.robotMode = 0; 
    }
    //변환 로봇 기능 : 다음 블록 색상을 즉시 랜덤 변경
    public void handleConvertRobot() {
        generateNextBlock(); 
        frame.repaint();
        useRobotItem("변환 로봇"); 
    }
	    
	    //체크박스 선택된 robot 목록(ArrayList) 반환
	    public void addSelectedRobots(JCheckBox C_robot, Robot robot, ArrayList<Robot> selected_robots) {
	    	if(C_robot.isSelected()) {
	    		if (!selected_robots.contains(robot)) selected_robots.add(robot);   // 중복 방지
	    	} else selected_robots.remove(robot); 
	    }

	    
	  //장바구니 일괄 구매
	    public void buyMain(ArrayList<Robot> selected_robots, int total_price) {
	    	if(selected_robots.isEmpty()) {
	    		JOptionPane.showMessageDialog(frame, "구매할 로봇을 선택해주세요");
	    		return;
	    	}
	    	
	    		int result = JOptionPane.showConfirmDialog(frame, "구매하시겠습니까?", "Confirm", JOptionPane.YES_NO_OPTION);
	    		if(result == JOptionPane.YES_OPTION) {
	    			if(coins < total_price) {
	    			    JOptionPane.showMessageDialog(frame, "코인 부족");
	    			}else {
	    				coins -= total_price;
	    				addScore(0,0);
	    				for (Robot robot : selected_robots) {
	                        String name = robot.getName();
	                        boolean success = gamePanel.getMyRobotPanel().addRobot(name, 2);
	                        if (success) {
	                            gamePanel.getRobotPanel().setPurchased(name);
	                            saveRobotPurchase(name);
	                        }
	                    }
	                }
	            } else {
	                JOptionPane.showMessageDialog(frame, "구매 취소/실패");
	            }
	            selected_robots.clear();
	        }
	    
 public void buyRobot(String robotName, int total_price) {
    	  boolean isAlreadyBought = false;
    	    if (robotName.equals("변환 로봇") && myRobotCounts[0] != -1) isAlreadyBought = true;
    	    else if (robotName.equals("삭제 로봇") && myRobotCounts[1] != -1) isAlreadyBought = true;
    	    else if (robotName.equals("메타몽 로봇") && myRobotCounts[2] != -1) isAlreadyBought = true;

    	    if (isAlreadyBought) {
    	        JOptionPane.showMessageDialog(frame, "구매 불가");
    	        return;
    	    }
    	  
    	if (coins >= total_price) {
    		boolean success = gamePanel.getMyRobotPanel().addRobot(robotName, 2);
    		
    		if(success) {
    			coins -= total_price;
    			addScore(0,0);
    			gamePanel.getRobotPanel().setPurchased(robotName);
    			saveRobotPurchase(robotName);
             }
    	
    	}else {
    		    JOptionPane.showMessageDialog(frame, "코인부족"); 
    	}
      }  	
	  
	  //위의 두 메서드 코드에서 중복되는 부분 따로 정리
	  private void saveRobotPurchase(String robotName) {
	        if (robotName.equals("변환 로봇")) myRobotCounts[0] = 2;
	        else if (robotName.equals("삭제 로봇")) myRobotCounts[1] = 2;
	        else if (robotName.equals("메타몽 로봇")) myRobotCounts[2] = 2;
	    }
	  
	  public void PrintRobot(String robotName, int price) {
		  String message = robotName + "은" + price + "coin 입니다.";
		  JOptionPane.showMessageDialog(frame, message);
				  
	  }
	 
	  
	  
	  

//=======================[Getters]==================
public int[][] getMapData() { return mapData; }
public int getGridSize() { return gridSize; }
public int getStage() { return stage; }
public int getCoins() { return coins; }
public int getScore() { return score; }
public int getNextBlockColor() { return colorNumber; }
public FileManager getFileManager() { return fileManager; }
}

    
   