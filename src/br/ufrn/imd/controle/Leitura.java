package br.ufrn.imd.controle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import br.ufrn.imd.modelo.FakeNews;

public class Leitura {
	private HashMap<String,FakeNews> boatos;
	//um hashmap que armazena o início e o fim do conteúdo da fake news, para ajudar na busca nos sites
	private HashMap<String,String> inicioEfim;
	public Leitura() {
		boatos = new HashMap<String,FakeNews>();
		inicioEfim = new HashMap<String,String>();
	}
	
	public void lerArquivo(int tamanhoMinimo) {
	 try{
         BufferedReader br = new BufferedReader(new FileReader("boatos.csv"));
//         FileWriter arq1 = new FileWriter("conteudos.txt");
//         FileWriter arq2 = new FileWriter("URLs.txt");
//         PrintWriter gravarConteudo = new PrintWriter(arq1);
//         PrintWriter gravarURL = new PrintWriter(arq2);
         int indice = 0;  
         br.readLine();
         while(br.ready()){     	    
        		String linha = br.readLine();
      	 		indice++;       	 	
        		String linhaOriginal;
        		String hash;
        		String URL;
        		String data;
        		linhaOriginal = linha;        		
        		data = separarData(linha);
        		URL = separarURL(linha);        		
        		linha = separarConteudo(linha);     		
        		linha = removerAcentos(linha);
//        		gravarConteudo.println(indice+":"+linha);
//        		gravarURL.println(indice+":"+URL);
        		//armazenando o inicio e o fim do conteudo da fake news (20 primeiros e 20 ultimos)
        		if(linha.length()>30) {
        			inicioEfim.put(linha.substring(0,30),linha.substring(linha.length()-30,linha.length()));
        		}
        		linha = tratar(linha);	     	
        		hash = gerarHash(linha);
        	
        		FakeNews f = new FakeNews(linhaOriginal,linha,data,URL);
        		boatos.put(hash,f);
        	    
        	
         }
//         arq1.close();
//         arq2.close();
         br.close();
      }catch(IOException ioe){
         ioe.printStackTrace();
      }
	}
	
	//Retorna somente a parte do conteúdo da string
	public String separarConteudo(String linha) {
		String[] linhaSeparada;
		linhaSeparada = linha.split(",");
		String conteudo="";
		if(linhaSeparada.length>3) {
			for (int i = 1; i < linhaSeparada.length-2; i++) {
				conteudo+=linhaSeparada[i];
			}
			conteudo = conteudo.replace("\"","");
			if(conteudo.substring(0,1).equals(" ")) {
				conteudo = conteudo.substring(1,conteudo.length());
			}
			return conteudo;	
		}
		return "N�o foi poss�vel separar o conte�do";
	}
	
	//Remove as palavras com menos de tamanhoMinimo de length e transforma todas as letras para menúsculas, além de também retirar os acentos
	public String removerPalavrasPequenas(String linha,int tamanhoMinimo) {
		int espacoEncontrado=0;
		String palavra="";
				
		String[] linhaSeparada;
		linhaSeparada = linha.split(" ");
		String linhaFinal="";
		for (int i = 0; i < linhaSeparada.length; i++) {
			if(linhaSeparada[i].length()>=tamanhoMinimo) {
				linhaFinal = linhaSeparada[i];
			}
		}
		linha=linhaFinal;
		//transformando tudo para menúsculo
		linha = linha.toLowerCase();
		if(linha.substring(0,1).equals(" ")) {
			linha = linha.substring(1,linha.length());
		}
		return linha;
	}
	
	//remove todos os acentos
	public String removerAcentos(String linha) {
	    linha =Normalizer.normalize(linha, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	    linha = linha.replaceAll("[^a-zA-Z ]", "");	    
	    linha = linha.trim();
	    return linha;
	}
	
	//retira repetição e coloca em ordem Alfabética
	public String removerRepeticao(String linha) {
		Set<String> palavras = new HashSet<String>();
		String linhaFinal;
		String[] linhaSeparada = linha.split(" "); 			
		Arrays.sort(linhaSeparada);
		
		for (int i = 0; i < linhaSeparada.length; i++) {
			palavras.add(linhaSeparada[i]);
		}
		linhaFinal = palavras.toString();
				
		return linhaFinal;
	}
	
	public String gerarHash(String linha) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.reset();
		    digest.update(linha.getBytes("utf8"));
		    linha = String.format("%040x", new BigInteger(1, digest.digest()));
		    return linha;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "falha";
	}
	
	public String separarURL(String linha) {
		String[] linhaSeparada;
		linhaSeparada = linha.split(",");
		for (int i = 0; i < linhaSeparada.length; i++) {
			if(linhaSeparada[i].length()>7) {
				if(linhaSeparada[i].substring(0,8).equals("https://")) {
					return linhaSeparada[i];
				}
			}
			
		}
		return "erro em separar URL";
	}
	
	public String separarData(String linha) {
		String[] linhaSeparada;
		linhaSeparada = linha.split(",");
		if(linhaSeparada[linhaSeparada.length-1].length()>20) {
			return "Erro ao pegar a data";
		}		
		return linhaSeparada[linhaSeparada.length-1];
	}
	
	public String tratar(String linha) {
		linha = removerPalavrasPequenas(linha, 4);
		linha = removerRepeticao(linha);
		return linha;
	}
	
	public boolean inicioIgual(String inicio) {
		if(inicioEfim.containsKey(inicio)) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public boolean fimIgual(String fim) {
		if(inicioEfim.containsValue(fim)){
			return true;
		}else {
			return false;
		}
	}
	
	
	//retorna true se aquela notícia está armazenada
	public boolean buscarFakeNews(String hash) {
		if(boatos.containsKey(hash)) {
			return true;
		}else {
			return false;
		}
		
	}
}
