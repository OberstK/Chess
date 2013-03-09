package main.java.gui;

import main.java.model.*;

public class Viewer {
	
	public void zeigeSpielBrett(Piece[][] spielBrett){
		
		
		
		System.out.println("    A     B     C     D     E     F     G     H    ");


		for(int i=0; i<8; i++){
			System.out.println("--------------------------------------------------");
			
			System.out.print(i+1+" ");
			for(int j=0;j<8; j++){
				System.out.print("| "+spielBrett[i][j].getSymbol()+" |");
			}
			System.out.print("\n");

			
		}
		System.out.println("--------------------------------------------------");

	}
	
	public Viewer(){
		
	}

}
