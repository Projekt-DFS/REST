package de.htwsaar.dfs.iosbootstrap.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
	
	private long id ;
	private String username;
	private String userpasswd;
	private long groupId ;
	//private List<Long> imageList;
	
	public User() {
		
	}
	
	
	public User( long id ,String username, String userpasswd) {
		this.id = id;
		this.username = username;
		this.userpasswd = userpasswd;
		//imageList = new ArrayList<Long>(); {};
	}

	public long getId() {
		return id;
	}

	
	/*public List<Long> getImageList() {
		return imageList;
	}

	public void setImageList(List<Long> imageList) {
		this.imageList = imageList;
	}*/

	public void setId(long userId) {
		this.id = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpasswd() {
		return userpasswd;
	}

	public void setUserpasswd(String userpasswd) {
		this.userpasswd = userpasswd;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	
}
