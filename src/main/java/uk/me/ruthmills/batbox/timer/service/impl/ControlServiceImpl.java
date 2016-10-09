package uk.me.ruthmills.batbox.timer.service.impl;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.me.ruthmills.batbox.timer.model.BoilerStatusBean;
import uk.me.ruthmills.batbox.timer.model.TemperatureAndHumidityBean;
import uk.me.ruthmills.batbox.timer.service.BoilerService;
import uk.me.ruthmills.batbox.timer.service.ControlService;
import uk.me.ruthmills.batbox.timer.service.ThermostatService;

@Service
public class ControlServiceImpl implements ControlService {
	
	private static final Logger LOGGER = Logger.getLogger(ControlServiceImpl.class);
	
	public enum Setting { ON, TIMER, OFF };
	
	private Setting hotWaterSetting = Setting.TIMER;
	private Setting heatingSetting = Setting.ON;

	private BigDecimal targetTemperature = new BigDecimal("20.0");
	
	@Autowired
	private BoilerService boilerService;
	
	@Autowired
	private ThermostatService thermostatService;
	
	private BigDecimal temperature;
	private BigDecimal humidity;
	
	private boolean timerOn;
	private boolean hotWaterOn;
	private boolean heatingOn;
	
	private boolean heatingHysteresisOn;
	
	@Override
	public void checkBoilerStatus() {
		LocalDateTime now = checkCurrentTime();				
		readTemperatureAndHumidity();
		updateTimerStatus(now);
		updateBoilerState();
	}
	
	@Override
	public BoilerStatusBean reportBoilerStatus() {
		return new BoilerStatusBean(hotWaterSetting, heatingSetting, targetTemperature, temperature, humidity, timerOn, hotWaterOn | heatingOn, heatingOn);
	}
	
	@Override
	public void turnHotWaterOff() {
		hotWaterSetting = Setting.OFF;
		updateBoilerState();
	}
	
	@Override
	public void turnHotWaterToTimer() {
		hotWaterSetting = Setting.TIMER;
		updateBoilerState();
	}
	
	@Override
	public void turnHotWaterOn() {
		hotWaterSetting = Setting.ON;
		updateBoilerState();
	}
	
	@Override
	public void turnHeatingOff() {
		heatingSetting = Setting.OFF;
		updateBoilerState();
	}
	
	@Override
	public void turnHeatingToTimer() {
		heatingSetting = Setting.TIMER;
		updateBoilerState();
	}
	
	@Override
	public void turnHeatingOn() {
		heatingSetting = Setting.ON;
		updateBoilerState();
	}
	
	@Override
	public void setTargetTemperature(String targetTemperature) {
		this.targetTemperature = new BigDecimal(targetTemperature);
		updateBoilerState();
	}

	private LocalDateTime checkCurrentTime() {
		LocalDateTime now = LocalDateTime.now();
		LOGGER.info("");
		LOGGER.info("Checking status. Date and time is: " + now);
		return now;
	}

	private void readTemperatureAndHumidity() {
		try {
			TemperatureAndHumidityBean temperatureAndHumidity = thermostatService.readTemperatureAndHumidity();
			temperature = temperatureAndHumidity.getTemperature();
			humidity = temperatureAndHumidity.getHumidity();
			LOGGER.info("Temperature: " + temperature + " C, Humidity: " + humidity + " %");
		} catch (Exception ex) {
			temperature = null;
			humidity = null;
			LOGGER.error("Thermostat is down. Cannot read temperature");
		}
	}

	private void updateBoilerState() {
		updateHotWaterStatus();
		updateHeatingStatus(temperature);		

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
	
	private boolean updateTimerStatus(LocalDateTime now) {
		int hour = now.getHour();
		DayOfWeek day = now.getDayOfWeek();
		if (hour >= 4 && hour < 8 && !day.equals(DayOfWeek.SATURDAY) && !day.equals(DayOfWeek.SUNDAY)) {
			LOGGER.info("Timer is on because we are between 4 am and 8 am on a weekday");
			timerOn = true;
		} else if (hour >= 6 && hour < 10 && (day.equals(DayOfWeek.SATURDAY) || day.equals(DayOfWeek.SUNDAY))) {
			LOGGER.info("Timer is on because we are between 6 am and 10 am on a weekend");
			timerOn = true;
		} else if (hour >= 17 && hour < 20) {
			LOGGER.info("Timer is on because we are between 5 pm and 8 pm");
			timerOn = false;
		} else {
			LOGGER.info("Timer is off");
			timerOn = false;
		}
		return timerOn;
	}
	
	private void updateHotWaterStatus() {
		if (hotWaterSetting == Setting.ON) {
			hotWaterOn = true;
		} else if (hotWaterSetting == Setting.TIMER) {
			hotWaterOn = timerOn;
		} else {
			hotWaterOn = false;
		}
	}

	private void updateHeatingStatus(BigDecimal temperature) {
		if (isTemperatureTooLow()) {
			if (heatingSetting == Setting.ON) {
				heatingOn = true;
			} else if (heatingSetting == Setting.TIMER) {
				heatingOn = timerOn;
			} else {
				heatingOn = false;
			}
		} else {
			LOGGER.info("Heating is off because temperature is NOT too low");
			heatingOn = false;
		}
	}

	private boolean isTemperatureTooLow() {
		if (temperature != null && temperature.doubleValue() < targetTemperature.doubleValue() - 0.5d && heatingHysteresisOn) {
			LOGGER.info("Temperature is too low because current temperature: " + temperature + " C is more than 0.5C less than target temperature: " + targetTemperature + " C");
			heatingHysteresisOn = false;
			return true;
		} else if (temperature != null && temperature.doubleValue() < targetTemperature.doubleValue() && !heatingHysteresisOn) {
			LOGGER.info("Temperature is too low because current temperature: " + temperature + " C is less than target temperature: " + targetTemperature + " C");
			return true;
		} else {
			LOGGER.info("Temperature is NOT too low because current temperature: " + temperature + " C is NOT less than target temperature: " + targetTemperature + " C");
			heatingHysteresisOn = true;
			return false;
		}
	}
}

