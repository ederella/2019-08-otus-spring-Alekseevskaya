package main.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "genres")
public class Genre {
	@Id
	private String id;

	@Field(value = "genreName")
	private String genreName;

	public Genre(String id, String genreName) {
		this.genreName = genreName;
	}

	public Genre() {
		this.genreName = "";
	}

	public Genre(String genreName) {
		this.genreName = genreName;
	}

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return genreName;
	}
}
