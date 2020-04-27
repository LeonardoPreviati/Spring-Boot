package br.com.empreendedorismo.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperSecurityConfiguration {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
}