package controller;

import gui.Viewer;

import java.io.File;
import java.util.ArrayList;

import pieces.*;
import players.Player;

public class GameMaster {
	
	private final Viewer viewer;
	private final CommandListener listener;
	private final Saver saver;
	private final Analyser analyse;
	private final Controller control;
	
	
	public static Piece[][] board = new Piece[8][8];
	public static Player[] spieler = new Player[2];
	public static ArrayList<Piece> geschlagene= new ArrayList<Piece>();
	
	
	//Save and Load
	public void saveBoard(){
		saver.saveBoardToXML(board);
	}
	
	public Piece[][] loadBoard(){
		return saver.loadBoardFromXML();
	}
	
	public void savePlayers(){
		saver.savePlayersToXML(spieler);
	}
	
	public Player[] loadPlayers(){
		return saver.loadPlayersFromXML();
	}
	
	
	public void makeMove(String sLetter, int sNum, String zLetter, int zNum){
		this.bewegeFigur(control.getFigurOnBoard(analyse.letterConverter(sLetter), sNum, board), analyse.letterConverter(zLetter), zNum);
	}
	
	public void schlageFigur(int xPos, int yPos){
		geschlagene.add(board[yPos][xPos]);
	}
	
	public boolean bewegeFigur(Piece figur, int letter, int num){
		boolean success= false;
		boolean test = false;
		int xPosEnd = letter;
		int yPosEnd = num-1; //weil Array bei 0 anf‰ngt, aber Schachbrett bei 1

		int xPosStart = figur.getPositionX();
		int yPosStart = figur.getPositionY();
		
		if(figur instanceof Knight){
			Knight knight = (Knight) figur;
			if(knight.movePossible(xPosStart, yPosStart, xPosEnd, yPosEnd)){
				test = true;
			}
		}else if(figur instanceof Queen){
			Queen queen = (Queen) figur;
			if(queen.movePossible(xPosStart, yPosStart, xPosEnd, yPosEnd, board)){
				test = true;
			}
		}else if(figur instanceof Pawn){
			Pawn pawn = (Pawn) figur;
			if(pawn.movePossible(xPosStart, yPosStart, xPosEnd, yPosEnd, board, pawn.isOwner())){
				test = true;
			}else if(pawn.hitPossible(xPosStart, yPosStart, xPosEnd, yPosEnd, board, pawn.isOwner())){
				test = true;
			}
		}else if(figur instanceof Bishop){
			Bishop bishop = (Bishop) figur;
			if(bishop.movePossible(xPosStart, yPosStart, xPosEnd, yPosEnd, board)){
				test = true;
			}
		}else if(figur instanceof Rock){
			Rock rock = (Rock) figur;
			if(rock.movePossible(xPosStart, yPosStart, xPosEnd, yPosEnd, board)){
				test=true;
			}
		}else if(figur instanceof King){
			King king = (King) figur;
			if(king.movePossible(xPosStart, yPosStart, xPosEnd, yPosEnd)){
				test=true;
			}
		}
		if(test){
			if(control.pruefeZielLeer(xPosEnd, yPosEnd, board)){
				figur.setPositionX(xPosEnd);
				figur.setPositionY(yPosEnd);
				control.loescheAltePos(yPosStart, xPosStart, board);
				control.setzeFigur(figur, board);
				success=true;
				return success;
			}else if(control.pruefeZielGegner(figur.isOwner(), xPosEnd, yPosEnd, board)){
				this.schlageFigur(xPosEnd, yPosEnd);
				figur.setPositionX(xPosEnd);
				figur.setPositionY(yPosEnd);
				control.loescheAltePos(yPosStart, xPosStart, board);
				control.setzeFigur(figur, board);		
				success=true;
				return success;
			}else{
				System.out.println("Feld belegt!");
				success=false;
				return success;
			}
		}else{
			System.out.println("Dieser Zug ist nicht g¸ltig!");
			success=false;
			return success;
		}
	}
	
