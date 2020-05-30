package br.com.empreendedorismo.respository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import br.com.empreendedorismo.entity.QuizResults;

public interface QuizResultsRepository extends JpaRepository<QuizResults, Integer>{
	
	@SuppressWarnings("rawtypes")
	@Query(value = "SELECT c.cod_category, c.description, a.value "
			+ 	   "FROM quiz_results qr 						  "
			+ 	   "JOIN answer a								  "
			+ 	   "  ON qr.answer_id = a.answer_id				  "
			+ 	   "JOIN category c 							  "
			+ 	   "  ON qr.category_id = c.category_id			  "
			+ 	   "WHERE qr.ACCOUNT_ID = :accountId			  "
			, nativeQuery = true)
	public List findResultsQueryByIdAccount(Integer accountId);
	
	
	
	

}
