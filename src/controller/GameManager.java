package controller;

import javax.swing.JOptionPane;
import java.util.Random;
import java.awt.Color;
import Gui.Frame;
import Gui.GamePanel;
import util.TimeThread;
import util.FileManager;

//멤버 변수 선언
public class GameManager {    
    private Frame frame; //전체 창 
    private GamePanel gamePanel;  //게임 화면
    private TimeThread timer;     //시간
    private FileManager fileManager; //랭킹 or 데이터
     
    
    //게임 상태 변수
    private int myScore = 0; 
    private int myCoins = 0;
    private int currentStage = 1; // 현재 스테이지
    private int gridSize = 4;     // 맵 크기 (1단계:4x4, 2단계:3x3 , 3단계 : 5X5)
   
    // 맵 데이터 (0: 빈칸,1:파랑,2:분홍,3:노랑,4:초록)
    private int[][] mapData; 
    
    //다음에 놓을 블록 생상(1~4)
    private int nextBlockColor;
    private Random random = new Random();
    
    // 게임 상태 (게임 중일 때만 클릭 되게 하도록 설정)
    private boolean isGameRunning = false;
    
    //로봇 모드 (0:기본 , 1:변환로봇 , 2:삭제로봇 , 3:메타몽 로봇) 추후 구현
    private int currentRobotMode = 0;

    // 생성자 : frame 전달받아서 저장하고 , FileManager 새로 생성
    public GameManager(Frame frame) {
        this.frame = frame;
        this.fileManager = new FileManager();
    }
    
    
    //GamePanel이 생성되면 이 메서드를 통해 GameManager로 알려줌
    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    //로봇 사용 모드 설정(0:기본,1:변환,2:삭제,3:메타몽)
    public void setRobotMode(int mode) {
    	this.currentRobotMode = mode;
    	System.out.println("로봇 변경" + mode);
    	if(mode != 0) {
    		JOptionPane.showMessageDialog(frame, "로봇스킬 활성화!");
    	}
    }
    
    //로봇 아이템 사용 완료 후 차감 처리
    public void useRobotItem(String robotName) {
    	//MyRobotPanel에게 1회 차감해달라고 요청
    	gamePanel.getMyRobotPanel().useRobotOnce(robotName);
    	this.currentRobotMode = 0; //사용 후 기본 로봇으로 복귀
    }
    //변환 로봇 기능(다음 블록 색상 변경되도록)
    public void handleConvertRobot() {
        System.out.println("변환 로봇 사용!");
        generateNextBlock(); // 다음 블록 랜덤 변경됨
        frame.repaint();
        useRobotItem("변환로봇"); // 횟수 차감됨
    }
    
    // 랭킹시스템 연결
    public FileManager getFileManager() {
        return fileManager;
    }
    
    //[1] 게임 흐름 제어(초기화, 시작, 종료)
    
    //게임 시작 (초기화)
    public void initGame() {
        System.out.println("GameManager : Stage" + currentStage + "초기화");
        
        if (currentStage ==1) {
            this.myScore = 0;
            this.myCoins = 0;
        }
        this.isGameRunning = true;
        
        //스테이지에 따른 맵 크기 설정
        if (currentStage == 1) gridSize = 4;
        else if (currentStage == 2) gridSize = 3;
        else gridSize = 5;
        
        //각 스테이지의 격자 크기에 맞게 맵 데이터 생성
        mapData = new int[gridSize][gridSize];
        
        if (gamePanel != null && gamePanel.getMapPanel() !=null) {
        	gamePanel.getMapPanel().setStage(currentStage);
        }
        if (gamePanel != null) {
        	gamePanel.updateScore(myScore);
        	gamePanel.updateCoins(myCoins);
        }
        
        //첫번 째 랜덤 박스 생성
        generateNextBlock();
        
        //타이머 스레드
        if (timer != null) timer.stopTimer(); 
        
        if(currentStage == 3) {
        	timer = new TimeThread(this, 60);    //Stage 3만 60초 제한
        	timer.start();
        } 
        
        //화면 갱신 frame에게 요청
        if (gamePanel != null) {
        	gamePanel.updateNextBlock(nextBlockColor);
            frame.repaint();
        }
    }
    
