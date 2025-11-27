package model;

public class LivingStuff extends Stuff{

	public LivingStuff(String category, String color, int weight) {
		super("생활용품", "green", weight);
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
