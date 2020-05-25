package br.com.empreendedorismo.dto;

import java.sql.Date;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AccountDTO {
	
	private String postalCode;
	private String publicPlace;
	private String neighborhood;
	private String complement;
	private String locale;
	private String uf;
	private Date dateOfBirth;
	private String telephone;
	
	public AccountDTO() {
		super();
	}
	
	public AccountDTO(String postalCode, String publicPlace, String neighborhood, String complement, String locale,
			String uf, Date dateOfBirth, String telephone) {
		super();
		this.postalCode = postalCode;
		this.publicPlace = publicPlace;
		this.neighborhood = neighborhood;
		this.complement = complement;
		this.locale = locale;
		this.uf = uf;
		this.dateOfBirth = dateOfBirth;
		this.telephone = telephone;
	}
	
	
	
	
	
}
