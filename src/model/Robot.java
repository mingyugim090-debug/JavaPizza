package model;

public abstract class Robot{
	
	protected String name;
	protected String manual;
	protected int price;
	protected int limit;
	protected boolean canMove;
	
	public Robot(String name, String manual, int price) {
		this.name = name;
		this.manual = manual;
		this.price = price;
	}
	
	//Overloading
	public Robot(String name, String manual, int price, int limit) {
		this.name = name;
		this.manual = manual;
		this.price = price;
		this.limit = limit;
	}
	
	
	public String getName() {
		return name;
	}
	
	public String getManual() {
		return manual;
	}
	
	public int getPrice() {
		return price;
	}
	
	public int getLimit() {
		return limit;
	}
	
	public String Info() {
		return name + "\n\n" + manual + "\n\n" + price + "\n\n" + limit;
	}
	
	public boolean CanCarry(int heft) {
		if (heft > limit) {
			canMove = false;
		}
		else {
			canMove = true;
		}
		
		return canMove;
	}
	public void skills() {
		
	}
	
//	protected int setVelocity() {
//		velocity = 0;
//		
//		return velocity;
//	};
	
//	protected int calTime(int weight) {
//		int v = setVelocity();
//		return (1/v) * weight;
//	}
}
