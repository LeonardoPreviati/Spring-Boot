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

import br.com.empreendedorismo.dto.AccountDTO;
import br.com.empreendedorismo.dto.UserDTO;
import br.com.empreendedorismo.entity.Account;
import br.com.empreendedorismo.entity.Usuario;
import br.com.empreendedorismo.respository.AccountRepository;
import br.com.empreendedorismo.service.AccountService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Account> findAll() throws Exception{
		long startTime = System.currentTimeMillis();
		log.info("AccountController.findAll() - BEGIN");
		List<Account> listAccount = new ArrayList<Account>();
		try {
			listAccount = accountService.findAll();
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("AccountController.findAll() - END (" + endTime + "ms)");
		return listAccount; 
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Account> findById(@PathVariable Integer id){
		long startTime = System.currentTimeMillis();
		log.info("AccountController.findById(@PathVariable Integer id) - BEGIN");
		ResponseEntity<Account> user = null;
		try {
			user = ResponseEntity.ok(accountService.findById(id));
		} catch (Exception e) {
			user = new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("AccountController.findById(@PathVariable Integer id) - END (" + endTime + "ms)");
		return user;	
	}
	
	@PostMapping
	public Account save(String email, String age, String telephone, String city) throws Exception {
		long startTime = System.currentTimeMillis();
		log.info("AccountController.save(String email, String age, String telephone, String city) - BEGIN");
		Account ret = null;
		try {
			ret = accountService.save(email, age, telephone, city);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("AccountController.save(String email, String age, String telephone, String city) - END (" + endTime + "ms)");
		return ret;
	}
	
	@PutMapping("/{id}")
	@Transactional
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Account update(@PathVariable Integer id, @RequestBody @Valid AccountDTO accountDTO){
		long startTime = System.currentTimeMillis();
		log.info("AccountController.update(@PathVariable Integer id, @RequestBody @Valid AccountDTO accountDTO) - BEGIN");
		Account account = null;
		try {
			if(findById(id) != null) {
				account = accountService.update(id, accountDTO);
			}else {
				account = (Account) ResponseEntity.notFound();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("AccountController.update(@PathVariable Integer id, @RequestBody @Valid AccountDTO accountDTO) - END (" + endTime + "ms)");
		return account;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(Integer id) {
		long startTime = System.currentTimeMillis();
		log.info("AccountController.deleteById(Integer id) - BEGIN");
		ResponseEntity<?> entity = null;
		try {
			accountService.deleteById(id);
			entity = ResponseEntity.ok().build();
		} catch (Exception e) {
			entity = ResponseEntity.notFound().build();
		}long endTime = System.currentTimeMillis() - startTime;
		log.info("AccountController.deleteById(Integer id) - END (" + endTime + "ms)");
		return entity;
		
	}
}
