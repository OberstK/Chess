package main.java.controller;


import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import main.java.gui.Board;
import main.java.gui.ChessPiece;
import main.java.gui.Square;
import main.java.gui.Viewer;
import main.java.pieces.*;
import main.java.players.Player;


public class GameMaster {
	
	private final Board _view;
	private final CommandListener listener;
	private final Saver saver;
	private final Analyser analyse;
	private final Controller control;
	private int xAdjustment;
	private int yAdjustment;
	private ChessPiece chessPiece;
	private Square startPanel;
	private Square endPanel;
	
	
	public static Piece[][] board = new Piece[8][8];
	public static Player[] playersInGame = new Player[2];
	public static ArrayList<Piece> outPieces= new ArrayList<Piece>();
	
	
	//Save and Load
	public void saveBoard(){
		saver.saveBoardToXML(board);
	}
	
	public Piece[][] loadBoard(){
		return saver.loadBoardFromXML();
	}
	
	public void savePlayers(){
		saver.savePlayersToXML(playersInGame);
	}
	
	public Player[] loadPlayers(){
		return saver.loadPlayersFromXML();
	}
	
	public void resetBoard() {
		board = control.generateBoard();	
	}

	public void startGame() {
		playersInGame = control.generatePlayers();
	}
	
	
	class MyMouseListener implements MouseListener{
		
		
		
		
		@Override
		public void mouseClicked(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {
			chessPiece = null;
			Component c =  _view.getChessBoard().findComponentAt(e.getX(), e.getY());
	 
			if (c instanceof JPanel) return;
			Point startLocation = c.getParent().getLocation();
			xAdjustment = startLocation.x - e.getX();
			yAdjustment = startLocation.y - e.getY();
			startPanel = (Square) c.getParent();
			int xViewStart = startPanel.getxPos();
			int yViewStart = startPanel.getyPos();
			
			for(String entry: control.getPositionOfPosDestSquares(control.getPieceOnBoard(xViewStart, yViewStart , board), xViewStart, yViewStart, board, control.getPlayerOnTurn(playersInGame).isOwner())){
				String[] parts = entry.split(",");
		        parts[0] = parts[0].trim();
				parts[1] = parts[1].trim();
				int xOut = Integer.parseInt(parts[0]);
				int yOut = Integer.parseInt(parts[1]);
				_view.findSquareByPos(xOut, yOut).setBackground(Color.GREEN);
			}
			

			//Prüfe ob Figur an dieser Stelle
			if(analyse.testIfEmpty(xViewStart, yViewStart, board)){
				System.out.println("Keine Figur an dieser Stelle!");
			//Prüfe ob eigene Figur
			}else if(analyse.testIfEnemy(control.getPlayerOnTurn(playersInGame).isOwner(), xViewStart, yViewStart, board)){
				System.out.println("Das ist keine von deinen Figuren!");
			//Sonst bewegen
			}else{
				chessPiece = (ChessPiece) c;
				 int x = e.getX() + xAdjustment;
				 int y = e.getY() + yAdjustment;
				_view.movePiece(chessPiece, x, y);
				_view.getFenster().add(chessPiece, JLayeredPane.DRAG_LAYER);
			}
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (chessPiece == null) return;
			 
			chessPiece.setVisible(false);
			Component c =  _view.getChessBoard().findComponentAt(e.getX(), e.getY());
			
			int xViewStart = startPanel.getxPos();
			int yViewStart = startPanel.getyPos();
			
			if (c instanceof JLabel)
			{
				Container parent = c.getParent();
				endPanel = (Square) parent;
				int xViewEnd = endPanel.getxPos();
				int yViewEnd = endPanel.getyPos();
				if(control.bewegeFigur(control.getPieceOnBoard(xViewStart, yViewStart , board), xViewEnd, yViewEnd, board, outPieces)){
					if(analyse.testIfOwnerPutsInCheck(board, control.getPlayerNotOnTurn(playersInGame).isOwner())){
						System.out.println("Nach dem Zug stehst du im Schach!");
					}
					parent.remove(0);
					parent.add(chessPiece);
					control.changePlayers(playersInGame);
				}else{
					startPanel.add(chessPiece);
				}
			}
			else
			{
				Container parent = (Container)c;
				endPanel = (Square) parent;
				int xViewEnd = endPanel.getxPos();
				int yViewEnd = endPanel.getyPos();
				if(control.bewegeFigur(control.getPieceOnBoard(xViewStart, yViewStart , board), xViewEnd, yViewEnd, board, outPieces)){
					parent.add(chessPiece);
					control.changePlayers(playersInGame);
				}else{
					startPanel.add(chessPiece);
				}
			}
			chessPiece.setVisible(true);
			
			
			//Prüfe auf Schachmatt des Spielers der jetzt dran ist
			if(analyse.testIfOwnerPutsInCheck(board, control.getPlayerNotOnTurn(playersInGame).isOwner()) && control.testMovesForCheckMate(control.getPlayerOnTurn(playersInGame).isOwner(), board)){
				System.out.println(control.getPlayerNotOnTurn(playersInGame).getColor()+" setzt "+control.getPlayerOnTurn(playersInGame).getColor()+" Schachmatt!");
				//gameEnded = true;
			}
			
			//Prüfe auf Schach des Spielers der jetzt dran ist
			if(analyse.testIfOwnerPutsInCheck(board, control.getPlayerNotOnTurn(playersInGame).isOwner())){
				System.out.println("Schach!");
			}
		}
	}
	
