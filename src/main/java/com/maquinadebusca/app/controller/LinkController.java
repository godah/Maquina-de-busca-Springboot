package com.maquinadebusca.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.maquinadebusca.app.mensagem.Mensagem;
import com.maquinadebusca.app.model.Link;
import com.maquinadebusca.app.model.UrlsSementes;
import com.maquinadebusca.app.model.service.LinkService;

@SuppressWarnings({ "rawtypes", "unchecked" })
@RestController
@RequestMapping("/link") // URL: http://localhost:8080/link
public class LinkController {

	@Autowired
	LinkService ls;

	// URL: http://localhost:8080/link
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity listarLink() {
		return new ResponseEntity(ls.getLink(), HttpStatus.OK);
	}

	// Request for: http://localhost:8080/link/{id}
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity listarLink(@PathVariable(value = "id") Long id) {
		return new ResponseEntity(ls.getLink(id), HttpStatus.OK);
	}

	// Request for: http://localhost:8080/link
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	public ResponseEntity inserirLink(@RequestBody @Valid Link link, BindingResult resultado) {
		ResponseEntity resposta = null;
		if (resultado.hasErrors()) {
			resposta = new ResponseEntity(
					new Mensagem("erro", "os dados sobre o link  não foram informados corretamente"),
					HttpStatus.BAD_REQUEST);
		} else {
			link = ls.salvarLink(link);
			if ((link != null) && (link.getId() > 0)) {
				resposta = new ResponseEntity(link, HttpStatus.OK);
			} else {
				resposta = new ResponseEntity(
						new Mensagem("erro", "não foi possível inserir o link informado no banco de dados"),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return resposta;
	}

	// Request for: http://localhost:8080/link
	@PutMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	public ResponseEntity atualizarLink(@RequestBody @Valid Link link, BindingResult resultado) {
		ResponseEntity resposta = null;
		if (resultado.hasErrors()) {
			resposta = new ResponseEntity(
					new Mensagem("erro", "os dados sobre o link  não foram informados corretamente"),
					HttpStatus.BAD_REQUEST);
		} else {
			link = ls.atualizarLink(link);
			if ((link != null) && (link.getId() > 0)) {
				resposta = new ResponseEntity(link, HttpStatus.OK);
			} else {
				resposta = new ResponseEntity(
						new Mensagem("erro", "não foi possível atualizar o link informado no banco de dados"),
						HttpStatus.NOT_ACCEPTABLE);
			}
		}
		return resposta;
	}

	// Request for: http://localhost:8080/link/atualizaUltimaColetaSementes
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(value = "/atualizaUltimaColetaSementes", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	public ResponseEntity atualizaUltimaColetaSementes(@RequestBody Link link) {
		ResponseEntity resposta = null;
		if (link == null || link.getUltimaColeta() == null) {
			resposta = new ResponseEntity(new Mensagem("erro", "os dados não foram informados corretamente"),
					HttpStatus.BAD_REQUEST);
		} else {
			List<Link> sementes = ls.atualizaUltimaColetaSementes(link.getUltimaColeta());
			if ((sementes != null) && (!sementes.isEmpty())) {
				resposta = new ResponseEntity(sementes, HttpStatus.OK);
			} else {
				resposta = new ResponseEntity(
						new Mensagem("erro", "não foi possível atualizar as sementes no banco de dados"),
						HttpStatus.NOT_ACCEPTABLE);
			}
		}
		return resposta;
	}

	// Request for: http://localhost:8080/link
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity removerLink(@RequestBody @Valid Link link, BindingResult resultado) {
		ResponseEntity resposta = null;
		if (resultado.hasErrors()) {
			resposta = new ResponseEntity(
					new Mensagem("erro", "os dados sobre o link  não foram informados corretamente"),
					HttpStatus.BAD_REQUEST);
		} else {
			link = ls.removerLink(link);
			if (link != null) {
				resposta = new ResponseEntity(new Mensagem("sucesso", "link removido com suceso"), HttpStatus.OK);
			} else {
				resposta = new ResponseEntity(
						new Mensagem("erro", "não foi possível remover o link informado no banco de dados"),
						HttpStatus.NOT_ACCEPTABLE);
			}
		}
		return resposta;
	}

	// Request for: http://localhost:8080/link
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity removerLink(@PathVariable(value = "id") Long id) {
		ResponseEntity resposta = null;
		if ((id != null) && (id <= 0)) {
			resposta = new ResponseEntity(
					new Mensagem("erro", "os dados sobre o link  não foram informados corretamente"),
					HttpStatus.BAD_REQUEST);
		} else {
			boolean resp = ls.removerLink(id);
			if (resp == true) {
				resposta = new ResponseEntity(new Mensagem("sucesso", "link removido com suceso"), HttpStatus.OK);
			} else {
				resposta = new ResponseEntity(
						new Mensagem("erro", "não foi possível remover o link informado no banco de dados"),
						HttpStatus.NOT_ACCEPTABLE);
			}
		}
		return resposta;
	}

	// Request for: http://localhost:8080/link/encontrar/{url}
	@GetMapping(value = "/encontrar/{url}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity encontrarLink(@PathVariable(value = "url") String url) {
		return new ResponseEntity(ls.encontrarLinkUrl(url), HttpStatus.OK);
	}

	// Request for: http://localhost:8080/link/ordemAlfabetica
	@GetMapping(value = "/ordemAlfabetica", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity listarEmOrdemAlfabetica() {
		return new ResponseEntity(ls.listarEmOrdemAlfabetica(), HttpStatus.OK);
	}

	// URL: http://localhost:8080/link/sementes
	@GetMapping(value = "/sementes", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity listarSementes() {
		return new ResponseEntity(ls.obterLinksNaoColetados(), HttpStatus.OK);
	}

	// Request for: http://localhost:8080/link/pagina
	@GetMapping(value = "/pagina", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity listarPagina() {
		return new ResponseEntity(ls.buscarPagina(), HttpStatus.OK);
	}

	// Request for: http://localhost:8080/link/pagina/{pageFlag}
	@GetMapping(value = "/pagina/{pageFlag}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity listarPagina(@PathVariable(value = "pageFlag") Integer pageFlag) {
		return new ResponseEntity(ls.buscarPagina(pageFlag), HttpStatus.OK);
	}

	// Request for: http://localhost:8080/link/intervalo/{id1}/{id2}
	@GetMapping(value = "/intervalo/{id1}/{id2}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity encontrarLinkPorIntervaloDeId(@PathVariable(value = "id1") Long id1,
			@PathVariable(value = "id2") Long id2) {
		return new ResponseEntity(ls.pesquisarLinkPorIntervaloDeIdentificacao(id1, id2), HttpStatus.OK);
	}

	// Request for: http://localhost:8080/link/intervalo/contar/{id1}/{id2}
	@GetMapping(value = "/intervalo/contar/{id1}/{id2}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity contarLinkPorIntervaloDeId(@PathVariable(value = "id1") Long id1,
			@PathVariable(value = "id2") Long id2) {
		return new ResponseEntity(ls.contarLinkPorIntervaloDeIdentificacao(id1, id2), HttpStatus.OK);
	}

	// Request for: http://localhost:8080/coletor/urlsSementes
	@PostMapping(value = "/urlsSementes", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity inserirUrlsSementes(@RequestBody UrlsSementes urlsSementes) {
		boolean erro = false;
		ResponseEntity resposta = null;

		for (String url : urlsSementes.getUrls()) {
			Link link = new Link();
			link.setUrl(url);
			link = ls.salvarLink(link);
			if ((link == null) || (link.getId() <= 0)) {
				erro = true;
				break;
			}
		}
		if (erro == false) {
			resposta = new ResponseEntity(
					new Mensagem("sucesso", "as urls sementes informadas foram inseridas no banco de dados"),
					HttpStatus.OK);
		} else {
			resposta = new ResponseEntity(
					new Mensagem("erro", "não foi possível inserir as urls sementes informadas no banco de dados"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return resposta;
	}

	// Request for: http://localhost:8080/link/encontrarSemente/{host}
	@GetMapping(value = "/encontrarSemente/{host}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity encontrarSementePorHost(@PathVariable(value = "host") String host) {
		return new ResponseEntity(ls.encontrarSementePorHost(host), HttpStatus.OK);
	}

	// Request for: http://localhost:8080/link/intervalo/datas/{dt1}/{dt2}
	@GetMapping(value = "/intervalo/datas/{dt1}/{dt2}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity contarLinkPorIntervaloDeId(@PathVariable(value = "dt1") String dt1, @PathVariable(value = "dt2") String dt2) {
		return new ResponseEntity(ls.contarLinkPorIntervaloDeData(dt1, dt2), HttpStatus.OK);
	}

}
