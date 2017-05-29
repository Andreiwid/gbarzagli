package br.edu.ifsp.tcc.gbarzagli.embrapa.share.controller;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.tcc.gbarzagli.embrapa.share.model.Authorization;
import br.edu.ifsp.tcc.gbarzagli.embrapa.share.model.Researcher;
import br.edu.ifsp.tcc.gbarzagli.embrapa.share.repository.ResearcherRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	ResearcherRepository researcherRepository;
	
	/**
	 * Authenticate the researcher
	 * @param username
	 * @param password
	 * @return a authorization to use the system containing the token and an expiration time
	 */
	@RequestMapping(
			method  = {
					RequestMethod.POST, 
					RequestMethod.GET 
			}
	)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Authorization authenticate(@RequestParam String username, @RequestParam String password) {
		Authorization auth = null;
		
		Researcher researcher = researcherRepository.findByUsername(username);
		if (researcher != null && researcher.getPassword().equals(password)) {
			Date today = Calendar.getInstance().getTime();
			
			// Expiration time algorithm
			Calendar validThru = Calendar.getInstance();
			validThru.add(Calendar.MINUTE, 30);
			Date validDate = validThru.getTime();
			long expiration = today.getTime() + validDate.getTime();
		
			// Token algorithm
			String hexDate = Long.toHexString(today.getTime());
			hexDate = researcher.getUsername() + hexDate;
			byte[] decodedToken = Base64.getDecoder().decode(hexDate);
			byte[] encodedToken = Base64.getEncoder().encode(decodedToken);
			String token = Base64.getUrlEncoder().encodeToString(encodedToken);
			
			auth = new Authorization(token, expiration);
		}
		
		return auth;
	}

}
