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
	@SuppressWarnings("rawtypes")
	public Integer findIdAccoutByEmail(String email) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT ACC.ACCOUNT_ID FROM account ACC	WHERE ACC.USER_EMAIL = :email ");
			org.hibernate.query.Query q = getSession().createSQLQuery(sql.toString());
			q.setParameter("email", email);
			List query = q.list();
			return !query.isEmpty() ? Integer.parseInt(q.uniqueResult().toString()) : 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	//Verify Quiz id by title
	@SuppressWarnings("rawtypes")
	public Integer findIdQuizByTitle(String title) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT Q.QUIZ_ID FROM quiz Q WHERE Q.TITLE = :title ");
			org.hibernate.query.Query q = getSession().createSQLQuery(sql.toString());
			q.setParameter("title", title);
			List query = q.list();
			return !query.isEmpty() ? Integer.parseInt(q.uniqueResult().toString()) : 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}

