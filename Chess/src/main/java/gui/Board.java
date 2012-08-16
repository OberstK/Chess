package main.java.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import main.java.pieces.Piece;



public class Board extends JFrame{
	private JLayeredPane fenster;
	private ChessPiece chessPiece; //JLabel
	private JPanel chessBoard;
	
	public JLayeredPane getFenster() {
		return fenster;
	}

	public void setFenster(JLayeredPane fenster) {
		this.fenster = fenster;
	}

	
	public JPanel getChessBoard() {
		return chessBoard;
	}

	public void setChessBoard(JPanel chessBoard) {
		this.chessBoard = chessBoard;
	}

	public ChessPiece getChessPiece() {
		return chessPiece;
	}

	public void setChessPiece(ChessPiece chessPiece) {
		this.chessPiece = chessPiece;
	}

	


	private static final long serialVersionUID = 1L;

		public Board(Piece[][] board){
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			fenster = new JLayeredPane();
			getContentPane().add(fenster);
			this.setSize(815,835);
			this.setLocation(0,0);
			
			chessBoard = new JPanel();
			fenster.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
			chessBoard.setLayout( new GridLayout(8, 8) );
			chessBoard.setBounds(0, 0, 800, 800);
			
			for(int i=0; i<=7; i++){
				for(int j=0; j<=7; j++){
					Square square = new Square(j,i);
					
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
		
		public Square findSquareByPos(int x, int y){
			Square foundSquare = null;
			for(int i=0; i<64; i++){
				if(chessBoard.getComponent(i) instanceof Square){
					Square square = (Square) chessBoard.getComponent(i);
					if(square.getxPos()==x && square.getyPos()==y){
						foundSquare = square;
					}
				}
			}
			return foundSquare;
		}
		
		public void setMouseMoveListen(MouseMotionListener m){
			fenster.addMouseMotionListener(m);
		}
		
		public void setMouseListen(MouseListener m){
			fenster.addMouseListener(m);
		}
		
		public void movePiece(ChessPiece chessPiece, int x, int y){
			chessPiece.setLocation(x, y);
		}


}
