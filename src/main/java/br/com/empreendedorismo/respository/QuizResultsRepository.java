package br.com.empreendedorismo.respository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import br.com.empreendedorismo.entity.QuizResults;

@Repository
@SuppressWarnings("rawtypes")
public interface QuizResultsRepository extends CrudRepository<QuizResults, Integer>{
	
	
	@Query(value = " SELECT C.CATEGORY_ID, C.COD_CATEGORY, C.DESCRIPTION, A.VALUE "
			+ 	   " FROM quiz_results QR 						   "
			+ 	   " JOIN answer A								   "
			+ 	   "   ON QR.ANSWER_ID = A.ANSWER_ID			   "
			+ 	   " JOIN category C 							   "
			+ 	   "   ON QR.CATEGORY_ID = C.CATEGORY_ID		   "
			+ 	   " WHERE QR.ACCOUNT_ID = :accountId			   ", nativeQuery = true)
	public List findResultsQueryByIdAccount(Integer accountId);
	
	@Query(value = " SELECT Q.QUIZ_ID       "
			+ 	   " FROM quiz Q            "
			+ 	   " WHERE Q.TITLE = :title ", nativeQuery = true)
	public Integer findQuizIdByTitle (String title);
	
	@Query(value = " SELECT ACC.ACCOUNT_ID    "
			+ 	   " FROM account ACC	      "
			+ 	   " WHERE ACC.EMAIL = :email ", nativeQuery = true)
	public Integer findAccountIdByEmail (String email);
	
	@Query(value = " SELECT Q.QUEST_ID 			   "
			+ 	   " FROM quest Q  				   "
			+ 	   " WHERE Q.COD_QUEST = :codQuest ", nativeQuery = true)
			
	public Integer findQuestIdByCodQuest (String codQuest);
	
	@Query(value = " SELECT A.ANSWER_ID 		  "
			+ 	   " FROM answer A  			  "
			+ 	   " WHERE A.VALUE = :valueAnswer ", nativeQuery = true)
	public Integer findAnswerIdByValue (Double valueAnswer);
	
	@Query(value = " SELECT C.CATEGORY_ID 				 "
				 + " FROM quest_category QC 			 "
				 + " JOIN category C 					 "
				 + "   ON C.CATEGORY_ID = QC.CATEGORY_ID "
				 + " WHERE QC.QUEST_ID = :questId 		 ", nativeQuery =  true)
	public Integer findCategoryIdByquestId (Integer questId);
	
	@Query(value = " SELECT QR.QUIZ_RESULTS_ID          "
			 	 + " FROM quiz_results QR               "
			     + " WHERE QR.ACCOUNT_ID = :accountId   "
			     + "   AND QR.QUIZ_ID = :quizId     	"
			     + "   AND QR.QUEST_ID = :questId     	"
			     + "   AND QR.CATEGORY_ID = :categoryId ", nativeQuery =  true)
	public List<Integer> findQuizResultsExists (Integer accountId, Integer quizId, Integer questId, Integer categoryId);
	
	@Query(value = " SELECT A.ANSWER_ID     "
			+ 	   " FROM answer A          "
			+ 	   " WHERE A.VALUE = :questValue ", nativeQuery = true)
	public List<Integer> findAnswerValueByQuestCod (Double questValue);
	
	@Query(value = " SELECT DISTINCT(C.COD_CATEGORY) 	 "
			     + " FROM category C 					 "
			     + " JOIN quiz_results QR 				 "
			     + "   ON QR.CATEGORY_ID = C.CATEGORY_ID  "
			     + " JOIN account A 						 "
			     + "	  ON A.ACCOUNT_ID = QR.ACCOUNT_ID 	 "
			     + " JOIN quiz Q 						 "
			     + "	  ON Q.QUIZ_ID = QR.QUIZ_ID 	     "
			     + " WHERE A.ACCOUNT_ID = :accountId      "
			    + "	  AND Q.TITLE = 'ENTREPRENEURSHIP'   ", nativeQuery = true)
	public String[] findCodCategory (Integer accountId);
	
	
	
	@Query(value = " SELECT COUNT(*) "
				 + " FROM category   ", nativeQuery = true)
	public Integer findCountCategory();
	
	
	
	

	
	
	

}
