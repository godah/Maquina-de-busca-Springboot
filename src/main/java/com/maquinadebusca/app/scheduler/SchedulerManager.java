package com.maquinadebusca.app.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.maquinadebusca.app.model.service.ColetorService;
import com.maquinadebusca.app.model.service.IndexadorService;

@Component
public class SchedulerManager {

	@Autowired
	IndexadorService is;

	@Autowired
	ColetorService cs;

	// cron example: "0 0 6,19 * * *" = 6:00 AM and 7:00 PM every day.
	@Scheduled(cron = "0 0 0 20 * *") //todo dia 20
	public void criaIndices() {
		is.limpaCriaIndice();
	}

	@Scheduled(cron = "0 0 0 1 * *") //todo dia 01
	public void coletar() {
		cs.executar();
	}

}
