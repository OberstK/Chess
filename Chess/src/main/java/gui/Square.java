package main.java.gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Square extends JPanel{
	

	private static final long serialVersionUID = 1L;


    public Square() {
        Border blackline = BorderFactory.createLineBorder(Color.black);
        setBorder(blackline);
    }
     
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
    }

}
