package br.edu.ifsp.tcc.gbarzagli.embrapa.share.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Representation of a plant
 * 
 * @author <a href="mailTo:gabriel.barzagli@hotmail.com"> Gabriel Viseli Barzagli (gabriel.barzagli@hotmail.com) </a>
 */
@Entity
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    protected Plant() {
    }

    public Plant(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Plant [id=" + id + ", name=" + name + "]";
    }

}
