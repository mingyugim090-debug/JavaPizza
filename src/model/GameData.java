package model;
// 맵 배열 , 현재점수, 보유한 로봇 리스트 관리

public class GameData {
	
	private int smallcount;
	private int middlecount;
	private int bigcount;
	private int pricesmall = 100;
	private int pricemiddle = 500;
	private int pricebig = 1000;
	
	public GameData() {
        smallcount = 0;
        middlecount = 0;
        bigcount = 0;
    }
	
	// 로봇 구매시 로봇 수량 증가
	public void buySmallRobot() {
        smallcount++;
    }

    public void buyMiddleRobot() {
        middlecount++;
    }

    public void buyBigRobot() {
        bigcount++;
    }

    // 현재 로봇 보유 수 
    public int getSmallCount() {
        return smallcount;
    }

    public int getMiddleCount() {
        return middlecount;
    }

    public int getBigCount() {
        return bigcount;
    }
    
    
	int score=0;
	int coin=0;
	// 점수, 코인 관련
	public void score() {
		score+=1000;
	}
	// 물건을 배치했을 때 얻는 코인
	public void coin() {
		coin+=100;
	}
	
	// 로봇을 구매했을때 사용하는 코인
	public void buysmall() {
		coin=coin-pricesmall;
	}
	public void buymiddle() {
		coin=coin-pricemiddle;
	}
	public void buybig() {
		coin=coin-pricebig;
	}
	
	
}
