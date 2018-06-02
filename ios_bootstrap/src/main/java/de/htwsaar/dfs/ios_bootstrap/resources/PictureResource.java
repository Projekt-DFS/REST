package de.htwsaar.dfs.ios_bootstrap.resources;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import de.htwsaar.dfs.ios_bootstrap.services.PictureService;
import de.htwsaar.dfs.ios_bootstrap.model.*;

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
import javax.ws.rs.WebApplicationException;

@Path("/pictures")
public class PictureResource {

	PictureService pictureService = new PictureService();
	
	public static final String UPLOAD_FILE_SERVER = "C:/Users/Aude/Desktop/";
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Picture> getListOfPictures(){
		return pictureService.getAllPictures();
	}
	
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	public Picture addPicture(Picture picture) {
		pictureService.addPicture(picture);
		return picture;
		
	}
	
	@PUT
	@Path("/{pictureId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Picture updatePicture(@PathParam("pictureId") long id ,
								Picture picture) {
		picture.setId(id);
		return pictureService.updatePicture(picture);
	}
	
	@DELETE
	@Path("/{pictureId}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deletePicture(@PathParam("pictureId") long id) {
		 pictureService.removePicture(id);
	}
	
	@GET
	@Path("/{pictureId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Picture getPicture( @PathParam("pictureId") long id) {
		return pictureService.getPicture(id);
	}
	 
    // http://localhost:8080/ios_bootstrap/webapi/pictures/download
   /**
    * Diese Methode laedt das Bild  C:/Users/Aude/Desktop/dienstleist_web.jpg herunter
    * @return
    */
	@GET
    @Path("/download")
    @Produces({"image/png", "image/jpg", "image/gif"})
    public Response downloadImageFile() {
 
        // set file (and path) to be download
        File file = new File("C:/Users/Aude/Desktop/dienstleist_web.jpg");
 
        ResponseBuilder responseBuilder = Response.ok((Object) file);
        responseBuilder.header("Content-Disposition", "attachment; filename=\"MyPngImageFile.jpg\"");
        return responseBuilder.build();
    }
 
    // http://localhost:8080/ios_bootstrap/pictures/upload
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImageFile(
            @FormDataParam("uploadFile") InputStream fileInputStream,
            @FormDataParam("uploadFile") FormDataContentDisposition fileFormDataContentDisposition) {
 
        // local variables
        String fileName = null;
        String uploadFilePath = null;
 
        try {
            fileName = fileFormDataContentDisposition.getFileName();
            uploadFilePath = writeToFileServer(fileInputStream, fileName);
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
        finally{
            // release resources, if any
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
        String qualifiedUploadFilePath = UPLOAD_FILE_SERVER + fileName;
 
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
            //release resource, if any
            outputStream.close();
        }
        return qualifiedUploadFilePath;
    }
}
