package pieces;

public class Bishop extends Piece{
	private int count;
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public Bishop(String color, int count){
		if(color.equalsIgnoreCase("weiﬂ")){
			this.setOwner(true);
			this.setSymbol(" B");
		}else if(color.equalsIgnoreCase("schwarz")){
			this.setOwner(false);
			this.setSymbol("-B");
		}else{
			System.out.println("Farbe nicht bekannt!");
		}
		
		if(count==1){
			this.setPositionX(3);			
		}else if(count==2){
			this.setPositionX(6);
		}else{
			System.out.println("Anzahl undefiniert!");
		}
		
		if(this.isOwner()==true){
			this.setColor("Weiﬂ");
			this.setPositionY(8);
			
		}else{
			this.setColor("Schwarz");
			this.setPositionY(1);
		}
		
		
	}

}
