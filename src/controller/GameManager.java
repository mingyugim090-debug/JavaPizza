package controller;

import view.MainFrame;

public class GameManager {    
    private MainFrame mainframe; 
    
    //생성자 : Main에서 넘겨준 화면 받는 곳
    public GameManager(MainFrame frame) {
            this.mainframe = frame;
    }
    
    //초기화 메서드
    public void initGame() {
        System.out.println("게임 초기화 중");
        // 이곳에 추후에 맵 불러오는거 , 점수 초기화하는 코드들 넣으면 될 듯
    
    
    }
}
