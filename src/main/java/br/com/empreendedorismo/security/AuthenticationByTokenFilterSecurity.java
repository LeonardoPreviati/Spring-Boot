package br.com.empreendedorismo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.empreendedorismo.controller.UserController;
import br.com.empreendedorismo.entity.Usuario;
import br.com.empreendedorismo.respository.UserRepository;
import br.com.empreendedorismo.service.TokenService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationByTokenFilterSecurity extends OncePerRequestFilter{

	private TokenService tokenService;
	
	private UserRepository userRepository;
	
	// Injection by constructor
	public AuthenticationByTokenFilterSecurity(TokenService tokenService, UserRepository userRepository) {
		this.tokenService = tokenService;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = retrieveToken(request);
		boolean valid = tokenService.isValidToken(token);
		if(valid) {
			userAuthenticate(token);
		}
		filterChain.doFilter(request, response);
	}

	private void userAuthenticate(String token) {
		Integer userId = tokenService.getUserId(token);
		Usuario usuario = userRepository.findById(userId).get();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
	}

	private String retrieveToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		
		return token.substring(7, token.length());
		
	}

}
