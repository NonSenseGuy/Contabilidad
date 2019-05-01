package controller;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import model.Container;

public class GBController implements Initializable{
	
	private Container container;

    @FXML
    private ListView<String> activeList;

    @FXML
    private ListView<String> passiveList;

    @FXML
    private ListView<String> heritageList;

    @FXML
    private Button nextButton;

    @FXML
    private Button activeButton;

    @FXML
    private Button passiveButton;

    @FXML
    private Button heritageButton;

    @FXML
    void addActive(ActionEvent event) {
    	String activeAccount = createInputDialog("Activo","Nombre de la cuenta");
    	int value = Integer.parseInt((createInputDialog("Activo","valor de la cuenta")));
    	 
    }

    @FXML
    void addHeritage(ActionEvent event) {

    }

    @FXML
    void addPasive(ActionEvent event) {

    }

    @FXML
    void next(ActionEvent event) {

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
		if(container.getActives() != null) {
			activeList.getItems().clear();
			for(String account: container.getActives().keySet()) {
				String s = account + " : " + container.getActives().get(account);
				activeList.getItems().add(s);
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		container = new Container();
		System.out.println("Hello");

	}

}
