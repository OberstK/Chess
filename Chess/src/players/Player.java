package players;

public class Player {
	
	private boolean onTurn;
	private String name;
	private String color;
	private boolean owner;
	
	public boolean isOwner() {
		return owner;
	}

	public void setOwner(boolean owner) {
		this.owner = owner;
	}

	public boolean isOnTurn() {
		return onTurn;
	}

	public void setOnTurn(boolean onTurn) {
		this.onTurn = onTurn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Player(){
		
		
		
	}

}
