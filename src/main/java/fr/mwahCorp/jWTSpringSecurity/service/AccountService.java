package fr.mwahCorp.jWTSpringSecurity.service;

import fr.mwahCorp.jWTSpringSecurity.entities.AppRole;
import fr.mwahCorp.jWTSpringSecurity.entities.AppUser;

public interface AccountService {
	public AppUser saveUser(AppUser u);
	public AppRole saveRole(AppRole r);
	public AppUser findByUsername(String username);
	public void addRoleToUser(String username, String Role);
}
