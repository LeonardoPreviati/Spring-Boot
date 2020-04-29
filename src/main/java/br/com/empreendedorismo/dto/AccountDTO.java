package br.com.empreendedorismo.dto;

import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class AccountDTO {
	
	@NotNull//(message = "Necessário inserir a idade do Usuário!!!")
	private String age;
	
	@NotNull//(message = "Necessário inserir o telefone do Usuário!!!")
	private String telephone;
	
	@NotNull//(message = "Necessário a cidade do Usuário!!!")
	private String city;
	
	public AccountDTO(String age, String telephone, String city) {
		this.age = age;
		this.telephone = telephone;
		this.city = city;
	}
	
	
	
	
	
}
