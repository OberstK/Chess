package pieces;

public class Pawn extends Piece{

	private int count;
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
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

}
