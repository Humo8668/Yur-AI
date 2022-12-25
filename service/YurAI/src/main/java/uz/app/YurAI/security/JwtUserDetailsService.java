package uz.app.YurAI.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import uz.app.YurAI.security.model.IUser;
import uz.app.YurAI.security.model.SecurityUser;
import uz.app.YurAI.security.service.UserService;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		IUser user = userService.getUserByLogin(username);
		if(user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		
		SecurityUser securityUser = new SecurityUser(user.getLogin(), user.getPassword(), new ArrayList<>());
		return securityUser;
		/*if ("admin".equals(username)) {
			uz.app.YurAI.security.model.SecurityUser user = new uz.app.YurAI.security.model.SecurityUser("admin", "$2a$12$6xzC2dO8wH0QEiJGOIRDA.zqqYfrfa1Q2TBhplVzhDXrcaQuqVsmm", new ArrayList<>());
			return user;
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}*/
	}
}