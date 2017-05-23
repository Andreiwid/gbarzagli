package br.edu.ifsp.tcc.gbarzagli.embrapa.share.repository;

import org.springframework.data.repository.CrudRepository;

import br.edu.ifsp.tcc.gbarzagli.embrapa.share.model.Plant;

public interface PlantRepository extends CrudRepository<Plant, Long> {
}
