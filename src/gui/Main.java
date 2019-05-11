package gui;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage window) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/gui/InitialForm.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		window.setScene(scene);
		window.setTitle("Chequeo de balance");
		window.show();
	}
	
	public static void main(String[] args) {

		launch(args);
	}
}
