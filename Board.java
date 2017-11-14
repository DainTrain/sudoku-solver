public class Board {
	
	Space[][] board;
	private int size; //number of non-zero spaces, with a max of 81
	
	public Board() {
		board = new Space[9][9];
		size = 0;
	}
	
	public void initialize(int index, int[] intArray) { //takes in the index and converts the integers in intArray to Spaces
		try {
			for (int i=0; i<intArray.length; i++) {
				if (intArray[i] != 0)
					size++;
				board[index][i] = new Space(intArray[i]);
			}
		} catch (Exception e) {
			System.out.println("Error trying to initialize the board.");
		}
	}
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int newSize) {
		size = newSize;
	}
	
	public void update() {
		System.out.println("updating...........");
		//these loops go through each space, and at each space they go through the row and column the space is in, updating the current space's possibilities
		for (int i=0; i<board.length; i++) {
			for (int j=0; j<board.length; j++) {
				if (board[i][j].getNumber() != 0)
					continue;
				if (board[i][j].optionsSize() == 1) {
					System.out.println("One option occuring at: " + i + ", " + j);
					board[i][j].oneOption();
					size++;
					continue;
				}
				if (board[i][j].getNumber() == 0) {
					for (int k=0; k<board.length; k++) {
						if (board[i][k].getNumber() != 0)
							board[i][j].eliminate(board[i][k].getNumber()-1);
					}
					for (int m=0; m<board.length; m++) {
						if (board[m][j].getNumber() != 0)
							board[i][j].eliminate(board[m][j].getNumber()-1);
					}
				}
			}
		}
		
		//these loops go through each quadrant, updating all space's possibilities based on the other spaces in the quadrant
		for (int w=1; w<8; w+=3) {
			for (int x=1; x<8; x+=3) {
				if (quadrantSize(w,x)==8) {
					quadrantSizeEight(w,x);
				}
				for (int y=w-1; y<w+2; y++) {
					for (int z=x-1; z<x+2; z++) {
						if (board[y][z].getNumber()==0) {
							for (int a=w-1; a<w+2; a++) {
								for (int b=x-1; b<x+2; b++) {
									if (board[a][b].getNumber() != 0)
										board[y][z].eliminate(board[a][b].getNumber()-1);
								}
							}
						}
					}
				}
			}
		}
	}
	
	//returns the number of non-zero numbers in the quadrant with the given center (rowIdx, colIdx)
	public int quadrantSize(int rowIdx, int colIdx) {
		int count = 0;
		for (int i=rowIdx-1; i<rowIdx+2; i++) {
			for (int j=colIdx-1; j<colIdx+2; j++) {
				if (board[i][j].getNumber() != 0)
					count++;
			}
		}
		return count;
	}
	
	//called if there is only one missing number in a quadrant, this method finds that number
	public void quadrantSizeEight(int rowIdx, int colIdx) {
		int sum = 0; //counts the numbers in the quadrants. Sum from 1 to 9 = 45
		for (int i=rowIdx-1; i<rowIdx+2; i++) {
			for (int j=colIdx-1; j<colIdx+2; j++) {
				sum += board[i][j].getNumber();
			}
		}
		for (int i=rowIdx-1; i<rowIdx+2; i++) {
			for (int j=colIdx-1; j<colIdx+2; j++) {
				if (board[i][j].getNumber() == 0) {
					board[i][j].setNumber(45-sum); //Sum from 1 to 9 = 45
					System.out.println("Quadrant size eight at: " + i + ", " + j + "set to: " + (45-sum));
					size++;
					return;
				}
			}
		}
	}
	
	public void quadrantSizeSeven(int rowIdx, int colIdx) {
		System.out.println("quadrant size seven");
		int[] numbers = new int[10];
		int[] remaining = new int[2];
		int k = 0;
		for (int i=rowIdx-1; i<rowIdx+2; i++) {
			for (int j=colIdx-1; j<colIdx+2; j++) {
				if (board[i][j].getNumber() != 0)
					numbers[board[i][j].getNumber()] = board[i][j].getNumber();
			}
		}
		for (int r=1; r<numbers.length; r++) {
			if (numbers[r] == 0)
				remaining[k++] = r;
		}
		for (int i=rowIdx-1; i<rowIdx+2; i++) {
			for (int j=colIdx-1; j<colIdx+2; j++) {
				if (board[i][j].getNumber() == 0) {
					if (board[i][j].getOption(remaining[0]-1) == false) {
						board[i][j].setNumber(remaining[1]);
						System.out.println("Quadrant size seven at: " + i + ", " + j + "set to: " + remaining[1]);
						size++;
						return;
					}
					if (board[i][j].getOption(remaining[1]-1) == false) {
						board[i][j].setNumber(remaining[0]);
						System.out.println("Quadrant size seven at: " + i + ", " + j + "set to: " + remaining[0]);
						size++;
						return;
					}
				}
			}
		}
		System.out.println("Quadrant size seven failed to place a number.");
	}
	
	/*public void quadrantSizeSix(int rowIdx, int colIdx) {
		System.out.println("quadrant size six");
		int[] numbers = new int[10];
		int[] remaining = new int[3];
		int k = 0;
		for (int i=rowIdx-1; i<rowIdx+2; i++) {
			for (int j=rowIdx-1; j<rowIdx+2; j++) {
				if (board[i][j].getNumber() != 0)
					numbers[board[i][j].getNumber()] = board[i][j].getNumber();
			}
		}
		for (int r=1; r<numbers.length; r++) {
			if (numbers[r] == 0)
				remaining[k++] = r;
		}
		for (int i=rowIdx-1; i<rowIdx+2; i++) {
			for (int j=colIdx-1; j<colIdx+2; j++) {
				if (board[i][j].getNumber() == 0) {
					for (int k=1; k<=9; k++) {
						if (board[m][n].getOption(k-1)==true) {
							if (!somewhereElse(rowIdx, colIdx, k, board[i][j])) {
								board[i][j].setNumber(k);
								size++;
							}
					
					
					
					
					
					
					
					
					
					
					
					if (board[i][j].getOption(remaining[0]-1) == false && board[i][j].getOption(remaining[1]-1) == false) {
						board[i][j].setNumber(remaining[2]);
						size++;
						return;
					}
					if (board[i][j].getOption(remaining[0]-1) == false && board[i][j].getOption(remaining[2]-1) == false) {
						board[i][j].setNumber(remaining[1]);
						size++;
						return;
					}
					if (board[i][j].getOption(remaining[1]-1) == false && board[i][j].getOption(remaining[2]-1) == false) {
						board[i][j].setNumber(remaining[0]);
						size++;
						return;
					}
				}
			}
		}
	}**/
	
	//returns the size of the row with the given index
	public int rowSize(int index) {
		int count = 0;
		for (int i=0; i<board.length; i++) {
			if (board[index][i].getNumber() != 0)
				count++;
		}
		return count;
	}
	
	//if a row has a size eight, this method finds the empty Space and fills it with the only possibility
	public void rowSizeEight(int index) {
		int sum = 0;
		for (int i=0; i<board.length; i++) {
			sum += board[index][i].getNumber();
		}
		for (int j=0; j<board.length; j++) {
			if (board[index][j].getNumber()==0) {
				board[index][j].setNumber(45-sum);
				System.out.println("Row size eight at: " + index + ", " + j + "set to: " + (45-sum));
				size++;
				return;
			}
		}
	}
	
	public void rowSizeSeven(int index) {
		System.out.println("row size seven:");
		int[] numbers = new int[10];
		int[] remaining = new int[2];
		int k = 0;
		for (int i=0; i<board.length; i++) {
			if (board[index][i].getNumber() != 0)
				numbers[board[index][i].getNumber()] = board[index][i].getNumber();
		}
		for (int j=1; j<numbers.length; j++) {
			if (numbers[j] == 0)
				remaining[k++] = j;
		}
		for (int a=0; a<board.length; a++) {
			if (board[index][a].getNumber() == 0) {
				if (board[index][a].getOption(remaining[0]-1) == false) {
					board[index][a].setNumber(remaining[1]);
					System.out.println("Row size seven at: " + index + ", " + a + "set to: " + remaining[1]);
					size++;
					return;
				}
				if (board[index][a].getOption(remaining[1]-1) == false) {
					board[index][a].setNumber(remaining[0]);
					System.out.println("Row size seven at: " + index + ", " + a + "set to: " + remaining[0]);
					size++;
					return;
				}
			}
		}
		System.out.println("Row size seven failed to place a number.");
	}
	
	public void rowSizeSix(int index) {
		System.out.println("row size six:");
		int[] numbers = new int[10];
		int[] remaining = new int[3];
		int k = 0;
		for (int i=0; i<board.length; i++) {
			if (board[index][i].getNumber() != 0)
				numbers[board[index][i].getNumber()] = board[index][i].getNumber();
		}
		for (int j=1; j<numbers.length; j++) {
			if (numbers[j] == 0)
				remaining[k++] = j;
		}
		for (int a=0; a<board.length; a++) {
			if (board[index][a].getNumber() == 0) {
				if (board[index][a].getOption(remaining[0]-1) == false && board[index][a].getOption(remaining[1]-1) == false) {
					board[index][a].setNumber(remaining[2]);
					System.out.println("row size six at: " + index + ", " + a + "set to: " + remaining[2]);
					size++;
					return;
				}
				if (board[index][a].getOption(remaining[1]-1) == false && board[index][a].getOption(remaining[2]-1) == false) {
					board[index][a].setNumber(remaining[0]);
					System.out.println("Row size six at: " + index + ", " + a + "set to: " + remaining[0]);
					size++;
					return;
				}
				if (board[index][a].getOption(remaining[0]-1) == false && board[index][a].getOption(remaining[2]-1) == false) {
					board[index][a].setNumber(remaining[1]);
					System.out.println("Row size six at: " + index + ", " + a + "set to: " + remaining[1]);
					size++;
					return;
				}
			}
		}
	}
			
			
			
	
	//returns the size of the column with the given index
	public int columnSize(int index) {
		int count = 0;
		for (int i=0; i<board.length; i++) {
			if (board[i][index].getNumber() != 0)
				count++;
		}
		return count;
	}
	
	//if a column has a size eight, this method finds the empty Space and fills it with the only possibility
	public void columnSizeEight(int index) {
		System.out.println("column size eight");
		int sum = 0;
		for (int i=0; i<board.length; i++) {
			sum += board[i][index].getNumber();
		}
		for (int j=0; j<board.length; j++) {
			if (board[j][index].getNumber()==0) {
				board[j][index].setNumber(45-sum);
				size++;
				System.out.println("Column size eight at: " + j + ", " + index + "set to: " + (45-sum));
				return;
			}
		}
	}
	
	public void columnSizeSeven(int index) {
		System.out.println("column size seven");
		int[] numbers = new int[10];
		int[] remaining = new int[2];
		int k = 0;
		for (int i=0; i<board.length; i++) {
			if (board[i][index].getNumber() != 0)
				numbers[board[i][index].getNumber()] = board[i][index].getNumber();
		}
		for (int j=1; j<numbers.length; j++) {
			if (numbers[j] == 0)
				remaining[k++] = j;
		}
		for (int a=0; a<board.length; a++) {
			if (board[a][index].getNumber() == 0) {
				if (board[a][index].getOption(remaining[0]-1) == false) {
					board[a][index].setNumber(remaining[1]);
					System.out.println("Column size seven at: " + a + ", " + index + "set to: " + remaining[1]);
					size++;
					return;
				}
				if (board[a][index].getOption(remaining[1]-1) == false) {
					board[a][index].setNumber(remaining[0]);
					System.out.println("column size seven at: " + a + ", " + index + "set to: " + remaining[0]);
					size++;
					return;
				}
			}
		}
		System.out.println("Column size seven failed to place a number.");
	}
	
	public void columnSizeSix(int index) {
		System.out.println("column size six:");
		int[] numbers = new int[10];
		int[] remaining = new int[3];
		int k = 0;
		for (int i=0; i<board.length; i++) {
			if (board[i][index].getNumber() != 0)
				numbers[board[i][index].getNumber()] = board[i][index].getNumber();
		}
		for (int j=1; j<numbers.length; j++) {
			if (numbers[j] == 0)
				remaining[k++] = j;
		}
		for (int a=0; a<board.length; a++) {
			if (board[a][index].getNumber() == 0) {
				if (board[a][index].getOption(remaining[0]-1) == false && board[a][index].getOption(remaining[1]-1) == false) {
					board[a][index].setNumber(remaining[2]);
					System.out.println("column size six at: " + a + ", " + index + "set to: " + remaining[2]);
					size++;
					return;
				}
				if (board[a][index].getOption(remaining[1]-1) == false && board[a][index].getOption(remaining[2]-1) == false) {
					board[a][index].setNumber(remaining[0]);
					System.out.println("column size six at: " + a + ", " + index + "set to: " + remaining[0]);
					size++;
					return;
				}
				if (board[a][index].getOption(remaining[0]-1) == false && board[a][index].getOption(remaining[2]-1) == false) {
					board[a][index].setNumber(remaining[1]);
					System.out.println("column size six at: " + a + ", " + index + "set to: " + remaining[1]);
					size++;
					return;
				}
			}
		}
	}
	
	public void quadrantAlgorithm() {
		System.out.println("quadrant algorithm");
		//goes through each space in the quadrant and checks if any number can't go anywhere else in the quadrant and therefore must go in the current Space
		for (int i=1; i<8; i+=3) {
			for (int j=1; j<8; j+=3) {
				for (int m=i-1; m<i+2; m++) {
					for (int n=j-1; n<j+2; n++) {
						if (board[m][n].getNumber()==0) {
							for (int k=1; k<=9; k++) {
								if (board[m][n].getOption(k-1)==true) {
									if (!somewhereElse(i, j, k, board[i][j])) {
										board[i][j].setNumber(k);
										System.out.println("Quadrant algorithm success at: " + i + ", " + j + "set to: " + k);
										size++;
										return;
									}
								}
							}
						}
					}
				}
			}
		}
							
	}
	
	//takes in a quadrant's indices, an integer n between 1-9, and a Space in that quadrant, and determines if n can possibly go anywhere else in the quadrant. If it can't, then n must go in the current Space.
	public boolean somewhereElse(int rowIdx, int colIdx, int n, Space current) {
		for (int i=rowIdx-1; i<rowIdx+2; i++) {
			for (int j=colIdx-1; j<colIdx+2; j++) {
				if (board[i][j].equals(current))
					continue;
				if (board[i][j].getNumber() != 0)
					continue;
				if (board[i][j].getOption(n-1) == true)
					return true;
			}
		}
		return false;
	}
	
	public boolean somewhereElseRow(int rowIdx, int n, Space current) {
		for (int i=0; i<board.length; i++) {
			if (board[rowIdx][i].equals(current))
				continue;
			if (board[rowIdx][i].getNumber() != 0)
				continue;
			if (board[rowIdx][i].getOption(n-1) == true)
				return true;
		}
		return false;
	}
	
	public boolean somewhereElseCol(int colIdx, int n, Space current) {
		for (int i=0; i<board.length; i++) {
			if (board[i][colIdx].equals(current))
				continue;
			if (board[i][colIdx].getNumber() != 0)
				continue;
			if (board[i][colIdx].getOption(n-1) == true)
				return true;
		}
		return false;
	}
	
	public void rowAndColAlgorithm() {
		for (int i=0; i<board.length; i++) {
			for (int j=0; j<board.length; j++) {
				if (board[i][j].getNumber() == 0) {
					for (int k=1; k<=9; k++) {
						if (board[i][j].getOption(k-1) == true) {
							if (!somewhereElseRow(i, k, board[i][j])) {
								board[i][j].setNumber(k);
								System.out.println("At : " + i + ", " + j + " in ROW and col set to:" + k);
								size++;
								return;
							}
							if (!somewhereElseCol(j, k, board[i][j])) {
								board[i][j].setNumber(k);
								System.out.println("At : " + i + ", " + j + " in row and COL set to:" + k);
								size++;
								return;
							}
						}
					}
				}
			}
		}
	}
								
	
	public void display() {
		for (int i=0; i<board.length; i++) {
			for (int j=0; j<board.length; j++) {
				System.out.print(board[i][j].getNumber());
				if (j != 8)
					System.out.print(", ");
				
			}
			System.out.println();
		}
	}
}