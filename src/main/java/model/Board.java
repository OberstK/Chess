package main.java.model;

import java.util.ArrayList;

public class Board {
	
	private Piece[][] board = new Piece[8][8];
	private ArrayList<Piece> outList = new ArrayList<Piece>();
	private Player[] playersInGame = new Player[2];
	

	public Piece[][] getBoard() {
		return board;
	}

	public void setBoard(Piece[][] board) {
		this.board = board;
	}

	public ArrayList<Piece> getOutList() {
		return outList;
	}

	public void setOutList(ArrayList<Piece> outPieces) {
		this.outList = outPieces;
	}

	public Player[] getPlayersInGame() {
		return playersInGame;
	}

	public void setPlayersInGame(Player[] playersInGame) {
		this.playersInGame = playersInGame;
	}

	public void setPieceOnSquare(Piece piece){
		int x = piece.getPositionX();
		int y = piece.getPositionY();
		board[y][x] = piece;
	}
	
	public void fillEmptySpot(int x, int y){
		Piece noPiece = new Piece();
		noPiece.setSymbol("  ");
		noPiece.setPositionY(y);
		noPiece.setPositionX(x);
		board[y][x] = noPiece;
	}
	
	public King getKingOnBoard(boolean owner){
		King king = new King();
		
		for(int i=0; i<=7; i++){
			for(int j=0; j<=7 ; j++){
				if(board[i][j] instanceof King && board[i][j].isOwner()==owner){
					return (King) board[i][j];
				}
			}
		}
		return king;
	}
	
	public Piece getPieceOnBoard(int x, int y){
		int colPos = x;
		int rowPos = y;
		
		if(board[rowPos][colPos].getType()==null){
			return null;
		}
		return board[rowPos][colPos];
	}
	
	public Player getPlayerOnTurn(){
		if(playersInGame[0].isOnTurn()){
			return playersInGame[0];
		}else{
			return playersInGame[1];
		}
	}
	
	public Player getPlayerNotOnTurn(){
		if(playersInGame[0].isOnTurn()){
			return playersInGame[1];
		}else{
			return playersInGame[0];
		}
	}
	
	public void generatePlayers(){
		//Spieler 1
		Player player1 = new Player("Spieler 1", true);
		//Spieler 2
		Player player2 = new Player("Spieler 2", false);

		playersInGame[0] = player1;
		playersInGame[1] = player2;
	}
	
	public void addPieceToOutList(Piece figur){
		outList.add(figur);
	}
}
