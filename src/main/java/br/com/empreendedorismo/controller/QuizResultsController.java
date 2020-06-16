package br.com.empreendedorismo.controller;

import java.util.HashMap;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import br.com.empreendedorismo.respository.AccountRepository;
import br.com.empreendedorismo.service.QuizResultsService;
import lombok.extern.slf4j.Slf4j;
import br.com.empreendedorismo.dto.QuizFinishedDTO;
import br.com.empreendedorismo.dto.QuizResultsDTO;
import br.com.empreendedorismo.entity.Account;
import br.com.empreendedorismo.entity.Quiz;
import br.com.empreendedorismo.entity.QuizFinished;
import br.com.empreendedorismo.entity.QuizResults;


@RestController
@RequestMapping("/quizResults")
@Slf4j
public class QuizResultsController {
	
	@Autowired
	private QuizResultsService quizResultsService;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
	public ResponseEntity<HashMap<Object, Double>> findResultsById(@PathVariable Integer accountId) throws Exception{
		long startTime = System.currentTimeMillis();
		log.info("QuizResultsController.findResultsById() - BEGIN");
		ResponseEntity<HashMap<Object, Double>> listQuizResults = null;
		Optional<Account> optional = accountRepository.findById(accountId);
		try {
			if(optional.isPresent()) {
				listQuizResults = ResponseEntity.ok(quizResultsService.findQuizResultsByAccountId(accountId));
			}else if(optional.isPresent() && quizResultsService.findQuizResultsByAccountId(accountId).isEmpty()) {
				listQuizResults = ResponseEntity.noContent().build();
			}else if (!optional.isPresent()){
				listQuizResults = ResponseEntity.notFound().build();
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR: " + e.getMessage());
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("QuizResultsController.findResultsById() - END (" + endTime + "ms)");
		return listQuizResults; 
	}
	
	@PostMapping
	public ResponseEntity<QuizResults> save(@RequestBody @Valid QuizResultsDTO quizResultsDTO) throws Exception {
		long startTime = System.currentTimeMillis();
		log.info("QuizResultsController.save(@RequestBody @Valid QuizResultsDTO quizResultsDTO) - BEGIN");
		ResponseEntity<QuizResults> quizResultsSave = null;
		QuizResults quizResults;
		try {
			quizResults = quizResultsService.save(quizResultsDTO);
			if (!quizResults.equals(null)) {
				quizResultsSave = new ResponseEntity<QuizResults>(HttpStatus.CREATED);
			}
		} catch (Exception e) {
			quizResultsSave = ResponseEntity.notFound().build();
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("QuizResultsController.save(@RequestBody @Valid QuizResultsDTO quizResultsDTO) - END (" + endTime + "ms)");
		return quizResultsSave;
	}
	
	@RequestMapping(value = "/findById/{quizResultsId}", method = RequestMethod.GET)
	public ResponseEntity<QuizResults> findById(@PathVariable Integer quizResultsId){
		long startTime = System.currentTimeMillis();
		log.info("QuizResultsController.findById(@PathVariable Integer quizResultsId) - BEGIN");
		ResponseEntity<QuizResults> accountResponse = null;
		QuizResults account;
		try {
			account = quizResultsService.findById(quizResultsId);
			if (!account.equals(null)) {
				accountResponse = ResponseEntity.ok().body(account);
			}
		} catch (Exception e) {
			accountResponse = ResponseEntity.notFound().build();
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("QuizResultsController.findById(@PathVariable Integer quizResultsId) - END (" + endTime + "ms)");
		return accountResponse;	
	}
}
