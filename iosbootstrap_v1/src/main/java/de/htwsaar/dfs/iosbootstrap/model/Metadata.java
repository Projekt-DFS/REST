package de.htwsaar.dfs.iosbootstrap.model;

import java.util.Date;
import java.util.LinkedList;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Metadata class 
 * This class describe metadata of a Picture
 * @author Aude Nana
 *
 */
@XmlRootElement
public class Metadata {

	
	private String owner;
	private Date created;
	private String location;
	private LinkedList<String> tagList;
	
	public Metadata() {}
	
	public Metadata(String owner, Date created,String photographer,LinkedList<String> tagList) {
		
		this.owner = owner;
		this.created = created;
		this.location = photographer;
		this.tagList = tagList;
	}

	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getPhotographer() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public LinkedList<String> getTagList() {
		return tagList;
	}
	public void setTagList(LinkedList<String> tagList) {
		this.tagList = tagList;
	}
	
	
}
