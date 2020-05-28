package br.com.empreendedorismo.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import br.com.empreendedorismo.entity.QuizResults;
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
	
	@RequestMapping(value = "{accountId}", method = RequestMethod.GET)
	public HashMap<String, Double> findResultsById(@PathVariable Integer accountId) throws Exception{
		long startTime = System.currentTimeMillis();
		log.info("QuizResultsController.findResultsById() - BEGIN");
		HashMap<String, Double> listQuizResults = null;
		try {
			listQuizResults = quizResultsService.findResultsById(accountId);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("QuizResultsController.findResultsById() - END (" + endTime + "ms)");
		return listQuizResults; 
	}
}
