package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class Main extends Application {
	
	Stage window;
	Scene mainScene, secondScene;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			window = primaryStage;
			primaryStage.setTitle("Sequence Aligner");
			
			Label labWelcome = new Label("Welcome");
			Label labGoBack = new Label("Go Back");
			
			Button butForward = new Button("Forward");
			butForward.setOnAction(e -> window.setScene(secondScene));
			
			Button butAlert = new Button("Alert");
			butAlert.setOnAction(e -> AlertBox.display("WARNING", "This is a warning"));
			
			VBox layout1 = new VBox(20);
			layout1.getChildren().addAll(labWelcome, butForward, butAlert);
			mainScene = new Scene(layout1, 200, 200);

			Button butBackward = new Button("Backward");
			butBackward.setOnAction(e -> window.setScene(mainScene));
		
			StackPane layout2 = new StackPane();
			layout2.getChildren().addAll(labGoBack, butBackward);
			secondScene = new Scene(layout2, 600, 480);
			
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
