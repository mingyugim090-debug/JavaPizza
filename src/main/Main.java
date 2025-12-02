package main;

import Gui.Frame; //화면 담당
import controller.GameManager; //게임 규칙,로직 

public class Main {
	public static void main(String[] args) {
		System.out.println("ROBOHUB 게임 시작 중"); //게임 시작하는 로그 출력 
		
		    //view 패키지 객체 생성
		    Frame frame = new Frame(); //메인 화면 생성

		    //GamaManager 객체 생성
		    //GameManager 와 frame 상호 연결
		    GameManager gameManager = new GameManager(frame);
		    frame.setGameManager(gameManager);
		    
		    frame.setVisible(true); //화면이 모니터에 보이도록 설정
		    System.out.println("준비 완료되었습니다."); 
   
		}
	}


//Main 클래스 역할 : 프로그램 시작점
//view 패캐지의 Frame클래스 와 controller패키지의 GameManager클래스 연결
//Main --> Frame , GameManager 생성
//GameManager와 Frame이 서로 상호작용
