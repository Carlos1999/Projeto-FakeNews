package br.ufrn.imd.controle;

import com.sun.xml.internal.ws.api.pipe.Engine;

import br.ufrn.imd.modelo.BuscaFakeNews;
import br.ufrn.imd.modelo.WebScraping;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.DragEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class NavegadorController extends BuscaController {
	@FXML
    private WebView webView;
	
	@FXML
    private Button buttonLigado;
	
	private WebEngine engine;
	
	@FXML
    void botaoLigar(ActionEvent event) {
		if(buttonLigado.getText().equals("Desligado")) {
			buttonLigado.setText("Ligado");
			engine = webView.getEngine();
			engine.load("https://www.google.com/");
			webView.setVisible(true);
		}else {
			buttonLigado.setText("Desligado");
			webView.setVisible(false);
		}
    }
	
	 @FXML
	 void botaoGoogle(ActionEvent event) {
		 if(buttonLigado.getText().equals("Desligado")) {
				mostrarAlerta("Erro","Primeiro ligue o navegador!");
			}else {
				engine.load("https://www.google.com/");
			}
	 }

	 @FXML
	 void botaoReload(ActionEvent event) {
		 if(buttonLigado.getText().equals("Desligado")) {
				mostrarAlerta("Erro","Primeiro ligue o navegador!");
			}else {
				engine.reload();
			}
	  }
	@FXML
    void buscarNoSite(ActionEvent event) {
		String URL = "";
		if(buttonLigado.getText().equals("Desligado")) {
			mostrarAlerta("Erro","Primeiro ligue o navegador!");
			return;
		}

		 URL = engine.getLocation();
		
		if(!leituraEfetuada) {
			mostrarAlerta("Erro", "Atenc�o, Primeiro efetue a leitura do Arquivo acima!");
			return;
		}
    	
    	try {
    		WebScraping web = new WebScraping(URL);
    		String fakeNewsCompleta = web.buscar(l,sliderSimilaridade.getValue()/100,valorInteiro);
    		int porcentagemFakeNews = BuscaFakeNews.buscar(fakeNewsCompleta, l,sliderSimilaridade.getValue()/100,valorInteiro);
    		if(porcentagemFakeNews == 100) {
    			progressFakeNews.setProgress(1);
    			labelResultado.setText(100+"%");
    			mostrarAlerta("Encontrada", "Atenc�o, o site cont�m uma Fake News com 100% de compatibilidade com not�cia armazenada");
    			mostrarAlerta("Encontrada", "Fake News encontrada no Site:"+fakeNewsCompleta);
    		}else if(porcentagemFakeNews>=sliderSimilaridade.getValue()) {
    			progressFakeNews.setProgress((double)porcentagemFakeNews/100);
    			labelResultado.setText(porcentagemFakeNews+"%");
    			mostrarAlerta("Encontrada", "Atenc�o, o site cont�m uma Fake News com "+porcentagemFakeNews+"% de compatibilidade com not�cia armazenada");
    			mostrarAlerta("Encontrada", "Fake News encontrada no Site:"+fakeNewsCompleta);
    		}else {
    			progressFakeNews.setProgress(0);
    			labelResultado.setText(0+"%");
    			mostrarAlerta("N�o encontrada", "Fake news n�o se encontra no banco de dados");
    		}
    	}catch (Exception e) {
    		e.printStackTrace();
    		mostrarAlerta("Erro", "URL n�o corresponde a um site");
    	}
			
    }
}
