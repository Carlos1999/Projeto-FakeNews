module Module {
	exports br.ufrn.imd;
	exports br.ufrn.imd.modelo;
	exports br.ufrn.imd.controle;
	opens br.ufrn.imd.controle;

	requires org.jsoup;
	requires javafx.fxml;
	requires java.desktop;
	requires javafx.controls;
	requires javafx.web;
	requires javafx.graphics;
}