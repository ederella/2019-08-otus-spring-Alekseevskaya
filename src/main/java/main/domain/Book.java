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
	private final String bookName;

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

	public String getBookName() {
		return bookName;
	}

	public List<Genre> getGenres() {
		return genres;
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
		sb.append("id - " + this.id + "\n");
		sb.append(getAuthorsString());
		sb.append("   ~ " + this.bookName + " ~   \n");
		sb.append(getGenresString());
		sb.append(this.count + " шт.\n");
		return sb.toString();
	}
	
	private String getGenresString() {
		StringBuilder sb = new StringBuilder();
		if (genres != null) {
			sb.append("(");
			genres.forEach((genre) -> {
				sb.append(genre.getGenreName());
				if (genres.indexOf(genre) < genres.size() - 1)
					sb.append(", ");
			});

			sb.append(")\n");
		}
		return sb.toString();
	}

	private String getAuthorsString() {
		StringBuilder sb = new StringBuilder();
		if (authors != null) {
			authors.forEach((author) -> {
				sb.append(author.toString());
				if (authors.indexOf(author) < authors.size() - 1)
					sb.append(", ");
			});
			sb.append(": \n");
		}
		return sb.toString();
	}
}
