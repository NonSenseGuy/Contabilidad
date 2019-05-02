package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import model.Container;

public class REController implements Initializable{
	
	private Container container; 

    @FXML
    private ListView<String> entryList;

    @FXML
    private ListView<String> spendList;

    @FXML
    private Button entryButton;

    @FXML
    private Button spendButton;

    @FXML
    void addEntry(ActionEvent event){
    	try {
    		String account = createInputDialog("Ingreso","Nombre de la cuenta");
        	int value = Integer.parseInt((createInputDialog("Ingreso","valor de la cuenta")));
        	container.getEntry().put(account, value);
        	updateGUI();
    	}catch(NumberFormatException e) {
    		Alert alert = new Alert(AlertType.ERROR);
	    	alert.setContentText("Formateo erroneo");
	    	alert.showAndWait();
	    	throw new IllegalArgumentException("");
    	}
    	
    }

    @FXML
    void addSpend(ActionEvent event) {
      	try {
    		String account = createInputDialog("Gasto","Nombre de la cuenta");
        	int value = Integer.parseInt((createInputDialog("Gasto","valor de la cuenta")));
        	container.getSpend().put(account, value);
        	updateGUI();
    	}catch(NumberFormatException e) {
    		Alert alert = new Alert(AlertType.ERROR);
	    	alert.setContentText("Formateo erroneo");
	    	alert.showAndWait();
	    	throw new IllegalArgumentException("");
    	}
    	
    }
    
    public void setContainer(Container c) {
    	container = c; 
    }
    
	public String createInputDialog(String title, String message) throws IllegalArgumentException{
		TextInputDialog dialog = new TextInputDialog("");
	    dialog.setTitle(title);
	    dialog.setHeaderText(null);
	    dialog.setContentText(message);

	    Optional<String> result = dialog.showAndWait();
	    if (result.isPresent()){
	    	return result.get();
	    }else {
	    	Alert alert = new Alert(AlertType.ERROR);
	    	alert.setContentText("Formateo erroneo, este seguro de no dejar ningun espacio en blanco");
	    	alert.showAndWait();
	    	throw new IllegalArgumentException("");
	    }
	}
    
    public void updateGUI() {
    	if(!container.getEntry().isEmpty()) {
    		entryList.getItems().clear();
    		for(String account: container.getEntry().keySet()) {
    			String s = account + " : " + container.getEntry().get(account);
    			entryList.getItems().add(s);
    		}
    		
    	}
    	if(!container.getSpend().isEmpty()){
    		spendList.getItems().clear();
    		for(String account: container.getSpend().keySet()) {
    			String s = account + " : " + container.getSpend().get(account);
    			spendList.getItems().add(s);
    		}
    		
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		   Platform.runLater(() -> {

				updateGUI();

		    });

		
	}

}
