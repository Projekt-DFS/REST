package de.htwsaar.dfs.iosbootstrap.model;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Metadata {

	
	private String owner;
	private Date created;
	private String location;
	private ArrayList<String> tagList;
	
	public Metadata() {}
	
	public Metadata(String owner, String location, ArrayList<String> tagList) {
		
		this.owner = owner;
		this.created = new Date();
		this.location = location;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public ArrayList<String> getTagList() {
		return tagList;
	}
	public void setTagList(ArrayList<String> tagList) {
		this.tagList = tagList;
	}
	
	
}
