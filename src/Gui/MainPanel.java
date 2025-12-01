package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainPanel extends JPanel {
	
	private Frame frame;
	private JButton startButton;
    private JButton helpButton;
    private JLabel logoLabel;
    private JLabel requestLabel;
    private JButton rankingButton;
    
    
    public MainPanel(Frame frame) {
        this.frame = frame;
        
        setLayout(null);
        
        //로고
        ImageIcon logo = new ImageIcon(getClass().getResource("robohub_logo.png"));
        logoLabel = new JLabel(logo);
        add(logoLabel); 
        
        //Start 버튼
        startButton = new JButton("START"); //문구
        startButton.setBackground(Color.GRAY); //배경색
        startButton.setForeground(Color.WHITE); //글씨색
        startButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5)); //테두리
        startButton.setOpaque(true); //배경색 적용
        startButton.setFocusPainted(false);
        startButton.setFont(new Font("Arial",Font.BOLD,30));
        add(startButton); //부착
        
        //Start버튼 누르면 StageIntroPanel로 이동, 익명 클래스로 이벤트 리스너 작성(교재 p566)
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.changePanel(new StageIntroPanel(frame,1));
            }
        });
        
        //Help 버튼
        helpButton = new JButton("HELP"); //문구
        helpButton.setBackground(Color.GRAY);
        helpButton.setForeground(Color.WHITE);
        helpButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        helpButton.setOpaque(true);
        helpButton.setFocusPainted(false);
        helpButton.setFont(new Font("Arial",Font.BOLD,30));
        add(helpButton); //부착
        
        //help버튼 누르면 HelpPanel로 이동, 익명 클래스로 이벤트 리스너 작성(교재 p566)
        helpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.changePanel(new HelpPanel(frame));
            }
        });  
        
        //Ranking 버튼
        rankingButton = new JButton("RANKING"); //문구
        rankingButton.setBackground(Color.GRAY);
        rankingButton.setForeground(Color.WHITE);
        rankingButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        rankingButton.setOpaque(true);
        rankingButton.setFocusPainted(false);
        rankingButton.setFont(new Font("Arial",Font.BOLD,30));
        add(rankingButton); //부착
        
        //Ranking 버튼 누르면 RankPanel로 이동
        rankingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.changePanel(new RankingPanel(frame));
            }
        });  
        //미션 라벨
        requestLabel = new JLabel("*전체 화면으로 이용해 주세요*", SwingConstants.CENTER);
        requestLabel.setFont(new Font("맑은 고딕", Font.BOLD, 23));
        requestLabel.setForeground(Color.RED);
        add(requestLabel);
    }
    
    //화면이 리사이즈될 때마다 자동 호출
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //전체 패널의 폭/높이
        int width = getWidth(); 
        int height = getHeight(); 

        //로고 위치/크기
        int logoWidth = logoLabel.getIcon().getIconWidth(); //로고 폭
        int logoHeight = logoLabel.getIcon().getIconHeight(); //로고 높이
        int logoX = (width - logoWidth) / 2; //로고 x좌표
        int logoY = height / 10; //로고 y좌표
        logoLabel.setLocation(logoX,logoY);
        logoLabel.setSize(logoWidth,logoHeight);

        //START버튼 위치/크기
        int buttonWidth = width / 4; 
        int buttonHeight = height / 10; 
        int buttonX = (width - buttonWidth) / 2;
        int buttonY = height * 35 / 100;  
        startButton.setLocation(buttonX,buttonY);
        startButton.setSize(buttonWidth,buttonHeight);

        //Ranking버튼 위치/크기
        int rankY = height / 2;
        rankingButton.setLocation(buttonX,rankY);
        rankingButton.setSize(buttonWidth,buttonHeight);
        
        //HELP버튼 위치/크기
        int helpY = height * 65 / 100;
        helpButton.setLocation(buttonX,helpY);
        helpButton.setSize(buttonWidth,buttonHeight);
        
        //"전체 화면으로 이용해 주세요" 문구 크기/위치
        int requestWidth = width * 4 / 10;
        int requestHeight = height / 20;
        int requestX = (width - requestWidth) / 2;
        int requestY = helpY + buttonHeight * 3 / 2 ;
        requestLabel.setLocation(requestX,requestY);
        requestLabel.setSize(requestWidth,requestHeight);
    }
}
