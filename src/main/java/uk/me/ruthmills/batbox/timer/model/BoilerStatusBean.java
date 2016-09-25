package uk.me.ruthmills.batbox.timer.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class BoilerStatusBean implements Serializable {
	private static final long serialVersionUID = -8401098055741018600L;
	
	private String temperature;
	private String humidity;
	private boolean hotWaterOn;
	private boolean heatingOn;
	
	public BoilerStatusBean(BigDecimal temperature, BigDecimal humidity, boolean hotWaterOn, boolean heatingOn) {
		this.setTemperature(temperature);
		this.setHumidity(humidity);
		this.setHotWaterOn(hotWaterOn);
		this.setHeatingOn(heatingOn);
	}
	
	public BoilerStatusBean() {
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(BigDecimal temperature) {
		if (temperature != null) {
			this.temperature = temperature.setScale(1, BigDecimal.ROUND_DOWN).toString();
		} else {
			this.temperature = "";
		}
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(BigDecimal humidity) {
		if (humidity != null) {
			this.humidity = humidity.setScale(1, BigDecimal.ROUND_DOWN).toString();
		} else {
			this.humidity = "";
		}
	}

	public boolean isHotWaterOn() {
		return hotWaterOn;
	}

	public void setHotWaterOn(boolean hotWaterOn) {
		this.hotWaterOn = hotWaterOn;
	}

	public boolean isHeatingOn() {
		return heatingOn;
	}

	public void setHeatingOn(boolean heatingOn) {
		this.heatingOn = heatingOn;
	}
}
