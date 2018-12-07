import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;


//never go negative
public class Smith_waterman
{
	enum Direction
    {
    	up,left,diagonal,start,blank
    }
	
	//the two sequences
	private static String A;
	private static String B;

	//the values for the scoring matrix
	private static int match;
	private static int miss;
	private static int gap;

	//scoring matrix for A and B
	private static Integer [][] scoringMatrix;

	//sequences and scoring matrix values are hardcoded for now
	public Smith_waterman()
	{
		//wikipedia exampls
		//A="TGGTTACGG";
		//B="CGGTTGACTA";
		
		B="AAATGCAAAGCTCG";
		A="AATGCA";

		match=1;
		miss=-1;
		gap=-2;

		scoringMatrix= new Integer[B.length()+1][A.length()+1];
		//fill it with 0s in 1st row and column and -1s everywhere else
		for(int row=0;row<scoringMatrix.length;row++)
		{
			for(int col=0;col<scoringMatrix[row].length;col++)
			{
				//if(row==0 || col==0)
					scoringMatrix[row][col]=0;/*
				else
					scoringMatrix[row][col]=-1;*/
			}
		}
	}
	//checks if two character match, miss or if one is a gap
	public static int checkScore(char a, char b)
	{
		//gap = "_"
		if(a=='_' || b=='_')
			return gap;		
		if(a==b)	
			return match;	

		return miss;
	}
	//finds max value between 4 values
	public static int max(int a, int b, int c, int d)
	{

		int max = a;

		if (b > max)
			max = b;
		if (c > max)
			max = c;
		if (d > max)
			max = d;

		return max;
	}
	//prints out the scoring matrix
	public void print()
	{
		System.out.println("  "+A);
		for(int row=0;row<scoringMatrix.length;row++)
		{
			if(row==0)
				System.out.print(" ");
			else
				System.out.print(B.charAt(row-1));
			for(int col=0;col<scoringMatrix[row].length;col++)
			{

				System.out.print(scoringMatrix[row][col]);//+"  ");
			}

			System.out.println();
		}
	}
	//fills the scoring matrix 
	public void fillMatrix()
	{
		for(int row=1;row<scoringMatrix.length;row++)
		{
			for(int col=1;col<scoringMatrix[row].length;col++)
			{


				//System.out.print(" I am going to compare "+A.charAt(col-1)+" and "+B.charAt(row-1));
				scoringMatrix[row][col]=max(
						scoringMatrix[row-1][col-1]+checkScore(A.charAt(col-1),B.charAt(row-1)),
						scoringMatrix[row][col-1]+gap,
						scoringMatrix[row-1][col]+gap,
						0);
				//System.out.print(" result is "+checkScore(A.charAt(col-1),B.charAt(row-1)));
			}
			//System.out.println();
		}
	}
	/*public String[] getAlignment() {
		  
		  //loadScoreTable();
		   
		  StringBuffer seq1Align = new StringBuffer();
	      StringBuffer seq2Align = new StringBuffer();
	      Cell currentCell = scoringMatrix[scoringMatrix.length - 1][scoringMatrix[0].length - 1];
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
	   }*/
	public static void main(String[] args)
	{
		Smith_waterman myMatrix = new Smith_waterman();


		myMatrix.print();

		System.out.println();

		myMatrix.fillMatrix();

		System.out.println("A : "+A);
		System.out.println("B : "+B);
		System.out.println();

		myMatrix.print();
		
		Stack<Integer> myValues = new Stack<Integer>();
		Stack<Coord> myPositions = new Stack<Coord>();
		Stack<Direction> myDirections = new Stack<Direction>();
		//Stack<Cell> myCells = new Stack<Cell>();
		//Cell cell= myMatrix.new Cell();
		Coord prev= myMatrix.new Coord();
		Coord prev2= myMatrix.new Coord();
		
		

		//find max element in the matrix
		ArrayList<Integer> allMax = new ArrayList<Integer>();
		int max=0;
		int myCol=0; 
		int myRow=0;
		for(int row=0;row<scoringMatrix.length;row++)
		{
			for(int col=0;col<scoringMatrix[row].length;col++)
			{
				if(scoringMatrix[row][col]>=max)
				{
					max=scoringMatrix[row][col];
					myRow=row;
					myCol=col;
					//allMax.add(max);
					//System.out.println(row+" "+col);
				}

			}
		}
		//read through again and add all = max
		System.out.println(allMax);
	
		StringBuffer seq1Align = new StringBuffer();
	    StringBuffer seq2Align = new StringBuffer();
	    
	
	
	    System.out.println(seq1Align);
	    System.out.println(seq2Align);		
	    Coord pos;
	    int next=-1;
	    int current=max;
	    Direction going=Direction.blank;
	    Direction coming=Direction.start;
		myDirections.push(coming);
	   // myRow--;
	  //  myCol--;
	   // boolean skip=false;
	    while(current>0)
	    {
	    	System.out.println("current "+current);
	    	myValues.push(current);
	    	if(!myPositions.isEmpty())
	    	{
	    		/*if(myPositions.size()>1)
	    		{	
	    			prev2=myPositions.elementAt(myPositions.size()-2);
	    	    	System.out.println("prev prev "+prev2);

	    		}*/
		    	System.out.println("previous pos : "+myPositions.peek());
		    	prev=myPositions.peek();
		    	
		    //	skip=true;
	    	}
	    	else
	    	{
	    		prev= myMatrix.new Coord(myRow+1,myCol+1);
	    	}
	    	
	    	pos = myMatrix.new Coord(myRow+1,myCol+1);
	    	System.out.println("at position "+pos);
	    	myPositions.push(pos);
	    	//System.out.println(myDirections.peek());
	    	//System.out.println(prev+" "+pos);
	    	//System.out.println(skip);
	    //	if(skip)
	    	{
    			System.out.println("coming is "+coming);
    			//System.out.println(going);


	    			    		//System.out.println(myRow);
	    		//System.out.println(myCol);

	    		
	    		/*switch(coming)
	    		{
	    		case start:
	    			seq1Align.insert(0, A.charAt(myCol-1));
	  			    seq2Align.insert(0, B.charAt(myRow-1));
	    			break;
	    		case up:
	    			seq1Align.insert(0, '_');
	    			seq2Align.insert(0, B.charAt(myRow-1));
	    			break;
	    		case left:
	    			seq2Align.insert(0, '_');
	    			seq1Align.insert(0, A.charAt(myCol-1));
	    			break;
	    		case diagonal:
	    			seq1Align.insert(0, A.charAt(myCol-1));
	  			    seq2Align.insert(0, B.charAt(myRow-1));
	    			break;
	    		default:
	    			System.out.println("bad");

	    			break;
	    		}*/

	    		//if(prev.x-pos.x==1)
	    		if(prev.x-myRow==1)
	    		//if(B.charAt(myRow-1)==A.charAt(myCol-1))
	    		{
	    			
	    			//seq2Align.insert(0, B.charAt(prev.x-1));
	    			//seq2Align.insert(0, B.charAt(myRow-1));
	    			

	    		}
	    		else
	    		{
	    			//seq2Align.insert(0, '_');
	    			//myRow--;
	    		}
	    		//if(prev.y-pos.y==1)
	    		if(prev.y-myCol==1)
	    		//if(B.charAt(myRow-1)==A.charAt(myCol-1))	
	    		{		
	    			//seq1Align.insert(0, B.charAt(prev.y-1));
	    			//this one appears to be correct
	    			//seq1Align.insert(0, A.charAt(myCol-1));

	    		}
	    		else
	    		{
	    			//seq1Align.insert(0, '_');
	    			//myCol=325;
	    		}
	    		/*
	    		with prev.x and prev.y
	    		_TT_GAC
				GTTGACT
				
				with myrow-1 and mycol-1
				_GT_TAC
				GGTTGAC
				
				_GT_TAC
				GGTTGAC
				
				_GT_TAC
				GGTTGAC
	

	    		 */

	    	}
	    	int diag=scoringMatrix[myRow-1][myCol-1];
	    	int left=scoringMatrix[myRow][myCol-1];
	    	int up=scoringMatrix[myRow-1][myCol];
	    	System.out.println(left);
	    	System.out.println(diag);
	    	System.out.println(up);

	    	next=max(left,
					diag,
					up,
					0);
	    	if(left==diag && diag==up)
	    		next=diag;
	    	
	    	System.out.println("next "+next);
	    	
	    	if(next==0)
	    	{
	    		myRow--;
	    		myCol--;
	    		going=Direction.blank;
	    	}
	    	else if(next==diag)
	    	{
	    		myRow--;
	    		myCol--;
	    		going=Direction.diagonal;

	    		/*seq2Align.insert(0, B.charAt(myRow-1));
	    		seq1Align.insert(0, A.charAt(myCol-1));*/
	    	}
	    	else
	    	if(next==up)
	    	{
	    		myRow--;
	    		going=Direction.up;
	    		/*seq1Align.insert(0, '_');
	    		seq2Align.insert(0, B.charAt(myRow-1));*/
	    	}
	    	else
	    	if(next==left)
	    	{
	    		myCol--;
	    		going=Direction.left;

	    		/*seq2Align.insert(0, '_');
	    		seq1Align.insert(0, A.charAt(myCol-1));*/
	    	}
	    	
	    	System.out.println("going is "+going);
	    	myDirections.push(going);
	    	System.out.println(myRow);
	    	System.out.println(myCol);

	    	System.out.print("compare "+B.charAt(myRow));
    		System.out.println(" with "+A.charAt(myCol));

	    	switch(going)
    		{
    		case start:
    			seq1Align.insert(0, B.charAt(myCol));
  			    seq2Align.insert(0, A.charAt(myRow));
    			break;
    		case up:
    			seq1Align.insert(0, '_');
    			//myCol--;
    			seq2Align.insert(0, B.charAt(myRow));
    			break;
    		case left:
    			seq2Align.insert(0, '_');
    			seq1Align.insert(0, A.charAt(myCol));
    			break;
    		case blank:
    		case diagonal:
    			seq1Align.insert(0, A.charAt(myCol));
  			    seq2Align.insert(0, B.charAt(myRow));
    			break;
    		default:
    			System.out.println("bad");

    			break;
    		}
	    	System.out.println(seq1Align);
	    	System.out.println(seq2Align);
	    	//dir=nextDir;
	    	coming=going;
	    	current=next;
	    	
	    	System.out.println("END OF ITERATION\n");
	    	//if(next==0)
	    		//break;
	    	//skip=false;
	    }
	    
	    
	    /*
	    while(next>0)
		{
	    	System.out.println("we are at "+myRow +" "+myCol);
	    	
	    	

	    	System.out.println("the value is "+scoringMatrix[myRow][myCol]);
	    	System.out.println("current is "+current);
	    	myValues.push(current);
	    //	prevNum=max;
	    	
	    	//System.out.println("max was "+next);
			next= max(scoringMatrix[myRow-1][myCol],
					scoringMatrix[myRow][myCol-1],
					scoringMatrix[myRow-1][myCol-1],
					0);
			if(next==0)
				break;
			
	    	System.out.println("next is "+next);

	    	//System.out.println("max is "+next);

			pos = myPositions.peek();
			System.out.println("previous position "+myPositions.peek());		

			//myValues.push(next);
		//	System.out.println("next biggest is : "+max);
			
				
			//check which one of those was the biggest
			if(next==scoringMatrix[myRow-1][myCol])
			{
				System.out.println(next +" we came from up!");
			//	seq1Align.insert(0, '_');
			//    seq2Align.insert(0, B.charAt(myRow-2));
				myRow--;

				
			}
			else if(next==scoringMatrix[myRow][myCol-1])
			{
				System.out.println(next + "we came from left!");
				

			//	seq1Align.insert(0, A.charAt(myCol-2));
			  //  seq2Align.insert(0, '_');
				//prev= myMatrix.new Coord(myRow, myCol-1);

				myCol--;
			}
			else if(next==scoringMatrix[myRow-1][myCol-1])
			{
				
				System.out.println(next+" we came from diagonal!");
				
				
				//seq1Align.insert(0, A.charAt(myCol-2));
			  //  seq2Align.insert(0, B.charAt(myRow-2));
				
			
				//prev= myMatrix.new Coord(myRow-1, myCol-1);
				myRow--;
				myCol--;
			}
			prev= myMatrix.new Coord(myRow, myCol);

			//System.out.println(prev+" "+pos);
			if(pos.x-prev.x==1)
			{
				 seq2Align.insert(0, B.charAt(prev.x-1));

			}
			else
			{
				seq2Align.insert(0, '_');
			}
			if(pos.y-prev.y==1)
			{			
				seq1Align.insert(0, A.charAt(prev.y-1));

			}
			else
			{
				seq1Align.insert(0, '_');
			}
			i++;
		//	cell=myMatrix.new Cell(pos,max);
		//	myCells.push(cell);
			//System.out.println("cell : "+cell);
			myPositions.push(prev);
			current=next;
			System.out.println(i);
		    System.out.println("current position : "+myPositions.peek());
		    System.out.println(seq1Align);
		    System.out.println(seq2Align);




		}*/
		
	   
		System.out.println("A : "+A);
		System.out.println("B : "+B);

		System.out.println(myValues.toString());
		System.out.println(myPositions.toString());
		
	    System.out.println(seq1Align);
	    System.out.println(seq2Align);

		

	
	}
/*	public class Cell
	{
		Coord pos= new Coord();
		int value;
		public Cell()
		{
			value=-1;
			pos=new Coord();
		}
		
		public Cell(Coord a, int b)
		{
			pos=a;
			value=b;
		}
		public String toString()
		{
			return String.valueOf(value) +", "+ pos.toString();
		}
		
		public Coord getPos() {
			return pos;
		}
		public void setPos(Coord pos) {
			this.pos = pos;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		
	}
	*/
	public class Coord
	{
		int x;
		int y;
		
