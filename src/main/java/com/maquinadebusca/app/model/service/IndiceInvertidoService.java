package com.maquinadebusca.app.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maquinadebusca.app.model.repository.IndiceInvertidoRepository;

@Service
public class IndiceInvertidoService {

	@Autowired
	IndiceInvertidoRepository ir;

	public void deleteAllNativeQuery() {
		ir.deleteAllNativeQuery();
	}
	
}
