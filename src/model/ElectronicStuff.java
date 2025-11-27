package model;

public class ElectronicStuff extends Stuff {
	
	public ElectronicStuff(String category, String color, int weight) {
		super("가전제품", "blue", weight);
		
	}
	
	@Override
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

}