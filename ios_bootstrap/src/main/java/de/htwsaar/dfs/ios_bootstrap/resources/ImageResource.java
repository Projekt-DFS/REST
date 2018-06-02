package de.htwsaar.dfs.ios_bootstrap.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import de.htwsaar.dfs.ios_bootstrap.model.Image;
import de.htwsaar.dfs.ios_bootstrap.services.ImageService;

@Path("/images")
public class ImageResource {

	private ImageService imageService = new ImageService();
	
	//where the picture will be upload to
	public static final String UPLOAD_FILE_LOCATION = "C:/Users/Aude/Desktop/uploadTest/";
	
	/**
	 * this method returns all images that are actually in the database
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Image> getListOfImages(){
		return imageService.getAllImages();
	}
	
	/**
	 * this method allows to add a picture in the database
	 * @param image
	 * @return
	 */
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	public Image addImage(Image image) {
		imageService.addImage(image);
		return image;
		
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
	@Produces(MediaType.APPLICATION_JSON)
	public Image updateImage(@PathParam("imageId") long id ,
								Image image) {
		image.setId(id);
		return imageService.updateImage(image);
	}
	
	/**
	 * this method deletes a picture in the database
	 * @param id
	 */
	@DELETE
	@Path("/{imageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteImage(@PathParam("imageId") long id) {
		 imageService.removeImage(id);
	}
	
	/**
	 * this method returns a special picture stored in the database
	 * @param id
	 * @return
	 */
	@GET
	@Path("/{imageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Image getImage( @PathParam("imageId") long id) {
		return imageService.getImage(id);
	}
	
	/**
	 * This method returns the metadatas of a picture
	 * @return
	 */
	@Path("/{imageId}/metadatas")
	public MetadataResource getMetadataResource( ) {
		return new MetadataResource();
	}
 
	/**
	 * This Method allows a client to download a Picture on this address
	 * http://localhost:8080/ios_bootstrap/rest/images/{imageId}/download
	 * @param imageId
	 * @return
	 */
	@GET
    @Path("/{imageId}/download")
    @Produces({"image/png", "image/jpg", "image/gif"})
    public Response downloadImage(@PathParam("imageId")long imageId) {
 
        // set file (and path) to be download
		String fileSource = imageService.getImage(imageId).getImageSource();
        File file = new File(fileSource);
 
        ResponseBuilder responseBuilder = Response.ok((Object) file);
        responseBuilder.header("Content-Disposition", "attachment; filename=\"myDownload.jpg\"");
        return responseBuilder.build();
    }
	
	/**
	 * This method allows a picture to be uploaded at this address  
	 * http://localhost:8080/ios_bootstrap/rest/images/upload
	 * @param fileInputStream
	 * @param fileFormDataContentDisposition
	 * @return
	 */
	@POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImage(
            @FormDataParam("uploadFile") InputStream fileInputStream,
            @FormDataParam("uploadFile") FormDataContentDisposition fileFormDataContentDisposition) {
 
        // local variables
        String fileName = null;
        String uploadFilePath = null;
 
        try {
        	//setting parameters
            fileName = fileFormDataContentDisposition.getFileName();
            uploadFilePath = writeToFileServer(fileInputStream, fileName);
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
        finally{
            // release resources
        	Image image = new Image();
        	image.setImageSource(uploadFilePath);
        	imageService.addImage(image);
        }
     
        return Response.ok("File uploaded successfully at " + uploadFilePath).build();
    }
    
 
    /**
     * write input stream to file server
     * @param inputStream
     * @param fileName
     * @throws IOException
     */
    private String writeToFileServer(InputStream inputStream, String fileName) throws IOException {
 
        OutputStream outputStream = null;
        String qualifiedUploadFilePath = UPLOAD_FILE_LOCATION + fileName;
 
        try {
            outputStream = new FileOutputStream(new File(qualifiedUploadFilePath));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        finally{
            outputStream.close();
        }
        return qualifiedUploadFilePath;
    }
}
