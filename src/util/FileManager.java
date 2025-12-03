package util;

import java.util.ArrayList;
import java.io.*;
import javax.swing.*;
import java.awt.*;

public class FileManager {
	public ArrayList<Integer>scores = new ArrayList<>();
	public ArrayList<String>names = new ArrayList<>();
	
	private final String FILE_NAME = "ranking.txt";
	
	// 랭킹 불러오기
	public FileManager() {
		loadFile();
	}
	
	// 점수랑 이름 받으면 arraylist에 저장. 
	//GamaManager클래스에 있는 gameover함수(80번째줄)에 게임 끝나면 "닉네임을 입력하세요" 같은 문구가 나오면 그때 이름 입력하는 식으로 하면 좋을것 같습니다.
	public void rank(int score, String name) {
		names.add(name);
		scores.add(score);
		
		arraydown();
		saveFile();
	}
	
	public void rank(int score) {
		names.add("GUEST");
		scores.add(score);
		
		arraydown();
		saveFile();
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
	
	// 파일에 저장
	private void saveFile() {
		FileWriter fw = null;
		
		try {
			fw = new FileWriter(FILE_NAME);
			
			for(int i=0;i<scores.size();i++) {
				fw.write(scores.get(i)+","+names.get(i)+"\n");
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}finally {
			if(fw!=null) {
				try {
					fw.close();
					}catch(IOException e) {}
				}
			}
	}
	
	// 저장된 파일 불러오기
	private void loadFile() {

        FileReader fr = null;
        BufferedReader br = null;

        File file = new File(FILE_NAME);
        
        if (!file.exists()) return;

        try {
            fr = new FileReader(FILE_NAME);
            
            // 한줄씩 읽기용
            br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");
                
                // 파일 형식 오류나면 그냥 다음줄 읽기
                if (parts.length != 2) continue;
                
                try{
                    int score = Integer.parseInt(parts[0]);
                    String name = parts[1];

                    scores.add(score);
                    names.add(name);
                } // 점수가 숫자가 아니면 그 라인 무시하고 다음라인 읽기
                catch(NumberFormatException e) {
                	System.out.println("잘못된 데이터: "+line);
                	continue;
                }
            }

            arraydown();

        } catch (IOException e) {
            System.out.println("랭킹 파일 읽기 실패: "+e.getMessage());
        } finally {
            try { 
            	if (br != null) br.close();
            } catch (IOException e) {}
            
            try { if (fr != null) fr.close();
            } catch (IOException e) {}
        }
    }
}
