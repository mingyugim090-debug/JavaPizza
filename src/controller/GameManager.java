package controller;

import javax.swing.JOptionPane;
import java.util.Random;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Gui.Frame;
import Gui.GamePanel;
import Gui.StageIntroPanel;
import util.TimeThread;
import util.FileManager;

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
import java.util.Random; 
import java.util.ArrayList;
import Gui.Frame;               
import Gui.GamePanel;
import Gui.StageIntroPanel;
import util.TimeThread;
import util.FileManager;
import model.Robot;

	
//======================[클래스 선언 및 멤버 변수]======================================
//게임 운영에 필요한 모든 상태값 변수를 private으로 선언하여 캡슐화
public class GameManager {    
    
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
   
    // 맵 데이터 (0: 빈칸,1:파랑,2:분홍,3:노랑,4:초록,5:보라(메타몽))
    private int[][] mapData; 
    
    //다음에 놓을 블록 생상(1~4 랜덤으로 생성)
    private int colorNumber;
    private Random random = new Random();
    
    // 게임 진행상태 (false : 종료 , true : 진행)
    private boolean isGameRunning = false;
    
//    처음 시작인가?
    private boolean firstStart = true;
    
    //로봇 모드. 기본로봇으로 시작(0:기본 , 1:변환로봇 , 2:삭제로봇 , 3:메타몽 로봇)
    private int robotMode = 0;

    
//======================[생성자 및 초기 설정]====================================    
    //생성자 : frame 전달받아서 저장하고 , FileManager 새로 생성
    public GameManager(Frame frame) {
        this.frame = frame;
        this.fileManager = new FileManager();
    }
    
    //StageIntroPanel에서 쓰이는 메소드임
    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    
    //==================[로봇 스킬 관련 메서드]==================================
    
    //로봇 사용 모드 설정(0:기본,1:변환,2:삭제,3:메타몽)
    public void setRobotMode(int robotmode) {
    	this.robotMode = robotmode;
    	if(robotmode != 0) {
    		JOptionPane.showMessageDialog(frame, "로봇스킬 활성화!");
    	}
    }
    
    //로봇 아이템 사용 완료 후 차감되고 , 기본로봇으로 복귀
    public void useRobotItem(String robotName) {
    	gamePanel.getMyRobotPanel().useRobotOnce(robotName);
    	this.robotMode = 0; 
    }
    //변환 로봇 기능 : 다음 블록 색상을 즉시 랜덤 변경
    public void handleConvertRobot() {
        generateNextBlock(); 
        frame.repaint();
        useRobotItem("변환로봇"); 
    }
    
    // 랭킹시스템 연결
    public FileManager getFileManager() {
        return fileManager;
    }
    
//========================[게임 흐름 제어(초기화)]===========================
    
    //게임 시작 및 스테이지 초기화 메서드
    public void initGame() {
        if (firstStart) {  //1단계 시작 시 점수/코인 0으로 설정
            score = 0;
            coins = 0;
            firstStart = false;
        }
        this.isGameRunning = true;
        
        //스테이지에 따른 맵 크기 설정 : 게임 난이도 조정해보고 바꿀 소요 있음.
        if (stage == 1) gridSize = 4; //이게 진짜 작동하는 코드임
        else if (stage == 2) gridSize = 3;
        else gridSize = 5;
        
        //각 스테이지의 격자 크기에 맞게 맵 데이터 생성
        mapData = new int[gridSize][gridSize];
        
        //GamePanel 안의 MapPanel에게 맵크기 바뀌었으니 다시 세팅하라고 지시
        if (gamePanel != null && gamePanel.getMapPanel() !=null) {
        	gamePanel.getMapPanel().setStage(stage);
        }
        //UI점수/코인 표시 초기화
        if (gamePanel != null) {
        	gamePanel.updateScore(score);
        	gamePanel.updateCoins(coins);
        }
        
        //첫번 째 랜덤 박스 생성
        generateNextBlock();
        
        //타이머 스레드 : Stage3에서만 60초 타이머 시작
        if (timer != null) timer.stopTimer(); 
        
        if(stage == 3) {
        	timer = new TimeThread(this, 60);    
        	timer.start();
        } 
        
        //최종 화면 갱신
        if (gamePanel != null) {
        	gamePanel.updateNextBlock(colorNumber);
            frame.repaint();
        }
    }
    
