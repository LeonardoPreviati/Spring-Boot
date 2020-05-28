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
import javax.persistence.JoinColumn;
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
	
	@JoinColumn(name = "EMAIL", unique = true)
	private String email;
	
	@Column(name = "ZIP_CODE")
	private String zipCode;
	
	@Column(name = "NEIGHBORHOOD")
	private String neighborhood;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "UF")
	private String uf;
	
	@Column(name = "DATE_OF_BIRTH")
	private Date dateOfBirth;
	
	@Column(name = "PHONE", unique = true)
	private String phone;
	
	@OneToMany(mappedBy = "account")
	private List<QuizFinished> quizFinished;
	
	@JsonIgnore
	@OneToMany(mappedBy = "account")
	private List<QuizResults> quizResults;
	
	@Column(name = "CREATION_DATE")
	private Date creationDate;
	
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;
	
	public Account() {
		super();
	}

	public Account(Integer id, String email, String zipCode, String neighborhood, String city, String uf,
			Date dateOfBirth, String phone, List<QuizFinished> quizFinished, List<QuizResults> quizResults,
			Date creationDate, Date lastUpdateDate) {
		super();
		this.id = id;
		this.email = email;
		this.zipCode = zipCode;
		this.neighborhood = neighborhood;
		this.city = city;
		this.uf = uf;
		this.dateOfBirth = dateOfBirth;
		this.phone = phone;
		this.quizFinished = quizFinished;
		this.quizResults = quizResults;
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
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
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
		if (neighborhood == null) {
			if (other.neighborhood != null)
				return false;
		} else if (!neighborhood.equals(other.neighborhood))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (quizFinished == null) {
			if (other.quizFinished != null)
				return false;
		} else if (!quizFinished.equals(other.quizFinished))
			return false;
		if (quizResults == null) {
			if (other.quizResults != null)
				return false;
		} else if (!quizResults.equals(other.quizResults))
			return false;
		if (uf == null) {
			if (other.uf != null)
				return false;
		} else if (!uf.equals(other.uf))
			return false;
		if (zipCode == null) {
			if (other.zipCode != null)
				return false;
		} else if (!zipCode.equals(other.zipCode))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastUpdateDate == null) ? 0 : lastUpdateDate.hashCode());
		result = prime * result + ((neighborhood == null) ? 0 : neighborhood.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((quizFinished == null) ? 0 : quizFinished.hashCode());
		result = prime * result + ((quizResults == null) ? 0 : quizResults.hashCode());
		result = prime * result + ((uf == null) ? 0 : uf.hashCode());
		result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
		return result;
	}
}
