package br.com.empreendedorismo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.empreendedorismo.dto.UserAccountDTO;
import br.com.empreendedorismo.dto.DPUserDTO;
import br.com.empreendedorismo.entity.ConfirmationToken;
import br.com.empreendedorismo.entity.DPUser;
import br.com.empreendedorismo.respository.ConfirmationTokenRepository;
import br.com.empreendedorismo.respository.DPUserRepository;
import br.com.empreendedorismo.service.DPUserService;
import br.com.empreendedorismo.utils.Constants;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/dpUser")
@Slf4j
/**
 * @author - Leonardo A. Previati
 * @version - 1.0		
 */
public class DPUserController {
	
	@Autowired
	private DPUserService dpUserService;
	
	@Autowired
	private DPUserRepository dpUserRepository;

	
	@Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
	
	/**
	 * @return all users
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<DPUser> findAll() throws Exception{
		long startTime = System.currentTimeMillis();
		log.info("DPUserController.findAll() - BEGIN");
		List<DPUser> listUser = new ArrayList<DPUser>();
		try {
			listUser = dpUserService.findAll();
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("DPUserController.findAll() - END (" + endTime + "ms)");
		return listUser; 
	}
	
	/**
	 * @return a specific user by userId
	 */
	@RequestMapping(value = "{userId}", method = RequestMethod.GET)
	public ResponseEntity<DPUser> findById(@PathVariable Integer userId){
		long startTime = System.currentTimeMillis();
		log.info("DPUserController.findById(@PathVariable Integer userId) - BEGIN");
		ResponseEntity<DPUser> user = null;
		DPUser optional = dpUserService.findById(userId);
		try {
			if (!optional.equals(null)) {
				user = ResponseEntity.ok().build();
			}else {
				user = ResponseEntity.notFound().build();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("DPUserController.findById(@PathVariable Integer userId) - END (" + endTime + "ms)");
		return user;
	}
	/**
	 * @param userAccountDTO - data pattern for create an user and account
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<DPUser> save(@RequestBody @Valid UserAccountDTO userAccountDTO) throws Exception {
		long startTime = System.currentTimeMillis();
		log.info("DPUserController.save(@RequestBody @Valid UserAccountDTO userAccountDTO) - BEGIN");
		ResponseEntity<DPUser> user = null;
		
		try {
			if (dpUserService.findByExists(null,userAccountDTO.getEmail()).equals(false)) {
				dpUserService.save(userAccountDTO);
				user = new ResponseEntity<DPUser>(HttpStatus.CREATED);
			}else {
				user = new ResponseEntity<DPUser>(HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("DPUserController.save(@RequestBody @Valid UserAccountDTO userAccountDTO) - END (" + endTime + "ms)");
		return user;
	}
	/**
	 * @param dpUserDTO - data pattern for updating an user
	 * @return the updated user
	 */
	@RequestMapping(value = "{userId}", method = RequestMethod.PUT)
	@Transactional
	public ResponseEntity<DPUser> update(@PathVariable Integer userId, @RequestBody @Valid DPUserDTO dpUserDTO){
		long startTime = System.currentTimeMillis();
		log.info("DPUserController.update(@PathVariable Integer userId, @RequestBody @Valid DPUserDTO dto) - BEGIN");
		ResponseEntity<DPUser> user = null;
		try {
			if(findById(userId) != null) {
				user = dpUserService.update(userId, dpUserDTO);
			}else {
				user = new ResponseEntity<DPUser>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("DPUserController.update(@PathVariable Integer userId, @RequestBody @Valid DPUserDTO dto) - END (" + endTime + "ms)");
		return user;
	}
	
	/**
	 * delete a specific user by userId
	 */
	@RequestMapping(value = "{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteById(@PathVariable Integer userId) {
		long startTime = System.currentTimeMillis();
		log.info("DPUserController.deleteById(@PathVariable Integer userId) - BEGIN");
		ResponseEntity<?> entity = null;
		try {
			entity = ResponseEntity.ok(dpUserService.deleteById(userId));
		} catch (Exception e) {
			entity = ResponseEntity.notFound().build();
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("DPUserController.deleteById(@PathVariable Integer userId) - END (" + endTime + "ms)");
		return entity;
		
	}
	
	/**
	 * @return name for a specific user by email
	 */
	public String findUserNameByEmail(String email) {
		try {
			String returnEmail = dpUserService.findUserNameByEmail(email);
			return returnEmail;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public String confirmUserAccount(@RequestParam("token")String confirmationToken)
    {
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if(token != null) {
        	DPUser user = dpUserRepository.findByEmailIgnoreCase(token.getUser().getEmail());
            user.setEnabled(true);
            dpUserRepository.save(user);
        }return "Conta registrada com sucesso";
        
        
    }
}
