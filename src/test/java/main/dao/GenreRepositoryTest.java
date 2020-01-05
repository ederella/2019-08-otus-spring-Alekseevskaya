package main.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import main.domain.Genre;
import reactor.core.publisher.Mono;

@DisplayName("Тест GenreRepository")
@ExtendWith(SpringExtension.class)
@DataMongoTest
@ComponentScan(basePackages = { "main.dao", "main.domain" })
public class GenreRepositoryTest {

	@Autowired
	private GenreRepository db;
	
	@DisplayName(" должен найти жанр по названию")
	@Test
	void shouldFindGenreByName() {
		Object genre = db.findByGenreName("Сатира");
		assertThat(((Mono)genre).block()).isInstanceOf(Genre.class);
	}
}
