package controller;


public class Analyser {
	
	public int letterConverter(String letter){
		int pos = 0;
		
		switch(letter){
		
		case "a": pos=0;
			break;
		case "b": pos=1;
			break;
		case "c": pos=2;
			break;
		case "d": pos=3;
			break;
		case "e": pos=4;
			break;
		case "f": pos=5;
			break;
		case "g": pos=6;
			break;
		case "h": pos=7;
			break;
			
		default:System.out.println("Letter Conversion failed!");
			break;
		}
		
		return pos;
		
	}
	


}
