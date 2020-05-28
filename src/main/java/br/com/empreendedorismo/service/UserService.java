package br.com.empreendedorismo.service;

import java.math.BigInteger;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import javax.persistence.Query;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import br.com.empreendedorismo.configuration.HibernateConfiguration;
import br.com.empreendedorismo.controller.AccountController;
import br.com.empreendedorismo.dto.UserAccountDTO;
import br.com.empreendedorismo.dto.UserDTO;
import br.com.empreendedorismo.entity.Account;
import br.com.empreendedorismo.entity.Usuario;
import br.com.empreendedorismo.respository.AccountRepository;
import br.com.empreendedorismo.respository.UserRepository;

@Service
public class UserService extends HibernateConfiguration {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountController accountController;
	

	public List<Usuario> findAll() {
		try {
			return userRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public ResponseEntity<Usuario> findById(Integer id) {
		ResponseEntity<Usuario> userEntity = null;
		try {
			Optional<Usuario> optional = userRepository.findById(id);
			userEntity = ResponseEntity.ok().body(optional.get());
		} catch (Exception e) {
			userEntity = new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
		}return userEntity;
	}
	
	public Usuario save(UserAccountDTO userAccountDTO) throws Exception {
		try {
			Usuario usuario = new Usuario();
			usuario.setName(userAccountDTO.getName());
			usuario.setEmail(userAccountDTO.getEmail());
			usuario.setPassword(new BCryptPasswordEncoder().encode(userAccountDTO.getPassword()));
			usuario.setCreationDate(new Date(Calendar.getInstance().getTimeInMillis()));
			Account account = accountController.save(userAccountDTO.getEmail(),userAccountDTO.getZipCode(), userAccountDTO.getNeighborhood(), userAccountDTO.getCity(), userAccountDTO.getUf(), userAccountDTO.getDateOfBirth(), userAccountDTO.getPhone());
			usuario.setAccount(account);
			accountRepository.save(account);
			userRepository.save(usuario);
			return usuario;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public ResponseEntity<Usuario> update(Integer id, UserDTO userDTO) {
		ResponseEntity<Usuario> userEntity = null;
		try {
			Optional<Usuario> userModified = userRepository.findById(id);
			if (!userModified.equals(null)) {
				userModified.get().setName(userDTO.getName());
				userModified.get().setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
				userModified.get().setLastUpdateDate(new Date(Calendar.getInstance().getTimeInMillis()));
				userRepository.save(userModified);
				userEntity = ResponseEntity.ok().build();
				
			}else {
				userEntity = new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}return userEntity;
	}
	
	public ResponseEntity<?> deleteById(Integer id) {
		ResponseEntity<?> entity = null;
		try {
			Integer accountId = findIdAccoutByUser(id);
			userRepository.deleteById(id);
			accountController.deleteById(accountId);
			entity = ResponseEntity.ok().build();
		} catch (Exception e) {
			entity = ResponseEntity.notFound().build();
		}return entity;
	}
	
	public Boolean findByExists(Integer id, String email) {
		boolean ret = false;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT COUNT(*) 			");
			sql.append(" FROM usuario 				");
			sql.append(" WHERE (USER_ID = :user_id  ");
			sql.append("	OR EMAIL = :email) 		");
			Query q = entityManager().createNativeQuery(sql.toString());
			q.setParameter("user_id", id);
			q.setParameter("email", email);
			ret = ((BigInteger) q.getSingleResult()).longValue() == 0 ? false : true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}return ret;
	}
	
	//Verify Account where email user and account identical
	@SuppressWarnings("rawtypes")
	public Integer findIdAccoutByUser(Integer id) {
		Integer ret = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT ACC.ACCOUNT_ID 			   ");
			sql.append(" FROM account ACC 				   ");
			sql.append(" JOIN usuario USER 				   ");
			sql.append(" 	ON USER.EMAIL = ACC.USER_EMAIL ");
			sql.append(" WHERE USER.USER_ID = :user_id 	   ");
			org.hibernate.query.Query q = getSession().createSQLQuery(sql.toString());
			q.setParameter("user_id", id);
			ret = Integer.parseInt(q.uniqueResult().toString());
			System.out.println(ret);
		} catch (Exception e) {
			e.printStackTrace();
			return ret;
		}return ret.intValue();
	}
}
