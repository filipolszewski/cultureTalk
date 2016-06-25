package pl.olszewski.culturetalk.webservice.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import pl.olszewski.culturetalk.entity.Event;
import pl.olszewski.culturetalk.entity.Institution;

public interface EventRepository extends CrudRepository<Event, Integer> {

	List<Event> findByInstitution(Institution inst);

	List<Event> findByIdIn(List<Integer> idList);

	// new PageRequest(pageNo,pageSize)
	@Query("Select e from Event e where e.institution = :idInst and lower(e.name) like :pat1 order by e.date")
	List<Event> findMatchingEvents(@Param("idInst") Institution idInst, @Param("pat1") String searchStr);

	@Query("Select e from Event e where lower(e.name) like :pat1 order by e.date")
	List<Event> findMatchingEvents(@Param("pat1") String lowerCase, Pageable pageable);

	@Query("Select e from Event e where e.province.idProvince = :idProv and lower(e.name) like :pat1 order by e.date")
	List<Event> findMatchingEvents(@Param("pat1") String lowerCase, @Param("idProv") Integer idProv,
			Pageable pageable);

}
