package repository;

import org.springframework.data.repository.CrudRepository;

import main.domain.Comment;

public interface RepositoryComment extends CrudRepository<Comment, Integer>{

}
