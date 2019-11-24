package main.bee.changelog;

import java.util.ArrayList;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBRef;
import com.mongodb.client.MongoDatabase;

@ChangeLog
public class DatabaseChangeLog {
	
    @ChangeSet(order = "000", id = "dropDB", author = "Veronika", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

	@ChangeSet(order = "001", id = "insertAuthors", author = "Veronika")
	public void insertAuthors(DB db) {
		DBCollection authors = db.getCollection("authors");
		BasicDBObject author = new BasicDBObject().append("surname", "Ильф").append("name", "Илья").append("secondname",
				"Арнольдович");
		authors.insert(author);

		author = new BasicDBObject().append("surname", "Петров").append("name", "Евгений").append("secondname",
				"Петрович");
		authors.insert(author);
	};

	@ChangeSet(order = "002", id = "insertGenres", author = "Veronika")
	public void insertGenres(DB db) {
		DBCollection genres = db.getCollection("genres");
		BasicDBObject doc = new BasicDBObject().append("genreName", "Сатира");
		genres.insert(doc);
	};

	@ChangeSet(order = "003", id = "insertBooks", author = "Veronika")
	public void insertBooks(DB db) {
		DBCollection authors = db.getCollection("authors");

		Object id1 = authors.findOne((new BasicDBObject().append("surname", "Ильф").append("name", "Илья")
				.append("secondname", "Арнольдович"))).get("_id");

		Object id2 = authors.findOne((new BasicDBObject().append("surname", "Петров").append("name", "Евгений")
				.append("secondname", "Петрович"))).get("_id");

		DBCollection books = db.getCollection("books");

		ArrayList<String> genresList = new ArrayList<String>();
		genresList.add("Сатира");

		ArrayList<DBRef> authorsList = new ArrayList<DBRef>();
		authorsList.add(new DBRef("authors", id1));
		authorsList.add(new DBRef("authors", id2));
		
		
		BasicDBObject doc = new BasicDBObject().append("bookname", "Золотой теленок").append("numberavailable", 10)
				.append("authors", authorsList).append("genres", genresList);
		books.insert(doc);
		
		DBCollection comments = db.getCollection("comments");
		BasicDBObject comment = new BasicDBObject().append("readerNickName", "Lermontov").append("commentText",
				"Аффтар жжот! Пеши исчо!").append("book", new DBRef("books", books.findOne(doc).get("_id")));
		comments.insert(comment);
		;
	};


}
