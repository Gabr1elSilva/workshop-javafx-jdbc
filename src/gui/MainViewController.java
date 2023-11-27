package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Pessoa;
import model.entities.Vacina;
import model.services.VacinaService;

public class MainViewController implements Initializable, DataChangeListener {

	private VacinaService vacina;

	@FXML
	private Button criarAplicacaoBotao;

	@FXML
	private Button editarBotaoVacina;

	@FXML
	private Button novaBotaoVacina;

	@FXML
	private Button pesquisarBotaoPessoa;

	@FXML
	private Button pesquisarBotaoVacina;

	@FXML
	private Button removerBotaoVacina;

	@FXML
	private TableView<Vacina> tableVacinaView;

	@FXML
	private TableColumn<Vacina, Long> tableVacinaColumnCodigo;

	@FXML
	private TableColumn<Vacina, String> tableVacinaColumnNome;

	@FXML
	private TableColumn<Vacina, String> tableVacinaColumnDescricao;

	@FXML
	private TableView<Pessoa> tablePessoaView;

	@FXML
	private TableColumn<Pessoa, Long> tablePessoaColumnCodigo;

	@FXML
	private TableColumn<Pessoa, String> tablePessoaColumnNome;

	@FXML
	private TableColumn<Pessoa, String> tablePessoaColumnCpf;

	@FXML
	private TableColumn<Pessoa, LocalDate> tablePessoaColumnNascimento;

	@FXML
	void onCriarAplicacaoBotaoAction() {
		System.out.println("onCriarAplicacaoBotaoAction");
	}

	@FXML
	void onEditarBotaoVacinaAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Vacina obj = new Vacina();
		createDialogForm(obj, "/gui/VacinaForm.fxml", parentStage);
	}

	@FXML
	void onNovaBotaoVacinaAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Vacina obj = new Vacina();
		createDialogForm(obj, "/gui/VacinaForm.fxml", parentStage);
	}

	@FXML
	void onPesquisarBotaoPessoaAction() {
		System.out.println("onPesquisarBotaoPessoaAction");
	}

	@FXML
	void onPesquisarBotaoVacinaAction() {
		pesquisarTodasVacinas();
		System.out.println("onPesquisarBotaoVacinaAction");
	}

	@FXML
	void onRemoverBotaoVacinaAction() {
		Vacina vacinaSelecionada = tableVacinaView.getSelectionModel().getSelectedItem();

		if (vacinaSelecionada != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText(null);
			alert.setContentText("Tem certeza de que deseja excluir esta vacina?");

			Optional<ButtonType> result = alert.showAndWait();

			if (result.isPresent() && result.get() == ButtonType.OK) {
				vacina.deleteByCodigo(vacinaSelecionada.getCodigo());
				pesquisarTodasVacinas();
			}
		} else {
			Alerts.showAlert("Nenhuma vacina selecionada", null, "Por favor, selecione uma vacina para remover.",
					AlertType.WARNING);
		}
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		this.vacina = new VacinaService();
		initializeNodes();
		pesquisarTodasVacinas();
	}

	private void initializeNodes() {
		tableVacinaColumnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
		tableVacinaColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableVacinaColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

		tablePessoaColumnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
		tablePessoaColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tablePessoaColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		tablePessoaColumnNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
	}

	private void pesquisarTodasVacinas() {
		try {
			VacinaService vacinaService = new VacinaService();
			List<Vacina> listaVacinas = vacinaService.findAll();
			atualizarTabelaVacinas(listaVacinas);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void atualizarTabelaVacinas(List<Vacina> listaVacinas) {
		if (listaVacinas != null) {
			ObservableList<Vacina> obsList = FXCollections.observableArrayList(listaVacinas);
			tableVacinaView.getItems().clear();
			tableVacinaView.setItems(obsList);
		} else {
			System.err.println("A lista de vacinas é nula.");
		}
	}

	private void createDialogForm(Vacina obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			VacinaFormController controller = loader.getController();
			controller.setVacina(obj);
			controller.setVacinaService(new VacinaService());
			controller.subscribeDataChangeListener(this);
			controller.updateVacinaFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Formulário de registro de vacina");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Erro ao carregar a janela", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChanged() {
		pesquisarTodasVacinas();
	}
}
