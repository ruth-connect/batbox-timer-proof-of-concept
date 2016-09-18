package uk.me.ruthmills.batbox.timer.service;

import uk.me.ruthmills.batbox.timer.model.TemperatureAndHumidityBean;

public interface ThermostatService {

	public TemperatureAndHumidityBean readTemperatureAndHumidity();
}
