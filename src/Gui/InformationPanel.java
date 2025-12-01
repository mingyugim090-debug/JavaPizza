package Gui;

import javax.swing.*;	
import javax.swing.border.LineBorder;
import java.awt.*;

public class InformationPanel extends JPanel {

    //사용자의 눈에 보이는 글자
    private JLabel stageLabel;
    private JLabel coinsLabel;
    private JLabel scoreLabel;

    private int stage;
    private int coins;
    private int score;

    //실제 사용할 생성자
    public InformationPanel(int stage, int coins, int score) {
        this.stage = stage;
        this.coins = coins;
        this.score = score;

        //전체 바 높이
        this.setPreferredSize(new Dimension(0, 60)); //세로 60에 맞추어 가로 길이 자동 생성 
        setOpaque(false);
        this.setLayout(new GridLayout(1, 3, 20, 0)); // 행,열,가로 칸 사이 간격 20px

        //stage, coins, score 라벨 생성
        stageLabel = createInfoLabel("Stage : " + stage);
        coinsLabel = createInfoLabel("Coins : " + coins);
        scoreLabel = createInfoLabel("Score : " + score);

        //박스 패널 안에 라벨 넣어서 추가
        add(createInfoBox(stageLabel));
        add(createInfoBox(coinsLabel));
        add(createInfoBox(scoreLabel));
    }

    //안에 들어갈 라벨 모양 설정
    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        label.setForeground(Color.WHITE); // 글자 흰색
        return label;
    }

    //네모 박스 디자인
    private JPanel createInfoBox(JLabel label) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.GRAY); // 진한 회색
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4)); //테두리
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    //외부 클래스로부터 바뀐 stage, coins, score를 받음
    public void setStage(int stage) {
        this.stage = stage;
        stageLabel.setText("Stage : " + stage);
    }

    public void setCoins(int coins) {
        this.coins = coins;
        coinsLabel.setText("Coins : " + coins);
    }

    public void setScore(int score) {
        this.score = score;
        scoreLabel.setText("Score : " + score);
    } 
}
