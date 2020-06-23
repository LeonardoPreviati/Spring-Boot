package br.com.empreendedorismo.service;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.persistence.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.empreendedorismo.configuration.HibernateConfiguration;
import br.com.empreendedorismo.dto.CategoryDTO;
import br.com.empreendedorismo.entity.Category;
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
	
	
   
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<HashMap<String, Object>> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT account_id,quest_id,category_id FROM quiz_results c order by quest_id asc");
		Query q = getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
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
