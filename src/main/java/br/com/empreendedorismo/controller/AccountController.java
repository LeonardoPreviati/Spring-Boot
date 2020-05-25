package br.com.empreendedorismo.controller;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
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
import br.com.empreendedorismo.entity.Account;
import br.com.empreendedorismo.respository.AccountRepository;
import br.com.empreendedorismo.service.AccountService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@GetMapping
	public ResponseEntity<List<Account>> findAll() throws Exception{
		long startTime = System.currentTimeMillis();
		log.info("AccountController.findAll() - BEGIN");
		ResponseEntity<List<Account>> listAccount = null;
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
		Optional<Account> optional = accountRepository.findById(id);
		try {
			if (optional.isPresent()) {
				user = ResponseEntity.ok().body(accountService.findById(id));
			}else {
				user = ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("AccountController.findById(@PathVariable Integer id) - END (" + endTime + "ms)");
		return user;	
	}
	
	@PostMapping
	public Account save(String email, String postalCode, String publicPlace, String neighborhood, String complement, String locale, String uf, Date dateOfBirth, String telephone) throws Exception {
		long startTime = System.currentTimeMillis();
		log.info("AccountController.save(String email, String age, String telephone, String city) - BEGIN");
		Account ret = null;
		try {
			ret = accountService.save(email, postalCode, publicPlace, neighborhood, complement, locale, uf, dateOfBirth, telephone);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("AccountController.save(String email, String age, String telephone, String city) - END (" + endTime + "ms)");
		return ret;
	}
	
	@PutMapping("{id}")
	@Transactional
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Account> update(@PathVariable Integer id, @RequestBody @Valid AccountDTO accountDTO){
		long startTime = System.currentTimeMillis();
		log.info("AccountController.update(@PathVariable Integer id, @RequestBody @Valid AccountDTO accountDTO) - BEGIN");
		ResponseEntity<Account> account = null;
		try {
			if(findById(id) != null) {
				account = accountService.update(id, accountDTO);
			}else {
				account = new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
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
	public ResponseEntity<?> deleteById(@PathVariable Integer id) {
		long startTime = System.currentTimeMillis();
		log.info("AccountController.deleteById(@PathVariable Integer id) - BEGIN");
		ResponseEntity<?> entity = null;
		try {
			entity = accountService.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("AccountController.deleteById(@PathVariable Integer id) - END (" + endTime + "ms)");
		return entity;
		
	}
}
