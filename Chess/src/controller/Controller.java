package controller;

import java.util.ArrayList;

import pieces.*;

public class Controller {
	public Piece getFigurOnBoard(int letter, int num, Piece[][] board){
		
		int spaltenPos = letter;
		int zeilenPos = num-1;
		
		if(board[zeilenPos][spaltenPos].getSymbol().equals("  ")){
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
		
		board[zeile][spalte] = this.erzeugeLeeresFeld(spalte, zeile);
	
	}
	
	public boolean pruefeZielLeer(int xPos, int yPos, Piece[][] board){
		
		if(board[yPos][xPos].getSymbol().equals("  ")){
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
	
	public Piece erzeugeLeeresFeld(int x, int y){
		Piece nix = new Piece();
		nix.setSymbol("  ");
		nix.setPositionY(y);
		nix.setPositionX(x);
		return nix;
	}
	
	public void doKurzeRochade(boolean owner, Piece[][] board){
		int i;
		if(owner){
			i=7;
		}else{
			i=0;
		}
		Rock rock = (Rock) this.getFigurOnBoard(7, i+1, board);
		King king = (King) this.getFigurOnBoard(4, i+1, board);
		king.setPositionX(6);
		rock.setPositionX(5);
		this.setzeFigur(king, board);
		this.setzeFigur(rock, board);
		this.loescheAltePos(i, 7, board);
		this.loescheAltePos(i, 4, board);
		System.out.println("kurze Rochade durchgeführt");
	}
	
	public void doLangeRochade(boolean owner, Piece[][] board){
		int i;
		if(owner){
			i=7;
		}else{
			i=0;
		}
		Rock rock = (Rock) this.getFigurOnBoard(0, i+1, board);
		King king = (King) this.getFigurOnBoard(4, i+1, board);
		king.setPositionX(2);
		rock.setPositionX(3);
		this.setzeFigur(king, board);
		this.setzeFigur(rock, board);
		this.loescheAltePos(i, 0, board);
		this.loescheAltePos(i, 4, board);
		System.out.println("lange Rochade durchgeführt");
		
	}
	public boolean testIfKingIsHere(Piece[][]board, ArrayList<String> possMoves, boolean owner){
		String kingField ="";
		//Suche nach weissem König
		if(owner){
			for(int i=0; i<=7; i++){
				for(int j=0; j<=7 ; j++){
					if(board[i][j] instanceof King && board[i][j].isOwner()==true){
						kingField = j+","+i;
					}
				}
			}
		}else{
			for(int i=0; i<=7; i++){
				for(int j=0; j<=7 ; j++){
					if(board[i][j] instanceof King && board[i][j].isOwner()==false){
						kingField = j+","+i;
					}
				}
			}
		}
		for(String item: possMoves){
			if(item.equals(kingField)){
				return true;
			}
		}
		return false;
	}
	
	public King getKingOnBoard(boolean owner, Piece[][] board){
		King king = new King();
		
		for(int i=0; i<=7; i++){
			for(int j=0; j<=7 ; j++){
				if(board[i][j] instanceof King && board[i][j].isOwner()==owner){
					return (King) board[i][j];
				}
			}
		}
		
		
		return king;
	}
	
	public boolean pruefeAufOwnerImSchach(Piece[][] board, boolean owner){
		
		//Durchlauf des Bretts
		for(int i=0; i<=7; i++){
			for(int j=0; j<=7 ; j++){
				if(board[i][j].isOwner()==owner){
					if(board[i][j] instanceof Queen){
						Queen queen = (Queen) board[i][j];
						ArrayList<String> possMoves = queen.getPossibleMoveDestinations(queen.getPositionX(), queen.getPositionY(), board);
						if(this.testIfKingIsHere(board, possMoves, !owner)){
							return true;
						}
					}else if(board[i][j] instanceof Bishop){
						Bishop bishop = (Bishop) board[i][j];
						ArrayList<String> possMoves = bishop.getPossibleMoveDestinations(bishop.getPositionX(), bishop.getPositionY(), board);
						if(this.testIfKingIsHere(board, possMoves, !owner)){
							return true;
						}
					}else if(board[i][j] instanceof Knight){
						Knight knight = (Knight) board[i][j];
						ArrayList<String> possMoves = knight.getPossibleMoveDestinations(knight.getPositionX(), knight.getPositionY());
						if(this.testIfKingIsHere(board, possMoves, !owner)){
							return true;
						}
					}else if(board[i][j] instanceof Rock){
						Rock rock = (Rock) board[i][j];
						ArrayList<String> possMoves = rock.getPossibleMoveDestinations(rock.getPositionX(), rock.getPositionY(), board);
						if(this.testIfKingIsHere(board, possMoves, !owner)){
							return true;
						}
					}else if(board[i][j] instanceof Pawn){
						Pawn pawn = (Pawn) board[i][j];
						ArrayList<String> possMoves = pawn.getPossibleHitDestinations(pawn.getPositionX(), pawn.getPositionY(), board, pawn.isOwner());
						if(this.testIfKingIsHere(board, possMoves, !owner)){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	

	public boolean testIfCheckMate(boolean owner, Piece[][] board){
		
		King king = this.getKingOnBoard(owner, board);
		ArrayList<String> possMoves = king.getPossibleMoveDestinations(king.getPositionX(), king.getPositionY());
		
		boolean checkMate = false;
		int xNow = king.getPositionX();
		int yNow = king.getPositionY();
		
		for(String item: possMoves){
	        String[] parts = item.split(",");
	        
	        parts[0] = parts[0].trim();
			parts[1] = parts[1].trim();
	        
	        int xMove = Integer.parseInt(parts[0]);
			int yMove = Integer.parseInt(parts[1]);
			if(board[yMove][yMove].isOwner()!=owner){

				king.setPositionX(xMove);
				king.setPositionY(yMove);
				Piece zwischen = board[yMove][xMove];
				
				board[yMove][xMove] = king;
				board[yNow][xNow] = this.erzeugeLeeresFeld(xNow, yNow);
				if(!this.pruefeAufOwnerImSchach(board, !owner)){
					king.setPositionX(xNow);
					king.setPositionY(yNow);
					board[yNow][xNow] = king;
					board[yMove][xMove] = zwischen;
					return false;
				}else{
					king.setPositionX(xNow);
					king.setPositionY(yNow);
					board[yNow][xNow] = king;
					board[yMove][xMove] = zwischen;
					checkMate = true;
				}
			}else{
				checkMate = true;
			}
		}
		
		if(checkMate){
			return true;
		}else{
			return false;	
		}
	}
}