		public Coord(int a, int b)
		{
			x=a;
			y=b;
		}

		public Coord()
		{
			x=0;
			y=0;
		}
		public void setPos(int a, int b)
		{
			x=a;
			y=b;
		}
		public String toString()
		{
			String a="";
			a='('+String.valueOf(x)+", "+String.valueOf(y)+')';
			return a;
		}
	}
}/*
*//**
 * Author: Paul Reiners
 *//*
package com.ibm.compbio.seqalign;

import com.ibm.compbio.Cell;
import com.ibm.compbio.DynamicProgramming;

*//**
 * @author Paul Reiners
 * 
 *//*
public abstract class SequenceAlignment extends DynamicProgramming {

   protected int match;
   protected int mismatch;
   protected int space;
   protected String[] alignments;

   public SequenceAlignment(String sequence1, String sequence2) {
      this(sequence1, sequence2, 1, -1, -1);
   }

   public SequenceAlignment(String sequence1, String sequence2, int match,
         int mismatch, int gap) {
      super(sequence1, sequence2);

      this.match = match;
      this.mismatch = mismatch;
      this.space = gap;
   }

   protected Object getTraceback() {
      StringBuffer align1Buf = new StringBuffer();
      StringBuffer align2Buf = new StringBuffer();
      Cell currentCell = getTracebackStartingCell();
      while (traceBackIsNotDone(currentCell)) {
         if (currentCell.getRow() - currentCell.getPrevCell().getRow() == 1) {
            align2Buf.insert(0, sequence2.charAt(currentCell.getRow() - 1));
         } else {
            align2Buf.insert(0, '-');
         }
         if (currentCell.getCol() - currentCell.getPrevCell().getCol() == 1) {
            align1Buf.insert(0, sequence1.charAt(currentCell.getCol() - 1));
         } else {
            align1Buf.insert(0, '-');
         }
         currentCell = currentCell.getPrevCell();
      }

      String[] alignments = new String[] { align1Buf.toString(),
            align2Buf.toString() };

      return alignments;
   }

   protected abstract boolean traceBackIsNotDone(Cell currentCell);

   public int getAlignmentScore() {
      if (alignments == null) {
         getAlignment();
      }

      int score = 0;
      for (int i = 0; i < alignments[0].length(); i++) {
         char c1 = alignments[0].charAt(i);
         char c2 = alignments[1].charAt(i);
         if (c1 == '-' || c2 == '-') {
            score += space;
         } else if (c1 == c2) {
            score += match;
         } else {
            score += mismatch;
         }
      }

      return score;
   }

   public String[] getAlignment() {
      ensureTableIsFilledIn();
      alignments = (String[]) getTraceback();
      return alignments;
   }

   protected abstract Cell getTracebackStartingCell();
}
*/

