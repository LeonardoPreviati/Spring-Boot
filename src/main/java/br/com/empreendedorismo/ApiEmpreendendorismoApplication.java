package br.com.empreendedorismo;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ApiEmpreendendorismoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiEmpreendendorismoApplication.class, args);
		
	}
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("GET","POST");
	}

}
