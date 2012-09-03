package main.java.gui;

import java.awt.Color;
import java.awt.Component;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import main.java.model.Piece;



public class MainView extends JFrame{
	private JLayeredPane boardPane;
	private ChessPiece chessPiece; //JLabel
	private JPanel chessBoard;
	//private JPanel outBoard;
	
	public JLayeredPane getFenster() {
		return boardPane;
	}

	public void setFenster(JLayeredPane fenster) {
		this.boardPane = fenster;
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
			this.setBounds(0, 0, 1150, 870);
			boardPane = new JLayeredPane();
			boardPane.setBounds(100, 100,800,800);
			getContentPane().add(boardPane);
			
			this.addMainMenuBar();
			
			chessBoard = new JPanel();
			chessBoard.setLayout( new GridLayout(8, 8) );
			chessBoard.setBounds(0, 0, 800,800);
			boardPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);

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
			/*
			int pawnCount = 0;
			Square pawnSquare = new Square();
			outBoard = new JPanel();
			outBoard.setLayout(new GridLayout(16,2));
			outBoard.setBounds(810, 10, 300, 790);
			boardPane.add(outBoard, JLayeredPane.DEFAULT_LAYER);
			for(int i=7; i>=0; i--){
				for(int j=0; j<=7; j++){
					Square square = new Square(j,i);					
					if(board[i][j].getType()!=null){
						if(board[i][j].getType()=="Pawn"){
							if(pawnCount==0){
								chessPiece = new ChessPiece(board[i][j].getType(), board[i][j].getColor());	
								square.add(chessPiece);
								pawnSquare = square;
								pawnCount++;
							}else{
								pawnCount++;
							}
						}
						//chessPiece = new ChessPiece(board[i][j].getType(), board[i][j].getColor());	
						//square.add(chessPiece);
						//outBoard.add( square );
					}
				}
			}
			pawnSquare.add(new JLabel(""+pawnCount+""));
			outBoard.add( pawnSquare );
			*/
			this.setVisible(true);
			
		}
		
		private void addMainMenuBar() {
			JMenuBar menuBar;
			JMenu menu;
			JMenuItem menuItem;

			//Create the menu bar.
			menuBar = new JMenuBar();

			//Build the first menu.
			menu = new JMenu("Menü");
			menu.setMnemonic(KeyEvent.VK_A);
			menu.getAccessibleContext().setAccessibleDescription(
			        "Das Hauptmenü");
			menuBar.add(menu);

			//a group of JMenuItems
			menuItem = new JMenuItem("Neues Spiel",
			                         KeyEvent.VK_N);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(
			        KeyEvent.VK_N, ActionEvent.ALT_MASK));
			menuItem.getAccessibleContext().setAccessibleDescription(
			        "Startet ein neues Spiel");
			menu.add(menuItem);
			
			menuItem = new JMenuItem("Spiel speichern",
                    KeyEvent.VK_S);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(
			   KeyEvent.VK_S, ActionEvent.ALT_MASK));
			menuItem.getAccessibleContext().setAccessibleDescription(
			   "Speichert das Spiel");
			menu.add(menuItem);
			
			menuItem = new JMenuItem("Spiel beenden",
                    KeyEvent.VK_E);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(
			   KeyEvent.VK_E, ActionEvent.ALT_MASK));
			menuItem.getAccessibleContext().setAccessibleDescription(
			   "Beendet das Spiel");
			menu.add(menuItem);

			//Build second menu in the menu bar.
			menu = new JMenu("About");
			menu.setMnemonic(KeyEvent.VK_N);
			menu.getAccessibleContext().setAccessibleDescription(
			        "About the author");
			menuBar.add(menu);

			this.setJMenuBar(menuBar);
			
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
			boardPane.addMouseMotionListener(m);
		}
		
		public void setMouseListen(MouseListener m){
			boardPane.addMouseListener(m);
		}
		
		public void movePiece(ChessPiece chessPiece, int x, int y){
			chessPiece.setLocation(x, y);
		}


}
