package uk.me.ruthmills.batbox.timer.service;

import uk.me.ruthmills.batbox.timer.model.BoilerStatusBean;

public interface ControlService {

	public void checkBoilerStatus();

	public BoilerStatusBean reportBoilerStatus();
	
	public void turnHotWaterOff();
	
	public void turnHotWaterToTimer();
	
	public void turnHotWaterOn();
	
	public void turnHeatingOff();
	
	public void turnHeatingToTimer();
	
	public void turnHeatingOn();
}
