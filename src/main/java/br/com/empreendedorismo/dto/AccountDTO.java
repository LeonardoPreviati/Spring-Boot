package br.com.empreendedorismo.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AccountDTO {
	
	@NotBlank(message = "Necessário inserir a idade do Usuário!!!")
	private String age;
	
	@NotBlank(message = "Necessário inserir o telefone do Usuário!!!")
	private String telephone;
	
	@NotBlank(message = "Necessário a cidade do Usuário!!!")
	private String city;
	
	public AccountDTO() {
		super();
	}
	
	public AccountDTO(String age, String telephone, String city) {
		super();
		this.age = age;
		this.telephone = telephone;
		this.city = city;
	}
}
