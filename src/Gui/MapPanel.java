package Gui;
import javax.swing.*;
 
import Gui.Frame;

import java.awt.*;
import java.awt.event.*;

public class MapPanel extends JPanel{
	
//    private Frame frame;
	private int gridSize; //맵 한줄의 개수 (3,4,5)
	private int cellSize = 120; //칸 하나의 크기(px)
	private int panelPadding = 60; //맵을 화면에 배치할 때 전체 여백
	private int mapStartX; //맵의 위치
	private int mapStartY;
	private int stage;
    private Box boxPanel; // box 크기 위치
    
	//드래그 상태
	private boolean isDragging = false; //드래그 중인가?
	private Image draggingImage = null; //드래그 중 따라다니는 이미지
	private int mouseX, mouseY; //현재 마우스 드래그 위치 
	private boolean gameOver = false;
	
	//Map 데이터
	private int[][] map;
	
	//생성자
	public MapPanel(int stage) {
		
		this.stage = stage;
		
		setLayout(null); 
	    setStage(stage);  //stage 번호를 전달받아 gridSize 설정 
	    setPreferredSize(new Dimension(600, 400));
	    setBackground(new Color(0xEDEDED));

	    addMouseListener(mouseListener);
	    addMouseMotionListener(mouseMotionListener);
	}
	
	public void setGameOver(boolean gameOver) {
	    this.gameOver = gameOver;

	    //gameOver == true 이면 드래그 인식 중지
	    if (gameOver) {
	        isDragging = false;
	        draggingImage = null;
	    }
	}
	
	//스테이지에 따른 gridSize
	public void setStage(int stage) {
		if(stage == 1) gridSize = 4; //4X4
		if(stage == 2) gridSize = 3; //3X3
		if(stage == 3) gridSize = 5; //5X5
		
		map = new int[gridSize][gridSize]; //스테이지에 따른 맵 초기화
		
		repaint();
	}
	
    public void updateMap(int[][] newMap) {
        this.map = newMap;
        repaint();
    }

    public void startDragging(Image img) {
    	if (gameOver) return; 
        isDragging = true;
        draggingImage = img;
    }

    public void stopDragging() {
        isDragging = false;
        draggingImage = null;
        repaint();
    }
    
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	
    	int width = getWidth();
    	int height = getHeight();
    	int boardSize = Math.min(width, height);
    	
    	cellSize = boardSize / gridSize;

        int mapPixelSize = gridSize * cellSize; //맵의 전체 픽셀 크기 계산
        //맵 배치
        mapStartX = (width - mapPixelSize) / 2;
        mapStartY = (height - mapPixelSize) / 2;

        drawGrid(g);
        drawMapObjects(g);

        //물건을 드래그 중일 때
        if (isDragging && draggingImage != null) {
        	//마우스가 현재 어느 칸 위에 위치하는가
        	int col = (mouseX - mapStartX) / cellSize;
            int row = (mouseY - mapStartY) / cellSize;
            
            //현재 칸의 정중앙 위치(픽셀값으로)
            int snapX = mapStartX + col * cellSize + cellSize/2;
            int snapY = mapStartY + row * cellSize + cellSize/2;

            //칸의 중앙 x값과 현재 마우스 위치 차이의 절댓값
            int dx = Math.abs(mouseX - snapX);
            int dy = Math.abs(mouseY - snapY);

            //스냅 거리 30px 이내면 스냅 작동
            if (dx < 30 && dy < 30) {
                g.drawImage(draggingImage,
                            snapX - draggingImage.getWidth(null)/2,
                            snapY - draggingImage.getHeight(null)/2,
                            60, 60, null);
            }else {
                g.drawImage(draggingImage, mouseX - 20, mouseY - 20, 60, 60, null);
            }
        }
    }
     
    //맵 그리는 메소드
    private void drawGrid(Graphics g) {

        for (int r = 0; r < gridSize; r++) {
            for (int c = 0; c < gridSize; c++) {

                int x = mapStartX + c * cellSize;
                int y = mapStartY + r * cellSize;

                //칸 배경색
                g.setColor(new Color(225, 225, 225));
                g.fillRect(x, y, cellSize, cellSize);

                //칸 테두리
                g.setColor(Color.BLACK);
                g.drawRect(x, y, cellSize, cellSize);
            }
        }
    }
    
