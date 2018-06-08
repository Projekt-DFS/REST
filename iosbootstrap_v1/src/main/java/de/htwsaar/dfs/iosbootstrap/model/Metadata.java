package de.htwsaar.dfs.iosbootstrap.model;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Metadata {

	
	private String owner;
	private Date created;
	private String photographer;
	private String tagList;
	
	public Metadata() {}
	
	public Metadata(String owner, String photographer,String tagList) {
		
		this.owner = owner;
		this.created = new Date();
		this.photographer = photographer;
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
		return photographer;
	}
	public void setLocation(String location) {
		this.photographer = location;
	}
	public String getTagList() {
		return tagList;
	}
	public void setTagList(String tagList) {
		this.tagList = tagList;
	}
	
	
}
