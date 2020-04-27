package br.com.empreendedorismo.entity;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ACCOUNT")
public class Account {
	
	@Id 
	@Column(name = "ACCOUNT_ID")
	private String id;
	
	@Column(name = "AGE")
	private String age;
	
	@Column(name = "TELEPHONE")
	private String telephone;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "CREATION_DATE")
	private Date creationDate;
	
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;
	
	@Column(name = "INACTIVATE_DATE")
	private Date inactivateDate;
	
	
	
}
