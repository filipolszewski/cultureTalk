package pl.olszewski.culturetalk.webservice.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import pl.olszewski.culturetalk.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	
	List<User> findByIdUserIn(List<Integer> idList);
	
	User findByEmail(String email);
	
	@Query("Select u from User u where lower(u.name) like :pat1 order by u.name")
	List<User> findMatchingUsers(@Param("pat1") String pat1, Pageable pageable);

	User findUserByName(String userName);
}
