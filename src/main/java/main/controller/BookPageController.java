package main.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.server.ServerRequest;

import main.controller.dto.BookDtoPage;
import main.dao.AuthorRepository;
import main.dao.BookRepository;
import main.dao.GenreRepository;
import main.domain.Author;
import main.domain.Book;
import main.domain.Genre;
import reactor.core.publisher.Mono;

@Controller
public class BookPageController {
	private BookRepository bookRepository;
	private AuthorRepository authorRepository;
	private GenreRepository genreRepository;
	
	@Autowired
	public BookPageController(BookRepository bookRepository,AuthorRepository authorRepository,GenreRepository genreRepository) {
		this.bookRepository = bookRepository;
		this.authorRepository = authorRepository;
		this.genreRepository = genreRepository;
	}

	@ModelAttribute
	public List<Author> listAuthors(){
		List<Author> authors = new ArrayList<Author>();
		authorRepository.findAll().subscribe(a->{authors.add(a);});
		return authors;
	}
	@ModelAttribute
	public List<Genre> listGenres(){
		List<Genre> genres = new ArrayList<Genre>();
		genreRepository.findAll().subscribe(g->{genres.add(g);});
		return genres;	
	}

	@GetMapping("/")
	public String listPage(Model model) {
		return "library_list";
	}
	@GetMapping("/edit/{id}")
    public String editPage(@PathVariable("id") String id, Model model){
		model.addAttribute("id", id);
		return "book_edit";
	}
	
	@PostMapping(value="/")
	public String editBook(Mono<BookDtoPage> bookDtoPageMono) {
		bookDtoPageMono.subscribe(bp -> {
			bookRepository.findById(bp.getId()).subscribe(b -> {
				if (bp.getButton().equalsIgnoreCase("save")) {
					b.setBookName(bp.getBookName());
					b.setAuthors(getAuthorsFromIds(bp.getAuthors()));
					b.setGenres(getGenresFromNames(bp.getGenres()));
					bookRepository.save(b).subscribe();
				} else if (bp.getButton().equalsIgnoreCase("delete")) {
					bookRepository.delete(b).subscribe();
				}
			});
		});
		return "library_list";
	}

	@GetMapping("/create")
    public String createPage (Model model) {
        return "book_create";
    }

	@PostMapping(value = "/create")
	public String createBook(Mono<BookDtoPage> bookDtoPageMono) {
		Book b = new Book();
		bookDtoPageMono.subscribe(bp -> {
			b.setBookName(bp.getBookName());
			b.setAuthors(getAuthorsFromIds(bp.getAuthors()));
			b.setGenres(getGenresFromNames(bp.getGenres()));
		});
		bookRepository.save(b).subscribe();
		return "library_list";
	}
	
	private ArrayList<Genre> getGenresFromNames(String[] genresNames) {
		ArrayList<Genre> genres = new ArrayList<Genre>();
		if (genresNames != null) {
			Arrays.stream(genresNames).forEach(genreName -> {
				genreRepository.findByGenreName(genreName).subscribe(genre -> {
					genres.add(genre);
				});
			});
		}
		return genres;
	}

	private ArrayList<Author> getAuthorsFromIds(String[] authorsIds) {
		ArrayList<Author> authors = new ArrayList<Author>();
		if (authorsIds != null) {
			Arrays.stream(authorsIds).forEach(authorId -> {
				authorRepository.findById(authorId).subscribe(author -> {
					authors.add(author);
				});
			});
		}
		return authors;
	}

}
