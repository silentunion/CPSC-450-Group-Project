package application.NeedlemanWunsch;
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

   public Cell[][] getScoreTable() {
	   loadScoreTable();
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
}
