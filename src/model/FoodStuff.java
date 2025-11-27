package model;

public class FoodStuff extends Stuff{

	public FoodStuff(String category, String color, int weight) {
		super("식품", "pink", weight);
		
	}
	
	@Override
	protected boolean evalWeight(int weight) {
		if(weight <0) {
			correctweight = false;
		}
		if(weight > 10) {
			correctweight = false;
		}
		else {
			correctweight = true;
		}
		
		return correctweight;
	}

}
