package br.com.empreendedorismo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import br.com.empreendedorismo.controller.AccountController;
import br.com.empreendedorismo.dto.UserAccountDTO;
import br.com.empreendedorismo.dto.DPUserDTO;
import br.com.empreendedorismo.entity.Account;
import br.com.empreendedorismo.entity.ConfirmationToken;
import br.com.empreendedorismo.entity.DPUser;
import br.com.empreendedorismo.entity.Profile;
import br.com.empreendedorismo.respository.AccountRepository;
import br.com.empreendedorismo.respository.ConfirmationTokenRepository;
import br.com.empreendedorismo.respository.DPUserRepository;
import br.com.empreendedorismo.respository.ProfileRespository;
import br.com.empreendedorismo.util.ConstantsUtil;
import br.com.empreendedorismo.util.EmailUtil;

@Service
public class DPUserService  {

	@Autowired
	private DPUserRepository dpUserRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private ProfileRespository profileRepository;
	
	@Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

	@Autowired
	private AccountController accountController;
	

	public List<DPUser> findAll() {
		try {
			return dpUserRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public DPUser findById(Integer id) {
		DPUser userEntity = null;
		try {
			Optional<DPUser> optional = dpUserRepository.findById(id);
			if (optional.isPresent()) {
				userEntity = optional.get();
			}else {
				userEntity = (DPUser) new ResponseEntity<DPUser>(HttpStatus.NOT_FOUND).getBody();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}return userEntity;
	}
	
	public DPUser save(UserAccountDTO userAccountDTO) throws Exception {
		try {
			DPUser user = new DPUser();
			List<Profile> profileList = new ArrayList<Profile>();
			user.setName(userAccountDTO.getName());
			user.setEmail(userAccountDTO.getEmail());
			Profile profile = saveProfile(userAccountDTO);
			profileList.add(profile);
            user.setProfile(profileList);
            user.setPassword(userAccountDTO.getPassword());
            user.setCreationDate(new Date());
			Account account = accountController.save(userAccountDTO.getEmail(),userAccountDTO.getZipCode(), userAccountDTO.getNeighborhood(), userAccountDTO.getCity(), userAccountDTO.getUf(), userAccountDTO.getDateOfBirth(), userAccountDTO.getPhone());
			user.setAccount(account);
			accountRepository.save(account);
			dpUserRepository.save(user);
			ConfirmationToken confirmationToken = new ConfirmationToken();
			confirmationToken.setUser(user);
			confirmationTokenRepository.save(confirmationToken);
            EmailUtil activeAccount = new EmailUtil();
			activeAccount.sendEmail(user, confirmationToken.getToken().toString());
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public Profile saveProfile(UserAccountDTO userAccountDTO) {
		Profile profile;
		try {
			if (userAccountDTO.getPassword().equals(ConstantsUtil.PASSWORD_DISCOVER_PROFILE_ENCODE)) {
				profile = profileRepository.findProfileByName(ConstantsUtil.DISCOVER_PROFILE);
			}else if (userAccountDTO.getPassword().equals(ConstantsUtil.PASSWORD_ADMIN_ENCODE)) {
				profile = profileRepository.findProfileByName(ConstantsUtil.ADMIN);
			}else {
				profile = profileRepository.findProfileByName(ConstantsUtil.USER);
			}return profile;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public ResponseEntity<DPUser> update(Integer id, DPUserDTO dpUserDTO) {
		ResponseEntity<DPUser> userEntity = null;
		try {
			DPUser userModified = findById(id);
			if (!userModified.equals(null)) {
				userModified.setName(dpUserDTO.getName());
				userModified.setPassword(new BCryptPasswordEncoder().encode(dpUserDTO.getPassword()));
				userModified.setLastUpdateDate(new Date());
				dpUserRepository.save(userModified);
				userEntity = ResponseEntity.ok().build();
			}else {
				userEntity = new ResponseEntity<DPUser>(HttpStatus.NOT_FOUND);
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
				dpUserRepository.deleteById(id);
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
			Integer user = dpUserRepository.findByUserExistsQuery(id, email);
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
			Integer userExists = dpUserRepository.findIdAccoutByUserQuery(id);
			ret = !userExists.equals(null) || !userExists.equals("") ? userExists : null;
		} catch (Exception e) {
			e.printStackTrace();
			return ret;
		}return ret.intValue();
	}
	
	public String findUserNameByEmail(String email) {
		try {
			String returnEmail = dpUserRepository.findUserNameByEmailQuery(email);
			return returnEmail;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public String confirmUserAccont(String confirmationToken) {
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
		String ret = "Link de cadastrado inválido ou já utilizado";
		try {
			 if(token != null) {
	        	DPUser user = dpUserRepository.findByEmailIgnoreCase(token.getUser().getEmail());
	            user.setEnabled(true);
	            dpUserRepository.save(user);
	            if (user.isEnabled()) {
	            	ret = ConstantsUtil.USER_ACCOUNT_REGISTERED;
	            }
			 }return ret;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
