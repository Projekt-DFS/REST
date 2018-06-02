package de.htwsaar.dfs.ios_bootstrap.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.htwsaar.dfs.ios_bootstrap.model.User;
import de.htwsaar.dfs.ios_bootstrap.services.UserService;


@Path("/users")
public class UserResource {

	private UserService userService = new UserService();
	
	/**
	 * this method returns all users that are actually in the database
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getListOfUsers(){
		return userService.getAllUsers();
	}
	
	
	/**
	 * this method allows to update a picture in the database
	 * @param id
	 * @param User
	 * @return
	 */
	@PUT
	@Path("/{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User updateUser(@PathParam("userId") long id ,
								User user) {
		user.setId(id);
		return userService.updateUser(user);
	}
	
	
	/**
	 * this method returns a special user stored in the database
	 * @param id
	 * @return
	 */
	@GET
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser( @PathParam("userId") long id) {
		return userService.getUser(id);
	}

}
