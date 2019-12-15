package main.domain;

import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name="GENRES")
public class Genre {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private final long id;	
	@Column(name = "GENRENAME")
	private final String genreName;
	
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "BOOK_GENRE", joinColumns = @JoinColumn(name = "GENREID"), inverseJoinColumns = @JoinColumn(name = "BOOKID"))
	private Set<Book> books;
	
	
	public Genre(long id, String genreName)
	{
		this.id = id;
		this.genreName = genreName;
	}
	
	public Genre()
	{
		this.id = 0L;
		this.genreName = "";
	}
	public Genre(String genreName)
	{
		this.id = 0L;
		this.genreName = genreName;
	}

	public String getGenreName() {
		return genreName;
	}
	public void addBook(Book book) {
		this.books.add(book);
	}
	public long getId() {
		return this.id;
	}
	public String toString() {
		return getGenreName();
	}
}
