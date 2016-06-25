package pl.olszewski.culturetalk.webservice.repo;

import org.springframework.data.repository.CrudRepository;

import pl.olszewski.culturetalk.entity.Institution;
import pl.olszewski.culturetalk.entity.TagForInstitution;

public interface TagForInstitutionRepository extends CrudRepository<TagForInstitution, Integer> {

	void deleteByIdInstitution(Institution edited);

}
