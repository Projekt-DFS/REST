package de.htwsaar.dfs.iosbootstrap.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.htwsaar.dfs.iosbootstrap.model.Metadata;
import de.htwsaar.dfs.iosbootstrap.services.MetadataService;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MetadataResource {
	
	private MetadataService metadataService = new MetadataService();
	
	@GET
	public Metadata getMetadata(@PathParam("imageId") long id){
		return metadataService.getMetadata(id);
	}
	
	@POST
	public Metadata addMetadata(@PathParam("imageId") long id , Metadata metadata) {
		return metadataService.addMetadata(id, metadata);
	}
	
	@PUT
	public Metadata updateMetadata(@PathParam("imageId") long id, Metadata metadata) {
		return metadataService.updateMetadata(id, metadata);
	}
	
	@DELETE
	public void deleteMetadata(@PathParam("imageId") long id) {
		metadataService.removeMetadata(id);
	}
	
/*	@GET
	@Path("/{metadataId}")
	public Metadata getMetadata( @PathParam("imageId") long id,
								@PathParam("metadataId") long mId) {
		return metadataService.getMetadata(id, mId);
	}
	*/
	
}