//====================GameManager와 연결되는 메서드================================
    //맵 위에 실제 물건(박스,로봇 등)을 그리는 메소드
    private void drawMapObjects(Graphics g) {
    	
        //map[row][col] 값에 따라 그림 표시
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
            	
            	int value = map[row][col]; //0:빈칸,1~4:색상
            	if (value!=0) {
            		Color boxColor = Color.GRAY; //기본색상 회색
            		
            		switch(value) {
            		    case 1: boxColor = new Color(100,149,237); break; // 파랑
            		    case 2: boxColor = new Color(255,182,193); break; // 분홍
            		    case 3: boxColor = new Color(255,255,100); break; // 노랑
            		    case 4: boxColor = new Color(144,238,144); break; // 초록
            		    
            		}
            		g.setColor(boxColor);
            		int x = mapStartX + col * cellSize;
            		int y = mapStartY + row * cellSize;
            		g.fillRect(x + 2 , y + 2, cellSize - 4 , cellSize -4);
            		g.setColor(Color.BLACK);
            		g.drawRect(x+2 ,  y+2 ,  cellSize - 4 ,cellSize - 4);
            	}
            }
        }
    }
//============================================================================
    //좌표
    public Point getGridPosition(int mouseX, int mouseY) {
        int col = (mouseX - mapStartX) / cellSize;
        int row = (mouseY - mapStartY) / cellSize;

        // 그리드 밖이면 -1, -1 반환
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            return new Point(-1, -1);
        }

        return new Point(col, row);
    }
    
 
    //마우스 이벤트
    private MouseListener mouseListener = new MouseAdapter() {
       
    	//마우스 버튼을 눌렀을 때
        public void mousePressed(MouseEvent e) {
        	if(gameOver) return;
            handleClick(e);
        }

        //마우스 버튼을 뗐을 때
        public void mouseReleased(MouseEvent e) {
        	if(gameOver) return;
            if (isDragging) {
                handleDrop(e);
            }
        }
    };

    private MouseMotionListener mouseMotionListener = new MouseMotionAdapter() {
       
        public void mouseDragged(MouseEvent e) {
        	if (gameOver) return;
            mouseX = e.getX();
            mouseY = e.getY();
            repaint();
        }

        public void mouseMoved(MouseEvent e) {
        	if (gameOver) return;
            mouseX = e.getX();
            mouseY = e.getY();
            repaint();
        }
    };
    
    //GameManager에게 클릭한 것 알려주는 역할
    private void handleClick(MouseEvent e) {
        //클릭한 위치의 좌표(row,col)계산
        int col = (e.getX() - mapStartX) / cellSize;
        int row = (e.getY() - mapStartY) / cellSize;

        System.out.println("Click : row=" + row + " col=" + col);
        
        //맵 바깥을 클릭했을 때 오류 방지
        if (row < 0 || col < 0 || row >= gridSize || col >= gridSize) {
            return; // 맵 밖이면 무시
        }
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        if(parentFrame != null && parentFrame.getGameManager() != null) {
        	parentFrame.getGameManager().handleMapAction(row,col);
        }
    }

    // GameManger와 MapPanel 이 연결되는 부분
	// 외부에서 맵 데이터(int[][])를 받아서 화면을 갱신하는 메서드
    public void setMapData(int[][] newMap) {
               this.map = newMap;
               repaint(); // 화면 다시 그리기
           }   	

    //드래그해서 놓았을 때
    private void handleDrop(MouseEvent e) {
    	int col = (e.getX() - mapStartX) / cellSize;
        int row = (e.getY() - mapStartY) / cellSize;

        System.out.println("Drop : row=" + row + " col=" + col);
        stopDragging();
        
        //마우스로 물건을 놓았을 때 GameManager에게 물어보도록 하는 역할
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        //Frame을 통해 GameManager를 가져옴
        if(parentFrame != null && parentFrame.getGameManager()!=null) {
        	//GameManager에게 좌표 처리해달라고 요청
        	parentFrame.getGameManager().handleMapAction(row,col);
        }
     }
 }

