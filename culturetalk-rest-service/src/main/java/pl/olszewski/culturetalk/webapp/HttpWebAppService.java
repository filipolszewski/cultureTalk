package pl.olszewski.culturetalk.webapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.olszewski.culturetalk.entity.Event;
import pl.olszewski.culturetalk.entity.Institution;
import pl.olszewski.culturetalk.entity.Message;
import pl.olszewski.culturetalk.entity.Province;
import pl.olszewski.culturetalk.entity.Tag;
import pl.olszewski.culturetalk.entity.TagForInstitution;
import pl.olszewski.culturetalk.entity.User;
import pl.olszewski.culturetalk.webservice.ServiceController;
import pl.olszewski.culturetalk.webservice.data.EventInfo;
import pl.olszewski.culturetalk.webservice.data.NewEventData;
import pl.olszewski.culturetalk.webservice.data.NewInstitutionData;
import pl.olszewski.culturetalk.webservice.security.SecurityUser;

@Controller
public class HttpWebAppService implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private ServiceController controler;

	private List<Province> provinces;
	private List<Tag> tags;

	private static final Integer userPerPage = 10;
	private static final Integer eventsPerPage = 10;
	private static final Integer instsPerPage = 10;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		tags = controler.getTags();
		provinces = controler.getProvinces();
	}

	@RequestMapping("/index")
	String index() {
		return "index";
	}

	@RequestMapping("/login")
	String login(Model model) {
		return "login";
	}
	// mapowanie widoków Instytucji

	@Secured("ROLE_USER")
	@RequestMapping(value = "/instPanel")
	String instPanel() {
		return "instPanel";
	}

	@Secured("ROLE_USER")
	@RequestMapping(value = "/settings", method = RequestMethod.GET)
	public String instSettingsForm(Model model) {

		Institution inst = getRequestingInstitution();
		NewInstitutionData data = new NewInstitutionData(inst);

		model.addAttribute("newInstData", data);
		model.addAttribute("provinces", provinces);
		model.addAttribute("tags", tags);
		return "settings";
	}

	@Secured("ROLE_USER")
	@RequestMapping(value = "/settings", method = RequestMethod.POST)
	public String InstSettingsSubmit(@ModelAttribute NewInstitutionData newInstData, Model model) {
		Institution inst = getRequestingInstitution();
		controler.editInstitution(newInstData, inst.getId());
		return "redirect:/instPanel";
	}

	@Secured("ROLE_USER")
	@RequestMapping(value = "/myEvent/{idEvent}", method = RequestMethod.GET)
	String myEventForm(@PathVariable Integer idEvent, Model model) {
		NewEventData event = new NewEventData(controler.findOneEvent(idEvent));

		model.addAttribute("tags", tags);
		model.addAttribute("myEventData", event);
		return "myEvent";
	}

	@Secured("ROLE_USER")
	@RequestMapping(value = "/myEvent/{idEvent}", method = RequestMethod.POST)
	String myEventPost(@PathVariable String idEvent, @ModelAttribute NewEventData myEventData, Model model) {
		Institution reqInst = getRequestingInstitution();
		myEventData.setInstitution(reqInst.getId());
		myEventData.setProvince(reqInst.getProvince().getIdProvince());
		controler.editEvent(myEventData, Integer.parseInt(idEvent));
		return "redirect:/instEvents";
	}

	@Secured("ROLE_USER")
	@RequestMapping("/instEvents")
	String instEvents(@RequestParam(required = false) String query, Model model,
			@RequestParam(required = false) Integer pageNo) {
		
		if (pageNo == null) {
			pageNo = 0;
		}

		Institution inst = getRequestingInstitution();
		List<Event> eventList = new ArrayList<>();
		if (query == null) {
			eventList = controler.findEventsFromInstitution(inst.getId(), "", pageNo, eventsPerPage);
		} else {
			eventList = controler.findEventsFromInstitution(inst.getId(), query, pageNo, eventsPerPage);
		}
		List<EventInfo> events = new ArrayList<>();
		for (Event event : eventList) {
			events.add(new EventInfo(event));
		}
		model.addAttribute("events", events);
		model.addAttribute("query", query);
		model.addAttribute("page", pageNo);
		model.addAttribute("lastPage", (events.size() < eventsPerPage) ? true : false);
		return "instEvents";
	}

	@Secured("ROLE_USER")
	@RequestMapping(value = "/newEvent", method = RequestMethod.GET)
	String newEventForm(Model model) {

		NewEventData data = new NewEventData();
		Institution reqInst = getRequestingInstitution();
		data.setAddress(reqInst.getAddress());

		List<Integer> instTags = getInstitutionTags(reqInst);
		for (Integer integer : instTags) {
			data.getTags().add(integer);
		}
		model.addAttribute("tags", tags);
		model.addAttribute("newEventData", data);
		return "newEvent";
	}

	@Secured("ROLE_USER")
	@RequestMapping(value = "/newEvent", method = RequestMethod.POST)
	String newEventSubmit(@ModelAttribute NewEventData newEventData, Model model) {
		Institution reqInst = getRequestingInstitution();
		newEventData.setInstitution(reqInst.getId());
		newEventData.setProvince(reqInst.getProvince().getIdProvince());
		controler.createEvent(newEventData);
		return "redirect:/instPanel";
	}

	@RequestMapping(value = "/registerInst", method = RequestMethod.GET)
	public String registerInstForm(Model model) {
		NewInstitutionData newInstitutionData = new NewInstitutionData();
		model.addAttribute("newInstData", newInstitutionData);
		model.addAttribute("provinces", provinces);
		model.addAttribute("tags", tags);
		return "registerInst";
	}

	@RequestMapping(value = "/registerInst", method = RequestMethod.POST)
	public String registerInstSubmit(@ModelAttribute NewInstitutionData newInstData, Model model) {
		controler.registerInstitution(newInstData);
		return "redirect:/login/regSuccess";
	}

	// mapowanie widoków Adminowych

	@Secured("ROLE_ADMIN")
	@RequestMapping("/admin")
	String indexAdmin() {
		return "indexAdmin";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("/users")
	String users(@RequestParam(required = false) String searchStr, Model model,
			@RequestParam(required = false) Integer pageNo) {

		if (pageNo == null) {
			pageNo = 0;
		}
		List<User> userList = new ArrayList<>();
		if (searchStr == null) {
			userList = controler.findMatchingUsers("", pageNo, userPerPage);
		} else {
			userList = controler.findMatchingUsers(searchStr.toLowerCase(), pageNo, userPerPage);
		}

		model.addAttribute("userList", userList);
		model.addAttribute("query", searchStr);
		model.addAttribute("page", pageNo);
		model.addAttribute("lastPage", (userList.size() < userPerPage) ? true : false);

		return "users";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("/institutions")
	String institutions(@RequestParam(required = false) String searchStr, Model model,
			@RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer idProv) {
		if (pageNo == null) {
			pageNo = 0;
		}
		model.addAttribute("page", pageNo);
		List<Institution> institutions = new ArrayList<>();
		if (searchStr == null) {
			institutions = controler.findInstitutions("", idProv, pageNo, instsPerPage);
		} else {
			institutions = controler.findInstitutions(searchStr, idProv, pageNo, instsPerPage);
		}
		List<NewInstitutionData> instDataList = new ArrayList<>();
		for (Institution inst : institutions) {
			instDataList.add(new NewInstitutionData(inst));
		}

		model.addAttribute("instDataList", instDataList);
		model.addAttribute("query", searchStr);
		model.addAttribute("lastPage", (instDataList.size() < userPerPage) ? true : false);
		model.addAttribute("idProv", idProv);
		model.addAttribute("provinces", provinces);

		return "institutions";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("/eventsAdmin")
	String eventsAdmin(@RequestParam(required = false) String searchStr, Model model,
			@RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer idProv) {
		if (pageNo == null) {
			pageNo = 0;
		}
		model.addAttribute("page", pageNo);
		List<Event> events = new ArrayList<>();
		if (searchStr == null) {
			events = controler.findMatchingEvents("", idProv, pageNo, eventsPerPage);
		} else {
			events = controler.findMatchingEvents(searchStr, idProv, pageNo, eventsPerPage);
		}
		List<EventInfo> eventList = new ArrayList<>();
		for (Event event : events) {
			eventList.add(new EventInfo(event));
		}

		model.addAttribute("eventList", eventList);
		model.addAttribute("query", searchStr);
		model.addAttribute("lastPage", (eventList.size() < userPerPage) ? true : false);
		model.addAttribute("idProv", idProv);
		model.addAttribute("provinces", provinces);

		return "adminEvents";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/institutionInfo/{idInst}", method = RequestMethod.GET)
	String institutionDataGet(Model model, @PathVariable String idInst) {

		NewInstitutionData inst = new NewInstitutionData(controler.findInstitutionById(Integer.parseInt(idInst)));
		model.addAttribute("instData", inst);
		model.addAttribute("provinces", provinces);
		model.addAttribute("tags", tags);

		return "institutionData";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/institutionInfo/{idInst}", method = RequestMethod.POST)
	String institutionDataPost(@ModelAttribute NewInstitutionData instData, Model model, @PathVariable String idInst) {

		controler.editInstitution(instData, Integer.parseInt(idInst));
		return "redirect:/institutions";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("/messages")
	String messages(@RequestParam(required = false) String searchStr,
			@RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate,
			@RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate, Model model,
			@RequestParam(required = false) Integer pageNo) throws Exception {

		if (pageNo == null) {
			pageNo = 0;
		}
		model.addAttribute("page", pageNo);

		List<Message> msgList = new ArrayList<>();
		if (searchStr == null) {
			msgList = controler.findMatchingMessages("", startDate, endDate, pageNo, userPerPage);
		} else {
			msgList = controler.findMatchingMessages(searchStr, startDate, endDate, pageNo, userPerPage);
		}
		model.addAttribute("query", searchStr);
		model.addAttribute("listOfMsg", msgList);

		Boolean lastPage = (msgList.size() < userPerPage) ? true : false;
		model.addAttribute("lastPage", lastPage);

		if (startDate == null) {
			model.addAttribute("startDate", new Date());
			model.addAttribute("endDate", new Date());
		} else {
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
		}

		return "messages";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/user/{userName}", method = RequestMethod.GET)
	@ResponseBody
	String users(@PathVariable String userName) {

		User user = controler.findUserByName(userName);
		return user.getEmail();
	}

	private Institution getRequestingInstitution() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		SecurityUser loggedUser = (SecurityUser) auth.getPrincipal();
		return controler.findInstitutionByEmail(loggedUser.getUsername());
	}

	private List<Integer> getInstitutionTags(Institution reqInst) {
		List<TagForInstitution> tfis = reqInst.getTfi();
		List<Integer> instTags = new ArrayList<>();
		for (TagForInstitution tfi : tfis) {
			instTags.add(tfi.getIdTag().getId());
		}
		return instTags;
	}

}
