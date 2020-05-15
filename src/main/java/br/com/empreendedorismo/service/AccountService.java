package br.com.empreendedorismo.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.empreendedorismo.dto.AccountDTO;
import br.com.empreendedorismo.dto.UserDTO;
import br.com.empreendedorismo.entity.Account;
import br.com.empreendedorismo.entity.QuizFinished;
import br.com.empreendedorismo.entity.Usuario;
import br.com.empreendedorismo.respository.AccountRepository;


@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountService accountService;
	
	public ResponseEntity<List<Account>> findAll() {
		 ResponseEntity<List<Account>> accountList = null;
		try {
			if (!accountRepository.findAll().isEmpty()){
				accountList = ResponseEntity.ok(accountRepository.findAll());
			}else {
				accountList = ResponseEntity.noContent().build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}return accountList;
	}
	
	public Account findById(Integer id) {
		Account entityAccount = null;
		try {
			Optional<Account> optional = accountRepository.findById(id);
			if (optional.isPresent()) {
				entityAccount = optional.get();
			}else {
				entityAccount = (Account) new ResponseEntity<Account>(HttpStatus.NOT_FOUND).getBody();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}return entityAccount;
	}
	
	public Account save(String email, String age, String telephone, String city)  {
		try {
			Account account = new Account();
			account.setUserEmail(email);
			account.setAge(age);
			account.setTelephone(telephone);
			account.setCity(city);
			account.setCreationDate(new Date(Calendar.getInstance().getTimeInMillis()));
			return account;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public ResponseEntity<Account> update(Integer id, AccountDTO accountDTO) {
		ResponseEntity<Account> account = null;
		try {
			Account accountModified = accountService.findById(id);
			if (!accountModified.equals(null)) {
				accountModified.setAge(accountDTO.getAge());
				accountModified.setTelephone(accountDTO.getTelephone());
				accountModified.setCity(accountDTO.getCity());
				accountModified.setLastUpdateDate(new Date(Calendar.getInstance().getTimeInMillis()));
				accountRepository.save(accountModified);
				account = ResponseEntity.ok().build();
			}else {
				account = ResponseEntity.notFound().build();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}return account;
	}
	
	public ResponseEntity<Account> deleteById(Integer id) {
		ResponseEntity<Account> entity = null;
		try {
			Optional<Account> account = accountRepository.findById(id);
			if (account.isPresent()) {
				accountRepository.deleteById(id);
				entity = ResponseEntity.ok().build();
			}else {
				entity = ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}return entity;
	}
}


