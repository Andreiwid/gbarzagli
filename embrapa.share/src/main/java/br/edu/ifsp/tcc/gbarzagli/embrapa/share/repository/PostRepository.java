package br.edu.ifsp.tcc.gbarzagli.embrapa.share.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifsp.tcc.gbarzagli.embrapa.share.model.Family;
import br.edu.ifsp.tcc.gbarzagli.embrapa.share.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	@Query("SELECT p FROM Post p WHERE p.sender = :name")
	public List<Post> findByUsername(@Param("name") String name);
}
