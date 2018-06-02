package de.htwsaar.dfs.iosbootstrap.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.htwsaar.dfs.iosbootstrap.database.Database;
import de.htwsaar.dfs.iosbootstrap.model.User;


public class UserService {

	private Map<Long , User> users = Database.getUsers(); 
	
	public UserService(){
		//dummy
		users.put(1L, new User(1, "anana", "dfs"));
		users.put(2L, new User(2, "anan", "dfs"));
	}
	
	/**
	 * This Method return a copy of all the users 
	 * that are actually in the Map
	 * @return
	 */
	public List<User> getAllUsers(){
		return new ArrayList<User>(users.values());
		
	}
	
	public User getUser( long UserId) {
		return users.get(UserId);
	}
	
	public User updateUser(User User) {
		if ( User.getId() <= 0 ) {
			return null;
		}
		users.put(User.getId(), User);
		return User;
	}
	
}
