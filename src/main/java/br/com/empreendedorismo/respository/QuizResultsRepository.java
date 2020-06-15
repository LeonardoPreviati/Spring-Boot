package br.com.empreendedorismo.respository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import br.com.empreendedorismo.entity.QuizResults;

@SuppressWarnings("rawtypes")
public interface QuizResultsRepository extends CrudRepository<QuizResults, Integer>{
	
	
	@Query(value = "SELECT c.cod_category, c.description, a.value "
			+ 	   "FROM quiz_results qr 						  "
			+ 	   "JOIN answer a								  "
			+ 	   "  ON qr.answer_id = a.answer_id				  "
			+ 	   "JOIN category c 							  "
			+ 	   "  ON qr.category_id = c.category_id			  "
			+ 	   "WHERE qr.ACCOUNT_ID = :accountId			  ", nativeQuery = true)
	public List findResultsQueryByIdAccount(Integer accountId);
	
	@Query(value = " SELECT Q.QUIZ_ID      "
			+ 	   " FROM quiz Q           "
			+ 	   " WHERE Q.TITLE = :title ", nativeQuery = true)
	public Integer findQuizIdByTitle (String title);
	
	@Query(value = " SELECT ACC.ACCOUNT_ID         "
			+ 	   " FROM account ACC	           "
			+ 	   " WHERE ACC.EMAIL = :email ", nativeQuery = true)
	public Integer findAccountIdByEmail (String email);
	
	@Query(value = " SELECT Q.QUEST_ID "
			+ 	   " FROM quest Q  "
			+ 	   " WHERE Q.COD_QUEST = :codQuest     			   ", nativeQuery = true)
			
	public Integer findQuestIdByCodQuest (String codQuest);
	
	@Query(value = " SELECT A.ANSWER_ID "
			+ 	   " FROM answer A  "
			+ 	   " WHERE A.VALUE = :valueAnswer     			   ", nativeQuery = true)
	public Integer findAnswerIdByValue (Double valueAnswer);
	
	@Query(value = " SELECT QC.CATEGORY_ID "
				 + " FROM quest_category QC "
				 + " WHERE QC.QUEST_ID = :questId ", nativeQuery =  true)
	public Integer findCategoryIdByquestId (Integer questId);
	
	@Query(value = " SELECT QR.QUIZ_RESULTS_ID          "
			 	 + " FROM quiz_results QR               "
			     + " WHERE QR.ACCOUNT_ID = :accountId   "
			     + "   AND QR.QUIZ_ID = :quizId     	"
			     + "   AND QR.QUEST_ID = :questId     	"
			     + "   AND QR.CATEGORY_ID = :categoryId "
			     + "   AND QR.ANSWER_ID = :answerId	", nativeQuery =  true)
	public List<Integer> findQuizResultsExists (Integer accountId, Integer quizId, Integer questId, Integer categoryId, Integer answerId);
	

	
	
	

}
