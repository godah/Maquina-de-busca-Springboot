package com.maquinadebusca.app.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class EntradaRanking implements Serializable {

	private static final long serialVersionUID = 1044815852298922919L;
	
	private String url;
	private List<Double> produtoPesos = new LinkedList<>();
	private double somaQuadradosPesosDocumento;
	private double somaQuadradosPesosConsulta;
	private double similaridade;

	public EntradaRanking() {
	}

	public EntradaRanking(String url, double produtoPesos, double somaQuadradosPesosDocumento,
			double somaQuadradosPesosConsulta) {
		this.url = url;
		this.produtoPesos.add(produtoPesos);
		this.somaQuadradosPesosDocumento = somaQuadradosPesosDocumento;
		this.somaQuadradosPesosConsulta = somaQuadradosPesosConsulta;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Double> getProdutoPesos() {
		return produtoPesos;
	}

	public void setProdutoPesos(List<Double> produtoPesos) {
		this.produtoPesos = produtoPesos;
	}

	public double getSomaQuadradosPesosDocumento() {
		return somaQuadradosPesosDocumento;
	}

	public void setSomaQuadradosPesosDocumento(double somaQuadradosPesosDocumento) {
		this.somaQuadradosPesosDocumento = somaQuadradosPesosDocumento;
	}

	public double getSomaQuadradosPesosConsulta() {
		return somaQuadradosPesosConsulta;
	}

	public void setSomaQuadradosPesosConsulta(double somaQuadradosPesosConsulta) {
		this.somaQuadradosPesosConsulta = somaQuadradosPesosConsulta;
	}

	public double getSimilaridade() {
		return similaridade;
	}

	public void setSimilaridade(double similaridade) {
		this.similaridade = similaridade;
	}

	public void adicionarProdutoPesos(double produtoPesos) {
		this.produtoPesos.add(produtoPesos);
	}

	public void computarSimilaridade() {
		int i;
		double numerador = 0, denominador;

		if ((this.somaQuadradosPesosDocumento > 0) && (this.somaQuadradosPesosConsulta > 0)) {
			for (i = 0; i < this.produtoPesos.size(); i++) {
				numerador += this.produtoPesos.get(i);
			}
			denominador = Math.sqrt(this.somaQuadradosPesosDocumento) * Math.sqrt(this.somaQuadradosPesosConsulta);

			this.similaridade = numerador / denominador;
		} else {
			this.similaridade = 0;
		}
	}

	public void computarSimilaridadeSemiNormalizada() {
		int i;
		double numerador = 0, denominador;

		if ((this.somaQuadradosPesosDocumento > 0) && (this.somaQuadradosPesosConsulta > 0)) {
			for (i = 0; i < this.produtoPesos.size(); i++) {
				numerador += this.produtoPesos.get(i);
			}
			denominador = Math.sqrt(this.somaQuadradosPesosDocumento);

			this.similaridade = numerador / denominador;
		} else {
			this.similaridade = 0;
		}
	}

	public EntradaRanking clone() {
		EntradaRanking retorno = new EntradaRanking();
		retorno.setProdutoPesos(this.produtoPesos);
		retorno.setSimilaridade(this.similaridade);
		retorno.setSomaQuadradosPesosConsulta(this.somaQuadradosPesosConsulta);
		retorno.setSomaQuadradosPesosDocumento(this.somaQuadradosPesosDocumento);
		retorno.setUrl(this.url);
		return retorno;
	}

}
