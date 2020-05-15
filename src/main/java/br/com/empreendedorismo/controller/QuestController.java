package br.com.empreendedorismo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.empreendedorismo.dto.CategoryDTO;
import br.com.empreendedorismo.dto.QuestDTO;
import br.com.empreendedorismo.entity.Account;
import br.com.empreendedorismo.entity.Category;
import br.com.empreendedorismo.entity.Quest;
import br.com.empreendedorismo.entity.Quiz;
import br.com.empreendedorismo.respository.CategoryRepository;
import br.com.empreendedorismo.respository.QuestRepository;
import br.com.empreendedorismo.respository.QuizRepository;
import br.com.empreendedorismo.service.QuestService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/quest")
@Slf4j
public class QuestController {
	
	@Autowired
	private QuestService questService;
	
	@Autowired
	private QuizRepository quizRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping
	public ResponseEntity<List<Quest>> findAll() throws Exception{
		long startTime = System.currentTimeMillis();
		log.info("QuestController.findAll() - BEGIN");
		ResponseEntity<List<Quest>> questList = null;
		try {
			questList = questService.findAll();
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("QuestController.findAll() - END (" + endTime + "ms)");
		return questList; 
	}
	
	
	@PostMapping
	@SuppressWarnings("unchecked")
	public ResponseEntity<Quest> save(@RequestBody @Valid QuestDTO dto) {
		try {
			Optional<Quiz> quiz = quizRepository.findById(dto.getQuizId());
			Optional<Category> category = categoryRepository.findById(dto.getCategoryId());
			if (quiz.isPresent() && category.isPresent()) {
				questService.save(dto.getDescription(), quiz.get(), category.get());
				return new ResponseEntity<Quest>(HttpStatus.CREATED);
			}else {
				return new ResponseEntity<Quest>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
