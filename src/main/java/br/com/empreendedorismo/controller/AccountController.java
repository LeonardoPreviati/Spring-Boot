package br.com.empreendedorismo.controller;

import java.sql.Date;
import java.util.List;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import br.com.empreendedorismo.dto.AccountDTO;
import br.com.empreendedorismo.entity.Account;
import br.com.empreendedorismo.service.AccountService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/account")
@Slf4j

/**
 * @author - Leonardo A. Previati
 * @version - 1.0		
 */
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	/**
	 * @return all accounts
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Account>> findAll() throws Exception{
		long startTime = System.currentTimeMillis();
		log.info("AccountController.findAll() - BEGIN");
		ResponseEntity<List<Account>> statusAccountList;
		List<Account> accountList;
		try {
			accountList = accountService.findAll();
			if (!accountList.isEmpty()) {
				statusAccountList = ResponseEntity.ok().body(accountList);
			}else {
				statusAccountList = ResponseEntity.notFound().build();
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("AccountController.findAll() - END (" + endTime + "ms)");
		return statusAccountList; 
	}
	
	/**
	 * @return a specific account
	 */
	@RequestMapping(value = "{accountId}", method = RequestMethod.GET)
	public ResponseEntity<Account> findById(@PathVariable Integer accountId){
		long startTime = System.currentTimeMillis();
		log.info("AccountController.findById(@PathVariable Integer accountId) - BEGIN");
		ResponseEntity<Account> accountResponse = null;
		Account account;
		try {
			account = accountService.findById(accountId);
			if (!account.equals(null)) {
				accountResponse = ResponseEntity.ok().body(account);
			}
		} catch (Exception e) {
			accountResponse = ResponseEntity.notFound().build();
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("AccountController.findById(@PathVariable Integer accountId) - END (" + endTime + "ms)");
		return accountResponse;	
	}
	
	public Account save(String email, String zipCode, String neighborhood, String city, String uf, Date dateOfBirth, String phone) throws Exception {
		long startTime = System.currentTimeMillis();
		log.info("AccountController.save(String email, String age, String telephone, String city) - BEGIN");
		Account ret;
		try {
			ret = accountService.save(email, zipCode, neighborhood, city, uf, dateOfBirth, phone);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("AccountController.save(String email, String age, String telephone, String city) - END (" + endTime + "ms)");
		return ret;
	}
	
	/**
	 * @param accountDTO - data pattern for updating an account
	 * @return the updated account
	 */
	@RequestMapping(value = "{accountId}", method = RequestMethod.PUT)
	@Transactional
	public ResponseEntity<Account> update(@PathVariable Integer accountId, @RequestBody @Valid AccountDTO accountDTO){
		long startTime = System.currentTimeMillis();
		log.info("AccountController.update(@PathVariable Integer accountId, @RequestBody @Valid AccountDTO accountDTO) - BEGIN");
		ResponseEntity<Account> account = null;
		try {
			if(!findById(accountId).equals(null)) {
				account = ResponseEntity.ok().body(accountService.update(accountId, accountDTO));
			}else {
				account = ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("AccountController.update(@PathVariable Integer accountId, @RequestBody @Valid AccountDTO accountDTO) - END (" + endTime + "ms)");
		return account;
	}
	
	/**
	 * delete a specific account
	 */
	@RequestMapping(value = "{accountId}", method = RequestMethod.DELETE)
	public ResponseEntity<Account> deleteById(@PathVariable Integer accountId) {
		long startTime = System.currentTimeMillis();
		log.info("AccountController.deleteById(@PathVariable Integer accountId) - BEGIN");
		ResponseEntity<Account> entity = null;
		try {
			entity = accountService.deleteById(accountId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("AccountController.deleteById(@PathVariable Integer accountId) - END (" + endTime + "ms)");
		return entity;
		
	}
}
