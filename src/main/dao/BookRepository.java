package main.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import main.domain.Book;
@Transactional
public interface BookRepository extends CrudRepository<Book, Long> {

	List<Book> findAll();

	Book findById(long id);

	@Modifying
	@Query("update Book b set b.count=:count where b.id=:id")
	int updateBookCountById(@Param("id") long id, @Param("count")int count);

	long count();

}
