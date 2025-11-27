package model;

public class Stuff {

	String category;
	String color;
	int weight;
	boolean correctweight;
	
	public Stuff(String category, String color, int weight) {
		this.color = color;
		boolean correct = evalWeight(weight);
		setWeight(correct, weight);
	}
	
	public String getCategory() {
		return category;
	}
	public String getColor() {
		return color;
	}
	public int getWeight() {
		return weight;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setColor(String category) {
		this.color = category;
	}
	protected boolean evalWeight(int weight) {
		if(weight <0) {
			correctweight = false;
		}
		if(weight > 20) {
			correctweight = false;
		}
		else {
			correctweight = true;
		}
		
		return correctweight;
	}
	
	public void setWeight(boolean correctwieght, int weight) {
		if(correctwieght) {
			this.weight = weight;
		}
	}

}
