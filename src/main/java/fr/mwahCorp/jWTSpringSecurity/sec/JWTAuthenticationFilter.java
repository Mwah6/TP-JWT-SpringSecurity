package fr.mwahCorp.jWTSpringSecurity.sec;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.mwahCorp.jWTSpringSecurity.entities.AppUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
//C'est une classe qui intervient spécifiquement sur les opérations d'autentification donc elle doit hériter de "UsernamePasswordAuthenticationFilter"
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	private AuthenticationManager authenticationManager;//vient de spring

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) { //Constructeur avec paramètre
		super();
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		//On récupère tout d'abord le username et le password
		AppUser user=null;
		try {
			//si on passe par le format Json, on utilise "ObjectMapper" pour désérialiser (c'est le cas ici)
			user = new ObjectMapper().readValue(request.getInputStream(),  AppUser.class);
			//Le mieux aurait été de généré un bean "ObjectMapper" car sous cette forme, il sera instancié plein de fois

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		System.out.println("1******************");
		System.err.println("1username:" + user.getUsername());
		System.err.println("1password:" + user.getPassword());
		
		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException , ServletException {

		User springUser = (User)authResult.getPrincipal();
		String jwtToken=Jwts.builder()
				.setSubject(springUser.getUsername())
				.setExpiration(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
				.claim("roles", springUser.getAuthorities())
				.compact();
		response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX+jwtToken);
	}
}
