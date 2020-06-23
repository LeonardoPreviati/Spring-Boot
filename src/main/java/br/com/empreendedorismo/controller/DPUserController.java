package br.com.empreendedorismo.controller;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.empreendedorismo.dto.UserAccountDTO;
import br.com.empreendedorismo.dto.DPUserDTO;
import br.com.empreendedorismo.entity.DPUser;
import br.com.empreendedorismo.service.DPUserService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author - Leonardo A. Previati
 * @version - 1.0		
 */
@RestController
@RequestMapping("/dpUser")
@Slf4j
public class DPUserController {
	
	@Autowired
	private DPUserService dpUserService;
	
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
		long startTime = System.currentTimeMillis();
		log.info("DPUserController.findUserNameByEmail(String email) - BEGIN");
		String returnEmail = null;
		try {
			returnEmail = dpUserService.findUserNameByEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("DPUserController.findUserNameByEmail(String email) - END (" + endTime + "ms)");
		return returnEmail;
	}
	
	/**
	 * 
	 * @param confirmationToken - generate token to activate the account
	 * @return user account activation confirmation response
	 */
	@RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public String confirmUserAccount(@RequestParam("token")String confirmationToken){
		long startTime = System.currentTimeMillis();
		log.info("DPUserController.confirmUserAccount(@RequestParam('token')String confirmationToken) - BEGIN");
		String responseConfirm = null;
		try {
			responseConfirm = dpUserService.confirmUserAccont(confirmationToken);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		long endTime = System.currentTimeMillis() - startTime;
		log.info("DPUserController.confirmUserAccount(@RequestParam('token')String confirmationToken) - END (" + endTime + "ms)");
		return responseConfirm;
	}
}
