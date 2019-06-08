package br.ufrn.imd.controle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import br.ufrn.imd.modelo.BuscaFakeNews;
import br.ufrn.imd.modelo.Leitura;
import br.ufrn.imd.modelo.WebScraping;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PrincipalControler implements Initializable {

	Leitura l ;
	boolean leituraEfetuada;
	@FXML
	private TextField minimoPalavras;

	 @FXML
	 private TextField TextFieldURL;

	 @FXML
	 private TextArea TextAreaTexto;
	 
	 @FXML
	 private Label LidoComSucesso;
	 
	 @FXML
	 private ProgressIndicator progresso;

	 @FXML
	 private TextField inicioBusca;

	 @FXML
	 private TextField fimBusca;
	 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		l = new Leitura();
		leituraEfetuada = false;
	}
	
	
	@FXML
	private void botaoLerArquivo(ActionEvent event) {
		int valorInteiro=-5;
		boolean valorValido=false;
		
			try {
				valorInteiro =Integer.parseInt(minimoPalavras.getText());
				valorValido=true;
			}catch (NumberFormatException e){
				mostrarAlerta("Valor informado em: Mínimo de palavras INVALIDO!","Informe um valor entre 0 e 10 ");
				LidoComSucesso.setText("");
				leituraEfetuada = false;
				return;
			}
	
		
		if(valorInteiro<0||valorInteiro>10) {
			mostrarAlerta("Valor informado em: Mínimo de palavras INVALIDO!","Informe um valor entre 0 e 10 ");
			LidoComSucesso.setText("");
			leituraEfetuada = false;
		}else {
			l.lerArquivo(valorInteiro);
			LidoComSucesso.setText("Lido com Sucesso");
			leituraEfetuada = true;
			System.out.println(valorInteiro);
		}
	}
	
	@FXML
	public void botaoInserirUrl() {
		if(!leituraEfetuada) {
			mostrarAlerta("Erro", "Atenção, Primeiro efetue a leitura do Arquivo acima!");
			return;
		}
		WebScraping web = new WebScraping(TextFieldURL.getText());
		int porcentagemFakeNews = web.buscar(l);
		if(porcentagemFakeNews == 100) {
			mostrarAlerta("Encontrada", "Atenção, o site contém uma Fake News com 100% de compatibilidade com notícia armazenada");
		}else if(porcentagemFakeNews>=85) {
			mostrarAlerta("Encontrada", "Atenção, o site contém uma Fake News com "+porcentagemFakeNews+"% de compatibilidade com notícia armazenada");
		}else {
			mostrarAlerta("Não encontrada", "Fake news não se encontra no banco de dados");
		}
		
								
	}
	
	@FXML
	public void botaoInserirTexto() {
		if(!leituraEfetuada) {
			mostrarAlerta("Erro", "Atenção, Primeiro efetue a leitura do Arquivo acima!");
			return;
		}
		int porcentagemFakeNews = BuscaFakeNews.buscar(l.removerAcentos(TextAreaTexto.getText()), l);
		if(porcentagemFakeNews == 100) {
			mostrarAlerta("Encontrada", "Atenção, o site contém uma Fake News com 100% de compatibilidade com notícia armazenada");
		}else if(porcentagemFakeNews>=85) {
			mostrarAlerta("Encontrada", "Atenção, o site contém uma Fake News com "+porcentagemFakeNews+"% de compatibilidade com notícia armazenada");
		}else {
			mostrarAlerta("Não encontrada", "Fake news não se encontra no banco de dados");
		}
	}
	public void mostrarAlerta(String titulo, String mensagem) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titulo);
		alert.setContentText(mensagem);
		alert.showAndWait();
	}
	
	@FXML
    void buscarUrlsArmazenadas(ActionEvent event) {
		int inicioIndice;
		int fimIndice;
		try {
			inicioIndice =Integer.parseInt(inicioBusca.getText());
		}catch (NumberFormatException e){
			mostrarAlerta("Valor informado em: Início da busca INVALIDO!","Informe um valor entre 0 e 1000 ");
			return;
		}
		
		try {
			fimIndice =Integer.parseInt(fimBusca.getText());
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
			
			if(web.buscar(l)>=85) {
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
			progresso.setProgress(contador/(inicioIndice-fimIndice));
		}
		mostrarAlerta("Resultado!","Notícias encontradas: "+encontradas+"| Notícias NÃO encontradas: "+naoEncontradas);
    }
}
