package pieces;

import java.util.ArrayList;

public class Pawn extends Piece{

	private int count;
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
	public boolean movePossible(int xPosStart, int yPosStart, int xPosEnd, int yPosEnd, Piece[][] board, boolean owner){
		boolean doubleMove = false;
		ArrayList<String> possibleDestinations = new ArrayList<String>();
		String destPoint = xPosEnd+","+yPosEnd;
		
		if((owner && yPosStart==6)||(owner==false && yPosStart==1)){
			doubleMove = true;
		}
		
		//Weiﬂ
		if(owner){
			//Bewegung nach Vorne (1 und 2 Schritte)
			if(board[yPosStart-1][xPosStart].getSymbol()=="  "){
				possibleDestinations.add(xPosStart+","+(yPosStart-1));
				if(doubleMove && board[yPosStart-2][xPosStart].getSymbol()=="  "){
					possibleDestinations.add(xPosStart+","+(yPosStart-2));
				}
			}
			//Schlagen nach rechts
			if(board[yPosStart-1][xPosStart+1].getSymbol()!="  " && board[yPosStart+1][xPosStart+1].isOwner()!=owner){
				possibleDestinations.add((xPosStart+1)+","+(yPosStart-1));
			}
			
			//Schlagen nach links
			if(board[yPosStart-1][xPosStart-1].getSymbol()!="  " && board[yPosStart+1][xPosStart+1].isOwner()!=owner){
				possibleDestinations.add((xPosStart-1)+","+(yPosStart-1));
			}
			
		}

		//Schwarz
		if(owner==false){
			//Bewegung nach Vorne (1 und 2 Schritte)
				
				if(board[yPosStart+1][xPosStart].getSymbol()=="  "){
					possibleDestinations.add(xPosStart+","+(yPosStart+1));
					if(doubleMove && board[yPosStart+2][xPosStart].getSymbol()=="  "){
						possibleDestinations.add(xPosStart+","+(yPosStart+2));
					}
				}
			
			//Schlagen nach rechts
			if(board[yPosStart+1][xPosStart+1].getSymbol()!="  " && board[yPosStart+1][xPosStart+1].isOwner()!=owner){
				possibleDestinations.add((xPosStart+1)+","+(yPosStart+1));
			}
			
			//Schlagen nach links
			if(board[yPosStart+1][xPosStart-1].getSymbol()!="  " && board[yPosStart+1][xPosStart+1].isOwner()!=owner){
				possibleDestinations.add((xPosStart-1)+","+(yPosStart-1));
			}
		}
		
		//Pr¸fung
		for(String item: possibleDestinations){
			System.out.println(item);
			if(item.equals(destPoint)){
				return true;
			}
		}
		
		
		
		return false;
	}
	public Pawn(String color, int count) {
		
		if(color.equalsIgnoreCase("weiﬂ")){
			this.setOwner(true);
			this.setSymbol(" P");
		}else if(color.equalsIgnoreCase("schwarz")){
			this.setOwner(false);
			this.setSymbol("-P");
		}else{
			System.out.println("Farbe nicht bekannt!");
		}
		
		switch (count) {
		case 0: this.setPositionX(count);
			break;
		case 1: this.setPositionX(count);
			break;
		case 2: this.setPositionX(count);
			break;
		case 3: this.setPositionX(count);
			break;
		case 4: this.setPositionX(count);
			break;
		case 5: this.setPositionX(count);
			break;
		case 6: this.setPositionX(count);
			break;
		case 7: this.setPositionX(count);
			break;


		default: System.out.println("Anzahl Bauern undefiniert!");
			break;
		}
		

		
		if(this.isOwner()==true){
			this.setColor("Weiﬂ");
			this.setPositionY(6);
			
		}else{
			this.setColor("Schwarz");
			this.setPositionY(1);
		}
		
	}
	
	public Pawn(){
		
	}

}