    //게임 오버 (시간 종료 or 맵 가득 참)
    public void gameOver() {
        isGameRunning = false;
        if (timer != null) timer.stopTimer();
        
        if(gamePanel != null) {
        	gamePanel.showGameOver();
        }
        frame.repaint();
       
     //사용자에게 이름 입력받아 랭킹 저장
        String name = JOptionPane.showInputDialog(frame, 
            "Game Over! \n점수: " + score + "\n이름을 입력하세요");

        if (name != null && !name.trim().isEmpty()) {

            // FileManager의 rank 메서드 호출
            fileManager.rank(score, name); 
        }
        //메인 화면으로 복귀
        frame.showMainPanel();
    }

//=====================[데이터 관리(박스 생성,점수,코인)]========================

    private void generateNextBlock() {
    	colorNumber = random.nextInt(4) + 1;
    	if(gamePanel != null) gamePanel.updateNextBlock(colorNumber);
    }
    public int getNextBlockColor() {
    	return colorNumber;
    }

    //TimeThread가 1초마다 이 메서드 호출하여 남은시간을 받아 GamePanel의 숫자를 바꿈
    public void updateTime(int time) {
        if(gamePanel != null) {
            gamePanel.updateTimer(time);
        }
    }

    //점수 및 코인 획득 메서드
    public void addScore(int score , int coins) {
    	if (stage ==3) {
    		coins = 0;   //Stage3는 코인 획득 불가
    	}
        this.score += score;
        this.coins += coins;
        
        //  화면갱신
        if(gamePanel != null) {
        	gamePanel.updateScore(this.score);
        	gamePanel.updateCoins(this.coins);
        	gamePanel.updateStage(stage);
        } 
    }
    
// ======================[상호작용 및 판정]===========================
    //사용자가 맵을 클릭했을 때 호출되는 핵심 로직
    //사용자가 맵의 (row, col) 위치에 물건을 놓았을 때 MapPanel에서 호출하는 메서드 
    public void handleMapAction(int row, int col) {
        if (!isGameRunning) return;
        if (row < 0 || col < 0 || row >= gridSize || col >= gridSize) return;
        
        // --- 로봇 스킬 모드일 경우 따른 분류 ---
        //삭제 로봇 : 클릭한 로봇 제거
        if (robotMode == 2) {
            if (mapData[row][col] != 0) {
                mapData[row][col] = 0; // 블록 삭제
                useRobotItem("삭제로봇"); // 사용횟수 차감
                frame.repaint();
            }
            return; 
        }
        
        // 메타몽 로봇 : 빈칸에 만능 블록(보라색) 배치
        if (robotMode == 3) { 
            if (mapData[row][col] == 0) {
                mapData[row][col] = 5; //5번 : 보라색
                useRobotItem("메타몽로봇");
                checkLineMatch(row, col);
                generateNextBlock();
                frame.repaint();
            }
            return;
        }
        
        //기본 모드 : 블록배치
        if (mapData[row][col] != 0) {
            return;
        }

        // 맵이 현재 대기 중인 블록 배치
        mapData[row][col] = colorNumber;
        addScore(0,100);          // 기본 적재 보상(+100 coins)
        checkLineMatch(row,col);  //라인 매칭(가로/세로)확인
        generateNextBlock();      //화면 갱신
        frame.repaint();          //화면 갱신
        
        //스테이지 클리어 확인(Stage1 , Stage2만 해당)
        if (stage < 3) {
        	checkStageClear();
        } else {
        	checkGameOverStage3(); // Stage3는 꽉 차면 게임 오버
        }
        }
    
