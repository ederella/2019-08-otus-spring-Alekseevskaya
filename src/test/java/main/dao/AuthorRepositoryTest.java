package main.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import main.domain.Author;

@DisplayName("Тест AuthorRepository")
@ExtendWith(SpringExtension.class)
@DataMongoTest
@ComponentScan(basePackages = { "main.dao", "main.domain" })
public class AuthorRepositoryTest {

	@Autowired
	private AuthorRepository db;
	
	@DisplayName(" должен найти автора по ФИО")
	@Test
	void shouldFindAuthorByFullName() {
		Object author = db.findBySurnameAndFirstnameAndSecondname("Ильф", "Илья", "Арнольдович");
		assertThat(author).isInstanceOf(Author.class);
	}

	@DisplayName(" должен вернуть список всех авторов")
	@Test
	void shouldReturnListOfAuthors() {
		List<Author> authors = db.findAll();
		assertThat(authors).isNotEmpty();
	}

}
