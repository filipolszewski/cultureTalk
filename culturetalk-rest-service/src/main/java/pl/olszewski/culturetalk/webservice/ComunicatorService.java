
package pl.olszewski.culturetalk.webservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.olszewski.culturetalk.entity.Event;
import pl.olszewski.culturetalk.entity.Province;
import pl.olszewski.culturetalk.entity.Tag;
import pl.olszewski.culturetalk.entity.User;
import pl.olszewski.culturetalk.webservice.data.EventCriteria;
import pl.olszewski.culturetalk.webservice.data.EventInfo;
import pl.olszewski.culturetalk.webservice.data.MessageData;
import pl.olszewski.culturetalk.webservice.data.NewEventData;
import pl.olszewski.culturetalk.webservice.data.NewInstitutionData;
import pl.olszewski.culturetalk.webservice.data.NewUserData;
import pl.olszewski.culturetalk.webservice.data.StateInfo;
import pl.olszewski.culturetalk.webservice.data.StateRequest;
import pl.olszewski.culturetalk.webservice.data.UserData;
import pl.olszewski.culturetalk.webservice.security.SecurityUser;

@Controller
@RequestMapping("/culturetalk/ws")
public class ComunicatorService {

	@Autowired
	private ServiceController controler;
	private Integer reqUserid;
	private static final Integer maxUserAmount = 200;

	@RequestMapping(value = "/status", method = RequestMethod.POST)
	public @ResponseBody String setUserStatus(@RequestBody Boolean b) {
		reqUserid = getRequestingUserId();
		return controler.setUserStatus(reqUserid, b);
	}

	@RequestMapping(value = "/message", method = RequestMethod.POST)
	public @ResponseBody String sendMessage(@RequestBody MessageData mData) {
		reqUserid = getRequestingUserId();
		return controler.sendMessage(reqUserid, mData.getToUser().getId(), mData.getMessage());
	}

	@RequestMapping(value = "/newState", method = RequestMethod.POST)
	public @ResponseBody StateInfo getNewState(@RequestBody StateRequest request) {
		reqUserid = getRequestingUserId();
		return controler.getNewState(request, reqUserid);
	}

	@RequestMapping(value = "/newEvent", method = RequestMethod.POST)
	public @ResponseBody Integer createEvent(@RequestBody NewEventData event) {
		return controler.createEvent(event);
	}

	@RequestMapping(value = "/deleteEvent", method = RequestMethod.POST)
	public @ResponseBody Integer createEvent(@RequestBody Integer idEvent) {
		return controler.deleteEvent(idEvent);
	}

	@RequestMapping(value = "/deleteInstitution", method = RequestMethod.POST)
	public @ResponseBody Integer createInstitution(@RequestBody Integer idInst) {
		return controler.deleteInstitution(idInst);
	}

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public @ResponseBody Integer registerNewUser(@RequestBody NewUserData newUserData) {
		return controler.registerUser(newUserData);
	}

	@RequestMapping(value = "/registerInstitution", method = RequestMethod.POST)
	public @ResponseBody Integer registerNewInstitution(@RequestBody NewInstitutionData newInstitution) {
		return controler.registerInstitution(newInstitution);
	}

	@RequestMapping(value = "/findUsers", method = RequestMethod.POST)
	public @ResponseBody List<UserData> findUsers(@RequestBody String findString) {
		List<User> matchingUsers = controler.findMatchingUsers(findString, 0, maxUserAmount);
		List<UserData> usersData = new ArrayList<>();
		for (User user : matchingUsers) {
			usersData.add(new UserData(user.getIdUser(), user.getName(), user.getEmail()));
		}
		return usersData;
	}

	@RequestMapping(value = "/findEvents", method = RequestMethod.POST)
	public @ResponseBody List<EventInfo> findEvents(@RequestBody EventCriteria criteria) {

		return controler.findEvents(criteria);
	}
	
	@RequestMapping(value = "/findOneEvent", method = RequestMethod.POST)
	public @ResponseBody EventInfo findOneEvent(@RequestBody Integer idEvent) {
		return new EventInfo(controler.findOneEvent(idEvent));
	}

	@RequestMapping(value = "/findInstutitionsEvents", method = RequestMethod.POST)
	public @ResponseBody List<Event> findInstitutionsEvents(@RequestBody Integer idInst) {
		return controler.findEventByInstitution(idInst);
	}

	@RequestMapping(value = "/getTags", method = RequestMethod.POST)
	public @ResponseBody List<Tag> getTags() {
		return controler.getTags();
	}

	@RequestMapping(value = "/getProvinces", method = RequestMethod.POST)
	public @ResponseBody List<Province> getProvinces() {
		return controler.getProvinces();
	}

	private Integer getRequestingUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		SecurityUser loggedUser = (SecurityUser) auth.getPrincipal();
		return controler.findUserByEmail(loggedUser.getUsername()).getIdUser();
	}

}
