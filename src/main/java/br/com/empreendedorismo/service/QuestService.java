package br.com.empreendedorismo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.empreendedorismo.dto.CategoryDTO;
import br.com.empreendedorismo.dto.QuestDTO;
import br.com.empreendedorismo.entity.Category;
import br.com.empreendedorismo.entity.Quest;
import br.com.empreendedorismo.entity.Quiz;
import br.com.empreendedorismo.respository.QuestRepository;

@Service
public class QuestService {
	
	@Autowired
	private QuestRepository questRepository;
	
	public ResponseEntity<List<Quest>> findAll() {
		 ResponseEntity<List<Quest>> questList = null;
		try {
			if (!questRepository.findAll().isEmpty()){
				questList = ResponseEntity.ok(questRepository.findAll());
			}else {
				questList = ResponseEntity.noContent().build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}return questList;
	}
	
	public Quest save(String description, Quiz question, Category category) {
		Quest quest = new Quest();
		List<Category> list = new ArrayList<Category>();
		list.add(category);
		quest.setDescription(description);
		quest.setQuiz(question);
		quest.setCategory(list);
		return questRepository.save(quest);
	}

}


