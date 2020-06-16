package br.com.empreendedorismo.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.empreendedorismo.entity.DPUser;

@Repository
public interface UserCategoryRepository extends JpaRepository<DPUser, Integer>{

	
}
