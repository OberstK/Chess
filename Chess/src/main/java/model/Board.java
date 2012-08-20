package main.java.model;

import java.util.ArrayList;

public class Board {
	
	private Piece[][] board = new Piece[8][8];
	private ArrayList<Piece> outPieces = new ArrayList<Piece>();
	private Player[] playersInGame = new Player[2];

	public Piece[][] getBoard() {
		return board;
	}

	public void setBoard(Piece[][] board) {
		this.board = board;
	}

	public ArrayList<Piece> getOutPieces() {
		return outPieces;
	}

	public void setOutPieces(ArrayList<Piece> outPieces) {
		this.outPieces = outPieces;
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
}