    //라인 매칭 알고리즘
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
    
    //라인 완성 시 보상 및 처리
    private void processLineClear(boolean isRow , int index) {
    	addScore(100,300); //보상: +300코인 , + 100 점수
    	
    	//Stage3인 경우 줄 삭제하는 로직(애니팡과 비슷한 느낌)
    	if (stage == 3) {
    		if (isRow) {
    			for(int c = 0; c < gridSize; c++) mapData[index][c] = 0;
    		} else {
    			for (int r = 0 ; r < gridSize; r++) mapData[r][index] = 0;
    		}
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
    
    //getter , setter 메서드
    public int[][] getMapData(){
    	return mapData;
    	
    }
    public int getGridSize() {
    	return gridSize;
    }
    
    public int getStage() {
    	return stage;
    }
    
    public int getCoins() {
    	return coins;
    }
    
    public int getScore() {
    	return score;
    }

    //체크박스 선택되면 total coin 변경, 해제되면 감소
//	    public void setTotalCoin(JCheckBox C_robot, Robot robot, int[] total_price) {
//	    	if(C_robot.isSelected()) {
//	    		total_price[0] += robot.getPrice(); 
//	        }
//	    	else {
//	    		total_price[0] -= robot.getPrice();
//	    	}
//	    }
	    
	    //체크박스 선택된 robot 목록(ArrayList) 반환
	    public void addSelectedRobots(JCheckBox C_robot, Robot robot, ArrayList<Robot> selected_robots) {
	    	if(C_robot.isSelected()) {
	    		if (!selected_robots.contains(robot)) {
	                selected_robots.add(robot);   // 중복 방지
	            }
	        } else {
	            selected_robots.remove(robot); 
	    	}
	    }

	    
	  //로봇 구매 메서드(수정)
	    public void buyMain(ArrayList<Robot> selected_robots) {
	    	if(selected_robots.size() == 0) {
	    		System.out.println("선택된 로봇이 없습니다.");
	    		JOptionPane.showMessageDialog(frame, "구매할 로봇을 선택해주세요");
	    	}
	    	else {
	    		int result = JOptionPane.showConfirmDialog(frame, "구매하시겠습니까?", "Confirm", JOptionPane.YES_NO_OPTION);
	    		if(result == JOptionPane.CLOSED_OPTION) {
	    			JOptionPane.showMessageDialog(frame, "구매에 실패했습니다.");
	    			System.out.println("구매에 실패했습니다.");
	    		}
	    		else if (result == JOptionPane.YES_OPTION) {
	    			for(Robot selected_robot : selected_robots) {
	    				buyRobot(selected_robot.getName(), selected_robot.getPrice());
	    			}
	    			selected_robots.clear();
	    			
	    		}
	    		else {
	    			JOptionPane.showMessageDialog(frame, "구매에 실패했습니다.");
	    			System.out.println("구매에 실패했습니다.");
	    		}
	    			
	    			
	    	}
	    	
	    }
	    
  
	    //*금액버튼을 누를때 메세지 띄우기
	    public void PrintRobot(String robotName, int price) {
	    	String strings = robotName + "은 " + price + " coin 입니다.";
	    	System.out.println(strings);
	    	JOptionPane.showMessageDialog(frame, strings);
	    	
	    	}

    public void buyRobot(String robotName, int price) {
    	if (coins >= price) {
    		boolean success = gamePanel.getMyRobotPanel().addRobot(robotName, 2);
    		
    		if(success) {
    			coins -= price;
    			addScore(0,0);

    			gamePanel.getRobotPanel().setPurchased(robotName); //로봇 구매 완료시 버튼 텍스트 변경 요청
        	
    	} else {
    		JOptionPane.showMessageDialog(frame, "이미 보유 중인 로봇입니다.");
    		
    		}
    	}else {
    		JOptionPane.showMessageDialog(frame, "코인부족");
    	}
    }	
    }
    
   