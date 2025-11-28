package model;

public class BigRobot extends Robot{

	public BigRobot(String name, String manual, int price, int limit) {
		super("대형로봇", "20kg 미만의 물건을 옮길 수 있다. ", 500, 20);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected int setVelocity() {
		this.velocity = 1;
		return velocity;
	}

}
