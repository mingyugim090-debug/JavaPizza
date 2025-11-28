package util;

import controller.GameManager; 

public class TimeThread extends Thread{
	
	//변수 선언
	private GameManager gameManager; //GameManager에 시간을 보고해야함
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
			//1초 간격으로 카운트
			try {
				Thread.sleep(1000);
				
				//시간 1초씩 감소
				timeLeft--;
				
				// 시간 줄어들 때마다 GameManager에게 보고해서 화면 갱신
				gameManager.updateTime(timeLeft);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// clear가 아니라 시간이 0이되어서 끝나면 GameManager에게 gameover 신호 보냄
		if (timeLeft <= 0) {
			gameManager.gameOver();
		}
	}
	
	//사용자가 게임 clear해서 게임이 중간에 끝났을 때 타이머를 멈추는 기능
	public void stopTimer() {
		this.isRunning = false;
	}
}

//TimeThread 클래스의 역할 : 게임의 시간담당
//멀티스레드 구현. 메인 프로그램에서 게임을 하는 동안 동시에 시간이 지나도록 함
//GameManager가 게임 시작할 때 TimeThread 생성하고 시작
//TimeThread가 1초마다 GameManager를 호출하여 보고하고 , 시간 다 지나면 gameover 보냄
//TimeThread가 GameManager에게 보고하면 , GameManager가 GamePanel의 숫자를 바꾸어서 화면에 시간이 줄어드는 것이 보이도록함




