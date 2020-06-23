package br.com.empreendedorismo.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import br.com.empreendedorismo.entity.DPUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "security")
public class TokenService {
	
	@Value("${security.jwt.token.expire-length}")
	private String expiration;
	
	@Value("${security.jwt.token.secret-key}")
	private String secret;
	
	public String generateToken(Authentication authentication) {
		DPUser logged = (DPUser) authentication.getPrincipal();
		Date date = new Date();
		Date dateExpiration = new Date(date.getTime() + Long.parseLong(expiration));
		return Jwts.builder()
				.setIssuer("ApiEmpreendedorismo")
				.setSubject(logged.getId().toString())
				.setIssuedAt(date)
				.setExpiration(dateExpiration)
				.signWith(SignatureAlgorithm.HS256, secret)//algoritmo assimétrico e usa um par de chaves pública / 
				.compact();								   //privada: o provedor de identidade possui uma chave privada (secreta) 
	}													   //usada para gerar a assinatura e o consumidor do JWT recebe uma chave pública para validar a assinatura.
	
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
