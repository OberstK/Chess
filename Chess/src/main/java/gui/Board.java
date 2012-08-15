package main.java.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import main.java.pieces.Piece;



public class Board extends JFrame implements MouseListener, MouseMotionListener{
	JLayeredPane layeredPane;
	JPanel chessBoard;
	ChessPiece chessPiece; //JLabel
    int xAdjustment;
    int yAdjustment;


	private static final long serialVersionUID = 1L;

		public Board(Piece[][] board){
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			
			
			layeredPane = new JLayeredPane();
			getContentPane().add(layeredPane);
			layeredPane.addMouseListener( this );
			layeredPane.addMouseMotionListener( this );
			this.setSize(815,835);
			this.setLocation(0,0);
			
			chessBoard = new JPanel();
			layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
			chessBoard.setLayout( new GridLayout(8, 8) );
			chessBoard.setBounds(0, 0, 800, 800);
			
			for(int i=0; i<=7; i++){
				for(int j=0; j<=7; j++){
					Square square = new Square();
					
					if((j % 2==0 && i % 2 != 0) || (j% 2!=0 && i%2 ==0)){
						square.setBackground(Color.GRAY);
					}
					chessPiece = new ChessPiece(board[i][j].getType(), board[i][j].getColor());					
					square.add(chessPiece);
					chessBoard.add( square );
				}
			}
			this.setVisible(true);
		}
		
		public void mousePressed(MouseEvent e)
		{
			chessPiece = null;
			Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
	 
			if (c instanceof JPanel) return;
	 
			Point parentLocation = c.getParent().getLocation();
			xAdjustment = parentLocation.x - e.getX();
			yAdjustment = parentLocation.y - e.getY();
			chessPiece = (ChessPiece)c;
			chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
			chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
			layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
		}
		/*
		**  Move the chess piece around
		*/
		public void mouseDragged(MouseEvent me)
		{
			if (chessPiece == null) return;
	 
			chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
		 }
	 
		/*
		**  Drop the chess piece back onto the chess board
		*/
		public void mouseReleased(MouseEvent e)
		{
			if (chessPiece == null) return;
	 
			chessPiece.setVisible(false);
			Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
	 
			if (c instanceof JLabel)
			{
				Container parent = c.getParent();
				parent.remove(0);
				parent.add( chessPiece );
			}
			else
			{
				Container parent = (Container)c;
				parent.add( chessPiece );
			}
	 
			chessPiece.setVisible(true);
		}
	    
	    public void mouseClicked(MouseEvent e) {}
	    public void mouseMoved(MouseEvent e) {}
	    public void mouseEntered(MouseEvent e) {}
	    public void mouseExited(MouseEvent e) {}

}
