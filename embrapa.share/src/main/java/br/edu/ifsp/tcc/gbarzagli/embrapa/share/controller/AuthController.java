package br.edu.ifsp.tcc.gbarzagli.embrapa.share.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import br.edu.ifsp.tcc.gbarzagli.embrapa.share.controller.form.AuthForm;
import br.edu.ifsp.tcc.gbarzagli.embrapa.share.model.Researcher;
import br.edu.ifsp.tcc.gbarzagli.embrapa.share.repository.ResearcherRepository;

@Controller
public class AuthController {
	
	@Autowired
	ResearcherRepository researcherRepository;
	
	private static final String WRONG_CREDENTIALS_ERROR = "Your username or password is not correct.";
	
	@GetMapping("login")
	public String index(Model model) {
	    model.addAttribute("form", new AuthForm());
        return "login";
	}
	
	@PostMapping("login")
	public String auth(@ModelAttribute AuthForm form, Model model) {
	    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    HttpSession session = attr.getRequest().getSession();
	    
	    Researcher researcher = researcherRepository.findByUsername(form.getUsername());
	    if (researcher != null) {
	        String userPW = DigestUtils.md5Hex(form.getPassword());
	        if (researcher.getPassword().equals(userPW)) {
	            session.setAttribute("user", researcher);
	            return "redirect:/home";
	        } else {
	            model.addAttribute("form", form);
	            model.addAttribute("message", WRONG_CREDENTIALS_ERROR);
	        }
	    } else {
	        model.addAttribute("form", form);
	        model.addAttribute("message", WRONG_CREDENTIALS_ERROR);
	    }
	    
        return "login";
	}
	
	@GetMapping("signup")
	public String signup(Model model) {
	    model.addAttribute("form", new AuthForm());
	    return "signup";
	}
	
	@PostMapping("signup")
    public String register(@ModelAttribute AuthForm form, Model model) {
        if (form.getName() == null || form.getName().isEmpty()) {
            model.addAttribute("message", "Missing name!");
        } else {
            if (form.getUsername() == null || form.getUsername().isEmpty()) {
                model.addAttribute("message", "Missing e-mail!");
            } else {
                if (form.getPassword() == null || form.getPassword().isEmpty()) {
                    model.addAttribute("message", "Missing password!");
                } else {
                    if (form.getConfirmation() == null || form.getConfirmation().isEmpty()) {
                        model.addAttribute("message", "Missing password confirmation!");
                    } else {
                        if (!form.getPassword().equals(form.getConfirmation())) {
                            model.addAttribute("message", "The passwords that you typed aren't equal!");
                        }
                    }
                }
            }
        }
        
        if (!model.containsAttribute("message")) {
            researcherRepository.save(form.toModel());
            return "redirect:/login";
        }
        
        return "signup";
    }
}
