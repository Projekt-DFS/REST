package de.htwsaar.dfs.iosbootstrap.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/secured")
public class SecuredRessource {

	@GET
	@Path("/message")
	@Produces(MediaType.TEXT_PLAIN)
	public String secureMethod() {
		return "This app is secured";
	}
}
