package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Pessoa;
import model.entities.Vacina;

public class MainViewController implements Initializable {

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
	void onEditarBotaoVacinaAction() {
		loadView("/gui/VacinaRegistrationView.fxml");
	}

	@FXML
	void onNovaBotaoVacinaAction() {
		loadView("/gui/VacinaRegistrationView.fxml");
	}

	@FXML
	void onPesquisarBotaoPessoaAction() {
		System.out.println("onPesquisarBotaoPessoaAction");
	}

	@FXML
	void onPesquisarBotaoVacinaAction() {
		System.out.println("onPesquisarBotaoVacinaAction");
	}

	@FXML
	void onRemoverBotaoVacinaAction() {
		System.out.println("onRemoverBotaoVacinaAction");
	}

	private synchronized void loadView(String absoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Parent root = loader.load();

			Scene scene = new Scene(root);

			Stage newStage = new Stage();
			newStage.setTitle("Nova Janela");

			newStage.initModality(Modality.WINDOW_MODAL);

			newStage.setScene(scene);

			newStage.show();

		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Erro carregando a página", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableVacinaColumnCodigo.setCellValueFactory(new PropertyValueFactory<>("Código"));
		tableVacinaColumnNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
		tableVacinaColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("Descrição"));

		tablePessoaColumnCodigo.setCellValueFactory(new PropertyValueFactory<>("Código"));
		tablePessoaColumnNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
		tablePessoaColumnCpf.setCellValueFactory(new PropertyValueFactory<>("CPF"));
		tablePessoaColumnNascimento.setCellValueFactory(new PropertyValueFactory<>("Nascimento"));
	}

}
