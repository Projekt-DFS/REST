package de.htwsaar.dfs.iosbootstrap.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class Image {

	private long id;
	private long ownerId;
	private Metadata metadata;
	private String imageSource;
	private String thumbnailSource;
		
	public Image() {}
	
	public Image(long id, long ownerId, String imageSource ,Metadata metadata, String thmbnailSource) {
		this.id = id;
		this.ownerId = ownerId;
		this.metadata = metadata;
		this.imageSource = imageSource;
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

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	@XmlTransient
	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public String getImageSource() {
		return imageSource;
	}

	public void setImageSource(String imageSrc) {
		this.imageSource = imageSrc;
	}
	
	
}
