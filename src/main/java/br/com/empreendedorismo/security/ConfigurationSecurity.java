package br.com.empreendedorismo.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

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

import br.com.empreendedorismo.respository.UserRepository;
import br.com.empreendedorismo.service.TokenService;



@EnableWebSecurity
@Configuration
public class ConfigurationSecurity extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private AuthenticationSecurity authenticationSecurity;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserRepository userRepository;
	
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
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(HttpMethod.POST, "/user").permitAll()
		.antMatchers(HttpMethod.POST, "/auth").permitAll()
		.antMatchers(HttpMethod.POST, "/created").permitAll()
		.antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
		.antMatchers(HttpMethod.GET, "/category/findById/*").permitAll()
		.antMatchers(HttpMethod.POST,"/category/created").permitAll()
		.antMatchers(HttpMethod.DELETE,"/category/deleteById/*").permitAll()
		//above endpoints, can be accessed without authentication via token
		.anyRequest().authenticated()
		//to access the rest of the requests, you will need to be authenticated to the system
		.and().cors()
		//enable cors to integrate with the front
		.and().csrf().disable()
		//disable one type of hacker attack
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        //It is not to create session, because we will use token, for each authentication
        .and().addFilterBefore(new AuthenticationByTokenFilterSecurity(tokenService, userRepository), UsernamePasswordAuthenticationFilter.class);
	}
	
	//Static Resource Settings(js, css, imagens, etc...)
	@Override
	public void configure(WebSecurity web) throws Exception {
		
	}

	
}
