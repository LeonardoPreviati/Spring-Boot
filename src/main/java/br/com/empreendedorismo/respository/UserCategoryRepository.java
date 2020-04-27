package br.com.empreendedorismo.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.empreendedorismo.entity.Usuario;

@Repository
public interface UserCategoryRepository extends JpaRepository<Usuario, Integer>{

	
}
