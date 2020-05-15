package br.com.empreendedorismo.dto;

import javax.validation.constraints.NotBlank;

import br.com.empreendedorismo.entity.Quiz;
import lombok.Data;

@Data
public class QuestDTO {
	
	@NotBlank
	private String description;
	
	private Integer quizId;
	
	private Integer categoryId;
	
	

}
