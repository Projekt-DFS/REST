package de.htwsaar.dfs.iosbootstrap;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import de.htwsaar.dfs.iosbootstrap.model.Image;
import de.htwsaar.dfs.iosbootstrap.model.Metadata;
import de.htwsaar.dfs.iosbootstrap.model.User;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.Map;


/**
 * Main Class
 * Starts the Server
 * @author Aude Nana
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI =  // "http://192.168.1.5:8080/iosbootstrap/v1/";
    "http://localhost:8080/iosbootstrap/v1/";
    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    
    public static Map<Long, Image> images = Database.getImages();
    public static Map<Long, User> users = Database.getUsers();
	
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in de.htwsaar.dfs.iosbootstrap package
        final ResourceConfig rc = new ResourceConfig().packages("de.htwsaar.dfs.iosbootstrap.resource");
        rc.register(MultiPartFeature.class);
        rc.register(LoggingFilter.class);
        rc.register(SecurityFilter.class);
        
        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }
    
    private static void putInDb() {
    	//images
		Image i = new Image(1, 1, "Noname",
						new Metadata("nana", new Date(),"nana", null),
						"C:/Users/Aude/Documents/Studium/Projektarbeit/Bilder/fernbedin.jpg",
						"C:/Users/Aude/Documents/Studium/Projektarbeit/Bilder/fernbedin.jpg");
		Image i2 = new Image(2, 2, "pff",
						new Metadata("nana", new Date(),"nana", null),
						"C:/Users/Aude/Documents/Studium/Projektarbeit/Bilder/question.jpg",
					"C:/Users/Aude/Documents/Studium/Projektarbeit/Bilder/question.jpg");
		images.put(1L, i);
		images.put(2L,i2);
		
		//users
		users.put( 1L, new User(1, "user", "user"));
		users.put( 2L, new User(2, "User", "password"));

    }
 
    
    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
    	putInDb();
        startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
       // server.stop();
    }
}

