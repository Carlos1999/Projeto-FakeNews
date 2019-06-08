package br.ufrn.imd.controle;

import br.ufrn.imd.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class InicioController{

    @FXML
    void abrirTelaBuscarURL(ActionEvent event) {
			Main.mudarTela(1);
    }

}
