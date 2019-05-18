package com.maquinadebusca.app.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maquinadebusca.app.model.IdIndiceInvertido;
import com.maquinadebusca.app.model.IndiceInvertido;

public interface IndiceInvertidoRepository extends JpaRepository<IndiceInvertido, IdIndiceInvertido> {

  @Override
  IndiceInvertido save (IndiceInvertido indiceInvertido);

}
