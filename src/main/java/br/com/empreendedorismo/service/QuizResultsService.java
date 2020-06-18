package br.com.empreendedorismo.service;

import java.text.DecimalFormat;
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
import br.com.empreendedorismo.utils.CategoryEnum;


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
		Double CAT_ONE = 0.0;
		Double CAT_TWO = 0.0;
		Double CAT_THREE = 0.0;
		Double CAT_FOUR = 0.0;
		Double CAT_FIVE = 0.0;
		Double CAT_SIX = 0.0;
		Double CAT_SEVEN = 0.0;
		Double CAT_EIGTH = 0.0;
		Double CAT_NINE = 0.0;
		Double CAT_TEN = 0.0;
		//Search result, containing cod_category and description of category and response percentage by accountId
		List queryResult =  quizResultsRepository.findResultsQueryByIdAccount(accountId);
		//Format category percentage
		DecimalFormat format = new DecimalFormat("#.##");
		//Totally questions linked by category
		Double allQuestionsByCategory = 3.0;
		//Completed percentage of all categories 
		HashMap<Object, Double> resultsCategoryPercentage = new HashMap<Object, Double>();
		try {
			for (Object[] category: (List<String[]>) queryResult) {
				Double categoryPercentageSingle = Double.parseDouble((String.valueOf(category[2]).toString()));
				Double maxPercentageByCategory = Double.parseDouble(format.parse(format.format((categoryPercentageSingle))).toString());
				if (category[0].toString().equals(CategoryEnum.CAT_ONE.toString())) {
					resultsCategoryPercentage.put(category[1], Double.parseDouble((format.parse(format.format(CAT_ONE +=  maxPercentageByCategory / allQuestionsByCategory))).toString()));
				}else if (category[0].toString().equals(CategoryEnum.CAT_TWO.toString())) {
					resultsCategoryPercentage.put(category[1], Double.parseDouble((format.parse(format.format(CAT_TWO +=  maxPercentageByCategory / allQuestionsByCategory))).toString()));
				}else if (category[0].toString().equals(CategoryEnum.CAT_THREE.toString())) {
					resultsCategoryPercentage.put(category[1], Double.parseDouble((format.parse(format.format(CAT_THREE +=  maxPercentageByCategory / allQuestionsByCategory))).toString()));
				}else if (category[0].toString().equals(CategoryEnum.CAT_FOUR.toString())) {
					resultsCategoryPercentage.put(category[1], Double.parseDouble((format.parse(format.format(CAT_FOUR +=  maxPercentageByCategory / allQuestionsByCategory))).toString()));
				}else if (category[0].toString().equals(CategoryEnum.CAT_FIVE.toString())) {
					resultsCategoryPercentage.put(category[1], Double.parseDouble((format.parse(format.format(CAT_FIVE +=  maxPercentageByCategory / allQuestionsByCategory))).toString()));
				}else if (category[0].toString().equals(CategoryEnum.CAT_SIX.toString())) {
					resultsCategoryPercentage.put(category[1], Double.parseDouble((format.parse(format.format(CAT_SIX +=  maxPercentageByCategory / allQuestionsByCategory))).toString()));
				}else if (category[0].toString().equals(CategoryEnum.CAT_SEVEN.toString())) {
					resultsCategoryPercentage.put(category[1], Double.parseDouble((format.parse(format.format(CAT_SEVEN +=  maxPercentageByCategory / allQuestionsByCategory))).toString()));
				}else if (category[0].toString().equals(CategoryEnum.CAT_EIGHT.toString())) {
					resultsCategoryPercentage.put(category[1], Double.parseDouble((format.parse(format.format(CAT_EIGTH +=  maxPercentageByCategory / allQuestionsByCategory))).toString()));
				}else if (category[0].toString().equals(CategoryEnum.CAT_NINE.toString())) {
					resultsCategoryPercentage.put(category[1], Double.parseDouble((format.parse(format.format(CAT_NINE +=  maxPercentageByCategory / allQuestionsByCategory))).toString()));
				}else if (category[0].toString().equals(CategoryEnum.CAT_TEN.toString())) {
					resultsCategoryPercentage.put(category[1], Double.parseDouble((format.parse(format.format(CAT_TEN +=  maxPercentageByCategory / allQuestionsByCategory))).toString()));
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
					quizResults = createUser(questionPercentage.getKey(), questionPercentage.getValue(), account.get(), quiz.get()); 
				}	 
			}return quizResults;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public QuizResults createUser (String questionCod, Double questionValue, Account account, Quiz quiz) {
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
				updateUser(answer.get(),quizResultsExist.get(0));
			}return quizResults;
		} catch (ConstraintViolationException e) {
				e.printStackTrace();
				throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;		
		}
	}
	
	public QuizResults updateUser(Answer answer, Integer existingQuizResults) {
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

