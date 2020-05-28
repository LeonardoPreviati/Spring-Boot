package br.com.empreendedorismo.dto;

import java.sql.Date;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AccountDTO {
	
	private String zipCode;
	private String neighborhood;
	private String city;
	private String uf;
	private Date dateOfBirth;
	private String phone;
	
	public AccountDTO() {
		super();
	}

	public AccountDTO(String zipCode, String neighborhood, String city, String uf, Date dateOfBirth, String phone) {
		super();
		this.zipCode = zipCode;
		this.neighborhood = neighborhood;
		this.city = city;
		this.uf = uf;
		this.dateOfBirth = dateOfBirth;
		this.phone = phone;
	}
	
	
	
	
	
	
	
}
