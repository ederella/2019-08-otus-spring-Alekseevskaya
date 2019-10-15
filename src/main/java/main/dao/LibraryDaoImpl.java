package main.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import main.domain.Author;
import main.domain.Book;
import java.util.ArrayList;

@Repository
public class LibraryDaoImpl implements LibraryDao{

private final NamedParameterJdbcOperations namedParameterJdbcOperations;
	
	public LibraryDaoImpl(NamedParameterJdbcOperations namedParameterJdbcOperations)
	{
		this.namedParameterJdbcOperations = namedParameterJdbcOperations;
	}
	
	@Override
	public List<Book> getAll()
	{
		String sql = "SELECT ID FROM BOOKS";
	
		List<Long> listOfIds = namedParameterJdbcOperations.getJdbcOperations().queryForList(sql, Long.class);
		List<Book> books = new ArrayList<Book>();
		
		for (Iterator iterator = listOfIds.iterator(); iterator.hasNext();) 
		{
			Long id = (Long) iterator.next();
			books.add(getById(id));
		}
		
		return books;
	}

	@Override
	public void addBook(Book book) 
	{	
		addBookName(book);
		long bookId = getBookIdByName(book);
		addBookAuthors(bookId, book.getAuthors());
		addBookGenres(bookId, book.getGenres());		
	}

	@Override
	public void setNumberOfBooks(long id, int count) 
	{
		String sql = "UPDATE BOOKS SET NUMBERAVAILABLE = :count WHERE ID = :id";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource()
												.addValue("count", count)
												.addValue("id", id);

        namedParameterJdbcOperations.update(sql, namedParameters);
	}
	

	private Long getGenreIdByName(String genre) {
		String sql = "SELECT G.ID "
				+ "FROM GENRES G "
				+ "WHERE G.GENRENAME= :genre";
	
		SqlParameterSource namedParameters = new MapSqlParameterSource("genre", genre);

		return namedParameterJdbcOperations.queryForObject(sql, namedParameters, Long.class);
	}

	private void addBookAuthors(long bookId, List<Author> authors) {
		Long authorId = null;
		for (Iterator iterator = authors.iterator(); iterator.hasNext();) 
		{
			authorId = null;
			Author author = (Author) iterator.next();
			try
			{
				authorId = getAuthorIdByName(author);
			}
			catch(EmptyResultDataAccessException e)
			{
				addAuthor(author);
				authorId = getAuthorIdByName(author);		
			}
			
			if(authorId != null)
			{
				addAuthorToBook(authorId, bookId);
			}
		}
	}
	
	@Override
	public void deleteBookById(long id) 
	{
		String sql = "DELETE FROM BOOKS WHERE ID = :id";
		
		Map<String, Object> params = Collections.singletonMap("id",id);
		
        namedParameterJdbcOperations.update(sql, params);
	}

	@Override
	public Book getById(long id) 
	{
		String sql = "SELECT B.ID, B.BOOKNAME, B.NUMBERAVAILABLE "
                	+ "FROM BOOKS B "
                	+ "WHERE B.ID = :id";
                
		Map<String, Object> params = Collections.singletonMap("id", id);
		
		return namedParameterJdbcOperations.queryForObject(sql, params, new BookMapper());
	}

	@Override
	public void giveBook(long id) 
	{
		increaseBookCount(id, -1);
	}

	@Override
	public void returnBook(long id) 
	{
		increaseBookCount(id, 1);
	}

	private void increaseBookCount(long id, int i) 
	{
		String sql = "UPDATE BOOKS SET NUMBERAVAILABLE = NUMBERAVAILABLE+"+i+" WHERE ID = :id";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);

