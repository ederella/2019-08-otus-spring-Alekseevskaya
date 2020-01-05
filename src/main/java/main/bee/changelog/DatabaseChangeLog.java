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
		authors.insert(createAuthor("Ильф", "Илья",	"Арнольдович"));
		authors.insert(createAuthor("Петров","Евгений","Петрович"));
		authors.insert(createAuthor("Брэдбери","Рэй",""));
		authors.insert(createAuthor("Донцова","Дарья",""));
		authors.insert(createAuthor("Кастанеда","Карлос",""));
		authors.insert(createAuthor("Гослинг","Джеймс",""));
		authors.insert(createAuthor("Джой","Билл",""));
		authors.insert(createAuthor("Стил","Гай",""));
		authors.insert(createAuthor("Брача","Гилард",""));
		authors.insert(createAuthor("Баксли","Алекс",""));
	};

	private BasicDBObject createAuthor(String surname, String name, String secondName)	{
		return new BasicDBObject()
				.append("surname", surname)
				.append("name", name)
				.append("secondname",secondName);
	}
	
	@ChangeSet(order = "002", id = "insertGenres", author = "Veronika")
	public void insertGenres(DB db) {
		DBCollection genres = db.getCollection("genres");
		genres.insert(new BasicDBObject().append("genreName", "Сатира"));
		genres.insert(new BasicDBObject().append("genreName", "Детектив"));
		genres.insert(new BasicDBObject().append("genreName", "Рассказы"));
		genres.insert(new BasicDBObject().append("genreName", "Фантастика"));
		genres.insert(new BasicDBObject().append("genreName", "Философский роман"));
		genres.insert(new BasicDBObject().append("genreName", "Эзотерика"));
		genres.insert(new BasicDBObject().append("genreName", "Компьютерная литература"));
		
	};

	@ChangeSet(order = "003", id = "insertBooks", author = "Veronika")
	public void insertBooks(DB db) {
		
		DBCollection authors = db.getCollection("authors");
		DBCollection books = db.getCollection("books");

		ArrayList<String> genresList = new ArrayList<String>();
		genresList.add("Сатира");

		ArrayList<DBRef> authorsList = new ArrayList<DBRef>();
		authorsList.add(new DBRef(db.getName(), "authors", authors.findOne(createAuthor("Ильф", "Илья", "Арнольдович")).get("_id")));
		authorsList.add(new DBRef(db.getName(),"authors", authors.findOne(createAuthor("Петров","Евгений","Петрович")).get("_id")));		

		books.insert(new BasicDBObject()
				.append("bookname", "Золотой теленок")
				.append("numberavailable", 10)
				.append("authors", authorsList)
				.append("genres", genresList));
		
		books.insert(new BasicDBObject()
				.append("bookname", "Двенадцать стульев")
				.append("numberavailable", 8)
				.append("authors", authorsList)
				.append("genres", genresList));
		
		genresList = new ArrayList<String>();
		genresList.add("Фантастика");

		authorsList = new ArrayList<DBRef>();
		authorsList.add(new DBRef(db.getName(),"authors", authors.findOne(createAuthor("Брэдбери", "Рэй", "")).get("_id")));
		
		books.insert(new BasicDBObject()
				.append("bookname", "О скитаньях вечных и о Земле")
				.append("numberavailable", 10)
				.append("authors", authorsList)
				.append("genres", genresList));
	};
}
