package pieces;

public class Queen extends Piece{
	
	public Queen(String color){
		
		if(color.equalsIgnoreCase("weiﬂ")){
			this.setOwner(true);
			this.setSymbol(" Q");
		}else if(color.equalsIgnoreCase("schwarz")){
			this.setOwner(false);
			this.setSymbol("-Q");
		}else{
			System.out.println("Farbe nicht bekannt!");
		}
		

		if(this.isOwner()==true){
			this.setColor("Weiﬂ");
			this.setPositionX(3);
			this.setPositionY(7);
			
		}else{
			this.setColor("Schwarz");
			this.setPositionX(3);
			this.setPositionY(0);
		}
		
	}

}
