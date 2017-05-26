package br.edu.ifsp.tcc.gbarzagli.embrapa.share.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.tcc.gbarzagli.embrapa.share.model.Family;
import br.edu.ifsp.tcc.gbarzagli.embrapa.share.model.Plant;
import br.edu.ifsp.tcc.gbarzagli.embrapa.share.repository.FamilyRepository;
import br.edu.ifsp.tcc.gbarzagli.embrapa.share.repository.PlantRepository;

@RestController
@RequestMapping("/plant")
public class PlantController {

	@Autowired
	PlantRepository plantRepository;
	@Autowired
	FamilyRepository familyRepository;
	
	@RequestMapping(method = RequestMethod.POST)
	public void register(@RequestParam String name, @RequestParam String familyName) {
		
		Family family = familyRepository.findByName(familyName);
		Plant plant = new Plant(name, family);
		plantRepository.save(plant);
		
	}
}
