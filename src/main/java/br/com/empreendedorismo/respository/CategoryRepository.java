package br.com.empreendedorismo.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.empreendedorismo.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

	

}
