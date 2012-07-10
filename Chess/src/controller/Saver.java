package controller;


import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;


import pieces.Piece;


public class Saver {

	public void saveToXMl(Piece[][] board){
		
		ArrayList<Piece> saveList = new ArrayList<Piece>();

		for(int i=0; i<8; i++){
			for(int j=0;j<8; j++){
				saveList.add(board[i][j]);			
			}
		}
		
	    try {
	        XMLEncoder en = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("Save.xml")));
	        for (Object o : saveList) {
	            en.writeObject(o);
	        }
	        en.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	}


	public Piece[][] loadFromXL (){
		
	    ArrayList<Piece> loadList = new ArrayList<Piece>();
	    boolean objectsLeft = true;
	    Piece[][] board = new Piece[8][8];
	    
	    try {
	        XMLDecoder de = new XMLDecoder(new BufferedInputStream(new FileInputStream("Save.xml")));
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
}
