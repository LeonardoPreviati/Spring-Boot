package br.com.empreendedorismo.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.empreendedorismo.dto.AccountDTO;
import br.com.empreendedorismo.dto.CategoryDTO;
import br.com.empreendedorismo.dto.QuestDTO;
import br.com.empreendedorismo.entity.Account;
import br.com.empreendedorismo.entity.Category;
import br.com.empreendedorismo.entity.Quest;
import br.com.empreendedorismo.entity.Quiz;
import br.com.empreendedorismo.respository.QuestRepository;

@Service
public class QuestService {
	
	@Autowired
	private QuestRepository questRepository;
	
	public List<Quest> findAll() {
		 List<Quest> questList = null;
		try {
			questList = questRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}return questList;
	}
	
	
	public Quest findById(Integer id) {
		Quest entityQuest = null;
		try {
			Optional<Quest> optional = questRepository.findById(id);
			if (optional.isPresent()) {
				entityQuest = optional.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}return entityQuest;
	}
	
	public Quest save(String description, Quiz question, Category category) {
		Quest quest = new Quest();
		Set<Category> list = new HashSet<>();
		list.add(category);
		quest.setDescription(description);
		quest.setQuiz(question);
		quest.setCategory(list);
		return questRepository.save(quest);
	}
	
	@SuppressWarnings("null")
	public Quest update(Integer id, String description) {
		Quest quest = null;
		try {
			quest.setId(id);
			quest.setDescription(description);
			quest.setLastUpdateDate(new Date(Calendar.getInstance().getTimeInMillis()));
			questRepository.save(quest);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}return quest;
	}

}


