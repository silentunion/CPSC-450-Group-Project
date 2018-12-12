package application;

import java.util.ArrayList;
import java.util.Stack;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import sequenceAlignment.Cell;
import sequenceAlignment.longestCommonSubsequence.LCS;
import sequenceAlignment.needlemanWunsch.SequenceAlignmentWunsch;
import sequenceAlignment.smithWaterman.Smith_waterman;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;

public class Main extends Application {

	//Enum for our algorithm types
	public enum algorithmType {
		LCS, // Longest common subsequence
		NMW, // Needleman Wunsch
		SWM // Smith Waterman
	};
	//The currently selected algorithm
	private algorithmType selectedAlgorithm = algorithmType.LCS;
	
	Stage window;
	Scene mainScene, secondScene;

	BorderPane mainLayout = new BorderPane();
	ScrollPane scroll = new ScrollPane();
	HBox hbox = new HBox();
	HBox hboxSequence1 = new HBox();
	HBox hboxSequence2 = new HBox();
	VBox vboxSequences = new VBox();
	HBox hboxBottomBox = new HBox();
	GridPane grid = new GridPane();
	StackPane stack = new StackPane();

	//These are our interactable GUI objects
	TextField txtInput = new TextField();
	TextField txtInput2 = new TextField();
	Button btnCreate = new Button("Create Grid");
	Button btnClear = new Button("Clear");
	Button btnTest = new Button("Run Algorithm");
	Button btnNext = new Button("Next Traceback");
	CheckBox checkLCS = new CheckBox("LCS");
	CheckBox checkNMW = new CheckBox("Needleman Wunsch");
	CheckBox checkSWM = new CheckBox("Smith Waterman");
	// create the Grid class, this is to get it out of main
	Grid mainGrid = new Grid(grid);

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			window = primaryStage;
			primaryStage.setTitle("Sequence Aligner");

			// Grid starts out invisible
			grid.setVisible(false);
			
			// Only one check box can be active at a time. Start program with LCS active.
			checkLCS.setSelected(true);
			checkNMW.setSelected(false);
			checkSWM.setSelected(false);

			// First we set up the vbox & hbox's which contain the top layout with the text fields and buttons
			
			//This first HBox contains the first sequence text input box
			Label sequenceLabel = new Label("Sequence 1:");			
			hboxSequence1.setSpacing(10);
			hboxSequence1.setAlignment(Pos.CENTER_LEFT);
			hboxSequence1.getChildren().addAll(sequenceLabel, txtInput);
			
			//This second HBox contains the second sequence text input box
			sequenceLabel = new Label("Sequence 2:");
			hboxSequence2.setSpacing(10);
			hboxSequence2.setAlignment(Pos.CENTER_LEFT);
			hboxSequence2.getChildren().addAll(sequenceLabel, txtInput2);
			
			//This VBox contains positions the text inputs on top of one another.
			vboxSequences.setSpacing(10);
			vboxSequences.setMaxSize(300, 300);
			vboxSequences.setPadding(new Insets(15, 12, 10, 15));
			vboxSequences.getChildren().addAll(hboxSequence1, hboxSequence2);
			
			//Finally this last HBox organized the text inputs with the buttons and check boxes.
			hbox.getChildren().add(vboxSequences);
			hbox.setAlignment(Pos.CENTER);
			hbox.getChildren().addAll(btnCreate, btnClear, btnTest, checkLCS, checkNMW, checkSWM);
			hbox.setBorder(Border.EMPTY);
			hbox.setPadding(new Insets(5, 5, 5, 5));
			hbox.setSpacing(10);

			//This HBox is for the bottom
			hboxBottomBox.setSpacing(10);
			hboxBottomBox.getChildren().add(btnNext);
			
			//TEXT INPUT TEXT FORMATTER ALLOWS ONLY A C G T and U to be entered
			//Text inputs cannot share the same formatter so we need two.
			TextFormatter<String> textFormatterSeq1 = new TextFormatter<>( change -> {
			    if (!change.isContentChange()) {
			        return change;
			    }

			    Character text;
			    //Grab the character that was entered into the text input, if size = 0 then we are at the first entry.
			    if (change.getControlNewText().length() > 0)
			    	text = change.getControlNewText().charAt(change.getControlNewText().length() - 1);
			    else
			    	return change;
			    //If it is the correct letter, allow the change. Otherwise throw it away.
			    if (text.equals('a') || text.equals('c') || text.equals('g') || text.equals('t') ||  text.equals('u')) {
			        return change;
			    }
			    return null;
			});
			
