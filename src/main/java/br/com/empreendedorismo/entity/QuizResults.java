package br.com.empreendedorismo.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
@Entity
@Table(name = "QUIZ_RESULTS", uniqueConstraints = @UniqueConstraint( columnNames = {"QUEST_ID","CATEGORY_ID","ANSWER_ID"}))
public class QuizResults {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "QUIZ_RESULTS_ID")
	private Integer id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "ACCOUNT_ID")
	private Account account;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "QUIZ_ID")
	private Quiz quiz;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "QUEST_ID")
	private Quest quest;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID")
	private Category category;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "ANSWER_ID")
	private Answer answer;
	
	@Column(name = "CREATION_DATE")
	private Date creationDate;
	
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;
	
	public QuizResults() {
		super();
	}

	@SuppressWarnings("null")
	public QuizResults(Integer id, Account account, Quiz quiz, Quest quest, Category category, Answer answer,
			Date creationDate, Date lastUpdateDate) {
		super();
		this.id = id;
		this.account = account;
		this.quiz = quiz;
		this.quest = quest;
		this.category = category;
		this.answer = answer;
		this.creationDate = creationDate == null ? new Date(creationDate.getTime()) : creationDate;
		this.lastUpdateDate = lastUpdateDate == null? new Date(lastUpdateDate.getTime()) :lastUpdateDate;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuizResults other = (QuizResults) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (answer == null) {
			if (other.answer != null)
				return false;
		} else if (!answer.equals(other.answer))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
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
		if (quest == null) {
			if (other.quest != null)
				return false;
		} else if (!quest.equals(other.quest))
			return false;
		if (quiz == null) {
			if (other.quiz != null)
				return false;
		} else if (!quiz.equals(other.quiz))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((answer == null) ? 0 : answer.hashCode());
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastUpdateDate == null) ? 0 : lastUpdateDate.hashCode());
		result = prime * result + ((quest == null) ? 0 : quest.hashCode());
		result = prime * result + ((quiz == null) ? 0 : quiz.hashCode());
		return result;
	}
	
	
	
	

}
