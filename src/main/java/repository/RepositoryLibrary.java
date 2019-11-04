package repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import main.domain.Book;

public interface RepositoryLibrary extends CrudRepository{

	List<Book> findAll();
}
