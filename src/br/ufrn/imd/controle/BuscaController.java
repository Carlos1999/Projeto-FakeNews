package br.ufrn.imd.controle;

import br.ufrn.imd.Main;
import br.ufrn.imd.modelo.Leitura;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public abstract class BuscaController {
	protected boolean leituraEfetuada = false;
	protected Leitura l;
	protected int valorInteiro;
	
	@FXML
	protected TextField textFieldMinimoPalavra;

    @FXML
	protected Slider sliderSimilaridade;
    
    @FXML
    protected Label sliderValor;

    @FXML
    protected ProgressBar progressFakeNews;
    
    @FXML
    protected Label labelResultado;
    
    @FXML
    protected TextArea textAreaResultado;

    @FXML
    void LerArquivo(ActionEvent event) {
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
			l = new Leitura();
			l.lerArquivo(valorInteiro);
			leituraEfetuada = true;
			mostrarAlerta("Leitura","Leitura Efetuada com sucesso!");
		}
    }
    
    @FXML
    void duvidaLerArquivo(ActionEvent event) {
    		mostrarAlerta("Ajuda","Palavras, no texto das notícias, com o tamanho menor do que o valor que você digitar no campo 'Tamanho mínimo de palavras' serão desconsideradas após o tratamento do arquivo.");
    }
    
    @FXML
    void sliderMoveu(MouseEvent event) {
    	int a= (int) sliderSimilaridade.getValue();
    	sliderValor.setText(a+"%");
    }
    
    @FXML
    void botaoVoltar(ActionEvent event) {
    	Main.mudarTela(0);
    }
    
    @FXML
    void botaoFechar(ActionEvent event) {
    	System.exit(0);
    }
    
    public void mostrarAlerta(String titulo, String mensagem) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titulo);
		alert.setContentText(mensagem);
		alert.showAndWait();
	}
}
