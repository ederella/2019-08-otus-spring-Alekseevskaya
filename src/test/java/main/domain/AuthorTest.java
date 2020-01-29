package main.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Тест Author ")
@SpringBootTest
public class AuthorTest {

	private Author author;

	@DisplayName(" должен заполнять поля данными и возвращать их")
	@Test
	public void shouldFillFieldsAndReturnThem() {
		author = new Author("A", "B", "C");
		assertThat(author.getSurname().equalsIgnoreCase("A"));
		assertThat(author.getFirstname().equalsIgnoreCase("B"));
		assertThat(author.getSecondname().equalsIgnoreCase("C"));
	}

	@DisplayName(" должен возвращать ФИО автора в строку")
	@Test
	public void shouldPrintAuthorFullName() {
		author = new Author("A", "B", "C");
		assertThat(author.toString()).matches("A B C");
	}
}
