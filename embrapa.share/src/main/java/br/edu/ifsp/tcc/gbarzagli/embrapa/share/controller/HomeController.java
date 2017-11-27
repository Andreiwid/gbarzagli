package br.edu.ifsp.tcc.gbarzagli.embrapa.share.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import br.edu.ifsp.tcc.gbarzagli.embrapa.share.controller.form.HomeForm;
import br.edu.ifsp.tcc.gbarzagli.embrapa.share.controller.form.HomeForm.UserPartForm;
import br.edu.ifsp.tcc.gbarzagli.embrapa.share.model.Diagnostic;
import br.edu.ifsp.tcc.gbarzagli.embrapa.share.model.Image;
import br.edu.ifsp.tcc.gbarzagli.embrapa.share.model.Post;
import br.edu.ifsp.tcc.gbarzagli.embrapa.share.model.Researcher;
import br.edu.ifsp.tcc.gbarzagli.embrapa.share.repository.ImageRepository;
import br.edu.ifsp.tcc.gbarzagli.embrapa.share.repository.PostRepository;

@Controller
public class HomeController {

    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private ImageRepository imageRepository;
    
    @GetMapping("home")
    public String index(Model model) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        
        Researcher researcher = (Researcher) session.getAttribute("user");
        if (researcher == null) {
            return "redirect:/login";
        }
        
        int votes = 0;
        int diagnostics = 0;
        if (researcher.getDiagnostics() != null) {
            diagnostics = researcher.getDiagnostics().size();
            
            for (Diagnostic diagnostic : researcher.getDiagnostics()) {
                votes += diagnostic.getScore();
            }
        }
        int score = diagnostics == 0 ? 0 : votes / diagnostics; 
        
        List<Post> posts = postRepository.findAll(new Sort(Sort.Direction.DESC, "id"));
        
        HomeForm homeForm = new HomeForm();        
        UserPartForm user = homeForm.new UserPartForm();
        
        user.setName(researcher.getName());
        user.setDiagnostics(diagnostics);
        user.setScore(score);
        
        homeForm.setUser(user);
        homeForm.setPosts(posts);

        model.addAttribute("form", homeForm);
        
        return "home";
    }
    
    /**
     * GET endpoint to return a single image
     * @param id image's id
     * @return a image in jpeg format
     */
    @RequestMapping(value = "/image/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody byte[] getPostImage(@PathVariable("id") Long id) {
        byte[] response = null;
        try {
            Image image = imageRepository.findOne(id);
            
            File file = new File(image.getPath());
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            FileInputStream fileInputStream = new FileInputStream(file);
            
            IOUtils.copy(fileInputStream, byteStream);
            
            fileInputStream.close();
            response = byteStream.toByteArray();
            byteStream.close();
        } catch (IOException e) {
        }
        
        return response;
    }
    
    @RequestMapping("logout")
    public String logout() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        
        session.removeAttribute("user");
        return "redirect:/login";
    }
    
}
