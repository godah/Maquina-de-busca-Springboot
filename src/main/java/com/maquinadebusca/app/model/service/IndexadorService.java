package com.maquinadebusca.app.model.service;

import com.maquinadebusca.app.model.Documento;
import com.maquinadebusca.app.model.TermoDocumento;
import com.maquinadebusca.app.model.repository.DocumentoRepository;
import com.maquinadebusca.app.model.repository.IndiceInvertidoRepository;
import com.maquinadebusca.app.model.repository.TermoRepository;
import java.util.Hashtable;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IndexadorService {

	private Hashtable hashTermos;

	@Autowired
	DocumentoService ds;

	@Autowired
	TermoService ts;
	
	@Autowired
	IndiceInvertidoService is;

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
	
	//@Transactional
	public boolean criarIndice() {
		this.hashTermos = new Hashtable<>();
		List<Documento> documentos = ds.findAll();//this.getDocumentos();
		for (Documento documento : documentos) {
			documento.setFrequenciaMaxima(0L);
			documento.setSomaQuadradosPesos(0L);
			documento = ds.save(documento); //Documentos ja salvos
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
		return (Math.log(x) / Math.log(base));
	}
	
	private double calcularTf(int frequencia) {
		return 1 + log(frequencia, 2); 
	}
	
	private double calculaIdf(Integer N, Long n) {
		return log((N.doubleValue() / n.doubleValue()),2);
	}
	
	private double calculaFrequeciaNormalizada(int frequencia, double frequenciaMaxima) {
		if(frequencia == 0 || frequenciaMaxima == 0)
			return 0;
		return (frequencia/frequenciaMaxima);
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
		List<Documento> documentos = getDocumentos();
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
		Documento documento;
		List<Documento> documentos = new LinkedList<>();

		documento = new Documento();
		documento.setUrl("www.1.com.br");
		documento.setTexto("to do is to be to be is to do");
		documento.setVisao("to do is to be to be is to do");
		documentos.add(documento);

		documento = new Documento();
		documento.setUrl("www.2.com.br");
		documento.setTexto("to be or not to be i am what i am");
		documento.setVisao("to be or not to be i am what i am");
		documentos.add(documento);

		documento = new Documento();
		documento.setUrl("www.3.com.br");
		documento.setTexto("i think therefore i am do be do be do");
		documento.setVisao("i think therefore i am do be do be do");
		documentos.add(documento);
		documento = new Documento();
		documento.setUrl("www.4.com.br");
		documento.setTexto("do do do da da da let it be let it be");
		documento.setVisao("do do do da da da let it be let it be");
		documentos.add(documento);

		return documentos;
	}

	
}
