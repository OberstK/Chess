package main.java.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Icon extends JLabel{

	private static final long serialVersionUID = 1L;
	
	private ImageIcon image;
	
	public Icon(String pieceName, String type, String color){
		String colorName;
        if(color==null){
        	colorName = "leer";
        }else if(color.equalsIgnoreCase("weiﬂ")){
        	colorName = "white";
        }else{
        	colorName = "black";
        }
        if(!colorName.equals("leer")){
	        try { 
	        	//InputStream input = this.getClass().getResourceAsStream("/gui/Images/"+type+" "+colorName+".png");
	            //Image image = ImageIO.read(input);
	        	this.setImageIcon(new ImageIcon("/gui/Images/"+type+" "+colorName+".png"));
	         } catch (java.lang.IllegalArgumentException ex){
	        	 System.out.println("Das Bild wurde nicht gefunden");
	         }
    	}
		this.setIcon(getImageIcon());
	}
	
	
	public ImageIcon getImageIcon() {
		return image;
	}

	public void setImageIcon(ImageIcon image) {
		this.image = image;
	}
     
	

}
