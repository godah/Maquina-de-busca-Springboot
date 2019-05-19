package com.maquinadebusca.app.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maquinadebusca.app.model.TermoDocumento;
import com.maquinadebusca.app.model.repository.TermoRepository;

@Service
public class TermoService {

	@Autowired
	TermoRepository tr;

	public void deleteAllNativeQuery() {
		tr.deleteAllNativeQuery();
		
	}

	public TermoDocumento save(TermoDocumento termo) {
		return tr.save(termo);
	}

	
	
}
