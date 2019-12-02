package main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import main.dao.AuthorRepository;
import main.dao.BookRepository;
import main.dao.GenreRepository;
import main.domain.Book;

@Controller
public class BookController {
	
	private final BookRepository bookRepository;
	private final AuthorRepository authorRepository;
	private final GenreRepository genreRepository;
	
	@Autowired
	public BookController(BookRepository bookRepository,AuthorRepository authorRepository,GenreRepository genreRepository) {
		this.bookRepository = bookRepository;
		this.authorRepository = authorRepository;
		this.genreRepository = genreRepository;
	}
	
	@GetMapping("/")
	public String listPage(Model model) {
		List<Book> books= bookRepository.findAll();
		model.addAttribute("books", books);
        return "library_list";
	}
	
	@GetMapping("/edit")
    public String editPage(@RequestParam("id") int id, Model model) {
        model.addAttribute("book", bookRepository.findById(id));
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        return "book_edit";
    }
	
	@GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        return "book_create";
    }

	@GetMapping("/delete")
    public String delete(@RequestParam("id") long id, Model model) {
		bookRepository.deleteById(id);
        return listPage(model);
    }
}
