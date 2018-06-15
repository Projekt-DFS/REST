package de.htwsaar.dfs.model;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


import de.htwsaar.dfs.utils.RestUtils;
import de.htwsaar.dfs.utils.StaticFunctions;

/**
 * This class is used to Parse ImageContainer zu Json with string
 * @author Aude Nana
 *
 */
@XmlRootElement
public class Image {

	private long id;
	private int ownerId;
	private String imageName;
	private Metadata metaData;
	private String imageSource;
	private String thumbnail;
		
	//public Image() {}

	//constructor with values
	public Image(long id, int ownerId, String imageName, 
			Metadata metadata, String imageSource, String thumbnailSource) {
		super();
		this.id = id;
		this.ownerId = ownerId;
		this.imageName = imageName;
		this.metaData = metadata;
		this.imageSource = imageSource;
		this.thumbnail = thumbnailSource;//createThumbnail(imageSource);
	}
	
	public Image() {
		this.id= 0; this.ownerId = 0; this.imageName = ""; this.metaData = new Metadata(); this.imageSource = ""; this.thumbnail = "";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Metadata getMetaData() {
		return metaData;
	}

	public void setMetaData(Metadata metadata) {
		this.metaData = metadata;
	}

	public String getImageSource() {
		return imageSource;
	}

	public void setImageSource(String imageSource) {
		this.imageSource = imageSource;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnailSource) {
		this.thumbnail = thumbnailSource;
	}
	
	//Thumbnails
			/**
			 * creates a Thumbnail and saves it in this object
			 * @param img the original image
			 */
			private String createThumbnail(String imgPath) {
				BufferedImage img = RestUtils.decodeToImage(imgPath);//null;
//				try {
//					img = ImageIO.read(new File(imgPath));
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
				java.awt.Image temp = img.getScaledInstance(img.getWidth() / 10, img.getHeight() / 10, BufferedImage.SCALE_DEFAULT);
				String t= RestUtils.encodeToString(StaticFunctions.toBufferedImage(temp), "png");
			return t;
			}
	
	

	
}
