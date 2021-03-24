package fr.mwahCorp.jWTSpringSecurity.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.mwahCorp.jWTSpringSecurity.dao.AppRoleRepository;
import fr.mwahCorp.jWTSpringSecurity.dao.AppUserRepository;
import fr.mwahCorp.jWTSpringSecurity.entities.AppRole;
import fr.mwahCorp.jWTSpringSecurity.entities.AppUser;

@Service
@Transactional
public class AccountServiceImpl implements AccountService{

	@Autowired
	private AppUserRepository userRepository;
	@Autowired
	private AppRoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public AppUser saveUser(AppUser u) {
		u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
		return userRepository.save(u);
	}

	@Override
	public AppRole saveRole(AppRole r) {
		return roleRepository.save(r);
	}

	@Override
	public AppUser findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public void addRoleToUser(String username, String Role) {
		AppUser appUser = userRepository.findByUsername(username);
		AppRole appRole = roleRepository.findByRole(Role);
		appUser.getRoles().add(appRole);
		
	}

}
