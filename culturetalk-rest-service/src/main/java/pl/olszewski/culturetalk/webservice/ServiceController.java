package pl.olszewski.culturetalk.webservice;

import java.util.Date;
import java.util.List;

import pl.olszewski.culturetalk.entity.Event;
import pl.olszewski.culturetalk.entity.Institution;
import pl.olszewski.culturetalk.entity.Message;
import pl.olszewski.culturetalk.entity.Province;
import pl.olszewski.culturetalk.entity.Tag;
import pl.olszewski.culturetalk.entity.User;
import pl.olszewski.culturetalk.webservice.data.EventCriteria;
import pl.olszewski.culturetalk.webservice.data.EventInfo;
import pl.olszewski.culturetalk.webservice.data.NewEventData;
import pl.olszewski.culturetalk.webservice.data.NewInstitutionData;
import pl.olszewski.culturetalk.webservice.data.NewUserData;
import pl.olszewski.culturetalk.webservice.data.StateInfo;
import pl.olszewski.culturetalk.webservice.data.StateRequest;

public interface ServiceController {

	StateInfo getNewState(StateRequest req, Integer reqUserid);

	Integer registerUser(NewUserData newUserData);
	
	Integer registerInstitution(NewInstitutionData newInstitution);

	User findUserByEmail(String name);

	String sendMessage(Integer idFrom, Integer idTo, String msg);

	String setUserStatus(Integer reqUserid, Boolean b);

	List<User> findMatchingUsers(String fS, Integer pageNo, Integer rowPerPage);

	List<Message> findMatchingMessages(String query, Date date, Date endDate, Integer pageNo, Integer rowperpage);

	User findUserByName(String userName);

	Integer createEvent(NewEventData event);

	List<Tag> getTags();

	Integer deleteEvent(Integer idEvent);

	Integer deleteInstitution(Integer idInst);

	Institution findInstitutionByEmail(String name);

	Institution findInstitutionById(Integer idInst);

	List<Institution> findInstitutions(String string, Integer idProv, Integer pageNo, Integer instsperpage);

	List<EventInfo> findEvents(EventCriteria criteria);

	List<Event> findMatchingEvents(String string, Integer idProv, Integer pageNo, Integer eventsperpage);

	List<Province> getProvinces();

	List<Event> findEventByInstitution(Integer idInst);

	List<Event> findEventsFromInstitution(Integer idInst, String searchStr, Integer pageNo, Integer rowPerPage);

	Event findOneEvent(Integer idEvent);

	void editEvent(NewEventData myEventData, Integer parseInt);

	void editInstitution(NewInstitutionData newInstData, Integer id);

}
