package br.edu.ifsp.tcc.gbarzagli.embrapa.share.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 * Representation of a plant
 * 
 * @author <a href="mailTo:gabriel.barzagli@hotmail.com"> Gabriel Viseli Barzagli (gabriel.barzagli@hotmail.com) </a>
 */
@Entity
public class Plant extends PersistedObject {

    @Column(nullable = false)
    private String name;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "plant", cascade = CascadeType.ALL)
    private List<Post> post;

    public Plant() {}

    public Plant(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public List<Post> getPost() {
        return post;
    }

    public void setPost(List<Post> post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Plant [name=" + name + ", post=" + post + "]";
    }



}
