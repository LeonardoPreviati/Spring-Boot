package br.com.empreendedorismo.service;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.catalina.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import br.com.empreendedorismo.configuration.HibernateConfiguration;
import br.com.empreendedorismo.dto.CategoryDTO;
import br.com.empreendedorismo.entity.Category;
import br.com.empreendedorismo.entity.Usuario;

import br.com.empreendedorismo.respository.CategoryRepository;

@Service
public class CategoryService extends HibernateConfiguration{
	
	
	
	@Autowired
	private CategoryRepository categoryRepository;

	public Category save(CategoryDTO dto) {
		Category category = new Category();
		//category.setName(dto.getName());
		category.setDescription(dto.getDescription());
		//category.setMovieUrl(dto.getMovieUrl());
		return categoryRepository.save(category);
	}
	
	
   
	
	public List<HashMap<String, Object>> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT 	c.category_id, c.user_id FROM user_category c ");
		Query q = getSession().createSQLQuery(sql.toString()).
				setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return q.getResultList();
	}
		
		
	
	public Category findById(Integer id) {
		try {
			Optional<Category> optional = categoryRepository.findById(id);
			return optional.get();
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("Categoria Inexistente");
		}
		
	}

	public Category deleteById(Integer id) {
		Optional<Category> optional = categoryRepository.findById(id);
		return optional.get();
	}
	
}
