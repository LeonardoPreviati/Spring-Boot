package br.com.empreendedorismo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.empreendedorismo.entity.Account;
import br.com.empreendedorismo.respository.AccountRepository;


@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	public List<Account> findAll() {
		return accountRepository.findAll();
	}

}
