import java.util.Arrays;
import java.util.Stack;


//never go negative
public class Smith_waterman
{
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
		//only works if strings same length right now
		A="ATACATCT";
		B="ATCGCTCGG";

		match=1;
		miss=-1;
		gap=-1;

		scoringMatrix= new Integer[B.length()+1][A.length()+1];
		//fill it with 0s in 1st row and column and -1s everywhere else
		for(int row=0;row<scoringMatrix.length;row++)
		{
			for(int col=0;col<scoringMatrix[row].length;col++)
			{
				if(row==0 || col==0)
					scoringMatrix[row][col]=0;
				else
					scoringMatrix[row][col]=-1;
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

				System.out.print(scoringMatrix[row][col]);
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
		Coord pos= myMatrix.new Coord();
		//find max element in the matrix
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
					//System.out.println(row+" "+col);
				}

			}
		}
		System.out.println("max value is : "+max);
		//System.out.println(myRow+" "+myCol);
		myValues.push(max);
		
		pos.setPos(myRow, myCol);
		System.out.println("position is "+pos);		
		myPositions.push(pos);
		//System.out.println(myPositions.toString());
		System.out.println(A.charAt(myRow-1));
		System.out.println(B.charAt(myRow-1));
		System.out.println(A.charAt(myCol-1));
		System.out.println(B.charAt(myCol-1));

		/*System.out.println(scoringMatrix[myRow][myCol]);
		System.out.println(scoringMatrix[myRow-1][myCol]);
		System.out.println(scoringMatrix[myRow-1][myCol-1]);
		System.out.println(scoringMatrix[myRow][myCol-1]);*/
		
		//now find max( the three adjacent squares to max and call that one the new max)
		//repeat until reach 0
		String alignment ="";
		
		//alignment=
		
		//need to figure out what to add to alignment to get desired result
		StringBuffer seq1Align = new StringBuffer();
	    StringBuffer seq2Align = new StringBuffer();
		boolean left,up = false;
		int next=0;
		do
		{
			next= max(scoringMatrix[myRow-1][myCol],
					scoringMatrix[myRow][myCol-1],
					scoringMatrix[myRow-1][myCol-1],
					0);
			myValues.push(next);
			System.out.println("next biggest is : "+next);
			
			
		/*	 //If cell points up then grab the next character otherwise add a gap
	         if (currentCell.getRow() - currentCell.getPrevCell().getRow() == 1) {
	            seq2Align.insert(0, sequence2.charAt(currentCell.getRow() - 1));
	         } else {
	            seq2Align.insert(0, '-');
	         }
			*/
			//check which one of those was the biggest
			if(next==scoringMatrix[myRow-1][myCol])
			{
				System.out.println("we went up!");
				//alignment=(B.charAt(myRow-1))+alignment;
				System.out.println(A.charAt(myRow-1));
				System.out.println(B.charAt(myRow-1));
				System.out.println(A.charAt(myCol-1));
				System.out.println(B.charAt(myCol-1));

				seq2Align.insert(0,A.charAt(myRow-1));
				pos= myMatrix.new Coord(myRow-1, myCol);
				myRow--;
			}
		/*	  //If cell points left than grab the next character otherwise add a gap
	         if (currentCell.getCol() - currentCell.getPrevCell().getCol() == 1) {
	            seq1Align.insert(0, sequence1.charAt(currentCell.getCol() - 1));
	         } else {
	            seq1Align.insert(0, '-');
	         }
			*/
			else if(next==scoringMatrix[myRow][myCol-1])
			{
				System.out.println("we went left!");
				seq1Align.insert(0, B.charAt(myCol-1));
				System.out.println(A.charAt(myRow-1));
				System.out.println(B.charAt(myRow-1));
				System.out.println(A.charAt(myCol-1));
				System.out.println(B.charAt(myCol-1));

				//alignment=(A.charAt(myCol-1))+alignment;
				pos= myMatrix.new Coord(myRow, myCol-1);
				myCol--;
			}
			else if(next==scoringMatrix[myRow-1][myCol-1])
			{
				System.out.println("we went diagonal!");
				alignment="_"+alignment;
				System.out.println(A.charAt(myRow-1));
				System.out.println(B.charAt(myRow-1));
				System.out.println(A.charAt(myCol-1));
				System.out.println(B.charAt(myCol-1));
				pos= myMatrix.new Coord(myRow-1, myCol-1);
				myRow--;
				myCol--;
			}

			myPositions.push(pos);





		}while(next>1);
		alignment=seq1Align.toString();
		alignment+=seq2Align.toString();
		String[] alignments = new String[] { seq1Align.toString(),
	            seq2Align.toString() };

		System.out.println(alignments.toString().toString().toString());
		System.out.println(alignment);
		System.out.println(myValues.toString());
		System.out.println(myPositions.toString());
		
		
		System.out.println(myPositions.pop().x);
		System.out.println(myPositions.pop().toString());

	
	}
	
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
}
