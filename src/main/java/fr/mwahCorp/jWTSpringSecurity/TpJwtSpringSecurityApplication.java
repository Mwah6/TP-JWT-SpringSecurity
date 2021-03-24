package fr.mwahCorp.jWTSpringSecurity;



import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import fr.mwahCorp.jWTSpringSecurity.dao.AppUserRepository;
import fr.mwahCorp.jWTSpringSecurity.dao.TaskRepository;
import fr.mwahCorp.jWTSpringSecurity.entities.AppRole;
import fr.mwahCorp.jWTSpringSecurity.entities.AppUser;
import fr.mwahCorp.jWTSpringSecurity.entities.Task;
import fr.mwahCorp.jWTSpringSecurity.service.AccountService;

@SpringBootApplication
public class TpJwtSpringSecurityApplication implements CommandLineRunner{
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AppUserRepository appUserRepository;

	public static void main(String[] args) {
		SpringApplication.run(TpJwtSpringSecurityApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void run(String... arg0) throws Exception {
		accountService.saveUser(new AppUser(null, "user", "1234", null));
		accountService.saveUser(new AppUser(null, "admin", "1234", null));
		accountService.saveRole(new AppRole(null, "USER"));
		accountService.saveRole(new AppRole(null, "ADMIN"));
		accountService.addRoleToUser("user",  "USER");
		accountService.addRoleToUser("admin",  "USER");
		accountService.addRoleToUser("admin",  "ADMIN");
		Stream.of("T1","T2","T3").forEach(t->{
			taskRepository.save(new Task(null, t));
		});
		appUserRepository.findAll().forEach(u->{
			System.out.println("Username : " +u.getUsername());
			System.out.println("Password  : "+u.getPassword());
			u.getRoles().forEach(r->{
				System.out.println("   - Role : " + r.getRole());
			});
		});
		taskRepository.findAll().forEach(t->{
			System.out.println(t.getTaskName());
		});
		
//		taskRepository.save(new Task (null ,"T1"));
//		taskRepository.save(new Task  (null ,"T2"));
//		taskRepository.save(new Task (null ,"T3"));
//
//		accountService.saveRole(new AppRole(null, "USER"));
//		accountService.saveRole(new AppRole(null, "ADMIN"));
//		accountService.saveUser(new AppUser(null, "user", "1234", null));
//		accountService.saveUser(new AppUser(null, "admin", "1234", null));
//
//		accountService.addRoleToUser("user",  "USER");
//		accountService.addRoleToUser("admin",  "USER");
//		accountService.addRoleToUser("admin",  "ADMIN");
	}
}
