package br.com.empreendedorismo.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserAccountDTO {
	
	@NotBlank(message = "Necessário inserir o nome do usuário!!!")
	private String name;
	
	@NotBlank(message = "Necessário inserir um email válido")
	private String email;
	
	@NotBlank(message = "Necessário inserir a senha!!!")
	private String password;
	
	@NotBlank(message = "Necessário inserir a idade do Usuário!!!")
	private String age;
	
	@NotBlank(message = "Necessário inserir o telefone do Usuário!!!")
	private String telephone;
	
	@NotBlank(message = "Necessário a cidade do Usuário!!!")
	private String city;

	public UserAccountDTO(String name, String email, String password, String age, String telephone, String city) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.age = age;
		this.telephone = telephone;
		this.city = city;
	}
}
