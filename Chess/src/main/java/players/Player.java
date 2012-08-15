package main.java.players;

public class Player {
	
	private boolean onTurn;
	private String name;
	private String color;
	private boolean owner;
	private boolean imSchach;
	
	public boolean isImSchach() {
		return imSchach;
	}

	public void setImSchach(boolean imSchach) {
		this.imSchach = imSchach;
	}

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

	public Player(String name, boolean owner){
		if(owner){
			this.setColor("Weiss");
			this.setOnTurn(true);
			
		}else{
			this.setColor("Schwarz");
			this.setOnTurn(false);
		}
		this.setOwner(owner);
		this.setImSchach(false);
		this.setName(name);
	}
	public Player(){
		
	}

}
