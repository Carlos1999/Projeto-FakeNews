package br.ufrn.imd.controle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import br.ufrn.imd.modelo.BuscaFakeNews;
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
		if (!leituraEfetuada) {
			mostrarAlerta("Erro", "Atenção, Primeiro efetue a leitura do Arquivo acima!");
			return;
		}

		try {
			inicioIndice = Integer.parseInt(textFieldInicio.getText());
		} catch (NumberFormatException e) {
			mostrarAlerta("Valor informado em: Início da busca INVALIDO!", "Informe um valor entre 0 e 1000 ");
			return;
		}

		try {
			fimIndice = Integer.parseInt(textFieldFim.getText());
		} catch (NumberFormatException e) {
			mostrarAlerta("Valor informado em: Final da busca INVALIDO!", "Informe um valor entre 0 e 1000 ");
			return;
		}

		if (inicioIndice > fimIndice) {
			mostrarAlerta("Erro!", "Início da busca é maior que o Fim da busca!");
			return;
		}

		if (inicioIndice < 0 || inicioIndice > 1000 || fimIndice < 0 || fimIndice > 1000) {
			mostrarAlerta("Valor informado em: Final ou Inicio da busca INVALIDO!", "Informe um valor entre 0 e 1000 ");
			return;
		}
		ArrayList<String> a = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader("URLs.txt"));
			int i = 1;
			while (br.ready()) {

				String linha = br.readLine();
				if (i < 10) {
					linha = linha.substring(2, linha.length());
				} else if (i < 100) {
					linha = linha.substring(3, linha.length());
				} else {
					linha = linha.substring(4, linha.length());
				}
				a.add(linha);
				i++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int contador = 0;
		double encontradas = 0;
		double naoEncontradas = 0;
		double resultado;
		int intResultado;
		String textoResultado = "";
		for (String url : a) {
			if (contador > fimIndice) {
				break;
			}

			try {
				if (contador >= inicioIndice) {

					WebScraping web = new WebScraping(url);
					String fakeNewsCompleta = web.buscar(l, sliderSimilaridade.getValue() / 100, valorInteiro);
					int porcentagemSimilaridade = BuscaFakeNews.buscar(fakeNewsCompleta, l,
							sliderSimilaridade.getValue() / 100, valorInteiro);
					if (porcentagemSimilaridade > sliderSimilaridade.getValue()) {
						textoResultado += "Notícia Fake news encontrada! similaridade:" + porcentagemSimilaridade
								+ "% \n";
						System.out
								.println("Notícia Fake news encontrada: similaridade:" + porcentagemSimilaridade + "%");
						encontradas++;
					} else {
						System.out.println(contador);
						System.out.println(url);
						System.out.println("Notícia Fake news NÃO encontrada");
						textoResultado += "similaridade: " + porcentagemSimilaridade + "% \n";
						System.out.println("similaridade: " + porcentagemSimilaridade + "%");
						textoResultado += "Notícia Fake news NÃO encontrada \n";
						textoResultado += "Número da notícia: " + contador + "\n ";
						textoResultado += "URL:" + url + " \n";
						naoEncontradas++;
					}

				}

				contador++;
				resultado = (encontradas / ((inicioIndice - fimIndice) - 1)) * -1;
				progressFakeNews.setProgress(resultado);
				resultado *= 100;
				intResultado = (int) resultado;
				labelResultado.setText(intResultado + "%");

			} catch (Exception e) {
				// não faça nada caso alguma url seja invalida
				System.out.println("URL invalido");
			}

		}
		mostrarAlerta("Resultado!",
				"Notícias encontradas: " + (int) encontradas + "| Notícias NÃO encontradas: " + (int) naoEncontradas);
		textAreaResultado.setText(textoResultado);
	}
}
