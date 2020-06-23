package br.com.empreendedorismo.entity;

import java.sql.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Entity
@Data
@Table(name = "CATEGORY")
public class Category {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CATEGORY_ID")
	private Integer id;
	
	@Column(name = "COD_CATEGORY")
	private String codCategory;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "MOVIE_URL")
	private String movieUrl;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "category", fetch = FetchType.LAZY)
	private List<Quest> quest;
	
	@JsonIgnore
	@OneToMany(mappedBy = "category")
	private List<QuizResults> quizResults;
	
	@Column(name = "CREATION_DATE")
	private Date creationDate;
	
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	@Override
	public String toString() {
		return "Category [id=" + id + ", codCategory=" + codCategory + ", name=" + name + ", description=" + description
				+ ", movieUrl=" + movieUrl + ", quest=" + quest + ", quizResults=" + quizResults + ", creationDate="
				+ creationDate + ", lastUpdateDate=" + lastUpdateDate + "]";
	}
	
	
	
}
