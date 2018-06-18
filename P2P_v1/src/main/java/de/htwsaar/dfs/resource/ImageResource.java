package de.htwsaar.dfs.resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.htwsaar.dfs.model.Image;
import de.htwsaar.dfs.model.Metadata;
import de.htwsaar.dfs.service.ImageService;

@Path("/")
public class ImageResource {

	private ImageService imageService = new ImageService();
	
	/**
	 * this method returns all images that are actually in the database
	 * @return
	 * */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Image> getListOfImages(@PathParam("userId") int id){
		System.out.println("test");
		return imageService.getAllImages(id);
	}
	
	/**
	 * this method allows to add a picture 
	 * @param image
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON )
	public Image addImage(@PathParam("userId") int userId, Image image) {
		imageService.addImage(userId, image);
		return image;
		
	}

	/**
	 * this method returns a special picture 
	 * @param id
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	@GET
	@Path("/{imageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Image getImage( @PathParam("userId") int userId, @PathParam("imageId") long id) 
			throws FileNotFoundException, ClassNotFoundException, IOException {
		return imageService.getImage(userId, id);
	}
	
	/**
	 * this method allows to update a picture in the database
	 * @param id
	 * @param image
	 * @return
	 */
	@PUT
	@Path("/{imageId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON )
	public Image updateImage(@PathParam("userId") int userId ,
							@PathParam("imageId") long imageId,
								Image image) {
		image.setId(imageId);
		return imageService.updateImage(userId, image);
	}
	
	/**
	 * this method deletes a picture in the database
	 * @param id
	 */
	@DELETE
	@Path("/{imageId}")
	@Produces({MediaType.APPLICATION_JSON })
	public void deleteImage(@PathParam("userId") int userId ,
			@PathParam("imageId") long imageId) {
		 imageService.removeImage(userId, imageId);
	}
	
	
	/**
	 * This method returns the metadata of a picture
	 * @return
	 */ 
	@GET
	@Path("/{imageId}/metadata")
	@Produces(MediaType.APPLICATION_JSON)
	public Metadata getMetadata(@PathParam("userId") int userId ,
			@PathParam("imageId") long imageId ) {
		return imageService.getMetadata(userId, imageId);
	}
	
	
	@PUT
	@Path("/{imageId}/metadata")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON })
	public Metadata updateMetadata(@PathParam("userId") int userId ,
			@PathParam("imageId") long imageId , Metadata metadata ) {
		return imageService.updateMetadata(userId, imageId, metadata);
	}
	
	
}
