package main.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import main.domain.Book;
@Transactional
public interface BookRepository extends CrudRepository<Book, Long> {

	@PostFilter("hasPermission(filterObject, 'READ')")
	List<Book> findAll();

	@PostAuthorize("hasPermission(returnObject, 'READ')")
	Book findById(long id);

	@PreAuthorize("hasRole('ROLE_USER')")
	@Modifying(clearAutomatically = true)
	@Query("update Book b set b.count=:count where b.id=:id")
	int updateBookCountById(@Param("id") long id, @Param("count")int count);
	
	@PostAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	long count();

	@PreAuthorize("hasPermission(#b, 'WRITE')")
	void delete(Book b);

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	Book save(Book b);
}
