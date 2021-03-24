package fr.mwahCorp.jWTSpringSecurity.sec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
	//OncePerRequestFilter->pour chaque requête, il intervient


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		response.addHeader("Access-Control-Allow-Origin",  "*");
		//CORS->Le navigateur doit prendre l'autorisation du serveur
		response.addHeader("Access-Control-Allow-Headers",  "Origin, Accept, X-Request-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, authorization");
		//Liste des Headers que le serveur autorise
		response.addHeader("Access-Control-Expose-Headers",  "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, authorization");
		//Exposition des headers => Permet à l'application front end de lire ces entêtes
		response.addHeader("Access-Control-Allow-Methods",  "GET, POST, DELETE, PUT, PATCH");
		if (request.getMethod().equals("OPTIONS")) {
			response.setStatus(HttpServletResponse.SC_OK);
		//Une requête avec option serait bloquée. Hors, une requêt avec option ne veut pas une ressource mais interroger le serveur
		//=>On lui octroie donc un droit d'accès, pas besoin d'utiliser les règles de sécurité
		//le serveur lui répond avec les 3 headers précdédents
		}
		else {
			String jwtToken = request.getHeader(SecurityConstants.HEADER_STRING);
			System.out.println(jwtToken);
			if(jwtToken==null || !jwtToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
				filterChain.doFilter(request, response); return;
			}
			Claims claims=Jwts.parser()
					.setSigningKey(SecurityConstants.SECRET)
					.parseClaimsJws(jwtToken.replaceAll(SecurityConstants.TOKEN_PREFIX,  ""))
					.getBody();
			String username = claims.getSubject();
			ArrayList<Map<String,String>> roles = (ArrayList<Map<String, String>>) claims.get("roles");
			Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			roles.forEach(r->{
				authorities.add(new SimpleGrantedAuthority(r.get("authority")));
			});
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				//SecurityContextHolder->permet d'accéder au contexte de sécurité de Spring
				filterChain.doFilter(request, response);
		}
	}

}
