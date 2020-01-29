package main.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.jupiter.api.DisplayName;

import main.domain.Genre;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackages = { "main.dao" })
public class GenreRepositoryTest {

	@Autowired
	GenreRepository db;
	@Autowired
	TestEntityManager em;
	@DisplayName(" должен возвращать жанр по имени")
	@Test 
	public void shoulReturnGenreByName() {
		Object genre = db.findByGenreName("Сатира");
		assertThat(genre).isInstanceOf(Genre.class);
		Genre genreDB = (Genre) em.getEntityManager().createQuery("SELECT g FROM Genre g WHERE g.genreName ='Сатира'").getSingleResult();
		assertThat((Genre)genre).isEqualToComparingFieldByField(genreDB);
	}
}
