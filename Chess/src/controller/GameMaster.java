package controller;

import gui.Viewer;

import java.io.File;
import java.util.ArrayList;

import pieces.*;


public class GameMaster {
	
	private final Viewer viewer;
	private final CommandListener listener;
	private final Saver saver;
	private final Analyser analyse;
	private final Controller control;
	
	
	public static Piece[][] board = new Piece[8][8];
	public static ArrayList<Piece> geschlagene= new ArrayList<Piece>();
	
	
	//Save and Load
	public void saveBoard(){
		saver.saveToXMl(board);
	}
	
	public Piece[][] loadBoard(){
		return saver.loadFromXL();
	}
	
	
	public void makeMove(String sLetter, int sNum, String zLetter, int zNum){
		this.bewegeFigur(control.getFigurOnBoard(analyse.letterConverter(sLetter), sNum, board), analyse.letterConverter(zLetter), zNum);
	}
	
	public void schlageFigur(int xPos, int yPos){
		geschlagene.add(board[yPos][xPos]);
	}
	
	public void bewegeFigur(Piece figur, int letter, int num){
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
			}else if(control.pruefeZielGegner(figur.isOwner(), xPosEnd, yPosEnd, board)){
				this.schlageFigur(xPosEnd, yPosEnd);
				figur.setPositionX(xPosEnd);
				figur.setPositionY(yPosEnd);
				control.loescheAltePos(yPosStart, xPosStart, board);
				control.setzeFigur(figur, board);		
			}else{
				System.out.println("Feld belegt!");
			}
		}else{
			System.out.println("Dieser Zug ist nicht g¸ltig!");
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
		
		return board;
	}
	
	public void listenUser(){

		Command command = listener.scanInput();
		
		if(command == null){
			System.out.println("Befehl nicht verstanden!");
			this.listenUser();
			
			//Zug-Befehl
		}else if(command.getCommand().equals(CommandConst.ZUG)){
			
			String[] values = command.getValues();
			
			if(control.getFigurOnBoard(analyse.letterConverter(values[0]), Integer.parseInt(values[1]), board)==null){
				System.out.println("Keine Figur an dieser Stelle!");
				viewer.zeigeSpielBrett(board);
				this.listenUser();
			}else{
			this.bewegeFigur(control.getFigurOnBoard(analyse.letterConverter(values[0]), Integer.parseInt(values[1]), board), analyse.letterConverter(values[2]), Integer.parseInt(values[3]));
			
			viewer.zeigeSpielBrett(board);
			
			this.listenUser();
			}
			
			//Ablage-Befehl
		}else if(command.getCommand().equals(CommandConst.ABL)){
			System.out.println("Geschlagene Figuren:");
			for(Piece item: geschlagene){
				System.out.print(item.getSymbol()+", ");
			}
			System.out.println("\n");
			
			this.listenUser();
			
			//Exit-Befehl
		}else if(command.getCommand().equals(CommandConst.EXIT)){
			
			this.saveBoard();
			System.out.println("Spiel gesichert und beendet");
			
		}else{
			System.out.println("Fehler!");
		}
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
		
        if (new File("Save.xml").exists()) {
        	board = chef.loadBoard();
        	view.zeigeSpielBrett(board);
        } else {
        	view.zeigeSpielBrett(chef.aufstellungGenerieren());
        }
		
		chef.listenUser();
			
	}

}
