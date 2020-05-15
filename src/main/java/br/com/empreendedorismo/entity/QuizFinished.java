package br.com.empreendedorismo.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Data
@Table(name = "QUIZ_FINISHED")
public class QuizFinished {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "QUIZ_FINISHED_ID")
	private Integer id;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="ACCOUNT_ID")
	private Account account;
	
	@OneToOne
	@JoinColumn(name = "QUIZ_ID")
	private Quiz quiz;
	
	@Column(name = "CREATION_DATE")
	private Date creationDate;
	
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;
	
	//@JsonBackReference
		/* Anotação usada para indicar que a propriedade associada faz parte 
		 * da ligação bidirecional entre campos; e que seu papel é o link "filho" 
		 * (ou "voltar"). O tipo de valor da propriedade deve ser um bean: não pode 
		 * ser uma coleção, mapa, matriz ou enumeração. A ligação é tratada de modo 
		 * que a propriedade anotada com esta anotação não seja serializada; e durante 
		 * a desserialização, seu valor é definido para a instância que possui o 
		 * link "gerenciado" (encaminhamento).
		 */
	
	public QuizFinished() {
		super();
	}

	public QuizFinished(Integer id, Account account, Date creationDate, Date lastUpdateDate) {
		super();
		this.id = id;
		this.account = account;
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
		QuizFinished other = (QuizFinished) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
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
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastUpdateDate == null) ? 0 : lastUpdateDate.hashCode());
		return result;
	}
	
	
	
	

}
