package fr.mwahCorp.jWTSpringSecurity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import fr.mwahCorp.jWTSpringSecurity.entities.Task;

//@RepositoryRestResource
//-> Génére l'Api Rest (TaskController) Mais on ne le fait pas dans cet exemple
public interface TaskRepository extends JpaRepository<Task, Long>{

}