			TextFormatter<String> textFormatterSeq2 = new TextFormatter<>( change -> {
			    if (!change.isContentChange()) {
			        return change;
			    }

			    Character text;
			    //Grab the character that was entered into the text input, if size = 0 then we are at the first entry.
			    if (change.getControlNewText().length() > 0)
			    	text = change.getControlNewText().charAt(change.getControlNewText().length() - 1);
			    else
			    	return change;
			    //If it is the correct letter, allow the change. Otherwise throw it away.
			    if (text.equals('a') || text.equals('c') || text.equals('g') || text.equals('t') ||  text.equals('u')) {
			        return change;
			    }
			    return null;
			});
			txtInput.setTextFormatter(textFormatterSeq1);
			txtInput2.setTextFormatter(textFormatterSeq2);
			
			
			// Here we set up of side panel for information, to be expanded on later, uses a stackpane
			// Label info = new Label("Info goes Here");
			// StackPane.setAlignment(info, Pos.CENTER);
			// stack.getChildren().add(info);
			stack.setPadding(new Insets(15, 12, 10, 15));
			stack.setBorder(Border.EMPTY);
			stack.setMinWidth(300);
			stack.setStyle("-fx-border-style:solid; -fx-border-color: black;");

			// add the layouts to the main layout
			mainLayout.setTop(hbox);
			mainLayout.setRight(stack);
			mainLayout.setCenter(scroll);
			mainLayout.setBottom(hboxBottomBox);
			mainScene = new Scene(mainLayout, 900, 400);

			// create the window and initialize
			window.setScene(mainScene);
			window.show();
			
			
			//CHECK BOX EVENT HANDLERS GO BELOW HERE
			

			checkLCS.setOnAction(e -> {
				// Only one check box can be active at a time.
				checkLCS.setSelected(true);
				checkNMW.setSelected(false);
				checkSWM.setSelected(false);

				selectedAlgorithm = algorithmType.LCS;
			});

			checkNMW.setOnAction(e -> {
				// Only one check box can be active at a time.
				checkLCS.setSelected(false);
				checkNMW.setSelected(true);
				checkSWM.setSelected(false);

				selectedAlgorithm = algorithmType.NMW;
			});

			checkSWM.setOnAction(e -> {
				// Only one check box can be active at a time.
				checkLCS.setSelected(false);
				checkNMW.setSelected(false);
				checkSWM.setSelected(true);

				selectedAlgorithm = algorithmType.SWM;
			});
			
			
			// BUTTON EVENT HANDLERS GO BELOW HERE

			
			// Create button creates the grid
			btnCreate.setOnAction(e -> {
				// Make sure the user has inputed some sequences
				if (!txtInput.getText().isEmpty() && !txtInput2.getText().isEmpty()) {
					grid = new GridPane();
					mainGrid = new Grid(grid);
					setupGrid();
					mainGrid.createGrid(txtInput, txtInput2);
				}
			});

			// Clear button resets the grid view.
			btnClear.setOnAction(e -> {

				// Remove the grid elements, the stack elements and set the grid visibility to
				// false so the test button doesn't work.
				grid.getChildren().clear();
				grid.setStyle("-fx-border-style:none ");
				stack.getChildren().clear();
				mainLayout.getChildren().remove(grid);
				grid.setVisible(false);

			});

