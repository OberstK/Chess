package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Square extends JPanel{
	

	private static final long serialVersionUID = 1L;
	private String piece;

    public Square(String piece) {
        Border blackline = BorderFactory.createLineBorder(Color.black);
        setBorder(blackline);
        this.setLayout(new GridBagLayout());
        this.setPiece(piece);
    }
     
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Font font = new Font("TimesRoman", Font.PLAIN, 30);
     // Find the size of string s in font f in the current Graphics context g.
        FontMetrics fm   = g.getFontMetrics(font);
        java.awt.geom.Rectangle2D rect = fm.getStringBounds(piece, g);

        int textHeight = (int)(rect.getHeight()); 
        int textWidth  = (int)(rect.getWidth());
        int panelHeight= this.getHeight();
        int panelWidth = this.getWidth();
        g.fillRect(panelWidth/2-20,panelHeight/2-20, 40, 40);
        // Center text horizontally and vertically
        int x = (panelWidth  - textWidth)  / 2;
        int y = (panelHeight - textHeight) / 2  + fm.getAscent();
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(piece,x, y);// Draw the string.
        
    }

	public String getPiece() {
		return piece;
	}

	public void setPiece(String piece) {
		this.piece = piece;
	}
     

}
