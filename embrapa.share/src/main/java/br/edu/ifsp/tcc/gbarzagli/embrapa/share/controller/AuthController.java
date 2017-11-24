package br.edu.ifsp.tcc.gbarzagli.embrapa.share.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifsp.tcc.gbarzagli.embrapa.share.repository.ResearcherRepository;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	ResearcherRepository researcherRepository;
	
//	private static ApplicationContext context; 
//	static {
//		context = new AnnotationConfigApplicationContext(BeanConfiguration.class);
//	}
	
	@RequestMapping("")
	public String index() {
        return "login";
	}
	
	@RequestMapping("/signup")
	public String signup() {
	    return "signup";
	}
}
