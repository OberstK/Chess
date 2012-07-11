package controller;

import pieces.King;
import pieces.Piece;
import pieces.Rock;


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
							if(board[7][6].getSymbol().equals("  ") && board[7][5].getSymbol().equals("  ")){
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
							if(board[0][6].getSymbol().equals("  ") && board[0][5].getSymbol().equals("  ")){
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
		
		
		//Weiß
		if(owner){
			if(board[7][4] instanceof King){
				King king = (King) board[7][4];
				if(king.isRochade()){
					if(board[7][0] instanceof Rock){
						Rock rock = (Rock) board[7][0];
						if(rock.isRochade()){
							if(board[7][1].getSymbol().equals("  ") && board[7][2].getSymbol().equals("  ") && board[7][3].getSymbol().equals("  ")){
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
							if(board[0][1].getSymbol().equals("  ") && board[0][2].getSymbol().equals("  ") && board[0][3].getSymbol().equals("  ")){
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
