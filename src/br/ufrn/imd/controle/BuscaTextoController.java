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
			mostrarAlerta("Erro", "Atenção, Primeiro efetue a leitura do Arquivo acima!");
			return;
		}
    	
    	try {
    		
    		int porcentagemFakeNews = BuscaFakeNews.buscar(l.removerAcentos(textAreaTexto.getText()), l, sliderSimilaridade.getValue()/100,valorInteiro);
    		if(porcentagemFakeNews == 100) {
    			progressFakeNews.setProgress(1);
    			labelResultado.setText(100+"%");
    			mostrarAlerta("Encontrada", "Atenção, o site contém uma Fake News com 100% de compatibilidade com notícia armazenada");
    			
    		}else if(porcentagemFakeNews>=sliderSimilaridade.getValue()) {
    			progressFakeNews.setProgress((double)porcentagemFakeNews/100);
    			labelResultado.setText(porcentagemFakeNews+"%");
    			mostrarAlerta("Encontrada", "Atenção, o site contém uma Fake News com "+porcentagemFakeNews+"% de compatibilidade com notícia armazenada");    			
    		}else {
    			progressFakeNews.setProgress((double)porcentagemFakeNews/100);
    			labelResultado.setText(porcentagemFakeNews+"%");
    			mostrarAlerta("Não encontrada", "Fake news não se encontra no banco de dados");
    		}
    		
    	}catch (Exception e) {
    		mostrarAlerta("Erro", "Texto Inválido");
    	}
    }
}
