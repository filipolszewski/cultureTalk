package pl.olszewski.culturetalk.webservice.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import pl.olszewski.culturetalk.entity.Institution;
import pl.olszewski.culturetalk.entity.User;

@SuppressWarnings("serial")
public class SecurityUser implements UserDetails {

	private String email;
	private String password;
	private String role;

	public SecurityUser(User user) {
		if (user != null) {
			email = user.getEmail();
			password = user.getPassword();
			if (user.isAdmin()) {
				setRole("ROLE_ADMIN");
			} else {
				setRole("ROLE_USER");
			}
		}
	}

	public SecurityUser(Institution inst) {
		if (inst != null) {
			email = inst.getEmail();
			password = inst.getPassword();
			setRole("ROLE_USER");
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority role;
		if (this.role == "ROLE_USER") {
			role = new SimpleGrantedAuthority("ROLE_USER");
		} else {
			role = new SimpleGrantedAuthority("ROLE_ADMIN");
		}
		List<SimpleGrantedAuthority> lista = new ArrayList<>();
		lista.add(role);
		return lista;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
