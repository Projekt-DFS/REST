package de.htwsaar.dfs.iosbootstrap.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.htwsaar.dfs.iosbootstrap.model.User;
import de.htwsaar.dfs.iosbootstrap.service.UserService;

/**
 * This class give access to users Resource
 * @author Aude Nana
 *
 */
@Path("users")
public class UserResource {

	private UserService userService = new UserService();
	
	/**
	 * this method returns all users 
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getListOfUsers(){
		return userService.getAllUsers();
	}
	
	/**
	 * this method returns a special user 
	 * @param id
	 * @return
	 */
	@GET
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser( @PathParam("userId") int id) {
		return userService.getUser(id - 1);
	}
	
	
	/**
	 * this method allows to update user's information
	 * @param id
	 * @param User
	 * @return
	 */
	@PUT
	@Path("/{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User updateUser(@PathParam("userId") int id ,
								User user) {
		user.setID(id);
		return userService.updateUser(user);
	}
	
	/**
	 * This method returns all the pictures of a special user
	 * @return
	 */
	@Path("/{userId}/images")
	public ImageResource getImageResource( ) {
		return new ImageResource();
	}
}
