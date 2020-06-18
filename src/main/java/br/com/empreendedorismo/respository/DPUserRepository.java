package br.com.empreendedorismo.respository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.empreendedorismo.entity.DPUser;

@Repository
public interface DPUserRepository extends JpaRepository<DPUser, Integer>{
	
	public Optional<DPUser> findByEmail(String email);

	@Query(value = " SELECT COUNT(U.USER_ID)       "
				 + " FROM dp_user U	       	       "
				 + " JOIN account ACC		       "
				 + "   ON U.EMAIL = ACC.EMAIL      "
				 + " WHERE (U.USER_ID = :userId    "
				 + " 	OR U.EMAIL = :email) 	   ", nativeQuery = true)
	public Integer findByUserExistsQuery (Integer userId, String email);
	
	@Query(value = " SELECT ACC.ACCOUNT_ID 			"
				 + " FROM account ACC 				"
				 + " JOIN dp_user U 				"
				 + " 	ON U.EMAIL = ACC.EMAIL "
				 + " WHERE U.USER_ID = :userId 	    ", nativeQuery = true)
	public Integer findIdAccoutByUserQuery (Integer userId);
	
	@Query(value = " SELECT U.NAME       		   "
			 	 + " FROM dp_user U	       	       "
			     + " JOIN account ACC		       "
			     + "   ON U.EMAIL = ACC.EMAIL "
			     + " WHERE U.EMAIL = :email 	   ", nativeQuery = true)
	public String findUserNameByEmailQuery (String email);

	
}