			// Test button runs the selected algorithm.
			btnTest.setOnAction(e -> {

				// If the create button hasn't been pressed, don't run the algorithm
				if (!grid.isVisible())
					return;
				//If the sequences have been changed we need to recreate the grid
				String[] inputedSequences = mainGrid.getSequences();
				if (txtInput.getText() != inputedSequences[0]|| txtInput2.getText() != inputedSequences[1] )
				{
					btnClear.fire();
					btnCreate.fire();
				}
				
				// Reset the stackpanes info so we dont write on top of existing data
				stack.getChildren().clear();

				Label alignmentLabel;
				// Run the selected algorithm
				switch (selectedAlgorithm) {

				// IF LONGEST COMMON SUBSEQUENCE IS SELECTED:
				case LCS:
					// Run an instance of the algorithm
					LCS lcs = new LCS(txtInput.getText(), txtInput2.getText());
					Cell[][] lcsTable = lcs.getScoreTable();
					ArrayList<Cell> LCSCellList = lcs.getTracebackPath();
					
					// For the LCS algorithm we skip row & column zero because its always the same
					for (int i = 1; i < lcsTable.length; i++)
						for (int j = 1; j < lcsTable[i].length; j++)
							mainGrid.addItem(lcsTable[i][j].getScore(), j, i, LCSCellList.contains(lcsTable[i][j]));

					// Create the info for the information tab:
					alignmentLabel = new Label("Longest common subsequence:");
					Label commonSubsequence = new Label(lcs.getLCSTraceback());

					StackPane.setAlignment(alignmentLabel, Pos.TOP_CENTER);
					stack.getChildren().add(alignmentLabel);
					stack.getChildren().add(commonSubsequence);

					break;

				// IF NEEDLEMAN WUNSCH ALGORITHM IS SELECTED:
				case NMW:
					// Run an instance of the algorithm
					SequenceAlignmentWunsch NMW = new SequenceAlignmentWunsch(txtInput.getText(), txtInput2.getText());
					Cell[][] cellTable = NMW.getScoreTable();
					ArrayList<Cell> NMWCellList = NMW.getTracebackPath();
					// For the needleman wunsch algorithm we skip row & column zero because its
					// always the same
					for (int i = 1; i < cellTable.length; i++)
						for (int j = 1; j < cellTable[i].length; j++)
						{
							mainGrid.addItem(cellTable[i][j].getScore(), j, i, NMWCellList.contains(cellTable[i][j]));
						}
						
					// Create the info for the information tab:
					alignmentLabel = new Label("Aligned Sequences:");
					Label alignment1 = new Label(NMW.getAlignment()[0] + "\n" + NMW.getAlignment()[1]);

					StackPane.setAlignment(alignmentLabel, Pos.TOP_CENTER);
					StackPane.setAlignment(alignment1, Pos.CENTER);
					stack.getChildren().add(alignmentLabel);
					stack.getChildren().add(alignment1);

					break;

				// IF SMITH WATERMAN ALGORITHM IS SELECTED:
				case SWM:
					Smith_waterman smith = new Smith_waterman(txtInput.getText(), txtInput2.getText());
					Integer[][] matrix = smith.getMatrix();
					
					for (int i = 1; i < matrix.length+1; i++)
						for (int j = 1; j < matrix[i-1].length+1; j++)
							mainGrid.addItem(matrix[i-1][j-1], j, i,false);
					
					alignmentLabel = new Label("Aligned Sequences:");
					Label alignment2 = new Label(smith.getAlignment()+ "\n" +smith.getAlignment2());
					
					//Stack<Smith_waterman.> myStack = smith.getTraceback();
				//	Stack<Smith_waterman.Coord> myStack = smith.getTraceback();
					
					//mainGrid.addItem(2, 2, 2, true);
					
				/*	for(Smith_waterman.Coord item: myStack)
					{
						System.out.println(item.getX());
						System.out.println(item.getY());
						//mainGrid.addItem(matrix[item.getY()][item.getX()], item.getX(), item.getY(), true);
					}*/
					
					StackPane.setAlignment(alignmentLabel, Pos.TOP_CENTER);
					StackPane.setAlignment(alignment2, Pos.CENTER);
					stack.getChildren().add(alignmentLabel);
					stack.getChildren().add(alignment2);

					break;

				default:
					break;
				}
			});

		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setupGrid() {
		// set up the grid layout: the main grid to be used
		grid.setStyle("-fx-border-style:solid; -fx-border-color:black;");
		grid.gridLinesVisibleProperty().set(true); // DEBUGGING ONLY
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(0);
		grid.setHgap(0);
		grid.setStyle("-fx-border-style:solid; -fx-border-color:black; -fx-background-color: #000000;");

		scroll.setContent(grid);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
