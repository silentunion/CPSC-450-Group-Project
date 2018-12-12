package sequenceAlignment.needlemanWunsch;

import java.util.ArrayList;

import sequenceAlignment.Cell;
import sequenceAlignment.DynamicProgramming;

/**************************************************
 * 
 * Class Name : SequenceAlignment
 * Purpose : Align 2 given sequences via traceback of a score matrix
 * 
 **************************************************/

public class SequenceAlignmentWunsch extends DynamicProgramming {

   public SequenceAlignmentWunsch(String sequence1, String sequence2) {
      this(sequence1, sequence2, 1, -1, -2);
   }

   public SequenceAlignmentWunsch(String sequence1, String sequence2, int match,
         int mismatch, int gap) {
      super(sequence1, sequence2, match, mismatch, gap);
   }

   /**
	 * Method Name : getAlignment
	 * Purpose:	Returns the two aligned sequences by tracing the cells back
	 * 
	 * Parameters : Nothing
	 * 
	 * Return : An array containing two aligned sequences.
	 **/
   public String[] getAlignment() {
	  
	  //Call get score table first
	  //loadScoreTable();
	   
	  StringBuffer seq1Align = new StringBuffer();
      StringBuffer seq2Align = new StringBuffer();
      Cell currentCell = scoreTable[scoreTable.length - 1][scoreTable[0].length - 1];
      //Start from the bottom right of the score table.
      
      while (!isTraceBackFinished(currentCell)) {
         //If cell points left than grab the next character otherwise add a gap
         if (currentCell.getCol() - currentCell.getPrevCell().getCol() == 1) {
            seq1Align.insert(0, sequence1.charAt(currentCell.getCol() - 1));
         } else {
            seq1Align.insert(0, '-');
         }
       
   	  //If cell points up then grab the next character otherwise add a gap
         if (currentCell.getRow() - currentCell.getPrevCell().getRow() == 1) {
            seq2Align.insert(0, sequence2.charAt(currentCell.getRow() - 1));
         } else {
            seq2Align.insert(0, '-');
         }
 
         currentCell = currentCell.getPrevCell();
      }

      String[] alignments = new String[] { seq1Align.toString(),
            seq2Align.toString() };

      return alignments;
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
   
   //Return the score table as an integer array.
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
}
