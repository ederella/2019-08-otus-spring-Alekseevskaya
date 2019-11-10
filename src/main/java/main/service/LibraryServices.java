package main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.dao.AuthorDao;
import main.dao.CommentDao;
import main.dao.LibraryDao;
import main.domain.Author;
import main.domain.Book;
import main.domain.Comment;
import main.domain.Genre;

@Service
public class LibraryServices {

	private final LibraryDao library;
	private final AuthorDao authorsLib;
	private final CommentDao commentsLib;

	@Autowired
	public LibraryServices(LibraryDao library, AuthorDao authorsLib, CommentDao commentsLib) {
		this.library = library;
		this.authorsLib = authorsLib;
		this.commentsLib = commentsLib;
	}

	String printAllBooksInfo() {
		StringBuilder sb = new StringBuilder();
		List<Book> books = library.findAll();
		List<Comment> comments = null;
		int i = 1;
		for (Book book : books) {
			sb.append(i + ". ");
			sb.append(book.toString());
			comments = commentsLib.findByBook(book);
			for (Comment comment : comments) {
				sb.append(">>" + comment.toString()+"\n");
			}
			i++;
		}
		return sb.toString();
	}

	public long getCountOfBookTypes() {
		return library.count();
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
		library.add(book);
	}

	public void deleteBookById(long id) throws Exception {
		if (library.deleteById(id) == 0)
			throw new Exception("Book is not found");
	}

	public boolean giveBook(long id) {
		Book b = library.findById(id);
		if (b != null) {
			return setNumberOfBooks(id, b.getCount() - 1);
		}
		return false;
	}

	public boolean returnBook(long id) {
		Book b = library.findById(id);
		if (b != null) {
			return setNumberOfBooks(id, b.getCount() + 1);
		}
		return false;
	}

	public boolean setNumberOfBooks(long id, int count) {
		if (count > 0)
			return library.updateBookCountById(id, count);
		else
			return false;
	}

	public String printBookById(long id) throws Exception {
		Book b = library.findById(id);
		if(b!=null)
			return b.toString();
		else
			throw new Exception("Book is not found");
	}

	public String printAllAuthors() {
		StringBuilder sb = new StringBuilder();
		List<Author> authors = authorsLib.findAll();
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
		Book b = library.findById(id);
		if (b != null) {
			Scanner sc = new Scanner(System.in);

			System.out.println("Enter your name");
			String nickName = sc.nextLine().trim();

			System.out.println("Enter your comment");
			String commentText = sc.nextLine().trim();

			Comment c = new Comment(nickName, commentText, b);
			commentsLib.add(c);
			return true;
		}
		return false;

	}

	public boolean addAnonimousComment(String text, long id) {
		Book b = library.findById(id);
		if (b != null) {
			Comment c = new Comment(text, b);
			commentsLib.add(c);		
			return true;
		}
		return false;
	}

	public String printAllCommentsByBookId(long id) throws Exception {
		Book b = library.findById(id);
		if (b != null) {
			List<Comment> comments = commentsLib.findByBook(b);
			StringBuilder sb = new StringBuilder();
			int i = 1;
			for (Comment comment : comments) {
				sb.append(i + ". ");
				sb.append(">> " + comment.toString() + "\n");
				i++;
			}
			return sb.toString();
		}
		else
			throw new Exception("Book is not found");
	}
}
