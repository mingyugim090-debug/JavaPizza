package model;
import controller.GameManager;
public class DeleteRobot extends Robot{

	public DeleteRobot(String name) {
		super(name);
	}
	
	public DeleteRobot(String name, String manual, int price) {
		super(name, manual, price);
	}

	@Override
	public void skills(GameManager gm, int row, int col) {
		int [][] mapdata = gm.getMapData();
		mapdata[row][col] = 0;
		

	}

}