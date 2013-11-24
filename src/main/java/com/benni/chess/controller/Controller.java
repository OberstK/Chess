package com.benni.chess.controller;

import java.util.ArrayList;

import com.benni.chess.model.*;


public class Controller {
	
	private final Analyser analyse;
	
	
	//Hole Figur an dieser Position

	
	//Hole den jeweiligen König
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
		//Weisse Figuren
		figuren.add(new King("weiss"));
		figuren.add(new Queen("weiss"));
		figuren.add(new Knight("weiss", 1));
		figuren.add(new Knight("weiss", 2));
		figuren.add(new Bishop("weiss", 1));
		figuren.add(new Bishop("weiss", 2));
		figuren.add(new Rock("weiss", 1));
		figuren.add(new Rock("weiss", 2));
		for(int i=0; i<8; i++){
			figuren.add(new Pawn("weiss", i));
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
		
		//Leere Felder füllen
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
	

	
	//Wechsel den "Dran"-Status der Spieler
	public void changePlayers(Board boardObj){
		Player[] playersInGame = boardObj.getPlayersInGame();
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
			moves.addAll(pawn.getPossibleHitDestinations(x, y, board, owner));
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
	 * Diverse Prüfungen finden statt
	 */
	
	public boolean macheZug(Piece figur, int xPosEnd, int yPosEnd, Board boardObj){
		Piece[][] board = boardObj.getBoard();
		boolean possible = false;
		boolean enemy = false;
		
		if(this.testIfMoveAllowed(figur, xPosEnd, yPosEnd, board)){
			//Testen ob Feld leer ist
			if(analyse.testIfEmpty(xPosEnd, yPosEnd, board)){
				possible=true;
			//Testen ob auf Feld ein Gegner steht
			}else if(analyse.testIfEnemy(figur.isOwner(), xPosEnd, yPosEnd, board)){
				enemy=true;
				possible=true;
			//Wenn belegt -> Gib false zurück
			}else{
				System.out.println("Feld belegt!");
				return false;
			}
		}else{
			System.out.println("Zug nicht erlaubt!");
			return false;
		}
		if(possible){
			//Zug testweise durchführen
			int xPosStart = figur.getPositionX();
			int yPosStart = figur.getPositionY();
			Piece altFigur = boardObj.getPieceOnBoard(xPosEnd, yPosEnd);
			figur.setPositionX(xPosEnd);
			figur.setPositionY(yPosEnd);
			boardObj.fillEmptySpot(xPosStart, yPosStart);
			boardObj.setPieceOnSquare(figur);
			
			//testen ob man jetzt im Schach steht
			if(analyse.testIfOwnerPutsInCheck(board, boardObj.getPlayerNotOnTurn().isOwner())){
				System.out.println("Nach dem Zug stehst du im Schach!");
				//Zug rückgängig machen
				figur.setPositionX(xPosStart);
				figur.setPositionY(yPosStart);
				boardObj.setPieceOnSquare(figur);
				if(altFigur==null){
					boardObj.fillEmptySpot(xPosEnd, yPosEnd);
				}else{
					boardObj.setPieceOnSquare(altFigur);
				}
				return false;
			}else if(enemy){
				boardObj.addPieceToOutList(altFigur);
				System.out.println(altFigur.getType()+" geschlagen!");
				return true;
			}else{
				return true;
			}
			
			
		}
		
		return true;
	}
	
	public boolean testIfMoveAllowed(Piece figur, int xPosEnd, int yPosEnd, Piece[][] board){
		//Start und Endposition holen
		int xPosStart = figur.getPositionX();
		int yPosStart = figur.getPositionY();
		
		//Prüfung ob Bauer und ob Zug möglich ist
		if(figur instanceof Pawn){
			Pawn pawn = (Pawn) figur;
			if(pawn.movePossible(xPosStart, yPosStart, xPosEnd, yPosEnd, board, pawn.isOwner())){
				return true;
			}else if(pawn.hitPossible(xPosStart, yPosStart, xPosEnd, yPosEnd, board, pawn.isOwner())){
				return true;
			}
		//Prüfung ob Springer und ob Zug möglich ist
		}else if(figur instanceof Knight){
			Knight knight = (Knight) figur;
			if(knight.movePossible(xPosStart, yPosStart, xPosEnd, yPosEnd)){
				return true;
			}
		//Prüfung ob Königin und ob Zug möglich ist
		}else if(figur instanceof Queen){
			Queen queen = (Queen) figur;
			if(queen.movePossible(xPosStart, yPosStart, xPosEnd, yPosEnd, board)){
				return true;
			}
		//Prüfung ob Läufer und ob Zug möglich ist
		}else if(figur instanceof Bishop){
			Bishop bishop = (Bishop) figur;
			if(bishop.movePossible(xPosStart, yPosStart, xPosEnd, yPosEnd, board)){
				return true;
			}
		//Prüfung ob Turm und ob Zug möglich ist
		}else if(figur instanceof Rock){
			Rock rock = (Rock) figur;
			if(rock.movePossible(xPosStart, yPosStart, xPosEnd, yPosEnd, board)){
				return true;
			}
		//Prüfung ob König und ob Zug möglich ist
		}else if(figur instanceof King){
			King king = (King) figur;
			if(king.movePossible(xPosStart, yPosStart, xPosEnd, yPosEnd)){
				return true;
			}
		}
		return false;
	}

	
	//Führt eine kurze Rochade durch -> nimmt Farbe als Wert an
	public void doKurzeRochade(boolean owner, Board boardObj){
		//Prüfung ob Weiss oder Schwarz, verändert die beteiligte Zeile
		int i;
		if(owner){
			i=7;
		}else{
			i=0;
		}
		//Holt beteiligten König und Turm
		Rock rock = (Rock) boardObj.getPieceOnBoard(7, i+1);
		King king = (King) boardObj.getPieceOnBoard(4, i+1);
		//Setzt neue Position
		king.setPositionX(6);
		rock.setPositionX(5);
		//Setzt die Figuren an neue Position und löscht alte Position
		boardObj.setPieceOnSquare(king);
		boardObj.setPieceOnSquare(rock);
		boardObj.fillEmptySpot(7, i);
		boardObj.fillEmptySpot(4, i);
		System.out.println("kurze Rochade durchgeführt");
	}
	
	//Führt eine lange Rochade durch -> nimmt Farbe als Wert an
	public void doLangeRochade(boolean owner, Board boardObj){
		//Prüfung ob Weiss oder Schwarz, verändert die beteiligte Zeile
		int i;
		if(owner){
			i=7;
		}else{
			i=0;
		}
		//Holt beteiligten König und Turm
		Rock rock = (Rock) boardObj.getPieceOnBoard(0, i+1);
		King king = (King) boardObj.getPieceOnBoard(4, i+1);
		//Setzt neue Position
		king.setPositionX(2);
		rock.setPositionX(3);
		//Setzt die Figuren an neue Position und löscht alte Position
		boardObj.setPieceOnSquare(king);
		boardObj.setPieceOnSquare(rock);
		boardObj.fillEmptySpot(0, i);
		boardObj.fillEmptySpot(4, i);
		System.out.println("lange Rochade durchgeführt");
		
	}

	//Prüft ob die möglichen Züge des gegnerischen Königs im Schach noch Optionen frei halten
	public boolean testMovesForCheckMate(boolean owner, Board boardObj){
		//hole Brett
		Piece[][] board = boardObj.getBoard();
		//holt König auf dem Brett
		King king = boardObj.getKingOnBoard(owner);
		//Mögliche Züge des Königs generieren
		ArrayList<String> possMoves = king.getPossibleMoveDestinations(king.getPositionX(), king.getPositionY());
		
		//Initiiere beteiligte Informationen
		boolean checkMate = false;
		int xNow = king.getPositionX();
		int yNow = king.getPositionY();
		
		//iteriert durch alle Möglichkeiten
		for(String item: possMoves){
			//Verarbeitet Positions-String zu X und Y Wert
	        String[] parts = item.split(",");
	        parts[0] = parts[0].trim();
			parts[1] = parts[1].trim();
	        int xMove = Integer.parseInt(parts[0]);
			int yMove = Integer.parseInt(parts[1]);
			//Prüfe ob Figur auf dem zu prüfenden Feld ein Gegner ist (wenn nicht-> keine Option)
			if(analyse.testIfEnemy(owner, xMove, yMove, board) || analyse.testIfEmpty(xMove, yMove, board)){
				//Führt den Zug auf das Prüffeld testweise durch
				king.setPositionX(xMove);
				king.setPositionY(yMove);
				Piece zwischen = board[yMove][xMove];
				board[yMove][xMove] = king;
				board[yNow][xNow] = this.fillEmptySpot(xNow, yNow);
				//Testet ob man immer noch im Schach steht nach diesem Zug
				if(!analyse.testIfOwnerPutsInCheck(board, !owner)){
					//wenn nicht, wird der Testzug "undo-ed" und false zurück gegeben
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
			//Iteration beginnt mit neuem "möglichen" Ziel von vorne
		}
		
		//Wenn man nach vollständigem Durchlauf hier ankommt, prüft man die Variable und returned entsprechend
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
