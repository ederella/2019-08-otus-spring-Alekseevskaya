package main.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="BOOKS")
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

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "BOOK_GENRE", joinColumns = @JoinColumn(name = "BOOKID"), inverseJoinColumns = @JoinColumn(name = "GENREID"))
	private List<Genre> genres;


	public Book()
	{
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
		for (Genre genre : genres) {
			names.add(genre.getGenreName());
		}
		return names;
	}
	
	public int getCount() {
		return count;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id - " + this.id + "\n");
		if (authors != null) {
			for (Iterator iterator = authors.iterator(); iterator.hasNext();) {
				Author author = (Author) iterator.next();
				sb.append(author.toString());

				if (!iterator.hasNext())
					sb.append(": \n");
				else
					sb.append(", ");
			}
		}
		sb.append("   ~ " + this.bookName + " ~   \n");

		if (genres != null) {
			if (genres.size() > 0)
				sb.append("(");
			for (Iterator iterator = genres.iterator(); iterator.hasNext();) {
				Genre genre = (Genre) iterator.next();
				sb.append(genre.getGenreName());

				if (!iterator.hasNext())
					sb.append(")\n");
				else
					sb.append(", ");

			}
		}
		sb.append(this.count + " шт.\n");
		return sb.toString();
	}
}
