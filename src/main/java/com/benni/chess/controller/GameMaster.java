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

import main.java.gui.MainView;
import main.java.gui.ChessPiece;
import main.java.gui.Square;
import main.java.model.*;


public class GameMaster {
	
	private final MainView _view;
	private final Saver saver;
	private final Analyser analyse;
	private final Controller control;
	private final Board _modelBoard;
	private int xAdjustment;
	private int yAdjustment;
	private ChessPiece chessPiece;
	private Square startPanel;
	private Square endPanel;
	
	//Saves
	public void saveBoard(){
		saver.saveBoardToXML(_modelBoard.getBoard());
	}
	
	public void savePlayers(){
		saver.savePlayersToXML(_modelBoard.getPlayersInGame());
	}
	
	public void saveOutList(){
		saver.saveOutListToXML(_modelBoard.getOutList());
	}
	
	//Loads
	public Piece[][] loadBoard(){
		return saver.loadBoardFromXML();
	}
	
	public Player[] loadPlayers(){
		return saver.loadPlayersFromXML();
	}
	
	public ArrayList<Piece> loadOutList(){
		return saver.loadOutListFromXML();
	}
	
	//Start und Reset
	public void resetBoard() {
		_modelBoard.setBoard(control.generateBoard());	
	}

	public void startGame() {
		_modelBoard.generatePlayers();
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
			Piece[][] board = _modelBoard.getBoard();
			chessPiece = null;
			//Piece[][] board = _modelBoard.getBoard();
			Component c =  _view.getChessBoard().findComponentAt(e.getX(), e.getY());
	 
			if (c instanceof JPanel) return;
			if( c == null) return;
			Point startLocation = c.getParent().getLocation();
			xAdjustment = startLocation.x - e.getX();
			yAdjustment = startLocation.y - e.getY();
			startPanel = (Square) c.getParent();
			int xViewStart = startPanel.getxPos();
			int yViewStart = startPanel.getyPos();
			//Pruefe ob Figur an dieser Stelle
			if(analyse.testIfEmpty(xViewStart, yViewStart, board)){
				System.out.println("Keine Figur an dieser Stelle!");
			//Pruefe ob eigene Figur
			}else if(analyse.testIfEnemy(_modelBoard.getPlayerOnTurn().isOwner(), xViewStart, yViewStart, board)){
				System.out.println("Das ist keine von deinen Figuren!");
			//Sonst bewegen
			}else{
				for(String entry: control.getPositionOfPosDestSquares(_modelBoard.getPieceOnBoard(xViewStart, yViewStart), xViewStart, yViewStart, board, _modelBoard.getPlayerOnTurn().isOwner())){
					String[] parts = entry.split(",");
			        parts[0] = parts[0].trim();
					parts[1] = parts[1].trim();
					int xOut = Integer.parseInt(parts[0]);
					int yOut = Integer.parseInt(parts[1]);
					_view.findSquareByPos(xOut, yOut).setBackground(Color.GREEN);
					if(!analyse.testIfEmpty(xOut, yOut, board)){
						_view.findSquareByPos(xOut, yOut).setBackground(Color.RED);
					}
				}
				chessPiece = (ChessPiece) c;
				 int x = e.getX() + xAdjustment;
				 int y = e.getY() + yAdjustment;
				_view.movePiece(chessPiece, x, y);
				_view.getFenster().add(chessPiece, JLayeredPane.DRAG_LAYER);
			}
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			Piece[][] board = _modelBoard.getBoard();
			if (chessPiece == null) return;
			_view.resetColorSquares();
			chessPiece.setVisible(false);
			//Ziel-Feld holen
			Component c =  _view.getChessBoard().findComponentAt(e.getX(), e.getY());
			if (c instanceof JLabel){
				Container end = c.getParent();
				endPanel = (Square) end;
			}else{
				Container end = (Container)c;
				endPanel = (Square) end;
			}
			
			int xViewStart = startPanel.getxPos();
			int yViewStart = startPanel.getyPos();
			int xViewEnd = endPanel.getxPos();
			int yViewEnd = endPanel.getyPos();
			
			if(control.macheZug(_modelBoard.getPieceOnBoard(xViewStart, yViewStart), xViewEnd, yViewEnd, _modelBoard)){
				_view.updateBoard(board);
				control.changePlayers(_modelBoard);
			}else{
				_view.updateBoard(board);
			}

			if(xViewStart != xViewEnd && yViewStart != yViewEnd){
				//Pruefe auf Schachmatt des Spielers der jetzt dran ist
				if(analyse.testIfOwnerPutsInCheck(board, _modelBoard.getPlayerNotOnTurn().isOwner()) && control.testMovesForCheckMate(_modelBoard.getPlayerOnTurn().isOwner(), _modelBoard)){
					System.out.println(_modelBoard.getPlayerNotOnTurn().getColor()+" setzt "+_modelBoard.getPlayerOnTurn().getColor()+" Schachmatt!");
					//gameEnded = true;
				}
				
				//Pruefe auf Schach des Spielers der jetzt dran ist
				if(analyse.testIfOwnerPutsInCheck(board, _modelBoard.getPlayerNotOnTurn().isOwner())){
					System.out.println("Schach!");
				}
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
	
	/*
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
			
			//Pr�fe ob Figur an dieser Stelle
			if(control.getPieceOnBoard(sLetter, sNum, board)==null){
				System.out.println("Keine Figur an dieser Stelle!");
				//viewer.zeigeSpielBrett(board);
				this.listenUser();
				
			//Pr�fe ob eigene Figur
			}else if(control.getPieceOnBoard(sLetter, sNum, board).isOwner()!=playerOnTurn.isOwner()){
				System.out.println("Das ist keine von deinen Figuren!");
				//viewer.zeigeSpielBrett(board);
				this.listenUser();
			
			//ist eigene und vorhandene Figur, f�hre Zug durch
			}else{
				boolean reverse = false;
				boolean gameEnded = false;
				//Pr�fung auf Schachmatt
				if(playerOnTurn.isImSchach() && control.getPieceOnBoard(sLetter, sNum, board) instanceof King){
					King king = (King) control.getPieceOnBoard(sLetter, sNum, board);
					if(king.getPossibleMoveDestinations(sLetter, sNum).isEmpty()){
						System.out.println("Schachmatt! "+playerOnTurn.getName()+" hat verloren");
					}
				}
				//Pr�fung auf Schach
				else if(playerOnTurn.isImSchach() && !(control.getPieceOnBoard(zLetter, zNum, board) instanceof King)){
					if(control.bewegeFigur(control.getPieceOnBoard(zLetter, zNum, board), sLetter, sNum, board, outPieces)==false){
						System.out.println("Reverse nicht m�glich! Schwerwiegender Fehler!");
					}else{
						System.out.println("Du stehst im Schach!");
						reverse =true;
					}	
					
				//Bewegung durchf�hren
				}else if(control.bewegeFigur(control.getPieceOnBoard(sLetter, sNum, board), zLetter, zNum, board, outPieces)){
					
					//Pr�fe ob man selbst im Schach steht nach dem Zug
					if(analyse.testIfOwnerPutsInCheck(board, playerNotOnTurn.isOwner())){
						if(control.bewegeFigur(control.getPieceOnBoard(zLetter, zNum, board), sLetter, sNum, board, outPieces)==false){
							System.out.println("Reverse nicht m�glich! Schwerwiegender Fehler!");
						}else{
							System.out.println("Du w�rdest nach diesem Zug im Schach stehen!");
							reverse =true;
						}
					}	

					//Pr�fe auf Schachmatt des Spielers der nicht dran ist
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
				//Wei�
				if(playerOnTurn.isOwner() == true){
					control.doKurzeRochade(true, board);
					control.changePlayers(playersInGame);
				//Schwarz	
				}else{
					control.doKurzeRochade(false, board);
					control.changePlayers(playersInGame);
				}
			}else{
				System.out.println("kurze Rochade nicht m�glich. Figuren im Weg oder an falscher Position!");
			}
			//viewer.zeigeSpielBrett(board);
			this.listenUser();
			
		}else if(command.getCommand().equals(CommandConst.RL)){
			//Rochade Lang
			if(analyse.rochadeLongPossible(playerOnTurn.isOwner(), board)){
				//Wei�
				if(playerOnTurn.isOwner() == true){
					control.doLangeRochade(true, board);
					control.changePlayers(playersInGame);
					//Schwarz
				}else{
					
					control.doLangeRochade(false, board);
					control.changePlayers(playersInGame);
				}
			}else{
				System.out.println("lange Rochade nicht m�glich. Figuren im Weg oder an falscher Position!");
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
			
		}
	}
	
	*/
	
	public GameMaster(){
		saver = new Saver();
		analyse = new Analyser();
		control = new Controller();
		_modelBoard = new Board();
		if (new File("Board.xml").exists() && new File("Players.xml").exists() && new File("Outs.xml").exists()) {
        	System.out.println("Gespeichertes Spiel gefunden. Lade...");
        	_modelBoard.setBoard(this.loadBoard());
        	_modelBoard.setPlayersInGame(this.loadPlayers());
        	_modelBoard.setOutList(this.loadOutList());
        	System.out.println("Spiel geladen.");
        	//view.zeigeSpielBrett(board);
        } else {
        	System.out.println("Kein gespeichertes Spiel gefunden. Beginne Neues...");
        	this.startGame();
        	this.resetBoard();
        	System.out.println("Neues Spiel gestartet. Viel Spass.");
        	//view.zeigeSpielBrett(board);
        }
		String dran = _modelBoard.getPlayerOnTurn().getName();
		String dranColor = _modelBoard.getPlayerOnTurn().getColor();
		System.out.println(dran+" - "+dranColor+" ist dran!");
		_view = new MainView(_modelBoard.getBoard());
		this.addListener();

	}
	
	public static void main(String[] args) {
		
		new GameMaster();
		
			
	}
}
