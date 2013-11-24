package main.java.controller;

import java.util.ArrayList;

import main.java.model.Bishop;
import main.java.model.King;
import main.java.model.Knight;
import main.java.model.Pawn;
import main.java.model.Piece;
import main.java.model.Player;
import main.java.model.Queen;
import main.java.model.Rock;



public class Analyser {
	
	public int letterConverter(String letter){
		int pos = 0;
		
		switch(letter){
		
		case "a": pos=0;
			break;
		case "b": pos=1;
			break;
		case "c": pos=2;
			break;
		case "d": pos=3;
			break;
		case "e": pos=4;
			break;
		case "f": pos=5;
			break;
		case "g": pos=6;
			break;
		case "h": pos=7;
			break;
			
		default:System.out.println("Letter Conversion failed!");
			break;
		}
		
		return pos;
		
	}
	
	public boolean whosTurn(Player[] playersInGame){
		if(playersInGame[0].isOnTurn()){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean testIfEnemy(boolean owner, int xPos, int yPos, Piece[][] board){
		if(board[yPos][xPos].isOwner()!=owner){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean testIfEmpty(int xPos, int yPos, Piece[][] board){
		if(board[yPos][xPos].getType()==null){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean testIfKingIsHere(Piece[][]board, ArrayList<String> possMoves, boolean owner){
		String kingField ="";
		//Suche nach weissem K�nig
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
	
	public boolean testIfOwnerPutsInCheck(Piece[][] board, boolean owner){
		
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
	

	
	public boolean rochadeShortPossible(boolean owner, Piece[][] board){
		//Weiß
		if(owner){
			if(board[7][4] instanceof King){
				System.out.println("König gefunden");
				King king = (King) board[7][4];
				if(king.isRochade()){
					System.out.println("König darf rochieren");
					if(board[7][7] instanceof Rock){
						System.out.println("Turm gefunden");
						Rock rock = (Rock) board[7][0];
						if(rock.isRochade()){
							System.out.println("Turm darf rochieren");
							if(board[7][6].getType()==null && board[7][5].getType()==null){
								System.out.println("Felder dazwischen sind frei");
								return true;
							}
						}
						
					}
				}
			}
		}
		//Schwarz
		else{
			if(board[0][4] instanceof King){
				King king = (King) board[0][4];
				if(king.isRochade()){
					if(board[0][7] instanceof Rock){
						Rock rock = (Rock) board[0][0];
						if(rock.isRochade()){
							if(board[0][6].getType()==null && board[0][5].getType()==null){
								return true;
							}
						}
						
					}
				}
			}
		}
			
		return false;
	}
	
	public boolean rochadeLongPossible(boolean owner, Piece[][] board){
		
		
		//Wei�
		if(owner){
			if(board[7][4] instanceof King){
				King king = (King) board[7][4];
				if(king.isRochade()){
					if(board[7][0] instanceof Rock){
						Rock rock = (Rock) board[7][0];
						if(rock.isRochade()){
							if(board[7][1].getType()==null && board[7][2].getType()==null && board[7][3].getType()==null){
								return true;
							}
						}
						
					}
				}
			}
		}
		//Schwarz
		else{
			if(board[0][4] instanceof King){
				King king = (King) board[0][4];
				if(king.isRochade()){
					if(board[0][0] instanceof Rock){
						Rock rock = (Rock) board[0][0];
						if(rock.isRochade()){
							if(board[0][1].getType()==null && board[0][2].getType()==null && board[0][3].getType()==null){
								return true;
							}
						}
						
					}
				}
			}
		}
				
		return false;
	}
	


}
