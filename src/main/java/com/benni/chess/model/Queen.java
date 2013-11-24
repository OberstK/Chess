package main.java.model;

import java.util.ArrayList;

public class Queen extends Piece{
	
	public ArrayList<String> getPossibleMoveDestinations(int xPosStart, int yPosStart, Piece[][] board){
		ArrayList<String> possibleDestinations = new ArrayList<String>();
		
		//nach rechts, selbe Zeile
				for(int i=1; i<=7;i++){
					int xPosTest = xPosStart+i;
					if(xPosTest<=7){
						if(board[yPosStart][xPosTest].getType()==null){
							possibleDestinations.add(xPosTest+","+yPosStart);
						}else{
							possibleDestinations.add(xPosTest+","+yPosStart);
							i=8;
						}
					}
				}
				
				//nach links, selbe Zeile
				for(int i=1; i<=7;i++){
					int xPosTest = xPosStart-i;
					if(xPosTest>=0){
						if(board[yPosStart][xPosTest].getType()==null){
							possibleDestinations.add(xPosTest+","+yPosStart);
						}else{
							possibleDestinations.add(xPosTest+","+yPosStart);
							i=8;
						}
					}
				}
				
				//nach oben, selbe Spalte
				for(int i=1; i<=7;i++){
					int yPosTest = yPosStart+i;
					if(yPosTest<=7){
						if(board[yPosTest][xPosStart].getType()==null){
							possibleDestinations.add(xPosStart+","+yPosTest);
						}else{
							possibleDestinations.add(xPosStart+","+yPosTest);
							i=8;
						}
					}
				}
				
				//nach unten, selbe Spalte
				for(int i=1; i<=7;i++){
					int yPosTest = yPosStart-i;
					if(yPosTest>=0){
						if(board[yPosTest][xPosStart].getType()==null){
							possibleDestinations.add(xPosStart+","+yPosTest);
						}else{
							possibleDestinations.add(xPosStart+","+yPosTest);
							i=8;
						}
					}
				}
				
				//schr�g rechts oben
				for(int i=1; i<=7;i++){
					int yPosTest = yPosStart+i;
					int xPosTest = xPosStart+i;
					if(yPosTest<=7 && xPosTest<=7){
						if(board[yPosTest][xPosTest].getType()==null){
							possibleDestinations.add(xPosTest+","+yPosTest);
						}else{
							possibleDestinations.add(xPosTest+","+yPosTest);
							i=8;
						}
					}
				}
				//schr�g rechts unten
				for(int i=1; i<=7;i++){
					int yPosTest = yPosStart-i;
					int xPosTest = xPosStart+i;
					if(yPosTest>=0 && xPosTest<=7){
						if(board[yPosTest][xPosTest].getType()==null){
							possibleDestinations.add(xPosTest+","+yPosTest);
						}else{
							possibleDestinations.add(xPosTest+","+yPosTest);
							i=8;
						}
					}
				}
				//schr�g links oben
				for(int i=1; i<=7;i++){
					int yPosTest = yPosStart+i;
					int xPosTest = xPosStart-i;
					if(yPosTest<=7 && xPosTest>=0){
						if(board[yPosTest][xPosTest].getType()==null){
							possibleDestinations.add(xPosTest+","+yPosTest);
						}else{
							possibleDestinations.add(xPosTest+","+yPosTest);
							i=8;
						}
					}
				}
				//schr�g links unten
				for(int i=1; i<=7;i++){
					int yPosTest = yPosStart-i;
					int xPosTest = xPosStart-i;
					if(yPosTest>=0 && xPosTest>=0){
						if(board[yPosTest][xPosTest].getType()==null){
							possibleDestinations.add(xPosTest+","+yPosTest);
						}else{
							possibleDestinations.add(xPosTest+","+yPosTest);
							i=8;
						}
					}
				}
		
		return possibleDestinations;
		
	}
	
	public boolean movePossible(int xPosStart, int yPosStart, int xPosEnd, int yPosEnd, Piece[][] board){
		ArrayList<String> possibleDestinations = this.getPossibleMoveDestinations(xPosStart, yPosStart, board);
		String destPoint = xPosEnd+","+yPosEnd;

		//Pr�fung
		for(String item: possibleDestinations){
			if(item.equals(destPoint)){
				return true;
			}
		}
		
		return false;
	}
	
	public Queen(String color){
		
		if(color.equalsIgnoreCase("weiss")){
			this.setOwner(true);
			this.setSymbol(" Q");
		}else if(color.equalsIgnoreCase("schwarz")){
			this.setOwner(false);
			this.setSymbol("-Q");
		}else{
			System.out.println("Farbe nicht bekannt!");
		}
		

		if(this.isOwner()==true){
			this.setColor("Weiss");
			this.setPositionX(3);
			this.setPositionY(0);
			
		}else{
			this.setColor("Schwarz");
			this.setPositionX(3);
			this.setPositionY(7);
		}
		
		this.setType("Queen");
		
	}
	
	public Queen(){
	
	}

}
