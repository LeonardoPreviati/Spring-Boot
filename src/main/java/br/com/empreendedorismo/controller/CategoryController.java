package br.com.empreendedorismo.controller;

import java.util.List;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import br.com.empreendedorismo.dto.CategoryDTO;
import br.com.empreendedorismo.entity.Category;
import br.com.empreendedorismo.service.CategoryService;
import java.util.HashMap;

@RestController
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@SuppressWarnings("unused")
	@Autowired
	private ModelMapper modelMapper;
	
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<HashMap<String, Object>> findAll() {
		return categoryService.findAll();
	}
		
	
	
		
		/*List<Category> category;// = new ArrayList<Category>();
		category = categoryService.findAll();
		return category;
		if ((!category.equals(0))  || (!category.equals(null))) {
			return (List<Category>)category;
		}else {
			return (List<Category>) new ResponseEntity(HttpStatus.NOT_FOUND);
		}*/
		
	
	
	@GetMapping("/findById/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Category findById(@PathVariable String id){
		try {
			Category category = categoryService.findById(Integer.parseInt(id));
			return category;
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Entrada inválida");
		}
	}
	
	@PostMapping("/created")
	public ResponseEntity<Category> save(@RequestBody @Valid CategoryDTO dto) {
		try {
			categoryService.save(dto);
			return new ResponseEntity<Category>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Category>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<Category> deleteById(@PathVariable Integer id){
		if(!categoryService.deleteById(id).equals(null)) {
			return new ResponseEntity<Category>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Category>(HttpStatus.NO_CONTENT);
		}
	}

}
