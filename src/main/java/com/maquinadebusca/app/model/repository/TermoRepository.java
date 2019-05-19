package com.maquinadebusca.app.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.maquinadebusca.app.model.TermoDocumento;

public interface TermoRepository extends JpaRepository<TermoDocumento, Long> {

	@Override
	List<TermoDocumento> findAll();

	TermoDocumento findById(long id);

	@Override
	TermoDocumento save(TermoDocumento termo);
	
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value="DELETE FROM TermoDocumento", nativeQuery=true)
	void deleteAllNativeQuery();

}
