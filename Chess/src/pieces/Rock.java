package pieces;

import java.util.ArrayList;

public class Rock extends Piece{
	
	private int count;
	private boolean rochade;

	public boolean isRochade() {
		return rochade;
	}

	public void setRochade(boolean rochade) {
		this.rochade = rochade;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public ArrayList<String> getPossibleMoveDestinations(int xPosStart, int yPosStart, Piece[][]board){
		ArrayList<String> possibleDestinations = new ArrayList<String>();
		
		//nach rechts, selbe Zeile
				for(int i=1; i<=7;i++){
					int xPosTest = xPosStart+i;
					if(xPosTest<=7){
						if(board[yPosStart][xPosTest].getSymbol().equals("  ")){
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
						if(board[yPosStart][xPosTest].getSymbol().equals("  ")){
							possibleDestinations.add(xPosTest+","+yPosStart);
						}else{
							possibleDestinations.add(xPosTest+","+yPosStart);
							i=8;
						}
					}
				}
				
				//nach oben, selbe Spalte
				for(int i=1; i<=7;i++){
					int yPosTest = yPosStart-i;
					if(yPosTest>=0){
						if(board[yPosTest][xPosStart].getSymbol().equals("  ")){
							possibleDestinations.add(xPosStart+","+yPosTest);
						}else{
							possibleDestinations.add(xPosStart+","+yPosTest);
							i=8;
						}
					}
				}
				
				//nach unten, selbe Spalte
				for(int i=1; i<=7;i++){
					int yPosTest = yPosStart+i;
					if(yPosTest<=7){
						if(board[yPosTest][xPosStart].getSymbol().equals("  ")){
							possibleDestinations.add(xPosStart+","+yPosTest);
						}else{
							possibleDestinations.add(xPosStart+","+yPosTest);
							i=8;
						}
					}
				}
		
		return possibleDestinations;
	}
	
	public boolean movePossible(int xPosStart, int yPosStart, int xPosEnd, int yPosEnd, Piece[][]board){
		
		ArrayList<String> possibleDestinations = this.getPossibleMoveDestinations(xPosStart, yPosStart, board);
		String destPoint = xPosEnd+","+yPosEnd;
		
		//Prüfung
		for(String item: possibleDestinations){
			if(item.equals(destPoint)){
				return true;
			}
		}
		return false;
	}

	public Rock(String color, int count){
		this.setRochade(true);
		
		if(color.equalsIgnoreCase("weiß")){
			this.setOwner(true);
			this.setSymbol(" R");
		}else if(color.equalsIgnoreCase("schwarz")){
			this.setOwner(false);
			this.setSymbol("-R");
		}else{
			System.out.println("Farbe nicht bekannt!");
		}
		
		if(count==1){
			this.setPositionX(0);			
		}else if(count==2){
			this.setPositionX(7);
		}else{
			System.out.println("Anzahl undefiniert!");
		}
		
		if(this.isOwner()==true){
			this.setColor("Weiß");
			this.setPositionY(7);
			
		}else{
			this.setColor("Schwarz");
			this.setPositionY(0);
		}
		
		
	}
	
	public Rock(){
		
	}

}
