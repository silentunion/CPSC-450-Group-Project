package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox
{
	public static void display(String title, String message) {
		Stage winAlert = new Stage();
		
		winAlert.initModality(Modality.APPLICATION_MODAL);
		winAlert.setTitle(title);
		winAlert.setMinWidth(300);
		
		Label lblAlert = new Label();
		lblAlert.setText(message);
		
		Button btnClose = new Button("Close");
		btnClose.setOnAction(e -> winAlert.close());
		
		VBox layoutAlert = new VBox(0);
		layoutAlert.getChildren().addAll(lblAlert, btnClose);
		VBox.setMargin(lblAlert, new Insets(10, 10, 10, 10));
		VBox.setMargin(btnClose, new Insets(10, 10, 10, 10));
		layoutAlert.setAlignment(Pos.CENTER);
		
		Scene sceneAlert = new Scene(layoutAlert);
		winAlert.setScene(sceneAlert);
		winAlert.showAndWait();
	}
}
