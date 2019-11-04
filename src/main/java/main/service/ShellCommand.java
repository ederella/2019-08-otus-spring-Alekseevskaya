package main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import main.dao.LibraryDao;
import main.domain.Author;
import main.domain.Book;
import main.domain.Comment;
import main.domain.Genre;
import repository.RepositoryLibrary;

@ShellComponent
public class ShellCommand {

	private final RepositoryLibrary library;

	@Autowired
	public ShellCommand(RepositoryLibrary library) {
		this.library = library;
	}

	@ShellMethod(value = "List all books", key = "ls")
	public String listBooks() {
		StringBuilder sb = new StringBuilder();
		sb.append("Library:\n");
		List<Book> books = library.findAll();
		List<Comment> comments = null;
		int i = 1;
		for (Book book : books) {
			sb.append(i + ". ");
			sb.append(book.toString());
			comments = library.getCommentsForBook(book.getId());
			for(Comment comment: comments)
			{
				sb.append(">> " + comment.toString()+"\n");
			}
			sb.append("\n");
			i++;
		}
		return sb.toString();
	}

	@ShellMethod(value = "Count books types", key = "c")
	public String countBookTypes() {
		return "Count books types: " + library.count();
	}

	@ShellMethod(value = "Add book to library", key = "a")
	public String addBookToLibrary() throws Exception {
		Scanner sc = new Scanner(System.in);
		try {
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
				
				if(genreName.length()>0)
					genres.add(new Genre(genreName));
				
				System.out.println("Would you like to add one more genre? y/n");
				yesNo = sc.nextLine();
			}
			Book book = new Book(authors, bookName, genres);
			library.addBook(book);

			return "New book has been successfully added";
		} catch (Exception e) {
			return "Book was not added because of the error:" + e.getMessage();
		}
	}

	@ShellMethod(value = "Delete book from the library", key = "d")
	String deleteBookById(long id) {
		try {
			library.deleteBookById(id);

			return "Book has been deleted";
		} catch (Exception e) {
			return "Book is not found";
		}
	}

	@ShellMethod(value = "Give the book from the library", key = "g")
	String giveBook(long id) {
		try {
			
			if(library.giveBook(id))
				return "Book has been given to reader";
			else
				throw new Exception();
		} catch (Exception e) {
			return "Book is not found";
		}
	}

	@ShellMethod(value = "Return the book to the library", key = "r")
	String returnBook(long id) {
		try {
			if(library.returnBook(id))
				return "Book has been returned";
			else
				throw new Exception();
		} catch (Exception e) {
			return "Book is not found";
		}
	}

	@ShellMethod(value = "Set number of book of a certain book type", key = "n")
	String setNumberOfBooks(long id, int count) {
		try {
			if(library.setNumberOfBooks(id, count))
				return "Book number has been loaded";
			else
				throw new Exception();
		} catch (Exception e) {
			return "Book is not found";
		}
	}

	@ShellMethod(value = "Get the book info by Id", key = "id")
	String getBookById(long id) {
		try {
			Book book = library.getById(id);

			return book.toString();
		} catch (Exception e) {
			return "Book is not found";
		}
	}
	
	@ShellMethod(value = "List all authors", key = "lsa")
	public String listAuthors() {
		StringBuilder sb = new StringBuilder();
		sb.append("Library:\n");
		List<Author> authors = library.listAuthors();
		int i = 1;
		for (Author author : authors) {
			sb.append(i + ". ");
			sb.append(author.toString());
			sb.append("\n");
			i++;
		}
		return sb.toString();
	}

	@ShellMethod(value = "Add a comment", key = "cm")
	public String addComment(long id)
	{
		Scanner sc = new Scanner(System.in);
		try {
					
			System.out.println("Enter your name");
			String nickName = sc.nextLine().trim();
			
			System.out.println("Enter your comment");
			String commentText = sc.nextLine().trim();
			
			Comment c = new Comment(nickName, commentText, id);
			
			if(library.addComment(c))
				return "Your comment has been added";
			else
				throw new Exception("Book is not found");
			
		} catch (Exception e) {
			return "Book was not added because of the error:" + e.getMessage();
		}
	}
	
	@ShellMethod(value = "Add anonimous comment", key = "cma")
	public String addAnonimousComment(long id, String text)
	{
		try {

			if(library.addAnonimousComment(text, id))
				return "Your comment has been added";
			else
				throw new Exception("Book is not found");
			
		} catch (Exception e) {
			return "Book was not added because of the error:" + e.getMessage();
		}
	}
}