	public Piece[][] aufstellungGenerieren(){
		ArrayList<Piece> figuren = new ArrayList<Piece>();
		
		//Weiﬂe Figuren
		figuren.add(new King("weiﬂ"));
		figuren.add(new Queen("weiﬂ"));
		figuren.add(new Knight("weiﬂ", 1));
		figuren.add(new Knight("weiﬂ", 2));
		figuren.add(new Bishop("weiﬂ", 1));
		figuren.add(new Bishop("weiﬂ", 2));
		figuren.add(new Rock("weiﬂ", 1));
		figuren.add(new Rock("weiﬂ", 2));
		for(int i=0; i<8; i++){
			figuren.add(new Pawn("weiﬂ", i));
		}
		
		//Schwarze Figuren
		figuren.add(new King("schwarz"));
		figuren.add(new Queen("schwarz"));
		figuren.add(new Knight("schwarz", 1));
		figuren.add(new Knight("schwarz", 2));
		figuren.add(new Bishop("schwarz", 1));
		figuren.add(new Bishop("schwarz", 2));
		figuren.add(new Rock("schwarz", 1));
		figuren.add(new Rock("schwarz", 2));
		for(int i=0; i<8; i++){
			figuren.add(new Pawn("schwarz", i));
		}
		
		//Setzen
		for(Piece figur: figuren){
			control.setzeFigur(figur, board);
		}
		
		//Leere Felder f¸llen
		
		for(int i=2; i<=5; i++){
			for(int j=0; j<=7;j++){
				board[i][j] = control.erzeugeLeeresFeld(j, i);
			}
		}
		
		return board;
	}
	
