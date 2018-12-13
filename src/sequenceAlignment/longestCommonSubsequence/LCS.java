package sequenceAlignment.longestCommonSubsequence;

import java.util.ArrayList;

import sequenceAlignment.Cell;
import sequenceAlignment.DynamicProgramming;

public class LCS extends DynamicProgramming{

	//set up LCS
	public LCS(String sequence1, String sequence2) 
	{
		super(sequence1, sequence2);
	}
	
	//return LCS
	public String getLongestCommonSubsequence()
	{
		loadScoreTable();
		return (String) getLCSTraceback();
	}
	
	/**
	 * Method Name : populateTable
	 * Purpose:	Is responsible for generating the score values and cell pointers
	 * 
	 * Parameters : Nothing
	 * 
	 * Return : Nothing
	 **/
	@Override
	protected void populateTable()
	{
		for(int row = 1; row < scoreTable.length; row++)
		{
			for(int col = 1; col < scoreTable[row].length; col++)
			{
				Cell currentCell = scoreTable[row][col];
	            Cell cellAbove = scoreTable[row - 1][col];
	            Cell cellToLeft = scoreTable[row][col - 1];
	            Cell cellAboveLeft = scoreTable[row - 1][col - 1];
	            
	            int aboveScore = cellAbove.getScore();
	    	    int leftScore = cellToLeft.getScore();
	    	    int matchScore;
	    	    if (sequence1.charAt(currentCell.getCol() - 1) == sequence2.charAt(currentCell.getRow() - 1)) 
	    	    {
	    	    	matchScore = cellAboveLeft.getScore() + 1;
	    	    } 
	    	    else 
	    	    {
	    	        matchScore = cellAboveLeft.getScore();
	    	    }
	    	    int cellScore;
	    	    Cell cellPointer;
	    	    if (matchScore >= aboveScore) 
	    	    {
	    	    	if (matchScore >= leftScore) 
	    	    	{
	    	        // matchScore >= aboveScore and matchScore >= leftScore
	    	        cellScore = matchScore;
	    	        cellPointer = cellAboveLeft;
	    	    	} 
	    	    	else 
	    	    	{
	    	    		// leftScore > matchScore >= aboveScore
	    	    		cellScore = leftScore;
	    	    		cellPointer = cellToLeft;
	    	    	}
	    	      } 
	    	      else 
	    	      {
	    	    	  if (aboveScore >= leftScore) 
	    	    	  {
	    	    		  // aboveScore > matchScore and aboveScore >= leftScore
	    	    		  cellScore = aboveScore;
	    	    		  cellPointer = cellAbove;
	    	    	  } 
	    	    	  else 
	    	    	  {
	    	    		  // leftScore > aboveScore > matchScore
	    	    		  cellScore = leftScore;
	    	    		  cellPointer = cellToLeft;
	    	    	  }
	    	      }
	    	      currentCell.setScore(cellScore);
	    	      currentCell.setPrevCell(cellPointer);
			}
		}
	}
	
	//Call this to get the full LCS for the score matrix
	public String getLCSTraceback()
	{
		StringBuffer lCSBuf = new StringBuffer();
		Cell currentCell = scoreTable[scoreTable.length - 1][scoreTable[0].length - 1];
	    while (currentCell.getScore() > 0)
	    {
	    	Cell prevCell = currentCell.getPrevCell();
	        if ((currentCell.getRow() - prevCell.getRow() == 1 && currentCell.getCol() - prevCell.getCol() == 1)
	               && currentCell.getScore() == prevCell.getScore() + 1) 
	        {
	            lCSBuf.insert(0, sequence1.charAt(currentCell.getCol() - 1));
	        }
	        currentCell = prevCell;
	      }
	    return lCSBuf.toString();
	}
	
	//Call this to get the traceback up to a certain point within the score matrix.
	public String getLCSTraceback(Cell stopCell)
	{
		//Load table first
		loadScoreTable();
		
		StringBuffer lCSBuf = new StringBuffer();
		Cell currentCell = scoreTable[scoreTable.length - 1][scoreTable[0].length - 1];
	    while (!currentCell.equals(stopCell))
	    {
	    	Cell prevCell = currentCell.getPrevCell();
	        if ((currentCell.getRow() - prevCell.getRow() == 1 && currentCell.getCol() - prevCell.getCol() == 1)
	               && currentCell.getScore() == prevCell.getScore() + 1) 
	        {
	            lCSBuf.insert(0, sequence1.charAt(currentCell.getCol() - 1));
	        }
	        currentCell = prevCell;
	      }
	    return lCSBuf.toString();
	}
	

	public Cell[][] getScoreTable() {
		loadScoreTable();
		   
		 //convert score table from Cell object to Integers.
		 /*int[][] matrix = new int[scoreTable.length][scoreTable[0].length];
		 for(int i = 0; i < matrix.length; i++)
		 {
			 for(int j = 0; j < matrix[i].length; j++)
			 {
				 matrix[i][j] = scoreTable[i][j].getScore();
			 }
		 }
		 return matrix;*/
		 return scoreTable;
	}
	
	 public ArrayList<Cell> getTracebackPath()
	   {
		 //Call get score table first
			  //loadScoreTable();
		   
		      Cell currentCell = scoreTable[scoreTable.length - 1][scoreTable[0].length - 1];
		      ArrayList<Cell> cellList = new ArrayList<Cell>();
		      //Start from the bottom right of the score table.
		      
		      cellList.add(currentCell);
		      while (!isTraceBackFinished(currentCell)) {
	  
		    	  currentCell = currentCell.getPrevCell();
		    	  cellList.add(currentCell);
		      }

		      return cellList;
	   }
	 
	   /**
		 * Method Name : isTraceBackFinished
		 * Purpose:	Checks whether the next step in the trace is the end
		 * 
		 * Parameters : currentCell - The current cell being examined
		 * 
		 * Return : A boolean stating whether the trace back is finished or not
		 **/
	   private boolean isTraceBackFinished(Cell currentCell) {
		  //If we reach the left bound of the array then we are finished
	      if (currentCell.getPrevCell() == null)
	    	  return true;
	      else
	    	  return false;
	   }
}
