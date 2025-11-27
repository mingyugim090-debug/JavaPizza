package util;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class FileManager {
	public ArrayList<Integer>scores = new ArrayList<>();
	public ArrayList<String>names = new ArrayList<>();
	
	
	// 점수랑 이름 받으면 arraylist에 저장. 
	//GamaManager클래스에 있는 gameover함수(80번째줄)에 게임 끝나면 "닉네임을 입력하세요" 같은 문구가 나오면 그때 이름 입력하는 식으로 하면 좋을것 같습니다.
	public void rank(int score, String name) {
		names.add(name);
		scores.add(score);
		
		arraydown();
	}
	
	// 점수 내림차순 정렬
	private void arraydown() {
        for (int i = 0; i < scores.size() - 1; i++) {
            for (int j = i + 1; j < scores.size(); j++) {
                if (scores.get(i) < scores.get(j)) {

                    int tempScore = scores.get(i);
                    scores.set(i, scores.get(j));
                    scores.set(j, tempScore);

                    String tempName = names.get(i);
                    names.set(i, names.get(j));
                    names.set(j, tempName);
                }
            }
        }
    }
	
	

}
