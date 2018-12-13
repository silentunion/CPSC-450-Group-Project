package application;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

public class Grid
{
	GridPane grid;
	int numCols = 0;
	int numRows = 0;
	String input;
	String input2;
	
	ColumnConstraints cols = new ColumnConstraints(40);
	RowConstraints rows = new RowConstraints(40);
	
	Grid(GridPane grid)
	{
		this.grid = grid;
	}
	
	/*
	Creates the labels of the sequences provided. Input 1 = top row. Input 2 = left column.
	Most of this is explained in the fillGrid Method.
	 */
	public void createGrid(TextField txtInput, TextField txtInput2)
	{		
		input = " "+txtInput.getText().toUpperCase();
		input2 = " "+txtInput2.getText().toUpperCase();
						
		for(int i = 0; i < input.length(); i++)
		{
			Label label = new Label(Character.toString(input.charAt(i)));
			label.setFont(new Font(24));
			Pane pane = new Pane();
			pane.getChildren().add(label);
			
			label.layoutXProperty().bind(pane.widthProperty().subtract(label.widthProperty()).divide(2));
			label.layoutYProperty().bind(pane.heightProperty().subtract(label.heightProperty()).divide(2));
			
			pane.setStyle("-fx-border-style:solid; -fx-border-color:white; -fx-background-color: #AAAAAA;");
							
			GridPane.setConstraints(pane, i + 1, 0);
			GridPane.setHalignment(pane, HPos.CENTER);
			GridPane.setValignment(pane, VPos.CENTER);
			
			grid.getChildren().add(pane);					
			grid.getColumnConstraints().add(cols);
			
			numCols++;
		}
		
		grid.getColumnConstraints().add(cols);
		numCols++;
		
		for(int j = 0; j < input2.length(); j++)
		{
			Label label2 = new Label(Character.toString(input2.charAt(j)));
			label2.setFont(new Font(24));
			Pane pane = new Pane();
			pane.getChildren().add(label2);
			
			label2.layoutXProperty().bind(pane.widthProperty().subtract(label2.widthProperty()).divide(2));
			label2.layoutYProperty().bind(pane.heightProperty().subtract(label2.heightProperty()).divide(2));
			
			pane.setStyle("-fx-border-style:solid; -fx-border-color:white; -fx-background-color: #AAAAAA;");
			
			GridPane.setConstraints(pane, 0, j + 1);
			GridPane.setHalignment(pane, HPos.CENTER);
			GridPane.setValignment(pane, VPos.CENTER);
			
			grid.getChildren().add(pane);
			grid.getRowConstraints().add(rows);
			numRows++;
		}
		
		grid.getRowConstraints().add(rows);
		numRows++;
		
		for(int i = 1; i < numCols; i++)
		{
			for(int j = 1; j < numRows; j++)
			{
				Pane pane = new Pane();
				pane.setStyle("-fx-border-style:solid; -fx-border-color:white; -fx-background-color: #CCCCCC;");
				GridPane.setConstraints(pane, i, j);
				GridPane.setHalignment(pane, HPos.CENTER);
				GridPane.setValignment(pane, VPos.CENTER);
				
				grid.getChildren().add(pane);
			}
		}
		
		grid.setVisible(true);
	}
	
	/*
	This is a dummy algorithm and "proof of concept" that number can be added to the grid.
	It is based on a coordinate system commented below
	*/
	public void fillGrid()
	{
		int max = 5;
		//int[] stuff;
		
		for(int i = 0; i < max; i++)
		{
			//stuff = new int[i];
			
			//label goes inside the pane which goes inside the grid coord
			Label label = new Label(Integer.toString(i));
			label.setFont(new Font(24));
			Pane pane = new Pane();
			pane.getChildren().add(label);
			
			//centers the label
			label.layoutXProperty().bind(pane.widthProperty().subtract(label.widthProperty()).divide(2));
			label.layoutYProperty().bind(pane.heightProperty().subtract(label.heightProperty()).divide(2));
			
			//colours are fun!
			pane.setStyle("-fx-border-style:solid; -fx-border-color:white; -fx-background-color: #CCCCCC;");
			
			//coordinates go here
			GridPane.setConstraints(pane, i + 1, 1);
			
			//these 2 pieces may not be necessary but it centers the pane inside just in case
			GridPane.setHalignment(pane, HPos.CENTER);
			GridPane.setValignment(pane, VPos.CENTER);
			
			grid.getChildren().add(pane);
		}
	}
	
	/*
	 Method to add a single item, just in case it is preferred.
	 Takes in an int and coords and adds to the grid at the desired coords
	 */
	public void addItem(int item, int x, int y, boolean lightMe)
	{
		//label goes inside the pane which goes inside the grid coord
		Label label = new Label(Integer.toString(item));
		label.setFont(new Font(24));
		Pane pane = new Pane();
		pane.getChildren().add(label);
		
		//centers the label
		label.layoutXProperty().bind(pane.widthProperty().subtract(label.widthProperty()).divide(2));
		label.layoutYProperty().bind(pane.heightProperty().subtract(label.heightProperty()).divide(2));
		if(lightMe)
		pane.setStyle("-fx-background-color:rgba(255,255,153,1);");
		else
		pane.setStyle("-fx-background-color:rgba(0,0,255,0);");

		//colours are fun!
		//pane.setStyle("-fx-border-style:solid; -fx-border-color:white; -fx-background-color: #CCCCCC;");
		
		//coordinates go here
		GridPane.setConstraints(pane, x, y);
		
		//these 2 pieces may not be necessary but it centers the pane inside just in case
		GridPane.setHalignment(pane, HPos.CENTER);
		GridPane.setValignment(pane, VPos.CENTER);
		
		grid.getChildren().add(pane);
	}
	
	
	
	public void setHighlight(boolean isHighlighted, int x, int y)
	{
		Node node = grid.getChildren().get((numRows * numCols) + x + (y * (numCols - 1)));

		if(isHighlighted)
		{
			node.setStyle("-fx-background-color:rgba(255,255,153,1);");
		}
		else
		{
			node.setStyle("-fx-background-color:rgba(0,0,255,0);");
		}
	}
	
	public boolean isHighlighted(int x, int y)
	{
		Node node = grid.getChildren().get((numRows * numCols) + x + (y * (numCols - 1)));
		
		if (node.getStyle() == "-fx-background-color:rgba(255,255,153,1);")
			return true;
		else
			return false;
	}
	
	//Returns the sequences that the grid was created with.
	public String[] getSequences()
	{
		String[] sequences = { input, input2 };
		return sequences;
	}
}
