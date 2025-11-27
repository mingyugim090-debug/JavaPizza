package model;

public class RotatingBot extends Robot{

	public MiddleRobot(String name, String manual, int price) {
		super("중형로봇", "10kg 미만의 물건을 옮길 수 있다.", 300);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected int setVelocity() {
		velocity = 2;
		return velocity;
	}

}
