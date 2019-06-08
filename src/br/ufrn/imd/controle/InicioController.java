package br.ufrn.imd.controle;

import br.ufrn.imd.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class InicioController{

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
    void botaoAjuda(ActionEvent event) {

    }
}
