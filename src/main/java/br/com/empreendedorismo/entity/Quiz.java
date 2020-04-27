package br.com.empreendedorismo.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;


@Data
@Entity
public class Quiz {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name = "quiz_id")
	private Integer id;
	private String title;
	private Date date;
}
