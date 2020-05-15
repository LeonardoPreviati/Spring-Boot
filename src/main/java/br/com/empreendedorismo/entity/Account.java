package br.com.empreendedorismo.entity;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "ACCOUNT")
public class Account {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "ACCOUNT_ID")
	private Integer id;
	
	@Column(name = "USER_EMAIL", unique = true)
	private String userEmail;
	
	@Column(name = "AGE")
	private String age;
	
	@Column(name = "TELEPHONE")
	private String telephone;
	
	@Column(name = "CITY")
	private String city;
	
	@OneToMany(mappedBy = "account")
	private List<QuizFinished> quizFinished;
	
	@JsonIgnore
	@OneToMany(mappedBy = "account")
	private List<QuizResults> quizResults;
	
	@Column(name = "CREATION_DATE")
	private Date creationDate;
	
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;
	
	//@JsonManagedReference
	/* Anotação usada para indicar que a propriedade anotada 
	 * faz parte da ligação bidirecional entre os campos; e que seu papel é o 
	 * link "pai" (ou "encaminhamento"). O tipo de valor (classe) da propriedade 
	 * deve ter uma única propriedade compatível anotada com JsonBackReference. 
	 * A ligação é manipulada de modo que a propriedade anotada com esta anotação 
	 * seja manipulada normalmente (serializada normalmente, sem tratamento especial 
	 * para desserialização); é a referência anterior correspondente que requer tratamento especial.
	 */
	
	
	public Account() {
		super();
	}

	public Account(Integer id, String userEmail, String age, String telephone, String city, Date creationDate,
			Date lastUpdateDate) {
		super();
		this.id = id;
		this.userEmail = userEmail;
		this.age = age;
		this.telephone = telephone;
		this.city = city;
		this.creationDate = creationDate;
		this.lastUpdateDate = lastUpdateDate;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastUpdateDate == null) {
			if (other.lastUpdateDate != null)
				return false;
		} else if (!lastUpdateDate.equals(other.lastUpdateDate))
			return false;
		if (telephone == null) {
			if (other.telephone != null)
				return false;
		} else if (!telephone.equals(other.telephone))
			return false;
		if (userEmail == null) {
			if (other.userEmail != null)
				return false;
		} else if (!userEmail.equals(other.userEmail))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastUpdateDate == null) ? 0 : lastUpdateDate.hashCode());
		result = prime * result + ((telephone == null) ? 0 : telephone.hashCode());
		result = prime * result + ((userEmail == null) ? 0 : userEmail.hashCode());
		return result;
	}
	
	
}
