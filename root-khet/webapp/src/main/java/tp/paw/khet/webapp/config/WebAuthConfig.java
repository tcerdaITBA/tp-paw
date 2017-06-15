package tp.paw.khet.webapp.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import tp.paw.khet.webapp.auth.RefererLoginSuccessHandler;

@Configuration
@EnableWebSecurity
@ComponentScan("tp.paw.khet.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.userDetailsService(userDetailsService).sessionManagement()
				  .invalidSessionUrl("/")
			.and().authorizeRequests()
				.antMatchers("/errors/**").permitAll()
				.antMatchers("/upload").authenticated()
				.antMatchers("/login").anonymous()
				.antMatchers("/register").anonymous()
				.antMatchers(HttpMethod.POST, "/favlist/create").authenticated()
				.antMatchers(HttpMethod.POST, "/profile/customize/**").authenticated()
				.antMatchers(HttpMethod.POST, "/product/**").authenticated()
				.antMatchers(HttpMethod.POST, "/delete/product/**").authenticated()
				.antMatchers(HttpMethod.POST, "/vote/product/**").authenticated()
				.antMatchers("/**").permitAll()
			.and().formLogin()
				.usernameParameter("j_username").passwordParameter("j_password")
				.successHandler(successHandler())
				.loginPage("/login")
				.failureUrl("/login?error=1")
			.and().rememberMe()
				.userDetailsService(userDetailsService)
				.rememberMeParameter("j_rememberme")
				.key("037066dfb06356184e35a9eefa80b64c")
				.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
			.and().logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
			.and().exceptionHandling()
				.accessDeniedPage("/errors/403")
			.and().csrf().disable();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico", "/403");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}
	
	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationSuccessHandler successHandler() {
		RefererLoginSuccessHandler handler = new RefererLoginSuccessHandler();
		handler.setAlwaysUseDefaultTargetUrl(false);
		handler.setDefaultTargetUrl("/");
		return handler;
	}
}
