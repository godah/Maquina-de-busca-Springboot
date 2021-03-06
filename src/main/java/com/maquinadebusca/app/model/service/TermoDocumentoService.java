package com.maquinadebusca.app.model.service;

import com.maquinadebusca.app.model.TermoDocumento;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.maquinadebusca.app.model.repository.TermoDocumentoRepository;

@Service
public class TermoDocumentoService {

	@Autowired
	TermoDocumentoRepository tr;

	public TermoDocumentoService() {
	}

	public TermoDocumento save(TermoDocumento termoDocumento) {
		return tr.save(termoDocumento);
	}

	public double getIdf(String termoDocumento) {
		try {
			return Double.parseDouble(tr.getIdf(termoDocumento));
		}catch (Exception e) {
			return 0.0;
		}
	}

	public void deleteAllNativeQuery() {
		tr.deleteAllNativeQuery();
	}

}
