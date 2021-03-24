package fr.mwahCorp.jWTSpringSecurity.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.mwahCorp.jWTSpringSecurity.entities.AppRole;

public interface AppRoleRepository extends JpaRepository<AppRole, Long>{
	public AppRole findByRole(String Role);
}
