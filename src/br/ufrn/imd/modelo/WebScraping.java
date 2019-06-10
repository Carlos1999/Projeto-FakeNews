package br.ufrn.imd.modelo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
	
	public String buscar(Leitura l,double MinimaPorcentagem, int tamanhoMinimo) {
		Elements p = doc.select("p"); 	
		String fakeNewsCompleta="";
		String fakeNewsTratada;
		boolean coletaParagrafos = false;
		String paragrafoString;
		for(Element paragrafo:p) {
			paragrafoString = paragrafo.text();
			paragrafoString = l.removerAcentos(paragrafoString);
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
			return fakeNewsCompleta;
		}
		return "erro";
	}
}