    //게임 오버 (TimeThread에서 호출)
    public void gameOver() {
        isGameRunning = false;
        if (timer != null) timer.stopTimer();
        System.out.println("Game Over!");
        
       
     //사용자에게 이름 입력받기
        String name = JOptionPane.showInputDialog(frame, 
            "⏰ Times up!! final_score : " + myScore + "\n이름 :");

        //이름이 있으면 FileManager에게 저장 요청
        if (name != null && !name.trim().isEmpty()) {
            
            // FileManager의 rank 메서드 호출
            fileManager.rank(myScore, name); 
        }
        //메인 화면으로 복귀
        frame.showMainPanel();
    }

    // --- [2] 데이터 관리 (박스 생성 , 점수 , 코인) ---
    private void generateNextBlock() {
    	nextBlockColor = random.nextInt(4) + 1;
    	System.out.println("다음 블록 색상 : " + nextBlockColor);
    	
    	if(gamePanel != null) gamePanel.updateNextBlock(nextBlockColor);
    }
    public int getNextBlockColor() {
    	return nextBlockColor;
    }

    //TimeThread가 1초마다 이 메서드 호출하여 남은시간을 받아 GamePanel의 숫자를 바꿈
    public void updateTime(int time) {
        // 화면에 시간 표시 갱신
        if(gamePanel != null) {
            gamePanel.updateTimer(time);
        }
    }

    //점수 추가
    public void addScore(int point , int coin) {
    	if (currentStage ==3) {
    		coin = 0;
    	}
        this.myScore += point;
        this.myCoins += coin;
        System.out.println("점수 획득: " + point + " / 총점: " + myScore);
        
        //  화면 갱신 (점수판이 있다면 갱신 요청)
        if(gamePanel != null) {
        	gamePanel.updateScore(myScore);
        	gamePanel.updateCoins(myCoins);
        	
        	gamePanel.updateStage(currentStage);
        } 
    }
    
    // --- [3] 상호작용 및 판정 (MapPanel과 연결) ---
    
    
    //사용자가 맵의 (row, col) 위치에 물건을 놓았을 때 MapPanel에서 호출하는 메서드 
    public void handleMapAction(int row, int col) {
        // 게임 중이 아니면 무시
        if (!isGameRunning) return;

        // 맵 범위를 벗어났거나, 이미 물건이 있는 경우
        if (row < 0 || col < 0 || row >= gridSize || col >= gridSize) return;
         
        
        // --- 로봇 모드에 따른 분류 ---
        //삭제 로봇
        if (currentRobotMode == 2) {
            if (mapData[row][col] != 0) {
                mapData[row][col] = 0; // 블록 삭제
                System.out.println("블록 삭제 완료!");
                useRobotItem("삭제로봇"); // 횟수 차감
                frame.repaint();
            }
            return; 
        }
        
        // 메타몽 로봇
        if (currentRobotMode == 3) { 
            if (mapData[row][col] == 0) {
                mapData[row][col] = 5; //5번 색깔을 무지개색으로 설정
                useRobotItem("메타몽로봇");
                checkLineMatch(row, col);
                generateNextBlock();
                frame.repaint();
            }
            return;
        }
        
        
        if (mapData[row][col] != 0) {
            System.out.println("이미 물건이 있습니다!");
            return; // 실패
        }

        // 맵이 현재 대기 중인 블록 배치
        mapData[row][col] = nextBlockColor;
        System.out.println("적재 성공! (" + row + "," + col +") 색상 : " + nextBlockColor);
        

        // 기본 적재 보상(+100 coins)
        addScore(0,100); 
        //라인 매칭(가로/세로)확인
        checkLineMatch(row,col);
        //다음 블록 생성
        generateNextBlock();
        //화면 갱신
        frame.repaint(); 
        //스테이지 클리어 확인(Stage1 , Stage2만 해당)
        if (currentStage < 3) {
        	checkStageClear();
        } else {
        	checkGameOverStage3(); // Stage3는 꽉 차면 게임 오버
        }
        }
    
