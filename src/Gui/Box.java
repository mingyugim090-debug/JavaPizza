package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.Color;	

public class Box extends JPanel{
	//fields
	Color color;
	int[] x_set;	//도형을 그릴 x point 배열
	int[] y_set;	//도형을 그릴 y point 배열
	
	//constructor - overloading
	// 1) 색상만 입력받아서 색상 초기화
	public Box(Color color) { 
		this.color = color;
	}
	//2) x/y point 배열까지 입력받아서 초기화, 각 배열은 좌표로 구성됨.
	public Box(Color color, int[] x_set, int[] y_set) {
		this.color = color;
		this.x_set = x_set;
		this.y_set = y_set;
	}
	
	//box 그리는 코드
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(color);
		
		int []x = x_set;
		int []y = y_set;
		
		//사각형 그림 (fill - 색상 칠해짐)
		//x[0] ~ x[1] / y[0] ~ y[1] 범위
		//width = x[1] - x[0], height = y[1] - y[0]
		g.fillRect(x[0], y[0], x[1]-x[0], y[1]-y[0]);
		
		//박스모양 그리기
		int d = 5;	//큰 사각형에서 얼마나 떨어져있는지
		//1) 상단 선 그리기
		g.drawLine(x[0], y[1]-d, x[1], y[1]-d);
		//2) 하단 선 그리기
		g.drawLine(x[0], y[0]+d, x[1], y[0]+d);
		//3) 왼쪽 선 그리기
		g.drawLine(x[0]+d, y[0]+d, x[0]+d, y[1]-d);
		//4) 오른쪽 선 그리기
		g.drawLine(x[1]-d, y[0]+d, x[1]-d, y[1]-d);
		
		//대각선 그리기
		int s = 2;	//대각선끼리의 간격
		//1) 큰 대각선 그리기
		g.drawLine(x[0]+d, y[0]+d, x[1]-d, y[1]-d);
		//2) 하단 대각선 그리기
		g.drawLine(x[0]+d+s, y[0]+d, x[1]-d, y[1]-d-s);
		g.drawLine(x[0]+d+(2*s), y[0]+d, x[1]-d, y[1]-d-(2*s));
		//3) 상단 대각선 그리기
		g.drawLine(x[0]+d, y[0]+d+s, x[0]+d+(2*s), y[1]-d);
		g.drawLine(x[0]+d, y[0]+d+(2*s), x[0]+d+s, y[1]-d);
	}
	
	//색상 설정
	public void setColor(Color color) {
		this.color = color;
	}
	//색상 반환
	public Color getColor() {
		return color;
	}
	//x point 배열 설정
	public void setX(int[] x_set){
		this.x_set = x_set;
	}
	//y point 배열 설정
	public void setY(int[] y_set){
		this.y_set = y_set;
	}
	//x point 배열 반환
	public int[] getPointsX() {
		return x_set;
	}
	//y point 배열 반환
	public int[] getPointsY() {
		return y_set;
	}
}

