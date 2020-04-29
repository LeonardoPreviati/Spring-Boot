package br.com.empreendedorismo.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.empreendedorismo.dto.AccountDTO;
import br.com.empreendedorismo.dto.UserDTO;
import br.com.empreendedorismo.entity.Account;
import br.com.empreendedorismo.entity.Usuario;
import br.com.empreendedorismo.respository.AccountRepository;


@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	public List<Account> findAll() {
		try {
			return accountRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public Account findById(Integer id) {
		try {
			Optional<Account> optional = accountRepository.findById(id);
			return optional.get();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
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
	
	public Account update(Integer id, AccountDTO accountDTO) {
		try {
			Account accountModified = findById(id);
			accountModified.setAge(accountDTO.getAge());
			accountModified.setTelephone(accountDTO.getTelephone());
			accountModified.setCity(accountDTO.getCity());
			accountModified.setLastUpdateDate(new Date(Calendar.getInstance().getTimeInMillis()));
			return accountModified;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public ResponseEntity<?> deleteById(Integer id) {
		ResponseEntity<?> entity = null;
		try {
			accountRepository.deleteById(id);
			entity = ResponseEntity.ok().build();
		} catch (Exception e) {
			entity = ResponseEntity.notFound().build();
		}return entity;
	}
}


