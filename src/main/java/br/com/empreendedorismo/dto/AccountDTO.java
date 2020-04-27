package br.com.empreendedorismo.dto;

import lombok.Data;

@Data
public class AccountDTO {
	
	private String age;
	private String telephone;
	private String city;
	
	public AccountDTO(String age, String telephone, String city) {
		this.age = age;
		this.telephone = telephone;
		this.city = city;
	}
	
	
	
	
	
}
