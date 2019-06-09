package br.ufrn.imd.controle;

import br.ufrn.imd.modelo.BuscaFakeNews;
import br.ufrn.imd.modelo.WebScraping;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class BuscaTextoController extends BuscaController{
	@FXML
	private TextArea textAreaTexto;
	
	@FXML
    void buscarTexto(ActionEvent event) {
		if(!leituraEfetuada) {
			mostrarAlerta("Erro", "Aten��o, Primeiro efetue a leitura do Arquivo acima!");
			return;
		}
    	
    	try {
    		
    		int porcentagemFakeNews = BuscaFakeNews.buscar(textAreaTexto.getText(), l, sliderSimilaridade.getValue()/100);
    		if(porcentagemFakeNews == 100) {
    			progressFakeNews.setProgress(100);
    			mostrarAlerta("Encontrada", "Aten��o, o site cont�m uma Fake News com 100% de compatibilidade com not�cia armazenada");
    		}else if(porcentagemFakeNews>=sliderSimilaridade.getValue()) {
    			progressFakeNews.setProgress(porcentagemFakeNews);
    			mostrarAlerta("Encontrada", "Aten��o, o site cont�m uma Fake News com "+porcentagemFakeNews+"% de compatibilidade com not�cia armazenada");
    		}else {
    			progressFakeNews.setProgress(0);
    			mostrarAlerta("N�o encontrada", "Fake news n�o se encontra no banco de dados");
    		}
    	}catch (Exception e) {
    		e.printStackTrace();
    		mostrarAlerta("Erro", "URL n�o corresponde a um site");
    	}
    }
}
