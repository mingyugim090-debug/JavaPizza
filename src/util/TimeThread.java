package util;

import controller.GameManager;

public class TimeThread extends Thread{
	
	private GameManager gameManager;
	private int timeLeft; //남은 시간(초)
	private boolean isRunning = true; // 스레드 종료용 플래그

	
	//생성자 : 누구에게 보고할지 , 몇초를 셀지 받아오는 생성자
	public TimeThread(GameManager gameManager, int seconds) {
		this.gameManager = gameManager;
		this.timeLeft = seconds;
	}
	@Override
	public void run() {
		// 게임이 진행중이고 , 시간 남아있으면 진행
		while (isRunning && timeLeft > 0) {
			try {
				Thread.sleep(1000);
				
				//시간 감소
				timeLeft--;
				
				// 시간 줄어들 때마다 화면 갱신
				gameManager.updateTime(timeLeft);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// 시간이 0이되면 게임오버
		if (timeLeft <= 0) {
			gameManager.gameOver();
		}
	}
	
	//사용자가 게임 clear해서 게임이 중간에 끝났을 때 타이머를 멈추는 기능
	public void stopTimer() {
		this.isRunning = false;
	}
}




