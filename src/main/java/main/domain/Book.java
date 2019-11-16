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
	private final String bookName;

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
