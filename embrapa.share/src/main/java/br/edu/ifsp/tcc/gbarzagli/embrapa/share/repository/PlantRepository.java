package br.edu.ifsp.tcc.gbarzagli.embrapa.share.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifsp.tcc.gbarzagli.embrapa.share.model.Plant;

public interface PlantRepository extends JpaRepository<Plant, Long> {
}