	class MyMouseMoveListener implements MouseMotionListener{
		
		@Override
		public void mouseDragged(MouseEvent e) {
			if (chessPiece == null) return;
			 int x = e.getX() + xAdjustment;
			 int y = e.getY() + yAdjustment;
			 _view.movePiece(chessPiece, x, y);
		}
		@Override
		public void mouseMoved(MouseEvent e) {}

	}
	
	public void addListener(){
		this._view.setMouseListen(new MyMouseListener());
		this._view.setMouseMoveListen(new MyMouseMoveListener());
	}
	
	
	public void listenUser(){

		Command command = listener.scanInput();
		//Wer ist dran?
		Player playerOnTurn = new Player();
		Player playerNotOnTurn = new Player();
		for(int i=0; i<=1; i++){
			if(playersInGame[i].isOnTurn()){
				playerOnTurn = playersInGame[i];
			}else{
				playerNotOnTurn = playersInGame[i];
			}
		}

		
		//Befehlsinterpreter
		if(command == null){
			System.out.println("Befehl nicht verstanden!");
			this.listenUser();
			
			
		}else if(command.getCommand().equals(CommandConst.ZUG)){
			//Zug-Befehl
			String[] values = command.getValues();
			//Werte
			int sLetter = analyse.letterConverter(values[0]);
			int sNum = Integer.parseInt(values[1]);
			int zLetter = analyse.letterConverter(values[2]);
			int zNum = Integer.parseInt(values[3]);
			
			//Prüfe ob Figur an dieser Stelle
			if(control.getPieceOnBoard(sLetter, sNum, board)==null){
				System.out.println("Keine Figur an dieser Stelle!");
				//viewer.zeigeSpielBrett(board);
				this.listenUser();
				
			//Prüfe ob eigene Figur
			}else if(control.getPieceOnBoard(sLetter, sNum, board).isOwner()!=playerOnTurn.isOwner()){
				System.out.println("Das ist keine von deinen Figuren!");
				//viewer.zeigeSpielBrett(board);
				this.listenUser();
			
			//ist eigene und vorhandene Figur, führe Zug durch
			}else{
				boolean reverse = false;
				boolean gameEnded = false;
				//Prüfung auf Schachmatt
				if(playerOnTurn.isImSchach() && control.getPieceOnBoard(sLetter, sNum, board) instanceof King){
					King king = (King) control.getPieceOnBoard(sLetter, sNum, board);
					if(king.getPossibleMoveDestinations(sLetter, sNum).isEmpty()){
						System.out.println("Schachmatt! "+playerOnTurn.getName()+" hat verloren");
					}
				}
				//Prüfung auf Schach
				else if(playerOnTurn.isImSchach() && !(control.getPieceOnBoard(zLetter, zNum, board) instanceof King)){
					if(control.bewegeFigur(control.getPieceOnBoard(zLetter, zNum, board), sLetter, sNum, board, outPieces)==false){
						System.out.println("Reverse nicht möglich! Schwerwiegender Fehler!");
					}else{
						System.out.println("Du stehst im Schach!");
						reverse =true;
					}	
					
				//Bewegung durchführen
				}else if(control.bewegeFigur(control.getPieceOnBoard(sLetter, sNum, board), zLetter, zNum, board, outPieces)){
					
					//Prüfe ob man selbst im Schach steht nach dem Zug
					if(analyse.testIfOwnerPutsInCheck(board, playerNotOnTurn.isOwner())){
						if(control.bewegeFigur(control.getPieceOnBoard(zLetter, zNum, board), sLetter, sNum, board, outPieces)==false){
							System.out.println("Reverse nicht möglich! Schwerwiegender Fehler!");
						}else{
							System.out.println("Du würdest nach diesem Zug im Schach stehen!");
							reverse =true;
						}
					}	

					//Prüfe auf Schachmatt des Spielers der nicht dran ist
					if(analyse.testIfOwnerPutsInCheck(board, playerOnTurn.isOwner()) && control.testMovesForCheckMate(playerNotOnTurn.isOwner(), board)){
						System.out.println("Schachmatt!");
						gameEnded = true;
					}
					//Schach-Var setzen wenn im Schach
					else if(analyse.testIfOwnerPutsInCheck(board, playerOnTurn.isOwner())){
						playerOnTurn.setImSchach(playerOnTurn.isOwner());
						System.out.println(playerNotOnTurn.getColor()+" im Schach!");
					}else{
						playerOnTurn.setImSchach(false);
					}
					
					if(playerOnTurn.isOwner()){
						playersInGame[0] = playerOnTurn;
					}else{
						playersInGame[1] = playerOnTurn;
					}
				}else{
					//viewer.zeigeSpielBrett(board);
					this.listenUser();
				}
				
				if(gameEnded){
					System.out.println(playerOnTurn.getName()+" hat gewonnen! Programm wird beendet");
					//viewer.zeigeSpielBrett(board);
				}else{
					if(reverse){
						//viewer.zeigeSpielBrett(board);
						this.listenUser();
					}else{
						control.changePlayers(playersInGame);
						//viewer.zeigeSpielBrett(board);
						this.listenUser();
					}
				}
			}
	
			
		}else if(command.getCommand().equals(CommandConst.ABL)){
			//Ablage-Befehl
			System.out.println("Geschlagene Figuren:");
			for(Piece item: outPieces){
				System.out.print(item.getSymbol()+", ");
			}
			System.out.println("\n");
			
			this.listenUser();
			
			
		}else if(command.getCommand().equals(CommandConst.RS)){
			//Rochade kurz
			if(analyse.rochadeShortPossible(playerOnTurn.isOwner(), board)){
				//Weiß
				if(playerOnTurn.isOwner() == true){
					control.doKurzeRochade(true, board);
					control.changePlayers(playersInGame);
				//Schwarz	
				}else{
					control.doKurzeRochade(false, board);
					control.changePlayers(playersInGame);
				}
			}else{
				System.out.println("kurze Rochade nicht möglich. Figuren im Weg oder an falscher Position!");
			}
			//viewer.zeigeSpielBrett(board);
			this.listenUser();
			
		}else if(command.getCommand().equals(CommandConst.RL)){
			//Rochade Lang
			if(analyse.rochadeLongPossible(playerOnTurn.isOwner(), board)){
				//Weiß
				if(playerOnTurn.isOwner() == true){
					control.doLangeRochade(true, board);
					control.changePlayers(playersInGame);
					//Schwarz
				}else{
					
					control.doLangeRochade(false, board);
					control.changePlayers(playersInGame);
				}
			}else{
				System.out.println("lange Rochade nicht möglich. Figuren im Weg oder an falscher Position!");
			}
			//viewer.zeigeSpielBrett(board);
			this.listenUser();
			
		}else if(command.getCommand().equals(CommandConst.EXIT)){
			//Exit-Befehl
			this.savePlayers();
			this.saveBoard();
			System.out.println("Spiel gesichert und beendet");
			
		}else if(command.getCommand().equals(CommandConst.NEW)){
			//New Game Befehl
			this.resetBoard();
			this.startGame();
			System.out.println("Neues Spiel gestartet");
			//viewer.zeigeSpielBrett(board);
			this.listenUser();
			
		}else if(command.getCommand().equals(CommandConst.ERR)){
			//Error-Befehl
			System.out.println("Fehler! Befehlsformat einhalten");
			this.listenUser();
		}else{
			//Fehlerbehandlung
			System.out.println("Fehler!");
		}
	}
	
	public GameMaster(){
		saver = new Saver();
		analyse = new Analyser();
		control = new Controller();
		if (new File("Board.xml").exists() && new File("Players.xml").exists()) {
        	System.out.println("Gespeichertes Spiel gefunden. Lade...");
        	board = this.loadBoard();
        	playersInGame = this.loadPlayers();
        	System.out.println("Spiel geladen.");
        	//view.zeigeSpielBrett(board);
        } else {
        	System.out.println("Kein gespeichertes Spiel gefunden. Beginne Neues...");
        	this.startGame();
        	this.resetBoard();
        	System.out.println("Neues Spiel gestartet. Viel Spass.");
        	//view.zeigeSpielBrett(board);
        }
		String dran = control.getPlayerOnTurn(playersInGame).getName();
		String dranColor = control.getPlayerOnTurn(playersInGame).getColor();
		System.out.println(dran+" - "+dranColor+" ist dran!");
		_view = new Board(board);
		addListener();
		listener = new CommandListener();

	}
	
	public static void main(String[] args) {
		
		new GameMaster();
		
			
	}
}
