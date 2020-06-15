package br.com.empreendedorismo.dto;

import java.util.HashMap;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class QuizResultsDTO {
	
	@NotBlank(message = "Necessário inserir o email da conta!!!")
	private String email;
	
	@NotBlank(message = "Necessário inserir o titulo do quiz!!!")
	private String title;
	
	@NotNull(message = "Necessário inserir a questão remete o valor")
	private HashMap<String, Double> questionPercentage;

	public QuizResultsDTO() {
		super();
	}

	public QuizResultsDTO(@NotBlank(message = "Necessário inserir o email da conta!!!") String email,
			@NotBlank(message = "Necessário inserir o titulo do quiz!!!") String title,
			@NotNull(message = "Necessário inserir a questão remete o valor") HashMap<String, Double> questionPercentage) {
		super();
		this.email = email;
		this.title = title;
		this.questionPercentage = questionPercentage;
	}
	
	
	
	
	
	

}
