package br.ufrn.imd.modelo;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.ufrn.imd.controle.Leitura;

public class WebScraping {
	private Document doc;
	private String url;
	
	public WebScraping(String url) {
		this.url = url;
		
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public boolean buscar(Leitura l) {
		Elements p = doc.select("p"); 	
		String fakeNewsCompleta="";
		String fakeNewsTradada;
		boolean coletaParagrafos = false;
		String paragrafoString;
		for(Element paragrafo:p) {
			paragrafoString = paragrafo.text();
			paragrafoString = l.removerAcentos(paragrafoString);
			paragrafoString = paragrafoString.replace("Mensagem ","");		
			paragrafoString = paragrafoString.replace("Transcricao ","");		
			paragrafoString = paragrafoString.replace("Versao  ","");	
			if(paragrafoString.length()>30) {
			
				if(l.inicioIgual(paragrafoString.substring(0,30))) {
					coletaParagrafos = true;
				}
				
				if(coletaParagrafos == true) {
					fakeNewsCompleta += paragrafoString+" ";
				}
				
				if(l.fimIgual(paragrafoString.substring(paragrafoString.length()-30,paragrafoString.length()))) {

					break;
				}	
			}
			
		}
		
		if(!fakeNewsCompleta.equals("")) {
			
			fakeNewsTradada = l.tratar(fakeNewsCompleta);	
			fakeNewsTradada = l.gerarHash(fakeNewsTradada);				
			if(l.buscarFakeNews(fakeNewsTradada)) {
				return true;
			}	
			System.out.println(fakeNewsCompleta);
			System.out.println(fakeNewsTradada);
		}
		return false;
	}
}
