package com.maquinadebusca.app.util;

import java.util.LinkedList;
import java.util.List;

import com.maquinadebusca.app.model.Documento;

public class MockupTestes {

	public List<Documento> getDocumentosExercicio1() {
		Documento documento;
		List<Documento> documentos = new LinkedList<>();

		documento = new Documento();
		documento.setUrl("www.exercicio1d1.com.br");
		documento.setTexto("Casa carro Casa carro porta");
		documento.setVisao("Casa carro Casa carro porta");
		documentos.add(documento);

		documento = new Documento();
		documento.setUrl("www.exercicio1d2.com.br");
		documento.setTexto("Porta carro Carro");
		documento.setVisao("Porta carro Carro");
		documentos.add(documento);

		documento = new Documento();
		documento.setUrl("www.exercicio1d3.com.br");
		documento.setTexto("Porta casa Casa porta Casa");
		documento.setVisao("Porta casa Casa porta Casa");
		documentos.add(documento);
		documento = new Documento();
		documento.setUrl("www.exercicio1d4.com.br");
		documento.setTexto("Porta jardim");
		documento.setVisao("Porta jardim");
		documentos.add(documento);

		return documentos;
	}

	public List<Documento> getDocumentosExercicio2() {
		Documento documento;
		List<Documento> documentos = new LinkedList<>();

		documento = new Documento();
		documento.setUrl("www.exercicio2d1.com.br");
		documento.setTexto("ontem hoje amanhã ontem sempre ontem sempre amanhã ontem hoje");
		documento.setVisao("ontem hoje amanhã ontem sempre ontem sempre amanhã ontem hoje");
		documentos.add(documento);

		documento = new Documento();
		documento.setUrl("www.exercicio2d2.com.br");
		documento.setTexto("ontem sempre ocasionalmente nunca ontem sempre");
		documento.setVisao("ontem sempre ocasionalmente nunca ontem sempre");
		documentos.add(documento);

		documento = new Documento();
		documento.setUrl("www.exercicio2d3.com.br");
		documento.setTexto("hoje sempre hoje sempre hoje");
		documento.setVisao("hoje sempre hoje sempre hoje");
		documentos.add(documento);
		documento = new Documento();
		documento.setUrl("www.exercicio2d4.com.br");
		documento.setTexto("sempre hoje hoje hoje sempre");
		documento.setVisao("sempre hoje hoje hoje sempre");
		documentos.add(documento);

		return documentos;
	}

	public List<Documento> getDocumentosExercicio3() {
		Documento documento;
		List<Documento> documentos = new LinkedList<>();

		documento = new Documento();
		documento.setUrl("www.exercicio3d1.com.br");
		documento.setTexto("to do is to be to be is to do");
		documento.setVisao("to do is to be to be is to do");
		documentos.add(documento);

		documento = new Documento();
		documento.setUrl("www.exercicio3d2.com.br");
		documento.setTexto("to be or not to be i am what i am");
		documento.setVisao("to be or not to be i am what i am");
		documentos.add(documento);

		documento = new Documento();
		documento.setUrl("www.exercicio3d3.com.br");
		documento.setTexto("i think therefore i am do be do be do");
		documento.setVisao("i think therefore i am do be do be do");
		documentos.add(documento);
		documento = new Documento();
		documento.setUrl("www.exercicio3d4.com.br");
		documento.setTexto("do do do da da da let it be let it be");
		documento.setVisao("do do do da da da let it be let it be");
		documentos.add(documento);

		return documentos;
	}

}
