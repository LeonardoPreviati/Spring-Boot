package br.com.empreendedorismo.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "CITY")
public class City {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CITY_ID")
	private Integer id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "UF")
	private String uf;
	
	@ManyToOne
	private State state;
	
	@Column(name = "CREATION_DATE")
	private Date creationDate;
	
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;

}
