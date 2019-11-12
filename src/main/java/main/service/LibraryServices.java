package main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.dao.AuthorRepository;
import main.dao.BookRepository;

import main.dao.CommentRepository;
import main.dao.GenreRepository;
import main.domain.Author;
import main.domain.Book;
import main.domain.Comment;
import main.domain.Genre;

@Service
public class LibraryServices {

	@Autowired
	private final BookRepository bookRepository;
	@Autowired
	private final AuthorRepository authorRepository;
	@Autowired
	private final CommentRepository commentRepository;
	@Autowired
	private final GenreRepository genreRepository;

	// @Autowired
	public LibraryServices(BookRepository bookRepository, AuthorRepository authorRepository,
			CommentRepository commentRepository, GenreRepository genreRepository) {
		this.bookRepository = bookRepository;
		this.authorRepository = authorRepository;
		this.commentRepository = commentRepository;
		this.genreRepository = genreRepository;
	}

	String printAllBooksInfo() {
		StringBuilder sb = new StringBuilder();
		List<Book> books = bookRepository.findAll();
		List<Comment> comments = null;
		int i = 1;
		for (Book book : books) {
			sb.append(i + ". ");
			sb.append(book.toString());
			comments = commentRepository.findByBook(book);
			for (Comment comment : comments) {
				sb.append(">>" + comment.toString() + "\n");
			}
			i++;
		}
		return sb.toString();
	}

	public long getCountOfBookTypes() {
		return bookRepository.count();
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
		addBookAuthors(authors);
		addBookGenres(genres);
		bookRepository.save(book);
	}

	private void addBookAuthors(List<Author> authors) {
		Author author = null;
		Author authorFromDB = null;

		if (authors != null) {
			int size = authors.size();

			for (int i = 0; i < size; i++) {
				author = authors.get(i);
				authorFromDB = authorRepository.findBySurnameAndFirstnameAndSecondname(author.getSurname(),
						author.getFirstname(), author.getSecondname());
				if (authorFromDB != null)
					authors.set(i, authorFromDB);
				else
					authorRepository.save(author);
			}
		}
	}

	private void addBookGenres(List<Genre> genres) {
		Genre genre = null;
		Genre genreFromDB = null;

		if (genres != null) {
			int size = genres.size();

			for (int i = 0; i < size; i++) {
				genre = genres.get(i);
				genreFromDB = genreRepository.findByGenreName(genre.getGenreName());
				if (genreFromDB != null)
					genres.set(i, genreFromDB);
				else
					genreRepository.save(genre);
			}
		}
	}

	public void deleteBookById(long id) throws Exception {
		bookRepository.deleteById(id);
	}

	public boolean giveBook(long id) {
		Book b = bookRepository.findById(id);
		if (b != null) {
			return setNumberOfBooks(id, b.getCount() - 1);
		}
		return false;
	}

	public boolean returnBook(long id) {
		Book b = bookRepository.findById(id);
		if (b != null) {
			return setNumberOfBooks(id, b.getCount() + 1);
		}
		return false;
	}

	public boolean setNumberOfBooks(long id, int count) {
		if (count >= 0)
			return bookRepository.updateBookCountById(id, count) > 0;
		else
			return false;
	}

	public String printBookById(long id) throws Exception {
		Book b = bookRepository.findById(id);
		if (b != null)
			return b.toString();
		else
			throw new Exception("Book is not found");
	}

	public String printAllAuthors() {
		StringBuilder sb = new StringBuilder();
		List<Author> authors = authorRepository.findAll();
		int i = 1;
		for (Author author : authors) {
			sb.append(i + ". ");
			sb.append(author.toString());
			sb.append("\n");
			i++;
		}
		return sb.toString();
	}

	public boolean addComment(long id) {
		Book b = bookRepository.findById(id);
		if (b != null) {
			Scanner sc = new Scanner(System.in);

			System.out.println("Enter your name");
			String nickName = sc.nextLine().trim();

			System.out.println("Enter your comment");
			String commentText = sc.nextLine().trim();

			Comment c = new Comment(nickName, commentText, b);
			commentRepository.save(c);
			return true;
		}
		return false;

	}

	public boolean addAnonimousComment(String text, long id) {
		Book b = bookRepository.findById(id);
		if (b != null) {
			Comment c = new Comment(text, b);
			commentRepository.save(c);
			return true;
		}
		return false;
	}

	public String printAllCommentsByBookId(long id) throws Exception {
		Book b = bookRepository.findById(id);
		if (b != null) {
			List<Comment> comments = commentRepository.findByBook(b);
			StringBuilder sb = new StringBuilder();
			int i = 1;
			for (Comment comment : comments) {
				sb.append(i + ". ");
				sb.append(">> " + comment.toString() + "\n");
				i++;
			}
			return sb.toString();
		} else
			throw new Exception("Book is not found");
	}
}
