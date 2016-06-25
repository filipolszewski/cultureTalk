package pl.olszewski.culturetalk.webservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, proxyTargetClass = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService);
		auth.authenticationProvider(authenticationProvider());
	}

	private AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(customUserDetailsService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}

	@Configuration
	@Order(1)
	public static class WebServiceSecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
					.antMatcher("/culturetalk/ws/**").authorizeRequests().anyRequest().hasRole("USER").and()
					.authorizeRequests().antMatchers("/culturetalk/ws/registerUser").permitAll().and()
					.httpBasic();
			// http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			// .authorizeRequests().antMatchers("/culturetalk/ws/registerUser").permitAll().and()
			// .authorizeRequests().antMatchers("/culturetalk/ws/status").hasRole("USER").and().httpBasic();
		}
	}

	@Configuration
	@Order(2)
	public static class WebAppWebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Autowired
		private AuthenticationSuccessHandler successHandler;

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.formLogin().loginPage("/login").successHandler(successHandler);
			http.httpBasic();
			http.authorizeRequests().antMatchers("/index", "/registerInst").permitAll().and().authorizeRequests()
					.antMatchers("/instPanel").hasRole("USER").and().authorizeRequests().antMatchers("/admin")
					.hasRole("ADMIN");
			http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/index");
		}
	}

	private PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
