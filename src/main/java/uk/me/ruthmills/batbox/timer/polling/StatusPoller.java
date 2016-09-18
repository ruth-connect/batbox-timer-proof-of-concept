package uk.me.ruthmills.batbox.timer.polling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import uk.me.ruthmills.batbox.timer.service.ControlService;

@Component
public class StatusPoller {
	
	@Autowired
	private ControlService controlService;
	
	@Scheduled(cron = "*/10 * * * * *")
	public void checkStatus() {
		controlService.updateBoilerState();
	}
}
