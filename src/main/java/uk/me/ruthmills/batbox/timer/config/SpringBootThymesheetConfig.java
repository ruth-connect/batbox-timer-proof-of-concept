package uk.me.ruthmills.batbox.timer.config;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.ITemplateModeHandler;

import com.connect_group.thymesheet.spring4.SpringThymesheetTemplateEngine;

import uk.me.ruthmills.batbox.timer.thymeleaf.TimerDialect;
import uk.me.ruthmills.batbox.timer.thymesheet.SpringBootThymesheetTemplateModeHandler;

@Configuration
public class SpringBootThymesheetConfig {

	@Autowired
	private ServletContext servletContext;
	
	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setPrefix("classpath:/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCacheable(false); // This would be set to true in a live production environment.
		templateResolver.initialize();
		return templateResolver;
	}
	
	@Bean
	public SpringThymesheetTemplateEngine templateEngine() {
		SpringThymesheetTemplateEngine templateEngine = new SpringThymesheetTemplateEngine();
		
		Set<ITemplateModeHandler> templateModeHandlers = new HashSet<>();
		SpringBootThymesheetTemplateModeHandler templateModeHandler = new SpringBootThymesheetTemplateModeHandler();
		templateModeHandler.setServletContext(servletContext);
		templateModeHandlers.add(templateModeHandler);
		templateEngine.setTemplateModeHandlers(templateModeHandlers);
		
		templateEngine.setTemplateResolver(templateResolver());
		
		Set<IDialect> additionalDialects = new HashSet<>();
		additionalDialects.add(new TimerDialect());
		templateEngine.setAdditionalDialects(additionalDialects);
		
		return templateEngine;
	}
}
