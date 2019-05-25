package com.maquinadebusca.app.model.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maquinadebusca.app.model.Consulta;
import com.maquinadebusca.app.model.EntradaRanking;
import com.maquinadebusca.app.model.IndiceInvertido;
import com.maquinadebusca.app.model.TermoConsulta;

@Service
public class ProcessadorConsultaService {

	@Autowired
	TermoDocumentoService ts;

	@Autowired
	DocumentoService ds;

	@Autowired
	IndiceInvertidoService iis;

	@Autowired
	IndexadorService is;

	@Autowired
	private StopwordsService stopwordsService;

	private Map<String, EntradaRanking> mergeListasInvertidas = new Hashtable<>();

	public ProcessadorConsultaService() {
	}

	public Consulta processarConsulta(String textoConsulta) {
		Document d = Jsoup.parse(textoConsulta);
		Consulta consulta = new Consulta(textoConsulta, stopwordsService.tratarVisao(d));
		this.iniciarTermosConsulta(consulta);
		this.processarListasInvertidas(consulta);
		this.computarSimilaridade();
		consulta.setRanking(this.getRanking());
		return consulta;
	}

	public void iniciarTermosConsulta(Consulta consulta) {
		String visaoConsulta = consulta.getVisao();
		String[] termos = visaoConsulta.split(" ");
		for (String termo : termos) {
			if (!termo.equals("")) {
				int f = is.frequencia(termo, termos);
				double idf = ts.getIdf(termo);
				TermoConsulta termoConsulta = new TermoConsulta(termo, f, idf);
				consulta.adicionarTermoConsulta(termoConsulta);
			}
		}
	}

	public void processarListasInvertidas(Consulta consulta) {
		List<TermoConsulta> termosConsulta = consulta.getTermosConsulta();
		for (TermoConsulta termoConsulta : termosConsulta) {
			List<IndiceInvertido> entradasIndiceInvertido = iis.getEntradasIndiceInvertido(termoConsulta.getTexto());
			for (IndiceInvertido entradaIndiceInvertido : entradasIndiceInvertido) {
				if (this.mergeListasInvertidas.containsKey(entradaIndiceInvertido.getDocumento().getUrl())) {
					EntradaRanking entradaRanking = this.mergeListasInvertidas
							.get(entradaIndiceInvertido.getDocumento().getUrl());
					entradaRanking.adicionarProdutoPesos(termoConsulta.getPeso() * entradaIndiceInvertido.getPeso());
				} else {
					EntradaRanking entradaRanking = new EntradaRanking();
					entradaRanking.setUrl(entradaIndiceInvertido.getDocumento().getUrl());
					entradaRanking.adicionarProdutoPesos(termoConsulta.getPeso() * entradaIndiceInvertido.getPeso());
					entradaRanking.setSomaQuadradosPesosDocumento(
							entradaIndiceInvertido.getDocumento().getSomaQuadradosPesos());
					entradaRanking.setSomaQuadradosPesosConsulta(consulta.getSomaQuadradosPesos());
					this.mergeListasInvertidas.put(entradaIndiceInvertido.getDocumento().getUrl(), entradaRanking);
				}
			}
		}
	}

	public void computarSimilaridade() {
		Collection<EntradaRanking> ranking = this.mergeListasInvertidas.values();
		for (EntradaRanking entradaRanking : ranking) {
			entradaRanking.computarSimilaridade();
				
		}
	}

	public List<EntradaRanking> getRanking() {
		List<EntradaRanking> resp = new LinkedList<>();
		Collection<EntradaRanking> ranking = this.mergeListasInvertidas.values();
		for (EntradaRanking entradaRanking : ranking) {
			resp.add(entradaRanking);
		}
		return ordenarRanking(resp);
	}
	
	private List<EntradaRanking> getRankingSemiNormalizado() {
		List<EntradaRanking> rankings = getRanking();
		List<EntradaRanking> retorno = new ArrayList<>();
		for (EntradaRanking ranking : rankings) {
			EntradaRanking novo = ranking.clone();
			novo.computarSimilaridadeSemiNormalizada();
			retorno.add(novo);
		}
		return retorno;
	}

	private List<EntradaRanking> ordenarRanking(List<EntradaRanking> ranking) {
		return ranking.stream().sorted(Comparator.comparing(EntradaRanking::getSimilaridade).reversed())
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * @param tipo 1 = normalizada, 2 = seminormalizada
	 * @return
	 */
	public List<EntradaRanking>  ranking(Integer tipo) {
		if (tipo == 1)
			return getRanking();
		
		if(tipo == 2 )
			return getRankingSemiNormalizado();
		
		return new ArrayList<>();
	}
	
}