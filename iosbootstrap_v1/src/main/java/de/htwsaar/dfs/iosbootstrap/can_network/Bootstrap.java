package de.htwsaar.dfs.iosbootstrap.can_network;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import javax.imageio.ImageIO;
import javax.xml.bind.annotation.XmlRootElement;


import de.htwsaar.dfs.iosbootstrap.model.Image;
import de.htwsaar.dfs.iosbootstrap.model.User;
import de.htwsaar.dfs.iosbootstrap.utils.StaticFunctions;

@XmlRootElement
public class Bootstrap extends Peer {

	//Variables
	private LinkedList<User> userList;
	//TODO ID von IP-Adresse ableiten
	//private Zone initialZone;

	/**
	 * Constructor
	 * If a userList is already present, this list will be deserialized and be used
	 * 
	 */
	public Bootstrap() {
		//TODO extend Peer Quatsch rein Save Images
		/*
		 * Eigener Ordner hash-funktion als name
		 * Peer sucht im ordner nach den image-objekten
		 * 
		 * BT - Create Image	--> Eher REST Problem?
		 * Peer - Save Image
		 * Peer - Load Image
		 * 
		 * 
		 */
		
		//Create or load UserList
		userList = new LinkedList<User>();
		try {
			importUserList();
		} catch (FileNotFoundException e){
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		//Create a new Zone
		createZone(new Point2D.Double(0.0, 0.0), new Point2D.Double(1.0, 1.0));
		id = 0;					//Bootstrap has always ID 0 TODO: Deprecated
	}

	//getters and setters für userList
	public LinkedList<User> getUserList() {
		return userList;
	}


	public void setUserList(LinkedList<User> userList) {
		this.userList = userList;
	}

	
	public User getUser(int userId) {
		return userList.get(userId);
	}

	/**
	 * Creates a new User
	 * @param id identifier
	 * @param name of the new User
	 * @param password of the new User
	 * @return success or fail message
	 */
	public String createUser(String name, String password) {
		User newUser;
		newUser = new User(userList.size()+1, name, password);
		for(User user : userList) {
			if(user.getName().equals(name)) {
				return ("User already exists");
			}
		}
		userList.add(newUser);
		return ("User has been added");
	}
	
	/**
	 * Deletes the User
	 * @param name of the deleting User
	 */
	public void deleteUser(String name) {
		// TODO Auto-generated method stub
		int i = 0;
		for(User user : userList) {
			if (user.getName().equals(name)) {
				userList.remove(i);
			}
			i++;
		}
	}

	public void deleteUser(long userId) {
		// TODO Auto-generated method stub
		int i = 0;
		for(User user : userList) {
			if (user.getID() == userId) {
				userList.remove(i);
			}
			i++;
		}
	}

	/**
	 * Check, if Username and Password are correct
	 * @param name
	 * @param password
	 * @return true, if User & Password are correct, otherwise false
	 */
	public boolean authenticateUser(String name, String password) {
		for(User user : userList) {

			if(user.getName().equals(name) && user.getPassword().equals(password)) {
				return true;
			} 
		}
		return false;
	}

	/**
	 * 
	 * @return a List with all Users
	 */
	public String getAllUsers() {
		StringBuffer sb = new StringBuffer();
		for (User user : userList) {
			sb.append(user.toString()).append(" | ");
		}
		return sb.toString();
	}

	/**
	 * 
	 * @return how many Users are registered
	 */
	public int getUserCount() {
		int count = 0;
		for(@SuppressWarnings("unused") User user : userList) {
			count++;
		}
		return count;
	}

	/**
	 * Delete all Users
	 */
	public void dumpUsers() {
		userList.removeAll(userList);
	}


	/**
	 * Serialize the UserList in "userList.dat"
	 * @throws IOException
	 */
	public void exportUserList() throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(
				new BufferedOutputStream(
						new FileOutputStream("userList.dat")));
		out.writeObject(userList);
		out.close();
	}


	/**
	 * Deserialize the UserList from "userList.dat"
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException if userList.dat does not exist
	 */
	@SuppressWarnings("unchecked")
	public void importUserList() throws IOException, ClassNotFoundException, FileNotFoundException {
		ObjectInputStream in;
		userList = new LinkedList<User>();
		in= new ObjectInputStream(
				new BufferedInputStream(
						new FileInputStream("userList.dat")));
		userList= (LinkedList<User>)in.readObject();
		in.close();

	}



