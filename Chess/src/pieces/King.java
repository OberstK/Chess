package pieces;

public class King extends Piece{
	
	public King(String color){
		
		if(color.equalsIgnoreCase("weiﬂ")){
			this.setOwner(true);
			this.setSymbol(" K");
		}else if(color.equalsIgnoreCase("schwarz")){
			this.setOwner(false);
			this.setSymbol("-K");
		}else{
			System.out.println("Farbe nicht bekannt!");
		}
		
		
		
		if(this.isOwner()==true){
			this.setColor("Weiﬂ");
			this.setPositionX(5);
			this.setPositionY(8);
			
		}else{
			this.setColor("Schwarz");
			this.setPositionX(5);
			this.setPositionY(1);
		}
		

	}

}
