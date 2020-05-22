package br.com.empreendedorismo.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name="STATE")
public class State {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "STATE_ID")
	private Integer id;
	
	@Column(name = "NAME")
	private String name;
	
	@OneToMany(mappedBy = "state")
	private List<City> city;
	
	@ManyToOne
	private Country country;
	
	@Column(name = "CREATION_DATE")
	private Date creationDate;
	
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;
	
}
