package controller;

public class Command {

    private String[] values ;
    private CommandConst cConst;

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public CommandConst getCommand() {
		return cConst;
	}

	public void setCommand(CommandConst cConst) {
		this.cConst = cConst;
	}
}

enum CommandConst{
	
	      ZUG, EXIT, ABL
}
 
