package br.com.empreendedorismo.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import lombok.Data;

@Data
public class LoginFormDTO {

	private String email;
	private String password;
	public UsernamePasswordAuthenticationToken convert() {
		return new UsernamePasswordAuthenticationToken(email, password);
	}
}
