package pl.olszewski.culturetalk.webservice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.olszewski.culturetalk.entity.Event;
import pl.olszewski.culturetalk.entity.Institution;
import pl.olszewski.culturetalk.entity.Message;
import pl.olszewski.culturetalk.entity.Province;
import pl.olszewski.culturetalk.entity.Tag;
import pl.olszewski.culturetalk.entity.TagForEvent;
import pl.olszewski.culturetalk.entity.TagForInstitution;
import pl.olszewski.culturetalk.entity.User;
import pl.olszewski.culturetalk.webapp.UserStatus;
import pl.olszewski.culturetalk.webservice.data.EventCriteria;
import pl.olszewski.culturetalk.webservice.data.EventInfo;
import pl.olszewski.culturetalk.webservice.data.MessageData;
import pl.olszewski.culturetalk.webservice.data.NewEventData;
import pl.olszewski.culturetalk.webservice.data.NewInstitutionData;
import pl.olszewski.culturetalk.webservice.data.NewUserData;
import pl.olszewski.culturetalk.webservice.data.StateInfo;
import pl.olszewski.culturetalk.webservice.data.StateRequest;
import pl.olszewski.culturetalk.webservice.data.UserData;
import pl.olszewski.culturetalk.webservice.repo.EventFinder;
import pl.olszewski.culturetalk.webservice.repo.EventRepository;
import pl.olszewski.culturetalk.webservice.repo.InstitutionRepository;
import pl.olszewski.culturetalk.webservice.repo.MessageRepository;
import pl.olszewski.culturetalk.webservice.repo.ProvinceRepository;
import pl.olszewski.culturetalk.webservice.repo.TagForEventRepository;
import pl.olszewski.culturetalk.webservice.repo.TagForInstitutionRepository;
import pl.olszewski.culturetalk.webservice.repo.TagRepository;
import pl.olszewski.culturetalk.webservice.repo.UserRepository;

