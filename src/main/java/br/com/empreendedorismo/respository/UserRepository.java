package br.com.empreendedorismo.respository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.empreendedorismo.entity.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Integer>{
	
	public Optional<Usuario> findByEmail(String email);

	@Query(value = " SELECT COUNT(*) 		   "
				 + " FROM usuario 	           "
				 + " WHERE (USER_ID = :userId  "
				 + " 	OR EMAIL = :email) 	   ", nativeQuery = true)
	public Integer findByUserExistsQuery (Integer userId, String email);
	
	@Query(value = " SELECT ACC.ACCOUNT_ID 			   "
				 + " FROM account ACC 				   "
				 + " JOIN usuario USER 				   "
				 + " 	ON USER.EMAIL = ACC.USER_EMAIL "
				 + " WHERE USER.USER_ID = :userId 	   ", nativeQuery = true)
	public Integer findIdAccoutByUserQuery (Integer userId);
	
	@Query(value = " SELECT U.NAME  		"
				 + " FROM usuario U 		"
				 + " WHERE U.EMAIL = :email ", nativeQuery = true)
	public String findUserNameByEmailQuery (String email);

	
}
