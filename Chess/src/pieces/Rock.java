package pieces;

public class Rock extends Piece{
	
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Rock(String color, int count){
		
		if(color.equalsIgnoreCase("weiﬂ")){
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
			this.setColor("Weiﬂ");
			this.setPositionY(7);
			
		}else{
			this.setColor("Schwarz");
			this.setPositionY(0);
		}
		
		
	}

}
