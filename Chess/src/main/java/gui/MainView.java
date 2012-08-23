package main.java.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import main.java.model.Piece;



public class MainView extends JFrame{
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

		public MainView(Piece[][] board){
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			fenster = new JLayeredPane();
			getContentPane().add(fenster);
			this.setSize(1100,950);
			
			fenster.add(new JButton("Neues Spiel"));
			fenster.add(new JButton("Speichern und Beenden"));
			
			chessBoard = new JPanel();
			chessBoard.setLayout( new GridLayout(8, 8) );
			chessBoard.setBounds(100, 100, 800, 800);
			chessBoard.setAlignmentX(MAXIMIZED_HORIZ);
			fenster.add(chessBoard, JLayeredPane.DEFAULT_LAYER);

			
			for(int i=7; i>=0; i--){
				for(int j=0; j<=7; j++){
					Square square = new Square(j,i);					
					if((j % 2==0 && i % 2 != 0) || (j% 2!=0 && i%2 ==0)){
						square.setBackground(Color.GRAY);
						square.setBackColor(Color.GRAY);
					}else{
						square.setBackground(Color.WHITE);
						square.setBackColor(Color.WHITE);
					}
					chessPiece = new ChessPiece(board[i][j].getType(), board[i][j].getColor());					
					square.add(chessPiece);
					chessBoard.add( square );
				}
			}
			this.setVisible(true);
		}
		
		public void updateBoard(Piece[][] board){
			Component[] c = chessBoard.getComponents();
			for(Component ele: c){
				if(ele instanceof Square){
					Square square = (Square) ele;
					square.removeAll();
					//System.out.println(square.getComponents());
					int x = square.getxPos();
					int y = square.getyPos();
					//System.out.println(square);
					square.add(new ChessPiece(board[y][x].getType(), board[y][x].getColor()));
				}
			}
			chessBoard.repaint();
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
		
		public void resetColorSquares(){
			for(int i=0; i<64; i++){
				if(chessBoard.getComponent(i) instanceof Square){
					Square square = (Square) chessBoard.getComponent(i);
					square.setBackground(square.getBackColor());
				}
			}
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
