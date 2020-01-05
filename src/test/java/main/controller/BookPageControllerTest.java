package main.controller;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import main.controller.dto.BookDto;
import main.controller.dto.BookDtoPage;
import main.dao.AuthorRepository;
import main.dao.BookRepository;
import main.dao.GenreRepository;
import main.domain.Author;
import main.domain.Book;
import main.domain.Genre;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@DisplayName("Тест BookPageController ")
@SpringBootTest
public class BookPageControllerTest {

	@Autowired
	BookPageController controller;
	@Autowired
	BookRepository bookRepository;
	@Mock
	Model model;
	
	
	@DisplayName(" должен вернуть список авторов")
	@Test
	void shouldReturnListOfAuthors() {
		List<Author> list = controller.listAuthors();
		assertThat(!list.isEmpty());
	}
	
	@DisplayName(" должен вернуть список жанров")
	@Test
	void shouldReturnListOfGenres() {
		List<Genre> list = controller.listGenres();
		assertThat(!list.isEmpty());

	}
	
	@DisplayName(" должен вернуть имя шаблона library_list")
	@Test
	void shouldReturnTemplateNameList() {
		String string = controller.listPage(model);
		assertThat(string.equalsIgnoreCase("library_list"));
	}
	@DisplayName(" должен вернуть имя шаблона book_edit")
	@Test
	void shouldReturnTemplateNameEdit() {
		String string = controller.editPage("123", model);
		assertThat(string.equalsIgnoreCase("book_edit"));
	}
	@DisplayName(" должен удалить книгу")
	@Test
	void shouldDeleteBook() {
		BookDtoPage b = new BookDtoPage();
		String id = bookRepository.findAll().blockFirst().getId();
		b.setButton("delete");
		b.setId(id);
		controller.editBook(Mono.just(b));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Mono resp = bookRepository.findById(id);
		assertThat(resp.block()).isNull();
		
	}
	@DisplayName(" должен сохранить книгу")
	@Test
	void shouldSaveAndDeleteBook() {
		BookDtoPage b = new BookDtoPage();
		Book book = bookRepository.findAll().blockFirst();
		b.setId(book.getId());
		b.setBookName("Test update");
		b.setButton("save");
		controller.editBook(Mono.just(b));
		assertThat(bookRepository.findById(book.getId()).block().getBookName().matches("Test update"));
		
	}
	
	@DisplayName(" должен вернуть имя шаблона book_create")
	@Test
	void shouldReturnTemplateNameCreate() {
		String string = controller.createPage(model);
		assertThat(string.equalsIgnoreCase("book_create"));
	}
	@DisplayName(" должен добавить книгу в базу данных")
	@Test
	void shouldCreateABookInDataBase() {
		BookDtoPage b = new BookDtoPage();
		b.setBookName("Test create");
		controller.createBook(Mono.just(b));
		List<Book> list = new ArrayList<Book>();
		bookRepository.findAll().subscribe(e->{list.add(e);});
		
		Condition<Book> bookNameIsSpecial = new Condition<>(s->s.getBookName().equalsIgnoreCase("Test create"), "bookNameIsSpecial");
		assertThat(list).areAtLeastOne(bookNameIsSpecial);
		
	}
}
