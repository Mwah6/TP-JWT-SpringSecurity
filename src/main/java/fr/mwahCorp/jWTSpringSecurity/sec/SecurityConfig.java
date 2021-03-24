package fr.mwahCorp.jWTSpringSecurity.sec;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private UserDetailsService userDetailsService; //-> Système d'autentification basé sur un service
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	//-> La méthode qu'on utilise pour dire à Spring Security comment on va chercher les utilisateurs et les rôles
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//		auth.inMemoryAuthentication()
		//		.withUser("admin").password("1234").roles("ADMIN", "User")
		//		.and()
		//		.withUser("user").password("1234").roles("USER");
		//Version en dur pour pouvoir tester, sans les classes User et Role
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);	
		//->Ondit à Spring Security que l'authentification sera basé sur UserDetailsService
	}
	//-> La méthode qui va nous permettre de définir les droits d'accès, les filtres, quelles routes nécessitent d'être authentifier 
	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable();
//		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
////		http.formLogin(); //Fait parti de Spring Security. Formulaire d'authentification préexistant
//		
//		http.authorizeRequests().antMatchers("/login/**",  "/register/**", "/users/**").permitAll();
//		http.authorizeRequests().antMatchers(HttpMethod.POST,  "/task/**").hasAuthority("ADMIN");// Dit à Spring qu'il faut avoir le statut d'admin pour faire cela
//		http.authorizeRequests().anyRequest().authenticated(); //Dit à Spring que toutes les ressources de l'application nécessitent une authentification
//		http.addFilter(new JWTAuthenticationFilter(authenticationManager()));
		
		

		http.csrf().disable() //-On n'utilse pas les cookies donc c'est inutile	
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//désactive la création de session
		.and()
		.authorizeRequests().antMatchers("/register/**", "/login/**").permitAll()
		.antMatchers(HttpMethod.POST, "/tasks/**").hasAuthority("ADMIN")
		.anyRequest().authenticated()
		.and()
		.addFilter(new JWTAuthenticationFilter(authenticationManager()))//méthode que nous héritons de la classe WebSecurityConfigurerAdapter
		.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}


}
