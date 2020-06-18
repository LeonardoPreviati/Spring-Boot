package br.com.empreendedorismo.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.empreendedorismo.entity.DPUser;
import br.com.empreendedorismo.respository.DPUserRepository;
import br.com.empreendedorismo.service.TokenService;
import br.com.empreendedorismo.utils.Constants;

@EnableWebSecurity
@Configuration
public class ConfigurationSecurity extends WebSecurityConfigurerAdapter implements WebMvcConfigurer{
	
	@Autowired
	private AuthenticationSecurity authenticationSecurity;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private DPUserRepository dpUserRepository;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	//Authentication Settings
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authenticationSecurity).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	//Settings Authorization
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests()
		.antMatchers(HttpMethod.POST, Constants.AUTH, Constants.DP_USER).permitAll()
		.antMatchers(HttpMethod.GET,  Constants.ACTUATOR).hasAuthority(Constants.DISCOVER_PROFILE)
		.antMatchers(HttpMethod.GET,  Constants.QUIZ_RESULTS).hasAnyAuthority(Constants.USER, Constants.ADMIN)
		//above endpoints, can be accessed without authentication via token
		.anyRequest().authenticated()
		//to access the rest of the requests, you will need to be authenticated to the system
		.and().cors()
		//enable cors to integrate with the front
		.and().csrf().disable()
		//disable one type of hacker attack
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        //It is not to create session, because we will use token, for each authentication
        .and().addFilterBefore(new AuthenticationByTokenFilterSecurity(tokenService, dpUserRepository), UsernamePasswordAuthenticationFilter.class);
	}
	
	//Static Resource Settings(js, css, imagens, etc...)
	@Override
	public void configure(WebSecurity web) throws Exception {
		
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		//spring automatically considers any prefixes present in the application
		.allowedOrigins(Constants.ALLOWED_ORIGINS_HOSTNAME)
		//origin allowed (host of front)
		.allowedMethods(Constants.GET, Constants.POST, Constants.PUT, Constants.DELETE);
		//type of request allowed
		
		
		
	}

	
}
