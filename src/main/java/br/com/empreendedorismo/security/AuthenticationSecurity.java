package br.com.empreendedorismo.security;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.empreendedorismo.entity.Usuario;
import br.com.empreendedorismo.respository.UserRepository;

//Chama a service para realizar a autenticação do login
@Service
public class AuthenticationSecurity implements UserDetailsService{
	
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> user = userRepository.findByEmail(username);
		if (user.isPresent()) {
			return user.get();
		}
		throw new UsernameNotFoundException("Dados inválidos!");
		
	}

	

}
