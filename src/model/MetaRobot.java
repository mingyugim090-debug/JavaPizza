package model;
import controller.GameManager;
public class MetaRobot extends Robot{

	
	public MetaRobot(String name) {
		super(name);
	}
	
	public MetaRobot(String name, String manual, int price) {
		super(name, manual, price);
		
	}
	@Override
	public void skills(GameManager gm, int row, int col) {
		int [][] mapdata = gm.getMapData();
		mapdata[row][col] = 5;

	}

}