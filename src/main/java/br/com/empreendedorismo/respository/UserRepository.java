package br.com.empreendedorismo.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.empreendedorismo.entity.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Integer>{
	
	public Optional<Usuario> findByEmail(String email);
	
	
}
