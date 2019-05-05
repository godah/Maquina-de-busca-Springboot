package com.maquinadebusca.app.model.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.maquinadebusca.app.model.Link;

public interface LinkRepository extends JpaRepository<Link, Long> {

	@Override
	List<Link> findAll();

	Link findById(long id);

	Link findByUrl(String url);
	
	List<Link> findByUltimaColeta(LocalDateTime data);

	@Override
	Link save(Link link);

	@Query(value = "SELECT l FROM Link l WHERE l.ultimaColeta IS NULL ")
	List<Link> obterLinksNaoColetados();

	@Query(value = "SELECT l.url FROM Link l WHERE l.ultimaColeta IS NULL ")
	List<String> obterUrlsNaoColetadas();

	List<Link> findByUrlIgnoreCaseContaining(String url);

	@Query(value = "SELECT * FROM Link ORDER BY url", nativeQuery = true)
	List<Link> getInLexicalOrder();

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "UPDATE Link SET ultimaColeta = ?1  WHERE ultimaColeta IS NULL", nativeQuery = true)
	void atualizaUltimaColetaSementes(LocalDateTime data);

}
