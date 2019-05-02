package controller;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
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
    private Label totalActives;
    
    @FXML
    private Label totalPassives;
    
    @FXML
    private Label totalHeritage;
    
    @FXML
    private Button evaluateButton;
    
    @FXML
    void next(ActionEvent event) {
    	goToRE();
    }

    @FXML
    void addActive(ActionEvent event) {
    	try {
    		String account = createInputDialog("Activo","Nombre de la cuenta");
        	int value = Integer.parseInt((createInputDialog("Activo","valor de la cuenta")));
        	container.getActives().put(account, value);
        	String entryorspend = entryOrSpendDialog();
        	if(entryorspend.equals("i")) {
        		container.getEntry().put(account, value);
        	}else if(entryorspend.equals("g")) {
        		container.getSpend().put(account, value);
        	}
        	updateGUI();
        	
    	}catch(NumberFormatException e) {
    		Alert alert = new Alert(AlertType.ERROR);
	    	alert.setContentText("Formateo erroneo");
	    	alert.showAndWait();
	    	throw new IllegalArgumentException("");
    	}
    	 
    }

    @FXML
    void addHeritage(ActionEvent event) {
    	try {
    		String account = createInputDialog("Patrimonio","Nombre de la cuenta");
        	int value = Integer.parseInt((createInputDialog("Patrimonio","valor de la cuenta")));
        	container.getHeritage().put(account, value);
        	String entryorspend = entryOrSpendDialog();
        	if(entryorspend.equals("i")) {
        		container.getEntry().put(account, value);
        	}else if(entryorspend.equals("g")) {
        		container.getSpend().put(account, value);
        	}
        	updateGUI();
    	}catch(NumberFormatException e) {
    		Alert alert = new Alert(AlertType.ERROR);
	    	alert.setContentText("Formateo erroneo");
	    	alert.showAndWait();
	    	throw new IllegalArgumentException("");
    	}
    	
    	 
    }

    @FXML
    void addPasive(ActionEvent event) {
    	try {
    		String account = createInputDialog("Pasivo","Nombre de la cuenta");
        	int value = Integer.parseInt((createInputDialog("Pasivo","valor de la cuenta")));
        	container.getPassives().put(account, value);
        	String entryorspend = entryOrSpendDialog();
        	if(entryorspend.equals("i")) {
        		container.getEntry().put(account, value);
        	}else if(entryorspend.equals("g")) {
        		container.getSpend().put(account, value);
        	}
        	updateGUI();
    	}catch(NumberFormatException e) {
    		Alert alert = new Alert(AlertType.ERROR);
	    	alert.setContentText("Formateo erroneo");
	    	alert.showAndWait();
	    	throw new IllegalArgumentException("");
    	}
    	
    	 
    }

    @FXML
    void evaluateBalance(ActionEvent event) {
    	if(container.isBalanced()) {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
	    	alert.setContentText("El balange general es correcto");
	    	alert.showAndWait();
	    	
    	}else {
    		Alert alert = new Alert(AlertType.ERROR);
	    	alert.setContentText("El balange general esta erroneo, la diferencia es de " + container.diff());
	    	alert.showAndWait();
    	}
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
	
	public String entryOrSpendDialog() {
		TextInputDialog dialog = new TextInputDialog("");
	    dialog.setTitle("Clasifica como ingreso o gasto?");
	    dialog.setHeaderText(null);
	    dialog.setContentText("Digite 'i' si clasifica como ingreso,'g' si clasifica como gasto o presione cualquier otra tecla si no clasifica como ninguno de los 2");

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
		refreshUtility();
		if(!container.getActives().isEmpty()) {
			activeList.getItems().clear();
			for(String account: container.getActives().keySet()) {
				String s = account + " : " + container.getActives().get(account);
				activeList.getItems().add(s);
			}
		}
		if(!container.getPassives().isEmpty()) {
			passiveList.getItems().clear();
			for(String account: container.getPassives().keySet()) {
				String s = account + " : " + container.getPassives().get(account);
				passiveList.getItems().add(s);
			}
		}
		if(!container.getHeritage().isEmpty()) {
			heritageList.getItems().clear();
			for(String account: container.getHeritage().keySet()) {
				String s = account + " : " + container.getHeritage().get(account);
				heritageList.getItems().add(s);
			}	
				
		}
		
		totalActives.setText(Integer.toString(container.totalActives()));
		totalPassives.setText(Integer.toString(container.totalPassives()));
		totalHeritage.setText(Integer.toString(container.totalHeritage()));
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		container = new Container();
		   Platform.runLater(() -> {

				updateGUI();
				if(container == null) {
					container = new Container();
				}
				
		    });

	}
	
	public void setContainer(Container c ) {
		container = c;
	}
	
	public void goToRE() {
	  	try {

	  		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/RE.fxml"));
	  		Parent root = (Parent)fxmlLoader.load();
	  		REController controller = fxmlLoader.<REController>getController();
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
	
	public void refreshUtility() {
		container.getHeritage().put("Utilidad", container.getUtility());
	}

}
