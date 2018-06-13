package de.htwsaar.dfs.iosbootstrap.model;


import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

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
	private Metadata metadata;
	private String imageSource;
	private String thumbnailSource;
		
	public Image() {}

	//constructor with values
	public Image(long id, int ownerId, String imageName, Metadata metadata, String imageSource,
			String thumbnailSource) {
		super();
		this.id = id;
		this.ownerId = ownerId;
		this.imageName = imageName;
		this.metadata = metadata;
		this.imageSource = imageSource;
		this.thumbnailSource = thumbnailSource;
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

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public String getImageSource() {
		return imageSource;
	}

	public void setImageSource(String imageSource) {
		this.imageSource = imageSource;
	}

	public String getThumbnailSource() {
		return thumbnailSource;
	}

	public void setThumbnailSource(String thumbnailSource) {
		this.thumbnailSource = thumbnailSource;
	}
	
	
	

	
}
