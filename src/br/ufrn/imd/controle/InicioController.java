package br.ufrn.imd.controle;

import br.ufrn.imd.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;

public class InicioController{
	
	@FXML
    private ImageView imagem;

    @FXML
    void abrirTelaBuscarURL(ActionEvent event) {
		Main.mudarTela(1);
    }

    @FXML
    void abrirTelaBuscarTexto(ActionEvent event) {
    	Main.mudarTela(2);
    }

    @FXML
    void abrirTelaBuscaCompleta(ActionEvent event) {
    	Main.mudarTela(3);
    }

    @FXML
    void abrirTelaNavegador(ActionEvent event) {
    	Main.mudarTela(4);
    }
    
    
    @FXML
    void botaoAjuda(ActionEvent event) {
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Ajuda");
		alert.setContentText("Sistema de detec��o de Fake News: Usando um banco de dados com not�cias j� consideradas 'fake News' o sistema realiza uma busca em novas not�cias apresentadas para determinar se s�o compat�veis com alguma das j� cadastradas.");
		alert.showAndWait();
    }
}
