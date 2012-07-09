package field;

import java.util.ArrayList;

import pieces.*;


public class GameMaster {
	
	private final Viewer viewer;
	private final CommandListener listener;
	
	
	public static Piece[][] board = new Piece[8][8];
	public static ArrayList<Piece> geschlagene= new ArrayList<Piece>();
	
	public int letterConverter(String letter){
		int pos = 0;
		
		switch(letter){
		
		case "a": pos=0;
			break;
		case "b": pos=1;
			break;
		case "c": pos=2;
			break;
		case "d": pos=3;
			break;
		case "e": pos=4;
			break;
		case "f": pos=5;
			break;
		case "g": pos=6;
			break;
		case "h": pos=7;
			break;
			
		default:System.out.println("Letter Conversion failed!");
			break;
		}
		
		return pos;
		
	}
	
	public void makeMove(String sLetter, int sNum, String zLetter, int zNum){
		
		this.bewegeFigur(this.getFigurOnBoard(sLetter, sNum), zLetter, zNum);
		
	}
	
	public Piece getFigurOnBoard(String letter, int num){
		
		int spaltenPos = this.letterConverter(letter);
		int zeilenPos = num-1;
		
		if(board[zeilenPos][spaltenPos].getSymbol()=="  "){
			return null;
		}
		
		return board[zeilenPos][spaltenPos];
		
	}

	
	public void setzeFigur(Piece figur){
		int xPos = figur.getPositionX();
		int yPos = figur.getPositionY();
		

		board[yPos][xPos]= figur;
		
	}
	
	public void loescheAltePos(int zeile, int spalte){
		
		board[zeile][spalte]=null;
		
	}
	
	public boolean pruefeZielLeer(int xPos, int yPos){
		
		if(board[yPos][xPos].getSymbol()=="  "){
			return true;
		}else{
			return false;
		}
		
	}
	
	public boolean pruefeZielGegner(boolean owner, int xPos, int yPos){
		
		if(board[yPos][xPos].isOwner()!=owner){
			return true;
		}else{
			return false;
		}
	}
	
	public void schlageFigur(int xPos, int yPos){
		geschlagene.add(board[yPos][xPos]);
	}
	
	public void bewegeFigur(Piece figur, String letter, int num){
		boolean test = false;
		int xPosZiel = this.letterConverter(letter);
		int yPosZiel = num-1; //weil Array bei 0 anf‰ngt, aber Schachbrett bei 1

		int xPosNow = figur.getPositionX();
		int yPosNow = figur.getPositionY();
		
		if(figur instanceof Knight){
			Knight knight = (Knight) figur;
			if(knight.movePossible(xPosNow, yPosNow, xPosZiel, yPosZiel)){
				test = true;
			}
		}else if(figur instanceof Queen){
			Queen queen = (Queen) figur;
			if(queen.movePossible(xPosNow, yPosNow, xPosZiel, yPosZiel, board)){
				test = true;
			}
		}else if(figur instanceof Pawn){
			Pawn pawn = (Pawn) figur;
			if(pawn.movePossible(xPosNow, yPosNow, xPosZiel, yPosZiel, board, pawn.isOwner())){
				test = true;
			}
		}else if(figur instanceof Bishop){
			test=true;
		}else if(figur instanceof Rock){
			test=true;
		}else if(figur instanceof King){
			test=true;
		}
		if(test){
			if(this.pruefeZielLeer(xPosZiel, yPosZiel)){
				figur.setPositionX(xPosZiel);
				figur.setPositionY(yPosZiel);
				this.loescheAltePos(yPosNow, xPosNow);
				this.setzeFigur(figur);
			}else if(this.pruefeZielGegner(figur.isOwner(), xPosZiel, yPosZiel)){
				this.schlageFigur(xPosZiel, yPosZiel);
				figur.setPositionX(xPosZiel);
				figur.setPositionY(yPosZiel);
				this.loescheAltePos(yPosNow, xPosNow);
				this.setzeFigur(figur);		
			}else{
				System.out.println("Feld belegt!");
			}
		}else{
			System.out.println("Dieser Zug ist nicht g¸ltig!");
		}

	}
	
	public Piece[][] aufstellungGenerieren(Piece[][] board){
		
		ArrayList<Piece> figuren = new ArrayList<Piece>();
		//Weiﬂe Figuren
		figuren.add(new King("weiﬂ"));
		figuren.add(new Queen("weiﬂ"));
		figuren.add(new Knight("weiﬂ", 1));
		figuren.add(new Knight("weiﬂ", 2));
		figuren.add(new Bishop("weiﬂ", 1));
		figuren.add(new Bishop("weiﬂ", 2));
		figuren.add(new Rock("weiﬂ", 1));
		figuren.add(new Rock("weiﬂ", 2));
		for(int i=0; i<8; i++){
			figuren.add(new Pawn("weiﬂ", i));
		}
		

		//Schwarze Figuren
		figuren.add(new King("schwarz"));
		figuren.add(new Queen("schwarz"));
		figuren.add(new Knight("schwarz", 1));
		figuren.add(new Knight("schwarz", 2));
		figuren.add(new Bishop("schwarz", 1));
		figuren.add(new Bishop("schwarz", 2));
		figuren.add(new Rock("schwarz", 1));
		figuren.add(new Rock("schwarz", 2));
		for(int i=0; i<8; i++){
			figuren.add(new Pawn("schwarz", i));
		}
		
		//Setzen
		for(Piece figur: figuren){
			this.setzeFigur(figur);
		}
		
		return board;
	}
	
	public void listenUser(){

		Command command = listener.scanInput();
		
		if(command == null){
			System.out.println("Befehl nicht verstanden!");
			
			this.listenUser();
		}else if(command.getCommand().equals(CommandConst.ZUG)){
			
			String[] values = command.getValues();
			
			if(this.getFigurOnBoard(values[0], Integer.parseInt(values[1]))==null){
				System.out.println("Keine Figur an dieser Stelle!");
				viewer.zeigeSpielBrett(board);
				this.listenUser();
			}else{
			this.bewegeFigur(this.getFigurOnBoard(values[0], Integer.parseInt(values[1])), values[2], Integer.parseInt(values[3]));
			
			viewer.zeigeSpielBrett(board);
			this.listenUser();
			}
		}else if(command.getCommand().equals(CommandConst.ABL)){
			System.out.println("Geschlagene Figuren:");
			for(Piece item: geschlagene){
				System.out.print(item.getSymbol()+", ");
			}
			System.out.println("\n");
			
			this.listenUser();
			
		}else if(command.getCommand().equals(CommandConst.EXIT)){
			
			System.out.println("Spiel beendet");
			
		}else{
			System.out.println("Fehler!");
		}
	}
	
	public GameMaster(){
		viewer = new Viewer();
		listener = new CommandListener();
	}
	
	public static void main(String[] args) {
		
		GameMaster chef = new GameMaster();
		Viewer view = new Viewer();
		
		board = chef.aufstellungGenerieren(board);
		view.zeigeSpielBrett(board);
		chef.listenUser();
		
		
	}

}
