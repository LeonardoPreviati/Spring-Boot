package br.com.empreendedorismo.service;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.hibernate.StatelessSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.empreendedorismo.controller.AuthenticationController;
import br.com.empreendedorismo.entity.Usuario;
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
		Usuario logged = (Usuario) authentication.getPrincipal();
		Date date = new Date();
		Date dateExpiration = new Date(date.getTime() + Long.parseLong(expiration));
		return Jwts.builder()
				.setIssuer("ApiEmpreendedorismo")
				.setSubject(logged.getId().toString())
				.setIssuedAt(date)
				.setExpiration(dateExpiration)
				.signWith(SignatureAlgorithm.HS256, secret)//algoritmo assimétrico e usa um par de chaves pública / 
				.compact();								   //privada: o provedor de identidade possui uma chave privada (secreta) 
														   //usada para gerar a assinatura e o consumidor do JWT recebe uma chave pública para validar a assinatura.
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
	
	public void sendEmail (String name, UsernamePasswordAuthenticationToken dataLogin) {
		try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("discover.profile@gmail.com", "DiscoProfileVoador#300"));
            email.setSSLOnConnect(true);
            email.setFrom("discover.profile@gmail.com");
            email.setSubject("Discover Profile: Login Realizado");
            email.setMsg("Olá " + name + ", Esta é uma notificação para confirmar seu login efetuado com sucesso "
            		   + "na Discover Profile.\n\nAtenciosamente,\nEquipe Discover Profile");
            email.addTo(dataLogin.getName());
            log.info("O usuario '" + name + "' entrou no sistema.");
            email.send();

        } catch (EmailException e) {
            e.printStackTrace();
        }
      
	}
}
