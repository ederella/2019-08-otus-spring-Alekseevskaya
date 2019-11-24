package main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.dao.GenreRepository;
import main.domain.Genre;

@Service
public class GenreServices {

	private final GenreRepository genreRepository;

	@Autowired
	public GenreServices(GenreRepository genreRepository) {
		this.genreRepository = genreRepository;
	}

	public void addBookGenres(List<Genre> genres) {
		if (genres != null) {
			genres.stream().forEach((genre) -> {
				Genre genreFromDB = genreRepository.findByGenreName(genre.getGenreName());
				if (genreFromDB != null)
					genres.set(genres.indexOf(genre), genreFromDB);
				else
					genreRepository.save(genre);
			});
		}
	}
}