package main.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import main.controller.dto.BookDto;
import main.domain.Book;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@DisplayName("Тест BookController ")
@SpringBootTest
public class BookControllerTest {
	
	@Autowired
	BookController controller;
	
	@DisplayName(" должен вернуть список книг")
	@Test
	void shouldReturnListOfBookDtoWithinFlux() {
		List<BookDto> list = new ArrayList<BookDto>();
		controller.listPage().subscribe(b->{ list.add(b);			
		});
		assertThat(!list.isEmpty());
	}
	
	@DisplayName(" должен вернуть книгу по id")
	@Test
	void shouldReturnABookinMonoById() {
		String id = controller.listPage().blockFirst().getId();		
		Object book = controller.editPageParams(id);
		assertThat(((Mono)book).block()).isInstanceOf(Book.class);
	}
}
