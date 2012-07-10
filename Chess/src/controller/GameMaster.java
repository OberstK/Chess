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
		boolean ownerOnTurn = true;
		for(int i=0; i<=1; i++){
			if(spieler[i].isOnTurn()){
				ownerOnTurn = spieler[i].isOwner();
			}
		}
		
		//Befehlsinterpreter
		if(command == null){
			System.out.println("Befehl nicht verstanden!");
			this.listenUser();
			
			
		}else if(command.getCommand().equals(CommandConst.ZUG)){
			//Zug-Befehl
			String[] values = command.getValues();
			
			
			if(control.getFigurOnBoard(analyse.letterConverter(values[0]), Integer.parseInt(values[1]), board)==null){
				System.out.println("Keine Figur an dieser Stelle!");
				viewer.zeigeSpielBrett(board);
				this.listenUser();
			}else if(control.getFigurOnBoard(analyse.letterConverter(values[0]), Integer.parseInt(values[1]), board).isOwner()!=ownerOnTurn){
				System.out.println("Das ist keine von deinen Figuren!");
				viewer.zeigeSpielBrett(board);
				this.listenUser();
			}else{
				if(this.bewegeFigur(control.getFigurOnBoard(analyse.letterConverter(values[0]), Integer.parseInt(values[1]), board), analyse.letterConverter(values[2]), Integer.parseInt(values[3]))){
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

				viewer.zeigeSpielBrett(board);
				this.listenUser();
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
			if(analyse.rochadeShortPossible(ownerOnTurn, board)){
				//Weiss
				if(ownerOnTurn = true){
					Rock rock = (Rock) control.getFigurOnBoard(7, 8, board);
					King king = (King) control.getFigurOnBoard(4, 8, board);
					king.setPositionX(6);
					rock.setPositionX(5);
				}else{
					Rock rock = (Rock) control.getFigurOnBoard(7, 1, board);
					King king = (King) control.getFigurOnBoard(4, 1, board);
					king.setPositionX(6);
					rock.setPositionX(5);
				}
			}else{
				System.out.println("kurze Rochade nicht mˆglich. Figuren im Weg oder an falscher Position!");
			}
			this.listenUser();
	
		}else if(command.getCommand().equals(CommandConst.RL)){
			//Rochade Lang
			

			
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
	
	public void startGame(){

		//Spieler 1
		Player player1 = new Player();
		player1.setName("Spieler 1");
		player1.setColor("Weiss");
		player1.setOnTurn(true);
		player1.setOwner(true);
		
		//Spieler 2
		Player player2 = new Player();
		player2.setName("Spieler 2");
		player2.setColor("Schwarz");
		player2.setOnTurn(false);
		player2.setOwner(false);

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
        	view.zeigeSpielBrett(board);
        } else {
        	System.out.println("Kein gespeichertes Spiel gefunden. Beginne Neues...");
        	chef.startGame();
        	chef.resetBoard();
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
