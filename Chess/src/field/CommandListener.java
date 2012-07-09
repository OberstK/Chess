package field;

import java.util.Scanner;


public class CommandListener {

	private String input;
	
	public String[] zugConverter(String input){
		
		String[] values = new String[4];
		
        int indexDP = input.indexOf(":");
        String data = input.substring(indexDP + 1);

        String[] parts = data.split(" auf ");
        
        parts[0] = parts[0].trim();
		parts[1] = parts[1].trim();

        values[0] = parts[0].substring(0,1);
		values[1] = parts[0].substring(1,2);
		values[2] = parts[1].substring(0,1);
		values[3] = parts[1].substring(1,2);
		
		/*
		System.out.println(values[0]);
		System.out.println(values[1]);
		System.out.println(values[2]);
		System.out.println(values[3]);
		*/

		return values;
	}


	
	public Command scanInput() {
	    Scanner scanner = new Scanner(System.in);
	    input = scanner.nextLine();
	    input = input.toLowerCase();
	    
	    Command command = new Command();

	    if (input.contains("zug")) {
	    	
	    	String[] values = this.zugConverter(input);
	    	command.setCommand(CommandConst.ZUG);
	    	command.setValues(values);

	        return command;

	    } else if(input.contains("exit")){
	
	    	command.setCommand(CommandConst.EXIT);
	    	
	        return command;
	
	    } else if(input.contains("ablage")){
	    	
	    	command.setCommand(CommandConst.ABL);
	    	
	    	return command;
	    } else{
	    	
	    	System.out.println("Input nicht erkannt!");
	    	return null;
	    }
	
	}
	
	
}
