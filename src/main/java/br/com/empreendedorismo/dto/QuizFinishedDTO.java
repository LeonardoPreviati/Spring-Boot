package br.com.empreendedorismo.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class QuizFinishedDTO {
	
	@NotBlank(message = "Necessário inserir o email da conta!!!")
	private String email;
	
	@NotBlank(message = "Necessário inserir o titulo do quiz!!!")
	private String title;

	public QuizFinishedDTO() {
		super();
	}
	
	public QuizFinishedDTO(String email, String title) {
		super();
		this.email = email;
		this.title = title;
	}

}
