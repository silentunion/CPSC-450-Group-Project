package sequenceAlignment.smithWaterman;
import java.util.ArrayList;
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
	
	private static StringBuffer seq1Align;
	private static StringBuffer seq2Align;
	
	private static Stack<Coord> myPositions;
	private static ArrayList<String> partialAligns;
	private static ArrayList<String> partialAligns2;

	
	public static ArrayList<String> getPartialAligns() {
		return partialAligns;
	}



	public static ArrayList<String> getPartialAligns2() {
		return partialAligns2;
	}




	public ArrayList<Coord> getTraceback()
	{
		ArrayList<Coord> list = new ArrayList<Coord>(myPositions);
		return list;
	}
	
	
	public Integer[][] getMatrix()
	{
		return scoringMatrix;
	}
	public StringBuffer getAlignment()
	{
		return seq1Align;
	}
	public StringBuffer getAlignment2()
	{
		return seq2Align;
	}

	//sequences and scoring matrix values are hardcoded for now
	public Smith_waterman()
	{
		this("AAATGCAAAGCTCG","AATGCA",1,-1,-2);
		//B="AATGCA";
		
	
	}
	public Smith_waterman(String a, String b)
	{
		this(a,b,1,-2,-1);
	}
	
	public Smith_waterman(String A, String B, int match, int miss, int gap)
	{
		//wikipedia exampls
		//A="TGGTTACGG";
		//B="CGGTTGACTA";
		this.A=A;
		this.B=B;
		this.match=match;
		this.miss=miss;
		this.gap=gap;
		
		seq1Align = new StringBuffer();
	    seq2Align = new StringBuffer();
	    
	    myPositions = new Stack<Coord>();
	    partialAligns= new ArrayList<String>();
	    partialAligns2= new ArrayList<String>();


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
		
		runAlg(this);
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
	
	public static void main(String[] args)
	{
		Smith_waterman test = new Smith_waterman("TGTTACGG","GGTTGACTA ",3,-3,-2);
		//runAlg(test);
		/* and  
		;
		 */
	}
	
	public static void runAlg(Smith_waterman myMatrix)
	{
		


		//myMatrix.print();

		//System.out.println();

		myMatrix.fillMatrix();

		//System.out.println("A : "+A);
		//System.out.println("B : "+B);
		//System.out.println();

		//myMatrix.print();
		
		Stack<Integer> myValues = new Stack<Integer>();
		
		Stack<Direction> myDirections = new Stack<Direction>();
		//Stack<Cell> myCells = new Stack<Cell>();
		//Cell cell= myMatrix.new Cell();
		Coord prev= myMatrix.new Coord();
		//Coord prev2= myMatrix.new Coord();
		
		

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
		//System.out.println(allMax);
	
	
	    //System.out.println(seq1Align);
	    //System.out.println(seq2Align);		
	    Coord pos;
	    Coord myStringpos;
	    int next=-1;
	    int current=max;
	    Direction going=Direction.blank;
	    Direction coming=Direction.start;
		myDirections.push(coming);

	    while(current>0)
	    {
	    	//System.out.println("current "+current);
	    	myValues.push(current);
	    	if(!myPositions.isEmpty())
	    	{
		    	//System.out.println("previous pos : "+myPositions.peek());
		    	prev=myPositions.peek();
	    	}
		
	    	pos = myMatrix.new Coord(myRow,myCol);
	    	myStringpos= myMatrix.new Coord(myRow-1,myCol-1);
	    	//System.out.println("at position "+pos);
	    	//System.out.println("chars at "+myStringpos.x+" and "+myStringpos.y);
	    	//System.out.println(A.charAt(myStringpos.y)+" "+B.charAt(myStringpos.x));
	    	myPositions.push(pos);
	    
    		//System.out.println("coming is "+coming);
    		
	    	int diag=scoringMatrix[myRow-1][myCol-1];
	    	int left=scoringMatrix[myRow][myCol-1];
	    	int up=scoringMatrix[myRow-1][myCol];
	    	//System.out.println(left);
	    	//System.out.println(diag);
	    	//System.out.println(up);
	    	
	    	
	    	next=max(left,
					diag,
					up,
					0);
	    	if(left==diag && diag==up)
	    		next=diag;
	    	
	    	//System.out.println("next "+next);
	    	
	    	
	    	
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
	    	
	    	//System.out.println("going is "+going);
	    	myDirections.push(going);
	    	//System.out.println(myRow+", "+myCol);


	    	if(diag==0)
	    	{
	    		//System.out.print("LOOK HERE "+B.charAt(myStringpos.x));
	    		//System.out.println(" and "+A.charAt(myStringpos.y));	 
	    		if(A.charAt(myStringpos.y)==B.charAt(myStringpos.x))
	    		{
	    			seq1Align.insert(0, B.charAt(myStringpos.x));
	    			//System.out.println(B.charAt(myCol));
	  			    seq2Align.insert(0, A.charAt(myStringpos.y));

	  		    	partialAligns.add(seq1Align.toString());
	  		    	partialAligns2.add(seq2Align.toString());
	    		}
	    		break;
	    	}
    		switch(going)
    		{
    		case start:
    			break;
    		case up:

    			seq2Align.insert(0, '_');
    			seq1Align.insert(0, B.charAt(myStringpos.x));
    			break;
    		case left:
    			seq1Align.insert(0, '_');
    			seq2Align.insert(0, A.charAt(myStringpos.y));
    			break;
    		case blank:
    		case diagonal:
    			seq1Align.insert(0, B.charAt(myStringpos.x));
    			//System.out.println(B.charAt(myCol));
  			    seq2Align.insert(0, A.charAt(myStringpos.y));
    			break;
    		default:
    			System.out.println("bad");

    			break;
    		}
    		
    		
	    	//System.out.println(seq1Align);
	    	//System.out.println(seq2Align);
	    	partialAligns.add(seq1Align.toString());
	    	partialAligns2.add(seq2Align.toString());
	    	

	    	coming=going;
	    	current=next;
	    	

	    	
	    	//System.out.println("END OF ITERATION\n");
	    

	    }
	    
	    
	    
		
	   /*
		System.out.println("A : "+A);
		System.out.println("B : "+B);

		System.out.println(myValues.toString());
		System.out.println(myPositions.toString());
		
	    System.out.println(seq1Align);
	    System.out.println(seq2Align);
	    
	    System.out.println(partialAligns);
	    System.out.println(partialAligns2);

	    
	    
	    for(int i=0;i<partialAligns.size();i++)
	    {
	    	System.out.print(partialAligns.get(i)+" ");
	    	System.out.println(partialAligns2.get(i));

	    }
	    */
		

	
	}

	public class Coord
	{
		int x;
		int y;
		
		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}
		
		
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