    private void checkLineMatch(int row , int col) {
    	
    	//가로 줄 검사
    	boolean rowMatch = true;
    	int targetColor = -1;
    	for (int c=0; c < gridSize; c++) {
    		int cell = mapData[row][c];
            if (cell != 0 && cell != 5) {
                targetColor = cell;
                break; // 색깔을 찾았으면 중단
            }
        }
    	//라인 매칭 판단
        if (targetColor == -1) { 
            for (int c = 0; c < gridSize; c++) {
                if (mapData[row][c] == 0) {
                    rowMatch = false;
                    break;
                }
            }
        } else {
            // 기준 색상을 찾았다면 -> 모든 칸이 '그 색깔' 이거나 '메타몽'이어야 함
            for (int c = 0; c < gridSize; c++) {
                if (mapData[row][c] != targetColor && mapData[row][c] != 5) {
                    rowMatch = false;
                    break;
                }
            }
        }
        
    	//세로 줄 검사
        boolean colMatch = true;
        int targetColColor = -1;
       
        for (int r = 0; r < gridSize; r++) {
            int cell = mapData[r][col];
            if (cell != 0 && cell != 5) {
                targetColColor = cell;
                break;
            }
        }

        // 세로 줄 매칭 판단
        if (targetColColor == -1) {
            for (int r = 0; r < gridSize; r++) {
                if (mapData[r][col] == 0) {
                    colMatch = false;
                    break;
                }
            }
        } else {
            for (int r = 0; r < gridSize; r++) {
                if (mapData[r][col] != targetColColor && mapData[r][col] != 5) {
                    colMatch = false;
                    break;
                }
            }
        }
    	//보상 지급 및 라인처리
    	if (rowMatch) processLineClear(true, row);
    	if (colMatch) processLineClear(false, col);
    	
    }
    
    
    private void processLineClear(boolean isRow , int index) {
    	System.out.println("라인 보너스!" + (isRow ? "가로" : "세로")+"줄 완성");
    	addScore(100,300); //보상: +300코인 , + 100 점수
    	//Stage3인 경우 줄 삭제하는 로직
    	if (currentStage == 3) {
    		if (isRow) {
    			for(int c = 0; c < gridSize; c++) mapData[index][c] = 0;
    		} else {
    			for (int r = 0 ; r < gridSize; r++) mapData[r][index] = 0;
    		}
    	}
    
    }
    
    // 스테이지 1,2 클리어 조건(꽉 차면 다음 스테이지)
    private void checkStageClear() {
        int count = 0;
        // 맵 전체를 검사해서 물건이 몇 개인지 셈
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (mapData[i][j] != 0) count++;
            }
        }
        
        // 맵이 꽉 찼다면? (게임 Clear 했다면)
        if (count == gridSize * gridSize) {
        	if (timer != null) {
                timer.stopTimer();	
        	}
            JOptionPane.showMessageDialog(frame, "stage" + currentStage + "Clear! \n 다음단계로 이동합니다.");
            
            // 다음 스테이지로 넘어가는 로직
            if (currentStage < 3) {
                currentStage++;
                initGame(); // 다음 스테이지로 초기화 및 시작
            
            }
        }
    }
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
    public int[][] getMapData(){
    	return mapData;
    	
    }
    public int getGridSize() {
    	return gridSize;
    }
    // 로봇 구매 메서드
    public void buyRobot(String robotName, int price) {
    	if (myCoins >= price) {
    		boolean success = gamePanel.getMyRobotPanel().addRobot(robotName, 2);
    		
    		if(success) {
    			myCoins -= price;
    			addScore(0,0);
    			gamePanel.getRobotPanel().setPurchased(robotName); //로봇 구매 완료시 버튼 텍스트 변경 요청
        		System.out.println(robotName + "구매완료. 남은 코인 : " + myCoins);
    		
    	} else {
    		System.out.println("이미 보유 중인 로봇입니다.!");
    		JOptionPane.showMessageDialog(frame, "이미 보유 중인 로봇입니다.");
    			
    		}
    	}else {
    		System.out.println("코인 부족");
    		JOptionPane.showMessageDialog(frame, "코인부족");
    	}
    	}
    }
    
    

//GameManager의 역할
//게임 규칙 : 점수 , clear , 스테이지 관리 등 게임의 모든 규칙 판단
//데이터 관리 : 맵의 상태 , 점수 등 데이터관리
//여러 클래스들의 중매역할 
//TimeThread --> GameManager --> GamePanel
//MapPanel --> GameManager --> GamePanel
//Main클래스에 의해 생성되어서 Frame을 통해 화면을 제어하고 , 패널을 교체함
//GamePanel , MapPanel , TimeThread과 상호작용
