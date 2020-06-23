package br.com.empreendedorismo.dto;

import lombok.Data;

@Data
public class CategoryDTO {
	
	/*@NotBlank(message = "Necessário inserir o nome da Categoria!!!")
	private String name;
	@NotBlank(message = "Necessário inserir a descricao da Categoria!!!")
	private String description;
	@NotBlank(message = "Necessário inserir a url do video da Categoria!!!")
	private String movieUrl;
	private Integer parente_category_id;*/
	

	private String description;
}
