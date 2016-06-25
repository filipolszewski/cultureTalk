package pl.olszewski.culturetalk.webservice.repo;

import org.springframework.data.repository.CrudRepository;

import pl.olszewski.culturetalk.entity.Event;
import pl.olszewski.culturetalk.entity.TagForEvent;

public interface TagForEventRepository extends CrudRepository<TagForEvent, Integer> {

	void deleteByIdEvent(Event event);

}
