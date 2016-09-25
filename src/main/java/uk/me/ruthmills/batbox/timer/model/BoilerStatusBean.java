package uk.me.ruthmills.batbox.timer.model;

import java.io.Serializable;
import java.math.BigDecimal;

import uk.me.ruthmills.batbox.timer.service.impl.ControlServiceImpl;

public class BoilerStatusBean implements Serializable {
	private static final long serialVersionUID = -8401098055741018600L;
	
	private String hotWaterSetting;
	private String heatingSetting;
	private String targetTemperature;
	private String temperature;
	private String humidity;
	private boolean timerOn;
	private boolean hotWaterOn;
	private boolean heatingOn;
	
	public BoilerStatusBean(ControlServiceImpl.Setting hotWaterSetting, ControlServiceImpl.Setting heatingSetting, BigDecimal targetTemperature, BigDecimal temperature, BigDecimal humidity, boolean timerOn, boolean hotWaterOn, boolean heatingOn) {
		this.setHotWaterSetting(convertSettingToString(hotWaterSetting));
		this.setHeatingSetting(convertSettingToString(heatingSetting));
		this.setTargetTemperature(targetTemperature);
		this.setTemperature(temperature);
		this.setHumidity(humidity);
		this.setTimerOn(timerOn);
		this.setHotWaterOn(hotWaterOn);
		this.setHeatingOn(heatingOn);
	}
	
	public BoilerStatusBean() {
	}

	public String getHotWaterSetting() {
		return hotWaterSetting;
	}

	public void setHotWaterSetting(String hotWaterSetting) {
		this.hotWaterSetting = hotWaterSetting;
	}

	public String getHeatingSetting() {
		return heatingSetting;
	}

	public void setHeatingSetting(String heatingSetting) {
		this.heatingSetting = heatingSetting;
	}

	public String getTargetTemperature() {
		return targetTemperature;
	}

	public void setTargetTemperature(BigDecimal targetTemperature) {
		if (targetTemperature != null) {
			this.targetTemperature = targetTemperature.setScale(1, BigDecimal.ROUND_DOWN).toString();
		} else {
			this.targetTemperature = "";
		}
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

	public boolean isTimerOn() {
		return timerOn;
	}

	public void setTimerOn(boolean timerOn) {
		this.timerOn = timerOn;
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
	
	private String convertSettingToString(ControlServiceImpl.Setting setting) {
		if (setting == ControlServiceImpl.Setting.ON) {
			return "on";
		} else if (setting == ControlServiceImpl.Setting.TIMER) {
			return "timer";
		} else {
			return "off";
		}
	}
}
