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


    public Square(int xPos, int yPos) {
    	this.setxPos(xPos);
    	this.setyPos(yPos);
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

}
