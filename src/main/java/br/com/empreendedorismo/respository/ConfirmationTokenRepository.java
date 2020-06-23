package br.com.empreendedorismo.respository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import br.com.empreendedorismo.entity.ConfirmationToken;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {
    
	@Query(value = " SELECT * FROM confirmation_token ct where ct.confirmaton_token = :confirmationToken ", nativeQuery = true)
	public ConfirmationToken findByConfirmationToken(String confirmationToken);
}