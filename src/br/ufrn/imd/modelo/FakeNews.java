package br.ufrn.imd.modelo;

public class FakeNews {
	private String original;
	private String processado;
	private String data;
	private String URL;
	

	public FakeNews(String original, String processado, String data, String uRL) {
		this.original = original;
		this.processado = processado;
		this.data = data;
		URL = uRL;
	}

	public String getOriginal() {
		return original;
	}

	public String getProcessado() {
		return processado;
	}
	
	public String getData() {
		return data;
	}

	public String getURL() {
		return URL;
	}

}
