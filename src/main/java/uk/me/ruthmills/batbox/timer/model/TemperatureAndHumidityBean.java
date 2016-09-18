package uk.me.ruthmills.batbox.timer.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class TemperatureAndHumidityBean implements Serializable {
	private static final long serialVersionUID = -3752817315185986367L;
	
	private BigDecimal temperature;
	private BigDecimal humidity;
	
	public BigDecimal getTemperature() {
		return temperature;
	}
	
	public void setTemperature(BigDecimal temperature) {
		this.temperature = temperature;
	}

	public BigDecimal getHumidity() {
		return humidity;
	}

	public void setHumidity(BigDecimal humidity) {
		this.humidity = humidity;
	}
}
