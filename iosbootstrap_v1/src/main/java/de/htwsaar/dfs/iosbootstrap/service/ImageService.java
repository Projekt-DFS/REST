package de.htwsaar.dfs.iosbootstrap.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import de.htwsaar.dfs.iosbootstrap.Database;
import de.htwsaar.dfs.iosbootstrap.can_network.Bootstrap;
import de.htwsaar.dfs.iosbootstrap.can_network.ImageContainer;
import de.htwsaar.dfs.iosbootstrap.model.Image;
import de.htwsaar.dfs.iosbootstrap.model.Metadata;
import de.htwsaar.dfs.iosbootstrap.model.User;


public class ImageService {

	private Bootstrap bootstrap  = new Bootstrap();
	//dummy
	public static Map<Long, Image> images = Database.getImages();
	public static Map<Long, User> users = Database.getUsers();
	
	public ImageService(){
//		bootstrap.createImage(img, bootstrap.hashToPoint("name", "Nana"), "AN",
//				user, new Date(), null);
	}
	
	
	/**
	 * This Method return a copy of all the images 
	 * that are actually in the Map
	 * @return
	 */
	
	public List<Image> getAllImages(int userId){
		List<Image> list = new ArrayList<>();
//		List<ImageContainer> list2 = bootstrap.getAllImages(userId);
//		for( ImageContainer ic : list2) {
//			Image img = new Image();
//			img.setId(ic.getId());
//			img.setOwnerId(ic.getUser().getID());
//			img.setMetadata(new Metadata(ic.getUser().getName(), ic.getPhotographer(), ic.getTagList()));
//			img.setThumbnail(encodeToString(ic.getThumbnail(), "png"));
//			img.setImageSource(encodeToString(ic.getImage(), "png"));
//			list.add(img);
//		}
//		return list;
		list.addAll(images.values());
		List<Image> list2 = new ArrayList<>();
		for(Image i : list) {
			if(i.getOwnerId()==userId)
				list2.add(i);
		}
//		Stream<Image> stream = list.stream().filter((x)->(x.getOwnerId() == userId));
//		list = Arrays.asList((Image[])stream.toArray());
		return list2; 
	}

	
	/**
	 * This method returns a special image
	 * @param userId
	 * @param imageId
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	public Image getImage(int userId , long imageId) throws FileNotFoundException, ClassNotFoundException, IOException {
//		ImageContainer ic =  bootstrap.getImage(user, bootstrap.hashToPoint("name", "Nana"));
//				//bootstrap.getImage(userId, imageId);
//		Image img = new Image();
//		img.setId(ic.getId());
//		img.setOwnerId(ic.getUser().getID());
//		img.setMetadata(new Metadata(ic.getUser().getName(), ic.getPhotographer(), ic.getTagList()));
//		img.setThumbnail(encodeToString(ic.getThumbnail(), "png"));
//		img.setImageSource(encodeToString(ic.getImage(), "png"));
//		return img;
		List<Image> list = new ArrayList<>();
		list.addAll(images.values());
		List<Image> list2 = new ArrayList<>();
		for(Image i : list) {
			if(i.getOwnerId()==userId && i.getId()==imageId)
				list2.add(i);
		}
		return images.get(imageId);
	}
	
	
	public Image addImage(int userId, Image image) {
//		BufferedImage img = decodeToImage(image.getImageSource());
//		LinkedList<String> tagList = convertTagsToList(image.getMetadata().getTagList());
//		bootstrap.createImageCalculatingCoordinate(img, image.getMetadata().getPhotographer(),
//			bootstrap.getUser(userId), new Date(), tagList);
		long  i= 1;
		while(images.get(i) != null || i < images.size()) {
			i++;
		}
		image.setId(i);
		image.setOwnerId(userId);
		images.put(image.getId(), image);
		return image;
	}
	
	public Image updateImage(int userId, Image image) {
		if ( image.getId() <= 0 ) {
			return null;
		}
	
		return image;
	}
	
	public void removeImage(int userId, long imageId) {
		 images.remove(imageId);
	}
	
	
	
	private LinkedList<String> convertTagsToList(String tags){
		String[] str = tags.split("#");
		LinkedList<String> list = new LinkedList<>();
		for ( String s : str)
			list.add(s);
		return list;
	}

	public Metadata getMetadata(int userId, long imageId) {
		//ImageContainer ic = bootstrap.getImage(userId, imageId);
		//Metadata metadata = new Metadata(ic.getUser().getName(), ic.getPhotographer(), ic.getTagList());
		Metadata metadata = images.get(imageId).getMetadata();
		return metadata;
	}

	public Metadata updateMetadata(int userId, long imageId , Metadata metadata) {
//		ImageContainer ic = bootstrap.getImage(userId, imageId);
//		ic.setPhotographer(metadata.getPhotographer());
		//Metadata metadata = new Metadata(ic.getUser().getName(), ic.getPhotographer(), ic.getTagList());
		metadata.setCreated(images.get(imageId).getMetadata().getCreated());
		//metadata.setOwner(users.get(userId).getName());
		images.get(imageId).setMetadata(metadata);
		return metadata;
	}


	/**
	 * this metho returns the Picture als BufferedImage
	 * @param userId
	 * @param imageId
	 * @return
	 */
	public BufferedImage getBufferedImage(int userId, long imageId) {
		BufferedImage img = null;
		try {
			//img = bootstrap.getImage(userId, imageId).getImage();
				img = ImageIO.read(new File("C:/Users/Aude/Documents/Studium/Projektarbeit/Bilder/fernbedin.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}	
			return img;
	}


	
}
