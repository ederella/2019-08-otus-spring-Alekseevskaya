package main.repository;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import main.dao.LibraryDao;
import main.domain.Author;
import main.domain.Book;
import main.domain.Comment;
import main.domain.Genre;

@Repository
public class RepositoryLibrary implements LibraryDao {

	@Autowired
	private RepositoryAuthor repositoryAuthor;

	@Autowired
	private RepositoryBook repositoryBook;

	@Autowired
	private RepositoryComment repositoryComment;

	@Autowired
	private RepositoryGenre repositoryGenre;

	@Override
	public long count() {
		return repositoryBook.count();
	}

	@Override
	public List<Book> getAll() {
		return repositoryBook.findAll();
	}

	@Override
	public List<Author> listAuthors() {
		return repositoryAuthor.findAll();
	}

	@Override
	public void addBook(Book book) {
		addBookAuthors(book);
		addBookGenres(book);
		repositoryBook.save(book);
	}

	private void addBookAuthors(Book book) {
		Author author = null;
		Author authorFromDB = null;
		List<Author> authors = book.getAuthors();
		if (authors != null) {
			int size = authors.size();

			for (int i = 0; i < size; i++) {
				author = authors.get(i);
				authorFromDB = repositoryAuthor.findBySurnameAndFirstnameAndSecondname(author.getSurname(),
						author.getFirstname(), author.getSecondname());
				if (authorFromDB != null)
					authors.set(i, authorFromDB);
				else
					repositoryAuthor.save(author);
			}
		}
	}

	private void addBookGenres(Book book) {
		Genre genre = null;
		Genre genreFromDB = null;
		List<Genre> genres = book.getGenres();

		if (genres != null) {
			int size = genres.size();

			for (int i = 0; i < size; i++) {
				genre = genres.get(i);
				genreFromDB = repositoryGenre.findByGenreName(genre.getGenreName());
				if (genreFromDB != null)
					genres.set(i, genreFromDB);
				else
					repositoryGenre.save(genre);

			}
		}
	}

	@Override
	public void deleteBookById(long id) throws Exception {
		Book b = repositoryBook.findById(id);
		if (b != null)
			repositoryBook.delete(b);
		else
			throw new Exception("Book is not found");
	}

	@Override
	public Book getById(long id) {
		return repositoryBook.findById(id);
	}

	@Override
	public boolean giveBook(long id) {
		Book b = repositoryBook.findById(id);
		if (b != null) {
			b.setCount(b.getCount() - 1);
			repositoryBook.save(b);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean returnBook(long id) {
		Book b = repositoryBook.findById(id);
		if (b != null) {
			b.setCount(b.getCount() + 1);
			repositoryBook.save(b);
			return true;
		}
		return false;
	}

	@Override
	public boolean setNumberOfBooks(long id, int count) {
		Book b = repositoryBook.findById(id);
		if (b != null) {
			b.setCount(count);
			repositoryBook.save(b);
			return true;
		}
		return false;
	}

	@Override
	public boolean addComment(Comment comment) {
		Book b = repositoryBook.findById(comment.getBookId());
		if (b != null) {
			repositoryComment.save(comment);
			return true;
		}
		return false;
	}

	@Override
	public boolean addAnonimousComment(String comment, long bookId) {
		Book b = repositoryBook.findById(bookId);
		if (b != null) {
			repositoryComment.save(new Comment(comment, bookId));
			return true;
		}
		return false;
	}

	@Override
	public List<Comment> getCommentsForBook(long id) {
		return repositoryComment.findByBookId(id);
	}
}
