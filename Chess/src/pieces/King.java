package pieces;

import java.util.ArrayList;

public class King extends Piece{
	
	private boolean rochade;
	
	public boolean isRochade() {
		return rochade;
	}

	public void setRochade(boolean rochade) {
		this.rochade = rochade;
	}


	
	public boolean movePossible(int xPosStart, int yPosStart, int xPosEnd, int yPosEnd){
		
		ArrayList<String> possibleDestinations = new ArrayList<String>();
		String destPoint = xPosEnd+","+yPosEnd;
		
		
		//nach rechts 
		if(xPosStart+1<=7){
			int x = xPosStart+1;
			int y = yPosStart;
			possibleDestinations.add(x+","+y);
		}
		
		//nach links
		if(xPosStart-1>=0){
			int x = xPosStart-1;
			int y = yPosStart;
			possibleDestinations.add(x+","+y);
		}
		
		//nach oben
		if(yPosStart-1>=0){
			int x = xPosStart;
			int y = yPosStart-1;
			possibleDestinations.add(x+","+y);
		}
		
		//nach unten
		if(yPosStart+1<=7){
			int x = xPosStart;
			int y = yPosStart+1;
			possibleDestinations.add(x+","+y);
		}
		
		//nach rechts und unten
		if(xPosStart+1<=7 && yPosStart+1<=7){
			int x = xPosStart+1;
			int y = yPosStart+1;
			possibleDestinations.add(x+","+y);
		}
		
		//nach links und unten
		if(xPosStart-1>=0 && yPosStart+1<=7){
			int x = xPosStart-1;
			int y = yPosStart+1;
			possibleDestinations.add(x+","+y);
		}
		
		//nach rechts und oben
		if(xPosStart+1<=7 && yPosStart-1>=0){
			int x = xPosStart+1;
			int y = yPosStart-1;
			possibleDestinations.add(x+","+y);
		}
		
		//nach links und oben
		if(xPosStart-1>=0 && yPosStart-1>=0){
			int x = xPosStart-1;
			int y = yPosStart-1;
			possibleDestinations.add(x+","+y);
		}
		
		//Prüfung
		for(String item: possibleDestinations){
			if(item.equals(destPoint)){
				return true;
			}
		}
		possibleDestinations.clear();
		
		return false;
	}
	
	public King(String color){
		this.setRochade(true);
		
		if(color.equalsIgnoreCase("weiß")){
			this.setOwner(true);
			this.setSymbol(" K");
		}else if(color.equalsIgnoreCase("schwarz")){
			this.setOwner(false);
			this.setSymbol("-K");
		}else{
			System.out.println("Farbe nicht bekannt!");
		}
		
		
		
		if(this.isOwner()==true){
			this.setColor("Weiß");
			this.setPositionX(4);
			this.setPositionY(7);
			
		}else{
			this.setColor("Schwarz");
			this.setPositionX(4);
			this.setPositionY(0);
		}
		

	}
	
	public King(){
		
	}

}
