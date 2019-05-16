package controller;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.Container;

public class GBController implements Initializable{
	
	private Container container;
	

    @FXML
    private ListView<String> activeCurrentList;
    
    @FXML
    private ListView<String> activeNotCurrentList;

    @FXML
    private ListView<String> passiveList;

    @FXML
    private ListView<String> heritageList;

    @FXML
    private Button nextButton; 

    @FXML
    private Button addAccountButton;
    
    @FXML
    private Label companyName;
    
    @FXML
    private Label date;
    
    @FXML
    private Label totalActives;
    
    @FXML
    private Label totalActivesC;
    
    @FXML
    private Label totalActivesNC;
    
    @FXML
    private Label totalPassives;
    
    @FXML
    private Label totalHeritage;
    
    @FXML
    private Label totalPassivesAndHeritage;
        
    @FXML
    private Button evaluateButton;
    
    @FXML
    private Button DeleteAccountButton;

    @FXML
    void DeleteAccountClicked(ActionEvent event) {

    	deleteSelectedAccounts();
    	
    	updateGUI();
    }

    
    @FXML
    void next(ActionEvent event) {
    	goToRE();
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
    
    public void deleteSelectedAccounts() {
    	
    	String activeCurrentSearch = activeCurrentList.getSelectionModel().getSelectedItem();
    	if(activeCurrentSearch!=null) {
    		String [] activeCurrent=activeCurrentSearch.split(" ");
    		
    		container.getActivesCurrent().remove(activeCurrent[0]);
    	}
    	
    	String activeNotCurrentSearch = activeNotCurrentList.getSelectionModel().getSelectedItem();
    	if(activeNotCurrentSearch!=null) {
    		String [] activeNotCurrent=activeNotCurrentSearch.split(" ");
    		
    		container.getActivesNotCurrent().remove(activeNotCurrent[0]);
    	}  
    	
    	String passiveCurrentSearch=passiveList.getSelectionModel().getSelectedItem();
    	if(passiveCurrentSearch!=null) {
    		String [] passive=passiveCurrentSearch.split(" ");
    		
    		container.getPassives().remove(passive[0]);
    	}
    	
    	String heritageCurrentSearch = heritageList.getSelectionModel().getSelectedItem();
    	if(heritageCurrentSearch!=null) {
    		String [] heritage=heritageCurrentSearch.split(" ");
    		
    		container.getHeritage().remove(heritage[0]);
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
	
	@FXML
	public void addDato(ActionEvent event) {
		GridPane grid = new GridPane();
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		ButtonType loginButtonType = new ButtonType("Aceptar", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
		dialog.setTitle("Agregar nueva cuenta");
		dialog.setHeaderText(null);
		TextField name = new TextField();
		name.setPromptText("Nombre de la cuenta");
		TextField value = new TextField();
		value.setPromptText("Valor de la cuenta");

		ChoiceBox<String> choices = new ChoiceBox<>();
		choices.setItems(FXCollections.observableArrayList("Activo Corriente", "Activo No Corriente", "Pasivo",
				"Patrimonio"));
		grid.add(new Label("Nombre de la cuenta: "), 0, 0);
		grid.add(name, 1, 0);
		grid.add(new Label("Valor de la cuenta: "), 0, 1);
		grid.add(value, 1, 1);
		grid.add(new Label("Seleccione el tipo: "), 0, 2);
		grid.add(choices, 1, 2);
		dialog.getDialogPane().setContent(grid);
		dialog.setResultConverter(f -> {
			if (f == loginButtonType) {
				return new Pair<>(name.getText(), value.getText());
			}
			return null;
		});
		Optional<Pair<String, String>> result = dialog.showAndWait();
		result.ifPresent(e -> {
			addAccount(e.getKey(), Double.parseDouble(e.getValue()), choices.getValue());
		});
	}
	
	public void addAccount(String account, double value, String type) {

		Alert alert = null;
		if (account == null || account.equals("") || Double.toString(value) == null || Double.toString(value).equals("") || type == null
				|| type.equals("")) {

			if (account == null || account.equals("")) {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error en el nombre.");
				alert.setContentText("El nombre no puede estar vacío.");
				alert.showAndWait();

			} else if (Double.toString(value) == null || Double.toString(value).equals("")) {

				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error en el valor.");
				alert.setContentText("El valor no puede estar vacío.");
				alert.showAndWait();
			} else {

				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error en el tipo.");
				alert.setContentText("El tipo no puede estar vacío.");
				alert.showAndWait();
			}
//		} else if(Double.toString(value).startsWith("-")){
//			alert = new Alert(AlertType.ERROR);
//			alert.setTitle("Error");
//			alert.setHeaderText("Error en el valor.");
//			alert.setContentText("El valor ingresado debe ser positivo.");
//			alert.showAndWait();
			
		}else {
	
			if (type.equals("Activo Corriente")) {
				container.getActivesCurrent().put(account, value);
			}
			
			if (type.equals("Activo No Corriente")) {
				container.getActivesNotCurrent().put(account, value);
			}
			
			if (type.equals("Pasivo")) {
				container.getPassives().put(account, value);
			}
			
			if (type.equals("Patrimonio")) {
				container.getHeritage().put(account, value);
			}
			updateGUI();
		
		}

	}
	
	public void updateGUI() {
		refreshUtility();
		 DecimalFormat df = new DecimalFormat(
			      "#,##0.00", 
			      new DecimalFormatSymbols(new Locale("pt", "BR")));

		//if(!activeCurrentList.getItems().isEmpty()) {
			activeCurrentList.getItems().clear();
			for(String account: container.getActivesCurrent().keySet()) {
				String s = account + "          $ " + df.format(container.getActivesCurrent().get(account));
				activeCurrentList.getItems().add(s);
			}
		//}
		//if(!activeNotCurrentList.getItems().isEmpty()) {
			activeNotCurrentList.getItems().clear();
			for(String account: container.getActivesNotCurrent().keySet()) {
				String s = account + "          $ " + df.format(container.getActivesNotCurrent().get(account));
				activeNotCurrentList.getItems().add(s);
			}
		//}
		//if(!passiveList.getItems().isEmpty()) {
			passiveList.getItems().clear();
			for(String account: container.getPassives().keySet()) {
				String s = account + "          $ " + df.format(container.getPassives().get(account));
				passiveList.getItems().add(s);
			}
		//}
		//if(!heritageList.getItems().isEmpty()) {
			heritageList.getItems().clear();
			for(String account: container.getHeritage().keySet()) {
				String s = account + "          $ " + df.format(container.getHeritage().get(account));
				heritageList.getItems().add(s);
			}	
				
		//}
		
		totalActivesC.setText("$ "+df.format(container.totalActivesC()));
		totalActivesNC.setText("$ "+df.format(container.totalActivesNC()));
		totalPassives.setText("$ "+df.format(container.totalPassives()));
		totalHeritage.setText("$ "+df.format(container.totalHeritage()));
		totalActives.setText("$ "+df.format(container.totalActivesC()+container.totalActivesNC()));
		totalPassivesAndHeritage.setText("$ "+df.format(container.totalPassives()+container.totalHeritage()));
		
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
		
		   activeCurrentList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		   activeNotCurrentList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		   passiveList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		   heritageList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
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
	
	public void setCompanyName(String cName) {
		companyName.setText(cName);
	}
	
	public void setDate(String date) {
		this.date.setText(date);
	}

}
