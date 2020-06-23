package br.com.empreendedorismo.entity;

import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Data
@Entity
@Table(name = "ANSWER")
public class Answer {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ANSWER_ID")
	private Integer id;
	
	@Column(name = "ANSWER")
	private String answer;
	
	@Column(name = "VALUE")
	private Double value;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "answer", fetch = FetchType.LAZY)
	private List<Quest> quest;
	
	@JsonIgnore
	@OneToMany(mappedBy = "answer")
	private List<QuizResults> quizResults;

}
