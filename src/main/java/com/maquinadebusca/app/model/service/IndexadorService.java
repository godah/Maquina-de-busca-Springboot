package com.maquinadebusca.app.model.service;

import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maquinadebusca.app.model.Documento;
import com.maquinadebusca.app.model.TermoDocumento;

@Service
public class IndexadorService {

	private Hashtable hashTermos;

	@Autowired
	DocumentoService ds;

	@Autowired
	TermoService ts;
	
	@Autowired
	IndiceInvertidoService is;

	List<Documento> documentos;
	
	public IndexadorService() {
		this.hashTermos = new Hashtable<>();
	}

	@Transactional
	public boolean limpaCriaIndice() {
		this.hashTermos = new Hashtable<>();
		is.deleteAllNativeQuery();
		ts.deleteAllNativeQuery();
		return criarIndice();
	}
	
	public boolean criarIndice() {
		this.hashTermos = new Hashtable<>();
		documentos = ds.findAll();
		//MockupTestes mock = new MockupTestes();
		//documentos = mock.getDocumentosExercicio1();
		for (Documento documento : documentos) {
			documento.setFrequenciaMaxima(0L);
			documento.setSomaQuadradosPesos(0L);
			documento = ds.save(documento);
			this.indexar(documento, documentos.size());
		}
		return true;
	}

	public void indexar(Documento documento, int N) {
		String visaoDocumento = documento.getVisao();
		String[] termos = visaoDocumento.split(" ");
		for (String termo : termos) {
			if (!termo.equals("")) {
				TermoDocumento termoDocumento = this.getTermo(termo);
				int f = this.frequencia(termoDocumento.getTexto(), termos);
				if (f > documento.getFrequenciaMaxima()) {
					documento.setFrequenciaMaxima(f);
				}
				double peso = calcularPeso(N, termoDocumento.getN(), f);
				documento.adicionarPeso(peso);
				termoDocumento.inserirEntradaIndiceInvertido(documento, f, calculaFrequeciaNormalizada(f, documento.getFrequenciaMaxima()), peso);
			}
		}
	}

	private double calcularPeso(int N, Long n, int frequencia) {
		double tf = calcularTf(frequencia);
		double idf = calculaIdf(N, n);
		return tf * idf;
	}

	private double log(double x, int base){
		double a = Math.log(x);
		double b = Math.log(base);
		if (a == 0 || b == 0)
			return 0;
		return (a / b);
	}
	
	private double calcularTf(int frequencia) {
		return 1 + log(frequencia, 2); 
	}
	
	private double calculaIdf(Integer N, Long n) {
		if(N == 0 || n == 0L)
			return 0;
		return log((N.doubleValue() / n.doubleValue()),2);
	}
	
	private double calculaFrequeciaNormalizada(int frequencia, double frequenciaMaxima) {
		if(frequencia == 0 || frequenciaMaxima == 0)
			return 0;
		return (frequencia / frequenciaMaxima);
	}

	public TermoDocumento getTermo(String texto) {
		TermoDocumento termo;

		if (this.hashTermos.containsKey(texto)) {
			termo = (TermoDocumento) this.hashTermos.get(texto);
		} else {
			termo = new TermoDocumento();
			termo.setTexto(texto);
			termo.setN(quantDocPorTermo(texto));
			termo = ts.save(termo);
			this.hashTermos.put(texto, termo);
		}

		return termo;
	}

	private Long quantDocPorTermo(String texto) {
		Long n = 0L;
		for (Documento documento : documentos) {
			if(documento.getVisao().contains(texto.toLowerCase()))
				n++;
		}
		return n;
	}

	public int frequencia(String termo, String[] termos) {
		int i, contador = 0;

		for (i = 0; i < termos.length; i++) {
			if (!termos[i].equals("")) {
				if (termos[i].equalsIgnoreCase(termo)) {
					contador++;
					termos[i] = "";
				}
			}
		}

		return contador;
	}

	public List<Documento> getDocumentos() {
		return documentos;
	}
	
}
