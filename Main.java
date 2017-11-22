import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("Welcome to the Sudoku solver. Please enter one line of the puzzle at a time.");
		System.out.println("Separate the numbers using commas; Use the number zero (0) for empty spaces.");
		Scanner keyboard = new Scanner(System.in);
		
		Board board = new Board();
		
		int line = 0;
		while (line<9) {
			String inputLine = keyboard.nextLine();
			String[] stringArr = inputLine.split(",");
			int[] intArr = new int[stringArr.length];
			try {
				for (int i=0; i<stringArr.length; i++)
					intArr[i] = Integer.parseInt(stringArr[i]);
			} catch (Exception e) {
				System.out.println("Error parsing the input array.");
				continue;
			}
			board.initialize(line, intArr);
			line++;
		}
		while (board.getSize() < 81) { //stops when all spaces are filled with a non-zero number
			int tempSize = board.getSize();
			board.update(); //updates options arrays for all spaces, filling in the space if only one option is possible

			
			for (int i=0; i<9; i++) {
				if (board.rowSize(i)==8)
					board.rowSizeEight(i);
				if (board.columnSize(i)==8)
					board.columnSizeEight(i);
				if (board.rowSize(i)==7)
					board.rowSizeSeven(i);
				if (board.columnSize(i)==7)
					board.columnSizeSeven(i);
				if (board.rowSize(i)==6)
					board.rowSizeSix(i);
				if (board.columnSize(i)==6)
					board.columnSizeSix(i);
			}
			
			
			board.update();
			
			for (int i=1; i<8; i+=3) {
				for (int j=1; j<8; j+= 3) {
					if (board.quadrantSize(i, j) == 8)
						board.quadrantSizeEight(i, j);
					if (board.quadrantSize(i, j) == 7)
						board.quadrantSizeSeven(i, j);
					//if (board.quadrantSize(i, j) == 6)
						//board.quadrantSizeSix(i, j);
				}
			}
			
			board.quadrantAlgorithm();
			board.rowAndColAlgorithm();
			
			//TEST: if the board size doesn't change then I exit because it's not working!!
			if (board.getSize() == tempSize)
				board.setSize(999);
			board.display();
	
		}
		System.out.println("\nHere is your solved puzzle.\n");
		board.display();
	}
}