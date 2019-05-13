package controller;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
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
    private Label Utility;
    
    @FXML
    private Button nextButton;

    @FXML
    void next(ActionEvent event) {
    	goToGB();
    }
    
    @FXML
    private Button DeleteButton;

    @FXML
    void DeleteClicked(ActionEvent event) {

    	deleteSelectedAccounts();
    	
    }

    @FXML
    void addEntry(ActionEvent event){
    	try {
    		String account = createInputDialog("Ingreso","Nombre de la cuenta");
        	double value = Double.parseDouble((createInputDialog("Ingreso","valor de la cuenta")));
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
        	double value = Double.parseDouble((createInputDialog("Gasto","valor de la cuenta")));
        	container.getSpend().put(account, value);
        	updateGUI();
    	}catch(NumberFormatException e) {
    		Alert alert = new Alert(AlertType.ERROR);
	    	alert.setContentText("Formateo erroneo");
	    	alert.showAndWait();
	    	throw new IllegalArgumentException("");
    	}
    	
    }
    
    public void deleteSelectedAccounts() {
    	
    	String entrySearch = entryList.getSelectionModel().getSelectedItem();
    	if(entrySearch!=null) {
    		String [] entry = entrySearch.split(" ");
    		
    		container.getEntry().remove(entry[0]);
    	}
    	
    	String spendSearch = spendList.getSelectionModel().getSelectedItem();
    	if(spendSearch!=null) {
    		String [] spend = spendSearch.split(" ");
    		
    		container.getSpend().remove(spend[0]);
    	}
    	
    	updateGUI();
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
    	DecimalFormat df = new DecimalFormat(
			      "#,##0.00", 
			      new DecimalFormatSymbols(new Locale("pt", "BR")));
    	if(!container.getEntry().isEmpty()) {
    		entryList.getItems().clear();
    		for(String account: container.getEntry().keySet()) {
    			String s = account + "          $ "+ df.format(container.getEntry().get(account));
    			entryList.getItems().add(s);
    		}
    		
    	}
    	if(!container.getSpend().isEmpty()){
    		spendList.getItems().clear();
    		for(String account: container.getSpend().keySet()) {
    			String s = account + "          $ " + df.format(container.getSpend().get(account));
    			spendList.getItems().add(s);
    		}
    		
    	}
    	
    		Utility.setText(df.format(container.getUtility()));
    }
    
	public void goToGB() {
	  	try {

	  		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/GB.fxml"));
	  		Parent root = (Parent)fxmlLoader.load();
	  		GBController controller = fxmlLoader.<GBController>getController();
	  		controller.setContainer(container);
	  		Scene scene = new Scene(root);
	  		Stage stage = (Stage) nextButton.getScene().getWindow();
	  		stage.setScene(scene);
	  		stage.show();
        	
    	}catch(IOException e ) {
    		e.printStackTrace();
    	}
	  	refreshUtility();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		   Platform.runLater(() -> {

				updateGUI();
				if(container == null) {
					container = new Container();
				}
				
		    });

		entryList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		spendList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}
	
	public void refreshUtility() {
		container.getHeritage().put("Utilidad", container.getUtility());
	}

}
