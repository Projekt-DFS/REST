package de.htwsaar.dfs.ios_bootstrap.database;

import java.util.HashMap;
import java.util.Map;

import de.htwsaar.dfs.ios_bootstrap.model.Image;
import de.htwsaar.dfs.ios_bootstrap.model.Metadata;
import de.htwsaar.dfs.ios_bootstrap.model.Picture;
import de.htwsaar.dfs.ios_bootstrap.model.User;

/**
 * Dummy Database 
 * @author Aude
 *
 */
public class Database {

	private static Map<Long , Picture> pictures = new HashMap<>();
	private static Map<Long , User> users = new HashMap<>();
	private static Map<Long , Image> images = new HashMap<>();
	private static Map<Long , Metadata> metadatas = new HashMap<>();
	
	public static Map <Long , Picture> getPictures(){
		return pictures;
	}
	
	public static Map <Long , User> getUsers(){
		return users;
	}
	public static Map <Long , Image> getImages(){
		return images;
	}
	
	public static Map <Long , Metadata> getMetadata(){
		return metadatas;
	}
}
