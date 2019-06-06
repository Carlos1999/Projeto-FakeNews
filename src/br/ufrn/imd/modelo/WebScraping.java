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
			fakeNewsTratada = l.tratar(fakeNewsCompleta);	
			fakeNewsTratada = l.gerarHash(fakeNewsTratada);				
			//se for exatamente igual
			if(l.buscarFakeNews(fakeNewsTratada)) {
				return true;
			}
			System.out.println(fakeNewsCompleta);
			HashMap<String,FakeNews> boatos = l.getBoatos();
			
			//vai iterar sobre o banco de dados
			for (Entry<String, FakeNews> pair : boatos.entrySet()) {
			    FakeNews f = pair.getValue();
			    if(Similaridade.Jarodistance(l.tratar(fakeNewsCompleta), f.getProcessado())>0.85) {
			    	System.out.println("Notícia é "+Similaridade.Jarodistance(l.tratar(fakeNewsCompleta),( f.getProcessado()))*100+"% compatível com uma notícia armazenada.");
			    	System.out.println("Notícia do site: "+l.tratar(fakeNewsCompleta));
			    	System.out.println("Notícia do banco: "+(f.getProcessado()));
			    	return true;
			    }
			}
			
			
		}
		return false;
	}
}
