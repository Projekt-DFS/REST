package de.htwsaar.dfs;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import de.htwsaar.dfs.model.Image;
import de.htwsaar.dfs.model.Metadata;
import de.htwsaar.dfs.model.User;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;




/**
 * Main Class
 * Starts the Server
 * @author Aude Nana
 *
 */
public class Main {
	


    // Base URI the Grizzly HTTP server will listen on
   //"http://localhost:8080/iosbootstrap/v1/";
    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    
    public static Map<Long, Image> images = Database.getImages();
    public static Map<Integer, User> users = Database.getUsers();
	
    public static HttpServer startServer() throws UnknownHostException {
        // create a resource config that scans for JAX-RS resources and providers
        // in de.htwsaar.dfs.iosbootstrap package
        final ResourceConfig rc = new ResourceConfig().packages("de.htwsaar.dfs.resource");
        rc.register(MultiPartFeature.class);
        rc.register(LoggingFilter.class);
        rc.register(SecurityFilter.class);

               
        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create("http://"+getIP()+":8080/iosbootstrap/v1/"), rc);
    }
    
    //just let full the database
    private static void putInDb() {
    	
		users.put( 1, new User(1, "user", "user"));
		users.put( 2, new User(2, "User", "password"));
		
		String imgesHttpURL = "https://picsum.photos/1200/300?image=";
		String thumbnailHttpURL ="https://picsum.photos/200/200?image=";
	    
		String[] locations = {"Berlin", "Yaounde", "Milan" , "Paris" , "Saarbrucken"};
		String[] tags = {"Hochzeit", "Urlaub","Urlaub2017"};
		for ( long j=1; j<150 ; j++) {
			int uid = (int)(j%2)+1;
			LinkedList<String> tagList = new LinkedList<>();
			tagList.add(tags[(int) (j%tags.length)]);
			tagList.add(tags[(int) (j  %(tags.length -1))]);
			Image im = new Image(j, uid, "Bild"+j,
	    			new Metadata(users.get(uid).getName(), new Date(), 
	    					locations[(int) (j%locations.length)], tagList), 
	    			imgesHttpURL+j, thumbnailHttpURL+j);
	    	images.put(j, im);
	    }
	}
    
    static public String getIP() throws UnknownHostException {
    	return InetAddress.getLocalHost().getHostAddress();
    }
    
    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
    	putInDb();
        startServer();
       
        System.in.read();
      
    }
}

