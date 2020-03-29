package main.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PostAuthorize;

import main.domain.Genre;

public interface GenreRepository extends CrudRepository<Genre, Long>{

	@PostAuthorize("hasPermission(returnObject, 'READ')")
	Genre findByGenreName(String genreName);

}
