package main.java.controller;

import java.util.ArrayList;

import main.java.pieces.*;
import main.java.players.Player;


public class Controller {
	
	private final Analyser analyse;
	
	//Hole Figur an dieser Position
	public Piece getPieceOnBoard(int letter, int num, Piece[][] board){
		int colPos = letter;
		int rowPos = num;
		
		if(board[rowPos][colPos].getSymbol().equals("  ")){
			return null;
		}
		return board[rowPos][colPos];
	}
	
	//Hole den jeweiligen K�nig
	public King getKingOnBoard(boolean owner, Piece[][] board){
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
	
	//Schlage Figur an dieser Stelle
	public void hitOutPiece(int xPos, int yPos, ArrayList<Piece> outPiecesNow, Piece[][]board){
		outPiecesNow.add(board[yPos][xPos]);
	}
	
	//Setze Figur mittels seiner Position aufs Brett
	public void putPieceOnBoard(Piece figur, Piece[][] board){
		int xPos = figur.getPositionX();
		int yPos = figur.getPositionY();
		
		board[yPos][xPos]= figur;
	}
	
	//Erzeuge ein komplett neues Brett mit der Startaufstellung
	public Piece[][] generateBoard(){
		ArrayList<Piece> figuren = new ArrayList<Piece>();
		Piece[][] newBoard = new Piece[8][8];
		//Wei�e Figuren
		figuren.add(new King("wei�"));
		figuren.add(new Queen("wei�"));
		figuren.add(new Knight("wei�", 1));
		figuren.add(new Knight("wei�", 2));
		figuren.add(new Bishop("wei�", 1));
		figuren.add(new Bishop("wei�", 2));
		figuren.add(new Rock("wei�", 1));
		figuren.add(new Rock("wei�", 2));
		for(int i=0; i<8; i++){
			figuren.add(new Pawn("wei�", i));
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
			this.putPieceOnBoard(figur, newBoard);
		}
		
		//Leere Felder f�llen
		for(int i=2; i<=5; i++){
			for(int j=0; j<=7;j++){
				newBoard[i][j] = this.fillEmptySpot(j, i);
			}
		}
		return newBoard;
	}
	
	//Leere die alte Position nach einem Zug
	public void emptyOldPosition(int zeile, int spalte, Piece[][] board){
		board[zeile][spalte] = this.fillEmptySpot(spalte, zeile);
	}
	
	public Player getPlayerOnTurn(Player[] playersInGame){
		if(playersInGame[0].isOnTurn()){
			return playersInGame[0];
		}else{
			return playersInGame[1];
		}
	}
	
	public Player getPlayerNotOnTurn(Player[] playersInGame){
		if(playersInGame[0].isOnTurn()){
			return playersInGame[1];
		}else{
			return playersInGame[0];
		}
	}
	
	//Wechsel den "Dran"-Status der Spieler
	public void changePlayers(Player[] playersInGame){
		//Spieler wechseln
		for(int i=0; i<=1; i++){
			playersInGame[i].setOnTurn(!(playersInGame[i].isOnTurn()));
		}
		if(analyse.whosTurn(playersInGame)){
			System.out.println(playersInGame[0].getName()+" - "+playersInGame[0].getColor()+" ist am Zug");
		}else{
			System.out.println(playersInGame[1].getName()+" - "+playersInGame[1].getColor()+" ist am Zug");
		}
	}
	
	//Erzeuge die Spieler f�r ein neues Spiel
	public Player[] generatePlayers(){
		Player[] players = new Player[2];
		//Spieler 1
		Player player1 = new Player("Spieler 1", true);
		//Spieler 2
		Player player2 = new Player("Spieler 2", false);

		players[0] = player1;
		players[1] = player2;

		return players;
	}
	
	//Erzeuge eine "leere" Figur
	public Piece fillEmptySpot(int x, int y){
		Piece noPiece = new Piece();
		noPiece.setSymbol("  ");
		noPiece.setPositionY(y);
		noPiece.setPositionX(x);
		return noPiece;
	}
	
	public ArrayList<String> getPositionOfPosDestSquares(Piece piece, int x, int y, Piece[][] board, boolean owner){
		ArrayList<String> moves = new ArrayList<String>();
		
		if(piece instanceof Pawn){
			Pawn pawn = (Pawn) piece;
			moves = pawn.getPossibleMoveDestinations(x, y, board, owner);	
		}else if(piece instanceof Knight){
			Knight knight = (Knight) piece;
			moves = knight.getPossibleMoveDestinations(x, y);
		}else if(piece instanceof Queen){
			Queen queen = (Queen) piece;
			moves = queen.getPossibleMoveDestinations(x, y, board);
		}else if(piece instanceof Bishop){
			Bishop bishop = (Bishop) piece;
			moves = bishop.getPossibleMoveDestinations(x, y, board);
		}else if(piece instanceof Rock){
			Rock rock = (Rock) piece;
			moves = rock.getPossibleMoveDestinations(x, y, board);
		}else if(piece instanceof King){
			King king = (King) piece;
			moves = king.getPossibleMoveDestinations(x, y);
		}
		
		return moves;
	}
	
	/*Bewege eine Figur von seiner Startposition zu seiner Zielposition
	 * Diverse Pr�fungen finden statt
	 */
	public boolean bewegeFigur(Piece figur, int letter, int num, Piece[][] board, ArrayList<Piece> outPieces){
		//Initiierung von Variablen zur �berpr�fung des Zuges
		boolean success= false;
		boolean test = false;
		
		//Start und Endposition holen und in Integers wandeln
		int xPosEnd = letter;
		int yPosEnd = num; //weil Array bei 0 anf�ngt, aber Schachbrett bei 1
		int xPosStart = figur.getPositionX();
		int yPosStart = figur.getPositionY();
		
		//Pr�fung ob Bauer und ob Zug m�glich ist
		if(figur instanceof Pawn){
			Pawn pawn = (Pawn) figur;
			if(pawn.movePossible(xPosStart, yPosStart, xPosEnd, yPosEnd, board, pawn.isOwner())){
				test = true;
			}else if(pawn.hitPossible(xPosStart, yPosStart, xPosEnd, yPosEnd, board, pawn.isOwner())){
				test = true;
			}
		//Pr�fung ob Springer und ob Zug m�glich ist
		}else if(figur instanceof Knight){
			Knight knight = (Knight) figur;
			if(knight.movePossible(xPosStart, yPosStart, xPosEnd, yPosEnd)){
				test = true;
			}
		//Pr�fung ob K�nigin und ob Zug m�glich ist
		}else if(figur instanceof Queen){
			Queen queen = (Queen) figur;
			if(queen.movePossible(xPosStart, yPosStart, xPosEnd, yPosEnd, board)){
				test = true;
			}
		//Pr�fung ob L�ufer und ob Zug m�glich ist
		}else if(figur instanceof Bishop){
			Bishop bishop = (Bishop) figur;
			if(bishop.movePossible(xPosStart, yPosStart, xPosEnd, yPosEnd, board)){
				test = true;
			}
		//Pr�fung ob Turm und ob Zug m�glich ist
		}else if(figur instanceof Rock){
			Rock rock = (Rock) figur;
			if(rock.movePossible(xPosStart, yPosStart, xPosEnd, yPosEnd, board)){
				test=true;
			}
		//Pr�fung ob K�nig und ob Zug m�glich ist
		}else if(figur instanceof King){
			King king = (King) figur;
			if(king.movePossible(xPosStart, yPosStart, xPosEnd, yPosEnd)){
				test=true;
			}
		}
			
		if(test){
			//Testen ob Feld leer ist
			if(analyse.testIfEmpty(xPosEnd, yPosEnd, board)){
				figur.setPositionX(xPosEnd);
				figur.setPositionY(yPosEnd);
				this.emptyOldPosition(yPosStart, xPosStart, board);
				this.putPieceOnBoard(figur, board);
				success=true;
				return success;
			//Testen ob auf Feld ein Gegner steht
			}else if(analyse.testIfEnemy(figur.isOwner(), xPosEnd, yPosEnd, board)){
				this.hitOutPiece(xPosEnd, yPosEnd, outPieces, board);
				figur.setPositionX(xPosEnd);
				figur.setPositionY(yPosEnd);
				this.emptyOldPosition(yPosStart, xPosStart, board);
				this.putPieceOnBoard(figur, board);		
				success=true;
				return success;
			//Wenn belegt -> Gib false zur�ck
			}else{
				System.out.println("Feld belegt!");
				success=false;
				return success;
			}
		}else{
			//Wenn obige Pr�fungen auf Figur fehlschlagen, ist der Zug nicht m�glich (Regeln verletzt, etc.)
			System.out.println("Dieser Zug ist nicht g�ltig!");
			success=false;
			return success;
		}
	}
	
	//F�hrt eine kurze Rochade durch -> nimmt Farbe als Wert an
	public void doKurzeRochade(boolean owner, Piece[][] board){
		//Pr�fung ob Wei� oder Schwarz, ver�ndert die beteiligte Zeile
		int i;
		if(owner){
			i=7;
		}else{
			i=0;
		}
		//Holt beteiligten K�nig und Turm
		Rock rock = (Rock) this.getPieceOnBoard(7, i+1, board);
		King king = (King) this.getPieceOnBoard(4, i+1, board);
		//Setzt neue Position
		king.setPositionX(6);
		rock.setPositionX(5);
		//Setzt die Figuren an neue Position und l�scht alte Position
		this.putPieceOnBoard(king, board);
		this.putPieceOnBoard(rock, board);
		this.emptyOldPosition(i, 7, board);
		this.emptyOldPosition(i, 4, board);
		System.out.println("kurze Rochade durchgef�hrt");
	}
	
	//F�hrt eine lange Rochade durch -> nimmt Farbe als Wert an
	public void doLangeRochade(boolean owner, Piece[][] board){
		//Pr�fung ob Wei� oder Schwarz, ver�ndert die beteiligte Zeile
		int i;
		if(owner){
			i=7;
		}else{
			i=0;
		}
		//Holt beteiligten K�nig und Turm
		Rock rock = (Rock) this.getPieceOnBoard(0, i+1, board);
		King king = (King) this.getPieceOnBoard(4, i+1, board);
		//Setzt neue Position
		king.setPositionX(2);
		rock.setPositionX(3);
		//Setzt die Figuren an neue Position und l�scht alte Position
		this.putPieceOnBoard(king, board);
		this.putPieceOnBoard(rock, board);
		this.emptyOldPosition(i, 0, board);
		this.emptyOldPosition(i, 4, board);
		System.out.println("lange Rochade durchgef�hrt");
		
	}

	//Pr�ft ob die m�glichen Z�ge des gegnerischen K�nigs im Schach noch Optionen frei halten
	public boolean testMovesForCheckMate(boolean owner, Piece[][] board){
		//holt K�nig auf dem Brett
		King king = this.getKingOnBoard(owner, board);
		//M�gliche Z�ge des K�nigs generieren
		ArrayList<String> possMoves = king.getPossibleMoveDestinations(king.getPositionX(), king.getPositionY());
		
		//Initiiere beteiligte Informationen
		boolean checkMate = false;
		int xNow = king.getPositionX();
		int yNow = king.getPositionY();
		
		//iteriert durch alle M�glichkeiten
		for(String item: possMoves){
			//Verarbeitet Positions-String zu X und Y Wert
	        String[] parts = item.split(",");
	        parts[0] = parts[0].trim();
			parts[1] = parts[1].trim();
	        int xMove = Integer.parseInt(parts[0]);
			int yMove = Integer.parseInt(parts[1]);
			
			//Pr�fe ob Figur auf dem zu pr�fenden Feld ein Gegner ist (wenn nicht-> keine Option)
			if(board[yMove][yMove].isOwner()!=owner){
				//F�hrt den Zug auf das Pr�ffeld testweise durch
				king.setPositionX(xMove);
				king.setPositionY(yMove);
				Piece zwischen = board[yMove][xMove];
				board[yMove][xMove] = king;
				board[yNow][xNow] = this.fillEmptySpot(xNow, yNow);
				//Testet ob man immer noch im Schach steht nach diesem Zug
				if(!analyse.testIfOwnerPutsInCheck(board, !owner)){
					//wenn nicht, wird der Testzug "undo-ed" und false zur�ck gegeben
					king.setPositionX(xNow);
					king.setPositionY(yNow);
					board[yNow][xNow] = king;
					board[yMove][xMove] = zwischen;
					return false;
				}else{
					//wenn doch, wird der Testzug "undo-ed" und vorbehaltlich checkMate auf true gesetzt wird
					king.setPositionX(xNow);
					king.setPositionY(yNow);
					board[yNow][xNow] = king;
					board[yMove][xMove] = zwischen;
					checkMate = true;
				}
			}else{
				//wenn eigene Figur wird checkMate vorbehaltlich auf true gesetzt wird
				checkMate = true;
			}
			//Iteration beginnt mit neuem "m�glichen" Ziel von vorne
		}
		
		//Wenn man nach vollst�ndigem Durchlauf hier ankommt, pr�ft man die Variable und returned entsprechend
		if(checkMate){
			return true;
		}else{
			return false;	
		}
	}
	

	public Controller(){
		analyse = new Analyser();
	}
}
