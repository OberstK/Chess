package main.java.model;

import java.util.ArrayList;

public class Bishop extends Piece{
	private int count;
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public ArrayList<String> getPossibleMoveDestinations(int xPosStart, int yPosStart, Piece[][] board){
		ArrayList<String> possibleDestinations = new ArrayList<String>();
		
		//nach rechts oben
				for (int i=1;i<=7; i++){
					int xPosTest = xPosStart+i;
					int yPosTest = yPosStart+i;
					if(xPosTest<=7 && yPosTest<=7){
						if(board[yPosTest][xPosTest].getSymbol().equals("  ")){
							possibleDestinations.add(xPosTest+","+yPosTest);
						}else{
							possibleDestinations.add(xPosTest+","+yPosTest);
							i=8;
						}
					}
				}
				//nach rechts unten
				for (int i=1;i<=7; i++){
					int xPosTest = xPosStart+i;
					int yPosTest = yPosStart-i;
					if(xPosTest<=7 && yPosTest >=0){
						if(board[yPosTest][xPosTest].getSymbol().equals("  ")){
							possibleDestinations.add(xPosTest+","+yPosTest);
						}else{
							possibleDestinations.add(xPosTest+","+yPosTest);
							i=8;
						}
					}
				}
				//nach links oben
				for (int i=1;i<=7; i++){
					int xPosTest = xPosStart-i;
					int yPosTest = yPosStart+i;
					if(xPosTest>=0 && yPosTest<=7){
						if(board[yPosTest][xPosTest].getSymbol().equals("  ")){
							possibleDestinations.add(xPosTest+","+yPosTest);
						}else{
							possibleDestinations.add(xPosTest+","+yPosTest);
							i=8;
						}
					}
				}
				//nach links unten
				for (int i=1;i<=7; i++){
					int xPosTest = xPosStart-i;
					int yPosTest = yPosStart-i;
					if(xPosTest>=0 && yPosTest>=0){
						if(board[yPosTest][xPosTest].getSymbol().equals("  ")){
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
	
	public Bishop(String color, int count){
		if(color.equalsIgnoreCase("weiss")){
			this.setOwner(true);
			this.setSymbol(" B");
		}else if(color.equalsIgnoreCase("schwarz")){
			this.setOwner(false);
			this.setSymbol("-B");
		}else{
			System.out.println("Farbe nicht bekannt!");
		}
		
		if(count==1){
			this.setPositionX(2);			
		}else if(count==2){
			this.setPositionX(5);
		}else{
			System.out.println("Anzahl undefiniert!");
		}
		
		if(this.isOwner()==true){
			this.setColor("Weiss");
			this.setPositionY(0);
			
		}else{
			this.setColor("Schwarz");
			this.setPositionY(7);
		}
		this.setType("Bishop");
		
	}
	public Bishop(){
		
	}

}
