package main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.dao.BookRepository;
import main.domain.Author;
import main.domain.Book;
import main.domain.Comment;
import main.domain.Genre;

@Service
public class BookServices {

	private final BookRepository bookRepository;
	private final AuthorServices authorServices;
	private final CommentServices commentServices;
	private final GenreServices genreServices;

	@Autowired
	public BookServices(BookRepository bookRepository, AuthorServices authorServices, CommentServices commentServices,
			GenreServices genreServices) {
		this.bookRepository = bookRepository;
		this.authorServices = authorServices;
		this.commentServices = commentServices;
		this.genreServices = genreServices;
	}
	
	public BookServices() {
		this.bookRepository = null;
		this.authorServices = null;
		this.commentServices = null;
		this.genreServices = null;
	}

	String printAllBooksInfo() {
		StringBuilder sb = new StringBuilder();
		List<Book> books = bookRepository.findAll();
		books.stream().forEach((book) -> {
			sb.append(books.indexOf(book) + 1 + ". " + book.toString());
			commentServices.getRepository().findByBook(book).forEach((comment) -> {
				sb.append(">>" + comment.toString() + "\n");
			});
		});
		return sb.toString();
	}

	public long getCountOfBookTypes() {
		return bookRepository.count();
	}

	public void deleteBookById(String id) throws Exception {
		bookRepository.deleteById(id);
	}

	public boolean giveBook(String id) {
		Book b = findById(id);
		if (b != null) {
			return setNumberOfBooks(id, b.getCount() - 1);
		}
		return false;
	}

	public boolean returnBook(String id) {
		Book b = findById(id);
		if (b != null) {
			return setNumberOfBooks(id, b.getCount() + 1);
		}
		return false;
	}

	public boolean setNumberOfBooks(String id, int count) {
		if (count >= 0)
			return bookRepository.updateBookCountById(id, count) > 0;
		else
			return false;
	}

	public String printBookById(String id) throws Exception {
		Book b = findById(id);
		if (b != null)
			return b.toString();
		else
			throw new Exception("Book is not found");
	}

	public Book findById(String id) {
		return bookRepository.findById(id).get();
	}

	public void addBookToLibrary() {
		Scanner sc = new Scanner(System.in);
		List<Author> authors = new ArrayList<Author>();

		String yesNo = "y";
		String authorSurname = "";
		String authorFisrtname = "";
		String authorSecondname = "";
		while (yesNo.matches("y|Y|yes|YES|Yes")) {

			System.out.println("Enter an author's surname");
			authorSurname = sc.nextLine().trim();

			System.out.println("Enter an author's firstname");
			authorFisrtname = sc.nextLine().trim();

			System.out.println("Enter an author's secondname");
			authorSecondname = sc.nextLine().trim();

			Author author = new Author(authorSurname, authorFisrtname, authorSecondname);
			authors.add(author);
			System.out.println("Would you like to add one more author? y/n");
			yesNo = sc.nextLine();
		}

		System.out.println("Enter a book name");
		String bookName = sc.nextLine();
		List<Genre> genres = new ArrayList<Genre>();
		String genreName = "";
		yesNo = "y";
		while (yesNo.matches("y|Y|yes|YES|Yes")) {
			System.out.println("Enter a genre of the book");
			genreName = sc.nextLine().trim();

			if (genreName.length() > 0)
				genres.add(new Genre(genreName));

			System.out.println("Would you like to add one more genre? y/n");
			yesNo = sc.nextLine();
		}
		Book book = new Book(authors, bookName, genres);
		authorServices.addBookAuthors(authors);
		genreServices.addBookGenres(genres);
		bookRepository.save(book);
	}
}