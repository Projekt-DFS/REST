package de.htwsaar.dfs.ios_bootstrap.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.htwsaar.dfs.ios_bootstrap.database.Database;
import de.htwsaar.dfs.ios_bootstrap.model.Image;
import de.htwsaar.dfs.ios_bootstrap.model.Metadata;

public class MetadataService {

	private Map<Long , Image> images = Database.getImages(); 
	
	public MetadataService(){}
	
	/*public List<Metadata> getAllMetadatas(long imageId){
		Map <Long, Metadata> metadatas = images.get(imageId).getMetadatas();
		return new ArrayList<Metadata>(metadatas.values());
		
	}*/
	
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
		}
		images.get(imageId).setMetadata(metadata);
		return metadata;
	}
	
	public void removeMetadata(long imageId) {
		images.get(imageId).setMetadata(null);

	}

}
