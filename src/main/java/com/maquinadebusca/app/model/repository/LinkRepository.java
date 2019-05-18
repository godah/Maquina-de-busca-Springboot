package com.maquinadebusca.app.model.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

	@Query(value = "SELECT * FROM Link", nativeQuery = true)
	public Slice<Link> getPage(Pageable pageable);

	@Query(value = "SELECT * FROM Link WHERE id between ?1 and ?2", nativeQuery = true)
	List<Link> findLinkByIdRange(Long id1, Long id2);

	@Query(value = "SELECT COUNT(*) FROM Link WHERE id between :identificador1 and  :identificador2", nativeQuery = true)
	Long countLinkByIdRange(@Param("identificador1") Long id1, @Param("identificador2") Long id2);

	@Query(value = " SELECT l.* FROM Link l "
			+ " JOIN Host h ON l.host_id = h.id "
			+ " WHERE h.url like %:url% "
			+ " AND l.ultimaColeta IS NULL ", nativeQuery = true)
	List<Link> encontrarSementePorHost(@Param("url")String host);

	@Query(value = " SELECT * FROM Link "
			+ " WHERE ultimaColeta BETWEEN ?1 AND ?2 ", nativeQuery = true)
	List<Link> contarLinkPorIntervaloDeData(Date d1, Date d2);
}
