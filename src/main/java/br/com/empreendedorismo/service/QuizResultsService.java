package br.com.empreendedorismo.service;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import br.com.empreendedorismo.utils.Constants;
import net.bytebuddy.implementation.bytecode.Throw;

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
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}return entityAccount;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashMap<Object, Double> findQuizResultsByAccountId(Integer accountId) throws Exception {
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
				Double maxPercentageByCategory = Double.parseDouble(format.parse(format.format((categoryPercentageSingle + categoryPercentageSingle) / allQuestionsByCategory)).toString());
				resultsCategoryPercentage.put(category[1], maxPercentageByCategory);
			}return resultsCategoryPercentage;
		 }  catch (Exception e) {
			e.printStackTrace();
			throw e;		
		}
	}
	
	public QuizResults save(QuizResultsDTO quizResultsDTO) throws Exception {
		try {
			Integer email = quizResultsRepository.findAccountIdByEmail(quizResultsDTO.getEmail());
			Integer title = quizResultsRepository.findQuizIdByTitle(quizResultsDTO.getTitle());
			Optional<Account> account = accountRepository.findById(email);
			Optional<Quiz> quiz = quizRepository.findById(title);
			if (account.isPresent() && quiz.isPresent()) {
				for (Map.Entry<String, Double> questionPercentage : quizResultsDTO.getQuestionPercentage().entrySet()) {
					QuizResults quizResults = new QuizResults();
					//find questId by cod_quest
					Integer questId = quizResultsRepository.findQuestIdByCodQuest(questionPercentage.getKey());
					//find categoryId by questId
					Integer categoryId = quizResultsRepository.findCategoryIdByquestId(questId);
					//find answerId by value
					Integer answerId = quizResultsRepository.findAnswerIdByValue(questionPercentage.getValue());
					//verify quiz_results identical in data base
					List<Integer> exists = quizResultsRepository.findQuizResultsExists(account.get().getId(), quiz.get().getId(), questId, categoryId, answerId);
					if (exists.isEmpty()) {
						Optional<Quest> quest = questRepository.findById(questId);
						Optional<Category> category = categoryRepository.findById(categoryId);
						Optional<Answer> answer = answerRepository.findById(answerId);
						quizResults.setAccount(account.get());
						quizResults.setQuiz(quiz.get());
						quizResults.setQuest(quest.get());
						quizResults.setCategory(category.get());
						quizResults.setAnswer(answer.get());
						quizResults.setCreationDate(new Date(Calendar.getInstance().getTimeInMillis()));
						quizResultsRepository.save(quizResults);
					}else {
						QuizResults updateEntity = findById(exists.get(0));
						updateEntity.setLastUpdateDate(new Date(Calendar.getInstance().getTimeInMillis()));
						quizResultsRepository.save(updateEntity);
					}
				}	 
			}return new QuizResults();
		} catch (Exception e) {
			return new QuizResults();
		}
	}
}

