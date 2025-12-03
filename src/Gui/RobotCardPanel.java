package Gui;

import javax.swing.*;
import java.awt.*;

public class RobotCardPanel extends JPanel {
	
	private JLabel nameLabel;
    private JLabel countLabel;
    private JButton useButton;
    private Image image;
    
    private int count; //남은 횟수(다른 패키지에서 전달받을 예정)
    
    public RobotCardPanel(String name, ImageIcon icon, int count) {

        this.count = count;
        this.image = icon.getImage();

        setLayout(null);
        setBackground(new Color(225, 225, 225));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 4)); 

        //이름
        nameLabel = new JLabel(name, SwingConstants.CENTER);
        nameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        add(nameLabel);

        //남은 횟수 표시
        countLabel = new JLabel("", SwingConstants.CENTER);
        countLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        add(countLabel);

        //사용하기 버튼
        useButton = new JButton("사용하기");
        useButton.setFocusPainted(false);
        add(useButton);

        updateCountUI();
    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth(); //RobotCardPanel의 너비
        int height = getHeight(); //RobotCardPanel의 높이

        //로봇 이름 위치/크기
        int nameWidth =width * 9 / 10; 
        int nameHeight = height * 15 / 100; 
        int nameX = width / 20 ;
        int nameY = height * 3 / 100;  
        nameLabel.setLocation(nameX,nameY);
        nameLabel.setSize(nameWidth,nameHeight);
        
        
        //로봇 이미지 그리기(크기 조절하여 그리기)
        if (image != null) {

            //카드 패널 크기 기반 이미지 비율 조절
            int imgWidth = width * 7 / 10;
            int imgHeight = height * 4 / 10;

            //가운데 정렬
            int imgX = (width - imgWidth) / 2;
            int imgY = height * 22/ 100;

            g.drawImage(image, imgX, imgY, imgWidth, imgHeight, this);
        }
        
        //"남은 횟수: N" 위치/크기
        int countWidth =width * 9 / 10; 
        int countHeight = height * 12 / 100; 
        int countX = width * 5 / 100;
        int countY = height * 63 / 100;  
        countLabel.setLocation(countX,countY);
        countLabel.setSize(countWidth,countHeight);
        
        int buttonWidth =width * 1 / 2; 
        int buttonHeight = height * 18 / 100; 
        int buttonX = width * 25 / 100;
        int buttonY = height * 77 / 100;  
        useButton.setLocation(buttonX,buttonY);
        useButton.setSize(buttonWidth,buttonHeight);
    }
    
    //남은 횟수는 외부 패키지에서 설정 
    public void setCount(int newCount) {
        this.count = newCount;
        updateCountUI();
    }
	
    //1번 사용
    public void useOnce() {
        if (count > 0) {
            count--;
            updateCountUI();
        }
    }
    
    private void updateCountUI() {
        countLabel.setText("남은횟수: " + count);

        if (count <= 0) {
            useButton.setEnabled(false);
            useButton.setBackground(new Color(0xDDDDDD));
            useButton.setForeground(Color.GRAY);
            useButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        } else {
            useButton.setEnabled(true);
            useButton.setBackground(Color.BLACK);
            useButton.setForeground(Color.WHITE);
            useButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        }
    }
    
    public JButton getUseButton() {
        return useButton;
    }
    public int getCount() {
    	return count;
    }
}
