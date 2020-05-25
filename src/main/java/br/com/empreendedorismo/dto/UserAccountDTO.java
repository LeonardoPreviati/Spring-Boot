package br.com.empreendedorismo.dto;

import java.sql.Date;

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
	
	@NotBlank(message = "Necessário inserir o telefone do Usuário!!!")
	private String telephone;
	
	private String postalCode;
	
	private String publicPlace;
	
	private String neighborhood;
	
	private String complement;
	
	private String locale;
	
	private String uf;
	
	private Date dateOfBirth;
	
	public UserAccountDTO() {
		super();
	}

	public UserAccountDTO(@NotBlank(message = "Necessário inserir o nome do usuário!!!") String name,
			@NotBlank(message = "Necessário inserir um email válido") String email,
			@NotBlank(message = "Necessário inserir a senha!!!") String password,
			@NotBlank(message = "Necessário inserir o telefone do Usuário!!!") String telephone, String postalCode,
			String publicPlace, String neighborhood, String complement, String locale, String uf, Date dateOfBirth) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.telephone = telephone;
		this.postalCode = postalCode;
		this.publicPlace = publicPlace;
		this.neighborhood = neighborhood;
		this.complement = complement;
		this.locale = locale;
		this.uf = uf;
		this.dateOfBirth = dateOfBirth;
	}
	
	
	


}
