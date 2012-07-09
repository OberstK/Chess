package pieces;

import java.util.ArrayList;

public class Queen extends Piece{
	
	public boolean movePossible(int xPosStart, int yPosStart, int xPosEnd, int yPosEnd, Piece[][] board){
		ArrayList<String> possibleDestinations = new ArrayList<String>();
		String destPoint = xPosEnd+","+yPosEnd;
		
		//nach rechts, selbe Zeile
		for(int i=1; i<=7;i++){
			int xPosTest = xPosStart+i;
			if(xPosTest<=7){
				if(board[yPosStart][xPosTest].getSymbol()=="  "){
					possibleDestinations.add(xPosTest+","+yPosStart);
				}else{
					i=8;
				}
			}
		}
		
		//nach links, selbe Zeile
		for(int i=1; i<=7;i++){
			int xPosTest = xPosStart-i;
			if(xPosTest>=0){
				if(board[yPosStart][xPosTest].getSymbol()=="  "){
					possibleDestinations.add(xPosTest+","+yPosStart);
				}else{
					i=8;
				}
			}
		}
		
		//nach oben, selbe Spalte
		for(int i=1; i<=7;i++){
			int yPosTest = yPosStart-i;
			if(yPosTest>=0){
				if(board[yPosTest][xPosStart].getSymbol()=="  "){
					possibleDestinations.add(xPosStart+","+yPosTest);
				}else{
					i=8;
				}
			}
		}
		
		//nach unten, selbe Spalte
		for(int i=1; i<=7;i++){
			int yPosTest = yPosStart+i;
			if(yPosTest<=7){
				if(board[yPosTest][xPosStart].getSymbol()=="  "){
					possibleDestinations.add(xPosStart+","+yPosTest);
				}else{
					i=8;
				}
			}
		}
		
		//schräg rechts oben
		for(int i=1; i<=7;i++){
			int yPosTest = yPosStart+i;
			int xPosTest = xPosStart+i;
			if(yPosTest<=7 && xPosTest<=7){
				if(board[yPosTest][xPosTest].getSymbol()=="  "){
					possibleDestinations.add(xPosTest+","+yPosTest);
				}else{
					i=8;
				}
			}
		}
		//schräg rechts unten
		for(int i=1; i<=7;i++){
			int yPosTest = yPosStart-i;
			int xPosTest = xPosStart+i;
			if(yPosTest>=0 && xPosTest<=7){
				if(board[yPosTest][xPosTest].getSymbol()=="  "){
					possibleDestinations.add(xPosTest+","+yPosTest);
				}else{
					i=8;
				}
			}
		}
		//schräg links oben
		for(int i=1; i<=7;i++){
			int yPosTest = yPosStart+i;
			int xPosTest = xPosStart-i;
			if(yPosTest<=7 && xPosTest>=0){
				if(board[yPosTest][xPosTest].getSymbol()=="  "){
					possibleDestinations.add(xPosTest+","+yPosTest);
				}else{
					i=8;
				}
			}
		}
		//schräg links unten
		for(int i=1; i<=7;i++){
			int yPosTest = yPosStart-i;
			int xPosTest = xPosStart-i;
			if(yPosTest>=0 && xPosTest>=0){
				if(board[yPosTest][xPosTest].getSymbol()=="  "){
					possibleDestinations.add(xPosTest+","+yPosTest);
				}else{
					i=8;
				}
			}
		}
		
		
		//Prüfung
		for(String item: possibleDestinations){
			System.out.println(item);
			if(item.equals(destPoint)){
				return true;
			}
		}
		
		
		return false;
	}
	
	public Queen(String color){
		
		if(color.equalsIgnoreCase("weiß")){
			this.setOwner(true);
			this.setSymbol(" Q");
		}else if(color.equalsIgnoreCase("schwarz")){
			this.setOwner(false);
			this.setSymbol("-Q");
		}else{
			System.out.println("Farbe nicht bekannt!");
		}
		

		if(this.isOwner()==true){
			this.setColor("Weiß");
			this.setPositionX(3);
			this.setPositionY(7);
			
		}else{
			this.setColor("Schwarz");
			this.setPositionX(3);
			this.setPositionY(0);
		}
		
	}

}
