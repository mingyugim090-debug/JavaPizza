package Gui;

import javax.swing.*;	
import java.awt.*;
import java.awt.event.*;
import util.FileManager;

public class RankingPanel extends JPanel {

    private Frame frame;
    private JPanel rankBox;
    private JTextArea rankText;
    private JButton backButton;
    private JScrollPane scrollpane;
    

    private FileManager fileManager;

    public RankingPanel(Frame frame) {
        this.frame = frame;
        this.fileManager = frame.getGameManager().getFileManager(); 

        setLayout(null);
        setBackground(new Color(0xEDEDED));

        //랭킹 박스
        rankBox = new JPanel();
        rankBox.setLayout(null);
        rankBox.setBackground(Color.GRAY);
        rankBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        add(rankBox);

        //랭킹 텍스트
        rankText = new JTextArea();
        rankText.setEditable(false);
        rankText.setBackground(Color.GRAY);
        rankText.setForeground(Color.WHITE);
        rankText.setFont(new Font("맑은 고딕", Font.BOLD, 25));
        rankBox.add(rankText);

        //뒤로가기 버튼
        backButton = new JButton("뒤로가기");
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        backButton.setOpaque(true);
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        add(backButton);

        
        // 랭킹 스크롤 가능하게
        scrollpane = new JScrollPane(rankText);
        scrollpane.setBorder(null);
        rankBox.add(scrollpane);
        
        //뒤로가기 버튼 리스너
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.changePanel(new MainPanel(frame));
            }
        });
        
        //backButton "돌아가기 누르면 StartPanel로 돌아감
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.changePanel(new MainPanel(frame));
            }
        });
        loadRanking();   //랭킹 읽기
    }

    //랭킹 불러오기
    private void loadRanking() {
    
        String text = "RANKING \n\n";

        if (fileManager.names.size() == 0) {
            text = "등록된 기록이 없습니다.";
        }
        else {
        	for (int i = 0; i < fileManager.names.size(); i++) {
        		text += (i + 1) + "위  " + fileManager.names.get(i) + "  -  " + fileManager.scores.get(i) + "점 \n";
             }
        }
        rankText.setText(text);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        //박스 크기
        int boxWidth = width / 2;
        int boxHeight = height * 3 / 5;
        int boxX = (width - boxWidth) / 2;
        int boxY = height * 3 / 20;
        rankBox.setLocation(boxX, boxY);
        rankBox.setSize(boxWidth, boxHeight);

        
        
        //텍스트 크기
        int msgWidth = boxWidth * 9 / 10;
        int msgHeight = boxHeight * 92 / 100;
        int msgX = (boxWidth - msgWidth) / 2;
        int msgY = (boxHeight - msgHeight) / 2 - 10;
        rankText.setLocation(msgX, msgY);
        rankText.setSize(msgWidth, msgHeight);
        scrollpane.setLocation(msgX, msgY);
        scrollpane.setSize(msgWidth, msgHeight);

        //뒤로가기 버튼 크기
        int buttonWidth = width / 5;
        int buttonHeight = height / 10;
        int buttonX = (width - buttonWidth) / 2;
        int buttonY = height * 4 / 5;
        backButton.setLocation(buttonX, buttonY);
        backButton.setSize(buttonWidth, buttonHeight);
    }
}
