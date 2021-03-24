package fr.mwahCorp.jWTSpringSecurity.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.mwahCorp.jWTSpringSecurity.entities.AppUser;
import fr.mwahCorp.jWTSpringSecurity.service.AccountService;

@RestController
public class AccountRestController {

	@Autowired
	private AccountService accountService;
	@PostMapping("/register")
	public AppUser register(@RequestBody RegistrationForm userForm) {
		AppUser appUser = new AppUser();
		if(!userForm.getPassword().equals(userForm.getRepassword())) throw new RuntimeException("You must confirm your password");
		AppUser user = accountService.findByUsername(userForm.getUsername());
		if (user!=null) throw new RuntimeException("This user already exists !");
		appUser.setUsername(userForm.getUsername());
		appUser.setPassword(userForm.getPassword());
		accountService.saveUser(appUser);
		accountService.addRoleToUser(userForm.getUsername(), "USER");


		return appUser;
	}
}
