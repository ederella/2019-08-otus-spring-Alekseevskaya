package repository;

import org.springframework.data.repository.CrudRepository;

import main.domain.Book;

public interface RepositoryBook extends CrudRepository<Book, Integer>{

}
