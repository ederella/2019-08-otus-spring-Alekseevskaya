package main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import main.domain.Book;

@ShellComponent
public class ShellCommand {

	private final AuthorServices authorServices;
	private final BookServices bookServices;
	private final CommentServices commentServices;

	@Autowired
	public ShellCommand(AuthorServices authorServices, BookServices bookServices, CommentServices commentServices) {
		this.authorServices = authorServices;
		this.bookServices = bookServices;
		this.commentServices = commentServices;
	}

	@ShellMethod(value = "List all books", key = "ls")
	public String listBooks() {
		return "Library:\n" + bookServices.printAllBooksInfo();
	}

	@ShellMethod(value = "Count books types", key = "c")
	public String countBookTypes() {
		return "Count books types: " + bookServices.getCountOfBookTypes();
	}

	@ShellMethod(value = "Add book to library", key = "a")
	public String addBookToLibrary() throws Exception {
		try {
			bookServices.addBookToLibrary();

			return "New book has been successfully added";
		} catch (Exception e) {
			return "Book was not added because of the error:" + e.getMessage();
		}
	}

	@ShellMethod(value = "Delete book from the library", key = "d")
	String deleteBookById(long id) {
		try {
			bookServices.deleteBookById(id);

			return "Book has been deleted";
		} catch (Exception e) {
			return "Book is not found";
		}
	}

	@ShellMethod(value = "Give the book from the library", key = "g")
	String giveBook(long id) {
		try {

			if (bookServices.giveBook(id))
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
			if (bookServices.returnBook(id))
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
			if (bookServices.setNumberOfBooks(id, count))
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
			return bookServices.printBookById(id);
		} catch (Exception e) {
			return "Book is not found";
		}
	}

	@ShellMethod(value = "List all authors", key = "lsa")
	public String listAuthors() {
		return "Authors:\n" + authorServices.printAllAuthors();
	}

	@ShellMethod(value = "Add a comment", key = "cm")
	public String addComment(long id) {

		try {
			Book b = bookServices.findById(id);
			if (b != null) {
				commentServices.addComment(b);
				return "Your comment has been added";
			} else
				throw new Exception("Book is not found");

		} catch (Exception e) {
			return "The comment was not added because of the error:" + e.getMessage();
		}
	}

	@ShellMethod(value = "Add anonimous comment", key = "cma")
	public String addAnonimousComment(long id, String text) {
		try {
			Book b = bookServices.findById(id);
			if (bookServices.findById(id) != null) {
				commentServices.addAnonimousComment(text, b);
				return "Your comment has been added";
			} else
				throw new Exception("Book is not found");

		} catch (Exception e) {
			return "The comment was not added because of the error:" + e.getMessage();
		}
	}

	@ShellMethod(value = "Read comments on book", key = "cmb")
	public String readCommentsOnBook(long id) {
		try {
			Book b = bookServices.findById(id);
			if(b!=null)
				return commentServices.printAllCommentsByBook(b);
			else
				return "Book is not found";
		} catch (Exception e) {
			return "Book is not found";
		}
	}

}
