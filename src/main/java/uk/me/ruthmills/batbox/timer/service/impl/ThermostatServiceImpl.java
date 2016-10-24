package uk.me.ruthmills.batbox.timer.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import uk.me.ruthmills.batbox.timer.model.TemperatureAndHumidityBean;
import uk.me.ruthmills.batbox.timer.service.ThermostatService;

@Service
public class ThermostatServiceImpl implements ThermostatService {
	
	private static final String READ_TEMPERATURE_AND_HUMIDITY_ENDPOINT = "http://thermostat:61453/thermostat/readTemperatureAndHumidity";
	
	private RestTemplate restTemplate;
	
	@PostConstruct
	public void initialise() {
		restTemplate = new RestTemplate();
	}

	@Override
	public TemperatureAndHumidityBean readTemperatureAndHumidity() {
		return restTemplate.getForObject(READ_TEMPERATURE_AND_HUMIDITY_ENDPOINT, TemperatureAndHumidityBean.class);
	}
}
