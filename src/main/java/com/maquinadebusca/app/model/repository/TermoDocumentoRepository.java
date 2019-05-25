package com.maquinadebusca.app.model.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.maquinadebusca.app.model.TermoDocumento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TermoDocumentoRepository extends JpaRepository<TermoDocumento, Long> {

	@Override
	List<TermoDocumento> findAll();

	TermoDocumento findById(long id);

	@SuppressWarnings("unchecked")
	@Override
	TermoDocumento save(TermoDocumento termo);

	@Query(value = 
			" select log (2, (select count(distinct d.id) from Documento d)/ t.n) " + 
			" from TermoDocumento t " + 
			" where t.texto ='to' ", 
			nativeQuery = true)
	public double getIdf(@Param("termo") String termo);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "DELETE FROM TermoDocumento", nativeQuery = true)
	void deleteAllNativeQuery();

}
