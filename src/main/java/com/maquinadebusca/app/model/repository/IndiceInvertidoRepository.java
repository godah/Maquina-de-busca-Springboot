package com.maquinadebusca.app.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.maquinadebusca.app.model.IdIndiceInvertido;
import com.maquinadebusca.app.model.IndiceInvertido;

public interface IndiceInvertidoRepository extends JpaRepository<IndiceInvertido, IdIndiceInvertido> {

	@Override
	IndiceInvertido save(IndiceInvertido indiceInvertido);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "DELETE FROM IndiceInvertido", nativeQuery = true)
	void deleteAllNativeQuery();

}
