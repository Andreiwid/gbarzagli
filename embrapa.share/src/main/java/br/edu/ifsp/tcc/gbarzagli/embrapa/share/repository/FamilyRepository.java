package br.edu.ifsp.tcc.gbarzagli.embrapa.share.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifsp.tcc.gbarzagli.embrapa.share.model.Family;

public interface FamilyRepository extends JpaRepository<Family, Long> {
	
	@Query("SELECT f FROM Family f WHERE f.name = :name")
	public Family findByName(@Param("name") String name);
	
}
