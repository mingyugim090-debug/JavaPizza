package controller;

import javax.swing.JOptionPane;
import Gui.Frame;
import Gui.GamePanel;
import util.TimeThread;
import util.FileManager;
import java.util.Scanner;

public class GameManager {    
    private Frame frame; 
    private GamePanel gamePanel; 
    private TimeThread timer;    
    private FileManager fileManager;
    
    // 게임 데이터
    private int myScore = 0; 
    private int currentStage = 1; // 현재 스테이지
    private int gridSize = 3;     // 맵 크기 (1단계:3x3, 2단계:4x4 로 점점 늘려나갈 예정)
   
    // 맵 데이터 (0: 빈칸, 1: 물건있음)
    private int[][] mapData; 
    
    // 게임 상태 플래그 (게임 중일 때만 클릭 되게 하도록 설정)
    private boolean isGameRunning = false;

    // 생성자 : Main에서 넘겨준 화면 받는 곳
    public GameManager(Frame frame) {
        this.frame = frame;
        this.fileManager = new FileManager();
    }
    
    //GamePanel이 생성되면 이 메서드를 통해 GameManager로 알려줌
    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    
    // 랭킹시스템
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
        
        // 맵 데이터 생성
        mapData = new int[gridSize][gridSize];
        
        // 타이머 스레드
        if (timer != null) timer.stopTimer(); 
        timer = new TimeThread(this, 60);     // 60초 제한
        timer.start();
        
        // 화면 갱신 요청
        if (gamePanel != null) {
            frame.repaint();
        }
    }
    
    // 게임 오버 (TimeThread에서 호출)
    public void gameOver() {
    	Scanner scanner =new Scanner(System.in);
        isGameRunning = false;
        System.out.println("Game Over!");
        
        // 게임 끝나면 알림 메시지
        JOptionPane.showMessageDialog(frame, "⏰ 시간 종료! 최종 점수: " + myScore);
        
        //나중에 여기에 랭킹 저장(FileManager) 코드 추가할 예정
        // 사용자의 닉네임을 입력받으면 FileManager에 있는 rank(myScore, name)에 들어가야됨
        
        // 게임 끝나면 메인 화면으로 복귀 (Frame에 있는 메서드 호출)
        frame.showMainPanel();
    }

    // --- [2] 시간 및 점수 관리 (TimeThread 연결) ---
    
    // 시간이 1초 줄어들 때마다 호출됨
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
        // if(gamePanel != null) gamePanel.updateScore(myScore);
        
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
        
        // 맵이 꽉 찼다면?
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