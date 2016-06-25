package pl.olszewski.culturetalk.webservice.repo;

import java.util.List;

public interface EventFinder {

	List<Integer> findIdsByProvinceAndTags(Integer idProvince, List<Integer> tags, Integer integer);
}
