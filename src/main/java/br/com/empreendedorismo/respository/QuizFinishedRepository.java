package br.com.empreendedorismo.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.empreendedorismo.entity.QuizFinished;

public interface QuizFinishedRepository extends JpaRepository<QuizFinished, Integer>{
	
	@Query(value = " SELECT ACC.ACCOUNT_ID         "
			+ 	   " FROM account ACC	           "
			+ 	   " WHERE ACC.USER_EMAIL = :email ", nativeQuery = true)
	public Integer findResultQueryByEmail (String email);
	
	@Query(value = " SELECT Q.QUIZ_ID      "
			+ 	   " FROM quiz Q           "
			+ 	   " WHERE Q.TITLE = :title ", nativeQuery = true)
	public Integer findResultQueryByTitle (String title);

}
