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
}
