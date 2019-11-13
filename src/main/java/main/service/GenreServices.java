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
		Genre genre = null;
		Genre genreFromDB = null;

		if (genres != null) {
			int size = genres.size();

			for (int i = 0; i < size; i++) {
				genre = genres.get(i);
				genreFromDB = genreRepository.findByGenreName(genre.getGenreName());
				if (genreFromDB != null)
					genres.set(i, genreFromDB);
				else
					genreRepository.save(genre);
			}
		}
	}
}
