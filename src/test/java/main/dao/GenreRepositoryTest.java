package main.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import main.domain.Genre;

@DisplayName("Тест GenreRepository")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@DataJpaTest
@ComponentScan(basePackages = { "main.dao" })
public class GenreRepositoryTest {

	@Autowired
	GenreRepository db;
	@Autowired
	TestEntityManager em;
	@DisplayName(" должен возвращать жанр по имени")
	@Test 
	void shoulReturnGenreByName() {
		Object genre = db.findByGenreName("Сатира");
		assertThat(genre).isInstanceOf(Genre.class);
		Genre genreDB = (Genre) em.getEntityManager().createQuery("SELECT g FROM Genre g WHERE g.genreName ='Сатира'").getSingleResult();
		assertThat((Genre)genre).isEqualToComparingFieldByField(genreDB);
	}
}
