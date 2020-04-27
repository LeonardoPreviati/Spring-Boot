package br.com.empreendedorismo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.empreendedorismo.dto.UserCategoryDTO;
import br.com.empreendedorismo.respository.UserCategoryRepository;


@Service
public class UserCategoryService {

	@Autowired
	private UserCategoryRepository userCategoryRepository;
	
	public List<UserCategoryDTO> findAll() {
		return null;//userCategoryRepository.findUserCategory();
	}

}
