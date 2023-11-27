package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class VacinaController implements Initializable{
	
	@FXML
	private Button btOK;
	
	@FXML
	private Button btCancelar;
	
	@FXML
	public void OnBtOkAction() {
		System.out.println("OnBtOkAction");
	}
	
	@FXML
	public void OnBtCancelarAction() {
		System.out.println("OnBtCancelarAction");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {		
	}

}
