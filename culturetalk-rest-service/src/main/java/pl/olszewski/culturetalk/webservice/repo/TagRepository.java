package pl.olszewski.culturetalk.webservice.repo;

import org.springframework.data.repository.CrudRepository;

import pl.olszewski.culturetalk.entity.Tag;

public interface TagRepository extends CrudRepository<Tag, Integer> {

}
