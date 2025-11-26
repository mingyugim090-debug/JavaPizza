package controller;

import Gui.Frame;
import Gui.GamePanel;
import util.TimeThread;

public class GameManager {    
    private Frame frame; 
    private int myScore=0; //내 점수를 저장할 변수
    private GamePanel gamePanel;
    
    //생성자 : Main에서 넘겨준 화면 받는 곳
    public GameManager(Frame frame) {
            this.frame = frame;
    }
    
    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    
    //초기화 메서드
    public void initGame() {
        System.out.println("게임 초기화 중");
        TimeThread timer = new TimeThread(this , 60);
        timer.start();
    }
    
    
    //[1]시간 관련 메서드 (TimeThread와 연결됨)
    //시간이 1초 줄어들때마다 호출되어 실행됨
    public void updateTime(int time) {
        System.out.println("남은 시간: " + time + "초");
        
        javax.swing.SwingUtilities.invokeLater(() -> {
            gamePanel.updateTimer(time);  
        });
    }

    // 시간이 0이 되면 게임오버 호출
    public void gameOver() {
        System.out.println("Game Over! Times up!");
            // 나중에 여기서 팝업창 띄우고 게임 정지 로직 timeThread.stopTimer(); 추가 예정
        }

        //[2] 점수 관련 메서드
        // 기본 점수 추가
        public void addScore(int point) {
        	this.myScore += point; //점수 누적
            System.out.println("점수 획득: " + point);
    }
}
