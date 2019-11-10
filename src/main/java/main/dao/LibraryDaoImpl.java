package main.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import main.domain.Author;
import main.domain.Book;
import main.domain.Genre;
@Repository
@Transactional
public class LibraryDaoImpl implements LibraryDao{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void add(Book book) {
		em.persist(book);		
		addBookAuthors(book);
		addBookGenres(book);
		em.merge(book);
	}

	@Override
	public List<Book> findAll() {
		String sql = "SELECT b FROM Book b";
		TypedQuery<Book> query = em.createQuery(sql, Book.class);
		return query.getResultList();
	}

	@Override
	public Book findById(long id) {
		return em.find(Book.class,id);
	}

	@Override
	public int deleteById(long id) {
		String sql = "DELETE FROM Book b WHERE b.id = :id";
		Query query = em.createQuery(sql);
		query.setParameter("id", id);
		return query.executeUpdate();
	}

	@Override
	public boolean updateBookCountById(long id, int count) {
		Book b = findById(id);
		if(b!=null)
		{
			b.setCount(count);
			em.merge(b);
			return true;
		}
		return false;	
	}

	@Override
	public long count() {
		String sql = "SELECT COUNT(b) FROM Book b";
		Query query = em.createQuery(sql);
		return (long)query.getSingleResult();
	}

	private void addBookAuthors(Book book) {
		Author author = null;
		List<Author> authors = book.getAuthors();
		int size = authors.size();
		
		for (int i = 0; i < size; i++) {
			author = authors.get(i);
			try {
				author = getAuthorByName(author);
				authors.set(i, author);
			} catch (NoResultException e) {
				em.persist(author);
			}
		}
	}
	private Author getAuthorByName(Author author) {
		String sql = "SELECT a FROM Author a WHERE a.surname= :surname AND a.firstname= :firstname AND a.secondname= :secondname";
		TypedQuery<Author> query = em.createQuery(sql, Author.class);
		query.setParameter("surname", author.getSurname());
		query.setParameter("firstname", author.getFirstname());
		query.setParameter("secondname", author.getSecondname());
		return (Author)query.getSingleResult();
	}

	private void addBookGenres(Book book) {
		Genre genre = null;
		List<Genre> genres = book.getGenres();
		int size = genres.size();
		
		for (int i = 0; i < size; i++) {
			genre = genres.get(i);
			try
			{
				genre = getGenreByName(genre.getGenreName());
				genres.set(i, genre);
			}
			catch(NoResultException e)
			{
				em.persist(genre);
			}
		}
	}
	
	private Genre getGenreByName(String genreName) {
		String sql = "SELECT g FROM Genre g WHERE g.genreName = :genreName";
		TypedQuery<Genre> query = em.createQuery(sql, Genre.class);
		query.setParameter("genreName", genreName);
		return (Genre)query.getSingleResult();
	}
}
