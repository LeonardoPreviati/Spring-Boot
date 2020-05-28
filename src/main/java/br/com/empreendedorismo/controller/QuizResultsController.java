package br.com.empreendedorismo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import br.com.empreendedorismo.entity.QuizResults;
import br.com.empreendedorismo.respository.AccountRepository;
import br.com.empreendedorismo.respository.QuizResultsRepository;
import br.com.empreendedorismo.service.QuizResultsService;
import lombok.extern.slf4j.Slf4j;

import br.com.empreendedorismo.entity.Account;
import br.com.empreendedorismo.entity.Category;

@RestController
@RequestMapping("/quizResults")
@Slf4j
public class QuizResultsController {
	
	@Autowired
	private QuizResultsService quizResultsService;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@RequestMapping(value = "{accountId}", method = RequestMethod.GET)
	public ResponseEntity<HashMap<String, Double>> findResultsById(@PathVariable Integer accountId) throws Exception{
		long startTime = System.currentTimeMillis();
		log.info("QuizResultsController.findResultsById() - BEGIN");
		ResponseEntity<HashMap<String, Double>> listQuizResults = null;
		Optional<Account> optional = accountRepository.findById(accountId);
		try {
			if(optional.isPresent()) {
				listQuizResults = ResponseEntity.ok(quizResultsService.findResultsById(accountId));
			}if(optional.isPresent() && quizResultsService.findResultsById(accountId).isEmpty()) {
				listQuizResults = ResponseEntity.noContent().build();
			}if (!optional.isPresent()){
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
}
