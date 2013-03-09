package main.java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Square extends JPanel{
	

	private static final long serialVersionUID = 1L;
	private int xPos;
	private int yPos;
	private Color backColor;


    public Square(int xPos, int yPos) {
    	this.setxPos(xPos);
    	this.setyPos(yPos);
    	/*if(yPos == 7){
    		this.setyPos(0);
    	}else if(yPos == 6){
    		this.setyPos(1);
    	}else if(yPos == 5){
    		this.setyPos(2);
    	}else if(yPos == 4){
    		this.setyPos(3);
    	}else if(yPos == 3){
    		this.setyPos(4);
    	}else if(yPos == 2){
    		this.setyPos(5);
    	}else if(yPos == 1){
    		this.setyPos(6);
    	}else if(yPos == 0){
    		this.setyPos(7);
    	}else{
    		System.out.println("Ueberpruefe, zu viele Felder");
    	}*/
        Border blackline = BorderFactory.createLineBorder(Color.black);
        setBorder(blackline);
        this.setLayout(new BorderLayout());
    }
     
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
    }

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public Color getBackColor() {
		return backColor;
	}

	public void setBackColor(Color backColor) {
		this.backColor = backColor;
	}
	
	public Square(){
		
	}

}
