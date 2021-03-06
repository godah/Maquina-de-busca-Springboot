package com.maquinadebusca.app.model.service;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maquinadebusca.app.model.Documento;
import com.maquinadebusca.app.model.Link;

@Service
public class ColetorService {

	@Autowired
	private DocumentoService ds;

	@Autowired
	private LinkService ls;

	@Autowired
	private HostService hostService;
	
	@Autowired
	private UtilsService utilsService;
	
	@Autowired
	private RobotsService robotsService;
	
	@Autowired
	private StopwordsService stopWordsService;

	String urlStringAnterior = null;
	List<String> sementes = new LinkedList<>();

	public List<Documento> executar() {
		List<Documento> documentos = new LinkedList<>();

		try {
			sementes = ls.obterUrlsNaoColetadas();

			while (!sementes.isEmpty()) {
				URL url = new URL(sementes.iterator().next());
				utilsService.verificaColetaConsecultiva(urlStringAnterior, url);
				if (robotsService.verificaPermissaoRobots(url)) {
					documentos.add(this.coletar(sementes.iterator().next()));
				} else {
					sementes.remove(0);
				}
				System.out.println(sementes.size() + " Sementes restantes.");
			}
		} catch (Exception e) {
			System.out.println("\n\n\n Erro ao executar o serviço de coleta! \n\n\n");
			e.printStackTrace();
		}
		return documentos;
	}

	private Documento coletar(String urlDocumento) {
		System.out.println("Iniciando coleta url [" + urlDocumento + "]");
		Documento documento = null;

		try {
			
			Document d = Jsoup.connect(urlDocumento).get();
			Elements urls = d.select("a[href]");

			documento = loadOrNewDoc(urlDocumento);
			documento.setTexto(d.html());
			documento.setVisao(stopWordsService.tratarVisao(d));
			
			trataLinksColetados(urlDocumento, documento, urls);
			documento.setLinks(utilsService.removeElementosRepetidos(documento.getLinks()));
			
			documento = ds.save(documento);
			
		} catch (Exception e) {
			System.out.println("\n\n\n Erro ao coletar a página! \n\n\n");
			e.printStackTrace();
		} finally {
			urlStringAnterior = sementes.remove(0);
			sementes.addAll(ls.obterUrlsNaoColetadas());
			sementes = utilsService.removeElementosRepetidos(sementes);
		}
		return documento;
	}

	

	private Documento loadOrNewDoc(String urlDocumento) {
		Documento documento;
		Link link;
		Documento docOld = ds.findByUrl(urlDocumento);
		if(docOld != null && docOld.getId() != null) {
			documento = docOld;
		}else {
			documento = new Documento();
			documento.setUrl(urlDocumento);
			
			link = loadOrNewLink(urlDocumento, documento);
			documento.addLink(link);
		}
		return documento;
	}

	private Link loadOrNewLink(String urlDocumento, Documento documento) {
		Link link;
		link = ls.findByUrl(urlDocumento);
		if (link == null) {
			link = new Link();
			link.setUrl(urlDocumento);
			link.setHost(hostService.obterHostPorUrl(urlDocumento));
		}
		link.setUltimaColeta(LocalDateTime.now());
		link.addDocumento(documento);
		return link;
	}

	private void trataLinksColetados(String urlDocumento, Documento documento, Elements urls) {
		List<String> urlsColetadas = new ArrayList<>();
		Link link;
		
		urlsColetadas = converteElementToList(urls, urlsColetadas);
		
		int i = 0;
		for (String url : urlsColetadas) {
			if (url.length() > 253)
				continue;
			i++;
			if ((!url.equals("")) && (url != null)) {
				link = ls.findByUrl(url);
				if (link == null) {
					link = new Link();
					link.setUrl(url);
					link.setHost(hostService.obterHostPorUrl(url));
					link.setUltimaColeta(null);
				}
				link.addDocumento(documento);
				documento.addLink(link);
			}
		}
		System.out.println("Finalizano coleta de [" + urlDocumento + "].");
		System.out.println("Número de links coletados: [" + i + "]");
		System.out.println("Tamanho da lista links: [" + documento.getLinks().size() + "]");
	}

	private List<String> converteElementToList(Elements urls, List<String> urlsColetadas) {
		for (Element url : urls) {
			urlsColetadas.add(url.attr("abs:href"));
		}
		urlsColetadas = utilsService.removeElementosRepetidos(urlsColetadas);
		return urlsColetadas;
	}

}
