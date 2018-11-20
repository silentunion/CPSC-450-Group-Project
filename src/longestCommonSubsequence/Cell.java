package longestCommonSubsequence;

public class Cell {
	//variables
	private Cell prevCell;
	private int score;
	private int row;
	private int col;
	
	//constructor
	public Cell(int row, int col)
	{
		this.row = row;
		this.col = col;
	}
	
	//getters and setters
	public void setScore(int score)
	{
		this.score = score;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public void setPrevCell(Cell prevCell)
	{
		this.prevCell = prevCell;
	}
	
	public Cell getPrevCell()
	{
		return prevCell;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getCol()
	{
		return col;
	}
	
	//toString method
	@Override
	public String toString()
	{
		return "Cell(" + row + ", " + col + "): score=" + score + ", prevCell="
	            + prevCell + "]";
	}
}