	public void listenUser(){

		Command command = listener.scanInput();
		//Wer ist dran?
		Player playerOnTurn = new Player();
		Player playerNotOnTurn = new Player();
		for(int i=0; i<=1; i++){
			if(spieler[i].isOnTurn()){
				playerOnTurn = spieler[i];
			}else{
				playerNotOnTurn = spieler[i];
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
			
			//Pr¸fe ob Figur an dieser Stelle
			if(control.getFigurOnBoard(sLetter, sNum, board)==null){
				System.out.println("Keine Figur an dieser Stelle!");
				viewer.zeigeSpielBrett(board);
				this.listenUser();
				
			//Pr¸fe ob eigene Figur
			}else if(control.getFigurOnBoard(sLetter, sNum, board).isOwner()!=playerOnTurn.isOwner()){
				System.out.println("Das ist keine von deinen Figuren!");
				viewer.zeigeSpielBrett(board);
				this.listenUser();
			
			//ist eigene und vorhandene Figur, f¸hre Zug durch
			}else{
				boolean reverse = false;
				boolean gameEnded = false;
				//Pr¸fung auf Schachmatt
				if(playerOnTurn.isImSchach() && control.getFigurOnBoard(sLetter, sNum, board) instanceof King){
					King king = (King) control.getFigurOnBoard(sLetter, sNum, board);
					if(king.getPossibleMoveDestinations(sLetter, sNum).isEmpty()){
						System.out.println("Schachmatt! "+playerOnTurn.getName()+" hat verloren");
					}
				}
				//Pr¸fung auf Schach
				else if(playerOnTurn.isImSchach() && !(control.getFigurOnBoard(zLetter, zNum, board) instanceof King)){
					if(this.bewegeFigur(control.getFigurOnBoard(zLetter, zNum, board), sLetter, sNum)==false){
						System.out.println("Reverse nicht mˆglich! Schwerwiegender Fehler!");
					}else{
						System.out.println("Du stehst im Schach!");
						reverse =true;
					}	
					
				//Bewegung durchf¸hren
				}else if(this.bewegeFigur(control.getFigurOnBoard(sLetter, sNum, board), zLetter, zNum)){
					
					//Pr¸fe ob man selbst im Schach steht nach dem Zug
					if(control.pruefeAufOwnerImSchach(board, !playerOnTurn.isOwner())){
						if(this.bewegeFigur(control.getFigurOnBoard(zLetter, zNum, board), sLetter, sNum)==false){
							System.out.println("Reverse nicht mˆglich! Schwerwiegender Fehler!");
						}else{
							System.out.println("Du w¸rdest nach diesem Zug im Schach stehen!");
							reverse =true;
						}
					}	

					//Pr¸fe auf Schachmatt des Spielers der nicht dran ist
					if(control.testIfCheckMate(!playerOnTurn.isOwner(), board)){
						System.out.println("Schachmatt!");
						gameEnded = true;
					}
					//Schach-Var setzen wenn im Schach
					else if(control.pruefeAufOwnerImSchach(board, playerOnTurn.isOwner())){
						playerOnTurn.setImSchach(playerOnTurn.isOwner());
						System.out.println(playerNotOnTurn.getColor()+" im Schach!");
					}else{
						playerOnTurn.setImSchach(false);
					}
					
					if(playerOnTurn.isOwner()){
						spieler[0] = playerOnTurn;
					}else{
						spieler[1] = playerOnTurn;
					}
				}
				
				if(gameEnded){
					System.out.println(playerOnTurn.getName()+" hat gewonnen! Programm wird beendet");
				}else{
					if(reverse){
						viewer.zeigeSpielBrett(board);
						this.listenUser();
					}else{
						this.changePlayers();
						viewer.zeigeSpielBrett(board);
						this.listenUser();
					}
				}

			}
			
			
		}else if(command.getCommand().equals(CommandConst.ABL)){
			//Ablage-Befehl
			System.out.println("Geschlagene Figuren:");
			for(Piece item: geschlagene){
				System.out.print(item.getSymbol()+", ");
			}
			System.out.println("\n");
			
			this.listenUser();
			
			
		}else if(command.getCommand().equals(CommandConst.RS)){
			//Rochade kurz
			if(analyse.rochadeShortPossible(playerOnTurn.isOwner(), board)){
				//Weiss
				if(playerOnTurn.isOwner() == true){
					control.doKurzeRochade(true, board);
					this.changePlayers();
				//Schwarz	
				}else{
					control.doKurzeRochade(false, board);
					this.changePlayers();
				}
			}else{
				System.out.println("kurze Rochade nicht mˆglich. Figuren im Weg oder an falscher Position!");
			}
			viewer.zeigeSpielBrett(board);
			this.listenUser();
			
		}else if(command.getCommand().equals(CommandConst.RL)){
			//Rochade Lang
			if(analyse.rochadeLongPossible(playerOnTurn.isOwner(), board)){
				//Weiss
				if(playerOnTurn.isOwner() == true){
					control.doLangeRochade(true, board);
					this.changePlayers();
					//Schwarz
				}else{
					
					control.doLangeRochade(false, board);
					this.changePlayers();
				}
			}else{
				System.out.println("lange Rochade nicht mˆglich. Figuren im Weg oder an falscher Position!");
			}
			viewer.zeigeSpielBrett(board);
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
			viewer.zeigeSpielBrett(board);
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
	
	public boolean weissAmZug(){
		
		if(spieler[0].isOnTurn()){
			return true;
		}
		
		return false;
	}
	
	
	public void changePlayers(){
		//Spieler wechseln
		for(int i=0; i<=1; i++){
			spieler[i].setOnTurn(!(spieler[i].isOnTurn()));
		}
		if(this.weissAmZug()){
			System.out.println(spieler[0].getName()+" - "+spieler[0].getColor()+" ist am Zug");
		}else{
			System.out.println(spieler[1].getName()+" - "+spieler[1].getColor()+" ist am Zug");
		}
	}
	public void startGame(){

		//Spieler 1
		Player player1 = new Player("Spieler 1", true);
		
		//Spieler 2
		Player player2 = new Player("Spieler 2", false);

		spieler[0] = player1;
		spieler[1] = player2;

	}
	
	public void resetBoard(){
		this.aufstellungGenerieren();
	}
	
	public GameMaster(){
		viewer = new Viewer();
		listener = new CommandListener();
		saver = new Saver();
		analyse = new Analyser();
		control = new Controller();
	}
	
	public static void main(String[] args) {
		
		GameMaster chef = new GameMaster();
		Viewer view = new Viewer();
		
        if (new File("Board.xml").exists() && new File("Players.xml").exists()) {
        	System.out.println("Gespeichertes Spiel gefunden. Lade...");
        	board = chef.loadBoard();
        	spieler = chef.loadPlayers();
        	System.out.println("Spiel geladen.");
        	view.zeigeSpielBrett(board);
        } else {
        	System.out.println("Kein gespeichertes Spiel gefunden. Beginne Neues...");
        	chef.startGame();
        	chef.resetBoard();
        	System.out.println("Neues Spiel gestartet. Viel Spass.");
        	view.zeigeSpielBrett(board);
        }
		
        if(spieler[0].isOnTurn()){
        	System.out.println("Spieler 1 - Weiﬂ ist am Zug");
        }else{
        	System.out.println("Spieler 2 - Schwarz ist am Zug");
        }
		chef.listenUser();
			
	}

}
