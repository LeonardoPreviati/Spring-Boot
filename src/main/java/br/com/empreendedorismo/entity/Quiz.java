package br.com.empreendedorismo.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
@Entity
@Table(name = "QUIZ")
public class Quiz {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "QUIZ_ID")
	private Integer id;
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@OneToMany(mappedBy = "quiz")
	private List<Quest> quest;
	
	@JsonIgnore
	@OneToMany(mappedBy = "quiz")
	private List<QuizResults> quizResults;
	
	@Column(name = "CREATION_DATE")
	private Date creationDate;
	
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;
	
	//@JsonManagedReference//
		/* Anotação usada para indicar que a propriedade anotada 
		 * faz parte da ligação bidirecional entre os campos; e que seu papel é o 
		 * link "pai" (ou "encaminhamento"). O tipo de valor (classe) da propriedade 
		 * deve ter uma única propriedade compatível anotada com JsonBackReference. 
		 * A ligação é manipulada de modo que a propriedade anotada com esta anotação 
		 * seja manipulada normalmente (serializada normalmente, sem tratamento especial 
		 * para desserialização); é a referência anterior correspondente que requer tratamento especial.
		 */
}
