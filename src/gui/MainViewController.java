package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Aplicacao;
import model.entities.Pessoa;
import model.entities.Situacao;
import model.entities.Vacina;
import model.services.AplicacaoService;
import model.services.PessoaService;
import model.services.VacinaService;

public class MainViewController implements Initializable, DataChangeListener {

	private VacinaService vacina;

	private PessoaService pessoa;

	private AplicacaoService aplicacaoService;

	@FXML
	private TextField txtCodigoVacina;

	@FXML
	private TextField txtNomeVacina;
	@FXML
	private TextField txtDescricaoVacina;

	@FXML
	private TextField txtCodigoPessoa;

	@FXML
	private TextField txtNomePessoa;

	@FXML
	private TextField txtCpfPessoa;

	@FXML
	private DatePicker dpApartir;

	@FXML
	private DatePicker dpAte;

	@FXML
	private Label labelErrorCodigo;

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
		try {
			List<Pessoa> pessoasSelecionadas = tablePessoaView.getSelectionModel().getSelectedItems();
			List<Vacina> vacinasSelecionadas = tableVacinaView.getSelectionModel().getSelectedItems();

			if (pessoasSelecionadas.isEmpty() || vacinasSelecionadas.isEmpty()) {
				Alerts.showAlert("Erro", "Seleção Inválida",
						"Selecione uma pessoa e uma vacina para criar uma aplicação.", AlertType.ERROR);
				return;
			}

			for (Pessoa pessoa : pessoasSelecionadas) {
				for (Vacina vacina : vacinasSelecionadas) {
					Aplicacao aplicacao = new Aplicacao();
					aplicacao.setPessoa(pessoa);
					aplicacao.setVacina(vacina);
					aplicacao.setSituacao(Situacao.ATIVO);

					aplicacaoService.insert(aplicacao);
				}
			}

			Alerts.showAlert("Sucesso", "Aplicações Criadas", "As aplicações foram criadas com sucesso.",
					AlertType.INFORMATION);
		} catch (Exception e) {
			Alerts.showAlert("Erro", "Erro ao Criar Aplicações", "Ocorreu um erro ao criar as aplicações.",
					AlertType.ERROR);
			e.printStackTrace();
		}
	}

	@FXML
	void onEditarBotaoVacinaAction(ActionEvent event) {
		Vacina vacinaSelecionada = tableVacinaView.getSelectionModel().getSelectedItem();

		VacinaFormController controller = new VacinaFormController();
		controller.setVacina(vacinaSelecionada); 

		if (vacinaSelecionada != null) {
			Stage parentStage = Utils.currentStage(event);
			createDialogForm(vacinaSelecionada, "/gui/VacinaForm.fxml", parentStage);
		} else {
			Alerts.showAlert("Nenhuma vacina selecionada", null, "Por favor, selecione uma vacina para editar.",
					AlertType.WARNING);
		}
	}

	@FXML
	void onNovaBotaoVacinaAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Vacina obj = new Vacina();
		createDialogForm(obj, "/gui/VacinaForm.fxml", parentStage);
	}

	@FXML
	void onPesquisarBotaoPessoaAction() {
		try {
			Long codigo = Utils.tryParseToLong(txtCodigoPessoa.getText());
			String nome = txtNomePessoa.getText();
			String cpf = txtCpfPessoa.getText();
			LocalDate dataInicio = dpApartir.getValue();
			LocalDate dataFinal = dpAte.getValue();

			List<Pessoa> listaPessoas;

			if (codigo == null && nome.isEmpty() && cpf.isEmpty() && dataInicio == null && dataFinal == null) {
				listaPessoas = pessoa.findAll();
			} else {
				listaPessoas = pessoa.findByParameters(codigo, nome, cpf, dataInicio, dataFinal);
			}

			atualizarTabelaPessoas(listaPessoas);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onPesquisarBotaoVacinaAction() {
		try {
			Long codigo = Utils.tryParseToLong(txtCodigoVacina.getText());
			String nome = txtNomeVacina.getText();
			String descricao = txtDescricaoVacina.getText();

			List<Vacina> listaVacinas;

			if (codigo == null && nome.isEmpty() && descricao.isEmpty()) {
				listaVacinas = vacina.findBySituacao(Situacao.ATIVO);
			} else {
				listaVacinas = vacina.findByParameters(codigo, nome, descricao);
			}

			atualizarTabelaVacinas(listaVacinas);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
				vacina.updateSituacao(vacinaSelecionada.getCodigo(), Situacao.INATIVO);
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
		this.pessoa = new PessoaService();
		this.aplicacaoService = new AplicacaoService();
		initializeNodes();
		pesquisarTodasVacinas();
		pesquisarTodasPessoas();
	}

	private void initializeNodes() {
		tableVacinaColumnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
		tableVacinaColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableVacinaColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		Constraints.setTextFieldLong(txtCodigoVacina);
		Constraints.setTextFieldMaxLength(txtNomeVacina, 70);
		Constraints.setTextFieldMaxLength(txtDescricaoVacina, 2000);

		tablePessoaColumnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
		tablePessoaColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tablePessoaColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		tablePessoaColumnNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
		Utils.formatTableColumnDate(tablePessoaColumnNascimento, "dd/MM/yyyy");
		Constraints.setTextFieldLong(txtCodigoPessoa);
		Constraints.setTextFieldMaxLength(txtNomePessoa, 70);
		Constraints.setTextFieldMaxLength(txtCpfPessoa, 11);
		Utils.formatDatePicker(dpApartir, "dd/MM/yyyy");
		Utils.formatDatePicker(dpAte, "dd/MM/yyyy");
	}

	private void pesquisarTodasVacinas() {
		try {
			VacinaService vacinaService = new VacinaService();
			Situacao situacao = Situacao.ATIVO;
			List<Vacina> listaVacinas = vacinaService.findBySituacao(situacao);
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

	private void pesquisarTodasPessoas() {
		try {
			PessoaService pessoaService = new PessoaService();
			List<Pessoa> listaPessoas = pessoaService.findAll();
			atualizarTabelaPessoas(listaPessoas);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void atualizarTabelaPessoas(List<Pessoa> listaPessoas) {
		if (listaPessoas != null) {
			ObservableList<Pessoa> obsList = FXCollections.observableArrayList(listaPessoas);
			tablePessoaView.getItems().clear();
			tablePessoaView.setItems(obsList);
		} else {
			System.err.println("A lista de pessoas é nula.");
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
