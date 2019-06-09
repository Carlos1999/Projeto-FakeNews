package br.ufrn.imd.controle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import br.ufrn.imd.modelo.WebScraping;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sun.net.ProgressSource;

public class BuscaCompletaController extends BuscaController {
	@FXML
    private TextField textFieldInicio;

    @FXML
    private TextField textFieldFim;
    
	@FXML
	void buscarNoticias(ActionEvent event) {
		 int inicioIndice;
			int fimIndice;
			try {
				inicioIndice =Integer.parseInt(textFieldInicio.getText());
			}catch (NumberFormatException e){
				mostrarAlerta("Valor informado em: Início da busca INVALIDO!","Informe um valor entre 0 e 1000 ");
				return;
			}
			
			try {
				fimIndice =Integer.parseInt(textFieldFim.getText());
			}catch (NumberFormatException e){
				mostrarAlerta("Valor informado em: Final da busca INVALIDO!","Informe um valor entre 0 e 1000 ");
				return;
			}
			
			if(inicioIndice>fimIndice) {
				mostrarAlerta("Erro!","Início da busca é maior que o Fim da busca!");
			}
			ArrayList<String> a = new ArrayList<String>();
			try {
				BufferedReader br = new BufferedReader(new FileReader("URLs.txt"));
				int i=1;
					while(br.ready()){
						
						String linha = br.readLine();
						if(i<10) {
							linha=linha.substring(2, linha.length());
						}else if(i<100) {
							linha=linha.substring(3, linha.length());
						}else {
							linha=linha.substring(4, linha.length());
						}
						a.add(linha);
						i++;
					}
				}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			int contador =0;
			int encontradas =0;
			int naoEncontradas=0;
			for(String url:a) {
				if(contador>fimIndice) {
					break;
				}
				
				if(contador>=inicioIndice) {
				
				WebScraping web = new WebScraping(url);
				
				if(web.buscar(l,sliderSimilaridade.getValue()/100)>=85) {
					System.out.println("Notícia Fake news encontrada");
					encontradas++;
				}else {
					System.out.println(contador);
					System.out.println(url);
					System.out.println("Notícia Fake news NÃO encontrada");
					naoEncontradas++;
				}							
				
				}
				contador++;
				progressFakeNews.setProgress(contador/(inicioIndice-fimIndice));
			}
			mostrarAlerta("Resultado!","Notícias encontradas: "+encontradas+"| Notícias NÃO encontradas: "+naoEncontradas);
	    }
}

