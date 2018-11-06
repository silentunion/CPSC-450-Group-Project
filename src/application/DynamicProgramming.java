package application;
/**************************************************
 * 
 * Class Name : DynamicProgramming
 * Purpose : To calculate and fill out the score matrix for sequence alignment
 * 
 **************************************************/

public abstract class DynamicProgramming {

	protected String sequence1;
	protected String sequence2;
	protected Cell[][] scoreTable;
	protected int match;
	protected int mismatch;
	protected int space;

	public DynamicProgramming(String sequence1, String sequence2, int match, int mismatch, int gap) {
		this.sequence1 = sequence1;
		this.sequence2 = sequence2;
		this.match = match;
		this.mismatch = mismatch;
		this.space = gap;
		scoreTable = new Cell[sequence2.length() + 1][sequence1.length() + 1];
	}

	/**
	 * Method Name : initializeScores
	 * Purpose:	Sets the initial and important score values for the score matrix 
	 * 
	 * Parameters : Nothing
	 * 
	 * Return : Nothing
	 **/
	protected void initializeScores() {
		for (int i = 0; i < scoreTable.length; i++) {
			for (int j = 0; j < scoreTable[i].length; j++) {
				//The first row and first column characters will never match because our table is of size sequence length + 1
				//This means there is always a gap. Everywhere else has yet to be determined
				if (i == 0 && j != 0) {
					scoreTable[i][j].setScore(j * space);
				} else if (j == 0 && i != 0) {
					scoreTable[i][j].setScore(i * space);
				} else {
					scoreTable[i][j].setScore(0);
				}
			}
		}
	}

	/**
	 * Method Name : initializePointers
	 * Purpose:	Sets the initial and important score cell pointers for the score matrix 
	 * 
	 * Parameters : Nothing
	 * 
	 * Return : Nothing
	 **/
	protected void initializePointers() {
		for (int i = 0; i < scoreTable.length; i++) {
			for (int j = 0; j < scoreTable[i].length; j++) {		
				// The 0th row and 0th column always traceback to the left and upwards
				// The top left corner points nowhere (is our end goal for traceback) and the remaining cells have yet to be decided
				if (i == 0 && j != 0) {
					scoreTable[i][j].setPrevCell(scoreTable[i][j - 1]);
				} else if (j == 0 && i != 0) {
					scoreTable[i][j].setPrevCell(scoreTable[i - 1][j]);
				} else {
					scoreTable[i][j].setPrevCell(null);
				}
			}
		}
	}

	/**
	 * Method Name : initializeTable
	 * Purpose:	Creates our score table and initializes all the values
	 * 
	 * Parameters : Nothing
	 * 
	 * Return : Nothing
	 **/
	protected void initializeTable() {
		// First we create a score cell for each entry in the table
		for (int i = 0; i < scoreTable.length; i++) {
			for (int j = 0; j < scoreTable[i].length; j++) {
				scoreTable[i][j] = new Cell(i, j);
			}
		}
		// Now we initialize the values for each score cell.
		initializeScores();
		initializePointers();

	}

	/**
	 * Method Name : loadScoreTable
	 * Purpose:	Creates our score matrix and then begins to fill it out
	 * 
	 * Parameters : Nothing
	 * 
	 * Return : Nothing
	 **/
	protected void loadScoreTable() {
		initializeTable();
		populateTable();
	}
	
	/**
	 * Method Name : populateTable
	 * Purpose:	Is responsible for generating the score values and cell pointers
	 * 
	 * Parameters : Nothing
	 * 
	 * Return : Nothing
	 **/
	protected void populateTable() {
		
		//Zero'th row and column have already been calculated
		for (int row = 1; row < scoreTable.length; row++) {
			for (int col = 1; col < scoreTable[row].length; col++) {
				
				Cell currentCell = scoreTable[row][col];
				Cell cellAbove = scoreTable[row - 1][col];
				Cell cellToLeft = scoreTable[row][col - 1];
				Cell cellAboveLeft = scoreTable[row - 1][col - 1];
				
				/*the score[i,j] is the max of
				 *		the score of the cell above + gap value
				 *		the score of the cell to the left + gap value
				 *		the score of the cell to the up and left + mis or mismatch score
				 */
				int rowScore = cellAbove.getScore() + space;
				int colScore = cellToLeft.getScore() + space;
				int matchOrMismatchScore = cellAboveLeft.getScore();								
				if (sequence2.charAt(currentCell.getRow() - 1) == sequence1.charAt(currentCell.getCol() - 1)) {
					matchOrMismatchScore += match;
				} else {
					matchOrMismatchScore += mismatch;
				}
				
				//Now we calculate the max of the scores and set the score and pointer accordingly		
				if (rowScore >= colScore) {
					if (matchOrMismatchScore >= rowScore) {
						currentCell.setScore(matchOrMismatchScore);
						currentCell.setPrevCell(cellAboveLeft);
					} else {
						currentCell.setScore(rowScore);
						currentCell.setPrevCell(cellAbove);
					}
				} else {
					if (matchOrMismatchScore >= colScore) {
						currentCell.setScore(matchOrMismatchScore);
						currentCell.setPrevCell(cellAboveLeft);
					} else {
						currentCell.setScore(colScore);
						currentCell.setPrevCell(cellToLeft);
					}
				}
			}
		}
	}

	public void printScoreTable() {
		loadScoreTable();
		System.out.print("\n");
		for (int i = 0; i < sequence2.length() + 2; i++) {
			for (int j = 0; j < sequence1.length() + 2; j++) {
				if (i == 0) {
					if (j == 0 || j == 1) {
						System.out.print("  ");
					} else {
						if (j == 2) {
							System.out.print("     ");
						} else {
							System.out.print("   ");
						}
						System.out.print(sequence1.charAt(j - 2));
					}
				} else if (j == 0) {
					if (i == 1) {
						System.out.print("  ");
					} else {
						System.out.print(" " + sequence2.charAt(i - 2));
					}
				} else {
					String toPrint;
					Cell currentCell = scoreTable[i - 1][j - 1];
					Cell prevCell = currentCell.getPrevCell();
					if (prevCell != null) {
						if (currentCell.getCol() == prevCell.getCol() + 1
								&& currentCell.getRow() == prevCell.getRow() + 1) {
							toPrint = "\\";
						} else if (currentCell.getCol() == prevCell.getCol() + 1) {
							toPrint = "-";
						} else {
							toPrint = "|";
						}
					} else {
						toPrint = " ";
					}
					int score = currentCell.getScore();
					String s = String.format("%1$3d", score);
					toPrint += s;
					System.out.print(toPrint);
				}

				System.out.print(' ');
			}
			System.out.println();
		}
	}
}
