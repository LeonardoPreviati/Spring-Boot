package br.com.empreendedorismo.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import br.com.empreendedorismo.service.TokenService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.empreendedorismo.dto.LoginFormDTO;
import br.com.empreendedorismo.dto.TokenDTO;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	@GetMapping
	public ResponseEntity<TokenDTO> authentication(@RequestBody @Valid LoginFormDTO loginFormDTO){
		UsernamePasswordAuthenticationToken dataLogin = loginFormDTO.convert();
		try {
			Authentication authentication = authenticationManager.authenticate(dataLogin);
			String token = tokenService.generateToken(authentication);
			return ResponseEntity.ok(new TokenDTO(token,"Bearer"));
		} catch (org.springframework.security.core.AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
		
	}
}
