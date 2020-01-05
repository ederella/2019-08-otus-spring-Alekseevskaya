package main.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection = "books")
public class Book {
	@Id
	private String id;

	@Field(value = "bookname")
	private String bookName;

	@Field(value = "numberavailable")
	private int count;
	
	@DBRef
	private List<Author> authors;

	private List<Genre> genres;

	public Book() {
		this.bookName = "";
	}

	public Book(List<Author> authors, String bookName, List<Genre> genres) {
		this.authors = authors;
		this.bookName = bookName;
		this.genres = genres;
	}

	public Book(String id, List<Author> authors, String bookName, List<Genre> genres, int number) {
		this.id = id;
		this.authors = authors;
		this.bookName = bookName;
		this.genres = genres;
		this.count = number;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getId() {
		return id;
	}

	public List<Author> getAuthors() {
		return authors;
	}
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public List<Genre> getGenres() {
		return genres;
	}
	
	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}
	public List<String> getGenreNames() {
		List<String> names = new ArrayList<String>();
		genres.forEach((genre) -> {
			names.add(genre.getGenreName());
		});
		return names;
	}

	public int getCount() {
		return count;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getListString(authors, ",")+ ": ");		
		sb.append("\"" + this.bookName + "\"");
		sb.append("("+ getListString(genres, ",")+")");
		return sb.toString();
	}

	private String getListString(List<?> list, String separator) {
		StringBuilder sb = new StringBuilder();
		if (list != null) {
			list.forEach((o) -> {
				sb.append(o.toString());
				if (list.indexOf(o) < list.size() - 1)
					sb.append(separator + " ");
			});
		}
		return sb.toString();
	}
}
