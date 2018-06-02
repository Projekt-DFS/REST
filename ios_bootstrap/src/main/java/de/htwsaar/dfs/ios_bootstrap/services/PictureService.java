package de.htwsaar.dfs.ios_bootstrap.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.htwsaar.dfs.ios_bootstrap.database.Database;
import de.htwsaar.dfs.ios_bootstrap.model.*;
public class PictureService {

	private Map<Long , Picture> pictures = Database.getPictures(); 
	
	public PictureService(){
		//dummy
		pictures.put(1L, new Picture(1 , "Picture1" , "Nana"));
		pictures.put(2L, new Picture(2 , "Picture2" , "Nana"));
	}
	
	/**
	 * This Method return a copy of all the pictures 
	 * that are actually in the Map
	 * @return
	 */
	public List<Picture> getAllPictures(){
		return new ArrayList<Picture>(pictures.values());
		
	}
	
	public Picture getPicture( long pictureId) {
		return pictures.get(pictureId);
	}
	
	public Picture addPicture(Picture picture) {
		picture.setId(pictures.size() + 1);
		pictures.put(picture.getId(), picture);
		return picture;
	}
	
	public Picture updatePicture(Picture picture) {
		if ( picture.getId() <= 0 ) {
			return null;
		}
		pictures.put(picture.getId(), picture);
		return picture;
	}
	
	public void removePicture(long pictureId) {
		 pictures.remove(pictureId);
	}
}
