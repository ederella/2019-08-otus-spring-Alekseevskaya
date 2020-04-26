package main.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import main.controller.InlineAuthors;
import main.domain.Book;
@Transactional
@RepositoryRestResource(excerptProjection = InlineAuthors.class)
public interface BookRepository extends CrudRepository<Book, Long> {

	List<Book> findAll();

	Book findById(long id);

	@Modifying
	@Query("update Book b set b.count=:count where b.id=:id")
	@RestResource(exported = false)
	int updateBookCountById(@Param("id") long id, @Param("count")int count);

	long count();
	
	@RestResource(path = "findByBookName", rel = "findByBookName")
	List<Book> findByBookName(String bookName);
	
	@Query("SELECT b FROM Book b WHERE b.bookName LIKE CONCAT('%',:bookName,'%')")
	List<Book> findByPartOfBookName(@Param("bookName") String bookName);

}
