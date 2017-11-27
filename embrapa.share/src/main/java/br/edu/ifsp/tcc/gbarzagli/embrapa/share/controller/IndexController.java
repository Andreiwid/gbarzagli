package br.edu.ifsp.tcc.gbarzagli.embrapa.share.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import br.edu.ifsp.tcc.gbarzagli.embrapa.share.model.Researcher;

@Controller
public class IndexController {

    @RequestMapping("")
    public String index() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        
        Researcher researcher = (Researcher) session.getAttribute("user");
        if (researcher != null) {
            return "redirect:/home";
        }
        
        return "redirect:/login";
    }
}
