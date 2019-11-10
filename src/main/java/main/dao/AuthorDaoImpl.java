package main.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import main.domain.Author;

@Repository
@Transactional
public class AuthorDaoImpl implements AuthorDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void add(Author author) {
		em.persist(author);
	}

	@Override
	public List<Author> findAll() {
		String sql = "SELECT a FROM Author a";
		TypedQuery<Author> query = em.createQuery(sql, Author.class);
		return query.getResultList();
	}

	@Override
	public Author findById(long id) {
		return em.find(Author.class, id);
	}

	@Override
	public int deleteById(long id) {
		String sql = "DELETE FROM Author a WHERE a.id = :id";
		Query query = em.createQuery(sql);
		query.setParameter("id", id);
		return query.executeUpdate();
	}
}
