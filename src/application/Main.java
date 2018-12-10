package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import application.NeedlemanWunsch.Cell;
import application.NeedlemanWunsch.SequenceAlignmentWunsch;
import javafx.*;


public class Main extends Application {
	
	Stage window;
	Scene mainScene, secondScene;
	
	BorderPane mainLayout = new BorderPane();
	ScrollPane scroll = new ScrollPane();
	HBox hbox = new HBox();
	GridPane grid = new GridPane();
	StackPane stack = new StackPane();			
			
	TextField txtInput = new TextField();
	TextField txtInput2 = new TextField();
	Button btnCreate = new Button("Create");
	Button btnClear = new Button("Clear");
	Button btnTest = new Button("Test");

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			window = primaryStage;
			primaryStage.setTitle("Sequence Aligner");
			
			//set up the hbox: the top layout with the text fields and buttons			
			hbox.getChildren().addAll(txtInput, txtInput2, btnCreate, btnClear, btnTest);
			hbox.setBorder(Border.EMPTY);
			hbox.setPadding(new Insets(15, 12, 10, 15));
			hbox.setSpacing(10);
			
			//set up the grid layout: the main grid to be used
			grid.setStyle("-fx-border-style:solid; -fx-border-color:black;");
			grid.gridLinesVisibleProperty().set(true); //DEBUGGING ONLY
			grid.setPadding(new Insets(10, 10, 10, 10));
			grid.setAlignment(Pos.CENTER);
			grid.setVgap(0);
			grid.setHgap(0);
			grid.setStyle("-fx-border-style:solid; -fx-border-color:black; -fx-background-color: #000000;");
						
			//grid starts out invisible
			grid.setVisible(false);
			
			//create the Grid class, this is to get it out of main
			Grid mainGrid = new Grid(grid);
			
			//scroll pane holds the grid pane
			scroll.setContent(grid);			
			
			//BUTTONS GO HERE
			//Create button creates the grid
			btnCreate.setOnAction(e -> { mainGrid.createGrid(txtInput, txtInput2); });
			
			//Note: Clear doesn't work 100%
			btnClear.setOnAction(e -> {
				
				grid.getChildren().clear();
				stack.getChildren().clear();
				mainLayout.getChildren().remove(grid);
			
			});
			
			//Test button tests with a dummy algorithm
			btnTest.setOnAction(e -> { 
				//mainGrid.fillGrid(); 
				//IF NEEDLEMAN WUNSCH ALGORITHM IS SELECTED:
				SequenceAlignmentWunsch NMW = new SequenceAlignmentWunsch(txtInput.getText(), txtInput2.getText());
				Cell[][] scoreTable = NMW.getScoreTable();
				//For the needleman wunsch algorithm we skip row & column zero because its always the same
				for (int i = 1; i < scoreTable.length; i++)
					for (int j = 1; j < scoreTable[i].length; j++)
							mainGrid.addItem(scoreTable[i][j].getScore(), j, i);
				
				Label alignmentLabel = new Label("Aligned Sequences:");
				Label alignment1 = new Label(NMW.getAlignment()[0]);
				Label alignment2 = new Label(NMW.getAlignment()[1]);
				
				StackPane.setAlignment(alignmentLabel, Pos.TOP_CENTER);
				StackPane.setAlignment(alignment1, Pos.CENTER);
				StackPane.setAlignment(alignment2, Pos.BOTTOM_CENTER);
				
				stack.getChildren().add(alignmentLabel);
				stack.getChildren().add(alignment1);
				stack.getChildren().add(alignment2);
				
				
			});
			
				
			//set up of side panel for information, to be expanded on later
			//uses a stackpane
			//Label info = new Label("Info goes Here");
			//StackPane.setAlignment(info, Pos.CENTER);
			//stack.getChildren().add(info);
			stack.setPadding(new Insets(15, 12, 10, 15));
			stack.setBorder(Border.EMPTY);
			stack.setMinWidth(300);
			
			stack.setStyle("-fx-border-style:solid; -fx-border-color: black;");
		
			//add the layouts to the main layout
			mainLayout.setTop(hbox);
			mainLayout.setRight(stack);
			mainLayout.setCenter(scroll);
			mainScene = new Scene(mainLayout, 800, 400);
			
			//create the window and initialize
			window.setScene(mainScene);			
			window.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
