package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainPanel extends JPanel {
	
	private Frame frame;
	private JButton startButton;
    private JButton helpButton;
    private JLabel logo;
    
    public MainPanel(Frame frame) {
        this.frame = frame;
        
        setLayout(null); //MainPanel 배치관리자 제거
        
        //로고
        ImageIcon logo = new ImageIcon(getClass().getResource("robohub_logo.png"));
        JLabel label = new JLabel(logo);
        label.setBounds(330,50, logo.getIconWidth(), logo.getIconHeight()); //로고의 위치와 크기
        add(label); 
        
        //Start 버튼
        startButton = new JButton("START"); //문구
        startButton.setBackground(Color.GRAY); //배경색
        startButton.setForeground(Color.WHITE); //글씨색
        startButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5)); //테두리
        startButton.setOpaque(true); //배경색 적용
        startButton.setFocusPainted(false);
        startButton.setFont(new Font("Arial",Font.BOLD,30));
        
        startButton.setLocation(600,300); //위치
        startButton.setSize(300,100); //크기
        add(startButton); //부착
        
        //Help 버튼
        helpButton = new JButton("HELP"); //문구
        helpButton.setBackground(Color.GRAY);
        helpButton.setForeground(Color.WHITE);
        helpButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        helpButton.setOpaque(true);
        helpButton.setFocusPainted(false);
        helpButton.setFont(new Font("Arial",Font.BOLD,30));
        
        helpButton.setLocation(600,500); //위치
        helpButton.setSize(300,100); //크기
        add(helpButton); //부착
        
      //help버튼 누르면 HelpPanel로 이동, 익명 클래스로 이벤트 리스너 작성(교재 p566)
        helpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.changePanel(new HelpPanel(frame));
            }
        });
        
        
    }

}
