package br.com.empreendedorismo.dto;

import lombok.Data;

@Data
public class TokenDTO {
	
	private String token;
	private String type;
	
	public TokenDTO(String token, String type) {
		this.token = token;
		this.type = type;
	}
}
