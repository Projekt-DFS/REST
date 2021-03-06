package de.htwsaar.dfs.Bootstrap.model;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import de.htwsaar.dfs.Bootstrap.exeptions.EmptyStringException;


/**
 * 
 */

/**
 * @author Thomas Spanier
 *
 */
@XmlRootElement
public class User implements Serializable {

	
	//TODO Serializable???
	private static final long serialVersionUID = -3153801662101748013L;
	//Variables
	private int id;
	private String name;
	private String password;
	//imageList?
	
	public User() {
		
	}
	/**
	 * Constructor
	 * @param id
	 * @param name
	 * @param password
	 */
	public User(int id, String name, String password) {
	//public User(String name, String password) {
		//TODO id sinnvoll?
		this.id=id;
		setName(name);
		setPassword(password);
	}
	
	//get-methods
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPassword() {
		return password;
	}
	
	//set-methods
	public void setID(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		if (name.trim().isEmpty()) {
			throw new EmptyStringException();
		}
		this.name=name;
		
	}
	
	public void setPassword(String password) {
		if (password.trim().isEmpty()) {
			throw new EmptyStringException();
		}
		this.password=password;
	}

	/**
	 * ToString method
	 * @return Username and Password
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getName()).append(", ").append(getPassword());
		
		return sb.toString();
	}
	
}
