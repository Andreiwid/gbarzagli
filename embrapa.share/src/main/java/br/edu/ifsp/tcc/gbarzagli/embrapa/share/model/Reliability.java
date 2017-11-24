package br.edu.ifsp.tcc.gbarzagli.embrapa.share.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * Used to configure the reliability rules to rank users.
 * @author <a href="mailTo:gabriel.barzagli@hotmail.com"> Gabriel Viseli Barzagli (gabriel.barzagli@hotmail.com) </a>
 *
 */
@Entity
public class Reliability extends PersistedObject {

    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private int rule;
    
    @OneToMany(mappedBy = "reliability", cascade = CascadeType.ALL)
    private List<Researcher> researchers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRule() {
        return rule;
    }

    public void setRule(int rule) {
        this.rule = rule;
    }
    
    public List<Researcher> getResearchers() {
        return researchers;
    }
    
    public void setResearchers(List<Researcher> researchers) {
        this.researchers = researchers;
    }

    @Override
    public String toString() {
        return "Reliability [name=" + name + ", rule=" + rule + "]";
    }
    
}
