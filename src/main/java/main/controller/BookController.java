package main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import main.controller.dto.BookDto;
import main.dao.BookRepository;
import main.domain.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class BookController {
	
	private final BookRepository bookRepository;
	
	@Autowired
	public BookController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	@GetMapping("/api/books")
	public Flux<BookDto> listPage() {
        return bookRepository.findAll().map(BookDto::toDto);
	}
	
	@GetMapping("/api/edit/{id}")
    public Mono<Book> editPageParams(@PathVariable("id")String id) {
        return bookRepository.findById(id);      
    }
	

}
