package main.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import main.dao.AuthorRepository;
import main.dao.BookRepository;
import main.dao.GenreRepository;
import main.domain.Author;
import main.domain.Book;
import main.domain.Genre;

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
        model.addAttribute("selected_genres", b.getGenres());
        return "book_edit";
    }

	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public String editBook(@RequestParam("id") long id,
						   @RequestParam("bookName") String bookName,
						   @RequestParam("authors") long[] authorsIds,
						   @RequestParam("genres") long[] genresIds,
						   @RequestParam("button") String button, 
						   Model model) {
		Book b = bookRepository.findById(id);
		
		if (button.equalsIgnoreCase("save")) {
			b.setBookName(bookName);
			b.setAuthors(getAuthorsFromIds(authorsIds));
			b.setGenres(getGenresFromIds(genresIds));
			bookRepository.save(b);
		} 
		else if (button.equalsIgnoreCase("delete")) {
			bookRepository.delete(b);
		}
		return listPage(model);
	}

	private ArrayList<Genre> getGenresFromIds(long[] genresIds) {
		ArrayList<Genre> genres = new ArrayList<Genre>();
		Arrays.stream(genresIds).forEach(genreId->{
			genres.add(genreRepository.findById(genreId).get());
		});
		return genres;
	}

	private ArrayList<Author> getAuthorsFromIds(long[] authorsIds) {
		ArrayList<Author> authors = new ArrayList<Author>();
		Arrays.stream(authorsIds).forEach(authorId->{
			authors.add(authorRepository.findById(authorId));
		});
		return authors;
	}
	
	@GetMapping("/create")
    public String createPage( Model model) {
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        return "book_create";
    }
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String createBook( @RequestParam("bookName") String bookName,
			 				  @RequestParam("authors") long[] authorsIds,
			 				  @RequestParam("genres") long[] genresIds,
			 				  Model model) {
		Book b = new Book();
		b.setBookName(bookName);
		b.setAuthors(getAuthorsFromIds(authorsIds));
		b.setGenres(getGenresFromIds(genresIds));
		bookRepository.save(b);
		return listPage(model);
	}
}
