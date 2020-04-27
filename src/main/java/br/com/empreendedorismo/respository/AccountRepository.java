package br.com.empreendedorismo.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.empreendedorismo.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>{

}
