package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Vacina;
import model.exceptions.ValidationException;
import model.services.VacinaService;

public class VacinaFormController implements Initializable {

	private Vacina entity;

	private VacinaService vacina;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtCodigo;

	@FXML
	private TextField txtNome;
	@FXML
	private TextField txtDescricao;

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

	public void setVacinaService(VacinaService vacina) {
		this.vacina = vacina;
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	@FXML
	public void OnBtOkAction(ActionEvent event) {
		try {
			if (entity == null) {
				throw new IllegalStateException("A entidade está nula");
			}
			if (vacina == null) {
				throw new IllegalStateException("O serviço está nulo");
			}
			entity = getFormData();
			
			vacina.saveOrUpdate(entity);

			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		} catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		} catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}

	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	private Vacina getFormData() {
		Vacina obj = new Vacina();

		ValidationException exception = new ValidationException("Validation Error");	
		
		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "O campo não pode ser vazio");
		}

		if (txtDescricao.getText() == null || txtDescricao.getText().trim().equals("")) {
			exception.addError("descricao", "O campo não pode ser vazio");
		}

		obj.setNome(txtNome.getText());
		obj.setDescricao(txtDescricao.getText());

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return obj;
	}

	@FXML
	public void OnBtCancelarAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		Constraints.setTextFieldLong(txtCodigo);
		Constraints.setTextFieldMaxLength(txtNome, 60);
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

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		if (fields.contains("nome")) {
			labelErrorNome.setText(errors.get("nome"));
		}

		if (fields.contains("descricao")) {
			labelErrorDescricao.setText(errors.get("descricao"));
		}
	}
}