	//TODO implement hashFunction
	public void createImageCalculatingCoordinate(BufferedImage img, String photographer, 
			User user, Date date, LinkedList<String> tagList) {

	}


	//Image functions
	/**
	 * Creates an ImageContainer and sends it into the network
	 * @param ic the ImageContainer to be saved
	 */
	public void createImage(ImageContainer ic) {
		try {
			saveImage(ic);						//TODO: temporary
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates an ImageContainer and sends it into the network
	 * @param img the image to be saved
	 * @param canCoordinate the calculated coordinate in the network
	 * @param photographer the image's photographer 
	 * @param user the user who uploaded the image
	 * @param date the date when the image was shot
	 * @param tagList a list of tags
	 */
	public void createImage(BufferedImage img, Point2D.Double canCoordinate,
			String photographer, User user, Date date, LinkedList<String> tagList) {
		//Koordinate jetzt erst berechnen?
		ImageContainer ic = new ImageContainer(img, canCoordinate, photographer, user, date, tagList);
		//TODO Weiterleiten an die peers
		try {
			saveImage(ic);						//TODO: temporary (routing)
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}							

	}

	/**
	 * Edits the image's meta data
	 */
	public void editMeta() {
		//TODO implement
	}

	/**
	 * Saves an ImageContainer including the image and the thumbnail on the hdd
	 * @param ic the imageContainer to be saved
	 */
	public void saveImage(ImageContainer ic) throws IOException {
		//Get location
		StringBuffer fileName = new StringBuffer();
		fileName.append("Images//").append(ic.getUser().getName()).append("_")
				.append(StaticFunctions.pointToString(ic.getCoordinate()));
		
		//Save imageContainer
		ObjectOutputStream out = new ObjectOutputStream(
				new BufferedOutputStream(
						new FileOutputStream(fileName.toString() + ".data")));
		out.writeObject(ic);
		out.close();
		
		//Save image
		File outputFile = new File(fileName.toString() + ".jpg");
		ImageIO.write(ic.getImage(), "jpg", outputFile);
		
		//Save thumbnail
		outputFile = new File(fileName.toString() + "_thumbnail.jpg");
		ImageIO.write(ic.getThumbnail(), "jpg", outputFile);
	}

	/**
	 * Deserialize imageContainer  
	 * @param canCoordinate
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public ImageContainer getImage(User user, Point2D.Double canCoordinate) throws FileNotFoundException, IOException, ClassNotFoundException {
		//Get location
		StringBuffer fileName = new StringBuffer();
		fileName.append("Images//").append(user.getName()).append("_")
				.append(StaticFunctions.pointToString(canCoordinate));
		
		//Load image
		File inputFile = new File(fileName.toString() + ".jpg");
		BufferedImage img = ImageIO.read(inputFile);
		
		/* Thumbnails will be genereated in IC-Class
		//Load thumbnail
		inputFile = new File(fileName.toString() + "_thumbnail.jpg");
		BufferedImage thumbnail = ImageIO.read(inputFile);
		*/
		
		//Load imageContainer and set image and thumbnail 
		ImageContainer ic;
		ObjectInputStream in= new ObjectInputStream(
				new BufferedInputStream(
						new FileInputStream(fileName.toString() + ".data")));
		ic= (ImageContainer)in.readObject();
		ic.setImage(img);
		//ic.createThumbnail(thumbnail);
		in.close();
		return ic;
	}
	
	public Point2D.Double hashToPoint(String imageName, String userName) {
		final double multiplier = 1.0 / 2147483648.0;
		
		double x = imageName.hashCode() * multiplier;
		double y = userName.hashCode() * multiplier;
		
		Point2D.Double coordinatePoint = new Point2D.Double(x, y);
		
		return coordinatePoint;
	}

	public List<ImageContainer> getAllImages(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public ImageContainer getImage(int userId, long imageId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 *  The sends coordinates Hashmap of the Bootstrap
	 * @return coordinates
	 */
	public HashMap<Long, Zone> getRoutingTbl () {
		return coordinates;
		
	}
}