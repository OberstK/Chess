package controller;

import pieces.Piece;

public class Controller {
	public Piece getFigurOnBoard(int letter, int num, Piece[][] board){
		
		int spaltenPos = letter;
		int zeilenPos = num-1;
		
		if(board[zeilenPos][spaltenPos].getSymbol()=="  "){
			return null;
		}
		return board[zeilenPos][spaltenPos];
		
	}
	
	public void setzeFigur(Piece figur, Piece[][] board){
		int xPos = figur.getPositionX();
		int yPos = figur.getPositionY();
		

		board[yPos][xPos]= figur;
		
	}
	
	public void loescheAltePos(int zeile, int spalte, Piece[][] board){
		
		board[zeile][spalte]=null;
		
	}
	
	public boolean pruefeZielLeer(int xPos, int yPos, Piece[][] board){
		
		if(board[yPos][xPos].getSymbol()=="  "){
			return true;
		}else{
			return false;
		}
		
	}
	
	public boolean pruefeZielGegner(boolean owner, int xPos, int yPos, Piece[][] board){
		
		if(board[yPos][xPos].isOwner()!=owner){
			return true;
		}else{
			return false;
		}
	}
}
