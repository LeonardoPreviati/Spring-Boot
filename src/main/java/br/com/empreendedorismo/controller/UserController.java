package br.com.empreendedorismo.controller;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.empreendedorismo.dto.UserAccountDTO;
import br.com.empreendedorismo.dto.UserDTO;
import br.com.empreendedorismo.entity.Usuario;
import br.com.empreendedorismo.service.UserService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Usuario> findAll() throws Exception{
		long startTime = System.currentTimeMillis();
		log.info("UserController.findAll() - BEGIN");
		List<Usuario> listUser = new ArrayList<Usuario>();
		try {
			listUser = userService.findAll();
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("UserController.findAll() - END (" + endTime + "ms)");
		return listUser; 
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Usuario> findById(@PathVariable Integer id){
		long startTime = System.currentTimeMillis();
		log.info("UserController.findById(@PathVariable Integer id) - BEGIN");
		ResponseEntity<Usuario> user = null;
		try {
			user = userService.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("UserController.findById(@PathVariable Integer id) - END (" + endTime + "ms)");
		return user;
	}
	
	@PostMapping
	public ResponseEntity<Usuario> save(@RequestBody @Valid UserAccountDTO userAccountDTO) throws Exception {
		long startTime = System.currentTimeMillis();
		log.info("UserController.save(@RequestBody @Valid UserAccountDTO userAccountDTO) - BEGIN");
		ResponseEntity<Usuario> user = null;
		
		try {
			if (userService.findByExists(null,userAccountDTO.getEmail()).equals(false)) {
				userService.save(userAccountDTO);
				user = new ResponseEntity<Usuario>(HttpStatus.CREATED);
			}else {
				user = new ResponseEntity<Usuario>(HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("UserController.save(@RequestBody @Valid UserAccountDTO userAccountDTO) - END (" + endTime + "ms)");
		return user;
	}
	
	@PutMapping("/{id}")
	@Transactional
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Usuario> update(@PathVariable Integer id, @RequestBody @Valid UserDTO userDTO){
		long startTime = System.currentTimeMillis();
		log.info("UserController.update(@PathVariable Integer id, @RequestBody @Valid UserDTO dto) - BEGIN");
		ResponseEntity<Usuario> user = null;
		try {
			if(findById(id) != null) {
				user = userService.update(id, userDTO);
			}else {
				user = new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("UserController.update(@PathVariable Integer id, @RequestBody @Valid UserDTO dto) - END (" + endTime + "ms)");
		return user;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Integer id) {
		long startTime = System.currentTimeMillis();
		log.info("UserController.deleteById(@PathVariable Integer id) - BEGIN");
		ResponseEntity<?> entity = null;
		try {
			entity = ResponseEntity.ok(userService.deleteById(id));
		} catch (Exception e) {
			entity = ResponseEntity.notFound().build();
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("UserController.deleteById(@PathVariable Integer id) - END (" + endTime + "ms)");
		return entity;
		
	}
}
