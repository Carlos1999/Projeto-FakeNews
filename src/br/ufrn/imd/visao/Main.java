package br.ufrn.imd.visao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import br.ufrn.imd.controle.Leitura;
import br.ufrn.imd.modelo.Similaridade;
import br.ufrn.imd.modelo.WebScraping;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
		
	
		Leitura l = new Leitura();
		l.lerArquivo(4);
		int contador =0;
		int encontradas =0;
		int naoEncontradas=0;
		for(String url:a) {
			if(contador>200) {
				break;
			}
			
			if(contador>=0) {
			
			WebScraping web = new WebScraping(url);
			
			if(web.buscar(l)) {
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
		}
		System.out.println(encontradas);
		System.out.println(naoEncontradas);
	}
	
}
