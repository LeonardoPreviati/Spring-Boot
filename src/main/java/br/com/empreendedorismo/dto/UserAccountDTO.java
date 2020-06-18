package br.com.empreendedorismo.dto;



import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.Data;

@Data
public class UserAccountDTO {
	
	@NotBlank(message = "Necessário inserir o nome do usuário!!!")
	private String name;
	
	@NotBlank(message = "Necessário inserir um email válido")
	private String email;
	
	@NotBlank(message = "Necessário inserir a senha!!!")
	private String password;
	
	private String phone;
	
	private String zipCode;
	
	private String neighborhood;
	
	private String city;
	
	private String uf;
	
	private Date dateOfBirth;
	
	public UserAccountDTO() {
		super();
	}

	public UserAccountDTO(@NotBlank(message = "Necessário inserir o nome do usuário!!!") String name,
			@NotBlank(message = "Necessário inserir um email válido") String email,
			@NotBlank(message = "Necessário inserir a senha!!!") String password, String phone, String zipCode,
			String neighborhood, String city, String uf, Date dateOfBirth) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.zipCode = zipCode;
		this.neighborhood = neighborhood;
		this.city = city;
		this.uf = uf;
		this.dateOfBirth = dateOfBirth;
	}
}
