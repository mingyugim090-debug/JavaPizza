package Gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HelpPanel extends JPanel {
	
	private Frame frame;
	private JButton closeButton;
	
	public HelpPanel(Frame frame) {
		this.frame = frame;
		
		setLayout(null);
		setBackground(new Color(0xEDEDED));
		
        //게임 설명칸
        JPanel helpBox = new JPanel();
        helpBox.setLayout(null);
        helpBox.setBackground(new Color(120, 120, 120)); 
        helpBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        helpBox.setLocation(350,100); //위치
        helpBox.setSize(800,530); //크기
        add(helpBox);

        //게임 방법 텍스트
        JTextArea message = new JTextArea(
                "당신은 ROBO HUB의 배송기사입니다.\n"
                + "우측에 표시된 물건을 로봇의 도움을 받아 배송해야 합니다.\n\n"
                + "[물건 배치]\n"
                + "맵 주변의 물건을 드래그해 중앙으로 옮기세요.\n"
                + "물건은 빈 공간에 배치해야하며, 되돌릴 수 없습니다.\n\n"
                + "[로봇 구매]\n"
                + "화면의 하단에서 로봇을 구매할 수 있습니다.\n"
                + "로봇을 적절히 구매해 전략적으로 수행하세요.\n\n"
                + "[코인]\n"
                + "물건을 중앙에 배치하면 코인을 획득합니다.\n"
        );

        message.setEditable(false);
        message.setBackground(new Color(120, 120, 120));
        message.setForeground(Color.WHITE);
        message.setFont(new Font("맑은 고딕", Font.BOLD, 25));
        message.setLocation(50,30); //위치
        message.setSize(700,450); //크기
        helpBox.add(message);
        
        
        closeButton = new JButton("알겠습니다"); //문구
        closeButton.setBackground(Color.GRAY); //배경색
        closeButton.setForeground(Color.WHITE); //글씨색
        closeButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5)); //테두리
        closeButton.setOpaque(true); //배경색 적용
        closeButton.setFocusPainted(false);
        closeButton.setFont(new Font("맑은 고딕",Font.BOLD,30));
        
        closeButton.setLocation(600,650); //위치
        closeButton.setSize(300,100); //크기
        add(closeButton); //부착
        
        //closeButton("알겠습니다")누르면 StartPanel로 돌아감, 익명 클래스로 이벤트 리스너 작성(교재 p566)
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.changePanel(new MainPanel(frame));
            }
        });
	}

}

