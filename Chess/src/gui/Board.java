package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;


import javax.swing.JFrame;


import pieces.Piece;
/*
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;import javax.swing.ImageIcon;
import javax.swing.JPanel;
*/

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
					Square pane = new Square(board[i][j].getSymbol());

					if((j % 2==0 && i % 2 != 0) || (j% 2!=0 && i%2 ==0)){
						pane.setBackground(Color.BLACK);

					}
					
					con.add(pane);
				}
			}
			setVisible(true);
			
		}

}
