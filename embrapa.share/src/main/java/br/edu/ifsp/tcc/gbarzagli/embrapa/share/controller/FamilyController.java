package br.edu.ifsp.tcc.gbarzagli.embrapa.share.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.tcc.gbarzagli.embrapa.share.model.Family;
import br.edu.ifsp.tcc.gbarzagli.embrapa.share.repository.FamilyRepository;

@RestController
@RequestMapping("/family")
public class FamilyController {

	@Autowired
	FamilyRepository familyRepository;
	
	@RequestMapping(method = RequestMethod.POST)
	public Long register(@RequestParam String name) {
		
		Family family = new Family(name);
		familyRepository.save(family);
		return family.getId();
		
	}
}
