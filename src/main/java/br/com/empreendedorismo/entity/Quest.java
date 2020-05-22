package br.com.empreendedorismo.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "QUEST")
public class Quest {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "QUEST_ID")
	private Integer id;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "QUIZ_ID")
	private Quiz quiz;
	
	@JsonIgnore
	@OneToMany(mappedBy = "quest")
	private List<QuizResults> quizResults;
	
	@ManyToMany
	@Fetch(FetchMode.JOIN)
	@JoinTable(name = "QUEST_CATEGORY", joinColumns = { @JoinColumn(name = "QUEST_ID"), }, inverseJoinColumns = { @JoinColumn(name = "CATEGORY_ID"), })
	private List<Category> category;
	
	@ManyToMany
	@Fetch(FetchMode.JOIN)
	@JoinTable(name = "QUEST_ANSWER", joinColumns = { @JoinColumn(name = "QUEST_ID"), }, inverseJoinColumns = { @JoinColumn(name = "ANSWER_ID"), })
	private Set<Answer> answer;
	
	@Column(name = "CREATION_DATE")
	private Date creationDate;
	
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;
	
	

}
