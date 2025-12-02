package Gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuestionPanel extends JPanel {
	
	private Frame frame;
	private JButton closeButton;
	private JPanel helpBox;
	private JTextArea message;
	
	public QuestionPanel(Frame frame) {
		this.frame = frame;
		
		setLayout(null);
		setBackground(new Color(0xEDEDED));
		
        //게임 설명칸
        helpBox = new JPanel();
        helpBox.setLayout(null);
        helpBox.setBackground(Color.GRAY); 
        helpBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        add(helpBox);

        //게임 방법 텍스트 
        message = new JTextArea(
                "당신은 ROBO HUB의 배송기사입니다.\n"
                + "[Stage 1]\n"
                + "중앙 맵을 물류 상자로 채워 코인을 획득하세요.\n"
                + "같은 색상을 가로열, 세로행에 맞추면 Coin과 Score가 상승합니다.\n"
                + "물건은 빈 공간에 배치해야합니다.\n\n"
                + "[Stage 2]\n"
                + "Stage 1과 마찬가지로 중앙 맵을 물류 상자로 채워 주세요.\n"
                + "Stage 1과 2에서 획득한 Coin으로 Stage 3을 클리어합니다.\n\n"
                + "[Stage 3]\n"
                + "제한 시간은 60초 입니다.\n"
                + "가로/세로 줄이 가득 차면 그 줄이 비워지면서 Score를 얻을 수 있습니다.\n"
                + "60초 안에 최대한 많은 score를 얻어야 합니다.\n\n"
                + "[로봇 구매]\n"
                + "모든 스테이지에서 로봇을 구매하고 사용할 수 있습니다.\n"
                + "높은 score를 얻기 위해서 게임의 규칙을 전략적으로 활용하세요.\n"
        );

        message.setEditable(false);
        message.setBackground(Color.GRAY);
        message.setForeground(Color.WHITE);
        message.setFont(new Font("맑은 고딕", Font.BOLD, 17));
        helpBox.add(message);
        
        
        closeButton = new JButton("돌아가기"); //문구
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
                frame.changePanel(new GamePanel(frame));
            }
        });
	}
	
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //전체 패널의 폭/높이
        int width = getWidth();
        int height = getHeight();

        //도움말상자 위치/크기
        int boxWidth = width / 2;
        int boxHeight = height * 6 / 10;
        int boxX = (width - boxWidth) / 2;
//        int boxY = height * 15 / 100;
        int boxY = height / 7;
        helpBox.setLocation(boxX,boxY);
        helpBox.setSize(boxWidth,boxHeight);

        //도움말 내 텍스트상자 위치/크기
        int msgWidth = boxWidth * 9 / 10; //message박스는 box보다 아주 조금 작게 만듦
        int msgHeight = boxHeight * 92 / 100;
        int msgX = (boxWidth - msgWidth) / 2;
//        int msgY = (boxHeight - msgHeight) / 2 - 10;
        int msgY = height / 35;
        message.setLocation(msgX,msgY);
        message.setSize(msgWidth,msgHeight);

        //"알겠습니다"버튼 위치/크기
        int buttonWidth = width * 2 / 10;
        int buttonHeight = height / 10;
        int buttonX = (width - buttonWidth) / 2;
        int buttonY = height * 8 / 10;
        closeButton.setLocation(buttonX,buttonY);
        closeButton.setSize(buttonWidth,buttonHeight);
    }

}
