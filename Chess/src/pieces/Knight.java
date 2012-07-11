package pieces;

import java.util.ArrayList;

public class Knight extends Piece{
	private int count;
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public ArrayList<String> getPossibleMoveDestinations(int xPosStart, int yPosStart){
		ArrayList<String> possibleDestinations = new ArrayList<String>();
		
		//nach rechts und unten
		if(xPosStart+2<=7 && yPosStart+1<=7){
			int x = xPosStart+2;
			int y = yPosStart+1;
			possibleDestinations.add(x+","+y);
		}
		
		//nach rechts und oben
		if(xPosStart+2<=7 && yPosStart-1>=0){
			int x = xPosStart+2;
			int y = yPosStart-1;
			possibleDestinations.add(x+","+y);
		}
		
		//nach links und oben
		if(xPosStart-2>=0 && yPosStart+1<=7){
			int x = xPosStart-2;
			int y = yPosStart+1;
			possibleDestinations.add(x+","+y);
		}
		
		//nach links und unten
		if(xPosStart-2>=0 && yPosStart-1>=0){
			int x = xPosStart-2;
			int y = yPosStart-1;
			possibleDestinations.add(x+","+y);
		}
		
		//nach oben und rechts
		if(yPosStart+2<=7 && xPosStart+1<=7){
			int y = yPosStart+2;
			int x = xPosStart+1;
			possibleDestinations.add(x+","+y);
		}
		
		//nach oben und links
		if(yPosStart+2<=7 && xPosStart-1>=0){
			int y = yPosStart+2;
			int x = xPosStart-1;
			possibleDestinations.add(x+","+y);
		}
		
		//nach unten und rechts
		if(yPosStart-2>=0 && xPosStart+1<=7){
			int y = yPosStart-2;
			int x = xPosStart+1;
			possibleDestinations.add(x+","+y);
		}
		
		//nach unten und links
		if(yPosStart-2>=0 && xPosStart-1>=0){
			int y = yPosStart-2;
			int x = xPosStart-1;
			possibleDestinations.add(x+","+y);
		}
		
		
		return possibleDestinations;
	}
	
	public boolean movePossible(int xPosStart, int yPosStart, int xPosEnd, int yPosEnd) {
		
		ArrayList<String> possibleDestinations = this.getPossibleMoveDestinations(xPosStart, yPosStart);
		String destPoint = xPosEnd+","+yPosEnd;

		//Prüfung
		for(String item: possibleDestinations){
			if(item.equals(destPoint)){
				return true;
			}
		}

		return false;
	}

	public Knight(String color, int count){
		if(color.equalsIgnoreCase("weiß")){
			this.setOwner(true);
			this.setSymbol(" N");
		}else if(color.equalsIgnoreCase("schwarz")){
			this.setOwner(false);
			this.setSymbol("-N");
		}else{
			System.out.println("Farbe nicht bekannt!");
		}
		
		if(count==1){
			this.setPositionX(1);			
		}else if(count==2){
			this.setPositionX(6);
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
	
	public Knight(){
		
	}

}