@Service
@Transactional
public class ServiceControllerImpl implements ServiceController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private InstitutionRepository instRepo;

	@Autowired
	private MessageRepository msgRepo;

	@Autowired
	private EventRepository eventRepo;

	@Autowired
	private TagRepository tagRepo;

	@Autowired
	private TagForEventRepository tagForEventRepo;

	@Autowired
	private TagForInstitutionRepository tagForInstitutionRepo;

	@Autowired
	private EventFinder eventFinder;

	@Autowired
	private ProvinceRepository provRepo;

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	@Override
	public StateInfo getNewState(StateRequest req, Integer reqUserId) {
		List<MessageData> listaMsgData = new ArrayList<>();
		User user = userRepo.findOne(reqUserId);
		List<Message> listaMsg = msgRepo.findByIdMessageGreaterThanAndUserTo(req.getLastMessageId(), user);
		for (Message message : listaMsg) {
			User u = message.getUserFrom();
			listaMsgData
					.add(new MessageData(message.getIdMessage(), new UserData(u.getIdUser(), u.getName(), u.getEmail()),
							message.getMessage(),
							df.format(message.getDateSent())));
		}
		List<UserStatus> listaSt = new ArrayList<>();
		List<User> listaUser = userRepo.findByIdUserIn(req.getMyUsersIds());
		for (User u : listaUser) {
			listaSt.add(new UserStatus(u.getIdUser(), u.isState()));
		}
		return new StateInfo(listaMsgData, listaSt);
	}

	@Override
	public Integer registerUser(NewUserData data) {

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(data.getPassword());

		User newUser = new User(data.getName(), data.getEmail(), hashedPassword, false, false);
		newUser = userRepo.save(newUser);
		return newUser.getIdUser();
	}

	@Override
	public Integer registerInstitution(NewInstitutionData newInst) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		Institution institution = new Institution(newInst.getName(), passwordEncoder.encode(newInst.getPassword()),
				new Province(newInst.getProvince()), newInst.getAddress(), newInst.getNumber(), newInst.getEmail(),
				newInst.getPage(), newInst.getFacebook(), newInst.getDescription());
		institution = instRepo.save(institution);
		Tag tag;
		for (Integer idTag : newInst.getTags()) {
			tag = tagRepo.findOne(idTag);
			tagForInstitutionRepo.save(new TagForInstitution(institution, tag));
		}
		return institution.getId();
	}

	@Override
	public Integer deleteInstitution(Integer idInst) {

		Institution inst = instRepo.findOne(idInst);
		List<Event> eventList = eventRepo.findByInstitution(inst);
		for (Event event : eventList) {
			deleteEvent(event);
		}
		instRepo.delete(inst);
		return 0;
	}

	@Override
	public Integer createEvent(NewEventData eventData) {
		Event event = eventRepo.save(new Event(eventData));
		Tag tag;
		for (Integer idTag : eventData.getTags()) {
			tag = tagRepo.findOne(idTag);
			tagForEventRepo.save(new TagForEvent(event, tag));
		}

		return event.getId();
	}

	@Override
	public Integer deleteEvent(Integer idEvent) {
		Event event = eventRepo.findOne(idEvent);
		return deleteEvent(event);
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public String sendMessage(Integer idFrom, Integer idTo, String msg) {
		User userTo = userRepo.findOne(idTo);
		Message message = new Message(userRepo.findOne(idFrom), userTo, new Date(), msg);
		msgRepo.save(message);
		return "Wysłano: || " + msg + " || do " + userTo.getName();
	}

	@Override
	public String setUserStatus(Integer id, Boolean b) {
		User user = userRepo.findOne(id);
		user.setState(b);
		return (b ? "User now active!" : "User is now inactive.");
	}

	@Override
	public List<User> findMatchingUsers(String fS, Integer pageNo, Integer rowPerPage) {
		return userRepo.findMatchingUsers("%" + fS + "%", new PageRequest(pageNo, rowPerPage));
	}

	@Override
	public List<EventInfo> findEvents(EventCriteria crit) {
		List<Integer> idList = eventFinder.findIdsByProvinceAndTags(crit.getIdProvince(), crit.getTagList(),
				crit.getMaxResults());
		List<Event> events = eventRepo.findByIdIn(idList);
		List<Event> sortedEvents = new ArrayList<>();
		for (Integer id : idList) {
			for (Event event : events) {
				if (event.getId() == id) {
					sortedEvents.add(event);
				}
			}
		}
		List<EventInfo> eventsInfo = new ArrayList<>();
		for (Event event : sortedEvents) {
			eventsInfo.add(new EventInfo(event.getId(), event.getName(), event.getDate(),
					event.getInstitution().getName(), event.getProvince().getName(), event.getAddress(),
					event.getTicketLink(), event.getDescription(), event.getTfe()));
		}
		return eventsInfo;
	}

	@Override
	public List<Event> findEventsFromInstitution(Integer idInst, String searchStr, Integer pageNo, Integer pageSize) {
		return eventRepo.findMatchingEvents(instRepo.findOne(idInst), "%" + searchStr + "%");
	}
	// new PageRequest(pageNo, pageSize)

	@Override
	public List<Event> findEventByInstitution(Integer idInst) {
		return eventRepo.findByInstitution(instRepo.findOne(idInst));
	}

	@Override
	public List<Message> findMatchingMessages(String fS, Date startDate, Date endDate, Integer pageNo,
			Integer rowPerPage) {
		if (startDate == null) {
			startDate = new Date();
			endDate = dateTommorow(startDate);
		}
		endDate = dateTommorow(endDate);

		List<Message> lista = msgRepo.findMatchingMessages("%" + fS + "%", startDate, endDate,
				new PageRequest(pageNo, rowPerPage));
		return lista;
	}

	@Override
	public User findUserByName(String userName) {
		return userRepo.findUserByName(userName);
	}

	@Override
	public List<Tag> getTags() {
		return (List<Tag>) tagRepo.findAll();
	}

	private Integer deleteEvent(Event event) {
		tagForEventRepo.deleteByIdEvent(event);
		eventRepo.delete(event);
		return 0;
	}

	private Date dateTommorow(Date startDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}

	@Override
	public Institution findInstitutionByEmail(String email) {
		return instRepo.findByEmail(email);
	}

	@Override
	public List<Province> getProvinces() {
		List<Province> lista = new ArrayList<>();
		Province emptyProv = new Province();
		emptyProv.setIdProvince(50); // specjalne ID, które mówi, że nie chcemy
										// szukać po województwie
		emptyProv.setName("");
		lista.add(emptyProv);
		lista.addAll((List<Province>) provRepo.findAll());
		return lista;
	}

	@Override
	public Event findOneEvent(Integer idEvent) {
		return eventRepo.findOne(idEvent);
	}

	@Override
	public void editEvent(NewEventData e, Integer id) {
		Event edited = eventRepo.findOne(id);
		edited.setName(e.getName());
		edited.setDate(e.getDate());
		edited.setAddress(e.getAddress());
		edited.setDescription(e.getDescription());
		edited.setInstitution(instRepo.findOne(e.getInstitution()));
		edited.setProvince(provRepo.findOne(e.getProvince()));
		edited.setTicketLink(e.getTicketLink());
		eventRepo.save(edited);
		tagForEventRepo.deleteByIdEvent(edited);

		Tag tag;
		for (Integer idTag : e.getTags()) {
			tag = tagRepo.findOne(idTag);
			tagForEventRepo.save(new TagForEvent(edited, tag));
		}
	}

	@Override
	public void editInstitution(NewInstitutionData newInst, Integer id) {
		Institution edited = instRepo.findOne(id);
		edited.setName(newInst.getName());
		edited.setFacebook(newInst.getFacebook());
		edited.setAddress(newInst.getAddress());
		edited.setDescription(newInst.getDescription());
		edited.setPage(newInst.getPage());
		edited.setProvince(provRepo.findOne(newInst.getProvince()));
		edited.setNumber(newInst.getNumber());

		instRepo.save(edited);
		tagForInstitutionRepo.deleteByIdInstitution(edited);

		Tag tag;
		for (Integer idTag : newInst.getTags()) {
			tag = tagRepo.findOne(idTag);
			tagForInstitutionRepo.save(new TagForInstitution(edited, tag));
		}
	}

	@Override
	public List<Institution> findInstitutions(String fS, Integer idProv, Integer pageNo, Integer rowPerPage) {
		if (idProv == null || idProv == 50) {
			return instRepo.findMatchingInsts(("%" + fS + "%").toLowerCase(), new PageRequest(pageNo, rowPerPage));
		} else {
			return instRepo.findMatchingInsts(("%" + fS + "%").toLowerCase(), idProv,
					new PageRequest(pageNo, rowPerPage));
		}
	}

	@Override
	public Institution findInstitutionById(Integer idInst) {
		return instRepo.findOne(idInst);
	}

	@Override
	public List<Event> findMatchingEvents(String fS, Integer idProv, Integer pageNo, Integer eventsPerPage) {
		if (idProv == null || idProv == 50) {
			return eventRepo.findMatchingEvents(("%" + fS + "%").toLowerCase(), new PageRequest(pageNo, eventsPerPage));
		} else {
			return eventRepo.findMatchingEvents(("%" + fS + "%").toLowerCase(), idProv,
					new PageRequest(pageNo, eventsPerPage));
		}
	}
}