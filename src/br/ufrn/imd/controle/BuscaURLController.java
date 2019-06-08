package br.ufrn.imd.controle;

import br.ufrn.imd.modelo.Leitura;
import br.ufrn.imd.modelo.WebScraping;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

public class BuscaURLController {
	
	private boolean leituraEfetuada = false;
	private Leitura l;
	
	@FXML
    private TextField textFieldMinimoPalavra;

    @FXML
    private TextField textFieldURL;

    @FXML
    private Slider sliderSimilaridade;
    
    @FXML
    private Label sliderValor;

    @FXML
    private ProgressBar progressFakeNews;

    @FXML
    void LerArquivo(ActionEvent event) {
    	int valorInteiro=-5;
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
		}else {
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
    void buscarNoticiaUrl(ActionEvent event) {
    	if(!leituraEfetuada) {
			mostrarAlerta("Erro", "Atenção, Primeiro efetue a leitura do Arquivo acima!");
			return;
		}
    	
    	try {
    		WebScraping web = new WebScraping(textFieldURL.getText());
    		int porcentagemFakeNews = web.buscar(l,sliderSimilaridade.getValue());
    		if(porcentagemFakeNews == 100) {
    			progressFakeNews.setProgress(100);
    			mostrarAlerta("Encontrada", "Atenção, o site contém uma Fake News com 100% de compatibilidade com notícia armazenada");
    		}else if(porcentagemFakeNews>=sliderSimilaridade.getValue()) {
    			progressFakeNews.setProgress(porcentagemFakeNews);
    			mostrarAlerta("Encontrada", "Atenção, o site contém uma Fake News com "+porcentagemFakeNews+"% de compatibilidade com notícia armazenada");
    		}else {
    			progressFakeNews.setProgress(0);
    			mostrarAlerta("Não encontrada", "Fake news não se encontra no banco de dados");
    		}
    	}catch (Exception e) {
    		e.printStackTrace();
    		mostrarAlerta("Erro", "URL não corresponde a um site");
    	}
		
		
		
    }

    public void mostrarAlerta(String titulo, String mensagem) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titulo);
		alert.setContentText(mensagem);
		alert.showAndWait();
	}
}
