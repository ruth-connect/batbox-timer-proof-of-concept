package uk.me.ruthmills.batbox.timer.thymeleaf;

import org.thymeleaf.dialect.AbstractDialect;

public class TimerDialect extends AbstractDialect {

	@Override
	public String getPrefix() {
		return "timer";
	}
	
	@Override
	public boolean isLenient() {
		return false;
	}
}
