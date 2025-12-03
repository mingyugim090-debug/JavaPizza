package model;

import controller.GameManager;

public class TransferRobot extends Robot {
	
	public TransferRobot(String name) {
		super(name);
	}

	public TransferRobot(String name, String manual, int price) {
		super(name, manual, price);
	}
	@Override
	public void skills(GameManager gm, int row, int col) {
        gm.generateNextBlock(); 
        gm.useRobotItem("변환 로봇"); 
    }
}