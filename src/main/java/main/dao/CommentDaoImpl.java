package main.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import main.domain.Book;
import main.domain.Comment;

@Repository
@Transactional
public class CommentDaoImpl implements CommentDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void add(Comment comment) {		
		em.persist(comment);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> findByBook(Book book) {
		String sql = "SELECT c FROM Comment c WHERE c.book = :book";
		Query query = em.createQuery(sql);
		query.setParameter("book", book);
		return query.getResultList();
	}

	@Override
	public int deleteById(long id) {
		String sql = "DELETE FROM Comment c WHERE c.id = :id";
		Query query = em.createQuery(sql);
		query.setParameter("id", id);
		return query.executeUpdate();
	}

}
