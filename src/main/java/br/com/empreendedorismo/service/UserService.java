package br.com.empreendedorismo.service;

import java.math.BigInteger;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import javax.persistence.Query;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	public Usuario findById(Integer id) {
		try {
			Optional<Usuario> optional = userRepository.findById(id);
			return optional.get();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public Usuario save(@Valid UserAccountDTO userAccountDTO) throws Exception {
		Usuario user = null;
		try {
			Usuario usuario = new Usuario();
			usuario.setName(userAccountDTO.getName());
			usuario.setEmail(userAccountDTO.getEmail());
			usuario.setPassword(new BCryptPasswordEncoder().encode(userAccountDTO.getPassword()));
			usuario.setCreationDate(new Date(Calendar.getInstance().getTimeInMillis()));
			Account account = accountController.save(userAccountDTO.getEmail(),userAccountDTO.getAge(),userAccountDTO.getTelephone(), userAccountDTO.getCity());
			usuario.setAccount(account);
			accountRepository.save(account);
			return userRepository.save(usuario);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public Usuario update(Integer id, UserDTO userDTO) {
		try {
			Usuario userModified = findById(id);
			userModified.setName(userDTO.getName());
			userModified.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
			userModified.setLastUpdateDate(new Date(Calendar.getInstance().getTimeInMillis()));
			return userRepository.save(userModified);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
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
			sql.append("SELECT COUNT(*) ");
			sql.append("FROM usuario ");
			sql.append("WHERE (USER_ID = :user_id OR EMAIL = :email)");
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
	public Integer findIdAccoutByUser(Integer id) {
		Integer ret = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT ACC.ACCOUNT_ID 			    ");
			sql.append(" FROM account ACC 				    ");
			sql.append(" JOIN usuario USER 				    ");
			sql.append(" 	ON USER.EMAIL = ACC.USER_EMAIL  ");
			sql.append(" WHERE USER.USER_ID = :user_id 	    ");
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
