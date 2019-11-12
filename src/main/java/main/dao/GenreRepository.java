package main.dao;


import org.springframework.data.repository.CrudRepository;

import main.domain.Genre;

public interface GenreRepository extends CrudRepository<Genre, Long>{

	Genre findByGenreName(String genreName);

}