        namedParameterJdbcOperations.update(sql, namedParameters);
	}

	@Override
	public int count() 
	{
		String sql = "SELECT COUNT(*) FROM BOOKS";
		
		return namedParameterJdbcOperations.getJdbcOperations().queryForObject(sql, Integer.class);
	}
	
	@SuppressWarnings("unchecked")
	private List<String> getGenresOfTheBook(long bookId) 
	{
		String sql = "SELECT G.GENRENAME "
					+ "FROM GENRES G, BOOK_GENRE B "
					+ "WHERE G.ID=B.GENREID AND B.BOOKID= :id";
		SqlParameterSource namedParameters = new MapSqlParameterSource("id", bookId);
		
		return namedParameterJdbcOperations.queryForList(sql, namedParameters, String.class);
	}

	private List<Author> getAuthorsOfTheBook(long bookId) 
	{
		String sql = "SELECT A.SURNAME, A.FIRSTNAME, A.SECONDNAME "
					+ "FROM AUTHORS A, BOOK_AUTHOR B "
					+ "WHERE A.ID=B.AUTHORID AND B.BOOKID= :id";
		
		return namedParameterJdbcOperations.query(sql, new MapSqlParameterSource("id", bookId), new AuthorMapper());
	}

	private Long getAuthorIdByName(Author author) 
	{
		String sql = "SELECT A.ID "
					+ "FROM AUTHORS A "
					+ "WHERE A.SURNAME= :surname AND A.FIRSTNAME= :firstName AND A.SECONDNAME= :secondName";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource()
											.addValue("surname", author.getSurname())
											.addValue("firstName", author.getFirstname())
											.addValue("secondName", author.getSecondname());

		return namedParameterJdbcOperations.queryForObject(sql, namedParameters, Long.class);
	}

	private void addAuthor(Author author) 
	{
		String sql = "INSERT INTO AUTHORS (SURNAME, FIRSTNAME, SECONDNAME) "
					+ "VALUES(:surname, :firstName, :secondName)";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource()
											.addValue("surname", author.getSurname())
											.addValue("firstName", author.getFirstname())
											.addValue("secondName", author.getSecondname());
		
		
		namedParameterJdbcOperations.update(sql, namedParameters);
	}
	
	private void addAuthorToBook(Long authorId, Long bookId)
	{
		String sql = "INSERT INTO BOOK_AUTHOR (AUTHORID, BOOKID) "
				+ "VALUES(:authorId, :bookId)";
	
		SqlParameterSource namedParameters = new MapSqlParameterSource()
										.addValue("authorId", authorId)
										.addValue("bookId", bookId);

		namedParameterJdbcOperations.update(sql, namedParameters);
	}
	
	private long getBookIdByName(Book book) 
	{
		String sql = "SELECT B.ID "
				+ "FROM BOOKS B "
				+ "WHERE B.BOOKNAME= :name";
	
		SqlParameterSource namedParameters = new MapSqlParameterSource("name", book.getBookName());

		return namedParameterJdbcOperations.queryForObject(sql, namedParameters, Long.class);
	}

	private void addBookName(Book book) 
	{
		String sql = "INSERT INTO BOOKS (BOOKNAME) "
					+ "VALUES(:name)";
	
		SqlParameterSource namedParameters = new MapSqlParameterSource("name", book.getBookName());
	
		namedParameterJdbcOperations.update(sql, namedParameters);		
	}

	private void addBookGenres(long bookId, List<String> genres) 
	{
		Long genreId = null;
		for (Iterator iterator = genres.iterator(); iterator.hasNext();) 
		{
			String genre = (String) iterator.next();
			genreId = null;
			
			try 
			{
				genreId = getGenreIdByName(genre);
			}
			catch(EmptyResultDataAccessException e)
			{
				addGenre(genre);
				genreId = getGenreIdByName(genre);
			}

			if(genreId!=null)
			{
				addGenreToBook(genreId, bookId);
			}
			
		}
	}

	private void addGenreToBook(Long genreId, long bookId) {
		String sql = "INSERT INTO BOOK_GENRE (BOOKID, GENREID) "
				+ "VALUES(:bookId, :genreId)";
	
		SqlParameterSource namedParameters = new MapSqlParameterSource()
										.addValue("bookId", bookId)
										.addValue("genreId", genreId);

		namedParameterJdbcOperations.update(sql, namedParameters);
		
	}

	private void addGenre(String genre) 
	{
		String sql = "INSERT INTO GENRES (GENRENAME) "
				+ "VALUES(:genre)";

		SqlParameterSource namedParameters = new MapSqlParameterSource("genre", genre);

		namedParameterJdbcOperations.update(sql, namedParameters);		
	}
	
	public class BookMapper implements RowMapper<Book>{

		@Override
		public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
			long id = rs.getLong("ID");
			String name = rs.getString("BOOKNAME");
			List<Author> authors = getAuthorsOfTheBook(id);
			List<String> genres = getGenresOfTheBook(id);
			int number = rs.getInt("NUMBERAVAILABLE");
			return new Book(id, authors, name, genres, number);
		}

	}
	
	public class AuthorMapper  implements RowMapper<Author>
	{
		@Override
		public Author mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
			String surname = rs.getString("SURNAME");
			String firstname =  rs.getString("FIRSTNAME");
			String secondname = rs.getString("SECONDNAME");
			return new Author(surname, firstname, secondname);
		}
	}
}
	
