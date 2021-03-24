package fr.mwahCorp.jWTSpringSecurity.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.mwahCorp.jWTSpringSecurity.entities.AppUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
//Va permettre l'autentification par userDetailService
	@Autowired
	private AccountService accountService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = accountService.findByUsername(username);
		if (user==null) throw new UsernameNotFoundException(username);
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		//Les objets de type "Role" dans Spring Security sont des objets GrantedAuthority
		user.getRoles().forEach(r->{
			//On peut retourner les objets de type Role à partir de l'utilisateur parce qu'on a définit la collection des "Role" en tant que "Eager"
			authorities.add(new SimpleGrantedAuthority(r.getRole()));
			
		});
		return new User(user.getUsername(), user.getPassword(), authorities);
	}

}
