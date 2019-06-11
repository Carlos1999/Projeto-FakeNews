package br.ufrn.imd.controle;

import br.ufrn.imd.Main;
import br.ufrn.imd.modelo.Leitura;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public abstract class BuscaController {
	protected boolean leituraEfetuada = false;
	protected Leitura leitura;
	protected int valorInteiro;
	
	@FXML
	protected TextField textFieldMinimoPalavra;

    @FXML
	protected Slider sliderSimilaridade;
    
    @FXML
    protected Label labelValor;

    @FXML
    protected ProgressBar progressFakeNews;
    
    @FXML
    protected Label labelResultado;
    

    @FXML
    protected void lerArquivo(ActionEvent event) {
		boolean valorValido=false;
		
			try {
				valorInteiro =Integer.parseInt(textFieldMinimoPalavra.getText());
				valorValido=true;
			}catch (NumberFormatException e){
				mostrarAlerta("Tamanho mínimo de palavras INVALIDO!","Informe um valor entre 0 e 10 ");
				leituraEfetuada = false;
				return;
			}
	
		
		if(valorInteiro<0||valorInteiro>10) {
			mostrarAlerta("Tamanho mínimo de palavras INVALIDO!","Informe um valor entre 0 e 10 ");
			leituraEfetuada = false;
		}else if(valorValido) {
			leitura = new Leitura();
			leitura.lerArquivo(valorInteiro);
			leituraEfetuada = true;
			mostrarAlerta("Leitura","Leitura Efetuada com sucesso!");
		}
    }
    
    @FXML
    protected void sliderMoveu(MouseEvent event) {
    	int a= (int) sliderSimilaridade.getValue();
    	labelValor.setText(a+"%");
    }
    
    @FXML
    protected void botaoVoltar(ActionEvent event) {
    	Main.mudarTela(0);
    }
    
    @FXML
    protected void botaoFechar(ActionEvent event) {
    	System.exit(0);
    }
    
    protected void mostrarAlerta(String titulo, String mensagem) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titulo);
		alert.setContentText(mensagem);
		alert.showAndWait();
	}
}
