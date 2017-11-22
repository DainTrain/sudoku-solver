//Each instance of the Space class represents one square in the Sudoku board. 
//Each instance will have a number associated with it, and also an array of boolean values which will represent
//the possible numbers the Space can be.

public class Space {
	private int number; //the number of the space, 0 is an empty space
	private boolean[] options; //represents the possible numbers a space can be, where number = index + 1, 
	//e.g. index 0 is true if the Space could hold the number 1, and index 7 is false if the Space can't hold the number 8
	
	//constructor
	public Space(int initNumber) {
		number = initNumber;
		if (initNumber == 0) {
			options = new boolean[9]; //only allocates space for the array if the number isn't known (zero)
			
			//boolean arrays initialize to false, therefore a loop is needed to set each index to true
			for (int i=0; i<options.length; i++) {
				options[i] = true;
			}
		}
	}
	
	//getter and setter for the number of the Space
	public int getNumber() {
		return number;
	}
	public void setNumber(int newNumber) {
		number = newNumber;
	}
	
	
	public boolean getOption(int index) {
		return options[index];
	}
	
	//this method sets the index in the array to false, which means index + 1 is not a valid number for the Space
	public void eliminate(int index) {
		if (index < 0 || index > 8)
			return;
		options[index] = false;
	}
	
	//returns number of true values in the options array
	public int optionsSize() {
		int count = 0;
		for (int i=0; i<options.length; i++) {
			if (options[i] == true)
				count++;
		}
		return count;
	}
	
	public void oneOption() { //if optionsSize()==1, then this method finds the only possible number for a Space
		for (int i=0; i<options.length; i++)
			if (options[i] == true) {
				setNumber(i+1);
				System.out.println("Set to: " + (i+1));
			}
	}
}