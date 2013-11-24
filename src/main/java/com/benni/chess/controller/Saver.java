package main.java.controller;


import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;

import main.java.model.Piece;
import main.java.model.Player;




public class Saver {

	public void saveBoardToXML(Piece[][] board){
		
		ArrayList<Piece> saveList = new ArrayList<Piece>();

		for(int i=0; i<8; i++){
			for(int j=0;j<8; j++){
				saveList.add(board[i][j]);			
			}
		}
		
	    try {
	        XMLEncoder en = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("Board.xml")));
	        for (Object o : saveList) {
	            en.writeObject(o);
	        }
	        en.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	}
	
	public void savePlayersToXML(Player[] spieler){

	    try {
	        XMLEncoder en = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("Players.xml")));
	        for (Object o : spieler) {
	            en.writeObject(o);
	        }
	        en.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	}
	
	public void saveOutListToXML(ArrayList<Piece> outList){
		
		try {
	        XMLEncoder en = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("Outs.xml")));
	        for (Object o : outList) {
	            en.writeObject(o);
	        }
	        en.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


	public Piece[][] loadBoardFromXML (){
		
	    ArrayList<Piece> loadList = new ArrayList<Piece>();
	    boolean objectsLeft = true;
	    Piece[][] board = new Piece[8][8];
	    
	    try {
	        XMLDecoder de = new XMLDecoder(new BufferedInputStream(new FileInputStream("Board.xml")));
	        while (objectsLeft) {
	        	loadList.add((Piece) de.readObject());
	        }
	        de.close();
	    } catch (java.lang.ArrayIndexOutOfBoundsException e){
	        objectsLeft = false;
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	        return null;
	    }
	   
	    for(Piece item: loadList){
	    	int x = item.getPositionX();;
	    	int y = item.getPositionY();;

	    	board[y][x] = item;
	    }

	    return board;
	}
	
	public Player[] loadPlayersFromXML (){
		
	    Player[] spieler = new Player[2];
	    boolean objectsLeft = true;
	    int i=0;
	    
	    try {
	        XMLDecoder de = new XMLDecoder(new BufferedInputStream(new FileInputStream("Players.xml")));
	        while (objectsLeft) {
	        	spieler[i] = (Player) de.readObject();
	        	i++;
	        }
	        de.close();
	    } catch (java.lang.ArrayIndexOutOfBoundsException e){
	        objectsLeft = false;
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	        return null;
	    }
	  
	    return spieler;
	}
	
	public ArrayList<Piece> loadOutListFromXML(){
		ArrayList<Piece> loadList = new ArrayList<Piece>();
		boolean objectsLeft = true;
		
		try {
	        XMLDecoder de = new XMLDecoder(new BufferedInputStream(new FileInputStream("Outs.xml")));
	        while (objectsLeft) {
	        	loadList.add((Piece) de.readObject());
	        }
	        de.close();
	    } catch (java.lang.ArrayIndexOutOfBoundsException e){
	        objectsLeft = false;
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	        return null;
	    }

	    return loadList;
	}
}
