package br.com.empreendedorismo.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import br.com.empreendedorismo.dto.AccountDTO;
import br.com.empreendedorismo.entity.Account;
import br.com.empreendedorismo.respository.AccountRepository;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountService accountService;
	
	public List<Account> findAll() {
		 List<Account> accountList = null;
		try {
			accountList = accountRepository.findAll();
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
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}return entityAccount;
	}
	
	public Account save(String email, String zipCode, String neighborhood, String locale, String uf, Date dateOfBirth, String phone)  {
		try {
			Account account = new Account(email,zipCode, neighborhood, locale, uf, dateOfBirth, phone, new Date());
			return account;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public Account update(Integer id, AccountDTO accountDTO) {
		try {
			Account accountModified = accountService.findById(id);
			accountModified.setZipCode(accountDTO.getZipCode());
			accountModified.setNeighborhood(accountDTO.getNeighborhood());
			accountModified.setCity(accountDTO.getCity());
			accountModified.setUf(accountDTO.getUf());
			accountModified.setDateOfBirth(accountDTO.getDateOfBirth());
			accountModified.setPhone(accountDTO.getPhone());
			accountModified.setLastUpdateDate(new Date(Calendar.getInstance().getTimeInMillis()));
			accountRepository.save(accountModified);
			return accountModified;	
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public ResponseEntity<Account> deleteById(Integer id) {
		ResponseEntity<Account> entity = null;
		try {
			Optional<Account> account = accountRepository.findById(id);
			if (account.isPresent()) {
				accountRepository.deleteById(id);
				entity = ResponseEntity.noContent().build();
			}else {
				entity = ResponseEntity.notFound().build();
			}return entity;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}


