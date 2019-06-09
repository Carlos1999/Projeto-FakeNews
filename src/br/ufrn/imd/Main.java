package br.ufrn.imd;


import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	private static Stage stage;
	private static Scene inicioScene;
	private static Scene BuscaURLScene;
	private static Scene BuscaTextoScene;
	private static Scene BuscaCompletaScene;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}
	
	@Override
	public void start(Stage primaryStage)  {
		// TODO Auto-generated method stub
		stage = primaryStage;
		primaryStage.setTitle("Sistema de Detec��o de Fake News");
		
		try {
			Parent fxmlMain = FXMLLoader.load(getClass().getResource("visao/Inicio.fxml"));
			inicioScene = new Scene(fxmlMain,600,500); 
			
			Parent fxmlBuscaUrl = FXMLLoader.load(getClass().getResource("visao/BuscaURL.fxml"));
			BuscaURLScene = new Scene(fxmlBuscaUrl,600,500); 
			
			Parent fxmlBuscaTexto = FXMLLoader.load(getClass().getResource("visao/BuscaTexto.fxml"));
			BuscaTextoScene = new Scene(fxmlBuscaTexto,600,500); 
			
			Parent fxmlBuscaCompleta = FXMLLoader.load(getClass().getResource("visao/BuscaCompleta.fxml"));
			BuscaCompletaScene = new Scene(fxmlBuscaCompleta,600,500); 
			
			
			primaryStage.setScene(inicioScene);
			primaryStage.show();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	
	public static void mudarTela(int tela) {
		switch(tela) {
			case 0:
				stage.setScene(inicioScene);
				break;
			case 1:
				stage.setScene(BuscaURLScene);
				break;
			case 2:
				stage.setScene(BuscaTextoScene);
				break;
			case 3:
				stage.setScene(BuscaCompletaScene);
				break;	
		}
	
	}

	
}
