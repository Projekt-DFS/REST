package de.htwsaar.dfs.iosbootstrap.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.htwsaar.dfs.iosbootstrap.database.Database;
import de.htwsaar.dfs.iosbootstrap.model.Image;
import de.htwsaar.dfs.iosbootstrap.model.Metadata;


public class ImageService {

	private Map<Long , Image> images = Database.getImages(); 
	
	public ImageService(){
		
		//dummy
		ArrayList<String> str = new ArrayList<>();
		str.add("Hochzeit");
		images.put(1L, new Image(1,1, "C:/Users/Aude/Documents/Studium/Projektarbeit/Bilder/fernbedin.jpg", 
				new Metadata("Nana", "FH",str),
				"C:/Users/Aude/Documents/Studium/Projektarbeit/Bilder/fernbedin.jpg"));
		images.put(2L, new Image(2,1, "C:/Users/Aude/Documents/Studium/Projektarbeit/Bilder/question.jpg", 
				new Metadata("Aude", "HTW", new ArrayList<String>()), 
				"C:/Users/Aude/Documents/Studium/Projektarbeit/Bilder/question.jpg"));
		
	}
	
	/**
	 * This Method return a copy of all the images 
	 * that are actually in the Map
	 * @return
	 */
	public List<Image> getAllImages(){
		return new ArrayList<Image>(images.values());
		
	}
	
	public Image getImage( long imageId) {
		return images.get(imageId);
	}
	
	public Image addImage(Image image) {
		image.setId(images.size() + 1);
		images.put(image.getId(), image);
		return image;
	}
	
	public Image updateImage(Image image) {
		if ( image.getId() <= 0 ) {
			return null;
		}
		images.put(image.getId(), image);
		return image;
	}
	
	public void removeImage(long imageId) {
		 images.remove(imageId);
	}
}
