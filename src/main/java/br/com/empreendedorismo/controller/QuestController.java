package br.com.empreendedorismo.controller;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import br.com.empreendedorismo.dto.QuestDTO;
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
/**
 * @author - Leonardo A. Previati
 * @version - 1.0		
 */
public class QuestController {
	
	@Autowired
	private QuestService questService;
	
	@Autowired
	private QuizRepository quizRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private QuestRepository questRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Quest>> findAll() throws Exception{
		long startTime = System.currentTimeMillis();
		log.info("QuestController.findAll() - BEGIN");
		ResponseEntity<List<Quest>> questListResponse = null;
		List<Quest> questList;
		try {
			questList = questService.findAll();
			if (!questList.isEmpty()){
				questListResponse = ResponseEntity.ok(questList);
			}else {
				questListResponse = ResponseEntity.notFound().build();
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("QuestController.findAll() - END (" + endTime + "ms)");
		return questListResponse; 
	}
	
	@RequestMapping(value = "{questId}", method = RequestMethod.GET)
	public ResponseEntity<Quest> findById(@PathVariable Integer questId){
		long startTime = System.currentTimeMillis();
		log.info("QuestController.findById(@PathVariable Integer questId) - BEGIN");
		ResponseEntity<Quest> questResponse = null;
		Quest quest;
		try {
			quest = questService.findById(questId);
			if (!quest.equals(null)) {
				questResponse = ResponseEntity.ok().body(quest);
			}
		} catch (Exception e) {
			questResponse = ResponseEntity.notFound().build();
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("QuestController.findById(@PathVariable Integer questId) - END (" + endTime + "ms)");
		return questResponse;	
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Quest> save(@RequestBody @Valid QuestDTO questDTO) {
		ResponseEntity<Quest> questResponse = null;
		long startTime = System.currentTimeMillis();
		log.info("QuestController.save(@RequestBody @Valid QuestDTO questDTO) - BEGIN");
		try {
			Optional<Quiz> quiz = quizRepository.findQuizByTitle(questDTO.getQuizTitle());
			Optional<Category> category = categoryRepository.findCategoryByCod(questDTO.getCodCategory());
			if (quiz.isPresent() && category.isPresent()) {
				questService.save(questDTO.getQuestDescription(), quiz.get(), category.get());
				questResponse = new ResponseEntity<Quest>(HttpStatus.CREATED);
			}else {
				questResponse = ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("QuestController.save(@RequestBody @Valid QuestDTO questDTO) - END (" + endTime + "ms)");
		return questResponse;
	}
	

	@RequestMapping(value = "{accountId}", method = RequestMethod.PUT)
	@Transactional
	public ResponseEntity<Quest> update(@PathVariable Integer questId, @RequestBody String description){
		long startTime = System.currentTimeMillis();
		log.info("QuestController.update(@PathVariable Integer questId, @RequestBody @Valid QuestDTO questDTO) - BEGIN");
		ResponseEntity<Quest> questResponse = null;
		Optional<Quest> accountModified = questRepository.findById(questId);
		try {
			if (accountModified.isPresent()) {
				questService.update(questId, description);
				questResponse = ResponseEntity.ok().build();
			}else {
				questResponse = ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("QuestController.update(@PathVariable Integer questId, @RequestBody @Valid QuestDTO questDTO) - END (" + endTime + "ms)");
		return questResponse;
	}

}
