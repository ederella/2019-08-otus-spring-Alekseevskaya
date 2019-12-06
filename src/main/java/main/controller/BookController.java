package main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
        Book b = bookRepository.findById(id);
        model.addAttribute("book", b);
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("selected_authors", b.getAuthors());
        model.addAttribute("genres", genreRepository.findAll());
        //model.addAttribute("selected_genres", b.getGenres());
        return "book_edit";
    }
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public String editBook(@RequestParam("id") long id, @RequestParam("bookName") String bookName,
			@RequestParam("button") String button, Model model) {
		Book b = bookRepository.findById(id);
		if (button.equalsIgnoreCase("save")) {
			b.setBookName(bookName);
			bookRepository.save(b);
		} 
		else if (button.equalsIgnoreCase("delete")) {
			bookRepository.delete(b);
		}
		return listPage(model);
	}
	
	@GetMapping("/create")
    public String createPage( Model model) {
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        return "book_create";
    }
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String createBook( @RequestParam("bookName") String bookName, Model model) {
		return listPage(model);
	}
}
