package br.com.empreendedorismo.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.empreendedorismo.configuration.HibernateConfiguration;
import br.com.empreendedorismo.entity.Account;
import br.com.empreendedorismo.entity.Quiz;
import br.com.empreendedorismo.entity.QuizFinished;
import br.com.empreendedorismo.respository.QuizFinishedRepository;

@Service
public class QuizFinishedService extends HibernateConfiguration{

	@Autowired
	private QuizFinishedRepository quizFinishedRepository;
	
	public ResponseEntity<List<QuizFinished>> findAll() {
		 ResponseEntity<List<QuizFinished>> responseEntity = null;
		try {
			if (!quizFinishedRepository.findAll().isEmpty()){
				responseEntity = ResponseEntity.ok(quizFinishedRepository.findAll());
			}else {
				responseEntity = ResponseEntity.noContent().build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}return responseEntity;
	}
	
	public ResponseEntity<QuizFinished> findById(Integer id) {
		ResponseEntity<QuizFinished> quizFinished = null;
		try {
			Optional<QuizFinished> optional = quizFinishedRepository.findById(id);
			quizFinished = ResponseEntity.ok().body(optional.get());
		} catch (Exception e) {
			quizFinished = ResponseEntity.notFound().build();
		}return quizFinished;
	}
	
	public QuizFinished save(Account account, Quiz quiz) throws Exception{
		try {
			QuizFinished quizFinished = new QuizFinished();
			quizFinished.setAccount(account);
			quizFinished.setQuiz(quiz);
			quizFinished.setCreationDate(new Date(Calendar.getInstance().getTimeInMillis()));
			quizFinishedRepository.save(quizFinished);
			return quizFinished;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public ResponseEntity<QuizFinished> deleteById(Integer id) {
		ResponseEntity<QuizFinished> quizEntity = null;
		try {
			Optional<QuizFinished> quizFinished = quizFinishedRepository.findById(id);
			if (quizFinished.isPresent()) {
				quizFinishedRepository.deleteById(id);
				quizEntity = ResponseEntity.ok().build();
			}else {
				quizEntity = ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}return quizEntity;
	}
	
	//Verify Account id by email
	public Integer findAccoutIdByEmail(String email) {
		try {
			Integer accountId = quizFinishedRepository.findResultQueryByEmail(email);
			return !accountId.toString().isEmpty() ? accountId : null;
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	//Verify Quiz id by title
	public Integer findQuizIdByTitle(String title) {
		try {
			Integer quizId = quizFinishedRepository.findResultQueryByTitle(title);
			return !quizId.toString().isEmpty() ? quizId : null;
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw e;
		}
	}
}

