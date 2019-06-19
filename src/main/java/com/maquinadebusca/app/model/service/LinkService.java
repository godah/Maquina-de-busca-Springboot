package com.maquinadebusca.app.model.service;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.maquinadebusca.app.model.Host;
import com.maquinadebusca.app.model.Link;
import com.maquinadebusca.app.model.LinkPage;
import com.maquinadebusca.app.model.repository.HostRepository;
import com.maquinadebusca.app.model.repository.LinkRepository;

@Service
public class LinkService {

	@Autowired
	private LinkRepository lr;
	
	@Autowired
	private HostRepository hr;

	String urlStringAnterior = null;
	List<String> sementes = new LinkedList<>();

	public Link salvarLink(Link link) {
		Link l = null;
		try {
			URL url = new URL(link.getUrl());
			Host host = hr.obterPorUrl(url.getProtocol() + "://" + url.getHost());
			if(host == null) {
				host = new Host();
				host.setUrl(url.getProtocol() + "://" + url.getHost());
				host.setCount(1L);
				hr.save(host);
			}
			link.setHost(host);
			l = lr.save(link);
		} catch (Exception e) {
			System.out.println("\n>>> Não foi possível salvar o link informado no banco de dados.\n");
			e.printStackTrace();
		}
		return l;
	}

	public List<Link> salvarLinks(List<Link> links) {
		List<Link> ls = null;
		try {
			ls = lr.saveAll(links);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

	public Link atualizarLink(Link link) {
		Link l = null;
		try {
			l = lr.save(link);
		} catch (Exception e) {
			System.out.println("\n>>> Não foi possível atualizar o link informado no banco de dados.\n");
			e.printStackTrace();
		}
		return l;
	}

	public List<Link> getLink() {
		Iterable<Link> links = lr.findAll();
		List<Link> resposta = new LinkedList<>();
		for (Link link : links) {
			resposta.add(link);
		}
		return resposta;
	}

	public Link getLink(long id) {
		Link link = lr.findById(id);
		return link;
	}

	public boolean removerLink(Long id) {
		boolean resp = false;
		try {
			lr.deleteById(id);
			resp = true;
		} catch (Exception e) {
			System.out.println("\n>>> Não foi possível remover o link informado no banco de dados.\n");
			e.printStackTrace();
		}
		return resp;
	}

	public Link removerLink(Link link) {
		try {
			lr.delete(link);
		} catch (Exception e) {
			link = null;
			System.out.println("\n>>> Não foi possível remover o link informado no banco de dados.\n");
			e.printStackTrace();
		}
		return link;
	}

	public List<Link> encontrarLinkUrl(String url) {
		return lr.findByUrlIgnoreCaseContaining(url);
	}

	public List<Link> listarEmOrdemAlfabetica() {
		return lr.getInLexicalOrder();
	}

	public List<String> obterUrlsNaoColetadas() {
		return lr.obterUrlsNaoColetadas();
	}

	public List<Link> obterLinksNaoColetados() {
		return lr.obterLinksNaoColetados();
	}

	public Link findByUrl(String url) {
		return lr.findByUrl(url);
	}

	public List<Link> atualizaUltimaColetaSementes(LocalDateTime data) {
		lr.atualizaUltimaColetaSementes(data);
		return lr.findByUltimaColeta(data);
	}

	public List<LinkPage> buscarPagina() {
		List<LinkPage> paginas = new ArrayList<>();
		Slice<Link> pagina = null;
		Pageable pageable = PageRequest.of(0, 15, Sort.by(Sort.Direction.DESC, "url"));

		while (true) {
			pagina = lr.getPage(pageable);
			int numeroDaPagina = pagina.getNumber();
			int numeroDeElementosNaPagina = pagina.getNumberOfElements();
			int tamanhoDaPagina = pagina.getSize();
			System.out.println("\n\nPágina: " + numeroDaPagina + "   Número de Elementos: " + numeroDeElementosNaPagina
					+ "   Tamaho da Página: " + tamanhoDaPagina);
			List<Link> links = pagina.getContent();
			LinkPage linkPage = new LinkPage(links);
			paginas.add(linkPage);
			links.forEach(System.out::println);
			if (!pagina.hasNext()) {
				break;
			}
			pageable = pagina.nextPageable();
		}
		return paginas;
	}

	public List<Link> buscarPagina(Integer pageFlag) {
		Slice<Link> pagina = null;
		Pageable pageable = PageRequest.of(0, 15, Sort.by(Sort.Direction.DESC, "url"));
		while (true) {
			pagina = lr.getPage(pageable);
			int numeroDaPagina = pagina.getNumber();
			int numeroDeElementosNaPagina = pagina.getNumberOfElements();
			int tamanhoDaPagina = pagina.getSize();
			System.out.println("\n\nPágina: " + numeroDaPagina + "   Número de Elementos: " + numeroDeElementosNaPagina
					+ "   Tamaho da Página: " + tamanhoDaPagina);
			List<Link> links = pagina.getContent();
			links.forEach(System.out::println);

			if (numeroDaPagina == pageFlag)
				return links;

			if (!pagina.hasNext()) {
				break;
			}
			pageable = pagina.nextPageable();
		}
		return null;
	}

	public Long contarLinkPorIntervaloDeIdentificacao(Long id1, Long id2) {
		return lr.countLinkByIdRange(id1, id2);
	}

	public List<Link> pesquisarLinkPorIntervaloDeIdentificacao(Long id1, Long id2) {
		return lr.findLinkByIdRange(id1, id2);
	}

	public List<Link> encontrarSementePorHost(String host) {
		return lr.encontrarSementePorHost(host);
	}

	public List<Link> encontrarSementesPorIntervaloDeData(LocalDateTime dt1, LocalDateTime dt2) {
			return lr.encontrarSementesPorIntervaloDeData(dt1, dt2);
	}

	public int atualizarDataUltimaColeta(String host, LocalDateTime dataUltimaColeta) {
		return lr.updateLastCrawlingDate(dataUltimaColeta, host);
	}
}
