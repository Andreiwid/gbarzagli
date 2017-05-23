package br.edu.ifsp.tcc.gbarzagli.embrapa.share.repository;

import org.springframework.data.repository.CrudRepository;

import br.edu.ifsp.tcc.gbarzagli.embrapa.share.model.Post;

public interface PostRepository extends CrudRepository<Post, Long> {
}
