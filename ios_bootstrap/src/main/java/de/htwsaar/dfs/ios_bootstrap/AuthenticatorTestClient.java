package de.htwsaar.dfs.ios_bootstrap;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;


import de.htwsaar.dfs.ios_bootstrap.model.User;


public class AuthenticatorTestClient {

	//URL for the request
    private static String userHttpURL = "http://localhost:8080/ios_bootstrap/rest/users";
    
    public static void main(String []args) throws IOException {
    	authenticate("aude", "aude");
    	authenticate("anana", "dfs");
    	authenticate("user", "password");	
    	
    }
    
    private static boolean authenticate(String username , String password) {
    	Client client = ClientBuilder.newClient();
    	Response response = client.target(userHttpURL).request().get();
   
    	List<User> users =  (List<User>) response.readEntity(new GenericType<List<User>>() {
        });
    	for ( User user : users) {
			if (user.getUsername().equals(username))
				if(user.getUserpasswd().equals(password)) {
					System.out.println(username +" : succeed !");
					return true;
				}
		}
		
		return false;
	}
}
