package br.com.empreendedorismo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.empreendedorismo.dto.QuizFinishedDTO;
import br.com.empreendedorismo.entity.Account;
import br.com.empreendedorismo.entity.Quiz;
import br.com.empreendedorismo.entity.QuizFinished;
import br.com.empreendedorismo.entity.Usuario;
import br.com.empreendedorismo.respository.AccountRepository;
import br.com.empreendedorismo.respository.QuizFinishedRepository;
import br.com.empreendedorismo.respository.QuizRepository;
import br.com.empreendedorismo.service.QuizFinishedService;
import br.com.empreendedorismo.service.UserService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/quizFinished")
@Slf4j
public class QuizFinishedController {
	
	@Autowired
	private QuizFinishedService quizFinishedService;
	
	@Autowired
	private QuizRepository quizRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@GetMapping
	public ResponseEntity<List<QuizFinished>> findAll() throws Exception{
		long startTime = System.currentTimeMillis();
		log.info("QuizFinishedController.findAll() - BEGIN");
		ResponseEntity<List<QuizFinished>> quizList = null;
		try {
			quizList =  quizFinishedService.findAll();
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("QuizFinishedController.findAll() - END (" + endTime + "ms)");
		return quizList; 
	}
	
	@GetMapping("{id}")
	public ResponseEntity<QuizFinished> findById(@PathVariable Integer id){
		long startTime = System.currentTimeMillis();
		log.info("QuizFinishedController.findById(@PathVariable Integer id) - BEGIN");
		ResponseEntity<QuizFinished> quizFind = null;
		try {
			quizFind = quizFinishedService.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("QuizFinishedController.findById(@PathVariable Integer id) - END (" + endTime + "ms)");
		return quizFind;
	}
	
	@PostMapping
	public ResponseEntity<QuizFinished> save(@RequestBody @Valid QuizFinishedDTO quizFinishedDTO) throws Exception {
		long startTime = System.currentTimeMillis();
		log.info("QuizFinishedController.save(@PathVariable Integer id) - BEGIN");
		ResponseEntity<QuizFinished> quizSave = null;
		Integer account = findIdAccoutByEmail(quizFinishedDTO.getEmail());
		Integer quiz = findIdQuizByTitle(quizFinishedDTO.getTitle());
		Optional<Account> accountId = accountRepository.findById(account);
		Optional<Quiz> quizId = quizRepository.findById(quiz);
		try {
			if (accountId.isPresent() && quizId.isPresent()) {
				quizSave = ResponseEntity.ok(quizFinishedService.save(accountId.get(), quizId.get()));
			}else {
				quizSave = ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("QuizFinishedController.save(@PathVariable Integer id) - END (" + endTime + "ms)");
		return quizSave;
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteById(@PathVariable Integer id) {
		long startTime = System.currentTimeMillis();
		log.info("QuizFinishedController.deleteById(@PathVariable Integer id) - BEGIN");
		ResponseEntity<?> quizEntity = null;
		try {
			quizEntity = quizFinishedService.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("QuizFinishedController.deleteById(@PathVariable Integer id) - END (" + endTime + "ms)");
		return quizEntity;
		
	}
	
	public Integer findIdAccoutByEmail(String email) {
		long startTime = System.currentTimeMillis();
		log.info("QuizFinishedController.findIdAccoutByEmail(Account email) - BEGIN");
		Integer accountId = null;
		try {
			accountId = quizFinishedService.findAccoutIdByEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("QuizFinishedController.findIdAccoutByEmail(Account email) - END (" + endTime + "ms)");
		return accountId;
	}
	
	public Integer findIdQuizByTitle(String title) {
		long startTime = System.currentTimeMillis();
		log.info("QuizFinishedController.deleteById(@PathVariable Integer id) - BEGIN");
		Integer quiz = null;
		try {
			quiz = quizFinishedService.findQuizIdByTitle(title);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("QuizFinishedController.deleteById(@PathVariable Integer id) - END (" + endTime + "ms)");
		return quiz;
	}
}


