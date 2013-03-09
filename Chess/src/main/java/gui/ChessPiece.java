package main.java.gui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class ChessPiece extends JLabel{
	
	private static final long serialVersionUID = 1L;


	public ChessPiece(String type, String color){
		this.setIcon(getIcon(type, color));
		this.setHorizontalAlignment(CENTER);
		this.setVerticalAlignment(CENTER);
	}
	

	public ImageIcon getIcon(String type, String color){
		String colorName;
        if(color==null){
        	colorName = "leer";
        }else if(color.equalsIgnoreCase("weiss")){
        	colorName = "white";
        }else{
        	colorName = "black";
        }
        if(!colorName.equals("leer")){
        	java.net.URL imgURL = getClass().getResource("/main/resources/"+type+"_"+colorName+".png");
            
            if (imgURL != null) 
            {
                return new ImageIcon(imgURL);
            } else {
                System.err.println("Couldn't find file: " + "/main/resources/"+type+"_"+colorName+".png");
                return null;
            }
    	}else{
    		return null;
    	}
	}
	
}
