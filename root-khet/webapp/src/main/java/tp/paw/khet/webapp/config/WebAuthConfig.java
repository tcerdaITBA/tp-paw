package tp.paw.khet.webapp.config;

import java.util.Base64;

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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import tp.paw.khet.webapp.auth.StatelessAuthenticationFilter;
import tp.paw.khet.webapp.auth.StatelessLoginSuccessHandler;

@Configuration
@EnableWebSecurity
@ComponentScan("tp.paw.khet.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private StatelessLoginSuccessHandler statelessLoginSuccessHandler;
	
	@Autowired
	private StatelessAuthenticationFilter statelessAuthenticationFilter;

	@Autowired
	private AuthenticationEntryPoint restAuthenticationEntryPoint;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.userDetailsService(userDetailsService).sessionManagement()
				.and()
					.csrf().disable().exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
				.and().authorizeRequests()
					.antMatchers(HttpMethod.POST, "/api/login").anonymous()
					.antMatchers(HttpMethod.POST, "/api/users").anonymous()
					.antMatchers("/api/user/**").authenticated()
					.antMatchers(HttpMethod.POST).authenticated()
					.antMatchers(HttpMethod.DELETE).authenticated()
					.antMatchers(HttpMethod.PUT).authenticated()
					.antMatchers("/api/**").permitAll()
				.and()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
					.formLogin().usernameParameter("j_username").passwordParameter("j_password").loginProcessingUrl("/api/login")
					.successHandler(statelessLoginSuccessHandler)
					.failureHandler(new SimpleUrlAuthenticationFailureHandler())
				.and()
					.addFilterBefore(statelessAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
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
	public String tokenSigningKey() {
		return Base64.getEncoder().encodeToString("037066dfb06356184e35a9eefa80b64c".getBytes());
	}
}
