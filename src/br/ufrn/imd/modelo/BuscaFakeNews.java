package br.ufrn.imd.modelo;

import java.util.HashMap;
import java.util.Map.Entry;

public class BuscaFakeNews {

	public static int buscar(String boato,Leitura l,double MinimaPorcentagem) {
		String boatoTratado = l.tratar(boato);
		String boatoHash = l.gerarHash(boatoTratado);
		if(l.containsFakeNews(boatoHash)) {
			return 100;
		}

		HashMap<String,FakeNews> boatos = l.getBoatos();
		int retorno = 0;
		
		//vai iterar sobre o banco de dados
		for (Entry<String, FakeNews> pair : boatos.entrySet()) {
		    FakeNews f = pair.getValue();
		    if(Similaridade.Jarodistance(boatoTratado, f.getProcessado())>MinimaPorcentagem) {
		    	
		    	retorno += Similaridade.Jarodistance(boatoTratado, f.getProcessado())*100;
		    	return retorno;
		    }
		}
		return 0;
	}
}
