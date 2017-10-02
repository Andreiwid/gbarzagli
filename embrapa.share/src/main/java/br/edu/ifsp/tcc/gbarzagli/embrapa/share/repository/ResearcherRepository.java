package br.edu.ifsp.tcc.gbarzagli.embrapa.share.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifsp.tcc.gbarzagli.embrapa.share.model.Researcher;

public interface ResearcherRepository extends JpaRepository<Researcher, Long> {
    @Query("SELECT r FROM Researcher r WHERE r.username = :username")
    public Researcher findByUsername(@Param("username") String username);
}
