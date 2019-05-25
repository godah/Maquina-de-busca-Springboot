package com.maquinadebusca.app.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maquinadebusca.app.model.IndiceInvertido;
import com.maquinadebusca.app.model.repository.IndiceInvertidoRepository;

@Service
public class IndiceInvertidoService {

	@Autowired
	IndiceInvertidoRepository ir;

	public void deleteAllNativeQuery() {
		ir.deleteAllNativeQuery();
	}

	public List<IndiceInvertido> getEntradasIndiceInvertido(String termo) {
		return ir.getEntradasIndiceInvertido(termo);
	}

}
