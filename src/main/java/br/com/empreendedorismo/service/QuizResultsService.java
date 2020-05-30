package br.com.empreendedorismo.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import br.com.empreendedorismo.configuration.HibernateConfiguration;
import br.com.empreendedorismo.entity.Category;
import br.com.empreendedorismo.entity.QuizResults;
import br.com.empreendedorismo.respository.QuizResultsRepository;
import br.com.empreendedorismo.utils.Constants;
import br.com.empreendedorismo.utils.CategoryEnum;

@Service
public class QuizResultsService extends HibernateConfiguration{
	
	@Autowired
	private QuizResultsRepository quizResultsRepository;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashMap<Object, Double> findQuizResultsByAccountId(Integer accountId) throws Exception {
		List queryResult =  quizResultsRepository.findResultsQueryByIdAccount(accountId);
		DecimalFormat format = new DecimalFormat("#.##");
		Double allQuestionsByCategory = 3.0;
		HashMap<Object, Double> resultsCategoryPercentage = new HashMap<Object, Double>();
		try {
			for (Object[] category: (List<Object[]>) queryResult) {
				Double categoryPercentageSingle = Double.parseDouble((String.valueOf(category[2]).toString()));
				Double maxPercentageByCategory = Double.parseDouble(format.parse(format.format((categoryPercentageSingle + categoryPercentageSingle) / allQuestionsByCategory)).toString());
				
				if (category[0].toString().equals(CategoryEnum.CAT_ONE.toString())) {
					resultsCategoryPercentage.put(category[1], maxPercentageByCategory);
				}
				else if (category[0].toString().equals(CategoryEnum.CAT_TWO.toString())) {
					resultsCategoryPercentage.put(category[1], maxPercentageByCategory);
				}
				else if (category[0].toString().equals(CategoryEnum.CAT_THREE.toString())) {
					resultsCategoryPercentage.put(category[1], maxPercentageByCategory);
				}
				else if (category[0].toString().equals(CategoryEnum.CAT_FOUR.toString())) {
					resultsCategoryPercentage.put(category[1], maxPercentageByCategory);
				}
				else if (category[0].toString().equals(CategoryEnum.CAT_FIVE.toString())) {
					resultsCategoryPercentage.put(category[1], maxPercentageByCategory);
				}
				else if (category[0].toString().equals(CategoryEnum.CAT_SIX.toString())) {
					resultsCategoryPercentage.put(category[1], maxPercentageByCategory);
				}
				else if (category[0].toString().equals(CategoryEnum.CAT_EIGHT.toString())) {
					resultsCategoryPercentage.put(category[1], maxPercentageByCategory);
				}
				else if (category[0].toString().equals(CategoryEnum.CAT_NINE.toString())) {
					resultsCategoryPercentage.put(category[1], maxPercentageByCategory);
				}
				else if (category[0].toString().equals(CategoryEnum.CAT_TEN.toString())) {
					resultsCategoryPercentage.put(category[1], maxPercentageByCategory);
				}
			}	return resultsCategoryPercentage;
		 }  catch (Exception e) {
			e.printStackTrace();
			throw e;		
		}
	}
}

