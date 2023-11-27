package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
		loadView("/gui/Nova.fxml");
	}

	@FXML
	void onNovaBotaoVacinaAction() {
		loadView("/gui/Nova.fxml");
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
}
