package de.htwsaar.dfs;

import java.util.HashMap;
import java.util.Map;

import de.htwsaar.dfs.model.Image;
import de.htwsaar.dfs.model.User;

/**
 * Dummy Database 
 * @author Aude
 *
 */
public class Database {

	private static Map<Long , User> users = new HashMap<>();
	private static Map<Long , Image> images = new HashMap<>();

	
	public static Map <Long , Image> getImages(){
		return images;
	}
	
	public static Map <Long , User> getUsers(){
		return users;
	}
	
}

