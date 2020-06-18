package br.com.empreendedorismo.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.empreendedorismo.entity.Profile;

@Repository
public interface ProfileRespository extends JpaRepository<Profile, Integer>{
	
	@Query(value = " SELECT * 			  "
		 	 	 + " FROM profile P		  "
		 	 	 + " WHERE P.NAME = :name ", nativeQuery = true)
	public Profile findProfileByName (String name);

}
