package fr.mwahCorp.jWTSpringSecurity.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.mwahCorp.jWTSpringSecurity.dao.TaskRepository;
import fr.mwahCorp.jWTSpringSecurity.entities.Task;

@RestController
public class TasksController {
	@Autowired 
	private TaskRepository taskRepository;
	
	@GetMapping("/tasks")
	public List<Task> listTasks() {
		return taskRepository.findAll();
	}
	
	@PostMapping("/tasks")
	//@RequestBody -> va chercher dans le contenu de la requête
	public Task save(@RequestBody Task task) {
		return taskRepository.save(task);
	}

}

//	public TasksController(TaskRepository taskRepository) {
//		super();
//		this.taskRepository = taskRepository;
//	} ->L'utilisation d'un constructeur avec paramètre permet de se passer de l'annotation @Autowired
