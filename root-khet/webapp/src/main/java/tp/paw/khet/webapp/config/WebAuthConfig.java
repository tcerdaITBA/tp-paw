package tp.paw.khet.webapp.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@ComponentScan("tp.paw.khet.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.userDetailsService(userDetailsService).sessionManagement()
				  .invalidSessionUrl("/login")
			.and().authorizeRequests()
				.antMatchers("/login").anonymous()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/**").authenticated()
			.and().formLogin()
				.usernameParameter("j_username").passwordParameter("j_password")
				.defaultSuccessUrl("/", false).loginPage("/login")
			.and().rememberMe()
				.userDetailsService(userDetailsService)
				.rememberMeParameter("j_rememberme")
				.key("mysupersecretkeythatnobodyknowsabout")
				.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
			.and().logout()
				.logoutUrl("/logout").logoutSuccessUrl("/login")
			.and().exceptionHandling()
				.accessDeniedPage("/403")
			.and().csrf().disable();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico", "/403");
	}

}
