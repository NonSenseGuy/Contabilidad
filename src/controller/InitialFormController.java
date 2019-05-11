package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InitialFormController {

    @FXML
    private TextField txtCompanyName;

    @FXML
    private DatePicker date;

    @FXML
    private Button acceptButton;

    @FXML
    private Button cancelButton;

    @FXML
    void accept(ActionEvent event) {
	  	try {

	  		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/GB.fxml"));
	  		Parent root = (Parent)fxmlLoader.load();
	  		GBController controller = fxmlLoader.<GBController>getController();
	  		controller.setCompanyName(txtCompanyName.getText());
	  		String dateTime = date.getValue().toString();
	  		controller.setDate(dateTime);
	  		Scene scene = new Scene(root);
	  		Stage stage = (Stage) acceptButton.getScene().getWindow();
	  		stage.setScene(scene);
	  		stage.show();
        	
    	}catch(IOException e ) {
    		e.printStackTrace();
    	}
    }

    @FXML
    void cancel(ActionEvent event) {
    	 Stage stage = (Stage) cancelButton.getScene().getWindow();
    	 stage.close();
    }

}
