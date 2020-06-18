package br.com.empreendedorismo.dto;

import java.sql.Date;
import lombok.Data;

@Data
public class AccountDTO {
	
	private String phone;
	private String zipCode;
	private String neighborhood;
	private String city;
	private String uf;
	private Date dateOfBirth;
	
	public AccountDTO() {
		super();
	}
	
	public AccountDTO(String phone, String zipCode, String neighborhood, String city, String uf, Date dateOfBirth) {
		super();
		this.phone = phone;
		this.zipCode = zipCode;
		this.neighborhood = neighborhood;
		this.city = city;
		this.uf = uf;
		this.dateOfBirth = dateOfBirth;
	}
	
	
	
}
