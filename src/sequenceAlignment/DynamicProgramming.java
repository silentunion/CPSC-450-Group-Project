package sequenceAlignment;

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
	protected int match = 0;
	protected int mismatch = 0;
	protected int space = 0;

	//Constructor for the LCS algorithm.
	public DynamicProgramming(String sequence1, String sequence2)
	{
		this.sequence1 = sequence1;
		this.sequence2 = sequence2;		
		scoreTable = new Cell[sequence2.length() + 1][sequence1.length() + 1];
	}
	
	//Constructor for Needleman Wunsch algorithm.
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
	abstract protected void populateTable();

}
