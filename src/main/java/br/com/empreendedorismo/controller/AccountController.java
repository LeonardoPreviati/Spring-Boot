package br.com.empreendedorismo.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
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

}
