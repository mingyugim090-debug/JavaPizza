package model;
import java.awt.Color;
import java.util.Random;

public class Stuff_skh {
	private int weight;
	private Color color;
	
    // 랜덤 무게 생성
    private int RandomWeight() {
        return new Random().nextInt(20) + 1;   // 1~20
    }
	
    // 무게에 따라 색 지정
	private Color decideColor(int w) {
		if (w <= 5)
            return Color.GREEN; // 5이하 초록색
        else if (w <= 10)
            return Color.YELLOW; // 10이하 노랑색
        else if (w <= 20)
            return Color.RED; // 20이하 빨간색
        else return Color.black;
	}
	
	
	public Stuff_skh() {
        this.weight = RandomWeight();  // 1~20 무게 생성
        this.color = decideColor(weight);      // 색 결정
    }
	

    public int getWeight() {
        return weight;
    }

    public Color getColor() {
        return color;
    }
}