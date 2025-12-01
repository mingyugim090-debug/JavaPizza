package model;

import controller.GameManager;
import Gui.Frame;

public class TransferRobot extends Robot {

	private GameManager gameManager;
	private Frame frame;

	public TransferRobot(String name, String manual, int price) {
		super(name, manual, price);
	}

	public TransferRobot(String name, String manual, int price, GameManager gameManager, Frame frame) {
		super(name, manual, price);
		this.gameManager = gameManager;
		this.frame = frame;
	}

//	@Override
//	public void skills() {
//        System.out.println("변환 로봇 사용!");
//        gameManager.generateNextBlock(); // 다음 블록 랜덤 변경됨
//        frame.repaint();
//        gameManager.useRobotItem("변환로봇"); // 횟수 차감됨
//    }

}