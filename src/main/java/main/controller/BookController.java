package main.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.controller.dto.BookDto;
import main.dao.BookRepository;
import main.domain.Book;

@RestController
public class BookController {
	
	private final BookRepository bookRepository;
	
	@Autowired
	public BookController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	@GetMapping("/api/books")
	public List<BookDto> listPage() {
        return bookRepository.findAll().stream().map(BookDto::toDto).collect(Collectors.toList());
	}
	
	@GetMapping("/api/edit")
    public Book editPageParams(@RequestParam("id")int id) {
        Book b = bookRepository.findById(id);
        return b;      
    }
}
