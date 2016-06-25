package pl.olszewski.culturetalk.webservice.repo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

@Service
public class EventFinderImpl implements EventFinder {

	private EntityManagerFactory emFactory;

	public EventFinderImpl() {
		this.emFactory = Persistence.createEntityManagerFactory("default");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> findIdsByProvinceAndTags(Integer idProvince, List<Integer> tags, Integer maxResults) {
		EntityManager entityManager = emFactory.createEntityManager();
		String string = buildQuery(idProvince, tags);

		Query query = entityManager.createNativeQuery(string);
		query.setMaxResults(maxResults);
		System.out.println(query.toString());
		List<Object[]> lista = query.getResultList();
		List<Integer> eventIds = new ArrayList<>();
		for (Object[] obj : lista) {
			eventIds.add((Integer) obj[0]);
		}
		return eventIds;
	}

	private String buildQuery(Integer idProvince, List<Integer> tags) {
		StringBuilder buffer = new StringBuilder("SELECT e.id_event, (");
		for (int i = 0; i < tags.size(); i++) {
			buffer.append("sign(coalesce(tfe");
			buffer.append(i);
			buffer.append(".id_tag, 0))");
			if (i != tags.size() - 1) {
				buffer.append(" + ");
			}
		}
		buffer.append(") AS sum FROM Event e ");
		for (int i = 0; i < tags.size(); i++) {
			Integer idTag = tags.get(i);
			buffer.append("LEFT OUTER JOIN TagForEvent tfe");
			buffer.append(i);
			buffer.append(" ON (e.id_event = tfe");
			buffer.append(i);
			buffer.append(".id_event) AND (tfe");
			buffer.append(i);
			buffer.append(".id_tag = ");
			buffer.append(idTag);
			buffer.append(") ");
		}
		buffer.append("WHERE ((");
		for (int i = 0; i < tags.size(); i++) {
			buffer.append("tfe");
			buffer.append(i);
			buffer.append(".id_tag IS NOT NULL");
			if (i != tags.size() - 1) {
				buffer.append(" OR ");
			}
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String today = df.format(new Date());
		buffer.append(") AND e.date > '");
		buffer.append(today);
		// buffer.append("'::date");
		buffer.append("' AND e.id_province = ");
		buffer.append(idProvince);
		buffer.append(") ORDER BY sum DESC");
		return buffer.toString();
	}

}
