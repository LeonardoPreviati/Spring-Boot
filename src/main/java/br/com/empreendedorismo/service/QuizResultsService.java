package br.com.empreendedorismo.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.empreendedorismo.configuration.HibernateConfiguration;
import br.com.empreendedorismo.dto.QuizResultsDTO;
import br.com.empreendedorismo.entity.Account;
import br.com.empreendedorismo.entity.Answer;
import br.com.empreendedorismo.entity.Category;
import br.com.empreendedorismo.entity.Quest;
import br.com.empreendedorismo.entity.Quiz;
import br.com.empreendedorismo.entity.QuizResults;
import br.com.empreendedorismo.respository.AccountRepository;
import br.com.empreendedorismo.respository.AnswerRepository;
import br.com.empreendedorismo.respository.CategoryRepository;
import br.com.empreendedorismo.respository.QuestRepository;
import br.com.empreendedorismo.respository.QuizRepository;
import br.com.empreendedorismo.respository.QuizResultsRepository;

@Service
public class QuizResultsService extends HibernateConfiguration{
	
	@Autowired
	private QuizResultsRepository quizResultsRepository;
	
	@Autowired
	private QuizRepository quizRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private QuestRepository questRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	public QuizResults findById(Integer id) {
		QuizResults entityAccount = null;
		try {
			Optional<QuizResults> optional = quizResultsRepository.findById(id);
			if (optional.isPresent()) {
				entityAccount = optional.get();
			}return entityAccount;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashMap<Object, Double> findQuizResultsByAccountId(Integer accountId) throws Exception {
		//declaration of category values
		List<Double> arrCat = new ArrayList<Double>();//{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		//Search result, containing cod_category and description of category and response percentage by accountId
		List queryResult =  quizResultsRepository.findResultsQueryByIdAccount(accountId);
		//count many category exists
		Integer countCat = quizResultsRepository.findCountCategory();
		//find cod_category linked with question
		String[] codCat = quizResultsRepository.findCodCategory(accountId);
		//Format category percentage
		DecimalFormat format = new DecimalFormat("#.##");
		//Totally questions linked by category
		Double allQuestionsByCategory = 3.0;
		//Completed percentage of all categories 
		HashMap<Object, Double> resultsCategoryPercentage = new HashMap<Object, Double>();
		try {
			for (int i = 0; i < countCat + 1; ++i) {
				arrCat.add(0.0);
			}double[] someCat = arrCat.stream().mapToDouble(Double::doubleValue).toArray();
			for (Object[] category: (List<String[]>) queryResult) {
				Double categoryPercentageSingle = Double.parseDouble((String.valueOf(category[3]).toString()));
				Double maxPercentageByCategory = Double.parseDouble(format.parse(format.format((categoryPercentageSingle))).toString());
				for (String cat : codCat) {
					if (category[1].toString().equals(cat)) {
						resultsCategoryPercentage.put(category[2], Double.parseDouble((format.parse(format.format(someCat[(int) category[0]] +=  maxPercentageByCategory / allQuestionsByCategory))).toString()));
					}
				}
			}return resultsCategoryPercentage;   
		 }  catch (Exception e) {
			e.printStackTrace();
			throw e;		
		}
	}
	
	public QuizResults save(QuizResultsDTO quizResultsDTO) throws Exception {
		try {
			QuizResults quizResults = new QuizResults();
			Integer email = quizResultsRepository.findAccountIdByEmail(quizResultsDTO.getEmail());
			Integer title = quizResultsRepository.findQuizIdByTitle(quizResultsDTO.getTitle());
			Optional<Account> account = accountRepository.findById(email);
			Optional<Quiz> quiz = quizRepository.findById(title);
			if (account.isPresent() && quiz.isPresent()) {
				for (Map.Entry<String, Double> questionPercentage : quizResultsDTO.getQuestionPercentage().entrySet()) {
					quizResults = createQuizResults(questionPercentage.getKey(), questionPercentage.getValue(), account.get(), quiz.get()); 
				}	 
			}return quizResults;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public QuizResults createQuizResults (String questionCod, Double questionValue, Account account, Quiz quiz) {
		try {
			QuizResults quizResults = new QuizResults();
			//find questId by cod_quest
			Integer questId = quizResultsRepository.findQuestIdByCodQuest(questionCod);
			//find categoryId by questId
			Integer categoryId = quizResultsRepository.findCategoryIdByquestId(questId);
			//find answerId by value
			Integer answerId = quizResultsRepository.findAnswerIdByValue(questionValue);
			//verify quiz_results identical in data base
			List<Integer> quizResultsExist = quizResultsRepository.findQuizResultsExists(account.getId(), quiz.getId(), questId, categoryId);
			List<Integer> findAnswerValueByQuestCod = quizResultsRepository.findAnswerValueByQuestCod(questionValue);
			Optional<Quest> quest = questRepository.findById(questId);
			Optional<Category> category = categoryRepository.findById(categoryId);
			Optional<Answer> answer = answerRepository.findById(answerId);
			if (quizResultsExist.isEmpty() && !findAnswerValueByQuestCod.isEmpty()) {
				quizResults.setAccount(account);
				quizResults.setQuiz(quiz);
				quizResults.setQuest(quest.get());
				quizResults.setCategory(category.get());
				quizResults.setAnswer(answer.get());
				quizResults.setCreationDate(new Date());
				quizResultsRepository.save(quizResults);
		   }else if (!quizResultsExist.isEmpty() && !findAnswerValueByQuestCod.isEmpty()){
				updateQuizResults(answer.get(),quizResultsExist.get(0));
			}return quizResults;
		} catch (ConstraintViolationException e) {
				e.printStackTrace();
				throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;		
		}
	}
	
	public QuizResults updateQuizResults(Answer answer, Integer existingQuizResults) {
		try {
			QuizResults updateEntity = findById(existingQuizResults);
			updateEntity.setAnswer(answer);
			updateEntity.setLastUpdateDate(new Date());
			quizResultsRepository.save(updateEntity);
			return updateEntity;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}

