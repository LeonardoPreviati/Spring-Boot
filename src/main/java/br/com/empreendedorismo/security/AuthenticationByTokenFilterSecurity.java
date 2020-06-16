package br.com.empreendedorismo.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import br.com.empreendedorismo.entity.DPUser;
import br.com.empreendedorismo.respository.DPUserRepository;
import br.com.empreendedorismo.service.TokenService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationByTokenFilterSecurity extends OncePerRequestFilter{

	private TokenService tokenService;
	
	private DPUserRepository dpUserRepository;
	
	// Injection by constructor
	public AuthenticationByTokenFilterSecurity(TokenService tokenService, DPUserRepository dpUserRepository) {
		this.tokenService = tokenService;
		this.dpUserRepository = dpUserRepository;
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

	protected void userAuthenticate(String token) {
		Integer userId = tokenService.getUserId(token);
		DPUser user = dpUserRepository.findById(userId).get();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
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
