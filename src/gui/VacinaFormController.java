package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
import model.services.VacinaService;

public class VacinaFormController implements Initializable {

	private Vacina entity;

	private VacinaService service;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

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

	public void setVacinaService(VacinaService service) {
		this.service = service;
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	@FXML
	public void OnBtOkAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("A entidade estava nula");
		}
		if (service == null) {
			throw new IllegalStateException("Serviço estava nulo.");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
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

		obj.setCodigo(Utils.tryParseToLong(txtCodigo.getText()));
		obj.setNome(txtNome.getText());
		obj.setDescricao(txtDescricao.getText());

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
