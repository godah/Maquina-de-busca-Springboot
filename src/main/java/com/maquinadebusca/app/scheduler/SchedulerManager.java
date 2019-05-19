package com.maquinadebusca.app.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.maquinadebusca.app.model.service.IndexadorService;

@Component
public class SchedulerManager {

	@Autowired
	IndexadorService is;
	
	//cron example: "0 0 6,19 * * *" = 6:00 AM and 7:00 PM every day.
	@Scheduled(cron="0 0 0 * * *")
	public void criaIndices() {
		is.limpaCriaIndice();
	}

//	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//	@Scheduled(fixedRate=5000, initialDelay=5000)
//	public void reportCurrentTime() {
//		System.out.println("==> reportCurrentTime The time is now " + dateFormat.format(new Date()));
//	}
}
