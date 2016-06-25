package pl.olszewski.culturetalk.webservice.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import pl.olszewski.culturetalk.entity.Institution;

public interface InstitutionRepository extends CrudRepository<Institution, Integer> {

	Institution findByEmail(String email);

	@Query("Select i from Institution i where lower(i.name) like :pat and i.province.idProvince = :idProv order by i.name")
	List<Institution> findMatchingInsts(@Param("pat") String pat, @Param("idProv") Integer idProv,
			Pageable pageable);

	@Query("Select i from Institution i where lower(i.name) like :pat or lower(i.address) like :pat order by i.name")
	List<Institution> findMatchingInsts(@Param("pat") String lowerCase, Pageable pageable);

}
