package br.com.empreendedorismo.service;

import java.util.Date;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import br.com.empreendedorismo.entity.DPUser;
import br.com.empreendedorismo.utils.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Service
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "security")
@Slf4j
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
	
	public void sendEmail (String name, UsernamePasswordAuthenticationToken dataLogin) {
		try {
            Email email = new SimpleEmail();
            email.setHostName(Constants.EMAIL_SERVER_HOSTNAME);
            email.setSmtpPort(Constants.SMPT_PORT);
            email.setAuthenticator(new DefaultAuthenticator(Constants.EMAIL_FROM, Constants.PASSWORD_DISCOVER_PROFILE));
            email.setSSLOnConnect(true);
            email.setFrom(Constants.EMAIL_FROM);
            email.setSubject(Constants.SUBJECT);
            email.setMsg(Constants.HELLO_MSG + name + Constants.BODY_MSG);
            email.addTo(dataLogin.getName());
            log.info(Constants.INFO_USER + name + Constants.INFO_LOGIN);
            email.send();

        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
