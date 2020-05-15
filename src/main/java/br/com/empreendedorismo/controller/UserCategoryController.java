package br.com.empreendedorismo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.empreendedorismo.dto.UserCategoryDTO;
import br.com.empreendedorismo.service.UserCategoryService;

@RestController
@RequestMapping("/userCategory")
public class UserCategoryController {

	
	@Autowired
	private UserCategoryService userCategoryService;
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unlikely-arg-type" })
	@GetMapping("/findAll")
	@ResponseStatus(HttpStatus.OK)
	public List<UserCategoryDTO> findAll() {
		List userCategory;// = new ArrayList<Category>();
		userCategory = userCategoryService.findAll();
		if ((!userCategory.equals(0))  || (!userCategory.equals(null))) {
			return userCategory;
		}else {
			return (List) new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}
}
