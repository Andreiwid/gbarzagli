package br.edu.ifsp.tcc.gbarzagli.embrapa.share.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Researcher extends PersistedObject {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
    
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

    @Override
    public String toString() {
        return "Researcher [name=" + name + ", username=" + username + ", password=" + password + ", reliability=" + reliability + "]";
    }
    
    

}
