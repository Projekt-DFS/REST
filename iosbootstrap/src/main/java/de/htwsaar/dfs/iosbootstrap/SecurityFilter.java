package de.htwsaar.dfs.iosbootstrap;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

import de.htwsaar.dfs.iosbootstrap.model.User;
import de.htwsaar.dfs.iosbootstrap.services.UserService;

@Provider
public class SecurityFilter implements ContainerRequestFilter {

	private static final String AUTHENTICATION_HEADER_KEY = "Authorization";
	private static final String AUTHENTICATION_HEADER_PREFIX = "Basic";
	private static final String SECURED_URL_PREFIX = "images";
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		if(requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)) {
			List<String> authHeader = requestContext.getHeaders().get(AUTHENTICATION_HEADER_KEY);
			if(authHeader != null && authHeader.size() > 0) {
				String authToken = authHeader.get(0);
				authToken = authToken.replaceFirst(AUTHENTICATION_HEADER_PREFIX, "");
				authToken = authToken.trim();
				String decodedString = Base64.decodeAsString(authToken);
				StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
				String username = tokenizer.nextToken();
				String password = tokenizer.nextToken();
				
				if(authenticate(username, password)) {
					return;
				}
			}
			Response unAuthStatus = Response.status(Response.Status.UNAUTHORIZED)
					.entity("User cannot access the resource.").build();
			requestContext.abortWith(unAuthStatus);
		}
		
	}
	
	private boolean authenticate(String username , String password) {
		List<User> users = new UserService().getAllUsers();
		for (User user : users) {
			if (user.getUsername().equals(username))
				if(user.getUserpasswd().equals(password))
					return true;
		}
		
		return false;
	}
	
	

}
