	package Gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HelpPanel extends JPanel {
	
	private Frame frame;
	private JButton closeButton;
	private JPanel helpBox;
	private JTextArea message;
	
	public HelpPanel(Frame frame) {
		this.frame = frame;
		
		setLayout(null);
		setBackground(new Color(0xEDEDED));
		
        //게임 설명칸
        helpBox = new JPanel();
        helpBox.setLayout(null);
        helpBox.setBackground(Color.GRAY); 
        helpBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        add(helpBox);

        //게임 방법 텍스트 kkkkkkkk
        message = new JTextArea(
                "당신은 ROBO HUB의 배송기사입니다.\n"
                + "우측에 표시된 물건을 로봇을 이용하여 배송해야 합니다.\n\n"
                + "[물건 배치]\n"
                + "맵 주변의 물건을 드래그해 중앙으로 옮기세요.\n"
                + "물건은 빈 공간에 배치해야하며, 되돌릴 수 없습니다.\n\n"
                + "[로봇 구매]\n"
                + "우측에 있는 \"로봇 목록\"에서 로봇을 구매할 수 있습니다.\n"
                + "로봇을 적절히 구매해 전략적으로 수행하세요.\n\n"
                + "[코인]\n"
                + "stage에 따른 초기 자금이 주어지고,\n"
                + "물건을 중앙에 배치하면 코인을 획득합니다.\n"
        );

        message.setEditable(false);
        message.setBackground(Color.GRAY);
        message.setForeground(Color.WHITE);
        message.setFont(new Font("맑은 고딕", Font.BOLD, 23));
        helpBox.add(message);
        
        
        closeButton = new JButton("알겠습니다"); //문구
        closeButton.setBackground(Color.GRAY); //배경색
        closeButton.setForeground(Color.WHITE); //글씨색
        closeButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5)); //테두리
        closeButton.setOpaque(true); //배경색 적용
        closeButton.setFocusPainted(false);
        closeButton.setFont(new Font("맑은 고딕",Font.BOLD,30));
        add(closeButton); //부착
        
        //closeButton("알겠습니다")누르면 StartPanel로 돌아감, 익명 클래스로 이벤트 리스너 작성(교재 p566)
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.changePanel(new MainPanel(frame));
            }
        });
	}
	
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //전체 패널의 폭/높이
        int width = getWidth();
        int height = getHeight();

        //도움말상자 위치/크기
        int boxWidth = (int)(width * 0.5);
        int boxHeight = (int)(height * 0.60);
        int boxX = (width - boxWidth) / 2;
        int boxY = (int)(height * 0.15);
   
        helpBox.setLocation(boxX,boxY);
        helpBox.setSize(boxWidth,boxHeight);

        //도움말 내 텍스트상자 위치/크기
        int msgWidth = (int)(boxWidth * 0.90); //message박스는 box보다 아주 조금 작게 만듦
        int msgHeight = (int)(boxHeight * 0.92);
        int msgX = (boxWidth - msgWidth) / 2;
        int msgY = (boxHeight - msgHeight) / 2 - 10;

        message.setLocation(msgX,msgY);
        message.setSize(msgWidth,msgHeight);

        //"알겠습니다"버튼 위치/크기
        int buttonWidth = (int)(width * 0.20);
        int buttonHeight = (int)(height * 0.10);
        int buttonX = (width - buttonWidth) / 2;
        int buttonY = (int)(height * 0.80);

        closeButton.setLocation(buttonX,buttonY);
        closeButton.setSize(buttonWidth,buttonHeight);
    }

}
