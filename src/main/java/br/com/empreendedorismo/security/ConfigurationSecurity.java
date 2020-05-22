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
	
	//Configurações de Autenticação
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authenticationSecurity).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	//Configurações de Autorização
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(HttpMethod.POST, "/user").permitAll()
		.antMatchers(HttpMethod.GET, "/auth").permitAll()
		.antMatchers(HttpMethod.POST, "/created").permitAll()
		.antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
		.antMatchers(HttpMethod.GET, "/category/findById/*").permitAll()
		.antMatchers(HttpMethod.POST,"/category/created").permitAll()
		.antMatchers(HttpMethod.DELETE,"/category/deleteById/*").permitAll()
		.antMatchers(HttpMethod.GET, "/user/*").permitAll()
		.anyRequest().authenticated()
		//csrf() tipo de ataque hacker
		.and().csrf().disable()
		//Não é para criar sessão, pois vamos usar token, para cada autenticação
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().addFilterBefore(new AuthenticationByTokenFilterSecurity(tokenService, userRepository), UsernamePasswordAuthenticationFilter.class);
		
		
	}
	
	//Configurações de Recursos Estáticos(js, css, imagens, etc...)
	@Override
	public void configure(WebSecurity web) throws Exception {
		
	}

	
}
