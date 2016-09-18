package uk.me.ruthmills.batbox.timer.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.me.ruthmills.batbox.timer.model.TemperatureAndHumidityBean;
import uk.me.ruthmills.batbox.timer.service.BoilerService;
import uk.me.ruthmills.batbox.timer.service.ControlService;
import uk.me.ruthmills.batbox.timer.service.ThermostatService;

@Service
public class ControlServiceImpl implements ControlService {
	
	private static final Logger LOGGER = Logger.getLogger(ControlServiceImpl.class);

	private static final double HEATING_ON_BELOW_THIS_TEMPERATURE = 26.0d;
	
	@Autowired
	private BoilerService boilerService;
	
	@Autowired
	private ThermostatService thermostatService;
	
	@Override
	public void updateBoilerState() {
		LocalDateTime now = LocalDateTime.now();
		LOGGER.info("");
		LOGGER.info("Checking status. Date and time is: " + now);
		
		BigDecimal temperature = null;
		BigDecimal humidity = null;
		
		try {
			TemperatureAndHumidityBean temperatureAndHumidity = thermostatService.readTemperatureAndHumidity();
			temperature = temperatureAndHumidity.getTemperature();
			humidity = temperatureAndHumidity.getHumidity();
			LOGGER.info("Temperature: " + temperature + " C, Humidity: " + humidity + " %");
		} catch (Exception ex) {
			LOGGER.error("Thermostat is down. Cannot read temperature");
		}
		
		boolean hotWaterOn = false;
		int second = now.getSecond();
		if (second < 30) {
			LOGGER.info("Hot water is on because we are before 30 seconds");
			hotWaterOn = true;
		} else {
			LOGGER.info("Hot water is off because we are after 30 seconds");
		}

		boolean heatingOn = false;
		if (temperature != null) {
			if (temperature.doubleValue() < HEATING_ON_BELOW_THIS_TEMPERATURE) {
				LOGGER.info("Heating is on because temperature is less than " + HEATING_ON_BELOW_THIS_TEMPERATURE + " C");
				heatingOn = true;
			} else {
				LOGGER.info("Heating is off because temperature is " + HEATING_ON_BELOW_THIS_TEMPERATURE + " C or greater");
			}
		}
		
		if (heatingOn) {
			LOGGER.info("TURNING BOTH HEATING AND HOT WATER ON");
			boilerService.heatingAndHotWater();
		} else if (hotWaterOn) {
			LOGGER.info("TURNING HOT WATER ON AND HEATING OFF");
			boilerService.hotWaterOnly();
		} else {
			LOGGER.info("TURNING BOTH HEATING AND HOT WATER OFF");
			boilerService.off();
		}
	}
}
