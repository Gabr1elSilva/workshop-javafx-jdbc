package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

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
	void onCriarAplicacaoBotaoAction() {
		System.out.println("onCriarAplicacaoBotaoAction");
	}

	@FXML
	void onEditarBotaoVacinaAction() {
		System.out.println("onEditarBotaoVacinaAction");
	}

	@FXML
	void onNovaBotaoVacinaAction() {
		System.out.println("onNovaBotaoVacinaAction");
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

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
	}
}
