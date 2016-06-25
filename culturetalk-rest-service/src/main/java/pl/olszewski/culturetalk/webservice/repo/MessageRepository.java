package pl.olszewski.culturetalk.webservice.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import pl.olszewski.culturetalk.entity.Message;
import pl.olszewski.culturetalk.entity.User;

public interface MessageRepository extends CrudRepository<Message, Integer> {

	List<Message> findByIdMessageGreaterThanAndUserTo(Integer id, User userTo);

	@Query("Select m from Message m join fetch m.userFrom u1 join fetch m.userTo u2 where (lower(u1.name) "
			+ "like :pat1 or lower(u2.name) like :pat1) and (m.dateSent >= :dateFrom and m.dateSent <= :dateTo) order by m.dateSent desc")
	List<Message> findMatchingMessages(@Param("pat1") String string, @Param("dateFrom") Date date,
			@Param("dateTo") Date dateTo, Pageable pageable);
}
