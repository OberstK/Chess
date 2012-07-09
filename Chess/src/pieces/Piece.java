package pieces;

public class Piece {
	
	private boolean owner;
	private int positionX;
	private int positionY;
	private String symbol;
	private String color;
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public boolean isOwner() {
		return owner;
	}
	public void setOwner(boolean owner) {
		this.owner = owner;
	}
	public int getPositionX() {
		return positionX;
	}
	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}
	public int getPositionY() {
		return positionY;
	}
	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}
	

}
