package br.com.empreendedorismo.controller;

import javax.validation.Valid;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import br.com.empreendedorismo.service.TokenService;
import br.com.empreendedorismo.util.EmailUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.empreendedorismo.dto.LoginFormDTO;
import br.com.empreendedorismo.dto.TokenDTO;
import br.com.empreendedorismo.entity.DPUser;
import br.com.empreendedorismo.respository.DPUserRepository;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private DPUserRepository dpUserRepository;
	
	@PostMapping
	public ResponseEntity<TokenDTO> authentication(@RequestBody @Valid LoginFormDTO loginFormDTO) throws EmailException{
		UsernamePasswordAuthenticationToken dataLogin = loginFormDTO.convert();
		DPUser findByEmail;
		try {
			findByEmail = dpUserRepository.findByEmailIgnoreCase(dataLogin.getName());
			System.out.println(findByEmail);
			Authentication authentication = authenticationManager.authenticate(dataLogin);
			String token = tokenService.generateToken(authentication);
			EmailUtil loginAccount = new EmailUtil();
			loginAccount.sendEmail(findByEmail, "");
			return ResponseEntity.ok(new TokenDTO(token,"Bearer"));
		} catch (org.springframework.security.core.AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
		
	}
}
