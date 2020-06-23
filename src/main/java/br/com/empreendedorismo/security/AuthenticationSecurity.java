package br.com.empreendedorismo.security;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import br.com.empreendedorismo.entity.DPUser;
import br.com.empreendedorismo.respository.DPUserRepository;

//Chama a service para realizar a autenticação do login
@Service
public class AuthenticationSecurity implements UserDetailsService{
	
	
	@Autowired
	private DPUserRepository dpUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<DPUser> user = dpUserRepository.findByEmail(username);
		if (user.isPresent()) {
			return user.get();
		}
		throw new UsernameNotFoundException("Dados inválidos!");
		
	}

	

}
