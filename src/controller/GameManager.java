package controller;

import javax.swing.JOptionPane;
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
    private int currentStage = 1; // 현재 스테이지
    private int gridSize = 3;     // 맵 크기 (1단계:3x3, 2단계:4x4 로 점점 늘려나갈 예정)
   
    // 맵 데이터 (0: 빈칸, 1: 물건있음)
    private int[][] mapData;  //맵의 각 칸에 물건이 있으면 1 , 없으면 0으로 저장
    
    // 게임 상태 (게임 중일 때만 클릭 되게 하도록 설정)
    private boolean isGameRunning = false;

    // 생성자 : frame 전달받아서 저장하고 , FileManager 새로 생성
    public GameManager(Frame frame) {
        this.frame = frame;
        this.fileManager = new FileManager();
    }
    
    
    //GamePael이 생성되면 이 메서드를 통해 GameManager로 알려줌
    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    
    // 랭킹시스템 연결
    public FileManager getFileManager() {
        return fileManager;
    }
    
    // --- [1] 게임 흐름 제어 (초기화, 시작, 종료) ---
    
    // 게임 시작 (초기화)
    public void initGame() {
        System.out.println("GameManager: 게임 초기화 및 시작");
        this.myScore = 0;
        this.isGameRunning = true;
        
        // 스테이지에 따른 맵 크기 설정
        if (currentStage == 1) gridSize = 3;
        else if (currentStage == 2) gridSize = 4;
        else gridSize = 5;
        
        // 각 스테이지의 격자 크기에 맞게 맵 데이터 생성
        mapData = new int[gridSize][gridSize];
        
        // 타이머 스레드
        if (timer != null) timer.stopTimer(); 
        timer = new TimeThread(this, 60);     // 60초 제한
        timer.start();
        
        // 화면 갱신 frame에게 요청
        if (gamePanel != null) {
            frame.repaint();
        }
    }
    
    // 게임 오버 (TimeThread에서 호출)
    public void gameOver() {
        isGameRunning = false;
        if (timer != null) timer.stopTimer();
        System.out.println("Game Over!");
        
       
     // 사용자에게 이름 입력받기
        String name = JOptionPane.showInputDialog(frame, 
            "⏰ Times up!! final_score : " + myScore + "\n이름 :");

        //이름이 있으면 FileManager에게 저장 요청
        if (name != null && !name.trim().isEmpty()) {
            
            // FileManager의 rank 메서드 호출
            fileManager.rank(myScore, name); 
            System.out.println("랭킹 저장 완료: " + name + " (" + myScore + "점)");
        }
        
        //메인 화면으로 복귀
        frame.showMainPanel();
    }

    // --- [2] 시간 및 점수 관리 (TimeThread 연결) ---
    
    //TimeThread가 1초마다 이 메서드 호출하여 남은시간을 받아 GamePanel의 숫자를 바꿈
    public void updateTime(int time) {
        System.out.println("남은 시간: " + time + "초");
        
        // 화면에 시간 표시 갱신
        if(gamePanel != null) {
            gamePanel.updateTimer(time);
        }
    }

    //점수 추가
    public void addScore(int point) {
        this.myScore += point;
        System.out.println("점수 획득: " + point + " / 총점: " + myScore);
        
        // 1. 화면 갱신 (점수판이 있다면 갱신 요청)
        if(gamePanel != null) {
        	gamePanel.updateScore(myScore);
        }
        
        // 2. 스테이지 클리어 조건 확인
        checkStageClear(); 
    }
    
    // --- [3] 상호작용 및 판정 (MapPanel과 연결) ---
    
    
    //사용자가 맵의 (row, col) 위치에 물건을 놓았을 때 MapPanel에서 호출하는 메서드 
    public void handleMapAction(int row, int col) {
        // 1. 게임 중이 아니면 무시
        if (!isGameRunning) return;

        // 2.맵 범위를 벗어났거나, 이미 물건이 있는 경우
        if (row < 0 || col < 0 || row >= gridSize || col >= gridSize) return;
        
        if (mapData[row][col] != 0) {
            System.out.println("이미 물건이 있습니다!");
            return; // 실패
        }

        // 3. 성공 처리 (데이터 갱신: 1은 '물건 있음' 표시)
        mapData[row][col] = 1; 
        System.out.println("적재 성공! 좌표: " + row + "," + col);

        // 4. 점수 추가
        addScore(100); 

        // 5. 화면 갱신 요청
        frame.repaint(); 
        //즉 모든 검사를 통과하면 데이터 1(물건이 놓여짐)로 바꾸고 , 점수 추가하고 화면 갱신
    }
    
    // 스테이지 클리어 조건 확인
    private void checkStageClear() {
        int count = 0;
        // 맵 전체를 검사해서 물건(1)이 몇 개인지 셈
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (mapData[i][j] != 0) count++;
            }
        }
        
        // 맵이 꽉 찼다면? (게임 Clear 했다면)
        if (count == gridSize * gridSize) {
            isGameRunning = false;
            timer.stopTimer(); // 타이머 정지
            
            System.out.println("♨ Stage clear! ♨");
            JOptionPane.showMessageDialog(frame, "Congratulations! Stage Clear");
            
            // 다음 스테이지로 넘어가는 로직
            if (currentStage < 3) {
                currentStage++;
                initGame(); // 다음 스테이지로 초기화 및 시작
            } else {
                JOptionPane.showMessageDialog(frame, "모든 스테이지를 완료했습니다! 게임 종료.");
                frame.showMainPanel();
            }
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




