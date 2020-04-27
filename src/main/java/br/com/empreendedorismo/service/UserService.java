package br.com.empreendedorismo.service;

import java.math.BigInteger;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import javax.persistence.Query;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import br.com.empreendedorismo.configuration.HibernateConfiguration;
import br.com.empreendedorismo.dto.AccountDTO;
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

	public List<Usuario> findAll() {
		return userRepository.findAll();
	}	
	
	public Usuario save(@Valid UserDTO userDTO, @Valid AccountDTO accountDTO) throws Exception {
		Usuario usuario = new Usuario();
		usuario.setName(userDTO.getName());
		usuario.setEmail(userDTO.getEmail());
		usuario.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
		usuario.setCreationDate(new Date(Calendar.getInstance().getTimeInMillis()));
		Account account = new Account();
		account.setId(userDTO.getEmail());
		account.setAge(accountDTO.getAge());
		account.setTelephone(accountDTO.getTelephone());
		account.setCity(accountDTO.getCity());
		account.setCreationDate(new Date(Calendar.getInstance().getTimeInMillis()));
		usuario.setAccount(account);
		accountRepository.save(account);
		return userRepository.save(usuario);
	}
	
	public Usuario update(Integer id, UserDTO userDTO) {
		Usuario userModified = findById(id);
		userModified.setName(userDTO.getName());
		userModified.setEmail(userDTO.getEmail());
		userModified.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
		userModified.setLastUpdateDate(new Date(Calendar.getInstance().getTimeInMillis()));
		return userRepository.save(userModified);
	}
	
	public Usuario findById(Integer id) {
		Optional<Usuario> optional = userRepository.findById(id);
		return optional.get();
	}
	
	public void deleteById(Integer id) {
		Usuario usuario = findById(id);
		usuario.setInactivateDate(new Date(Calendar.getInstance().getTimeInMillis()));
	}
	
	public Boolean findActive(Integer id, String email) {
		boolean ret = false;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT COUNT(*) FROM usuario WHERE (USER_ID = :user_id OR EMAIL = :email) AND INACTIVATE_DATE IS NULL");
			Query q = entityManager().createNativeQuery(sql.toString());
			q.setParameter("user_id", id);
			q.setParameter("email", email);
			ret = ((BigInteger) q.getSingleResult()).longValue() == 0 ? false : true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return ret;
	}
}
