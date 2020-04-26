package main.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "BOOKS")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private final long id;

	@Column(name = "BOOKNAME")
	private String bookName;

	@Column(name = "NUMBERAVAILABLE")
	private int count;

	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "BOOK_AUTHOR", joinColumns = @JoinColumn(name = "BOOKID"), inverseJoinColumns = @JoinColumn(name = "AUTHORID"))
	private List<Author> authors;

	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "BOOK_GENRE", joinColumns = @JoinColumn(name = "BOOKID"), inverseJoinColumns = @JoinColumn(name = "GENREID"))
	private List<Genre> genres;

	public Book() {
		this.id = 0L;
		this.bookName = "";
	}

	public Book(List<Author> authors, String bookName, List<Genre> genres) {
		this.id = 0L;
		this.authors = authors;
		this.bookName = bookName;
		this.genres = genres;
	}

	public Book(long id, List<Author> authors, String bookName, List<Genre> genres, int number) {
		this.id = id;
		this.authors = authors;
		this.bookName = bookName;
		this.genres = genres;
		this.count = number;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public long getId() {
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
