package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Vacina;

public class VacinaFormController implements Initializable {

	private Vacina entity;

	@FXML
	private TextField txtCodigo;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtDescricao;

	@FXML
	private Label labelErrorCodigo;

	@FXML
	private Label labelErrorNome;

	@FXML
	private Label labelErrorDescricao;

	@FXML
	private Button btOK;

	@FXML
	private Button btCancelar;

	public void setVacina(Vacina entity) {
		this.entity = entity;
	}

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
		initializeNodes();
	}

	private void initializeNodes() {
		Constraints.setTextFieldLong(txtCodigo);
		Constraints.setTextFieldMaxLength(txtNome, 30);
		Constraints.setTextFieldMaxLength(txtDescricao, 1400);
	}

	public void updateVacinaFormData() {
		if (entity == null) {
			throw new IllegalStateException("A entidade está nula");
		}
		txtCodigo.setText(String.valueOf(entity.getCodigo()));
		txtNome.setText(entity.getNome());
		txtDescricao.setText(entity.getDescricao());

	}

}
