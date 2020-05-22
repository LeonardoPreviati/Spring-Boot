package br.com.empreendedorismo;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ApiEmpreendendorismoApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(ApiEmpreendendorismoApplication.class, args);
		
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		//spring automatically considers any prefixes present in the application
		.allowedOrigins("http://localhost:3000")
		//origin allowed (host of front)
		.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS",  "HEAD", "TRACE", "CONNECT");
		//type of request allowed
	}
}
