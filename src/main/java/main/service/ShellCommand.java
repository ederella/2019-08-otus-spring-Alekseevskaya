package main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import main.dao.LibraryDao;
import main.domain.Author;
import main.domain.Book;

@ShellComponent
public class ShellCommand {

	private final LibraryDao library;
	
	@Autowired
	public ShellCommand(LibraryDao library)
	{
		this.library = library;
	}
	
	@ShellMethod(value="List all books", key="ls")
	public String listBooks()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Library:\n");
		List<Book> books = library.getAll();
		int i = 1;
		for (Book book : books) {
			sb.append(i + ". ");
			sb.append(book.toString());
			sb.append("\n");
			i++;
		}
		return sb.toString();
	}
	
	@ShellMethod(value="Count books types", key="c")
	public String countBookTypes()
	{
		return "Count books types: " + library.count();
	}
	
	@ShellMethod(value="Add book to library", key="a")
	public String addBookToLibrary() throws Exception
	{
		Scanner sc = new Scanner(System.in);		
		try
		{
			List<Author> authors = new ArrayList<Author>();
			
			String yesNo = "y";
			String authorSurname = "";
			String authorFisrtname = "";
			String authorSecondname = "";
			while(yesNo.matches("y|Y|yes|YES|Yes"))
			{
							
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
			List<String> genres = new ArrayList<String>();
			yesNo = "y";
			while(yesNo.matches("y|Y|yes|YES|Yes"))
			{
				System.out.println("Enter an genre of the book");
				genres.add(sc.nextLine());
				System.out.println("Would you like to add one more genre? y/n");
				yesNo = sc.nextLine();
			}
			Book book = new Book(authors, bookName, genres);
			library.addBook(book);
			
			return "New book has been successfully added";
		}
		catch(Exception e) 
		{
			return "Book was not added because of the error:" + e.getMessage();
		}		
	}
	
	@ShellMethod(value="Delete book from the library", key="d")
	String deleteBookById(long id)
	{
		try
		{
			library.deleteBookById(id);
		
			return "Book has been deleted";
		}
		catch(Exception e)
		{
			return "Book is not found";
		}
	}
	
	@ShellMethod(value="Give the book from the library", key="g")
	String giveBook(long id)
	{
		try
		{
			library.giveBook(id);
		
			return "Book has been given to reader";
		}
		catch(Exception e)
		{
			return "Book is not found";
		}
	}
	
	@ShellMethod(value="Return the book to the library", key="r")
	String returnBook(long id)
	{
		try
		{
			library.returnBook(id);
		
			return "Book has been returned";
		}
		catch(Exception e)
		{
			return "Book is not found";
		}
	}
	
	@ShellMethod(value="Set number of bookk of a certain book type", key="n")
	String setNumberOfBooks(long id, int count)
	{
		try
		{
			library.setNumberOfBooks(id, count);
			
			return "Book number has been loaded";
		}
		catch(Exception e)
		{
			return "Book is not found";
		}
		
	}
	
	@ShellMethod(value="Get the book info by Id", key="id")
	String getBookById(long id)
	{
		try
		{
			Book book = library.getById(id);
		
			return book.toString();
		}
		catch(Exception e)
		{
			return "Book is not found";
		}
	}	
}
