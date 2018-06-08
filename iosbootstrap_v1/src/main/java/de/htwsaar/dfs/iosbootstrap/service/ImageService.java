package de.htwsaar.dfs.iosbootstrap.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import de.htwsaar.dfs.iosbootstrap.can_network.Bootstrap;
import de.htwsaar.dfs.iosbootstrap.can_network.ImageContainer;
import de.htwsaar.dfs.iosbootstrap.model.Image;
import de.htwsaar.dfs.iosbootstrap.model.Metadata;
import de.htwsaar.dfs.iosbootstrap.model.User;


public class ImageService {

	private Bootstrap bootstrap  = new Bootstrap();
	//dummy
	private static Map<Long, Image> images = new HashMap<>();

	
	public ImageService(){
		//dummy 
		BufferedImage img = null, img2 = null;
		try {
			img = ImageIO.read(new File("C:/Users/Aude/Documents/Studium/Projektarbeit/Bilder/fernbedin.jpg"));
			img2= ImageIO.read(new File("C:/Users/Aude/Documents/Studium/Projektarbeit/Bilder/question.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image i = new Image(1, 1, encodeToString(img, "png"), 
				new Metadata("nana", "nana", null), encodeToString(img, "png"));
		Image i2 = new Image(2, 2, encodeToString(img2, "png"), 
				new Metadata("nana", "nana", null), encodeToString(img2, "png"));
		images.put(1L, i);
		images.put(2L,i2);
	}
	
	
	/**
	 * This Method return a copy of all the images 
	 * that are actually in the Map
	 * @return
	 */
	public List<Image> getAllImagesTest(int userId){
		return new ArrayList<>(images.values()); 
	}
	
	public List<Image> getAllImages(int userId){
		List<Image> list = new ArrayList<>();
		List<ImageContainer> list2 = bootstrap.getAllImages(userId);
		for( ImageContainer ic : list2) {
			Image img = new Image();
			img.setId(ic.getId());
			img.setOwnerId(ic.getUser().getID());
		//	img.setMetadata(new Metadata(ic.getUser().getName(), ic.getPhotographer(), ic.getTagList()));
			img.setThumbnail(encodeToString(ic.getThumbnail(), "png"));
			img.setImageSource(encodeToString(ic.getImage(), "png"));
			list.add(img);
		}
		return list; 
	}

	
	/**
	 * This method returns a special image
	 * @param userId
	 * @param imageId
	 * @return
	 */
	public BufferedImage getImage(int userId , long imageId) {
		ImageContainer ic = bootstrap.getImage(userId, imageId);
		Image img = new Image();
		img.setId(ic.getId());
		img.setOwnerId(ic.getUser().getID());
	//	img.setMetadata(new Metadata(ic.getUser().getName(), ic.getPhotographer(), ic.getTagList()));
		img.setThumbnail(encodeToString(ic.getThumbnail(), "png"));
		img.setImageSource(encodeToString(ic.getImage(), "png"));
		return null;
	}
	
	public Image addImage(int userId, Image image) {
		//bootstrap.createImage(ic);
		return image;
	}
	
	public Image updateImage(int userId, Image image) {
		if ( image.getId() <= 0 ) {
			return null;
		}
	
		return image;
	}
	
	public void removeImage(int userId, long imageId) {
		 //images.remove(imageId);
	}
	
	/**
	 * This method convert a BufferedImage to a Base64 String
	 * @param image
	 * @param type specify which format the Image has example: png , jpg , gif
	 * @return
	 */
	public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
 
        try {
        	
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();
 
            imageString = Base64.getEncoder().encodeToString(imageBytes);
 
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }
}
