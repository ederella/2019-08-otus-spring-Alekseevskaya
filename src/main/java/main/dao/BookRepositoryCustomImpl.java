package main.dao;

import java.awt.print.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.client.result.UpdateResult;
@Component
public class BookRepositoryCustomImpl implements BookRepositoryCustom {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public int updateBookCountById(String id, int count) {
		Query query = new Query(Criteria.where("_id").is(id));
		Update update = new Update();
		update.set("numberavailable", count);
		UpdateResult r = mongoTemplate.updateFirst(query, update, "books");
		if (r != null)
			return (int) r.getModifiedCount();
		else
			return 0;
	}

}
