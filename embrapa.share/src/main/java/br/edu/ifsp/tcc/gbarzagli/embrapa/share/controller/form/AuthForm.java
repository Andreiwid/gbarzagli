package br.edu.ifsp.tcc.gbarzagli.embrapa.share.controller.form;

import org.apache.commons.codec.digest.DigestUtils;

import br.edu.ifsp.tcc.gbarzagli.embrapa.share.model.Researcher;

public class AuthForm {
    
    private String name;

    private String username;

    private String password;
    
    private String confirmation;
    
    public AuthForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }
    
    public Researcher toModel() {
        Researcher researcher = new Researcher();
        researcher.setName(name);
        researcher.setPassword(DigestUtils.md5Hex(password));
        researcher.setUsername(username);
        return researcher;
    }

    @Override
    public String toString() {
        return "ResearcherForm [name=" + name + ", username=" + username + ", password=" + password + ", confirmation=" + confirmation + "]";
    }
    
}
