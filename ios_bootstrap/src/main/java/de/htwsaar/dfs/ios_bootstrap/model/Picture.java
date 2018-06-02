package de.htwsaar.dfs.ios_bootstrap.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Picture {
	
	private long pictureId; 
	private String message;
	private String older ; 
	private Date created;
	
	
	public Picture(){}
	
	public Picture(long id ,String message , String older ){
		this.message = message;
		this.pictureId = id;
		this.created = new Date();
		this.older = older;
	}
	
	public long getId() {
		return pictureId;
	}
	
	public void setId(long id) {
		pictureId = id;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String toString() {
		return this.getMessage();
	}
	
	public void setCreated(Date date) {
		created = date ;
	}
	
	public Date getCreated() {
		return created;
	}
}
