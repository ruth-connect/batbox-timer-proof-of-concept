package uk.me.ruthmills.batbox.timer.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import uk.me.ruthmills.batbox.timer.service.BoilerService;

@Service
public class BoilerServiceImpl extends BaseServiceImpl implements BoilerService {
	
	private static final String OFF_ENDPOINT = "http://boiler:61455/boiler/off";
	private static final String HOT_WATER_ONLY_ENDPOINT = "http://boiler:61455/boiler/hotWaterOnly";
	private static final String HEATING_AND_HOT_WATER_ENDPOINT = "http://boiler:61455/boiler/heatingAndHotWater";
	
	private RestTemplate restTemplate;
	
	@PostConstruct
	public void initialise() {
		restTemplate = new RestTemplate(getClientHttpRequestFactory());
	}
	
	@Override
	public void off() {
		restTemplate.execute(OFF_ENDPOINT, HttpMethod.GET, null, null);
	}

	@Override
	public void hotWaterOnly() {
		restTemplate.execute(HOT_WATER_ONLY_ENDPOINT, HttpMethod.GET, null, null);
	}

	@Override
	public void heatingAndHotWater() {
		restTemplate.execute(HEATING_AND_HOT_WATER_ENDPOINT, HttpMethod.GET, null, null);
	}
}
