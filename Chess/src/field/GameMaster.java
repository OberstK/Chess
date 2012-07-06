package field;

import java.util.ArrayList;

import pieces.*;

public class GameMaster {
	
	
	public static Piece[][] board = new Piece[8][8];
	
	
	public int letterConverter(String letter){
		int pos = 0;
		
		switch(letter){
		
		case "A": pos=0;
			break;
		case "B": pos=1;
			break;
		case "C": pos=2;
			break;
		case "D": pos=3;
			break;
		case "E": pos=4;
			break;
		case "F": pos=5;
			break;
		case "G": pos=6;
			break;
		case "H": pos=7;
			break;
			
		default:System.out.println("Letter Conversion failed!");
			break;
		}
		
		return pos;
		
	}
	
	public void makeMove(String sLetter, int sNum, String zLetter, int zNum){
		
		this.bewegeFigur(this.getFigurOnBoard(sLetter, sNum), zLetter, zNum);
		
	}
	
	public Piece getFigurOnBoard(String letter, int num){
		

		int spaltenPos = this.letterConverter(letter);
		int zeilenPos = num-1;
		
		return board[zeilenPos][spaltenPos];
		
	}

	
	public void setzeFigur(Piece figur){
		int xPos = figur.getPositionX();
		int yPos = figur.getPositionY();
		
		board[yPos-1][xPos-1]= figur;
		
	}
	
	public void loescheAltePos(int zeile, int spalte){
		
		board[zeile-1][spalte-1]=null;
		
	}
	
	public void bewegeFigur(Piece figur, String letter, int num){
		
		int xPosZiel = this.letterConverter(letter);
		int yPosZiel = num-1;

		int xPosNow = figur.getPositionX();
		int yPosNow = figur.getPositionY();
		
		figur.setPositionX(xPosZiel);
		figur.setPositionY(yPosZiel);

		this.loescheAltePos(yPosNow, xPosNow);
		this.setzeFigur(figur);
	}
	
	public Piece[][] aufstellungGenerieren(Piece[][] board){
		
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
		for(int i=1; i<9; i++){
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
		for(int i=1; i<9; i++){
			figuren.add(new Pawn("schwarz", i));
		}
		
		//Setzen
		for(Piece figur: figuren){
			this.setzeFigur(figur);
		}
		
		return board;
	}
	
	public static void main(String[] args) {
		
		GameMaster chef = new GameMaster();
		Viewer view = new Viewer();
		board = chef.aufstellungGenerieren(board);
		view.zeigeSpielBrett(board);
		chef.makeMove("B", 8, "D", 4);
		view.zeigeSpielBrett(board);
	}

}
