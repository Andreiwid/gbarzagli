package br.edu.ifsp.tcc.gbarzagli.embrapa.share.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Researcher extends PersistedObject {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "researcher", cascade = CascadeType.ALL)
    private List<Diagnostic> diagnostics;
    
    @ManyToOne
    @JoinColumn(name = "fk_reliable", referencedColumnName = "id")
    private Reliability reliability;

    public Researcher() {}

    public Researcher(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
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
    
    public Reliability getReliability() {
        return reliability;
    }
    
    public void setReliability(Reliability reliability) {
        this.reliability = reliability;
    }
    
    

    public List<Diagnostic> getDiagnostics() {
        return diagnostics;
    }

    public void setDiagnostics(List<Diagnostic> diagnostics) {
        this.diagnostics = diagnostics;
    }

    @Override
    public String toString() {
        return "Researcher [name=" + name + ", username=" + username + ", password=" + password + ", diagnostics=" + diagnostics + ", reliability=" + reliability + "]";
    }

}
