package uk.me.ruthmills.batbox.timer.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import uk.me.ruthmills.batbox.timer.model.BoilerStatusBean;
import uk.me.ruthmills.batbox.timer.service.ControlService;

@Controller
public class TimerController {

	private static final Logger log = Logger.getLogger(TimerController.class);
	
	@Autowired
	private ControlService controlService;
	
	@RequestMapping({"/", "/index.html"})
    public ModelAndView index() {
		if(log.isInfoEnabled()) {
			log.info("TimerController index() invoked");
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("boilerStatus", controlService.reportBoilerStatus());
		modelAndView.setViewName("index");
        return modelAndView;
    }
	
	@RequestMapping(value = "/reportBoilerStatus")
	public @ResponseBody BoilerStatusBean reportBoilerStatus() {
		return controlService.reportBoilerStatus();
	}
	
	@RequestMapping(value = "/hotWater/off")
	public void turnHotWaterOff() {
		controlService.turnHotWaterOff();
	}
	
	@RequestMapping(value = "/hotWater/timer")
	public void turnHotWaterToTimer() {
		controlService.turnHotWaterToTimer();
	}
	
	@RequestMapping(value = "/hotWater/on")
	public void turnHotWaterOn() {
		controlService.turnHotWaterOn();
	}
	
	@RequestMapping(value = "/heating/off")
	public void turnHeatingOff() {
		controlService.turnHeatingOff();
	}
	
	@RequestMapping(value = "/heating/timer")
	public void turnHeatingToTimer() {
		controlService.turnHeatingToTimer();
	}
	
	@RequestMapping(value = "/heating/on")
	public void turnHeatingOn() {
		controlService.turnHeatingOn();
	}
	
	@RequestMapping(value = "/setTargetTemperature/{targetTemperature}")
	public void setTargetTemperature(@PathVariable String targetTemperature) {
		controlService.setTargetTemperature(targetTemperature);
	}
}
