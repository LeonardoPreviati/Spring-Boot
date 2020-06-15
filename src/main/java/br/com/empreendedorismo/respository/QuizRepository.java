package br.com.empreendedorismo.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.empreendedorismo.entity.Category;
import br.com.empreendedorismo.entity.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer>{
	
	@Query(value = " SELECT * 			   "
			 	 + " FROM quiz 			   "
			     + " WHERE TITLE = :title  ", nativeQuery = true)
	Optional<Quiz> findQuizByTitle (String title);

}
