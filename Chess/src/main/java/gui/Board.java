package main.java.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import main.java.pieces.Piece;



public class Board extends JFrame{
	

	private static final long serialVersionUID = 1L;

		public Board(Piece[][] board){
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Container con = this.getContentPane();
			this.setSize(800, 800);
			this.setLocation(0,0);
			con.setLayout(new GridLayout(8,8));
			for(int i=0; i<=7; i++){
				for(int j=0; j<=7; j++){
					Square square = new Square();
					
					if((j % 2==0 && i % 2 != 0) || (j% 2!=0 && i%2 ==0)){
						square.setBackground(Color.GRAY);
					}
					
					ImageIcon icon = getIcon(board[i][j].getSymbol(), board[i][j].getType(), board[i][j].getColor());
					JLabel label = new JLabel(icon, JLabel.CENTER);					
					square.add(label);
					con.add(square);
				}
			}
			setVisible(true);
			
		}
		
		public ImageIcon getIcon(String pieceName, String type, String color){
			String colorName;
	        if(color==null){
	        	colorName = "leer";
	        }else if(color.equalsIgnoreCase("weiß")){
	        	colorName = "white";
	        }else{
	        	colorName = "black";
	        }
	        if(!colorName.equals("leer")){
	        	java.net.URL imgURL = getClass().getResource("/main/resources/"+type+"_"+colorName+".png");
	            
	            if (imgURL != null) 
	            {
	                return new ImageIcon(imgURL);
	            } else {
	                System.err.println("Couldn't find file: " + "/main/resources/"+type+"_"+colorName+".png");
	                return null;
	            }
	    	}else{
	    		return null;
	    	}
		}
}
