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
	
	@Column(name = "POSTAL_CODE")
	private String postalCode;
	
	@Column(name = "PUBLIC_PLACE")
	private String publicPlace;
	
	@Column(name = "NEIGHBORHOOD")
	private String neighborhood;
	
	@Column(name = "COMPLEMENT")
	private String complement;
	
	@Column(name = "LOCALE")
	private String locale;
	
	@Column(name = "UF")
	private String uf;
	
	@Column(name = "DATE_OF_BIRTH")
	private Date dateOfBirth;
	
	@Column(name = "TELEPHONE", unique = true)
	private String telephone;
	
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

	public Account(Integer id, String email, String postalCode, String publicPlace, String neighborhood,
			String complement, String locale, String uf, Date dateOfBirth, String telephone,
			List<QuizFinished> quizFinished, List<QuizResults> quizResults, Date creationDate, Date lastUpdateDate) {
		super();
		this.id = id;
		this.email = email;
		this.postalCode = postalCode;
		this.publicPlace = publicPlace;
		this.neighborhood = neighborhood;
		this.complement = complement;
		this.locale = locale;
		this.uf = uf;
		this.dateOfBirth = dateOfBirth;
		this.telephone = telephone;
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
		if (complement == null) {
			if (other.complement != null)
				return false;
		} else if (!complement.equals(other.complement))
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
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		if (neighborhood == null) {
			if (other.neighborhood != null)
				return false;
		} else if (!neighborhood.equals(other.neighborhood))
			return false;
		if (postalCode == null) {
			if (other.postalCode != null)
				return false;
		} else if (!postalCode.equals(other.postalCode))
			return false;
		if (publicPlace == null) {
			if (other.publicPlace != null)
				return false;
		} else if (!publicPlace.equals(other.publicPlace))
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
		if (telephone == null) {
			if (other.telephone != null)
				return false;
		} else if (!telephone.equals(other.telephone))
			return false;
		if (uf == null) {
			if (other.uf != null)
				return false;
		} else if (!uf.equals(other.uf))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((complement == null) ? 0 : complement.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastUpdateDate == null) ? 0 : lastUpdateDate.hashCode());
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		result = prime * result + ((neighborhood == null) ? 0 : neighborhood.hashCode());
		result = prime * result + ((postalCode == null) ? 0 : postalCode.hashCode());
		result = prime * result + ((publicPlace == null) ? 0 : publicPlace.hashCode());
		result = prime * result + ((quizFinished == null) ? 0 : quizFinished.hashCode());
		result = prime * result + ((quizResults == null) ? 0 : quizResults.hashCode());
		result = prime * result + ((telephone == null) ? 0 : telephone.hashCode());
		result = prime * result + ((uf == null) ? 0 : uf.hashCode());
		return result;
	}
	
	
	
	
	
	
	
}
