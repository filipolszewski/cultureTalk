package pl.olszewski.culturetalk.webservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pl.olszewski.culturetalk.entity.Institution;
import pl.olszewski.culturetalk.entity.User;
import pl.olszewski.culturetalk.webservice.ServiceController;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private ServiceController controller;

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		User user = controller.findUserByEmail(name);
		if (user == null) {
			Institution inst = controller.findInstitutionByEmail(name);
			if (inst == null) {
				throw new UsernameNotFoundException("User " + name + " not found");
			}
			return new SecurityUser(inst);
		}
		return new SecurityUser(user);
	}

}
