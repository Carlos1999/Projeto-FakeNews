package br.ufrn.imd.controle;

import br.ufrn.imd.modelo.BuscaFakeNews;
import br.ufrn.imd.modelo.WebScraping;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
			mostrarAlerta("Erro", "Atencão, Primeiro efetue a leitura do Arquivo acima!");
			return;
		}
    	
    	try {
    		WebScraping web = new WebScraping(URL);
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
    			mostrarAlerta("Encontrada", "Atencão, o site contém uma Fake News com "+porcentagemFakeNews+"% de compatibilidade com notécia armazenada");
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
