package de.htwsaar.dfs.iosbootstrap.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.htwsaar.dfs.iosbootstrap.database.Database;
import de.htwsaar.dfs.iosbootstrap.model.Image;
import de.htwsaar.dfs.iosbootstrap.model.Metadata;

public class MetadataService {

	private Map<Long , Image> images = Database.getImages(); 
	
	public MetadataService(){}
	
	public Metadata getMetadata( long imageId ) {
		Metadata metadata = images.get(imageId).getMetadata();
		return metadata;
	}
	
	public Metadata addMetadata(long imageId, Metadata metadata) {
		images.get(imageId).setMetadata(metadata);
		return metadata;
	}
	
	public Metadata updateMetadata(long imageId, Metadata metadata) {
		if ( images.get(imageId).getMetadata() == null ) {
			return null;
		}else {
			images.get(imageId).setMetadata(metadata);
			return metadata;
		}
	}
	
	public void removeMetadata(long imageId) {
		//Image image = images.get(imageId);
		images.get(imageId).setMetadata(new Metadata());

	}

}
