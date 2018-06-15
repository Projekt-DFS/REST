package de.htwsaar.dfs.Bootstrap;

import java.util.HashMap;
import java.util.Map;

import de.htwsaar.dfs.Bootstrap.can.Peer;
import de.htwsaar.dfs.Bootstrap.model.Image;
import de.htwsaar.dfs.Bootstrap.model.User;


/**
 * Dummy Database 
 * @author Aude
 *
 */
public class Database {

	private static Map<Integer , User> users = new HashMap<>();
	private static Map<Long , Image> images = new HashMap<>();
	private static Map<Long , Peer> peers = new HashMap<>();
	
	public static Map <Long , Image> getImages(){
		return images;
	}
	
	public static Map <Integer , User> getUsers(){
		return users;
	}
	
	public static Map <Long , Peer> getPeers(){
		return peers;
	}
}