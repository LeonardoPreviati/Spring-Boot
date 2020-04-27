package br.com.empreendedorismo.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.empreendedorismo.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${ApiEmpreendedorismo.jwt.expiration}")
	private String expiration;
	
	@Value("${ApiEmpreendedorismo.jwt.secret}")
	private String secret;

	public String generateToken(Authentication authentication) {
		Usuario logged = (Usuario) authentication.getPrincipal();
		Date date = new Date();
		Date dateExpiration = new Date(date.getTime() + Long.parseLong(expiration));
		return Jwts.builder()
				.setIssuer("ApiEmpreendedorismo")
				.setSubject(logged.getId().toString())
				.setIssuedAt(date)
				.setExpiration(dateExpiration)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();				
	}

	public boolean isValidToken(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public Integer getUserId(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Integer.parseInt(claims.getSubject());
	}
}
