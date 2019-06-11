package br.ufrn.imd.controle;

import br.ufrn.imd.modelo.BuscaFakeNews;
import br.ufrn.imd.modelo.WebScraping;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class BuscaURLController extends BuscaController {
    
	@FXML
    private TextField textFieldURL;
	
    @FXML
    void buscarNoticiaUrl(ActionEvent event) {
    	if(!leituraEfetuada) {
			mostrarAlerta("Erro", "Atencão, Primeiro efetue a leitura do Arquivo acima!");
			return;
		}
    	
    	try {
    		WebScraping web = new WebScraping(textFieldURL.getText());
    		String fakeNewsCompleta = web.buscar(leitura,sliderSimilaridade.getValue()/100,valorInteiro);
    		int porcentagemFakeNews = BuscaFakeNews.buscar(fakeNewsCompleta, leitura,sliderSimilaridade.getValue()/100,valorInteiro);
    		if(porcentagemFakeNews == 100) {
    			progressFakeNews.setProgress(1);
    			labelResultado.setText(100+"%");
    			mostrarAlerta("Encontrada", "Atencão, o site contém uma Fake News com 100% de compatibilidade com notícia armazenada");
    			mostrarAlerta("Encontrada", "Fake News encontrada no Site:"+fakeNewsCompleta);
    		}else if(porcentagemFakeNews>=sliderSimilaridade.getValue()) {
    			progressFakeNews.setProgress((double)porcentagemFakeNews/100);
    			labelResultado.setText(porcentagemFakeNews+"%");
    			mostrarAlerta("Encontrada", "Atencão, o site contém uma Fake News com "+porcentagemFakeNews+"% de compatibilidade com notícia armazenada");
    			mostrarAlerta("Encontrada", "Fake News encontrada no Site:"+fakeNewsCompleta);
    		}else {
    			progressFakeNews.setProgress(0);
    			labelResultado.setText(0+"%");
    			mostrarAlerta("Não encontrada", "Fake news não se encontra no banco de dados");
    		}
    	}catch (Exception e) {
    		e.printStackTrace();
    		mostrarAlerta("Erro", "URL não corresponde a um site");
    	}
			
    }
}
