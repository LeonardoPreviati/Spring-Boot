package br.com.empreendedorismo.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
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
	
	public Usuario findById(Integer id) {
		Usuario userEntity = null;
		try {
			Optional<Usuario> optional = userRepository.findById(id);
			if (optional.isPresent()) {
				userEntity = optional.get();
			}else {
				userEntity = (Usuario) new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND).getBody();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}return userEntity;
	}
	
	public Usuario save(UserAccountDTO userAccountDTO) throws Exception {
		try {
			Usuario usuario = new Usuario(userAccountDTO.getName(), userAccountDTO.getEmail(), new BCryptPasswordEncoder().encode(userAccountDTO.getPassword()), new Date(Calendar.getInstance().getTimeInMillis()));
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
			Usuario userModified = findById(id);
			if (!userModified.equals(null)) {
				userModified.setName(userDTO.getName());
				userModified.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
				userModified.setLastUpdateDate(new Date(Calendar.getInstance().getTimeInMillis()));
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
			if (!accountId.equals(null)){
				userRepository.deleteById(id);
				accountController.deleteById(accountId);
				entity = ResponseEntity.ok().build();
			}else {
				entity = ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}return entity;
	}
	
	//Verify User exists
	public Boolean findByExists(Integer id, String email) {
		boolean ret = false;
		try {
			Integer user = userRepository.findByUserExistsQuery(id, email);
			ret = !user.equals(0) ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}return ret;
	}
	
	//Verify Account where email user and account identical
	public Integer findIdAccoutByUser(Integer id) {
		Integer ret = null;
		try {
			Integer userExists = userRepository.findIdAccoutByUserQuery(id);
			ret = !userExists.equals(null) || !userExists.equals("") ? userExists : null;
		} catch (Exception e) {
			e.printStackTrace();
			return ret;
		}return ret.intValue();
	}
	
	public String findUserNameByEmail(String email) {
		try {
			String returnEmail = userRepository.findUserNameByEmailQuery(email);
			return returnEmail;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
