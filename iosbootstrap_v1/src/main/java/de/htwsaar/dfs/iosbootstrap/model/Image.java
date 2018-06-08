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
	//private Metadata metadata;
	private String imageSource;
	private String thumbnailSource;
		
	public Image() {}
	
	//constructor with values
	public Image(long id, int ownerId, String img ,Metadata metadata, String thmbnailSource) {
		this.id = id;
		this.ownerId = ownerId;
		//this.metadata = metadata;
		this.imageSource = img;
		this.thumbnailSource = thmbnailSource;
	}


	public String getThumbnail() {
		return thumbnailSource;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnailSource = thumbnail;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

//	@XmlTransient
//	public Metadata getMetadata() {
//		return metadata;
//	}
//
//	public void setMetadata(Metadata metadata) {
//		this.metadata = metadata;
//	}

	public String getImageSource() {
		return imageSource;
	}

	public void setImageSource(String imageSrc) {
		this.imageSource = imageSrc;
	}
	
	
}
