package br.com.empreendedorismo.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.empreendedorismo.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	@Query(value = " SELECT * 					        "
				 + " FROM category 				        "
				 + " WHERE COD_CATEGORY = :codCategory  ", nativeQuery = true)
	Optional<Category> findCategoryByCod (String codCategory);

	

}
