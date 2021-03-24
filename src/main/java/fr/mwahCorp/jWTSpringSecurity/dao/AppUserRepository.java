package fr.mwahCorp.jWTSpringSecurity.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.mwahCorp.jWTSpringSecurity.entities.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long>{
	public AppUser findByUsername(String username);
}
