package br.com.empreendedorismo.dto;

import javax.validation.constraints.NotBlank;

import br.com.empreendedorismo.entity.Quiz;
import lombok.Data;

@Data
public class QuestDTO {
	
	@NotBlank(message = "É necessário inserir a descrição da categoria que deseja alterar!!!")
	private String questDescription;
	
	@NotBlank(message = "Necessário inserir o id do quiz!!!")
	private String quizTitle;
	
	@NotBlank(message = "Necessário inserir o id da categoria!!!")
	private String codCategory;
	
	
	
	
}
