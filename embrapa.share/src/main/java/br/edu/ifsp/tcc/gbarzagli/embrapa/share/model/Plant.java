package br.edu.ifsp.tcc.gbarzagli.embrapa.share.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Representation of a plant
 * @author <a href="mailTo:gabriel.barzagli@hotmail.com"> Gabriel Viseli Barzagli (gabriel.barzagli@hotmail.com) </a>
 */
@Entity
public class Plant {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String name;
	
	@ManyToOne
	@JoinColumn(name="fk_family", referencedColumnName="id")
	private Family family;
	
	protected Plant() {}
	
	public Plant(String name, Family family) {
		this.name = name;
		this.family = family;
	}

	@Override
	public String toString() {
		return "Plant [id=" + id + ", name=" + name + ", family=" + family.toString() + "]";
	}
	
}
