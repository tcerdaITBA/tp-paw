package tp.paw.khet.webapp.config;

import java.nio.charset.StandardCharsets;

import javax.sql.DataSource;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@ComponentScan({"tp.paw.khet.webapp.controller", "tp.paw.khet.persistence", "tp.paw.khet.service"})
@Configuration
public class WebConfig {
	
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		
		return resolver;
	}
	
	@Bean
	public DataSource dataSource() {
		final SimpleDriverDataSource ds = new SimpleDriverDataSource();
		ds.setDriverClass(org.postgresql.Driver.class);
		ds.setUrl("jdbc:postgresql://localhost/tp-paw");
		ds.setUsername("root");
		ds.setPassword("root");
		return ds;
	}
	
	@Bean
	public MessageSource messageSource() {
		final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:i18n/messages");
		messageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
		return messageSource;
	}
}
